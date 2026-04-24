package com.orbitmines.minecraft.spigot.servers.fog.world;

import com.orbitmines.minecraft.spigot.servers.fog.run.Run;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.World.Environment;

import java.util.Random;

/** Runtime wrapper around the {@link World} for a specific {@link Run}. */
public class FoGWorld {

    public static World createOrLoad(String worldFileName, long seed) {
        World existing = Bukkit.getWorld(worldFileName);
        if (existing != null) return existing;

        WorldCreator creator = new WorldCreator(worldFileName)
                .environment(Environment.NORMAL)
                .generateStructures(true)
                .seed(seed);
        World world = Bukkit.createWorld(creator);
        if (world == null) return null;

        world.setDifficulty(Difficulty.NORMAL);
        world.setGameRule(GameRule.KEEP_INVENTORY, false);
        world.setGameRule(GameRule.MOB_DROPS, true);
        world.setGameRule(GameRule.SPAWN_MOBS, true);
        world.setGameRule(GameRule.RANDOM_TICK_SPEED, 3);
        world.setAutoSave(true);
        return world;
    }

    public static long randomSeed() {
        return new Random().nextLong();
    }

    public static String computeFileName(Run run) {
        return run.getOwnerUuid().toString().toLowerCase() + "_fog_" + run.getId();
    }
}
