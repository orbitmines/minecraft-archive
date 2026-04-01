package com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.pubsub.publishers;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.pubsub.SpigotPublisher;
import lombok.AllArgsConstructor;

public class ServerClosePublisher extends SpigotPublisher.Simple<ServerClosePublisher.Message> {

    public ServerClosePublisher() {
        super("server_close", Message.class);
    }

    public void publish(String serverName) {
        publish(new Message(
            serverName
        ));
    }

    @AllArgsConstructor
    public class Message {

        String server_name;

    }
}
