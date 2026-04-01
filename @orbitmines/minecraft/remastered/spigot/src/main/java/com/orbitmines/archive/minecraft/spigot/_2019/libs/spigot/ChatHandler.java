package com.orbitmines.archive.minecraft.spigot._2019.libs.spigot;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft._2019.libs.Environment;
import com.orbitmines.archive.minecraft._2019.libs.database.models.discord.DiscordUser;
import com.orbitmines.archive.minecraft._2019.libs.database.models.discord.squad.DiscordSquad;
import com.orbitmines.archive.minecraft._2019.libs.discord.CustomChannel;
import com.orbitmines.archive.minecraft._2019.libs.discord.CustomEmote;
import com.orbitmines.archive.minecraft._2019.libs.jedis.OnlinePlayer;
import com.orbitmines.archive.minecraft._2019.libs.player.Name;
import com.orbitmines.archive.minecraft._2019.libs.player.PlayerInstance;
import com.orbitmines.archive.minecraft._2019.libs.player.UnknownPlayer;
import com.orbitmines.archive.minecraft._2019.libs.rank.Rank;
import com.orbitmines.archive.minecraft._2019.libs.rank.StaffRank;
import com.orbitmines.archive.minecraft._2019.libs.rank.VipRank;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.Command;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.builder.CommandHelpBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.discord.SpigotDiscordBot;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.chat.text.TextBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.chat.text.TextComponent;
import lombok.Setter;
import net.dv8tion.jda.api.entities.*;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;

import java.util.*;

public class ChatHandler<S extends OMServer<S, P>, P extends OMPlayer<S, P>> {

    private static final int MAX_COMMAND_MENTIONS = 2;

    protected final S server;
    protected final Type type;
    protected final SpigotDiscordBot bot;
    protected final Guild guild;

    protected final PlayerInstance sender;
    protected String message;

    private int commandCount = 0;

    @Setter private DiscordSquad squad;

    public ChatHandler(S server, Type type, PlayerInstance sender) {
        this(server, type, sender, null);
    }

    public ChatHandler(S server, Type type, PlayerInstance sender, String message) {
        this.server = server;
        this.type = type;
        this.bot = server.getDiscordBot();
        this.guild = this.bot.getGuild();
        this.sender = sender;
        this.message = message;
    }

    public void handleChatMessage() {
        minecraftToMinecraft();
        minecraftToDiscord();
    }

    public void handleDiscordMessage(Message discordMessage) {
        discordToMinecraft(discordMessage);
    }

    /*

        From Minecraft

     */

    protected void minecraftToMinecraft() {
        TextBuilder<P> parent = new TextBuilder<>();

        switch (this.type) {
            case STAFF_CHAT: {
                parent.add(Color.AQUA, p -> com.orbitmines.archive.minecraft._2019.libs.utils.Message.format("Staff", Color.AQUA, ""))
                    .hover(HoverEvent.Action.SHOW_TEXT, p -> "§7" + p.translate("spigot", "player.group_chat", "§9@<message>§7"))
                    .click(ClickEvent.Action.SUGGEST_COMMAND, p -> "@");
                break;
            }
            case DISCORD_SQUAD: {
                parent.add(squad.getColor(), p -> com.orbitmines.archive.minecraft._2019.libs.utils.Message.format(squad.getName(), squad.getColor(), ""))
                    .hover(HoverEvent.Action.SHOW_TEXT, p -> "§7" + p.translate("spigot", "player.group_chat", "§9!<message>§7"))
                    .click(ClickEvent.Action.SUGGEST_COMMAND, p -> "!");
                break;
            }
        }

        /* Prefix & Sender Name */
        appendSender(parent);
        parent.add(Color.SILVER, player -> " » ");

        /* Actual Message */
        Rank rank = sender.getRank();

        for (P receiver : getPlayers()) {
            boolean mentioned = message.toLowerCase().contains(receiver.getRawName().toLowerCase());
            Color messageColor = getChatColor(rank, mentioned);

            TextBuilder<P> builder = parent.copy();
            appendMessage(builder, receiver, mentioned, messageColor, message);

            builder.send(receiver);
        }
    }

    protected void minecraftToDiscord() {
        UUID uuid = sender.getUUID();
        String playerName = sender.getName(Name.RAW);

        filterMessageToDiscord();

        String prefix;

        if (this.type == Type.STAFF_CHAT || this.type == Type.DISCORD_SQUAD)
            prefix = bot.getEmote(CustomEmote.from(server.getType())).getAsMention();
        else
            prefix = "";

        TextChannel channel;
        switch (this.type) {

            case NORMAL:
                channel = bot.getTextChannel();
                break;
            case STAFF_CHAT:
                channel = bot.getTextChannel(CustomChannel.STAFF);
                break;
            case BUILDER_CHAT:
                channel = bot.getTextChannel(CustomChannel.BUILDER);
                break;
            case DISCORD_SQUAD:
                channel = squad.getTextChannel(bot);
                break;
            default:
                throw new IllegalStateException();
        }

        bot.withPlayerEmote(uuid, playerName, false, (emote) ->
            channel.sendMessage(
                prefix + bot.getPlayerDisplay(sender, emote, playerName) + " » " + this.message
            ).queue()
        );
    }

