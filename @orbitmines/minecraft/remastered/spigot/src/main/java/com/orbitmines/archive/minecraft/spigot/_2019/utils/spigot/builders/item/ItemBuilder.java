package com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import org.bukkit.Material;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class ItemBuilder extends ItemBuilderInstance<ItemBuilder, ItemMeta> {

    public ItemBuilder(Material material) {
        super(material);
    }

    public ItemBuilder(Material material, int amount) {
        super(material, amount);
    }

    public ItemBuilder(Material material, int amount, String displayName) {
        super(material, amount, displayName);
    }

    public ItemBuilder(Material material, int amount, String displayName, String... lore) {
        super(material, amount, displayName, lore);
    }

    public ItemBuilder(ItemBuilder itemBuilder) {
        super(itemBuilder);
    }

    public ItemBuilder(Material material, int amount, String displayName, List<String> lore) {
        super(material, amount, displayName, lore);
    }

    @Override
    public ItemBuilder clone() {
        return new ItemBuilder(this);
    }

    @Override
    protected ItemBuilder getInstance() {
        return this;
    }
}
