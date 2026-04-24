package com.orbitmines.minecraft.spigot.servers.fog.shop;

import com.orbitmines.archive.minecraft._2019.libs.player.Languageable;
import lombok.Getter;
import org.bukkit.Material;

/** One offer in the shop. Text comes from {@code fog/shop.<id>.{name,description}}. */
public class ShopOffer {

    @Getter private final String id;
    @Getter private final Material icon;
    @Getter private final int priceCredits;

    public ShopOffer(String id, Material icon, int priceCredits) {
        this.id = id;
        this.icon = icon;
        this.priceCredits = priceCredits;
    }

    public String nameKey()        { return "shop." + id + ".name"; }
    public String descriptionKey() { return "shop." + id + ".description"; }

    public String getDisplayName(Languageable viewer) { return viewer.translate("fog", nameKey()); }
    public String getDescription(Languageable viewer) { return viewer.translate("fog", descriptionKey()); }
}
