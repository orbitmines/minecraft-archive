package com.orbitmines.archive.minecraft.spigot._2019.servers.prison.events;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft.spigot._2019.servers.prison.Prison;
import com.orbitmines.archive.minecraft.spigot._2019.servers.prison.PrisonInventory;
import com.orbitmines.archive.minecraft.spigot._2019.servers.prison.PrisonPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.servers.prison.items.PrisonItem;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BreakBlockEvent implements Listener {

    private final Prison server;

    public BreakBlockEvent(Prison server) {
        this.server = server;
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        PrisonPlayer player = server.getPlayer(event.getPlayer());
        PrisonInventory inventory = player.getPrisonInventory();

        PrisonItem item = inventory.getHeldItem();

        if (item.shouldTrackBlocks())
            item.addTrackerCount(1);
    }
}
