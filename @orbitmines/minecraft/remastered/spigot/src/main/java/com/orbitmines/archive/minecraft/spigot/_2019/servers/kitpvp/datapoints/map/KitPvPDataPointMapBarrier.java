package com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.datapoints.map;

import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.worlds.maps.datapoints.DataPoint;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.worlds.maps.datapoints.DataPointLoader;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.ArrayList;
import java.util.List;

/*
 * OrbitMines - @author Fadi Shawki - 2018
 */
@Deprecated
public class KitPvPDataPointMapBarrier extends DataPoint {

    @Getter private List<Block> barriers;

    public KitPvPDataPointMapBarrier() {
        super(Type.GOLD_PLATE, Material.WHITE_WOOL);

        barriers = new ArrayList<>();
    }

    @Override
    public boolean buildAt(DataPointLoader loader, Location location) {
        barriers.add(location.getBlock());
        return true;
    }

    @Override
    public boolean setup() {
        return true;
    }
}
