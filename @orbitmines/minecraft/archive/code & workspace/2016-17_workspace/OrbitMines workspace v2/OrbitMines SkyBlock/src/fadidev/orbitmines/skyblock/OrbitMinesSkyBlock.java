package fadidev.orbitmines.skyblock;

import fadidev.orbitmines.api.OrbitMinesAPI;
import fadidev.orbitmines.api.OrbitMinesServer;
import fadidev.orbitmines.api.handlers.Kit;
import fadidev.orbitmines.api.handlers.npc.Hologram;
import fadidev.orbitmines.api.handlers.npc.NPC;
import fadidev.orbitmines.api.handlers.npc.NPCArmorStand;
import fadidev.orbitmines.api.utils.ConfigUtils;
import fadidev.orbitmines.api.utils.WorldUtils;
import fadidev.orbitmines.api.utils.enums.Mob;
import fadidev.orbitmines.api.utils.enums.ranks.VIPRank;
import fadidev.orbitmines.skyblock.cmd.*;
import fadidev.orbitmines.skyblock.events.*;
import fadidev.orbitmines.skyblock.handlers.Challenge;
import fadidev.orbitmines.skyblock.handlers.Island;
import fadidev.orbitmines.skyblock.handlers.ItemData;
import fadidev.orbitmines.skyblock.handlers.SkyBlockPlayer;
import fadidev.orbitmines.skyblock.inventories.SkyBlockOMTShopInv;
import fadidev.orbitmines.skyblock.managers.SkyBlockConfigManager;
import fadidev.orbitmines.skyblock.runnables.NetherSkyBlockRunnable;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.EulerAngle;

import java.util.*;

/**
 * Created by Fadi on 20-9-2016.
 */
public class OrbitMinesSkyBlock extends JavaPlugin {

    private static OrbitMinesSkyBlock skyBlock;
    private OrbitMinesServer omServer;
    private OrbitMinesAPI api;

    private SkyBlockConfigManager configManager;

    private World lobby;
    private World skyBlockWorld;
    private World skyBlockNetherWorld;
    private Location spawn;
    private List<Island> islands;
    private List<Challenge> challenges;
    private int totalIslands;
    private Location lastLocation;

    private Map<Player, SkyBlockPlayer> players;
    private List<SkyBlockPlayer> skyBlockPlayers;

    @Override
    public void onEnable() {
        skyBlock = this;
        this.omServer = new SkyBlock();
        this.api = getOmServer().getApi();

        this.configManager = new SkyBlockConfigManager();
        getConfigManager().setup("playerdata", "islands");

        this.lobby = Bukkit.getWorld("SkyBlockLobby");
        this.skyBlockWorld = Bukkit.getWorld("SkyBlock");
        this.skyBlockNetherWorld = Bukkit.getWorld("SkyBlock_nether");
        this.spawn = new Location(getLobby(), 0.5, 74, 0.5, 0, 0);
        this.islands = new ArrayList<>();
        this.challenges = new ArrayList<>();
        this.totalIslands = getConfigManager().get("islands").getInt("TotalIslands");
        this.lastLocation = ConfigUtils.parseLocation(getConfigManager().get("islands").getString("LastLocation"));
        this.players = new HashMap<>();
        this.skyBlockPlayers = new ArrayList<>();

        registerCurrencies();
        registerCommands();
        registerEvents();
        registerRunnables();
        registerIslands();
        registerChallenges();
        registerKits();

        new BukkitRunnable(){
            public void run(){
                WorldUtils.removeEntities(getLobby());
                spawnNpcs();
            }
        }.runTaskLater(this, 100);
    }

    @Override
    public void onDisable() {

    }

    public static OrbitMinesSkyBlock getSkyBlock() {
        return skyBlock;
    }

    public OrbitMinesServer getOmServer() {
        return omServer;
    }

    public OrbitMinesAPI getApi(){
        return api;
    }

    public SkyBlockConfigManager getConfigManager() {
        return configManager;
    }

    public World getLobby() {
        return lobby;
    }

    public World getSkyBlockWorld() {
        return skyBlockWorld;
    }

