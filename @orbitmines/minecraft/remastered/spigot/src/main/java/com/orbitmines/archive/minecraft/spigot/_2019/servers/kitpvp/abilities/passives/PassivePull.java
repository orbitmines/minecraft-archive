package com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.passives;

import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.Passive;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.events.KitEvent;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.util.Vector;

public class PassivePull implements Passive.Handler<EntityDamageByEntityEvent> {

    @Override
    public void trigger(KitEvent<EntityDamageByEntityEvent> passiveEvent, EntityDamageByEntityEvent event, int level) {
        if (!(event.getDamager() instanceof Player) || !(event.getEntity() instanceof LivingEntity))
            return;

        Player damager = (Player) event.getDamager();
        LivingEntity victim = (LivingEntity) event.getEntity();

        /* Delay by 1 tick to override Minecraft's built-in knockback */
        passiveEvent.server().getPlugin().getServer().getScheduler().runTaskLater(passiveEvent.server().getPlugin(), () -> {
            if (victim.isDead() || (victim instanceof Player && !((Player) victim).isOnline()))
                return;

            Vector pull = damager.getLocation().toVector().subtract(victim.getLocation().toVector());
            if (pull.length() < 1.0)
                return;

            pull.normalize().multiply(getStrength(level));
            pull.setY(0.15);

            victim.setVelocity(pull);
            victim.getWorld().playSound(victim.getLocation(), Sound.ENTITY_FISHING_BOBBER_RETRIEVE, 0.8f, 0.6f);
        }, 1L);
    }

    public double getStrength(int level) {
        switch (level) {
            case 1: return 0.4D;
            case 2: return 0.5D;
            case 3: return 0.6D;
            default: throw new ArrayIndexOutOfBoundsException();
        }
    }
}
