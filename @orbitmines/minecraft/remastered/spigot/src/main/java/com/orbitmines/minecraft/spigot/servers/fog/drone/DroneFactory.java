package com.orbitmines.minecraft.spigot.servers.fog.drone;

import com.orbitmines.minecraft.spigot.servers.fog.ore.Ore;
import com.orbitmines.minecraft.spigot.servers.fog.run.Run;

/** Ore-based repair & module-removal economics for drones. */
public class DroneFactory {

    /** Cost to fully repair a drone from 0 HP, in units per listed ore. */
    public static int repairCost(Drone drone) {
        int total = 0;
        for (DroneModule module : drone.getModules()) {
            for (Ore o : module.getType().getRepairOres()) {
                total += module.getType().getRepairCostPerOre() * Math.max(1, module.getTier());
            }
        }
        return total;
    }

    /**
     * Cost to remove a module. Charged in the same ores that would be needed
     * to repair — keeps the recipe relationship consistent.
     */
    public static int removeCost(DroneModule module) {
        int total = 0;
        for (Ore o : module.getType().getRepairOres()) {
            total += module.getType().getRepairCostPerOre() * 2 * Math.max(1, module.getTier());
        }
        return total;
    }

    public static void repair(Drone drone) {
        drone.setHealth(drone.getMaxHealth());
    }

    public static boolean canCraftDrone(Run run) {
        /* POC: infinite. Real build: consume resources. */
        return true;
    }
}
