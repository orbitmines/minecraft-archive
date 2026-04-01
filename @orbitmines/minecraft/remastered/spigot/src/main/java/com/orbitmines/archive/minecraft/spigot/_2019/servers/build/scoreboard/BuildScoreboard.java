package com.orbitmines.archive.minecraft.spigot._2019.servers.build.scoreboard;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.scoreboard.DefaultScoreboard;
import com.orbitmines.archive.minecraft.spigot._2019.servers.build.Build;
import com.orbitmines.archive.minecraft.spigot._2019.servers.build.BuildPlayer;

public class BuildScoreboard extends DefaultScoreboard<Build, BuildPlayer> {

    public BuildScoreboard(Build server, BuildPlayer player) {
        super(server, player,
                () -> server.getScoreboardAnimation().get(),
                () -> "§m--------------",
                () -> ""
        );
    }

    @Override
    public boolean canBypassSettings() {
        return false;
    }
}
