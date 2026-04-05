package com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.actives;

import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.KitPvP;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.KitPvPPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.Active;
import com.orbitmines.archive.minecraft._2019.utils.cooldown.Cooldown;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class ActiveTNTLauncher implements Active.Handler {

    private final Cooldown cooldown = new Cooldown(7 * 1000);

    @Override
    public void trigger(PlayerInteractEvent event, KitPvPPlayer omp, int level) {
        Player player = omp.bukkit();
        Location loc = player.getLocation().add(0, 1, 0);
        Vector direction = player.getEyeLocation().getDirection().multiply(2);
        KitPvP kitPvP = (KitPvP) KitPvP.getInstance();

        /* Launch a visual-only TNT: use a FallingBlock that creates an explosion effect on landing */
        org.bukkit.entity.FallingBlock fb = loc.getWorld().spawnFallingBlock(loc, org.bukkit.Material.TNT.createBlockData());
        fb.setVelocity(direction);
        fb.setDropItem(false);
        fb.setHurtEntities(false);

        omp.playSound(Sound.ENTITY_TNT_PRIMED);

        int fuseTicks = getFuseTicks(level);

        /* After fuse ticks, create explosion effect (no block damage) */
        new BukkitRunnable() {
            @Override
            public void run() {
                Location explodeLoc = fb.getLocation();
                if (!fb.isDead()) fb.remove();

                /* Explosion effect only - no block damage */
                explodeLoc.getWorld().createExplosion(explodeLoc, 3.0f, false, false, player);
            }
        }.runTaskLater(kitPvP.getPlugin(), fuseTicks);
    }

    private int getFuseTicks(int level) {
        switch (level) {
            case 1: return 15;
            case 2: return 12;
            case 3: return 10;
            default: throw new ArrayIndexOutOfBoundsException();
        }
    }

    @Override
    public Cooldown getCooldown(int level) {
        return cooldown;
    }
}
