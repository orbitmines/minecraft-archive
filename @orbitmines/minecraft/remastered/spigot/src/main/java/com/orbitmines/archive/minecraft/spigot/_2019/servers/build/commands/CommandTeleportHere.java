package com.orbitmines.archive.minecraft.spigot._2019.servers.build.commands;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft._2019.libs.Server;
import com.orbitmines.archive.minecraft._2019.libs.player.Name;
import com.orbitmines.archive.minecraft._2019.libs.rank.StaffRank;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.arguments.PlayerArgument;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.Command;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.executors.Executor1;
import com.orbitmines.archive.minecraft.spigot._2019.servers.build.Build;
import com.orbitmines.archive.minecraft.spigot._2019.servers.build.BuildPlayer;

public class CommandTeleportHere extends Command<Build, BuildPlayer> {

    public CommandTeleportHere(Build plugin) {
        super(plugin, Server.BUILD, "tphere", "tph", "teleporthere");

        withArg(
            new PlayerArgument<Build, BuildPlayer>(plugin, false).executes((Executor1<Build, BuildPlayer,
                BuildPlayer, PlayerArgument<Build, BuildPlayer>>
            ) (player, target) -> {
                plugin.runSync(() -> {
                    target.teleport(player.getLocation());
                    player.sendMessage("Teleporter", Color.LIME, "build", "player.command.tphere.player", target.getName(Name.RAW_COLORED) + "§7");
                    target.sendMessage("Teleporter", Color.LIME, "build", "player.command.tphere.teleported", player.getName(Name.RAW_COLORED) + "§7");
                });
            })
        );

        requires(StaffRank.PROVISIONAL_BUILDER);
    }

    @Override
    public String getDescription(BuildPlayer player) {
        return player.translate("build", "player.command.tphere.description");
    }
}
