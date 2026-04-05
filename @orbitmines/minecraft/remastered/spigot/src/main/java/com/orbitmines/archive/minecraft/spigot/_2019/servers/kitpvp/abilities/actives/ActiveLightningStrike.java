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

public class ActiveLightningStrike implements Active.Handler {

    private final Cooldown[] cooldowns = new Cooldown[] {
            new Cooldown(20 * 1000),
            new Cooldown(18 * 1000),
            new Cooldown(15 * 1000)
    };

    private static final double AOE_RADIUS = 5.0;

    /* Damage per level */
    private static final double[] DAMAGE = { 4.0, 6.0, 8.0 };

    @Override
    public void trigger(PlayerInteractEvent event, KitPvPPlayer omp, int level) {
        Player player = omp.bukkit();
        KitPvP kitPvP = (KitPvP) KitPvP.getInstance();

        /* Strike at player's target location */
        Location targetLoc = player.getTargetBlock(null, 15).getLocation().add(0.5, 1, 0.5);
        targetLoc.setWorld(player.getWorld());

        /* Strike lightning (visual only, no fire) */
        player.getWorld().strikeLightningEffect(targetLoc);

        omp.playSound(Sound.ENTITY_LIGHTNING_BOLT_THUNDER);

        double damage = DAMAGE[level - 1];

        /* Damage nearby players */
        for (Player nearby : player.getWorld().getPlayers()) {
            if (nearby.equals(player))
                continue;

            if (nearby.getLocation().distance(targetLoc) > AOE_RADIUS)
                continue;

            nearby.damage(damage, player);
        }

        /* Second lightning strike after a short delay for dramatic effect */
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!player.isOnline())
                    return;

                player.getWorld().strikeLightningEffect(targetLoc);

                for (Player nearby : player.getWorld().getPlayers()) {
                    if (nearby.equals(player))
                        continue;

                    if (nearby.getLocation().distance(targetLoc) > AOE_RADIUS)
                        continue;

                    nearby.damage(damage * 0.5, player);
                }
            }
        }.runTaskLater(kitPvP.getPlugin(), 10);
    }

    @Override
    public Cooldown getCooldown(int level) {
        return cooldowns[level - 1];
    }
}
