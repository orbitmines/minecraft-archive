package com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft._2019.libs.rank.Rank;
import com.orbitmines.archive.minecraft._2019.libs.rank.StaffRank;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMServer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.Command;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.executors.Executor0;
import org.bukkit.GameMode;

public class CommandGamemode<S extends OMServer<S, P>, P extends OMPlayer<S, P>> extends Command<S, P> {

    private final GameMode gameMode;

    public CommandGamemode(S plugin, GameMode gameMode, String name, String... aliases) {
        this(plugin, StaffRank.NONE, gameMode, name, aliases);
    }

    public CommandGamemode(S plugin, Rank rank, GameMode gameMode, String name, String... aliases) {
        super(plugin, name, aliases);

        this.gameMode = gameMode;

        executes((Executor0<S, P>) (player) -> {
            plugin.runSync(() -> {
                player.setGameMode(gameMode);
                player.sendRawMessage("GameMode", Color.LIME, "Gamemode set to §6" + gameMode.name().toLowerCase() + "§7.");
            });
        });

        requires(rank);
    }

    @Override
    public String getDescription(P player) {
        return "Set your gamemode to " + gameMode.name().toLowerCase();
    }
}
