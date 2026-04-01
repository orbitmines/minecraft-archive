package com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.prevention;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import lombok.Getter;
import org.bukkit.World;
import org.bukkit.event.*;

import java.util.HashSet;
import java.util.Set;

public abstract class PreventionEvent<E extends Event> implements Listener {

    @Getter protected Set<World> worlds;

    public PreventionEvent(World world) {
        this.worlds = new HashSet<>();
        this.worlds.add(world);
    }

    protected abstract boolean shouldBeCancelled(E event, World world);

    protected abstract World getWorld(E event);

    public abstract Class<E> getEventClass();

    public EventPriority getPriority() {
        return EventPriority.LOWEST;
    }

    public void execute(Event e) {
        try {
            E event = (E) e;

            World world = getWorld(event);

            if (!worlds.contains(world))
                return;

            if (shouldBeCancelled(event, world))
                cancel(event);
        } catch (ClassCastException ex) {
            //TODO FIX WEIRD BUG:
            // java.lang.ClassCastException: org.bukkit.event.inventory.FurnaceExtractEvent cannot be cast to org.bukkit.event.block.BlockBreakEvent
        }
    }

    protected void cancel(E event) {
        ((Cancellable) event).setCancelled(true);
    }

    public void unregister() {
        HandlerList.unregisterAll(this);
    }

    public void unregister(World world) {
        worlds.remove(world);
    }
}
