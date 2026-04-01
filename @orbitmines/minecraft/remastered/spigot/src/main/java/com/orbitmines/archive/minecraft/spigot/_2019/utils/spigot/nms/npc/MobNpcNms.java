package com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc;

import org.bukkit.Location;
import org.bukkit.entity.Entity;

import java.util.Collection;

/*
 * OrbitMines - @author Fadi Shawki - 2018
 */
public interface MobNpcNms {

    Entity spawn(Location location, Collection<Option> options);

//    Entity spawnRideable(Location location, float speed, float backMultiplier, float sideMultiplier, float walkHeight, float jumpHeight);

    enum Option {

        DISABLE_MOVEMENT,
        DISABLE_ATTACK,
        DISABLE_COLLISION,
        DISABLE_SOUNDS,

        //v1_10_R1+
        DISABLE_GRAVITY

    }
}
