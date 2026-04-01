package com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.prevention.events;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.prevention.PreventionEvent;
import org.bukkit.World;
import org.bukkit.event.player.PlayerDropItemEvent;

public class PreventItemDrop extends PreventionEvent<PlayerDropItemEvent> {

    public PreventItemDrop(World world) {
        super(world);
    }

    @Override
    protected boolean shouldBeCancelled(PlayerDropItemEvent event, World world) {
        return true;
    }

    @Override
    protected World getWorld(PlayerDropItemEvent event) {
        return event.getPlayer().getWorld();
    }

    @Override
    public Class<PlayerDropItemEvent> getEventClass() {
        return PlayerDropItemEvent.class;
    }
}
