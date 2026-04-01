package com.orbitmines.archive.minecraft.bungeecord._2019.servers.bungeecord.utils.runnable;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft.bungeecord._2019.servers.bungeecord.libs.BungeePlayer;
import net.md_5.bungee.api.plugin.Plugin;

import java.util.Collection;

public abstract class PlayerRunnable extends BungeeRunnable {

    public PlayerRunnable(Plugin plugin, Interval interval) {
        super(plugin, interval);
    }

    public abstract void run(BungeePlayer player);

    public abstract Collection<BungeePlayer> getPlayers();

    @Override
    public void run() {
        for (BungeePlayer player : getPlayers()) {
            run(player);
        }
    }
}
