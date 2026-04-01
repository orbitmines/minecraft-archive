package com.orbitmines.archive.minecraft.spigot._2019.servers.survival.commands;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft._2019.libs.Server;
import com.orbitmines.archive.minecraft._2019.libs.database.models.PlayerModel;
import com.orbitmines.archive.minecraft._2019.libs.rank.StaffRank;
import com.orbitmines.archive.minecraft._2019.libs.rank.VipRank;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.arguments.PlayerModelArgument;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.Command;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.executors.Executor0;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.executors.Executor1;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.Survival;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.player.SurvivalPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.PlayerUtils;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.runnable.Interval;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.runnable.SpigotRunnable;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.runnable.TimeUnit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class CommandEnderchest extends Command<Survival, SurvivalPlayer> {

    public CommandEnderchest(Survival plugin) {
        super(plugin, Server.SURVIVAL, "enderchest", "echest", "ec");

        executes((Executor0<Survival, SurvivalPlayer>) (player) -> {
            plugin.runSync(() -> player.openInventory(player.getEnderChest()));
        }).requires(VipRank.DIAMOND);
        
        withArg(
            new PlayerModelArgument<Survival, SurvivalPlayer>(true).executes((Executor1<Survival, SurvivalPlayer,
                PlayerModel, PlayerModelArgument<Survival, SurvivalPlayer>>
            ) (player, model) -> {
                Player target;
                boolean wasOnline;

                if (plugin.getPlayer(model.getUUID()) != null) {
                    target = plugin.getPlayer(model.getUUID()).bukkit();
                    wasOnline = true;
                } else {
                    target = PlayerUtils.loadOfflinePlayer(model.getUUID());

                    if (target == null) {
                        player.sendMessage("Enderchest", Color.RED, "survival", "player.claim.gui.cannot_find_player");
                        return;
                    }

                    wasOnline = false;
                }

                Inventory inventory = target.getEnderChest();

                plugin.runSync(() -> player.openInventory(inventory));

                new SpigotRunnable<Survival>(plugin, Interval.of(TimeUnit.TICK, 1)) {
                    @Override
                    public void run() {
                        if (!player.bukkit().isOnline() || !inventory.equals(player.getOpenInventory().getTopInventory())) {
                            cancel();

                            if (player.bukkit().isOnline())
                                player.closeInventory();

                            return;
                        }

                        if (wasOnline) {
                            /* Player is Online, when he's offline, close inventory */
                            if (target.isOnline())
                                return;

                            cancel();
                            player.closeInventory();
                        } else {
                            /* Player is offline, when he's online, close inventory */
                            SurvivalPlayer player2 = CommandEnderchest.this.getPlugin().getPlayer(target.getUniqueId());

                            if (player2 == null) {
                                target.saveData();
                                return;
                            }

                            cancel();
                            player.closeInventory();
                        }
                    }
                }.start();
            }).requires(StaffRank.ADMIN)
        );
    }

    @Override
    public String getDescription(SurvivalPlayer player) {
        return player.translate("survival", "player.command.enderchest.description");
    }
}
