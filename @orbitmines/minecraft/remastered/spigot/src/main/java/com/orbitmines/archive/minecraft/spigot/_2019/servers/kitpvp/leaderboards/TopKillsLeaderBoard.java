package com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.leaderboards;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.player.OfflinePlayer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.database.models.kitpvp.KitPvPPlayerKitModel;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.leaderboard.LeaderBoard;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.leaderboard.podium.DefaultPodiumLeaderBoard;
import com.orbitmines.archive.minecraft._2019.utils.NumberUtils;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.Selectable;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.builder.mysql.MySQLQueryBuilder;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.froms.MySQLColumnInstance;
import org.bukkit.Location;

public class TopKillsLeaderBoard extends LeaderBoard.Instantiator {

    public TopKillsLeaderBoard() {
        super("KITPVP_KILLS");
    }

    @Override
    public LeaderBoard instantiate(Location location, String[] data) {
        Selectable totalKills = () -> "SUM(`kitpvp_player_kits`.`kills`) AS total_kills";

        MySQLColumnInstance uuidColumn = KitPvPPlayerKitModel.column.UUID.getColumn();

        return new DefaultPodiumLeaderBoard(location, data, 3,
            KitPvPPlayerKitModel.TABLE,
            KitPvPPlayerKitModel.column.UUID.getColumn(),
            totalKills,
            new MySQLQueryBuilder(KitPvPPlayerKitModel.TABLE).group(uuidColumn)
        ) {
            @Override
            public String getValue(OfflinePlayer player, int count) {
                return "§c§l" + NumberUtils.locale(count) + " " + (count == 1 ? "Kill" : "Kills");
            }
        };
    }
}
