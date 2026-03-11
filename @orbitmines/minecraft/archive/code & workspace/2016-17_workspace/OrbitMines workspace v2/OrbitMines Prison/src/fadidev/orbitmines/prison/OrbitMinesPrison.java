package fadidev.orbitmines.prison;

import fadidev.orbitmines.api.OrbitMinesAPI;
import fadidev.orbitmines.api.OrbitMinesServer;
import fadidev.orbitmines.api.handlers.Kit;
import fadidev.orbitmines.api.handlers.StringInt;
import fadidev.orbitmines.api.handlers.npc.Hologram;
import fadidev.orbitmines.api.handlers.npc.NPCArmorStand;
import fadidev.orbitmines.api.utils.WorldUtils;
import fadidev.orbitmines.api.utils.enums.Direction;
import fadidev.orbitmines.prison.cmd.*;
import fadidev.orbitmines.prison.events.*;
import fadidev.orbitmines.prison.handlers.*;
import fadidev.orbitmines.prison.managers.PrisonConfigManager;
import fadidev.orbitmines.prison.runnables.*;
import fadidev.orbitmines.prison.utils.enums.Rank;
import fadidev.orbitmines.prison.utils.enums.WoodType;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

/**
 * Created by Fadi on 18-9-2016.
 */
public class OrbitMinesPrison extends JavaPlugin {

    private static OrbitMinesPrison prison;
    private OrbitMinesServer omServer;
    private OrbitMinesAPI api;

    private PrisonConfigManager configManager;

    private World lobby;
    private World prisonWorld;
    private World cellWorld;
    private Location spawn;
    private List<Mine> mines;
    private List<StringInt> topGold;
    private List<ShopSign> shopSigns;
    private List<Shop> shops;
    private List<Cell> cells;
    private int lastCellId;

    private Map<Player, PrisonPlayer> players;
    private List<PrisonPlayer> survivalPlayers;

    @Override
    public void onEnable() {
        prison = this;
        this.omServer = new Prison();
        this.api = getOmServer().getApi();

        this.configManager = new PrisonConfigManager();
        getConfigManager().setup("chestshops", "shops", "cells", "playerdata");

        this.lobby = Bukkit.getWorld("PrisonLobby");
        this.prisonWorld = Bukkit.getWorld("PrisonWorld");
        this.cellWorld = Bukkit.getWorld("PrisonPlots");
        this.spawn = new Location(getLobby(), 0, 70, 0, 135, 0);
        this.mines = new ArrayList<>();
        this.topGold = new ArrayList<>();
        this.shopSigns = ShopSign.readFromConfig();
        this.shops = new ArrayList<>();
        this.cells = new ArrayList<>();
        this.lastCellId = getConfigManager().get("cells").getInt("LastCellID");

        this.players = new HashMap<>();
        this.survivalPlayers = new ArrayList<>();

        loadCells();
        registerCurrencies();
        registerCommands();
        registerEvents();
        registerRunnables();
        registerMines();
        registerShops();
        registerKits();

        new BukkitRunnable(){
            public void run(){
                WorldUtils.removeAllEntitiesFrame();
                spawnNpcs();
            }
        }.runTaskLater(this, 100);
    }

    @Override
    public void onDisable() {

    }

    public static OrbitMinesPrison getPrison() {
        return prison;
    }

    public OrbitMinesServer getOmServer() {
        return omServer;
    }

    public OrbitMinesAPI getApi(){
        return api;
    }

    public PrisonConfigManager getConfigManager() {
        return configManager;
    }

    public World getLobby() {
        return lobby;
    }

    public World getPrisonWorld() {
        return prisonWorld;
    }

    public World getCellWorld() {
        return cellWorld;
    }

    public Location getSpawn() {
        return spawn;
    }

    public List<Mine> getMines() {
        return mines;
    }

    public List<StringInt> getTopGold() {
        return topGold;
    }

    public void setTopGold(List<StringInt> topGold) {
        this.topGold = topGold;
    }

    public List<ShopSign> getShopSigns() {
        return shopSigns;
    }

    public List<Shop> getShops() {
        return shops;
    }

    public List<Cell> getCells() {
        return cells;
    }

    public int getLastCellId() {
        return lastCellId;
    }

    public void setLastCellId(int lastCellId) {
        this.lastCellId = lastCellId;

        getConfigManager().get("cells").set("LastCellID", getLastCellId());
        getConfigManager().save("cells");
    }


    public Map<Player, PrisonPlayer> getPlayers() {
        return players;
    }

    public List<PrisonPlayer> getPrisonPlayers() {
        return survivalPlayers;
    }

    /* Register */

    private void loadCells(){
        List<Cell> cells = new ArrayList<>();
        if(getConfigManager().get("cells").contains("cells")){
            for(String stringCellId : getConfigManager().get("cells").getConfigurationSection("cells").getKeys(false)){
                Cell cell = new Cell(Integer.parseInt(stringCellId));
                cell.load();
                cells.add(cell);
            }
        }
        this.cells = cells;
    }

    private void registerCurrencies(){

    }

