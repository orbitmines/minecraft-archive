package com.orbitmines.archive.minecraft.bungeecord._2019.servers.bungeecord.libs.pubsub.publishers;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.ServerList;
import com.orbitmines.archive.minecraft.bungeecord._2019.servers.bungeecord.utils.pubsub.BungeePublisher;
import lombok.AllArgsConstructor;

import java.util.UUID;

public class PlayerVotePublisher extends BungeePublisher.Simple<PlayerVotePublisher.Message> {

    public PlayerVotePublisher() {
        super("player_vote", Message.class);
    }

    public void publish(UUID uuid, ServerList serverList) {
        publish(new Message(
            uuid.toString(),
            serverList.toString()
        ));
    }

    @AllArgsConstructor
    public class Message {

        String uuid;
        String serverList;

    }
}
