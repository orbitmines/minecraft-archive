package com.orbitmines.minecraft.spigot.servers.fog.stats;

import com.orbitmines.minecraft.spigot.servers.fog.choice.Choice;
import com.orbitmines.minecraft.spigot.servers.fog.choice.ChoiceState;
import com.orbitmines.minecraft.spigot.servers.fog.faction.Faction;
import com.orbitmines.minecraft.spigot.servers.fog.run.RunStore;
import lombok.Getter;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * In-memory stats for a {@link StatsHolder}, backed by a {@link RunStore}.
 *
 * <p>Strict sync/async discipline:
 * <ul>
 *   <li>The constructor does <b>zero</b> DB access — safe on the sync thread.</li>
 *   <li>{@link #load()} seeds every counter, choice state, faction and damaged-level
 *       index from the store. <b>Async only.</b></li>
 *   <li>{@link #flush()} writes the blocks-broken counter back.
 *       <b>Async only.</b></li>
 *   <li>Event handlers call {@link #incrementBlocksBroken(ToolType)},
 *       {@link #onChoiceActivated(Choice)}, {@link #onChoiceDamaged(int, Choice)} to
 *       mutate the cache on sync; no DB hits from those paths.</li>
 * </ul>
 *
 * <p>All read-side methods (derived stats, stack counts, faction, damaged lookup)
 * hit only in-memory maps and are therefore safe from any thread.</p>
 */
public class Stats {

    @Getter private final RunStore store;
    @Getter private final String prefix;

    private final Map<ToolType, Long> blocksBroken = new EnumMap<>(ToolType.class);
    /** Number of ACTIVE stacks of each choice — decreases on DAMAGED. */
    private final Map<Choice, Integer> activeStacks = new EnumMap<>(Choice.class);
    /** Total times each choice was ever picked — unaffected by DAMAGED flips. */
    private final Map<Choice, Integer> choiceStackCount = new EnumMap<>(Choice.class);
    /** level → the DAMAGED choice at that level (for the guaranteed re-offer). */
    private final Map<Integer, Choice> damagedChoiceByLevel = new HashMap<>();

    @Getter private volatile Faction faction;
    @Getter private volatile boolean loaded;

    public Stats(RunStore store, String prefix) {
        this.store = store;
        this.prefix = prefix;
        for (ToolType t : ToolType.values()) blocksBroken.put(t, 0L);
        for (Choice c : Choice.values()) {
            activeStacks.put(c, 0);
            choiceStackCount.put(c, 0);
        }
    }

    /** Async only. Seeds the in-memory cache from the run store. */
    public void load() {
        for (ToolType t : ToolType.values()) {
            blocksBroken.put(t, store.getBlocksBroken(prefix, t));
        }
        UUID uuid = extractUuid(prefix);
        if (uuid != null) {
            for (Choice c : Choice.values()) {
                int active = 0;
                for (int lvl = 0; lvl <= 100; lvl++) {
                    ChoiceState state = store.getChoiceState(uuid, lvl, c);
                    if (state == ChoiceState.ACTIVE) {
                        active++;
                    } else if (state == ChoiceState.DAMAGED) {
                        damagedChoiceByLevel.put(lvl, c);
                    }
                }
                activeStacks.put(c, active);
                choiceStackCount.put(c, store.getChoiceStackCount(uuid, c));
            }
            this.faction = store.getMemberFaction(uuid);
        }
        loaded = true;
    }

    /** Async only. Writes the blocks-broken counter back to the store. */
    public void flush() {
        for (Map.Entry<ToolType, Long> e : blocksBroken.entrySet()) {
            store.setBlocksBroken(prefix, e.getKey(), e.getValue());
        }
    }

    /* === Mutations from sync threads (in-memory only) === */

    public void incrementBlocksBroken(ToolType tool) {
        blocksBroken.merge(tool, 1L, Long::sum);
    }

    public void onChoiceActivated(Choice choice) {
        activeStacks.merge(choice, 1, Integer::sum);
        choiceStackCount.merge(choice, 1, Integer::sum);
        switch (choice) {
            case FACTION_OMEGA: this.faction = Faction.OMEGA; break;
            case FACTION_ALPHA: this.faction = Faction.ALPHA; break;
            case FACTION_BETA:  this.faction = Faction.BETA;  break;
            default: /* no-op */
        }
    }

    public void onChoiceDamaged(int level, Choice choice) {
        activeStacks.merge(choice, -1, Integer::sum);
        damagedChoiceByLevel.put(level, choice);
    }

    public void setFaction(Faction faction) {
        this.faction = faction;
    }

    /* === Reads (always from memory) === */

    public long getBlocksBroken(ToolType tool) {
        return blocksBroken.getOrDefault(tool, 0L);
    }

    public int getActiveStacks(Choice choice) {
        return activeStacks.getOrDefault(choice, 0);
    }

    public int getChoiceStackCount(Choice choice) {
        return choiceStackCount.getOrDefault(choice, 0);
    }

    public Choice getDamagedChoiceAt(int level) {
        return damagedChoiceByLevel.get(level);
    }

    public int getExtraHearts()              { return 4 * getActiveStacks(Choice.EXTRA_HP_4); }
    public double getDamageMultiplier()      { return 1.0 + 0.05 * getActiveStacks(Choice.DAMAGE_5); }
    public double getSpeedMultiplier()       { return 1.0 + 0.05 * getActiveStacks(Choice.SPEED_5); }
    public double getMiningSpeedMultiplier() { return 1.0 + 0.05 * getActiveStacks(Choice.MINING_SPEED_5); }

    private static UUID extractUuid(String prefix) {
        if (prefix == null) return null;
        String[] parts = prefix.split(":");
        if (parts.length < 2) return null;
        try { return UUID.fromString(parts[1]); } catch (IllegalArgumentException e) { return null; }
    }
}
