package com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMServer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.Command;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.executors.Executor0;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.guis.LootGUI;

public class CommandLoot<S extends OMServer<S, P>, P extends OMPlayer<S, P>> extends Command<S, P> {

    public CommandLoot(S plugin) {
        super(plugin, "loot", "spaceturtle");

        executes((Executor0<S, P>) player -> {
            new LootGUI<>(player, player).open();
        });
    }

    @Override
    public String getDescription(P player) {
        return player.translate("spigot", "player.command.loot.description");
    }
}
