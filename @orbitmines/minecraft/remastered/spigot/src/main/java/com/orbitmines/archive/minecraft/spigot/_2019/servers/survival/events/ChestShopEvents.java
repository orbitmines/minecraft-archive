package com.orbitmines.archive.minecraft.spigot._2019.servers.survival.events;

/*
 * Survival - @author Fadi Shawki - 2018
 */

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.database.models.survival.SurvivalChestShop;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.Survival;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.database.models.ChestShop;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.gui.chestshop.ChestShopEditorGUI;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.gui.chestshop.ChestShopViewerGUI;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.player.SurvivalPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.BlockUtils;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.InventoryUtils;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.ItemUtils;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class ChestShopEvents implements Listener {

    private final Survival survival;

    public ChestShopEvents(Survival survival) {
        this.survival = survival;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Block block = event.getClickedBlock();

        if (block == null || !ItemUtils.isSign(block.getType()))
            return;

        ChestShop shop = survival.getChestShop(block);

        if (shop == null)
            return;

        SurvivalPlayer player = survival.getPlayer(event.getPlayer());

        if (shop.getUuid() != null && shop.getUuid().equals(player.getUUID())) {
            if (event.getAction() == Action.RIGHT_CLICK_BLOCK)
                new ChestShopEditorGUI(survival, player, shop).open();

            /* Else let them destroy it */

            return;
        }

        Chest chest = shop.getChest();

        if (chest == null) {
            player.sendMessage("Shop", Color.RED, "survival", "player.chest_shop.no_chest");
            return;
        }

        switch (event.getAction()) {
            case LEFT_CLICK_BLOCK: {
                if (player.getItemInMainHand() == null || player.getItemInMainHand().getType() == Material.AIR)
                    new ChestShopViewerGUI(shop, player).open();
                break;
            }
            case RIGHT_CLICK_BLOCK: {
                switch (shop.getPurchaseType()) {
                    case BUY: {
                        if (!shop.canBuy()) {
                            player.sendMessage("Shop", Color.RED, "survival", "player.chest_shop.sold_out");
                            break;
                        }

                        if (player.getCredits() < shop.getPrice()) {
                            int needed = shop.getPrice() - player.getCredits();
                            player.sendMessage("Shop", Color.RED, "survival", "player.chest_shop.insufficient_credits", shop.getPriceDisplay(needed) + "§7");
                            break;
                        }

                        if (InventoryUtils.getEmptySlotCount(player.getInventory()) < InventoryUtils.getSlotsRequired(shop.getMaterial(), shop.getAmount())) {
                            player.sendMessage("Shop", Color.RED, "survival", "player.chest_shop.inventory_full");
                            break;
                        }

                        shop.buy(player);
                        break;
                    }
                    case SELL: {
                        if (!shop.canSell()) {
                            player.sendMessage("Shop", Color.RED, "survival", "player.chest_shop.chest_full");
                            break;
                        }

                        if (!shop.hasCredits()) {
                            player.sendMessage("Shop", Color.RED, "survival", "player.chest_shop.owner.insufficient_credits");
                            break;
                        }

                        if (InventoryUtils.getAmount(player.getInventory(), shop.getMaterial()) < shop.getAmount()) {
                            player.sendMessage("Shop", Color.RED, "survival", "player.chest_shop.not_enough_items");
                            break;
                        }

                        shop.sell(player);
                        break;
                    }
                }
                break;
            }
        }
    }

    @EventHandler
    public void onSignChange(SignChangeEvent event) {
        if (event.isCancelled())
            return;

        String[] lines = event.getLines();

        if (lines[0] == null || (!lines[0].equalsIgnoreCase("[shop]")))
            return;

        SurvivalPlayer player = survival.getPlayer(event.getPlayer());
        Block block = event.getBlock();

        if (BlockUtils.getChestAtSign(block.getLocation()) == null) {
            block.breakNaturally();
            player.sendMessage("Shop", Color.RED, "survival", "player.chest_shop.no_chest_found");
            return;
        }

        ChestShop shop = new ChestShop(player.getSurvivalModel(), player.getUUID(), block.getLocation(), Material.COBBLESTONE, SurvivalChestShop.PurchaseType.BUY, 1, 1);
        shop.insert();
        survival.getChestShops().add(shop);

        player.sendMessage("Shop", Color.LIME, "survival", "player.chest_shop.created_successfully");

        new BukkitRunnable() {
            @Override
            public void run() {
                shop.update();
            }
        }.runTaskLater(survival, 1);
    }
}