    /*

        From Discord

     */

    protected void discordToMinecraft(Message discordMessage) {
        String discordLink = Environment.get("OM_DISCORD_INVITE_LINK", "https://www.orbitmines.com/discord");

        TextBuilder<P> parent = new TextBuilder<>();

        switch (this.type) {
            case STAFF_CHAT: {
                parent.add(Color.AQUA, p -> com.orbitmines.archive.minecraft._2019.libs.utils.Message.format("Staff", Color.AQUA, ""))
                    .hover(HoverEvent.Action.SHOW_TEXT, p -> "§7" + p.translate("spigot", "player.group_chat", "§9@<message>§7"))
                    .click(ClickEvent.Action.SUGGEST_COMMAND, p -> "@");
                break;
            }
            case DISCORD_SQUAD: {
                parent.add(squad.getColor(), p -> com.orbitmines.archive.minecraft._2019.libs.utils.Message.format(squad.getName(), squad.getColor(), ""))
                    .hover(HoverEvent.Action.SHOW_TEXT, p -> "§7" + p.translate("spigot", "player.group_chat", "§9!<message>§7"))
                    .click(ClickEvent.Action.SUGGEST_COMMAND, p -> "!");
                break;
            }
            case BUILDER_CHAT: {
                parent.
                    add(Color.FUCHSIA, player -> "BUILD").bold().
                    click(ClickEvent.Action.OPEN_URL, player -> discordLink).
                    hover(HoverEvent.Action.SHOW_TEXT, player -> "§7" + player.translate("spigot", "word.open_link") + " " + Color.DISCORD.getCc() + discordLink).
                    add(Color.SILVER, player -> " » ");
                break;
            }
            case NORMAL: {
                parent.
                    add(Color.DISCORD, player -> "DISCORD").bold().
                    click(ClickEvent.Action.OPEN_URL, player -> discordLink).
                    hover(HoverEvent.Action.SHOW_TEXT, player -> "§7" + player.translate("spigot", "word.open_link") + " " + Color.DISCORD.getCc() + discordLink).
                    add(Color.SILVER, player -> " » ");
                break;
            }
        }

        /* Prefix & Sender Name */
        appendSender(parent);
        parent.add(Color.SILVER, player -> " » ");

        filterMessageFromDiscord(discordMessage);

        /* File attachment */
        Message.Attachment attachment = discordMessage.getAttachments().size() > 0 ? discordMessage.getAttachments().get(0) : null;

        /* Actual Message */
        Rank rank = sender.getRank();

        for (P receiver : getPlayers()) {
            boolean mentioned = message.toLowerCase().contains(receiver.getRawName().toLowerCase());
            Color messageColor = getChatColor(rank, mentioned);

            TextBuilder<P> builder = parent.copy();
            appendMessage(builder, receiver, mentioned, messageColor, message);

            /* File attachment */
            if (attachment != null) {
                if (!this.message.isEmpty())
                    builder.space();

                builder.
                        add(messageColor, player -> "(" + player.translate("spigot", "word.file") + ": ").
                        add(messageColor, player -> attachment.getFileName()).bold().
                            click(ClickEvent.Action.OPEN_URL, player -> attachment.getUrl()).
                            hover(HoverEvent.Action.SHOW_TEXT, player -> "§7" + player.translate("spigot", "player.open_file.hover", "§l" + attachment.getFileName() + "§7")).
                        add(messageColor, player -> ")");
            }

            builder.send(receiver);
        }
    }

    protected void filterMessageFromDiscord(Message discordMessage) {
        message = message.replaceAll("\\n", " ");

        for (Role role : discordMessage.getMentionedRoles()) {
            message = message.replaceAll(role.getAsMention(), "@" + role.getName());
        }
        for (TextChannel textChannel : discordMessage.getMentionedChannels()) {
            message = message.replaceAll(textChannel.getAsMention(), "#" + textChannel.getName());
        }
        for (Member mem : discordMessage.getMentionedMembers()) {
            message = message.replaceAll(mem.getAsMention(), "@" + mem.getEffectiveName());
        }
        for (RichCustomEmoji emote : discordMessage.getEmotes()) {
            message = message.replaceAll(emote.getAsMention(), emote.getName());
        }
    }

    /*

        To Discord

     */

    protected void filterMessageToDiscord() {
        for (Role role : guild.getRoles()) {
            message = message.replaceAll("@" + role.getName(), role.getAsMention());
        }
        for (TextChannel textChannel : guild.getTextChannels()) {
            message = message.replaceAll("#" + textChannel.getName(), textChannel.getAsMention());
        }
        for (Member member : guild.getMembers()) {
            message = message.replace("@" + member.getEffectiveName() + "#" + member.getUser().getDiscriminator(), member.getAsMention()).replaceAll("@" + member.getEffectiveName(), member.getAsMention()).replaceAll("@" + member.getNickname(), member.getAsMention());
        }
        for (RichCustomEmoji emote : guild.getEmotes()) {
            message = message.replaceAll(":" + emote.getName() + ":", emote.getAsMention());
        }

        /* We don't want people using @everyone & @here */
        message = message.replaceAll("@everyone", "[everyone]").replaceAll("@here", "[here]");
    }

