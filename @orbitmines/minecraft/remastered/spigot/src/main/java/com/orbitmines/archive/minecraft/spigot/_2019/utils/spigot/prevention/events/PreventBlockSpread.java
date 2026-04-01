package com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.prevention.events;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.prevention.PreventionEvent;
import org.bukkit.World;
import org.bukkit.event.block.BlockSpreadEvent;

public class PreventBlockSpread extends PreventionEvent<BlockSpreadEvent> {

    public PreventBlockSpread(World world) {
        super(world);
    }

    @Override
    protected boolean shouldBeCancelled(BlockSpreadEvent event, World world) {
        return true;
    }

    @Override
    protected World getWorld(BlockSpreadEvent event) {
        return event.getBlock().getWorld();
    }

    @Override
    public Class<BlockSpreadEvent> getEventClass() {
        return BlockSpreadEvent.class;
    }
}
