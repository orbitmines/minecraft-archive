package com.orbitmines.archive.minecraft.spigot._2019.servers.creative.gui;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.utils.DateUtils;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.utils.SkullTexture;
import com.orbitmines.archive.minecraft.spigot._2019.servers.creative.Creative;
import com.orbitmines.archive.minecraft.spigot._2019.servers.creative.CreativePlayer;
import com.orbitmines.archive.minecraft.spigot._2019.servers.creative.CreativeWorld;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.ItemBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.PlayerSkullBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.mutable.MutableItemBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.guis.GUI;
import org.bukkit.Material;
import org.bukkit.Sound;

import java.util.List;

public class WorldListGUI extends GUI<CreativePlayer> {

    private static final int MAX_ROWS = 5;
    private static final int ITEMS_PER_PAGE = MAX_ROWS * 9;

    private final Creative creative;
    private final List<CreativeWorld> worlds;
    private final boolean isOwner;
    private final int page;

    /* Own worlds */
    public WorldListGUI(Creative creative, CreativePlayer viewer) {
        this(creative, viewer, viewer.getWorlds(), true, viewer.translate("creative", "player.world.list.title"), 0);
    }

    /* Another player's worlds */
    public WorldListGUI(Creative creative, CreativePlayer viewer, List<CreativeWorld> worlds, String ownerName) {
        this(creative, viewer, worlds, false, ownerName, 0);
    }

    private WorldListGUI(Creative creative, CreativePlayer viewer, List<CreativeWorld> worlds, boolean isOwner, String title, int page) {
        super(computeSize(worlds.size(), isOwner, page, totalPages(worlds.size(), isOwner)), "§0§l" + title, viewer);
        this.creative = creative;
        this.worlds = worlds;
        this.isOwner = isOwner;
        this.page = page;

        int totalItems = worlds.size() + (isOwner ? 1 : 0);
        int totalPages = totalPages(worlds.size(), isOwner);
        boolean hasNav = totalPages > 1;

        int start = page * ITEMS_PER_PAGE;
        int end = Math.min(worlds.size(), start + ITEMS_PER_PAGE);

        int slot = 0;
        for (int i = start; i < end; i++) {
            set(slot++, worldItem(worlds.get(i)));
        }

        /* Create New World button — show on last page if owner */
        if (isOwner && page == totalPages - 1 && slot < ITEMS_PER_PAGE) {
            set(slot, new Item<CreativePlayer, MutableItemBuilder>(() -> {
                return new ItemBuilder(Material.LIME_CONCRETE, 1,
                    "§a§l" + viewer.translate("creative", "player.world.list.create"));
            }, event -> {
                new WorldCreateGUI(creative, viewer).open();
            }));
        }

        /* Navigation row */
        if (hasNav) {
            int navRow = MAX_ROWS;
            int navBase = navRow * 9;

            if (page > 0) {
                set(navBase, new Item<CreativePlayer, MutableItemBuilder>(() -> {
                    return new PlayerSkullBuilder("Cyan Arrow Left", SkullTexture.CYAN_ARROW_LEFT, 1,
                        "§3« " + viewer.translate("spigot", "word.previous"));
                }, event -> {
                    new WorldListGUI(creative, viewer, worlds, isOwner, viewer.translate("creative", "player.world.list.title"), page - 1).open();
                }));
            }

            if (page < totalPages - 1) {
                set(navBase + 8, new Item<CreativePlayer, MutableItemBuilder>(() -> {
                    return new PlayerSkullBuilder("Cyan Arrow Right", SkullTexture.CYAN_ARROW_RIGHT, 1,
                        "§3" + viewer.translate("spigot", "word.next") + " »");
                }, event -> {
                    new WorldListGUI(creative, viewer, worlds, isOwner, viewer.translate("creative", "player.world.list.title"), page + 1).open();
                }));
            }
        }
    }

    private static int totalPages(int worldCount, boolean isOwner) {
        int totalItems = worldCount + (isOwner ? 1 : 0);
        return Math.max(1, (int) Math.ceil((double) totalItems / ITEMS_PER_PAGE));
    }

    private static int computeSize(int worldCount, boolean isOwner, int page, int totalPages) {
        boolean hasNav = totalPages > 1;
        int contentRows;

        if (hasNav) {
            /* Always use max rows + 1 nav row when paginated */
            contentRows = MAX_ROWS + 1;
        } else {
            int totalItems = worldCount + (isOwner ? 1 : 0);
            contentRows = Math.max(1, (int) Math.ceil((double) totalItems / 9.0));
        }

        return 9 * contentRows;
    }

    private Item<CreativePlayer, MutableItemBuilder> worldItem(CreativeWorld world) {
        return new Item<>(() -> {
            Material icon = getWorldIcon(world);
            ItemBuilder item = new ItemBuilder(icon, 1, "§d§l" + world.getName());

            item.addLore("");
            item.addLore(" §7" + viewer.translate("creative", "player.world.list.type", "§f§l" + world.getWorldGenerator().name()));
            item.addLore(" §7" + viewer.translate("creative", "player.world.list.server", world.getServer().getDisplayName()));
            item.addLore(" §7" + viewer.translate("creative", "player.world.list.map_type", "§f§l" + world.getWorldType().name()));
            item.addLore(" §7" + viewer.translate("creative", "player.world.list.members", "§f§l" + world.getMembers(false).size()));
            item.addLore(" §7" + viewer.translate("creative", "player.world.list.created", "§f§l" + DateUtils.format(world.getCreatedAt(), DateUtils.DATE_FORMAT)));

            if (world.isLoaded())
                item.addLore(" §a" + viewer.translate("creative", "player.world.list.loaded"));

            item.addLore("");
            if (isOwner) {
                item.addLore("§6§lLEFT CLICK §8- §7" + viewer.translate("creative", "player.world.list.left_click_action"));
                item.addLore("§e§lRIGHT CLICK §8- §a" + viewer.translate("creative", "player.world.list.right_click_action"));
            } else {
                item.addLore("§e§lCLICK §8- §a" + viewer.translate("creative", "player.world.list.right_click_action"));
            }

            return item;
        }, event -> {
            if (isOwner && event.getClick().isLeftClick()) {
                /* Left-click: edit (own worlds only) */
                new WorldEditorGUI(creative, world, viewer).open();
            } else {
                /* Right-click (or any click for other player's worlds): teleport */
                close();
                viewer.playSound(Sound.ENTITY_ENDERMAN_TELEPORT);
                creative.loadAndTeleport(viewer, world);
            }
        });
    }

    @Override
    public void open() {
        super.open();

        viewer.playSound(Sound.ITEM_ARMOR_EQUIP_DIAMOND);
    }

    private Material getWorldIcon(CreativeWorld world) {
        switch (world.getWorldGenerator()) {
            case NORMAL:
                return Material.GRASS_BLOCK;
            case NETHER:
                return Material.NETHERRACK;
            case END:
                return Material.END_STONE;
            case FLAT:
                return Material.SMOOTH_STONE;
            case VOID:
                return Material.GLASS;
            default:
                return Material.MAP;
        }
    }
}
