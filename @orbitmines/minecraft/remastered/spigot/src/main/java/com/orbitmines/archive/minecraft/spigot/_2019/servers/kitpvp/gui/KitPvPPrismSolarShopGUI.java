package com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.gui;

/*
 * OrbitMines - @author Fadi Shawki - 2018
 */

import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.database.models.kitpvp.KitPvPPlayerModel;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.guis.PrismSolarShopGUI;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.KitPvP;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.KitPvPPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.ItemBuilder;
import org.bukkit.Material;

public class KitPvPPrismSolarShopGUI extends PrismSolarShopGUI<KitPvP, KitPvPPlayer> {

    public KitPvPPrismSolarShopGUI(KitPvPPlayer viewer) {
        super(viewer);

        set(3, 2, new ShopItem<>(viewer, this, Currency.PRISMS, 100,
            () -> new ItemBuilder(Material.GOLD_NUGGET, 1, "§6§lSmall Coin Package"),
            player -> {
                player.addCoins(200);

                player.update(KitPvPPlayerModel.column.COINS);
            }, "§6§l200 Coins"));

        set(3, 4, new ShopItem<>(viewer, this, Currency.PRISMS, 500,
            () -> new ItemBuilder(Material.GOLD_NUGGET, 5, "§6§lLarge Coin Package"),
            player -> {
                player.addCoins(1000);

                player.update(KitPvPPlayerModel.column.COINS);
            }, "§6§l1,000 Coins"));

        set(3, 6, new ShopItem<>(viewer, this, Currency.PRISMS, 1000,
            () -> new ItemBuilder(Material.GOLD_NUGGET, 10, "§6§lHuge Coin Package"),
            player -> {
                player.addCoins(2000);

                player.update(KitPvPPlayerModel.column.COINS);
            }, "§6§l2,000 Coins"));
    }
}
