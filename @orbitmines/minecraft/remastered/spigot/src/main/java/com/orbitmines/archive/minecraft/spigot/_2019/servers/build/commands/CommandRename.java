package com.orbitmines.archive.minecraft.spigot._2019.servers.build.commands;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft._2019.libs.Server;
import com.orbitmines.archive.minecraft._2019.libs.rank.StaffRank;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.arguments.MessageArgument;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.Command;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.executors.Executor1;
import com.orbitmines.archive.minecraft.spigot._2019.servers.build.Build;
import com.orbitmines.archive.minecraft.spigot._2019.servers.build.BuildPlayer;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CommandRename extends Command<Build, BuildPlayer> {

    public CommandRename(Build plugin) {
        super(plugin, Server.BUILD, "rename", "name");

        withArg(
            new MessageArgument<Build, BuildPlayer>("name").executes((Executor1<Build, BuildPlayer,
                String, MessageArgument<Build, BuildPlayer>>
            ) (player, name) -> {
                plugin.runSync(() -> {
                    ItemStack item = player.getItemInMainHand();

                    if (item == null || item.getType() == Material.AIR) {
                        player.sendRawMessage("Build", Color.ERROR, "You don't have an item in your hand!");
                        return;
                    }

                    String coloredName = Color.translateAlternateColorCodes('&', name);

                    ItemMeta meta = item.getItemMeta();
                    meta.setDisplayName(coloredName);
                    meta.setLocalizedName(coloredName);
                    item.setItemMeta(meta);

                    player.setItemInMainHand(item);

                    player.sendRawMessage("Item", Color.SUCCESS, "Successfully renamed the item in your hand to " + coloredName + "§7.");
                });
            })
        );

        requires(StaffRank.PROVISIONAL_BUILDER);
    }

    @Override
    public String getDescription(BuildPlayer player) {
        return player.translate("build", "player.command.rename.description");
    }
}
