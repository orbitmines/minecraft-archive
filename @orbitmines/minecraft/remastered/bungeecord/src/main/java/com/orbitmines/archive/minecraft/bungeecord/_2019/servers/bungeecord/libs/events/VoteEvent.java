package com.orbitmines.archive.minecraft.bungeecord._2019.servers.bungeecord.libs.events;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.ServerList;
import com.orbitmines.archive.minecraft._2019.libs.database.models.PlayerModel;
import com.orbitmines.archive.minecraft._2019.libs.database.models.vote.LastVote;
import com.orbitmines.archive.minecraft._2019.libs.database.models.vote.MonthlyVotes;
import com.orbitmines.archive.minecraft._2019.libs.database.models.vote.PendingVote;
import com.orbitmines.archive.minecraft._2019.libs.discord.CustomChannel;
import com.orbitmines.archive.minecraft._2019.libs.discord.CustomEmote;
import com.orbitmines.archive.minecraft.bungeecord._2019.servers.bungeecord.libs.Bungeecord;
import com.orbitmines.archive.minecraft.bungeecord._2019.servers.bungeecord.libs.pubsub.publishers.PlayerVotePublisher;
import com.orbitmines.archive.minecraft._2019.utils.DateUtils;
import com.orbitmines.archive.minecraft._2019.utils.UUIDUtils;
import com.vexsoftware.votifier.bungee.events.VotifierEvent;
import com.vexsoftware.votifier.model.Vote;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.Date;
import java.util.UUID;

public class VoteEvent implements Listener {

    private final Bungeecord bungee;

    public VoteEvent(Bungeecord bungee) {
        this.bungee = bungee;
    }

    @EventHandler
    public void onVote(VotifierEvent event) {
        Vote vote = event.getVote();

        /* Some voting sites have a space after them for some reason */
        String name = vote.getUsername().replaceAll(" ", "");

        PlayerModel model = PlayerModel.findBy(PlayerModel.class, PlayerModel.column.NAME.is(name));

        if (model == null) {
            bungee.discord(bot -> bot.getTextChannel(CustomChannel.VOTES).sendMessage(bot.getEmote(CustomEmote.barrier).getAsMention() + " Received vote for unknown player '**" + name + "**'.").queue());
            return;
        }

        UUID uuid = model.getUUID();

        ServerList serverList = ServerList.fromDomain(vote.getServiceName());
        if (serverList == null || !serverList.isActive()) {
            bungee.discord(bot -> bot.getTextChannel(CustomChannel.VOTES).sendMessage(bot.getEmote(CustomEmote.barrier).getAsMention() + " Received vote for unknown server list '**" + vote.getServiceName() + "**'.").queue());
            return;
        }

        try {
            String playerName = UUIDUtils.getName(uuid);

            long timestamp = Long.parseLong(vote.getTimeStamp());
            /* MinecraftServers.org and Minecraft-Serverlist.com send timestamps in milliseconds, all others send seconds */
            boolean alreadyMillis = serverList == ServerList.MINECRAFT_SERVERS_ORG
                    || serverList == ServerList.MINECRAFT_SERVERLIST_COM;
            long millis = alreadyMillis ? timestamp : timestamp * 1000L;
            Date date = new Date(millis);

            LastVote lastVote = LastVote.findOrInitializeBy(uuid, serverList);
            lastVote.setLastVoteAt(date);
            lastVote.insertOrUpdate(LastVote.column.LAST_VOTE_AT);

            MonthlyVotes monthlyVotes = MonthlyVotes.findOrInitializeBy(uuid, DateUtils.getMonth(), DateUtils.getYear());
            monthlyVotes.addVote();
            monthlyVotes.insertOrUpdate(MonthlyVotes.column.VOTES);

            new PendingVote(uuid).insert();

            bungee.discord(bot -> bot.withPlayerEmote(uuid, playerName, false, emote -> {
                bot.getTextChannel(CustomChannel.VOTES).sendMessage(bot.getPlayerDisplay(model, emote, model.getRawName()) + " has voted and received " + bot.getEmote(CustomEmote.prismarine_shard).getAsMention() + "**250 Prisms**.").queue();
            }));

            new PlayerVotePublisher().publish(uuid, serverList);
        } catch (Exception ex) {
            ex.printStackTrace();
            bungee.discord(bot -> bot.getTextChannel(CustomChannel.VOTES).sendMessage(bot.getEmote(CustomEmote.barrier).getAsMention() + " An error occured while processing a vote for '**" + name + "**' from server list '**" + vote.getServiceName() + "**'.").queue());
        }
    }
}
