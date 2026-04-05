package com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.passives;

import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.Passive;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.events.KitEvent;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

public class PassiveTrade implements Passive.Handler<EntityDamageByEntityEvent> {

    @Override
    public void trigger(KitEvent<EntityDamageByEntityEvent> passiveEvent, EntityDamageByEntityEvent event, int level) {
        if (!(event.getDamager() instanceof Player) || !(event.getEntity() instanceof Player))
            return;

        Player damager = (Player) event.getDamager();
        Player victim = (Player) event.getEntity();

        ItemStack damagerItem = damager.getInventory().getItemInMainHand();
        ItemStack victimItem = victim.getInventory().getItemInMainHand();

        /* Consume one bread from the trade item */
        if (damagerItem.getAmount() > 1)
            damagerItem.setAmount(damagerItem.getAmount() - 1);
        else
            damager.getInventory().setItemInMainHand(null);

        /* Give victim's item to damager, give bread to victim */
        damager.getInventory().addItem(victimItem.clone());
        victim.getInventory().setItemInMainHand(new ItemStack(Material.BREAD, 1));

        damager.getWorld().playSound(damager.getLocation(), Sound.ENTITY_VILLAGER_TRADE, 1.0f, 1.0f);
        victim.getWorld().playSound(victim.getLocation(), Sound.ENTITY_VILLAGER_TRADE, 1.0f, 1.0f);
    }
}
