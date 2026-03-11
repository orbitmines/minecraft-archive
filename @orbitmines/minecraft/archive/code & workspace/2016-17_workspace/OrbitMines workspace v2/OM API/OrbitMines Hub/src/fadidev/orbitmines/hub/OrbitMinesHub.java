package fadidev.orbitmines.hub;

import fadidev.orbitmines.api.OrbitMinesAPI;
import fadidev.orbitmines.api.OrbitMinesServer;
import fadidev.orbitmines.api.events.PickupEvent;
import fadidev.orbitmines.api.handlers.Currency;
import fadidev.orbitmines.api.handlers.*;
import fadidev.orbitmines.api.handlers.Particle;
import fadidev.orbitmines.api.handlers.npc.Hologram;
import fadidev.orbitmines.api.handlers.npc.NPC;
import fadidev.orbitmines.api.handlers.npc.NPCArmorStand;
import fadidev.orbitmines.api.utils.ConfigUtils;
import fadidev.orbitmines.api.utils.ItemUtils;
import fadidev.orbitmines.api.utils.UUIDUtils;
import fadidev.orbitmines.api.utils.WorldUtils;
import fadidev.orbitmines.api.utils.enums.Direction;
import fadidev.orbitmines.api.utils.enums.Language;
import fadidev.orbitmines.api.utils.enums.Mob;
import fadidev.orbitmines.api.utils.enums.Server;
import fadidev.orbitmines.hub.cmd.*;
import fadidev.orbitmines.hub.events.*;
import fadidev.orbitmines.hub.events.bungee.BungeeMessageEvent;
import fadidev.orbitmines.hub.handlers.HubConsoleCommandExecuter;
import fadidev.orbitmines.hub.handlers.MiniGameArena;
import fadidev.orbitmines.hub.handlers.ServerPortal;
import fadidev.orbitmines.hub.handlers.players.HubPlayer;
import fadidev.orbitmines.hub.inventories.MiniGamesInv;
import fadidev.orbitmines.hub.managers.HubConfigManager;
import fadidev.orbitmines.hub.managers.MindCraftManager;
import fadidev.orbitmines.hub.runnables.*;
import fadidev.orbitmines.hub.utils.enums.MiniGameType;
import fadidev.orbitmines.hub.utils.enums.State;
import fadidev.orbitmines.hub.utils.enums.TicketType;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.*;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Skull;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.EulerAngle;

import java.util.*;

/**
 * Created by Fadi on 7-9-2016.
 */
public class OrbitMinesHub extends JavaPlugin {

    private static OrbitMinesHub hub;
    private OrbitMinesServer omServer;
    private Permission permission = null;

    private HubConfigManager configManager;
    private MindCraftManager mindCraftManager;

    private int playerCounter;

    private World lobby;
    private World builderWorld1;
    private World builderWorld2;

    private Location spawn;
    private Location miniGameArea;
    private Location lapisParkour;
    private Location topVoter1;
    private Location topVoter2;
    private Location topVoter3;
    private Location topVoterSign1;
    private Location topVoterSign2;
    private Location topVoterSign3;
    private Location lastDonatorSign;
    private NPCArmorStand lastDonatorNpc;
    private String lastDonatorString;
    private Location miniGameLocation;

    private Map<Player, HubPlayer> players;
    private List<HubPlayer> hubPlayers;
    private List<ServerPortal> serverPortals;
    private List<Location> waterfalls;
    private List<MiniGameArena> miniGameArenas;
    private Map<MiniGameType, Location> miniGameSigns;
    private Map<Server, NPCArmorStand> serverNpcs;

    private List<UUID> builderPerms;
    private Map<Language, Kit> lobbyKit;
    private Map<Language, Kit> mindCraftKit;
    private List<String> updateLines;

    public void onEnable() {
        hub = this;
        this.omServer = new Hub();

        this.configManager = new HubConfigManager();
        getConfigManager().setup("config", "cages");

        this.lobby = Bukkit.getWorld("Hub");
        this.builderWorld1 = Bukkit.getWorld("BuilderWorld");
        this.builderWorld2 = Bukkit.getWorld("BuilderWorld_v2");
        this.spawn = new Location(getLobby(), 0.5, 75, 0.5, 90, 0);
        this.miniGameArea = new Location(getLobby(), -48.5, 75, 54.5, 30, 0);
        this.lapisParkour = new Location(getLobby(), -36.5, 75, 37.5, 100, 0);
        this.playerCounter = getConfigManager().get("config").getInt("PlayerCounter");
        this.topVoter1 = new Location(getLobby(), -5, 78, 38);
        this.topVoter2 = new Location(getLobby(), -5, 77, 39);
        this.topVoter3 = new Location(getLobby(), -5, 76, 37);
        this.topVoterSign1 = new Location(getLobby(), -6, 77, 38);
        this.topVoterSign2 = new Location(getLobby(), -6, 76, 39);
        this.topVoterSign3 = new Location(getLobby(), -6, 75, 37);
        this.lastDonatorSign = new Location(getLobby(), -7, 76, 8);
        this.lastDonatorString = UUIDUtils.getName(UUID.fromString(getConfigManager().get("config").getString("LastDonator")));
        this.miniGameLocation = new Location(getLobby(), -54.5, 76, 64.5, -90, 0);

        this.players = new HashMap<>();
        this.hubPlayers = new ArrayList<>();
        this.serverPortals = new ArrayList<>();
        this.waterfalls = new ArrayList<>();
        this.miniGameArenas = new ArrayList<>();
        this.miniGameSigns = new HashMap<>();
        this.serverNpcs = new HashMap<>();
        this.builderPerms = ConfigUtils.parseUUIDList(getConfigManager().get("config").getStringList("BuilderPerms"));
        this.lobbyKit = new HashMap<>();
        this.mindCraftKit = new HashMap<>();
        this.updateLines = getConfigManager().get("config").getStringList("UpdateLines");

        this.mindCraftManager = new MindCraftManager();

        for(TicketType type : TicketType.values()){
            type.setup();
        }

        setupPermissions();
        registerCommands();
        registerBungee();
        registerEvents();
        registerCurrencies();
        registerRunnables();
        registerLobbyKit();
        registerServerPortals();
        registerWaterFalls();
        registerMiniGameArenas();
        registerMiniGameSigns();
        registerPodium();

        new BukkitRunnable(){
            @Override
            public void run() {
                WorldUtils.removeAllEntities();
                spawnNpcs();
            }
        }.runTaskLater(this, 100);
    }

    public void onDisable() {

    }

    /* Getters & Setters */

    public static OrbitMinesHub getHub() {
        return hub;
    }

    public OrbitMinesServer getOmServer() {
        return omServer;
    }

    public OrbitMinesAPI getApi(){
        return getOmServer().getApi();
    }

