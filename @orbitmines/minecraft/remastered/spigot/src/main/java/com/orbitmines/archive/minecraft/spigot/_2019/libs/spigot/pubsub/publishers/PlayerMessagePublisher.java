package com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.pubsub.publishers;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.jedis.SpigotPublisher;
import lombok.AllArgsConstructor;

import java.util.UUID;

public class PlayerMessagePublisher extends SpigotPublisher.Simple<PlayerMessagePublisher.Message> {

    public PlayerMessagePublisher() {
        super("player_message", Message.class);
    }

    public void publish(UUID uuid, String message) {
        publish(new Message(
            uuid.toString(),
            message
        ));
    }

    @AllArgsConstructor
    public class Message {

        String uuid;
        String message;

    }
}
