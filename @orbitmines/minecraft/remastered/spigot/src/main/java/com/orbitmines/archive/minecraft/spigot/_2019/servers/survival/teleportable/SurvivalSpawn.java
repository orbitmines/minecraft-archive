package com.orbitmines.archive.minecraft.spigot._2019.servers.survival.teleportable;

/*
 * OrbitMines - @author Fadi Shawki - 2018
 */

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.Survival;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.player.SurvivalPlayer;
import org.bukkit.Location;

public class SurvivalSpawn implements Teleportable {

    private final Survival survival;

    public SurvivalSpawn(Survival survival) {
        this.survival = survival;
    }

    @Override
    public Location getLocation() {
        return survival.getSpawn();
    }

    @Override
    public int getDuration(SurvivalPlayer player) {
        return 3;
    }

    @Override
    public Color getColor() {
        return Color.LIME;
    }

    @Override
    public String getName() {
        return "Spawn";
    }

    @Override
    public void onTeleport(SurvivalPlayer player, Location from, Location to) {
        player.setBackLocation(from);
    }
}
