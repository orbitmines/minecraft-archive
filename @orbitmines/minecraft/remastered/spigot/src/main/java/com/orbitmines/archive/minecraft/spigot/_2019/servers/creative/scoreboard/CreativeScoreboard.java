package com.orbitmines.archive.minecraft.spigot._2019.servers.creative.scoreboard;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.scoreboard.DefaultScoreboard;
import com.orbitmines.archive.minecraft.spigot._2019.servers.creative.Creative;
import com.orbitmines.archive.minecraft.spigot._2019.servers.creative.CreativePlayer;
import com.orbitmines.archive.minecraft.spigot._2019.servers.creative.CreativeWorld;

public class CreativeScoreboard extends DefaultScoreboard<Creative, CreativePlayer> {

    public CreativeScoreboard(Creative server, CreativePlayer player) {
        super(server, player,
            () -> server.getScoreboardAnimation().get(),
            () -> "§m--------------",
            () -> "",
            () -> "§c§lOwner",
            () -> {
                CreativeWorld world = server.getWorldByBukkitWorld(player.getWorld());
                if (world == null)
                    return " §7-";
                return " " + world.getOwnerName();
            },
            () -> " ",
            () -> "§d§lWorld",
            () -> {
                CreativeWorld world = server.getWorldByBukkitWorld(player.getWorld());
                if (world == null)
                    return " §7-";
                String name = world.getName();
                if (name.length() > 32)
                    name = name.substring(0, 32);
                return " §f" + name;
            },
            () -> "  "
        );
    }

    @Override
    public boolean canBypassSettings() {
        return false;
    }
}
