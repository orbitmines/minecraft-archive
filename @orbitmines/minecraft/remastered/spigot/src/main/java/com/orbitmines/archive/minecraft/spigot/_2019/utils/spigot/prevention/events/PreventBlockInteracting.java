package com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.prevention.events;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.ItemUtils;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.prevention.PreventionEvent;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.event.player.PlayerInteractEvent;

public class PreventBlockInteracting extends PreventionEvent<PlayerInteractEvent> {

    public PreventBlockInteracting(World world) {
        super(world);
    }

    @Override
    protected boolean shouldBeCancelled(PlayerInteractEvent event, World world) {
        if (event.getClickedBlock() == null)
            return false;

        Material blockType = event.getClickedBlock().getType();

        return ItemUtils.isInteractable(blockType);
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
