package com.orbitmines.minecraft.spigot.servers.fog.run;

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.chat.ActionBar;
import com.orbitmines.minecraft.spigot.servers.fog.FoG;
import com.orbitmines.minecraft.spigot.servers.fog.FoGPlayer;
import com.orbitmines.minecraft.spigot.servers.fog.database.FoGPlayerModel;
import com.orbitmines.minecraft.spigot.servers.fog.database.FoGRunModel;
import com.orbitmines.minecraft.spigot.servers.fog.structure.Compartment;
import com.orbitmines.minecraft.spigot.servers.fog.structure.CompartmentType;
import com.orbitmines.minecraft.spigot.servers.fog.world.CaveScorer;
import com.orbitmines.minecraft.spigot.servers.fog.world.CityWall;
import com.orbitmines.minecraft.spigot.servers.fog.world.FoGWorld;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Lifecycle of {@link Run} instances. Sync/async discipline:
 *
 * <ul>
 *   <li>DB reads/writes (RunStore, models) happen only on async threads.</li>
 *   <li>World block operations, teleports, inventory edits happen only on the main thread.</li>
 * </ul>
 *
 * All public methods here hide that split from callers.
 */
public class RunManager {

    private final FoG server;
    private final Map<Long, Run> loaded = new ConcurrentHashMap<>();

    public RunManager(FoG server) {
        this.server = server;
    }

    public Run get(long id) { return loaded.get(id); }

    public Run registerLoaded(Run run) {
        loaded.put(run.getId(), run);
        return run;
    }

    public Run runForModel(FoGRunModel model) {
        Run existing = loaded.get(model.getId());
        if (existing != null) return existing;
        return new Run(model);
    }

    /** Create a fresh run for the given player. Async cave-scoring, then teleport. */
    public void startNewRun(FoGPlayer owner, Difficulty difficulty) {
        final UUID ownerUuid = owner.getUUID();

        owner.sendMessage("FoG", Color.YELLOW, "fog", "run.preparing");
        new ActionBar(owner.bukkit(), () -> "§e§l" + owner.translate("fog", "run.preparing"), 20 * 60) {
            @Override public void onRun() {
                if (owner.getActiveRun() != null) forceStop();
            }
        }.send();

        /* 1) Async: insert run model to get an id, compute world file name. */
        server.runAsync(() -> {
            FoGRunModel model = new FoGRunModel(ownerUuid, difficulty.name(), "pending", RunState.PREPARING.name());
            model.insert();

            String worldFileName = ownerUuid.toString().toLowerCase() + "_fog_" + model.getId();
            model.setWorldFileName(worldFileName);
            model.update(FoGRunModel.column.WORLD_FILE_NAME);

            long seed = FoGWorld.randomSeed();

            /* 2) Sync: load the world. */
            server.runSync(() -> {
                World world = FoGWorld.createOrLoad(worldFileName, seed);
                if (world == null) {
                    owner.sendMessage("FoG", Color.RED, "fog", "run.generation_failed");
                    return;
                }

                /* 3) Async: cave-score (forces chunk gen, slow). */
                server.runAsync(() -> {
                    Random r = new Random(seed);
                    int startCx = r.nextInt(40) - 20;
                    int startCz = r.nextInt(40) - 20;
                    Location spawn = CaveScorer.findBestSpawn(world, startCx, startCz, 8);

                    /* 4) Sync: place blocks, teleport, give inventory, start level-up. */
                    server.runSync(() -> {
                        world.setSpawnLocation(spawn);
                        world.save();

                        CityWall.build(world, spawn.getBlockX(), spawn.getBlockY(), spawn.getBlockZ(), 24, 5);

                        int[] farmC   = Compartment.place(world, CompartmentType.FARM,          spawn.getBlockX() + 12, spawn.getBlockY(), spawn.getBlockZ() + 12);
                        int[] enchC   = Compartment.place(world, CompartmentType.ENCHANTING,    spawn.getBlockX() - 14, spawn.getBlockY(), spawn.getBlockZ() + 10);
                        int[] crateC  = Compartment.place(world, CompartmentType.CRATES,        spawn.getBlockX() + 14, spawn.getBlockY(), spawn.getBlockZ() - 10);
                        int[] droneFC = Compartment.place(world, CompartmentType.DRONE_FACTORY, spawn.getBlockX() - 12, spawn.getBlockY(), spawn.getBlockZ() - 12);

                        Run run = runForModel(model);
                        registerLoaded(run);

                        /* Fresh run — no prior data, so Stats stays all zeros; no load needed. */
                        com.orbitmines.minecraft.spigot.servers.fog.stats.Stats freshStats =
                                new com.orbitmines.minecraft.spigot.servers.fog.stats.Stats(
                                        run.getStore(), "member:" + ownerUuid);

                        /* Teleport FIRST so the downgrade to SURVIVAL + no-flight happens
                           on solid ground (avoids fall damage if the player is still at
                           the sky anchor when this fires). */
                        owner.teleport(spawn);
                        owner.bukkit().setInvulnerable(false);
                        owner.setGameMode(GameMode.SURVIVAL);
                        owner.setAllowFlight(false);
                        owner.setFlying(false);
                        owner.bukkit().setCollidable(true);

                        owner.initForRun(run, null, freshStats, 0L);
                        owner.giveRunStartInventory();
                        owner.sendMessage("FoG", Color.LIME, "fog", "run.ready");

                        /* Immediately confront the player with a level 0 choice. */
                        server.getLevelUpFlow().start(owner, 0, run);

                        /* 5) Async: persist all DB writes (compartments, membership, model state, player pointer). */
                        server.runAsync(() -> {
                            Compartment.persist(run.getStore(), CompartmentType.FARM,          farmC[0], farmC[1], farmC[2]);
                            Compartment.persist(run.getStore(), CompartmentType.ENCHANTING,    enchC[0], enchC[1], enchC[2]);
                            Compartment.persist(run.getStore(), CompartmentType.CRATES,        crateC[0], crateC[1], crateC[2]);
                            Compartment.persist(run.getStore(), CompartmentType.DRONE_FACTORY, droneFC[0], droneFC[1], droneFC[2]);

                            run.getStore().registerMember(ownerUuid);

                            model.setState(RunState.ACTIVE.name());
                            model.update(FoGRunModel.column.STATE);

                            owner.getPlayerModel().setActiveRunId(model.getId());
                            if (!owner.getPlayerModel().isInserted()) owner.getPlayerModel().insert();
                            else owner.getPlayerModel().update(FoGPlayerModel.column.ACTIVE_RUN_ID);
                        });
                    });
                });
            });
        });
    }

