package com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.events;

import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMServer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

/*
* OrbitMines - @author Fadi Shawki - 2017
*/
public class AfkEvents<S extends OMServer<S, P>, P extends OMPlayer<S, P>> implements Listener {

    private S server;

    public AfkEvents(S server) {
        this.server = server;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInteract(PlayerInteractEvent event) {
        P omp = server.getPlayer(event.getPlayer());

        if (omp.isAfk())
            omp.noLongerAfk();
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onMove(PlayerMoveEvent event) {
        P omp = server.getPlayer(event.getPlayer());

        if (omp.isAfk() && omp.isMoving(event))
            omp.noLongerAfk();
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        P omp = server.getPlayer(event.getPlayer());

        if (omp.isAfk())
            omp.noLongerAfk();
    }
}
