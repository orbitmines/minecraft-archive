package com.orbitmines.archive.minecraft.spigot._2019.servers.survival.commands.claim;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.Server;
import com.orbitmines.archive.minecraft._2019.libs.database.models.PlayerModel;
import com.orbitmines.archive.minecraft._2019.libs.rank.StaffRank;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.arguments.PlayerModelArgument;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.Command;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.executors.Executor0;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.executors.Executor1;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.Survival;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.gui.claim.ClaimListGUI;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.player.SurvivalPlayer;

public class CommandClaimList extends Command<Survival, SurvivalPlayer> {

    public CommandClaimList(Survival plugin) {
        super(plugin, Server.SURVIVAL, "claimlist");

        executes((Executor0<Survival, SurvivalPlayer>) (player) -> {
            new ClaimListGUI(plugin, player, player).open();
        }).withArg(
            new PlayerModelArgument<Survival, SurvivalPlayer>(false).
                executes((Executor1<Survival, SurvivalPlayer,
                    PlayerModel, PlayerModelArgument<Survival, SurvivalPlayer>>
                ) (player, model) -> {
                    new ClaimListGUI(plugin, player, model).open();
                }).requires(StaffRank.MODERATOR)
        );
    }

    @Override
    public String getDescription(SurvivalPlayer player) {
        return player.translate("survival", "player.command.claim_list.description");
    }
}
