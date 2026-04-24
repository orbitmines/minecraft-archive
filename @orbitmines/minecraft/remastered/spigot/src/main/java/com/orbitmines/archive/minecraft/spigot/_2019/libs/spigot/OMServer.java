package com.orbitmines.archive.minecraft.spigot._2019.libs.spigot;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft._2019.libs.Environment;
import com.orbitmines.archive.minecraft._2019.libs.Server;
import com.orbitmines.archive.minecraft._2019.libs.player.PlayerInstance;
import com.orbitmines.archive.minecraft._2019.utils.FileUtils;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.*;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.Command;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.database.models.LobbyPreference;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.database.models.OMMap;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.database.models.PlayerAchievement;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.database.models.kitpvp.KitPvPPlayerKitModel;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.database.models.kitpvp.KitPvPPlayerModel;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.database.models.survival.*;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.database.models.creative.CreativePlayerModel;
import com.orbitmines.archive.minecraft.spigot._2019.servers.creative.CreativeWorldMember;
import com.orbitmines.minecraft.spigot.servers.fog.database.FoGPlayerModel;
import com.orbitmines.minecraft.spigot.servers.fog.database.FoGRunModel;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.discord.SpigotDiscordBot;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.events.*;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.prevention.Prevention;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.exceptions.ChunkSaveOnRestartException;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.loot.LootHandler;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.pubsub.publishers.ServerClosePublisher;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.pubsub.publishers.ServerStartupPublisher;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.pubsub.subscribers.*;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.runnables.*;
import com.orbitmines.archive.minecraft._2019.utils.RandomUtils;
import com.orbitmines.archive.minecraft._2019.utils.SkinLibrary;
import com.orbitmines.archive.minecraft._2019.utils.database.DatabaseManager;
import com.orbitmines.archive.minecraft._2019.utils.database.SQLiteDatabase;
import com.orbitmines.archive.minecraft._2019.utils.database.exceptions.DatabaseConnectionException;
import com.orbitmines.archive.minecraft._2019.utils.pubsub.PubSubBroker;
import com.orbitmines.archive.minecraft._2019.utils.pubsub.SubscriberInstance;
import com.orbitmines.archive.minecraft._2019.utils.state.StateProvider;
import com.orbitmines.archive.minecraft._2019.utils.language.Language;
import com.orbitmines.archive.minecraft._2019.utils.list.ScrollerList;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.ReflectionUtils;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.config.ConfigHandler;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.guis.events.GUIEvents;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.item_handlers.events.ItemHandlerEvents;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.npcs.events.NpcEvents;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.placeholders.SpigotServer;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.pubsub.SpigotPubSubBroker;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.runnable.Interval;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.runnable.TimeUnit;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.state.SpigotStateCache;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.worlds.WorldLoader;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.worlds.maps.datapoints.DataPoint;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.worlds.maps.datapoints.DataPointHandler;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.worlds.maps.datapoints.DataPointSign;
import lombok.Getter;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.minecraft.server.MinecraftServer;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.GameRule;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.IllegalPluginAccessException;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class OMServer<S extends OMServer<S, P>, P extends OMPlayer<S, P>> extends SpigotServer<P> {

    public OMServer(JavaPlugin plugin) {
        super(plugin);
    }

    private boolean restarting = false;

    @Getter protected ConfigHandler configHandler;
    @Getter protected WorldLoader worldLoader;

    @Getter protected SpigotDiscordBot discordBot;

    public void discord(java.util.function.Consumer<SpigotDiscordBot> callback) {
        if (discordBot != null)
            callback.accept(discordBot);
    }
    @Getter protected SkinLibrary skinLibrary;

    @Getter private PatchNotes patchNotes;

    @Getter protected OMMap lobby;
    private final Map<String, OMMap> loadedCustomLobbies = new ConcurrentHashMap<>();
    private final Map<String, String> lobbyLastEdit = new ConcurrentHashMap<>();

    @Deprecated @Getter private final ScrollerList<String> scoreboardAnimation = new ScrollerList<>(
            "§8§lOrbit§7§lMines",
            "§7§lO§8§lrbit§7§lMines",
            "§7§lOr§8§lbit§7§lMines",
            "§7§lOrb§8§lit§7§lMines",
            "§7§lOrbi§8§lt§7§lMines",
            "§7§lOrbit§7§lMines",
            "§7§lOrbit§8§lM§7§lines",
            "§7§lOrbit§8§lMi§7§lnes",
            "§7§lOrbit§8§lMin§7§les",
            "§7§lOrbit§8§lMine§7§ls",
            "§7§lOrbit§8§lMines",

            "§8§lO§7§lrbit§8§lMines",
            "§8§lOr§7§lbit§8§lMines",
            "§8§lOrb§7§lit§8§lMines",
            "§8§lOrbi§7§lt§8§lMines",
            "§8§lOrbit§8§lMines",
            "§8§lOrbit§7§lM§8§lines",
            "§8§lOrbit§7§lMi§8§lnes",
            "§8§lOrbit§7§lMin§8§les",
            "§8§lOrbit§7§lMine§8§ls"
    );

    public abstract void afterStartupSync();

    protected abstract S instance();

    public abstract Server getType();

    public abstract ChatHandler newChatHandler(PlayerInstance sender, ChatHandler.Type type, String message);

    public abstract TabListHandler newTabListHandler(P player);

    public abstract LootHandler newLootHandler(P player);

    public abstract boolean clearPlayerData();

    public abstract boolean saveChunksOnRestart();

    public abstract boolean broadcastWhenSaving();

    public abstract boolean shouldSetupLobby();

    public abstract DataPointHandler createDataPointHandler(OMMap.Type type);

    public abstract Prevention[] getLobbyPreventions();

    public abstract void setupNpc(String string, Location location);

    protected abstract void instantiateLeaderBoards();

    /* Before Database is setup */
    @Override
    public void onStartup() {
        configHandler = new ConfigHandler(plugin);
        /* Setup Languages */
        Language.initialize(getPluginName(), true);

        worldLoader = new WorldLoader(Bukkit.getWorldContainer().getAbsolutePath() + "/worlds", clearPlayerData());
    }

    /* After Database is setup */
    @Override
    public void onStart() {
        if (restarting)
            return;

        /* Setup Plugin Messaging and State */
        setupPubSub();

        try {
            SQLiteDatabase database = DatabaseManager.getInstance().initializeDefaultDatabase();

            database.checkConnection();

            System.out.println("Successfully setup SQLite connection.");

            clearStaleState();
        } catch(DatabaseConnectionException ex) {
            restart("Could not connect to database, restarting... (Caused by: " + ex.getClass().getSimpleName() + ": " + ex.getCause().getMessage() + ")", true);
            return;
        } catch(Exception ex) {
            ex.printStackTrace();
        }

        try {
            getType().setStatus(Server.Status.RESTARTING);

            setupSkinLibrary();
            setupDiscordBot();
            setupPlan();

            /* Notify Bungeecord we are starting up */
            publishServerStartup();

            /* Discord Startup Message */
            discord(bot -> {
                EmbedBuilder builder = new EmbedBuilder();
                builder.setAuthor("Starting " + getType().getName() + "...");
                builder.setColor(Color.LIME.getAwtColor());
                bot.getTextChannel().sendMessageEmbeds(builder.build()).queue();
            });

            DatabaseManager.getInstance().setupDefaultDatabase(
                OMMap.TABLE,
                PlayerAchievement.TABLE,
                LobbyPreference.TABLE,

                /* Survival */
                SurvivalPlayerModel.TABLE,
                SurvivalClaim.TABLE,
                SurvivalClaimMember.TABLE,
                SurvivalChestShop.TABLE,
                SurvivalHome.TABLE,
                SurvivalWarp.TABLE,
                SurvivalFavoriteWarp.TABLE,
                SurvivalRegion.TABLE,

                /* KitPvP */
                KitPvPPlayerModel.TABLE,
                KitPvPPlayerKitModel.TABLE,

                /* Creative */
                CreativePlayerModel.TABLE,
                CreativeWorldMember.TABLE,

                /* FoG */
                FoGPlayerModel.TABLE,
                FoGRunModel.TABLE
            );

            /* Register to bungeecord startup subscriber first, in case it is starting up/down */
            registerSubscribers(
                new BungeeStartupSubscriber(this),
                new PlayerDiscordLinkSubscriber(this),
                new PlayerFriendsUpdateSubscriber(this),
                new PlayerNameChangeSubscriber(),
                new PlayerVoteSubscriber(this),
                new ServerRestartSubscriber(this),
                new PlayerDonationSubscriber(this)
            );

            new CommandTopVoters<>(instance());
            instantiateLeaderBoards();

            ReflectionUtils.setMaxCapacity(getType().getMaxPlayers());

            /* Setup Lobby */
            if (shouldSetupLobby()) {
                lobby = OMMap.getLobby(getType());

                File worldSourceDir = findWorldSource(lobby.getWorldFileName());
                if (worldSourceDir == null)
                    throw new IllegalStateException("Lobby world source not found: " + lobby.getWorldFileName());
                World lobbyWorld = worldLoader.fromDirectory(worldSourceDir, lobby.getWorldFileName(), true, lobby.getWorldGenerator());
                lobbyWorld.setGameRule(GameRule.ADVANCE_TIME, false);
                lobbyWorld.setGameRule(GameRule.SPAWN_MOBS, false);
                /* GameRule.DO_FIRE_TICK removed in 26.1 */
                lobbyWorld.setTime(18000); /* Midnight */
                lobbyWorld.setAutoSave(false);

                lobby.setup(lobbyWorld, createDataPointHandler(OMMap.Type.LOBBY));
            }

            registerEvents(
                newCommandEventsInstance(),
                new FinishedStartupEvent(this),
                new PlayerChatEvent<>(instance()),
                new PlayerJoinQuitEvents(),
                new NpcEvents<>(instance()),
                new ItemHandlerEvents<>(this),
                new GUIEvents<>(instance()),
                new AfkEvents<>(instance())
            );

            registerCommands(
                new CommandHelp<>(instance()),
                new CommandDiscord<>(instance()),
                new CommandHub<>(instance()),
                new CommandList<>(instance()),
                new CommandMsg<>(instance()),
                new CommandReply<>(instance()),
                new CommandServer<>(instance()),
                new CommandShop<>(instance()),
                new CommandWebsite<>(instance()),
                new CommandMotd<>(instance()),
                new CommandFind<>(instance()),
                new CommandPunish.Ban<>(instance()),
                new CommandPunish.Mute<>(instance()),
                new CommandPunish.Warn<>(instance()),
                new CommandPardon.Unban<>(instance()),
                new CommandPardon.Unmute<>(instance()),
                new CommandFriends<>(instance()),
                new CommandLoot<>(instance()),
                new CommandPrisms<>(instance()),
                new CommandSolars<>(instance()),
                new CommandSettings<>(instance()),
                new CommandStats<>(instance()),
                new CommandNickname<>(instance()),
                new CommandAfk<>(instance()),
                new CommandReport<>(instance()),
                new CommandServers<>(instance()),
                new CommandVote<>(instance()),
                new CommandDiscordLink<>(instance()),
                new CommandDiscordSquad<>(instance()),
                new CommandInvSee<>(instance()),
                new CommandRestart<>(instance()),
                new CommandOpMode<>(instance()),
                new CommandTeleportTo<>(instance())
            );

            afterStartupSync();

            /* Setup Async Checker&Querier -> We don't allow database queries on the main thread */
            DatabaseManager.getInstance().setAsyncChecker(() -> !Bukkit.isPrimaryThread());
            DatabaseManager.getInstance().setAsyncQuerier(this::runAsync);

            registerRunnables();

            runAsync(this::afterStartupAsync);
        } catch (Exception ex) {
            restart("Error while starting up: " + ex.getMessage(), true);
            ex.printStackTrace();
        }
    }

    public CommandEvents<S, P> newCommandEventsInstance() {
        return new CommandEvents<>(instance());
    }
    
    public void afterStartupAsync() {
        /* Setup Patch Notes */
        if (getType() != Server.CREATIVE) {
            patchNotes = new PatchNotes(this);
            patchNotes.build();
        }
    }

    @Override
    public void onStop() {
        /* Notify Bungeecord we are closing */
        try {
            publishServerClose();
        } catch (IllegalPluginAccessException ex) { /* TODO: Fix; Sometimes says it can't register task while disabled, we want it to publish though */
        }

        if (worldLoader != null)
            worldLoader.cleanUp();

        /* Discord Shutdown Message */
        discord(bot -> {
            EmbedBuilder builder = new EmbedBuilder();
            builder.setAuthor("Shutting down " + getType().getName() + "...");
            builder.setColor(Color.RED.getAwtColor());
            bot.getTextChannel().sendMessageEmbeds(builder.build()).queue();
        });

    }

    public void restart(String message, boolean fallBackToServer) {
        this.restarting = true;

        /* We're restarting, and running this sync, open up database connections in sync */
        DatabaseManager manager = DatabaseManager.getInstance();
        manager.setAsyncQuerier(null);
        manager.setAsyncChecker(null);

        try {
            System.out.println("-------------------------------------------------------");
            System.out.println(" ");
            System.out.println(message);
            System.out.println(" ");
            System.out.println("-------------------------------------------------------");

            if (broadcastWhenSaving()) {
                discord(bot -> {
                    EmbedBuilder builder = new EmbedBuilder();
                    builder.setAuthor("Saving " + getType().getName() + "...");
                    builder.setColor(Color.GRAY.getAwtColor());
                    bot.getTextChannel().sendMessageEmbeds(builder.build()).queue();
                });
            }

            getType().setStatus(Server.Status.RESTARTING);

            MinecraftServer server = MinecraftServer.getServer();
            if (!clearPlayerData()) {
                System.out.println("Saving Players....");
                server.getPlayerList().saveAll();
            }

            System.out.println("Kicking Players...");
            if (fallBackToServer) {
                try {
                    for (P player : getPlayers()) {
//                        player.fallback(true);
                        player.connect(Server.SURVIVAL, true);
                    }
                } catch (IllegalPluginAccessException ex) {
                }
            }

            if (saveChunksOnRestart()) {
                System.out.println("Saving Chunks...");
                boolean saved = server.saveAllChunks(true, false, true);

                if (!saved) /* Notify error tracker */
                    new ChunkSaveOnRestartException(message).printStackTrace();
            }

            /* Cleanup server stop */
            System.out.println("Cleaning up server...");
            onStop();
        } catch (Exception ex) {
            System.out.println(" ");
            System.out.println("An error occurred while restarting");
            System.out.println(" ");

            /* Anything in the restart clause failed, but we still want to restart, print the error and try to restart the server through the stop command */
            /* If for some reason, *sigh 1.14*, the server isn't able to properly stop, our server-restarter service will pick it up */
            ex.printStackTrace();
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "stop");
        }
    }

    public Server.Status getStatus() {
        if (restarting)
            return Server.Status.RESTARTING;

        return Server.Status.ONLINE; //TODO MAINTENANCE
    }

    /* Called Async */
    public void onStartupFinish() {
        if (Environment.isProduction())
            discord(bot -> bot.setStatus(OnlineStatus.ONLINE));

        getType().setStatus(Server.Status.ONLINE);
    }

    public void publishServerStartup() {
        new ServerStartupPublisher().publish(
            getType().getPluginName(),
            Environment.get("OM_IP", "x.x.x.x"),
            Environment.get("OM_PORT", 25565),
            getType().ordinal()
        );
    }

    public void publishServerClose() {
        new ServerClosePublisher().publish(
            getType().getPluginName()
        );
    }

    @Override
    public String getPluginName() {
        return getType().getPluginName();
    }

    protected void registerRunnables() {
        new ServerPingRunnable(this, getType()).async().start();
        new LeaderBoardRunnable(this).async().start(Interval.of(TimeUnit.SECOND, 1));
        new ScoreboardRunnable<>(instance()).async().start();
        new TpsAlertRunnable<>(instance()).async().start();
        new ServerSelectorRunnable<>(instance()).async().start();
    }

    private void registerCommands(Command<? extends OMServer<S, P>, P>... commands) {
        for (Command<? extends OMServer<S, P>, P> command : commands) {
            command.register();
        }
    }

    /*

        Custom Lobby

     */

    /** Load a custom lobby world. Must be called on the main thread. Returns the loaded world or null. */
    public World loadCustomLobbyWorld(OMMap map) {
        World existing = Bukkit.getWorld(map.getWorldFileName());
        if (existing != null)
            return existing;

        String worldFileName = map.getWorldFileName();
        File worldDir = new File(Bukkit.getWorldContainer().getAbsoluteFile(), worldFileName);

        /* Always delete and re-copy from source to get fresh state */
        File source = findWorldSource(worldFileName);
        if (source == null) {
            Bukkit.getLogger().warning("[lobby] Source not found for lobby world: " + worldFileName);
            return null;
        }

        /* Don't copy over the source itself (e.g. creative server where source == worldDir) */
        if (!source.getAbsolutePath().equals(worldDir.getAbsolutePath())) {
            try {
                if (worldDir.exists())
                    FileUtils.deleteDirectory(worldDir);

                Bukkit.getLogger().info("[lobby] Copying lobby world: " + source.getAbsolutePath() + " -> " + worldDir.getAbsolutePath());
                FileUtils.copyDirectory(source, worldDir);

                /* Remove old entity region files (same fix as creative worlds) */
                File entitiesDir = new File(worldDir, "entities");
                if (entitiesDir.isDirectory())
                    FileUtils.deleteDirectory(entitiesDir);

                /* Delete uid.dat so Minecraft generates a fresh unique world ID (avoids duplicate world rejection) */
                File uidFile = new File(worldDir, "uid.dat");
                if (uidFile.exists())
                    uidFile.delete();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        Bukkit.getLogger().info("[lobby] Loading world: " + worldFileName + " (dir exists: " + worldDir.exists() + ", generator: " + map.getWorldGenerator() + ")");
        World world = worldLoader.loadWorld(worldFileName, true, map.getWorldGenerator());
        if (world == null) {
            Bukkit.getLogger().warning("[lobby] worldLoader.loadWorld returned null for: " + worldFileName);
            return null;
        }

        world.setGameRule(GameRule.ADVANCE_TIME, false);
        world.setGameRule(GameRule.SPAWN_MOBS, false);
        world.setTime(18000);
        world.setAutoSave(false);

        map.setup(world, createDataPointHandler(OMMap.Type.LOBBY));

        /* Apply same preventions as the default lobby */
        Prevention[] preventions = getLobbyPreventions();
        if (preventions != null)
            Prevention.prevent(plugin, world, preventions);

        /* Register void fall respawn */
        registerEvents(new EnterVoidEvent<S, P>(instance(), world) {
            @Override
            public Location getRespawnLocation(P player) {
                return getSpawnFromLobbyMap(map);
            }
        });

        loadedCustomLobbies.put(worldFileName, map);
        return world;
    }

    /** Load and teleport a player to a custom lobby. */
    public void loadAndTeleportToLobby(P player, OMMap map) {
        /* Pre-fetch last_edit timestamp on async thread before loading the world on sync */
        String worldFileName = map.getWorldFileName();
        runAsync(() -> {
            String lastEdit = StateProvider.getInstance().getString("world:" + worldFileName + ":last_edit");
            runSync(() -> {
                World world = loadCustomLobbyWorld(map);
                if (world == null) {
                    player.sendMessage("spigot", "settings.lobby.load_failed");
                    player.teleport(getLobbySpawn());
                    return;
                }

                /* Cache the timestamp so staleness detection works for subsequent /spawn calls */
                if (lastEdit != null)
                    lobbyLastEdit.put(worldFileName, lastEdit);
                player.lobbyLastEdit = lastEdit;

                Location target = getSpawnFromLobbyMap(map);
                player.teleport(target);
            });
        });
    }

    /**
     * Teleport a player to their preferred lobby. Must be called on the main thread.
     * Uses the pre-resolved lobbyPreferenceMap from OMPlayer.onJoin.
     * @param onArrival optional callback run after teleport (e.g. give inventory)
     */
    public void teleportToPlayerLobby(P player, Runnable onArrival) {
        String worldFileName = player.getLobbyPreference() != null ? player.getLobbyPreference().getWorldFileName() : null;

        /* Prefer the cached map from loadedCustomLobbies (has correct world + datapoints),
           fall back to the DB-loaded lobbyPreferenceMap for first-time loading */
        OMMap map = worldFileName != null ? loadedCustomLobbies.get(worldFileName) : null;
        if (map == null)
            map = player.getLobbyPreferenceMap();

        if (map != null) {
            /* Check if the loaded lobby is stale (creative world was edited since last copy) */
            if (worldFileName != null && map.getWorld() != null) {
                String currentLastEdit = player.getLobbyLastEdit();
                String cachedLastEdit = lobbyLastEdit.get(worldFileName);
                if (currentLastEdit != null && !currentLastEdit.equals(cachedLastEdit)) {
                    Bukkit.getLogger().info("[lobby] World " + worldFileName + " was edited (cached=" + cachedLastEdit + ", current=" + currentLastEdit + "), reloading...");
                    /* Teleport all players in this world to default lobby first */
                    World staleWorld = map.getWorld();
                    for (Player p : staleWorld.getPlayers()) {
                        p.teleport(getLobbySpawn());
                    }
                    Bukkit.unloadWorld(staleWorld, false);
                    loadedCustomLobbies.remove(worldFileName);
                    map = player.getLobbyPreferenceMap() != null ? player.getLobbyPreferenceMap() : map;
                }
            }

            World world = map.getWorld();
            if (world == null) {
                world = loadCustomLobbyWorld(map);
                /* Cache the player's pre-fetched last_edit timestamp so we can detect staleness later */
                if (world != null && worldFileName != null && player.getLobbyLastEdit() != null)
                    lobbyLastEdit.put(worldFileName, player.getLobbyLastEdit());
            }

            if (world != null) {
                Location spawn = getSpawnFromLobbyMap(map);
                player.teleport(spawn);
            } else {
                player.teleport(getLobbySpawn());
            }
        } else {
            player.teleport(getLobbySpawn());
        }
        if (onArrival != null) onArrival.run();
    }

    /** Default lobby spawn location. */
    public Location getLobbySpawn() {
        if (lobby != null)
            return getSpawnFromLobbyMap(lobby);

        return Bukkit.getWorlds().get(0).getSpawnLocation();
    }

    /** Get a spawn location from a lobby map's datapoint spawns, falling back to world spawn. */
    public Location getSpawnFromLobbyMap(OMMap map) {
        World world = map.getWorld();
        /* Ensure we have the world reference even if the map wasn't setup yet */
        if (world == null)
            world = Bukkit.getWorld(map.getWorldFileName());
        DataPointHandler handler = map.getDataPointHandler();
        if (handler != null) {
            for (List<DataPoint> points : handler.getAsMap().values()) {
                for (DataPoint point : points) {
                    if (point instanceof DataPointSign) {
                        List<Location> spawns = ((DataPointSign) point).getSpawns();
                        if (!spawns.isEmpty()) {
                            Location spawn = RandomUtils.randomFrom(spawns);
                            /* Ensure the location references the correct world (same fix as creative) */
                            if (world != null)
                                return new Location(world, spawn.getX(), spawn.getY(), spawn.getZ(), spawn.getYaw(), spawn.getPitch());
                            return spawn;
                        }
                    }
                }
            }
        }

        if (world != null) {
            Location spawn = world.getSpawnLocation();
            return new Location(world, spawn.getX(), spawn.getY(), spawn.getZ(), spawn.getYaw(), spawn.getPitch());
        }

        return Bukkit.getWorlds().get(0).getSpawnLocation();
    }

    /** Unload custom lobby worlds that have no players in them. */
    public void unloadEmptyCustomLobbies() {
        loadedCustomLobbies.entrySet().removeIf(entry -> {
            OMMap map = entry.getValue();
            World world = map.getWorld();
            if (world == null || world.getPlayers().isEmpty()) {
                if (world != null)
                    Bukkit.unloadWorld(world, false);
                lobbyLastEdit.remove(entry.getKey());
                return true;
            }
            return false;
        });
    }

    /**
     * Find the source directory for a world, checking creative server first, then archive.
     */
    public File findWorldSource(String worldFileName) {
        String root = Environment.get("OM_ROOT", ".");

        /* Creative server world container — world file names are always lowercase */
        File creativeDir = new File(root, ".orbitmines/servers/creative/" + worldFileName.toLowerCase());
        if (creativeDir.isDirectory())
            return creativeDir;

        /* Check archive (exact match first) */
        File archiveDir = new File(root, "@orbitmines/minecraft/archive/worlds/" + worldFileName);
        if (archiveDir.isDirectory())
            return archiveDir;

        /* Case-insensitive lookup in archive (archive dirs are mixed case) */
        File archiveParent = new File(root, "@orbitmines/minecraft/archive/worlds");
        if (archiveParent.isDirectory()) {
            File[] dirs = archiveParent.listFiles(File::isDirectory);
            if (dirs != null) {
                for (File dir : dirs) {
                    if (dir.getName().equalsIgnoreCase(worldFileName))
                        return dir;
                }
            }
        }

        return null;
    }

    /*

        Broadcast

     */

    public void broadcast(String namespace, String key) {
        for (P player : players.values()) {
            player.sendMessage(namespace, key);
        }
    }

    public void broadcast(String namespace, String key, Object... args) {
        for (P player : players.values()) {
            player.sendMessage(namespace, key, args);
        }
    }

    public void broadcastRaw(String message) {
        for (P player : players.values()) {
            player.sendRawMessage(message);
        }
    }

    public void broadcastRaw(String prefix, Color color, String message) {
        for (P player : players.values()) {
            player.sendRawMessage(prefix, color, message);
        }
    }

    public  void broadcast(String prefix, Color color, String key) {
        for (P player : players.values()) {
            player.sendMessage(prefix, color, key);
        }
    }

    public void broadcast(String prefix, Color color, String key, Object... args) {
        for (P player : players.values()) {
            player.sendMessage(prefix, color, key, args);
        }
    }

    public void broadcast(String prefix, Color color, String namespace, String key) {
        for (P player : players.values()) {
            player.sendMessage(prefix, color, namespace, key);
        }
    }

    public void broadcast(String prefix, Color color, String namespace, String key, Object... args) {
        for (P player : players.values()) {
            player.sendMessage(prefix, color, namespace, key, args);
        }
    }
    
    public void broadcast(BroadcastExclusion<P> exclusion, String namespace, String key) {
        for (P player : players.values()) {
            if (!exclusion.includes(player))
                continue;
            
            player.sendMessage(namespace, key);
        }
    }

    public void broadcast(BroadcastExclusion<P> exclusion, String namespace, String key, Object... args) {
        for (P player : players.values()) {
            if (!exclusion.includes(player))
                continue;

            player.sendMessage(namespace, key, args);
        }
    }

    public void broadcastRaw(BroadcastExclusion<P> exclusion, String prefix, Color color, String message) {
        for (P player : players.values()) {
            if (!exclusion.includes(player))
                continue;

            player.sendRawMessage(prefix, color, message);
        }
    }

    public  void broadcast(BroadcastExclusion<P> exclusion, String prefix, Color color, String key) {
        for (P player : players.values()) {
            if (!exclusion.includes(player))
                continue;

            player.sendMessage(prefix, color, key);
        }
    }

    public void broadcast(BroadcastExclusion<P> exclusion, String prefix, Color color, String key, Object... args) {
        for (P player : players.values()) {
            if (!exclusion.includes(player))
                continue;

            player.sendMessage(prefix, color, key, args);
        }
    }

    public void broadcast(BroadcastExclusion<P> exclusion, String prefix, Color color, String namespace, String key) {
        for (P player : players.values()) {
            if (!exclusion.includes(player))
                continue;

            player.sendMessage(prefix, color, namespace, key);
        }
    }

    public void broadcast(BroadcastExclusion<P> exclusion, String prefix, Color color, String namespace, String key, Object... args) {
        for (P player : players.values()) {
            if (!exclusion.includes(player))
                continue;

            player.sendMessage(prefix, color, namespace, key, args);
        }
    }

    /*

        Setup

     */

    protected void registerSubscribers(SubscriberInstance... subscribers) {
        for (SubscriberInstance subscriber : subscribers) {
            subscriber.subscribe();
        }
    }

    private void setupPubSub() {
        /* Initialize state cache */
        SpigotStateCache stateCache = new SpigotStateCache();
        StateProvider.initialize(stateCache);

        /* Initialize plugin messaging broker */
        SpigotPubSubBroker broker = new SpigotPubSubBroker(plugin);
        PubSubBroker.initialize(broker);

        /* Register state sync handler to receive state updates from BungeeCord */
        stateCache.registerStateSyncHandler();

        System.out.println("Successfully setup plugin messaging.");
    }

    /* Called after database is initialized to clear stale state */
    private void clearStaleState() {
        StateProvider.getInstance().clearServerPlayers(getType().getPluginName());
    }

    private void setupSkinLibrary() {
        String root = System.getProperty("OM_ROOT", ".");
        skinLibrary = new SkinLibrary(root + "/.orbitmines/skins") {
            @Override
            protected void updateLibraryAsync(Runnable runnable) {
                Bukkit.getScheduler().runTaskAsynchronously(plugin, runnable);
            }
        };
    }

    private void setupDiscordBot() {
        String token = Environment.get("OM_DISCORD_BOT_TOKEN", null);

        if (token == null)
            return;

        String serverId = Environment.get("OM_DISCORD_SERVER_ID", "473472016092233746");

        discordBot = new SpigotDiscordBot(token, serverId, this, skinLibrary);
        discordBot.initialize(Environment.isProduction() ? OnlineStatus.IDLE : OnlineStatus.ONLINE);
    }

    private void setupPlan() {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "plan m setup " + Environment.get("PLAN_URL", "http://bungeecord:8804"));
    }

    @FunctionalInterface
    public interface BroadcastExclusion<P> {

        boolean includes(P player);

    }
}
