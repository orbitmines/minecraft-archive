package com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.leaderboard.instantiators;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.database.models.vote.MonthlyVotes;
import com.orbitmines.archive.minecraft._2019.libs.player.OfflinePlayer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.leaderboard.LeaderBoard;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.leaderboard.hologram.DefaultHologramLeaderBoard;
import com.orbitmines.archive.minecraft._2019.utils.DateUtils;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.builder.mysql.MySQLQueryBuilder;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.operators.mysql.Order;
import org.bukkit.Location;

public class LeaderBoardTopVoters extends LeaderBoard.Instantiator {

    public LeaderBoardTopVoters() {
        super("TOP_VOTERS");
    }

    @Override
    public LeaderBoard instantiate(Location location, String[] data) {
        return new DefaultHologramLeaderBoard(
            location,
            0,
            () -> "§7§lTop Voters of " + DateUtils.humanFriendlyMonth() + " " + DateUtils.getYear(),
            5,
            MonthlyVotes.TABLE,
            MonthlyVotes.column.UUID.getColumn(),
            MonthlyVotes.column.VOTES.getColumn(),
            new MySQLQueryBuilder(MonthlyVotes.TABLE).
                where(MonthlyVotes.column.MONTH.getColumn(), DateUtils.getMonth()).
                where(MonthlyVotes.column.YEAR.getColumn(), DateUtils.getYear()).
                order(MonthlyVotes.column.VOTES.getColumn(), Order.DESC).
                limit(5)
        ) {
            @Override
            public String getValue(OfflinePlayer player, int count) {
                return "§9§l" + count + " " + (count == 1 ? "Vote" : "Votes");
            }
        };
    }
}
