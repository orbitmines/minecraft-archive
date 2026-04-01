package com.orbitmines.archive.minecraft.bungeecord._2019.servers.bungeecord.utils;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import net.md_5.bungee.api.ChatColor;

public class ColorUtils {

    public static ChatColor toChatColor(com.orbitmines.archive.minecraft._2019.libs.Color color) {
        switch (color) {

            case AQUA:
                return ChatColor.AQUA;
            case BLACK:
                return ChatColor.BLACK;
            case BLUE:
                return ChatColor.BLUE;
            case FUCHSIA:
                return ChatColor.LIGHT_PURPLE;
            case GRAY:
                return ChatColor.DARK_GRAY;
            case GREEN:
                return ChatColor.DARK_GREEN;
            case LIME:
                return ChatColor.GREEN;
            case MAROON:
                return ChatColor.DARK_RED;
            case NAVY:
                return ChatColor.DARK_BLUE;
            case ORANGE:
                return ChatColor.GOLD;
            case PURPLE:
                return ChatColor.DARK_PURPLE;
            case RED:
                return ChatColor.RED;
            case SILVER:
                return ChatColor.GRAY;
            case TEAL:
                return ChatColor.DARK_AQUA;
            case WHITE:
                return ChatColor.WHITE;
            case YELLOW:
                return ChatColor.YELLOW;
        }
        throw new IllegalArgumentException();
    }
}
