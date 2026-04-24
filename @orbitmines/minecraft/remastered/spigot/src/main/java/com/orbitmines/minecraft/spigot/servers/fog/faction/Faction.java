package com.orbitmines.minecraft.spigot.servers.fog.faction;

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft._2019.libs.player.Languageable;
import lombok.Getter;
import org.bukkit.Material;

public enum Faction {

    OMEGA(Color.PURPLE, Material.ENDER_EYE),
    ALPHA(Color.RED,    Material.BLAZE_POWDER),
    BETA (Color.AQUA,   Material.PRISMARINE_CRYSTALS);

    @Getter private final Color color;
    @Getter private final Material icon;

    Faction(Color color, Material icon) {
        this.color = color;
        this.icon = icon;
    }

    public String nameKey()        { return "faction." + name().toLowerCase() + ".name"; }
    public String descriptionKey() { return "faction." + name().toLowerCase() + ".description"; }

    public String getDisplayName(Languageable viewer) { return viewer.translate("fog", nameKey()); }
    public String[] getDescriptionLines(Languageable viewer) {
        return viewer.getLanguage().getStringArray("fog", descriptionKey());
    }

    public String getColoredName(Languageable viewer) {
        return color.getCc() + "§l" + getDisplayName(viewer);
    }

    public static Faction parse(String s) {
        if (s == null) return null;
        try { return Faction.valueOf(s.toUpperCase()); } catch (IllegalArgumentException e) { return null; }
    }
}
