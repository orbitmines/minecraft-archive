package com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.pubsub.publishers;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.jedis.SpigotPublisher;
import lombok.AllArgsConstructor;

public class PlayerKickPublisher extends SpigotPublisher.Simple<PlayerKickPublisher.Message> {

    public PlayerKickPublisher() {
        super("player_kick", Message.class);
    }

    public void publish(OMPlayer player, String message) {
        publish(new Message(
            player.getUniqueId().toString(),
            message
        ));
    }

    @AllArgsConstructor
    public class Message {

        String uuid;
        String message;

    }
}
