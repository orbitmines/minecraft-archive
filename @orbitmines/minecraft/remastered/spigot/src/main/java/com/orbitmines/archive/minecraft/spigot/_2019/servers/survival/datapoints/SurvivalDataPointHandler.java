package com.orbitmines.archive.minecraft.spigot._2019.servers.survival.datapoints;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.datapoints.DataPointLeaderBoard;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.datapoints.DataPointNpc;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.datapoints.DataPointPatchNotes;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.Survival;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.worlds.maps.datapoints.DataPoint;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.worlds.maps.datapoints.DataPointHandler;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.worlds.maps.datapoints.DataPointInstance;

@Deprecated
public class SurvivalDataPointHandler extends DataPointHandler {

    public SurvivalDataPointHandler() {
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
                return new DataPointNpc<>((Survival) Survival.getInstance());
            }
        },
        PATCH_NOTES() {
            @Override
            public DataPoint newInstance() {
                return new DataPointPatchNotes<>((Survival) Survival.getInstance());
            }
        },

        SPAWNPOINT() {
            @Override
            public DataPoint newInstance() {
                return new SurvivalDataPointSpawnpoint();
            }
        },

        NETHER_RESET() {
            @Override
            public DataPoint newInstance() {
                return new SurvivalDataPointNetherReset();
            }
        },
        END_RESET() {
            @Override
            public DataPoint newInstance() {
                return new SurvivalDataPointEndReset();
            }
        },
        SPAWN_SHOP() {
            @Override
            public DataPoint newInstance() {
                return new SurvivalDataPointSpawnShop();
            }
        };

    }
}
