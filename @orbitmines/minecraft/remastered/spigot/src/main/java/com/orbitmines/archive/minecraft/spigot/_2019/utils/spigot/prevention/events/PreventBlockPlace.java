package com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.prevention.events;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.prevention.PreventionEvent;
import org.bukkit.World;
import org.bukkit.event.block.BlockPlaceEvent;

public class PreventBlockPlace extends PreventionEvent<BlockPlaceEvent> {

    public PreventBlockPlace(World world) {
        super(world);
    }

    @Override
    protected boolean shouldBeCancelled(BlockPlaceEvent event, World world) {
        return true;
    }

    @Override
    protected World getWorld(BlockPlaceEvent event) {
        return event.getPlayer().getWorld();
    }

    @Override
    public Class<BlockPlaceEvent> getEventClass() {
        return BlockPlaceEvent.class;
    }
}
