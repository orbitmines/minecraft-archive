package com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.utils;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.Server;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;

public class ServerUtils {

    public static ItemBuilder getItemBuilder(Server server) {
        switch (server) {

            case HUB:
                return new ItemBuilder(Material.LIGHT_GRAY_TERRACOTTA);
            case SURVIVAL:
                return new ItemBuilder(Material.STONE_HOE).addFlag(ItemFlag.HIDE_ATTRIBUTES);
            case KITPVP:
                return new ItemBuilder(Material.IRON_SWORD).addFlag(ItemFlag.HIDE_ATTRIBUTES);
            case CREATIVE:
                return new ItemBuilder(Material.WOODEN_AXE).addFlag(ItemFlag.HIDE_ATTRIBUTES);
            case FOG:
                return new ItemBuilder(Material.NETHER_STAR).addFlag(ItemFlag.HIDE_ATTRIBUTES);
            default:
                throw new IllegalStateException();
        }
    }

}
