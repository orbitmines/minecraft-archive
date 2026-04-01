package com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.events;

/*
 * OrbitMines - @author Fadi Shawki - 2018
 */

import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.KitPvP;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.KitPvPPlayer;
import com.orbitmines.archive.minecraft._2019.utils.RandomUtils;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.entity.EntityNms;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class DeathEvent implements Listener {

    private KitPvP server;

    public DeathEvent(KitPvP server) {
        this.server = server;
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        /* Clear Drops */
        event.setDroppedExp(0);
        event.getDrops().clear();

        KitPvPPlayer player = server.getPlayer(event.getEntity());

        KitPvPPlayer playerKiller = player.getKiller() != null ? server.getPlayer(player.getKiller()) : null;
        /* Handle Kill */
        if (playerKiller != null)
            playerKiller.processKill(event, player);

        /* Handle Death */
        player.processDeath(event, playerKiller);

        /* Clear Inventory & Potion Effects */
        player.clearFullInventory();
        player.clearPotionEffects();

        /* Set Health */
        server.getNms().entity().setAttribute(player.bukkit(), EntityNms.Attribute.MAX_HEALTH, 20D);
        player.setHealth(20D);

        new BukkitRunnable() {
            @Override
            public void run() {
                /* Teleport to Spawn */
                player.teleport(RandomUtils.randomFrom(server.getSpawns()));
                /* Clear Velocity */
                player.setVelocity(new Vector(0, 0, 0));
                /* Clear Fire ticks */
                player.setFireTicks(0);
                /* Clear Arrows */
                //TODO
//                server.getNms().entity().clearArrowsInBody(player.bukkit());
                /* Give Lobby Kit */
                server.getLobbyKit().copyToInventory(player);
                player.getInventory().setHeldItemSlot(4);
            }
        }.runTaskLater(server, 1);
    }
}
