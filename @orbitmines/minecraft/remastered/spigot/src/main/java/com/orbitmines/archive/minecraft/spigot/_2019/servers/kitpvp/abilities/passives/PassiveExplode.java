package com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.passives;

import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.Passive;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.events.KitEvent;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.entity.ProjectileHitEvent;

public class PassiveExplode implements Passive.Handler<ProjectileHitEvent> {

    @Override
    public void trigger(KitEvent<ProjectileHitEvent> passiveEvent, ProjectileHitEvent event, int level) {
        Location loc = event.getEntity().getLocation();
        Player source = passiveEvent.getPlayer() != null ? passiveEvent.getPlayer().bukkit() : null;

        /* Spawn TNT at arrow impact location */
        TNTPrimed tnt = loc.getWorld().spawn(loc, TNTPrimed.class);
        tnt.setFuseTicks(0);
        tnt.setIsIncendiary(false);
        if (source != null)
            tnt.setSource(source);
    }
}
