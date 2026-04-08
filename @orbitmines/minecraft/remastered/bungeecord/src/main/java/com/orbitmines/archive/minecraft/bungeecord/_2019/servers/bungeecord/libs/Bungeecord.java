package com.orbitmines.archive.minecraft.bungeecord._2019.servers.bungeecord.libs;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.google.common.collect.ImmutableMap;
import com.google.common.io.ByteStreams;
import com.google.common.io.Files;
import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft._2019.libs.Environment;
import com.orbitmines.archive.minecraft._2019.libs.Server;
import com.orbitmines.archive.minecraft._2019.libs.database.models.IPEntry;
import com.orbitmines.archive.minecraft._2019.libs.player.Name;
import com.orbitmines.archive.minecraft.bungeecord._2019.servers.bungeecord.libs.commands.CommandDonation;
import com.orbitmines.archive.minecraft.bungeecord._2019.servers.bungeecord.libs.discord.BungeeDiscordBot;
import com.orbitmines.archive.minecraft.bungeecord._2019.servers.bungeecord.libs.events.BungeePlayerEvents;
import com.orbitmines.archive.minecraft.bungeecord._2019.servers.bungeecord.libs.events.PingEvent;
import com.orbitmines.archive.minecraft.bungeecord._2019.servers.bungeecord.libs.events.PlayerChatEvent;
import com.orbitmines.archive.minecraft.bungeecord._2019.servers.bungeecord.libs.events.VoteEvent;
import com.orbitmines.archive.minecraft.bungeecord._2019.servers.bungeecord.libs.pubsub.publishers.BungeeStartupPublisher;
import com.orbitmines.archive.minecraft.bungeecord._2019.servers.bungeecord.libs.pubsub.subscribers.*;
import com.orbitmines.archive.minecraft.bungeecord._2019.servers.bungeecord.libs.error_tracker.ErrorTracker;
import com.orbitmines.archive.minecraft.bungeecord._2019.servers.bungeecord.libs.restarter.Restarter;
import com.orbitmines.archive.minecraft.bungeecord._2019.servers.bungeecord.utils.config.ConfigHandler;
import com.orbitmines.archive.minecraft._2019.utils.SkinLibrary;
import com.orbitmines.archive.minecraft._2019.utils.database.DatabaseManager;
import com.orbitmines.archive.minecraft._2019.utils.database.MySQLDumpImporter;
import com.orbitmines.archive.minecraft._2019.utils.database.WorldSeeder;
import com.orbitmines.archive.minecraft._2019.utils.database.SQLiteDatabase;
import com.orbitmines.archive.minecraft._2019.utils.database.exceptions.DatabaseConnectionException;
import com.orbitmines.archive.minecraft._2019.utils.pubsub.PubSubBroker;
import com.orbitmines.archive.minecraft._2019.utils.pubsub.SubscriberInstance;
import com.orbitmines.archive.minecraft._2019.utils.state.StateProvider;
import com.orbitmines.archive.minecraft._2019.utils.language.Language;
import com.orbitmines.archive.minecraft.bungeecord._2019.servers.bungeecord.utils.pubsub.BungeePubSubBroker;
import com.orbitmines.archive.minecraft.bungeecord._2019.servers.bungeecord.utils.state.BungeeStateManager;
import com.vexsoftware.votifier.VoteHandler;
import com.vexsoftware.votifier.VotifierPlugin;
import com.vexsoftware.votifier.bungee.events.VotifierEvent;
import com.vexsoftware.votifier.bungee.forwarding.ForwardingVoteSource;
import com.vexsoftware.votifier.bungee.forwarding.OnlineForwardPluginMessagingForwardingSource;
import com.vexsoftware.votifier.bungee.forwarding.PluginMessagingForwardingSource;
import com.vexsoftware.votifier.bungee.forwarding.cache.FileVoteCache;
import com.vexsoftware.votifier.bungee.forwarding.cache.MemoryVoteCache;
import com.vexsoftware.votifier.bungee.forwarding.cache.VoteCache;
import com.vexsoftware.votifier.bungee.forwarding.proxy.ProxyForwardingVoteSource;
import com.vexsoftware.votifier.model.Vote;
import com.vexsoftware.votifier.net.VotifierSession;
import com.vexsoftware.votifier.net.protocol.VoteInboundHandler;
import com.vexsoftware.votifier.net.protocol.VotifierGreetingHandler;
import com.vexsoftware.votifier.net.protocol.VotifierProtocolDifferentiator;
import com.vexsoftware.votifier.net.protocol.v1crypto.RSAIO;
import com.vexsoftware.votifier.net.protocol.v1crypto.RSAKeygen;
import com.vexsoftware.votifier.util.KeyCreator;
import com.vexsoftware.votifier.util.TokenUtil;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.Getter;
import net.dv8tion.jda.api.OnlineStatus;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.ReconnectHandler;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.KeyPair;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.logging.Level;