    public World getSkyBlockNetherWorld() {
        return skyBlockNetherWorld;
    }

    public Location getSpawn() {
        return spawn;
    }

    public List<Island> getIslands() {
        return islands;
    }

    public List<Challenge> getChallenges() {
        return challenges;
    }

    public int getTotalIslands() {
        return totalIslands;
    }

    public void setTotalIslands(int totalIslands) {
        this.totalIslands = totalIslands;

        getConfigManager().get("islands").set("TotalIslands", this.totalIslands);
        getConfigManager().save("islands");
    }

    public Location getLastLocation() {
        return lastLocation;
    }

    public void setLastLocation(Location lastLocation) {
        this.lastLocation = lastLocation;

        getConfigManager().get("islands").set("LastLocation", ConfigUtils.parseString(lastLocation));
        getConfigManager().save("islands");
    }

    public Map<Player, SkyBlockPlayer> getPlayers() {
        return players;
    }

    public List<SkyBlockPlayer> getSkyBlockPlayers() {
        return skyBlockPlayers;
    }

    /* Register */

    private void registerCurrencies(){

    }

    private void registerCommands(){
        getApi().registerCommand(new EnderchestCommand());
        getApi().unregisterCommand("/fly");
        getApi().registerCommand(new FlyCommand());
        getApi().unregisterCommand("/invsee");
        getApi().registerCommand(new InvSeeCommand());
        getApi().registerCommand(new IslandCommand());
        getApi().registerCommand(new KitCommand());
        getApi().registerCommand(new SpawnCommand());
        getApi().registerCommand(new WorkbenchCommand());
    }

    private void registerEvents(){
        getServer().getPluginManager().registerEvents(new BreakEvent(), this);
        getServer().getPluginManager().registerEvents(new ClickEvent(), this);
        getServer().getPluginManager().registerEvents(new DamageByEntityEvent(), this);
        getServer().getPluginManager().registerEvents(new DeathEvent(), this);
        getServer().getPluginManager().registerEvents(new InteractAtEntityEvent(), this);
        getServer().getPluginManager().registerEvents(new InteractEntityEvent(), this);
        getServer().getPluginManager().registerEvents(new InteractEvent(), this);
        getServer().getPluginManager().registerEvents(new JoinEvent(), this);
        getServer().getPluginManager().registerEvents(new MoveEvent(), this);
        getServer().getPluginManager().registerEvents(new PlaceEvent(), this);
        getServer().getPluginManager().registerEvents(new SignEvent(), this);
    }

    private void registerRunnables(){
        new NetherSkyBlockRunnable();
    }

    private void registerIslands(){
        FileConfiguration c = getConfigManager().get("islands");
        if(c.contains("islands")){
            for(String islandIdString : c.getConfigurationSection("islands").getKeys(false)){
                int islandId = Integer.parseInt(islandIdString);
                boolean netherGenerated = false;
                if(c.contains("islands." + islandId + ".NetherGenerated"))
                    netherGenerated = c.getBoolean("islands." + islandId + ".NetherGenerated");

                this.islands.add(new Island(islandId, ConfigUtils.parseLocation(c.getString("islands." + islandId + ".IslandLocation")), c.getString("islands." + islandId + ".CreatedDate"), UUID.fromString(c.getString("islands." + islandId + ".Players.Owner")), ConfigUtils.parseUUIDList(c.getStringList("islands." + islandId + ".Players.Members")), Boolean.parseBoolean(c.getString("islands." + islandId + ".TeleportEnabled")), Boolean.parseBoolean(c.getString("islands." + islandId + ".IslandProtection")), netherGenerated));
            }
        }
    }

