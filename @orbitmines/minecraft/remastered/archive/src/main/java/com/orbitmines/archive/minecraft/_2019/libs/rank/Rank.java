package com.orbitmines.archive.minecraft._2019.libs.rank;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.Color;

public interface Rank {

    String NONE = "NONE";

    String getName();

    Color getPrefixColor();

    Color getDefaultChatColor();

    default String getAsPrefix() {
        return getAsPrefix(getPrefixColor());
    }

    default String getAsPrefix(Color color) {
        if (isNone())
            return color == null ? "" : color.getCc();

        return getPrefixColor().getCc() + "§l" + getName() + (color == null ? "" : "§r " + color.getCc());
    }

    default String getDisplayName() {
        return getName().equals(NONE) ? "§f§l" + NONE : getPrefixColor().getCc() + "§l" + getName();
    }

    default boolean isNone() {
        return getName().equals(NONE);
    }
}
