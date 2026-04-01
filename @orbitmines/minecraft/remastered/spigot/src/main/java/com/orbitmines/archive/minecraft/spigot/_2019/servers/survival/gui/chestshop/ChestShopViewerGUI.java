package com.orbitmines.archive.minecraft.spigot._2019.servers.survival.gui.chestshop;

/*
 * OrbitMines - @author Fadi Shawki - 2018
 */

import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.database.models.ChestShop;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.player.SurvivalPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.guis.GUI;

public class ChestShopViewerGUI extends GUI<SurvivalPlayer> {

    public ChestShopViewerGUI(ChestShop chestShop, SurvivalPlayer viewer) {
        super(chestShop.getInventory().getSize(), "§0§lChest Shop Viewer", viewer);

        inventory.setContents(chestShop.getInventory().getContents());
    }

    @Override
    public void open() {
        viewer.openInventory(inventory);
    }

    @Override
    public void update() {

    }

    @Override
    protected void update(boolean open) {

    }
}
