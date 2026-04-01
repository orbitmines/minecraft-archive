package com.orbitmines.archive.minecraft.spigot._2019.servers.hub.datapoints;

import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.datapoints.DataPointLeaderBoard;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.datapoints.DataPointNpc;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.datapoints.DataPointPatchNotes;
import com.orbitmines.archive.minecraft.spigot._2019.servers.hub.Hub;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.worlds.maps.datapoints.DataPoint;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.worlds.maps.datapoints.DataPointHandler;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.worlds.maps.datapoints.DataPointInstance;

/*
 * OrbitMines - @author Fadi Shawki - 2018
 */
@Deprecated
public class HubDataPointHandler extends DataPointHandler {

    public HubDataPointHandler() {
        super(Type.values());
    }

    public enum Type implements DataPointInstance {

        LEADERBOARD() {
            @Override
            public DataPoint newInstance() {
                return new DataPointLeaderBoard();
            }
        },
        NPC() {
            @Override
            public DataPoint newInstance() {
                return new DataPointNpc<>((Hub) Hub.getInstance());
            }
        },
        PATCH_NOTES() {
            @Override
            public DataPoint newInstance() {
                return new DataPointPatchNotes<>((Hub) Hub.getInstance());
            }
        },

        SPAWNPOINT() {
            @Override
            public DataPoint newInstance() {
                return new HubDataPointSpawnpoint();
            }
        },
        STAFF_HOLO() {
            @Override
            public DataPoint newInstance() {
                return new HubDataPointStaffHologram();
            }
        };

    }
}
