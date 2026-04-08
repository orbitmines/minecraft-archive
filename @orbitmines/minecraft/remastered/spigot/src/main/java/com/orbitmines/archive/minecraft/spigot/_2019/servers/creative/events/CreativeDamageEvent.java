package com.orbitmines.archive.minecraft.spigot._2019.servers.creative.events;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft.spigot._2019.servers.creative.Creative;
import com.orbitmines.archive.minecraft.spigot._2019.servers.creative.CreativePlayer;
import com.orbitmines.archive.minecraft.spigot._2019.servers.creative.CreativeWorld;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class CreativeDamageEvent implements Listener {

    private final Creative server;

    public CreativeDamageEvent(Creative server) {
        this.server = server;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player player))
            return;

        if (event.getCause() == EntityDamageEvent.DamageCause.VOID) {
            /* Void teleport — own world: world spawn, otherwise: lobby spawn */
            event.setCancelled(true);
            player.setFallDistance(0F);

            CreativeWorld world = server.getWorldByBukkitWorld(player.getWorld());

            if (world != null && world.isLoaded()) {
                player.teleport(world.getWorld().getSpawnLocation());
            } else {
                player.teleport(server.getLobbySpawn());
            }
            return;
        }

        /* Prevent all other death — cancel any lethal damage */
        event.setCancelled(true);
    }
}
