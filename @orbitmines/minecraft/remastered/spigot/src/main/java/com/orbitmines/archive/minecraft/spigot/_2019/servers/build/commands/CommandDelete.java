package com.orbitmines.archive.minecraft.spigot._2019.servers.build.commands;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft._2019.libs.Server;
import com.orbitmines.archive.minecraft._2019.libs.rank.StaffRank;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.Command;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.executors.Executor1;
import com.orbitmines.archive.minecraft.spigot._2019.servers.build.Build;
import com.orbitmines.archive.minecraft.spigot._2019.servers.build.BuildPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.servers.build.commands.argument.MapArgument;
import com.orbitmines.archive.minecraft.spigot._2019.servers.build.database.models.BuildMap;

public class CommandDelete extends Command<Build, BuildPlayer> {

    public CommandDelete(Build plugin) {
        super(plugin, Server.CREATIVE, "delete");

        withArg(
            new MapArgument(plugin).executes((Executor1<Build, BuildPlayer,
                BuildMap, MapArgument>
            ) (player, map) -> {
                plugin.runSync(() -> {
                    player.sendRawMessage("Map", Color.BLUE, "Deleting world...");

                    map.delete();
                    plugin.getMaps().remove(map);

                    player.sendRawMessage("Map", Color.SUCCESS, "Successfully deleted world!");
                });
            })
        );

        requires(StaffRank.BUILDER);
    }

    @Override
    public String getDescription(BuildPlayer player) {
        return player.translate("build", "player.command.delete.description");
    }
}
