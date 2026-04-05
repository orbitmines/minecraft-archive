package com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.actives;

import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.KitPvP;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.KitPvPPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.Active;
import com.orbitmines.archive.minecraft._2019.utils.cooldown.Cooldown;
import org.bukkit.*;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.concurrent.ThreadLocalRandom;

public class ActiveTornado implements Active.Handler {

    private final Cooldown[] cooldowns = new Cooldown[] {
            new Cooldown(45 * 1000),
            new Cooldown(45 * 1000),
            new Cooldown(45 * 1000)
    };

    /* Duration in ticks per level: 15s, 17s, 20s */
    private static final int[] DURATION_TICKS = { 300, 340, 400 };

    private static final double PULL_RADIUS = 6.0;
    private static final double PULL_STRENGTH = 0.35;

    private static final Material[] DEBRIS_MATERIALS = {
            Material.DIRT, Material.GRAVEL, Material.SAND, Material.COBBLESTONE,
            Material.OAK_LEAVES, Material.DEAD_BUSH
    };

    @Override
    public void trigger(PlayerInteractEvent event, KitPvPPlayer omp, int level) {
        Player player = omp.bukkit();
        KitPvP kitPvP = (KitPvP) KitPvP.getInstance();

        /* Spawn tornado at where the player is looking (raycast 10 blocks) */
        Location targetLoc = player.getTargetBlock(null, 10).getLocation().add(0.5, 1, 0.5);
        targetLoc.setWorld(player.getWorld());

        int duration = DURATION_TICKS[level - 1];

        omp.playSound(Sound.ENTITY_WIND_CHARGE_WIND_BURST);

        new BukkitRunnable() {
            int tick = 0;

            @Override
            public void run() {
                if (tick >= duration || !player.isOnline()) {
                    cancel();
                    return;
                }

                World world = targetLoc.getWorld();

                /* Tornado particle spiral - two interleaved helixes */
                double height = 6.0;
                for (int i = 0; i < 40; i++) {
                    double y = (height * i) / 40.0;
                    double radius = 0.3 + (y / height) * 2.5;
                    double angle = (tick * 0.3) + (y * 2.0);

                    double x1 = Math.cos(angle) * radius;
                    double z1 = Math.sin(angle) * radius;
                    double x2 = Math.cos(angle + Math.PI) * radius;
                    double z2 = Math.sin(angle + Math.PI) * radius;

                    world.spawnParticle(Particle.CLOUD,
                            targetLoc.getX() + x1, targetLoc.getY() + y, targetLoc.getZ() + z1,
                            0, 0, 0, 0, 0);
                    world.spawnParticle(Particle.CLOUD,
                            targetLoc.getX() + x2, targetLoc.getY() + y, targetLoc.getZ() + z2,
                            0, 0, 0, 0, 0);
                }

                /* Spawn floating debris blocks every 10 ticks */
                if (tick % 10 == 0) {
                    ThreadLocalRandom r = ThreadLocalRandom.current();
                    Material mat = DEBRIS_MATERIALS[r.nextInt(DEBRIS_MATERIALS.length)];
                    double angle = r.nextDouble(Math.PI * 2);
                    double dist = r.nextDouble(0.5, 2.0);

                    Location debrisLoc = targetLoc.clone().add(Math.cos(angle) * dist, r.nextDouble(1, 4), Math.sin(angle) * dist);
                    FallingBlock fb = world.spawnFallingBlock(debrisLoc, mat.createBlockData());
                    fb.setDropItem(false);
                    fb.setCancelDrop(true);
                    fb.setHurtEntities(false);
                    fb.setGravity(false);

                    /* Spin the debris around */
                    new BukkitRunnable() {
                        int debrisTick = 0;

                        @Override
                        public void run() {
                            if (debrisTick >= 30 || fb.isDead()) {
                                fb.remove();
                                cancel();
                                return;
                            }

                            double a = (debrisTick * 0.4) + angle;
                            double r2 = dist + (debrisTick * 0.05);
                            fb.setVelocity(new Vector(
                                    Math.cos(a) * 0.3,
                                    0.05,
                                    Math.sin(a) * 0.3
                            ));

                            debrisTick++;
                        }
                    }.runTaskTimer(kitPvP.getPlugin(), 0, 1);
                }

                /* Pull in nearby players */
                for (Player nearby : world.getPlayers()) {
                    if (nearby.equals(player))
                        continue;

                    double distance = nearby.getLocation().distance(targetLoc);
                    if (distance > PULL_RADIUS || distance < 0.5)
                        continue;

                    /* Pull toward center + upward */
                    Vector pull = targetLoc.toVector().subtract(nearby.getLocation().toVector()).normalize().multiply(PULL_STRENGTH);
                    pull.setY(pull.getY() + 0.15);
                    nearby.setVelocity(nearby.getVelocity().add(pull));

                    /* Damage every 20 ticks (1 second) */
                    if (tick % 20 == 0) {
                        nearby.damage(2.0, player);
                    }
                }

                /* Sound every second */
                if (tick % 20 == 0) {
                    world.playSound(targetLoc, Sound.ENTITY_WIND_CHARGE_WIND_BURST, 1.0f, 0.5f);
                }

                tick++;
            }
        }.runTaskTimer(kitPvP.getPlugin(), 0, 1);
    }

    @Override
    public Cooldown getCooldown(int level) {
        return cooldowns[level - 1];
    }
}
