package com.orbitmines.minecraft.spigot.servers.fog;

import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMPlayer;
import com.orbitmines.minecraft.spigot.servers.fog.database.FoGPlayerModel;
import com.orbitmines.minecraft.spigot.servers.fog.drone.Drone;
import com.orbitmines.minecraft.spigot.servers.fog.gui.RunSelector;
import com.orbitmines.minecraft.spigot.servers.fog.level.LevelFormulas;
import com.orbitmines.minecraft.spigot.servers.fog.raid.RaidManager;
import com.orbitmines.minecraft.spigot.servers.fog.run.Difficulty;
import com.orbitmines.minecraft.spigot.servers.fog.run.Run;
import com.orbitmines.minecraft.spigot.servers.fog.scoreboard.FoGScoreboard;
import com.orbitmines.minecraft.spigot.servers.fog.stats.Stats;
import com.orbitmines.minecraft.spigot.servers.fog.stats.StatsHolder;
import com.orbitmines.minecraft.spigot.servers.fog.util.InventoryBlob;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class FoGPlayer extends OMPlayer<FoG, FoGPlayer> implements StatsHolder {

    @Getter private FoGPlayerModel playerModel;
    @Getter @Setter private Run activeRun;
    @Getter private RaidManager raidManager;
    @Getter private Stats stats;
    @Getter private final List<Drone> drones = new ArrayList<>();
    @Getter @Setter private boolean dronesActive = false;

    /** Pending coop join requests: requester raw name → run id. */
    @Getter private final Map<String, Long> coopRequestsFrom = new HashMap<>();

    /** Captured on quit (sync) so {@link #afterQuitAsync()} can write to the DB. */
    private transient String pendingInventoryBlob;
    private transient Run pendingInventoryRun;

    public FoGPlayer(Player player, FoG server) {
        super(player, server);
    }

    @Override
    protected void register() {
        server.registerPlayer(this);
    }

    @Override
    protected void unregister() {
        server.unregisterPlayer(this);
    }

    @Override
    public FoGPlayer getInstance() {
        return this;
    }

    @Override
    public boolean onJoin() {
        super.onJoin();
        this.playerModel = FoGPlayerModel.findOrInitializeBy(getUUID());
        if (!this.playerModel.isInserted()) this.playerModel.insert();

        server.runSync(() -> {
            /* Fall-damage safety net — the player may land mid-air if their last
               logout location was the sky anchor. Invulnerability only here; the
               downstream paths (joinRun / teleportToSkyAnchor) own flight state.
               Setting flight during the initial join handshake causes client
               desync (player can't move, tab/chat silent, no error). */
            bukkit().setInvulnerable(true);

            resetScoreboard();
            setScoreboard(new FoGScoreboard(server, this));

            /* Always-on kit (Switch Run + Spectate) so the player has an escape
               route regardless of state (spectating, no run, or in their run). */
            server.getLobbyKit().copyAlwaysOnToInventory(this);

            if (playerModel.getActiveRunId() != null) {
                server.getRunManager().joinRun(this, playerModel.getActiveRunId());
            } else {
                /* No active run — elevate the player to a creative-style sky
                   anchor in the default world and open the run selector. */
                server.teleportToSkyAnchor(this);
                RunSelector.open(server, this);
            }
        });

        return true;
    }

    @Override
    public void beforeQuitSync() {
        /* Sync thread: safe to serialize the inventory, NOT to write the DB.
           Capture the blob for afterQuitAsync to persist. */
        if (activeRun != null) {
            pendingInventoryRun = activeRun;
            pendingInventoryBlob = InventoryBlob.serialize(bukkit());
        }
        for (Drone d : drones) d.despawn();
        super.beforeQuitSync();
    }

    @Override
    public void afterQuitAsync() {
        if (pendingInventoryRun != null) {
            pendingInventoryRun.getStore().setMemberInventoryBlob(getUUID(), pendingInventoryBlob);
        }
        super.afterQuitAsync();
    }

    @Override
    public void onFirstLogin() {
        /* The sky-anchor + RunSelector.open flow runs in onJoin for everyone
           without an active run, so first-login has nothing additional to do. */
    }

    /** Give the freshly-created-run spawn inventory. Only call on run start / first join. */
    public void giveRunStartInventory() {
        server.getLobbyKit().copyToInventory(this);
    }

    public void toggleDroneMode() {
        setDronesActive(!dronesActive);
        for (Drone d : drones) d.setMode(dronesActive ? Drone.Mode.ACTIVE : Drone.Mode.IDLE);
        /* Kit item material flips LEVER / REDSTONE_TORCH — refresh the slot.
           The ItemHoverActionBar re-renders every tick, so the ACTIVE/idle label
           updates on its own — no chat message. */
        server.getLobbyKit().refreshDroneToggle(this);
    }

    /** In-memory cache of the player's total run XP. Populated on initForRun from a
        pre-fetched async read; kept in sync by addExperience. Sync-safe to read. */
    private volatile long cachedTotalXp;

    public int getLevel() {
        if (activeRun == null) return 0;
        return LevelFormulas.computeLevel(cachedTotalXp);
    }

    public long getTotalXp() {
        return cachedTotalXp;
    }

    /** Async-only — writes to the RunStore. */
    public void addExperience(long amount) {
        Run run = activeRun;
        if (run == null) return;
        int before = getLevel();
        long newTotal = cachedTotalXp + amount;
        cachedTotalXp = newTotal;
        run.getStore().setMemberExperience(getUUID(), newTotal);
        int after = LevelFormulas.computeLevel(newTotal);
        run.getStore().setMemberLevel(getUUID(), after);
        server.runSync(this::updateExperienceBar);
        if (after > before) {
            int fBefore = before, fAfter = after;
            server.runSync(() -> {
                for (int lvl = fBefore + 1; lvl <= fAfter; lvl++) {
                    server.getLevelUpFlow().start(this, lvl, run);
                }
            });
        }
    }

    /** Sync-safe — reads only the cached XP. */
    public void updateExperienceBar() {
        if (activeRun == null) return;
        int level = getLevel();
        long into = LevelFormulas.xpIntoLevel(cachedTotalXp, level);
        long required = LevelFormulas.required(level);
        bukkit().setLevel(level);
        bukkit().setExp(required == 0 ? 0f : Math.min(1f, (float) into / (float) required));
    }

    /** Sync-safe: reads only the in-memory stats map. */
    public void applyDerivedStats() {
        if (activeRun == null || stats == null) return;
        AttributeInstance maxHp = bukkit().getAttribute(Attribute.MAX_HEALTH);
        if (maxHp != null) maxHp.setBaseValue(Math.max(2.0, 20.0 + stats.getExtraHearts() * 2.0));
        AttributeInstance speed = bukkit().getAttribute(Attribute.MOVEMENT_SPEED);
        if (speed != null) speed.setBaseValue(0.1 * stats.getSpeedMultiplier());
    }

    /**
     * Sync-only. Applies a new run to this player. DB reads/writes are the caller's
     * responsibility — see {@link com.orbitmines.minecraft.spigot.servers.fog.run.RunManager}.
     *
     * @param newRun            new run (or null to leave)
     * @param restoreFromBlob   pre-fetched inventory blob; null means don't touch inventory
     * @param preloadedStats    Stats already loaded async by the caller; becomes this
     *                          player's stats handle. Pass a fresh empty Stats for new runs.
     * @param preloadedTotalXp  Member experience pre-fetched from the store (async).
     */
    public void initForRun(Run newRun, String restoreFromBlob, Stats preloadedStats, long preloadedTotalXp) {
        this.activeRun = newRun;
        if (newRun != null) {
            this.raidManager = new RaidManager(newRun);
            this.stats = preloadedStats;
            this.cachedTotalXp = preloadedTotalXp;
            if (restoreFromBlob != null) InventoryBlob.deserializeInto(bukkit(), restoreFromBlob);
            applyDerivedStats();
            updateExperienceBar();
        } else {
            this.raidManager = null;
            this.stats = null;
            this.cachedTotalXp = 0L;
        }
    }

    @Override
    public UUID getOwnerUuid() { return getUUID(); }

    @Override
    public String getStatsPrefix() { return "member:" + getUUID(); }

    @Override
    public Stats getStats() {
        /* Returns the already-loaded Stats handle, or null if no run is active.
           Never allocates here — a new Stats would be empty until load() runs async,
           which would silently report zeros. Callers must tolerate null. */
        return stats;
    }

    public Difficulty getDifficulty() {
        return activeRun != null ? activeRun.getDifficulty() : Difficulty.NORMAL;
    }
}
