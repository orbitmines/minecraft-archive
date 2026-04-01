package com.orbitmines.archive.minecraft.spigot._2019.servers.survival.gui.warp;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.player.Name;
import com.orbitmines.archive.minecraft._2019.libs.player.OfflinePlayer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.database.models.survival.SurvivalFavoriteWarp;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.database.models.survival.SurvivalWarp;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.utils.SkullTexture;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.Survival;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.database.models.Warp;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.player.SurvivalPlayer;
import com.orbitmines.archive.minecraft._2019.utils.NumberUtils;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.ItemBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.PlayerSkullBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.mutable.MutableItemBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.guis.PaginatableGUI;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public class WarpGUI extends PaginatableGUI<SurvivalPlayer, SurvivalPlayer, Warp> {

    private final Survival survival;
    private Type type;

    public WarpGUI(Survival survival, SurvivalPlayer viewer, SurvivalPlayer key) {
        super(54, "§0§lWarps", viewer, key, 9, 3);

        this.survival = survival;
        this.type = Type.NORMAL;

        set(0, 3, new Item<SurvivalPlayer, MutableItemBuilder>(() -> {
            int warps = key.getWarps().size();
            ItemBuilder item = new ItemBuilder(Material.WRITABLE_BOOK, 1, "§7§l" + viewer.translate("survival", "player.warp.your_warps", "§3§l" + warps + "§7§l", "§3§l" + SurvivalWarp.Slot.values().length + "§7§l"));

            if (warps < key.getWarpsAllowed())
                item.glow();

            return item;
        }, event -> {
            new WarpSlotsGUI(survival, viewer).open();
        }));

        set(0, 5, new Item<SurvivalPlayer, MutableItemBuilder>(() -> {
            ItemBuilder item = new ItemBuilder(Material.DIAMOND, 1, "§6§l" + viewer.translate("spigot", "player.friends.add_to_favorites"));

            if (type == Type.FAVORITE)
                item.glow();

            return item;
        }, event -> {
            if (type == Type.FAVORITE)
                type = Type.NORMAL;
            else
                type = Type.FAVORITE;

            update();
        }));

        paginate(2, 0);

        setPreviousPage(5, 0, () -> new PlayerSkullBuilder("Cyan Arrow Left", SkullTexture.CYAN_ARROW_LEFT, 1, "§7« " + viewer.translate("survival", "player.warp.paginate")));
        setNextPage(5, 8, () -> new PlayerSkullBuilder("Cyan Arrow Right", SkullTexture.CYAN_ARROW_RIGHT, 1, "§7" + viewer.translate("survival", "player.warp.paginate") + " »"));
    }

    @Override
    public Item<SurvivalPlayer, MutableItemBuilder> getItem(PageItem<Warp> pageItem) {
        return new Item<>(() -> {
            Warp warp = pageItem.getObject();

            if (warp == null)
                return null;

            ItemBuilder item = warp.getIcon().getItemBuilder();

            if (!warp.isEnabled()) {
                item.setMaterial(Material.BLACK_STAINED_GLASS_PANE);
            }

            item.setDisplayName("§7§lWarp §3§l" + warp.getName());

            SurvivalFavoriteWarp favorite = key.getFavoriteWarp(warp, false);
            boolean isFavorite = favorite != null && favorite.isFavorite();

            if (isFavorite)
                item.glow();

            OfflinePlayer owner = OfflinePlayer.get(warp.getOwner());
            item.addLore(" §7" + viewer.translate("survival", "player.warp.owner") + ": " + owner.getName(Name.RAW_COLORED));
            item.addLore(" §7XZ: " + (warp.getLocation() == null ? "§c§l" + viewer.translate("survival", "player.warp.not_set") : "§a§l" + NumberUtils.locale(warp.getLocation().getBlockX()) + "§7 / §a§l" +  NumberUtils.locale(warp.getLocation().getBlockZ())));

            item.addLore("");

            switch (type) {

                case NORMAL:
                    if (isFavorite) {
                        item.addLore("§6§l" + viewer.translate("survival", "player.warp.favorite"));
                        item.addLore("");
                    }

                    if (warp.isEnabled())
                        item.addLore("§a" + viewer.translate("survival", "player.warp.hover"));
                    else
                        item.addLore("§c" + viewer.translate("survival", "player.warp.is_disabled"));
                    break;
                case FAVORITE:
                    if (isFavorite)
                        item.addLore("§c" + viewer.translate("spigot", "player.friends.hover.remove_favorite"));
                    else
                        item.addLore("§6" + viewer.translate("spigot", "player.friends.hover.add_favorite"));
                    break;
            }

            return item;
        }, event -> {
            Warp warp = pageItem.getObject();

            if (warp == null)
                return;

            switch (type) {

                case NORMAL:
                    if (warp.isEnabled()) {
                        warp.teleport(viewer);
                        warp.addTimeUsed();
                        warp.update(SurvivalWarp.column.TIMES_USED);
                        close();
                    }
                    break;
                case FAVORITE:
                    SurvivalFavoriteWarp favorite = key.getFavoriteWarp(warp, false);

                    if (favorite == null) {
                        favorite = new SurvivalFavoriteWarp(this.key.getUUID(), warp.getModel(), true);
                        favorite.insert();

                        key.getFavoriteWarps(false).add(favorite);
                    } else {
                        favorite.setFavorite(!favorite.isFavorite());
                        favorite.update(SurvivalFavoriteWarp.column.FAVORITE);
                    }

                    update();

                    break;
                default:
                    break;
            }
        });
    }

    @Override
    public List<Warp> getCollection() {
        List<Warp> sortedWarps = new ArrayList<>();
        List<Warp> nonFavorites = new ArrayList<>(survival.getWarps());
        List<Warp> notEnabled = new ArrayList<>();

        for (SurvivalFavoriteWarp favorite : key.getFavoriteWarps(false)) {
            for (Warp warp : new ArrayList<>(nonFavorites)) {
                if (!warp.isEnabled()) {
                    nonFavorites.remove(warp);
                    notEnabled.add(warp);
                    continue;
                }

                if (!warp.getId().equals(favorite.getWarpId()))
                    continue;

                nonFavorites.remove(warp);
                sortedWarps.add(warp);
            }
        }

        sortedWarps.addAll(nonFavorites);
        sortedWarps.addAll(notEnabled);

        return sortedWarps;
    }

    @Override
    public Class<Warp> getTypeClass() {
        return Warp.class;
    }

    public enum Type {

        NORMAL,
        FAVORITE;

    }
}
