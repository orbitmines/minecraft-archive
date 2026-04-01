package com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.bednpc;

import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.npcs.BedNpc;
import org.bukkit.Location;

/*
* OrbitMines - @author Fadi Shawki - 26-6-2017
*/
public interface BedNpcNms {

    int spawn(BedNpc bedNpc, boolean withArmor, boolean withItemsInHand);

    void destroy(BedNpc bedNpc);

    Location getFixedLocation(BedNpc bedNpc);

}
