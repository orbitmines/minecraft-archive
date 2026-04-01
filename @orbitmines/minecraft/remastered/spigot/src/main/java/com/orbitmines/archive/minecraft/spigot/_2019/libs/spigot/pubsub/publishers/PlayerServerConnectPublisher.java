package com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.pubsub.publishers;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.Server;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.pubsub.SpigotPublisher;
import lombok.AllArgsConstructor;

public class PlayerServerConnectPublisher extends SpigotPublisher.Simple<PlayerServerConnectPublisher.Message> {

    public PlayerServerConnectPublisher() {
        super("player_server_connect", Message.class);
    }

    public void publish(OMPlayer player, Server server, boolean notify) {
        publish(new Message(
            player.getUniqueId().toString(),
            server.toString(),
            notify
        ));
    }

    @AllArgsConstructor
    public class Message {

        String uuid;
        String server_name;
        boolean notify;

    }
}
