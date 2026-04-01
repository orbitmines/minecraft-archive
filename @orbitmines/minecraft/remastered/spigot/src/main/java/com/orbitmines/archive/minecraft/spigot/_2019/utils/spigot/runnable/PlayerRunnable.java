package com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.runnable;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.placeholders.SpigotPlayer;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Collection;

public abstract class PlayerRunnable<Plugin extends JavaPlugin, P extends SpigotPlayer> extends SpigotRunnable {

    public PlayerRunnable(Plugin plugin, Interval interval) {
        super(plugin, interval);
    }

    public abstract void run(P player);

    public abstract Collection<P> getPlayers();

    @Override
    public void run() {
        for (P player : new ArrayList<>(getPlayers())) {
            run(player);
        }
    }
}
