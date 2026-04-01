package com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.worlds.generators;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import org.bukkit.World;
import org.bukkit.WorldCreator;

public interface WorldType {

    Class<? extends WorldCreator> getWorldCreator();

    World.Environment getEnvironment();

}
