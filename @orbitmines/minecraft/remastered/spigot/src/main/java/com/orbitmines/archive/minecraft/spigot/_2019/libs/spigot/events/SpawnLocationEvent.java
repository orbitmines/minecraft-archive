package com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.events;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.spigotmc.event.player.PlayerSpawnLocationEvent;

public abstract class SpawnLocationEvent implements Listener {

    public abstract Location getSpawnLocation(Player player);

    @EventHandler
    public void onSpawn(PlayerSpawnLocationEvent event) {
        Player player = event.getPlayer();

        Location location = getSpawnLocation(player);

        if (location == null)
            return;

        event.setSpawnLocation(location);
    }
}
