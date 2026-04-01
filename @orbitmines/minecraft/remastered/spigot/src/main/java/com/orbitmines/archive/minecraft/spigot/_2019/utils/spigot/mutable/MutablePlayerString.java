package com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.mutable;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.placeholders.SpigotPlayer;

@FunctionalInterface
public interface MutablePlayerString<P extends SpigotPlayer> {

    String toString(P player);

}
