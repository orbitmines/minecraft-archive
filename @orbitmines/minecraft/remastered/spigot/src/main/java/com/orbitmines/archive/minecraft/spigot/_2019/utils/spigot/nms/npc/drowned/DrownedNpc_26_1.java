package com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.drowned;

import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.npc.NpcNms;
import org.bukkit.Location;
import org.bukkit.entity.Drowned;
import org.bukkit.entity.Entity;

import java.util.Collection;

public class DrownedNpc_26_1 implements DrownedNpc {

    private final NpcNms npcNms;

    public DrownedNpc_26_1(NpcNms npcNms) {
        this.npcNms = npcNms;
    }

    @Override
    public Entity spawn(Location location, Collection<Option> options) {
        Drowned entity = location.getWorld().spawn(location, Drowned.class, false, null);
        entity.setRemoveWhenFarAway(false);

        if (options.contains(Option.DISABLE_MOVEMENT))
            npcNms.setNoAI(entity);

        if (options.contains(Option.DISABLE_GRAVITY))
            entity.setGravity(false);

        if (options.contains(Option.DISABLE_COLLISION))
            entity.setCollidable(false);

        return entity;
    }
}
