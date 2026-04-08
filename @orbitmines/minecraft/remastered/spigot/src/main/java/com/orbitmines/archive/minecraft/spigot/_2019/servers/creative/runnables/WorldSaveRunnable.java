package com.orbitmines.archive.minecraft.spigot._2019.servers.creative.runnables;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft.spigot._2019.servers.creative.Creative;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.runnable.Interval;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.runnable.SpigotRunnable;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.runnable.TimeUnit;

public class WorldSaveRunnable extends SpigotRunnable<Creative> {

    private Creative creative;

    public WorldSaveRunnable(Creative plugin) {
        super(plugin, Interval.of(TimeUnit.HOUR, 1));

        this.creative = plugin;
    }

    @Override
    public void run() {
        creative.broadcast("World", Color.INFO, "creative", "player.save_worlds");

        creative.saveAllWorlds();

        creative.broadcast("World", Color.INFO, "creative", "player.save_world.done");
    }
}
