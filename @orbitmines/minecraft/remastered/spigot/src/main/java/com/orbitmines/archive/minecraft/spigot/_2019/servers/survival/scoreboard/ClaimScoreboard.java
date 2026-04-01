package com.orbitmines.archive.minecraft.spigot._2019.servers.survival.scoreboard;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.scoreboard.DefaultScoreboard;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.Survival;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.player.SurvivalPlayer;
import com.orbitmines.archive.minecraft._2019.utils.NumberUtils;

public class ClaimScoreboard extends DefaultScoreboard {

    public ClaimScoreboard(Survival survival, SurvivalPlayer player) {
        super(survival, player,
            () -> survival.getScoreboardAnimation().get(),
            () -> "§f§m------------------------",
            () -> "§9§lClaimblocks",
            () -> " " + NumberUtils.locale(player.getRemainingClaimBlocks()),
            () -> "",
            () -> "§6§l" + player.translate("spigot", "player.mouse.left_click").toUpperCase(),
            () -> "§7" + player.translate("survival", "player.claim.scoreboard.left_click.1"),
            () -> "§7" + player.translate("survival", "player.claim.scoreboard.left_click.2"),
            () -> " ",
            () -> "§6§lSHIFT + " + player.translate("spigot", "player.mouse.left_click").toUpperCase(),
            () -> "§7" + player.translate("survival", "player.claim.scoreboard.shift_left_click"),
            () -> "  ",
            () -> {
                String color = survival.canEditOtherClaims(player) ? "§c§l§m" : "§6§l";
                return color + player.translate("spigot", "player.mouse.right_click").toUpperCase();
            },
            () -> "§7" + player.translate("survival", "player.claim.scoreboard.right_click.1"),
            () -> "§7" + player.translate("survival", "player.claim.scoreboard.right_click.2"),
            () -> "§7" + player.translate("survival", "player.claim.scoreboard.right_click.3")
        );
    }

    @Override
    public boolean canBypassSettings() {
        return true;
    }
}
