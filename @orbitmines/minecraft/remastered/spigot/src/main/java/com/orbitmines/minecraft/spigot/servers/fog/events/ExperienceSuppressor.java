package com.orbitmines.minecraft.spigot.servers.fog.events;

import com.orbitmines.minecraft.spigot.servers.fog.FoG;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.event.entity.ExpBottleEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.entity.ExperienceOrb;

/**
 * Blocks all vanilla XP sources. The bar is managed via
 * {@link com.orbitmines.minecraft.spigot.servers.fog.FoGPlayer#updateExperienceBar()}.
 * XP bottles from crates push tracked XP via a different path.
 */
public class ExperienceSuppressor implements Listener {

    private final FoG server;

    public ExperienceSuppressor(FoG server) {
        this.server = server;
    }

    @EventHandler
    public void onExpChange(PlayerExpChangeEvent event) {
        event.setAmount(0);
    }

    @EventHandler
    public void onOrbTarget(EntityTargetLivingEntityEvent event) {
        /* Prevent XP orbs from homing on players (they're destroyed when ignored). */
        if (event.getEntity() instanceof ExperienceOrb) {
            event.setCancelled(true);
            event.getEntity().remove();
        }
    }

    @EventHandler
    public void onExpBottle(ExpBottleEvent event) {
        event.setExperience(0);
        event.setShowEffect(true);
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        event.setDroppedExp(0);
        event.setKeepLevel(true);
    }
}