public class Bungeecord implements VoteHandler, VotifierPlugin {

    @Getter private static Bungeecord instance;

    @Getter private final Plugin plugin;

    private boolean restarting = false;
    private String restartingMessage = null;

    @Getter private ConfigHandler configHandler;
    @Getter private BungeeDiscordBot discordBot;

    public void discord(java.util.function.Consumer<BungeeDiscordBot> callback) {
        if (discordBot != null)
            callback.accept(discordBot);
    }
    @Getter private SkinLibrary skinLibrary;
    @Getter private BungeeStateManager stateManager;
    @Getter private ErrorTracker errorTracker;

    protected Map<UUID, BungeePlayer> players;

    public Bungeecord(Plugin plugin) {
        this.plugin = plugin;
    }

    /* Convenience delegates to plugin */
    public ProxyServer getProxy() { return plugin.getProxy(); }
    public java.util.logging.Logger getLogger() { return plugin.getLogger(); }
    public File getDataFolder() { return plugin.getDataFolder(); }
    public java.io.InputStream getResourceAsStream(String name) { return plugin.getResourceAsStream(name); }

    public void onLoad() {
        instance = this;

        configHandler = new ConfigHandler(plugin);
        /* Setup Languages */
        Language.initialize("bungeecord", true);

        this.players = new HashMap<>();
    }

    public void onEnable() {
        /* Setup Plugin Messaging and State */
        setupPubSub();

        String root = Environment.get("OM_ROOT", ".");
        String dbPath = Environment.get("OM_DB_PATH", root + "/.orbitmines/database/current");
        boolean firstLoad = !java.nio.file.Files.exists(java.nio.file.Path.of(dbPath));

        try {
            SQLiteDatabase database = DatabaseManager.getInstance().initializeDefaultDatabase();

            database.checkConnection();

            getLogger().info("Successfully setup SQLite connection.");

            if (firstLoad) {
                importDump(database);
                clearSurvivalData(database);
                seedMaps(database, root);
            }

            /* Clean up vote entries with timestamps in the future (caused by millisecond vs second bugs) */
            database.executeQuery("DELETE FROM `last_votes` WHERE `last_vote_at` > datetime('now')");
        } catch(DatabaseConnectionException ex) {
            getLogger().severe("Failed to setup SQLite connection.");
            restart("Could not connect to database, restarting... (Caused by: " + ex.getClass().getSimpleName() + ": " + ex.getCause().getMessage() + ")");
            return;
        } catch(Exception ex) {
            ex.printStackTrace();
        }

        setupSkinLibrary();
        setupDiscordBot();
        setupPlan();

        /* Cleanup Server Configuration */
        getProxy().getServers().clear();

        DatabaseManager.getInstance().setupDefaultDatabase();

        registerEvents(
            new PlayerChatEvent(this),
            new BungeePlayerEvents(this),
            new PingEvent(),
            new VoteEvent(this)
        );

        getProxy().setReconnectHandler(new ReconnectHandler() {
            @Override
            public ServerInfo getServer(ProxiedPlayer proxiedPlayer) {
                BungeePlayer player = getPlayer(proxiedPlayer);
                // TODO getting initial server on connection
                ServerInfo info = player.getFallbackServer();

                if (info == null)
                    player.disconnect(new TextComponent(
                        "§8§lOrbit§7§lMines\n" +
                        "§7" + IPEntry.ProtocolVersion.humanReadableVersion(IPEntry.ProtocolVersion.FIRST_SUPPORTED_VERSION) + "\n" +
                        "\n" +
                        "§7" + player.translate("bungeecord", "connection.no_fallback_server")
                    ));

                return info;
            }

            @Override
            public void setServer(ProxiedPlayer proxiedPlayer) {
                // TODO Saving last server
            }

            @Override
            public void save() {
                // TODO Save pending setServer
            }

            @Override
            public void close() {
                // TODO on stop
            }
        });

        registerSubscribers(
            new PlayerServerConnectSubscriber(this),
            new PlayerVipGainedSubscriber(this),
            new PlayerKickSubscriber(this),
            new ServerStartupSubscriber(this),
            new ServerCloseSubscriber(this),
            new PlayerBanSubscriber(this),
            new PlayerMessageSubscriber(this),
            new PlayerLanguageChangeSubscriber(this),
            new PlayerNicknameChangeSubscriber(this)
        );

        /* Console Commands */
        getProxy().getPluginManager().registerCommand(plugin, new CommandDonation(this));

        /* Notify Servers we have started up (After ServerStartupSubscriber has been initialized) */
        publishBungeeStartup();

        setupVotifier();

//        new Restarter(plugin, this).async().start();

        errorTracker = new ErrorTracker(this);
        errorTracker.start();
    }

