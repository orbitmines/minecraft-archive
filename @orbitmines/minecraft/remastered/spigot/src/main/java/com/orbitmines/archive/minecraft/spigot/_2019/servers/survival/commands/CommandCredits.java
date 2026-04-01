package com.orbitmines.archive.minecraft.spigot._2019.servers.survival.commands;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft._2019.libs.Server;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.Command;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.executors.Executor0;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.Survival;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.player.SurvivalPlayer;
import com.orbitmines.archive.minecraft._2019.utils.NumberUtils;

public class CommandCredits extends Command<Survival, SurvivalPlayer> {

    public CommandCredits(Survival plugin) {
        super(plugin, Server.SURVIVAL, "credits");

        executes((Executor0<Survival, SurvivalPlayer>) player -> {
            String credits = "§2§l" + NumberUtils.locale(player.getCredits()) + " " + (player.getCredits() == 1 ? " Credit " : "Credits") + "§7";
            player.sendMessage("Credits", Color.INFO, "survival", "player.command.credits.message", credits);
        });
    }

    @Override
    public String getDescription(SurvivalPlayer player) {
        return player.translate("survival", "player.command.credits.description");
    }
}
