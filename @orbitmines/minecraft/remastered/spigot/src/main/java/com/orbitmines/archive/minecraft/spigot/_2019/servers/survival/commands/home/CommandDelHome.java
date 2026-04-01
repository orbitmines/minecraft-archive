package com.orbitmines.archive.minecraft.spigot._2019.servers.survival.commands.home;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft._2019.libs.Server;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.Command;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.executors.Executor1;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.Survival;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.commands.argument.HomeArgument;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.database.models.Home;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.player.SurvivalPlayer;

public class CommandDelHome extends Command<Survival, SurvivalPlayer> {

    public CommandDelHome(Survival plugin) {
        super(plugin, Server.SURVIVAL, "delhome", "deletehome", "delh");

        withArg(
            new HomeArgument().executes((Executor1<Survival, SurvivalPlayer,
                Home, HomeArgument>
            ) (player, home) -> {
                home.delete();
                player.getHomes(false).remove(home);

                player.sendMessage("Home", Color.SUCCESS, "survival", "player.command.del_home.successful", "§6" + home.getName() + "§7");
            })
        );
    }

    @Override
    public String getDescription(SurvivalPlayer player) {
        return player.translate("survival", "player.command.del_home.description");
    }
}
