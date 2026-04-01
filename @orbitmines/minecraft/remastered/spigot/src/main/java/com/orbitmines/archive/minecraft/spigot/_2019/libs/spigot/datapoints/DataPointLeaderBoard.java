package com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.datapoints;

import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMServer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.leaderboard.LeaderBoard;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.worlds.maps.datapoints.DataPointLoader;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.worlds.maps.datapoints.DataPointSign;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;

/*
 * OrbitMines - @author Fadi Shawki - 2018
 */
@Deprecated
public class DataPointLeaderBoard extends DataPointSign {

    private Map<Location, String[]> leaderboardData;

    public DataPointLeaderBoard() {
        super("LEADERBOARD", Type.IRON_PLATE, Material.RED_WOOL);

        leaderboardData = new HashMap<>();
    }

    @Override
    public boolean buildAt(DataPointLoader loader, Location location, String[] data) {
        leaderboardData.put(location.add(0.5, 0.5, 0.5), data);
        return true;
    }

    @Override
    public boolean setup() {
        if (leaderboardData.isEmpty()) {
            failureMessage = "Did not find any leaderboard data points";
            return false;
        }

        /* Add delay for LeaderBoard setup */
        new BukkitRunnable() {
            @Override
            public void run() {
                LeaderBoard.setup(leaderboardData);
            }
        }.runTaskLater(OMServer.getInstance().getPlugin(), 1);

        return true;
    }

    public Map<Location, String[]> getLeaderboardData() {
        return leaderboardData;
    }
}
