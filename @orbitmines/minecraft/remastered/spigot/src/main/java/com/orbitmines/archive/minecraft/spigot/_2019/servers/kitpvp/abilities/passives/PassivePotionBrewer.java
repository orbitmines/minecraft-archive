package com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.passives;

/*
 * OrbitMines - @author Fadi Shawki - 2018
 */

import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.KitPvPPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.Passive;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.events.KitEvent;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.item_builders.KitPotionItemBuilder;
import com.orbitmines.archive.minecraft._2019.utils.NumberUtils;
import com.orbitmines.archive.minecraft._2019.utils.RandomUtils;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.ItemUtils;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.PlayerUtils;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.PotionBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.mutable.MutablePlayerItemBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.kits.Kit;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.npcs.FloatingItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class PassivePotionBrewer implements Passive.Handler<PlayerDeathEvent> {

    @Override
    public void trigger(KitEvent<PlayerDeathEvent> passiveEvent, PlayerDeathEvent event, int level) {
        /* There's a chance of brewing a potion, otherwise move on */
        if (Math.random() >= getChance(level))
            return;

        Player entity = event.getEntity();

        Player killer = entity.getKiller();
        KitPvPPlayer player = passiveEvent.server().getPlayer(killer);

        Kit<KitPvPPlayer> kit = player.getSelectedKit().getKit();
        List<MutablePlayerItemBuilder<?, KitPvPPlayer>> potions = kit.with(player, Material.POTION, Material.LINGERING_POTION, Material.SPLASH_POTION);

        /* No potions from current selected kit */
        if (potions.size() == 0)
            return;

        KitPotionItemBuilder item = (KitPotionItemBuilder) RandomUtils.randomFrom(potions).toBuilder(player);
        ItemStack potion = item.build();
        int slot = killer.getInventory().first(potion);
        if (slot == -1) {
            killer.getInventory().addItem(potion);
        } else {
            potion = killer.getInventory().getItem(slot);

            /* Already reached max capacity for this item */
            if (potion.getAmount() >= 64)
                return;

            potion.setAmount(potion.getAmount() + 1);
            killer.getInventory().setItem(slot, potion);

            PlayerUtils.updateInventory(killer);
        }
        /* Build Item Hologram */
        FloatingItem hologram = new FloatingItem(item::build, entity.getLocation());
        hologram.addLine(() -> Passive.POTION_BREWER.getColor().getCc() + "§l" + Passive.POTION_BREWER.getName(), false);

        PotionBuilder builder = item.getPotionBuilders().get(0);
        hologram.addLine(() -> "§e§o+ " + ItemUtils.getName(builder.getType()) + " " + NumberUtils.toRoman(builder.getAmplifier() + 1) + " Potion", false);
        hologram.create(killer);

        new BukkitRunnable() {
            @Override
            public void run() {
                hologram.destroy();
            }
        }.runTaskLater(passiveEvent.server().getPlugin(), 60L);
    }

    public double getChance(int level) {
        switch (level) {
            case 1:
                return 0.125;
            default:
                throw new ArrayIndexOutOfBoundsException();
        }
    }
}
