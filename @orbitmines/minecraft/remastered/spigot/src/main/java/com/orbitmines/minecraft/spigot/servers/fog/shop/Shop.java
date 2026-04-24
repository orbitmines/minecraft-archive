package com.orbitmines.minecraft.spigot.servers.fog.shop;

import org.bukkit.Material;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Catalogue of shop offers. Translations live in fog_*.properties under `shop.<id>.{name,description}`. */
public class Shop {

    public static final List<ShopOffer> DEFAULT_OFFERS;

    static {
        List<ShopOffer> offers = new ArrayList<>();
        offers.add(new ShopOffer("key_basic",          Material.TRIPWIRE_HOOK,    500));
        offers.add(new ShopOffer("key_epic",           Material.LAPIS_LAZULI,    2500));
        offers.add(new ShopOffer("xp_bottle",          Material.EXPERIENCE_BOTTLE, 300));
        offers.add(new ShopOffer("enchant_autoreplant",Material.ENCHANTED_BOOK,  4000));
        offers.add(new ShopOffer("enchant_homing",     Material.ENCHANTED_BOOK,  6000));
        offers.add(new ShopOffer("recipe_pickaxe",     Material.DIAMOND_PICKAXE, 8000));
        offers.add(new ShopOffer("recipe_suit",        Material.IRON_CHESTPLATE, 9000));
        offers.add(new ShopOffer("module_upgrade",     Material.SMITHING_TABLE, 12000));
        offers.add(new ShopOffer("enchant_remove",     Material.GRINDSTONE,      2000));
        DEFAULT_OFFERS = Collections.unmodifiableList(offers);
    }
}
