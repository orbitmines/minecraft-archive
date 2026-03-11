package fadidev.orbitmines.survival;

import fadidev.orbitmines.api.OrbitMinesAPI;
import fadidev.orbitmines.api.OrbitMinesServer;
import fadidev.orbitmines.api.handlers.StringInt;
import fadidev.orbitmines.api.handlers.npc.Hologram;
import fadidev.orbitmines.api.handlers.npc.NPC;
import fadidev.orbitmines.api.handlers.npc.NPCArmorStand;
import fadidev.orbitmines.api.handlers.npc.NPCMoving;
import fadidev.orbitmines.api.utils.ItemUtils;
import fadidev.orbitmines.api.utils.WorldUtils;
import fadidev.orbitmines.api.utils.enums.Mob;
import fadidev.orbitmines.survival.cmd.*;
import fadidev.orbitmines.survival.events.*;
import fadidev.orbitmines.survival.handlers.*;
import fadidev.orbitmines.survival.inventories.RegionInv;
import fadidev.orbitmines.survival.inventories.SurvivalOMTShopInv;
import fadidev.orbitmines.survival.managers.SurvivalConfigManager;
import fadidev.orbitmines.survival.runnables.ChestShopTutorialRunnable;
import fadidev.orbitmines.survival.runnables.MobHeadRunnable;
import fadidev.orbitmines.survival.runnables.ShopSignRunnable;
import fadidev.orbitmines.survival.runnables.TopMoneyRunnable;
import fadidev.orbitmines.survival.utils.enums.Rarity;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.EulerAngle;

import java.util.*;

/**
 * Created by Fadi on 15-9-2016.
 */
public class OrbitMinesSurvival extends JavaPlugin {

    private static OrbitMinesSurvival survival;
    private OrbitMinesServer omServer;
    private OrbitMinesAPI api;

    private SurvivalConfigManager configManager;

    private World lobby;
    private World survivalWorld;
    private Location spawn;
    private List<Location> pvPSpawns;
    private List<Region> regions;
    private RegionInv regionTeleporter;
    private List<WorldPortal> worldPortals;
    private List<StringInt> topMoney;
    private List<ShopSign> shopSigns;
    private List<Warp> warps;
    private Location tutorials;

    private Map<Player, SurvivalPlayer> players;
    private List<SurvivalPlayer> survivalPlayers;

    private List<Player> noFall;
    private Map<Rarity, List<UUID>> mobHeads;