    public void onDisable() {
        if (errorTracker != null)
            errorTracker.stop();

        if (restarting) {
            getLogger().info("-------------------------------------------------------");
            getLogger().info(restartingMessage);
            getLogger().info("-------------------------------------------------------");
            return;
        }
    }

    public void restart(String message) {
        this.restarting = true;
        this.restartingMessage = message;

        getProxy().stop();
    }

    public void publishBungeeStartup() {
        new BungeeStartupPublisher().publish(
            Environment.get("OM_IP", "x.x.x.x"),
            Environment.get("OM_PORT", 25565)
        );
    }

    public ServerInfo getServerInfo(Server server) {
        return getProxy().getServerInfo(server.getPluginName());
    }

    public Server getServer(ServerInfo serverInfo) {
        if (serverInfo == null)
            return null;

        for (Server server : Server.values()) {
            if (server.getPluginName().equals(serverInfo.getName()))
                return server;
        }
        return null;
    }

    /*

        Players

     */

    public void triggerJoinEvent(ProxiedPlayer player) {
        getPlayer(player);
    }

    public BungeePlayer newPlayerInstance(ProxiedPlayer player) {
        BungeePlayer bungeePlayer = new BungeePlayer(this, player);
        bungeePlayer.processJoinEvent();

        return bungeePlayer;
    }

    public BungeePlayer getPlayer(UUID uuid) {
        if (this.players.containsKey(uuid))
            return this.players.get(uuid);

        ProxiedPlayer player = getProxy().getPlayer(uuid);

        return player != null ? newPlayerInstance(player) : null;
    }

    public BungeePlayer getPlayerIfOnline(UUID uuid) {
        return this.players.get(uuid);
    }

    public BungeePlayer getPlayer(ProxiedPlayer player) {
        return this.players.containsKey(player.getUniqueId()) ? this.players.get(player.getUniqueId()) : newPlayerInstance(player);
    }

    public void registerPlayer(BungeePlayer player) {
        this.players.put(player.getUniqueId(), player);

        Map<String, String> data = new HashMap<>();
        data.put("name", player.getName(Name.RAW));

        if (player.hasNickName())
            data.put("nick_name", player.getName(Name.NICK));

        data.put("staff_rank", player.getStaffRank().toString());
        data.put("vip_rank", player.getVipRank().toString());
        data.put("language", player.getLanguage().toString());

        stateManager.setPlayerData(player.getUniqueId(), data);
    }

