package com.orbitmines.archive.minecraft.bungeecord._2019.servers.bungeecord.utils.runnable;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import lombok.Getter;
import net.md_5.bungee.api.plugin.Plugin;

public abstract class BungeeTimer extends BungeeRunnable {

    private static Interval ONE_SECOND_INTERVAL = Interval.of(TimeUnit.SECOND, 1);

    @Getter private final Interval length;
    @Getter private final Interval timerInterval;

    @Getter private long remainingSeconds;
    private long intervalSeconds;

    public BungeeTimer(Plugin plugin, Interval length, Interval timerInterval) {
        super(plugin, ONE_SECOND_INTERVAL);

        this.length = length;
        this.timerInterval = timerInterval;
    }

    public abstract void onFinish();

    public abstract void onInterval();

    /* Between 0 and 1 */
    public float getProgress() {
        return (float) remainingSeconds / (float) this.length.toSeconds();
    }

    public float getReverseProgress() {
        long length = this.length.toSeconds();

        return ((float) length - (float) remainingSeconds) / (float) length;
    }

    public void finish() {
        this.remainingSeconds = 0;
        onFinish();
        cancel();
    }

    @Override
    public BungeeRunnable start(Interval delay) {
        this.remainingSeconds = this.length.toSeconds();
        this.intervalSeconds = 0;

        return super.start(delay);
    }

    @Override
    public void run() {
        this.remainingSeconds--;

        if (this.remainingSeconds == 0) {
            finish();
            return;
        }

        this.intervalSeconds++;

        if (this.timerInterval.toSeconds() == this.intervalSeconds) {
            onInterval();

            this.intervalSeconds = 0;
        }
    }
}
