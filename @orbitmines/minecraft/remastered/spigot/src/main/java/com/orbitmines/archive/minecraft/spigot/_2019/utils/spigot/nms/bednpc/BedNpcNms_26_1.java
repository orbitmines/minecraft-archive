package com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.bednpc;

import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.npcs.BedNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.placeholders.SpigotServer;
import org.bukkit.Location;

public class BedNpcNms_26_1 implements BedNpcNms {

    private final SpigotServer server;

    public BedNpcNms_26_1(SpigotServer server) {
        this.server = server;
    }

    @Override
    public int spawn(BedNpc bedNpc, boolean withArmor, boolean withItemsInHand) {
        return 0;
    }

    @Override
    public void destroy(BedNpc bedNpc) {

    }

    @Override
    public Location getFixedLocation(BedNpc bedNpc) {
        return null;
    }
}
