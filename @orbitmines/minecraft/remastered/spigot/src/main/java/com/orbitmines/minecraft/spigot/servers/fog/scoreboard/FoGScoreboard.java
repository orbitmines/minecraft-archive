package com.orbitmines.minecraft.spigot.servers.fog.scoreboard;

import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.scoreboard.DefaultScoreboard;
import com.orbitmines.minecraft.spigot.servers.fog.FoG;
import com.orbitmines.minecraft.spigot.servers.fog.FoGPlayer;
import com.orbitmines.minecraft.spigot.servers.fog.run.Run;

public class FoGScoreboard extends DefaultScoreboard<FoG, FoGPlayer> {

    public FoGScoreboard(FoG server, FoGPlayer player) {
        super(server, player,
            () -> server.getScoreboardAnimation().get(),
            () -> "§m--------------",
            () -> "",
            () -> "§e§l" + player.translate("fog", "scoreboard.level"),
            () -> " §f" + player.getLevel(),
            () -> " ",
            () -> "§6§l" + player.translate("fog", "scoreboard.run"),
            () -> {
                Run run = player.getActiveRun();
                if (run == null) return " §7-";
                return " §f#" + run.getId() + " §7(" + run.getDifficulty().getColoredName(player) + ")";
            },
            () -> "  ",
            () -> "§b§l" + player.translate("fog", "scoreboard.drones"),
            () -> " §f" + player.getDrones().size() + "§7/9",
            () -> "   "
        );
    }

    @Override
    public boolean canBypassSettings() {
        return false;
    }
}
