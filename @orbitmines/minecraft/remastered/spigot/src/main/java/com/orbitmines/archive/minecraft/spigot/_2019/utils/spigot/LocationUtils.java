package com.orbitmines.archive.minecraft.spigot._2019.utils.spigot;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import org.bukkit.Location;

public class LocationUtils {

    public static String humanFriendlyString(Location location) {
        return "x: " + location.getBlockX() + ", y: " + location.getBlockY() + ", z: " + location.getBlockZ();
    }

    public static boolean isBetween3D(Location loc, Location loc1, Location loc2){
        int minX = (int) Math.min(loc.getX(), loc1.getX());
        int maxX = (int) Math.max(loc.getX(), loc1.getX());
        int minY = (int) Math.min(loc.getY(), loc1.getY());
        int maxY = (int) Math.max(loc.getY(), loc1.getY());
        int minZ = (int) Math.min(loc.getZ(), loc1.getZ());
        int maxZ = (int) Math.max(loc.getZ(), loc1.getZ());

        return (minX <= loc2.getX() && loc2.getX() <= maxX) && (minY <= loc2.getY() && loc2.getY() <= maxY) && (minZ <= loc2.getZ() && loc2.getZ() <= maxZ);
    }

    public static boolean isBetween2D(Location loc, Location loc1, Location loc2){
        int minX = (int) Math.min(loc.getX(), loc1.getX());
        int maxX = (int) Math.max(loc.getX(), loc1.getX());
        int minY = (int) Math.min(loc.getY(), loc1.getY());
        int maxY = (int) Math.max(loc.getY(), loc1.getY());

        return (minX <= loc2.getX() && loc2.getX() <= maxX) && (minY <= loc2.getY() && loc2.getY() <= maxY);
    }

    /*

        Yaw & Pitch

     */

    /* Yaw -180/Degree 0 is facing north */
    public static float yawToDegree(Location location) {
        return yawToDegree(location.getYaw());
    }
    public static float yawToDegree(float yaw) {
        return yaw + 180F;
    }

    public static float degreeToYaw(float degree) {
        return degree - 180F;
    }

    /* 0-180 */
    public static float pitchToDegree(Location location) {
        return pitchToDegree(location.getPitch());
    }
    public static float pitchToDegree(float pitch) {
        return pitch + 90F;
    }

    public static float degreeToPitch(float degree) {
        return degree - 90F;
    }
}
