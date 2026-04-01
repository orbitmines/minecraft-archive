package com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.runnables;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMServer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.guis.ServerSelectorGUI;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.runnable.Interval;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.runnable.PassiveRunnable;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.runnable.TimeUnit;

public class ServerSelectorRunnable<S extends OMServer<S, P>, P extends OMPlayer<S, P>> extends PassiveRunnable<S> {

    private final S server;

    public ServerSelectorRunnable(S plugin) {
        super(plugin, Interval.of(TimeUnit.SECOND, 2));

        this.server = plugin;
    }

    @Override
    public void onRun() {
        for (P player : server.getPlayers()) {
            if (!(player.getLastGUI() instanceof ServerSelectorGUI))
                continue;

            if (!player.hasOpened(player.getLastGUI()))
                continue;

            player.getLastGUI().update();
        }
    }
}
