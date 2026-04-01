package com.orbitmines.archive.minecraft.spigot._2019.servers.build.commands;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft._2019.libs.Server;
import com.orbitmines.archive.minecraft._2019.libs.rank.StaffRank;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.Command;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.executors.Executor0;
import com.orbitmines.archive.minecraft.spigot._2019.servers.build.Build;
import com.orbitmines.archive.minecraft.spigot._2019.servers.build.BuildPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.servers.build.database.models.BuildMap;

public class CommandMapList extends Command<Build, BuildPlayer> {

    public CommandMapList(Build plugin) {
        super(plugin, Server.BUILD, "maps", "maplist");

        executes((Executor0<Build, BuildPlayer>) (player) -> {
            StringBuilder builder = new StringBuilder();

            BuildMap current = player.getCurrentMap();

            for (int i = 0; i < plugin.getMaps().size(); i++) {
                if (i != 0)
                    builder.append("§7, ");

                BuildMap map = plugin.getMaps().get(i);

                builder.append(map.equals(current) ? "§a" : "§9").append(map.getName());
            }

            player.sendRawMessage("Maps", Color.BLUE, builder.toString());
        });

        requires(StaffRank.PROVISIONAL_BUILDER);
    }

    @Override
    public String getDescription(BuildPlayer player) {
        return player.translate("build", "player.command.map_list.description");
    }
}
