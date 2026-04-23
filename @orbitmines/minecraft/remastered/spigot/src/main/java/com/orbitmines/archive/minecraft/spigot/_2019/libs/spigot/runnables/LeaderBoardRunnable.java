package com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.runnables;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMServer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.leaderboard.LeaderBoard;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.runnable.Interval;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.runnable.SpigotRunnable;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.runnable.TimeUnit;

public class LeaderBoardRunnable extends SpigotRunnable<OMServer> {

    public LeaderBoardRunnable(OMServer plugin) {
        super(plugin, Interval.of(TimeUnit.MINUTE, 1));
    }

    @Override
    public void run() {
        for (LeaderBoard leaderBoard : LeaderBoard.getLeaderBoards()) {
            leaderBoard.update();
        }
    }
}
