package com.orbitmines.minecraft.spigot.servers.fog.events;

import com.orbitmines.minecraft.spigot.servers.fog.FoG;
import org.bukkit.entity.Bee;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Ravager;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.Random;

/** Applies FoG-specific damage side-effects (hunger, knock-up, poison). */
public class DamageListener implements Listener {

    private static final Random RANDOM = new Random();
    private final FoG server;

    public DamageListener(FoG server) {
        this.server = server;
    }

    @EventHandler
    public void onHit(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player victim)) return;
        if (!(event.getDamager() instanceof LivingEntity damager)) return;

        /* Zombie → chance of hunger if food bar is empty */
        if (damager instanceof Zombie) {
            if (victim.getFoodLevel() <= 0 && RANDOM.nextInt(100) < 20) {
                victim.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 20 * 5, 0));
                victim.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, 20 * 3, 2));
            }
        }

        /* Ravager (Bull) → knock up */
        if (damager instanceof Ravager) {
            victim.setVelocity(victim.getVelocity().add(new Vector(0, 1.4, 0)));
        }

        /* Bee → 20% poison chance */
        if (damager instanceof Bee) {
            if (RANDOM.nextInt(100) < 20) {
                victim.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 20 * 2, 0));
            }
        }
    }
}
