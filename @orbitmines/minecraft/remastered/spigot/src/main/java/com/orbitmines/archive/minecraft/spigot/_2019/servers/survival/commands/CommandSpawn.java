package com.orbitmines.archive.minecraft.spigot._2019.servers.survival.commands;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.Server;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.Command;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.executors.Executor0;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.Survival;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.player.SurvivalPlayer;

public class CommandSpawn extends Command<Survival, SurvivalPlayer> {

    public CommandSpawn(Survival plugin) {
        super(plugin, Server.SURVIVAL, "spawn");

        executes((Executor0<Survival, SurvivalPlayer>) (player) -> {
            if (player.getLobbyPreference() != null) {
                plugin.runSync(() -> plugin.teleportToPlayerLobby(player, null));
            } else if (player.getWorld().equals(plugin.getLobby().getWorld())) {
                plugin.runSync(() -> player.teleport(plugin.getSpawn()));
            } else {
                plugin.getSpawnTeleportable().teleport(player);
            }
        });
    }

    @Override
    public String getDescription(SurvivalPlayer player) {
        return player.translate("survival", "player.command.pet.description");
    }
}
