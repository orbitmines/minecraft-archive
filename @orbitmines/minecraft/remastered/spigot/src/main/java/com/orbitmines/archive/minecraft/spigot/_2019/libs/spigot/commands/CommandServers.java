package com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMServer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.Command;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.executors.Executor0;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.guis.ServerSelectorGUI;

public class CommandServers<S extends OMServer<S, P>, P extends OMPlayer<S, P>> extends Command<S, P> {

    public CommandServers(S plugin) {
        super(plugin, "servers", "serverselector");

        executes((Executor0<S, P>) player -> {
            new ServerSelectorGUI<>(player).open();
        });
    }

    @Override
    public String getDescription(P player) {
        return player.translate("spigot", "player.command.servers.description");
    }
}
