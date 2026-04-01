package com.orbitmines.archive.minecraft.spigot._2019.servers.survival.commands;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.Server;
import com.orbitmines.archive.minecraft._2019.libs.rank.VipRank;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.Command;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.executors.Executor0;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.Survival;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.player.SurvivalPlayer;

public class CommandWorkbench extends Command<Survival, SurvivalPlayer> {

    public CommandWorkbench(Survival plugin) {
        super(plugin, Server.SURVIVAL, "workbench", "wb");

        executes((Executor0<Survival, SurvivalPlayer>) (player) -> {
            plugin.runSync(() -> player.openWorkbench(null, true));
        }).requires(VipRank.GOLD);
    }

    @Override
    public String getDescription(SurvivalPlayer player) {
        return player.translate("survival", "player.command.workbench.description");
    }
}
