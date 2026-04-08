package com.orbitmines.archive.minecraft.spigot._2019.servers.minigames;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.database.models.OMMap;
import lombok.Getter;
import org.bukkit.Material;

public enum Minigame {

    CHICKEN_FIGHT("Chicken Fight", Material.FEATHER),
    SKY_WARS("SkyWars", Material.ENDER_EYE),
    SURVIVAL_GAMES("Survival Games", Material.IRON_SWORD);

    @Getter private final String name;
    @Getter private final Material icon;

    Minigame(String name, Material icon) {
        this.name = name;
        this.icon = icon;
    }

    public OMMap.Type getMapType() {
        return OMMap.Type.valueOf("GAMEMAP_" + name());
    }

    public static Minigame fromMapType(OMMap.Type type) {
        if (type == null)
            return null;

        String name = type.name();
        if (!name.startsWith("GAMEMAP_") || name.equals("GAMEMAP"))
            return null;

        try {
            return Minigame.valueOf(name.substring("GAMEMAP_".length()));
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
