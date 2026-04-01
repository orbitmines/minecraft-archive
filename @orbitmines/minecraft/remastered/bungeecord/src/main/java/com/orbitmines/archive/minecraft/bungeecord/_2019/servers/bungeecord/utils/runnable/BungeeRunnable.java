package com.orbitmines.archive.minecraft.bungeecord._2019.servers.bungeecord.utils.runnable;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import lombok.Getter;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.scheduler.ScheduledTask;
import net.md_5.bungee.api.scheduler.TaskScheduler;

import java.util.concurrent.TimeUnit;

public abstract class BungeeRunnable implements Runnable {

    protected final Plugin plugin;
    @Getter protected Interval interval;
    @Getter protected ScheduledTask task;
    @Getter protected boolean async;

    public BungeeRunnable(Plugin plugin, Interval interval) {
        this.plugin = plugin;
        this.interval = interval;
    }

    public abstract void run();

    public BungeeRunnable async() {
        this.async = true;

        return this;
    }

    public BungeeRunnable start() {
        return start(Interval.ZERO);
    }

    public BungeeRunnable start(Interval delay) {
        TaskScheduler scheduler = plugin.getProxy().getScheduler();

        if (async)
            this.task = scheduler.schedule(plugin, () -> scheduler.runAsync(plugin, this), delay.toMillis(), this.interval.toMillis(), TimeUnit.MILLISECONDS);
        else
            this.task = scheduler.schedule(plugin, this, delay.toMillis(), this.interval.toMillis(), TimeUnit.MILLISECONDS);

        return this;
    }

    public void cancel() {
        if (this.task == null)
            return;

        this.task.cancel();
    }

    public boolean isRunning() {
        return this.task != null && this.task.getTask() != null;
    }
}
