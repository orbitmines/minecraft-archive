package com.orbitmines.archive.minecraft.spigot._2019.servers.survival.runnables;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.Survival;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.runnable.Interval;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.runnable.PassiveRunnable;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.runnable.TimeUnit;

public class WorldSaveRunnable extends PassiveRunnable<Survival> {

    private Survival survival;

    public WorldSaveRunnable(Survival plugin) {
        super(plugin, Interval.of(TimeUnit.HOUR, 1));

        this.survival = plugin;
    }

    @Override
    public void onRun() {
        survival.broadcast("World", Color.INFO, "survival", "player.save_worlds");

        survival.getWorld().save();
        survival.getWorldNether().save();
        survival.getWorldTheEnd().save();

        survival.broadcast("World", Color.INFO, "survival", "player.save_world.done");
    }
}
