package com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.executors;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.mojang.brigadier.context.CommandContext;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMServer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.Command;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.Executor;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.builder.ArgumentBuilder;

@FunctionalInterface
public interface Executor0
    <
        S extends OMServer<S, P>,
        P extends OMPlayer<S, P>
    >
extends Executor<S, P> {

    void onExecute(P player);

    @Override
    default void onExecute(S plugin, P player, Command command, ArgumentBuilder lastArg, CommandContext context) {
        onExecute(player);
    }
}
