package com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.pubsub.subscribers;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMServer;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.pubsub.SpigotSubscriber;
import lombok.AllArgsConstructor;

import java.util.UUID;

public class PlayerVoteSubscriber extends SpigotSubscriber.Simple<PlayerVoteSubscriber.Message> {

    private final OMServer<?, ? extends OMPlayer> plugin;

    public PlayerVoteSubscriber(OMServer<?, ? extends OMPlayer> plugin) {
        super("player_vote", Message.class);

        this.plugin = plugin;
    }

    @Override
    protected void onMessage(Message object) {
        OMPlayer player = plugin.getPlayer(UUID.fromString(object.uuid));

        if (player == null)
            return;

        player.handleVotes();
    }

    @AllArgsConstructor
    public class Message {

        String uuid;
        String serverList;

    }
}
