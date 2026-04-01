package com.orbitmines.archive.minecraft.spigot._2019.servers.survival.gui.chestshop;

/*
 * Survival - @author Fadi Shawki - 2018
 */

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.database.models.survival.SurvivalChestShop;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.Survival;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.database.models.ChestShop;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.player.SurvivalPlayer;
import com.orbitmines.archive.minecraft._2019.utils.EnumUtils;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.ItemUtils;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.ItemBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.mutable.MutableItemBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.guis.GUI;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.anvilgui.AnvilNms;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.placeholders.SpigotServer;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.scheduler.BukkitRunnable;

public class ChestShopEditorGUI extends GUI<SurvivalPlayer> {

    private final Survival server;

    private Material material;
    private int amount;
    private int price;
    private SurvivalChestShop.PurchaseType purchaseType;

    public ChestShopEditorGUI(Survival server, SurvivalPlayer viewer, ChestShop shop) {
        super(27, "§0§lChest Shop Editor", viewer);
        this.server = server;

        this.material = shop.getMaterial();
        this.amount = shop.getAmount();
        this.price = shop.getPrice();
        this.purchaseType = shop.getPurchaseType();

        set(1, 1, new Item<SurvivalPlayer, MutableItemBuilder>(() -> {
            return new ItemBuilder(material, 1, "§7§lItem: §a§l" + ItemUtils.getName(this.material));
        }, event -> {
            openMaterialPicker();
        }));

        set(1, 2, new Item<SurvivalPlayer, MutableItemBuilder>(() -> {
            return new ItemBuilder(Material.MAP, 1, "§7§l" + viewer.translate("survival", "player.chest_shop.amount") + ": §a§l" + amount);
        }, event -> {
            openAmountPicker();
        }));

        set(1, 3, new Item<SurvivalPlayer, MutableItemBuilder>(() -> {
            return new ItemBuilder(Material.TURTLE_SCUTE, 1, "§7§l" + viewer.translate("survival", "player.chest_shop.price") + ": " + shop.getPriceDisplay(price));
        }, event -> {
            openPricePicker();
        }));

        set(1, 4, new Item<SurvivalPlayer, MutableItemBuilder>(() -> {
            return new ItemBuilder(this.purchaseType == SurvivalChestShop.PurchaseType.BUY ? Material.CHEST_MINECART : Material.HOPPER_MINECART, 1, "§7§lShop Type: §a§l" + this.purchaseType.getName(viewer.getLanguage()));
        }, event -> {
            this.purchaseType = EnumUtils.next(SurvivalChestShop.PurchaseType.class, this.purchaseType);
            update();
        }));

        set(1, 6, new Item<SurvivalPlayer, MutableItemBuilder>(() -> {
            return new ItemBuilder(Material.OAK_SIGN, 1, "§a§l" + viewer.translate("survival", "player.chest_shop.save"));
        }, event -> {
            shop.setMaterial(this.material);
            shop.setPrice(this.price);
            shop.setAmount(this.amount);
            shop.setPurchaseType(this.purchaseType);
            
            shop.update(
                SurvivalChestShop.column.MATERIAL, 
                SurvivalChestShop.column.PRICE,
                SurvivalChestShop.column.AMOUNT,
                SurvivalChestShop.column.PURCHASE_TYPE
            );
            
            close();
            viewer.playSound(Sound.ENTITY_ARROW_HIT_PLAYER);
        }));
        
        set(1, 7, new Item<SurvivalPlayer, MutableItemBuilder>(() -> {
            return new ItemBuilder(Material.BARRIER, 1, "§c§l" + viewer.translate("survival", "player.chest_shop.cancel"));
        }, event -> {
            close();
        }));
    }

