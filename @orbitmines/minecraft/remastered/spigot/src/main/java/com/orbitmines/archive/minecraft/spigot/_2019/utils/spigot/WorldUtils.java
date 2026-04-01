package com.orbitmines.archive.minecraft.spigot._2019.utils.spigot;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class WorldUtils {

    public static void removeAllEntities() {
        for (World world : Bukkit.getWorlds()) {
            removeEntities(world);
        }
    }

    public static void removeEntities(World world) {
        for (Entity en : world.getEntities()) {
            if (en instanceof Player)
                continue;

            en.remove();
        }
    }

    public static List<Entity> getEntities(World world, Collection<Entity> entities) {
        return entities.stream().
                filter(entity -> world.equals(entity.getWorld())).
                collect(Collectors.toList());
    }

    public static int blockToChunk(int blockVal) {    // 1 chunk is 16x16 blocks
        return blockVal >> 4;   // ">>4" == "/16"
    }

    public static int blockToRegion(int blockVal) {    // 1 region is 512x512 blocks
        return blockVal >> 9;   // ">>9" == "/512"
    }

    public static int chunkToRegion(int chunkVal) {    // 1 region is 32x32 chunks
        return chunkVal >> 5;   // ">>5" == "/32"
    }

    public static int chunkToBlock(int chunkVal) {
        return chunkVal << 4;   // "<<4" == "*16"
    }

    public static int regionToBlock(int regionVal) {
        return regionVal << 9;   // "<<9" == "*512"
    }

    public static int regionToChunk(int regionVal) {
        return regionVal << 5;   // "<<5" == "*32"
    }
}
