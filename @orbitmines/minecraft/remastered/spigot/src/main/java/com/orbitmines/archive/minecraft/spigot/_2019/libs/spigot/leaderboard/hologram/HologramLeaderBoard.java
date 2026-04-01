package com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.leaderboard.hologram;

import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.leaderboard.LeaderBoard;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.Selectable;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.Table;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.builder.mysql.MySQLQueryBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.npcs.Hologram;
import org.bukkit.Location;

/*
 * OrbitMines - @author Fadi Shawki - 2018
 */
public abstract class HologramLeaderBoard extends LeaderBoard {

    protected Hologram hologram;

    public HologramLeaderBoard(Location location, double yOff, Table table, Selectable uuidColumn, Selectable column, MySQLQueryBuilder queryBuilder) {
        super(location, table, uuidColumn, column, queryBuilder);

        hologram = new Hologram(location, yOff, Hologram.Face.UP);
    }
}
