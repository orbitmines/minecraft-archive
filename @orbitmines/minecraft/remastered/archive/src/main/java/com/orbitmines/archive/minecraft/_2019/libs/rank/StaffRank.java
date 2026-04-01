package com.orbitmines.archive.minecraft._2019.libs.rank;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.Color;
import lombok.Getter;

public enum StaffRank implements Rank {

    NONE(Rank.NONE, Color.GRAY, Color.SILVER),
    TWITCH("TWITCH", Color.PURPLE),
    YOUTUBE("YT", Color.RED),
    PROVISIONAL_BUILDER(Rank.NONE, Color.GRAY, Color.SILVER),
    BUILDER("BUILDER", Color.FUCHSIA),
    HELPER("HELPER", Color.GREEN),
    MODERATOR("MOD", Color.AQUA),
    DEVELOPER("DEV", Color.RED, true),
    ADMIN("ADMIN", Color.RED, true),
    OWNER("OWNER", Color.MAROON, true);

    @Getter private final String name;
    @Getter private final Color prefixColor;
    @Getter private final Color defaultChatColor;
    @Getter private final boolean hasVipPrivileges;

    StaffRank(String name, Color prefixColor) {
        this(name, prefixColor, Color.WHITE, false);
    }

    StaffRank(String name, Color prefixColor, boolean hasVipPrivileges) {
        this(name, prefixColor, Color.WHITE, hasVipPrivileges);
    }

    StaffRank(String name, Color prefixColor, Color defaultChatColor) {
        this(name, prefixColor, defaultChatColor, false);
    }

    StaffRank(String name, Color prefixColor, Color defaultChatColor, boolean hasVipPrivileges) {
        this.name = name;
        this.prefixColor = prefixColor;
        this.defaultChatColor = defaultChatColor;
        this.hasVipPrivileges = hasVipPrivileges;
    }
}
