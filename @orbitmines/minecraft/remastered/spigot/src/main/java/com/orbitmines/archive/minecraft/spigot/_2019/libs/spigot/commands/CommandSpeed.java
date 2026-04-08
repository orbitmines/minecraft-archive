package com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft._2019.libs.rank.Rank;
import com.orbitmines.archive.minecraft._2019.libs.rank.StaffRank;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMServer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.arguments.IntegerArgument;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.Command;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.executors.Executor1;

public class CommandSpeed<S extends OMServer<S, P>, P extends OMPlayer<S, P>> extends Command<S, P> {

    public CommandSpeed(S plugin) {
        this(plugin, StaffRank.NONE);
    }

    public CommandSpeed(S plugin, Rank rank) {
        super(plugin, "speed");

        withArg(
            new IntegerArgument<S, P>("speed").executes((Executor1<S, P,
                Integer, IntegerArgument<S, P>>
            ) (player, speed) -> {
                if (speed < 0 || speed > 10) {
                    player.sendRawMessage("Speed", Color.ERROR, "Speed must be between §60§7 and §610§7.");
                    return;
                }

                float value = speed / 10.0f;

                plugin.runSync(() -> {
                    if (player.isFlying()) {
                        player.bukkit().setFlySpeed(value);
                    } else {
                        player.bukkit().setWalkSpeed(value);
                    }
                    player.sendRawMessage("Speed", Color.LIME, (player.isFlying() ? "Fly" : "Walk") + " speed set to §6" + speed + "§7.");
                });
            })
        );

        requires(rank);
    }

    @Override
    public String getDescription(P player) {
        return "Set your fly/walk speed";
    }
}
