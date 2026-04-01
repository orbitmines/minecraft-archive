package com.orbitmines.archive.minecraft.spigot._2019.servers.survival.utils;

import com.orbitmines.archive.minecraft._2019.libs.Color;
import org.bukkit.Material;
import org.bukkit.block.Biome;

/*
* OrbitMines - @author Fadi Shawki - 2017
*/
public class BiomeUtils {

    public static boolean isOcean(Biome biome) {
        return biome == Biome.OCEAN
            || biome == Biome.DEEP_OCEAN
            || biome == Biome.WARM_OCEAN
            || biome == Biome.FROZEN_OCEAN
            || biome == Biome.LUKEWARM_OCEAN
            || biome == Biome.COLD_OCEAN
            || biome == Biome.DEEP_COLD_OCEAN
            || biome == Biome.DEEP_FROZEN_OCEAN
            || biome == Biome.DEEP_LUKEWARM_OCEAN;
    }

    public static boolean isOceanNotFrozen(Biome biome) {
        return biome == Biome.OCEAN
            || biome == Biome.DEEP_OCEAN
            || biome == Biome.WARM_OCEAN
            || biome == Biome.LUKEWARM_OCEAN
            || biome == Biome.COLD_OCEAN
            || biome == Biome.DEEP_COLD_OCEAN
            || biome == Biome.DEEP_LUKEWARM_OCEAN;
    }

    public static Material material(Biome biome) {
        if (biome == Biome.RIVER) {
            return Material.WATER_BUCKET;
        } else if (biome == Biome.OCEAN || biome == Biome.DEEP_OCEAN) {
            return Material.SEAGRASS;
        } else if (biome == Biome.PLAINS) {
            return Material.GRASS_BLOCK;
        } else if (biome == Biome.DESERT || biome == Biome.BEACH) {
            return Material.SAND;
        } else if (biome == Biome.WINDSWEPT_HILLS || biome == Biome.STONY_SHORE) {
            return Material.STONE;
        } else if (biome == Biome.FOREST || biome == Biome.WINDSWEPT_FOREST) {
            return Material.OAK_SAPLING;
        } else if (biome == Biome.TAIGA || biome == Biome.SNOWY_TAIGA
                || biome == Biome.OLD_GROWTH_PINE_TAIGA
                || biome == Biome.OLD_GROWTH_SPRUCE_TAIGA) {
            return Material.SPRUCE_SAPLING;
        } else if (biome == Biome.SWAMP || biome == Biome.MANGROVE_SWAMP) {
            return Material.VINE;
        } else if (biome == Biome.NETHER_WASTES) {
            return Material.NETHERRACK;
        } else if (biome == Biome.THE_END || biome == Biome.SMALL_END_ISLANDS
                || biome == Biome.END_MIDLANDS || biome == Biome.END_HIGHLANDS
                || biome == Biome.END_BARRENS) {
            return Material.END_STONE;
        } else if (biome == Biome.FROZEN_RIVER || biome == Biome.COLD_OCEAN
                || biome == Biome.DEEP_COLD_OCEAN) {
            return Material.ICE;
        } else if (biome == Biome.SNOWY_PLAINS || biome == Biome.SNOWY_BEACH) {
            return Material.SNOW_BLOCK;
        } else if (biome == Biome.MUSHROOM_FIELDS) {
            return Material.RED_MUSHROOM_BLOCK;
        } else if (biome == Biome.JUNGLE || biome == Biome.SPARSE_JUNGLE) {
            return Material.JUNGLE_SAPLING;
        } else if (biome == Biome.BIRCH_FOREST || biome == Biome.OLD_GROWTH_BIRCH_FOREST) {
            return Material.BIRCH_SAPLING;
        } else if (biome == Biome.DARK_FOREST) {
            return Material.DARK_OAK_SAPLING;
        } else if (biome == Biome.SAVANNA || biome == Biome.SAVANNA_PLATEAU
                || biome == Biome.WINDSWEPT_SAVANNA) {
            return Material.ACACIA_SAPLING;
        } else if (biome == Biome.BADLANDS || biome == Biome.WOODED_BADLANDS
                || biome == Biome.ERODED_BADLANDS) {
            return Material.YELLOW_TERRACOTTA;
        } else if (biome == Biome.WARM_OCEAN || biome == Biome.LUKEWARM_OCEAN
                || biome == Biome.DEEP_LUKEWARM_OCEAN) {
            return Material.HORN_CORAL;
        } else if (biome == Biome.THE_VOID) {
            return Material.BEDROCK;
        } else if (biome == Biome.SUNFLOWER_PLAINS) {
            return Material.SUNFLOWER;
        } else if (biome == Biome.WINDSWEPT_GRAVELLY_HILLS) {
            return Material.GRAVEL;
        } else if (biome == Biome.FLOWER_FOREST) {
            return Material.ROSE_BUSH;
        } else if (biome == Biome.DEEP_FROZEN_OCEAN || biome == Biome.ICE_SPIKES
                || biome == Biome.FROZEN_OCEAN) {
            return Material.PACKED_ICE;
        } else if (biome == Biome.BAMBOO_JUNGLE) {
            return Material.BAMBOO;
        }
        throw new IllegalArgumentException();
    }

