package com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.passives;

import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.Passive;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.events.KitEvent;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.util.Vector;

public class PassiveKnockup implements Passive.Handler<EntityDamageByEntityEvent> {

    @Override
    public void trigger(KitEvent<EntityDamageByEntityEvent> passiveEvent, EntityDamageByEntityEvent event, int level) {
        if (!(event.getEntity() instanceof LivingEntity))
            return;

        if (Math.random() >= getChance(level))
            return;

        LivingEntity entity = (LivingEntity) event.getEntity();
        entity.setVelocity(entity.getVelocity().add(new Vector(0, getVelocityY(level), 0)));
        entity.getWorld().playSound(entity.getLocation(), Sound.ENTITY_IRON_GOLEM_ATTACK, 1.0f, 1.0f);
    }

    public double getChance(int level) {
        return 0.50D + (0.05D * (level - 1));
    }

    public double getVelocityY(int level) {
        switch (level) {
            case 1: return 0.9D;
            case 2: return 1.0D;
            case 3: return 1.1D;
            default: throw new ArrayIndexOutOfBoundsException();
        }
    }
}
