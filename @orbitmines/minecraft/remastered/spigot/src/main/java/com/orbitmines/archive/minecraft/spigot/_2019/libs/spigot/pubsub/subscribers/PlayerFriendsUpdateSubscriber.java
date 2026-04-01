package com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.pubsub.subscribers;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMServer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.guis.friends.FriendGUIInstance;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.jedis.SpigotSubscriber;
import lombok.AllArgsConstructor;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class PlayerFriendsUpdateSubscriber extends SpigotSubscriber.Simple<PlayerFriendsUpdateSubscriber.Message> {

    private final OMServer<?, ? extends OMPlayer> plugin;

    public PlayerFriendsUpdateSubscriber(OMServer<?, ? extends OMPlayer> plugin) {
        super("player_friends_update", Message.class);

        this.plugin = plugin;
    }

    @Override
    protected void onMessage(Message object) {
        OMPlayer player = plugin.getPlayer(UUID.fromString(object.uuid));

        if (player == null)
            return;

        new BukkitRunnable() {
            @Override
            public void run() {
                update(player);
            }
        }.runTaskLaterAsynchronously(plugin, 5);
    }

    private void update(OMPlayer player) {
        player.loadFriends();
        player.loadIncomingFriendInvites();
        player.loadOutgoingFriendInvites();

        if (player.getLastGUI() instanceof FriendGUIInstance && player.hasOpened(player.getLastGUI()))
            player.getLastGUI().update();
    }

    @AllArgsConstructor
    public class Message {

        String uuid;

    }
}
