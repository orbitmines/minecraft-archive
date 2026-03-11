package com.orbitmines.world_generator;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class WorldGenerator extends JavaPlugin {

    @Override
    public void onEnable() {
//        new BukkitRunnable() {
//            @Override
//            public void run() {
//                Bukkit.shutdown();
//            }
//        }.runTaskLater(this, 15 * 60 * 20);

        Runnable task = new WorldChunkCounterTask(Bukkit.getServer(), Bukkit.getWorld("world"), 125);

        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, task, 0, 1);
    }
}
