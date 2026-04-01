package com.orbitmines.archive.minecraft._2019.libs.utils;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.Color;

public class Message {

    public static String format(String prefix, Color color, String message) {
        return prefix == null ? message : color.getCc() + prefix + " » §7" + message;
    }
}
