package com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.commands;

/*
 * OrbitMines - @author Fadi Shawki - 2015
 */

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft._2019.libs.Server;
import com.orbitmines.archive.minecraft._2019.libs.rank.StaffRank;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.Command;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.executors.Executor0;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.KitPvP;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.KitPvPPlayer;

public class CommandFreeKitSaturday extends Command<KitPvP, KitPvPPlayer> {

    public CommandFreeKitSaturday(KitPvP plugin) {
        super(plugin, Server.KITPVP, "freekitsaturday", "fks");

        executes((Executor0<KitPvP, KitPvPPlayer>) (player) -> {
            plugin.setFreeKitSaturday(!plugin.isFreeKitSaturday());

            String state = plugin.isFreeKitSaturday() ? "§a§lENABLED" : "§c§lDISABLED";
            player.sendRawMessage("KitPvP", Color.LIME, "Free Kit Saturday has been " + state + "§7.");
        });

        requires(StaffRank.MODERATOR);
    }

    @Override
    public String getDescription(KitPvPPlayer player) {
        return "Toggle Free Kit Saturday on or off";
    }
}
