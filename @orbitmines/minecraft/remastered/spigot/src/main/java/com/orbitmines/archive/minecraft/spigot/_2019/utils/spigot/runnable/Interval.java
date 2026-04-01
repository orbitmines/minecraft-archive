package com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.runnable;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import lombok.Getter;

public class Interval {

    public final static Interval ZERO = new Interval(TimeUnit.TICK, 0);

    @Getter private TimeUnit timeUnit;
    @Getter private int amount;

    public Interval(TimeUnit timeUnit, int amount) {
        this.timeUnit = timeUnit;
        this.amount = amount;
    }

    public long toTicks() {
        return timeUnit.getTicks() * amount;
    }

    public long toMillis() {
        return toTicks() * 50;
    }

    public boolean equals(Interval time) {
        return this.toTicks() == time.toTicks();
    }

    public static Interval of(TimeUnit timeUnit, int amount) {
        return new Interval(timeUnit, amount);
    }

}
