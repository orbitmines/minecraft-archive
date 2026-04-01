package com.orbitmines.archive.minecraft.spigot._2019.libs.spigot;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft._2019.libs.Environment;
import com.orbitmines.archive.minecraft._2019.libs.Server;
import com.orbitmines.archive.minecraft._2019.libs.player.PlayerInstance;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.*;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.Command;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.database.models.OMMap;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.database.models.PlayerAchievement;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.database.models.kitpvp.KitPvPPlayerKitModel;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.database.models.kitpvp.KitPvPPlayerModel;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.database.models.survival.*;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.discord.SpigotDiscordBot;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.events.*;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.exceptions.ChunkSaveOnRestartException;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.loot.LootHandler;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.pubsub.publishers.ServerClosePublisher;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.pubsub.publishers.ServerStartupPublisher;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.pubsub.subscribers.*;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.runnables.*;
import com.orbitmines.archive.minecraft._2019.utils.SkinLibrary;
import com.orbitmines.archive.minecraft._2019.utils.database.DatabaseManager;
import com.orbitmines.archive.minecraft._2019.utils.database.MySQLDatabase;
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
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.worlds.maps.datapoints.DataPointHandler;
import lombok.Getter;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.minecraft.server.MinecraftServer;
import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.plugin.IllegalPluginAccessException;

public abstract class OMServer<S extends OMServer<S, P>, P extends OMPlayer<S, P>> extends SpigotServer<P> {

    private boolean restarting = false;

    @Getter protected ConfigHandler configHandler;
    @Getter protected WorldLoader worldLoader;

    @Getter protected SpigotDiscordBot discordBot;
    @Getter protected SkinLibrary skinLibrary;

    @Getter private PatchNotes patchNotes;

    @Getter protected OMMap lobby;

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

    public abstract void setupNpc(String string, Location location);

    protected abstract void instantiateLeaderBoards();

    /* Before Database is setup */
    @Override
    public void onStartup() {
        configHandler = new ConfigHandler(this);
        /* Setup Languages */
        Language.initialize(getPluginName(), true);

        worldLoader = new WorldLoader(this.getServer().getWorldContainer().getAbsolutePath() + "/worlds", clearPlayerData());
    }

    /* After Database is setup */
    @Override
    public void onStart() {
        if (restarting)
            return;

        /* Setup Plugin Messaging and State */
        setupPubSub();

        try {
            MySQLDatabase database = DatabaseManager.getInstance().initializeDefaultDatabase();

            database.checkConnection();

            System.out.println("Successfully setup MySQL connection.");
        } catch(DatabaseConnectionException ex) {
            restart("Could not connect to mysql, restarting... (Caused by: " + ex.getClass().getSimpleName() + ": " + ex.getCause().getMessage() + ")", true);
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
            EmbedBuilder builder = new EmbedBuilder();
            builder.setAuthor("Starting " + getType().getName() + "...");
            builder.setColor(Color.LIME.getAwtColor());
            getDiscordBot().getTextChannel().sendMessageEmbeds(builder.build()).queue();

            DatabaseManager.getInstance().setupDefaultDatabase(
                OMMap.TABLE,
                PlayerAchievement.TABLE,

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
                KitPvPPlayerKitModel.TABLE
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

                World lobbyWorld = worldLoader.fromZip(lobby.getWorldFileName(), true, lobby.getWorldGenerator());
                lobbyWorld.setGameRule(GameRule.ADVANCE_TIME, false);
                lobbyWorld.setGameRule(GameRule.SPAWN_MOBS, false);
                /* GameRule.DO_FIRE_TICK removed in 26.1 */
                lobbyWorld.setTime(18000);
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
        if (getType() != Server.BUILD) {
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
        EmbedBuilder builder = new EmbedBuilder();
        builder.setAuthor("Shutting down " + getType().getName() + "...");
        builder.setColor(Color.RED.getAwtColor());
        getDiscordBot().getTextChannel().sendMessageEmbeds(builder.build()).queue();

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
                EmbedBuilder builder = new EmbedBuilder();
                builder.setAuthor("Saving " + getType().getName() + "...");
                builder.setColor(Color.GRAY.getAwtColor());
                getDiscordBot().getTextChannel().sendMessageEmbeds(builder.build()).queue();
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
            discordBot.setStatus(OnlineStatus.ONLINE);

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
        SpigotPubSubBroker broker = new SpigotPubSubBroker(this);
        PubSubBroker.initialize(broker);

        /* Register state sync handler to receive state updates from BungeeCord */
        stateCache.registerStateSyncHandler();

        /* Clear local server player data */
        stateCache.clearServerPlayers(getType().getPluginName());

        System.out.println("Successfully setup plugin messaging.");
    }

    private void setupSkinLibrary() {
        skinLibrary = new SkinLibrary(this.getServer().getWorldContainer().getAbsolutePath() + "/skins") {
            @Override
            protected void updateLibraryAsync(Runnable runnable) {
                getServer().getScheduler().runTaskAsynchronously(OMServer.this, runnable);
            }
        };
    }

    private void setupDiscordBot() {
        String token = Environment.get("OM_DISCORD_BOT_TOKEN", null);

        if (token == null)
            return;

        String serverId = Environment.get("OM_DISCORD_SERVER_ID", "473472016092233746");

        discordBot = new SpigotDiscordBot(token, serverId, this, this.skinLibrary);
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
