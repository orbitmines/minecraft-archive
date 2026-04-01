package com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.worlds.maps.datapoints;

import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.worlds.maps.WorldMapType;
import org.bukkit.Bukkit;

import java.util.*;

/*
 * OrbitMines - @author Fadi Shawki - 2018
 */
public class DataPointHandler {

    private static Map<WorldMapType, DataPointHandler> handlers = new HashMap<>();

    protected Map<DataPointInstance, DataPoint> instances;

    public DataPointHandler(DataPointInstance... instances) {
        this(Arrays.asList(instances));
    }

    public DataPointHandler(Collection<? extends DataPointInstance> instances) {
        this.instances = new HashMap<>();

        add(instances);
    }

    protected void add(Collection<? extends DataPointInstance> instances) {
        for (DataPointInstance instance : instances) {
            this.instances.put(instance, instance.newInstance());
        }
    }

    public void setup() {
        for (DataPoint dataPoint : instances.values()) {
            if (!dataPoint.setup())
                Bukkit.broadcastMessage(dataPoint.failureMessage);
        }
    }

    public void clearDataPoints() {
        for (DataPointInstance instance : new ArrayList<>(instances.keySet())) {
            this.instances.put(instance, instance.newInstance());
        }
    }

    public DataPoint getDataPoint(DataPointInstance instance) {
        return instances.get(instance);
    }

    public Map<DataPoint.Type, List<DataPoint>> getAsMap() {
        Map<DataPoint.Type, List<DataPoint>> dataPoints = new HashMap<>();

        for (DataPoint dataPoint : instances.values()) {
            dataPoints.computeIfAbsent(dataPoint.getType(), key -> new ArrayList<>()).add(dataPoint);
        }

        return dataPoints;
    }

    public static DataPointHandler getHandler(WorldMapType instance) {
        return handlers.get(instance);
    }

    public static void setHandler(WorldMapType instance, DataPointHandler handler) {
        handlers.put(instance, handler);
    }
}