    @Override
    public void onEnable() {
        survival = this;
        this.omServer = new Survival();
        this.api = getOmServer().getApi();

        this.configManager = new SurvivalConfigManager();
        getConfigManager().setup("playerdata", "chestshops", "warps");

        this.lobby = Bukkit.getWorld("SurvivalLobby");
        this.survivalWorld = Bukkit.getWorld("SurvivalWorld");
        this.spawn = new Location(getLobby(), 0.5, 74, 0.5, 0, 0);
        this.pvPSpawns = Arrays.asList(new Location(getLobby(), 4, 68, 51, 45, 0), new Location(getLobby(), 20, 69, 48, 75, 0), new Location(getLobby(), 14, 68, 54, 170, 0));
        this.regions = new ArrayList<>();
        this.worldPortals = new ArrayList<>();
        this.topMoney = new ArrayList<>();
        this.shopSigns = ShopSign.readFromConfig();
        this.warps = Warp.readFromConfig();
        this.tutorials = new Location(getLobby(), -48.5, 79, -0.5, 180, 0);
        this.players = new HashMap<>();
        this.survivalPlayers = new ArrayList<>();

        this.noFall = new ArrayList<>();
        this.mobHeads = new HashMap<>();

        registerCurrencies();
        registerCommands();
        registerEvents();
        registerRunnables();
        registerWorldPortals();
        registerRegions();

        this.regionTeleporter = new RegionInv();

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

    public static OrbitMinesSurvival getSurvival() {
        return survival;
    }

    public OrbitMinesServer getOmServer() {
        return omServer;
    }

    public OrbitMinesAPI getApi(){
        return api;
    }

    public SurvivalConfigManager getConfigManager() {
        return configManager;
    }

    public World getLobby() {
        return lobby;
    }

    public World getSurvivalWorld() {
        return survivalWorld;
    }

    public Location getSpawn() {
        return spawn;
    }

    public List<Location> getPvPSpawns() {
        return pvPSpawns;
    }

    public List<Region> getRegions() {
        return regions;
    }

    public RegionInv getRegionTeleporter() {
        return regionTeleporter;
    }

    public List<WorldPortal> getWorldPortals() {
        return worldPortals;
    }

    public List<StringInt> getTopMoney() {
        return topMoney;
    }

    public void setTopMoney(List<StringInt> topMoney) {
        this.topMoney = topMoney;
    }

    public List<ShopSign> getShopSigns() {
        return shopSigns;
    }

    public List<Warp> getWarps() {
        return warps;
    }

    public Location getTutorials() {
        return tutorials;
    }

    public Map<Player, SurvivalPlayer> getPlayers() {
        return players;
    }

    public List<SurvivalPlayer> getSurvivalPlayers() {
        return survivalPlayers;
    }

    public List<Player> getNoFall() {
        return noFall;
    }

    public Map<Rarity, List<UUID>> getMobHeads() {
        return mobHeads;
    }

    public void setMobHeads(Map<Rarity, List<UUID>> mobHeads) {
        this.mobHeads = mobHeads;
    }

    /* Register */
    private void registerEvents(){
        getServer().getPluginManager().registerEvents(new BreakEvent(), this);
        getServer().getPluginManager().registerEvents(new ClickEvent(), this);
        getServer().getPluginManager().registerEvents(new CommandPreprocessEvent(), this);
        getServer().getPluginManager().registerEvents(new DamageByEntityEvent(), this);
        getServer().getPluginManager().registerEvents(new DamageEvent(), this);
        getServer().getPluginManager().registerEvents(new DeathEvent(), this);
        getServer().getPluginManager().registerEvents(new InteractEvent(), this);
        getServer().getPluginManager().registerEvents(new JoinEvent(), this);
        getServer().getPluginManager().registerEvents(new MoveEvent(), this);
        getServer().getPluginManager().registerEvents(new PlaceEvent(), this);
        getServer().getPluginManager().registerEvents(new SignEvent(), this);
        getServer().getPluginManager().registerEvents(new SpawnEvent(), this);
    }

    private void registerCommands(){
        getApi().registerCommand(new AcceptCommand());
        getApi().registerCommand(new BackCommand());
        getApi().registerCommand(new DelHomeCommand());
        getApi().registerCommand(new EditWarpCommand());
        getApi().registerCommand(new EnderchestCommand());
        getApi().unregisterCommand("/fly");
        getApi().registerCommand(new FlyCommand());
        getApi().registerCommand(new HomeCommand());
        getApi().registerCommand(new HomesCommand());
        getApi().registerCommand(new InvAcceptCommand());
        getApi().unregisterCommand("/invsee");
        getApi().registerCommand(new InvSeeCommand());
        getApi().registerCommand(new MyWarpsCommand());
        getApi().registerCommand(new RegionCommand());
        getApi().registerCommand(new SetHomeCommand());
        getApi().registerCommand(new SetWarpCommand());
        getApi().registerCommand(new SpawnCommand());
        getApi().unregisterCommand("/teleport");
        getApi().registerCommand(new TeleportCommand());
        getApi().registerCommand(new TopMoneyCommand());
        getApi().registerCommand(new TpHereCommand());
        getApi().registerCommand(new WarpsCommand());
        getApi().registerCommand(new WorkbenchCommand());

        SurvivalConsoleCommandExecuter ccE = new SurvivalConsoleCommandExecuter();
        List<String> list = Arrays.asList("money", "addhomes", "addshops", "addwarps");

        for(String command : list){
            getCommand(command).setExecutor(ccE);
        }
    }

    private void registerRunnables(){
        new ChestShopTutorialRunnable();
        new MobHeadRunnable();
        new ShopSignRunnable();
        new TopMoneyRunnable();
    }

    private void registerCurrencies(){

    }

    private void registerWorldPortals(){
        getWorldPortals().add(new WorldPortal(getLobby(), WorldUtils.getBlocksBetween(new Location(getLobby(), 10, 72, -13), new Location(getLobby(), 14, 68, -13))));
    }

    private void registerRegions(){
        World w = getSurvivalWorld();

        getRegions().add(new Region(1, new Location(w, -445.5, 63, -279.5, -70, -90), Biome.DEEP_OCEAN, 22));
        getRegions().add(new Region(2, new Location(w, -445.5, 63, -1279.5, -70, -90), Biome.DEEP_OCEAN, 21));
        getRegions().add(new Region(3, new Location(w, -445.5, 79, -2279.5, -70, -90), Biome.EXTREME_HILLS, 20));
        getRegions().add(new Region(4, new Location(w, -445.5, 63, -3279.5, -70, -90), Biome.RIVER, 19));
        getRegions().add(new Region(5, new Location(w, -445.5, 66, -4279.5, -70, -90), Biome.ROOFED_FOREST, 18));
        getRegions().add(new Region(6, new Location(w, 1055.5, 69, -279.5, -70, -90), Biome.SAVANNA, 13));
        getRegions().add(new Region(7, new Location(w, 2555.5, 66, -279.5, -70, -90), Biome.DESERT, 4));
        getRegions().add(new Region(8, new Location(w, -445.5, 63, 721.5, -70, -90), Biome.DEEP_OCEAN, 23));
        getRegions().add(new Region(9, new Location(w, -445.5, 69, 1721.5, -70, -90), Biome.DESERT, 24));
        getRegions().add(new Region(10, new Location(w, -445.5, 63, 2721.5, -70, -90), Biome.RIVER, 25));
        getRegions().add(new Region(11, new Location(w, -445.5, 62, 3721.5, -70, -90), Biome.PLAINS, 26));
        getRegions().add(new Region(12, new Location(w, -1945.5, 63, -279.5, -70, -90), Biome.OCEAN, 31));
        getRegions().add(new Region(13, new Location(w, -3445.5, 64, -279.5, -70, -90), Biome.ICE_FLATS, 40));
        getRegions().add(new Region(14, new Location(w, 1055.5, 73, -1279.5, -70, -90), Biome.ROOFED_FOREST, 12));
        getRegions().add(new Region(15, new Location(w, 2555.5, 64, -1279.5, -70, -90), Biome.BEACHES, 3));
        getRegions().add(new Region(16, new Location(w, 1055.5, 90, -2279.5, -70, -90), Biome.EXTREME_HILLS, 11));
        getRegions().add(new Region(17, new Location(w, 2555.5, 64, -2279.5, -70, -90), Biome.PLAINS, 2));
        getRegions().add(new Region(18, new Location(w, 1055.5, 67, -3279.5, -70, -90), Biome.PLAINS, 10));
        getRegions().add(new Region(19, new Location(w, 2555.5, 71, -3279.5, -70, -90), Biome.PLAINS, 1));
        getRegions().add(new Region(20, new Location(w, 1055.5, 63, -4279.5, -70, -90), Biome.DEEP_OCEAN, 9));
        getRegions().add(new Region(21, new Location(w, 2555.5, 70, -4279.5, -70, -90), Biome.TAIGA_COLD, 0));
        getRegions().add(new Region(22, new Location(w, 1055.5, 64, 721.5, -70, -90), Biome.DESERT, 14));
        getRegions().add(new Region(23, new Location(w, 2555.5, 68, 721.5, -70, -90), Biome.SAVANNA, 5));
        getRegions().add(new Region(24, new Location(w, 1055.5, 63, 1721.5, -70, -90), Biome.DEEP_OCEAN, 15));
        getRegions().add(new Region(25, new Location(w, 2555.5, 64, 1721.5, -70, -90), Biome.DESERT, 6));
        getRegions().add(new Region(26, new Location(w, 1055.5, 63, 2721.5, -70, -90), Biome.DEEP_OCEAN, 16));
        getRegions().add(new Region(27, new Location(w, 2555.5, 63, 2721.5, -70, -90), Biome.DEEP_OCEAN, 7));
        getRegions().add(new Region(28, new Location(w, 1055.5, 63, 3721.5, -70, -90), Biome.DEEP_OCEAN, 17));
        getRegions().add(new Region(29, new Location(w, 2555.5, 64, 3721.5, -70, -90), Biome.DESERT, 8));
        getRegions().add(new Region(30, new Location(w, -1945.5, 63, 721.5, -70, -90), Biome.DEEP_OCEAN, 32));
        getRegions().add(new Region(31, new Location(w, -3445.5, 66, 721.5, -70, -90), Biome.FOREST, 41));
        getRegions().add(new Region(32, new Location(w, -1945.5, 78, 1721.5, -70, -90), Biome.EXTREME_HILLS, 33));
        getRegions().add(new Region(33, new Location(w, -3445.5, 63, 1721.5, -70, -90), Biome.SWAMPLAND, 42));
        getRegions().add(new Region(34, new Location(w, -1945.5, 63, 2721.5, -70, -90), Biome.OCEAN, 34));
        getRegions().add(new Region(35, new Location(w, -3445.5, 66, 2721.5, -70, -90), Biome.ROOFED_FOREST, 43));
        getRegions().add(new Region(36, new Location(w, -1945.5, 66, 3721.5, -70, -90), Biome.FOREST, 35));
        getRegions().add(new Region(37, new Location(w, -3445.5, 74, 3721.5, -70, -90), Biome.BIRCH_FOREST, 44));
        getRegions().add(new Region(38, new Location(w, -1945.5, 63, -1279.5, -70, -90), Biome.DEEP_OCEAN, 30));
        getRegions().add(new Region(39, new Location(w, -3445.5, 63, -1279.5, -70, -90), Biome.OCEAN, 39));
        getRegions().add(new Region(40, new Location(w, -1945.5, 89, -2279.5, -70, -90), Biome.EXTREME_HILLS, 29));
        getRegions().add(new Region(41, new Location(w, -3445.5, 67, -2279.5, -70, -90), Biome.FOREST, 38));
        getRegions().add(new Region(42, new Location(w, -1945.5, 65, -3279.5, -70, -90), Biome.BIRCH_FOREST, 28));
        getRegions().add(new Region(43, new Location(w, -3445.5, 63, -3279.5, -70, -90), Biome.RIVER, 37));
        getRegions().add(new Region(44, new Location(w, -1945.5, 63, -4279.5, -70, -90), Biome.DEEP_OCEAN, 27));
        getRegions().add(new Region(45, new Location(w, -3445.5, 64, -4279.5, -70, -90), Biome.FOREST, 36));
    }

    private void spawnNpcs(){
        {
            NPCArmorStand npc = new NPCArmorStand(new Location(getLobby(), 5, 72, 14, 180, 0), new NPCArmorStand.InteractAction() {
                @Override
                public void click(Player player, NPCArmorStand npc) {
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
            NPCArmorStand npc = new NPCArmorStand(new Location(getLobby(), 13, 68, -8, 0, 0), new NPCArmorStand.InteractAction() {
                @Override
                public void click(Player player, NPCArmorStand e) {
                    getRegionTeleporter().open(player);
                }
            });
            npc.setGravity(false);
            npc.setVisible(false);
            npc.setSmall(true);
            npc.setUseItem(true);
            npc.setItemStack(new ItemStack(Material.EYE_OF_ENDER, 1));
            npc.setItemName("§a§lRegion Teleporter");
            npc.spawn();

            getApi().registerNpcArmorStand(npc);
        }
        {
            NPCArmorStand npc = new NPCArmorStand(new Location(getLobby(), -5.5, 74, 7.5, -90, 0), new NPCArmorStand.InteractAction() {
                @Override
                public void click(Player player, NPCArmorStand npcArmorStand) {
                    SurvivalPlayer omp = SurvivalPlayer.getSurvivalPlayer(player);
                    omp.resetCooldown(SurvivalCooldowns.PVP_CONFIRM);
                }
            });
            npc.setGravity(false);
            npc.setVisible(false);
            npc.setRightArmPose(EulerAngle.ZERO.setX(-0.5));
            npc.setHelmet(ItemUtils.itemstack(Material.SKULL_ITEM, 1, 2));
            npc.setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
            npc.setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
            npc.setBoots(new ItemStack(Material.DIAMOND_BOOTS));
            npc.setItemInHand(new ItemStack(Material.DIAMOND_SWORD));
            npc.setCustomName("§2§lPvP Area");
            npc.setCustomNameVisible(true);
            npc.spawn();

            getApi().registerNpcArmorStand(npc);
        }
        {
            NPCArmorStand npc = new NPCArmorStand(new Location(getLobby(), -5, 74.5, 5.5, -45, 0));
            npc.setGravity(false);
            npc.setVisible(false);
            npc.setSmall(true);
            npc.setHeadPose(EulerAngle.ZERO.setX(-0.1));
            npc.setLeftLegPose(EulerAngle.ZERO.setX(0.5));
            npc.setRightArmPose(EulerAngle.ZERO.setX(-0.75));
            npc.setRightLegPose(EulerAngle.ZERO.setX(-0.5));
            npc.setHelmet(ItemUtils.itemstack(Material.SKULL_ITEM, 1, 2));
            npc.setChestplate(new ItemStack(Material.GOLD_CHESTPLATE));
            npc.setLeggings(new ItemStack(Material.GOLD_LEGGINGS));
            npc.setBoots(new ItemStack(Material.GOLD_BOOTS));
            npc.setItemInHand(new ItemStack(Material.GOLD_SWORD));
            npc.spawn();

            getApi().registerNpcArmorStand(npc);
        }
        {
            NPCArmorStand npc = new NPCArmorStand(new Location(getLobby(), -4.2, 77, 11.2, -145, 25));
            npc.setGravity(false);
            npc.setVisible(false);
            npc.setSmall(true);
            npc.setHeadPose(EulerAngle.ZERO.setX(-0.1));
            npc.setLeftLegPose(EulerAngle.ZERO.setX(-0.5));
            npc.setRightArmPose(EulerAngle.ZERO.setX(-0.75));
            npc.setRightLegPose(EulerAngle.ZERO.setX(0.5));
            npc.setHelmet(ItemUtils.itemstack(Material.SKULL_ITEM, 1, 2));
            npc.setChestplate(new ItemStack(Material.GOLD_CHESTPLATE));
            npc.setLeggings(new ItemStack(Material.GOLD_LEGGINGS));
            npc.setBoots(new ItemStack(Material.GOLD_BOOTS));
            npc.setItemInHand(new ItemStack(Material.GOLD_SWORD));
            npc.spawn();

            getApi().registerNpcArmorStand(npc);
        }
        {
            NPC npc = new NPC(Mob.BLAZE, new Location(getLobby(), 7, 71, 0.5, 0, 0), "§e§lOMT Shop", new NPC.InteractAction() {
                @Override
                public void click(Player player, NPC npc) {
                    new SurvivalOMTShopInv().open(player);
                }
            });

            getApi().registerNpc(npc);
        }
        {
            NPC npc = new NPC(Mob.VILLAGER, new Location(getLobby(), 16.5, 70, 0.5, 90, 0), "§f§lTutorials", new NPC.InteractAction() {
                @Override
                public void click(Player player, NPC npc) {
                    player.teleport(survival.getTutorials());
                }
            });
            npc.setVillagerProfession(Villager.Profession.LIBRARIAN);

            getApi().registerNpc(npc);
        }
        {
            NPC npc = new NPC(Mob.VILLAGER, new Location(getLobby(), -48.5, 79, -10.5, 0, 0), "§f§lTerug naar Spawn", new NPC.InteractAction() {
                @Override
                public void click(Player player, NPC npc) {
                    player.teleport(survival.getSpawn());
                }
            });
            npc.setVillagerProfession(Villager.Profession.LIBRARIAN);

            getApi().registerNpc(npc);
        }
        {
            NPCMoving npc = new NPCMoving(Mob.SKELETON, new Location(getLobby(), -48.5, 79, -3.5, 0, 0), "§7§lClaim Tutorial", new NPCMoving.ArriveAction() {
                @Override
                public void arrive(NPCMoving npc, int index, int seconds) {
                    World w = getLobby();

                    if(index == 0){
                        if(seconds == 9){
                            npc.setItemInHand(null);

                            w.getBlockAt(new Location(w, -53, 78, -7)).setType(Material.GRASS);
                            w.getBlockAt(new Location(w, -53, 78, -8)).setType(Material.GRASS);
                            w.getBlockAt(new Location(w, -52, 78, -8)).setType(Material.GRASS);
                            w.getBlockAt(new Location(w, -53, 78, -11)).setType(Material.GRASS);
                            w.getBlockAt(new Location(w, -53, 78, -14)).setType(Material.GRASS);
                            w.getBlockAt(new Location(w, -53, 78, -15)).setType(Material.GRASS);
                            w.getBlockAt(new Location(w, -52, 78, -15)).setType(Material.GRASS);
                            w.getBlockAt(new Location(w, -49, 78, -15)).setType(Material.GRASS);
                            w.getBlockAt(new Location(w, -46, 78, -15)).setType(Material.GRASS);
                            w.getBlockAt(new Location(w, -45, 78, -15)).setType(Material.GRASS);
                            w.getBlockAt(new Location(w, -45, 78, -14)).setType(Material.GRASS);
                            w.getBlockAt(new Location(w, -45, 78, -14)).setType(Material.GRASS);
                            w.getBlockAt(new Location(w, -45, 78, -11)).setType(Material.GRASS);
                            w.getBlockAt(new Location(w, -45, 78, -8)).setType(Material.GRASS);
                            w.getBlockAt(new Location(w, -45, 78, -7)).setType(Material.GRASS);
                            w.getBlockAt(new Location(w, -46, 78, -7)).setType(Material.GRASS);
                            w.getBlockAt(new Location(w, -46, 78, -7)).setType(Material.GRASS);
                            w.getBlockAt(new Location(w, -49, 78, -7)).setType(Material.GRASS);
                            w.getBlockAt(new Location(w, -52, 78, -7)).setType(Material.GRASS);
                        }
                        else if(seconds == 5){
                            final Hologram h = new Hologram(new Location(w, -48.5, 79.25, -3.5));
                            h.addLine("§7Neem een Stone Hoe in je hand");
                            h.create();

                            new BukkitRunnable(){
                                public void run(){
                                    h.delete();
                                }
                            }.runTaskLater(survival, 120);

                            npc.setItemInHand(new ItemStack(Material.STONE_HOE));
                        }
                    }
                    else if(index == 1){
                        if(seconds == 5){
                            final Hologram h = new Hologram(new Location(w, -53.5, 79.25, -5.5));
                            h.addLine("§6Rechtermuisklik§7 op de hoek van je huis");
                            h.create();

                            final Hologram h2 = new Hologram(new Location(w, -52.5, 77, -6.5));
                            h2.addLine("§bRechtermuisklik");
                            h2.create();

                            new BukkitRunnable(){
                                public void run(){
                                    h.delete();
                                    h2.delete();
                                }
                            }.runTaskLater(survival, 120);

                            w.getBlockAt(new Location(w, -53, 78, -7)).setType(Material.SOIL);
                        }
                    }
                    else if(index == 3){
                        if(seconds == 5){
                            final Hologram h = new Hologram(new Location(w, -43.5, 79.25, -15.5));
                            h.addLine("§6Rechtermuisklik§7 op de tegenovergestelde hoek van je huis");
                            h.create();

                            final Hologram h2 = new Hologram(new Location(w, -44.5, 77, -14.5));
                            h2.addLine("§bRechtermuisklik");
                            h2.create();

                            new BukkitRunnable(){
                                public void run(){
                                    h.delete();
                                    h2.delete();
                                }
                            }.runTaskLater(survival, 120);

                            w.getBlockAt(new Location(w, -53, 78, -7)).setType(Material.GLOWSTONE);
                            w.getBlockAt(new Location(w, -53, 78, -8)).setType(Material.GOLD_BLOCK);
                            w.getBlockAt(new Location(w, -52, 78, -8)).setType(Material.GOLD_BLOCK);
                            w.getBlockAt(new Location(w, -53, 78, -11)).setType(Material.GOLD_BLOCK);
                            w.getBlockAt(new Location(w, -53, 78, -14)).setType(Material.GOLD_BLOCK);
                            w.getBlockAt(new Location(w, -53, 78, -15)).setType(Material.GLOWSTONE);
                            w.getBlockAt(new Location(w, -52, 78, -15)).setType(Material.GOLD_BLOCK);
                            w.getBlockAt(new Location(w, -49, 78, -15)).setType(Material.GOLD_BLOCK);
                            w.getBlockAt(new Location(w, -46, 78, -15)).setType(Material.GOLD_BLOCK);
                            w.getBlockAt(new Location(w, -45, 78, -15)).setType(Material.GLOWSTONE);
                            w.getBlockAt(new Location(w, -45, 78, -14)).setType(Material.GOLD_BLOCK);
                            w.getBlockAt(new Location(w, -45, 78, -14)).setType(Material.GOLD_BLOCK);
                            w.getBlockAt(new Location(w, -45, 78, -11)).setType(Material.GOLD_BLOCK);
                            w.getBlockAt(new Location(w, -45, 78, -8)).setType(Material.GOLD_BLOCK);
                            w.getBlockAt(new Location(w, -45, 78, -7)).setType(Material.GLOWSTONE);
                            w.getBlockAt(new Location(w, -46, 78, -7)).setType(Material.GOLD_BLOCK);
                            w.getBlockAt(new Location(w, -46, 78, -7)).setType(Material.GOLD_BLOCK);
                            w.getBlockAt(new Location(w, -49, 78, -7)).setType(Material.GOLD_BLOCK);
                            w.getBlockAt(new Location(w, -52, 78, -7)).setType(Material.GOLD_BLOCK);
                        }
                    }
                }
            });
            npc.addMoveLocation(new Location(getLobby(), -48.5, 79, -3.5, 180, 0), 10);
            npc.addMoveLocation(new Location(getLobby(), -53.5, 79, -5.5, 135, 45), 10);
            npc.addMoveLocation(new Location(getLobby(), -53, 79, -13.5, 180, 0), 0);
            npc.addMoveLocation(new Location(getLobby(), -46, 79, -15.5, -65, 45), 10);
            npc.addMoveLocation(new Location(getLobby(), -44, 79, -7.5, 0, 0), 0);
            
            getApi().registerNpc(npc);
        }
        {
            Hologram h = new Hologram(new Location(getLobby(), -42.5, 79, -4.5));
            h.addLine("§6§lChest Shop Tutorial");
            h.create();
        }
    }
}
