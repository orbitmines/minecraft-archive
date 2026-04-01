package com.orbitmines.archive.minecraft._2019.libs.discord;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft._2019.libs.rank.Rank;
import com.orbitmines.archive.minecraft._2019.libs.rank.StaffRank;
import com.orbitmines.archive.minecraft._2019.libs.rank.VipRank;
import com.orbitmines.archive.minecraft._2019.utils.discord.DiscordRole;
import lombok.Getter;

public enum CustomRole implements DiscordRole {

    MEMBER("member", Color.WHITE, false),

    OWNER("owner", StaffRank.OWNER.getPrefixColor(), true),
    ADMIN("admin", StaffRank.ADMIN.getPrefixColor(), true),
    DEVELOPER("developer", StaffRank.DEVELOPER.getPrefixColor(), true),
    MODERATOR("moderator", StaffRank.MODERATOR.getPrefixColor(), true),
    HELPER("helper", StaffRank.HELPER.getPrefixColor(), true),
    BUILDER("builder", StaffRank.BUILDER.getPrefixColor(), true),
    YOUTUBE("youtube", StaffRank.YOUTUBE.getPrefixColor(), true),
    TWITCH("twitch", StaffRank.TWITCH.getPrefixColor(), true),

    EMERALD("emerald", VipRank.EMERALD.getPrefixColor(), true),
    DIAMOND("diamond", VipRank.DIAMOND.getPrefixColor(), true),
    GOLD("gold", VipRank.GOLD.getPrefixColor(), true),
    IRON("iron", VipRank.IRON.getPrefixColor(), true),

    STAFF("staff", Color.SILVER, true),
    VIP("vip", Color.SILVER, true),

    MUTED("Muted", Color.GRAY, false),
    FIX_IT_FADI("FixItFadi", Color.YELLOW, true),

    JOIN("»", Color.LIME, false),
    LEAVE("«", Color.RED, false),

    TOP_VOTER_1("#1", Color.ORANGE, false),
    TOP_VOTER_2("#2", Color.SILVER, false),
    TOP_VOTER_3("#3", Color.MAROON, false),
    TOP_VOTER_4("#4", Color.GRAY, false),
    TOP_VOTER_5("#5", Color.GRAY, false),

    AQUA(Color.AQUA.getName(), Color.AQUA, false),
    BLACK(Color.BLACK.getName(), Color.BLACK, false),
    BLUE(Color.BLUE.getName(), Color.BLUE, false),
    FUCHSIA(Color.FUCHSIA.getName(), Color.FUCHSIA, false),
    GRAY(Color.GRAY.getName(), Color.GRAY, false),
    GREEN(Color.GREEN.getName(), Color.GREEN, false),
    LIME(Color.LIME.getName(), Color.LIME, false),
    MAROON(Color.MAROON.getName(), Color.MAROON, false),
    NAVY(Color.NAVY.getName(), Color.NAVY, false),
    ORANGE(Color.ORANGE.getName(), Color.ORANGE, false),
    PURPLE(Color.PURPLE.getName(), Color.PURPLE, false),
    RED(Color.RED.getName(), Color.RED, false),
    SILVER(Color.SILVER.getName(), Color.SILVER, false),
    TEAL(Color.TEAL.getName(), Color.TEAL, false),
    WHITE(Color.WHITE.getName(), Color.WHITE, false),
    YELLOW(Color.YELLOW.getName(), Color.YELLOW, false);

    @Getter private final String name;
    @Getter private final java.awt.Color color;
    @Getter private final boolean mentionable;

    CustomRole(String name, Color color, boolean mentionable) {
        this.name = name;
        this.color = color.getAwtColor();
        this.mentionable = mentionable;
    }

    @Override
    public String toString() {
        return name;
    }

    public static CustomRole from(Rank rank) {
        if (rank.toString().equals(Rank.NONE))
            return MEMBER;

        return CustomRole.valueOf(rank.toString());
    }

    public static CustomRole from(Color color) {
        try {
            for (CustomRole role : values()) {
                if (role.getName().equals(color.getName()))
                    return role;
            }
        } catch(NullPointerException | IllegalArgumentException ex) { }

        return CustomRole.WHITE;
    }
}
