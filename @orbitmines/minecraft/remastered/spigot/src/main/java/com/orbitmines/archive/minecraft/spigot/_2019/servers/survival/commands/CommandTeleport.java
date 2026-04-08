package com.orbitmines.archive.minecraft.spigot._2019.servers.survival.commands;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.rank.VipRank;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.Survival;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.player.SurvivalPlayer;

public class CommandTeleport extends com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.CommandTeleport<Survival, SurvivalPlayer> {

    public CommandTeleport(Survival plugin) {
        super(plugin, VipRank.GOLD, "accept");
    }
}
