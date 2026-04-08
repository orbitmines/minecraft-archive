package com.orbitmines.archive.minecraft.spigot._2019.servers.creative.events;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft.spigot._2019.servers.creative.Creative;
import com.orbitmines.archive.minecraft.spigot._2019.servers.creative.CreativePlayer;
import com.orbitmines.archive.minecraft.spigot._2019.servers.creative.CreativeWorld;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.chat.ActionBar;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityPlaceEvent;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.hanging.HangingPlaceEvent;
import org.bukkit.event.player.*;
import org.bukkit.event.vehicle.VehicleDamageEvent;
import org.bukkit.event.vehicle.VehicleDestroyEvent;

public class WorldProtectionEvent implements Listener {

    private final Creative server;

    public WorldProtectionEvent(Creative server) {
        this.server = server;
    }

    /* Block Break */
    @EventHandler(priority = EventPriority.LOW)
    public void onBlockBreak(BlockBreakEvent event) {
        if (shouldCancel(event.getPlayer()))
            event.setCancelled(true);
    }

    /* Block Place */
    @EventHandler(priority = EventPriority.LOW)
    public void onBlockPlace(BlockPlaceEvent event) {
        if (shouldCancel(event.getPlayer()))
            event.setCancelled(true);
    }

    /* Bucket Empty */
    @EventHandler(priority = EventPriority.LOW)
    public void onBucketEmpty(PlayerBucketEmptyEvent event) {
        if (shouldCancel(event.getPlayer()))
            event.setCancelled(true);
    }

    /* Bucket Fill */
    @EventHandler(priority = EventPriority.LOW)
    public void onBucketFill(PlayerBucketFillEvent event) {
        if (shouldCancel(event.getPlayer()))
            event.setCancelled(true);
    }

    /* All interact events — covers spawn eggs, item usage, block interaction, etc. */
    @EventHandler(priority = EventPriority.LOW)
    public void onInteract(PlayerInteractEvent event) {
        if (shouldCancel(event.getPlayer()))
            event.setCancelled(true);
    }

    /* Entity interact — item frames, armor stands, etc. */
    @EventHandler(priority = EventPriority.LOW)
    public void onInteractEntity(PlayerInteractEntityEvent event) {
        if (shouldCancel(event.getPlayer()))
            event.setCancelled(true);
    }

    /* Entity interact at — armor stand manipulation */
    @EventHandler(priority = EventPriority.LOW)
    public void onInteractAtEntity(PlayerInteractAtEntityEvent event) {
        if (shouldCancel(event.getPlayer()))
            event.setCancelled(true);
    }

    /* Entity place — boats, minecarts, armor stands, end crystals */
    @EventHandler(priority = EventPriority.LOW)
    public void onEntityPlace(EntityPlaceEvent event) {
        if (event.getPlayer() != null && shouldCancel(event.getPlayer()))
            event.setCancelled(true);
    }

    /* Entity damage by player — prevent hitting entities */
    @EventHandler(priority = EventPriority.LOW)
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        Entity damager = event.getDamager();
        if (damager instanceof Player && shouldCancel((Player) damager))
            event.setCancelled(true);
    }

    /* Hanging entity break (paintings, item frames) */
    @EventHandler(priority = EventPriority.LOW)
    public void onHangingBreak(HangingBreakByEntityEvent event) {
        Entity remover = event.getRemover();
        if (remover instanceof Player && shouldCancel((Player) remover))
            event.setCancelled(true);
    }

    /* Hanging entity place (paintings, item frames) */
    @EventHandler(priority = EventPriority.LOW)
    public void onHangingPlace(HangingPlaceEvent event) {
        if (event.getPlayer() != null && shouldCancel(event.getPlayer()))
            event.setCancelled(true);
    }

    /* Vehicle damage (boats, minecarts) */
    @EventHandler(priority = EventPriority.LOW)
    public void onVehicleDamage(VehicleDamageEvent event) {
        Entity attacker = event.getAttacker();
        if (attacker instanceof Player && shouldCancel((Player) attacker))
            event.setCancelled(true);
    }

    /* Vehicle destroy */
    @EventHandler(priority = EventPriority.LOW)
    public void onVehicleDestroy(VehicleDestroyEvent event) {
        Entity attacker = event.getAttacker();
        if (attacker instanceof Player && shouldCancel((Player) attacker))
            event.setCancelled(true);
    }

    /* Item drop */
    @EventHandler(priority = EventPriority.LOW)
    public void onItemDrop(PlayerDropItemEvent event) {
        if (shouldCancel(event.getPlayer()))
            event.setCancelled(true);
    }

    /* Armor stand manipulation */
    @EventHandler(priority = EventPriority.LOW)
    public void onArmorStandManipulate(PlayerArmorStandManipulateEvent event) {
        if (shouldCancel(event.getPlayer()))
            event.setCancelled(true);
    }

    /* Fishing — can hook entities */
    @EventHandler(priority = EventPriority.LOW)
    public void onFish(PlayerFishEvent event) {
        if (event.getCaught() != null && shouldCancel(event.getPlayer()))
            event.setCancelled(true);
    }

    private boolean shouldCancel(Player player) {
        try {
            CreativeWorld world = server.getWorldByBukkitWorld(player.getWorld());

            /* Not a creative world — allow (could be default/lobby world) */
            if (world == null)
                return false;

            /* Owner or member can build */
            if (world.canBuild(player.getUniqueId()))
                return false;

            /* Staff with op mode can build anywhere */
            CreativePlayer cp = server.getPlayer(player);
            if (cp != null && cp.isOpMode())
                return false;

            /* Not allowed — show bold ActionBar like claims */
            if (cp != null) {
                String ownerName = world.getOwnerName();
                new ActionBar(cp.bukkit(), () -> "§c§l" + cp.translate("creative", "player.world.protection.cant_build", ownerName + "§c§l"), 60).send();
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }
}
