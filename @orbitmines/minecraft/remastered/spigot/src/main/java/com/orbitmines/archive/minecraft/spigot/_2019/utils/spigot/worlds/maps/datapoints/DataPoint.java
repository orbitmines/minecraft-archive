package com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.worlds.maps.datapoints;

import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockState;

/*
 * OrbitMines - @author Fadi Shawki - 2018
 */
public abstract class DataPoint {

    @Getter protected final Type type;

    @Getter protected final Material material;

    /* Used for testing */
    @Getter protected String failureMessage;

    public DataPoint(Type type, Material material) {
        this.type = type;
        this.material = material;
    }

    /* Returns whether or not the datapoint successfully loaded */
    public abstract boolean buildAt(DataPointLoader loader, Location location);

    /* Returns whether or not the final setup is completed */
    public abstract boolean setup();

    public boolean equals(BlockState blockState) {
        return blockState.getType() == material;
    }

    public enum Type {

        GOLD_PLATE(Material.LIGHT_WEIGHTED_PRESSURE_PLATE),
        IRON_PLATE(Material.HEAVY_WEIGHTED_PRESSURE_PLATE);

        private final Material material;

        Type(Material material) {
            this.material = material;
        }

        public Material getMaterial() {
            return material;
        }

    }
}
