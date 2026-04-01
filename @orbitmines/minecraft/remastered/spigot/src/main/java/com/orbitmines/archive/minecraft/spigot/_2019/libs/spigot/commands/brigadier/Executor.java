package com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.mojang.brigadier.context.CommandContext;
import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMServer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.builder.ArgumentBuilder;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;

@FunctionalInterface
public interface Executor<S extends OMServer<S, P>, P extends OMPlayer<S, P>> {

    void onExecute(S plugin, P player, Command command, ArgumentBuilder lastArg, CommandContext context);

    /* Retrieve a list of all ARGUMENTS passed through context */
    default ArrayList<Argument> args(ArgumentBuilder lastArg) {
        ArrayList<Argument> reversed = new ArrayList<>();

        do {
            if (lastArg instanceof Argument)
                reversed.add((Argument) lastArg);

            lastArg = lastArg.getParent();
        } while (lastArg != null && lastArg.getParent() != null);

        Collections.reverse(reversed);

        return reversed;
    }

    /* Retrieve complete list of all ARGUMENTS, NAMESPACES AND COMMAND passed through context */
    default ArrayList<ArgumentBuilder<S, P, ?>> allArgs(ArgumentBuilder<S, P, ?> lastArg) {
        ArrayList<ArgumentBuilder<S, P, ?>> reversed = new ArrayList<>();

        while (lastArg != null) {
            reversed.add(lastArg);

            lastArg = lastArg.getParent();
        }

        Collections.reverse(reversed);

        return reversed;
    }

    default <A extends ArgumentBuilder> A getArgument(ArrayList<Argument> args, int index) {
        return args.size() > index ? (A) args.get(index) : null;
    }

    default <V> V getValue(CommandContext context, Argument<S, P, V, ?> argument, P player) {
        return argument != null ? argument.getValue(player, getString(context, argument)) : null;
    }

    default String getString(CommandContext context, Argument argument) {
        return argument.getString(context);
    }

    default P getPlayer(S plugin, CommandContext context) {
        return getPlayer(plugin, context.getSource());
    }

    default P getPlayer(S plugin, Object wrapper) {
        CommandSender sender = OMServer.getInstance().getNms().brigadier().getSender(wrapper);

        if (!(sender instanceof Player))
            throw new IllegalStateException("Console is not supported (yet)!");

        return plugin.getPlayer((Player) sender);
    }

    default void onError(S plugin, P player, Command command, ArgumentBuilder lastArg, CommandContext context) {
        player.sendMessage("Commands", Color.ERROR, "spigot", "player.command.error");
    }
}