    private void registerCommands(){
        getApi().registerCommand(new CellCommand());
        getApi().registerCommand(new EnderchestCommand());
        getApi().unregisterCommand("/feed");
        getApi().registerCommand(new FeedCommand());
        getApi().unregisterCommand("/fly");
        getApi().registerCommand(new FlyCommand());
        getApi().unregisterCommand("/invsee");
        getApi().registerCommand(new InvSeeCommand());
        getApi().registerCommand(new KitCommand());
        getApi().registerCommand(new MinesCommand());
        getApi().registerCommand(new PayCommand());
        getApi().registerCommand(new RankupCommand());
        getApi().registerCommand(new SpawnCommand());
        getApi().registerCommand(new TopGoldCommand());
        getApi().registerCommand(new WarpsCommand());
        getApi().registerCommand(new WorkbenchCommand());
    }

    private void registerEvents(){
        getServer().getPluginManager().registerEvents(new BreakEvent(), this);
        getServer().getPluginManager().registerEvents(new ClickEvent(), this);
        getServer().getPluginManager().registerEvents(new DamageByEntityEvent(), this);
        getServer().getPluginManager().registerEvents(new DamageEvent(), this);
        getServer().getPluginManager().registerEvents(new DeathEvent(), this);
        getServer().getPluginManager().registerEvents(new FromToEvent(), this);
        getServer().getPluginManager().registerEvents(new InteractAtEntityEvent(), this);
        getServer().getPluginManager().registerEvents(new InteractEntityEvent(), this);
        getServer().getPluginManager().registerEvents(new InteractEvent(), this);
        getServer().getPluginManager().registerEvents(new JoinEvent(), this);
        getServer().getPluginManager().registerEvents(new MoveEvent(), this);
        getServer().getPluginManager().registerEvents(new PlaceEvent(), this);
        getServer().getPluginManager().registerEvents(new SignEvent(), this);
        getServer().getPluginManager().registerEvents(new SpawnEvent(), this);
    }

    private void registerRunnables(){
        new ChestShopTutorialRunnable();
        new MineRunnable();
        new ShopRunnable();
        new ShopSignRunnable();
        new TopGoldRunnable();
    }

