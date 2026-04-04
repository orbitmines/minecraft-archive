package com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.passives;

import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.Passive;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.events.KitEvent;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PassiveWitherHit implements Passive.Handler<EntityDamageByEntityEvent> {

    @Override
    public void trigger(KitEvent<EntityDamageByEntityEvent> passiveEvent, EntityDamageByEntityEvent event, int level) {
        if (!(event.getEntity() instanceof LivingEntity))
            return;

        LivingEntity entity = (LivingEntity) event.getEntity();
        entity.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, getDuration(level), getAmplifier(level)));
    }

    public int getAmplifier(int level) {
        switch (level) {
            case 1: return 0;
            case 2: return 0;
            case 3: return 1;
            default: throw new ArrayIndexOutOfBoundsException();
        }
    }

    public int getDuration(int level) {
        switch (level) {
            case 1: return 80;
            case 2: return 100;
            case 3: return 80;
            default: throw new ArrayIndexOutOfBoundsException();
        }
    }
}
