package com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.firework;

import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.placeholders.SpigotServer;
import org.bukkit.craftbukkit.entity.CraftFirework;
import org.bukkit.entity.Firework;
import org.bukkit.scheduler.BukkitRunnable;

public class FireworkNms_26_1 implements FireworkNms {

    private final SpigotServer server;

    public FireworkNms_26_1(SpigotServer server) {
        this.server = server;
    }

    @Override
    public void explode(Firework firework) {
        new BukkitRunnable() {
            public void run() {
                ((CraftFirework) firework).getHandle().lifetime = 0;
            }
        }.runTaskLater(server, 1);
    }
}
