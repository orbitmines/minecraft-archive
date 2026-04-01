package com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.executors;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.mojang.brigadier.context.CommandContext;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMServer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.Argument;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.Command;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.Executor;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.builder.ArgumentBuilder;

import java.util.ArrayList;

@FunctionalInterface
public interface Executor1
    <
        S extends OMServer<S, P>,
        P extends OMPlayer<S, P>,
        AV, A extends Argument<S, P, AV, ?>
    >
extends Executor<S, P> {

    void onExecute(P player, AV a);

    @Override
    default void onExecute(S plugin, P player, Command command, ArgumentBuilder lastArg, CommandContext context) {
        ArrayList<Argument> args = args(lastArg);

        /* Arg 1 */
        A a = getArgument(args, 0);
        AV av = getValue(context, a, player);
        if (a != null && !a.isValid(av))
            return;

        onExecute(player, av);
    }
}
