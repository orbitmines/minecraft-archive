package com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.passives;

import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.Passive;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.events.KitEvent;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PassiveWitherArmor implements Passive.Handler<EntityDamageEvent> {

    @Override
    public void trigger(KitEvent<EntityDamageEvent> passiveEvent, EntityDamageEvent event, int level) {
        if (!(event instanceof EntityDamageByEntityEvent))
            return;

        EntityDamageByEntityEvent damageByEntity = (EntityDamageByEntityEvent) event;
        if (!(damageByEntity.getDamager() instanceof LivingEntity))
            return;

        LivingEntity attacker = (LivingEntity) damageByEntity.getDamager();
        attacker.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, getDuration(level), getAmplifier(level)));
    }

    public int getAmplifier(int level) {
        return 1;
    }

    public int getDuration(int level) {
        switch (level) {
            case 1: return 100;
            case 2: return 120;
            default: throw new ArrayIndexOutOfBoundsException();
        }
    }
}
