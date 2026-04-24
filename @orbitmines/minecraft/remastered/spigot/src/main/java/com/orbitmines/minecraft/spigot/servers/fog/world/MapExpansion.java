package com.orbitmines.minecraft.spigot.servers.fog.world;

import com.orbitmines.archive.minecraft._2019.libs.player.Languageable;
import lombok.Getter;

/**
 * Named regions the player can unlock via the MAP_EXPANSION choice.
 * Persistence of the unlocked set lives on {@link com.orbitmines.minecraft.spigot.servers.fog.run.RunStore}.
 */
public enum MapExpansion {

    NORTH_HIGHLANDS(120, 0, 120, 40),
    EAST_BADLANDS  (0, 120, 120, 40),
    SOUTH_DELTA    (-120, 0, 120, 40),
    WEST_DUNES     (0, -120, 120, 40),
    DEEP_CAVE_1    (0, 0, 80, 80);

    @Getter private final int offsetX;
    @Getter private final int offsetZ;
    @Getter private final int radius;
    @Getter private final int height;

    MapExpansion(int offsetX, int offsetZ, int radius, int height) {
        this.offsetX = offsetX;
        this.offsetZ = offsetZ;
        this.radius = radius;
        this.height = height;
    }

    public String nameKey()        { return "expansion." + name().toLowerCase() + ".name"; }
    public String descriptionKey() { return "expansion." + name().toLowerCase() + ".description"; }

    public String getDisplayName(Languageable viewer) { return viewer.translate("fog", nameKey()); }
    public String[] getDescriptionLines(Languageable viewer) {
        return viewer.getLanguage().getStringArray("fog", descriptionKey());
    }
}
