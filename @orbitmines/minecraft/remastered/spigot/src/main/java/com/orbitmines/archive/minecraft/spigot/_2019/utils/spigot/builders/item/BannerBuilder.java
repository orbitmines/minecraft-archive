package com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.ColorUtils;
import lombok.Getter;
import org.bukkit.DyeColor;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.inventory.meta.BannerMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BannerBuilder extends ItemBuilderInstance<BannerBuilder, BannerMeta> {

    @Getter private DyeColor baseColor;
    @Getter private List<Pattern> patterns;

    public BannerBuilder(DyeColor baseColor) {
        this(baseColor, new ArrayList<>());
    }

    public BannerBuilder(DyeColor baseColor, Pattern... patterns) {
        this(baseColor, Arrays.asList(patterns));
    }

    public BannerBuilder(DyeColor baseColor, List<Pattern> patterns) {
        super(ColorUtils.getBannerMaterial(baseColor));

        this.baseColor = baseColor;
        this.patterns = patterns;
    }

    public BannerBuilder(DyeColor baseColor, List<Pattern> patterns, int amount) {
        super(ColorUtils.getBannerMaterial(baseColor), amount);

        this.baseColor = baseColor;
        this.patterns = patterns;
    }

    public BannerBuilder(DyeColor baseColor, List<Pattern> patterns, int amount, String displayName) {
        super(ColorUtils.getBannerMaterial(baseColor), amount, displayName);

        this.baseColor = baseColor;
        this.patterns = patterns;
    }

    public BannerBuilder(DyeColor baseColor, List<Pattern> patterns, int amount, String displayName, String... lore) {
        super(ColorUtils.getBannerMaterial(baseColor), amount, displayName, lore);

        this.baseColor = baseColor;
        this.patterns = patterns;
    }

    public BannerBuilder(DyeColor baseColor, List<Pattern> patterns, int amount, String displayName, List<String> lore) {
        super(ColorUtils.getBannerMaterial(baseColor), amount, displayName, lore);

        this.baseColor = baseColor;
        this.patterns = patterns;
    }

    public BannerBuilder(BannerBuilder itemBuilder) {
        super(itemBuilder);

        this.baseColor = itemBuilder.baseColor;
        this.patterns = new ArrayList<>(itemBuilder.patterns);
    }

    public BannerBuilder setBaseColor(DyeColor baseColor) { this.baseColor = baseColor; this.material = ColorUtils.getBannerMaterial(this.baseColor); return this; }
    public BannerBuilder setPatterns(List<Pattern> patterns) { this.patterns = patterns; return this; }
    public BannerBuilder addPattern(Pattern pattern) { this.patterns.add(pattern); return this; }
    public BannerBuilder addPattern(DyeColor dyeColor, PatternType patternType) { return addPattern(new Pattern(dyeColor, patternType)); }

    @Override
    public BannerBuilder clone() {
        return new BannerBuilder(this);
    }

    @Override
    protected BannerBuilder getInstance() {
        return this;
    }

    @Override
    public BannerMeta buildMeta(BannerMeta meta) {
        meta = super.buildMeta(meta);

        meta.setPatterns(new ArrayList<>(this.patterns));

        return meta;
    }
}
