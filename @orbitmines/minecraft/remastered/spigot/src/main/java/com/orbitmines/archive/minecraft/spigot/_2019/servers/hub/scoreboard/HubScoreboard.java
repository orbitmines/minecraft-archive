package com.orbitmines.archive.minecraft.spigot._2019.servers.hub.scoreboard;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.scoreboard.DefaultScoreboard;
import com.orbitmines.archive.minecraft.spigot._2019.servers.hub.Hub;
import com.orbitmines.archive.minecraft.spigot._2019.servers.hub.player.HubPlayer;
import com.orbitmines.archive.minecraft._2019.utils.NumberUtils;

public class HubScoreboard extends DefaultScoreboard<Hub, HubPlayer> {

    public HubScoreboard(Hub server, HubPlayer player) {
        super(server, player,
                () -> server.getScoreboardAnimation().get(),
                () -> "§m--------------",
                () -> "",
                () -> "§9§lPrisms",
                () -> " " + NumberUtils.locale(player.getPrisms()),
                () -> " ",
                () -> "§e§lSolars",
                () -> " " + NumberUtils.locale(player.getSolars()) + " ",
                () -> "  ",
                () -> "§c§lRank",
                () -> " " + player.getRank().getName(),
                () -> "   "
        );
    }

    @Override
    public boolean canBypassSettings() {
        return false;
    }
}
