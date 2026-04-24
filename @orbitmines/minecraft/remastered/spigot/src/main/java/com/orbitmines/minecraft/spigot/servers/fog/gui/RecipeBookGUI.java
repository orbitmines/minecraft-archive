package com.orbitmines.minecraft.spigot.servers.fog.gui;

import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.ItemBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.mutable.MutableItemBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.guis.GUI;
import com.orbitmines.minecraft.spigot.servers.fog.FoG;
import com.orbitmines.minecraft.spigot.servers.fog.FoGPlayer;
import com.orbitmines.minecraft.spigot.servers.fog.recipe.RecipeBook;
import com.orbitmines.minecraft.spigot.servers.fog.recipe.RecipeEntry;
import com.orbitmines.minecraft.spigot.servers.fog.stats.Stats;
import org.bukkit.Material;

public class RecipeBookGUI extends GUI<FoGPlayer> {

    public RecipeBookGUI(FoG server, FoGPlayer viewer) {
        super(54, "§0§l" + viewer.translate("fog", "gui.codex.title"), viewer);

        /* Unlocked = the player has ever picked the gating choice. Reads the
           stats cache — no DB access, so the GUI can be built on any thread. */
        Stats stats = viewer.getStats();
        int slot = 0;
        for (RecipeEntry entry : RecipeBook.ENTRIES) {
            boolean unlocked = entry.getUnlockChoice() == null
                    || (stats != null && stats.getChoiceStackCount(entry.getUnlockChoice()) > 0);
            Material icon = unlocked ? entry.getIcon() : Material.GRAY_DYE;
            String name = unlocked
                    ? ("§a§l" + entry.getDisplayName(viewer))
                    : ("§7§l" + entry.getDisplayName(viewer) + " §c" + viewer.translate("fog", "gui.codex.locked_suffix"));
            set(slot++, new Item<FoGPlayer, MutableItemBuilder>(() ->
                new ItemBuilder(icon, 1, name).addLore("§7" + entry.getDescription(viewer))
            ));
            if (slot >= getInventory().getSize()) break;
        }
    }
}
