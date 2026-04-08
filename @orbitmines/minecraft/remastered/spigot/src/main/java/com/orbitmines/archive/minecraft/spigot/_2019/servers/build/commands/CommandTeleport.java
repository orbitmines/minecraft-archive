package com.orbitmines.archive.minecraft.spigot._2019.servers.build.commands;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft._2019.libs.player.Name;
import com.orbitmines.archive.minecraft._2019.libs.rank.Rank;
import com.orbitmines.archive.minecraft._2019.libs.rank.StaffRank;
import com.orbitmines.archive.minecraft._2019.libs.utils.Message;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMServer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.arguments.PlayerArgument;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.Command;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.executors.Executor1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.chat.text.TextBuilder;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;

public class CommandTeleport<S extends OMServer<S, P>, P extends OMPlayer<S, P>> extends Command<S, P> {

    public CommandTeleport(S plugin) {
        this(plugin, StaffRank.NONE);
    }

    public CommandTeleport(S plugin, Rank rank) {
        super(plugin, "tp", "teleport");

        withArg(
            new PlayerArgument<S, P>(plugin, false).executes((Executor1<S, P,
                P, PlayerArgument<S, P>>
            ) (player, target) -> {
                target.getTpRequests().add(player.getRawName());
                target.getTpHereRequests().remove(player.getRawName());

                player.sendMessage("Teleport", Color.LIME, "spigot", "player.command.tp.sent", target.getName(Name.RAW_COLORED) + "§7");
                target.sendMessage("Teleport", Color.BLUE, "spigot", "player.command.tp.received", player.getName(Name.RAW_COLORED) + "§7");

                TextBuilder<P> builder = new TextBuilder<>();
                builder.add(Color.SILVER, p -> Message.format("Teleport", Color.LIME, "  " + target.translate("spigot", "player.command.tp.click_to_accept", "§a" + target.translate("spigot", "player.command.tp.accept"))))
                    .click(ClickEvent.Action.RUN_COMMAND, p -> "/tpaccept " + player.getRawName())
                    .hover(HoverEvent.Action.SHOW_TEXT, p -> "§7" + target.translate("spigot", "player.teleport_to", player.getName(Name.RAW_COLORED) + "§7"));

                builder.send(target);
            })
        );

        requires(rank);
    }

    @Override
    public String getDescription(P player) {
        return player.translate("spigot", "player.command.tp.description");
    }
}
