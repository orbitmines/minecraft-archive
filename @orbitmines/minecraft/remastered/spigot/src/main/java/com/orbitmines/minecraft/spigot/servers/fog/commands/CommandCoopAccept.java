package com.orbitmines.minecraft.spigot.servers.fog.commands;

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft._2019.libs.Server;
import com.orbitmines.archive.minecraft._2019.libs.jedis.OnlinePlayer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.arguments.GlobalPlayerArgument;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.Command;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.executors.Executor1;
import com.orbitmines.minecraft.spigot.servers.fog.FoG;
import com.orbitmines.minecraft.spigot.servers.fog.FoGPlayer;

import java.util.Map;

/** {@code /coopaccept <requester>} — accept a pending coop-join request. */
public class CommandCoopAccept extends Command<FoG, FoGPlayer> {

    public CommandCoopAccept(FoG server) {
        super(server, Server.FOG, "coopaccept");

        withArg(new GlobalPlayerArgument<FoG, FoGPlayer>(false).executes(
            (Executor1<FoG, FoGPlayer, OnlinePlayer, GlobalPlayerArgument<FoG, FoGPlayer>>) (host, requester) -> {
                Map<String, Long> reqs = host.getCoopRequestsFrom();
                Long runId = reqs.remove(requester.getRawName());
                if (runId == null) {
                    host.sendMessage("Coop", Color.RED, "fog", "coop.request.none", requester.getRawName());
                    return;
                }
                host.sendMessage("Coop", Color.LIME, "fog", "coop.request.accepted", requester.getRawName());

                /* Look up the requester as a FoGPlayer by UUID and join them into the run. */
                FoGPlayer requesterPlayer = server.getPlayer(org.bukkit.Bukkit.getPlayer(requester.getUUID()));
                if (requesterPlayer == null) {
                    host.sendMessage("Coop", Color.RED, "fog", "coop.request.requester_offline", requester.getRawName());
                    return;
                }
                requesterPlayer.sendMessage("Coop", Color.LIME, "fog", "coop.request.you_were_accepted", host.getRawName());
                server.getRunManager().joinRun(requesterPlayer, runId);
            }
        ));
    }

    @Override
    public String getDescription(FoGPlayer player) {
        return player.translate("fog", "player.command.coopaccept.description");
    }
}
