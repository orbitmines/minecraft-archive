package com.orbitmines.archive.minecraft.bungeecord._2019.servers.bungeecord.libs.discord;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.discord.CustomChannel;
import com.orbitmines.archive.minecraft._2019.libs.discord.CustomEmote;
import com.orbitmines.archive.minecraft._2019.libs.discord.CustomRole;
import com.orbitmines.archive.minecraft._2019.libs.discord.OMDiscordBot;
import com.orbitmines.archive.minecraft.bungeecord._2019.servers.bungeecord.libs.Bungeecord;
import com.orbitmines.archive.minecraft.bungeecord._2019.servers.bungeecord.libs.discord.commands.*;
import com.orbitmines.archive.minecraft.bungeecord._2019.servers.bungeecord.libs.discord.events.PlayerJoinEvent;
import com.orbitmines.archive.minecraft.bungeecord._2019.servers.bungeecord.utils.ConsoleUtils;
import com.orbitmines.archive.minecraft._2019.utils.SkinLibrary;
// var moved in JDA 5
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.entities.channel.Channel;
import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.entities.channel.concrete.Category;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.entities.emoji.RichCustomEmoji;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.entities.channel.attribute.ICategorizableChannel;
import net.dv8tion.jda.api.entities.channel.middleman.GuildChannel;
import net.dv8tion.jda.api.managers.channel.ChannelManager;
import net.dv8tion.jda.api.managers.RoleManager;
import org.apache.commons.lang.NotImplementedException;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class BungeeDiscordBot extends OMDiscordBot {

    private final Bungeecord bungeecord;

    public BungeeDiscordBot(String token, String serverId, Bungeecord bungeecord, SkinLibrary skinLibrary) {
        super(token, serverId, skinLibrary);

        this.bungeecord = bungeecord;
    }

    @Override
    public void initialize(OnlineStatus status) {
        super.initialize(status);

        /* Cleanup */
        deleteAllExistingPlayerEmotes();

        /* Setup */
        setupCustomEmotes();
        setupRoles();
        setupCustomChannels();
        setupCommands();
    }

    public void setupCommands() {
        new HelpCommand(this, "help", "?");
        new DiscordLinkCommand(this, "discordlink", "link");
        new VoteCommand(this, "vote", "votelinks");
        new ClearchatCommand(this, "clearchat", "clear");
        new IPCommand(this, "ip", "server", "serverip");
        new ListCommand(this, "list");
        new ShopCommand(this, "shop", "webshop", "donate", "rank");
        new SiteCommand(this, "site", "website");
        new InitCommand(this, "init");
        new StatsCommand(this, "stats", "statistics");
        new TopvotersCommand(this, "votetop", "topvoters");
    }


    @Override
    public void registerEvents() {
        super.registerEvents();
        getJda().addEventListener(new PlayerJoinEvent(this, getTextChannel(CustomChannel.COMMUNITY)));
    }

    @Override
    protected void withPlayerEmoteAsync(Runnable runnable) {
        bungeecord.getProxy().getScheduler().runAsync(bungeecord.getPlugin(), runnable);
    }

    @Override
    public void processMessage(MessageReceivedEvent event) {

    }

    private void setupCustomEmotes() {
        for (CustomEmote emote : CustomEmote.values()) {
            RichCustomEmoji discordEmote = getEmote(emote);
            if (discordEmote != null) {
                changeCustomEmote(discordEmote, emote);
                continue;
            }

            createCustomEmote(emote);
        }
    }
    private void changeCustomEmote(RichCustomEmoji discordEmote, CustomEmote emote) {
        var manager = discordEmote.getManager();
        boolean changed = false;

        if (!discordEmote.getName().equals(emote.getName())) {
            manager = manager.setName(emote.getName());
            changed = true;
        }

        if (changed)
            manager.queue();
    }
    private void createCustomEmote(CustomEmote emote) {
        Guild controller = getController();

        ConsoleUtils.msg("Adding " + emote.getName() + " emote to the Discord Server...");

        Icon icon;
        try {
            icon = Icon.from(new URL(emote.getImage().getUrl()).openStream());
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        controller.createEmoji(emote.getName(), icon).
                queue((e) -> ConsoleUtils.msg("Successfully added " + emote.getName() + " emote to the Discord Server."));
    }

    private void setupCustomChannels() {
//        for (CustomChannel channel : CustomChannel.values()) {
//            Channel discordChannel = getChannel(channel);
//            if (discordChannel != null) {
////                changeCustomChannel(discordChannel, channel);
//                continue;
//            }
//
//            createCustomChannel(channel);
//        }
        Set<String> categoryNames = Arrays.stream(CustomChannel.values())
                .map(CustomChannel::getCategoryName)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        Map<String, Category> categoryMap = new HashMap<>();

        List<CompletableFuture<Void>> futures = new ArrayList<>();

        for (String name : categoryNames) {
            List<Category> existing = getGuild().getCategoriesByName(name, true);

            if (!existing.isEmpty()) {
                categoryMap.put(name, existing.get(0));
                continue;
            }

            CompletableFuture<Void> future = new CompletableFuture<>();

            getGuild().createCategory(name).queue(category -> {
                categoryMap.put(name, category); // ✅ use returned object
                future.complete(null);
            }, future::completeExceptionally);

            futures.add(future);
        }

        CompletableFuture
            .allOf(futures.toArray(new CompletableFuture[0]))
            .thenRun(() -> {

                for (CustomChannel cc : CustomChannel.values()) {

                    Category category = null;

                    if (cc.getCategoryName() != null) {
                        category = categoryMap.get(cc.getCategoryName());
                    }

                    if (cc.getChannelType() == ChannelType.TEXT) {
                        Channel discordChannel = getChannel(cc);
                        if (discordChannel != null) {
                            continue;
                        }

                        ConsoleUtils.msg("Adding " + cc.getName() + " channel to the Discord Server...");

                        if (category != null) {
                            category.createTextChannel(cc.getName()).queue((channel) -> ConsoleUtils.msg("Successfully added " + channel.getName() + " channel to the Discord Server."));
                        } else {
                            getGuild().createTextChannel(cc.getName()).queue((channel) -> ConsoleUtils.msg("Successfully added " + channel.getName() + " channel to the Discord Server."));
                        }
                    } else {
                        throw new NotImplementedException();
                    }
                }
            });
    }
    @SuppressWarnings({"unchecked", "rawtypes"})
//    private void changeCustomChannel(Channel discordChannel, CustomChannel channel) {
//        ICategorizableChannel categorizableChannel = (ICategorizableChannel) discordChannel;
//        GuildChannel guildChannel = (GuildChannel) discordChannel;
//        var manager = categorizableChannel.getManager();
//        boolean changed = false;
//
//        if (categorizableChannel.getParentCategory() != null && !categorizableChannel.getParentCategory().getName().equals(channel.getCategoryName())) {
//            Category category = channel.getCategory(this);
//
//            if (category != null) {
//                manager = manager.setParent(category);
//                changed = true;
//            } else {
//                Guild controller = getController();
//                controller.createCategory(channel.getCategoryName()).queue((c) -> ((ICategorizableChannel) discordChannel).getManager().setParent((Category) c).queue());
//            }
//        }
//        if (!guildChannel.getName().equals(channel.getName())) {
//            manager = manager.setName(channel.getName());
//            changed = true;
//        }
//
//        if (changed)
//            manager.queue();
//    }
//    private void createCustomChannel(CustomChannel channel) {
//        Guild controller = getController();
//
//        ConsoleUtils.msg("Adding " + channel.getName() + " channel to the Discord Server...");
//
//        if (channel.getCategoryName() != null) {
//            Category category = channel.getCategory(this);
//            if (category == null) {
//                controller.createCategory(channel.getCategoryName()).queue((c) -> createCustomChannel((Category) c, channel));
//                return;
//            }
//            createCustomChannel(category, channel);
//        } else {
//            createCustomChannel(null, channel);
//        }
//    }
//    private void createCustomChannel(Category category, CustomChannel channel) {
//        switch (channel.getChannelType()) {
//
//            case TEXT:
//                category.createTextChannel(channel.getName()).
//                        queue((e) -> ConsoleUtils.msg("Successfully added " + channel.getName() + " channel to the Discord Server."));
//                break;
//            case VOICE:
//                category.createVoiceChannel(channel.getName()).
//                        queue((e) -> ConsoleUtils.msg("Successfully added " + channel.getName() + " voice channel to the Discord Server."));
//                break;
//        }
//    }
    
    private void setupRoles() {
        for (CustomRole role : CustomRole.values()) {
            Role discordRole = getRole(role);
            if (discordRole != null) {
                changeCustomRole(discordRole, role);
                continue;
            }

            createCustomRole(role);
        }
    }
    private void changeCustomRole(Role discordRole, CustomRole role) {
        RoleManager manager = discordRole.getManager();
        boolean changed = false;

        if (!discordRole.getName().equals(role.getName())) {
            manager = manager.setName(role.getName());
            changed = true;
        }

        if (!discordRole.getColor().equals(role.getColor())) {
            manager = manager.setColor(role.getColor());
            changed = true;
        }

        if (discordRole.isMentionable() != role.isMentionable()) {
            manager = manager.setMentionable(role.isMentionable());
            changed = true;
        }

        if (changed)
            manager.queue();
    }
    private void createCustomRole(CustomRole role) {
        Guild controller = getController();

        ConsoleUtils.msg("Adding " + role.getName() + " role to the Discord Server...");

        controller.createRole().
                setName(role.getName()).
                setColor(role.getColor()).
                setMentionable(role.isMentionable())
                .queue((r) -> ConsoleUtils.msg("Successfully added " + role.getName() + " role to the Discord Server."));
    }
}
