package com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.datapoints;

import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.datapoints.DataPointLeaderBoard;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.datapoints.DataPointNpc;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.datapoints.DataPointPatchNotes;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.KitPvP;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.datapoints.lobby.KitPvPDataPointLobbyKitInfo;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.datapoints.lobby.KitPvPDataPointSpawnpoint;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.worlds.maps.datapoints.DataPoint;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.worlds.maps.datapoints.DataPointHandler;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.worlds.maps.datapoints.DataPointInstance;

/*
 * OrbitMines - @author Fadi Shawki - 2018
 */
@Deprecated
public class KitPvPLobbyDataPointHandler extends DataPointHandler {

    public KitPvPLobbyDataPointHandler() {
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
                return new DataPointNpc<>((KitPvP) KitPvP.getInstance());
            }
        },
        PATCH_NOTES() {
            @Override
            public DataPoint newInstance() {
                return new DataPointPatchNotes<>((KitPvP) KitPvP.getInstance());
            }
        },

        SPAWNPOINT() {
            @Override
            public DataPoint newInstance() {
                return new KitPvPDataPointSpawnpoint();
            }
        },

        KIT_INFO() {
            @Override
            public DataPoint newInstance() {
                return new KitPvPDataPointLobbyKitInfo();
            }
        };

    }
}
