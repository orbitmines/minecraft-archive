package com.orbitmines.minecraft.spigot.servers.fog.world;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

/**
 * Builds a simple stone-brick ring around the player's starting area.
 * The wall constrains where the player can roam pre-map-expansion.
 */
public class CityWall {

    public static void build(World world, int cx, int cy, int cz, int radius, int height) {
        for (int x = cx - radius; x <= cx + radius; x++) {
            for (int z = cz - radius; z <= cz + radius; z++) {
                int dx = x - cx, dz = z - cz;
                int distSq = dx * dx + dz * dz;
                if (distSq < (radius - 1) * (radius - 1)) continue;
                if (distSq > (radius + 1) * (radius + 1)) continue;
                for (int dy = 0; dy < height; dy++) {
                    Block b = world.getBlockAt(x, cy + dy, z);
                    b.setType(Material.STONE_BRICKS, false);
                }
                /* Top cap */
                world.getBlockAt(x, cy + height, z).setType(Material.SMOOTH_STONE_SLAB, false);
            }
        }
    }

    /** Convert existing wall blocks into red stained glass to mark "damaged/inaccessible" boundaries. */
    public static void sealAsDamaged(World world, int cx, int cy, int cz, int radius, int height) {
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
}
