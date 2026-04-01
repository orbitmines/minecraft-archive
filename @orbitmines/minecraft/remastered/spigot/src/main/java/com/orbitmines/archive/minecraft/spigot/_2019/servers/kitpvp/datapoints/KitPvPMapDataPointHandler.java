package com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.datapoints;

import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.datapoints.map.KitPvPDataPointMapBarrier;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.datapoints.map.KitPvPDataPointMapSpawnpoint;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.datapoints.map.KitPvPDataPointMapSpectatorSpawnpoint;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.worlds.maps.datapoints.DataPoint;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.worlds.maps.datapoints.DataPointHandler;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.worlds.maps.datapoints.DataPointInstance;

/*
 * OrbitMines - @author Fadi Shawki - 2018
 */
@Deprecated
public class KitPvPMapDataPointHandler extends DataPointHandler {

    public KitPvPMapDataPointHandler() {
        super(Type.values());
    }

    public enum Type implements DataPointInstance {

        BARRIER() {
            @Override
            public DataPoint newInstance() {
                return new KitPvPDataPointMapBarrier();
            }
        },
        SPAWNPOINT() {
            @Override
            public DataPoint newInstance() {
                return new KitPvPDataPointMapSpawnpoint();
            }
        },
        SPECTATOR_SPAWNPOINT() {
            @Override
            public DataPoint newInstance() {
                return new KitPvPDataPointMapSpectatorSpawnpoint();
            }
        };

    }
}
