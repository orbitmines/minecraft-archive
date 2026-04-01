package com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.runnable;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import lombok.Getter;

public  enum TimeUnit {

    TICK(1),
    SECOND(20),
    MINUTE(1200),
    HOUR(72000);

    @Getter private long ticks;

    TimeUnit(long ticks) {
        this.ticks = ticks;
    }
}
