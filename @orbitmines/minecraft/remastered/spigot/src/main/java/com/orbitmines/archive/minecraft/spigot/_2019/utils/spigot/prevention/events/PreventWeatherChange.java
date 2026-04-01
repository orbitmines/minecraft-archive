package com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.prevention.events;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.prevention.PreventionEvent;
import org.bukkit.World;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;

public class PreventWeatherChange extends PreventionEvent<WeatherChangeEvent> implements Listener {

    public PreventWeatherChange(World world) {
        super(world);
    }

    @Override
    protected boolean shouldBeCancelled(WeatherChangeEvent event, World world) {
        return true;
    }

    @Override
    protected World getWorld(WeatherChangeEvent event) {
        return event.getWorld();
    }

    @Override
    public Class<WeatherChangeEvent> getEventClass() {
        return WeatherChangeEvent.class;
    }
}
