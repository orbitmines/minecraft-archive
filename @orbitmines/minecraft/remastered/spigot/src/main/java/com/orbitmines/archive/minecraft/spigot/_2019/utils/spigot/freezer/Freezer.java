package com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.freezer;

import org.bukkit.Location;
import org.bukkit.entity.Player;

/*
* OrbitMines - @author Fadi Shawki - 2017
*/
public enum Freezer {
    /*
        !IMPORTANT! Register NpcEvents before using
     */

    ARMOR_STAND_RIDE {
        @Override
        public void freeze(Player player, Location location) {
            if (location != null)
                new ArmorStandPlayerFreezer(player, location);
            else
                new ArmorStandPlayerFreezer(player);
        }

        @Override
        public void clearFreeze(Player player) {
            ArmorStandPlayerFreezer.getFreezer(player).destroy();
        }
    },
    MOVE,
    MOVE_AND_JUMP,
    MOVE_AND_LOOK_AROUND;

    public void freeze(Player player, Location location) {}

    public void clearFreeze(Player player) {}

}
