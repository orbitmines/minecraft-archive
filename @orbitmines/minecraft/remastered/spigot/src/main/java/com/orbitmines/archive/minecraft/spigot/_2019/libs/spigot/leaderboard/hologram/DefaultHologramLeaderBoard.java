package com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.leaderboard.hologram;

import com.orbitmines.archive.minecraft._2019.libs.player.Name;
import com.orbitmines.archive.minecraft._2019.libs.player.OfflinePlayer;
import com.orbitmines.archive.minecraft._2019.libs.rank.VipRank;
import com.orbitmines.archive.minecraft._2019.utils.database.DatabaseManager;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.Selectable;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.Table;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.builder.mysql.MySQLQueryBuilder;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.froms.MySQLColumnInstance;
import com.orbitmines.archive.minecraft._2019.utils.mutable.MutableString;
import org.bukkit.Location;

import java.util.*;

/*
 * OrbitMines - @author Fadi Shawki - 29-7-2017
 */
public class DefaultHologramLeaderBoard extends HologramLeaderBoard {

    protected final int size;

    protected ArrayList<LinkedHashMap<Selectable, Object>> ordered;

    public DefaultHologramLeaderBoard(Location location, double yOff, MutableString title, int size, Table table, MySQLColumnInstance uuidColumn, MySQLColumnInstance column, MySQLQueryBuilder queryBuilder) {
        this(location, yOff, new MutableString[] { title }, size, table, uuidColumn, column, queryBuilder);
    }

    public DefaultHologramLeaderBoard(Location location, double yOff,  MutableString[] title, int size, Table table, MySQLColumnInstance uuidColumn, MySQLColumnInstance column, MySQLQueryBuilder queryBuilder) {
        super(location, yOff, table, uuidColumn, column, queryBuilder);

        this.size = size;
        this.ordered = new ArrayList<>();

        for (MutableString string : title) {
            hologram.addLine(string, true);
        }

        hologram.addEmptyLine(true);

        for (int i = 0; i < size; i++) {
            int index = i;

            hologram.addLine(() -> {
                if (ordered.size() < index + 1)
                    return null; /* Empty Line */

                LinkedHashMap<Selectable, Object> entry = ordered.get(index);

                UUID uuid = UUID.fromString((String) entry.get(columnArray[0]));
                OfflinePlayer player = OfflinePlayer.get(uuid);

                int count = asInteger(entry.get(columnArray[1]));

                if (player == null)
                    return "§7" + (index + 1) + ". " + VipRank.NONE.getPrefixColor().getCc() + "UNKNOWN PLAYER" + "  " + getValue(null, count);

                return "§7" + (index + 1) + ". " + player.getName(Name.RAW_COLORED) + "  " + getValue(player, count);
            }, true);
        }
    }

    @Override
    public void update() {
        /* Clear from previous update */
        this.ordered.clear();

        /* Update top {size} players */
        ArrayList<LinkedHashMap<Selectable, Object>> entries = DatabaseManager.getInstance().getDefault().getEntries(queryBuilder, columnArray[0], columnArray[1]);

        this.ordered = getOnLeaderBoard(entries);

        /* Update Hologram */
        hologram.update();
    }

    public int getSize() {
        return size;
    }

    /* Override this method to change the displayed uuids */
    protected ArrayList<LinkedHashMap<Selectable, Object>> getOnLeaderBoard(ArrayList<LinkedHashMap<Selectable, Object>> entries) {
        ArrayList<LinkedHashMap<Selectable, Object>> ordered = new ArrayList<>(entries);
        ordered.sort((m1, m2) -> asInteger(m2.get(columnArray[1])) - asInteger(m1.get(columnArray[1])));

        if (ordered.size() > size)
            ordered = new ArrayList<>(ordered.subList(0, size));

        return ordered;
    }

    /* Override this method to change to change the message displayed at the end */
    public String getValue(OfflinePlayer player, int count) {
        return "§6" + count + "";
    }
}
