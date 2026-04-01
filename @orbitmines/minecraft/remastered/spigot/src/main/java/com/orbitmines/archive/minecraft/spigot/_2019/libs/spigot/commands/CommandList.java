package com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.player.Name;
import com.orbitmines.archive.minecraft._2019.libs.Server;
import com.orbitmines.archive.minecraft._2019.libs.jedis.OnlinePlayer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMServer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.Command;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.executors.Executor0;

import java.util.List;
import java.util.Map;

public class CommandList<S extends OMServer<S, P>, P extends OMPlayer<S, P>> extends Command<S, P> {

    public CommandList(S plugin) {
        super(plugin, "list");

        executes((Executor0<S, P>) player -> {
            Map<Server, List<OnlinePlayer>> allPlayers = OnlinePlayer.getAllByServer(player);
            int size = 0;
            for (Server server : allPlayers.keySet()) {
                size += allPlayers.get(server).size();
            }

            player.sendMessage(" " + Server.SERVER_DISPLAY_NAME + " §6§l " + size + " " + playerWord(player, size == 1) + " Online");

            for (Server server : allPlayers.keySet()) {
                List<OnlinePlayer> players = allPlayers.get(server);

                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 0; i < players.size(); i++) {
                    if (i != 0)
                        stringBuilder.append("§7, ");

                    stringBuilder.append(players.get(i).getName(Name.NICK_COLORED));
                }

                player.sendMessage("  " + server.getDisplayName() + "§7(" + players.size() + "): " + stringBuilder.toString());
            }
        });
    }

    private String playerWord(P player, boolean singular) {
        return player.translate("spigot", "word.player." + (singular ? "singular" : "plural"));
    }

    @Override
    public String getDescription(P player) {
        return player.translate("spigot", "player.command.list.description");
    }
}
