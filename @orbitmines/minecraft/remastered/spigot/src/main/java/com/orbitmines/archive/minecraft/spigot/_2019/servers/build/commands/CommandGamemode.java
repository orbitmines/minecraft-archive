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
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.executors.Executor0;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.executors.Executor1;
import com.orbitmines.archive.minecraft.spigot._2019.servers.build.Build;
import com.orbitmines.archive.minecraft.spigot._2019.servers.build.BuildPlayer;
import org.bukkit.GameMode;

public class CommandGamemode extends Command<Build, BuildPlayer> {

    public CommandGamemode(Build plugin, GameMode gameMode, String cmd, String... aliases) {
        super(plugin, Server.BUILD, cmd, aliases);

        withArg(
            new PlayerArgument<Build, BuildPlayer>(plugin, true).executes((Executor1<Build, BuildPlayer,
                BuildPlayer, PlayerArgument<Build, BuildPlayer>>
            ) (player, target) -> {
                plugin.runSync(() -> {
                    target.setGameMode(gameMode);
                    player.sendRawMessage("Skull", Color.LIME, "Set " + target.getName(Name.RAW_COLORED) + "'s§7 gamemode to " + gameMode.toString().toLowerCase());
                });
            }).requires(StaffRank.BUILDER)
        );

        executes((Executor0<Build, BuildPlayer>) (player) -> {
            plugin.runSync(() -> {
                player.setGameMode(gameMode);
                player.sendRawMessage("Skull", Color.LIME, "Set your gamemode to " + gameMode.toString().toLowerCase());
            });
        });

        requires(StaffRank.PROVISIONAL_BUILDER);
    }

    @Override
    public String getDescription(BuildPlayer player) {
        return player.translate("build", "player.command.gamemode.description");
    }
}
