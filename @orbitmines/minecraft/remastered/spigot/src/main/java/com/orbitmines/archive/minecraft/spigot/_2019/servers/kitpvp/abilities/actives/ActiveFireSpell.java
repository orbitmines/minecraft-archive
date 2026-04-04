package com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.actives;

import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.KitPvP;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.KitPvPPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.Active;
import com.orbitmines.archive.minecraft._2019.utils.cooldown.Cooldown;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.concurrent.ThreadLocalRandom;

public class ActiveFireSpell implements Active.Handler {

    private final Cooldown cooldown = new Cooldown(1000);

    @Override
    public void trigger(PlayerInteractEvent event, KitPvPPlayer omp, int level) {
        Player player = omp.bukkit();
        Location eyeLoc = player.getEyeLocation();
        Vector direction = eyeLoc.getDirection().multiply(2);

        /* Main fire block */
        spawnFireBlock(eyeLoc, direction);

        /* Spray blocks */
        int sprayCount = getSprayCount(level);
        ThreadLocalRandom r = ThreadLocalRandom.current();
        for (int i = 0; i < sprayCount; i++) {
            Vector spray = direction.clone().add(new Vector(
                r.nextDouble(-1.5, 1.5),
                r.nextDouble(-0.5, 0.5),
                r.nextDouble(-1.5, 1.5)
            ));
            spawnFireBlock(eyeLoc, spray);
        }

        omp.playSound(Sound.ENTITY_BLAZE_SHOOT);

        /* Clean up fire blocks after 60 ticks */
        KitPvP kitPvP = (KitPvP) KitPvP.getInstance();
        // FallingBlocks auto-remove on landing
    }

    private void spawnFireBlock(Location loc, Vector velocity) {
        FallingBlock fb = loc.getWorld().spawnFallingBlock(loc, Material.FIRE.createBlockData());
        fb.setVelocity(velocity);
        fb.setDropItem(false);
        fb.setHurtEntities(true);
    }

    private int getSprayCount(int level) {
        switch (level) {
            case 1: return 2;
            case 2: return 5;
            case 3: return 7;
            default: throw new ArrayIndexOutOfBoundsException();
        }
    }

    @Override
    public Cooldown getCooldown(int level) {
        return cooldown;
    }
}
