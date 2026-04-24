package com.orbitmines.minecraft.spigot.servers.fog.drone;

import lombok.Getter;
import lombok.Setter;

/** One equipped module on a drone. Not a Bukkit item — just a record. */
public class DroneModule {

    @Getter private final ModuleType type;
    @Getter @Setter private int tier;

    public DroneModule(ModuleType type, int tier) {
        this.type = type;
        this.tier = tier;
    }

    public DroneModule(ModuleType type) {
        this(type, 1);
    }
}
