package com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.world;

import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.entities.Mob;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Collection;

/*
* OrbitMines - @author Fadi Shawki - 2017
*/
public interface WorldNms {

    void chestAnimation(Location location, boolean opened);

    void enderchestAnimation(Location location, boolean opened);

    void conduitAnimation(Location location, Collection<? extends Player> players);

    void setSpawner(Location location, Mob mob);

    Mob getSpawner(Location location);
}
