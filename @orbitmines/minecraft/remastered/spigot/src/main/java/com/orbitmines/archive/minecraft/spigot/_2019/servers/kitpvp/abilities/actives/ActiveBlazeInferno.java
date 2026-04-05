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

    private final Cooldown cooldown = new Cooldown(60 * 1000);

    @Override
    public void trigger(PlayerInteractEvent event, KitPvPPlayer omp, int level) {
        Player player = omp.bukkit();
        KitPvP kitPvP = (KitPvP) KitPvP.getInstance();
        Location startLoc = player.getLocation().clone();

        /* Make invulnerable */
        player.setInvulnerable(true);
        player.setAllowFlight(true);
        player.setFlying(true);

        /* Apply regeneration */
        player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 60, 1));

        omp.playSound(Sound.ENTITY_BLAZE_AMBIENT);

        /* Fly up animation with expanding fire particle circle */
        new BukkitRunnable() {
            int tick = 0;

            @Override
            public void run() {
                if (tick >= 30) {
                    cancel();

                    /* After flying up, create fire ring */
                    createFireRing(player, kitPvP, 5);

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
                Location center = player.getLocation();
                int particles = 16 + tick;
                for (int i = 0; i < particles; i++) {
                    double angle = (2 * Math.PI * i) / particles;
                    double x = Math.cos(angle) * radius;
                    double z = Math.sin(angle) * radius;
                    center.getWorld().spawnParticle(Particle.FLAME, center.getX() + x, center.getY(), center.getZ() + z, 0, 0, 0, 0, 0);
                }

                tick++;
            }
        }.runTaskTimer(kitPvP.getPlugin(), 0, 1);
    }

    private void createFireRing(Player player, KitPvP kitPvP, int radius) {
        Location center = player.getLocation();
        List<Block> fireBlocks = new ArrayList<>();

        /* Create ring of fire at the radius */
        int diameter = radius * 2 + 1;
        for (int x = -radius; x <= radius; x++) {
            for (int z = -radius; z <= radius; z++) {
                double dist = Math.sqrt(x * x + z * z);

                /* Ring: only at ~radius distance (between radius-1 and radius) */
                if (dist < radius - 1 || dist > radius + 0.5)
                    continue;

                Block block = center.getWorld().getBlockAt(
                    center.getBlockX() + x,
                    center.getBlockY(),
                    center.getBlockZ() + z
                );

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

        player.getWorld().playSound(center, Sound.ENTITY_BLAZE_SHOOT, 2.0f, 0.5f);

        /* Extinguish fire after 100 ticks */
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
    }

    @Override
    public Cooldown getCooldown(int level) {
        return cooldown;
    }
}
