package com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.pubsub.subscribers;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMServer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.guis.LootGUI;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.jedis.SpigotSubscriber;
import lombok.AllArgsConstructor;

import java.util.UUID;

public class PlayerDonationSubscriber extends SpigotSubscriber.Simple<PlayerDonationSubscriber.Message> {

    private final OMServer<?, ? extends OMPlayer> plugin;

    public PlayerDonationSubscriber(OMServer<?, ? extends OMPlayer> plugin) {
        super("player_donation", Message.class);

        this.plugin = plugin;
    }

    @Override
    protected void onMessage(Message object) {
        OMPlayer player = plugin.getPlayer(UUID.fromString(object.uuid));

        if (player == null)
            return;

        player.loadLootItems();

        if (player.getLastGUI() instanceof LootGUI)
            player.getLastGUI().update();
    }

    @AllArgsConstructor
    public class Message {

        String uuid;
        Long id;

    }
}
