package com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft._2019.libs.Server;
import com.orbitmines.archive.minecraft._2019.libs.rank.StaffRank;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMServer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.Command;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.builder.CommandHelpBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.executors.Executor0;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.chat.text.TextBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandHelp<S extends OMServer<S, P>, P extends OMPlayer<S, P>> extends Command<S, P> {

    public CommandHelp(S plugin) {
        super(plugin, "help", "?");

        executes((Executor0<S, P>) player -> {
            player.sendMessage("");
            player.sendMessage(" §8§lOrbit§7§lMines §9§lCommand Help");

            Map<Server, List<Command>> serverCommands = new HashMap<>();
            for (Command command : Command.getCommands()) {
                if (command.getServer() == null) {
                    sendHelp(player, command);
                    continue;
                }

                serverCommands.computeIfAbsent(command.getServer(), key -> new ArrayList<>()).add(command);
            }

            for (Server server : serverCommands.keySet()) {
                player.sendMessage(" " + server.getDisplayName());

                for (Command command : serverCommands.get(server)) {
                    sendHelp(player, command);
                }
            }
        });
    }

    private void sendHelp(P player, Command command) {
        if (command.getRank() instanceof StaffRank && !player.isEligible((StaffRank) command.getRank()))
            return;

        TextBuilder<P> builder = new TextBuilder<>();
        builder.add(Color.SILVER, p -> "  - ");

        new CommandHelpBuilder<S, P>().append(builder, player, command, command, true);

        builder.send(player);
    }

    @Override
    public String getDescription(P player) {
        return player.translate("spigot", "player.command.help.description");
    }
}
