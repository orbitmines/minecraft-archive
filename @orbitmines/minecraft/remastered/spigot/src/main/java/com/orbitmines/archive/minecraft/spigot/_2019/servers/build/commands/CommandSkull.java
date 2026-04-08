package com.orbitmines.archive.minecraft.spigot._2019.servers.build.commands;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft._2019.libs.rank.Rank;
import com.orbitmines.archive.minecraft._2019.libs.rank.StaffRank;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMServer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.arguments.MessageArgument;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.Command;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.executors.Executor1;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class CommandSkull<S extends OMServer<S, P>, P extends OMPlayer<S, P>> extends Command<S, P> {

    public CommandSkull(S plugin) {
        this(plugin, StaffRank.NONE);
    }

    public CommandSkull(S plugin, Rank rank) {
        super(plugin, "skull");

        withArg(
            new MessageArgument<S, P>("player").executes((Executor1<S, P,
                String, MessageArgument<S, P>>
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

        requires(rank);
    }

    @Override
    public String getDescription(P player) {
        return player.translate("build", "player.command.skull.description");
    }
}
