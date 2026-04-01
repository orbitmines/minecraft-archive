package com.orbitmines.archive.minecraft.spigot._2019.servers.build.commands;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft._2019.libs.Server;
import com.orbitmines.archive.minecraft._2019.libs.rank.StaffRank;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.arguments.IntegerArgument;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.Command;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.executors.Executor1;
import com.orbitmines.archive.minecraft.spigot._2019.servers.build.Build;
import com.orbitmines.archive.minecraft.spigot._2019.servers.build.BuildPlayer;

public class CommandSpeed extends Command<Build, BuildPlayer> {

    public CommandSpeed(Build plugin) {
        super(plugin, Server.BUILD, "speed");

        withArg(
            new IntegerArgument<Build, BuildPlayer>("speed").executes((Executor1<Build, BuildPlayer,
                Integer, IntegerArgument<Build, BuildPlayer>>
            ) (player, speed) -> {
                float actualSpeed = speed * 0.1F;

                plugin.runSync(() -> {
                    player.setFlySpeed(actualSpeed);
                    player.setWalkSpeed(actualSpeed);
                    player.sendRawMessage("Speed", Color.LIME, "Set flight and walk speed to §6" + speed + "§7.");
                });
            })
        );

        requires(StaffRank.PROVISIONAL_BUILDER);
    }

    @Override
    public String getDescription(BuildPlayer player) {
        return player.translate("build", "player.command.speed.description");
    }
}
