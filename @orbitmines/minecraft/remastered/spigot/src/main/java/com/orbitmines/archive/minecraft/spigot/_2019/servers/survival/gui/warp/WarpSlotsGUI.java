package com.orbitmines.archive.minecraft.spigot._2019.servers.survival.gui.warp;

/*
 * OrbitMines - @author Fadi Shawki - 2018
 */

import com.orbitmines.archive.minecraft._2019.libs.player.Name;
import com.orbitmines.archive.minecraft._2019.libs.player.OfflinePlayer;
import com.orbitmines.archive.minecraft._2019.libs.rank.VipRank;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.database.models.survival.SurvivalWarp;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.utils.SkullTexture;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.Survival;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.database.models.Warp;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.player.SurvivalPlayer;
import com.orbitmines.archive.minecraft._2019.utils.NumberUtils;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.ItemBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.PlayerSkullBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.mutable.MutableItemBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.guis.GUI;
import org.bukkit.Material;

import java.util.List;

public class WarpSlotsGUI extends GUI<SurvivalPlayer> {

    public WarpSlotsGUI(Survival survival, SurvivalPlayer viewer) {
        super(27, "§0§l" + viewer.translate("survival", "player.warp.your_warps.title"), viewer);

        set(1, 2, new Item<SurvivalPlayer, MutableItemBuilder>(() -> {
            return new PlayerSkullBuilder("Cyan Arrow Left", SkullTexture.CYAN_ARROW_LEFT, 1, "§3« " + viewer.translate("survival", "player.warp.back_to_warps"));
        }, event -> {
            new WarpGUI(survival, viewer, viewer).open();
        }));

        SurvivalWarp.Slot[] slots = SurvivalWarp.Slot.values();
        for (int i = 0; i < slots.length; i++) {
            SurvivalWarp.Slot slot = slots[i];

            int column = 4 + i;
            set(1, column, new Item<SurvivalPlayer, MutableItemBuilder>(() -> {
                Warp warp = getFromSlot(viewer.getWarps(), slot);

                if (warp == null) {
                    if (!hasUnlocked(slot))
                        return new ItemBuilder(Material.BOOK, column - 3, slot.getDisplayName(viewer) + " §7§lSlot", " §7" + slot.getDescription(viewer));

                    return new ItemBuilder(
                        Material.WRITABLE_BOOK, column - 3,
                        slot.getDisplayName(viewer) + " §7§lSlot",
                        " §7" + slot.getDescription(viewer),
                        "",
                        "§a" + viewer.translate("survival", "player.warp.create")
                    ).glow();
                }

                ItemBuilder item = warp.getIcon().getItemBuilder().setAmount(column - 3);

                item.setDisplayName("§7§lWarp §3§l" + warp.getName());

                OfflinePlayer owner = OfflinePlayer.get(warp.getOwner());
                item.addLore(" §7" + viewer.translate("survival", "player.warp.owner") + ": " + owner.getName(Name.RAW_COLORED));
                item.addLore(" §7XZ: " + (warp.getLocation() == null ? "§c§l" + viewer.translate("survival", "player.warp.not_set") : "§3§l" + NumberUtils.locale(warp.getLocation().getBlockX()) + "§7 / " + "§3§l" + NumberUtils.locale(warp.getLocation().getBlockZ())));

                item.addLore("");
                item.addLore("§a" + viewer.translate("survival", "player.warp.edit.hover"));

                return item;
            }, event -> {
                Warp warp = getFromSlot(viewer.getWarps(), slot);

                if (warp == null) {
                    if (!hasUnlocked(slot))
                        return;

                    warp = new Warp(viewer.getUUID(), viewer.getRawName() + "s Warp", slot);
                    warp.insert();
                    viewer.getWarps().add(warp);
                    survival.getWarps().add(warp);

                    new WarpEditorGUI(survival, warp, viewer).open();
                    return;
                }

                new WarpEditorGUI(survival, warp, viewer).open();
            }));
        }
    }

    private boolean hasUnlocked(SurvivalWarp.Slot slot) {
        switch (slot) {

            case VIP_SLOT:
                return viewer.isEligible(VipRank.EMERALD);
            case SHOP_SLOT:
                return viewer.isWarpSlotShop();
            case PRISMS_SLOT:
                return viewer.isWarpSlotPrisms();
        }
        throw new IllegalStateException();
    }

    private Warp getFromSlot(List<Warp> warps, SurvivalWarp.Slot slot) {
        for (Warp warp : warps) {
            if (warp.getSlot() == slot)
                return warp;
        }
        return null;
    }
}
