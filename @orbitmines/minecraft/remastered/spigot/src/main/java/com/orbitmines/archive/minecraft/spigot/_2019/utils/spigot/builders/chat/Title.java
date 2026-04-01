package com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.chat;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.mutable.MutablePlayerString;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.placeholders.SpigotPlayer;
import lombok.Getter;

import java.util.Collection;

public class Title<P extends SpigotPlayer> {

    // TODO, actually make fadeIn, stay, fadeOut work on a runnable, and allow user to pass on interval for update

    @Getter private final MutablePlayerString<P> title;
    @Getter private final MutablePlayerString<P> subTitle;
    @Getter private final int fadeIn;
    @Getter private final int stay;
    @Getter private final int fadeOut;

    public Title(MutablePlayerString<P> title, MutablePlayerString<P> subTitle, int fadeIn, int stay, int fadeOut) {
        this.title = title;
        this.subTitle = subTitle;
        this.fadeIn = fadeIn;
        this.stay = stay;
        this.fadeOut = fadeOut;
    }

    public void send(P player) {
        player.sendTitle(title.toString(player), subTitle.toString(player), fadeIn, stay, fadeOut);
    }

    public void send(P... players) {
        for (P player : players) {
            send(player);
        }
    }

    public void send(Collection<P> players) {
        for (P player : players) {
            send(player);
        }
    }
}
