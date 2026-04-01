package com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMServer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.Command;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.executors.Executor0;
import com.orbitmines.archive.minecraft._2019.utils.NumberUtils;

public class CommandSolars<S extends OMServer<S, P>, P extends OMPlayer<S, P>> extends Command<S, P> {

    public CommandSolars(S plugin) {
        super(plugin, "solars", "solar");

        executes((Executor0<S, P>) player -> {
            String solars = "§e§l" + NumberUtils.locale(player.getSolars()) + " " + (player.getSolars() == 1 ? " Solar " : "Solars") + "§7";
            player.sendMessage("Solars", Color.INFO, "spigot", "player.command.solars.message", solars);
        });
    }

    @Override
    public String getDescription(P player) {
        return player.translate("spigot", "player.command.solars.description");
    }
}
