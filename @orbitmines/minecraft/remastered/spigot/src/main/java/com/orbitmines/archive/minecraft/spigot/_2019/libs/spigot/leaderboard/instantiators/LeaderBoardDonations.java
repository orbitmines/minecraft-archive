package com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.leaderboard.instantiators;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.database.models.Donation;
import com.orbitmines.archive.minecraft._2019.libs.player.OfflinePlayer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.leaderboard.LeaderBoard;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.leaderboard.hologram.DefaultHologramLeaderBoard;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.builder.mysql.MySQLQueryBuilder;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.operators.mysql.Order;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.npcs.Hologram;
import org.bukkit.Location;

public class LeaderBoardDonations extends LeaderBoard.Instantiator {

    public LeaderBoardDonations() {
        super("DONATIONS");
    }

    @Override
    public LeaderBoard instantiate(Location location, String[] data) {
        return new DefaultHologramLeaderBoard(
            location,
            Hologram.Y_OFFSET_PER_LINE * 2, /* This is to match the height of Top Total Votes */
            () -> "§7§lRecent Donations",
            3,
            Donation.TABLE,
            Donation.column.UUID.getColumn(),
            Donation.column.TYPE_ID.getColumn(),
            new MySQLQueryBuilder(Donation.TABLE).
                order(Donation.column.DONATED_AT.getColumn(), Order.DESC).
                limit(3)
        ) {
            @Override
            public String getValue(OfflinePlayer player, int count) {
                return Donation.Type.getById(count).getTitle();
            }
        };
    }
}
