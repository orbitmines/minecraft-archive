package com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.prevention.events;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.prevention.PreventionEvent;
import org.bukkit.World;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class PreventMobSpawn extends PreventionEvent<CreatureSpawnEvent> {

    public PreventMobSpawn(World world) {
        super(world);
    }

    @Override
    protected boolean shouldBeCancelled(CreatureSpawnEvent event, World world) {
        return event.getSpawnReason() != CreatureSpawnEvent.SpawnReason.CUSTOM;
    }

    @Override
    protected World getWorld(CreatureSpawnEvent event) {
        return event.getEntity().getWorld();
    }

    @Override
    public Class<CreatureSpawnEvent> getEventClass() {
        return CreatureSpawnEvent.class;
    }
}
