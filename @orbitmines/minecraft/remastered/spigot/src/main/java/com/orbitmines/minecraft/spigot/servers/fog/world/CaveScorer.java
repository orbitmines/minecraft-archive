package com.orbitmines.minecraft.spigot.servers.fog.world;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

/**
 * Evaluate a region of a world for the presence of a "big cave" beneath it.
 * The POC uses a cheap sampling heuristic: count air blocks in the Y range
 * [-30, 40] across the column; higher air count = better cave.
 *
 * Score is deliberately coarse — it doesn't have to be right, just good
 * enough that the player's base ends up over a genuinely open cavern
 * rather than a solid mountain.
 */
public class CaveScorer {

    /** Score a single column. Higher == more cave-like. */
    public static int scoreColumn(World world, int x, int z) {
        int surface = world.getHighestBlockYAt(x, z);
        int air = 0;
        int yMin = Math.max(world.getMinHeight(), -40);
        int yMax = Math.min(surface - 5, 50);
        for (int y = yMin; y < yMax; y++) {
            Block b = world.getBlockAt(x, y, z);
            if (b.getType().isAir()) air++;
        }
        return air;
    }

    /** Score a 16x16 chunk by summing column scores. */
    public static int scoreChunk(World world, int chunkX, int chunkZ) {
        int sx = chunkX << 4, sz = chunkZ << 4;
        int total = 0;
        for (int dx = 0; dx < 16; dx += 4) {
            for (int dz = 0; dz < 16; dz += 4) {
                total += scoreColumn(world, sx + dx, sz + dz);
            }
        }
        return total;
    }

    /**
     * Search a spiral of chunks around (centreChunkX, centreChunkZ) and return
     * the best-scoring chunk location. `radius` is in chunks. Caller is expected
     * to run this on an async thread — it forces chunk generation via
     * {@link World#getChunkAt(int, int)}.
     */
    public static Location findBestSpawn(World world, int centreChunkX, int centreChunkZ, int radius) {
        int bestScore = Integer.MIN_VALUE;
        Chunk best = null;
        for (int r = 0; r <= radius; r++) {
            for (int dx = -r; dx <= r; dx++) {
                for (int dz = -r; dz <= r; dz++) {
                    if (Math.abs(dx) != r && Math.abs(dz) != r) continue;
                    Chunk c = world.getChunkAt(centreChunkX + dx, centreChunkZ + dz);
                    int s = scoreChunk(world, c.getX(), c.getZ());
                    if (s > bestScore) {
                        bestScore = s;
                        best = c;
                    }
                }
            }
        }
        if (best == null) return world.getSpawnLocation();
        int wx = (best.getX() << 4) + 8, wz = (best.getZ() << 4) + 8;
        int surface = world.getHighestBlockYAt(wx, wz);
        /* Spawn on top of the surface, clear of trees */
        while (surface < world.getMaxHeight() && !world.getBlockAt(wx, surface, wz).getType().isAir()) {
            surface++;
        }
        return new Location(world, wx + 0.5, surface + 1, wz + 0.5);
    }
}
