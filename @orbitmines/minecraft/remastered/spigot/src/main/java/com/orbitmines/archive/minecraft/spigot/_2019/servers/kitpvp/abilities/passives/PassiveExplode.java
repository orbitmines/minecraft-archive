package com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.passives;

import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.Passive;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.events.KitEvent;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.entity.ProjectileHitEvent;

public class PassiveExplode implements Passive.Handler<ProjectileHitEvent> {

    @Override
    public void trigger(KitEvent<ProjectileHitEvent> passiveEvent, ProjectileHitEvent event, int level) {
        Location loc = event.getEntity().getLocation();

        TNTPrimed tnt = (TNTPrimed) loc.getWorld().spawnEntity(loc, EntityType.TNT);
        tnt.setFuseTicks(getFuseTicks(level));
        tnt.setIsIncendiary(false);
        if (passiveEvent.getPlayer() != null)
            tnt.setSource(passiveEvent.getPlayer().bukkit());
    }

    public int getFuseTicks(int level) {
        return 10;
    }
}
