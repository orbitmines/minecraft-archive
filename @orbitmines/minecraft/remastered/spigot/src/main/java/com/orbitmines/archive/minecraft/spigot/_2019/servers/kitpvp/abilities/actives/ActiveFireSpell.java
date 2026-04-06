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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class ActiveFireSpell implements Active.Handler {

    private final Cooldown cooldown = new Cooldown(1000);

    @Override
    public void trigger(PlayerInteractEvent event, KitPvPPlayer omp, int level) {
        Player player = omp.bukkit();
        Location eyeLoc = player.getEyeLocation();
        Vector direction = eyeLoc.getDirection().multiply(1.2);
        KitPvP kitPvP = (KitPvP) KitPvP.getInstance();

        List<FallingBlock> projectiles = new ArrayList<>();

        /* Main fire block */
        projectiles.add(spawnFireBlock(eyeLoc, direction));

        /* Spray blocks */
        int sprayCount = getSprayCount(level);
        ThreadLocalRandom r = ThreadLocalRandom.current();
        for (int i = 0; i < sprayCount; i++) {
            Vector spray = direction.clone().add(new Vector(
                r.nextDouble(-0.4, 0.4),
                r.nextDouble(-0.2, 0.2),
                r.nextDouble(-0.4, 0.4)
            ));
            projectiles.add(spawnFireBlock(eyeLoc, spray));
        }

        omp.playSound(Sound.ENTITY_BLAZE_SHOOT);

        /* Track projectile positions with particle trail, then clean up fire at landing sites */
        List<Location> landingSites = new ArrayList<>();

        new BukkitRunnable() {
            int tick = 0;

            @Override
            public void run() {
                /* Spawn flame particles along each projectile's path */
                for (FallingBlock fb : projectiles) {
                    if (fb.isDead() || !fb.isValid()) {
                        continue;
                    }
                    Location loc = fb.getLocation();
                    loc.getWorld().spawnParticle(Particle.FLAME, loc, 2, 0.1, 0.1, 0.1, 0);
                }

                /* After 40 ticks all projectiles should have landed — record positions and remove entities */
                if (tick >= 40) {
                    cancel();

                    for (FallingBlock fb : projectiles) {
                        landingSites.add(fb.getLocation().clone());
                        if (!fb.isDead()) fb.remove();
                    }

                    /* Clean up fire around each landing site after delay */
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            for (Location site : landingSites) {
                                int radius = 2;
                                for (int x = -radius; x <= radius; x++) {
                                    for (int y = -2; y <= 2; y++) {
                                        for (int z = -radius; z <= radius; z++) {
                                            Block block = site.getWorld().getBlockAt(
                                                site.getBlockX() + x,
                                                site.getBlockY() + y,
                                                site.getBlockZ() + z
                                            );
                                            if (block.getType() == Material.FIRE) {
                                                block.setType(Material.AIR);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }.runTaskLater(kitPvP.getPlugin(), 60);

                    return;
                }

                tick++;
            }
        }.runTaskTimer(kitPvP.getPlugin(), 0, 1);
    }

    private FallingBlock spawnFireBlock(Location loc, Vector velocity) {
        FallingBlock fb = loc.getWorld().spawnFallingBlock(loc, Material.FIRE.createBlockData());
        fb.setVelocity(velocity);
        fb.setDropItem(false);
        fb.setHurtEntities(true);
        return fb;
    }

    private int getSprayCount(int level) {
        switch (level) {
            case 1: return 1;
            case 2: return 3;
            case 3: return 5;
            default: throw new ArrayIndexOutOfBoundsException();
        }
    }

    @Override
    public Cooldown getCooldown(int level) {
        return cooldown;
    }
}
