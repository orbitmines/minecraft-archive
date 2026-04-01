package com.orbitmines.archive.minecraft.bungeecord._2019.servers.bungeecord.libs.pubsub.subscribers;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft.bungeecord._2019.servers.bungeecord.libs.Bungeecord;
import com.orbitmines.archive.minecraft.bungeecord._2019.servers.bungeecord.utils.pubsub.BungeeSubscriber;
import lombok.AllArgsConstructor;

public class ServerCloseSubscriber extends BungeeSubscriber.Simple<ServerCloseSubscriber.Message> {

    private final Bungeecord plugin;

    public ServerCloseSubscriber(Bungeecord plugin) {
        super("server_close", Message.class);

        this.plugin = plugin;
    }

    @Override
    protected void onMessage(Message object) {
        plugin.getProxy().getServers().remove(object.server_name);
    }

    @AllArgsConstructor
    public class Message {

        String server_name;

    }
}
