package com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.pubsub.publishers;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.jedis.SpigotPublisher;
import lombok.AllArgsConstructor;

import java.util.UUID;

public class PlayerFriendsUpdatePublisher extends SpigotPublisher.Simple<PlayerFriendsUpdatePublisher.Message> {

    public PlayerFriendsUpdatePublisher() {
        super("player_friends_update", Message.class);
    }

    public void publish(UUID player) {
        publish(new Message(
            player.toString()
        ));
    }

    @AllArgsConstructor
    public class Message {

        String uuid;

    }
}
