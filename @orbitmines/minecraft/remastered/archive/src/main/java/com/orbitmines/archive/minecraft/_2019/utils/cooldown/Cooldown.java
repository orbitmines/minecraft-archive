package com.orbitmines.archive.minecraft._2019.utils.cooldown;

import lombok.Getter;

/*
* OrbitMines - @author Fadi Shawki - 2017
*/
public class Cooldown {

    @Getter private long cooldown;

    public Cooldown(long cooldown) {
        this.cooldown = cooldown;
    }

    public boolean onCooldown(Long lastUsage) {
        return lastUsage != null && System.currentTimeMillis() - lastUsage < this.cooldown;
    }

    public boolean canUse(Long lastUsage) {
        return !onCooldown(lastUsage);
    }
}
