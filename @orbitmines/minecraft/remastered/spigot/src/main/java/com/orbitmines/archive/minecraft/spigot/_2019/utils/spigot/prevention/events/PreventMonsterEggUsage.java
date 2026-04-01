package com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.prevention.events;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.ItemUtils;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.prevention.PreventionEvent;
import org.bukkit.World;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.PlayerInventory;

public class PreventMonsterEggUsage {

    public static class InteractEntity extends PreventionEvent<PlayerInteractEntityEvent> {

        public InteractEntity(World world) {
            super(world);
        }

        @Override
        protected boolean shouldBeCancelled(PlayerInteractEntityEvent event, World world) {
            PlayerInventory inventory = event.getPlayer().getInventory();

            switch (event.getHand()) {

                case HAND:
                    return ItemUtils.isEgg(inventory.getItemInMainHand());
                case OFF_HAND:
                    return ItemUtils.isEgg(inventory.getItemInOffHand());
                default:
                    return false;
            }
        }

        @Override
        protected World getWorld(PlayerInteractEntityEvent event) {
            return event.getRightClicked().getWorld();
        }

        @Override
        protected void cancel(PlayerInteractEntityEvent event) {
            super.cancel(event);

            event.getPlayer().updateInventory();
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
            PlayerInventory inventory = event.getPlayer().getInventory();

            switch (event.getHand()) {

                case HAND:
                    return ItemUtils.isEgg(inventory.getItemInMainHand());
                case OFF_HAND:
                    return ItemUtils.isEgg(inventory.getItemInOffHand());
                default:
                    return false;
            }
        }

        @Override
        protected World getWorld(PlayerInteractAtEntityEvent event) {
            return event.getRightClicked().getWorld();
        }

        @Override
        protected void cancel(PlayerInteractAtEntityEvent event) {
            super.cancel(event);

            event.getPlayer().updateInventory();
        }

        @Override
        public Class<PlayerInteractAtEntityEvent> getEventClass() {
            return PlayerInteractAtEntityEvent.class;
        }
    }

    public static class Interact extends PreventionEvent<PlayerInteractEvent> {

        public Interact(World world) {
            super(world);
        }

        @Override
        protected boolean shouldBeCancelled(PlayerInteractEvent event, World world) {
            PlayerInventory inventory = event.getPlayer().getInventory();

            if (event.getHand() == null)
                return false;

            switch (event.getHand()) {

                case HAND:
                    return ItemUtils.isEgg(inventory.getItemInMainHand());
                case OFF_HAND:
                    return ItemUtils.isEgg(inventory.getItemInOffHand());
                default:
                    return false;
            }
        }

        @Override
        protected World getWorld(PlayerInteractEvent event) {
            return event.getPlayer().getWorld();
        }

        @Override
        protected void cancel(PlayerInteractEvent event) {
            super.cancel(event);

            event.getPlayer().updateInventory();
        }

        @Override
        public Class<PlayerInteractEvent> getEventClass() {
            return PlayerInteractEvent.class;
        }
    }
}
