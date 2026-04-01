package com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.prevention.events;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.prevention.PreventionEvent;
import org.bukkit.World;
import org.bukkit.event.entity.EntityPickupItemEvent;

public class PreventItemPickup extends PreventionEvent<EntityPickupItemEvent> {

    public PreventItemPickup(World world) {
        super(world);
    }

    @Override
    protected boolean shouldBeCancelled(EntityPickupItemEvent event, World world) {
        return true;
    }

    @Override
    protected World getWorld(EntityPickupItemEvent event) {
        return event.getEntity().getWorld();
    }

    @Override
    public Class<EntityPickupItemEvent> getEventClass() {
        return EntityPickupItemEvent.class;
    }
}
