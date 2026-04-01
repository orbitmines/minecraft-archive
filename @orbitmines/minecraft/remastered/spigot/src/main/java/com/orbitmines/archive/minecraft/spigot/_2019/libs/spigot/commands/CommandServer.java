package com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft._2019.libs.Server;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMServer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.arguments.ServerArgument;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.Command;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.executors.Executor0;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.executors.Executor1;

public class CommandServer<S extends OMServer<S, P>, P extends OMPlayer<S, P>> extends Command<S, P> {

    public CommandServer(S plugin) {
        super(plugin, "server");

        withArg(
            new ServerArgument<S, P>().executes((Executor1<S, P,
                Server, ServerArgument<S, P>>
            ) (player, server) -> {
                player.connect(server, true);
            })
        ).executes((Executor0<S, P>) player -> {
            player.sendMessage("Server", Color.INFO, "spigot", "player.command.server.connected_to", player.server().getType().getDisplayName() + "§7");
        });
    }

    @Override
    public String getDescription(P player) {
        return player.translate("spigot", "player.command.server.description");
    }
}
