package com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.actionbar;


import org.bukkit.entity.Player;

import java.util.Collection;

/**
 * Created by Fadi on 30-4-2016.
 */
public interface ActionBarNms {

    void send(Collection<? extends Player> players, String actionBar);

}
