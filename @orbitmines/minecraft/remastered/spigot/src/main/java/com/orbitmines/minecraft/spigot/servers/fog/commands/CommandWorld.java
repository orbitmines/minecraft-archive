package com.orbitmines.minecraft.spigot.servers.fog.commands;

import com.orbitmines.archive.minecraft._2019.libs.Server;
import com.orbitmines.archive.minecraft._2019.libs.jedis.OnlinePlayer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.arguments.GlobalPlayerArgument;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.Command;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.executors.Executor0;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.executors.Executor1;
import com.orbitmines.minecraft.spigot.servers.fog.FoG;
import com.orbitmines.minecraft.spigot.servers.fog.FoGPlayer;
import com.orbitmines.minecraft.spigot.servers.fog.gui.SpectatorJoinGUI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/** Spectate another player's active run: `/world [PLAYER]`. */
public class CommandWorld extends Command<FoG, FoGPlayer> {

    public CommandWorld(FoG server) {
        super(server, Server.FOG, "world");

        executes((Executor0<FoG, FoGPlayer>) viewer -> SpectatorJoinGUI.open(server, viewer));

        withArg(new GlobalPlayerArgument<FoG, FoGPlayer>(true).executes(
            (Executor1<FoG, FoGPlayer, OnlinePlayer, GlobalPlayerArgument<FoG, FoGPlayer>>) (viewer, target) -> {
                Player bukkit = Bukkit.getPlayer(target.getUUID());
                if (bukkit == null) {
                    viewer.sendMessage("FoG", com.orbitmines.archive.minecraft._2019.libs.Color.RED, "fog", "command.world.not_online", target.getRawName());
                    return;
                }
                FoGPlayer targetPlayer = server.getPlayer(bukkit);
                if (targetPlayer == null || targetPlayer.getActiveRun() == null) {
                    viewer.sendMessage("FoG", com.orbitmines.archive.minecraft._2019.libs.Color.RED, "fog", "command.world.not_in_run", target.getRawName());
                    return;
                }
                server.getRunManager().spectateRun(viewer, targetPlayer.getActiveRun().getId());
            })
        );
    }

    @Override
    public String getDescription(FoGPlayer player) {
        return player.translate("fog", "player.command.world.description");
    }
}
