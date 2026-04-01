package com.orbitmines.archive.minecraft.bungeecord._2019.servers.bungeecord.libs.discord.commands;

import com.google.common.collect.Iterators;
import com.orbitmines.archive.minecraft._2019.libs.Server;
import com.orbitmines.archive.minecraft._2019.libs.database.models.PlayerModel;
import com.orbitmines.archive.minecraft._2019.libs.database.models.discord.DiscordUser;
import com.orbitmines.archive.minecraft._2019.libs.discord.CustomEmote;
import com.orbitmines.archive.minecraft._2019.libs.discord.DiscordCommand;
import com.orbitmines.archive.minecraft._2019.libs.discord.OMDiscordBot;
import com.orbitmines.archive.minecraft._2019.libs.jedis.OnlinePlayer;
import com.orbitmines.archive.minecraft._2019.libs.player.PlayerInstance;
import com.orbitmines.archive.minecraft._2019.libs.player.UnknownPlayer;
import lombok.AllArgsConstructor;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.entities.emoji.RichCustomEmoji;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.requests.restaction.MessageCreateAction;

import java.util.*;

public class ListCommand extends DiscordCommand {

    //TODO SETUP FOR DISCORD_SQUAD, STAFF_CHAT & BUILDER_CHAT

    public ListCommand(OMDiscordBot bot, String... alias) {
        super(bot, "Display all online players", alias);
    }

    @Override
    public void onDispatch(MessageReceivedEvent event, User user, Member member, MessageChannel ch, Message msg, String[] a) {
        TextChannel channel = (TextChannel) ch;

        DiscordUser linkedUser = DiscordUser.findBy(DiscordUser.class, DiscordUser.column.DISCORD_USER_ID.is(user.getIdLong()));
        PlayerInstance sender = linkedUser != null ? PlayerModel.findBy(PlayerModel.class, PlayerModel.column.UUID.is(linkedUser.getUuid())) : UnknownPlayer.get("null");

        for (ChannelType type : ChannelType.values()) {
            if (!type.withinChannel(sender, bot, channel))
                continue;

            PlayerList list = new PlayerList(sender, type, channel);
            list.broadcast();
            break;
        }
    }

    public class PlayerList {

        private final PlayerInstance sender;
        private final ChannelType type;
        private final TextChannel channel;

        private List<OnlinePlayer> allPlayers;
        private Map<Server, List<OnlinePlayer>> mapped;

        private Iterator<Server> serverIterator;
        private final int batchSize = 10;
        private Iterator<List<OnlinePlayer>> batches;
        private Iterator<OnlinePlayer> currentBatch;
        private StringBuilder batchBuilder;

        public PlayerList(PlayerInstance sender, ChannelType type, TextChannel channel) {
            this.sender = sender;
            this.type = type;
            this.channel = channel;

            allPlayers = type.getPlayers(type != ChannelType.STAFF ? null : sender, bot, channel);
            mapped = mappedByServer(allPlayers);
            serverIterator = mapped.keySet().iterator();
        }

        public void broadcast() {
            header().queue(message -> nextServer());
        }

        private void nextServer() {
            if (!serverIterator.hasNext())
                return;

            Server server = serverIterator.next();

            if (type != ChannelType.STAFF && !sender.isEligible(server.getRank())) {
                nextServer();
                return;
            }

            List<OnlinePlayer> players = mapped.get(server);

            batches = Iterators.partition(players.iterator(), batchSize);

            if (type == ChannelType.SERVERS) {
                nextBatch();
                return;
            }

            serverHeader(server).queue(m -> nextBatch());
        }

        private void nextBatch() {
            if (!batches.hasNext()) {
                nextServer();
                return;
            }

            currentBatch = batches.next().iterator();
            batchBuilder = new StringBuilder();
            nextPlayer();
        }

        private void nextPlayer() {
            if (!currentBatch.hasNext()) {
                channel.sendMessage(batchBuilder.toString()).queue(message -> nextBatch());
                return;
            }

            OnlinePlayer player = currentBatch.next();
            bot.withPlayerEmote(player.getUUID(), player.getRawName(), false, emote -> {
                if (batchBuilder.length() != 0)
                    batchBuilder.append(", ");

                batchBuilder.append(bot.getPlayerDisplay(player, emote, player.getRawName()));

                nextPlayer();
            });
        }

        private MessageCreateAction header() {
            return channel.sendMessage("There " + (allPlayers.size() == 1 ? "is" : "are") + " currently **" + allPlayers.size() + " " + (allPlayers.size() == 1 ? "Player " : "Players") + "** online on **" + type.getName(sender, bot, channel) + "**");
        }

        private MessageCreateAction serverHeader(Server server) {
            return channel.sendMessage("**" + bot.getEmote(CustomEmote.from(server)).getAsMention() + server.getName() + " (" + mapped.get(server).size() + ")** » ");
        }

        private Map<Server, List<OnlinePlayer>> mappedByServer(List<OnlinePlayer> list) {
            Map<Server, List<OnlinePlayer>> players = new HashMap<>();

            for (OnlinePlayer player : list) {
                players.computeIfAbsent(player.getServer(), key -> new ArrayList<>()).add(player);
            }

            return players;
        }
    }

    @AllArgsConstructor
    public enum ChannelType {

        SERVERS() {
            @Override
            public boolean withinChannel(PlayerInstance sender, OMDiscordBot bot, TextChannel channel) {
                return channel.getParentCategory() != null && channel.getParentCategory().getName().equals("SERVERS");
            }

            @Override
            public List<OnlinePlayer> getPlayers(PlayerInstance sender, OMDiscordBot bot, TextChannel channel) {
                Server server = getServer(channel);

                return server != null ? OnlinePlayer.getAllByServer(null).get(server) : super.getPlayers(null, bot, channel);
            }

            private Server getServer(TextChannel channel) {
                for (Server server : Server.playable()) {
                    if (channel.getName().startsWith(server.getName().toLowerCase() + "_"))
                        return server;
                }
                return null;
            }

            @Override
            public String getName(PlayerInstance sender, OMDiscordBot bot, TextChannel channel) {
                Server server = getServer(channel);

                return server != null ? bot.getEmote(CustomEmote.from(server)).getAsMention() + server.getName() : super.getName(null, bot, channel);
            }
        },
        DISCORD_SQUAD() {

        },
        STAFF() {

        },
        OTHER;

        public boolean withinChannel(PlayerInstance sender, OMDiscordBot bot, TextChannel channel) {
            return true;
        }

        public List<OnlinePlayer> getPlayers(PlayerInstance sender, OMDiscordBot bot, TextChannel channel) {
            return OnlinePlayer.getAll(sender);
        }

        public String getName(PlayerInstance sender, OMDiscordBot bot, TextChannel channel) {
            return bot.getEmote(CustomEmote.orbitmines).getAsMention() + "OrbitMines";
        }
    }
}
