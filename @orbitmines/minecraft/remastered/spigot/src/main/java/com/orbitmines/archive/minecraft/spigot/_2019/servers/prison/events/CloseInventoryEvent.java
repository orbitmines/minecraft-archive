package com.orbitmines.archive.minecraft.spigot._2019.servers.prison.events;

/*
    Created By Robin Egberts On 3/7/2019
    Copyrighted By OrbitMines ©2019
*/

import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.ConsoleUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

public class CloseInventoryEvent implements Listener {

    //TODO: TEST!

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        Inventory inventory = event.getInventory();

//        if (inventory.getLocation() == null) //TODO: ADD NAME STUFF
//            event.getPlayer().openInventory(event.getPlayer().getInventory());

//        ConsoleUtils.msg(inventory.getType().toString());
    }
}
