package com.orbitmines.minecraft.spigot.servers.fog.choice;

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft._2019.libs.player.Languageable;
import lombok.Getter;

/** Choice rarity; weights sum to 100. Display name comes from `fog/rarity.<name>.name`. */
public enum Rarity {

    COMMON(Color.WHITE, 50),
    RARE(Color.AQUA, 30),
    EPIC(Color.PURPLE, 15),
    LEGENDARY(Color.YELLOW, 5);

    @Getter private final Color color;
    @Getter private final int weight;

    Rarity(Color color, int weight) {
        this.color = color;
        this.weight = weight;
    }

    public String getTranslationKey() {
        return "rarity." + name().toLowerCase() + ".name";
    }

    public String getDisplayName(Languageable viewer) {
        return viewer.translate("fog", getTranslationKey());
    }

    public String getColoredName(Languageable viewer) {
        return color.getCc() + "§l" + getDisplayName(viewer);
    }
}
