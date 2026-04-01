package com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.scoreboard;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.scoreboard.DefaultScoreboard;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.KitPvP;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.KitPvPPlayer;
import com.orbitmines.archive.minecraft._2019.utils.NumberUtils;

public class KitPvPScoreboard extends DefaultScoreboard<KitPvP, KitPvPPlayer> {

    public KitPvPScoreboard(KitPvP server, KitPvPPlayer player) {
        super(server, player,
                () -> server.getScoreboardAnimation().get(),
                () -> "§m--------------",
                () -> "",
                () -> "§7§lKit",
                () -> " " + (player.getSelectedKit() != null ? player.getSelectedKit().getHandler().getDisplayName() + " §a§lLvl " + player.getSelectedKit().getLevel() : "§fNone"),
                () -> " ",
                () -> "§6§lCoins",
                () -> " " + NumberUtils.locale(player.getCoins()),
                () -> "  ",
                () -> "§c§lKills",
                () -> " " + NumberUtils.locale(player.getKills()) + " ",
                () -> "   ",
                () -> "§4§lDeaths",
                () -> " " + NumberUtils.locale(player.getDeaths()) + "  ",
                () -> "    "
        );
    }

    @Override
    public boolean canBypassSettings() {
        return false;
    }
}
