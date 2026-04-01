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

public class CommandPrisms<S extends OMServer<S, P>, P extends OMPlayer<S, P>> extends Command<S, P> {

    public CommandPrisms(S plugin) {
        super(plugin, "prisms", "prism");

        executes((Executor0<S, P>) player -> {
            String prisms = "§9§l" + NumberUtils.locale(player.getPrisms()) + " " + (player.getPrisms() == 1 ? " Prism " : "Prisms") + "§7";
            player.sendMessage("Prisms", Color.INFO, "spigot", "player.command.prisms.message", prisms);
        });
    }

    @Override
    public String getDescription(P player) {
        return player.translate("spigot", "player.command.prisms.description");
    }
}
