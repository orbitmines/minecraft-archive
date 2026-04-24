package com.orbitmines.minecraft.spigot.servers.fog.item;

import com.orbitmines.archive.minecraft._2019.libs.player.Languageable;
import com.orbitmines.minecraft.spigot.servers.fog.FoG;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BundleMeta;

/** Custom "Backpack" — a stamped bundle that can hold more than a vanilla stack. */
public class Backpack {

    public static final String NS = "fog";
    public static final String KEY_TIER = "backpack_tier";

    public static ItemStack create(FoG server, Languageable viewer, int tier) {
        ItemStack stack = new ItemStack(Material.BUNDLE);
        BundleMeta meta = (BundleMeta) stack.getItemMeta();
        if (meta != null) {
            meta.setDisplayName("§6§l" + viewer.translate("fog", "item.backpack.name", tier));
            stack.setItemMeta(meta);
        }
        return server.getNms().customItem().setMetaData(stack, NS, KEY_TIER, tier);
    }

    public static Integer getTier(FoG server, ItemStack stack) {
        return server.getNms().customItem().getMetaDataInt(stack, NS, KEY_TIER);
    }
}
