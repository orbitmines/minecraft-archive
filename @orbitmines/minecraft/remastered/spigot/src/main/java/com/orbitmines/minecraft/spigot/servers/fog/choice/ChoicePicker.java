package com.orbitmines.minecraft.spigot.servers.fog.choice;

import com.orbitmines.minecraft.spigot.servers.fog.faction.Faction;
import com.orbitmines.minecraft.spigot.servers.fog.run.Run;
import com.orbitmines.minecraft.spigot.servers.fog.run.RunStore;
import com.orbitmines.minecraft.spigot.servers.fog.stats.Stats;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

/**
 * Picks three {@link Choice}s for a level-up prompt, applying rarity weights and
 * exclusion rules. <b>Sync-safe:</b> reads only from the in-memory caches on
 * {@link Stats} and {@link Run} — never touches the store directly.
 *
 * <p>{@link #recordChoice(Run, UUID, int, Choice)} writes to the store
 * (<b>async-only</b>) and returns without updating in-memory caches. Callers
 * should have already bumped {@link Stats#onChoiceActivated(Choice)} and
 * {@link Run#markUniqueTaken(Choice)} on the sync thread before scheduling the
 * record — see {@link #applyInMemory(Run, Stats, Choice)}.</p>
 */
public class ChoicePicker {

    private static final Random RANDOM = new Random();

    public static List<Choice> pickThree(Run run, Stats stats, int level) {
        List<Choice> candidates = new ArrayList<>();

        Set<Choice> excluded = new HashSet<>();
        for (Choice c : Choice.values()) {
            if (c.isUnique() && run.isUniqueTaken(c)) excluded.add(c);
            if (c.getMaxStacks() > 0 && stats.getChoiceStackCount(c) >= c.getMaxStacks()) excluded.add(c);
        }
        Faction current = stats.getFaction();
        if (current != null) {
            if (current != Faction.OMEGA) excluded.add(Choice.FACTION_OMEGA);
            if (current != Faction.ALPHA) excluded.add(Choice.FACTION_ALPHA);
            if (current != Faction.BETA)  excluded.add(Choice.FACTION_BETA);
        }

        for (Choice c : Choice.values()) {
            if (excluded.contains(c)) continue;
            if (level < c.getMinLevel()) continue;
            candidates.add(c);
        }

        /* A previously-DAMAGED choice on this level is guaranteed to reappear. */
        Choice previouslyMade = stats.getDamagedChoiceAt(level);
        List<Choice> chosen = new ArrayList<>();
        if (previouslyMade != null && candidates.contains(previouslyMade)) {
            chosen.add(previouslyMade);
            candidates.remove(previouslyMade);
        }

        /* Guaranteed Drone Factory at lvl 10 if not yet unlocked. */
        if (level == 10 && !run.isUniqueTaken(Choice.DRONE_FACTORY) && candidates.contains(Choice.DRONE_FACTORY)) {
            chosen.add(Choice.DRONE_FACTORY);
            candidates.remove(Choice.DRONE_FACTORY);
        }

        while (chosen.size() < 3 && !candidates.isEmpty()) {
            Choice roll = weightedPick(candidates);
            chosen.add(roll);
            candidates.remove(roll);
        }

        while (chosen.size() < 3) {
            chosen.add(Choice.EXTRA_HP_4);
        }

        return Collections.unmodifiableList(chosen);
    }

    private static Choice weightedPick(List<Choice> pool) {
        int totalWeight = 0;
        for (Choice c : pool) totalWeight += c.getRarity().getWeight();
        int r = RANDOM.nextInt(totalWeight);
        int cum = 0;
        for (Choice c : pool) {
            cum += c.getRarity().getWeight();
            if (r < cum) return c;
        }
        return pool.get(0);
    }

    /**
     * Sync-safe. Mutates the in-memory caches to reflect a just-applied choice.
     * Pair this with {@link #recordChoice(Run, UUID, int, Choice)} on async to
     * keep the store in sync.
     */
    public static void applyInMemory(Run run, Stats stats, Choice choice) {
        if (stats != null) stats.onChoiceActivated(choice);
        if (run != null) run.markUniqueTaken(choice);
    }

    /** Async only. Persists the choice to the store. Does not touch caches. */
    public static void recordChoice(Run run, UUID uuid, int level, Choice choice) {
        RunStore store = run.getStore();
        store.setChoiceState(uuid, level, choice, ChoiceState.ACTIVE);
        store.incrementChoiceStackCount(uuid, choice);
        if (choice.isUnique()) store.setUniqueHolder(choice, uuid);
        if (choice == Choice.FACTION_OMEGA) store.setMemberFaction(uuid, Faction.OMEGA);
        if (choice == Choice.FACTION_ALPHA) store.setMemberFaction(uuid, Faction.ALPHA);
        if (choice == Choice.FACTION_BETA)  store.setMemberFaction(uuid, Faction.BETA);
    }
}
