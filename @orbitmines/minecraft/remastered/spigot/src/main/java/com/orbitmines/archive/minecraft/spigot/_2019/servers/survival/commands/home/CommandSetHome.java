package com.orbitmines.archive.minecraft.spigot._2019.servers.survival.commands.home;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft._2019.libs.Server;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.Command;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.executors.Executor1;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.database.models.survival.SurvivalClaim;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.database.models.survival.SurvivalHome;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.Survival;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.commands.argument.HomeArgument;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.database.models.Claim;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.database.models.Home;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.player.SurvivalPlayer;

public class CommandSetHome extends Command<Survival, SurvivalPlayer> {

    public CommandSetHome(Survival plugin) {
        super(plugin, Server.SURVIVAL, "sethome", "seth");

        withArg(
            new HomeArgument(true).executes((Executor1<Survival, SurvivalPlayer,
                Home, HomeArgument>
            ) (player, home) -> {
                if (home.isInserted()) {
                    Claim claim = plugin.getClaimHandler().getClaimAt(player.getLocation(), false, player.getLastClaim());
                    if (claim != null)
                        player.setLastClaim(claim);

                    if (claim != null && !claim.canAccess(player, false)) {
                        player.sendMessage("Home", Color.RED, "survival", "player.command.set_home.in_claim", "§a§l" + SurvivalClaim.Permission.ACCESS.getName(player) + "§7", claim.getOwnerName() + "§7");
                        return;
                    }

                    home.setLocation(player.getLocation());
                    home.update(SurvivalHome.column.LOCATION);

                    player.sendMessage("Home", Color.SUCCESS, "survival", "player.command.set_home.set", "§6" + home.getName() + "§7");
                    return;
                }

                if (player.getHomes(false).size() >= player.getHomesAllowed()) {
                    player.sendMessage("Home", Color.ERROR, "survival", "player.command.set_home.maximum", player.getHomesAllowed());
                    return;
                }

                Claim claim = plugin.getClaimHandler().getClaimAt(player.getLocation(), false, player.getLastClaim());
                if (claim != null)
                    player.setLastClaim(claim);

                if (claim != null && !claim.canAccess(player, false)) {
                    player.sendMessage("Home", Color.RED, "survival", "player.command.set_home.in_claim", "§a§l" + SurvivalClaim.Permission.ACCESS.getName(player) + "§7", claim.getOwnerName() + "§7");
                    return;
                }

                home.insert();
                player.getHomes(false).add(home);

                player.sendMessage("Home", Color.SUCCESS, "survival", "player.command.set_home.set", "§6" + home.getName() + "§7");
            })
        );
    }

    @Override
    public String getDescription(SurvivalPlayer player) {
        return player.translate("survival", "player.command.set_home.description");
    }
}
