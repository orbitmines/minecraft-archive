package com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.pubsub.subscribers;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMServer;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.jedis.SpigotSubscriber;
import lombok.AllArgsConstructor;

public class BungeeStartupSubscriber extends SpigotSubscriber.Simple<BungeeStartupSubscriber.Message> {

    private final OMServer plugin;

    public BungeeStartupSubscriber(OMServer plugin) {
        super("bungee_startup", Message.class);

        this.plugin = plugin;
    }

    @Override
    protected void onMessage(Message object) {
        /* Notify Bungeecord we are alive */
        plugin.publishServerStartup();
    }

    @AllArgsConstructor
    public class Message {

        String ip;
        int port;

    }
}
