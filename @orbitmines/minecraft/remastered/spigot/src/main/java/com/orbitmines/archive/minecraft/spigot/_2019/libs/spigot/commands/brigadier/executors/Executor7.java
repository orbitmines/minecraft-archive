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
public interface Executor7
    <
        S extends OMServer<S, P>,
        P extends OMPlayer<S, P>,
        AV, A extends Argument<S, P, AV, ?>,
        BV, B extends Argument<S, P, BV, ?>,
        CV, C extends Argument<S, P, CV, ?>,
        DV, D extends Argument<S, P, DV, ?>,
        EV, E extends Argument<S, P, EV, ?>,
        FV, F extends Argument<S, P, FV, ?>,
        GV, G extends Argument<S, P, GV, ?>
    >
extends Executor<S, P> {

    void onExecute(P player, AV a, BV b, CV c, DV d, EV e, FV f, GV g);

    @Override
    default void onExecute(S plugin, P player, Command command, ArgumentBuilder lastArg, CommandContext context) {
        ArrayList<Argument> args = args(lastArg);

        /* Arg 1 */
        A a = getArgument(args, 0);
        AV av = getValue(context, a, player);
        if (a != null && !a.isValid(av))
            return;
        /* Arg 2 */
        B b = getArgument(args, 1);
        BV bv = getValue(context, b, player);
        if (b != null && !b.isValid(bv))
            return;
        /* Arg 3 */
        C c = getArgument(args, 2);
        CV cv = getValue(context, c, player);
        if (c != null && !c.isValid(cv))
            return;
        /* Arg 4 */
        D d = getArgument(args, 3);
        DV dv = getValue(context, d, player);
        if (d != null && !d.isValid(dv))
            return;
        /* Arg 5 */
        E e = getArgument(args, 4);
        EV ev = getValue(context, e, player);
        if (e != null && !e.isValid(ev))
            return;
        /* Arg 6 */
        F f = getArgument(args, 5);
        FV fv = getValue(context, f, player);
        if (f != null && !f.isValid(fv))
            return;
        /* Arg 7 */
        G g = getArgument(args, 6);
        GV gv = getValue(context, g, player);
        if (g != null && !g.isValid(gv))
            return;

        onExecute(player, av, bv, cv, dv, ev, fv, gv);
    }
}
