package com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.guis;

/*
 * OrbitMines - @author Fadi Shawki - 2018
 */

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft._2019.libs.database.models.PlayerModel;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMServer;
import com.orbitmines.archive.minecraft._2019.utils.NumberUtils;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.ItemBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.ItemBuilderInstance;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.mutable.MutableItemBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.guis.GUI;
import org.bukkit.Material;
import org.bukkit.Sound;

public class PrismSolarShopGUI<S extends OMServer<S, P>, P extends OMPlayer<S, P>> extends GUI<P> {

    public PrismSolarShopGUI(P viewer) {
        super(45, "§0§lPrism & Solar Shop", viewer);
        
        set(1, 3, new Item<P, MutableItemBuilder>(() -> new ItemBuilder(Material.SUNFLOWER, 1, "§e§l" + NumberUtils.locale(viewer.getSolars()) + " Solar" + (viewer.getSolars() == 1 ? "" : "s"))));
        set(1, 5, new Item<P, MutableItemBuilder>(() -> new ItemBuilder(Material.PRISMARINE_SHARD, 1, "§9§l" + NumberUtils.locale(viewer.getPrisms()) + " Prism" + (viewer.getPrisms() == 1 ? "" : "s"))));
    }

    public static class ShopItem<S extends OMServer<S, P>, P extends OMPlayer<S, P>> extends Item<P, MutableItemBuilder> {

        public ShopItem(P player, GUI<P> gui, Currency currency, int price, MutableItemBuilder item, ShopHandler<S, P> handler, String... description) {
            super(() -> {
                ItemBuilderInstance builder = item.toBuilder();

                builder.addLore("§7" + player.translate("spigot", "player.prism_shop.price")+ ": " + currency.color.getCc() + "§l" + NumberUtils.locale(price) + " " + currency.name);
                builder.addLore("");

                for (String desc : description) {
                    builder.addLore("§7 - " + desc);
                }

                if (handler.showClickToBuy(player)) {
                    builder.addLore("");
                    builder.addLore("§a" + player.translate("spigot", "player.prism_shop.buy.hover"));
                }

                return builder;
            }, event -> {
                if (currency.has(player, price)) {
                    if (handler.canReceive(player)) {
                        currency.remove(player, price);
                        handler.give(player);
                        gui.update();

                        StringBuilder received = new StringBuilder();
                        for (int i = 0; i < description.length; i++) {
                            if (i != 0) {
                                if (i == description.length - 1)
                                    received.append("§7 & ");
                                else
                                    received.append("§7, ");
                            }

                            received.append(description[i]);
                        }
                        received.append("§7");

                        player.sendMessage("Shop", Color.SUCCESS, "spigot", "player.prism_shop.received", received.toString());
                    }
                } else {
                    player.playSound(Sound.ENTITY_ENDERMAN_SCREAM);
                    player.sendMessage("Shop", Color.RED, "spigot", "player.prism_shop.insufficient_funds", currency.color.getCc() + "§l" + currency.name + "§7");
                }
            });
        }
    }

    @FunctionalInterface
    public interface ShopHandler<S extends OMServer<S, P>, P extends OMPlayer<S, P>> {

        void give(P player);

        /* Override to use */
        default boolean canReceive(P player) {
            return true;
        }

        default boolean showClickToBuy(P player) {
            return true;
        }
    }

    public enum Currency {

        PRISMS(Color.BLUE, "Prisms") {
            @Override
            public int get(OMPlayer player) {
                return player.getPrisms();
            }

            @Override
            public void remove(OMPlayer player, int price) {
                player.removePrisms(price);
                player.update(PlayerModel.column.PRISMS);
            }
        },
        SOLARS(Color.YELLOW, "Solars") {
            @Override
            public int get(OMPlayer player) {
                return player.getSolars();
            }

            @Override
            public void remove(OMPlayer player, int price) {
                player.removeSolars(price);
                player.update(PlayerModel.column.SOLARS);
            }
        };

        private final Color color;
        private final String name;

        Currency(Color color, String name) {
            this.color = color;
            this.name = name;
        }

        public boolean has(OMPlayer player, int price) {
            return get(player) >= price;
        }

        public int get(OMPlayer player) {
            throw new IllegalStateException();
        }

        public void remove(OMPlayer player, int price) {
            throw new IllegalStateException();
        }
    }
}
