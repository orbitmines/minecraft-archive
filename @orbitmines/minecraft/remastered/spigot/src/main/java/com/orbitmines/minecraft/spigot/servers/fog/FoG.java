package com.orbitmines.minecraft.spigot.servers.fog;

import com.orbitmines.archive.minecraft._2019.libs.Server;
import com.orbitmines.archive.minecraft._2019.libs.player.PlayerInstance;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.ChatHandler;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMServer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.TabListHandler;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.Command;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.database.models.OMMap;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.events.CommandEvents;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.loot.LootHandler;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.freezer.events.FreezeEvents;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.prevention.Prevention;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.worlds.maps.datapoints.DataPointHandler;
import com.orbitmines.minecraft.spigot.servers.fog.ability.AbilityRegistry;
import com.orbitmines.minecraft.spigot.servers.fog.commands.CommandChoice;
import com.orbitmines.minecraft.spigot.servers.fog.commands.CommandCodex;
import com.orbitmines.minecraft.spigot.servers.fog.commands.CommandCoopAccept;
import com.orbitmines.minecraft.spigot.servers.fog.commands.CommandFactory;
import com.orbitmines.minecraft.spigot.servers.fog.commands.CommandRun;
import com.orbitmines.minecraft.spigot.servers.fog.commands.CommandShop;
import com.orbitmines.minecraft.spigot.servers.fog.commands.CommandWorld;
import com.orbitmines.minecraft.spigot.servers.fog.events.BlockBreakListener;
import com.orbitmines.minecraft.spigot.servers.fog.events.DamageListener;
import com.orbitmines.minecraft.spigot.servers.fog.events.DeathListener;
import com.orbitmines.minecraft.spigot.servers.fog.events.ExperienceSuppressor;
import com.orbitmines.minecraft.spigot.servers.fog.events.FoGCommandEvents;
import com.orbitmines.minecraft.spigot.servers.fog.events.ItemDropListener;
import com.orbitmines.minecraft.spigot.servers.fog.events.LockedSlotsListener;
import com.orbitmines.minecraft.spigot.servers.fog.kit.FoGLobbyKit;
import com.orbitmines.minecraft.spigot.servers.fog.level.LevelUpFlow;
import com.orbitmines.minecraft.spigot.servers.fog.raid.RaidManager;
import com.orbitmines.minecraft.spigot.servers.fog.run.Run;
import com.orbitmines.minecraft.spigot.servers.fog.run.RunManager;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class FoG extends OMServer<FoG, FoGPlayer> {

    @Getter private RunManager runManager;
    @Getter private LevelUpFlow levelUpFlow;
    @Getter private AbilityRegistry abilityRegistry;
    @Getter private FoGLobbyKit lobbyKit;

    public FoG(JavaPlugin plugin) {
        super(plugin);
    }

    @Override
    public FoGPlayer newPlayerInstance(org.bukkit.entity.Player player) {
        return new FoGPlayer(player, this);
    }

    @Override
    public void afterStartupSync() {
        this.runManager = new RunManager(this);
        this.levelUpFlow = new LevelUpFlow(this);
        this.abilityRegistry = new AbilityRegistry();
        this.lobbyKit = new FoGLobbyKit(this);

        /* Hologram tag setup + sweep any worlds already loaded at startup. */
        com.orbitmines.minecraft.spigot.servers.fog.util.HologramTag.init(plugin);
        int removed = com.orbitmines.minecraft.spigot.servers.fog.util.HologramTag.cleanupAllLoaded();
        if (removed > 0) {
            org.bukkit.Bukkit.getLogger().info("[fog] Startup swept " + removed + " stale hologram entities across loaded worlds.");
        }

        registerCommands(
            new CommandRun(this),
            new CommandWorld(this),
            new CommandShop(this),
            new CommandCodex(this),
            new CommandFactory(this),
            new CommandChoice(this),
            new CommandCoopAccept(this)
        );

        registerEvents(
            new ExperienceSuppressor(this),
            new DeathListener(this),
            new DamageListener(this),
            new BlockBreakListener(this),
            new ItemDropListener(this),
            new LockedSlotsListener(this),
            new FreezeEvents(this),
            new com.orbitmines.minecraft.spigot.servers.fog.events.HologramCleanupListener(this),
            new com.orbitmines.minecraft.spigot.servers.fog.events.RunGUICloseListener(this)
        );

        /* Per-run raid + mob ticking */
        new BukkitRunnable() {
            @Override
            public void run() {
                for (FoGPlayer player : getPlayers()) {
                    Run run = player.getActiveRun();
                    if (run == null) continue;
                    RaidManager rm = player.getRaidManager();
                    if (rm != null) rm.tick();
                }
            }
        }.runTaskTimer(plugin, 20L, 20L);

        /* Periodic stats flush for all online players (async thread) */
        new BukkitRunnable() {
            @Override
            public void run() {
                for (FoGPlayer player : getPlayers()) {
                    com.orbitmines.minecraft.spigot.servers.fog.stats.Stats s = player.getStats();
                    if (s == null) continue;
                    try { s.flush(); } catch (Exception ignored) {}
                }
            }
        }.runTaskTimerAsynchronously(plugin, 20L * 30, 20L * 30);
    }

    @Override
    public CommandEvents<FoG, FoGPlayer> newCommandEventsInstance() {
        return new FoGCommandEvents(this);
    }

    /**
     * Skip the base class's {@code PatchNotes.build()} — FoG has no patch notes
     * registered yet, and the default builder NPEs on {@code getLatest(FOG)}.
     */
    @Override
    public void afterStartupAsync() {
        /* intentionally empty */
    }

    private void registerCommands(Command<FoG, FoGPlayer>... commands) {
        for (Command<FoG, FoGPlayer> command : commands) {
            command.register();
        }
    }

    /**
     * Creative-style elevated spawn for players who have no active run yet, so
     * the run selector holograms are visible in open air. Uses the default
     * world, positions the player high above its spawn, and enables flight.
     */
    public void teleportToSkyAnchor(FoGPlayer player) {
        org.bukkit.World world = org.bukkit.Bukkit.getWorlds().get(0);
        org.bukkit.Location spawn = world.getSpawnLocation();
        org.bukkit.Location anchor = new org.bukkit.Location(world, spawn.getX(), 200, spawn.getZ(), 0f, 0f);
        player.setGameMode(org.bukkit.GameMode.ADVENTURE);
        player.teleport(anchor);
        /* Enable flight one tick later — Bukkit may reset allow-flight during a
           cross-world teleport, and calling setFlying(true) before that settles
           throws IllegalArgumentException. Also clear the onJoin invulnerability
           flag once the player is safely in the sky with flight. */
        new org.bukkit.scheduler.BukkitRunnable() {
            @Override public void run() {
                if (!player.bukkit().isOnline()) return;
                player.setAllowFlight(true);
                try {
                    player.setFlying(true);
                } catch (IllegalArgumentException ignored) {
                    /* Gamemode changed between the teleport and this tick — accept it. */
                }
                player.bukkit().setInvulnerable(false);
            }
        }.runTaskLater(plugin, 1L);
    }

    @Override
    public Server getType() {
        return Server.FOG;
    }

    @Override
    protected FoG instance() {
        return this;
    }

    @Override
    public ChatHandler newChatHandler(PlayerInstance sender, ChatHandler.Type type, String message) {
        return new FoGChatHandler(this, type, sender, message);
    }

    @Override
    public TabListHandler newTabListHandler(FoGPlayer player) {
        return new TabListHandler<>(this, player);
    }

    @Override
    public LootHandler newLootHandler(FoGPlayer player) {
        return new FoGLootHandler(player);
    }

    @Override
    public boolean clearPlayerData() { return false; }

    @Override
    public boolean saveChunksOnRestart() { return true; }

    @Override
    public boolean broadcastWhenSaving() { return true; }

    @Override
    public boolean shouldSetupLobby() { return false; }

    @Override
    public DataPointHandler createDataPointHandler(OMMap.Type type) { return null; }

    @Override
    public Prevention[] getLobbyPreventions() { return new Prevention[0]; }

    @Override
    public void setupNpc(String string, Location location) { }

    @Override
    protected void instantiateLeaderBoards() { }
}
