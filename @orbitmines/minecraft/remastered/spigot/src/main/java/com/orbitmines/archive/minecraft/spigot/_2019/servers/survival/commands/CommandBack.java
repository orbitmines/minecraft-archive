package com.orbitmines.archive.minecraft.spigot._2019.servers.survival.commands;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft._2019.libs.Server;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.Command;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.executors.Executor0;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.database.models.survival.SurvivalPlayerModel;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.Survival;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.player.SurvivalPlayer;
import com.orbitmines.archive.minecraft._2019.utils.NumberUtils;
import org.bukkit.Location;

public class CommandBack extends Command<Survival, SurvivalPlayer> {

    public CommandBack(Survival plugin) {
        super(plugin, Server.SURVIVAL, "back");

        executes((Executor0<Survival, SurvivalPlayer>) (player) -> {
            if (player.getBackLocation() == null) {
                player.sendMessage("Back", Color.RED, "survival", "player.command.back.none");
                return;
            }

            if (player.getBackCharges() <= 0) {
                player.sendMessage("Back", Color.RED, "survival", "player.command.back.no_charges", "§6§lBack Charges§7");
                return;
            }

            Location l = player.getLocation();
            plugin.runSync(() -> {
                player.teleport(player.getBackLocation());
                player.setBackLocation(l);
                player.removeBackCharges(1);

                player.update(SurvivalPlayerModel.column.BACK_CHARGES, SurvivalPlayerModel.column.BACK_LOCATION);

                player.sendMessage("Back", Color.LIME, "survival", "player.command.back.teleported", NumberUtils.locale(player.getBackCharges()));
            });
        });
    }

    @Override
    public String getDescription(SurvivalPlayer player) {
        return player.translate("survival", "player.command.back.description");
    }
}
