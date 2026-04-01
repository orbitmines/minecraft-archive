package com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.events;

/*
 * OrbitMines - @author Fadi Shawki - 2018
 */

import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.KitPvP;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.KitPvPPlayer;
import lombok.Getter;
import org.bukkit.event.Event;

public class KitEvent<E extends Event> {

    @Getter private final KitPvPPlayer player;
    @Getter private E event;

    public KitEvent(KitPvPPlayer player, E event) {
        this.player = player;
        this.event = event;
    }

    public KitPvP server() {
        return player.server();
    }
}
