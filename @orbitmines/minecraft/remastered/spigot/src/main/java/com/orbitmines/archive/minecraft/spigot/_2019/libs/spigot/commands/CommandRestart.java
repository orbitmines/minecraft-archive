package com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.rank.StaffRank;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMServer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.Command;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.executors.Executor0;
import com.orbitmines.archive.minecraft._2019.utils.DateUtils;

public class CommandRestart<S extends OMServer<S, P>, P extends OMPlayer<S, P>> extends Command<S, P> {

    public CommandRestart(S plugin) {
        super(plugin, "restartserver");

        executes((Executor0<S, P>) player -> {
            if (!player.isEligible(StaffRank.DEVELOPER))
               return;

            plugin.runSync(() -> {
                plugin.restart("Manual restart by " + player.getRawName() + ", " + DateUtils.format(DateUtils.now(), DateUtils.DATE_TIME_FORMAT), true);
            });
        });

        requires(StaffRank.DEVELOPER);
    }

    @Override
    public String getDescription(P player) {
        return player.translate("spigot", "player.command.restart.description");
    }
}
