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
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMServer;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.*;

/*
 * OrbitMines - @author Fadi Shawki - 29-7-2017
 */
public class DefaultHologramLeaderBoard extends HologramLeaderBoard {

    protected final int size;

    protected volatile String[] displayLines;

    public DefaultHologramLeaderBoard(Location location, double yOff, MutableString title, int size, Table table, MySQLColumnInstance uuidColumn, MySQLColumnInstance column, MySQLQueryBuilder queryBuilder) {
        this(location, yOff, new MutableString[] { title }, size, table, uuidColumn, column, queryBuilder);
    }

    public DefaultHologramLeaderBoard(Location location, double yOff,  MutableString[] title, int size, Table table, MySQLColumnInstance uuidColumn, MySQLColumnInstance column, MySQLQueryBuilder queryBuilder) {
        super(location, yOff, table, uuidColumn, column, queryBuilder);

        this.size = size;
        this.displayLines = new String[size];

        for (MutableString string : title) {
            hologram.addLine(string, true);
        }

        hologram.addEmptyLine(true);

        for (int i = 0; i < size; i++) {
            int index = i;

            hologram.addLine(() -> displayLines[index], true);
        }
    }

    @Override
    public void update() {
        /* Query DB and resolve display strings on async thread */
        ArrayList<LinkedHashMap<Selectable, Object>> entries = DatabaseManager.getInstance().getDefault().getEntries(queryBuilder, columnArray[0], columnArray[1]);

        ArrayList<LinkedHashMap<Selectable, Object>> ordered = getOnLeaderBoard(entries);

        String[] lines = new String[size];
        for (int i = 0; i < size; i++) {
            if (ordered.size() < i + 1) {
                lines[i] = null;
                continue;
            }

            LinkedHashMap<Selectable, Object> entry = ordered.get(i);

            UUID uuid = UUID.fromString((String) entry.get(columnArray[0]));
            OfflinePlayer player = OfflinePlayer.get(uuid);

            int count = asInteger(entry.get(columnArray[1]));

            if (player == null)
                lines[i] = "§7" + (i + 1) + ". " + VipRank.NONE.getPrefixColor().getCc() + "UNKNOWN PLAYER" + "  " + getValue(null, count);
            else
                lines[i] = "§7" + (i + 1) + ". " + player.getName(Name.RAW_COLORED) + "  " + getValue(player, count);
        }
        this.displayLines = lines;

        /* Update Hologram on main thread — ArmorStand metadata must be set synchronously */
        Bukkit.getScheduler().runTask(OMServer.getInstance().getPlugin(), () -> hologram.update());
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