    /** Join a run as a full member. Restores the saved per-run inventory if any. */
    public void joinRun(FoGPlayer player, long runId) {
        server.runAsync(() -> {
            FoGRunModel model = FoGRunModel.findBy(FoGRunModel.class, FoGRunModel.column.ID.is(runId));
            if (model == null) {
                player.sendMessage("FoG", Color.RED, "fog", "run.not_found");
                return;
            }
            /* Read everything we need async first. */
            String blob = null;
            boolean firstTimeMember;
            Run run;
            synchronized (loaded) {
                run = runForModel(model);
            }
            /* Seed the run's in-memory uniques set from the store (async-only). */
            run.loadUniquesFromStore();
            firstTimeMember = !run.getStore().isMember(player.getUUID());
            run.getStore().registerMember(player.getUUID());
            blob = run.getStore().getMemberInventoryBlob(player.getUUID());

            /* Pre-load stats async — constructor does no DB, load() walks the store. */
            com.orbitmines.minecraft.spigot.servers.fog.stats.Stats stats =
                    new com.orbitmines.minecraft.spigot.servers.fog.stats.Stats(
                            run.getStore(), "member:" + player.getUUID());
            stats.load();
            long totalXp = run.getStore().getMemberExperience(player.getUUID());
            Integer pendingChoiceLevel = run.getStore().getPendingChoiceLevel(player.getUUID());

            player.getPlayerModel().setActiveRunId(run.getId());
            if (!player.getPlayerModel().isInserted()) player.getPlayerModel().insert();
            else player.getPlayerModel().update(FoGPlayerModel.column.ACTIVE_RUN_ID);

            final String fBlob = blob;
            final boolean fFirstTimeMember = firstTimeMember;
            final Run fRun = run;
            final com.orbitmines.minecraft.spigot.servers.fog.stats.Stats fStats = stats;
            final long fTotalXp = totalXp;
            final Integer fPendingChoiceLevel = pendingChoiceLevel;

            /* Now sync: load world, teleport, apply inventory. */
            server.runSync(() -> {
                World world = FoGWorld.createOrLoad(model.getWorldFileName(), 0L);
                if (world == null) {
                    player.sendMessage("FoG", Color.RED, "fog", "run.load_failed");
                    return;
                }
                registerLoaded(fRun);

                /* Initialise stats/xp first — sync-safe, no DB. */
                player.initForRun(fRun, fBlob, fStats, fTotalXp);

                if (fPendingChoiceLevel != null) {
                    /* Rejoin mid-prompt: elevate to sky above run spawn, grant
                       flight, open the prompt. When the player resolves it,
                       drop them back to the run spawn in SURVIVAL. */
                    openPendingChoiceInSky(player, fRun, fPendingChoiceLevel);
                    return;
                }

                /* Normal rejoin: teleport FIRST, then downgrade to SURVIVAL + no flight.
                   The onJoin safety net (ADVENTURE + flight + invulnerable) is cleared
                   here, once the player is on solid ground. */
                Location spawn = fRun.getSpawn();
                if (spawn != null) player.teleport(spawn);
                player.bukkit().setInvulnerable(false);
                player.setGameMode(GameMode.SURVIVAL);
                player.setAllowFlight(false);
                player.setFlying(false);
                player.bukkit().setCollidable(true);

                if (fFirstTimeMember && fBlob == null) {
                    player.giveRunStartInventory();
                } else {
                    /* Even on a returning member, make sure the always-on items are present. */
                    server.getLobbyKit().copyAlwaysOnToInventory(player);
                }
            });
        });
    }

