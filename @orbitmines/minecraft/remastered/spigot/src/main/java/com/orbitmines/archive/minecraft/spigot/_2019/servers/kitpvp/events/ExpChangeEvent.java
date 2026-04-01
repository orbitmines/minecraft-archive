package com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.events;

/*
 * OrbitMines - @author Fadi Shawki - 2018
 */

import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.database.models.kitpvp.KitPvPPlayerModel;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.KitPvP;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.KitPvPPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class ExpChangeEvent implements Listener {

    private final KitPvP server;

    public ExpChangeEvent(KitPvP server) {
        this.server = server;
    }

    @EventHandler
    public void onExpChange(PlayerExpChangeEvent event) {
        KitPvPPlayer player = server.getPlayer(event.getPlayer());
        KitPvPPlayerModel.LevelData levelData = player.getLevelData();

        new BukkitRunnable() {
            @Override
            public void run() {
                levelData.updateExperienceBar(player.bukkit());
            }
        }.runTaskLater(server, 1);
    }
}