    private void registerChallenges(){
        // Gather Challenges \\
        getChallenges().add(new Challenge(0, new ItemData("Cobblestone Generator", 1, Material.COBBLESTONE, 0), Collections.singletonList(new ItemData("32 Cobblestone", 32, Material.COBBLESTONE, 0)), Collections.singletonList(new ItemData("1 Birch Sapling", 1, Material.SAPLING, 2)), null));
        getChallenges().add(new Challenge(1, new ItemData("Apples", 1, Material.APPLE, 0), Collections.singletonList(new ItemData("1 Apple", 1, Material.APPLE, 0)), Collections.singletonList(new ItemData("1 Sugarcane", 1, Material.SUGAR_CANE, 0)), null));
        getChallenges().add(new Challenge(2, new ItemData("Jack the Lumberjack", 1, Material.LOG, 0), Collections.singletonList(new ItemData("25 Oak Logs", 25, Material.LOG, 0)), Collections.singletonList(new ItemData("1 Melon Seed", 1, Material.MELON_SEEDS, 0)), null));
        getChallenges().add(new Challenge(3, new ItemData("More Smoothness", 1, Material.SMOOTH_BRICK, 0), Collections.singletonList(new ItemData("40 Stone Bricks", 40, Material.SMOOTH_BRICK, 0)), Collections.singletonList(new ItemData("1 Iron Ingot", 1, Material.IRON_INGOT, 0)), 0));
        getChallenges().add(new Challenge(4, new ItemData("Fishing Time!", 1, Material.RAW_FISH, 0), Collections.singletonList(new ItemData("10 Raw Fish", 10, Material.RAW_FISH, 0)), Collections.singletonList(new ItemData("2 Leather", 2, Material.LEATHER, 0)), 1));
        getChallenges().add(new Challenge(5, new ItemData("Chop Chop", 1, Material.LOG, 1), Arrays.asList(new ItemData("32 Oak Logs", 32, Material.LOG, 0), new ItemData("32 Birch Logs", 32, Material.LOG, 2), new ItemData("32 Spruce Logs", 32, Material.LOG, 1)), Collections.singletonList(new ItemData("16 Clay", 16, Material.CLAY_BALL, 0)), 2));
        getChallenges().add(new Challenge(6, new ItemData("Navigation", 1, Material.COMPASS, 0), Collections.singletonList(new ItemData("1 Compass", 1, Material.COMPASS, 0)), Collections.singletonList(new ItemData("1 Pumpkin Seed", 1, Material.PUMPKIN_SEEDS, 0)), 3));
        getChallenges().add(new Challenge(7, new ItemData("Librarian", 1, Material.BOOKSHELF, 0), Collections.singletonList(new ItemData("2 Bookshelfs", 2, Material.BOOKSHELF, 0)), Collections.singletonList(new ItemData("3 Gold Ore", 3, Material.GOLD_ORE, 0)), 4));
        getChallenges().add(new Challenge(8, new ItemData("The Dark Forest", 1, Material.LOG_2, 1), Collections.singletonList(new ItemData("256 Dark Oak Logs", 256, Material.LOG_2, 1)), Collections.singletonList(new ItemData("1 Wolf Spawn Egg", 1, Material.MONSTER_EGG, 95)), 5));
        getChallenges().add(new Challenge(9, new ItemData("It's Time", 1, Material.WATCH, 0), Collections.singletonList(new ItemData("1 Clock", 1, Material.WATCH, 0)), Collections.singletonList(new ItemData("1 Pig Spawn Egg", 1, Material.MONSTER_EGG, 90)), 6, 7));
        getChallenges().add(new Challenge(10, new ItemData("Stained Clay", 1, Material.STAINED_CLAY, 11), Collections.singletonList(new ItemData("28 Blue Stained Clay", 28, Material.STAINED_CLAY, 11)), Collections.singletonList(new ItemData("1 Mycelium", 1, Material.MYCEL, 0)), 7, 8));
        getChallenges().add(new Challenge(11, new ItemData("Sheep Shearing", 1, Material.WOOL, 0), Collections.singletonList(new ItemData("50 White Wool", 50, Material.WOOL, 0)), Collections.singletonList(new ItemData("3 Gravel", 3, Material.GRAVEL, 0)), 9));
        getChallenges().add(new Challenge(12, new ItemData("Windows", 1, Material.STAINED_GLASS, 11), Collections.singletonList(new ItemData("16 Blue Stained Glass", 16, Material.STAINED_GLASS, 11)), Collections.singletonList(new ItemData("2 Obsidian", 2, Material.OBSIDIAN, 0)), 10));
        getChallenges().add(new Challenge(13, new ItemData("Wood Factory", 1, Material.LOG_2, 0), Arrays.asList(new ItemData("64 Oak Logs", 64, Material.LOG, 0), new ItemData("64 Birch Logs", 64, Material.LOG, 2), new ItemData("64 Spruce Logs", 64, Material.LOG, 1), new ItemData("64 Dark Oak Logs", 64, Material.LOG_2, 1), new ItemData("64 Jungle Logs", 64, Material.LOG, 3), new ItemData("64 Acacia Logs", 64, Material.LOG_2, 0)), Collections.singletonList(new ItemData("1 Cow Spawn Egg", 1, Material.MONSTER_EGG, 92)), 11, 12));
        getChallenges().add(new Challenge(14, new ItemData("'Lucky' Fishing", 1, Material.NAME_TAG, 0), Collections.singletonList(new ItemData("3 Name Tags", 3, Material.NAME_TAG, 0)), Collections.singletonList(new ItemData("1 Nether Wart", 1, Material.NETHER_STALK, 0)), 13));

        // Farm Challenges \\
        getChallenges().add(new Challenge(15, new ItemData("Melons", 1, Material.MELON, 0), Collections.singletonList(new ItemData("50 Melons", 50, Material.MELON, 0)), Collections.singletonList(new ItemData("1 Spruce Sapling", 1, Material.SAPLING, 1)), null));
        getChallenges().add(new Challenge(16, new ItemData("Halloween Party", 1, Material.PUMPKIN, 0), Collections.singletonList(new ItemData("32 Pumpkins", 32, Material.PUMPKIN, 0)), Collections.singletonList(new ItemData("1 Brown Mushroom", 1, Material.BROWN_MUSHROOM, 0)), null));
        getChallenges().add(new Challenge(17, new ItemData("Sugarcane", 1, Material.SUGAR_CANE, 0), Collections.singletonList(new ItemData("55 Sugarcane", 55, Material.SUGAR_CANE, 0)), Collections.singletonList(new ItemData("1 Sand", 1, Material.SAND, 0)), null));
        getChallenges().add(new Challenge(18, new ItemData("Melon Blocks?!", 1, Material.MELON_BLOCK, 0), Collections.singletonList(new ItemData("32 Melon Blocks", 32, Material.MELON_BLOCK, 0)), Collections.singletonList(new ItemData("1 Dirt", 1, Material.DIRT, 0)), 15));
        getChallenges().add(new Challenge(19, new ItemData("Mushroom Hunting", 1, Material.MUSHROOM_SOUP, 0), Collections.singletonList(new ItemData("30 Mushroom Stew", 30, Material.MUSHROOM_SOUP, 0)), Collections.singletonList(new ItemData("1 Ice Block", 1, Material.ICE, 0)), 16));
        getChallenges().add(new Challenge(20, new ItemData("Bacon", 1, Material.GRILLED_PORK, 0), Collections.singletonList(new ItemData("40 Cooked Pork Chop", 40, Material.GRILLED_PORK, 0)), Collections.singletonList(new ItemData("1 Cactus", 1, Material.CACTUS, 0)), 17));
        getChallenges().add(new Challenge(21, new ItemData("Rabbits", 1, Material.CARROT_ITEM, 0), Collections.singletonList(new ItemData("100 Carrots", 100, Material.CARROT_ITEM, 0)), Collections.singletonList(new ItemData("1 Dark Oak Sapling", 1, Material.SAPLING, 5)), 18));
        getChallenges().add(new Challenge(22, new ItemData("Baker", 1, Material.BREAD, 0), Collections.singletonList(new ItemData("30 Bread", 30, Material.BREAD, 0)), Collections.singletonList(new ItemData("1 Cocoa Bean", 1, Material.INK_SACK, 3)), 19));
        getChallenges().add(new Challenge(23, new ItemData("Potatoes", 1, Material.BAKED_POTATO, 0), Collections.singletonList(new ItemData("100 Baked Potatoes", 100, Material.BAKED_POTATO, 0)), Collections.singletonList(new ItemData("1 Acacia Sapling", 1, Material.SAPLING, 4)), 20));
        getChallenges().add(new Challenge(24, new ItemData("Cookie Monster", 1, Material.COOKIE, 0), Collections.singletonList(new ItemData("200 Cookies", 200, Material.COOKIE, 0)), Collections.singletonList(new ItemData("5 Lapis Lazuli Ore", 5, Material.LAPIS_ORE, 0)), 21, 22));
        getChallenges().add(new Challenge(25, new ItemData("Cactus", 1, Material.CACTUS, 0), Collections.singletonList(new ItemData("100 Cacti", 100, Material.CACTUS, 0)), Collections.singletonList(new ItemData("1 Slimeball", 1, Material.SLIME_BALL, 0)), 22, 23));
        getChallenges().add(new Challenge(26, new ItemData("KFC Delivery", 1, Material.COOKED_CHICKEN, 0), Collections.singletonList(new ItemData("60 Cooked Chicken", 60, Material.COOKED_CHICKEN, 0)), Collections.singletonList(new ItemData("1 Spawn Sheep Egg", 1, Material.MONSTER_EGG, 91)), 24));
        getChallenges().add(new Challenge(27, new ItemData("Steak", 1, Material.COOKED_BEEF, 0), Collections.singletonList(new ItemData("80 Steak", 80, Material.COOKED_BEEF, 0)), Collections.singletonList(new ItemData("1 Mossy Cobblestone", 1, Material.MOSSY_COBBLESTONE, 0)), 25));
        getChallenges().add(new Challenge(28, new ItemData("Grandmother's Kitchen", 1, Material.PUMPKIN_PIE, 0), Collections.singletonList(new ItemData("125 Pumpkin Pies", 125, Material.PUMPKIN_PIE, 0)), Collections.singletonList(new ItemData("1 Soul Sand", 1, Material.SOUL_SAND, 0)), 26, 27));
        getChallenges().add(new Challenge(29, new ItemData("Cakes", 1, Material.CAKE, 0), Collections.singletonList(new ItemData("3 Cakes", 3, Material.CAKE, 0)), Collections.singletonList(new ItemData("1 Emerald Ore", 1, Material.EMERALD_ORE, 0)), 28));

        // Mob Challenges \\
        getChallenges().add(new Challenge(30, new ItemData("Skeletons", 1, Material.BONE, 0), Collections.singletonList(new ItemData("150 Bones", 150, Material.BONE, 0)), Collections.singletonList(new ItemData("1 Jungle Sapling", 1, Material.SAPLING, 3)), null));
        getChallenges().add(new Challenge(31, new ItemData("Arrows!", 1, Material.ARROW, 0), Collections.singletonList(new ItemData("200 Arrows", 200, Material.ARROW, 0)), Collections.singletonList(new ItemData("64 Cobblestone", 64, Material.COBBLESTONE, 0)), null));
        getChallenges().add(new Challenge(32, new ItemData("Zombie Apocalypse", 1, Material.ROTTEN_FLESH, 0), Collections.singletonList(new ItemData("175 Zombie Flesh", 175, Material.ROTTEN_FLESH, 0)), Collections.singletonList(new ItemData("3 Raw Fish", 3, Material.RAW_FISH, 0)), 30));
        getChallenges().add(new Challenge(33, new ItemData("Creepy Creeper", 1, Material.SULPHUR, 0), Collections.singletonList(new ItemData("225 Gunpowder", 225, Material.SULPHUR, 0)), Collections.singletonList(new ItemData("1 Squid Spawn Egg", 1, Material.MONSTER_EGG, 94)), 31));
        getChallenges().add(new Challenge(34, new ItemData("Destroy the Webs!", 1, Material.STRING, 0), Collections.singletonList(new ItemData("200 String", 200, Material.STRING, 0)), Collections.singletonList(new ItemData("1 Red Mushroom", 1, Material.RED_MUSHROOM, 0)), 32));
        getChallenges().add(new Challenge(35, new ItemData("Spider Eyes", 1, Material.SPIDER_EYE, 0), Collections.singletonList(new ItemData("15 Spider Eyes", 15, Material.SPIDER_EYE, 0)), Collections.singletonList(new ItemData("1 Ocelot Spawn Egg", 1, Material.MONSTER_EGG, 98)), 33));
        getChallenges().add(new Challenge(36, new ItemData("Enderwoman", 1, Material.ENDER_PEARL, 0), Collections.singletonList(new ItemData("16 Ender Pearls", 16, Material.ENDER_PEARL, 0)), Collections.singletonList(new ItemData("1 Chicken Spawn Egg", 1, Material.MONSTER_EGG, 93)), 34, 35));
        getChallenges().add(new Challenge(37, new ItemData("Goblins", 1, Material.GOLD_SWORD, 0), Collections.singletonList(new ItemData("35 Gold Swords", 35, Material.GOLD_SWORD, 0)), Collections.singletonList(new ItemData("1 Mooshroom Spawn Egg", 1, Material.MONSTER_EGG, 96)), null));
        getChallenges().add(new Challenge(38, new ItemData("Blazepow(d)er", 1, Material.BLAZE_ROD, 0), Collections.singletonList(new ItemData("50 Blaze Rods", 50, Material.BLAZE_ROD, 0)), Collections.singletonList(new ItemData("1 Rabbit Spawn Egg", 1, Material.MONSTER_EGG, 101)), 37));
        getChallenges().add(new Challenge(39, new ItemData("The Scream", 1, Material.GHAST_TEAR, 0), Collections.singletonList(new ItemData("10 Ghast Tears", 10, Material.GHAST_TEAR, 0)), Collections.singletonList(new ItemData("1 Villager Spawn Egg", 1, Material.MONSTER_EGG, 120)), 38));
        getChallenges().add(new Challenge(40, new ItemData("Black Money", 1, Material.SKULL_ITEM, 1), Collections.singletonList(new ItemData("1 Wither Skeleton Skull", 1, Material.SKULL_ITEM, 1)), Collections.singletonList(new ItemData("2 Diamond Ore", 2, Material.DIAMOND_ORE, 0)), 35, 39));
        getChallenges().add(new Challenge(41, new ItemData("Red Fruits", 1, Material.NETHER_WART_BLOCK, 0), Collections.singletonList(new ItemData("50 Nether Wart Blocks", 50, Material.NETHER_WART_BLOCK, 0)), Collections.singletonList(new ItemData("1 Beetroot Seeds", 1, Material.BEETROOT_SEEDS, 0)), 36));
        getChallenges().add(new Challenge(42, new ItemData("Wabbit", 1, Material.RABBIT_HIDE, 0), Collections.singletonList(new ItemData("100 Rabbit Hide", 100, Material.RABBIT_HIDE, 0)), Collections.singletonList(new ItemData("1 Polar Bear Egg", 1, Material.MONSTER_EGG, 102)), 40));

        for(Challenge c : this.challenges){
            c.updateRequired();
        }
    }

