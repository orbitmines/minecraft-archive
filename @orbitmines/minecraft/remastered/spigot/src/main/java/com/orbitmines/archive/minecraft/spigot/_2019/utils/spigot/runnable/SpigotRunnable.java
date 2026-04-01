package com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.runnable;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.placeholders.SpigotServer;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

public abstract class SpigotRunnable<S extends SpigotServer> implements Runnable {

    protected S server;
    @Getter protected Interval interval;
    @Getter protected BukkitTask task;
    @Getter protected boolean async;

    public SpigotRunnable(S server, Interval interval) {
        this.server = server;
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
        BukkitScheduler scheduler = Bukkit.getScheduler();

        if (async)
            this.task = scheduler.runTaskTimerAsynchronously(server.getPlugin(), this, delay.toTicks(), this.interval.toTicks());
        else
            this.task = scheduler.runTaskTimer(server.getPlugin(), this, delay.toTicks(), this.interval.toTicks());

        return this;
    }

    public void runSync(Runnable runnable) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(server.getPlugin(), runnable);
    }

    public void runSync(Runnable runnable, long delay) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(server.getPlugin(), runnable, delay);
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
