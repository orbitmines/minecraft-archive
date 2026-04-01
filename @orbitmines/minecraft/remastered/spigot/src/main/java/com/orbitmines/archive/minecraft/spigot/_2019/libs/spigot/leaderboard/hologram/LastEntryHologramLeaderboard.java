package com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.leaderboard.hologram;

/*
 * OrbitMines - @author Fadi Shawki - 2018
 */

import com.orbitmines.archive.minecraft._2019.utils.database.lib.Selectable;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.Table;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.builder.mysql.MySQLQueryBuilder;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.froms.MySQLColumnInstance;
import com.orbitmines.archive.minecraft._2019.utils.mutable.MutableString;
import org.bukkit.Location;

import java.util.*;

public abstract class LastEntryHologramLeaderboard extends DefaultHologramLeaderBoard {

    public LastEntryHologramLeaderboard(Location location, double yOff, MutableString title, int size, Table table, MySQLColumnInstance uuidColumn, MySQLColumnInstance column, MySQLQueryBuilder queryBuilder) {
        super(location, yOff, title, size, table, uuidColumn, column, queryBuilder);
    }

    public LastEntryHologramLeaderboard(Location location, double yOff, MutableString[] title, int size, Table table, MySQLColumnInstance uuidColumn, MySQLColumnInstance column, MySQLQueryBuilder queryBuilder) {
        super(location, yOff, title, size, table, uuidColumn, column, queryBuilder);
    }

    @Override
    protected ArrayList<LinkedHashMap<Selectable, Object>> getOnLeaderBoard(ArrayList<LinkedHashMap<Selectable, Object>> entries) {
        ArrayList<LinkedHashMap<Selectable, Object>> ordered = new ArrayList<>(entries);

        if (ordered.size() > size)
            ordered = new ArrayList<>(ordered.subList(ordered.size() -size, ordered.size()));

        Collections.reverse(ordered);

        return ordered;
    }
}
