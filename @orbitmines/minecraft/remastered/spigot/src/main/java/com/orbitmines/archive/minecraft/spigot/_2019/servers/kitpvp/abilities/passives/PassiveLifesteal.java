package com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.passives;

import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.Passive;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.events.KitEvent;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class PassiveLifesteal implements Passive.Handler<EntityDamageByEntityEvent> {

    @Override
    public void trigger(KitEvent<EntityDamageByEntityEvent> passiveEvent, EntityDamageByEntityEvent event, int level) {
        if (!(event.getDamager() instanceof Player))
            return;

        Player damager = (Player) event.getDamager();
        double maxHealth = damager.getAttribute(Attribute.MAX_HEALTH).getValue();
        double newHealth = Math.min(damager.getHealth() + getHealAmount(level), maxHealth);
        damager.setHealth(newHealth);
    }

    public double getHealAmount(int level) {
        switch (level) {
            case 1: return 1.5D;
            case 2: return 2.0D;
            case 3: return 2.5D;
            default: throw new ArrayIndexOutOfBoundsException();
        }
    }
}
