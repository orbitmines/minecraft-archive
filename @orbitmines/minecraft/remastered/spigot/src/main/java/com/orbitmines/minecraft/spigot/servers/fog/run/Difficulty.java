package com.orbitmines.minecraft.spigot.servers.fog.run;

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft._2019.libs.player.Languageable;
import lombok.Getter;
import org.bukkit.Material;

/** Difficulty knobs. Text comes from `fog/difficulty.<name>.{name,description}`. */
public enum Difficulty {

    NORMAL  (Color.LIME,   Material.LIME_CONCRETE,   5,  1.0, false),
    HARD    (Color.ORANGE, Material.ORANGE_CONCRETE, 10, 1.5, false),
    HARDCORE(Color.RED,    Material.RED_CONCRETE,    10, 1.5, true);

    @Getter private final Color color;
    @Getter private final Material icon;
    @Getter private final int maxLevelsLostOnDeath;
    @Getter private final double mobDifficultyMultiplier;
    @Getter private final boolean permadeath;

    Difficulty(Color color, Material icon, int maxLevelsLostOnDeath, double mobDifficultyMultiplier, boolean permadeath) {
        this.color = color;
        this.icon = icon;
        this.maxLevelsLostOnDeath = maxLevelsLostOnDeath;
        this.mobDifficultyMultiplier = mobDifficultyMultiplier;
        this.permadeath = permadeath;
    }

    public String nameKey()        { return "difficulty." + name().toLowerCase() + ".name"; }
    public String descriptionKey() { return "difficulty." + name().toLowerCase() + ".description"; }

    public String getDisplayName(Languageable viewer) { return viewer.translate("fog", nameKey()); }
    public String[] getDescriptionLines(Languageable viewer) {
        return viewer.getLanguage().getStringArray("fog", descriptionKey());
    }

    public String getColoredName(Languageable viewer) {
        return color.getCc() + "§l" + getDisplayName(viewer);
    }

    public static Difficulty parse(String s) {
        if (s == null) return NORMAL;
        try { return Difficulty.valueOf(s.toUpperCase()); } catch (IllegalArgumentException e) { return NORMAL; }
    }
}
