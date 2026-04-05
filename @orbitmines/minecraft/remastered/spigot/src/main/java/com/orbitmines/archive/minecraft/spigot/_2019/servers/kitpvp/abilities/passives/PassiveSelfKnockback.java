package com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.passives;

import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.Passive;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.events.KitEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.util.Vector;

public class PassiveSelfKnockback implements Passive.Handler<EntityDamageByEntityEvent> {

    @Override
    public void trigger(KitEvent<EntityDamageByEntityEvent> passiveEvent, EntityDamageByEntityEvent event, int level) {
        if (!(event.getDamager() instanceof Player))
            return;

        Player damager = (Player) event.getDamager();

        /* Launch self backwards and slightly upward */
        Vector direction = damager.getLocation().getDirection().normalize();
        Vector knockback = direction.multiply(-getStrength(level));
        knockback.setY(getYBoost(level));

        damager.setVelocity(damager.getVelocity().add(knockback));
    }

    public double getStrength(int level) {
        switch (level) {
            case 1: return 0.6D;
            case 2: return 0.7D;
            case 3: return 0.8D;
            default: throw new ArrayIndexOutOfBoundsException();
        }
    }

    public double getYBoost(int level) {
        switch (level) {
            case 1: return 0.35D;
            case 2: return 0.40D;
            case 3: return 0.45D;
            default: throw new ArrayIndexOutOfBoundsException();
        }
    }
}
