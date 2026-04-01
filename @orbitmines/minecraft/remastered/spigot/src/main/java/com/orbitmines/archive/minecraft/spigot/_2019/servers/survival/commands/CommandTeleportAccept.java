package com.orbitmines.archive.minecraft.spigot._2019.servers.survival.commands;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft._2019.libs.Server;
import com.orbitmines.archive.minecraft._2019.libs.player.Name;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.arguments.PlayerArgument;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.Command;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.executors.Executor1;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.database.models.survival.SurvivalPlayerModel;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.Survival;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.player.SurvivalPlayer;
import org.bukkit.Sound;

public class CommandTeleportAccept extends Command<Survival, SurvivalPlayer> {

    public CommandTeleportAccept(Survival plugin) {
        super(plugin, Server.SURVIVAL, "accept");


        withArg(
            new PlayerArgument<Survival, SurvivalPlayer>(plugin, false).executes((Executor1<Survival, SurvivalPlayer,
                SurvivalPlayer, PlayerArgument<Survival, SurvivalPlayer>>
            ) (player, target) -> {
                if (player.hasTpHereRequestFrom(target.getRawName())) {
                    player.setBackLocation(player.getLocation());
                    player.update(SurvivalPlayerModel.column.BACK_LOCATION);
                    
                    plugin.runSync(() -> {
                        player.teleport(target.getLocation());
                        player.playSound(Sound.ENTITY_ENDERMAN_TELEPORT);
                    });

                    player.sendMessage("Teleport", Color.LIME, "survival", "player.command.accept.tphere.accepted", target.getName(Name.RAW_COLORED) + "§7");
                    target.sendMessage("Teleport", Color.LIME, "survival", "player.command.accept.tp.accepted", player.getName(Name.RAW_COLORED) + "§7");

                    player.getTpHereRequests().remove(target.getRawName());
                } else if (player.hasTpRequestFrom(target.getRawName())) {
                    target.setBackLocation(target.getLocation());
                    target.update(SurvivalPlayerModel.column.BACK_LOCATION);

                    plugin.runSync(() -> {
                        target.teleport(player.getLocation());
                        target.playSound(Sound.ENTITY_ENDERMAN_TELEPORT);
                    });

                    player.sendMessage("Teleport", Color.LIME, "survival", "player.command.accept.tp.accepted", target.getName(Name.RAW_COLORED) + "§7");
                    target.sendMessage("Teleport", Color.LIME, "survival", "player.command.accept.tphere.accepted", player.getName(Name.RAW_COLORED) + "§7");

                    player.getTpRequests().remove(target.getRawName());
                } else {
                    player.sendMessage("Teleport", Color.ERROR, "survival", "player.command.accept.non_existant", target.getName(Name.RAW_COLORED) + "§7");
                }
            })
        );

    }

    @Override
    public String getDescription(SurvivalPlayer player) {
        return player.translate("survival", "player.command.accept.description");
    }
}
