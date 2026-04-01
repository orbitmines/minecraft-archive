package com.orbitmines.archive.minecraft.spigot._2019.servers.survival.items;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.Survival;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.player.SurvivalPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.scoreboard.ChestShopScoreboard;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.scoreboard.SurvivalScoreboard;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.ItemUtils;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.item_handlers.ItemHoverBlockTarget;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ChestShopBlockTarget extends ItemHoverBlockTarget<Survival, SurvivalPlayer> {

    private static List<Material> materials = new ArrayList<>();

    static {
        materials.add(Material.CHEST);
        materials.addAll(ItemUtils.SIGNS);
    }

    private Survival survival;

    public ChestShopBlockTarget(Survival server) {
        super(server, null, true, materials.toArray(new Material[0]));

        this.survival = server;
    }

    public boolean equals(ItemStack itemStack) {
        return ItemUtils.isSign(itemStack);
    }

    @Override
    public void onTargetEnter(SurvivalPlayer player) {
        survival.runSync(() -> {
            player.resetScoreboard();
            player.setScoreboard(new ChestShopScoreboard(survival, player));
        });
    }

    @Override
    protected void onLeave(SurvivalPlayer player) {
        survival.runSync(() -> {
            player.resetScoreboard();
            player.setScoreboard(new SurvivalScoreboard(survival, player));
        });
    }
}
