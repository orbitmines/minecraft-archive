package com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.prevention.events;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.prevention.PreventionEvent;
import org.bukkit.World;
import org.bukkit.event.world.ChunkUnloadEvent;

public class PreventChunkUnload extends PreventionEvent<ChunkUnloadEvent> {

    public PreventChunkUnload(World world) {
        super(world);
    }

    @Override
    protected boolean shouldBeCancelled(ChunkUnloadEvent event, World world) {
        return true;
    }

    @Override
    protected World getWorld(ChunkUnloadEvent event) {
        return event.getWorld();
    }

    @Override
    public Class<ChunkUnloadEvent> getEventClass() {
        return ChunkUnloadEvent.class;
    }
}
