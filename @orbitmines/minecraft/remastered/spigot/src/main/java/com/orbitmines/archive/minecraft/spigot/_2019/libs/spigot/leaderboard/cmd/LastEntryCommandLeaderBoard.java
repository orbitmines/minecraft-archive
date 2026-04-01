package com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.leaderboard.cmd;

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMServer;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.Selectable;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.Table;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.builder.mysql.MySQLQueryBuilder;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.froms.MySQLColumnInstance;

import java.util.*;

/*
 * OrbitMines - @author Fadi Shawki - 29-7-2017
 */
public abstract class LastEntryCommandLeaderBoard<S extends OMServer<S, P>, P extends OMPlayer<S, P>> extends DefaultCommandLeaderBoard<S, P> {

    public LastEntryCommandLeaderBoard(S plugin, String name, Color color, String command, String[] aliases, int size, Table table, MySQLColumnInstance uuidColumn, MySQLColumnInstance column, MySQLQueryBuilder queryBuilder) {
        super(plugin, name, color, command, aliases, size, table, uuidColumn, column, queryBuilder);
    }

    @Override
    protected List<LinkedHashMap<Selectable, Object>> getOnLeaderBoard(List<LinkedHashMap<Selectable, Object>> entries) {
        List<LinkedHashMap<Selectable, Object>> ordered = new ArrayList<>(entries);

        if (ordered.size() > size)
            ordered = ordered.subList(ordered.size() -size, ordered.size());

        Collections.reverse(ordered);

        return ordered;
    }
}
