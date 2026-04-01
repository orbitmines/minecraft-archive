package com.orbitmines.archive.minecraft.bungeecord._2019.servers.bungeecord.utils.runnable;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import lombok.Getter;

public enum TimeUnit {

    MILLISECOND(1),
    SECOND(1000),
    MINUTE(60000),
    HOUR(3600000);

    @Getter private long millis;

    TimeUnit(long millis) {
        this.millis = millis;
    }
}
