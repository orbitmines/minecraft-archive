package com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.discord;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.Server;
import com.orbitmines.archive.minecraft._2019.libs.database.models.discord.DiscordUser;
import com.orbitmines.archive.minecraft._2019.libs.database.models.discord.squad.DiscordSquad;
import com.orbitmines.archive.minecraft._2019.libs.discord.CustomChannel;
import com.orbitmines.archive.minecraft._2019.libs.discord.CustomEmote;
import com.orbitmines.archive.minecraft._2019.libs.discord.OMDiscordBot;
import com.orbitmines.archive.minecraft._2019.libs.player.OfflinePlayer;
import com.orbitmines.archive.minecraft._2019.libs.player.PlayerInstance;
import com.orbitmines.archive.minecraft._2019.libs.player.UnknownPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.ChatHandler;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMServer;
import com.orbitmines.archive.minecraft._2019.utils.SkinLibrary;
import com.orbitmines.archive.minecraft._2019.utils.discord.DiscordEmote;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.placeholders.SpigotServer;
import lombok.Getter;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.channel.concrete.Category;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class SpigotDiscordBot extends OMDiscordBot {

    public static final String SKULL_TEXTURE = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjJlZmE4M2M5OTgyMzNlOWRlYWY3OTc1YWNlNGNkMTZiNjM2MmE4NTlkNTY4MmMzNjMxNGQxZTYwYWYifX19";

    @Getter private final OMServer server;
    @Getter private final DiscordEmote emote;

    public SpigotDiscordBot(String token, String serverId, OMServer server, SkinLibrary skinLibrary) {
        super(token, serverId, skinLibrary);

        this.server = server;
        this.emote = CustomEmote.from(server.getType());
    }

    @Override
    protected void withPlayerEmoteAsync(Runnable runnable) {
        server.runAsync(runnable);
    }

    @Override
    public void initialize(OnlineStatus status) {
        super.initialize(status);

        if (server.getType() == Server.CREATIVE)
            return;

        Category category = getServersCategory();
        if (category == null) {
            initializeCategory();
            return;
        }

        TextChannel textChannel = getTextChannel();
        if (textChannel == null) {
            initializeChannel(category);
        }
    }

    @Override
    public void processMessage(MessageReceivedEvent event) {
        SpigotServer.getInstance().runAsync(() -> {
            User user = event.getAuthor();

            if (user.isBot())
                return;

            TextChannel channel = event.getChannel().asTextChannel();
            Category category = getServersCategory();

            if (channel.getIdLong() == getTextChannel(CustomChannel.STAFF).getIdLong()) {
                newChatHandler(user, ChatHandler.Type.STAFF_CHAT, event.getMessage()).handleDiscordMessage(event.getMessage());
                return;
            }

            if (server.getType() == Server.CREATIVE && channel.getIdLong() == getTextChannel(CustomChannel.BUILDER).getIdLong()) {
                newChatHandler(user, ChatHandler.Type.BUILDER_CHAT, event.getMessage()).handleDiscordMessage(event.getMessage());
                return;
            }

            DiscordSquad squad = DiscordSquad.findBy(DiscordSquad.class, DiscordSquad.column.TEXT_CHANNEL_ID.is(channel.getIdLong()));
            if (squad != null) {
                ChatHandler chatHandler = newChatHandler(user, ChatHandler.Type.DISCORD_SQUAD, event.getMessage());
                chatHandler.setSquad(squad);
                chatHandler.handleDiscordMessage(event.getMessage());
                return;
            }

            if (channel.getParentCategory() == null || channel.getParentCategory().getIdLong() != category.getIdLong())
                return;

            if (!category.getTextChannels().contains(channel))
                return;

            if (!channel.getName().equals(getChannelName()))
                return;

            newChatHandler(user, ChatHandler.Type.NORMAL, event.getMessage()).handleDiscordMessage(event.getMessage());
        });
    }

    private ChatHandler newChatHandler(User user, ChatHandler.Type type, Message message) {
        DiscordUser discordUser = DiscordUser.findBy(DiscordUser.class, DiscordUser.column.DISCORD_USER_ID.is(user.getIdLong()));

        PlayerInstance sender = discordUser != null ? OfflinePlayer.get(discordUser.getUuid()) : UnknownPlayer.get("@" + user.getName());

        return server.newChatHandler(sender, type, message.getContentDisplay());
    }

    private void initializeCategory() {
        getController().createCategory("Servers").queue(category -> initializeChannel(getServersCategory()));
    }

    private void initializeChannel(Category category) {
        getController().createTextChannel(getChannelName()).setParent(category).setTopic(getChannelTopic()).queue();
    }

    private String getChannelName() {
        return server.getType().toString().toLowerCase(); //+ "_chat";
    }

    private String getChannelTopic() {
        return "The in-game " + this.server.getType().getName() + " chat.";
    }

    public Category getServersCategory() {
        return getCategory("Servers");
    }

    public TextChannel getTextChannel() {
        if (server.getType() == Server.CREATIVE)
            return getTextChannel(CustomChannel.BUILDER);

        String channelName = getChannelName();

        for (TextChannel channel : getServersCategory().getTextChannels()) {
            if (channel.getName().equals(channelName))
                return channel;
        }

        return null;
    }
}
