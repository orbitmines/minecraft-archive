package com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.leaderboard.instantiators;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.database.models.PlayerModel;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.leaderboard.LeaderBoard;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.leaderboard.hologram.DefaultHologramLeaderBoard;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.builder.mysql.MySQLQueryBuilder;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.operators.mysql.Order;
import org.bukkit.Location;

public class LeaderBoardPrisms extends LeaderBoard.Instantiator {

    public LeaderBoardPrisms() {
        super("PRISMS");
    }

    @Override
    public LeaderBoard instantiate(Location location, String[] data) {
        return new DefaultHologramLeaderBoard(
            location,
            0,
            () -> "§7§lTop Prisms",
            10,
            PlayerModel.TABLE,
            PlayerModel.column.UUID.getColumn(),
            PlayerModel.column.PRISMS.getColumn(),
            new MySQLQueryBuilder(PlayerModel.TABLE).
                order(PlayerModel.column.PRISMS.getColumn(), Order.DESC).
                limit(10)
        );
    }
}
