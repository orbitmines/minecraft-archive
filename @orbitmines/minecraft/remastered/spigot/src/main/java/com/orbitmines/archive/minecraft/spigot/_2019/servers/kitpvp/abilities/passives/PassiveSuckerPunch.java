package com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.passives;

/*
 * OrbitMines - @author Fadi Shawki - 2018
 */

import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.Passive;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.events.KitEvent;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class PassiveSuckerPunch implements Passive.Handler<EntityDamageByEntityEvent> {

    /* Chance of applying knockback enchantment 1: 50%, 2: 55%, 3: 60%*/

    @Override
    public void trigger(KitEvent<EntityDamageByEntityEvent> passiveEvent, EntityDamageByEntityEvent event, int level) {
        if (!(event.getDamager() instanceof LivingEntity) || !(event.getEntity() instanceof LivingEntity))
            return;

        LivingEntity damager = (LivingEntity) event.getDamager();
        EntityEquipment equipment = damager.getEquipment();
        ItemStack item = equipment.getItemInMainHand();

        /* Skip if no item, or Knockback already present (don't stack over an existing enchant) */
        if (item == null || item.getEnchantments().containsKey(Enchantment.KNOCKBACK))
            return;

        if (Math.random() >= getChance(level))
            return;

        /* Apply knockback for this hit, remove next tick */
        item.addEnchantment(Enchantment.KNOCKBACK, 1);
        equipment.setItemInMainHand(item);

        new BukkitRunnable() {
            @Override
            public void run() {
                item.removeEnchantment(Enchantment.KNOCKBACK);
                equipment.setItemInMainHand(item);
            }
        }.runTaskLater(passiveEvent.server().getPlugin(), 1);
    }

    public double getChance(int level) {
        return 0.50D + (0.05D * (level -1));
    }
}
