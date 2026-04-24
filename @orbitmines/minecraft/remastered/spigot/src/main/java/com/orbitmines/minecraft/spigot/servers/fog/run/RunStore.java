package com.orbitmines.minecraft.spigot.servers.fog.run;

import com.orbitmines.archive.minecraft._2019.utils.state.StateProvider;
import com.orbitmines.minecraft.spigot.servers.fog.choice.Choice;
import com.orbitmines.minecraft.spigot.servers.fog.choice.ChoiceState;
import com.orbitmines.minecraft.spigot.servers.fog.faction.Faction;
import com.orbitmines.minecraft.spigot.servers.fog.stats.ToolType;
import com.orbitmines.minecraft.spigot.servers.fog.structure.CompartmentType;
import com.orbitmines.minecraft.spigot.servers.fog.world.MapExpansion;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Typed facade over the {@link StateProvider} key-value store, scoped to a run.
 *
 * <p>Every method here hides the underlying key shape so call sites never build
 * colon-separated keys by hand. If the storage layout changes, the change is
 * localized here.</p>
 *
 * <p>Base prefix is {@code fog:run:&lt;runId&gt;:}.</p>
 */
public class RunStore {

    private static final String KEY_MEMBER          = "member:";
    private static final String KEY_MEMBERS_CSV     = "members";
    private static final String KEY_UNIQUE          = "unique:";
    private static final String KEY_COMPARTMENT     = "compartment:";
    private static final String KEY_MAP_EXPANSIONS  = "map:expansions";
    private static final String KEY_MAP_EXPANSION   = "map:expansion:";
    private static final String KEY_HONEY_TREE      = "honey:tree:";

    private final long runId;
    private final String prefix;

    public RunStore(long runId) {
        this.runId = runId;
        this.prefix = "fog:run:" + runId + ":";
    }

    public long getRunId() { return runId; }

    /*
     *   Primitive access (kept package-private-ish; prefer the typed helpers below).
     */

    private String raw(String suffix) {
        return StateProvider.getInstance().getString(prefix + suffix);
    }

    private void rawSet(String suffix, String value) {
        if (value == null) {
            StateProvider.getInstance().deleteString(prefix + suffix);
            return;
        }
        StateProvider.getInstance().setString(prefix + suffix, value);
    }

    private void rawDelete(String suffix) {
        StateProvider.getInstance().deleteString(prefix + suffix);
    }

    private int rawInt(String suffix, int fallback) {
        String v = raw(suffix);
        if (v == null) return fallback;
        try { return Integer.parseInt(v); } catch (NumberFormatException e) { return fallback; }
    }

    private long rawLong(String suffix, long fallback) {
        String v = raw(suffix);
        if (v == null) return fallback;
        try { return Long.parseLong(v); } catch (NumberFormatException e) { return fallback; }
    }

    /*
     *   Members.
     */

    public boolean isMember(UUID uuid) {
        return raw(KEY_MEMBER + uuid) != null;
    }

    public List<UUID> getMembers() {
        List<UUID> uuids = new ArrayList<>();
        String csv = raw(KEY_MEMBERS_CSV);
        if (csv == null || csv.isEmpty()) return uuids;
        for (String part : csv.split(",")) {
            try { uuids.add(UUID.fromString(part)); } catch (IllegalArgumentException ignored) {}
        }
        return uuids;
    }

    public void registerMember(UUID uuid) {
        List<UUID> current = getMembers();
        if (current.contains(uuid)) return;
        current.add(uuid);
        StringBuilder csv = new StringBuilder();
        for (int i = 0; i < current.size(); i++) {
            if (i > 0) csv.append(',');
            csv.append(current.get(i));
        }
        rawSet(KEY_MEMBERS_CSV, csv.toString());
        rawSet(KEY_MEMBER + uuid, "1");
        /* Stamp first-joined-at if we've never registered this member before. */
        if (getMemberJoinedAtMillis(uuid) == null) {
            setMemberJoinedAtMillis(uuid, System.currentTimeMillis());
        }
    }

    public void removeMember(UUID uuid) {
        rawDelete(KEY_MEMBER + uuid);
    }

    /*
     *   Per-member progression.
     */

    public int getMemberLevel(UUID uuid) {
        return rawInt(KEY_MEMBER + uuid + ":level", 0);
    }

    public void setMemberLevel(UUID uuid, int level) {
        rawSet(KEY_MEMBER + uuid + ":level", Integer.toString(level));
    }

