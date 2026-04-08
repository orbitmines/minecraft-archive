package com.orbitmines.archive.minecraft.spigot._2019.servers.creative.gui;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft._2019.libs.Server;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.database.models.OMMap;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.utils.SkullTexture;
import com.orbitmines.archive.minecraft.spigot._2019.servers.creative.Creative;
import com.orbitmines.archive.minecraft.spigot._2019.servers.creative.CreativePlayer;
import com.orbitmines.archive.minecraft.spigot._2019.servers.creative.CreativeWorld;
import com.orbitmines.archive.minecraft.spigot._2019.servers.minigames.Minigame;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.ItemBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.PlayerSkullBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.mutable.MutableItemBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.guis.GUI;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.anvilgui.AnvilNms;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.placeholders.SpigotServer;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.scheduler.BukkitRunnable;

public class WorldEditorGUI extends GUI<CreativePlayer> {

    private final Creative creative;
    private final CreativeWorld world;

    public WorldEditorGUI(Creative creative, CreativeWorld world, CreativePlayer viewer) {
        super(27, "§0§l" + world.getName(), viewer);
        this.creative = creative;
        this.world = world;

        /* Back button */
        set(0, 0, new Item<CreativePlayer, MutableItemBuilder>(() -> {
            return new PlayerSkullBuilder("Cyan Arrow Left", SkullTexture.CYAN_ARROW_LEFT, 1, "§3« " + viewer.translate("creative", "player.world.editor.back"));
        }, event -> {
            new WorldListGUI(creative, viewer).open();
        }));

        /* Rename */
        set(1, 1, new Item<CreativePlayer, MutableItemBuilder>(() -> {
            return new ItemBuilder(Material.WRITABLE_BOOK, 1,
                "§7§l" + viewer.translate("creative", "player.world.editor.rename"),
                "§7Current: §d" + world.getName());
        }, event -> {
            openNamePicker();
        }));

        /* World Type (display only) */
        set(1, 2, new Item<CreativePlayer, MutableItemBuilder>(() -> {
            return new ItemBuilder(getWorldTypeIcon(world), 1,
                "§7§l" + viewer.translate("creative", "player.world.editor.world_type"),
                getWorldTypeColor(world) + world.getWorldGenerator().name(),
                "",
                "§8" + viewer.translate("creative", "player.world.editor.world_type.set_at_creation"));
        }, event -> {
            /* Display only */
        }));

        /* Map Type Toggle */
        set(1, 3, new Item<CreativePlayer, MutableItemBuilder>(() -> {
            OMMap.Type type = world.getWorldType();
            boolean isLobby = type == OMMap.Type.LOBBY;
            Material mat = isLobby ? Material.OAK_DOOR : Material.IRON_DOOR;

            String displayType = isLobby ? "LOBBY" : "GAMEMAP";
            Minigame minigame = world.getMinigame();
            if (minigame != null)
                displayType = minigame.getName();

            return new ItemBuilder(mat, 1,
                "§7§l" + viewer.translate("creative", "player.world.editor.map_type"),
                "§f" + displayType);
        }, event -> {
            OMMap.Type current = world.getWorldType();
            if (current == OMMap.Type.LOBBY) {
                world.setWorldType(OMMap.Type.GAMEMAP);
            } else {
                world.setWorldType(OMMap.Type.LOBBY);
            }
            world.update(OMMap.column.WORLD_TYPE);

            viewer.playSound(Sound.UI_BUTTON_CLICK);
            update();
        }));

        /* Server */
        set(1, 4, new Item<CreativePlayer, MutableItemBuilder>(() -> {
            Server server = world.getServer();
            return new ItemBuilder(Material.COMPASS, 1,
                "§7§l" + viewer.translate("creative", "player.world.editor.server"),
                server.getDisplayName());
        }, event -> {
            Server[] values = Server.values();
            Server current = world.getServer();
            int nextIndex = (current.ordinal() + 1) % values.length;
            /* Skip BUNGEECORD */
            if (values[nextIndex] == Server.BUNGEECORD)
                nextIndex = (nextIndex + 1) % values.length;

            world.setServer(values[nextIndex]);
            world.update(OMMap.column.SERVER);

            viewer.playSound(Sound.UI_BUTTON_CLICK);
            update();
        }));

        /* Minigame Selection — only visible when server == MINIGAMES and map type is GAMEMAP */
        set(1, 5, new Item<CreativePlayer, MutableItemBuilder>(() -> {
            if (world.getServer() != Server.MINIGAMES)
                return null;

            if (!world.getWorldType().isGameMap())
                return null;

            Minigame minigame = world.getMinigame();
            String minigameName = minigame != null ? minigame.getName() : "§7" + viewer.translate("creative", "player.world.editor.minigame.none");
            return new ItemBuilder(Material.GOLDEN_SWORD, 1,
                "§7§l" + viewer.translate("creative", "player.world.editor.minigame"),
                "§f" + minigameName);
        }, event -> {
            if (world.getServer() != Server.MINIGAMES)
                return;

            if (!world.getWorldType().isGameMap())
                return;

            Minigame[] values = Minigame.values();
            Minigame current = world.getMinigame();
            int currentIndex = current == null ? -1 : current.ordinal();
            int nextIndex = (currentIndex + 1) % values.length;

            world.setMinigame(values[nextIndex]);
            world.update(OMMap.column.WORLD_TYPE);

            viewer.playSound(Sound.UI_BUTTON_CLICK);
            update();
        }));

        /* Set Spawn Location */
        set(1, 6, new Item<CreativePlayer, MutableItemBuilder>(() -> {
            return new ItemBuilder(Material.RESPAWN_ANCHOR, 1,
                "§7§l" + viewer.translate("creative", "player.world.editor.set_spawn"),
                "§7" + viewer.translate("creative", "player.world.editor.set_spawn.description"));
        }, event -> {
            if (!world.isLoaded() || !viewer.bukkit().getWorld().getName().equals(world.getWorldFileName())) {
                viewer.sendMessage("Creative", Color.RED, "creative", "player.world.editor.set_spawn.not_in_world");
                return;
            }

            world.setSpawnLocation(viewer.getLocation());

            viewer.playSound(Sound.ENTITY_ARROW_HIT_PLAYER);
            viewer.sendMessage("Creative", Color.FUCHSIA, "creative", "player.world.editor.set_spawn.set");
        }));

        /* Members */
        set(1, 7, new Item<CreativePlayer, MutableItemBuilder>(() -> {
            return new ItemBuilder(Material.PLAYER_HEAD, 1,
                "§7§l" + viewer.translate("creative", "player.world.editor.members"),
                "§f" + viewer.translate("creative", "player.world.editor.members.count", String.valueOf(world.getMembers(false).size())));
        }, event -> {
            new WorldMembersGUI(creative, world, viewer).open();
        }));

        /* Delete */
        set(2, 8, new Item<CreativePlayer, MutableItemBuilder>(() -> {
            if (Boolean.TRUE.equals(world.getEnabled())) {
                return new ItemBuilder(Material.BARRIER, 1,
                    "§c§l" + viewer.translate("creative", "player.world.editor.delete"),
                    "§7" + viewer.translate("creative", "player.world.editor.delete.enabled"));
            }
            return new ItemBuilder(Material.BARRIER, 1,
                "§c§l" + viewer.translate("creative", "player.world.editor.delete"),
                "§7" + viewer.translate("creative", "player.world.editor.delete.warning"));
        }, event -> {
            if (Boolean.TRUE.equals(world.getEnabled()))
                return;
            new DeleteConfirmGUI(creative, world, viewer).open();
        }));
    }

