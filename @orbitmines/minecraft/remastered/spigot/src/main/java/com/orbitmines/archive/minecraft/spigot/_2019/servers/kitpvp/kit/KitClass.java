package com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.kit;

/*
 * OrbitMines - @author Fadi Shawki - 2018
 */

import lombok.Getter;

public enum KitClass {

    MELEE("Melee"),
    RANGED("Ranged"),
    SPELLCASTER("Spellcaster");

    @Getter private final String name;

    KitClass(String name) {
        this.name = name;
    }
}
