package com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.actives;

import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.KitPvP;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.KitPvPPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.Active;
import com.orbitmines.archive.minecraft._2019.utils.cooldown.Cooldown;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class ActiveBlazeInferno implements Active.Handler {

    private final Cooldown cooldown = new Cooldown(35 * 1000);

    @Override
    public void trigger(PlayerInteractEvent event, KitPvPPlayer omp, int level) {
        Player player = omp.bukkit();
        KitPvP kitPvP = (KitPvP) KitPvP.getInstance();
        Location center = player.getLocation().clone();

        /* Make invulnerable */
        player.setInvulnerable(true);
        player.setAllowFlight(true);
        player.setFlying(true);

        /* Apply regeneration */
        player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 60, 1));

        omp.playSound(Sound.ENTITY_BLAZE_AMBIENT);

        List<Block> fireBlocks = new ArrayList<>();

        /* Fly up with expanding particle circle, then spread fire from center */
        new BukkitRunnable() {
            int tick = 0;

            @Override
            public void run() {
                if (tick >= 30) {
                    cancel();

                    /* Spread fire outward from center over time */
                    spreadFire(player, kitPvP, center, fireBlocks);

                    /* End invulnerability after a short delay */
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            player.setInvulnerable(false);
                            player.setFlying(false);
                            player.setAllowFlight(false);
                        }
                    }.runTaskLater(kitPvP.getPlugin(), 20);

                    return;
                }

                /* Fly upward */
                if (tick < 20) {
                    player.setVelocity(new Vector(0, 0.3, 0));
                }

                /* Expanding fire particle circle */
                double radius = 1.0 + (tick * 0.15);
                Location pLoc = player.getLocation();
                int particles = 16 + tick;
                for (int i = 0; i < particles; i++) {
                    double angle = (2 * Math.PI * i) / particles;
                    double x = Math.cos(angle) * radius;
                    double z = Math.sin(angle) * radius;
                    pLoc.getWorld().spawnParticle(Particle.FLAME, pLoc.getX() + x, pLoc.getY(), pLoc.getZ() + z, 0, 0, 0, 0, 0);
                }

                tick++;
            }
        }.runTaskTimer(kitPvP.getPlugin(), 0, 1);
    }

    private void spreadFire(Player player, KitPvP kitPvP, Location center, List<Block> fireBlocks) {
        int maxRadius = 5;

        /* Spread fire ring by ring every 3 ticks */
        new BukkitRunnable() {
            int currentRadius = 0;

            @Override
            public void run() {
                if (currentRadius > maxRadius) {
                    cancel();

                    /* Extinguish all fire after 100 ticks */
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            for (Block block : fireBlocks) {
                                if (block.getType() == Material.FIRE) {
                                    block.setType(Material.AIR);
                                }
                            }
                        }
                    }.runTaskLater(kitPvP.getPlugin(), 100);
                    return;
                }

                /* Place fire at this radius ring */
                for (int x = -currentRadius; x <= currentRadius; x++) {
                    for (int z = -currentRadius; z <= currentRadius; z++) {
                        double dist = Math.sqrt(x * x + z * z);

                        /* Only this ring (between currentRadius-1 and currentRadius) */
                        if (currentRadius > 0 && dist < currentRadius - 1)
                            continue;
                        if (dist > currentRadius + 0.5)
                            continue;

                        /* Find ground level */
                        for (int y = 2; y >= -2; y--) {
                            Block check = center.getWorld().getBlockAt(
                                    center.getBlockX() + x,
                                    center.getBlockY() + y,
                                    center.getBlockZ() + z
                            );
                            Block above = center.getWorld().getBlockAt(
                                    center.getBlockX() + x,
                                    center.getBlockY() + y + 1,
                                    center.getBlockZ() + z
                            );

                            if (check.getType().isSolid() && above.getType() == Material.AIR) {
                                above.setType(Material.FIRE);
                                fireBlocks.add(above);
                                break;
                            }
                        }
                    }
                }

                center.getWorld().playSound(center, Sound.ENTITY_BLAZE_SHOOT, 1.0f, 0.5f + currentRadius * 0.1f);
                currentRadius++;
            }
        }.runTaskTimer(kitPvP.getPlugin(), 0, 3);
    }

    @Override
    public Cooldown getCooldown(int level) {
        return cooldown;
    }
}
