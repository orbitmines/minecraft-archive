package com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.PotionBuilder;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.meta.PotionMeta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PotionItemBuilder extends ItemBuilderInstance<PotionItemBuilder, PotionMeta> {

    @Getter protected Type type;
    @Getter protected List<PotionBuilder> potionBuilders;
    @Getter protected boolean effectHidden;

    public PotionItemBuilder(Type type) {
        super(type.material);

        this.type = type;
    }

    public PotionItemBuilder(Type type, PotionBuilder potionBuilder) {
        super(type.material);

        this.type = type;
        this.potionBuilders = new ArrayList<>(Collections.singletonList(potionBuilder));
    }

    public PotionItemBuilder(Type type, PotionBuilder potionBuilder, int amount) {
        super(type.material, amount);

        this.type = type;
        this.potionBuilders = new ArrayList<>(Collections.singletonList(potionBuilder));
    }

    public PotionItemBuilder(Type type, PotionBuilder potionBuilder, int amount, String displayName) {
        super(type.material, amount, displayName);

        this.type = type;
        this.potionBuilders = new ArrayList<>(Collections.singletonList(potionBuilder));
    }

    public PotionItemBuilder(Type type, PotionBuilder potionBuilder, int amount, String displayName, String... lore) {
        super(type.material, amount, displayName, lore);

        this.type = type;
        this.potionBuilders = new ArrayList<>(Collections.singletonList(potionBuilder));
    }

    public PotionItemBuilder(Type type, PotionBuilder potionBuilder, int amount, String displayName, List<String> lore) {
        super(type.material, amount, displayName, lore);

        this.type = type;
        this.potionBuilders = new ArrayList<>(Collections.singletonList(potionBuilder));
    }

    public PotionItemBuilder(PotionItemBuilder itemBuilder) {
        super(itemBuilder);

        this.type = itemBuilder.type;
        this.potionBuilders = new ArrayList<>(itemBuilder.potionBuilders);
    }

    public PotionItemBuilder setType(Type type) { this.type = type; this.material = type.material; return this; }
    public PotionItemBuilder setPotionBuilders(List<PotionBuilder> potionBuilders) { this.potionBuilders = potionBuilders; return this; }
    public PotionItemBuilder addPotionBuilder(PotionBuilder potionBuilder) { this.potionBuilders.add(potionBuilder); return this; }

    @Override
    public PotionItemBuilder clone() {
        return new PotionItemBuilder(this);
    }

    @Override
    protected PotionItemBuilder getInstance() {
        return this;
    }

    @Override
    public PotionMeta buildMeta(PotionMeta meta) {
        meta = super.buildMeta(meta);

        meta.setColor(potionBuilders.get(0).getType().getColor());

        for (PotionBuilder potionBuilder : potionBuilders) {
            meta.addCustomEffect(potionBuilder.build(), true);
        }

        return meta;
    }

    public enum Type {

        NORMAL(Material.POTION),
        SPLASH(Material.SPLASH_POTION),
        LINGERING(Material.LINGERING_POTION);

        @Getter private final Material material;

        Type(Material material) {
            this.material = material;
        }
    }
}
