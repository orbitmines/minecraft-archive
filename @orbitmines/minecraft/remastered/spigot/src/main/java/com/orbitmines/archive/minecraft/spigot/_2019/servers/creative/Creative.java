package com.orbitmines.archive.minecraft.spigot._2019.servers.creative;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.utils.state.StateProvider;
import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft._2019.libs.Server;
import com.orbitmines.archive.minecraft._2019.libs.player.Name;
import com.orbitmines.archive.minecraft._2019.libs.player.OfflinePlayer;
import com.orbitmines.archive.minecraft._2019.libs.player.PlayerInstance;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.ChatHandler;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMServer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.TabListHandler;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.Command;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.database.models.OMMap;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.events.CommandEvents;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.loot.LootHandler;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.chat.ActionBar;
import com.orbitmines.archive.minecraft.spigot._2019.servers.creative.chat.CreativeChatHandler;
import com.orbitmines.archive.minecraft.spigot._2019.servers.creative.commands.CommandPlot;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.prevention.Prevention;
import com.orbitmines.archive.minecraft.spigot._2019.servers.creative.events.InteractEvent;
import com.orbitmines.archive.minecraft.spigot._2019.servers.creative.events.CreativeCommandEvents;
import com.orbitmines.archive.minecraft.spigot._2019.servers.creative.events.CreativeDamageEvent;
import com.orbitmines.archive.minecraft.spigot._2019.servers.creative.events.WorldProtectionEvent;
import com.orbitmines.archive.minecraft.spigot._2019.servers.creative.kit.CreativeLobbyKit;
import com.orbitmines.archive.minecraft.spigot._2019.servers.creative.loot.CreativeLootHandler;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.worlds.maps.datapoints.DataPointHandler;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

public class Creative extends OMServer<Creative, CreativePlayer> {

    /** UUID that owns all non-UUID-prefixed archive worlds. */
    public static final UUID ARCHIVE_UUID = UUID.fromString("33ee168b-5c2c-42c5-b3b2-d841ceb76b70");
    /** Pattern matching a UUID prefix (xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx_). */
    private static final Pattern UUID_PREFIX = Pattern.compile("^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}_", Pattern.CASE_INSENSITIVE);

    @Getter private List<CreativeWorld> allWorlds = new java.util.concurrent.CopyOnWriteArrayList<>();
    @Getter private CreativeLobbyKit lobbyKit;

    public Creative(JavaPlugin plugin) {
        super(plugin);
    }

    @Override
    public CreativePlayer newPlayerInstance(Player player) {
        return new CreativePlayer(player, this);
    }

    @Override
    public void afterStartupSync() {
        this.lobbyKit = new CreativeLobbyKit(this);

        if (getLobby() != null && getLobby().getWorld() != null) {
            Prevention.prevent(plugin, getLobby().getWorld(),
                Prevention.BLOCK_BREAK,
                Prevention.BLOCK_INTERACTING,
                Prevention.BLOCK_PLACE,
                Prevention.MONSTER_EGG_USAGE,
                Prevention.BUCKET_USAGE,
                Prevention.ENTITY_INTERACTING,
                Prevention.FOOD_CHANGE,
                Prevention.ITEM_DROP,
                Prevention.LEAF_DECAY,
                Prevention.PLAYER_DAMAGE,
                Prevention.ITEM_PICKUP,
                Prevention.SWAP_HAND_ITEMS,
                Prevention.WEATHER_CHANGE
            );
        }

        registerCommands(
            new CommandPlot(this),
            new com.orbitmines.archive.minecraft.spigot._2019.servers.creative.commands.CommandSpawn(this),
            new com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.CommandGamemode<>(this, GameMode.CREATIVE, "creative", "c", "gmc"),
            new com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.CommandGamemode<>(this, GameMode.SPECTATOR, "spectator", "spec", "gmspec"),
            new com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.CommandSpeed<>(this),
            new com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.CommandTeleport<>(this),
            new com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.CommandTeleportHere<>(this),
            new com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.CommandTeleportAccept<>(this),
            new com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.CommandSkull<>(this),
            new com.orbitmines.archive.minecraft.spigot._2019.servers.creative.commands.CommandSave(this)
        );

        registerEvents(
            new WorldProtectionEvent(this),
            new InteractEvent(this),
            new CreativeDamageEvent(this)
        );

        new com.orbitmines.archive.minecraft.spigot._2019.servers.creative.runnables.WorldSaveRunnable(this).start();

        /* Explicitly save all creative worlds on /save-all */
        Bukkit.getPluginManager().registerEvents(new org.bukkit.event.Listener() {
            @org.bukkit.event.EventHandler
            public void onServerCommand(org.bukkit.event.server.ServerCommandEvent event) {
                if (event.getCommand().equalsIgnoreCase("save-all"))
                    saveAllWorlds();
            }

            @org.bukkit.event.EventHandler
            public void onPlayerCommand(org.bukkit.event.player.PlayerCommandPreprocessEvent event) {
                if (!event.getMessage().equalsIgnoreCase("/save-all"))
                    return;

                CreativePlayer cp = getPlayer(event.getPlayer());
                if (cp != null && cp.isOpMode())
                    saveAllWorlds();
            }
        }, plugin);
    }

