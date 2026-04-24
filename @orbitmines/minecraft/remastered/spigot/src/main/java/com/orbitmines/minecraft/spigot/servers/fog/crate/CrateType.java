package com.orbitmines.minecraft.spigot.servers.fog.crate;

import com.orbitmines.archive.minecraft._2019.libs.player.Languageable;
import com.orbitmines.minecraft.spigot.servers.fog.choice.Rarity;
import lombok.Getter;
import org.bukkit.Material;

import java.util.Map;

public enum CrateType {

    BASIC(Material.CHEST, Material.TRIPWIRE_HOOK, Map.of(
            Rarity.COMMON, 70,
            Rarity.RARE, 25,
            Rarity.EPIC, 4,
            Rarity.LEGENDARY, 1)),
    EPIC(Material.TRAPPED_CHEST, Material.LAPIS_LAZULI, Map.of(
            Rarity.COMMON, 10,
            Rarity.RARE, 50,
            Rarity.EPIC, 30,
            Rarity.LEGENDARY, 10));

    @Getter private final Material icon;
    @Getter private final Material keyMaterial;
    @Getter private final Map<Rarity, Integer> rarityWeights;

    CrateType(Material icon, Material keyMaterial, Map<Rarity, Integer> rarityWeights) {
        this.icon = icon;
        this.keyMaterial = keyMaterial;
        this.rarityWeights = Map.copyOf(rarityWeights);
    }

    public String nameKey() { return "crate." + name().toLowerCase() + ".name"; }

    public String getDisplayName(Languageable viewer) { return viewer.translate("fog", nameKey()); }
}
