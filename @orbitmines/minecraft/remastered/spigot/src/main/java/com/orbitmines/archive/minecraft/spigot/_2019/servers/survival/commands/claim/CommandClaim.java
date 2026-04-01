package com.orbitmines.archive.minecraft.spigot._2019.servers.survival.commands.claim;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft._2019.libs.Server;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.Command;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.executors.Executor0;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.Survival;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.items.ClaimTool;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.player.SurvivalPlayer;
import org.bukkit.Sound;
import org.bukkit.inventory.ItemStack;

public class CommandClaim extends Command<Survival, SurvivalPlayer> {

    public CommandClaim(Survival plugin) {
        super(plugin, Server.SURVIVAL, "claim", "claimtool");

        executes((Executor0<Survival, SurvivalPlayer>) (player) -> {
            if (player.getInventory().firstEmpty() == -1) {
                player.sendMessage("Claim", Color.RED, "survival", "player.command.claim.full_inventory");
                return;
            }

            int slot = player.getInventory().getHeldItemSlot();
            ItemStack inHand;
            if (player.getInventory().getItem(slot) != null)
                inHand = player.getInventory().getItem(slot);
            else
                inHand = null;

            plugin.runSync(() -> {
                player.getInventory().setItem(slot, ClaimTool.ITEM.build());

                if (inHand != null)
                    player.getInventory().addItem(inHand);
            });

            player.playSound(Sound.ENTITY_ITEM_PICKUP);
            player.sendMessage("Claim", Color.LIME, "survival", "player.command.claim.received", ClaimTool.ITEM.getDisplayName() + "§7");
        });
    }

    @Override
    public String getDescription(SurvivalPlayer player) {
        return player.translate("survival", "player.command.claim.description");
    }
}
