package com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft._2019.libs.player.Name;
import com.orbitmines.archive.minecraft._2019.libs.rank.StaffRank;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMServer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.arguments.IntegerArgument;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.arguments.PlayerArgument;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.Command;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.executors.Executor1;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.executors.Executor3;
import org.bukkit.Location;

public class CommandTeleportTo<S extends OMServer<S, P>, P extends OMPlayer<S, P>> extends Command<S, P> {

    public CommandTeleportTo(S plugin) {
        super(plugin, "tpto", "teleportto");

        withArg(
            new PlayerArgument<S, P>(plugin, false).executes((Executor1<S, P,
                P, PlayerArgument<S, P>>
            ) (player, target) -> {
                plugin.runSync(() -> {
                    player.teleport(target.getLocation());
                    player.sendMessage("Teleporter", Color.LIME, "spigot", "player.command.tpto.to_player", target.getName(Name.RAW_COLORED) + "§7");
                });
            })
        );

        withArg(
            new IntegerArgument<S, P>("x").withArg(
                new IntegerArgument<S, P>("y").withArg(
                    new IntegerArgument<S, P>("z").executes((Executor3<S, P,
                        Integer, IntegerArgument<S, P>,
                        Integer, IntegerArgument<S, P>,
                        Integer, IntegerArgument<S, P>>
                    ) (player, x, y, z) -> {
                        plugin.runSync(() -> {
                            player.teleport(new Location(player.getWorld(), x, y, z));
                            player.sendMessage("Teleporter", Color.LIME, "spigot", "player.command.tpto.to_location", "§6" + x + "§7", "§6" + y + "§7", "§6" + z + "§7");
                        });
                    })
                )
            ).requires(StaffRank.DEVELOPER)
        );

        requires(StaffRank.MODERATOR);
    }

    @Override
    public String getDescription(P player) {
        return player.translate("spigot", "player.command.tpto.description");
    }
}
