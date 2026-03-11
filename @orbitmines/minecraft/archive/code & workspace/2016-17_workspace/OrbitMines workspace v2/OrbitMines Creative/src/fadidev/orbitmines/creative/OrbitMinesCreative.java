package fadidev.orbitmines.creative;

import fadidev.orbitmines.api.OrbitMinesAPI;
import fadidev.orbitmines.api.handlers.npc.Hologram;
import fadidev.orbitmines.api.handlers.npc.NPC;
import fadidev.orbitmines.api.handlers.npc.NPCArmorStand;
import fadidev.orbitmines.api.utils.ItemUtils;
import fadidev.orbitmines.api.utils.WorldUtils;
import fadidev.orbitmines.api.utils.enums.Mob;
import fadidev.orbitmines.creative.cmd.PlotCommand;
import fadidev.orbitmines.creative.cmd.SpawnCommand;
import fadidev.orbitmines.creative.cmd.WorldEditCommand;
import fadidev.orbitmines.creative.events.*;
import fadidev.orbitmines.creative.handlers.CreativePlayer;
import fadidev.orbitmines.creative.handlers.Plot;
import fadidev.orbitmines.creative.inventories.CreativeOMTShopInv;
import fadidev.orbitmines.creative.inventories.PlotInfoInv;
import fadidev.orbitmines.creative.managers.CreativeConfigManager;
import fadidev.orbitmines.creative.runnables.BeaconRunnable;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.EulerAngle;

import java.util.*;

/**
 * Created by Fadi on 14-9-2016.
 */
public class OrbitMinesCreative extends JavaPlugin {

    private static OrbitMinesCreative creative;
    private OrbitMinesAPI api;
    private Creative omServer;
    private Permission permission = null;

    private Map<Player, CreativePlayer> players;
    private List<CreativePlayer> creativePlayers;

    private CreativeConfigManager configManager;

    private World lobby;
    private World plotWorld;
    private Location spawn;
    private List<Plot> plots;
    private int lastPlotId;
    private List<Block> beacons;

    @Override
    public void onEnable() {
        creative = this;
        omServer = new Creative();
        api = omServer.getApi();

        this.configManager = new CreativeConfigManager();
        getConfigManager().setup("plots", "playerdata");

        this.players = new HashMap<>();
        this.creativePlayers = new ArrayList<>();

        this.lobby = Bukkit.getWorld("CreativeLobby");
        this.plotWorld = Bukkit.getWorld("Creative");
        this.spawn = new Location(getLobby(), 0, 74, 0, 45, 0);
        this.plots = new ArrayList<>();
        this.lastPlotId = getConfigManager().get("plots").getInt("LastPlotID");
        this.beacons = Arrays.asList(getLobby().getBlockAt(new Location(getLobby(), -5, 74, 12)), getLobby().getBlockAt(new Location(getLobby(), -13, 74, 4)));

        loadPlots();
        setupPermissions();
        registerRunnables();
        registerEvents();
        registerCommands();

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

    /* Getters & Setters */
    public static OrbitMinesCreative getCreative() {
        return creative;
    }

    public OrbitMinesAPI getApi() {
        return api;
    }

    public Creative getOmServer() {
        return omServer;
    }

    public Permission getPermission() {
        return permission;
    }

    public Map<Player, CreativePlayer> getPlayers() {
        return players;
    }

    public List<CreativePlayer> getCreativePlayers() {
        return creativePlayers;
    }

    public CreativeConfigManager getConfigManager() {
        return configManager;
    }

    public World getLobby() {
        return lobby;
    }

    public World getPlotWorld() {
        return plotWorld;
    }

    public Location getSpawn() {
        return spawn;
    }

    public List<Plot> getPlots() {
        return plots;
    }

    public int getLastPlotId() {
        return lastPlotId;
    }

    public void setLastPlotId(int lastPlotId) {
        this.lastPlotId = lastPlotId;

        getConfigManager().get("plots").set("LastPlotID", getLastPlotId());
        getConfigManager().save("plots");
    }

    public List<Block> getBeacons() {
        return beacons;
    }

    /* Other */

    /* Register */

    private void loadPlots(){
        List<Plot> plots = new ArrayList<>();
        if(getConfigManager().get("plots").contains("plots")){
            for(String stringPlotId : getConfigManager().get("plots").getConfigurationSection("plots").getKeys(false)){
                Plot plot = new Plot(Integer.parseInt(stringPlotId));
                plot.load();
                plots.add(plot);
            }
        }
        this.plots = plots;
    }

    private void registerRunnables(){
        new BeaconRunnable();
    }

    private void registerEvents(){
        getServer().getPluginManager().registerEvents(new BreakEvent(), this);
        getServer().getPluginManager().registerEvents(new ClickEvent(), this);
        getServer().getPluginManager().registerEvents(new CommandPreprocessEvent(), this);
        getServer().getPluginManager().registerEvents(new DamageByEntityEvent(), this);
        getServer().getPluginManager().registerEvents(new DeathEvent(), this);
        getServer().getPluginManager().registerEvents(new DropEvent(), this);
        getServer().getPluginManager().registerEvents(new ExplodeEvent(), this);
        getServer().getPluginManager().registerEvents(new FoodEvent(), this);
        getServer().getPluginManager().registerEvents(new FromToEvent(), this);
        getServer().getPluginManager().registerEvents(new InteractAtEntityEvent(), this);
        getServer().getPluginManager().registerEvents(new InteractEntityEvent(), this);
        getServer().getPluginManager().registerEvents(new InteractEvent(), this);
        getServer().getPluginManager().registerEvents(new JoinEvent(), this);
        getServer().getPluginManager().registerEvents(new MoveEvent(), this);
        getServer().getPluginManager().registerEvents(new PickupEvent(), this);
        getServer().getPluginManager().registerEvents(new PlaceEvent(), this);
        getServer().getPluginManager().registerEvents(new ProjHitEvent(), this);
        getServer().getPluginManager().registerEvents(new SignEvent(), this);
        getServer().getPluginManager().registerEvents(new SpawnEvent(), this);
    }

    private void registerCommands(){
        getApi().registerCommand(new PlotCommand());
        getApi().registerCommand(new SpawnCommand());
        getApi().registerCommand(new WorldEditCommand());
    }

    private boolean setupPermissions(){
        RegisteredServiceProvider<Permission> permissionProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.permission.Permission.class);
        if (permissionProvider != null) {
            permission = permissionProvider.getProvider();
        }
        return (permission != null);
    }

