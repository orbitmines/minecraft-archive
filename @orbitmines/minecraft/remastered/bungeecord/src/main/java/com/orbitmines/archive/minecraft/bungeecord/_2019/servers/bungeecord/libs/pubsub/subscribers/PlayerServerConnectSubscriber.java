package com.orbitmines.archive.minecraft.bungeecord._2019.servers.bungeecord.libs.pubsub.subscribers;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.Server;
import com.orbitmines.archive.minecraft.bungeecord._2019.servers.bungeecord.libs.BungeePlayer;
import com.orbitmines.archive.minecraft.bungeecord._2019.servers.bungeecord.libs.Bungeecord;
import com.orbitmines.archive.minecraft.bungeecord._2019.servers.bungeecord.utils.jedis.BungeeSubscriber;
import lombok.AllArgsConstructor;

import java.util.UUID;

public class PlayerServerConnectSubscriber extends BungeeSubscriber.Simple<PlayerServerConnectSubscriber.Message> {

    private final Bungeecord plugin;

    public PlayerServerConnectSubscriber(Bungeecord plugin) {
        super("player_server_connect", Message.class);

        this.plugin = plugin;
    }

    @Override
    protected void onMessage(Message object) {
        UUID uuid = UUID.fromString(object.uuid);

        BungeePlayer player = plugin.getPlayerIfOnline(uuid);
        if (player == null)
            return;

        if (object.server_name == null) {
            player.fallback(object.notify);
            return;
        }

        Server server = Server.valueOf(object.server_name);

        player.connect(server, object.notify);
    }

    @AllArgsConstructor
    public class Message {

        String uuid;
        String server_name;
        boolean notify;

    }
}
