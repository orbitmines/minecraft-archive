package com.orbitmines.archive.minecraft.spigot._2019.servers.creative.commands;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft._2019.libs.Server;
import com.orbitmines.archive.minecraft._2019.libs.database.models.PlayerModel;
import com.orbitmines.archive.minecraft._2019.libs.player.Name;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.arguments.PlayerModelArgument;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.Command;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.executors.Executor0;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.executors.Executor1;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.executors.Executor2;
import com.orbitmines.archive.minecraft.spigot._2019.servers.creative.Creative;
import com.orbitmines.archive.minecraft.spigot._2019.servers.creative.CreativePlayer;
import com.orbitmines.archive.minecraft.spigot._2019.servers.creative.CreativeWorld;
import com.orbitmines.archive.minecraft.spigot._2019.servers.creative.commands.arguments.WorldNameArgument;
import com.orbitmines.archive.minecraft.spigot._2019.servers.creative.gui.WorldEditorGUI;
import com.orbitmines.archive.minecraft.spigot._2019.servers.creative.gui.WorldListGUI;

import java.util.List;

public class CommandPlot extends Command<Creative, CreativePlayer> {

    public CommandPlot(Creative plugin) {
        super(plugin, Server.CREATIVE, "plot", "world", "worlds");

        /* /plot — opens current world editor if in a world, otherwise world list GUI */
        executes((Executor0<Creative, CreativePlayer>) (player) -> {
            plugin.runSync(() -> {
                CreativeWorld current = plugin.getWorldByBukkitWorld(player.getWorld());
                if (current != null) {
                    new WorldEditorGUI(plugin, current, player).open();
                } else {
                    player.openWorldManager();
                }
            });
        });

        /* /plot <player> — open a player's world list */
        withArg(
            new PlayerModelArgument<Creative, CreativePlayer>(true).executes((Executor1<Creative, CreativePlayer,
                PlayerModel, PlayerModelArgument<Creative, CreativePlayer>>
            ) (player, model) -> {
                List<CreativeWorld> worlds = plugin.loadPlayerWorlds(model.getUUID());

                if (worlds.isEmpty()) {
                    player.sendMessage("Creative", Color.RED, "creative", "player.command.plot.no_worlds");
                    return;
                }

                String displayName = model.getName(Name.RAW_COLORED);

                plugin.runSync(() -> {
                    new WorldListGUI(plugin, player, worlds, displayName).open();
                });
            }).withArg(
                /* /plot <player> <world> — teleport to a specific world */
                new WorldNameArgument(plugin).executes((Executor2<Creative, CreativePlayer,
                    PlayerModel, PlayerModelArgument<Creative, CreativePlayer>,
                    String, WorldNameArgument>
                ) (player, model, worldName) -> {
                    List<CreativeWorld> worlds = plugin.loadPlayerWorlds(model.getUUID());

                    if (worlds.isEmpty()) {
                        player.sendMessage("Creative", Color.RED, "creative", "player.command.plot.no_worlds");
                        return;
                    }

                    CreativeWorld target = null;
                    for (CreativeWorld world : worlds) {
                        if (world.getName().equalsIgnoreCase(worldName)) {
                            target = world;
                            break;
                        }
                    }

                    if (target == null) {
                        player.sendMessage("Creative", Color.RED, "creative", "player.command.plot.world_not_found", worldName);
                        return;
                    }

                    CreativeWorld finalTarget = target;
                    plugin.runSync(() -> {
                        plugin.loadAndTeleport(player, finalTarget);
                    });
                })
            )
        );
    }

    @Override
    public String getDescription(CreativePlayer player) {
        return player.translate("creative", "player.command.plot.description");
    }
}