    public long getMemberExperience(UUID uuid) {
        return rawLong(KEY_MEMBER + uuid + ":experience", 0L);
    }

    public void setMemberExperience(UUID uuid, long xp) {
        rawSet(KEY_MEMBER + uuid + ":experience", Long.toString(xp));
    }

    public long getMemberPlayTimeMs(UUID uuid) {
        return rawLong(KEY_MEMBER + uuid + ":play-time", 0L);
    }

    public void addMemberPlayTimeMs(UUID uuid, long deltaMs) {
        rawSet(KEY_MEMBER + uuid + ":play-time", Long.toString(getMemberPlayTimeMs(uuid) + deltaMs));
    }

    /** Epoch millis at which this player was first registered as a member of this run.
        Used for per-player "joined this run at" stats distinct from the run's own createdAt. */
    public Long getMemberJoinedAtMillis(UUID uuid) {
        String v = raw(KEY_MEMBER + uuid + ":joined-at");
        if (v == null) return null;
        try { return Long.parseLong(v); } catch (NumberFormatException e) { return null; }
    }

    public void setMemberJoinedAtMillis(UUID uuid, long millis) {
        rawSet(KEY_MEMBER + uuid + ":joined-at", Long.toString(millis));
    }

    public Faction getMemberFaction(UUID uuid) {
        return Faction.parse(raw(KEY_MEMBER + uuid + ":faction"));
    }

    public void setMemberFaction(UUID uuid, Faction faction) {
        rawSet(KEY_MEMBER + uuid + ":faction", faction == null ? null : faction.name());
    }

    public boolean isMemberDead(UUID uuid) {
        return "1".equals(raw(KEY_MEMBER + uuid + ":dead"));
    }

    /** Level of the level-up choice currently in progress (if the player
        disconnected mid-prompt), or {@code null} if no prompt is pending. */
    public Integer getPendingChoiceLevel(UUID uuid) {
        String v = raw(KEY_MEMBER + uuid + ":pending-choice-level");
        if (v == null) return null;
        try { return Integer.parseInt(v); } catch (NumberFormatException e) { return null; }
    }

    public void setPendingChoiceLevel(UUID uuid, Integer level) {
        rawSet(KEY_MEMBER + uuid + ":pending-choice-level", level == null ? null : level.toString());
    }

    public void setMemberDead(UUID uuid, boolean dead) {
        rawSet(KEY_MEMBER + uuid + ":dead", dead ? "1" : null);
    }

    public String getMemberInventoryBlob(UUID uuid) {
        return raw(KEY_MEMBER + uuid + ":inventory");
    }

    public void setMemberInventoryBlob(UUID uuid, String blob) {
        rawSet(KEY_MEMBER + uuid + ":inventory", blob);
    }

    /*
     *   Per-member choice history.
     */

    public ChoiceState getChoiceState(UUID uuid, int level, Choice choice) {
        String v = raw(KEY_MEMBER + uuid + ":choice:" + level + ":" + choice.getStorageKey());
        if (v == null) return null;
        return ChoiceState.parse(v);
    }

    public boolean hasChoiceAtLevel(UUID uuid, int level, Choice choice) {
        return raw(KEY_MEMBER + uuid + ":choice:" + level + ":" + choice.getStorageKey()) != null;
    }

    public void setChoiceState(UUID uuid, int level, Choice choice, ChoiceState state) {
        rawSet(KEY_MEMBER + uuid + ":choice:" + level + ":" + choice.getStorageKey(), state == null ? null : state.name());
    }

    public int getChoiceStackCount(UUID uuid, Choice choice) {
        return rawInt(KEY_MEMBER + uuid + ":choice_count:" + choice.getStorageKey(), 0);
    }

    public void incrementChoiceStackCount(UUID uuid, Choice choice) {
        rawSet(KEY_MEMBER + uuid + ":choice_count:" + choice.getStorageKey(), Integer.toString(getChoiceStackCount(uuid, choice) + 1));
    }

    /** Walk member choice levels to find a DAMAGED choice previously made at `level`. */
    public Choice findDamagedChoiceAtLevel(UUID uuid, int level) {
        for (Choice c : Choice.values()) {
            if (getChoiceState(uuid, level, c) == ChoiceState.DAMAGED) return c;
        }
        return null;
    }

    /*
     *   Uniques (one-of-these-per-run).
     */

    public boolean isUniqueTaken(Choice choice) {
        return choice.isUnique() && raw(KEY_UNIQUE + choice.getStorageKey()) != null;
    }

