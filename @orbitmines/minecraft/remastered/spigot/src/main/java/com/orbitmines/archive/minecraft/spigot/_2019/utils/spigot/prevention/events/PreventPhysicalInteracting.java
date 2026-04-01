package com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.prevention.events;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.prevention.PreventionEvent;
import org.bukkit.World;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class PreventPhysicalInteracting extends PreventionEvent<PlayerInteractEvent> {

    public PreventPhysicalInteracting(World world) {
        super(world);
    }

    @Override
    protected boolean shouldBeCancelled(PlayerInteractEvent event, World world) {
        return event.getAction() == Action.PHYSICAL;
    }

    @Override
    protected World getWorld(PlayerInteractEvent event) {
        return event.getPlayer().getWorld();
    }

    @Override
    public Class<PlayerInteractEvent> getEventClass() {
        return PlayerInteractEvent.class;
    }
}
