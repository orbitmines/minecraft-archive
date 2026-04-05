package com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.events;

import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.KitPvP;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.KitPvPPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.kit.KitPvPKit;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.kit.kits._2015.KitFisherman;
import org.bukkit.Sound;
import org.bukkit.entity.FishHook;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.util.Vector;

public class FishermanRodEvent implements Listener {

    private final KitPvP server;

    public FishermanRodEvent(KitPvP server) {
        this.server = server;
    }

    /* Bobber hits a player in flight — knockback them upward and away */
    @EventHandler(priority = EventPriority.HIGH)
    public void onBobberHit(ProjectileHitEvent event) {
        if (!(event.getEntity() instanceof FishHook))
            return;

        if (!(event.getHitEntity() instanceof Player))
            return;

        FishHook hook = (FishHook) event.getEntity();
        if (!(hook.getShooter() instanceof Player))
            return;

        Player fisher = (Player) hook.getShooter();
        KitPvPPlayer omp = server.getPlayer(fisher);
        if (omp == null)
            return;

        KitPvPKit.Level selectedKit = omp.getSelectedKit();
        if (selectedKit == null || selectedKit.getHandler().getId() != KitFisherman.ID)
            return;

        Player hit = (Player) event.getHitEntity();

        Vector direction = hit.getLocation().toVector().subtract(fisher.getLocation().toVector()).normalize();
        direction.setY(0.8);
        hit.setVelocity(direction.multiply(2.4));
        hit.getWorld().playSound(hit.getLocation(), Sound.ENTITY_FISHING_BOBBER_RETRIEVE, 1.0f, 0.8f);
    }

    /* Reel in — pull the caught player toward the fisherman */
    @EventHandler(priority = EventPriority.HIGH)
    public void onFish(PlayerFishEvent event) {
        Player fisher = event.getPlayer();

        KitPvPPlayer omp = server.getPlayer(fisher);
        if (omp == null)
            return;

        KitPvPKit.Level selectedKit = omp.getSelectedKit();
        if (selectedKit == null || selectedKit.getHandler().getId() != KitFisherman.ID)
            return;

        if (!(event.getCaught() instanceof Player))
            return;

        Player caught = (Player) event.getCaught();

        if (event.getState() == PlayerFishEvent.State.CAUGHT_ENTITY) {
            Vector pull = fisher.getLocation().toVector().subtract(caught.getLocation().toVector());
            double distance = pull.length();

            if (distance > 0.5) {
                pull.normalize();
                double strength = Math.min(1.25, 0.4 + distance * 0.075);
                pull.multiply(strength);
                pull.setY(0.25);
                caught.setVelocity(pull);
                caught.getWorld().playSound(caught.getLocation(), Sound.ENTITY_FISHING_BOBBER_RETRIEVE, 1.0f, 1.2f);
            }
        }
    }
}
