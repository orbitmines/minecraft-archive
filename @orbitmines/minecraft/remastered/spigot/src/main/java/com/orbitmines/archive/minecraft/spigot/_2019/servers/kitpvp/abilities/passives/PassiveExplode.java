package com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.passives;

import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.Passive;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.events.KitEvent;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.ProjectileHitEvent;

public class PassiveExplode implements Passive.Handler<ProjectileHitEvent> {

    @Override
    public void trigger(KitEvent<ProjectileHitEvent> passiveEvent, ProjectileHitEvent event, int level) {
        Location loc = event.getEntity().getLocation();
        Player source = passiveEvent.getPlayer() != null ? passiveEvent.getPlayer().bukkit() : null;

        /* Explosion effect only - no block damage */
        loc.getWorld().createExplosion(loc, 3.0f, false, false, source);
    }
}
