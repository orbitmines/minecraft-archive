package com.orbitmines.minecraft.spigot.servers.fog.drone;

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft._2019.libs.player.Languageable;
import com.orbitmines.minecraft.spigot.servers.fog.ore.Ore;
import lombok.Getter;
import org.bukkit.Material;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum ModuleType {

    COLLECT    (Color.YELLOW, Material.HOPPER,          Arrays.asList(Ore.IRON, Ore.COPPER),     2),
    SKILL      (Color.GREEN,  Material.IRON_AXE,        Arrays.asList(Ore.IRON, Ore.COBALT),     3),
    ENCHANTMENT(Color.AQUA,   Material.ENCHANTED_BOOK,  Arrays.asList(Ore.AMETHYST, Ore.COBALT), 3),
    COMBAT     (Color.RED,    Material.IRON_SWORD,      Arrays.asList(Ore.IRON, Ore.URANIUM),    4),
    SHIELD     (Color.BLUE,   Material.SHIELD,          Arrays.asList(Ore.IRIDIUM),              3),
    INSPIRE    (Color.PURPLE, Material.BELL,            Arrays.asList(Ore.AMETHYST, Ore.FRANCIUM), 5);

    @Getter private final Color color;
    @Getter private final Material icon;
    @Getter private final List<Ore> repairOres;
    @Getter private final int repairCostPerOre;

    ModuleType(Color color, Material icon, List<Ore> repairOres, int repairCostPerOre) {
        this.color = color;
        this.icon = icon;
        this.repairOres = Collections.unmodifiableList(repairOres);
        this.repairCostPerOre = repairCostPerOre;
    }

    public String nameKey()        { return "module." + name().toLowerCase() + ".name"; }
    public String descriptionKey() { return "module." + name().toLowerCase() + ".description"; }

    public String getDisplayName(Languageable viewer) { return viewer.translate("fog", nameKey()); }
    public String[] getDescriptionLines(Languageable viewer) {
        return viewer.getLanguage().getStringArray("fog", descriptionKey());
    }
}