    private void spawnNpcs(){
        {
            NPCArmorStand npc = new NPCArmorStand(new Location(getLobby(), 7.5, 74, -7.5, 45, 0), new NPCArmorStand.InteractAction() {
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
            NPC npc = new NPC(Mob.BLAZE, new Location(getLobby(), 4.5, 74, -9.5, 0, 0), "§e§lOMT Shop", new NPC.InteractAction() {
                @Override
                public void click(Player player, NPC npc) {
                    new CreativeOMTShopInv().open(player);
                }
            });

            getApi().registerNpc(npc);
        }
        {
            Hologram h = new Hologram(new Location(getLobby(), -6.5, 75, 6.5, -50, 0));
            h.addLine("§d§lCreative");
            h.create();

            getApi().registerHologram(h);
        }
        {
            NPCArmorStand npc = new NPCArmorStand(new Location(getLobby(), -6.75, 75.5, 6.25, -50, 0));
            npc.setBodyPose(EulerAngle.ZERO.setX(0).setY(0).setZ(0));
            npc.setHeadPose(EulerAngle.ZERO.setX(0).setY(0).setZ(0));
            npc.setLeftArmPose(EulerAngle.ZERO.setX(0).setY(0).setZ(0));
            npc.setLeftLegPose(EulerAngle.ZERO.setX(0).setY(0).setZ(0));
            npc.setRightArmPose(EulerAngle.ZERO.setX(-1.1).setY(0).setZ(0));
            npc.setRightLegPose(EulerAngle.ZERO.setX(0).setY(0).setZ(0));
            npc.setGravity(false);
            npc.setVisible(false);
            npc.setItemInHand(new ItemStack(Material.WOOD_AXE, 1));
            npc.spawn();

            getApi().registerNpcArmorStand(npc);
        }
        {
            NPCArmorStand npc = new NPCArmorStand(new Location(getLobby(), -6.75, 75.5, 7.25, 140, 0));
            npc.setBodyPose(EulerAngle.ZERO.setX(0).setY(0).setZ(0));
            npc.setHeadPose(EulerAngle.ZERO.setX(0).setY(0).setZ(0));
            npc.setLeftArmPose(EulerAngle.ZERO.setX(0).setY(0).setZ(0));
            npc.setLeftLegPose(EulerAngle.ZERO.setX(0).setY(0).setZ(0));
            npc.setRightArmPose(EulerAngle.ZERO.setX(-1.1).setY(0).setZ(0));
            npc.setRightLegPose(EulerAngle.ZERO.setX(0).setY(0).setZ(0));
            npc.setGravity(false);
            npc.setVisible(false);
            npc.setItemInHand(new ItemStack(Material.WOOD_AXE, 1));
            npc.spawn();

            getApi().registerNpcArmorStand(npc);
        }
        {
            NPCArmorStand npc = new NPCArmorStand(new Location(getLobby(), 9.5, 74, -4.5, 90, 0), new NPCArmorStand.InteractAction() {
                @Override
                public void click(Player player, NPCArmorStand npcArmorStand) {
                    new PlotInfoInv().open(player);
                }
            });
            npc.setCustomName("§d§lPlot Info");
            npc.setCustomNameVisible(true);
            npc.setBodyPose(EulerAngle.ZERO.setX(0.1).setY(0).setZ(0));
            npc.setHeadPose(EulerAngle.ZERO.setX(0.15).setY(0).setZ(0));
            npc.setLeftArmPose(EulerAngle.ZERO.setX(0).setY(0).setZ(0));
            npc.setLeftLegPose(EulerAngle.ZERO.setX(0).setY(0).setZ(0));
            npc.setRightArmPose(EulerAngle.ZERO.setX(-0.75).setY(0).setZ(0));
            npc.setRightLegPose(EulerAngle.ZERO.setX(0).setY(0).setZ(0));
            npc.setGravity(false);
            npc.setVisible(false);
            npc.setHelmet(ItemUtils.addColor(new ItemStack(Material.LEATHER_HELMET), Color.FUCHSIA));
            npc.setChestplate(ItemUtils.addColor(new ItemStack(Material.LEATHER_CHESTPLATE), Color.FUCHSIA));
            npc.setLeggings(ItemUtils.addColor(new ItemStack(Material.LEATHER_LEGGINGS), Color.FUCHSIA));
            npc.setBoots(ItemUtils.addColor(new ItemStack(Material.LEATHER_BOOTS), Color.FUCHSIA));
            npc.setItemInHand(new ItemStack(Material.WOOD_AXE, 1));
            npc.spawn();

            getApi().registerNpcArmorStand(npc);
        }
    }
}
