package com.orbitmines.archive.minecraft.spigot._2019.servers.survival.commands.region;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft._2019.libs.Server;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.arguments.IntegerArgument;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.Command;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.Namespace;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.executors.Executor0;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.executors.Executor1;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.Survival;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.gui.RegionGUI;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.player.SurvivalPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.region.Region;

public class CommandRegion extends Command<Survival, SurvivalPlayer> {

    public CommandRegion(Survival plugin) {
        super(plugin, Server.SURVIVAL, "region", "rg", "regions");

        executes((Executor0<Survival, SurvivalPlayer>) (player) -> {
            new RegionGUI(plugin, player).open();
        }).withArg(
            new IntegerArgument<Survival, SurvivalPlayer>("region_number").executes((Executor1<Survival, SurvivalPlayer,
                Integer, IntegerArgument<Survival, SurvivalPlayer>>
            ) (player, regionId) -> {
                if (regionId > 0 && regionId <= Region.TELEPORTABLE) {
                    Region region = Region.getRegion(regionId);

                    if (player.getWorld().equals(plugin.getLobby().getWorld())) {
                        plugin.runSync(() -> {
                            player.teleport(region.getLocation());
                            player.sendMessage("Teleporter", Color.LIME, "survival", "player.teleported_to", "§a" + region.getName() + "§7");
                        });
                    } else if (region == null) {
                        player.sendMessage("Region", Color.RED, "survival", "player.command.region.cant_find", "§aRegion " + regionId + "§7");
                    } else {
                        region.teleport(player);
                    }
                    return;
                }

                player.sendMessage("Region", Color.RED, "survival", "player.command.region.cant_find", "§aRegion " + regionId + "§7");
            })
        ).namespace(
            new Namespace<Survival, SurvivalPlayer>("random", player -> player.translate("survival", "player.command.namespace.region.random.description")).
                executes((Executor0<Survival, SurvivalPlayer>) (player) -> {
                    Region region = Region.randomTeleportable(player.isFirstLogin()); /* We don't want new players to be spawning in regions under water, after that it's fine. */

                    if (player.getWorld().equals(plugin.getLobby().getWorld())) {
                        plugin.runSync(() -> {
                            player.teleport(region.getLocation());
                            player.sendMessage("Teleporter", Color.LIME, "survival", "player.teleported_to", "§a" + region.getName() + "§7");
                        });
                    } else {
                        region.teleport(player);
                    }
                })
        );
    }

    @Override
    public String getDescription(SurvivalPlayer player) {
        return player.translate("survival", "player.command.region.description");
    }
}