    @Override
    public CommandEvents<Creative, CreativePlayer> newCommandEventsInstance() {
        return new CreativeCommandEvents(this);
    }

    private void registerCommands(Command<Creative, CreativePlayer>... commands) {
        for (Command<Creative, CreativePlayer> command : commands) {
            command.register();
        }
    }

    public List<CreativeWorld> loadPlayerWorlds(UUID uuid) {
        List<CreativeWorld> worlds = new ArrayList<>();
        boolean isArchiveOwner = ARCHIVE_UUID.equals(uuid);

        String uuidPrefix = uuid.toString().toLowerCase() + "_";
        for (OMMap map : OMMap.getAll(OMMap.class)) {
            if (map.getWorldFileName() == null)
                continue;

            boolean match;
            if (map.getWorldFileName().startsWith(uuidPrefix)) {
                match = true;
            } else if (isArchiveOwner && !UUID_PREFIX.matcher(map.getWorldFileName()).find()) {
                /* Archive worlds: any world whose file name doesn't start with a UUID belongs to the archive owner */
                match = true;
            } else {
                match = false;
            }

            if (match) {
                CreativeWorld world = new CreativeWorld(this, map, uuid);
                worlds.add(world);

                if (!allWorlds.contains(world))
                    allWorlds.add(world);
            }
        }

        /* Cache owner name from DB (async-safe here) */
        if (!worlds.isEmpty()) {
            OfflinePlayer owner = OfflinePlayer.get(uuid);
            String ownerName = owner != null ? owner.getName(Name.RAW_COLORED) : uuid.toString();
            for (CreativeWorld world : worlds)
                world.setOwnerName(ownerName);
        }

        return worlds;
    }

    public void loadAndTeleport(CreativePlayer player, CreativeWorld world) {
        loadAndTeleport(player, world, false);
    }

    public void loadAndTeleport(CreativePlayer player, CreativeWorld world, boolean useLogoutLocation) {
        if (world.isLoading()) {
            player.sendMessage("Creative", Color.YELLOW, "creative", "player.world.already_loading");
            return;
        }

        /* If already loaded, just teleport directly on sync */
        if (world.isLoaded()) {
            runSync(() -> teleportToWorld(player, world, useLogoutLocation));
            return;
        }

        world.setLoading(true);

        /* Show ActionBar progress during load */
        ActionBar actionBar = new ActionBar(player.bukkit(), () -> "§d§lLoading world...", 20 * 120) {
            @Override
            public void onRun() {
                if (world.isLoaded())
                    forceStop();
            }
        };
        actionBar.send();

        /* Copy world files async (can be slow for large archive worlds) */
        runAsync(() -> {
            try {
                world.ensureWorldFiles();
            } catch (Exception e) {
                e.printStackTrace();
                world.setLoading(false);
                actionBar.forceStop();
                runSync(() -> player.sendMessage("Creative", Color.RED, "creative", "player.world.load_failed"));
                return;
            }

            /* Switch to sync for Bukkit.createWorld() + teleport */
            runSync(() -> {
                try {
                    World bukkit = world.loadOrCreateWorld();
                    if (bukkit == null) {
                        player.sendMessage("Creative", Color.RED, "creative", "player.world.load_failed");
                        return;
                    }

                    teleportToWorld(player, world, useLogoutLocation);
                } finally {
                    world.setLoading(false);
                    actionBar.forceStop();
                }
            });
        });
    }

    private void teleportToWorld(CreativePlayer player, CreativeWorld world, boolean useLogoutLocation) {
        World bukkit = world.getWorld();
        if (bukkit == null) {
            player.sendMessage("Creative", Color.RED, "creative", "player.world.load_failed");
            return;
        }

        /* Set op for WorldEdit/VoxelSniper permissions */
        player.bukkit().setOp(true);

        /* Determine target location — explicitly set world reference because old level.dat spawn can reference wrong world */
        Location spawn = bukkit.getSpawnLocation();
        Location target = new Location(bukkit, spawn.getX(), spawn.getY(), spawn.getZ(), spawn.getYaw(), spawn.getPitch());
        if (useLogoutLocation) {
            Location logout = player.getModel().resolveLogoutLocation();
            if (logout != null && logout.getWorld() != null && logout.getWorld().getName().equals(bukkit.getName())) {
                target = logout;
            }
        }

        /* Ensure the chunk is loaded before teleporting */
        target.getChunk().load(true);
        player.teleport(target);
    }

