package com.orbitmines.archive.minecraft.spigot._2019.servers.survival.commands;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft._2019.libs.Server;
import com.orbitmines.archive.minecraft._2019.libs.player.Name;
import com.orbitmines.archive.minecraft._2019.libs.rank.VipRank;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.arguments.PlayerArgument;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.Command;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.executors.Executor1;
import com.orbitmines.archive.minecraft._2019.libs.utils.Message;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.Survival;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.player.SurvivalPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.chat.text.TextBuilder;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;

public class CommandTeleport extends Command<Survival, SurvivalPlayer> {

    public CommandTeleport(Survival plugin) {
        super(plugin, Server.SURVIVAL, "tp", "teleport");

        withArg(
            new PlayerArgument<Survival, SurvivalPlayer>(plugin, false).executes((Executor1<Survival, SurvivalPlayer,
                SurvivalPlayer, PlayerArgument<Survival, SurvivalPlayer>>
            ) (player, target) -> {
                target.getTpRequests().add(player.getRawName());
                target.getTpHereRequests().remove(player.getRawName());

                player.sendMessage("Teleport", Color.LIME, "survival", "player.command.tp.sent", target.getName(Name.RAW_COLORED) + "§7");
                target.sendMessage("Teleport", Color.BLUE, "survival", "player.command.tp.received", player.getName(Name.RAW_COLORED) + "§7");

                TextBuilder<SurvivalPlayer> builder = new TextBuilder<>();
                builder.add(Color.SILVER, p -> Message.format("Teleport", Color.LIME, "  " + target.translate("survival", "player.command.tp.click_to_accept", "§a" + target.translate("survival", "player.command.tp.accept"))))
                    .click(ClickEvent.Action.RUN_COMMAND, p -> "/accept " + player.getRawName())
                    .hover(HoverEvent.Action.SHOW_TEXT, p -> "§7" + target.translate("survival", "player.teleport_to", player.getName(Name.RAW_COLORED) + "§7"));

                builder.send(target);
            })
        );

        requires(VipRank.GOLD);
    }

    @Override
    public String getDescription(SurvivalPlayer player) {
        return player.translate("survival", "player.command.tp.description");
    }
}
