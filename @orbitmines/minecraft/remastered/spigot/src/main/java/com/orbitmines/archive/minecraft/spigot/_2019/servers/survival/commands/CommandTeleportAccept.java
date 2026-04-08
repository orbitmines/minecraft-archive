package com.orbitmines.archive.minecraft.spigot._2019.servers.survival.commands;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.database.models.survival.SurvivalPlayerModel;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.Survival;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.player.SurvivalPlayer;

public class CommandTeleportAccept extends com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.CommandTeleportAccept<Survival, SurvivalPlayer> {

    public CommandTeleportAccept(Survival plugin) {
        super(plugin, "accept");
    }

    @Override
    protected void beforeTeleport(SurvivalPlayer accepter, SurvivalPlayer requester, boolean isTpHere) {
        if (isTpHere) {
            accepter.setBackLocation(accepter.getLocation());
        } else {
            requester.setBackLocation(requester.getLocation());
        }
    }
}
