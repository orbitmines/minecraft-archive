package com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.actives;

import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.KitPvP;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.KitPvPPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.Active;
import com.orbitmines.archive.minecraft._2019.utils.cooldown.Cooldown;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
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

        /* Main fire block */
        spawnFireBlock(eyeLoc, direction);

        /* Spray blocks - reduced spread */
        int sprayCount = getSprayCount(level);
        ThreadLocalRandom r = ThreadLocalRandom.current();
        for (int i = 0; i < sprayCount; i++) {
            Vector spray = direction.clone().add(new Vector(
                r.nextDouble(-0.4, 0.4),
                r.nextDouble(-0.2, 0.2),
                r.nextDouble(-0.4, 0.4)
            ));
            spawnFireBlock(eyeLoc, spray);
        }

        omp.playSound(Sound.ENTITY_BLAZE_SHOOT);

        /* Extinguish any fire blocks placed after a delay */
        new BukkitRunnable() {
            @Override
            public void run() {
                Location center = player.getLocation();
                int radius = 8;
                for (int x = -radius; x <= radius; x++) {
                    for (int y = -3; y <= 5; y++) {
                        for (int z = -radius; z <= radius; z++) {
                            Block block = center.getWorld().getBlockAt(
                                center.getBlockX() + x,
                                center.getBlockY() + y,
                                center.getBlockZ() + z
                            );
                            if (block.getType() == Material.FIRE) {
                                block.setType(Material.AIR);
                            }
                        }
                    }
                }
            }
        }.runTaskLater(kitPvP.getPlugin(), 80);
    }

    private void spawnFireBlock(Location loc, Vector velocity) {
        FallingBlock fb = loc.getWorld().spawnFallingBlock(loc, Material.FIRE.createBlockData());
        fb.setVelocity(velocity);
        fb.setDropItem(false);
        fb.setHurtEntities(true);
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
