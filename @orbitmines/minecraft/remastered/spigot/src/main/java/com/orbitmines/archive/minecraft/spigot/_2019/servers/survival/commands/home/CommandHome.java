package com.orbitmines.archive.minecraft.spigot._2019.servers.survival.commands.home;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft._2019.libs.Server;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.Command;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.executors.Executor0;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.executors.Executor1;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.database.models.survival.SurvivalHome;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.Survival;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.commands.argument.HomeArgument;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.database.models.Home;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.player.SurvivalPlayer;
import com.orbitmines.archive.minecraft._2019.utils.DateUtils;

public class CommandHome extends Command<Survival, SurvivalPlayer> {

    public CommandHome(Survival plugin) {
        super(plugin, Server.SURVIVAL, "home", "h");

        executes((Executor0<Survival, SurvivalPlayer>) (player) -> {
            if (player.getHomes(false).size() == 0) {
                player.sendMessage("Home", Color.ERROR, "survival", "player.command.home.none");
                return;
            }

            Home home = player.getHomes(false).get(0);
            home.teleport(player);

            home.addTimesUsed();
            home.setLastUsageOn(DateUtils.now());
            home.update(SurvivalHome.column.TIMES_USED, SurvivalHome.column.LAST_USAGE_ON);
        }).withArg(
            new HomeArgument().executes((Executor1<Survival, SurvivalPlayer,
                Home, HomeArgument>
            ) (player, home) -> {
                home.teleport(player);

                home.addTimesUsed();
                home.setLastUsageOn(DateUtils.now());
                home.update(SurvivalHome.column.TIMES_USED, SurvivalHome.column.LAST_USAGE_ON);
            })
        );
    }

    @Override
    public String getDescription(SurvivalPlayer player) {
        return player.translate("survival", "player.command.home.description");
    }
}
