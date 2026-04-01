package com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.kit;

/*
 * OrbitMines - @author Fadi Shawki - 2018
 */

import lombok.Getter;

public enum HealthRegen {

    EXTREMELY_LOW(0.5D, "Extremely Low"),
    LOW(0.7D, "Low"),
    MEDIUM(0.825D, "Medium"),
    ALMOST_NORMAL(0.90D, "Almost Normal"),
    NORMAL(1.0D, "Normal"),
    HIGH(1.1D, "High"),
    HIGHEST(1.1825D, "Highest"),
    INSANE(1.50D, "Insane");

    @Getter private final double multiplier;
    @Getter private final String name;

    HealthRegen(double multiplier, String name) {
        this.multiplier = multiplier;
        this.name = name;
    }
}
