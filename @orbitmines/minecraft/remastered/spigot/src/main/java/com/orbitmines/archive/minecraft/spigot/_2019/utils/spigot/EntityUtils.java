package com.orbitmines.archive.minecraft.spigot._2019.utils.spigot;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class EntityUtils {

    /* TODO: Check if this causes a memory leak over the span of 1 day */
    private final static Map<UUID, Entity> entityCache = new HashMap<>();
    public static Entity getEntityByUUID(UUID uuid) {
        return getEntityByUUID(uuid, Entity.class);
    }
    public static <T extends Entity> T getEntityByUUID(UUID uuid, Class<T> aClass) {
        if (entityCache.containsKey(uuid))
            return (T) entityCache.get(uuid);

        for (World world : Bukkit.getWorlds()) {
            for (T entity : world.getEntitiesByClass(aClass)) {
                if (entity.getUniqueId().equals(uuid)) {
                    entityCache.put(uuid, entity);
                    return entity;
                }
            }
        }
        return null;
    }

    public static void lookAt(LivingEntity entity, double x, double z) {
        lookAt(entity, x, entity.getLocation().getY() + entity.getEyeHeight(), z);
    }

    public static void lookAt(LivingEntity entity, Location location) {
        lookAt(entity, location.getX(), location.getY(), location.getZ());
    }

    public static void lookAt(LivingEntity entity, double x, double y, double z) {
        Location location = entity.getLocation();

        double vX = location.getX() - x;
        double vY = location.getY() - y;
        double vZ = location.getZ() - z;

        double d = Math.sqrt(vX * vX + vY * vY + vZ * vZ);

        vX /= d;
        vY /= d;
        vZ /= d;

        double yaw = Math.toDegrees(Math.atan2(vZ, vX)) + 90;
        double pitch = Math.toDegrees(Math.asin(vY));

        location.setYaw((float) yaw);
        location.setPitch((float) pitch);

        entity.teleport(location);
    }
}
