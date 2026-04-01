package com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.prevention.events;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.prevention.PreventionEvent;
import org.bukkit.World;
import org.bukkit.event.block.LeavesDecayEvent;

public class PreventLeafDecay extends PreventionEvent<LeavesDecayEvent> {

    public PreventLeafDecay(World world) {
        super(world);
    }

    @Override
    protected boolean shouldBeCancelled(LeavesDecayEvent event, World world) {
        return true;
    }

    @Override
    protected World getWorld(LeavesDecayEvent event) {
        return event.getBlock().getWorld();
    }

    @Override
    public Class<LeavesDecayEvent> getEventClass() {
        return LeavesDecayEvent.class;
    }
}
