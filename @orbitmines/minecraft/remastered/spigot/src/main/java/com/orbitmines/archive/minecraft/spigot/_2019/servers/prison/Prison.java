package com.orbitmines.archive.minecraft.spigot._2019.servers.prison;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.Server;
import com.orbitmines.archive.minecraft._2019.libs.database.models.loot.LootItem;
import com.orbitmines.archive.minecraft._2019.libs.player.PlayerInstance;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.ChatHandler;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMServer;
import com.orbitmines.archive.minecraft.spigot._2019.servers.prison.events.BreakBlockEvent;
import com.orbitmines.archive.minecraft.spigot._2019.servers.prison.events.CloseInventoryEvent;
import com.orbitmines.archive.minecraft.spigot._2019.servers.prison.events.InteractionEvent;
import com.orbitmines.archive.minecraft.spigot._2019.servers.prison.events.ItemPickUpEvent;
import org.bukkit.entity.Player;

public class Prison extends OMServer<PrisonPlayer> {

    @Override
    public PrisonPlayer newPlayerInstance(Player player) {
        return new PrisonPlayer(player, this);
    }

    @Override
    public void onStartup() {
        super.onStartup();


    }

    @Override
    public void onStart() {
        super.onStart();

        registerEvents(
                new BreakBlockEvent(this),
                new CloseInventoryEvent(),
                new InteractionEvent(this),
                new ItemPickUpEvent(this)
        );
    }

    @Override
    public void onStop() {


        super.onStop();
    }

    @Override
    public Server getType() {
        return Server.PRISON;
    }

    @Override
    public ChatHandler newChatHandler(PlayerInstance sender, String message) {
        return new ChatHandler<>(this, sender, message);
    }

    @Override
    public void giveLoot(PrisonPlayer player, LootItem... items) {

    }
}
