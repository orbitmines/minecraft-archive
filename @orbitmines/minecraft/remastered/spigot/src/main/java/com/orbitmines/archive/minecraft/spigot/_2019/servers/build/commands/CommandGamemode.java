package com.orbitmines.archive.minecraft.spigot._2019.servers.build.commands;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft._2019.libs.player.Name;
import com.orbitmines.archive.minecraft._2019.libs.rank.Rank;
import com.orbitmines.archive.minecraft._2019.libs.rank.StaffRank;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMServer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.arguments.PlayerArgument;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.Command;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.executors.Executor0;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.executors.Executor1;
import org.bukkit.GameMode;

public class CommandGamemode<S extends OMServer<S, P>, P extends OMPlayer<S, P>> extends Command<S, P> {

    public CommandGamemode(S plugin, GameMode gameMode, String cmd, String... aliases) {
        this(plugin, StaffRank.NONE, gameMode, cmd, aliases);
    }

    public CommandGamemode(S plugin, Rank rank, GameMode gameMode, String cmd, String... aliases) {
        super(plugin, cmd, aliases);

        withArg(
            new PlayerArgument<S, P>(plugin, true).executes((Executor1<S, P,
                P, PlayerArgument<S, P>>
            ) (player, target) -> {
                plugin.runSync(() -> {
                    target.setGameMode(gameMode);
                    player.sendRawMessage("Skull", Color.LIME, "Set " + target.getName(Name.RAW_COLORED) + "'s§7 gamemode to " + gameMode.toString().toLowerCase());
                });
            }).requires(rank)
        );

        executes((Executor0<S, P>) (player) -> {
            plugin.runSync(() -> {
                player.setGameMode(gameMode);
                player.sendRawMessage("Skull", Color.LIME, "Set your gamemode to " + gameMode.toString().toLowerCase());
            });
        });

        requires(rank);
    }

    @Override
    public String getDescription(P player) {
        return player.translate("build", "player.command.gamemode.description");
    }
}
