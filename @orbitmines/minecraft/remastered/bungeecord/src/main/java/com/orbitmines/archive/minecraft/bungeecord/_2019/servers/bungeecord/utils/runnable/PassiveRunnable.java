package com.orbitmines.archive.minecraft.bungeecord._2019.servers.bungeecord.utils.runnable;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import net.md_5.bungee.api.plugin.Plugin;

public abstract class PassiveRunnable extends BungeeRunnable {

    public PassiveRunnable(Plugin plugin, Interval interval) {
        super(plugin, interval);
    }

    public abstract void onRun();

    @Override
    public void run() {
        if (plugin.getProxy().getPlayers().size() == 0)
            return;

        onRun();
    }
}
