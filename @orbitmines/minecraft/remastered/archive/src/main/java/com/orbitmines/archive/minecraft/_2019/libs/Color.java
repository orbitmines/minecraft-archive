package com.orbitmines.archive.minecraft._2019.libs;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import lombok.Getter;

import java.util.regex.Pattern;

public enum Color {

    AQUA("§b", "Light Blue", 85, 255, 255),
    BLACK("§0", "Black", 1, 1, 1),/* Discord uses 0,0,0 as default color */
    BLUE("§9", "Blue", 85, 85, 255),
    FUCHSIA("§d", "Pink", 255, 85, 255),
    GRAY("§8", "Dark Gray", 85, 85, 85),
    GREEN("§2", "Green", 0, 170, 0),
    LIME("§a", "Light Green", 85, 255, 85),
    MAROON("§4", "Dark Red", 170, 0, 0),
    NAVY("§1", "Dark Blue", 0, 0, 170),
    ORANGE("§6", "Orange", 255, 170, 0),
    PURPLE("§5", "Purple", 170, 0, 170),
    RED("§c", "Red", 255, 85, 85),
    SILVER("§7", "Gray", 170, 170, 170),
    TEAL("§3", "Cyan", 0, 170, 170),
    WHITE("§f", "White", 255, 255, 255),
    YELLOW("§e", "Yellow", 255, 255, 85);

    public static final Color INFO = BLUE;
    public static final Color ERROR = RED;
    public static final Color SUCCESS = LIME;
    public static final Color DISCORD = BLUE;

    @Getter private final String cc;
    @Getter private final String name;

    @Getter private final int r;
    @Getter private final int g;
    @Getter private final int b;
    @Getter private final java.awt.Color awtColor;

    Color(String cc, String name, int r, int g, int b) {
        this.cc = cc;
        this.name = name;
        this.r = r;
        this.g = g;
        this.b = b;
        this.awtColor = new java.awt.Color(r, g, b);
    }

    public static final Pattern STRIP_COLOR_PATTERN = Pattern.compile("(?i)§[0-9A-FK-OR]");

    public static String stripColor(String input) {
        return input == null ? null : STRIP_COLOR_PATTERN.matcher(input).replaceAll("");
    }

    public static String translateAlternateColorCodes(char altColorChar, String textToTranslate) {
        char[] b = textToTranslate.toCharArray();

        for(int i = 0; i < b.length - 1; ++i) {
            if (b[i] == altColorChar && "0123456789AaBbCcDdEeFfKkLlMmNnOoRr".indexOf(b[i + 1]) > -1) {
                b[i] = 167;
                b[i + 1] = Character.toLowerCase(b[i + 1]);
            }
        }

        return new String(b);
    }
}
