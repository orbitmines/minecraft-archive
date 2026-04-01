package com.orbitmines.archive.minecraft.spigot._2019.servers.prison;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMPlayer;
import lombok.Getter;
import org.bukkit.entity.Player;

public class PrisonPlayer extends OMPlayer<Prison> {

    @Getter private final PrisonInventory prisonInventory;

    public PrisonPlayer(Player player, Prison server) {
        super(player, server);

        this.prisonInventory = new PrisonInventory(this);
    }

    @Override
    protected void register() {
        server.registerPlayer(this);
    }

    @Override
    protected void unregister() {
        server.unregisterPlayer(this);
    }
}
