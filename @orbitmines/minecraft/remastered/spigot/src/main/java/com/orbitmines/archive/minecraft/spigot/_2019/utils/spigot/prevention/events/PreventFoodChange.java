package com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.prevention.events;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.prevention.PreventionEvent;
import org.bukkit.World;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class PreventFoodChange extends PreventionEvent<FoodLevelChangeEvent> {

    public PreventFoodChange(World world) {
        super(world);
    }

    @Override
    protected boolean shouldBeCancelled(FoodLevelChangeEvent event, World world) {
        event.setFoodLevel(20);
        return true;
    }

    @Override
    protected World getWorld(FoodLevelChangeEvent event) {
        return event.getEntity().getWorld();
    }

    @Override
    public Class<FoodLevelChangeEvent> getEventClass() {
        return FoodLevelChangeEvent.class;
    }
}
