package com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.actives;

import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.KitPvP;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.KitPvPPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.Active;
import com.orbitmines.archive.minecraft._2019.utils.cooldown.Cooldown;
import org.bukkit.*;
import org.bukkit.block.Block;
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

    /* 5 seconds for all levels */
    private static final int DURATION_TICKS = 100;

    private static final double PULL_RADIUS = 8.0;
    private static final double PULL_STRENGTH = 0.45;
    private static final double MOVE_SPEED = 0.15;

    @Override
    public void trigger(PlayerInteractEvent event, KitPvPPlayer omp, int level) {
        Player player = omp.bukkit();
        KitPvP kitPvP = (KitPvP) KitPvP.getInstance();

        /* Spawn tornado at where the player is looking (raycast 10 blocks) */
        Location center = player.getTargetBlock(null, 10).getLocation().add(0.5, 1, 0.5);
        center.setWorld(player.getWorld());

        omp.playSound(Sound.ENTITY_WIND_CHARGE_WIND_BURST);

        new BukkitRunnable() {
            int tick = 0;
            /* Tornado wanders with a drifting direction */
            double moveAngle = ThreadLocalRandom.current().nextDouble(Math.PI * 2);
            double cx = center.getX();
            double cz = center.getZ();

            @Override
            public void run() {
                if (tick >= DURATION_TICKS || !player.isOnline()) {
                    cancel();
                    return;
                }

                World world = center.getWorld();
                ThreadLocalRandom r = ThreadLocalRandom.current();

                /* Wander: drift the movement angle gradually */
                moveAngle += r.nextDouble(-0.4, 0.4);
                cx += Math.cos(moveAngle) * MOVE_SPEED;
                cz += Math.sin(moveAngle) * MOVE_SPEED;

                /* Find ground level at current position */
                double baseY = center.getY();
                Block ground = world.getBlockAt((int) cx, (int) baseY - 1, (int) cz);
                if (ground.getType() == Material.AIR) {
                    for (int dy = 2; dy < 10; dy++) {
                        Block below = world.getBlockAt((int) cx, (int) baseY - dy, (int) cz);
                        if (below.getType().isSolid()) {
                            baseY = below.getY() + 1;
                            break;
                        }
                    }
                }

                double tornadoX = cx;
                double tornadoZ = cz;
                double tornadoY = baseY;

                /* Particle funnel — non-uniform, natural look */
                double height = 10.0;
                double baseRadius = 4.0;
                double topRadius = 0.6;

                for (int i = 0; i < 60; i++) {
                    double y = r.nextDouble(0, height);
                    double t = y / height;
                    /* Radius tapers from base to top */
                    double radius = baseRadius * (1 - t) + topRadius * t;
                    /* Add randomness to radius for organic shape */
                    radius *= r.nextDouble(0.6, 1.3);

                    double angle = r.nextDouble(Math.PI * 2);

                    double px = tornadoX + Math.cos(angle) * radius;
                    double pz = tornadoZ + Math.sin(angle) * radius;

                    world.spawnParticle(Particle.CLOUD,
                            px, tornadoY + y, pz,
                            0, 0, 0, 0, 0);
                }

                /* Additional dust/smoke particles around the base */
                for (int i = 0; i < 15; i++) {
                    double angle = r.nextDouble(Math.PI * 2);
                    double dist = r.nextDouble(1.0, baseRadius + 1.0);
                    world.spawnParticle(Particle.CAMPFIRE_COSY_SMOKE,
                            tornadoX + Math.cos(angle) * dist,
                            tornadoY + r.nextDouble(0, 1.5),
                            tornadoZ + Math.sin(angle) * dist,
                            0, 0, 0.05, 0, 0);
                }

                /* Spawn environmental debris every 4 ticks (more frequent, more blocks) */
                if (tick % 4 == 0) {
                    int debrisCount = 2 + r.nextInt(3);
                    for (int d = 0; d < debrisCount; d++) {
                        /* Pick up actual blocks from the ground nearby */
                        int dx = r.nextInt(-4, 5);
                        int dz = r.nextInt(-4, 5);
                        Block block = world.getBlockAt((int) tornadoX + dx, (int) tornadoY - 1, (int) tornadoZ + dz);

                        Material mat;
                        if (block.getType().isSolid() && block.getType() != Material.BEDROCK && block.getType() != Material.BARRIER) {
                            mat = block.getType();
                        } else {
                            continue;
                        }

                        double spawnAngle = r.nextDouble(Math.PI * 2);
                        double spawnDist = r.nextDouble(0.5, 3.0);
                        double spawnY = r.nextDouble(1, 6);

                        Location debrisLoc = new Location(world,
                                tornadoX + Math.cos(spawnAngle) * spawnDist,
                                tornadoY + spawnY,
                                tornadoZ + Math.sin(spawnAngle) * spawnDist);

                        FallingBlock fb = world.spawnFallingBlock(debrisLoc, mat.createBlockData());
                        fb.setDropItem(false);
                        fb.setCancelDrop(true);
                        fb.setHurtEntities(false);
                        fb.setGravity(false);

                        /* Orbit the debris around the funnel */
                        double startAngle = spawnAngle;
                        double orbitRadius = spawnDist;
                        double orbitY = spawnY;
                        new BukkitRunnable() {
                            int debrisTick = 0;
                            double a = startAngle;
                            double rad = orbitRadius;
                            double dy = orbitY;

                            @Override
                            public void run() {
                                if (debrisTick >= 40 || fb.isDead()) {
                                    fb.remove();
                                    cancel();
                                    return;
                                }

                                a += 0.3 + r.nextDouble(0, 0.15);
                                rad += r.nextDouble(-0.05, 0.05);
                                dy += r.nextDouble(-0.05, 0.15);

                                fb.setVelocity(new Vector(
                                        Math.cos(a) * 0.4,
                                        r.nextDouble(-0.02, 0.1),
                                        Math.sin(a) * 0.4
                                ));

                                debrisTick++;
                            }
                        }.runTaskTimer(kitPvP.getPlugin(), 0, 1);
                    }
                }

                /* Pull in nearby players */
                Location tornadoLoc = new Location(world, tornadoX, tornadoY, tornadoZ);
                for (Player nearby : world.getPlayers()) {
                    if (nearby.equals(player))
                        continue;

                    double distance = nearby.getLocation().distance(tornadoLoc);
                    if (distance > PULL_RADIUS || distance < 0.5)
                        continue;

                    /* Pull toward center + upward */
                    Vector pull = tornadoLoc.toVector().subtract(nearby.getLocation().toVector()).normalize().multiply(PULL_STRENGTH);
                    pull.setY(pull.getY() + 0.2);
                    nearby.setVelocity(nearby.getVelocity().add(pull));

                    /* Damage every 20 ticks (1 second) */
                    if (tick % 20 == 0) {
                        nearby.damage(2.0, player);
                    }
                }

                /* Sound every half second */
                if (tick % 10 == 0) {
                    world.playSound(tornadoLoc, Sound.ENTITY_WIND_CHARGE_WIND_BURST, 1.5f, 0.4f);
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
