package com.orbitmines.archive.minecraft.spigot._2019.servers.survival.gui.warp;

/*
 * OrbitMines - @author Fadi Shawki - 2018
 */

import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.database.models.survival.SurvivalWarp;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.utils.SkullTexture;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.Survival;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.database.models.Warp;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.player.SurvivalPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.ItemBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.PlayerSkullBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.mutable.MutableItemBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.guis.GUI;
import org.bukkit.Sound;

public class WarpIconEditorGUI extends GUI<SurvivalPlayer> {

    public WarpIconEditorGUI(Survival survival, Warp warp, SurvivalPlayer viewer) {
        super(54, "§0§l" + warp.getName(), viewer);

        SurvivalWarp.Icon[] icons = SurvivalWarp.Icon.values();
        for (int i = 0; i < icons.length; i++) {
            SurvivalWarp.Icon icon = icons[i];

            set(i, new Item<SurvivalPlayer, MutableItemBuilder>(() -> {
                ItemBuilder item = icon.getItemBuilder();

                if (warp.getIcon() == icon)
                    item.glow();

                return item;
            }, event -> {
                warp.setIcon(icon);
                warp.update(SurvivalWarp.column.ICON);

                viewer.playSound(Sound.UI_BUTTON_CLICK);
                update();
            }));
        }

        set(5, 4, new Item<SurvivalPlayer, MutableItemBuilder>(() -> {
            return new PlayerSkullBuilder("Cyan Arrow Left", SkullTexture.CYAN_ARROW_LEFT, 1, "§3« " + viewer.translate("survival", "player.warp.back_to_your_warps"));
        }, event -> {
            new WarpEditorGUI(survival, warp, viewer).open();
        }));
    }
}
