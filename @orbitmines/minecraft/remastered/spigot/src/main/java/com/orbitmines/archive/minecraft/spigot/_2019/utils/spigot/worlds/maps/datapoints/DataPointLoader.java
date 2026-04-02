package com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.worlds.maps.datapoints;

import lombok.Getter;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.BlockState;

import java.util.List;
import java.util.Map;
import java.util.Set;

/*
 * OrbitMines - @author Fadi Shawki - 2018
 */
public class DataPointLoader {

    @Getter protected final World world;
    @Getter protected final Map<DataPoint.Type, List<DataPoint>> dataPoints;

    public DataPointLoader(World world, Map<DataPoint.Type, List<DataPoint>> dataPoints) {
        this.world = world;
        this.dataPoints = dataPoints;
    }

    protected boolean buildAt(DataPoint dataPoint, Location location) {
        return dataPoint.buildAt(this, location);
    }

    public void load() {
        Set<DataPoint.Type> types = dataPoints.keySet();

        /* Ensure spawn chunks are loaded — in 26.1 they may not be loaded yet after world creation */
        Location spawn = world.getSpawnLocation();
        int spawnChunkX = spawn.getBlockX() >> 4;
        int spawnChunkZ = spawn.getBlockZ() >> 4;
        int radius = 8;
        for (int cx = spawnChunkX - radius; cx <= spawnChunkX + radius; cx++) {
            for (int cz = spawnChunkZ - radius; cz <= spawnChunkZ + radius; cz++) {
                world.getChunkAt(cx, cz);
            }
        }

        int minY = world.getMinHeight();
        int maxY = world.getMaxHeight();

        for (Chunk chunk : world.getLoadedChunks()) {
            int cX = chunk.getX() << 4;
            int cZ = chunk.getZ() << 4;

            for (int x = cX; x < cX + 16; x++) {
                for (int z = cZ; z < cZ + 16; z++) {
                    for (int y = minY; y < maxY; y++) {
                        Material material = world.getBlockAt(x, y, z).getType();

                        if (material == Material.AIR)
                            continue;

                        loop:
                        for (DataPoint.Type type : types) {
                            if (material != type.getMaterial())
                                continue;

                            /* Might be a DataPoint: */
                            BlockState under = world.getBlockAt(world.getBlockAt(x, y, z).getLocation().add(0, -1, 0)).getState();

                            for (DataPoint dataPoint : dataPoints.get(type)) {
                                /* DataPoint found */
                                if (dataPoint.equals(under)) {
                                    Location location = under.getLocation().clone();

                                    if (buildAt(dataPoint, location)) {
                                        /* Clear DataPoint */
                                        world.getBlockAt(x, y, z).setType(Material.AIR);
                                        under.getBlock().setType(Material.AIR);
                                    }

                                    break loop;
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
