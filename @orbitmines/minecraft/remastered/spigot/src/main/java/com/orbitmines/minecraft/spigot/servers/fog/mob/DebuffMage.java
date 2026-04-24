package com.orbitmines.minecraft.spigot.servers.fog.mob;

import com.orbitmines.minecraft.spigot.servers.fog.run.Run;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.WitherSkull;
import org.bukkit.entity.Witch;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

/** Undead mage that casts lingering slow fields (30s), withering skulls (5s cd). */
public class DebuffMage extends FoGMob {

    private long lastSkullTick;
    private long lastSlowTick;

    public DebuffMage(Run run) {
        super(run);
    }

    @Override public double baseHealth()     { return 36.0; }
    @Override public String translationKey() { return "mob.debuff_mage.name"; }
    @Override public String colorPrefix()    { return "§5"; }

    @Override
    public void spawn(Location at) {
        Witch witch = (Witch) at.getWorld().spawnEntity(at, EntityType.WITCH);
        witch.setMaxHealth(baseHealth());
        witch.setHealth(baseHealth());
        witch.setCustomName(displayName());
        witch.setCustomNameVisible(true);
        this.entity = witch;
        this.entityUuid = witch.getUniqueId();
    }

    @Override
    public void tick() {
        if (!isAlive()) return;
        long now = entity.getWorld().getFullTime();
        Location loc = entity.getLocation();

        if (now - lastSlowTick > 400) {
            for (Player p : entity.getWorld().getPlayers()) {
                if (p.getLocation().distance(loc) < 10) {
                    p.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, 20 * 30, 1));
                }
            }
            entity.getWorld().spawnParticle(Particle.WITCH, loc, 30, 2, 1, 2, 0);
            lastSlowTick = now;
        }

        if (now - lastSkullTick > 100) {
            LivingEntity target = nearestPlayer(20.0);
            if (target != null) {
                WitherSkull skull = entity.getWorld().spawn(loc.add(0, 1, 0), WitherSkull.class);
                Vector dir = target.getEyeLocation().toVector().subtract(loc.toVector()).normalize().multiply(1.2);
                skull.setDirection(dir);
                skull.setShooter(entity);
                skull.setCharged(false);
            }
            lastSkullTick = now;
        }
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