    public Permission getPermission() {
        return permission;
    }

    public HubConfigManager getConfigManager() {
        return configManager;
    }

    public MindCraftManager getMindCraft() {
        return mindCraftManager;
    }

    public World getLobby() {
        return lobby;
    }

    public World getBuilderWorld1() {
        return builderWorld1;
    }

    public World getBuilderWorld2() {
        return builderWorld2;
    }

    public Location getSpawn() {
        return spawn;
    }

    public Location getMiniGameArea() {
        return miniGameArea;
    }

    public Location getLapisParkour() {
        return lapisParkour;
    }

    public int getPlayerCounter() {
        return playerCounter;
    }

    public void setPlayerCounter(int playerCounter) {
        this.playerCounter = playerCounter;

        getConfigManager().get("config").set("PlayerCounter", playerCounter);
        getConfigManager().save("config");
    }

    public Location getTopVoter1() {
        return topVoter1;
    }

    public Location getTopVoter2() {
        return topVoter2;
    }

    public Location getTopVoter3() {
        return topVoter3;
    }

    public Location getTopVoterSign1() {
        return topVoterSign1;
    }

    public Location getTopVoterSign2() {
        return topVoterSign2;
    }

    public Location getTopVoterSign3() {
        return topVoterSign3;
    }

    public Location getLastDonatorSign() {
        return lastDonatorSign;
    }

    public NPCArmorStand getLastDonatorNpc() {
        return lastDonatorNpc;
    }

    public void setLastDonatorNpc(NPCArmorStand lastDonatorNpc) {
        this.lastDonatorNpc = lastDonatorNpc;
    }

    public String getLastDonatorString() {
        return lastDonatorString;
    }

    public void setLastDonatorString(String lastDonatorString) {
        UUID uuid = UUIDUtils.getUUID(lastDonatorString);

        if(uuid == null)
            return;

        this.lastDonatorString = lastDonatorString;

        getConfigManager().get("config").set("LastDonator", UUIDUtils.getUUID(lastDonatorString).toString());
        getConfigManager().save("config");
    }

    public Location getMiniGameLocation() {
        return miniGameLocation;
    }

    public Map<Player, HubPlayer> getPlayers() {
        return players;
    }

    public List<HubPlayer> getHubPlayers() {
        return hubPlayers;
    }

    public List<ServerPortal> getServerPortals() {
        return serverPortals;
    }

    public List<Location> getWaterfalls() {
        return waterfalls;
    }

    public List<MiniGameArena> getMiniGameArenas() {
        return miniGameArenas;
    }

    public Map<MiniGameType, Location> getMiniGameSigns() {
        return miniGameSigns;
    }

    public Map<Server, NPCArmorStand> getServerNpcs() {
        return serverNpcs;
    }

    public List<UUID> getBuilderPerms() {
        return builderPerms;
    }

    public Map<Language, Kit> getLobbyKit() {
        return lobbyKit;
    }

    public Map<Language, Kit> getMindCraftKit() {
        return mindCraftKit;
    }

    public List<String> getUpdateLines() {
        return updateLines;
    }

    /* Register */
    private void registerCommands(){
        getApi().registerCommand(new BuilderWorld1Command());
        getApi().registerCommand(new BuilderWorld2Command());
        getApi().registerCommand(new CageBuilderCommand());
        getApi().unregisterCommand("/give");
        getApi().registerCommand(new HubGiveCommand());
        getApi().unregisterCommand("/skull");
        getApi().registerCommand(new HubSkullCommand());
        getApi().unregisterCommand("/teleport");
        getApi().registerCommand(new HubTeleportCommand());

        HubConsoleCommandExecuter ccE = new HubConsoleCommandExecuter();
        List<String> list = Arrays.asList("resetMonthlyVIPPoints", "giveMonthlyVIPPoints");

        for(String command : list){
            getCommand(command).setExecutor(ccE);
        }
    }

    private void registerBungee(){
        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", new BungeeMessageEvent());
        getServer().getMessenger().registerOutgoingPluginChannel(this, "OrbitMinesHub");
        getServer().getMessenger().registerIncomingPluginChannel(this, "OrbitMinesHub", new BungeeMessageEvent());
    }

    private void registerEvents(){
        getServer().getPluginManager().registerEvents(new BreakEvent(), this);
        getServer().getPluginManager().registerEvents(new ClickEvent(), this);
        getServer().getPluginManager().registerEvents(new DamageEvent(), this);
        getServer().getPluginManager().registerEvents(new DropEvent(), this);
        getServer().getPluginManager().registerEvents(new EntityInteractEvent(), this);
        getServer().getPluginManager().registerEvents(new FoodEvent(), this);
        getServer().getPluginManager().registerEvents(new InteractEvent(), this);
        getServer().getPluginManager().registerEvents(new JoinEvent(), this);
        getServer().getPluginManager().registerEvents(new MoveEvent(), this);
        getServer().getPluginManager().registerEvents(new PickupEvent(), this);
        getServer().getPluginManager().registerEvents(new PlaceEvent(), this);
        getServer().getPluginManager().registerEvents(new SignEvent(), this);
    }

    private void registerCurrencies(){
        getApi().registerCurrency(new Currency("Coin", "Coins", "§f§l", Material.SNOW_BALL));
        getApi().registerCurrency(new Currency("Ticket", "Tickets", "§f§l", Material.NAME_TAG));
    }

    private void registerRunnables(){
        new MindcraftNpcRunnable();
        new MiniGameRunnable();
        new RecentDonatorRunnable();
        new ServerNpcRunnable();
        new WaterfallRunnable();
    }

