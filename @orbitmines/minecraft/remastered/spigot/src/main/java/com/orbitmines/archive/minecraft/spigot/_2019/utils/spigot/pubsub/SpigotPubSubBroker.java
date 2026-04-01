package com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.pubsub;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.utils.pubsub.PubSubBroker;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.io.*;

public class SpigotPubSubBroker extends PubSubBroker implements PluginMessageListener {

    public static final String CHANNEL = "orbitmines:pubsub";

    private final JavaPlugin plugin;

    public SpigotPubSubBroker(JavaPlugin plugin) {
        this.plugin = plugin;

        Bukkit.getMessenger().registerOutgoingPluginChannel(plugin, CHANNEL);
        Bukkit.getMessenger().registerIncomingPluginChannel(plugin, CHANNEL, this);
    }

    @Override
    public void send(String channel, String jsonMessage) {
        Player player = getAnyOnlinePlayer();

        if (player == null) {
            System.out.println("[PubSub] No online players to send plugin message on channel '" + channel + "'");
            return;
        }

        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            DataOutputStream out = new DataOutputStream(stream);

            out.writeUTF(channel);
            out.writeUTF(jsonMessage);

            player.sendPluginMessage(plugin, CHANNEL, stream.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] data) {
        if (!CHANNEL.equals(channel))
            return;

        try {
            DataInputStream in = new DataInputStream(new ByteArrayInputStream(data));

            String subchannel = in.readUTF();
            String jsonMessage = in.readUTF();

            /* Dispatch on async thread to avoid blocking the main thread */
            plugin.getServer().getScheduler().runTaskAsynchronously(plugin, () -> dispatch(subchannel, jsonMessage));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Player getAnyOnlinePlayer() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            return player;
        }
        return null;
    }

    public void unregister() {
        Bukkit.getMessenger().unregisterOutgoingPluginChannel(plugin, CHANNEL);
        Bukkit.getMessenger().unregisterIncomingPluginChannel(plugin, CHANNEL, this);
    }
}
