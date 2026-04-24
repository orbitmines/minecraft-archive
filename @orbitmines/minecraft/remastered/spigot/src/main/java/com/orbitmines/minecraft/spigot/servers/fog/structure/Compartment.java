package com.orbitmines.minecraft.spigot.servers.fog.structure;

import com.orbitmines.minecraft.spigot.servers.fog.run.Run;
import com.orbitmines.minecraft.spigot.servers.fog.run.RunStore;
import com.orbitmines.minecraft.spigot.servers.fog.world.StainedGlassBarrier;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;

/**
 * A generated city compartment (Farm, Enchanting, Crates, Drone Factory).
 *
 * <p>Responsibilities split to respect the sync/async contract:
 * <ul>
 *   <li>{@link #place(World, CompartmentType, int, int, int)} — sync-only, places
 *       blocks. Returns the centre coords so the caller can persist them later.</li>
 *   <li>{@link #persist(RunStore, CompartmentType, int, int, int)} — async-only,
 *       writes the location + DECAYED state to the RunStore (which hits the DB).</li>
 * </ul>
 */
public class Compartment {

    /** Place the compartment's blocks at (cx, cy, cz). Sync thread only. */
    public static int[] place(World world, CompartmentType type, int cx, int cy, int cz) {
        if (world == null) return null;
        int r = type.getRadius();
        for (int x = cx - r; x <= cx + r; x++) {
            for (int z = cz - r; z <= cz + r; z++) {
                int dx = x - cx, dz = z - cz;
                if (dx * dx + dz * dz > r * r) continue;
                world.getBlockAt(x, cy, z).setType(Material.STONE_BRICKS, false);
            }
        }
        for (int dy = 1; dy <= 3; dy++) {
            for (int x = cx - r; x <= cx + r; x++) {
                for (int z = cz - r; z <= cz + r; z++) {
                    int ddx = x - cx, ddz = z - cz;
                    int distSq = ddx * ddx + ddz * ddz;
                    if (distSq < (r - 1) * (r - 1)) continue;
                    if (distSq > (r + 1) * (r + 1)) continue;
                    world.getBlockAt(x, cy + dy, z).setType(Material.MOSSY_STONE_BRICKS, false);
                }
            }
        }
        world.getBlockAt(cx, cy + 1, cz).setType(type.getMarker(), false);
        Bukkit.getLogger().info("[fog] Generated compartment " + type + " at " + cx + "," + cy + "," + cz);
        return new int[]{cx, cy, cz};
    }

    /** Persist the compartment's location and set its state to DECAYED. Async thread only. */
    public static void persist(RunStore store, CompartmentType type, int cx, int cy, int cz) {
        store.setCompartmentLocation(type, cx, cy, cz);
        store.setCompartmentState(type, RunStore.CompartmentState.DECAYED);
    }

    /** Sync: read cached location (no DB — callers must have fetched coords on async),
        seal with red stained glass. */
    public static void markDamagedBlocks(Run run, CompartmentType type, int cx, int cy, int cz) {
        World world = run.getWorld();
        if (world == null) return;
        StainedGlassBarrier.seal(world, cx, cy + 1, cz, type.getRadius() + 1, type.getHeight());
    }

    /** Async: flip compartment state to DAMAGED. */
    public static void markDamagedPersist(RunStore store, CompartmentType type) {
        store.setCompartmentState(type, RunStore.CompartmentState.DAMAGED);
    }
}
