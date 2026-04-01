package com.orbitmines.archive.minecraft.bungeecord._2019.servers.bungeecord.libs.pubsub.publishers;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft.bungeecord._2019.servers.bungeecord.utils.pubsub.BungeePublisher;
import lombok.AllArgsConstructor;

public class BungeeStartupPublisher extends BungeePublisher.Simple<BungeeStartupPublisher.Message> {

    public BungeeStartupPublisher() {
        super("bungee_startup", Message.class);
    }

    public void publish(String ip, int port) {
        publish(new Message(
            ip,
            port
        ));
    }

    @AllArgsConstructor
    public class Message {

        String ip;
        int port;

    }
}
