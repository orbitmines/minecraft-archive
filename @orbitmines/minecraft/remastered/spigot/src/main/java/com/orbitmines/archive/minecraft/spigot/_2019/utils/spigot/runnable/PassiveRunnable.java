package com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.runnable;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.placeholders.SpigotServer;
import org.bukkit.Bukkit;

public abstract class PassiveRunnable<S extends SpigotServer> extends SpigotRunnable<S> {

    public PassiveRunnable(S server, Interval interval) {
        super(server, interval);
    }

    public abstract void onRun();

    @Override
    public void run() {
        if (Bukkit.getOnlinePlayers().size() == 0)
            return;

        onRun();
    }
}
