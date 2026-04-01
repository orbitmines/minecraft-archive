package com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EnchantedBookBuilder extends ItemBuilderInstance<EnchantedBookBuilder, EnchantmentStorageMeta> {

    @Getter protected Map<Enchantment, Integer> storedEnchantments;

    public EnchantedBookBuilder(Material material) {
        super(material);
    }

    public EnchantedBookBuilder(Material material, int amount) {
        super(material, amount);
    }

    public EnchantedBookBuilder(Material material, int amount, String displayName) {
        super(material, amount, displayName);
    }

    public EnchantedBookBuilder(Material material, int amount, String displayName, String... lore) {
        super(material, amount, displayName, lore);
    }

    public EnchantedBookBuilder(Material material, int amount, String displayName, List<String> lore) {
        super(material, amount, displayName, lore);
    }

    public EnchantedBookBuilder(EnchantedBookBuilder itemBuilder) {
        super(itemBuilder);

        this.storedEnchantments = new HashMap<>(itemBuilder.storedEnchantments);
    }

    public EnchantedBookBuilder setStoredEnchantments(Map<Enchantment, Integer> storedEnchantments) { this.storedEnchantments = storedEnchantments; return this; }
    public EnchantedBookBuilder addStoredEnchantment(Enchantment storedEnchantment, int level) { this.storedEnchantments.put(storedEnchantment, level); return this; }

    @Override
    public EnchantedBookBuilder clone() {
        return new EnchantedBookBuilder(this);
    }

    @Override
    protected EnchantedBookBuilder getInstance() {
        return this;
    }

    @Override
    public EnchantmentStorageMeta buildMeta(EnchantmentStorageMeta meta) {
        meta = super.buildMeta(meta);

        for (Enchantment enchantment : this.storedEnchantments.keySet()) {
            meta.addStoredEnchant(enchantment, this.storedEnchantments.get(enchantment), true);
        }

        return meta;
    }
}
