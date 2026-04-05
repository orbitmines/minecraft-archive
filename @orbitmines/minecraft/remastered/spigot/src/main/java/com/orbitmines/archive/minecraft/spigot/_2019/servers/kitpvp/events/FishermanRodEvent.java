package com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.events;

import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.KitPvP;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.KitPvPPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.kit.KitPvPKit;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.kit.kits._2015.KitFisherman;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.util.Vector;

public class FishermanRodEvent implements Listener {

    private final KitPvP server;

    public FishermanRodEvent(KitPvP server) {
        this.server = server;
    }

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
            /* Bobber hit — knockback the caught player away from the fisherman */
            Vector direction = caught.getLocation().toVector().subtract(fisher.getLocation().toVector()).normalize();
            direction.setY(0.4);
            caught.setVelocity(direction.multiply(1.2));
            caught.getWorld().playSound(caught.getLocation(), Sound.ENTITY_FISHING_BOBBER_RETRIEVE, 1.0f, 0.8f);

            /* Strong pull toward the fisherman */
            server.getPlugin().getServer().getScheduler().runTaskLater(server.getPlugin(), () -> {
                if (!caught.isOnline() || !fisher.isOnline())
                    return;

                Vector pull = fisher.getLocation().toVector().subtract(caught.getLocation().toVector());
                double distance = pull.length();

                if (distance > 0.5) {
                    pull.normalize();
                    /* Scale pull strength with distance for strong ping-pong effect */
                    double strength = Math.min(2.5, 0.8 + distance * 0.15);
                    pull.multiply(strength);
                    pull.setY(0.5);
                    caught.setVelocity(pull);
                    caught.getWorld().playSound(caught.getLocation(), Sound.ENTITY_FISHING_BOBBER_RETRIEVE, 1.0f, 1.2f);
                }
            }, 10L);
        }
    }
}
