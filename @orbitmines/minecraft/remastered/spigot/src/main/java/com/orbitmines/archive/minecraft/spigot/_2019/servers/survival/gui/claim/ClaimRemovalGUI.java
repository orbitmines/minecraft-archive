package com.orbitmines.archive.minecraft.spigot._2019.servers.survival.gui.claim;

/*
 * OrbitMines - @author Fadi Shawki - 2018
 */

import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.Survival;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.database.models.Claim;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.player.SurvivalPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.ItemBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.mutable.MutableItemBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.guis.GUI;
import org.bukkit.Material;

public class ClaimRemovalGUI extends GUI<SurvivalPlayer> {

    public ClaimRemovalGUI(Survival survival, SurvivalPlayer viewer, Claim claim) {
        super(54, "§0§lAre you sure?", viewer);

        set(2, 4, new Item<SurvivalPlayer, MutableItemBuilder>(() -> new ItemBuilder(Material.BARRIER, 1, "§c§l" + viewer.translate("survival", "player.claim.gui.remove"))));

        set(3, 4, new Item<SurvivalPlayer, MutableItemBuilder>(() -> ClaimGUI.getClaimIcon(survival, viewer, claim)));

        Item<SurvivalPlayer, MutableItemBuilder> confirm = new Item<>(() -> {
            return new ItemBuilder(Material.LIME_STAINED_GLASS_PANE, 1, "§a§l" + viewer.translate("survival", "player.claim.gui.confirm"));
        }, event -> {
            survival.getClaimHandler().abandonClaim(claim, viewer);
            close();
        });

        Item<SurvivalPlayer, MutableItemBuilder> cancel = new Item<>(() -> {
            return new ItemBuilder(Material.RED_STAINED_GLASS_PANE, 1, "§c§l" + viewer.translate("survival", "player.claim.gui.cancel"));
        }, event -> {
            new ClaimGUI(viewer, claim, survival).open();
        });

        for (int i = 0; i < 9; i++) {
            if (i > 2 && i < 6)
                continue;

            for (int j = 1; j < 5; j++) {
                set(j, i, i <= 2 ? confirm : cancel);
            }
        }
    }
}
