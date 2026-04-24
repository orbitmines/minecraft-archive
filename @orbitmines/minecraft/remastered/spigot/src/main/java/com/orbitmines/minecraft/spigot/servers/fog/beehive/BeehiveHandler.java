package com.orbitmines.minecraft.spigot.servers.fog.beehive;

import com.orbitmines.minecraft.spigot.servers.fog.run.Run;
import com.orbitmines.minecraft.spigot.servers.fog.run.RunStore;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Campfire;
import org.bukkit.entity.Bee;
import org.bukkit.entity.EntityType;

import java.util.Random;

/**
 * Scatters bee-hive "nests" around trees. Each nest is:
 *   - a Beehive block placed on top of the highest solid block;
 *   - a lit campfire one block below the hive, producing signal smoke;
 *   - a ring of random flowers (dandelion, poppy, allium, etc.) on the ground;
 *   - a small swarm of Bees spawned near each hive.
 *
 * Each placed hive's block coordinates are remembered in the {@link RunStore} so
 * {@link com.orbitmines.minecraft.spigot.servers.fog.events.BlockBreakListener}
 * can keep the hive unbreakable (part of the world's natural decor).
 */
public class BeehiveHandler {

    private static final Random RANDOM = new Random();

    private static final Material[] FLOWERS = {
            Material.DANDELION, Material.POPPY, Material.BLUE_ORCHID, Material.ALLIUM,
            Material.AZURE_BLUET, Material.RED_TULIP, Material.ORANGE_TULIP, Material.WHITE_TULIP,
            Material.PINK_TULIP, Material.OXEYE_DAISY, Material.CORNFLOWER, Material.LILY_OF_THE_VALLEY
    };

    public static boolean isProtectedHive(Run run, Location location) {
        if (run == null || location == null) return false;
        return run.getStore().isHoneyTree(location.getBlockX(), location.getBlockY(), location.getBlockZ());
    }

    /**
     * Populate the area around {@code origin} with up to {@code count} bee-hive nests.
     * Only attaches to trees (highest block is a log). Idempotent in the sense that
     * repeated calls may skip positions that already have a hive recorded.
     */
    public static void decorateNearbyTrees(Run run, Location origin, int radius, int count) {
        RunStore store = run.getStore();
        World world = origin.getWorld();
        int placed = 0;
        for (int tries = 0; tries < 60 && placed < count; tries++) {
            int dx = RANDOM.nextInt(radius * 2) - radius;
            int dz = RANDOM.nextInt(radius * 2) - radius;
            int x = origin.getBlockX() + dx;
            int z = origin.getBlockZ() + dz;
            int groundY = world.getHighestBlockYAt(x, z);
            Block ground = world.getBlockAt(x, groundY, z);
            if (!ground.getType().name().endsWith("_LOG")) continue;

            /* Hive goes one block above the tree top; campfire directly below the hive. */
            Block hiveBlock = world.getBlockAt(x, groundY + 1, z);
            Block campfireBlock = world.getBlockAt(x, groundY, z);

            placeCampfire(campfireBlock);
            placeHive(hiveBlock);
            scatterFlowers(world, x, groundY, z, 3);
            spawnBees(world, hiveBlock.getLocation(), 2 + RANDOM.nextInt(2));

            store.setHoneyTree(hiveBlock.getX(), hiveBlock.getY(), hiveBlock.getZ());
            placed++;
        }
    }

    private static void placeHive(Block block) {
        block.setType(Material.BEE_NEST, false);
    }

    private static void placeCampfire(Block block) {
        block.setType(Material.CAMPFIRE, false);
        if (block.getState() instanceof Campfire fire) {
            if (fire.getBlockData() instanceof org.bukkit.block.data.type.Campfire cd) {
                cd.setLit(true);
                cd.setSignalFire(true);
                fire.setBlockData(cd);
            }
            fire.update(true, false);
        }
    }

    private static void scatterFlowers(World world, int cx, int groundY, int cz, int ringRadius) {
        for (int dx = -ringRadius; dx <= ringRadius; dx++) {
            for (int dz = -ringRadius; dz <= ringRadius; dz++) {
                if (Math.abs(dx) + Math.abs(dz) < ringRadius - 1) continue;
                if (RANDOM.nextInt(3) != 0) continue;
                int fx = cx + dx, fz = cz + dz;
                int fy = world.getHighestBlockYAt(fx, fz);
                Block under = world.getBlockAt(fx, fy - 1, fz);
                Block at = world.getBlockAt(fx, fy, fz);
                if (!at.getType().isAir()) continue;
                if (!under.getType().isSolid()) continue;
                at.setType(FLOWERS[RANDOM.nextInt(FLOWERS.length)], false);
            }
        }
    }

    private static void spawnBees(World world, Location at, int count) {
        for (int i = 0; i < count; i++) {
            Location spawn = at.clone().add(RANDOM.nextInt(4) - 2, 1, RANDOM.nextInt(4) - 2);
            Bee bee = (Bee) world.spawnEntity(spawn, EntityType.BEE);
            bee.setRemoveWhenFarAway(false);
            bee.setAnger(0);
        }
    }
}
