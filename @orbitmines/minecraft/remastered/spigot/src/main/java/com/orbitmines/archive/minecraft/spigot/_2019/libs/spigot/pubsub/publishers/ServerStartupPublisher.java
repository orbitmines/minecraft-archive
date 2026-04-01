package com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.pubsub.publishers;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.jedis.SpigotPublisher;
import lombok.AllArgsConstructor;

public class ServerStartupPublisher extends SpigotPublisher.Simple<ServerStartupPublisher.Message> {

    public ServerStartupPublisher() {
        super("server_startup", Message.class);
    }

    public void publish(String serverName, String ip, int port, int priority) {
        publish(new Message(
            serverName,
            ip,
            port,
            priority
        ));
    }

    @AllArgsConstructor
    public class Message {

        String server_name;
        String ip;
        int port;
        int priority;

    }
}
