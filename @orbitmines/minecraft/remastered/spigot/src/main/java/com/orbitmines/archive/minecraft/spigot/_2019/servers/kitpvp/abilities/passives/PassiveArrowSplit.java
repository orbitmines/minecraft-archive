package com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.passives;

import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.Passive;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.events.KitEvent;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.util.Vector;

import java.util.concurrent.ThreadLocalRandom;

public class PassiveArrowSplit implements Passive.Handler<ProjectileHitEvent> {

    @Override
    public void trigger(KitEvent<ProjectileHitEvent> passiveEvent, ProjectileHitEvent event, int level) {
        /* Arrow split is handled at shoot time, not hit time. See triggerOnShoot(). */
    }

    public void triggerOnShoot(EntityShootBowEvent event, int level) {
        Entity projectile = event.getProjectile();
        if (!(projectile instanceof Arrow))
            return;

        Arrow original = (Arrow) projectile;
        Vector velocity = original.getVelocity();
        int extraArrows = getExtraArrows(level);

        for (int i = 0; i < extraArrows; i++) {
            ThreadLocalRandom r = ThreadLocalRandom.current();
            Vector spread = velocity.clone().add(new Vector(
                r.nextDouble(-0.5, 0.5),
                r.nextDouble(-0.5, 0.5),
                r.nextDouble(-0.5, 0.5)
            ));

            Arrow extra = original.getWorld().spawnArrow(
                original.getLocation(),
                spread.normalize(),
                (float) velocity.length(),
                0
            );
            extra.setShooter(original.getShooter());
            extra.setDamage(original.getDamage());
        }
    }

    public int getExtraArrows(int level) {
        switch (level) {
            case 1: return 4;
            case 2: return 6;
            case 3: return 8;
            default: throw new ArrayIndexOutOfBoundsException();
        }
    }
}
