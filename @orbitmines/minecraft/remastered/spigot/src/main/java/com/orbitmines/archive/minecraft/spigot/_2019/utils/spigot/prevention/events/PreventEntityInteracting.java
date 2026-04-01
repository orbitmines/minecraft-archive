package com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.prevention.events;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.prevention.PreventionEvent;
import org.bukkit.World;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class PreventEntityInteracting {

    public static class InteractEntity extends PreventionEvent<PlayerInteractEntityEvent> {

        public InteractEntity(World world) {
            super(world);
        }

        @Override
        protected boolean shouldBeCancelled(PlayerInteractEntityEvent event, World world) {
            return true;
        }

        @Override
        protected World getWorld(PlayerInteractEntityEvent event) {
            return event.getRightClicked().getWorld();
        }

        @Override
        public Class<PlayerInteractEntityEvent> getEventClass() {
            return PlayerInteractEntityEvent.class;
        }
    }

    public static class InteractAtEntity extends PreventionEvent<PlayerInteractAtEntityEvent> {

        public InteractAtEntity(World world) {
            super(world);
        }

        @Override
        protected boolean shouldBeCancelled(PlayerInteractAtEntityEvent event, World world) {
            return true;
        }

        @Override
        protected World getWorld(PlayerInteractAtEntityEvent event) {
            return event.getRightClicked().getWorld();
        }

        @Override
        public Class<PlayerInteractAtEntityEvent> getEventClass() {
            return PlayerInteractAtEntityEvent.class;
        }
    }
}
