package com.orbitmines.archive.minecraft._2019.libs.loot;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.Color;

public enum Rarity {

    COMMON(Color.LIME),
    UNCOMMON(Color.AQUA),
    RARE(Color.ORANGE),
    EPIC(Color.PURPLE),
    LEGENDARY(Color.RED);

    private final Color color;

    Rarity(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public String getDisplayName() {
        return color.getCc() + "§l" + toString();
    }

}