package com.orbitmines.archive.minecraft.spigot._2019.servers.survival.runnables;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.Survival;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.database.models.ChestShop;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.runnable.Interval;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.runnable.PassiveRunnable;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.runnable.TimeUnit;

public class ChestShopRunnable extends PassiveRunnable<Survival> {

    private Survival survival;

    public ChestShopRunnable(Survival plugin) {
        super(plugin, Interval.of(TimeUnit.TICK, 10));

        this.survival = plugin;
    }

    @Override
    public void onRun() {
        for (ChestShop chestShop : survival.getChestShops()) {
            chestShop.tick();
        }
    }
}