    public void unregisterPlayer(BungeePlayer player) {
        this.players.remove(player.getUniqueId());

        stateManager.removePlayerData(player.getUniqueId());
    }

    public Collection<BungeePlayer> getPlayers() {
        return this.players.values();
    }

    /*

        Setup

     */

    private void registerEvents(Listener... listeners) {
        PluginManager pluginManager = getProxy().getPluginManager();

        for (Listener l : listeners) {
            pluginManager.registerListener(plugin, l);
        }
    }

    private void registerSubscribers(SubscriberInstance... subscribers) {
        for (SubscriberInstance subscriber : subscribers) {
            subscriber.subscribe();
        }
    }

    private void importDump(SQLiteDatabase database) {
        String root = Environment.get("OM_ROOT", ".");
        java.nio.file.Path privateDump = java.nio.file.Path.of(root, "@orbitmines/minecraft/archive/private/databases/2019-05-06_dump3.sql");
        java.nio.file.Path publicDump = java.nio.file.Path.of(root, "@orbitmines/minecraft/archive/databases/2019-05-06_dump3.sql");

        java.nio.file.Path dumpFile = java.nio.file.Files.exists(privateDump) ? privateDump : publicDump;

        if (!java.nio.file.Files.exists(dumpFile)) {
            getLogger().warning("No SQL dump file found at " + privateDump + " or " + publicDump);
            return;
        }

        getLogger().info("First load detected. Importing dump from " + dumpFile + "...");

        try {
            MySQLDumpImporter.importDump(database, dumpFile);
            getLogger().info("SQL dump imported successfully.");
        } catch (Exception e) {
            getLogger().severe("Failed to import SQL dump: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void clearSurvivalData(SQLiteDatabase database) {
        getLogger().info("Clearing survival data (keeping survival_players)...");
        database.executeQuery("DELETE FROM `survival_homes`");
        database.executeQuery("DELETE FROM `survival_warps`");
        database.executeQuery("DELETE FROM `survival_favorite_warps`");
        database.executeQuery("DELETE FROM `survival_claims`");
        database.executeQuery("DELETE FROM `survival_claim_members`");
        database.executeQuery("DELETE FROM `survival_chest_shops`");
        database.executeQuery("DELETE FROM `survival_regions`");
        database.executeQuery("UPDATE `survival_players` SET `back_location` = NULL, `logout_location` = NULL");

        database.executeQuery("DELETE FROM `discord_squad_members`");
        database.executeQuery("DELETE FROM `discord_squad_invites`");
        database.executeQuery("DELETE FROM `discord_squads`");

        getLogger().info("Survival and discord squad data cleared.");
    }

    private void seedMaps(SQLiteDatabase database, String root) {
        String worldsPath = root + "/@orbitmines/minecraft/archive/worlds";
        try {
            WorldSeeder.seed(database, worldsPath);
        } catch (Exception e) {
            getLogger().severe("Failed to seed maps from worlds directory: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void setupPubSub() {
        /* Initialize state manager (in-memory state storage) */
        stateManager = new BungeeStateManager();
        StateProvider.initialize(stateManager);

        /* Initialize plugin messaging broker */
        BungeePubSubBroker broker = new BungeePubSubBroker(plugin);
        PubSubBroker.initialize(broker);

        getLogger().info("Successfully setup plugin messaging.");
    }

    private void setupSkinLibrary() {
        String root = Environment.get("OM_ROOT", ".");
        skinLibrary = new SkinLibrary(root + "/.orbitmines/skins") {
            @Override
            protected void updateLibraryAsync(Runnable runnable) {
                getProxy().getScheduler().runAsync(plugin, runnable);
            }
        };
    }

    private void setupDiscordBot() {
        String token = Environment.get("OM_DISCORD_BOT_TOKEN", null);

        if (token == null)
            return;

        String serverId = Environment.get("OM_DISCORD_SERVER_ID", "473472016092233746");

        discordBot = new BungeeDiscordBot(token, serverId, this, this.skinLibrary);
        discordBot.initialize(Environment.isProduction() ? OnlineStatus.IDLE : OnlineStatus.ONLINE);

    }

    private void setupPlan() {
        PluginManager pluginManager = ProxyServer.getInstance().getPluginManager();
        CommandSender console = ProxyServer.getInstance().getConsole();

        pluginManager.dispatchCommand(console, "planbungee reload");
        pluginManager.dispatchCommand(console, "planbungee setup");
    }

    /*

        Broadcast

     */

    public void broadcast(String namespace, String key) {
        for (BungeePlayer player : players.values()) {
            player.sendMessage(namespace, key);
        }
    }

    public void broadcast(String namespace, String key, Object... args) {
        for (BungeePlayer player : players.values()) {
            player.sendMessage(namespace, key, args);
        }
    }

    public void broadcastRaw(String prefix, Color color, String message) {
        for (BungeePlayer player : players.values()) {
            player.sendRawMessage(prefix, color, message);
        }
    }

    public  void broadcast(String prefix, Color color, String key) {
        for (BungeePlayer player : players.values()) {
            player.sendMessage(prefix, color, key);
        }
    }

    public void broadcast(String prefix, Color color, String key, Object... args) {
        for (BungeePlayer player : players.values()) {
            player.sendMessage(prefix, color, key, args);
        }
    }

    public void broadcast(String prefix, Color color, String namespace, String key) {
        for (BungeePlayer player : players.values()) {
            player.sendMessage(prefix, color, namespace, key);
        }
    }

    public void broadcast(String prefix, Color color, String namespace, String key, Object... args) {
        for (BungeePlayer player : players.values()) {
            player.sendMessage(prefix, color, namespace, key, args);
        }
    }

    public void broadcast(BroadcastExclusion exclusion, String namespace, String key) {
        for (BungeePlayer player : players.values()) {
            if (!exclusion.includes(player))
                continue;

            player.sendMessage(namespace, key);
        }
    }

    public void broadcast(BroadcastExclusion exclusion, String namespace, String key, Object... args) {
        for (BungeePlayer player : players.values()) {
            if (!exclusion.includes(player))
                continue;

            player.sendMessage(namespace, key, args);
        }
    }

    public void broadcastRaw(BroadcastExclusion exclusion, String prefix, Color color, String message) {
        for (BungeePlayer player : players.values()) {
            if (!exclusion.includes(player))
                continue;

            player.sendRawMessage(prefix, color, message);
        }
    }

    public  void broadcast(BroadcastExclusion exclusion, String prefix, Color color, String key) {
        for (BungeePlayer player : players.values()) {
            if (!exclusion.includes(player))
                continue;

            player.sendMessage(prefix, color, key);
        }
    }

    public void broadcast(BroadcastExclusion exclusion, String prefix, Color color, String key, Object... args) {
        for (BungeePlayer player : players.values()) {
            if (!exclusion.includes(player))
                continue;

            player.sendMessage(prefix, color, key, args);
        }
    }

    public void broadcast(BroadcastExclusion exclusion, String prefix, Color color, String namespace, String key) {
        for (BungeePlayer player : players.values()) {
            if (!exclusion.includes(player))
                continue;

            player.sendMessage(prefix, color, namespace, key);
        }
    }

    public void broadcast(BroadcastExclusion exclusion, String prefix, Color color, String namespace, String key, Object... args) {
        for (BungeePlayer player : players.values()) {
            if (!exclusion.includes(player))
                continue;

            player.sendMessage(prefix, color, namespace, key, args);
        }
    }

    @FunctionalInterface
    public interface BroadcastExclusion {

        boolean includes(BungeePlayer player);

    }

    /*

        VOTIFIER

     */

    /**
     * The server channel.
     */
    private Channel serverChannel;
    /**
     * The event group handling the channel.
     */
    private NioEventLoopGroup serverGroup;
    /**
     * The RSA key pair.
     */
    private KeyPair keyPair;
    /**
     * Debug mode flag
     */
    private boolean debug;
    /**
     * Keys used for websites.
     */
    private Map<String, Key> tokens = new HashMap<>();
    /**
     * Method used to forward votes to downstream servers
     */
    private ForwardingVoteSource forwardingMethod;

    public void setupVotifier() {
        if (!getDataFolder().exists()) {
            getDataFolder().mkdir();
        }

        // Handle configuration.
        File config = new File(getDataFolder() + "/votifierconfig.yml");
        File rsaDirectory = new File(getDataFolder() + "/rsa");
        Configuration configuration;

        if (!config.exists()) {
            try {
                // First time run - do some initialization.
                getLogger().info("Configuring Votifier for the first time...");

                // Initialize the configuration file.
                config.createNewFile();

                String cfgStr = new String(ByteStreams.toByteArray(getResourceAsStream("votifierConfig.yml")), StandardCharsets.UTF_8);
                String token = TokenUtil.newToken();
                cfgStr = cfgStr.replace("%default_token%", token);
                Files.write(cfgStr, config, StandardCharsets.UTF_8);

                /*
                 * Remind hosted server admins to be sure they have the right
                 * port number.
                 */
                getLogger().info("------------------------------------------------------------------------------");
                getLogger().info("Assigning NuVotifier to listen on port 8192. If you are hosting BungeeCord on a");
                getLogger().info("shared server please check with your hosting provider to verify that this port");
                getLogger().info("is available for your use. Chances are that your hosting provider will assign");
                getLogger().info("a different port, which you need to specify in config.yml");
                getLogger().info("------------------------------------------------------------------------------");
                getLogger().info("Assigning NuVotifier to listen to interface 0.0.0.0. This is usually alright,");
                getLogger().info("however, if you want NuVotifier to only listen to one interface for security ");
                getLogger().info("reasons (or you use a shared host), you may change this in the config.yml.");
                getLogger().info("------------------------------------------------------------------------------");
                getLogger().info("Your default Votifier token is " + token + ".");
                getLogger().info("You will need to provide this token when you submit your server to a voting");
                getLogger().info("list.");
                getLogger().info("------------------------------------------------------------------------------");
            } catch (Exception ex) {
                throw new RuntimeException("Unable to create configuration file", ex);
            }
        }

        // Load the configuration.
        try {
            configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(config);
        } catch (IOException e) {
            throw new RuntimeException("Unable to load configuration", e);
        }

        /*
         * Create RSA directory and keys if it does not exist; otherwise, read
         * keys.
         */
        try {
            if (!rsaDirectory.exists()) {
                rsaDirectory.mkdir();
                keyPair = RSAKeygen.generate(2048);
                RSAIO.save(rsaDirectory, keyPair);
            } else {
                keyPair = RSAIO.load(rsaDirectory);
            }
        } catch (Exception ex) {
            throw new RuntimeException("Error reading RSA prisms", ex);
        }

        // Load Votifier prisms.
        Configuration tokenSection = configuration.getSection("prisms");

        if (configuration.get("prisms") != null) {
            for (String s : tokenSection.getKeys()) {
                tokens.put(s, KeyCreator.createKeyFrom(tokenSection.getString(s)));
                getLogger().info("Loaded token for website: " + s);
            }
        } else {
            String token = TokenUtil.newToken();
            configuration.set("prisms", ImmutableMap.of("default", token));
            tokens.put("default", KeyCreator.createKeyFrom(token));
            try {
                ConfigurationProvider.getProvider(YamlConfiguration.class).save(configuration, config);
            } catch (IOException e) {
                throw new RuntimeException("Error generating Votifier token", e);
            }
            getLogger().info("------------------------------------------------------------------------------");
            getLogger().info("No prisms were found in your configuration, so we've generated one for you.");
            getLogger().info("Your default Votifier token is " + token + ".");
            getLogger().info("You will need to provide this token when you submit your server to a voting");
            getLogger().info("list.");
            getLogger().info("------------------------------------------------------------------------------");
        }

        // Initialize the receiver.
        final String host = configuration.getString("host", "0.0.0.0");
        final int port = configuration.getInt("port", 8192);
        debug = configuration.getBoolean("debug", false);
        if (debug)
            getLogger().info("DEBUG mode enabled!");

        final boolean disablev1 = configuration.getBoolean("disable-v1-protocol");
        if (disablev1) {
            getLogger().info("------------------------------------------------------------------------------");
            getLogger().info("Votifier protocol v1 parsing has been disabled. Most voting websites do not");
            getLogger().info("currently support the modern Votifier protocol in NuVotifier.");
            getLogger().info("------------------------------------------------------------------------------");
        }

        // Must set up server asynchronously due to BungeeCord goofiness.
        FutureTask<?> initTask = new FutureTask<>(Executors.callable(new Runnable() {
            @Override
            public void run() {
                serverGroup = new NioEventLoopGroup(2);

                new ServerBootstrap()
                        .channel(NioServerSocketChannel.class)
                        .group(serverGroup)
                        .childHandler(new ChannelInitializer<NioSocketChannel>() {
                            @Override
                            protected void initChannel(NioSocketChannel channel) throws Exception {
                                channel.attr(VotifierSession.KEY).set(new VotifierSession());
                                channel.attr(VotifierPlugin.KEY).set(Bungeecord.this);
                                channel.pipeline().addLast("greetingHandler", new VotifierGreetingHandler());
                                channel.pipeline().addLast("protocolDifferentiator", new VotifierProtocolDifferentiator(false, !disablev1));
                                channel.pipeline().addLast("voteHandler", new VoteInboundHandler(Bungeecord.this));
                            }
                        })
                        .bind(host, port)
                        .addListener(new ChannelFutureListener() {
                            @Override
                            public void operationComplete(ChannelFuture future) throws Exception {
                                if (future.isSuccess()) {
                                    serverChannel = future.channel();
                                    getLogger().info("Votifier enabled on socket "+serverChannel.localAddress()+".");
                                } else {
                                    SocketAddress socketAddress = future.channel().localAddress();
                                    if (socketAddress == null) {
                                        socketAddress = new InetSocketAddress(host, port);
                                    }
                                    getLogger().log(Level.SEVERE, "Votifier was not able to bind to " + socketAddress, future.cause());
                                }
                            }
                        });
            }
        }));
        getProxy().getScheduler().runAsync(plugin, initTask);
        try {
            initTask.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException("Unable to start server", e);
        }

        Configuration fwdCfg = configuration.getSection("forwarding");
        String fwdMethod = fwdCfg.getString("method", "none").toLowerCase();
        if ("none".equals(fwdMethod)) {
            getLogger().info("Method none selected for vote forwarding: Votes will not be forwarded to backend servers.");
        } else if ("pluginmessaging".equals(fwdMethod)) {
            String channel = fwdCfg.getString("pluginMessaging.channel", "NuVotifier");
            String cacheMethod = fwdCfg.getString("pluginMessaging.cache", "file").toLowerCase();
            VoteCache voteCache = null;
            if ("none".equals(cacheMethod)) {
                getLogger().info("Vote cache none selected for caching: votes that cannot be immediately delivered will be lost.");
            } else if ("memory".equals(cacheMethod)) {
                voteCache = new MemoryVoteCache(ProxyServer.getInstance().getServers().size());
                getLogger().info("Using in-memory cache for votes that are not able to be delivered.");
            } else if ("file".equals(cacheMethod)) {
                try {
                    voteCache = new FileVoteCache(ProxyServer.getInstance().getServers().size(), plugin, new File(getDataFolder(),
                            fwdCfg.getString("pluginMessaging.file.name")));
                } catch (IOException e) {
                    getLogger().log(Level.SEVERE, "Unload to load file cache. Votes will be lost!", e);
                }
            }
            if (!fwdCfg.getBoolean("pluginMessaging.onlySendToJoinedServer")) {

                List<String> ignoredServers = fwdCfg.getStringList("pluginMessaging.excludedServers");

                try {
                    forwardingMethod = new PluginMessagingForwardingSource(channel, ignoredServers, this, voteCache);
                    getLogger().info("Forwarding votes over PluginMessaging channel '" + channel + "' for vote forwarding!");
                } catch (RuntimeException e) {
                    getLogger().log(Level.SEVERE, "NuVotifier could not set up PluginMessaging for vote forwarding!", e);
                }
            } else {
                try {
                    String fallbackServer = fwdCfg.getString("pluginMessaging.joinedServerFallback", null);
                    if (fallbackServer != null && fallbackServer.isEmpty()) fallbackServer = null;
                    forwardingMethod = new OnlineForwardPluginMessagingForwardingSource(channel, this, voteCache, fallbackServer);
                } catch (RuntimeException e) {
                    getLogger().log(Level.SEVERE, "NuVotifier could not set up PluginMessaging for vote forwarding!", e);
                }
            }
        } else if ("proxy".equals(fwdMethod)) {
            Configuration serverSection = fwdCfg.getSection("proxy");
            List<ProxyForwardingVoteSource.BackendServer> serverList = new ArrayList<>();
            for (String s : serverSection.getKeys()) {
                Configuration section = serverSection.getSection(s);
                InetAddress address;
                try {
                    address = InetAddress.getByName(section.getString("address"));
                } catch (UnknownHostException e) {
                    getLogger().info("Address " + section.getString("address") + " couldn't be looked up. Ignoring!");
                    continue;
                }
                ProxyForwardingVoteSource.BackendServer server = new ProxyForwardingVoteSource.BackendServer(s,
                        new InetSocketAddress(address, section.getShort("port")),
                        KeyCreator.createKeyFrom(section.getString("token",section.getString("key"))));
                serverList.add(server);
            }

            forwardingMethod = new ProxyForwardingVoteSource(this, serverGroup, serverList, null);
            getLogger().info("Forwarding votes from this NuVotifier instance to another NuVotifier server.");
        } else {
            getLogger().severe("No vote forwarding method '" + fwdMethod + "' known. Defaulting to noop implementation.");
        }
    }

    public void disableVotifier() {
        // Shut down the network handlers.
        if (serverChannel != null)
            serverChannel.close();
        serverGroup.shutdownGracefully();

        if (forwardingMethod != null) {
            forwardingMethod.halt();
        }

        getLogger().info("Votifier disabled.");
    }

    @Override
    public Map<String, Key> getTokens() {
        return tokens;
    }

    @Override
    public KeyPair getProtocolV1Key() {
        return keyPair;
    }

    @Override
    public String getVersion() {
        return plugin.getDescription().getVersion();
    }

    public boolean isDebug() {
        return debug;
    }

    @Override
    public void onVoteReceived(Channel channel, Vote vote, VotifierSession.ProtocolVersion protocolVersion) throws Exception {
        if (debug) {
            if (protocolVersion == VotifierSession.ProtocolVersion.ONE) {
                getLogger().info("Got a protocol v1 vote record from " + channel.remoteAddress() + " -> " + vote);
            } else {
                getLogger().info("Got a protocol v2 vote record from " + channel.remoteAddress() + " -> " + vote);
            }
        }

        getProxy().getScheduler().runAsync(plugin, () -> getProxy().getPluginManager().callEvent(new VotifierEvent(vote)));

        if (forwardingMethod != null) {
            getProxy().getScheduler().runAsync(plugin, () ->forwardingMethod.forward(vote));
        }
    }

    @Override
    public void onError(Channel channel, Throwable throwable) {
        if (debug) {
            getLogger().log(Level.SEVERE, "Unable to process vote from " + channel.remoteAddress(), throwable);
        } else {
            getLogger().log(Level.SEVERE, "Unable to process vote from " + channel.remoteAddress());
        }
    }
}
