package com.orbitmines.archive.minecraft.spigot._2019.servers.survival.scoreboard;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.scoreboard.DefaultScoreboard;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.Survival;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.player.SurvivalPlayer;
import com.orbitmines.archive.minecraft._2019.utils.NumberUtils;

public class SurvivalScoreboard extends DefaultScoreboard {

    public SurvivalScoreboard(Survival server, SurvivalPlayer player) {
        super(server, player,
            () -> server.getScoreboardAnimation().get(),
            () -> "§m--------------",
            () -> "",
            () -> "§2§lCredits",
            () -> " " + NumberUtils.locale(player.getCredits()),
            () -> " ",
            () -> "§9§lClaimblocks",
            () -> " " + NumberUtils.locale(player.getRemainingClaimBlocks()) + " ",
            () -> "  ",
            () -> "§6§lBack Charges",
            () -> " " + NumberUtils.locale(player.getBackCharges()) + "  ",
            () -> "   "
        );
    }

    @Override
    public boolean canBypassSettings() {
        return false;
    }
}