    private static String getWorldTypeColor(CreativeWorld world) {
        switch (world.getWorldGenerator()) {
            case VOID: return "§f§l";
            case FLAT: return "§7§l";
            case NORMAL: return "§2§l";
            case NETHER: return "§c§l";
            case END: return "§e§l";
            default: return "§f§l";
        }
    }

    private static Material getWorldTypeIcon(CreativeWorld world) {
        switch (world.getWorldGenerator()) {
            case NORMAL: return Material.GRASS_BLOCK;
            case NETHER: return Material.NETHERRACK;
            case END: return Material.END_STONE;
            case FLAT: return Material.SMOOTH_STONE;
            case VOID: return Material.GLASS;
            default: return Material.MAP;
        }
    }

    private void openNamePicker() {
        AnvilNms anvil = creative.getNms().anvilGui(viewer.bukkit(), (event) -> {
            if (event.getSlot() != AnvilNms.AnvilSlot.OUTPUT)
                return;

            String newName = event.getName();

            if (newName.length() > 32) {
                viewer.sendMessage("Creative", Color.RED, "creative", "player.world.name_too_long");
                return;
            }

            for (int i = 0; i < newName.length(); i++) {
                char c = newName.charAt(i);
                if (!Character.isAlphabetic(c) && !Character.isDigit(c) && c != '_' && c != ' ') {
                    viewer.sendMessage("Creative", Color.RED, "creative", "player.world.name_invalid_chars");
                    return;
                }
            }

            event.getAnvilNms().destroy();
            close();

            world.setName(newName);
            world.update(OMMap.column.NAME);

            viewer.playSound(Sound.ENTITY_ARROW_HIT_PLAYER);
            viewer.sendMessage("Creative", Color.FUCHSIA, "creative", "player.world.renamed", "§d" + newName + "§7");
        }, new AnvilNms.AnvilCloseEvent() {
            @Override
            public void onClose() {
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        WorldEditorGUI.this.open();
                    }
                }.runTaskLater(creative.getPlugin(), 1);
            }
        });

        anvil.getItems().put(AnvilNms.AnvilSlot.INPUT_LEFT, new ItemBuilder(Material.PAPER, 1, world.getName()).build());

        SpigotServer.getInstance().runSync(anvil::open);
    }

    /**
     * Inner confirmation GUI for world deletion.
     */
    static class DeleteConfirmGUI extends GUI<CreativePlayer> {

        public DeleteConfirmGUI(Creative creative, CreativeWorld world, CreativePlayer viewer) {
            super(54, "§0§lAre you sure?", viewer);

            set(2, 4, new Item<CreativePlayer, MutableItemBuilder>(() ->
                new ItemBuilder(Material.BARRIER, 1, "§c§l" + viewer.translate("creative", "player.world.editor.delete"))));

            set(3, 4, new Item<CreativePlayer, MutableItemBuilder>(() ->
                new ItemBuilder(getWorldIcon(world), 1, "§d§l" + world.getName())));

            Item<CreativePlayer, MutableItemBuilder> confirm = new Item<>(() -> {
                return new ItemBuilder(Material.LIME_STAINED_GLASS_PANE, 1,
                    "§a§l" + viewer.translate("creative", "player.world.editor.delete.confirm"));
            }, event -> {
                close();

                viewer.getWorlds().remove(world);
                creative.getAllWorlds().remove(world);

                /* DB delete (already on async thread via GUI click handler) */
                world.deleteFromDatabase();

                /* Sync: teleport, unload, feedback */
                creative.runSync(() -> {
                    world.unloadForDelete();

                    viewer.playSound(Sound.ENTITY_IRON_GOLEM_DEATH);
                    viewer.sendMessage("Creative", Color.RED, "creative", "player.world.deleted", "§d" + world.getName() + "§7");

                    if (!viewer.getWorlds().isEmpty()) {
                        creative.loadAndTeleport(viewer, viewer.getWorlds().get(0));
                    } else {
                        viewer.teleport(creative.getLobbySpawn());
                    }
                });
            });

            Item<CreativePlayer, MutableItemBuilder> cancel = new Item<>(() -> {
                return new ItemBuilder(Material.RED_STAINED_GLASS_PANE, 1,
                    "§c§l" + viewer.translate("creative", "player.world.editor.delete.cancel"));
            }, event -> {
                new WorldEditorGUI(creative, world, viewer).open();
            });

            for (int i = 0; i < 9; i++) {
                if (i > 2 && i < 6)
                    continue;

                for (int j = 1; j < 5; j++) {
                    set(j, i, i <= 2 ? confirm : cancel);
                }
            }
        }

        private Material getWorldIcon(CreativeWorld world) {
            switch (world.getWorldGenerator()) {
                case NORMAL: return Material.GRASS_BLOCK;
                case NETHER: return Material.NETHERRACK;
                case END: return Material.END_STONE;
                case FLAT: return Material.SMOOTH_STONE;
                case VOID: return Material.GLASS;
                default: return Material.MAP;
            }
        }
    }
}
