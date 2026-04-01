package com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.utils;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.utils.language.Language;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.BannerBuilder;
import org.bukkit.DyeColor;
import org.bukkit.block.banner.PatternType;

public class LanguageUtils {

    public static BannerBuilder getBanner(Language language) {
        BannerBuilder builder;
        switch (language) {

            case DUTCH:
                builder = new BannerBuilder(DyeColor.WHITE);
                builder.addPattern(DyeColor.BLUE, PatternType.STRIPE_BOTTOM);
                builder.addPattern(DyeColor.RED, PatternType.STRIPE_TOP);
                return builder;
            case ENGLISH:
                builder = new BannerBuilder(DyeColor.BLUE);
                builder.addPattern(DyeColor.WHITE, PatternType.STRIPE_DOWNLEFT);
                builder.addPattern(DyeColor.WHITE, PatternType.STRIPE_DOWNRIGHT);
                builder.addPattern(DyeColor.RED, PatternType.CROSS);
                builder.addPattern(DyeColor.WHITE, PatternType.STRIPE_CENTER);
                builder.addPattern(DyeColor.WHITE, PatternType.STRIPE_MIDDLE);
                builder.addPattern(DyeColor.RED, PatternType.STRAIGHT_CROSS);
                return builder;
            default:
                throw new IllegalArgumentException();
        }
    }
}