    private void registerMines(){
        getMines().add(new Mine(Rank.Z,
                WorldUtils.getBlocksBetween(new Location(getPrisonWorld(), 2, 51, -29), new Location(getPrisonWorld(), 28, 68, -57)),
                Arrays.asList(new MineBlock(Material.STONE, 0, 98),
                new MineBlock(Material.COAL_ORE, 0, 2)),
                new Location(getPrisonWorld(), 0.5, 70, 0.5, 180, 0),
                new Location(getPrisonWorld(), 7, 80, -43),
                new Location(getPrisonWorld(), 0.5, 69, -26.5, -135, 0),
                new Location(getPrisonWorld(), 9, 70, -20),
                new Location(getPrisonWorld(), 8, 70, -20),
                new Location(getPrisonWorld(), 7, 70, -5),
                new Location(getPrisonWorld(), 7, 70, -4),
                new Location(getPrisonWorld(), 7, 70, -3),
                new Location(getPrisonWorld(), 7.5, 69, -66.5, 0, 0),
                new Location(getPrisonWorld(), -4.5, 69, -27.5, -30, 0),
                Material.COBBLESTONE,
                0,
                "§7Cobblestone",
                WoodType.OAK,
                Direction.NORTH
        ));

        getMines().add(new Mine(Rank.Z, WorldUtils.getBlocksBetween(new Location(getPrisonWorld(), -23, 72, -49), new Location(getPrisonWorld(), -34, 72, -49)), new Location(getPrisonWorld(), -35.5, 72, -49.5, -90, 0), WoodType.OAK));

        getMines().add(new Mine(Rank.Y,
                WorldUtils.getBlocksBetween(new Location(getPrisonWorld(), 985, 55, -37), new Location(getPrisonWorld(), 1013, 69, -10)),
                Arrays.asList(new MineBlock(Material.SANDSTONE, 0, 90), new MineBlock(Material.COAL_ORE, 0, 10)),
                new Location(getPrisonWorld(), 1000.5, 70, -0.5, 180, 0),
                new Location(getPrisonWorld(), 991, 81, -23),
                new Location(getPrisonWorld(), 1016.5, 70, -6.5, 135, 0),
                new Location(getPrisonWorld(), 1019, 71, -11),
                new Location(getPrisonWorld(), 1019, 71, -12),
                new Location(getPrisonWorld(), 994, 72, -45),
                new Location(getPrisonWorld(), 995, 72, -45),
                new Location(getPrisonWorld(), 996, 72, -45),
                new Location(getPrisonWorld(), 991, 70, -41, 0, 0),
                new Location(getPrisonWorld(), 1022.5, 70, -24.5, 0, 0),
                Material.SANDSTONE,
                0,
                "§eSandstone",
                WoodType.OAK,
                Direction.NORTH
        ));

        getMines().add(new Mine(Rank.Y, WorldUtils.getBlocksBetween(new Location(getPrisonWorld(), 1035, 69, -5), new Location(getPrisonWorld(), 1045, 69, -5)), new Location(getPrisonWorld(), 1048.5, 70, -5.5, 90, 0), WoodType.OAK));

        getMines().add(new Mine(Rank.X,
                WorldUtils.getBlocksBetween(new Location(getPrisonWorld(), 1015, 40, 986), new Location(getPrisonWorld(), 987, 58, 1014)),
                Arrays.asList(new MineBlock(Material.SANDSTONE, 0, 90), new MineBlock(Material.COAL_ORE, 0, 5), new MineBlock(Material.IRON_ORE, 0, 5)),
                new Location(getPrisonWorld(), 1001.5, 61, 1019.5, 180, 0),
                new Location(getPrisonWorld(), 993, 69, 1000),
                new Location(getPrisonWorld(), 994.5, 60, 1017.5, -160, 0),
                new Location(getPrisonWorld(), 1019, 62, 1014),
                new Location(getPrisonWorld(), 1019, 62, 1013),
                new Location(getPrisonWorld(), 1017, 61, 998),
                new Location(getPrisonWorld(), 1017, 61, 999),
                new Location(getPrisonWorld(), 1017, 61, 1000),
                new Location(getPrisonWorld(), 982, 59, 1005, -90, 0),
                new Location(getPrisonWorld(), 994.5, 59, 1022.5, -170, 0),
                Material.SANDSTONE,
                0,
                "§eSandstone",
                WoodType.JUNGLE,
                Direction.NORTH
        ));

        getMines().add(new Mine(Rank.X, WorldUtils.getBlocksBetween(new Location(getPrisonWorld(), 1003, 60, 982), new Location(getPrisonWorld(), 1010, 60, 982)), new Location(getPrisonWorld(), 1009.5, 60, 984.5, 35, 0), WoodType.JUNGLE));

        getMines().add(new Mine(Rank.W,
                WorldUtils.getBlocksBetween(new Location(getPrisonWorld(), -928, 87, -16), new Location(getPrisonWorld(), -956, 68, -44)),
                Arrays.asList(new MineBlock(Material.STONE, 0, 85), new MineBlock(Material.COAL_ORE, 0, 10), new MineBlock(Material.IRON_ORE, 0, 5)),
                new Location(getPrisonWorld(), -933.5, 86, 2.5, -90, 0),
                new Location(getPrisonWorld(), -939, 99, -22),
                new Location(getPrisonWorld(), -924.5, 88, -12.5, 135, 0),
                new Location(getPrisonWorld(), -922, 89, 0),
                new Location(getPrisonWorld(), -922, 89, -1),
                new Location(getPrisonWorld(), -933, 87, 1),
                new Location(getPrisonWorld(), -933, 87, 2),
                new Location(getPrisonWorld(), -933, 87, 3),
                new Location(getPrisonWorld(), -928.5, 91, -3.5, -135, 0),
                new Location(getPrisonWorld(), -936.5, 88, -12.5, -100, 0),
                Material.COBBLESTONE,
                0,
                "§7Cobblestone",
                WoodType.DARK_OAK,
                Direction.WEST
        ));

        getMines().add(new Mine(Rank.W, WorldUtils.getBlocksBetween(new Location(getPrisonWorld(), -917, 94, -17), new Location(getPrisonWorld(), -908, 94, -17)), new Location(getPrisonWorld(), -916.5, 94, -15.5, -90, 0), WoodType.DARK_OAK));

        getMines().add(new Mine(Rank.V,
                WorldUtils.getBlocksBetween(new Location(getPrisonWorld(), -1005, 50, -993), new Location(getPrisonWorld(), -1033, 68, -1021)),
                Arrays.asList(new MineBlock(Material.STONE, 0, 80), new MineBlock(Material.COAL_ORE, 0, 10), new MineBlock(Material.IRON_ORE, 0, 10)),
                new Location(getPrisonWorld(), -997.5, 69, -975.5, 180, 0),
                new Location(getPrisonWorld(), -1019, 78, -999),
                new Location(getPrisonWorld(), -1006.5, 69, -987.5, 140, 0),
                new Location(getPrisonWorld(), -989, 69, -991),
                new Location(getPrisonWorld(), -989, 69, -992),
                new Location(getPrisonWorld(), -999, 72, -982),
                new Location(getPrisonWorld(), -998, 72, -982),
                new Location(getPrisonWorld(), -997, 72, -982),
                new Location(getPrisonWorld(), -987.5, 80.5, -983.5, -170, 0),
                new Location(getPrisonWorld(), -999.5, 70, -1001.5, 20, 0),
                Material.COBBLESTONE,
                0,
                "§7Cobblestone",
                WoodType.JUNGLE,
                Direction.WEST
        ));

        getMines().add(new Mine(Rank.V, WorldUtils.getBlocksBetween(new Location(getPrisonWorld(), -1008, 67, -968), new Location(getPrisonWorld(), -1024, 68, -969)), new Location(getPrisonWorld(), -1005.5, 67, -969.5, 130, 0), WoodType.JUNGLE));

        getMines().add(new Mine(Rank.U,
                WorldUtils.getBlocksBetween(new Location(getPrisonWorld(), 985, 69, -984), new Location(getPrisonWorld(), 1013, 55, -1012)),
                Arrays.asList(new MineBlock(Material.QUARTZ_BLOCK, 0, 70), new MineBlock(Material.COAL_ORE, 0, 10), new MineBlock(Material.IRON_ORE, 0, 20)),
                new Location(getPrisonWorld(), 955.5, 71, -980.5, -90, 0),
                new Location(getPrisonWorld(), 991, 82, -998),
                new Location(getPrisonWorld(), 977.5, 70, -989.5, -90, 0),
                new Location(getPrisonWorld(), -983, 71, -1005),
                new Location(getPrisonWorld(), -983, 71, -1004),
                new Location(getPrisonWorld(), 981, 72, -988),
                new Location(getPrisonWorld(), 981, 72, -992),
                new Location(getPrisonWorld(), 981, 72, -996),
                new Location(getPrisonWorld(), 1009.5, 70, -957.5, 90, 0),
                new Location(getPrisonWorld(), 977.5, 70, -993.5, -40, 0),
                Material.QUARTZ_BLOCK,
                0,
                "§fQuartz Block",
                WoodType.OAK,
                Direction.NORTH
        ));

        getMines().add(new Mine(Rank.U, WorldUtils.getBlocksBetween(new Location(getPrisonWorld(), 978, 70, -964), new Location(getPrisonWorld(), -954, 79, -941)), new Location(getPrisonWorld(), 973.5, 71, -957.5, -135, 0), WoodType.OAK));

        getMines().add(new Mine(Rank.T,
                WorldUtils.getBlocksBetween(new Location(getPrisonWorld(), -983, 100, 1018), new Location(getPrisonWorld(), -1012, 70, 989)),
                Arrays.asList(new MineBlock(Material.STAINED_CLAY, 14, 65), new MineBlock(Material.COAL_ORE, 0, 14), new MineBlock(Material.IRON_ORE, 0, 20), new MineBlock(Material.DIAMOND_ORE, 0, 1)),
                new Location(getPrisonWorld(), -981.5, 101, 1028.5, 135, 0),
                new Location(getPrisonWorld(), -1006, 109, 1004),
                new Location(getPrisonWorld(), -1009.5, 101, 1022.5, -150, 0),
                new Location(getPrisonWorld(), -980, 102, 1021),
                new Location(getPrisonWorld(), -981, 102, 1021),
                new Location(getPrisonWorld(), -984, 101, 1025),
                new Location(getPrisonWorld(), -983, 101, 1025),
                new Location(getPrisonWorld(), -982, 101, 1025),
                new Location(getPrisonWorld(), -974.5, 101, 1019.5, 60, 0),
                new Location(getPrisonWorld(), -1017.5, 101, 1016.5, -60, 0),
                Material.STAINED_CLAY,
                14,
                "§cRed Stained Clay",
                WoodType.OAK,
                Direction.NORTH
        ));

        getMines().add(new Mine(Rank.T, WorldUtils.getBlocksBetween(new Location(getPrisonWorld(), -994, 101, 1030), new Location(getPrisonWorld(), -998, 124, 1032)), new Location(getPrisonWorld(), -999.5, 101, 1030.5, -135, 0), WoodType.OAK));

        getMines().add(new Mine(Rank.S,
                WorldUtils.getBlocksBetween(new Location(getPrisonWorld(), 2016, 98, 13), new Location(getPrisonWorld(), 1988, 70, -15)),
                Arrays.asList(new MineBlock(Material.STONE, 0, 60), new MineBlock(Material.COAL_ORE, 0, 14), new MineBlock(Material.IRON_ORE, 0, 24), new MineBlock(Material.DIAMOND_ORE, 0, 2)),
                new Location(getPrisonWorld(), 2012, 99, 28.5, -180, 0),
                new Location(getPrisonWorld(), 1994, 106, -1),
                new Location(getPrisonWorld(), 1988.5, 99, 16.5, -140, 0),
                new Location(getPrisonWorld(), 2028, 100, 11),
                new Location(getPrisonWorld(), 2028, 100, 10),
                new Location(getPrisonWorld(), 2001, 101, -42),
                new Location(getPrisonWorld(), 2002, 101, -42),
                new Location(getPrisonWorld(), 2003, 101, -42),
                new Location(getPrisonWorld(), 2002.5, 99, -37.5, 0, 0),
                new Location(getPrisonWorld(), 1982, 99, 6.5, -60, 0),
                Material.COBBLESTONE,
                0,
                "§7Cobblestone",
                WoodType.BIRCH,
                Direction.NORTH
        ));

        getMines().add(new Mine(Rank.S, WorldUtils.getBlocksBetween(new Location(getPrisonWorld(), 1995, 100, -26), new Location(getPrisonWorld(), 1987, 99, -33)), new Location(getPrisonWorld(), 1996.5, 99, -31.5, -40, 0), WoodType.BIRCH));

        getMines().add(new Mine(Rank.R,
                WorldUtils.getBlocksBetween(new Location(getPrisonWorld(), -2009, 71, -26), new Location(getPrisonWorld(), -1978, 40, 5)),
                Arrays.asList(new MineBlock(Material.NETHERRACK, 0, 55), new MineBlock(Material.COAL_ORE, 0, 17), new MineBlock(Material.IRON_ORE, 0, 25), new MineBlock(Material.DIAMOND_ORE, 0, 3)),
                new Location(getPrisonWorld(), -2026.5, 71.5, -20, -90, 0),
                new Location(getPrisonWorld(), -1994, 80, -18),
                new Location(getPrisonWorld(), -2011.5, 72, -22.5, -60, 0),
                new Location(getPrisonWorld(), -1995, 73, -31),
                new Location(getPrisonWorld(), -1996, 73, -31),
                new Location(getPrisonWorld(), -1974, 75, -20),
                new Location(getPrisonWorld(), -1974, 75, -19),
                new Location(getPrisonWorld(), -1974, 75, -18),
                new Location(getPrisonWorld(), -2011.5, 72, -14.5, 120, 0),
                new Location(getPrisonWorld(), -2011.5, 72, -29.5, -45, 0),
                Material.NETHERRACK,
                0,
                "§cNetherrack",
                WoodType.OAK,
                Direction.EAST
        ));

        getMines().add(new Mine(Rank.R, WorldUtils.getBlocksBetween(new Location(getPrisonWorld(), -1974, 86, -19), new Location(getPrisonWorld(), -1961, 86, -19)), new Location(getPrisonWorld(), -1961.5, 86, -19.5, 75, 0), WoodType.OAK));

        getMines().add(new Mine(Rank.Q,
                WorldUtils.getBlocksBetween(new Location(getPrisonWorld(), 2008, 90, 1991), new Location(getPrisonWorld(), 1980, 70, 2019)),
                Arrays.asList(new MineBlock(Material.SNOW_BLOCK, 0, 50), new MineBlock(Material.COAL_ORE, 0, 15), new MineBlock(Material.IRON_ORE, 0, 30), new MineBlock(Material.DIAMOND_ORE, 0, 5)),
                new Location(getPrisonWorld(), 2018.5, 91, 1992, 90, 0),
                new Location(getPrisonWorld(), 1994, 100, 2013),
                new Location(getPrisonWorld(), 2012.5, 91, 2002.5, 80, 0),
                new Location(getPrisonWorld(), 1998, 92, 1988),
                new Location(getPrisonWorld(), 1997, 92, 1988),
                new Location(getPrisonWorld(), 2016, 92, 1989),
                new Location(getPrisonWorld(), 2016, 92, 1988),
                new Location(getPrisonWorld(), 2016, 92, 1987),
                new Location(getPrisonWorld(), 2019, 91, 1995.5, 135, 0),
                new Location(getPrisonWorld(), 2006.5, 90.375, 1986.5, -50, 0),
                Material.SNOW_BALL,
                0,
                "§fSnowball",
                WoodType.SPRUCE,
                Direction.WEST
        ));

        getMines().add(new Mine(Rank.Q, WorldUtils.getBlocksBetween(new Location(getPrisonWorld(), 1995, 91, 2043), new Location(getPrisonWorld(), 1989, 94, 2038)), new Location(getPrisonWorld(), 1996.5, 91, 2039.5, -160, 0), WoodType.SPRUCE));

        getMines().add(new Mine(Rank.P,
                WorldUtils.getBlocksBetween(new Location(getPrisonWorld(), -2005, 89, -1994), new Location(getPrisonWorld(), -1977, 70, -2022)),
                Arrays.asList(new MineBlock(Material.STONE, 0, 50), new MineBlock(Material.COAL_ORE, 0, 10), new MineBlock(Material.IRON_ORE, 0, 33), new MineBlock(Material.DIAMOND_ORE, 0, 7)),
                new Location(getPrisonWorld(), -2043.5, 90, -1986.5, -90, 0),
                new Location(getPrisonWorld(), -1991, 98, -2016),
                new Location(getPrisonWorld(), -2008.5, 90, -1992.5, -130, 0),
                new Location(getPrisonWorld(), -2007, 91, -2013),
                new Location(getPrisonWorld(), -2007, 91, -2012),
                new Location(getPrisonWorld(), -1970, 91, -2009),
                new Location(getPrisonWorld(), -1970, 91, -2008),
                new Location(getPrisonWorld(), -1970, 91, -2007),
                new Location(getPrisonWorld(), -2005.5, 90, -1967.5, 170, 0),
                new Location(getPrisonWorld(), -1990.5, 90, -1988.5, 110, 0),
                Material.COBBLESTONE,
                0,
                "§7Cobblestone",
                WoodType.OAK,
                Direction.EAST
        ));

        getMines().add(new Mine(Rank.P, WorldUtils.getBlocksBetween(new Location(getPrisonWorld(), -2011, 94, -1994), new Location(getPrisonWorld(), -2040, 90, -2024)), new Location(getPrisonWorld(), -2031.5, 90, -2000.5, -100, 0), WoodType.OAK));

        getMines().add(new Mine(Rank.O,
                WorldUtils.getBlocksBetween(new Location(getPrisonWorld(), -1986, 90, 2014), new Location(getPrisonWorld(), -2013, 61, 1987)),
                Arrays.asList(new MineBlock(Material.STONE, 0, 45), new MineBlock(Material.COAL_ORE, 0, 10), new MineBlock(Material.IRON_ORE, 0, 35), new MineBlock(Material.DIAMOND_ORE, 0, 10)),
                new Location(getPrisonWorld(), -1964.5, 91, 1971.5, 60, 0),
                new Location(getPrisonWorld(), -1992, 99, 2000),
                new Location(getPrisonWorld(), -1999, 91, 1982.5, 0, 0),
                new Location(getPrisonWorld(), -1980, 92, 2001),
                new Location(getPrisonWorld(), -1980, 92, 2000),
                new Location(getPrisonWorld(), -1971, 92, 1978),
                new Location(getPrisonWorld(), -1972, 91, 1977),
                new Location(getPrisonWorld(), -1973, 92, 1978),
                new Location(getPrisonWorld(), -1964.5, 91, 2026.5, -180, 0),
                new Location(getPrisonWorld(), -1999, 91, 2019.5, -180, 0),
                Material.COBBLESTONE,
                0,
                "§7Cobblestone",
                WoodType.OAK,
                Direction.SOUTH
        ));

        getMines().add(new Mine(Rank.O, WorldUtils.getBlocksBetween(new Location(getPrisonWorld(), -1979, 91, 1952), new Location(getPrisonWorld(), -1990, 101, 1966)), new Location(getPrisonWorld(), -1986.5, 91, 1963.5, 20, 0), WoodType.OAK));

        getMines().add(new Mine(Rank.N,
                WorldUtils.getBlocksBetween(new Location(getPrisonWorld(), 1985, 69, -2033), new Location(getPrisonWorld(), 2016, 37, -2002)),
                Arrays.asList(new MineBlock(Material.SNOW_BLOCK, 0, 40), new MineBlock(Material.COAL_ORE, 0, 10), new MineBlock(Material.IRON_ORE, 0, 35), new MineBlock(Material.DIAMOND_ORE, 0, 15)),
                new Location(getPrisonWorld(), 2000.5, 73, -1988.5, -180, 0),
                new Location(getPrisonWorld(), 1992, 77, -2017),
                new Location(getPrisonWorld(), 1994.5, 73, -1995.5, -165, 0),
                new Location(getPrisonWorld(), 1981, 72, -2005),
                new Location(getPrisonWorld(), 1981, 72, -2004),
                new Location(getPrisonWorld(), 1999, 76, -1990),
                new Location(getPrisonWorld(), 2000, 76, -1990),
                new Location(getPrisonWorld(), 2001, 76, -1990),
                new Location(getPrisonWorld(), 2017.5, 72.0625, -1994.5, 90, 0),
                new Location(getPrisonWorld(), 1983.5, 73, -2011.5, -40, 0),
                Material.SNOW_BALL,
                0,
                "§fSnowball",
                WoodType.SPRUCE,
                Direction.NORTH
        ));

        getMines().add(new Mine(Rank.N, WorldUtils.getBlocksBetween(new Location(getPrisonWorld(), 1987, 72, -1996), new Location(getPrisonWorld(), 1982, 72, -1996)), new Location(getPrisonWorld(), -1982.5, 73, -1994.5, -100, 0), WoodType.SPRUCE));
    }

