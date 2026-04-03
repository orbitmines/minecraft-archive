package com.orbitmines.archive.minecraft.bungeecord._2019.servers.bungeecord.libs.pubsub.subscribers;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.Server;
import com.orbitmines.archive.minecraft.bungeecord._2019.servers.bungeecord.libs.Bungeecord;
import com.orbitmines.archive.minecraft.bungeecord._2019.servers.bungeecord.libs.error_tracker.ErrorTracker;
import com.orbitmines.archive.minecraft.bungeecord._2019.servers.bungeecord.utils.pubsub.BungeeSubscriber;
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

        /* Reset error tracker cursor so it reads the new log from the start */
        ErrorTracker errorTracker = plugin.getErrorTracker();
        if (errorTracker != null) {
            Server server = Server.getFromPluginName(object.server_name);
            if (server != null)
                errorTracker.resetCursor(server);
        }
    }

    @AllArgsConstructor
    public class Message {

        String server_name;
        String ip;
        int port;
        int priority;

    }
}
