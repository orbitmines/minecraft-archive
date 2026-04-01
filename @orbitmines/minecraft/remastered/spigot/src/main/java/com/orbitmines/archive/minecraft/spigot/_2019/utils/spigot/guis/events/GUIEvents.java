package com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.guis.events;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.guis.GUI;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.placeholders.SpigotPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.placeholders.SpigotServer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;

public class GUIEvents<S extends SpigotServer<P>, P extends SpigotPlayer<S>> implements Listener {

    private final S server;

    public GUIEvents(S server) {
        this.server = server;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onClick(InventoryClickEvent event) {
        try {
            if (event.getView().getTitle().startsWith("§0§l")) {
                /* TODO: Temp Fix */
                event.setCancelled(true);
            }
        } catch (Exception ex) {}

        P player = server.getPlayer((Player) event.getWhoClicked());

        GUI lastGui = player.getLastGUI();
        if (lastGui == null)
            return;

        lastGui.processClickEvent(event);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onDrag(InventoryDragEvent event) {
        try {
            if (event.getView().getTitle().startsWith("§0§l")) {
                /* TODO: Temp Fix */
                event.setCancelled(true);
            }
        } catch (Exception ex) {}

        P player = server.getPlayer((Player) event.getWhoClicked());

        GUI lastGui = player.getLastGUI();
        if (lastGui == null)
            return;

        lastGui.processDragEvent(event);
    }
}
