package com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.prevention.events;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.prevention.PreventionEvent;
import org.bukkit.World;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;

public class PreventSwapHandItems extends PreventionEvent<PlayerSwapHandItemsEvent> {

    public PreventSwapHandItems(World world) {
        super(world);
    }

    @Override
    protected boolean shouldBeCancelled(PlayerSwapHandItemsEvent event, World world) {
        return true;
    }

    @Override
    protected World getWorld(PlayerSwapHandItemsEvent event) {
        return event.getPlayer().getWorld();
    }

    @Override
    public Class<PlayerSwapHandItemsEvent> getEventClass() {
        return PlayerSwapHandItemsEvent.class;
    }
}
