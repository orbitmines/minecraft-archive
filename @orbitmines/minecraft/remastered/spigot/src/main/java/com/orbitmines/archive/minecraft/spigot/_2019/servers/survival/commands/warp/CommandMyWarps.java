package com.orbitmines.archive.minecraft.spigot._2019.servers.survival.commands.warp;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.Server;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.Command;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.executors.Executor0;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.Survival;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.gui.warp.WarpSlotsGUI;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.player.SurvivalPlayer;

public class CommandMyWarps extends Command<Survival, SurvivalPlayer> {

    public CommandMyWarps(Survival plugin) {
        super(plugin, Server.SURVIVAL, "mywarps");

        executes((Executor0<Survival, SurvivalPlayer>) (player) -> {
            new WarpSlotsGUI(plugin, player).open();
        });
    }

    @Override
    public String getDescription(SurvivalPlayer player) {
        return player.translate("survival", "player.command.mywarps.description");
    }
}
