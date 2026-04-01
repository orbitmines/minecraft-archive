package com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.runnable;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.placeholders.SpigotPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.placeholders.SpigotServer;

import java.util.ArrayList;
import java.util.Collection;

public abstract class PlayerRunnable<S extends SpigotServer, P extends SpigotPlayer> extends SpigotRunnable<S> {

    public PlayerRunnable(S server, Interval interval) {
        super(server, interval);
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
