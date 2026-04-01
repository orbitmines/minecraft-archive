package com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.runnable;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class SpigotTimer extends SpigotRunnable {

    private static Interval ONE_TICK_INTERVAL = Interval.of(TimeUnit.TICK, 1);

    @Getter private final Interval length;
    @Getter private final Interval timerInterval;

    @Getter private long remainingTicks;
    private long intervalTicks;

    public SpigotTimer(JavaPlugin plugin, Interval length, Interval timerInterval) {
        super(plugin, ONE_TICK_INTERVAL);

        this.length = length;
        this.timerInterval = timerInterval;
    }

    public abstract void onFinish();

    public abstract void onInterval();

    /* Between 0 and 1 */
    public float getProgress() {
        return (float) remainingTicks / (float) this.length.toTicks();
    }

    public float getReverseProgress() {
        long length = this.length.toTicks();

        return ((float) length - (float) remainingTicks) / (float) length;
    }

    public void finish() {
        this.remainingTicks = 0;
        onFinish();
        cancel();
    }

    @Override
    public SpigotRunnable start(Interval delay) {
        this.remainingTicks = this.length.toTicks();
        this.intervalTicks = 0;

        return super.start(delay);
    }

    @Override
    public void run() {
        this.remainingTicks--;

        if (this.remainingTicks == 0) {
            finish();
            return;
        }

        this.intervalTicks++;

        if (this.timerInterval.toTicks() == this.intervalTicks) {
            onInterval();

            this.intervalTicks = 0;
        }
    }
}
