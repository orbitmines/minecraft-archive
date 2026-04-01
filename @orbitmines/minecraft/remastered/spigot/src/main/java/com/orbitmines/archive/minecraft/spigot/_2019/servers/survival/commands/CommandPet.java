package com.orbitmines.archive.minecraft.spigot._2019.servers.survival.commands;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft._2019.libs.Server;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.Command;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.executors.Executor0;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.Survival;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.player.SurvivalPlayer;
import org.bukkit.Sound;
import org.bukkit.inventory.ItemStack;

public class CommandPet extends Command<Survival, SurvivalPlayer> {

    public CommandPet(Survival plugin) {
        super(plugin, Server.SURVIVAL, "pet", "petticket");

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
                player.getInventory().setItem(slot, plugin.getNms().customItem().setMetaData(Survival.PET_TICKET.clone().addLore("§7§oNone linked.").build(), "orbitmines_survival", "created_on", System.currentTimeMillis() + ""));

                if (inHand != null)
                    player.getInventory().addItem(inHand);
            });

            player.playSound(Sound.ENTITY_ITEM_PICKUP);
            player.sendMessage("Claim", Color.LIME, "survival", "player.command.pet.received", Survival.PET_TICKET.getDisplayName() + "§7");
        });
    }

    @Override
    public String getDescription(SurvivalPlayer player) {
        return player.translate("survival", "player.command.pet.description");
    }
}
