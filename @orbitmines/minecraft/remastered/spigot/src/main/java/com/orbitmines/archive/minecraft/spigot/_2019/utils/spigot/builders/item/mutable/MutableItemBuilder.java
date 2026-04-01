package com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.mutable;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.ItemBuilderInstance;
import org.bukkit.inventory.ItemStack;

@FunctionalInterface
public interface MutableItemBuilder<B extends ItemBuilderInstance> extends MutableItemStack {

    B toBuilder();

    default ItemStack toItemStack() {
        B builder = toBuilder();

        if (builder == null)
            return null;

        return builder.build();
    }
}