    private void registerShops(){
        getShops().add(new Shop(1, new Location(getLobby(), 9, 74, 15), WorldUtils.getBlocksBetween(new Location(getLobby(), 10, 73, 16), new Location(getLobby(), 13, 76, 19)), WorldUtils.getBlocksBetween(new Location(getLobby(), 10, 73, 16), new Location(getLobby(), 10, 73, 19)), WorldUtils.getBlocksBetween(new Location(getLobby(), 13, 76, 16), new Location(getLobby(), 13, 76, 19))));
        getShops().add(new Shop(2, new Location(getLobby(), 1, 74, 20), WorldUtils.getBlocksBetween(new Location(getLobby(), 0, 73, 19), new Location(getLobby(), -3, 76, 16)), WorldUtils.getBlocksBetween(new Location(getLobby(), 0, 73, 19), new Location(getLobby(), 0, 73, 16)), WorldUtils.getBlocksBetween(new Location(getLobby(), -3, 76, 19), new Location(getLobby(), -3, 76, 16))));
        getShops().add(new Shop(3, new Location(getLobby(), 10, 74, 22), WorldUtils.getBlocksBetween(new Location(getLobby(), 11, 73, 23), new Location(getLobby(), 14, 76, 26)), WorldUtils.getBlocksBetween(new Location(getLobby(), 11, 73, 23), new Location(getLobby(), 11, 73, 26)), WorldUtils.getBlocksBetween(new Location(getLobby(), 14, 76, 23), new Location(getLobby(), 14, 76, 26))));
        getShops().add(new Shop(4, new Location(getLobby(), 0, 74, 27), WorldUtils.getBlocksBetween(new Location(getLobby(), -1, 73, 26), new Location(getLobby(), -4, 76, 23)), WorldUtils.getBlocksBetween(new Location(getLobby(), -1, 73, 26), new Location(getLobby(), -1, 73, 23)), WorldUtils.getBlocksBetween(new Location(getLobby(), -4, 76, 26), new Location(getLobby(), -4, 76, 23))));
        getShops().add(new Shop(5, new Location(getLobby(), 9, 74, 29), WorldUtils.getBlocksBetween(new Location(getLobby(), 10, 73, 30), new Location(getLobby(), 13, 76, 33)), WorldUtils.getBlocksBetween(new Location(getLobby(), 10, 73, 30), new Location(getLobby(), 10, 73, 33)), WorldUtils.getBlocksBetween(new Location(getLobby(), 13, 76, 30), new Location(getLobby(), 13, 76, 33))));
        getShops().add(new Shop(6, new Location(getLobby(), 1, 74, 34), WorldUtils.getBlocksBetween(new Location(getLobby(), 0, 73, 33), new Location(getLobby(), -3, 76, 30)), WorldUtils.getBlocksBetween(new Location(getLobby(), 0, 73, 33), new Location(getLobby(), 0, 73, 30)), WorldUtils.getBlocksBetween(new Location(getLobby(), -3, 76, 33), new Location(getLobby(), -3, 76, 30))));
        getShops().add(new Shop(7, new Location(getLobby(), 5, 71, 43), WorldUtils.getBlocksBetween(new Location(getLobby(), 6, 70, 44), new Location(getLobby(), 9, 73, 47)), WorldUtils.getBlocksBetween(new Location(getLobby(), 6, 70, 44), new Location(getLobby(), 6, 70, 47)), WorldUtils.getBlocksBetween(new Location(getLobby(), 9, 73, 44), new Location(getLobby(), 9, 73, 47))));
        getShops().add(new Shop(8, new Location(getLobby(), 0, 71, 50), WorldUtils.getBlocksBetween(new Location(getLobby(), -1, 70, 49), new Location(getLobby(), -4, 73, 46)), WorldUtils.getBlocksBetween(new Location(getLobby(), -1, 70, 49), new Location(getLobby(), -1, 70, 46)), WorldUtils.getBlocksBetween(new Location(getLobby(), -4, 73, 49), new Location(getLobby(), -4, 73, 46))));
        getShops().add(new Shop(9, new Location(getLobby(), 6, 71, 50), WorldUtils.getBlocksBetween(new Location(getLobby(), 7, 70, 51), new Location(getLobby(), 10, 73, 54)), WorldUtils.getBlocksBetween(new Location(getLobby(), 7, 70, 51), new Location(getLobby(), 7, 70, 54)), WorldUtils.getBlocksBetween(new Location(getLobby(), 10, 73, 51), new Location(getLobby(), 10, 73, 54))));
        getShops().add(new Shop(10, new Location(getLobby(), 1, 71, 57), WorldUtils.getBlocksBetween(new Location(getLobby(), 0, 70, 56), new Location(getLobby(), -3, 73, 53)), WorldUtils.getBlocksBetween(new Location(getLobby(), 0, 70, 56), new Location(getLobby(), 0, 70, 53)), WorldUtils.getBlocksBetween(new Location(getLobby(), -3, 73, 56), new Location(getLobby(), -3, 73, 53))));
        getShops().add(new Shop(11, new Location(getLobby(), -1, 71, 75), WorldUtils.getBlocksBetween(new Location(getLobby(), -2, 70, 74), new Location(getLobby(), -5, 73, 71)), WorldUtils.getBlocksBetween(new Location(getLobby(), -2, 70, 74), new Location(getLobby(), -2, 70, 71)), WorldUtils.getBlocksBetween(new Location(getLobby(), -5, 73, 74), new Location(getLobby(), -5, 73, 71))));
        getShops().add(new Shop(12, new Location(getLobby(), 1, 71, 82), WorldUtils.getBlocksBetween(new Location(getLobby(), 0, 70, 81), new Location(getLobby(), -3, 73, 78)), WorldUtils.getBlocksBetween(new Location(getLobby(), 0, 70, 71), new Location(getLobby(), 0, 70, 78)), WorldUtils.getBlocksBetween(new Location(getLobby(), -3, 73, 81), new Location(getLobby(), -3, 73, 78))));
        getShops().add(new Shop(13, new Location(getLobby(), 7, 71, 82), WorldUtils.getBlocksBetween(new Location(getLobby(), 6, 70, 83), new Location(getLobby(), 3, 73, 86)), WorldUtils.getBlocksBetween(new Location(getLobby(), 6, 70, 83), new Location(getLobby(), 3, 70, 83)), WorldUtils.getBlocksBetween(new Location(getLobby(), 6, 73, 86), new Location(getLobby(), 3, 73, 86))));
        getShops().add(new Shop(14, new Location(getLobby(), 14, 71, 81), WorldUtils.getBlocksBetween(new Location(getLobby(), 13, 70, 82), new Location(getLobby(), 10, 73, 85)), WorldUtils.getBlocksBetween(new Location(getLobby(), 13, 70, 82), new Location(getLobby(), 10, 70, 82)), WorldUtils.getBlocksBetween(new Location(getLobby(), 13, 73, 85), new Location(getLobby(), 10, 73, 85))));
        getShops().add(new Shop(15, new Location(getLobby(), 21, 71, 79), WorldUtils.getBlocksBetween(new Location(getLobby(), 20, 70, 80), new Location(getLobby(), 17, 73, 83)), WorldUtils.getBlocksBetween(new Location(getLobby(), 20, 70, 80), new Location(getLobby(), 17, 70, 80)), WorldUtils.getBlocksBetween(new Location(getLobby(), 20, 73, 83), new Location(getLobby(), 17, 73, 83))));
        getShops().add(new Shop(16, new Location(getLobby(), 16, 71, 67), WorldUtils.getBlocksBetween(new Location(getLobby(), 15, 70, 68), new Location(getLobby(), 12, 73, 71)), WorldUtils.getBlocksBetween(new Location(getLobby(), 15, 70, 68), new Location(getLobby(), 12, 70, 68)), WorldUtils.getBlocksBetween(new Location(getLobby(), 15, 73, 71), new Location(getLobby(), 12, 73, 71))));
        getShops().add(new Shop(17, new Location(getLobby(), 23, 71, 66), WorldUtils.getBlocksBetween(new Location(getLobby(), 22, 70, 67), new Location(getLobby(), 19, 73, 70)), WorldUtils.getBlocksBetween(new Location(getLobby(), 22, 70, 67), new Location(getLobby(), 19, 70, 67)), WorldUtils.getBlocksBetween(new Location(getLobby(), 22, 73, 70), new Location(getLobby(), 19, 73, 70))));
        getShops().add(new Shop(18, new Location(getLobby(), 25, 71, 53), WorldUtils.getBlocksBetween(new Location(getLobby(), 24, 70, 52), new Location(getLobby(), 21, 73, 49)), WorldUtils.getBlocksBetween(new Location(getLobby(), 24, 70, 52), new Location(getLobby(), 24, 70, 49)), WorldUtils.getBlocksBetween(new Location(getLobby(), 21, 73, 52), new Location(getLobby(), 21, 73, 49))));
        getShops().add(new Shop(19, new Location(getLobby(), 29, 71, 47), WorldUtils.getBlocksBetween(new Location(getLobby(), 30, 70, 48), new Location(getLobby(), 33, 73, 51)), WorldUtils.getBlocksBetween(new Location(getLobby(), 30, 70, 48), new Location(getLobby(), 30, 70, 51)), WorldUtils.getBlocksBetween(new Location(getLobby(), 33, 73, 48), new Location(getLobby(), 33, 73, 51))));
        getShops().add(new Shop(20, new Location(getLobby(), 26, 71, 46), WorldUtils.getBlocksBetween(new Location(getLobby(), 25, 70, 45), new Location(getLobby(), 22, 73, 42)), WorldUtils.getBlocksBetween(new Location(getLobby(), 25, 70, 45), new Location(getLobby(), 25, 70, 42)), WorldUtils.getBlocksBetween(new Location(getLobby(), 22, 73, 45), new Location(getLobby(), 22, 73, 42))));
        getShops().add(new Shop(21, new Location(getLobby(), 29, 71, 37), WorldUtils.getBlocksBetween(new Location(getLobby(), 30, 70, 38), new Location(getLobby(), 33, 73, 41)), WorldUtils.getBlocksBetween(new Location(getLobby(), 30, 70, 38), new Location(getLobby(), 30, 70, 41)), WorldUtils.getBlocksBetween(new Location(getLobby(), 33, 73, 38), new Location(getLobby(), 33, 73, 41))));
        getShops().add(new Shop(22, new Location(getLobby(), 28, 71, 29), WorldUtils.getBlocksBetween(new Location(getLobby(), 29, 70, 30), new Location(getLobby(), 32, 73, 33)), WorldUtils.getBlocksBetween(new Location(getLobby(), 29, 70, 30), new Location(getLobby(), 29, 70, 33)), WorldUtils.getBlocksBetween(new Location(getLobby(), 32, 73, 30), new Location(getLobby(), 32, 73, 33))));
        getShops().add(new Shop(23, new Location(getLobby(), 27, 71, 12), WorldUtils.getBlocksBetween(new Location(getLobby(), 28, 70, 13), new Location(getLobby(), 31, 73, 16)), WorldUtils.getBlocksBetween(new Location(getLobby(), 28, 70, 13), new Location(getLobby(), 28, 70, 16)), WorldUtils.getBlocksBetween(new Location(getLobby(), 31, 73, 13), new Location(getLobby(), 31, 73, 16))));
        getShops().add(new Shop(24, new Location(getLobby(), 26, 71, 4), WorldUtils.getBlocksBetween(new Location(getLobby(), 27, 70, 5), new Location(getLobby(), 30, 73, 8)), WorldUtils.getBlocksBetween(new Location(getLobby(), 27, 70, 5), new Location(getLobby(), 27, 70, 8)), WorldUtils.getBlocksBetween(new Location(getLobby(), 30, 73, 5), new Location(getLobby(), 30, 73, 8))));
        getShops().add(new Shop(25, new Location(getLobby(), 20, 71, 4), WorldUtils.getBlocksBetween(new Location(getLobby(), 21, 70, 3), new Location(getLobby(), 24, 73, 0)), WorldUtils.getBlocksBetween(new Location(getLobby(), 21, 70, 3), new Location(getLobby(), 24, 70, 3)), WorldUtils.getBlocksBetween(new Location(getLobby(), 21, 73, 0), new Location(getLobby(), 24, 73, 0))));
    }