    private void registerKits(){
        {
            Kit kit = new Kit(VIPRank.IRON_VIP.toString());
            kit.setItem(0, new ItemStack(Material.COBBLESTONE, 32));
            kit.setItem(1, new ItemStack(Material.IRON_INGOT, 3));

            getApi().registerKit(kit);
        }
        {
            Kit kit = new Kit(VIPRank.GOLD_VIP.toString());
            kit.setItem(0, new ItemStack(Material.COBBLESTONE, 48));
            kit.setItem(1, new ItemStack(Material.IRON_INGOT, 5));
            kit.setItem(2, new ItemStack(Material.DIRT, 1));

            getApi().registerKit(kit);
        }
        {
            Kit kit = new Kit(VIPRank.DIAMOND_VIP.toString());
            kit.setItem(0, new ItemStack(Material.COBBLESTONE, 64));
            kit.setItem(1, new ItemStack(Material.IRON_INGOT, 8));
            kit.setItem(2, new ItemStack(Material.DIRT, 1));
            kit.setItem(3, new ItemStack(Material.SAND, 1));

            getApi().registerKit(kit);
        }
        {
            Kit kit = new Kit(VIPRank.EMERALD_VIP.toString());
            kit.setItem(0, new ItemStack(Material.COBBLESTONE, 64));
            kit.setItem(1, new ItemStack(Material.COBBLESTONE, 64));
            kit.setItem(2, new ItemStack(Material.IRON_INGOT, 10));
            kit.setItem(3, new ItemStack(Material.DIRT, 2));
            kit.setItem(4, new ItemStack(Material.SAND, 2));
            kit.setItem(5, new ItemStack(Material.DIAMOND, 1));

            getApi().registerKit(kit);
        }
    }

