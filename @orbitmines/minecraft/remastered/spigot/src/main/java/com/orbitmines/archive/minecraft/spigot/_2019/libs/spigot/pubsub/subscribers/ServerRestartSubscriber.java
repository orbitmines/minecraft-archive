package com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.pubsub.subscribers;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMServer;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.jedis.SpigotSubscriber;
import lombok.AllArgsConstructor;

public class ServerRestartSubscriber extends SpigotSubscriber.Simple<ServerRestartSubscriber.Message> {

    private final OMServer plugin;

    public ServerRestartSubscriber(OMServer plugin) {
        super("server_restart", Message.class);

        this.plugin = plugin;
    }

    @Override
    protected void onMessage(Message object) {
        plugin.restart(object.message + " at " + object.date, false);
    }

    @AllArgsConstructor
    public class Message {

        String message;
        String date;

    }
}
