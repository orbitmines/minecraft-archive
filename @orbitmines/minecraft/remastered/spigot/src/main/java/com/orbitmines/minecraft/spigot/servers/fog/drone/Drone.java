package com.orbitmines.minecraft.spigot.servers.fog.drone;

import com.orbitmines.minecraft.spigot.servers.fog.run.Run;
import com.orbitmines.minecraft.spigot.servers.fog.stats.Stats;
import com.orbitmines.minecraft.spigot.servers.fog.stats.StatsHolder;
import com.orbitmines.minecraft.spigot.servers.fog.util.Fogi18n;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.entity.Allay;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * A single drone belonging to a player. Modelled on an Allay; up to 9 per player.
 */
public class Drone implements StatsHolder {

    public enum Mode { IDLE, ACTIVE }

    @Getter private final int id;
    @Getter private final UUID ownerUuid;
    @Getter @Setter private Mode mode = Mode.IDLE;
    @Getter private final List<DroneModule> modules = new ArrayList<>();

    @Getter @Setter private Allay entity;
    @Getter @Setter private double health = 20.0;
    @Getter @Setter private double maxHealth = 20.0;

    private final Stats stats;

    public Drone(int id, UUID ownerUuid, Run run) {
        this.id = id;
        this.ownerUuid = ownerUuid;
        this.stats = new Stats(run.getStore(), "member:" + ownerUuid + ":drone:" + id);
    }

    public boolean addModule(DroneModule module) {
        if (modules.size() >= 4) return false; /* POC cap */
        modules.add(module);
        return true;
    }

    public boolean removeModule(ModuleType type) {
        return modules.removeIf(m -> m.getType() == type);
    }

    public boolean hasModule(ModuleType type) {
        return modules.stream().anyMatch(m -> m.getType() == type);
    }

    @Override
    public UUID getOwnerUuid() { return ownerUuid; }

    @Override
    public String getStatsPrefix() { return "member:" + ownerUuid + ":drone:" + id; }

    @Override
    public Stats getStats() { return stats; }

    /** Spawn the Allay entity at the given location (main thread). */
    public void spawn(Location at) {
        if (entity != null && entity.isValid()) return;
        LivingEntity e = (LivingEntity) at.getWorld().spawnEntity(at, EntityType.ALLAY);
        if (e instanceof Allay) {
            this.entity = (Allay) e;
            this.entity.setCustomName("§b§l" + Fogi18n.defaultText("drone.entity_name", id));
            this.entity.setCustomNameVisible(true);
            this.entity.setRemoveWhenFarAway(false);
        }
    }

    public void despawn() {
        if (entity != null && entity.isValid()) {
            entity.remove();
        }
        entity = null;
    }
}