    /** Teleport the player to the sky above their run's spawn and open the
        pending-choice prompt there. On resolve, drop them to the ground in
        SURVIVAL. */
    private void openPendingChoiceInSky(FoGPlayer player, Run run, int level) {
        Location runSpawn = run.getSpawn();
        if (runSpawn == null) return;
        Location sky = runSpawn.clone();
        sky.setY(Math.min(runSpawn.getWorld().getMaxHeight() - 10, runSpawn.getY() + 120));

        player.setGameMode(GameMode.ADVENTURE);
        player.teleport(sky);
        /* Flight setup deferred one tick so Bukkit's post-teleport state settles
           (matches the teleportToSkyAnchor pattern). */
        new org.bukkit.scheduler.BukkitRunnable() {
            @Override public void run() {
                if (!player.bukkit().isOnline()) return;
                player.setAllowFlight(true);
                try { player.setFlying(true); } catch (IllegalArgumentException ignored) {}
                server.getLobbyKit().copyAlwaysOnToInventory(player);
                server.getLevelUpFlow().start(player, level, run, () -> {
                    /* After the choice resolves, transition to normal play. */
                    Location ground = run.getSpawn();
                    if (ground != null) player.teleport(ground);
                    player.bukkit().setInvulnerable(false);
                    player.setGameMode(GameMode.SURVIVAL);
                    player.setAllowFlight(false);
                    player.setFlying(false);
                    player.bukkit().setCollidable(true);
                });
            }
        }.runTaskLater(server.getPlugin(), 1L);
    }

    /**
     * Spectate another player's run. Doesn't register the viewer as a member.
     *
     * <p>Uses ADVENTURE + flight (not GameMode.SPECTATOR) so the viewer's
     * top-right kit items remain right-clickable — essential for escaping the
     * spectate session without a relog. The viewer is set non-collidable so
     * they don't obstruct active players.</p>
     */
    public void spectateRun(FoGPlayer viewer, long runId) {
        server.runAsync(() -> {
            FoGRunModel model = FoGRunModel.findBy(FoGRunModel.class, FoGRunModel.column.ID.is(runId));
            if (model == null) {
                viewer.sendMessage("FoG", Color.RED, "fog", "run.not_found");
                return;
            }
            /* Construct + load uniques on the async side. */
            Run run;
            synchronized (loaded) {
                run = runForModel(model);
            }
            run.loadUniquesFromStore();
            final Run fRun = run;

            server.runSync(() -> {
                World world = FoGWorld.createOrLoad(model.getWorldFileName(), 0L);
                if (world == null) {
                    viewer.sendMessage("FoG", Color.RED, "fog", "run.load_failed");
                    return;
                }
                registerLoaded(fRun);
                Location spawn = fRun.getSpawn();
                if (spawn != null) viewer.teleport(spawn);
                viewer.bukkit().setInvulnerable(false);
                viewer.setGameMode(GameMode.ADVENTURE);
                viewer.setAllowFlight(true);
                viewer.setFlying(true);
                viewer.bukkit().setCollidable(false);
                /* Always-on kit must be present so the viewer can escape. */
                server.getLobbyKit().copyAlwaysOnToInventory(viewer);
            });
        });
    }
}
