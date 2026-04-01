package com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft._2019.libs.database.models.PlayerSettings;
import com.orbitmines.archive.minecraft._2019.libs.player.Name;
import com.orbitmines.archive.minecraft._2019.libs.rank.StaffRank;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMServer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.arguments.PlayerArgument;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.Command;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.executors.Executor0;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.executors.Executor1;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.guis.stats.StatsGUI;

public class CommandStats<S extends OMServer<S, P>, P extends OMPlayer<S, P>> extends Command<S, P> {

    public CommandStats(S plugin) {
        super(plugin, "stats", "statistics", "stat");

        withArg(
            new PlayerArgument<>(plugin, true).executes((Executor1<S, P,
                P, PlayerArgument<S, P>>
            ) (player, target) -> {
                if (!allowedBySettings(player, target))
                    return;

                new StatsGUI<>(plugin, player, target).open();
            })
        ).executes((Executor0<S, P>) player -> {
            new StatsGUI<>(plugin, player, player).open();
        });
    }

    @Override
    public String getDescription(P player) {
        return player.translate("spigot", "player.command.stats.description");
    }

    private boolean allowedBySettings(P player, P target) {
        if (player.isEligible(StaffRank.HELPER))
            return true;

        PlayerSettings settings = target.getSettings(PlayerSettings.Type.STATS, false);

        switch (settings.getLevel()) {

            case ENABLED:
                return true;
            case ONLY_FRIENDS: {
                boolean friend = target.isFriend(player.getUUID());

                if (!friend)
                    player.sendMessage("Stats", Color.RED, "spigot", "player.command.stats.only_friends", target.getName(Name.RAW_COLORED) + "§7");

                return friend;
            }
            case ONLY_FAVORITE_FRIENDS:
                boolean favoriteFriends = target.isFriend(player.getUUID(), true);

                if (!favoriteFriends)
                    player.sendMessage("Stats", Color.RED, "spigot", "player.command.stats.only_favorite_friends", target.getName(Name.RAW_COLORED) + "§7");

                return favoriteFriends;
            case DISABLED:
                player.sendMessage("Stats", Color.RED, "spigot", "player.command.stats.disabled", target.getName(Name.RAW_COLORED) + "§7");
                return false;
        }

        return true;
    }
}
