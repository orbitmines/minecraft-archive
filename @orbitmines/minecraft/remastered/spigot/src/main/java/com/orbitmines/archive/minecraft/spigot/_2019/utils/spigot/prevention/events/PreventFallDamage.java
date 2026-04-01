package com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.prevention.events;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.prevention.PreventionEvent;
import org.bukkit.World;
import org.bukkit.event.entity.EntityDamageEvent;

public class PreventFallDamage extends PreventionEvent<EntityDamageEvent> {

    public PreventFallDamage(World world) {
        super(world);
    }

    @Override
    protected boolean shouldBeCancelled(EntityDamageEvent event, World world) {
        return event.getCause() == EntityDamageEvent.DamageCause.FALL;
    }

    @Override
    protected World getWorld(EntityDamageEvent event) {
        return event.getEntity().getWorld();
    }

    @Override
    public Class<EntityDamageEvent> getEventClass() {
        return EntityDamageEvent.class;
    }
}
