package com.orbitmines.archive.minecraft.spigot._2019.servers.survival.scoreboard;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.scoreboard.DefaultScoreboard;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.Survival;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.player.SurvivalPlayer;
import com.orbitmines.archive.minecraft._2019.utils.NumberUtils;

public class ChestShopScoreboard extends DefaultScoreboard {

    public ChestShopScoreboard(Survival survival, SurvivalPlayer player) {
        super(survival, player,
            () -> survival.getScoreboardAnimation().get(),
            () -> "§m--------------",
            () -> "§2§lCredits",
            () -> " " + NumberUtils.locale(player.getCredits()),
            () -> "",
            () -> "§a§lSHOP SETUP",
            () -> "§7[shop]",
            () -> " ",
            () -> "§a§l" + player.translate("spigot", "player.mouse.right_click").toUpperCase(),
            () -> "§7Open Shop GUI.",
            () -> "  "
        );
    }

    @Override
    public boolean canBypassSettings() {
        return true;
    }
}
