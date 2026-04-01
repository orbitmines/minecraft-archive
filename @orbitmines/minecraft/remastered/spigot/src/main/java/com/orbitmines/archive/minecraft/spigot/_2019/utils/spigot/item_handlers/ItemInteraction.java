package com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.item_handlers;

import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.ItemBuilderInstance;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.placeholders.SpigotPlayer;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

/*
* OrbitMines - @author Fadi Shawki - 2017
*/
public abstract class ItemInteraction<P extends SpigotPlayer> {

    private static List<ItemInteraction> itemInteractions = new ArrayList<>();

    private final ItemBuilderInstance itemBuilder;

    public ItemInteraction(ItemBuilderInstance itemBuilder) {
        this.itemBuilder = itemBuilder;

        itemInteractions.add(this);
    }

    /* Cancelled by default, use event#setCancelled in order to allow if need be */
    public abstract void onInteract(P player, PlayerInteractEvent event, ItemStack itemStack);

    /* @Override to use */
    public void onLeftClick(P player, PlayerInteractEvent event, ItemStack itemStack) {}
    /* @Override to use */
    public void onRightClick(P player, PlayerInteractEvent event, ItemStack itemStack) {}

    public ItemBuilderInstance getItemBuilder() {
        return itemBuilder;
    }

    public boolean equals(ItemStack itemStack) {
        return this.itemBuilder.equals(itemStack);
    }

    public void unregister() {
        itemInteractions.remove(this);
    }

    public static List<ItemInteraction> getItemInteractions() {
        return itemInteractions;
    }
}