    public void saveAllWorlds() {
        String now = com.orbitmines.archive.minecraft._2019.utils.DateUtils.DATE_TIME_FORMAT.format(com.orbitmines.archive.minecraft._2019.utils.DateUtils.now());
        List<String> savedWorldFileNames = new ArrayList<>();
        for (CreativeWorld world : allWorlds) {
            if (world == null)
                continue;
            if (world.isLoaded()) {
                try {
                    world.getWorld().save();
                    savedWorldFileNames.add(world.getWorldFileName());
                } catch (IllegalStateException ignored) {
                    /* World's SavedDataStorage may already be closed during shutdown */
                }
            }
        }

        /* Persist last_edit timestamps on async thread (DB access not allowed on sync) */
        if (!savedWorldFileNames.isEmpty()) {
            runAsync(() -> {
                for (String worldFileName : savedWorldFileNames) {
                    StateProvider.getInstance().setString("world:" + worldFileName + ":last_edit", now);
                }
            });
        }
    }

    public Location getLobbySpawn() {
        Location spawn;
        if (getLobby() != null && getLobby().getWorld() != null)
            spawn = getLobby().getWorld().getSpawnLocation();
        else
            spawn = Bukkit.getWorlds().get(0).getSpawnLocation();

        return getSafeSpawn(spawn);
    }

    /** If spawn is inside a solid block, find the highest air block above. */
    public static Location getSafeSpawn(Location spawn) {
        if (spawn.getBlock().getType().isAir() || !spawn.getBlock().getType().isSolid())
            return spawn;

        Location safe = spawn.clone();
        int maxY = safe.getWorld().getMaxHeight();
        while (safe.getBlockY() < maxY && !safe.getBlock().getType().isAir()) {
            safe.add(0, 1, 0);
        }
        return safe;
    }

    public CreativeWorld getWorldByFileName(String worldFileName) {
        for (CreativeWorld world : allWorlds) {
            if (world == null)
                continue;
            if (world.getWorldFileName().equals(worldFileName))
                return world;
        }
        return null;
    }

    public CreativeWorld getWorldByBukkitWorld(World world) {
        for (CreativeWorld cw : allWorlds) {
            if (cw != null && cw.isLoaded() && cw.getWorld().equals(world))
                return cw;
        }
        return null;
    }

    @Override
    public Server getType() {
        return Server.CREATIVE;
    }

    @Override
    protected Creative instance() {
        return this;
    }

    @Override
    public ChatHandler newChatHandler(PlayerInstance sender, ChatHandler.Type type, String message) {
        return new CreativeChatHandler(this, type, sender, message);
    }

    @Override
    public TabListHandler newTabListHandler(CreativePlayer player) {
        return new TabListHandler<>(this, player);
    }

    @Override
    public LootHandler newLootHandler(CreativePlayer player) {
        return new CreativeLootHandler(player);
    }

    @Override
    public boolean clearPlayerData() {
        return false;
    }

    @Override
    public boolean saveChunksOnRestart() {
        return true;
    }

    @Override
    public boolean broadcastWhenSaving() {
        return false;
    }

    @Override
    public boolean shouldSetupLobby() {
        return true;
    }

    @Override
    public DataPointHandler createDataPointHandler(OMMap.Type type) {
        return null;
    }

    @Override
    public Prevention[] getLobbyPreventions() {
        return new Prevention[]{
            Prevention.BLOCK_BREAK,
            Prevention.BLOCK_INTERACTING,
            Prevention.BLOCK_PLACE,
            Prevention.MONSTER_EGG_USAGE,
            Prevention.BUCKET_USAGE,
            Prevention.ENTITY_INTERACTING,
            Prevention.FOOD_CHANGE,
            Prevention.ITEM_DROP,
            Prevention.LEAF_DECAY,
            Prevention.PLAYER_DAMAGE,
            Prevention.ITEM_PICKUP,
            Prevention.SWAP_HAND_ITEMS,
            Prevention.WEATHER_CHANGE
        };
    }

    @Override
    public void setupNpc(String string, Location location) {

    }

    @Override
    protected void instantiateLeaderBoards() {

    }
}
