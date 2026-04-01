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
import org.bukkit.inventory.meta.SkullMeta;

public class CommandSkull extends Command<Build, BuildPlayer> {

    public CommandSkull(Build plugin) {
        super(plugin, Server.BUILD, "skull");

        withArg(
            new MessageArgument<Build, BuildPlayer>("player").executes((Executor1<Build, BuildPlayer,
                String, MessageArgument<Build, BuildPlayer>>
            ) (player, skullName) -> {
                ItemStack item = new ItemStack(Material.PLAYER_HEAD, 1);
                SkullMeta meta = (SkullMeta) item.getItemMeta();
                meta.setOwner(skullName);
                item.setItemMeta(meta);

                plugin.runSync(() -> {
                    player.getInventory().addItem(item);
                    player.sendRawMessage("Skull", Color.LIME, "Given the skull of §6" + skullName + "§7 to yourself.");
                });
            })
        );

        requires(StaffRank.PROVISIONAL_BUILDER);
    }

    @Override
    public String getDescription(BuildPlayer player) {
        return player.translate("build", "player.command.skull.description");
    }
}
