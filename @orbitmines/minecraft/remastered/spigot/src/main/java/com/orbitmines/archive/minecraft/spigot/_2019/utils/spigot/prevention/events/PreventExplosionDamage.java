package com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.prevention.events;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.prevention.PreventionEvent;
import org.bukkit.World;
import org.bukkit.event.entity.EntityExplodeEvent;

public class PreventExplosionDamage extends PreventionEvent<EntityExplodeEvent> {

    public PreventExplosionDamage(World world) {
        super(world);
    }

    @Override
    protected boolean shouldBeCancelled(EntityExplodeEvent event, World world) {
        /* Clear block list so blocks aren't destroyed, but let the explosion still deal entity damage */
        event.blockList().clear();
        return false;
    }

    @Override
    protected World getWorld(EntityExplodeEvent event) {
        return event.getLocation().getWorld();
    }

    @Override
    public Class<EntityExplodeEvent> getEventClass() {
        return EntityExplodeEvent.class;
    }
}
