package com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.commands;

/*
 * OrbitMines - @author Fadi Shawki - 2015
 */

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft._2019.libs.Server;
import com.orbitmines.archive.minecraft._2019.libs.rank.StaffRank;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.arguments.IntegerArgument;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.Command;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.executors.Executor0;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.executors.Executor1;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.KitPvP;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.KitPvPPlayer;

public class CommandFreeKitSaturday extends Command<KitPvP, KitPvPPlayer> {

    public CommandFreeKitSaturday(KitPvP plugin) {
        super(plugin, Server.KITPVP, "freekitsaturday", "fks");

        withArg(
            new IntegerArgument<KitPvP, KitPvPPlayer>("level").executes((Executor1<KitPvP, KitPvPPlayer,
                Integer, IntegerArgument<KitPvP, KitPvPPlayer>>
            ) (player, level) -> {
                if (level < 1 || level > 3) {
                    player.sendRawMessage("KitPvP", Color.ERROR, "Level must be between 1 and 3.");
                    return;
                }

                plugin.setFreeKitSaturday(true);
                plugin.setFreeKitLevel(level);

                player.sendRawMessage("KitPvP", Color.LIME, "Free Kit Saturday §a§lENABLED§7 at §6§lLevel " + level + "§7.");
            })
        );

        executes((Executor0<KitPvP, KitPvPPlayer>) (player) -> {
            plugin.setFreeKitSaturday(!plugin.isFreeKitSaturday());
            if (!plugin.isFreeKitSaturday()) {
                plugin.setFreeKitLevel(1);
            }

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