    private void spawnNpcs(){
        {
            NPC npc = new NPC(Mob.SKELETON, new Location(getLobby(), -37.5, 75, 40.5, -130, 0), "§1§lLapis Parkour §8| §b§l250 VIP Points", new NPC.InteractAction() {
                @Override
                public void click(Player player, NPC npc) {
                    HubPlayer omp = HubPlayer.getHubPlayer(player);

                    if(omp.isInLapisParkour())
                        omp.leaveLapisParkour();
                    else
                        omp.joinLapisParkour();
                }
            });
            npc.setSkeletonType(Skeleton.SkeletonType.WITHER);
            npc.setHelmet(new ItemStack(Material.LAPIS_BLOCK));
            npc.setItemInHand(new ItemStack(Material.DIAMOND));
            npc.setChestplate(ItemUtils.addColor(new ItemStack(Material.LEATHER_CHESTPLATE), Color.NAVY));
            npc.setLeggings(ItemUtils.addColor(new ItemStack(Material.LEATHER_LEGGINGS), Color.NAVY));
            npc.setBoots(ItemUtils.addColor(new ItemStack(Material.LEATHER_BOOTS), Color.NAVY));

            getApi().registerNpc(npc);
        }
        {
            NPC npc = new NPC(Mob.SKELETON, new Location(getLobby(), 31.5, 75, 5.5, 140, 0), "§c§lMindCraft §7| §e§lJoin", new NPC.InteractAction() {
                @Override
                public void click(Player player, NPC npc) {
                    HubPlayer omp = HubPlayer.getHubPlayer(player);

                    if(omp.isInMindcraft())
                        omp.getMindCraftPlayer().leave();
                    else
                        omp.getMindCraftPlayer().join();
                }
            });
            npc.setSkeletonType(Skeleton.SkeletonType.WITHER);
            npc.setItemInHand(new ItemStack(Material.WOOL));
            npc.setHelmet(ItemUtils.addColor(new ItemStack(Material.LEATHER_HELMET), Color.BLACK));
            npc.setChestplate(ItemUtils.addColor(new ItemStack(Material.LEATHER_CHESTPLATE), Color.BLACK));
            npc.setLeggings(ItemUtils.addColor(new ItemStack(Material.LEATHER_LEGGINGS), Color.BLACK));
            npc.setBoots(ItemUtils.addColor(new ItemStack(Material.LEATHER_BOOTS), Color.BLACK));

            getApi().registerNpc(npc);
            getMindCraft().setNpc(npc);
        }
        {
            NPC npc = new NPC(Mob.SNOWMAN, new Location(getLobby(), -54.5, 76, 64.5, -90, 0), "§f§lMiniGames", new NPC.InteractAction() {
                @Override
                public void click(Player player, NPC npc) {
                    new MiniGamesInv().open(player);
                }
            });

            getApi().registerNpc(npc);
        }
        {
            Hologram h = new Hologram(new Location(getLobby(), -3.5, 76.5, 0.5, -90, 0));
            for(String s : getUpdateLines()){
                h.addLine(ChatColor.translateAlternateColorCodes('&', s));
            }
            h.create();

            getApi().registerHologram(h);
        }
        {
            NPCArmorStand npcas = new NPCArmorStand(new Location(getLobby(), -3.5, 75, -3.5, -45, 0), new NPCArmorStand.InteractAction(){
                @Override
                public void click(Player player, NPCArmorStand npcArmorStand) {
                    getApi().getServerSelector().open(player);
                }
            });
            npcas.setGravity(false);
            npcas.setVisible(false);
            npcas.setSmall(true);
            npcas.setUseItem(true);
            npcas.setItemStack(new ItemStack(Material.ENDER_PEARL, 1));
            npcas.setItemName("§3§lServer Selector");
            npcas.spawn();

            getApi().registerNpcArmorStand(npcas);
        }
        {
            NPCArmorStand npcas = new NPCArmorStand(new Location(getLobby(), -6.5, 76, 8.5, -140, 0));
            npcas.setCustomName("§7Recente Donateur");
            npcas.setCustomNameVisible(true);
            npcas.setBodyPose(EulerAngle.ZERO.setX(-0.1).setY(0).setZ(0));
            npcas.setHeadPose(EulerAngle.ZERO.setX(-0.15).setY(0).setZ(0));
            npcas.setLeftArmPose(EulerAngle.ZERO.setX(0).setY(0).setZ(0));
            npcas.setLeftLegPose(EulerAngle.ZERO.setX(-0.5).setY(0).setZ(0));
            npcas.setRightArmPose(EulerAngle.ZERO.setX(-0.75).setY(0).setZ(0));
            npcas.setRightLegPose(EulerAngle.ZERO.setX(0.5).setY(0).setZ(0));
            npcas.setGravity(false);
            npcas.setVisible(false);
            npcas.setSmall(true);
            npcas.setHelmet(new ItemStack(Material.SKULL_ITEM, 1));
            npcas.setChestplate(new ItemStack(Material.CHAINMAIL_CHESTPLATE, 1));
            npcas.setLeggings(new ItemStack(Material.CHAINMAIL_LEGGINGS, 1));
            npcas.setBoots(new ItemStack(Material.CHAINMAIL_BOOTS, 1));
            npcas.setItemInHand(new ItemStack(Material.ENDER_CHEST, 1));
            npcas.spawn();

            getApi().registerNpcArmorStand(npcas);
            setLastDonatorNpc(npcas);
        }
        {
            NPCArmorStand npcas = new NPCArmorStand(new Location(getLobby(), -4.0, 76, 0.5, -90, 0));
            npcas.setCustomName("§7Welkom op het §6§lOrbitMines§4§lNetwork§7!");
            npcas.setCustomNameVisible(true);
            npcas.setBodyPose(EulerAngle.ZERO.setX(0.1).setY(0).setZ(0));
            npcas.setHeadPose(EulerAngle.ZERO.setX(0.15).setY(0).setZ(0));
            npcas.setLeftArmPose(EulerAngle.ZERO.setX(0).setY(0).setZ(0));
            npcas.setLeftLegPose(EulerAngle.ZERO.setX(0).setY(0).setZ(-0.2));
            npcas.setRightArmPose(EulerAngle.ZERO.setX(-0.75).setY(0).setZ(0));
            npcas.setRightLegPose(EulerAngle.ZERO.setX(0).setY(0).setZ(0.2));
            npcas.setGravity(false);
            npcas.setVisible(false);
            npcas.setSmall(true);
            npcas.setHelmet(new ItemStack(Material.DIAMOND_BLOCK, 1));
            npcas.setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE, 1));
            npcas.setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS, 1));
            npcas.setBoots(new ItemStack(Material.DIAMOND_BOOTS, 1));
            npcas.setItemInHand(new ItemStack(Material.DIAMOND_SWORD, 1));
            npcas.spawn();

            getApi().registerNpcArmorStand(npcas);
        }
        {
            NPCArmorStand npcas = new NPCArmorStand(new Location(getLobby(), -23.5, 77, 8.5, -90, 0));
            npcas.setCustomName("§4§lOwner §4O_o_Fadi_o_O");
            npcas.setBodyPose(EulerAngle.ZERO.setX(0.1).setY(0).setZ(0));
            npcas.setHeadPose(EulerAngle.ZERO.setX(0.15).setY(0).setZ(0));
            npcas.setLeftArmPose(EulerAngle.ZERO.setX(0).setY(0).setZ(0));
            npcas.setLeftLegPose(EulerAngle.ZERO.setX(0).setY(0).setZ(0));
            npcas.setRightArmPose(EulerAngle.ZERO.setX(-0.75).setY(0).setZ(0));
            npcas.setRightLegPose(EulerAngle.ZERO.setX(0).setY(0).setZ(0));
            npcas.setGravity(false);
            npcas.setVisible(false);
            npcas.setSmall(true);
            npcas.setHelmet(ItemUtils.getSkull("O_o_Fadi_o_O"));
            npcas.setChestplate(ItemUtils.addColor(new ItemStack(Material.LEATHER_CHESTPLATE, 1), Color.RED));
            npcas.setLeggings(ItemUtils.addColor(new ItemStack(Material.LEATHER_LEGGINGS, 1), Color.RED));
            npcas.setBoots(ItemUtils.addColor(new ItemStack(Material.LEATHER_BOOTS, 1), Color.RED));
            npcas.setItemInHand(new ItemStack(Material.REDSTONE_COMPARATOR, 1));
            npcas.spawn();

            getApi().registerNpcArmorStand(npcas);
        }
        {
            NPCArmorStand npcas = new NPCArmorStand(new Location(getLobby(), -23.5, 77, 7.5, -90, 0));
            npcas.setCustomName("§b§lMob §beekhoorn2000");
            npcas.setBodyPose(EulerAngle.ZERO.setX(0.1).setY(0).setZ(0));
            npcas.setHeadPose(EulerAngle.ZERO.setX(0.15).setY(0).setZ(0));
            npcas.setLeftArmPose(EulerAngle.ZERO.setX(0).setY(0).setZ(0));
            npcas.setLeftLegPose(EulerAngle.ZERO.setX(0).setY(0).setZ(0));
            npcas.setRightArmPose(EulerAngle.ZERO.setX(-0.75).setY(0).setZ(0));
            npcas.setRightLegPose(EulerAngle.ZERO.setX(0).setY(0).setZ(0));
            npcas.setGravity(false);
            npcas.setVisible(false);
            npcas.setSmall(true);
            npcas.setHelmet(ItemUtils.getSkull("eekhoorn2000"));
            npcas.setChestplate(ItemUtils.addColor(new ItemStack(Material.LEATHER_CHESTPLATE, 1), Color.AQUA));
            npcas.setLeggings(ItemUtils.addColor(new ItemStack(Material.LEATHER_LEGGINGS, 1), Color.AQUA));
            npcas.setBoots(ItemUtils.addColor(new ItemStack(Material.LEATHER_BOOTS, 1), Color.AQUA));
            npcas.setItemInHand(new ItemStack(Material.COMPASS, 1));
            npcas.spawn();

            getApi().registerNpcArmorStand(npcas);
        }
        {
            NPCArmorStand npcas = new NPCArmorStand(new Location(getLobby(), -23.5, 77, 6.5, -90, 0));
            npcas.setCustomName("§b§lMob §bsharewoods");
            npcas.setBodyPose(EulerAngle.ZERO.setX(0.1).setY(0).setZ(0));
            npcas.setHeadPose(EulerAngle.ZERO.setX(0.15).setY(0).setZ(0));
            npcas.setLeftArmPose(EulerAngle.ZERO.setX(0).setY(0).setZ(0));
            npcas.setLeftLegPose(EulerAngle.ZERO.setX(0).setY(0).setZ(0));
            npcas.setRightArmPose(EulerAngle.ZERO.setX(-0.75).setY(0).setZ(0));
            npcas.setRightLegPose(EulerAngle.ZERO.setX(0).setY(0).setZ(0));
            npcas.setGravity(false);
            npcas.setVisible(false);
            npcas.setSmall(true);
            npcas.setHelmet(ItemUtils.getSkull("sharewoods"));
            npcas.setChestplate(ItemUtils.addColor(new ItemStack(Material.LEATHER_CHESTPLATE, 1), Color.AQUA));
            npcas.setLeggings(ItemUtils.addColor(new ItemStack(Material.LEATHER_LEGGINGS, 1), Color.AQUA));
            npcas.setBoots(ItemUtils.addColor(new ItemStack(Material.LEATHER_BOOTS, 1), Color.AQUA));
            npcas.setItemInHand(new ItemStack(Material.COMPASS, 1));
            npcas.spawn();

            getApi().registerNpcArmorStand(npcas);
        }
        {
            NPCArmorStand npcas = new NPCArmorStand(new Location(getLobby(), -23.5, 77, 5.5, -90, 0));
            npcas.setCustomName("§b§lMob §bAlderius");
            npcas.setBodyPose(EulerAngle.ZERO.setX(0.1).setY(0).setZ(0));
            npcas.setHeadPose(EulerAngle.ZERO.setX(0.15).setY(0).setZ(0));
            npcas.setLeftArmPose(EulerAngle.ZERO.setX(0).setY(0).setZ(0));
            npcas.setLeftLegPose(EulerAngle.ZERO.setX(0).setY(0).setZ(0));
            npcas.setRightArmPose(EulerAngle.ZERO.setX(-0.75).setY(0).setZ(0));
            npcas.setRightLegPose(EulerAngle.ZERO.setX(0).setY(0).setZ(0));
            npcas.setGravity(false);
            npcas.setVisible(false);
            npcas.setSmall(true);
            npcas.setHelmet(ItemUtils.getSkull("Alderius"));
            npcas.setChestplate(ItemUtils.addColor(new ItemStack(Material.LEATHER_CHESTPLATE, 1), Color.AQUA));
            npcas.setLeggings(ItemUtils.addColor(new ItemStack(Material.LEATHER_LEGGINGS, 1), Color.AQUA));
            npcas.setBoots(ItemUtils.addColor(new ItemStack(Material.LEATHER_BOOTS, 1), Color.AQUA));
            npcas.setItemInHand(new ItemStack(Material.COMPASS, 1));
            npcas.spawn();

            getApi().registerNpcArmorStand(npcas);
        }
        {
            NPCArmorStand npcas = new NPCArmorStand(new Location(getLobby(), -23.5, 77, 4.5, -90, 0));
            npcas.setCustomName("§d§lBuilder §dcasidas");
            npcas.setBodyPose(EulerAngle.ZERO.setX(0.1).setY(0).setZ(0));
            npcas.setHeadPose(EulerAngle.ZERO.setX(0.15).setY(0).setZ(0));
            npcas.setLeftArmPose(EulerAngle.ZERO.setX(0).setY(0).setZ(0));
            npcas.setLeftLegPose(EulerAngle.ZERO.setX(0).setY(0).setZ(0));
            npcas.setRightArmPose(EulerAngle.ZERO.setX(-0.75).setY(0).setZ(0));
            npcas.setRightLegPose(EulerAngle.ZERO.setX(0).setY(0).setZ(0));
            npcas.setGravity(false);
            npcas.setVisible(false);
            npcas.setSmall(true);
            npcas.setHelmet(ItemUtils.getSkull("casidas"));
            npcas.setChestplate(ItemUtils.addColor(new ItemStack(Material.LEATHER_CHESTPLATE, 1), Color.FUCHSIA));
            npcas.setLeggings(ItemUtils.addColor(new ItemStack(Material.LEATHER_LEGGINGS, 1), Color.FUCHSIA));
            npcas.setBoots(ItemUtils.addColor(new ItemStack(Material.LEATHER_BOOTS, 1), Color.FUCHSIA));
            npcas.setItemInHand(new ItemStack(Material.WOOD_AXE, 1));
            npcas.spawn();

            getApi().registerNpcArmorStand(npcas);
        }
        {
            NPCArmorStand npcas = new NPCArmorStand(new Location(getLobby(), -49.5, 78, 3.5, -90, 0));
            npcas.setHeadPose(EulerAngle.ZERO.setX(0.15).setY(0).setZ(0));
            npcas.setLeftArmPose(EulerAngle.ZERO.setX(0).setY(0).setZ(0));
            npcas.setLeftLegPose(EulerAngle.ZERO.setX(0).setY(0).setZ(0));
            npcas.setRightArmPose(EulerAngle.ZERO.setX(-0.75).setY(0).setZ(0));
            npcas.setRightLegPose(EulerAngle.ZERO.setX(0).setY(0).setZ(0));
            npcas.setGravity(false);
            npcas.setVisible(false);
            npcas.setHelmet(new ItemStack(Material.CHAINMAIL_HELMET, 1));
            npcas.setChestplate(new ItemStack(Material.CHAINMAIL_CHESTPLATE, 1));
            npcas.setLeggings(new ItemStack(Material.CHAINMAIL_LEGGINGS, 1));
            npcas.setBoots(new ItemStack(Material.CHAINMAIL_BOOTS, 1));
            npcas.setItemInHand(new ItemStack(Material.IRON_SWORD, 1));
            npcas.spawn();

            getApi().registerNpcArmorStand(npcas);
            getServerNpcs().put(Server.KITPVP, npcas);
        }
        {
            NPCArmorStand npcas = new NPCArmorStand(new Location(getLobby(), -43.5, 78, -33.5, -90, 0));
            npcas.setHeadPose(EulerAngle.ZERO.setX(0.15).setY(0).setZ(0));
            npcas.setLeftArmPose(EulerAngle.ZERO.setX(0).setY(0).setZ(0));
            npcas.setLeftLegPose(EulerAngle.ZERO.setX(0).setY(0).setZ(0));
            npcas.setRightArmPose(EulerAngle.ZERO.setX(-0.75).setY(0).setZ(0));
            npcas.setRightLegPose(EulerAngle.ZERO.setX(0).setY(0).setZ(0));
            npcas.setGravity(false);
            npcas.setVisible(false);
            npcas.setHelmet(new ItemStack(Material.IRON_HELMET, 1));
            npcas.setChestplate(new ItemStack(Material.IRON_CHESTPLATE, 1));
            npcas.setLeggings(new ItemStack(Material.IRON_LEGGINGS, 1));
            npcas.setBoots(new ItemStack(Material.IRON_BOOTS, 1));
            npcas.setItemInHand(ItemUtils.addEnchantment(new ItemStack(Material.DIAMOND_PICKAXE, 1), Enchantment.DIG_SPEED, 1));
            npcas.spawn();

            getApi().registerNpcArmorStand(npcas);
            getServerNpcs().put(Server.PRISON, npcas);
        }
        {
            NPCArmorStand npcas = new NPCArmorStand(new Location(getLobby(), -38.5, 78, -63.5, -90, 0));
            npcas.setHeadPose(EulerAngle.ZERO.setX(0.15).setY(0).setZ(0));
            npcas.setLeftArmPose(EulerAngle.ZERO.setX(0).setY(0).setZ(0));
            npcas.setLeftLegPose(EulerAngle.ZERO.setX(0).setY(0).setZ(0));
            npcas.setRightArmPose(EulerAngle.ZERO.setX(-0.75).setY(0).setZ(0));
            npcas.setRightLegPose(EulerAngle.ZERO.setX(0).setY(0).setZ(0));
            npcas.setGravity(false);
            npcas.setVisible(false);
            npcas.setHelmet(new ItemStack(Material.GOLD_HELMET, 1));
            npcas.setChestplate(new ItemStack(Material.GOLD_CHESTPLATE, 1));
            npcas.setLeggings(new ItemStack(Material.GOLD_LEGGINGS, 1));
            npcas.setBoots(new ItemStack(Material.GOLD_BOOTS, 1));
            npcas.setItemInHand(new ItemStack(Material.WOOD_AXE, 1));
            npcas.spawn();

            getApi().registerNpcArmorStand(npcas);
            getServerNpcs().put(Server.CREATIVE, npcas);
        }
        {
            NPCArmorStand npcas = new NPCArmorStand(new Location(getLobby(), 7.5, 78, -65.5, 0, 0));
            npcas.setHeadPose(EulerAngle.ZERO.setX(0.15).setY(0).setZ(0));
            npcas.setLeftArmPose(EulerAngle.ZERO.setX(0).setY(0).setZ(0));
            npcas.setLeftLegPose(EulerAngle.ZERO.setX(0).setY(0).setZ(0));
            npcas.setRightArmPose(EulerAngle.ZERO.setX(-0.75).setY(0).setZ(0));
            npcas.setRightLegPose(EulerAngle.ZERO.setX(0).setY(0).setZ(0));
            npcas.setGravity(false);
            npcas.setVisible(false);
            npcas.setHelmet(new ItemStack(Material.LEATHER_HELMET, 1));
            npcas.setChestplate(new ItemStack(Material.IRON_CHESTPLATE, 1));
            npcas.setLeggings(new ItemStack(Material.LEATHER_LEGGINGS, 1));
            npcas.setBoots(new ItemStack(Material.LEATHER_BOOTS, 1));
            npcas.setItemInHand(new ItemStack(Material.STONE_HOE, 1));
            npcas.spawn();

            getApi().registerNpcArmorStand(npcas);
            getServerNpcs().put(Server.SURVIVAL, npcas);
        }
        {
            NPCArmorStand npcas = new NPCArmorStand(new Location(getLobby(), -9.5, 78, 74.5, -180, 0));
            npcas.setHeadPose(EulerAngle.ZERO.setX(0.15).setY(0).setZ(0));
            npcas.setLeftArmPose(EulerAngle.ZERO.setX(0).setY(0).setZ(0));
            npcas.setLeftLegPose(EulerAngle.ZERO.setX(0).setY(0).setZ(0));
            npcas.setRightArmPose(EulerAngle.ZERO.setX(-0.75).setY(0).setZ(0));
            npcas.setRightLegPose(EulerAngle.ZERO.setX(0).setY(0).setZ(0));
            npcas.setGravity(false);
            npcas.setVisible(false);
            npcas.setHelmet(new ItemStack(Material.LEATHER_HELMET, 1));
            npcas.setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE, 1));
            npcas.setLeggings(new ItemStack(Material.LEATHER_LEGGINGS, 1));
            npcas.setBoots(new ItemStack(Material.LEATHER_BOOTS, 1));
            npcas.setItemInHand(new ItemStack(Material.FISHING_ROD, 1));
            npcas.spawn();

            getApi().registerNpcArmorStand(npcas);
            getServerNpcs().put(Server.SKYBLOCK, npcas);
        }
        {
            NPCArmorStand npcas = new NPCArmorStand(new Location(getLobby(), -17.5, 78, -69.5, 0, 0));
            npcas.setHeadPose(EulerAngle.ZERO.setX(0.15).setY(0).setZ(0));
            npcas.setLeftArmPose(EulerAngle.ZERO.setX(0).setY(0).setZ(0));
            npcas.setLeftLegPose(EulerAngle.ZERO.setX(0).setY(0).setZ(0));
            npcas.setRightArmPose(EulerAngle.ZERO.setX(-0.75).setY(0).setZ(0));
            npcas.setRightLegPose(EulerAngle.ZERO.setX(0).setY(0).setZ(0));
            npcas.setGravity(false);
            npcas.setVisible(false);
            npcas.setHelmet(ItemUtils.addColor(new ItemStack(Material.LEATHER_HELMET), Color.RED));
            npcas.setChestplate(ItemUtils.addColor(new ItemStack(Material.LEATHER_CHESTPLATE), Color.YELLOW));
            npcas.setLeggings(ItemUtils.addColor(new ItemStack(Material.LEATHER_LEGGINGS), Color.RED));
            npcas.setBoots(ItemUtils.addColor(new ItemStack(Material.LEATHER_BOOTS), Color.BLUE));
            npcas.setItemInHand(new ItemStack(Material.MAP, 1));
            npcas.spawn();

            getApi().registerNpcArmorStand(npcas);
            getServerNpcs().put(Server.FOG, npcas);
        }
    }

    private void registerLobbyKit(){
        {
            Kit kit = new Kit("Lobby_" + Language.ENGLISH.toString());
            {
                ItemStack item = new ItemStack(Material.WRITTEN_BOOK);
                BookMeta itemmeta = (BookMeta) item.getItemMeta();
                itemmeta.setDisplayName("§4§nServer Rules");
                itemmeta.addPage("1");
                itemmeta.setPage(1, "   §6§lOrbitMines§4§lRules" + "\n" + "§0§m-------------------" + "\n" + "§4DO NOT§0 Advertise!" + "\n" + "§0Watch your Language!" + "\n" + "Listen to Staff!" + "\n" + "§4DO NOT§0 Abuse Bugs!" + "\n" + "§4DO NOT§0 Hack!" + "\n" + "§4DO NOT§0 Spam!" + "\n" + "§4DO NOT§0 Bully Players!" + "\n" + "§0\n" + "§0§lHave Fun!");
                itemmeta.setAuthor("§6§lOrbitMines §4§lNetwork");
                item.setItemMeta(itemmeta);
                kit.setItem(0, item);
            }
            kit.setItem(1, ItemUtils.itemstack(Material.EXP_BOTTLE, 1, "§d§nAchievements"));
            kit.setItem(3, ItemUtils.itemstack(Material.REDSTONE_TORCH_ON, 1, "§c§nSettings"));
            kit.setItem(4, ItemUtils.itemstack(Material.ENDER_PEARL, 1, "§3§nServer Selector"));
            kit.setItem(7, ItemUtils.itemstack(Material.ENDER_CHEST, 1, "§9§nCosmetic Perks"));
            kit.setItem(8, ItemUtils.itemstack(Material.FEATHER, 1, "§f§nFly"));

            getApi().registerKit(kit);
            getLobbyKit().put(Language.ENGLISH, kit);
        }
        {
            Kit kit = new Kit("Lobby_" + Language.DUTCH.toString());
            {
                ItemStack item = new ItemStack(Material.WRITTEN_BOOK);
                BookMeta itemmeta = (BookMeta) item.getItemMeta();
                itemmeta.setDisplayName("§4§nServer Regels");
                itemmeta.addPage("1");
                itemmeta.setPage(1, "   §6§lOrbitMines§4§lRegels" + "\n" + "§0§m-------------------" + "\n" + "§4NIET§0 Adverteren!" + "\n" + "§0Let op je taalgebruik!" + "\n" + "Luister naar de Staff!" + "\n" + "§4GEEN§0 Bugs gebruiken!" + "\n" + "§4NIET§0 hacken!" + "\n" + "§4NIET§0 spammen!" + "\n" + "§4NIET§0 spelers pesten!" + "\n" + "§0\n" + "§0§lVeel Plezier!");
                itemmeta.setAuthor("§6§lOrbitMines §4§lNetwork");
                item.setItemMeta(itemmeta);
                kit.setItem(0, item);
            }
            kit.setItem(1, ItemUtils.itemstack(Material.EXP_BOTTLE, 1, "§d§nAchievements"));
            kit.setItem(3, ItemUtils.itemstack(Material.REDSTONE_TORCH_ON, 1, "§c§nInstellingen"));
            kit.setItem(4, ItemUtils.itemstack(Material.ENDER_PEARL, 1, "§3§nServer Selector"));
            kit.setItem(7, ItemUtils.itemstack(Material.ENDER_CHEST, 1, "§9§nCosmetic Perks"));
            kit.setItem(8, ItemUtils.itemstack(Material.FEATHER, 1, "§f§nFly"));

            /* Pet Abilities */
            kit.setItem(2, new ItemStack(Material.AIR));
            kit.setItem(6, new ItemStack(Material.AIR));

            getApi().registerKit(kit);
            getLobbyKit().put(Language.DUTCH, kit);
        }
    }

    private void registerServerPortals(){
        getServerPortals().add(new ServerPortal(Server.KITPVP, WorldUtils.getBlocksBetween(new Location(getLobby(), -52, 75, 6), new Location(getLobby(), -52, 87, 0))));
        getServerPortals().add(new ServerPortal(Server.PRISON, WorldUtils.getBlocksBetween(new Location(getLobby(), -46, 87, -37), new Location(getLobby(), -46, 75, -31))));
        getServerPortals().add(new ServerPortal(Server.CREATIVE, WorldUtils.getBlocksBetween(new Location(getLobby(), -41, 75, -61), new Location(getLobby(), -41, 87, -67))));
        getServerPortals().add(new ServerPortal(Server.SURVIVAL, WorldUtils.getBlocksBetween(new Location(getLobby(), 4, 75, -68), new Location(getLobby(), 10, 87, -68))));
        getServerPortals().add(new ServerPortal(Server.SKYBLOCK, WorldUtils.getBlocksBetween(new Location(getLobby(), -7, 75, 76), new Location(getLobby(), -13, 86, 76))));
        getServerPortals().add(new ServerPortal(Server.FOG, WorldUtils.getBlocksBetween(new Location(getLobby(), -21, 75, -72), new Location(getLobby(), -15, 87, -72))));
    }

    private void registerWaterFalls(){
        getWaterfalls().add(new Location(getLobby(), -16.5, 74.25, 56.5));
        getWaterfalls().add(new Location(getLobby(), 40.5, 81.25, -64.5));
    }

    private void registerMiniGameArenas(){
        getMiniGameArenas().add(new MiniGameArena(MiniGameType.SURVIVAL_GAMES, 1, State.WAITING, new Location(getLobby(), -52, 77, 74)));
        getMiniGameArenas().add(new MiniGameArena(MiniGameType.SURVIVAL_GAMES, 2, State.WAITING, new Location(getLobby(), -53, 77, 74)));
        getMiniGameArenas().add(new MiniGameArena(MiniGameType.SURVIVAL_GAMES, 3, State.WAITING, new Location(getLobby(), -54, 77, 74)));
        getMiniGameArenas().add(new MiniGameArena(MiniGameType.SURVIVAL_GAMES, 4, new Location(getLobby(), -52, 76, 74)));
        getMiniGameArenas().add(new MiniGameArena(MiniGameType.SURVIVAL_GAMES, 5, new Location(getLobby(), -53, 76, 74)));
        getMiniGameArenas().add(new MiniGameArena(MiniGameType.SURVIVAL_GAMES, 6, new Location(getLobby(), -54, 76, 74)));
        getMiniGameArenas().add(new MiniGameArena(MiniGameType.SURVIVAL_GAMES, 7, new Location(getLobby(), -52, 75, 74)));
        getMiniGameArenas().add(new MiniGameArena(MiniGameType.SURVIVAL_GAMES, 8, new Location(getLobby(), -53, 75, 74)));
        getMiniGameArenas().add(new MiniGameArena(MiniGameType.SURVIVAL_GAMES, 9, new Location(getLobby(), -54, 75, 74)));

        getMiniGameArenas().add(new MiniGameArena(MiniGameType.ULTRA_HARD_CORE, 1, State.WAITING, new Location(getLobby(), -55, 77, 74)));
        getMiniGameArenas().add(new MiniGameArena(MiniGameType.ULTRA_HARD_CORE, 2, new Location(getLobby(), -56, 77, 74)));
        getMiniGameArenas().add(new MiniGameArena(MiniGameType.ULTRA_HARD_CORE, 3, new Location(getLobby(), -57, 77, 74)));
        getMiniGameArenas().add(new MiniGameArena(MiniGameType.ULTRA_HARD_CORE, 4, new Location(getLobby(), -55, 76, 74)));
        getMiniGameArenas().add(new MiniGameArena(MiniGameType.ULTRA_HARD_CORE, 5, new Location(getLobby(), -56, 76, 74)));
        getMiniGameArenas().add(new MiniGameArena(MiniGameType.ULTRA_HARD_CORE, 6, new Location(getLobby(), -57, 76, 74)));
        getMiniGameArenas().add(new MiniGameArena(MiniGameType.ULTRA_HARD_CORE, 7, new Location(getLobby(), -55, 75, 74)));
        getMiniGameArenas().add(new MiniGameArena(MiniGameType.ULTRA_HARD_CORE, 8, new Location(getLobby(), -56, 75, 74)));
        getMiniGameArenas().add(new MiniGameArena(MiniGameType.ULTRA_HARD_CORE, 9, new Location(getLobby(), -57, 75, 74)));

        getMiniGameArenas().add(new MiniGameArena(MiniGameType.SKYWARS, 1, State.WAITING, new Location(getLobby(), -58, 77, 74)));
        getMiniGameArenas().add(new MiniGameArena(MiniGameType.SKYWARS, 2, State.WAITING, new Location(getLobby(), -59, 77, 74)));
        getMiniGameArenas().add(new MiniGameArena(MiniGameType.SKYWARS, 3, State.WAITING, new Location(getLobby(), -60, 77, 74)));
        getMiniGameArenas().add(new MiniGameArena(MiniGameType.SKYWARS, 4, new Location(getLobby(), -58, 76, 74)));
        getMiniGameArenas().add(new MiniGameArena(MiniGameType.SKYWARS, 5, new Location(getLobby(), -59, 76, 74)));
        getMiniGameArenas().add(new MiniGameArena(MiniGameType.SKYWARS, 6, new Location(getLobby(), -60, 76, 74)));
        getMiniGameArenas().add(new MiniGameArena(MiniGameType.SKYWARS, 7, new Location(getLobby(), -58, 75, 74)));
        getMiniGameArenas().add(new MiniGameArena(MiniGameType.SKYWARS, 8, new Location(getLobby(), -59, 75, 74)));
        getMiniGameArenas().add(new MiniGameArena(MiniGameType.SKYWARS, 9, new Location(getLobby(), -60, 75, 74)));

        getMiniGameArenas().add(new MiniGameArena(MiniGameType.CHICKEN_FIGHT, 1, State.WAITING, new Location(getLobby(), -66, 77, 66)));
        getMiniGameArenas().add(new MiniGameArena(MiniGameType.CHICKEN_FIGHT, 2, State.WAITING, new Location(getLobby(), -66, 77, 65)));
        getMiniGameArenas().add(new MiniGameArena(MiniGameType.CHICKEN_FIGHT, 3, State.WAITING, new Location(getLobby(), -66, 77, 64)));
        getMiniGameArenas().add(new MiniGameArena(MiniGameType.CHICKEN_FIGHT, 4, new Location(getLobby(), -66, 76, 66)));
        getMiniGameArenas().add(new MiniGameArena(MiniGameType.CHICKEN_FIGHT, 5, new Location(getLobby(), -66, 76, 65)));
        getMiniGameArenas().add(new MiniGameArena(MiniGameType.CHICKEN_FIGHT, 6, new Location(getLobby(), -66, 76, 64)));
        getMiniGameArenas().add(new MiniGameArena(MiniGameType.CHICKEN_FIGHT, 7, new Location(getLobby(), -66, 75, 66)));
        getMiniGameArenas().add(new MiniGameArena(MiniGameType.CHICKEN_FIGHT, 8, new Location(getLobby(), -66, 75, 65)));
        getMiniGameArenas().add(new MiniGameArena(MiniGameType.CHICKEN_FIGHT, 9, new Location(getLobby(), -66, 75, 64)));

        getMiniGameArenas().add(new MiniGameArena(MiniGameType.GHOST_ATTACK, 1, new Location(getLobby(), -66, 77, 63)));
        getMiniGameArenas().add(new MiniGameArena(MiniGameType.GHOST_ATTACK, 2, new Location(getLobby(), -66, 77, 62)));
        getMiniGameArenas().add(new MiniGameArena(MiniGameType.GHOST_ATTACK, 3, new Location(getLobby(), -66, 77, 61)));
        getMiniGameArenas().add(new MiniGameArena(MiniGameType.GHOST_ATTACK, 4, new Location(getLobby(), -66, 76, 63)));
        getMiniGameArenas().add(new MiniGameArena(MiniGameType.GHOST_ATTACK, 5, new Location(getLobby(), -66, 76, 62)));
        getMiniGameArenas().add(new MiniGameArena(MiniGameType.GHOST_ATTACK, 6, new Location(getLobby(), -66, 76, 61)));
        getMiniGameArenas().add(new MiniGameArena(MiniGameType.GHOST_ATTACK, 7, new Location(getLobby(), -66, 75, 63)));
        getMiniGameArenas().add(new MiniGameArena(MiniGameType.GHOST_ATTACK, 8, new Location(getLobby(), -66, 75, 62)));
        getMiniGameArenas().add(new MiniGameArena(MiniGameType.GHOST_ATTACK, 9, new Location(getLobby(), -66, 75, 61)));

        getMiniGameArenas().add(new MiniGameArena(MiniGameType.SPLEEF, 1, new Location(getLobby(), -66, 77, 60)));
        getMiniGameArenas().add(new MiniGameArena(MiniGameType.SPLEEF, 2, new Location(getLobby(), -66, 77, 59)));
        getMiniGameArenas().add(new MiniGameArena(MiniGameType.SPLEEF, 3, new Location(getLobby(), -66, 77, 58)));
        getMiniGameArenas().add(new MiniGameArena(MiniGameType.SPLEEF, 4, new Location(getLobby(), -66, 76, 60)));
        getMiniGameArenas().add(new MiniGameArena(MiniGameType.SPLEEF, 5, new Location(getLobby(), -66, 76, 59)));
        getMiniGameArenas().add(new MiniGameArena(MiniGameType.SPLEEF, 6, new Location(getLobby(), -66, 76, 58)));
        getMiniGameArenas().add(new MiniGameArena(MiniGameType.SPLEEF, 7, new Location(getLobby(), -66, 75, 60)));
        getMiniGameArenas().add(new MiniGameArena(MiniGameType.SPLEEF, 8, new Location(getLobby(), -66, 75, 59)));
        getMiniGameArenas().add(new MiniGameArena(MiniGameType.SPLEEF, 9, new Location(getLobby(), -66, 75, 58)));

        getMiniGameArenas().add(new MiniGameArena(MiniGameType.SPLATCRAFT, 1, new Location(getLobby(), -61, 77, 52)));
        getMiniGameArenas().add(new MiniGameArena(MiniGameType.SPLATCRAFT, 2, new Location(getLobby(), -60, 77, 52)));
        getMiniGameArenas().add(new MiniGameArena(MiniGameType.SPLATCRAFT, 3, new Location(getLobby(), -59, 77, 52)));
        getMiniGameArenas().add(new MiniGameArena(MiniGameType.SPLATCRAFT, 4, new Location(getLobby(), -61, 76, 52)));
        getMiniGameArenas().add(new MiniGameArena(MiniGameType.SPLATCRAFT, 5, new Location(getLobby(), -60, 76, 52)));
        getMiniGameArenas().add(new MiniGameArena(MiniGameType.SPLATCRAFT, 6, new Location(getLobby(), -59, 76, 52)));
        getMiniGameArenas().add(new MiniGameArena(MiniGameType.SPLATCRAFT, 7, new Location(getLobby(), -61, 75, 52)));
        getMiniGameArenas().add(new MiniGameArena(MiniGameType.SPLATCRAFT, 8, new Location(getLobby(), -60, 75, 52)));
        getMiniGameArenas().add(new MiniGameArena(MiniGameType.SPLATCRAFT, 9, new Location(getLobby(), -59, 75, 52)));
    }

    private void registerMiniGameSigns(){
        getMiniGameSigns().put(MiniGameType.SURVIVAL_GAMES, new Location(getLobby(), -53, 78, 74));
        getMiniGameSigns().put(MiniGameType.ULTRA_HARD_CORE, new Location(getLobby(), -56, 78, 74));
        getMiniGameSigns().put(MiniGameType.SKYWARS, new Location(getLobby(), -59, 78, 74));
        getMiniGameSigns().put(MiniGameType.CHICKEN_FIGHT, new Location(getLobby(), -66, 78, 65));
        getMiniGameSigns().put(MiniGameType.GHOST_ATTACK, new Location(getLobby(), -66, 78, 62));
        getMiniGameSigns().put(MiniGameType.SPLEEF, new Location(getLobby(), -66, 78, 59));
        getMiniGameSigns().put(MiniGameType.SPLATCRAFT, new Location(getLobby(), -60, 78, 52));
    }

    private void registerPodium(){
        getApi().registerPodium(new Podium(new Location(getLobby(), -5, 75, 38), Direction.WEST) {
            @Override
            public String[] getLines(int index, StringInt stringInt) {
                String[] lines = new String[4];
                lines[0] = "§lTop Voter (" + (index +1) + ")";
                lines[1] = "";

                if(stringInt == null || stringInt.getString() == null){
                    lines[2] = "";
                    lines[3] = "";
                }
                else{
                    lines[2] = stringInt.getString();

                    if(stringInt.getInteger() == 1)
                        lines[3] = "§o" + stringInt.getInteger() + " Vote";
                    else
                        lines[3] = "§o" + stringInt.getInteger() + " Votes";
                }

                return lines;
            }

            @Override
            public List<StringInt> getStringInts() {
                return getApi().getVoters();
            }
        });
    }

    private boolean setupPermissions(){
        RegisteredServiceProvider<Permission> permissionProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.permission.Permission.class);
        if (permissionProvider != null) {
            permission = permissionProvider.getProvider();
        }
        return (permission != null);
    }

    /* Other */
    public void updateWaterfalls(){
        for(Location location : getWaterfalls()){
            RandomFallingBlock b = new RandomFallingBlock(location);
            b.setMaterial(Material.STAINED_GLASS);
            b.setDurability((byte) 11);
            b.setDrop(false);
            b.spawn();

            Particle p = new Particle(org.bukkit.Particle.WATER_SPLASH, location);
            p.setSize(1, 1, 1);
            p.send(Bukkit.getOnlinePlayers());
        }
    }

    private void updateSkull(StringInt vote, Location location){
        Skull topVoter = (Skull) getLobby().getBlockAt(location).getState();
        if(vote.getString() == null){
            topVoter.setOwner(null);
            topVoter.setSkullType(SkullType.SKELETON);
        }
        else{
            topVoter.setSkullType(SkullType.PLAYER);
            topVoter.setOwner(vote.getString());
        }
        topVoter.setRotation(BlockFace.WEST);
        topVoter.update();
    }
}
