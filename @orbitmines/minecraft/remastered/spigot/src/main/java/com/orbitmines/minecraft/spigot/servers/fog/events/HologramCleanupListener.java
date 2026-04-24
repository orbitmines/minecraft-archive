package com.orbitmines.minecraft.spigot.servers.fog.events;

import com.orbitmines.minecraft.spigot.servers.fog.FoG;
import com.orbitmines.minecraft.spigot.servers.fog.util.HologramTag;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldLoadEvent;

/**
 * Removes leftover FoG holograms (tagged armor stands + floating items) whenever a
 * world is loaded. This covers world-reloads mid-session and any run-worlds that
 * come back with stale holograms saved in their level.dat from a prior session.
 */
public class HologramCleanupListener implements Listener {

    private final FoG server;

    public HologramCleanupListener(FoG server) {
        this.server = server;
    }

    @EventHandler
    public void onWorldLoad(WorldLoadEvent event) {
        int removed = HologramTag.cleanupWorld(event.getWorld());
        if (removed > 0) {
            Bukkit.getLogger().info("[fog] Removed " + removed + " stale hologram entities in " + event.getWorld().getName());
        }
    }
}
