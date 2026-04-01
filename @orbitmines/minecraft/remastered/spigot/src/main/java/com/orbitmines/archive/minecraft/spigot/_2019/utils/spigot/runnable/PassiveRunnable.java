package com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.runnable;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import org.bukkit.plugin.java.JavaPlugin;

public abstract class PassiveRunnable<Plugin extends JavaPlugin> extends SpigotRunnable {

    public PassiveRunnable(Plugin plugin, Interval interval) {
        super(plugin, interval);
    }

    public abstract void onRun();

    @Override
    public void run() {
        if (plugin.getServer().getOnlinePlayers().size() == 0)
            return;

        onRun();
    }
}
