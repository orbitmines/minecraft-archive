package com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.rank.VipRank;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMServer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.arguments.MessageArgument;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.Command;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.executors.Executor0;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.executors.Executor1;

public class CommandAfk<S extends OMServer<S, P>, P extends OMPlayer<S, P>> extends Command<S, P> {

    public CommandAfk(S plugin) {
        super(plugin, "afk");

        withArg(
            new MessageArgument<S, P>().executes((Executor1<S, P,
                String, MessageArgument<S, P>>
            ) (player, reason) -> {
                if (player.isAfk()) {
                    player.noLongerAfk();
                    return;
                }

                player.setAfk(reason);
            })
        ).executes((Executor0<S, P>
            ) (player) -> {
                if (player.isAfk()) {
                    player.noLongerAfk();
                    return;
                }

                player.setAfk("null");
            }
        );

        requires(VipRank.IRON);
    }

    @Override
    public String getDescription(P player) {
        return player.translate("spigot", "player.command.afk.description");
    }
}
