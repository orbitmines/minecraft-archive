package com.orbitmines.minecraft.spigot.servers.fog.world;

import org.bukkit.Material;
import org.bukkit.World;

/** Seals off a rectangular region with red stained-glass walls. Used when a
    map expansion is marked DAMAGED or a compartment is inaccessible. */
public class StainedGlassBarrier {

    public static void seal(World world, int cx, int cy, int cz, int radius, int height) {
        for (int x = cx - radius; x <= cx + radius; x++) {
            for (int z = cz - radius; z <= cz + radius; z++) {
                int dx = x - cx, dz = z - cz;
                int distSq = dx * dx + dz * dz;
                if (distSq < (radius - 1) * (radius - 1)) continue;
                if (distSq > (radius + 1) * (radius + 1)) continue;
                for (int dy = 0; dy < height; dy++) {
                    world.getBlockAt(x, cy + dy, z).setType(Material.RED_STAINED_GLASS, false);
                }
            }
        }
    }

    public static void unseal(World world, int cx, int cy, int cz, int radius, int height) {
        for (int x = cx - radius; x <= cx + radius; x++) {
            for (int z = cz - radius; z <= cz + radius; z++) {
                int dx = x - cx, dz = z - cz;
                int distSq = dx * dx + dz * dz;
                if (distSq < (radius - 1) * (radius - 1)) continue;
                if (distSq > (radius + 1) * (radius + 1)) continue;
                for (int dy = 0; dy < height; dy++) {
                    if (world.getBlockAt(x, cy + dy, z).getType() == Material.RED_STAINED_GLASS) {
                        world.getBlockAt(x, cy + dy, z).setType(Material.AIR, false);
                    }
                }
            }
        }
    }
}
