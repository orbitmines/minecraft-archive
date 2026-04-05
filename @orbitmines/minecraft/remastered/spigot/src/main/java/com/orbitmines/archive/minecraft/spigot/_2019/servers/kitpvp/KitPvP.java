package com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.Server;
import com.orbitmines.archive.minecraft._2019.libs.player.PlayerInstance;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.ChatHandler;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMServer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.TabListHandler;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.Command;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.database.models.OMMap;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.events.EnterVoidEvent;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.events.SpawnLocationEvent;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.loot.LootHandler;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.Passive;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.chat.KitPvPChatHandler;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.commands.CommandPrismShop;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.datapoints.KitPvPLobbyDataPointHandler;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.datapoints.KitPvPMapDataPointHandler;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.datapoints.lobby.KitPvPDataPointLobbyKitInfo;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.datapoints.lobby.KitPvPDataPointSpawnpoint;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.datapoints.map.KitPvPDataPointMapBarrier;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.datapoints.map.KitPvPDataPointMapSpawnpoint;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.datapoints.map.KitPvPDataPointMapSpectatorSpawnpoint;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.events.*;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.gui.KitSelectorGUI;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.item_builders.KitItemBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.actives.ActiveReaperTeleport;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.item_handlers.ItemHoverActiveHandler;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.item_handlers.ItemHoverPlayerTracker;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.kit.KitPvPKit;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.kit.LobbyKit;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.kit.kits.*;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.kit.kits._2015.*;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.leaderboards.TopKillsLeaderBoard;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.loot.KitPvPLootHandler;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.runnables.KitPassiveRunnable;
import com.orbitmines.archive.minecraft._2019.utils.RandomUtils;
import com.orbitmines.archive.minecraft._2019.utils.mutable.MutableString;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.ItemUtils;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.ItemBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.entities.Mob;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.npcs.PersonalisedFloatingItem;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.npcs.PersonalisedMobNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.prevention.Prevention;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.worlds.maps.datapoints.DataPointHandler;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.commands.CommandFreeKitSaturday;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.GameRule;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class KitPvP extends OMServer<KitPvP, KitPvPPlayer> {

    public KitPvP(JavaPlugin plugin) {
        super(plugin);
    }

    public static final int COINS_PER_KILL = 50;
    public static final int XP_PER_KILL = 10;

    public static final KitItemBuilder PLAYER_TRACKER = new KitItemBuilder(null, Material.COMPASS, 1, "§c§lPlayer Tracker").addPassive(Passive.PLAYER_TRACKING, 1);

    @Getter @Setter private boolean freeKitSaturday = false;
    @Getter @Setter private int freeKitLevel = 1;

    @Getter private LobbyKit lobbyKit;
    @Getter private KitPvPMap map;

    @Getter private List<Location> spawns;
    @Getter private List<KitPvPKit> kits;

    @Override
    public KitPvPPlayer newPlayerInstance(Player player) {
        return new KitPvPPlayer(player, this);
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public Server getType() {
        return Server.KITPVP;
    }

    @Override
    public void afterStartupSync() {

        Prevention.prevent(plugin, getLobby().getWorld(),
            Prevention.BLOCK_BREAK,
            Prevention.BLOCK_INTERACTING,
            Prevention.BLOCK_PLACE,
//            Prevention.CHUNK_UNLOAD,
            Prevention.CLICK_PLAYER_INVENTORY,
            Prevention.ENTITY_INTERACTING,
            Prevention.FOOD_CHANGE,
            Prevention.ITEM_DROP,
            Prevention.LEAF_DECAY,
            Prevention.PLAYER_DAMAGE,
            Prevention.ITEM_PICKUP,
            Prevention.SWAP_HAND_ITEMS,
            Prevention.WEATHER_CHANGE,
            Prevention.MONSTER_EGG_USAGE,
            Prevention.BUCKET_USAGE
        );
        
        /* Setup Map */
        map = KitPvPMap.load();
        String root = System.getProperty("OM_ROOT", ".");
        java.io.File mapSourceDir = new java.io.File(root, "@orbitmines/minecraft/archive/worlds/" + map.getName());
        World mapWorld = worldLoader.fromDirectory(mapSourceDir, map.getWorldFileName(), true, map.getWorldGenerator());
        mapWorld.setGameRule(GameRule.ADVANCE_TIME, false);
        mapWorld.setGameRule(GameRule.SPAWN_MOBS, false);
        mapWorld.setGameRule(GameRule.DROWNING_DAMAGE, true);
        /* GameRule.DO_FIRE_TICK removed in 26.1 */
        mapWorld.setTime(18000);
        mapWorld.setAutoSave(false);

        map.setup(mapWorld, createDataPointHandler(OMMap.Type.GAMEMAP));
        
        Prevention.prevent(plugin, mapWorld,
            Prevention.BLOCK_BREAK,
            Prevention.BLOCK_INTERACTING,
            Prevention.BLOCK_PLACE,
            Prevention.LEAF_DECAY,
            Prevention.WEATHER_CHANGE,

            Prevention.ENTITY_INTERACTING,
            Prevention.ITEM_DROP,
            Prevention.ITEM_PICKUP,
            Prevention.MONSTER_EGG_USAGE,

            Prevention.FOOD_CHANGE,
            Prevention.BLOCK_SPREAD,
            Prevention.MONSTER_EGG_USAGE,
            Prevention.BUCKET_USAGE,
            Prevention.PHYSICAL_INTERACTING_EXCEPT_PLATES,
            Prevention.EXPLOSION_DAMAGE
        );

        /* Datapoints */
        {
            List<Location> spawnLocations = ((KitPvPDataPointMapSpawnpoint) (map.getDataPointHandler().getDataPoint(KitPvPMapDataPointHandler.Type.SPAWNPOINT))).getSpawns();
            List<Location> spectatorLocations = ((KitPvPDataPointMapSpectatorSpawnpoint) (map.getDataPointHandler().getDataPoint(KitPvPMapDataPointHandler.Type.SPECTATOR_SPAWNPOINT))).getSpawns();

            map.setup(spawnLocations, spectatorLocations);

            for (Block block : ((KitPvPDataPointMapBarrier) (map.getDataPointHandler().getDataPoint(KitPvPMapDataPointHandler.Type.BARRIER))).getBarriers()) {
                for (int y = block.getY(); y < block.getWorld().getMaxHeight(); y++) {
                    block.getWorld().getBlockAt(block.getX(), y, block.getZ()).setType(Material.BARRIER);
                }
            }

            /* Remove trapdoor from PreventionSet: we want players to be able to interact with trapdoors and buttons */
            ItemUtils.INTERACTABLE.remove(Material.LEVER);
            ItemUtils.INTERACTABLE.remove(Material.STONE_BUTTON);

            ItemUtils.INTERACTABLE.remove(Material.ACACIA_BUTTON);
            ItemUtils.INTERACTABLE.remove(Material.BIRCH_BUTTON);
            ItemUtils.INTERACTABLE.remove(Material.DARK_OAK_BUTTON);
            ItemUtils.INTERACTABLE.remove(Material.JUNGLE_BUTTON);
            ItemUtils.INTERACTABLE.remove(Material.OAK_BUTTON);
            ItemUtils.INTERACTABLE.remove(Material.SPRUCE_BUTTON);
        }

        registerKits();
        registerItems();

        /* Datapoints */
        spawns = ((KitPvPDataPointSpawnpoint) (getLobby().getDataPointHandler().getDataPoint(KitPvPLobbyDataPointHandler.Type.SPAWNPOINT))).getSpawns();

        Map<Location, KitPvPDataPointLobbyKitInfo.KitInfo> kitInfo = ((KitPvPDataPointLobbyKitInfo) (getLobby().getDataPointHandler().getDataPoint(KitPvPLobbyDataPointHandler.Type.KIT_INFO))).getKitInfo();
        for (Location location : kitInfo.keySet()) {
            KitPvPDataPointLobbyKitInfo.KitInfo info = kitInfo.get(location);

            PersonalisedFloatingItem<KitPvPPlayer> npc = new PersonalisedFloatingItem<KitPvPPlayer>(info::getIcon, location.subtract(0, 2, 0)) {

                @Override
                public MutableString[] getLines(KitPvPPlayer player) {
                    return new MutableString[] {
                        info::getDisplayName,
                        () -> "§7§o" + info.getDescription(player, getKit(player.getLastSelectedId(), player.getLastSelectedLevel()))
                    };
                }
            };

            npc.create();
        }

        registerEvents(
            new SpawnLocationEvent() {
                @Override
                public Location getSpawnLocation(Player player) {
                    return RandomUtils.randomFrom(spawns);
                }
            },
            new EnterVoidEvent<KitPvP, KitPvPPlayer>(this, getLobby().getWorld()) {
                @Override
                public Location getRespawnLocation(KitPvPPlayer player) {
                    return RandomUtils.randomFrom(spawns);
                }
            },
            new DamageByEntityEvent(this),
            new DamageEvent(this),
            new DeathEvent(this),
            new ExpChangeEvent(this),
            new InteractEvent(this),
            new MovementEvent(this),
            new ProjectileEvents(this),
            new RegainHealthEvent(this),
            new SpectatorEvents(this),
            new ActiveReaperTeleport.ShadowStepListener(),
            new FireBreakEvent(this),
            new FishermanRodEvent(this)
        );
        
        registerCommands(
            new CommandPrismShop(this),
            new CommandFreeKitSaturday(this)
        );
    }

    private void registerCommands(Command<KitPvP, KitPvPPlayer>... commands) {
        for (Command<KitPvP, KitPvPPlayer> command : commands) {
            command.register();
        }
    }
    
    public boolean isSaturday() {
        return freeKitSaturday || Calendar.getInstance().get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY;
    }

    private void registerKits() {
        this.lobbyKit = new LobbyKit(this);
        
        this.kits = Arrays.asList(
            new KitKnight(this),
            new KitArcher(this),
            new KitSoldier(this),
            new KitMage(this),
            new KitTank(this),
            new KitKing(this),
            new KitDrunk(this),
            new KitPyro(this),
            new KitBunny(this),
            new KitEnchanter(this),
            /* 2015 kits */
            new KitAssassin(this),
            new KitBeast(this),
            new KitBlaze(this),
            new KitDarkMage(this),
            new KitFish(this),
            new KitFisherman(this),
            new KitGrimReaper(this),
            new KitHeavy(this),
            new KitLord(this),
            new KitMiner(this),
            new KitNecromancer(this),
            new KitSnowGolem(this),
            new KitSpider(this),
            new KitTNT(this),
            new KitTree(this),
            new KitVampire(this),
            new KitVillager(this),
            new KitWizard(this)
        );
    }

    public KitPvPKit getKitById(long id) {
        for (KitPvPKit kit : kits) {
            if (kit.getId() == id)
                return kit;
        }
        return null;
    }

    public KitPvPKit.Level getKit(long id, int level) {
        for (KitPvPKit kit : kits) {
            if (kit.getId() == id)
                return kit.getLevel(level);
        }
        return null;
    }

    private void registerItems() {
        new ItemHoverActiveHandler(this);
        new ItemHoverPlayerTracker(this);
    }

    @Override
    protected void registerRunnables() {
        super.registerRunnables();

        new KitPassiveRunnable(this).async().start();
    }

    @Override
    protected KitPvP instance() {
        return this;
    }

    @Override
    public ChatHandler newChatHandler(PlayerInstance sender, ChatHandler.Type type, String message) {
        return new KitPvPChatHandler(this, type, sender, message);
    }

    @Override
    public TabListHandler newTabListHandler(KitPvPPlayer player) {
        return new TabListHandler<>(this, player);
    }

    @Override
    public LootHandler newLootHandler(KitPvPPlayer player) {
        return new KitPvPLootHandler(player);
    }

    @Override
    public boolean clearPlayerData() {
        return true;
    }

    @Override
    public boolean saveChunksOnRestart() {
        return false;
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
        switch (type) {

            case LOBBY:
                return new KitPvPLobbyDataPointHandler();
            case GAMEMAP:
                return new KitPvPMapDataPointHandler();
            default:
                throw new IllegalStateException();
        }
    }

    @Override
    public void setupNpc(String string, Location location) {
        switch (string.toUpperCase()) {
            case "KIT_SELECTOR": {
                PersonalisedMobNpc<KitPvPPlayer> npc = new PersonalisedMobNpc<KitPvPPlayer>(Mob.DROWNED, location,
                    player -> "§c§lKit Selector",
                    player -> null,
                    player -> "§7§lLast Selected:",
                    player -> {
                        KitPvPKit.Level kit = getKit(player.getLastSelectedId(), player.getLastSelectedLevel());
                        return kit.getHandler().getDisplayName() + " §a§lLvl " + kit.getLevel();
                    }
                );
                npc.setInteractAction((event, player) -> new KitSelectorGUI(player).open());

                npc.create();

                npc.setChestPlate(new ItemBuilder(Material.DIAMOND_CHESTPLATE).build());
                npc.setItemInMainHand(new ItemBuilder(Material.DIAMOND_CHESTPLATE).build());

                break;
            }
        }
    }

    @Override
    protected void instantiateLeaderBoards() {
        new TopKillsLeaderBoard();
    }
}