    /*

        To Minecraft

     */

    public void appendSender(TextBuilder<P> builder) {
        StringBuilder hover = new StringBuilder().
                append(sender.getName(Name.RAW_COLORED)).append("\n").
                append("§7Rank: ");

        /* Rank */
        StaffRank staffRank = sender.getStaffRank();
        VipRank vipRank = sender.getVipRank();
        if (!staffRank.isNone()) {
            hover.append(staffRank.getDisplayName());

            if (!vipRank.isNone())
                hover.append(" §7/ ").append(vipRank.getDisplayName());
        } else {
            hover.append(vipRank.getDisplayName());
        }

        /* Discord */
        if (!(sender instanceof UnknownPlayer)) {
            hover.append("\n");

            DiscordUser discordUser = sender.getDiscordUser();

            hover.append("§7Discord: ");

            if (discordUser != null) {
                User user = discordUser.getDiscordUser(bot);

                if (user != null)
                    hover.append(sender.getRankColor().getCc() + user.getName() + "#" + user.getDiscriminator());
                else
                    hover.append("§f§l" + Rank.NONE);
            } else {
                hover.append("§f§l" + Rank.NONE);
            }
        }

        /* Nickname */
        if (sender.hasNickName())
            hover.append("\n§7Nickname: ").append(sender.getName(Name.NICK_COLORED));

        /* Build */
        String rawName = sender.getRawName();
        String name = sender.getName(Name.NICK_PREFIXED);
        String hoverString = hover.toString();

        TextComponent<P> component = new TextComponent<>(sender.getRankColor(), player -> name);
        if (sender instanceof OMPlayer || sender instanceof OnlinePlayer)
            component.
                click(ClickEvent.Action.SUGGEST_COMMAND, player -> "/msg " + rawName + " ").
                hover(HoverEvent.Action.SHOW_TEXT, player -> hoverString + "\n\n§a" + player.translate("spigot", "player.chat_mention.hover"));
        else
            component.
                hover(HoverEvent.Action.SHOW_TEXT, player -> hoverString);

        builder.add(component);
    }

    protected void appendMessage(TextBuilder<P> builder, P receiver, boolean mentioned, Color messageColor, String message) {
        String[] words = message.split(" ");

        for (int i = 0; i < words.length; i++) {
            if (i != 0)
                builder.space();

            handleWord(builder, receiver, mentioned, messageColor, words[i]);
        }
    }

    private void handleWord(TextBuilder<P> builder, P receiver, boolean mentioned, Color messageColor, String word) {
        if (word.contains("/") && this.commandCount < MAX_COMMAND_MENTIONS && appendCommand(builder, receiver, mentioned, messageColor, word))
            return;

        appendWord(builder, receiver, mentioned, messageColor, word);
    }

    protected void appendWord(TextBuilder<P> builder, P receiver, boolean mentioned, Color messageColor, String word) {
        builder.add(messageColor, player -> word);
    }

    protected boolean appendCommand(TextBuilder<P> builder, P receiver, boolean mentioned, Color messageColor, String word) {
        for (Command command : Command.getCommands()) {
            Set<String> aliases = command.getAllCommands();
            for (String alias : aliases) {
                if (!word.toLowerCase().endsWith("/" + alias.toLowerCase()))
                    continue;

                /* Don't append staff commands if player is not eligible */
                if (command.getRank() != null && command.getRank() instanceof StaffRank && !receiver.isEligible(command.getRank()))
                    return false;

                int index = word.indexOf(alias);
                String prefix = word.substring(0, index - 1);
                String commandInput = word.substring(index - 1, index + alias.length());
//                String suffix = word.substring(index + alias.length());

                builder.add(messageColor, player -> prefix);
                new CommandHelpBuilder<S, P>().append(builder, receiver, command, command, commandInput, false);
//                builder.add(messageColor, player -> suffix);

                commandCount++;
                return true;
            }
        }

        return false;
    }

    protected Color getChatColor(Rank rank, boolean mentioned) {
        return mentioned ? Color.ORANGE : rank.getDefaultChatColor();
    }

    private Collection<P> getPlayers() {
        switch (this.type) {

            case NORMAL:
            case BUILDER_CHAT:
                return server.getPlayers();
            case STAFF_CHAT: {
                List<P> players = new ArrayList<>();

                for (P player : server.getPlayers()) {
                    if (player.isEligible(StaffRank.BUILDER))
                        players.add(player);
                }

                return players;
            }
            case DISCORD_SQUAD: {
                List<P> players = new ArrayList<>();

                for (OnlinePlayer onlinePlayer : squad.getAllOnline()) {
                    P player = server.getPlayer(onlinePlayer.getUUID());

                    if (player != null)
                        players.add(player);
                }

                return players;
            }
        }

        throw new IllegalStateException();
    }

    public enum Type {

        NORMAL,
        STAFF_CHAT,
        BUILDER_CHAT,
        DISCORD_SQUAD;

    }
}