    public UUID getUniqueHolder(Choice choice) {
        String v = raw(KEY_UNIQUE + choice.getStorageKey());
        if (v == null) return null;
        try { return UUID.fromString(v); } catch (IllegalArgumentException e) { return null; }
    }

    public void setUniqueHolder(Choice choice, UUID uuid) {
        rawSet(KEY_UNIQUE + choice.getStorageKey(), uuid == null ? null : uuid.toString());
    }

    /*
     *   Stats (shared by player + drone). The caller provides its own prefix
     *   (e.g. "member:<uuid>" or "member:<uuid>:drone:<id>").
     */

    public long getBlocksBroken(String statsPrefix, ToolType tool) {
        return rawLong(statsPrefix + ":stats:tool:" + tool.name().toLowerCase() + ":blocks_broken", 0L);
    }

    public void setBlocksBroken(String statsPrefix, ToolType tool, long count) {
        rawSet(statsPrefix + ":stats:tool:" + tool.name().toLowerCase() + ":blocks_broken", Long.toString(count));
    }

    /*
     *   Compartments.
     */

    public enum CompartmentState { DECAYED, CLAIMED, DAMAGED }

    public CompartmentState getCompartmentState(CompartmentType type) {
        String v = raw(KEY_COMPARTMENT + type.name() + ":state");
        if (v == null) return null;
        try { return CompartmentState.valueOf(v); } catch (IllegalArgumentException e) { return null; }
    }

    public void setCompartmentState(CompartmentType type, CompartmentState state) {
        rawSet(KEY_COMPARTMENT + type.name() + ":state", state == null ? null : state.name());
    }

    public void setCompartmentLocation(CompartmentType type, int x, int y, int z) {
        rawSet(KEY_COMPARTMENT + type.name() + ":x", Integer.toString(x));
        rawSet(KEY_COMPARTMENT + type.name() + ":y", Integer.toString(y));
        rawSet(KEY_COMPARTMENT + type.name() + ":z", Integer.toString(z));
    }

    public Integer getCompartmentX(CompartmentType type) {
        String v = raw(KEY_COMPARTMENT + type.name() + ":x");
        if (v == null) return null;
        try { return Integer.parseInt(v); } catch (NumberFormatException e) { return null; }
    }

    public Integer getCompartmentY(CompartmentType type) {
        String v = raw(KEY_COMPARTMENT + type.name() + ":y");
        if (v == null) return null;
        try { return Integer.parseInt(v); } catch (NumberFormatException e) { return null; }
    }

    public Integer getCompartmentZ(CompartmentType type) {
        String v = raw(KEY_COMPARTMENT + type.name() + ":z");
        if (v == null) return null;
        try { return Integer.parseInt(v); } catch (NumberFormatException e) { return null; }
    }

    /*
     *   Map expansions.
     */

    public List<MapExpansion> getUnlockedExpansions() {
        List<MapExpansion> out = new ArrayList<>();
        String csv = raw(KEY_MAP_EXPANSIONS);
        if (csv == null || csv.isEmpty()) return out;
        for (String part : csv.split(",")) {
            try { out.add(MapExpansion.valueOf(part)); } catch (IllegalArgumentException ignored) {}
        }
        return out;
    }

    public void unlockExpansion(MapExpansion expansion) {
        List<MapExpansion> unlocked = getUnlockedExpansions();
        if (unlocked.contains(expansion)) return;
        unlocked.add(expansion);
        StringBuilder csv = new StringBuilder();
        for (int i = 0; i < unlocked.size(); i++) {
            if (i > 0) csv.append(',');
            csv.append(unlocked.get(i).name());
        }
        rawSet(KEY_MAP_EXPANSIONS, csv.toString());
    }

    public boolean isExpansionDamaged(MapExpansion expansion) {
        return "1".equals(raw(KEY_MAP_EXPANSION + expansion.name() + ":damaged"));
    }

    public void setExpansionDamaged(MapExpansion expansion, boolean damaged) {
        rawSet(KEY_MAP_EXPANSION + expansion.name() + ":damaged", damaged ? "1" : null);
    }

    /*
     *   Honey trees.
     */

    public boolean isHoneyTree(int x, int y, int z) {
        return "1".equals(raw(KEY_HONEY_TREE + x + ":" + y + ":" + z));
    }

    public void setHoneyTree(int x, int y, int z) {
        rawSet(KEY_HONEY_TREE + x + ":" + y + ":" + z, "1");
    }
}
