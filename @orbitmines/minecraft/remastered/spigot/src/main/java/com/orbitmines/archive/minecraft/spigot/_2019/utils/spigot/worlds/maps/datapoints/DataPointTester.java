package com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.worlds.maps.datapoints;

import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * OrbitMines - @author Fadi Shawki - 2018
 */
public class DataPointTester  extends DataPointLoader {

    @Getter private final Map<DataPoint, List<Location>> successes;
    @Getter private final Map<DataPoint, List<Location>> failures;

    public DataPointTester(World world, Map<DataPoint.Type, List<DataPoint>> dataPoints) {
        super(world, dataPoints);

        successes = new HashMap<>();
        failures = new HashMap<>();
    }

    @Override
    public boolean buildAt(DataPoint dataPoint, Location location) {
        if (super.buildAt(dataPoint, location)) {
            /* DataPoint is setup correctly */
            if (!successes.containsKey(dataPoint))
                successes.put(dataPoint, new ArrayList<>());

            successes.get(dataPoint).add(location);
        } else {
            /* DataPoint is not setup correctly */
            if (!failures.containsKey(dataPoint))
                failures.put(dataPoint, new ArrayList<>());

            failures.get(dataPoint).add(location);
        }

        /* We don't want the datapoints to be build while testing, so we return false */
        return false;
    }
}
