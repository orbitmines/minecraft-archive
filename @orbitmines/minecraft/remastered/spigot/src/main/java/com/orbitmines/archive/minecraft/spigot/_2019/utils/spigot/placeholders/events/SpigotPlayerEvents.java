package com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.placeholders.events;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.placeholders.SpigotPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.placeholders.SpigotServer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class SpigotPlayerEvents implements Listener {

    private final SpigotServer server;

    public SpigotPlayerEvents(SpigotServer server) {
        this.server = server;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onJoin(PlayerJoinEvent event) {
        server.triggerJoinEvent(event.getPlayer());
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onQuit(PlayerQuitEvent event) {
        SpigotPlayer player = server.getPlayer(event.getPlayer());
        player.processQuitEventSync();
    }
}
