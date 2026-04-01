package com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.firework;

/*
 * OrbitMines - @author Fadi Shawki - 2018
 */

import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.placeholders.SpigotServer;
import org.bukkit.craftbukkit.v1_14_R1.entity.CraftFirework;
import org.bukkit.entity.Firework;
import org.bukkit.scheduler.BukkitRunnable;

public class FireworkNms_1_14_R1 implements FireworkNms {

    private final SpigotServer server;

    public FireworkNms_1_14_R1(SpigotServer server) {
        this.server = server;
    }

    @Override
    public void explode(Firework firework) {
        new BukkitRunnable() {
            public void run() {
                ((CraftFirework) firework).getHandle().expectedLifespan = 0;
            }
        }.runTaskLater(server, 1);
    }
}
