package com.orbitmines.archive.minecraft.bungeecord._2019.servers.bungeecord.libs.pubsub.subscribers;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft.bungeecord._2019.servers.bungeecord.libs.Bungeecord;
import com.orbitmines.archive.minecraft.bungeecord._2019.servers.bungeecord.utils.jedis.BungeeSubscriber;
import lombok.AllArgsConstructor;
import net.md_5.bungee.api.config.ServerInfo;

import java.net.InetSocketAddress;

public class ServerStartupSubscriber extends BungeeSubscriber.Simple<ServerStartupSubscriber.Message> {

    private final Bungeecord plugin;

    public ServerStartupSubscriber(Bungeecord plugin) {
        super("server_startup", Message.class);

        this.plugin = plugin;
    }

    @Override
    protected void onMessage(Message object) {
        InetSocketAddress address = new InetSocketAddress(object.ip, object.port);

        ServerInfo info = plugin.getProxy().constructServerInfo(object.server_name, address, object.priority + "", false);
        plugin.getProxy().getServers().put(object.server_name, info);
    }

    @AllArgsConstructor
    public class Message {

        String server_name;
        String ip;
        int port;
        int priority;

    }
}
