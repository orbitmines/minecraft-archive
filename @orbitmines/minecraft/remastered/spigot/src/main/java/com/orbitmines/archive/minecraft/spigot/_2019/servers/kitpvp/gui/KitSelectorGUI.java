package com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.gui;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.database.models.kitpvp.KitPvPPlayerKitModel;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.KitPvPPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.kit.KitPvPKit;
import com.orbitmines.archive.minecraft._2019.utils.NumberUtils;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.ItemBuilderInstance;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.mutable.MutableItemBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.guis.GUI;
import org.bukkit.Sound;

public class KitSelectorGUI extends GUI<KitPvPPlayer> {

    public KitSelectorGUI(KitPvPPlayer viewer) {
        super(36, "§0§lKit Selector", viewer);
        
        boolean saturday = viewer.server().isSaturday();
        int freeLevel = viewer.server().getFreeKitLevel();

        for (KitPvPKit kit : KitPvPKit.getKits()) {
            KitPvPPlayerKitModel data = viewer.getKit(kit, false);

            set((int) kit.getId(), new Item<KitPvPPlayer, MutableItemBuilder>(() -> {
                ItemBuilderInstance icon = kit.getIcon().setDisplayName(kit.getColor().getCc() + "§l" + kit.getName());
                int level = data.getUnlockedLevel();

                if (level != 0 || saturday) {
                    int displayLevel = saturday ? Math.max(level, freeLevel) : level;
                    icon.glow();

                    if (level == 0) {
                        icon.setAmount(displayLevel);
                        icon.setDisplayName(icon.getDisplayName() + " §d§lFREE Lvl " + displayLevel);

                        icon.addLore("§d§lFREE KIT SATURDAY");
                    } else if (saturday && freeLevel > level) {
                        icon.setAmount(displayLevel);
                        icon.setDisplayName(icon.getDisplayName() + " §d§lFREE Lvl " + displayLevel);

                        icon.addLore("§d§lFREE KIT SATURDAY");
                    } else {
                        icon.setAmount(level);

                        icon.setDisplayName(icon.getDisplayName() + " §a§lLvl " + level);
                    }

                    icon.addLore("");
                    icon.addLore("§e§lRIGHT CLICK §8- §aSelect Kit");
                    icon.addLore("§6§lLEFT CLICK §8- " + (level == 0 ? "§7Purchase Kit" : "§7Details" + (level != kit.getLevels().length ? " & Upgrade Kit" : "")));
                } else {
                    icon.setDisplayName(icon.getDisplayName() + " §4§lLOCKED");

                    icon.addLore("");
                    icon.addLore("§c§l§mRIGHT CLICK §c§m- Select Kit");
                    icon.addLore("§6§lLEFT CLICK §8- §aPurchase Kit");
                }

                icon.addLore("");
                icon.addLore("§7Your Statistics");
                icon.addLore("  §7Kills: §c§l" + NumberUtils.locale(data.getKills()));
                icon.addLore("  §7Deaths: §4§l" + NumberUtils.locale(data.getDeaths()));
                icon.addLore("  §7Best streak: §5§l" + NumberUtils.locale(data.getBestStreak()));
                icon.addLore("  §7Damage dealt: §c§l" + NumberUtils.locale(data.getDamageDealt()));

                return icon;
            }, event -> {
                int level = data.getUnlockedLevel();

                if (level != 0 || saturday) {
                    int effectiveLevel = saturday ? Math.max(level, freeLevel) : level;

                    switch (event.getAction()) {
                        /* Right Click */
                        case PICKUP_HALF:
                            viewer.server().runSync(() -> viewer.joinMap(kit.getLevel(effectiveLevel)));
                            break;

                        /* Left Click */
                        case PICKUP_ALL:
                            new KitInfoGUI(viewer, kit.getLevel(effectiveLevel)).open();
                            break;
                    }
                } else {
                    switch (event.getAction()) {
                        /* Right Click */
                        case PICKUP_HALF:
                            break;

                        /* Left Click */
                        case PICKUP_ALL:
                            new KitInfoGUI(viewer, kit.getLevel(1)).open();
                            break;
                    }
                }
            }));
        }
    }

    @Override
    public void open() {
        super.open();

        viewer.playSound(Sound.ITEM_ARMOR_EQUIP_DIAMOND);
    }
}
