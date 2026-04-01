package com.orbitmines.archive.minecraft.spigot._2019.servers.prison.events;

/*
    Created By Robin Egberts On 3/7/2019
    Copyrighted By OrbitMines ©2019
*/

import com.orbitmines.archive.minecraft.spigot._2019.servers.prison.Prison;
import com.orbitmines.archive.minecraft.spigot._2019.servers.prison.PrisonPlayer;
import com.orbitmines.space_spigot.event.entity.EntityCannotPickupItemEvent;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.inventory.ItemStack;

public class ItemPickUpEvent implements Listener {

    private final Prison server;

    public ItemPickUpEvent(Prison server) {
        this.server = server;
    }

    private int getRemaining(PrisonPlayer player, ItemStack itemStack, int remaining) {
        return player.getPrisonInventory().addItem(itemStack, remaining);
    }

    @EventHandler
    public void onItemPickup(EntityPickupItemEvent event) {
        LivingEntity entity = event.getEntity();

        if (!(entity instanceof Player))
            return;

        PrisonPlayer player = server.getPlayer((Player) event.getEntity());

        int remaining = getRemaining(player, event.getItem().getItemStack(), event.getRemaining());
        event.setRemaining(remaining);
    }

    @EventHandler
    public void onItemPickup(EntityCannotPickupItemEvent event) {
        LivingEntity entity = event.getEntity();

        if (!(entity instanceof Player))
            return;

        PrisonPlayer player = server.getPlayer((Player) event.getEntity());

        int remaining = getRemaining(player, event.getItem().getItemStack(), event.getRemaining());

        if (remaining != event.getRemaining())
            player.playSound(Sound.ENTITY_ITEM_PICKUP);

        event.setRemaining(remaining);
    }
}
