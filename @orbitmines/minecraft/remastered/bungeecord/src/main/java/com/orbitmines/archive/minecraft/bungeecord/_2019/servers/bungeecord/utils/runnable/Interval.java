package com.orbitmines.archive.minecraft.bungeecord._2019.servers.bungeecord.utils.runnable;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import lombok.Getter;

public class Interval {

    public final static Interval ZERO = new Interval(TimeUnit.MILLISECOND, 0);

    @Getter private TimeUnit timeUnit;
    @Getter private int amount;

    public Interval(TimeUnit timeUnit, int amount) {
        this.timeUnit = timeUnit;
        this.amount = amount;
    }

    public long toMillis() {
        return timeUnit.getMillis() * amount;
    }

    public long toSeconds() {
        return (timeUnit.getMillis() / 1000) * amount;
    }

    public boolean equals(Interval time) {
        return this.toMillis() == time.toMillis();
    }

    public static Interval of(TimeUnit timeUnit, int amount) {
        return new Interval(timeUnit, amount);
    }

}
