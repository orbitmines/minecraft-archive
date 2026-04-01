package com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.runnable;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

public abstract class SpigotRunnable<Plugin extends JavaPlugin> implements Runnable {

    protected Plugin plugin;
    @Getter protected Interval interval;
    @Getter protected BukkitTask task;
    @Getter protected boolean async;

    public SpigotRunnable(Plugin plugin, Interval interval) {
        this.plugin = plugin;
        this.interval = interval;
    }

    public abstract void run();

    // Beware, you CANNOT access any Bukkit API methods while running tasks async
    // -> use #runSync instead for those operations to run in the Main Thread
    public SpigotRunnable async() {
        this.async = true;

        return this;
    }

    public SpigotRunnable start() {
        return start(Interval.ZERO);
    }

    public SpigotRunnable start(Interval delay) {
        BukkitScheduler scheduler = plugin.getServer().getScheduler();

        if (async)
            this.task = scheduler.runTaskTimerAsynchronously(plugin, this, delay.toTicks(), this.interval.toTicks());
        else
            this.task = scheduler.runTaskTimer(plugin, this, delay.toTicks(), this.interval.toTicks());

        return this;
    }

    public void runSync(Runnable runnable) {
        plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, runnable);
    }

    public void runSync(Runnable runnable, long delay) {
        plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, runnable, delay);
    }

    public void cancel() {
        if (this.task == null)
            return;

        this.task.cancel();
    }

    public boolean isRunning() {
        return this.task != null && !this.task.isCancelled();
    }
}
