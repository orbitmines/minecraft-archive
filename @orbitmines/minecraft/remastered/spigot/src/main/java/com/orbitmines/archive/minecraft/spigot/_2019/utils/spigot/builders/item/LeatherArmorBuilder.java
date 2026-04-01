package com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import lombok.Getter;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.List;

public class LeatherArmorBuilder extends ItemBuilderInstance<LeatherArmorBuilder, LeatherArmorMeta> {

    @Getter protected Type type;
    @Getter protected Color color;

    public LeatherArmorBuilder(Type type) {
        super(type.material);

        this.type = type;
    }

    public LeatherArmorBuilder(Type type, Color color) {
        super(type.material);

        this.type = type;
        this.color = color;
    }

    public LeatherArmorBuilder(Type type, Color color, int amount) {
        super(type.material, amount);

        this.type = type;
        this.color = color;
    }

    public LeatherArmorBuilder(Type type, Color color, int amount, String displayName) {
        super(type.material, amount, displayName);

        this.type = type;
        this.color = color;
    }

    public LeatherArmorBuilder(Type type, Color color, int amount, String displayName, String... lore) {
        super(type.material, amount, displayName, lore);

        this.type = type;
        this.color = color;
    }

    public LeatherArmorBuilder(Type type, Color color, int amount, String displayName, List<String> lore) {
        super(type.material, amount, displayName, lore);

        this.type = type;
        this.color = color;
    }

    public LeatherArmorBuilder(LeatherArmorBuilder itemBuilder) {
        super(itemBuilder);

        this.type = itemBuilder.type;
        this.color = itemBuilder.color;
    }

    public LeatherArmorBuilder setType(Type type) { this.type = type; this.material = type.material; return this; }
    public LeatherArmorBuilder setColor(Color color) { this.color = color; return this; }

    @Override
    public LeatherArmorBuilder clone() {
        return new LeatherArmorBuilder(this);
    }

    @Override
    protected LeatherArmorBuilder getInstance() {
        return this;
    }

    @Override
    public LeatherArmorMeta buildMeta(LeatherArmorMeta meta) {
        meta = super.buildMeta(meta);

        if (this.color != null)
            meta.setColor(this.color);

        return meta;
    }

    public enum Type {

        HELMET(Material.LEATHER_HELMET),
        CHESTPLATE(Material.LEATHER_CHESTPLATE),
        LEGGINGS(Material.LEATHER_LEGGINGS),
        BOOTS(Material.LEATHER_BOOTS);

        @Getter private final Material material;

        Type(Material material) {
            this.material = material;
        }
    }
}
