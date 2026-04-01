package com.orbitmines.archive.minecraft.spigot._2019.servers.survival.gui.warp;

/*
 * OrbitMines - @author Fadi Shawki - 2018
 */

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.database.models.survival.SurvivalWarp;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.utils.SkullTexture;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.Survival;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.database.models.Warp;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.player.SurvivalPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.ItemBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.PlayerSkullBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.mutable.MutableItemBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.guis.GUI;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.anvilgui.AnvilNms;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.placeholders.SpigotServer;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.scheduler.BukkitRunnable;

public class WarpEditorGUI extends GUI<SurvivalPlayer> {

    private final Survival survival;
    private final Warp warp;

    public WarpEditorGUI(Survival survival, Warp warp, SurvivalPlayer viewer) {
        super(27, "§0§l" + warp.getName(), viewer);

        this.survival = survival;
        this.warp = warp;

        set(1, 1, new Item<SurvivalPlayer, MutableItemBuilder>(() -> {
            return new PlayerSkullBuilder("Cyan Arrow Left", SkullTexture.CYAN_ARROW_LEFT, 1, "§3« " + viewer.translate("survival", "player.warp.back_to_your_warps"));
        }, event -> {
            new WarpSlotsGUI(survival, viewer).open();
        }));

        set(1, 4, new Item<SurvivalPlayer, MutableItemBuilder>(() -> {
            return new ItemBuilder(Material.WRITABLE_BOOK, 1, "§7§l" + viewer.translate("survival", "player.warp.rename"));
        }, event -> {
            openNamePicker();
        }));

        set(1, 5, new Item<SurvivalPlayer, MutableItemBuilder>(() -> {
            ItemBuilder item = new ItemBuilder(warp.getLocation() == null ? Material.MAP : Material.FILLED_MAP, 1, "§7§l" + viewer.translate("survival", "player.warp.set_location"));

            if (warp.getLocation() == null)
                item.glow();

            return item;
        }, event -> {
            if (!viewer.isOpMode() && viewer.getLocation().getWorld() != survival.getWorld()) {
                viewer.sendMessage("Warp", Color.RED, "survival", "player.warp.only_overworld");
                return;
            }

            close();

            warp.setLocation(viewer.getLocation());
            warp.update(SurvivalWarp.column.LOCATION);

            viewer.sendMessage("Warp", Color.LIME, "survival", "player.warp.location_changed", "§3" + warp.getName() + "§7");
            viewer.playSound(Sound.ENTITY_ARROW_HIT_PLAYER);
        }));

        set(1, 6, new Item<SurvivalPlayer, MutableItemBuilder>(() -> {
            return warp.getIcon().getItemBuilder().setDisplayName("§7§l" + viewer.translate("survival", "player.warp.change_icon"));
        }, event -> {
            new WarpIconEditorGUI(survival, warp, viewer).open();
        }));

        set(1, 7, new Item<SurvivalPlayer, MutableItemBuilder>(() -> {
            return new ItemBuilder(warp.isEnabled() ? Material.ENDER_EYE : Material.ENDER_PEARL, 1, "§7§l" + viewer.translate("survival", "player.warp.teleporting_setting") + " " + (warp.isEnabled() ? "§a§l" + viewer.translate("survival", "player.warp.teleporting_setting.enabled") : "§c§l" + viewer.translate("survival", "player.warp.teleporting_setting.disabled")));
        }, event -> {
            if (warp.getLocation() == null)
                return;

            warp.setEnabled(!warp.isEnabled());
            warp.update(SurvivalWarp.column.ENABLED);

            viewer.playSound(Sound.UI_BUTTON_CLICK);
            update();
        }));
    }

    private void openNamePicker() {
        AnvilNms anvil = survival.getNms().anvilGui(viewer.bukkit(), (event) -> {
            if (event.getSlot() != AnvilNms.AnvilSlot.OUTPUT) {
                return;
            }

            String warpName = event.getName();
            Warp warp = survival.getWarp(warpName);

            if (warp == null) {
                if (warpName.length() > Warp.MAX_CHARACTERS) {
                    viewer.sendMessage("Warp", Color.RED, "survival", "player.warp.max_characters");
                    return;
                }

                for (int i = 0; i < warpName.length(); i++) {
                    char c = warpName.charAt(i);
                    if (!Character.isAlphabetic(c) && !Character.isDigit(c) && c != '_' && c != ' ') {
                        viewer.sendRawMessage("Warp", Color.RED, "You are only allowed to use normal letters, numbers, underscores and spaces.");
                        return;
                    }
                }

                event.getAnvilNms().destroy();
                close();

                this.warp.setName(warpName);
                this.warp.update(SurvivalWarp.column.NAME);

                viewer.playSound(Sound.ENTITY_ARROW_HIT_PLAYER);
                viewer.sendMessage("Warp", Color.LIME, "survival", "player.warp.renamed", "§3" + warpName + "§7");
            } else {
                viewer.sendMessage("Warp", Color.RED, "survival", "player.warp.name_taken");
            }
        }, new AnvilNms.AnvilCloseEvent() {
            @Override
            public void onClose() {
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        open();
                    }
                }.runTaskLater(survival.getPlugin(),1);
            }
        });

        anvil.getItems().put(AnvilNms.AnvilSlot.INPUT_LEFT, warp.getIcon().getItemBuilder().setDisplayName(warp.getName()).build());

        SpigotServer.getInstance().runSync(anvil::open);
    }
}
