package com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft._2019.libs.rank.StaffRank;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMServer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.Command;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.executors.Executor0;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;

public class CommandOpMode<S extends OMServer<S, P>, P extends OMPlayer<S, P>> extends Command<S, P> {

    private BossBar bossBar;

    public CommandOpMode(S plugin) {
        super(plugin, "opmode");

        this.bossBar = Bukkit.createBossBar("§4§lOP MODE ENABLED", BarColor.RED, BarStyle.SOLID);

        executes((Executor0<S, P>) (player) -> {
            player.setOpMode(!player.isOpMode());
            plugin.runSync(() -> player.bukkit().setOp(player.isOpMode()));

            if (player.isOpMode()) {
                player.sendMessage("Op", Color.LIME, "spigot", "player.command.opmode.enabled");
                bossBar.addPlayer(player.bukkit());
            } else {
                player.sendMessage("Op", Color.RED, "spigot", "player.command.opmode.disabled");
                bossBar.removePlayer(player.bukkit());
            }
        });

        requires(StaffRank.ADMIN);
    }

    @Override
    public String getDescription(P player) {
        return player.translate("spigot", "player.command.opmode.description");
    }
}
