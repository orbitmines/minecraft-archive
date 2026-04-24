package com.orbitmines.minecraft.spigot.servers.fog.mob;

import com.orbitmines.minecraft.spigot.servers.fog.run.Run;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Blaze;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.SmallFireball;
import org.bukkit.util.Vector;

/**
 * Undead mage that throws fire balls (4s cd), leaves a fire trail for 10s,
 * and explodes on death. POC: blaze-based stand-in.
 */
public class FireMage extends FoGMob {

    private long lastFireballTick;

    public FireMage(Run run) {
        super(run);
    }

    @Override public double baseHealth()     { return 40.0; }
    @Override public String translationKey() { return "mob.fire_mage.name"; }
    @Override public String colorPrefix()    { return "§c"; }

    @Override
    public void spawn(Location at) {
        Blaze blaze = (Blaze) at.getWorld().spawnEntity(at, EntityType.BLAZE);
        blaze.setMaxHealth(baseHealth());
        blaze.setHealth(baseHealth());
        blaze.setCustomName(displayName());
        blaze.setCustomNameVisible(true);
        this.entity = blaze;
        this.entityUuid = blaze.getUniqueId();
    }

    @Override
    public void tick() {
        if (!isAlive()) return;
        Location loc = entity.getLocation();
        loc.getWorld().spawnParticle(Particle.FLAME, loc, 4, 0.2, 0.1, 0.2, 0.01);

        long now = loc.getWorld().getFullTime();
        if (now - lastFireballTick < 80) return;
        LivingEntity target = nearestPlayer(24.0);
        if (target == null) return;
        Vector dir = target.getEyeLocation().toVector().subtract(loc.toVector()).normalize().multiply(1.4);
        SmallFireball fb = loc.getWorld().spawn(loc.add(0, 1, 0), SmallFireball.class);
        fb.setDirection(dir);
        fb.setShooter((Blaze) entity);
        lastFireballTick = now;
    }

    private LivingEntity nearestPlayer(double range) {
        Player best = null;
        double bestDistSq = range * range;
        for (Player p : entity.getWorld().getPlayers()) {
            double d = p.getLocation().distanceSquared(entity.getLocation());
            if (d < bestDistSq) { bestDistSq = d; best = p; }
        }
        return best;
    }
}
