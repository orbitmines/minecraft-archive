package com.orbitmines.archive.minecraft.spigot._2019.servers.creative.commands;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft._2019.libs.Server;
import com.orbitmines.archive.minecraft._2019.utils.DateUtils;
import com.orbitmines.archive.minecraft._2019.utils.state.StateProvider;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.Command;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.executors.Executor0;
import com.orbitmines.archive.minecraft.spigot._2019.servers.creative.Creative;
import com.orbitmines.archive.minecraft.spigot._2019.servers.creative.CreativePlayer;
import com.orbitmines.archive.minecraft.spigot._2019.servers.creative.CreativeWorld;

public class CommandSave extends Command<Creative, CreativePlayer> {

    public CommandSave(Creative plugin) {
        super(plugin, Server.CREATIVE, "save");

        executes((Executor0<Creative, CreativePlayer>) (player) -> {
            plugin.runSync(() -> {
                CreativeWorld world = plugin.getWorldByFileName(player.getWorld().getName());

                if (world == null) {
                    player.sendMessage("Creative", Color.RED, "creative", "player.command.save.not_in_world");
                    return;
                }

                if (!world.canBuild(player.getUUID()) && !player.isOpMode()) {
                    player.sendMessage("Creative", Color.RED, "creative", "player.world.protection.cant_build", world.getOwnerName());
                    return;
                }

                player.getWorld().save();

                player.sendMessage("Creative", Color.INFO, "creative", "player.command.save.saved", world.getName());

                String worldFileName = world.getWorldFileName();
                String now = DateUtils.DATE_TIME_FORMAT.format(DateUtils.now());
                plugin.runAsync(() -> {
                    StateProvider.getInstance().setString("world:" + worldFileName + ":last_edit", now);
                });
            });
        });
    }

    @Override
    public String getDescription(CreativePlayer player) {
        return player.translate("creative", "player.command.save.description");
    }
}
