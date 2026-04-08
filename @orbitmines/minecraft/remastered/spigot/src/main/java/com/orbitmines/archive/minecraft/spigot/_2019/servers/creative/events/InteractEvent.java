package com.orbitmines.archive.minecraft.spigot._2019.servers.creative.events;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft.spigot._2019.servers.creative.Creative;
import com.orbitmines.archive.minecraft.spigot._2019.servers.creative.CreativePlayer;
import com.orbitmines.archive.minecraft.spigot._2019.servers.creative.gui.WorldCreateGUI;
import com.orbitmines.archive.minecraft.spigot._2019.servers.creative.gui.WorldListGUI;
import com.orbitmines.archive.minecraft.spigot._2019.servers.creative.kit.CreativeLobbyKit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class InteractEvent implements Listener {

    private final Creative server;

    public InteractEvent(Creative server) {
        this.server = server;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player))
            return;

        /* Only handle clicks in the player's own inventory */
        if (!(event.getClickedInventory() instanceof PlayerInventory))
            return;

        int slot = event.getSlot();
        if (slot != CreativeLobbyKit.SLOT_WORLD_MANAGER && slot != CreativeLobbyKit.SLOT_CREATE_WORLD)
            return;

        ItemStack clicked = event.getCurrentItem();
        if (clicked == null || clicked.getType().isAir())
            return;

        CreativePlayer cp = server.getPlayer(player);
        if (cp == null)
            return;

        event.setCancelled(true);

        if (slot == CreativeLobbyKit.SLOT_WORLD_MANAGER) {
            new WorldListGUI(server, cp).open();
        } else if (slot == CreativeLobbyKit.SLOT_CREATE_WORLD) {
            new WorldCreateGUI(server, cp).open();
        }
    }
}
