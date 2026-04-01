package com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.events;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.server.ServerFinishedStartupEvent;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMServer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class FinishedStartupEvent implements Listener {

    private final OMServer server;

    public FinishedStartupEvent(OMServer server) {
        this.server = server;
    }

    @EventHandler
    public void onStartup(ServerFinishedStartupEvent event) {
        server.runAsync(server::onStartupFinish);
    }
}
