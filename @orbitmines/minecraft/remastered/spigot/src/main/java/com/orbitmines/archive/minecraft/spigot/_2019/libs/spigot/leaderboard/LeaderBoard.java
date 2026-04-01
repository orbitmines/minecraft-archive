package com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.leaderboard;

import com.orbitmines.archive.minecraft._2019.utils.database.lib.Selectable;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.Table;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.builder.mysql.MySQLQueryBuilder;
import org.bukkit.Location;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * OrbitMines - @author Fadi Shawki - 2018
 */
@Deprecated /* TODO: Refactor */
public abstract class LeaderBoard {

    private static List<LeaderBoard> leaderBoards = new ArrayList<>();

    protected Location location;

    protected final Table table;
    protected final Selectable[] columnArray;
    protected final MySQLQueryBuilder queryBuilder;

    public LeaderBoard(Location location) {
        this(location, null, null, null, null);
    }

    public LeaderBoard(Location location, Table table, Selectable uuidColumn, Selectable column, MySQLQueryBuilder queryBuilder) {
        leaderBoards.add(this);

        this.location = location;
        this.table = table;
        this.columnArray = new Selectable[] { uuidColumn, column };
        this.queryBuilder = queryBuilder;
    }

    protected int asInteger(Object value) {
        if (value instanceof Integer)
            return (Integer) value;
        else if (value instanceof Long)
            return ((Long) value).intValue();
        else if (value instanceof BigDecimal)
            return ((BigDecimal) value).intValue();
        else
            return 0;
    }

    public abstract void update();

    public Location getLocation() {
        return location;
    }

    public static List<LeaderBoard> getLeaderBoards() {
        return leaderBoards;
    }

    public static void setup(Map<Location, String[]> leaderboardData) {
        Map<String, Instantiator> instantiators = Instantiator.getInstantiators();

        for (Location location : leaderboardData.keySet()) {
            String[] data = leaderboardData.get(location);

            String name = data[0].toUpperCase();

            if (instantiators.containsKey(name))
                instantiators.get(name).instantiate(location, data);
        }
    }

    public static abstract class Instantiator {

        private static Map<String, Instantiator> instantiators = new HashMap<>();

        protected final String datapointName;

        public Instantiator(String datapointName) {
            this.datapointName = datapointName;

            instantiators.put(datapointName, this);
        }

        public abstract LeaderBoard instantiate(Location location, String[] data);

        public String getDatapointName() {
            return datapointName;
        }

        public static Map<String, Instantiator> getInstantiators() {
            return instantiators;
        }
    }
}
