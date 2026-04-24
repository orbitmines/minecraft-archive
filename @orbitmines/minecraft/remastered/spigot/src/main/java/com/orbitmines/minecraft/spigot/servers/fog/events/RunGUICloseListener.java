package com.orbitmines.minecraft.spigot.servers.fog.events;

import com.orbitmines.minecraft.spigot.servers.fog.FoG;
import com.orbitmines.minecraft.spigot.servers.fog.FoGPlayer;
import com.orbitmines.minecraft.spigot.servers.fog.gui.CoopJoinGUI;
import com.orbitmines.minecraft.spigot.servers.fog.gui.SpectatorJoinGUI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

/**
 * Re-opens the top-level RunSelector when a player closes a run-related GUI
 * (coop / spectate) without actually committing to their action — cancelling
 * drops them back at the top-level choice rather than leaving them stranded.
 */
public class RunGUICloseListener implements Listener {

    private final FoG server;

    public RunGUICloseListener(FoG server) {
        this.server = server;
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        if (!(event.getPlayer() instanceof Player bukkit)) return;
        FoGPlayer player = server.getPlayer(bukkit);
        if (player == null) return;
        CoopJoinGUI.onInventoryClose(server, player, event.getInventory());
        SpectatorJoinGUI.onInventoryClose(server, player, event.getInventory());
    }
}
