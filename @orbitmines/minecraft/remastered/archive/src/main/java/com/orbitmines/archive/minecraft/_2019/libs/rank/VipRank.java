package com.orbitmines.archive.minecraft._2019.libs.rank;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.Color;
import lombok.Getter;

public enum VipRank implements Rank {

    NONE(Rank.NONE, Color.GRAY, Color.SILVER, 0, 0),
    IRON("IRON", Color.SILVER, 500, 50),
    GOLD("GOLD", Color.ORANGE, 750, 300),
    DIAMOND("DIAMOND", Color.BLUE, 1500, 600),
    EMERALD("EMERALD", Color.LIME, 2500, 1000);

    @Getter private final String name;
    @Getter private final Color prefixColor;
    @Getter private final Color defaultChatColor;
    @Getter private final int initialSolars;
    @Getter private final int monthlySolars;

    VipRank(String name, Color prefixColor, int initialSolars, int monthlySolars) {
        this(name, prefixColor, Color.WHITE, initialSolars, monthlySolars);
    }

    VipRank(String name, Color prefixColor, Color defaultChatColor, int initialSolars, int monthlySolars) {
        this.name = name;
        this.prefixColor = prefixColor;
        this.defaultChatColor = defaultChatColor;
        this.initialSolars = initialSolars;
        this.monthlySolars = monthlySolars;
    }

    public int getInitialSolars(VipRank previousRank) {
        return previousRank == null ? initialSolars : (initialSolars - previousRank.initialSolars);
    }
}
