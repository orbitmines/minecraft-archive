package com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.guis;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.placeholders.SpigotPlayer;

public abstract class RestrictedGUI<P extends SpigotPlayer> extends GUI<P> {

    public RestrictedGUI(int size, String title, P viewer) {
        super(size, title, viewer);
    }

    public abstract boolean allowedToOpen();

    @Override
    public void open() {
        if (!allowedToOpen())
            return;

        super.open();
    }
}
