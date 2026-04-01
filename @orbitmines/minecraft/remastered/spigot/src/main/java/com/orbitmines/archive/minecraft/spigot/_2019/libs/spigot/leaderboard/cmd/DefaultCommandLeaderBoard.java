package com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.leaderboard.cmd;

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft._2019.libs.player.Name;
import com.orbitmines.archive.minecraft._2019.libs.player.OfflinePlayer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMServer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.Command;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.executors.Executor0;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.leaderboard.LeaderBoard;
import com.orbitmines.archive.minecraft._2019.utils.database.DatabaseManager;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.Selectable;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.Table;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.builder.mysql.MySQLQueryBuilder;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

/*
 * OrbitMines - @author Fadi Shawki - 29-7-2017
 */
public abstract class DefaultCommandLeaderBoard<S extends OMServer<S, P>, P extends OMPlayer<S, P>> extends LeaderBoard {

    protected final String name;
    protected final Color color;
    protected final int size;

    protected final Command command;

    protected List<LinkedHashMap<Selectable, Object>> ordered;

    protected int totalCount;

    public DefaultCommandLeaderBoard(S plugin, String name, Color color, String command, String[] aliases, int size, Table table, Selectable uuidColumn, Selectable column, MySQLQueryBuilder queryBuilder) {
        super(null, table, uuidColumn, column, queryBuilder);

        this.name = name;
        this.color = color;
        this.size = size;
        this.ordered = new ArrayList<>();

        this.command = new LeaderBoardCommand(plugin, command, aliases);
        this.command.register();
    }

    public abstract void onDispatch(P player);

    @Override
    public void update() {
        /* Clear from previous update */
        this.ordered.clear();
        this.totalCount = 0;

        /* Update top {size} players */
        ArrayList<LinkedHashMap<Selectable, Object>> entries = DatabaseManager.getInstance().getDefault().getEntries(queryBuilder, columnArray[0], columnArray[1]);

        /* Update Total Count */
        for (LinkedHashMap<Selectable, Object> entry : entries) {
            totalCount += asInteger(entry.get(columnArray[1]));
        }

        this.ordered = getOnLeaderBoard(entries);
    }

    @Override
    public Location getLocation() {
        throw new IllegalStateException();
    }

    public int getSize() {
        return size;
    }

    public int getTotalCount() {
        return totalCount;
    }

    /* Override this method to change the displayed uuids */
    protected List<LinkedHashMap<Selectable, Object>> getOnLeaderBoard(List<LinkedHashMap<Selectable, Object>> entries) {
        List<LinkedHashMap<Selectable, Object>> ordered = new ArrayList<>(entries);
        ordered.sort((m1, m2) -> asInteger(m2.get(columnArray[1])) - asInteger(m1.get(columnArray[1])));

        if (ordered.size() > size)
            ordered = ordered.subList(0, size);

        return ordered;
    }

    /* Override this method to change to change the message displayed at the end */
    public String getValue(OfflinePlayer player, int count) {
        return "§6" + count + "";
    }

    public class LeaderBoardCommand extends Command<S, P> {

        public LeaderBoardCommand(S plugin, String command, String... aliases) {
            super(plugin, command, aliases);

            executes((Executor0<S, P>) player -> {
                player.sendMessage("");
                player.sendMessage(" §8§lOrbit§7§lMines " + color.getCc() + "§l" + DefaultCommandLeaderBoard.this.name);

                for (int i = 0; i < size; i++) {
                    if (ordered.size() < i + 1)
                        continue;

                    LinkedHashMap<Selectable, Object> entry = ordered.get(i);

                    UUID uuid = UUID.fromString((String) entry.get(columnArray[0]));
                    OfflinePlayer offlinePlayer = OfflinePlayer.get(uuid);
                    int count = asInteger(entry.get(columnArray[1]));

                    String color;
                    switch (i) {
                        case 0:
                            color = "§6";
                            break;
                        case 1:
                            color = "§7";
                            break;
                        case 2:
                            color = "§c";
                            break;
                        default:
                            color = "§8";
                            break;
                    }

                    player.sendMessage("  " + color + "§l" + (i + 1) + ". " + offlinePlayer.getName(Name.RAW_COLORED) + " §8- " + getValue(offlinePlayer, count));
                }

                DefaultCommandLeaderBoard.this.onDispatch(player);
            });
        }

        @Override
        public String getDescription(P player) {
            return player.translate("spigot", "player.command." + this.name + ".description");
        }
    }
}
