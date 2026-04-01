package com.orbitmines.archive.minecraft.spigot._2019.servers.prison.events;

/*
    Created By Robin Egberts On 3/7/2019
    Copyrighted By OrbitMines ©2019
*/

import com.orbitmines.archive.minecraft.spigot._2019.servers.prison.Prison;
import com.orbitmines.archive.minecraft.spigot._2019.servers.prison.PrisonPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.servers.prison.items.PrisonItem;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;

public class InteractionEvent implements Listener {

    private final Prison server;

    public InteractionEvent(Prison server) {
        this.server = server;
    }

    @EventHandler
    public void onInteract(InventoryClickEvent event) {
        PrisonPlayer p = server.getPlayer((Player) event.getWhoClicked());

        int slot = event.getSlot();
        InventoryType.SlotType slotType = event.getSlotType();
        ClickType click = event.getClick();

        PrisonItem item = p.getPrisonInventory().getItem(slotType, slot);

        if (item == null)
            return;

        if (click != ClickType.SHIFT_RIGHT && click != ClickType.RIGHT)
            return;

        if (item.isBackpack()) {
            p.openInventory(item.getAsBackpack());
            event.setCancelled(true);
        }
    }
}
