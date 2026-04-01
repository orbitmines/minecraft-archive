package com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.prevention.events;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.prevention.PreventionEvent;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class PreventPvP extends PreventionEvent<EntityDamageByEntityEvent> {

    public PreventPvP(World world) {
        super(world);
    }

    @Override
    protected boolean shouldBeCancelled(EntityDamageByEntityEvent event, World world) {
        return event.getEntity() instanceof Player && event.getDamager() instanceof Player;
    }

    @Override
    protected World getWorld(EntityDamageByEntityEvent event) {
        return event.getEntity().getWorld();
    }

    @Override
    public Class<EntityDamageByEntityEvent> getEventClass() {
        return EntityDamageByEntityEvent.class;
    }
}
