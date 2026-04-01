package com.orbitmines.archive.minecraft.spigot._2019.servers.survival.commands.warp;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.Server;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.Command;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.executors.Executor0;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.executors.Executor1;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.database.models.survival.SurvivalWarp;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.Survival;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.commands.argument.WarpArgument;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.database.models.Warp;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.gui.warp.WarpGUI;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.player.SurvivalPlayer;

public class CommandWarp extends Command<Survival, SurvivalPlayer> {

    public CommandWarp(Survival plugin) {
        super(plugin, Server.SURVIVAL, "warp", "warps");

        executes((Executor0<Survival, SurvivalPlayer>) (player) -> {
            new WarpGUI(plugin, player, player).open();
        }).withArg(
            new WarpArgument(plugin).
                executes((Executor1<Survival, SurvivalPlayer,
                    Warp, WarpArgument>
                ) (player, warp) -> {
                    warp.teleport(player);
                    warp.addTimeUsed();
                    warp.update(SurvivalWarp.column.TIMES_USED);
                })
        );
    }

    @Override
    public String getDescription(SurvivalPlayer player) {
        return player.translate("survival", "player.command.warp.description");
    }
}
