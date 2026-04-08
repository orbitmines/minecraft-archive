package com.orbitmines.archive.minecraft.spigot._2019.servers.creative.commands;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.Server;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.Command;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.executors.Executor0;
import com.orbitmines.archive.minecraft.spigot._2019.servers.creative.Creative;
import com.orbitmines.archive.minecraft.spigot._2019.servers.creative.CreativePlayer;

public class CommandSpawn extends Command<Creative, CreativePlayer> {

    public CommandSpawn(Creative plugin) {
        super(plugin, Server.CREATIVE, "spawn");

        executes((Executor0<Creative, CreativePlayer>) (player) -> {
            plugin.runSync(() -> plugin.teleportToPlayerLobby(player, player::giveSpawnInventory));
        });
    }

    @Override
    public String getDescription(CreativePlayer player) {
        return player.translate("creative", "player.command.spawn.description");
    }
}
