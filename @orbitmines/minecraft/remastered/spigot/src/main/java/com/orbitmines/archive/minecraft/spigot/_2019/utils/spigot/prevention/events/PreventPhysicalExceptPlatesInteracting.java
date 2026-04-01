package com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.prevention.events;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.ItemUtils;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.prevention.PreventionEvent;
import org.bukkit.World;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class PreventPhysicalExceptPlatesInteracting extends PreventionEvent<PlayerInteractEvent> {

    public PreventPhysicalExceptPlatesInteracting(World world) {
        super(world);
    }

    @Override
    protected boolean shouldBeCancelled(PlayerInteractEvent event, World world) {
        return event.getAction() == Action.PHYSICAL && ItemUtils.isPressurePlate(event.getClickedBlock().getType());
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
