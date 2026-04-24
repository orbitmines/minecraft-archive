package com.orbitmines.minecraft.spigot.servers.fog.gui;

import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.ItemBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.mutable.MutableItemBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.guis.GUI;
import com.orbitmines.minecraft.spigot.servers.fog.FoG;
import com.orbitmines.minecraft.spigot.servers.fog.FoGPlayer;
import com.orbitmines.minecraft.spigot.servers.fog.shop.Shop;
import com.orbitmines.minecraft.spigot.servers.fog.shop.ShopOffer;

public class ShopGUI extends GUI<FoGPlayer> {

    public ShopGUI(FoG server, FoGPlayer viewer) {
        super(54, "§0§l" + viewer.translate("fog", "gui.shop.title"), viewer);

        int slot = 0;
        for (ShopOffer offer : Shop.DEFAULT_OFFERS) {
            set(slot++, new Item<FoGPlayer, MutableItemBuilder>(() ->
                new ItemBuilder(offer.getIcon(), 1, "§e§l" + offer.getDisplayName(viewer))
                    .addLore("§7" + offer.getDescription(viewer))
                    .addLore(" ")
                    .addLore("§6§l" + viewer.translate("fog", "gui.shop.price", offer.getPriceCredits())),
                event -> viewer.sendMessage("FoG",
                        com.orbitmines.archive.minecraft._2019.libs.Color.YELLOW,
                        "fog", "shop.purchase_stub",
                        offer.getDisplayName(viewer))
            ));
            if (slot >= getInventory().getSize()) break;
        }
    }
}
