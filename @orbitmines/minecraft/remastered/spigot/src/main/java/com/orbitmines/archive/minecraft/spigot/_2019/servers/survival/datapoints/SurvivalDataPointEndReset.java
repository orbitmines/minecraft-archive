package com.orbitmines.archive.minecraft.spigot._2019.servers.survival.datapoints;

import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.worlds.maps.datapoints.DataPointLoader;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.worlds.maps.datapoints.DataPointSign;
import org.bukkit.Location;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

/*
 * OrbitMines - @author Fadi Shawki - 2018
 */
@Deprecated
public class SurvivalDataPointEndReset extends DataPointSign {

    private List<Location> locations;

    public SurvivalDataPointEndReset() {
        super("END_RESET", Type.GOLD_PLATE, Material.BLACK_WOOL);

        locations = new ArrayList<>();
    }

    @Override
    public boolean buildAt(DataPointLoader loader, Location location, String[] data) {
        locations.add(location.add(0.5, 0, 0.5));

        return true;
    }

    @Override
    public boolean setup() {
        return true;
    }

    public List<Location> getLocations() {
        return locations;
    }
}
