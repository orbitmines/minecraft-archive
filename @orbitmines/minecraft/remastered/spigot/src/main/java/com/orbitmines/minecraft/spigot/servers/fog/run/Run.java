package com.orbitmines.minecraft.spigot.servers.fog.run;

import com.orbitmines.minecraft.spigot.servers.fog.choice.Choice;
import com.orbitmines.minecraft.spigot.servers.fog.database.FoGRunModel;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.Date;
import java.util.EnumSet;
import java.util.Set;
import java.util.UUID;

/**
 * Runtime wrapper around a {@link FoGRunModel} + {@link RunStore}. The spawn
 * location is owned by the world's level.dat (via {@link World#setSpawnLocation})
 * — not persisted in the DB.
 *
 * <p>The "uniques taken" set is the in-memory authoritative view for the lifetime
 * of the server process. It's seeded from the store on the async side when a
 * {@link RunManager} first loads the run, and mutated by choice-recording flows.
 * Sync-thread callers must only read it — writes happen alongside the async
 * DB persistence.</p>
 */
public class Run {

    @Getter private final FoGRunModel model;
    @Getter private final RunStore store;
    private final Set<Choice> uniquesTaken = EnumSet.noneOf(Choice.class);

    public Run(FoGRunModel model) {
        this.model = model;
        this.store = new RunStore(model.getId() == null ? 0 : model.getId());
    }

    public long getId() { return model.getId() == null ? 0 : model.getId(); }
    public UUID getOwnerUuid() { return model.getOwnerUuid(); }
    public String getWorldFileName() { return model.getWorldFileName(); }
    public Difficulty getDifficulty() { return Difficulty.parse(model.getDifficulty()); }
    public RunState getState() { return RunState.parse(model.getState()); }

    public void setState(RunState state) {
        model.setState(state.toString());
    }

    public World getWorld() {
        if (model.getWorldFileName() == null) return null;
        return Bukkit.getWorld(model.getWorldFileName());
    }

    public Location getSpawn() {
        World world = getWorld();
        if (world == null) return null;
        return world.getSpawnLocation();
    }

    public boolean isOwner(UUID uuid) {
        return uuid != null && uuid.equals(model.getOwnerUuid());
    }

    /** Global run start date (when the owner created it). */
    public Date getCreatedAt() {
        return model.getCreatedAt();
    }

    /** Per-player "joined this run" date, or null if the player has never joined. */
    public Date getMemberJoinedAt(UUID uuid) {
        Long millis = store.getMemberJoinedAtMillis(uuid);
        return millis == null ? null : new Date(millis);
    }

    /* === In-memory uniques-taken set === */

    public boolean isUniqueTaken(Choice choice) {
        return uniquesTaken.contains(choice);
    }

    public void markUniqueTaken(Choice choice) {
        if (choice != null && choice.isUnique()) uniquesTaken.add(choice);
    }

    /** Async-only. Seeds the uniques-taken set from the store. Idempotent. */
    public void loadUniquesFromStore() {
        for (Choice c : Choice.values()) {
            if (c.isUnique() && store.isUniqueTaken(c)) uniquesTaken.add(c);
        }
    }
}
