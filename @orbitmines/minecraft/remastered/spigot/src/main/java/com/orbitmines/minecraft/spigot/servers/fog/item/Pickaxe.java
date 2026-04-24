package com.orbitmines.minecraft.spigot.servers.fog.item;

import com.orbitmines.archive.minecraft._2019.libs.player.Languageable;
import com.orbitmines.minecraft.spigot.servers.fog.FoG;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/** Pickaxe upgrade chain — crafted by combining a current pickaxe with ores. */
public class Pickaxe {

    public static final String NS = "fog";
    public static final String KEY_TIER = "pickaxe_tier";

    public static ItemStack tier(FoG server, Languageable viewer, int tier) {
        Material mat = switch (tier) {
            case 2 -> Material.IRON_PICKAXE;
            case 3 -> Material.DIAMOND_PICKAXE;
            case 4 -> Material.NETHERITE_PICKAXE;
            default -> Material.STONE_PICKAXE;
        };
        ItemStack stack = new ItemStack(mat);
        ItemMeta meta = stack.getItemMeta();
        if (meta != null) {
            meta.setDisplayName("§b§l" + viewer.translate("fog", "item.pickaxe.name", tier));
            stack.setItemMeta(meta);
        }
        return server.getNms().customItem().setMetaData(stack, NS, KEY_TIER, tier);
    }

    public static Integer getTier(FoG server, ItemStack stack) {
        return server.getNms().customItem().getMetaDataInt(stack, NS, KEY_TIER);
    }
}
