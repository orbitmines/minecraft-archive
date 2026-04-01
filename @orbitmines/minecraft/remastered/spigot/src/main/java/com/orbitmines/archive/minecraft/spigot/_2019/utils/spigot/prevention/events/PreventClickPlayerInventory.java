package com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.prevention.events;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.prevention.PreventionEvent;
import org.bukkit.World;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.PlayerInventory;

public class PreventClickPlayerInventory extends PreventionEvent<InventoryClickEvent> {

    public PreventClickPlayerInventory(World world) {
        super(world);
    }

    @Override
    protected boolean shouldBeCancelled(InventoryClickEvent event, World world) {
        return event.getClickedInventory() != null && event.getClickedInventory() instanceof PlayerInventory;
    }

    @Override
    protected World getWorld(InventoryClickEvent event) {
        return event.getWhoClicked().getWorld();
    }

    @Override
    public Class<InventoryClickEvent> getEventClass() {
        return InventoryClickEvent.class;
    }
}
