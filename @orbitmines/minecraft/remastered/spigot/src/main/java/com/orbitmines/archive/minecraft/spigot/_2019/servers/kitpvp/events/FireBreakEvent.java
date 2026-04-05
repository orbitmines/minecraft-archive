package com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.events;

import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.KitPvP;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class FireBreakEvent implements Listener {

    private final KitPvP server;

    public FireBreakEvent(KitPvP server) {
        this.server = server;
    }

    /* Override prevention for fire blocks — allow players to extinguish fire */
    @EventHandler(priority = EventPriority.HIGH)
    public void onFireBreak(BlockBreakEvent event) {
        if (event.getBlock().getType() == Material.FIRE) {
            event.setCancelled(false);
        }
    }

    /* Allow interacting with fire blocks (punching fire) */
    @EventHandler(priority = EventPriority.HIGH)
    public void onFireInteract(PlayerInteractEvent event) {
        if (event.getClickedBlock() != null && event.getClickedBlock().getType() == Material.FIRE) {
            event.setCancelled(false);
        }
    }
}
