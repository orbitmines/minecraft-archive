package com.orbitmines.archive.minecraft.bungeecord._2019.servers.bungeecord.utils.mutable;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft.bungeecord._2019.servers.bungeecord.libs.BungeePlayer;

@FunctionalInterface
public interface MutablePlayerString {

    String toString(BungeePlayer player);

}
