package com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMServer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.Command;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.executors.Executor0;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.guis.discord_squad.DiscordSquadGUI;

public class CommandDiscordSquad<S extends OMServer<S, P>, P extends OMPlayer<S, P>> extends Command<S, P> {

    public CommandDiscordSquad(S plugin) {
        super(plugin, "discordsquad", "squad");

        executes((Executor0<S, P>) player -> {
            new DiscordSquadGUI<>(player, player).open();
        });
    }

    @Override
    public String getDescription(P player) {
        return player.translate("spigot", "player.command.discord_squad.description");
    }
}
