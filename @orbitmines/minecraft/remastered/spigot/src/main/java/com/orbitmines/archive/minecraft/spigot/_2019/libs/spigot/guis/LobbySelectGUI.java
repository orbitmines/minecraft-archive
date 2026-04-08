package com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.guis;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.player.Name;
import com.orbitmines.archive.minecraft._2019.libs.player.OfflinePlayer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMServer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.database.models.LobbyPreference;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.database.models.OMMap;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.utils.SkullTexture;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.ItemBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.PlayerSkullBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.mutable.MutableItemBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.guis.GUI;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.placeholders.SpigotServer;
import org.bukkit.Material;
import org.bukkit.Sound;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SuppressWarnings("unchecked")
public class LobbySelectGUI extends GUI<OMPlayer> {

    private static final UUID ARCHIVE_UUID = UUID.fromString("33ee168b-5c2c-42c5-b3b2-d841ceb76b70");
    private static final int MAX_ROWS = 5;
    private static final int ITEMS_PER_PAGE = MAX_ROWS * 9;

    private final OMServer server;
    private final List<OMMap> lobbies;
    private final int page;

    public LobbySelectGUI(OMServer server, OMPlayer viewer) {
        this(server, viewer, 0);
    }

    private LobbySelectGUI(OMServer server, OMPlayer viewer, int page) {
        super(computeSize(getLobbies(server).size(), page, totalPages(getLobbies(server).size())),
            "§0§l" + viewer.translate("spigot", "settings.lobby.select"), viewer);

        this.server = server;
        this.lobbies = getLobbies(server);
        this.page = page;

        int totalPages = totalPages(lobbies.size());
        boolean hasNav = totalPages > 1;

        int start = page * ITEMS_PER_PAGE;

        int slot = 0;

        /* Default Lobby option on first page */
        if (page == 0) {
            set(slot++, defaultLobbyItem());
            start = Math.max(0, start - 1);
        } else {
            start = start - 1;
        }

        int end = Math.min(lobbies.size(), start + (ITEMS_PER_PAGE - (page == 0 ? 1 : 0)));

        for (int i = start; i < end; i++) {
            set(slot++, lobbyItem(lobbies.get(i)));
        }

        /* Navigation row */
        if (hasNav) {
            int navBase = MAX_ROWS * 9;

            if (page > 0) {
                set(navBase, new Item<OMPlayer, MutableItemBuilder>(() -> {
                    return new PlayerSkullBuilder("Cyan Arrow Left", SkullTexture.CYAN_ARROW_LEFT, 1,
                        "§3\u00AB " + viewer.translate("spigot", "word.previous"));
                }, event -> {
                    new LobbySelectGUI(server, viewer, page - 1).open();
                }));
            }

            if (page < totalPages - 1) {
                set(navBase + 8, new Item<OMPlayer, MutableItemBuilder>(() -> {
                    return new PlayerSkullBuilder("Cyan Arrow Right", SkullTexture.CYAN_ARROW_RIGHT, 1,
                        "§3" + viewer.translate("spigot", "word.next") + " \u00BB");
                }, event -> {
                    new LobbySelectGUI(server, viewer, page + 1).open();
                }));
            }
        }
    }

    private Item<OMPlayer, MutableItemBuilder> defaultLobbyItem() {
        return new Item<>(() -> {
            LobbyPreference pref = viewer.getLobbyPreference();
            boolean isSelected = pref == null;

            ItemBuilder item = new ItemBuilder(Material.BEACON, 1,
                "§a§l" + viewer.translate("spigot", "settings.lobby.default"));
            item.addLore("");
            item.addLore(" §7" + viewer.translate("spigot", "settings.lobby.default.description"));
            item.addLore("");
            if (isSelected) {
                item.addLore(" §a\u2714 " + viewer.translate("spigot", "settings.lobby.current"));
                item.glow();
            } else
                item.addLore(" §e" + viewer.translate("spigot", "settings.lobby.click"));

            return item;
        }, event -> {
            LobbyPreference pref = viewer.getLobbyPreference();
            if (pref != null) {
                pref.delete();
                viewer.setLobbyPreference(null, null);
            }

            viewer.playSound(Sound.UI_BUTTON_CLICK);
            close();

            /* Teleport to default lobby */
            server.runSync(() -> viewer.teleport(server.getLobbySpawn()));
        });
    }

    private Item<OMPlayer, MutableItemBuilder> lobbyItem(OMMap map) {
        return new Item<OMPlayer, MutableItemBuilder>(() -> {
            LobbyPreference pref = viewer.getLobbyPreference();
            boolean isSelected = pref != null && map.getWorldFileName().equals(pref.getWorldFileName());

            /* Use first author's skull, default to archive owner */
            UUID authorUuid = ARCHIVE_UUID;
            if (map.getAuthors() != null && map.getAuthors().size() > 0) {
                try {
                    authorUuid = UUID.fromString(map.getAuthors().get(0).getAsString());
                } catch (Exception ignored) {
                }
            }

            PlayerSkullBuilder item = new PlayerSkullBuilder(authorUuid, 1, "§d§l" + map.getName());

            OfflinePlayer author = OfflinePlayer.get(authorUuid);
            String authorName = author != null ? author.getName(Name.RAW_COLORED) : authorUuid.toString();

            item.addLore("");
            item.addLore(" " + authorName);
            item.addLore("");
            if (isSelected) {
                item.addLore(" §a\u2714 " + viewer.translate("spigot", "settings.lobby.current"));
                item.glow();
            } else
                item.addLore(" §e" + viewer.translate("spigot", "settings.lobby.click"));

            return item;
        }, event -> {
            LobbyPreference pref = viewer.getLobbyPreference();
            if (pref == null) {
                pref = new LobbyPreference(viewer.getUUID(), server.getType(), map.getWorldFileName());
                pref.insert();
            } else {
                pref.setWorldFileName(map.getWorldFileName());
                pref.insertOrUpdate(LobbyPreference.column.WORLD_FILE_NAME);
            }
            viewer.setLobbyPreference(pref, map);

            viewer.playSound(Sound.UI_BUTTON_CLICK);
            close();

            /* Load and teleport to selected lobby */
            server.loadAndTeleportToLobby(viewer, map);
        });
    }

    @Override
    public void open() {
        super.open();
        viewer.playSound(Sound.ITEM_ARMOR_EQUIP_DIAMOND);
    }

    private static int totalPages(int lobbyCount) {
        int totalItems = lobbyCount + 1;
        return Math.max(1, (int) Math.ceil((double) totalItems / ITEMS_PER_PAGE));
    }

    private static int computeSize(int lobbyCount, int page, int totalPages) {
        boolean hasNav = totalPages > 1;
        int contentRows;

        if (hasNav) {
            contentRows = MAX_ROWS + 1;
        } else {
            int totalItems = lobbyCount + 1;
            contentRows = Math.max(1, (int) Math.ceil((double) totalItems / 9.0));
        }

        return 9 * contentRows;
    }

    private static List<OMMap> getLobbies(OMServer server) {
        return new ArrayList<>(OMMap.getAll(OMMap.class,
            OMMap.column.WORLD_TYPE.is(OMMap.Type.LOBBY),
            OMMap.column.SERVER.is(server.getType())
        ));
    }
}
