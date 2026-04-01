package com.orbitmines.archive.minecraft.spigot._2019.servers.survival.commands;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft._2019.libs.Server;
import com.orbitmines.archive.minecraft._2019.libs.player.Name;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.arguments.IntegerArgument;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.arguments.PlayerArgument;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.Command;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.executors.Executor2;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.database.models.survival.SurvivalPlayerModel;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.Survival;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.player.SurvivalPlayer;
import com.orbitmines.archive.minecraft._2019.utils.NumberUtils;

public class CommandPay extends Command<Survival, SurvivalPlayer> {

    public CommandPay(Survival plugin) {
        super(plugin, Server.SURVIVAL, "pay");

        withArg(
            new PlayerArgument<Survival, SurvivalPlayer>(plugin, false).
                withArg(
                    new IntegerArgument<Survival, SurvivalPlayer>("amount").
                        executes((Executor2<Survival, SurvivalPlayer,
                            SurvivalPlayer, PlayerArgument<Survival, SurvivalPlayer>,
                            Integer, IntegerArgument<Survival, SurvivalPlayer>>
                        ) (player, target, amount) -> {
                            if (amount <= 0) {
                                player.sendMessage("Commands", Color.ERROR, "spigot", "player.command.argument.integer.invalid", amount + "");
                                return;
                            }

                            player.removeCredits(amount);
                            target.addCredits(amount);

                            player.update(SurvivalPlayerModel.column.CREDITS);
                            target.update(SurvivalPlayerModel.column.CREDITS);

                            String display = "§2§l" + NumberUtils.locale(amount) + " " + (amount == 1 ? "Credit" : "Credits") + "§7";

                            player.sendMessage("Pay", Color.LIME, "survival", "player.command.pay.paid", display, target.getName(Name.RAW_COLORED) + "§7");
                            target.sendMessage("Pay", Color.LIME, "survival", "player.command.pay.receive", display, player.getName(Name.RAW_COLORED) + "§7");
                        })
                )
        );
    }

    @Override
    public String getDescription(SurvivalPlayer player) {
        return player.translate("survival", "player.command.pay.description");
    }
}
