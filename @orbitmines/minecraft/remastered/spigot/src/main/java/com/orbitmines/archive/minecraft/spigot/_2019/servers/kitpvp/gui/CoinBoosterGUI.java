package com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.gui;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.guis.PrismSolarShopGUI;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.CoinBooster;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.KitPvP;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.KitPvPPlayer;
import com.orbitmines.archive.minecraft._2019.utils.TimeUtils;

public class CoinBoosterGUI extends PrismSolarShopGUI<KitPvP, KitPvPPlayer> {

    public CoinBoosterGUI(KitPvPPlayer viewer) {
        super(viewer);

        int slot = 2;
        for (CoinBooster.Type type : CoinBooster.Type.values()) {
            set(3, slot, new ShopItem<>(viewer, this, Currency.SOLARS, type.getPrice(),
                () -> type.getIcon().setDisplayName(type.getDisplayName()),

                new ShopHandler<KitPvP, KitPvPPlayer>() {
                    @Override
                    public void give(KitPvPPlayer player) {
                        CoinBooster booster = new CoinBooster(viewer.server(), type, player.getUUID());
                        booster.start();
                    }

                    @Override
                    public boolean canReceive(KitPvPPlayer player) {
                        return CoinBooster.ACTIVE == null && player.isEligible(type.getVipRank());
                    }

                    @Override
                    public boolean showClickToBuy(KitPvPPlayer player) {
                        return CoinBooster.ACTIVE == null && player.isEligible(type.getVipRank());
                    }
                },
                "§6§lx" + String.format("%.2f", type.getMultiplier()) + " Coin Booster", "§b§l" + TimeUtils.humanFriendlyTimer(viewer.getLanguage(), type.getDuration().toMillis()),
                "§7Applies to everyone"
            ));

            slot++;
        }
    }
}
