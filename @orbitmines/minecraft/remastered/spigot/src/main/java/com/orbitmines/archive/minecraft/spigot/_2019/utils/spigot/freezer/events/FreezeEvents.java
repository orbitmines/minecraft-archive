package com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.freezer.events;

import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.freezer.Freezer;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.placeholders.SpigotPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.placeholders.SpigotServer;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

/*
* OrbitMines - @author Fadi Shawki - 29-7-2017
*/
public class FreezeEvents implements Listener {

    private final SpigotServer server;

    public FreezeEvents(SpigotServer server) {
        this.server = server;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onMove(PlayerMoveEvent event) {
        SpigotPlayer player = server.getPlayer(event.getPlayer());

        if (!player.isFrozen())
            return;

        switch (player.getFreezer()) {

            case MOVE:
            case MOVE_AND_JUMP:
                Location to = event.getTo();
                Location newTo = event.getFrom();

                newTo.setYaw(to.getYaw());
                newTo.setPitch(to.getPitch());

                if (player.getFreezer() != Freezer.MOVE_AND_JUMP)
                    newTo.setY(to.getY());

                event.setTo(newTo);
                break;
            case MOVE_AND_LOOK_AROUND:
                event.setTo(event.getFrom());
                break;
        }
    }
}