    private void spawnNpcs(){
        {
            NPCArmorStand npc = new NPCArmorStand(new Location(getLobby(), 3.5, 76, 5.5, 0, 0), new NPCArmorStand.InteractAction() {
                @Override
                public void click(Player player, NPCArmorStand npcArmorStand) {
                    getApi().getServerSelector().open(player);
                }
            });
            npc.setGravity(false);
            npc.setVisible(false);
            npc.setSmall(true);
            npc.setUseItem(true);
            npc.setItemStack(new ItemStack(Material.ENDER_PEARL, 1));
            npc.setItemName("§3§lServer Selector");
            npc.spawn();

            getApi().registerNpcArmorStand(npc);
        }
        {
            NPCArmorStand npc = new NPCArmorStand(new Location(getLobby(), 2.5, 74, 12.5, -12, 0));
            npc.setGravity(false);
            npc.setVisible(false);
            npc.setRightArmPose(EulerAngle.ZERO.setX(-0.75));
            npc.setItemInHand(new ItemStack(Material.GRASS));
            npc.spawn();

            getApi().registerNpcArmorStand(npc);
        }
        {
            NPCArmorStand npc = new NPCArmorStand(new Location(getLobby(), 3.25, 73.75, 12.13, -38, 0));
            npc.setGravity(false);
            npc.setVisible(false);
            npc.setRightArmPose(EulerAngle.ZERO.setX(-0.75));
            npc.setItemInHand(new ItemStack(Material.GRASS));
            npc.spawn();

            getApi().registerNpcArmorStand(npc);
        }
        {
            NPCArmorStand npc = new NPCArmorStand(new Location(getLobby(), 5, 73.75, 11.79, 50, 0));
            npc.setGravity(false);
            npc.setVisible(false);
            npc.setRightArmPose(EulerAngle.ZERO.setX(-0.75));
            npc.setItemInHand(new ItemStack(Material.GRASS));
            npc.spawn();

            getApi().registerNpcArmorStand(npc);
        }
        {
            NPCArmorStand npc = new NPCArmorStand(new Location(getLobby(), 3.85, 75.1, 12.32, 47, 0));
            npc.setGravity(false);
            npc.setVisible(false);
            npc.setSmall(true);
            npc.setHeadPose(EulerAngle.ZERO.setX(0.45));
            npc.setLeftArmPose(EulerAngle.ZERO.setX(1.25));
            npc.setLeftLegPose(EulerAngle.ZERO.setX(-0.65));
            npc.setRightArmPose(EulerAngle.ZERO.setX(-0.25));
            npc.setRightLegPose(EulerAngle.ZERO.setX(1.1));
            npc.setHelmet(new ItemStack(Material.LEATHER_HELMET));
            npc.setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE));
            npc.setLeggings(new ItemStack(Material.LEATHER_LEGGINGS));
            npc.setBoots(new ItemStack(Material.LEATHER_BOOTS));
            npc.spawn();

            getApi().registerNpcArmorStand(npc);
        }
        {
            NPC npc = new NPC(Mob.BLAZE, new Location(getLobby(), -2.5, 75, -5.5, 0, 0), "§e§lOMT Shop", new NPC.InteractAction() {
                @Override
                public void click(Player player, NPC npc) {
                    new SkyBlockOMTShopInv().open(player);
                }
            });

            getApi().registerNpc(npc);
        }
        {
            Hologram h = new Hologram(new Location(getLobby(), 0.5, 71.5, 20.5, 180, 0));
            h.addLine("§dSpring naar beneden om te §d§lSpelen§d!");
            h.create();

            getApi().registerHologram(h);
        }
    }
}
