package com.orbitmines.archive.minecraft.spigot._2019.servers.survival.leaderboards;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.player.OfflinePlayer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.database.models.survival.SurvivalPlayerModel;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.leaderboard.LeaderBoard;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.leaderboard.hologram.DefaultHologramLeaderBoard;
import com.orbitmines.archive.minecraft._2019.utils.NumberUtils;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.builder.mysql.MySQLQueryBuilder;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.operators.mysql.Order;
import org.bukkit.Location;

public class LeaderBoardCredits extends LeaderBoard.Instantiator {

    public LeaderBoardCredits() {
        super("EARTH_MONEY");
    }

    @Override
    public LeaderBoard instantiate(Location location, String[] data) {
        return new DefaultHologramLeaderBoard(
            location,
            0,
            () -> "§7§lRichest Players",
            10,
            SurvivalPlayerModel.TABLE,
            SurvivalPlayerModel.column.UUID.getColumn(),
            SurvivalPlayerModel.column.CREDITS.getColumn(),
            new MySQLQueryBuilder(SurvivalPlayerModel.TABLE).
                order(SurvivalPlayerModel.column.CREDITS.getColumn(), Order.DESC).
                limit(10)
        ) {
            @Override
            public String getValue(OfflinePlayer player, int count) {
                return "§2§l" + NumberUtils.locale(count) + " " + (count == 1 ? "Credit" : "Credits");
            }
        };
    }
}
