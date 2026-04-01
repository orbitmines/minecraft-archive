package com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.armorstand;

import org.bukkit.Location;
import org.bukkit.craftbukkit.entity.CraftArmorStand;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;

public class ArmorStandNms_26_1 implements ArmorStandNms {

    @Override
    public ArmorStand spawn(Location location, boolean visible) {
        ArmorStand as = (ArmorStand) location.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);
        ((CraftArmorStand) as).getHandle().setInvisible(!visible);
        return as;
    }
}
