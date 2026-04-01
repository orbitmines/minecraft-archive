package com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.prevention.events;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.prevention.PreventionEvent;
import org.bukkit.World;
import org.bukkit.event.block.BlockBreakEvent;

public class PreventBlockBreak extends PreventionEvent<BlockBreakEvent> {

    public PreventBlockBreak(World world) {
        super(world);
    }

    @Override
    protected boolean shouldBeCancelled(BlockBreakEvent event, World world) {
        return true;
    }

    @Override
    protected World getWorld(BlockBreakEvent event) {
        return event.getPlayer().getWorld();
    }

    @Override
    public Class<BlockBreakEvent> getEventClass() {
        return BlockBreakEvent.class;
    }
}
