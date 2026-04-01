package com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.mutable;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.ItemBuilderInstance;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.placeholders.SpigotPlayer;

@FunctionalInterface
public interface MutablePlayerItemBuilder<B extends ItemBuilderInstance, P extends SpigotPlayer> {

    B toBuilder(P player);

}
