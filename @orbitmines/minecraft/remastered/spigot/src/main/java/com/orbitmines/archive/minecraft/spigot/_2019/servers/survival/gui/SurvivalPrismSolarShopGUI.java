package com.orbitmines.archive.minecraft.spigot._2019.servers.survival.gui;

/*
 * OrbitMines - @author Fadi Shawki - 2018
 */

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft._2019.libs.player.Name;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.database.models.survival.SurvivalPlayerModel;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.guis.PrismSolarShopGUI;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.Survival;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.player.SurvivalPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.ItemBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.PlayerSkullBuilder;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.inventory.ItemFlag;

public class SurvivalPrismSolarShopGUI extends PrismSolarShopGUI<Survival, SurvivalPlayer> {

    public SurvivalPrismSolarShopGUI(SurvivalPlayer viewer) {
        super(viewer);

        set(3, 1, new PrismSolarShopGUI.ShopItem<>(viewer, this, Currency.PRISMS, 200,
            () -> new ItemBuilder(Material.STONE_HOE, 1, "§a§lSurvival Package").addFlag(ItemFlag.HIDE_ATTRIBUTES),
            player -> {
                player.addClaimBlocks(100);
                player.addCredits(50);

                player.update(SurvivalPlayerModel.column.CLAIM_BLOCKS, SurvivalPlayerModel.column.CREDITS);
            }, "§9§l100 Claimblocks", "§2§l50 Credits"));

        set(3, 2, new PrismSolarShopGUI.ShopItem<>(viewer, this, Currency.PRISMS, 200,
            () -> new ItemBuilder(Material.TURTLE_SCUTE, 1, "§2§lCredits Package"),
            player -> {
                player.addCredits(200);
                player.update(SurvivalPlayerModel.column.CREDITS);
            }, "§2§l200 Credits"));

        set(3, 3, new PrismSolarShopGUI.ShopItem<>(viewer, this, Currency.PRISMS, 30000,
            () -> {
                if (viewer.isWarpSlotPrisms())
                    return new ItemBuilder(Material.BOOK, 1, "§c§lWarp Package", "§7§o" + viewer.translate("survival", "player.prism_shop.bought"), "");

                return new ItemBuilder(Material.WRITABLE_BOOK, 1, "§3§lWarp Package");
            }, new ShopHandler<Survival, SurvivalPlayer>() {
                @Override
                public void give(SurvivalPlayer player) {
                    player.setWarpSlotPrisms(true);
                    player.update(SurvivalPlayerModel.column.WARP_SLOT_PRISMS);
                }

                @Override
                public boolean canReceive(SurvivalPlayer player) {
                    return !viewer.isWarpSlotPrisms();
                }

            @Override
            public boolean showClickToBuy(SurvivalPlayer player) {
                return !viewer.isWarpSlotPrisms();
            }
        }, "§9§lPrisms Warp Slot"));

        set(3, 4, new PrismSolarShopGUI.ShopItem<>(viewer, this, Currency.PRISMS, 2500,
            () -> new ItemBuilder(Material.SPRUCE_DOOR, 1, "§6§lHome Package"),
            player -> {
                player.addExtraHomes(1);
                player.update(SurvivalPlayerModel.column.EXTRA_HOMES);
            }, "§6§l1 Home"));

        set(3, 5, new PrismSolarShopGUI.ShopItem<>(viewer, this, Currency.SOLARS, 1000,
            () -> {
                return new PlayerSkullBuilder(viewer.getUUID(), 1, "§a§lYour Skull Package");
            }, new ShopHandler<Survival, SurvivalPlayer>() {
            @Override
            public void give(SurvivalPlayer player) {
                player.server().runSync(() -> player.getInventory().addItem(new PlayerSkullBuilder(player.getUUID(), 1).build()));
            }

            @Override
            public boolean canReceive(SurvivalPlayer player) {
                boolean full = viewer.getInventory().firstEmpty() != -1;

                if (!full) {
                    player.sendMessage("Shop", Color.ERROR, "survival", "player.chest_shop.inventory_full");
                    player.playSound(Sound.ENTITY_ENDERMAN_SCREAM);
                }

                return full;
            }
        }, "§a§l1x " + viewer.getName(Name.RAW) + "'s Skull"));

        set(3, 6, new PrismSolarShopGUI.ShopItem<>(viewer, this, Currency.SOLARS, 50,
            () -> new ItemBuilder(Material.ENDER_EYE, 1, "§6§lBack Charges Package"),
            player -> {
                player.addBackCharges(10);
                player.update(SurvivalPlayerModel.column.BACK_CHARGES);
            }, "§6§l10 Back Charges"));

        set(3, 7, new PrismSolarShopGUI.ShopItem<>(viewer, this, Currency.SOLARS, 1500,
            () -> {
                return new ItemBuilder(Material.DIAMOND_PICKAXE, 1, "§5§lSpawner Miner Package").addFlag(ItemFlag.HIDE_ATTRIBUTES);
            }, new ShopHandler<Survival, SurvivalPlayer>() {
            @Override
            public void give(SurvivalPlayer player) {
                player.server().runSync(() -> player.getInventory().addItem(Survival.SPAWNER_MINER.build()));
            }

            @Override
            public boolean canReceive(SurvivalPlayer player) {
                boolean full = viewer.getInventory().firstEmpty() != -1;

                if (!full) {
                    player.sendMessage("Shop", Color.ERROR, "survival", "player.chest_shop.inventory_full");
                    player.playSound(Sound.ENTITY_ENDERMAN_SCREAM);
                }

                return full;
            }
        }, "§5§l1x " + Survival.SPAWNER_MINER.getDisplayName()));
    }
}
