package com.orbitmines.minecraft.spigot.servers.fog.enchant;

import com.orbitmines.archive.minecraft._2019.libs.player.Languageable;
import com.orbitmines.minecraft.spigot.servers.fog.choice.ChoiceState;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

/** Simple re-renderer for enchantment lore. Real impl uses persistent data. */
public class EnchantRegistry {

    public static ItemStack apply(Languageable viewer, ItemStack stack, CustomEnchant enchant, int level, ChoiceState state) {
        if (stack == null) return null;
        ItemMeta meta = stack.getItemMeta();
        if (meta == null) return stack;

        List<String> lore = meta.hasLore() ? new ArrayList<>(meta.getLore()) : new ArrayList<>();
        String currentName = enchant.getDisplayName(viewer).toLowerCase();
        lore.removeIf(line -> line != null && line.toLowerCase().contains(currentName));
        lore.add(enchant.loreLine(viewer, state, level));
        meta.setLore(lore);
        stack.setItemMeta(meta);
        return stack;
    }

    public static ItemStack markDamaged(Languageable viewer, ItemStack stack, CustomEnchant enchant) {
        return apply(viewer, stack, enchant, 1, ChoiceState.DAMAGED);
    }
}
