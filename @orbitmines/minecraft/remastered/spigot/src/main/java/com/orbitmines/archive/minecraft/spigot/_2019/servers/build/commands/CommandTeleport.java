package com.orbitmines.archive.minecraft.spigot._2019.servers.build.commands;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft._2019.libs.Server;
import com.orbitmines.archive.minecraft._2019.libs.player.Name;
import com.orbitmines.archive.minecraft._2019.libs.rank.StaffRank;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.arguments.IntegerArgument;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.arguments.PlayerArgument;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.Command;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.executors.Executor1;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.executors.Executor3;
import com.orbitmines.archive.minecraft.spigot._2019.servers.build.Build;
import com.orbitmines.archive.minecraft.spigot._2019.servers.build.BuildPlayer;
import org.bukkit.Location;

public class CommandTeleport extends Command<Build, BuildPlayer> {

    public CommandTeleport(Build plugin) {
        super(plugin, Server.BUILD, "tp", "teleport");

        withArg(
            new PlayerArgument<Build, BuildPlayer>(plugin, false).executes((Executor1<Build, BuildPlayer,
                BuildPlayer, PlayerArgument<Build, BuildPlayer>>
            ) (player, target) -> {
                plugin.runSync(() -> {
                    player.teleport(target.getLocation());
                    player.sendMessage("Teleporter", Color.LIME, "spigot", "player.command.tpto.to_player", target.getName(Name.RAW_COLORED) + "§7");
                });
            })
        );

        withArg(
            new IntegerArgument<Build, BuildPlayer>("x").withArg(
                new IntegerArgument<Build, BuildPlayer>("y").withArg(
                    new IntegerArgument<Build, BuildPlayer>("z").executes((Executor3<Build, BuildPlayer,
                        Integer, IntegerArgument<Build, BuildPlayer>,
                        Integer, IntegerArgument<Build, BuildPlayer>,
                        Integer, IntegerArgument<Build, BuildPlayer>>
                    ) (player, x, y, z) -> {
                        plugin.runSync(() -> {
                            player.teleport(new Location(player.getWorld(), x, y, z));
                            player.sendMessage("Teleporter", Color.LIME, "spigot", "player.command.tpto.to_location", "§6" + x + "§7", "§6" + y + "§7", "§6" + z + "§7");
                        });
                    })
                )
            )
        );

        requires(StaffRank.PROVISIONAL_BUILDER);
    }

    @Override
    public String getDescription(BuildPlayer player) {
        return player.translate("spigot", "player.command.tpto.description");
    }
}
