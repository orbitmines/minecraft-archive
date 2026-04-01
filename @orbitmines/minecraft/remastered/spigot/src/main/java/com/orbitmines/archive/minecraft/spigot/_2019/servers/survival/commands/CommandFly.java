package com.orbitmines.archive.minecraft.spigot._2019.servers.survival.commands;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft._2019.libs.Server;
import com.orbitmines.archive.minecraft._2019.libs.rank.VipRank;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.Command;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.executors.Executor0;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.database.models.survival.SurvivalClaim;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.Survival;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.database.models.Claim;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.player.SurvivalPlayer;

public class CommandFly extends Command<Survival, SurvivalPlayer> {

    public CommandFly(Survival plugin) {
        super(plugin, Server.SURVIVAL, "fly");

        executes((Executor0<Survival, SurvivalPlayer>) (player) -> {
            if (!player.isOpMode()) {
                Claim claim = plugin.getClaimHandler().getClaimAt(player.getLocation(), false, player.getLastClaim());
                if (claim != null)
                    player.setLastClaim(claim);

                if (claim == null || !claim.canAccess(player, false)) {
                    player.sendMessage("Fly", Color.RED, "survival", "player.claim.fly.only_in_claims", "§a§l" + SurvivalClaim.Permission.ACCESS.getName(player) + "§7");
                    return;
                }
            }

            plugin.runSync(() -> {
                player.setAllowFlight(!player.getAllowFlight());
                player.setFlying(player.getAllowFlight());

                if (player.isFlying())
                    player.sendMessage("Fly", Color.LIME, "survival", "player.command.fly.enabled");
                else
                    player.sendMessage("Fly", Color.RED, "survival", "player.command.fly.disabled");
            });
        });

        requires(VipRank.DIAMOND);
    }

    @Override
    public String getDescription(SurvivalPlayer player) {
        return player.translate("survival", "player.command.fly.description");
    }
}
