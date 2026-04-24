package com.orbitmines.minecraft.spigot.servers.fog.ore;

import com.orbitmines.minecraft.spigot.servers.fog.choice.Choice;
import lombok.Getter;
import org.bukkit.Material;

public enum Ore {

    /* Vanilla — always available */
    COAL(null, Material.COAL_ORE, Material.COAL, 1),
    IRON(null, Material.IRON_ORE, Material.RAW_IRON, 3),
    GOLD(null, Material.GOLD_ORE, Material.RAW_GOLD, 5),
    LAPIS(null, Material.LAPIS_ORE, Material.LAPIS_LAZULI, 6),
    REDSTONE(null, Material.REDSTONE_ORE, Material.REDSTONE, 4),
    DIAMOND(null, Material.DIAMOND_ORE, Material.DIAMOND, 10),
    EMERALD(null, Material.EMERALD_ORE, Material.EMERALD, 12),

    /* Unlocked via choices */
    COPPER(Choice.ORE_COPPER,     Material.COPPER_ORE,    Material.RAW_COPPER,        3),
    COBALT(Choice.ORE_COBALT,     Material.LIGHT_BLUE_CONCRETE_POWDER, Material.LIGHT_BLUE_DYE, 6),
    STRONTIUM(Choice.ORE_STRONTIUM, Material.YELLOW_CONCRETE_POWDER, Material.GLOWSTONE_DUST,  8),
    AMETHYST(Choice.ORE_AMETHYST, Material.AMETHYST_BLOCK, Material.AMETHYST_SHARD,    7),
    URANIUM(Choice.ORE_URANIUM,   Material.LIME_CONCRETE_POWDER, Material.LIME_DYE,    15),
    IRIDIUM(Choice.ORE_IRIDIUM,   Material.LIGHT_GRAY_CONCRETE_POWDER, Material.LIGHT_GRAY_DYE, 18),
    FRANCIUM(Choice.ORE_FRANCIUM, Material.MAGENTA_CONCRETE_POWDER, Material.MAGENTA_DYE, 25);

    @Getter private final Choice unlockChoice;
    @Getter private final Material oreMaterial;
    @Getter private final Material dropMaterial;
    @Getter private final int value; // abstract value for shop pricing / drone repair costs

    Ore(Choice unlockChoice, Material oreMaterial, Material dropMaterial, int value) {
        this.unlockChoice = unlockChoice;
        this.oreMaterial = oreMaterial;
        this.dropMaterial = dropMaterial;
        this.value = value;
    }

    public boolean alwaysAvailable() { return unlockChoice == null; }
}
