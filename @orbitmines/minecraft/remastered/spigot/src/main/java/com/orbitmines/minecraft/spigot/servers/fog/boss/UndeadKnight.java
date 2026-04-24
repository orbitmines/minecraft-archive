package com.orbitmines.minecraft.spigot.servers.fog.boss;

import com.orbitmines.minecraft.spigot.servers.fog.mob.DebuffMage;
import com.orbitmines.minecraft.spigot.servers.fog.mob.FireMage;
import com.orbitmines.minecraft.spigot.servers.fog.mob.FoGZombie;
import com.orbitmines.minecraft.spigot.servers.fog.run.Run;
import org.bukkit.Location;
import org.bukkit.boss.BarColor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.PiglinBrute;

import java.util.Random;

/**
 * First boss. Base Piglin Brute stand-in.
 *   - Every 30s: summons a random undead mage (FireMage or DebuffMage)
 *   - Every 15s: summons 2/3/4/5 zombies depending on hp %
 *   - Every 10s: pulls players in front of him
 *   - Below 50%: every 15s charges a random player knocking them up
 */
public class UndeadKnight extends Boss {

    private static final Random RANDOM = new Random();

    private long lastSummon;
    private long lastZombies;
    private long lastPull;
    private long lastCharge;

    public UndeadKnight(Run run) {
        super(run);
    }

    @Override public double baseHealth()     { return 300.0; }
    @Override public String translationKey() { return "boss.undead_knight.name"; }
    @Override public String colorPrefix()    { return "§4"; }

    @Override
    public void spawn(Location at) {
        PiglinBrute brute = (PiglinBrute) at.getWorld().spawnEntity(at, EntityType.PIGLIN_BRUTE);
        brute.setMaxHealth(baseHealth());
        brute.setHealth(baseHealth());
        brute.setCustomName(displayName());
        brute.setCustomNameVisible(true);
        this.entity = brute;
        this.entityUuid = brute.getUniqueId();
        initBossBar(displayName(), BarColor.RED);
        for (Player p : at.getWorld().getPlayers()) addViewer(p);
    }

    @Override
    public void tick() {
        if (!isAlive()) return;
        updateBar();
        long now = entity.getWorld().getFullTime();
        double ratio = entity.getHealth() / entity.getMaxHealth();

        if (now - lastSummon > 600) {
            if (RANDOM.nextBoolean())
                new FireMage(run).spawn(entity.getLocation());
            else
                new DebuffMage(run).spawn(entity.getLocation());
            lastSummon = now;
        }

        if (now - lastZombies > 300) {
            int count = ratio > 0.75 ? 2 : ratio > 0.5 ? 3 : ratio > 0.25 ? 4 : 5;
            for (int i = 0; i < count; i++) {
                FoGZombie z = new FoGZombie(run);
                z.spawn(entity.getLocation().add(RANDOM.nextInt(6) - 3, 0, RANDOM.nextInt(6) - 3));
            }
            lastZombies = now;
        }

        if (now - lastPull > 200) {
            for (Player p : entity.getWorld().getPlayers()) {
                if (p.getLocation().distance(entity.getLocation()) > 10) continue;
                org.bukkit.util.Vector dir = entity.getLocation().toVector().subtract(p.getLocation().toVector()).normalize().multiply(0.8);
                p.setVelocity(dir);
            }
            lastPull = now;
        }

        if (ratio < 0.5 && now - lastCharge > 300) {
            Player best = null;
            double bestDist = 16 * 16;
            for (Player p : entity.getWorld().getPlayers()) {
                double d = p.getLocation().distanceSquared(entity.getLocation());
                if (d < bestDist) { bestDist = d; best = p; }
            }
            if (best != null) {
                org.bukkit.util.Vector dir = best.getLocation().toVector().subtract(entity.getLocation().toVector()).normalize().multiply(2.0).setY(0.9);
                entity.setVelocity(dir);
                best.setVelocity(new org.bukkit.util.Vector(0, 1.4, 0));
            }
            lastCharge = now;
        }
    }
}