    public static String name(Biome biome) {
        StringBuilder stringBuilder = new StringBuilder();

        String[] parts = biome.name().split("_");
        for (int i = 0; i < parts.length; i++) {
            if (i != 0)
                stringBuilder.append(" ");

            stringBuilder.append(parts[i].substring(0, 1).toUpperCase());
            stringBuilder.append(parts[i].substring(1).toLowerCase());
        }

        return color(biome).getCc() + stringBuilder.toString();
    }

    private static Color color(Biome biome) {
        if (biome == Biome.OCEAN || biome == Biome.RIVER || biome == Biome.DEEP_OCEAN) {
            return Color.BLUE;
        } else if (biome == Biome.PLAINS) {
            return Color.LIME;
        } else if (biome == Biome.DESERT || biome == Biome.BEACH) {
            return Color.YELLOW;
        } else if (biome == Biome.WINDSWEPT_HILLS || biome == Biome.STONY_SHORE) {
            return Color.SILVER;
        } else if (biome == Biome.FOREST || biome == Biome.WINDSWEPT_FOREST) {
            return Color.LIME;
        } else if (biome == Biome.TAIGA || biome == Biome.SNOWY_TAIGA
                || biome == Biome.OLD_GROWTH_PINE_TAIGA
                || biome == Biome.OLD_GROWTH_SPRUCE_TAIGA) {
            return Color.GREEN;
        } else if (biome == Biome.SWAMP || biome == Biome.MANGROVE_SWAMP) {
            return Color.GREEN;
        } else if (biome == Biome.NETHER_WASTES) {
            return Color.RED;
        } else if (biome == Biome.THE_END || biome == Biome.SMALL_END_ISLANDS
                || biome == Biome.END_MIDLANDS || biome == Biome.END_HIGHLANDS
                || biome == Biome.END_BARRENS) {
            return Color.YELLOW;
        } else if (biome == Biome.FROZEN_OCEAN || biome == Biome.FROZEN_RIVER
                || biome == Biome.COLD_OCEAN || biome == Biome.DEEP_COLD_OCEAN
                || biome == Biome.DEEP_FROZEN_OCEAN) {
            return Color.AQUA;
        } else if (biome == Biome.SNOWY_PLAINS || biome == Biome.SNOWY_BEACH) {
            return Color.WHITE;
        } else if (biome == Biome.MUSHROOM_FIELDS) {
            return Color.RED;
        } else if (biome == Biome.JUNGLE || biome == Biome.SPARSE_JUNGLE) {
            return Color.GREEN;
        } else if (biome == Biome.BIRCH_FOREST || biome == Biome.OLD_GROWTH_BIRCH_FOREST) {
            return Color.WHITE;
        } else if (biome == Biome.DARK_FOREST) {
            return Color.GREEN;
        } else if (biome == Biome.SAVANNA || biome == Biome.SAVANNA_PLATEAU
                || biome == Biome.WINDSWEPT_SAVANNA) {
            return Color.ORANGE;
        } else if (biome == Biome.BADLANDS || biome == Biome.WOODED_BADLANDS
                || biome == Biome.ERODED_BADLANDS) {
            return Color.ORANGE;
        } else if (biome == Biome.WARM_OCEAN || biome == Biome.LUKEWARM_OCEAN
                || biome == Biome.DEEP_LUKEWARM_OCEAN) {
            return Color.BLUE;
        } else if (biome == Biome.THE_VOID) {
            return Color.GRAY;
        } else if (biome == Biome.SUNFLOWER_PLAINS) {
            return Color.YELLOW;
        } else if (biome == Biome.WINDSWEPT_GRAVELLY_HILLS) {
            return Color.SILVER;
        } else if (biome == Biome.FLOWER_FOREST) {
            return Color.RED;
        } else if (biome == Biome.ICE_SPIKES) {
            return Color.AQUA;
        } else if (biome == Biome.BAMBOO_JUNGLE) {
            return Color.YELLOW;
        }
        throw new IllegalArgumentException();
    }
}
