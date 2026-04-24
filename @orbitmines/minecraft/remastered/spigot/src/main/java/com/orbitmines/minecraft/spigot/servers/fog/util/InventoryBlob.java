package com.orbitmines.minecraft.spigot.servers.fog.util;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.io.ByteArrayOutputStream;
import java.util.Base64;

/**
 * Serialize a player's inventory to a single string and back. Uses the built-in
 * YAML serialization of ItemStack which handles NBT correctly for all vanilla
 * items as well as persistent-data-container fields used by FoGItem.
 *
 * Stored as base64-encoded UTF-8 YAML text.
 */
public class InventoryBlob {

    public static String serialize(Player player) {
        PlayerInventory inv = player.getInventory();
        YamlConfiguration yaml = new YamlConfiguration();
        for (int i = 0; i < inv.getSize(); i++) {
            ItemStack stack = inv.getItem(i);
            if (stack != null) yaml.set("slot." + i, stack);
        }
        yaml.set("helmet", inv.getHelmet());
        yaml.set("chestplate", inv.getChestplate());
        yaml.set("leggings", inv.getLeggings());
        yaml.set("boots", inv.getBoots());
        yaml.set("offhand", inv.getItemInOffHand());
        String s = yaml.saveToString();
        return Base64.getEncoder().encodeToString(s.getBytes());
    }

    public static void deserializeInto(Player player, String blob) {
        if (blob == null || blob.isEmpty()) return;
        String yaml = new String(Base64.getDecoder().decode(blob));
        YamlConfiguration conf = new YamlConfiguration();
        try {
            conf.loadFromString(yaml);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        PlayerInventory inv = player.getInventory();
        inv.clear();
        if (conf.isConfigurationSection("slot")) {
            for (String key : conf.getConfigurationSection("slot").getKeys(false)) {
                try {
                    int idx = Integer.parseInt(key);
                    ItemStack stack = conf.getItemStack("slot." + key);
                    if (stack != null) inv.setItem(idx, stack);
                } catch (NumberFormatException ignored) {}
            }
        }
        inv.setHelmet(conf.getItemStack("helmet"));
        inv.setChestplate(conf.getItemStack("chestplate"));
        inv.setLeggings(conf.getItemStack("leggings"));
        inv.setBoots(conf.getItemStack("boots"));
        ItemStack off = conf.getItemStack("offhand");
        if (off != null) inv.setItemInOffHand(off);
    }
}
