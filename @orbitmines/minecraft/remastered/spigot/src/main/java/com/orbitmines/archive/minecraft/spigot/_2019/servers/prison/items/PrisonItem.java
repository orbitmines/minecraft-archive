package com.orbitmines.archive.minecraft.spigot._2019.servers.prison.items;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.itemstack.ItemStackNms;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.itemstack.MetaData;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.placeholders.SpigotServer;
import org.bukkit.inventory.ItemStack;

public abstract class PrisonItem implements Backpack, TrackableItem {

    private final static ItemStackNms nms;

    static {
        nms = SpigotServer.getInstance().getNms().customItem();
    }

    public abstract ItemStack getItem();

    public abstract void setItem(ItemStack itemStack);

    public MetaData getMetaData() {
        ItemStack item = getItem();
        if (item == null)
            return null;

        return nms.getMetaData(item);
    }
}