    private void openMaterialPicker() {
        AnvilNms anvil = server.getNms().anvilGui(viewer.bukkit(), (event) -> {
            String name = event.getName();

            if (event.getSlot() != AnvilNms.AnvilSlot.OUTPUT || name.equals("")) {
                return;
            }

            Material material;
            try {
                material = Material.valueOf(name.replace("minecraft:", "").replaceAll(" ", "_").toUpperCase());
            } catch (IllegalArgumentException ex) {
                event.getAnvilNms().setSlot(AnvilNms.AnvilSlot.OUTPUT, new ItemBuilder(this.material, 1, "minecraft:", "minecraft:").build());

                viewer.sendMessage("Shop", Color.RED, "survival", "player.chest_shop.unknown_item");

                return;
            }

            event.getAnvilNms().destroy();
            close();

            new BukkitRunnable() {
                @Override
                public void run() {
                    ChestShopEditorGUI.this.material = material;
                    update();
                    open();
                }
            }.runTaskLater(server, 2);

        }, new AnvilNms.AnvilCloseEvent() {
            @Override
            public void onClose() {
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        open();
                    }
                }.runTaskLater(server, 1);
            }
        });

        anvil.getItems().put(AnvilNms.AnvilSlot.INPUT_LEFT, new ItemBuilder(material, 1, "minecraft:", "minecraft:").build());

        SpigotServer.getInstance().runSync(anvil::open);
    }

    private void openAmountPicker() {
        AnvilNms anvil = server.getNms().anvilGui(viewer.bukkit(), (event) -> {
            String name = event.getName();

            if (event.getSlot() != AnvilNms.AnvilSlot.OUTPUT || name.equals("")) {
                return;
            }

            int amount;
            try {
                amount = Integer.parseInt(name);
            } catch (NumberFormatException ex) {
                event.getAnvilNms().setSlot(AnvilNms.AnvilSlot.OUTPUT, new ItemBuilder(Material.MAP, 1, this.amount + "").build());

                viewer.sendMessage("Shop", Color.RED, "survival", "player.chest_shop.invalid_amount");

                return;
            }

            if (amount < 1 || amount > ChestShop.MAX_AMOUNT) {
                event.getAnvilNms().setSlot(AnvilNms.AnvilSlot.OUTPUT, new ItemBuilder(Material.MAP, 1, amount + "").build());

                viewer.sendMessage("Shop", Color.RED, "survival", "player.chest_shop.max_amount", ChestShop.MAX_AMOUNT);
                return;
            }

            event.getAnvilNms().destroy();
            close();

            new BukkitRunnable() {
                @Override
                public void run() {
                    ChestShopEditorGUI.this.amount = amount;
                    update();
                    open();
                }
            }.runTaskLater(server, 2);

        }, new AnvilNms.AnvilCloseEvent() {
            @Override
            public void onClose() {
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        open();
                    }
                }.runTaskLater(server, 1);
            }
        });

        anvil.getItems().put(AnvilNms.AnvilSlot.INPUT_LEFT, new ItemBuilder(Material.MAP, 1, amount + "").build());

        SpigotServer.getInstance().runSync(anvil::open);
    }

    private void openPricePicker() {
        AnvilNms anvil = server.getNms().anvilGui(viewer.bukkit(), (event) -> {
            String name = event.getName();

            if (event.getSlot() != AnvilNms.AnvilSlot.OUTPUT || name.equals("")) {
                return;
            }

            int price;
            try {
                price = Integer.parseInt(name);
            } catch (NumberFormatException ex) {
                event.getAnvilNms().setSlot(AnvilNms.AnvilSlot.OUTPUT, new ItemBuilder(Material.TURTLE_SCUTE, 1, this.price + "").build());

                viewer.sendMessage("Shop", Color.RED, "survival", "player.chest_shop.invalid_price");

                return;
            }

            if (price < 1 || price > ChestShop.MAX_PRICE) {
                event.getAnvilNms().setSlot(AnvilNms.AnvilSlot.OUTPUT, new ItemBuilder(Material.TURTLE_SCUTE, 1, this.price + "").build());

                viewer.sendMessage("Shop", Color.RED, "survival", "player.chest_shop.max_price", ChestShop.MAX_PRICE);

                return;
            }

            event.getAnvilNms().destroy();
            close();

            new BukkitRunnable() {
                @Override
                public void run() {
                    ChestShopEditorGUI.this.price = price;
                    update();
                    open();
                }
            }.runTaskLater(server, 2);

        }, new AnvilNms.AnvilCloseEvent() {
            @Override
            public void onClose() {
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        open();
                    }
                }.runTaskLater(server, 1);
            }
        });

        anvil.getItems().put(AnvilNms.AnvilSlot.INPUT_LEFT, new ItemBuilder(Material.TURTLE_SCUTE, 1, price + "").build());

        SpigotServer.getInstance().runSync(anvil::open);
    }
}
