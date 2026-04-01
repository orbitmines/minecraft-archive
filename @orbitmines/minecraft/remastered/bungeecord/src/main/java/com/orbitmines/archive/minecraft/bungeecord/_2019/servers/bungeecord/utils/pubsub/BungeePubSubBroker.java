package com.orbitmines.archive.minecraft.bungeecord._2019.servers.bungeecord.utils.pubsub;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.utils.pubsub.PubSubBroker;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;

import java.io.*;

public class BungeePubSubBroker extends PubSubBroker implements Listener {

    public static final String CHANNEL = "orbitmines:pubsub";

    private final Plugin plugin;

    public BungeePubSubBroker(Plugin plugin) {
        this.plugin = plugin;

        plugin.getProxy().registerChannel(CHANNEL);
        plugin.getProxy().getPluginManager().registerListener(plugin, this);
    }

    @Override
    public void send(String channel, String jsonMessage) {
        try {
            byte[] data = encode(channel, jsonMessage);

            /* Send to all connected servers */
            for (ServerInfo server : plugin.getProxy().getServers().values()) {
                if (server.getPlayers().isEmpty())
                    continue;

                server.sendData(CHANNEL, data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendToServer(String serverName, String channel, String jsonMessage) {
        ServerInfo server = plugin.getProxy().getServerInfo(serverName);

        if (server == null || server.getPlayers().isEmpty())
            return;

        try {
            server.sendData(CHANNEL, encode(channel, jsonMessage));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @EventHandler
    public void onPluginMessage(PluginMessageEvent event) {
        if (!CHANNEL.equals(event.getTag()))
            return;

        /* Only handle messages from servers (not from clients) */
        if (!(event.getSender() instanceof net.md_5.bungee.api.connection.Server))
            return;

        event.setCancelled(true);

        try {
            DataInputStream in = new DataInputStream(new ByteArrayInputStream(event.getData()));

            String subchannel = in.readUTF();
            String jsonMessage = in.readUTF();

            /* Dispatch on async thread */
            plugin.getProxy().getScheduler().runAsync(plugin, () -> dispatch(subchannel, jsonMessage));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private byte[] encode(String channel, String jsonMessage) throws IOException {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(stream);

        out.writeUTF(channel);
        out.writeUTF(jsonMessage);

        return stream.toByteArray();
    }

    public void unregister() {
        plugin.getProxy().unregisterChannel(CHANNEL);
    }
}
