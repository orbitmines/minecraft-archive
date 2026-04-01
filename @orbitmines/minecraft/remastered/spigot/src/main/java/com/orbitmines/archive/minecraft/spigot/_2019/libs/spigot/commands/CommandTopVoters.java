package com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft._2019.libs.database.models.vote.MonthlyVotes;
import com.orbitmines.archive.minecraft._2019.libs.player.OfflinePlayer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMServer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.leaderboard.cmd.DefaultCommandLeaderBoard;
import com.orbitmines.archive.minecraft._2019.utils.DateUtils;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.builder.mysql.MySQLQueryBuilder;

@Deprecated
public class CommandTopVoters<S extends OMServer<S, P>, P extends OMPlayer<S, P>> extends DefaultCommandLeaderBoard<S, P> implements CommandVoteHelper {

    public static CommandTopVoters INSTANCE;

    public CommandTopVoters(S plugin) {
        super(plugin,
            "Top Voters of " + DateUtils.humanFriendlyMonth() + " " + DateUtils.getYear(),
            Color.BLUE,
            "topvoters",
            new String[] {
                "topvoters",
                "voters"
            },
            5,
            MonthlyVotes.TABLE,
            MonthlyVotes.column.UUID.getColumn(),
            MonthlyVotes.column.VOTES.getColumn(),
            new MySQLQueryBuilder(MonthlyVotes.TABLE).
                where(MonthlyVotes.column.MONTH.getColumn(), DateUtils.getMonth()).
                where(MonthlyVotes.column.YEAR.getColumn(), DateUtils.getYear())
//                order(MonthlyVotes.column.VOTES.getColumn(), Order.DESC)
//                limit(5)
        );

        INSTANCE = this;
    }

    @Override
    public DefaultCommandLeaderBoard getLeaderBoardCommand() {
        return this;
    }

    @Override
    public String getValue(OfflinePlayer player, int count) {
        return "§9" + count + " " + (count == 1 ? "Vote" : "Votes");
    }

    @Override
    public void onDispatch(P player) {
        sendVoteRewardMessage(player);
    }
}
