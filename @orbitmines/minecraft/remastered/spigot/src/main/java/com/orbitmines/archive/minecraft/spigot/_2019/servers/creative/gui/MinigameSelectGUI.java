package com.orbitmines.archive.minecraft.spigot._2019.servers.creative.gui;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.Color;
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
import org.bukkit.Sound;

public class MinigameSelectGUI extends GUI<CreativePlayer> {

    public MinigameSelectGUI(Creative creative, CreativeWorld world, CreativePlayer viewer) {
        super(27, "§0§l" + viewer.translate("creative", "player.world.minigame.title"), viewer);

        /* Back button */
        set(0, 0, new Item<CreativePlayer, MutableItemBuilder>(() -> {
            return new PlayerSkullBuilder("Cyan Arrow Left", SkullTexture.CYAN_ARROW_LEFT, 1, "§3« " + viewer.translate("creative", "player.world.editor.back"));
        }, event -> {
            new WorldEditorGUI(creative, world, viewer).open();
        }));

        /* Minigame items */
        Minigame[] minigames = Minigame.values();
        for (int i = 0; i < minigames.length; i++) {
            Minigame minigame = minigames[i];
            boolean selected = world.getMinigame() == minigame;

            set(1, 2 + i, new Item<CreativePlayer, MutableItemBuilder>(() -> {
                ItemBuilder item = new ItemBuilder(minigame.getIcon(), 1, "§d§l" + minigame.getName());

                if (selected) {
                    item.glow();
                    item.addLore("");
                    item.addLore("§a§l" + viewer.translate("creative", "player.world.minigame.selected"));
                } else {
                    item.addLore("");
                    item.addLore("§a" + viewer.translate("creative", "player.world.minigame.click"));
                }

                return item;
            }, event -> {
                world.setMinigame(minigame);
                world.update(OMMap.column.WORLD_TYPE);

                viewer.playSound(Sound.ENTITY_ARROW_HIT_PLAYER);
                viewer.sendMessage("Creative", Color.FUCHSIA, "creative", "player.world.editor.minigame.set", "§d" + minigame.getName() + "§7");

                new WorldEditorGUI(creative, world, viewer).open();
            }));
        }
    }
}
