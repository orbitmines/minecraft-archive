package com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.events;

/*
 * OrbitMines - @author Fadi Shawki - 2018
 */

import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.KitPvP;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.KitPvPPlayer;
import com.orbitmines.archive.minecraft._2019.utils.RandomUtils;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.entity.EntityNms;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class DeathEvent implements Listener {

    private KitPvP server;

    public DeathEvent(KitPvP server) {
        this.server = server;
    }

    /* Intercept lethal damage to prevent actual death — in 26.1 PlayerDeathEvent + setHealth
       leaves the entity in a corrupt state where the tracker stops sending movement updates. */
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onLethalDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player))
            return;

        Player bukkit = (Player) event.getEntity();
        if (bukkit.getHealth() - event.getFinalDamage() > 0)
            return;

        /* This hit would kill — prevent it */
        event.setCancelled(true);

        KitPvPPlayer player = server.getPlayer(bukkit);

        if (player.getSelectedKit() == null || player.isSpectator())
            return;

        /* Find killer */
        KitPvPPlayer playerKiller = null;
        boolean shotByArrow = false;
        if (event instanceof EntityDamageByEntityEvent) {
            EntityDamageByEntityEvent edbee = (EntityDamageByEntityEvent) event;
            if (edbee.getDamager() instanceof Player) {
                playerKiller = server.getPlayer((Player) edbee.getDamager());
            } else if (edbee.getDamager() instanceof Arrow) {
                Arrow arrow = (Arrow) edbee.getDamager();
                if (arrow.getShooter() instanceof Player) {
                    playerKiller = server.getPlayer((Player) arrow.getShooter());
                    shotByArrow = true;
                }
            }
        }

        /* Handle Kill */
        if (playerKiller != null)
            playerKiller.processKill(null, player);

        /* Handle Death */
        player.processDeath(null, playerKiller, shotByArrow);

        /* Clear Drops & Inventory */
        player.clearFullInventory();
        player.clearPotionEffects();

        /* Set Health */
        server.getNms().entity().setAttribute(bukkit, EntityNms.Attribute.MAX_HEALTH, 20D);
        bukkit.setHealth(20D);

        new BukkitRunnable() {
            @Override
            public void run() {
                /* Teleport to Spawn */
                player.teleport(RandomUtils.randomFrom(server.getSpawns()));
                /* Clear Velocity */
                player.setVelocity(new Vector(0, 0, 0));
                /* Clear Fire ticks */
                player.setFireTicks(0);
                /* Give Lobby Kit */
                server.getLobbyKit().copyToInventory(player);
                player.getInventory().setHeldItemSlot(4);
            }
        }.runTaskLater(server.getPlugin(), 1);
    }
}
