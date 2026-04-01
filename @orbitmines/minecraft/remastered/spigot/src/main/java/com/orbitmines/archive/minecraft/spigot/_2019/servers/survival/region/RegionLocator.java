package com.orbitmines.archive.minecraft.spigot._2019.servers.survival.region;

import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.utils.BiomeUtils;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.ItemUtils;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/*
* OrbitMines - @author Fadi Shawki - 2017
*/
public class RegionLocator {

    private final Set<Material> yFinder = new HashSet<>();
    {
        yFinder.addAll(ItemUtils.LEAVES);
        yFinder.addAll(ItemUtils.LOGS);
    }

    private final Set<Material> underWaterPlants = new HashSet<>(Arrays.asList(
            Material.KELP_PLANT,
            Material.SEAGRASS,
            Material.TALL_SEAGRASS,
            Material.SEA_PICKLE,
            
            Material.BRAIN_CORAL,
            Material.BUBBLE_CORAL,
            Material.FIRE_CORAL,
            Material.HORN_CORAL,
            Material.TUBE_CORAL,
            
            Material.BRAIN_CORAL_FAN,
            Material.BUBBLE_CORAL_FAN,
            Material.FIRE_CORAL_FAN,
            Material.HORN_CORAL_FAN,
            Material.TUBE_CORAL_FAN,
            
            Material.BRAIN_CORAL_BLOCK,
            Material.BUBBLE_CORAL_BLOCK,
            Material.FIRE_CORAL_BLOCK,
            Material.HORN_CORAL_BLOCK,
            Material.TUBE_CORAL_BLOCK
    ));

    @Getter private final int id;

    private final World world;
    @Getter private int x;
    @Getter private int z;

    @Getter private int inventoryX;
    @Getter private int inventoryY;

    @Getter private boolean underWaterRegion;

    public RegionLocator(World world, int id) {
        this.id = id;
        this.world = world;
        x = Region.START_X;
        z = Region.START_Z;
        inventoryX = 0;
        inventoryY = 0;
    }

    public int locateY() {
        int depthAtWater = 0;
        int waterDepth = 0;

        for (int y = world.getMaxHeight(); y > 0; y--) {
            Block b = world.getBlockAt(x, y, z);

            if (b.getType() == Material.BARRIER || b.getType() == Material.CONDUIT) {
                if (waterDepth != 0)
                    waterDepth++;

                continue;
            }

            if ((b.getType() == Material.WATER || underWaterPlants.contains(b.getType())) && BiomeUtils.isOceanNotFrozen(b.getBiome())) {
                if (waterDepth == 0)
                    depthAtWater = y;

                waterDepth++;
                continue;
            }

            if (b.isEmpty() || yFinder.contains(b.getType()) || !b.getType().isSolid() && !b.isLiquid())
                continue;

            if (waterDepth >= 8)
                underWaterRegion = true;

            return (underWaterRegion || depthAtWater == 0) ? b.getY() : depthAtWater;
        }

        throw new IllegalArgumentException("Error while locating Region, Void World?");
    }

    public void locate() {
        for (int i = 0; i < id - 1; i++) {
            if (x < z) {
                if (-1 * x < z) {
                    x += Region.OFFSET;
                    inventoryX++;
                    continue;
                }
                z += Region.OFFSET;
                inventoryY++;
                continue;
            }
            if (x > z) {
                if (-1 * x >= z) {
                    x -= Region.OFFSET;
                    inventoryX--;
                    continue;
                }
                z -= Region.OFFSET;
                inventoryY--;
                continue;
            }
            if (x <= 0) {
                z += Region.OFFSET;
                inventoryY++;
                continue;
            }
            z -= Region.OFFSET;
            inventoryY--;
        }
    }
}