    private void registerKits(){
        for(Rank rank : Rank.values()){
            Kit kit = new Kit(rank.toString());
            kit.setItem(0, rank.getPickaxe());
            kit.setItem(1, rank.getAxe());
            kit.setItem(2, new ItemStack(Material.COOKED_BEEF, 32));

            rank.setKit(kit);
            getApi().registerKit(kit);
        }
    }

    private void spawnNpcs(){
        {
            NPCArmorStand npc = new NPCArmorStand(new Location(getLobby(), 3, 70.5, 3, 0, 0), new NPCArmorStand.InteractAction() {
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
            Hologram h = new Hologram(new Location(getPrisonWorld(), -1.5, 69, -8.5));
            h.addLine("§7§lCommands:");
            h.addLine("§c/kit");
            h.addLine("§c/cell");
            h.addLine("§c/rankup");
            h.addLine("§c/pay");
            h.addLine("§c/mines");
            h.addLine("§c/topgold");
            h.create();

            getApi().registerHologram(h);
        }
        {
            Hologram h = new Hologram(new Location(getLobby(), -3.5, 70, 0.5));
            h.addLine("§7§lCommands:");
            h.addLine("§c/kit");
            h.addLine("§c/cell");
            h.addLine("§c/rankup");
            h.addLine("§c/pay");
            h.addLine("§c/mines");
            h.addLine("§c/topgold");
            h.create();

            getApi().registerHologram(h);
        }

        for(Mine mine : getMines()){
            mine.spawnNpcs();
        }
    }
}
