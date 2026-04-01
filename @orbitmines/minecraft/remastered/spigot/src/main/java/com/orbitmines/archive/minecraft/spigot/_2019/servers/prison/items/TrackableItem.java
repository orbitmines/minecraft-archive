package com.orbitmines.archive.minecraft.spigot._2019.servers.prison.items;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.ItemUtils;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.itemstack.MetaData;
import org.bukkit.inventory.ItemStack;

public interface TrackableItem {

    ItemStack getItem();

    MetaData getMetaData();

    default boolean shouldTrackBlocks() {
        ItemStack item = getItem();

        if (item == null)
            return false;

        return ItemUtils.isPickaxe(item) || ItemUtils.isAxe(item) || ItemUtils.isShovel(item);
    }

    default void addTrackerCount(int count) {
        MetaData data = getMetaData();

        long counter = data.getOrDefault("prison", "block_counter", 0L);
        counter += count;

        data.set("prison", "block_counter", counter);
    }

}
