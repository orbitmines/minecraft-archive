package com.orbitmines.archive.minecraft.spigot._2019.servers.build.commands;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft._2019.libs.Server;
import com.orbitmines.archive.minecraft._2019.libs.rank.StaffRank;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.arguments.MessageArgument;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.arguments.ServerArgument;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.Command;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.executors.Executor4;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.database.models.OMMap;
import com.orbitmines.archive.minecraft.spigot._2019.servers.build.Build;
import com.orbitmines.archive.minecraft.spigot._2019.servers.build.BuildPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.servers.build.commands.argument.MapTypeArgument;
import com.orbitmines.archive.minecraft.spigot._2019.servers.build.commands.argument.WorldTypeArgument;
import com.orbitmines.archive.minecraft.spigot._2019.servers.build.database.models.BuildMap;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.worlds.generators.DefaultWorldType;

public class CommandCreate extends Command<Build, BuildPlayer> {

    public CommandCreate(Build plugin) {
        super(plugin, Server.CREATIVE, "create");

        withArg(
            new ServerArgument<Build, BuildPlayer>().withArg(
                new WorldTypeArgument().withArg(
                    new MapTypeArgument().withArg(
                        new MessageArgument<Build, BuildPlayer>("name").executes((Executor4<Build, BuildPlayer,
                            Server, ServerArgument<Build, BuildPlayer>,
                            DefaultWorldType, WorldTypeArgument,
                            OMMap.Type, MapTypeArgument,
                            String, MessageArgument<Build, BuildPlayer>>
                        ) (player, server, worldType, type, name) -> {
                            if (plugin.getMap(name) != null) {
                                player.sendRawMessage("Map", Color.RED, "That map already exists.");
                                return;
                            }

                            player.sendRawMessage("Map", Color.BLUE, "Setting up world...");

                            BuildMap map = new BuildMap(plugin, player.getUUID(), server, name, worldType, type);
                            map.insert();

                            plugin.runSync(() -> {
                                player.sendRawMessage("Map", Color.BLUE, "Creating world...");

                                map.loadOrCreateWorld();
                                plugin.getMaps().add(map);

                                player.sendRawMessage("Map", Color.LIME, "World created!");

                                player.teleport(map.getWorld().getSpawnLocation());
                            });
                        })
                    )
                )
            )
        );

        requires(StaffRank.BUILDER);
    }

    @Override
    public String getDescription(BuildPlayer player) {
        return player.translate("build", "player.command.create.description");
    }
}
