package fadidev.orbitmines.minigames;

import fadidev.orbitmines.api.OrbitMinesAPI;
import fadidev.orbitmines.api.OrbitMinesServer;
import fadidev.orbitmines.api.handlers.Kit;
import fadidev.orbitmines.api.utils.ItemUtils;
import fadidev.orbitmines.api.utils.WorldUtils;
import fadidev.orbitmines.api.utils.enums.Language;
import fadidev.orbitmines.minigames.events.*;
import fadidev.orbitmines.minigames.events.bungee.BungeeMessageEvent;
import fadidev.orbitmines.minigames.handlers.Arena;
import fadidev.orbitmines.minigames.handlers.ChestItem;
import fadidev.orbitmines.minigames.handlers.MiniGamesMap;
import fadidev.orbitmines.minigames.handlers.players.MiniGamesPlayer;
import fadidev.orbitmines.minigames.managers.MiniGamesConfigManager;
import fadidev.orbitmines.minigames.runnables.ArenaRunnable;
import fadidev.orbitmines.minigames.utils.enums.MiniGameType;
import fadidev.orbitmines.minigames.utils.enums.TicketType;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

/**
 * Created by Fadi on 30-9-2016.
 */
public class OrbitMinesMiniGames extends JavaPlugin {

    private static OrbitMinesMiniGames miniGames;
    private OrbitMinesServer omServer;

    private MiniGamesConfigManager configManager;

    private World lobby;
    private List<Arena> arenas;
    
    private Map<Player, MiniGamesPlayer> players;
    private List<MiniGamesPlayer> miniGamesPlayers;
    private Map<MiniGameType, List<MiniGamesMap>> maps;
    private Map<String, Arena> playersToJoin;
    private List<ChestItem> sgChestItems;
    private List<ChestItem> sgChestPotions;
    private List<ChestItem> swChestItems;
    private List<ChestItem> swTier2ChestItems;

    private Map<Language, Kit> lobbyKit;
    private Map<Language, Kit> spectatorKit;

    @Override
    public void onEnable() {
        miniGames = this;
        this.omServer = new MiniGames();

        this.configManager = new MiniGamesConfigManager();
        getConfigManager().setup();
        
        this.lobby = Bukkit.getWorld("MiniGamesLobby");
        this.playersToJoin = new HashMap<>();
        this.lobbyKit = new HashMap<>();
        this.spectatorKit = new HashMap<>();

        this.players = new HashMap<>();
        this.miniGamesPlayers = new ArrayList<>();

        for(TicketType type : TicketType.values()){
            type.setup();
        }

        registerBungee();
        registerKits();
        registerMaps();
        registerArenas();
        registerSGChestItems();
        registerSWChestItems();
        registerCommands();
        registerEvents();
        registerRunnables();

        new BukkitRunnable(){
            @Override
            public void run() {
                WorldUtils.removeAllEntities();
                spawnNpcs();
            }
        }.runTaskLater(this, 100);
    }

    @Override
    public void onDisable() {

    }

    public static OrbitMinesMiniGames getMiniGames() {
        return miniGames;
    }

    public OrbitMinesServer getOmServer() {
        return omServer;
    }

    public OrbitMinesAPI getApi(){
        return getOmServer().getApi();
    }

    public MiniGamesConfigManager getConfigManager() {
        return configManager;
    }

    public World getLobby() {
        return lobby;
    }

    public List<Arena> getArenas() {
        return arenas;
    }

    public Map<Player, MiniGamesPlayer> getPlayers() {
        return players;
    }

    public List<MiniGamesPlayer> getMiniGamesPlayers() {
        return miniGamesPlayers;
    }

    public Map<MiniGameType, List<MiniGamesMap>> getMaps() {
        return maps;
    }

    public Map<String, Arena> getPlayersToJoin() {
        return playersToJoin;
    }

    public List<ChestItem> getSgChestItems() {
        return sgChestItems;
    }

    public List<ChestItem> getSgChestPotions() {
        return sgChestPotions;
    }

    public List<ChestItem> getSwChestItems() {
        return swChestItems;
    }

    public List<ChestItem> getSwTier2ChestItems() {
        return swTier2ChestItems;
    }

    public Map<Language, Kit> getLobbyKit() {
        return lobbyKit;
    }

    public Map<Language, Kit> getSpectatorKit() {
        return spectatorKit;
    }

    /* Register */
    private void registerCommands(){

    }

    private void registerBungee(){
        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", new BungeeMessageEvent());
        getServer().getMessenger().registerOutgoingPluginChannel(this, "OrbitMinesMiniGames");
        getServer().getMessenger().registerIncomingPluginChannel(this, "OrbitMinesMiniGames", new BungeeMessageEvent());
    }

    private void registerEvents(){
        getServer().getPluginManager().registerEvents(new BreakEvent(), this);
        getServer().getPluginManager().registerEvents(new ClickEvent(), this);
        getServer().getPluginManager().registerEvents(new DamageByEntityEvent(), this);
        getServer().getPluginManager().registerEvents(new DamageEvent(), this);
        getServer().getPluginManager().registerEvents(new DeathEvent(), this);
        getServer().getPluginManager().registerEvents(new DropEvent(), this);
        getServer().getPluginManager().registerEvents(new EntityInteractEvent(), this);
        getServer().getPluginManager().registerEvents(new FoodEvent(), this);
        getServer().getPluginManager().registerEvents(new InteractEvent(), this);
        getServer().getPluginManager().registerEvents(new InventoryEvent(), this);
        getServer().getPluginManager().registerEvents(new JoinEvent(), this);
        getServer().getPluginManager().registerEvents(new MoveEvent(), this);
        getServer().getPluginManager().registerEvents(new PickupEvent(), this);
        getServer().getPluginManager().registerEvents(new PlaceEvent(), this);
        getServer().getPluginManager().registerEvents(new ProjHitEvent(), this);
        getServer().getPluginManager().registerEvents(new ToggleFlightEvent(), this);
    }

    private void registerRunnables(){
        new ArenaRunnable();
    }

    private void registerKits(){
        {
            Kit kit = new Kit("Lobby_" + Language.ENGLISH.toString());
            kit.setItem(0, ItemUtils.itemstack(Material.SKULL_ITEM, 1, "§2§nStats", 3));
            kit.setItem(1, ItemUtils.itemstack(Material.BREWING_STAND_ITEM, 1, "§e§nGame Effects"));
            kit.setItem(4, ItemUtils.itemstack(Material.ENDER_PEARL, 1, "§3§nBack to the Hub"));
            kit.setItem(8, ItemUtils.itemstack(Material.ENDER_CHEST, 1, "§9§nCosmetic Perks"));
            
            getApi().registerKit(kit);
            getLobbyKit().put(Language.ENGLISH, kit);
        }
        {
            Kit kit = new Kit("Spectator_" + Language.ENGLISH.toString());
            kit.setItem(3, ItemUtils.itemstack(Material.NAME_TAG, 1, "§e§nTeleporter"));
            kit.setItem(5, ItemUtils.itemstack(Material.ENDER_PEARL, 1, "§3§nBack to the Hub"));

            getApi().registerKit(kit);
            getSpectatorKit().put(Language.ENGLISH, kit);
        }
        {
            Kit kit = new Kit("Lobby_" + Language.DUTCH.toString());
            kit.setItem(0, ItemUtils.itemstack(Material.SKULL_ITEM, 1, "§2§nStats", 3));
            kit.setItem(1, ItemUtils.itemstack(Material.BREWING_STAND_ITEM, 1, "§e§nGame Effects"));
            kit.setItem(4, ItemUtils.itemstack(Material.ENDER_PEARL, 1, "§3§nTerug naar de Hub"));
            kit.setItem(8, ItemUtils.itemstack(Material.ENDER_CHEST, 1, "§9§nCosmetic Perks"));

            getApi().registerKit(kit);
            getLobbyKit().put(Language.DUTCH, kit);
        }
        {
            Kit kit = new Kit("Spectator_" + Language.ENGLISH.toString());
            kit.setItem(3, ItemUtils.itemstack(Material.NAME_TAG, 1, "§e§nTeleporter"));
            kit.setItem(5, ItemUtils.itemstack(Material.ENDER_PEARL, 1, "§3§nTerug naar de Hub"));

            getApi().registerKit(kit);
            getSpectatorKit().put(Language.DUTCH, kit);
        }

        {
            Kit kit = new Kit(TicketType.RUNNER_KIT.toString());

            getApi().registerKit(kit);
        }
        {
            String type = TicketType.FIGHTER_KIT.getRarity().getColor() + TicketType.FIGHTER_KIT.getName();
            Kit kit = new Kit(TicketType.FIGHTER_KIT.toString());
            kit.setRandomItem(0, Arrays.asList(ItemUtils.itemstack(Material.WOOD_SWORD, 1, null, 0, type), ItemUtils.itemstack(Material.WOOD_AXE, 1, null, 0, type), ItemUtils.itemstack(Material.STONE_SWORD, 1, null, 0, type), ItemUtils.itemstack(Material.STONE_AXE, 1, null, 0, type)));

            getApi().registerKit(kit);
        }
        {
            String type = TicketType.ARCHER_KIT.getRarity().getColor() + TicketType.FIGHTER_KIT.getName();
            Kit kit = new Kit(TicketType.ARCHER_KIT.toString());
            kit.setItem(0, ItemUtils.itemstack(Material.BOW, 1, null, 0, type));
            kit.setItem(8, ItemUtils.itemstack(Material.ARROW, 2, null, 0,type));

            getApi().registerKit(kit);
        }
        {
            String type = TicketType.WARRIOR_KIT.getRarity().getColor() + TicketType.WARRIOR_KIT.getName();
            Kit kit = new Kit(TicketType.WARRIOR_KIT.toString());
            kit.setRandomItem(0, Arrays.asList(ItemUtils.itemstack(Material.LEATHER_BOOTS, 1, null, 0, type), ItemUtils.itemstack(Material.LEATHER_LEGGINGS, 1, null, 0, type), ItemUtils.itemstack(Material.LEATHER_CHESTPLATE, 1, null, 0, type), ItemUtils.itemstack(Material.LEATHER_HELMET, 1, null, 0, type), ItemUtils.itemstack(Material.GOLD_BOOTS, 1, null, 0, type), ItemUtils.itemstack(Material.GOLD_LEGGINGS, 1, null, 0, type), ItemUtils.itemstack(Material.GOLD_CHESTPLATE, 1, null, 0, type), ItemUtils.itemstack(Material.GOLD_HELMET, 1, null, 0, type), ItemUtils.itemstack(Material.CHAINMAIL_BOOTS, 1, null, 0, type), ItemUtils.itemstack(Material.CHAINMAIL_LEGGINGS, 1, null, 0, type), ItemUtils.itemstack(Material.CHAINMAIL_CHESTPLATE, 1, null, 0, type), ItemUtils.itemstack(Material.CHAINMAIL_HELMET, 1, null, 0, type), ItemUtils.itemstack(Material.IRON_BOOTS, 1, null, 0, type), ItemUtils.itemstack(Material.IRON_LEGGINGS, 1, null, 0, type), ItemUtils.itemstack(Material.IRON_CHESTPLATE, 1, null, 0, type), ItemUtils.itemstack(Material.IRON_HELMET, 1, null, 0, type)));
            kit.setRandomItem(1, Arrays.asList(ItemUtils.itemstack(Material.LEATHER_BOOTS, 1, null, 0, type), ItemUtils.itemstack(Material.LEATHER_LEGGINGS, 1, null, 0, type), ItemUtils.itemstack(Material.LEATHER_CHESTPLATE, 1, null, 0, type), ItemUtils.itemstack(Material.LEATHER_HELMET, 1, null, 0, type), ItemUtils.itemstack(Material.GOLD_BOOTS, 1, null, 0, type), ItemUtils.itemstack(Material.GOLD_LEGGINGS, 1, null, 0, type), ItemUtils.itemstack(Material.GOLD_CHESTPLATE, 1, null, 0, type), ItemUtils.itemstack(Material.GOLD_HELMET, 1, null, 0, type), ItemUtils.itemstack(Material.CHAINMAIL_BOOTS, 1, null, 0, type), ItemUtils.itemstack(Material.CHAINMAIL_LEGGINGS, 1, null, 0, type), ItemUtils.itemstack(Material.CHAINMAIL_CHESTPLATE, 1, null, 0, type), ItemUtils.itemstack(Material.CHAINMAIL_HELMET, 1, null, 0, type), ItemUtils.itemstack(Material.IRON_BOOTS, 1, null, 0, type), ItemUtils.itemstack(Material.IRON_LEGGINGS, 1, null, 0, type), ItemUtils.itemstack(Material.IRON_CHESTPLATE, 1, null, 0, type), ItemUtils.itemstack(Material.IRON_HELMET, 1, null, 0, type)));

            getApi().registerKit(kit);
        }
        {
            String type = TicketType.BOMBER_KIT.getRarity().getColor() + TicketType.BOMBER_KIT.getName();
            Kit kit = new Kit(TicketType.BOMBER_KIT.toString());
            kit.setItem(0, ItemUtils.itemstack(Material.TNT, 10, null, 0, type));

            getApi().registerKit(kit);
        }

        {
            String type = TicketType.SURVIVOR_KIT.getRarity().getColor() + TicketType.SURVIVOR_KIT.getName();
            Kit kit = new Kit(TicketType.SURVIVOR_KIT.toString());
            kit.setItem(0, ItemUtils.itemstack(Material.COOKED_BEEF, 15, null, 0, type));

            getApi().registerKit(kit);
        }
        {
            String type = TicketType.STARTER_KIT.getRarity().getColor() + TicketType.STARTER_KIT.getName();
            Kit kit = new Kit(TicketType.STARTER_KIT.toString());
            kit.setItem(0, ItemUtils.itemstack(Material.WOOD_SWORD, 1, null, 0, type));
            kit.setItem(1, ItemUtils.itemstack(Material.WOOD_PICKAXE, 1, null, 0, type));
            kit.setItem(2, ItemUtils.itemstack(Material.WOOD_AXE, 1, null, 0, type));
            kit.setItem(3, ItemUtils.itemstack(Material.WOOD_SPADE, 1, null, 0, type));

            getApi().registerKit(kit);
        }
        {
            Kit kit = new Kit(TicketType.APPLETREE_KIT.toString());

            getApi().registerKit(kit);
        }
        {
            String type = TicketType.SPEEDSTER_KIT.getRarity().getColor() + TicketType.SPEEDSTER_KIT.getName();
            Kit kit = new Kit(TicketType.SPEEDSTER_KIT.toString());
            kit.setItem(0, ItemUtils.itemstack(Material.POTION, 1, null, 8194, type));

            getApi().registerKit(kit);
        }
        {
            Kit kit = new Kit(TicketType.MINER_KIT.toString());
            kit.setPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 18000, 0));

            getApi().registerKit(kit);
        }
        {
            String type = TicketType.TANK_KIT.getRarity().getColor() + TicketType.TANK_KIT.getName();
            Kit kit = new Kit(TicketType.TANK_KIT.toString());
            kit.setItem(0, ItemUtils.itemstack(Material.LEATHER_HELMET, 1, null, 0, type));
            kit.setItem(1, ItemUtils.itemstack(Material.CHAINMAIL_CHESTPLATE, 1, null, 0, type));
            kit.setItem(2, ItemUtils.itemstack(Material.CHAINMAIL_LEGGINGS, 1, null, 0, type));
            kit.setItem(3, ItemUtils.itemstack(Material.LEATHER_BOOTS, 1, null, 0, type));

            getApi().registerKit(kit);
        }
        {
            String type = TicketType.SNOWGOLEM_KIT.getRarity().getColor() + TicketType.SNOWGOLEM_KIT.getName();
            Kit kit = new Kit(TicketType.SNOWGOLEM_KIT.toString());
            kit.setItem(0, ItemUtils.itemstack(Material.CARROT_ITEM, 1, null, 0, type));
            kit.setItem(1, ItemUtils.addColor(ItemUtils.itemstack(Material.LEATHER_HELMET, 1, null, 0, type), Color.BLACK));
            kit.setItem(2, ItemUtils.itemstack(Material.SNOW_BALL, 16, null, 0, type));
            kit.setItem(3, ItemUtils.itemstack(Material.SNOW_BALL, 16, null, 0, type));
            kit.setItem(4, ItemUtils.itemstack(Material.SNOW_BALL, 16, null, 0, type));

            getApi().registerKit(kit);
        }
        {
            String type = TicketType.CREEPER_KIT.getRarity().getColor() + TicketType.CREEPER_KIT.getName();
            Kit kit = new Kit(TicketType.CREEPER_KIT.toString());
            kit.setItem(0, ItemUtils.itemstack(Material.TNT, 20, null, 0, type));
            kit.setItem(1, ItemUtils.itemstack(Material.FLINT_AND_STEEL, 1, null, 0, type));

            getApi().registerKit(kit);
        }
        {
            String type = TicketType.ENCHANTER_KIT.getRarity().getColor() + TicketType.ENCHANTER_KIT.getName();
            Kit kit = new Kit(TicketType.ENCHANTER_KIT.toString());
            kit.setItem(0, ItemUtils.itemstack(Material.ENCHANTMENT_TABLE, 1, null, 0, type));
            kit.setItem(1, ItemUtils.itemstack(Material.EXP_BOTTLE, 15, null, 0, type));

            getApi().registerKit(kit);
        }
        {
            String type = TicketType.ENDERMAN_KIT.getRarity().getColor() + TicketType.ENDERMAN_KIT.getName();
            Kit kit = new Kit(TicketType.ENDERMAN_KIT.toString());
            kit.setItem(0, ItemUtils.itemstack(Material.ENDER_PEARL, 2, null, 0, type));

            getApi().registerKit(kit);
        }
        {
            String type = TicketType.CHICKEN_MAMA_KIT.getRarity().getColor() + TicketType.CHICKEN_MAMA_KIT.getName();
            Kit kit = new Kit(TicketType.CHICKEN_MAMA_KIT.toString());
            kit.setItem(0, ItemUtils.itemstack(Material.FEATHER, 1, "§f§lFeather Attack", 0, type));

            getApi().registerKit(kit);
        }
        {
            String type = TicketType.BABY_CHICKEN_KIT.getRarity().getColor() + TicketType.BABY_CHICKEN_KIT.getName();
            Kit kit = new Kit(TicketType.BABY_CHICKEN_KIT.toString());
            kit.setItem(0, ItemUtils.itemstack(Material.EGG, 1, "§f§lEgg Bomb", 0, type));

            getApi().registerKit(kit);
        }
        {
            String type = TicketType.HOT_WING_KIT.getRarity().getColor() + TicketType.HOT_WING_KIT.getName();
            Kit kit = new Kit(TicketType.HOT_WING_KIT.toString());
            kit.setItem(0, ItemUtils.itemstack(Material.FIREBALL, 1, "§f§lFire Shield", 0, type));

            getApi().registerKit(kit);
        }
        {
            String type = TicketType.CHICKEN_WARLORD_KIT.getRarity().getColor() + TicketType.CHICKEN_WARLORD_KIT.getName();
            Kit kit = new Kit(TicketType.CHICKEN_WARLORD_KIT.toString());
            kit.setItem(0, ItemUtils.itemstack(Material.IRON_INGOT, 1, "§f§lIron Fist", 0, type));
            kit.setPotionEffect(new PotionEffect(PotionEffectType.SLOW, 2000000, 2));

            getApi().registerKit(kit);
        }
        {
            Kit kit = new Kit("GhostKit");
            kit.setPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 2000000, 0));

            kit.setItem(0, ItemUtils.addEnchantment(ItemUtils.itemstack(Material.GHAST_TEAR, 1, "§7§lGhost Tear"), Enchantment.DAMAGE_ALL, 5));
            kit.setItem(2, ItemUtils.itemstack(Material.INK_SACK, 1, "§8§lBlindness"));
            kit.setItem(3, ItemUtils.addEffect(ItemUtils.itemstack(Material.LINGERING_POTION, 3, "§c§lHealing Potion"), PotionEffectType.HEAL, 0, 0, true));
            kit.setItem(8, ItemUtils.itemstack(Material.COMPASS, 1, "§6§lTracker"));
            kit.setChestplate(ItemUtils.itemstack(Material.ELYTRA, 1, "§7§lElytra"));
            kit.setItemOffHand(ItemUtils.itemstack(Material.SHIELD, 1, "§7§lShield"));

            getApi().registerKit(kit);
        }
        {
            String type = TicketType.GHOST_BUSTER_KIT.getRarity().getColor() + TicketType.GHOST_BUSTER_KIT.getName();
            Kit kit = new Kit(TicketType.GHOST_BUSTER_KIT.toString());
            kit.setItem(0, ItemUtils.itemstack(Material.WOOD_SWORD, 1, "§6§lWooden Sword", 0, type));
            kit.setItem(2, ItemUtils.itemstack(Material.END_ROD, 1, "§f§lKnockback Rod", 0, type));
            kit.setItem(3, ItemUtils.itemstack(Material.REDSTONE_TORCH_ON, 1, "§c§lTrap", 0, type));
            kit.setHelmet(ItemUtils.addColor(ItemUtils.itemstack(Material.LEATHER_HELMET, 1, "§8§lHelmet", 0, type), Color.GRAY));
            kit.setChestplate(ItemUtils.addColor(ItemUtils.itemstack(Material.LEATHER_CHESTPLATE, 1, "§8§lChestplate", 0, type), Color.GRAY));
            kit.setLeggings(ItemUtils.addColor(ItemUtils.itemstack(Material.LEATHER_LEGGINGS, 1, "§f§lLeggings", 0, type), Color.WHITE));
            kit.setBoots(ItemUtils.addColor(ItemUtils.itemstack(Material.LEATHER_BOOTS, 1, "§8§lBoots", 0, type), Color.GRAY));

            getApi().registerKit(kit);
        }
        {
            String type = TicketType.BUTCHER_KIT.getRarity().getColor() + TicketType.BUTCHER_KIT.getName();
            Kit kit = new Kit(TicketType.BUTCHER_KIT.toString());
            kit.setItem(0, ItemUtils.addEnchantment(ItemUtils.itemstack(Material.IRON_AXE, 1, "§7§lIron Axe", 0, type), Enchantment.DAMAGE_ALL, 1));
            kit.setItem(2, ItemUtils.itemstack(Material.PORK, 1, "§d§lPig Launcher", 0, type));
            kit.setItem(3, ItemUtils.itemstack(Material.INK_SACK, 1, "§c§lRage", 1, type));
            kit.setHelmet(ItemUtils.addColor(ItemUtils.itemstack(Material.LEATHER_HELMET, 1, "§8§lHelmet", 0, type), Color.GRAY));
            kit.setChestplate(ItemUtils.itemstack(Material.IRON_CHESTPLATE, 1, "§7§lChestplate", 0, type));
            kit.setLeggings(ItemUtils.addColor(ItemUtils.itemstack(Material.LEATHER_LEGGINGS, 1, "§8§lLeggings", 0, type), Color.GRAY));
            kit.setBoots(ItemUtils.addColor(ItemUtils.itemstack(Material.LEATHER_BOOTS, 1, "§8§lBoots", 0, type), Color.GRAY));

            getApi().registerKit(kit);
        }
        {
            String type = TicketType.BETRAYER_KIT.getRarity().getColor() + TicketType.BETRAYER_KIT.getName();
            Kit kit = new Kit(TicketType.BETRAYER_KIT.toString());
            kit.setItem(0, ItemUtils.itemstack(Material.STONE_SWORD, 1, "§7§lStone Sword", 0, type));
            kit.setItem(2, ItemUtils.itemstack(Material.WEB, 1, "§f§lInvulnerability", 0, type));
            kit.setItem(3, ItemUtils.itemstack(Material.DRAGONS_BREATH, 1, "§c§lLevitate", 0, type));
            kit.setHelmet(ItemUtils.itemstack(Material.SKULL, 1, "§7§lHelmet", 0, type));
            kit.setChestplate(ItemUtils.addColor(ItemUtils.itemstack(Material.LEATHER_CHESTPLATE, 1, "§f§lChestplate", 0, type), Color.WHITE));
            kit.setLeggings(ItemUtils.addColor(ItemUtils.itemstack(Material.LEATHER_LEGGINGS, 1, "§8§lLeggings", 0, type), Color.GRAY));
            kit.setBoots(ItemUtils.addColor(ItemUtils.itemstack(Material.LEATHER_BOOTS, 1, "§8§lBoots", 0, type), Color.GRAY));

            getApi().registerKit(kit);
        }
        {
            String type = TicketType.ASSASSIN_KIT.getRarity().getColor() + TicketType.ASSASSIN_KIT.getName();
            Kit kit = new Kit(TicketType.ASSASSIN_KIT.toString());
            kit.setItem(0, ItemUtils.addEnchantment(ItemUtils.itemstack(Material.IRON_SWORD, 1, "§7§lIron Sword", 0, type), Enchantment.DAMAGE_ALL, 1));
            kit.setItem(2, ItemUtils.itemstack(Material.LEVER, 1, "§7§lKnive", 0, type));
            kit.setItem(3, ItemUtils.itemstack(Material.SUGAR, 1, "§f§lSpeed Boost", 0, type));
            kit.setHelmet(ItemUtils.addColor(ItemUtils.itemstack(Material.LEATHER_HELMET, 1, "§8§lHelmet", 0, type), Color.GRAY));
            kit.setChestplate(ItemUtils.itemstack(Material.CHAINMAIL_CHESTPLATE, 1, "§7§lChestplate", 0, type));
            kit.setLeggings(ItemUtils.itemstack(Material.CHAINMAIL_LEGGINGS, 1, "§7§lLeggings", 0, type));
            kit.setBoots(ItemUtils.addColor(ItemUtils.itemstack(Material.LEATHER_BOOTS, 1, "§8§lBoots", 0, type), Color.GRAY));

            getApi().registerKit(kit);
        }
        {
            String type = TicketType.ARMORER_KIT.getRarity().getColor() + TicketType.ARMORER_KIT.getName();
            Kit kit = new Kit(TicketType.ARMORER_KIT.toString());
            kit.setItem(0, ItemUtils.addEnchantment(ItemUtils.itemstack(Material.IRON_SWORD, 1, "§7§lIron Sword", 0, type), Enchantment.DAMAGE_ALL, 1));
            kit.setHelmet(ItemUtils.itemstack(Material.CHAINMAIL_HELMET, 1, "§7§lHelmet", 0, type));
            kit.setChestplate(ItemUtils.itemstack(Material.IRON_CHESTPLATE, 1, "§7§lChestplate", 0, type));
            kit.setLeggings(ItemUtils.itemstack(Material.CHAINMAIL_LEGGINGS, 1, "§7§lLeggings", 0, type));
            kit.setBoots(ItemUtils.itemstack(Material.IRON_BOOTS, 1, "§7§lBoots", 0, type));

            getApi().registerKit(kit);
        }
        {
            Kit kit = new Kit(TicketType.CHICKEN_KIT.toString());

            getApi().registerKit(kit);
        }
    }

    private void registerMaps(){
        this.maps = new HashMap<>();
        List<MiniGamesMap> sgMaps = new ArrayList<>();
        {
            MiniGamesMap map = new MiniGamesMap("Abrax Park", "xFrozenYeti§7, §6iClarify", MiniGameType.SURVIVAL_GAMES, "AbraxParkSG");
            map.setSpectatorLocation(new Location(map.getWorld(), -183.5, 85.5, 64.5, -180, 90));
            map.setSpawns(Arrays.asList(new Location(map.getWorld(), -183.5, 66, 89.5, 180, 0), new Location(map.getWorld(), -177.5, 66, 89.5, 165, 0), new Location(map.getWorld(), -171.5, 66, 86.5, 150, 0), new Location(map.getWorld(), -165.5, 66, 82.5, 135, 0), new Location(map.getWorld(), -161.5, 66, 76.5, 120, 0), new Location(map.getWorld(), -158.5, 66, 70.5, 105, 0), new Location(map.getWorld(), -158.5, 66, 64.5, 90, 0), new Location(map.getWorld(), -158.5, 66, 58.5, 75, 0), new Location(map.getWorld(), -161.5, 66, 52.5, 60, 0), new Location(map.getWorld(), -165.5, 66, 46.5, 45, 0), new Location(map.getWorld(), -171.5, 66, 42.5, 30, 0), new Location(map.getWorld(), -177.5, 66, 39.5, 15, 0), new Location(map.getWorld(), -183.5, 66, 39.5, 0, 0), new Location(map.getWorld(), -183.5, 66, 89.5, -15, 0), new Location(map.getWorld(), -195.5, 66, 42.5, -30, 0), new Location(map.getWorld(), -201.5, 66, 46.5, -45, 0), new Location(map.getWorld(), -205.5, 66, 52.5, -60, 0), new Location(map.getWorld(), -208.5, 66, 58.5, -75, 0), new Location(map.getWorld(), -208.5, 66, 64.5, -90, 0), new Location(map.getWorld(), -208.5, 66, 70.5, -105, 0), new Location(map.getWorld(), -205.5, 66, 76.5, -120, 0), new Location(map.getWorld(), -201.5, 66, 82.5, -135, 0), new Location(map.getWorld(), -195.5, 66, 86.5, -150, 0), new Location(map.getWorld(), -189.5, 66, 89.5, -165, 0)));
            sgMaps.add(map);
        }
        {
            MiniGamesMap map = new MiniGamesMap("Breeze Island 2", "xBayani§7, §6static_nightmare§7, §6cameron224", MiniGameType.SURVIVAL_GAMES, "Breeze_Island2");
            map.setSpectatorLocation(new Location(map.getWorld(), 16.5, 83, 26.5, 0, 0));
            map.setSpawns(Arrays.asList(new Location(map.getWorld(), 16.5, 67, 49.5, 180, 0), new Location(map.getWorld(), 22.5, 67, 48.5, 165, 0), new Location(map.getWorld(), 27.5, 67, 46.5, 150, 0), new Location(map.getWorld(), 32.5, 67, 42.5, 135, 0), new Location(map.getWorld(), 36.5, 67, 37.5, 120, 0), new Location(map.getWorld(), 38.5, 67, 32.5, 105, 0), new Location(map.getWorld(), 39.5, 67, 26.5, 90, 0), new Location(map.getWorld(), 38.5, 67, 20.5, 75, 0), new Location(map.getWorld(), 36.5, 67, 15.5, 60, 0), new Location(map.getWorld(), 32.5, 67, 10.5, 45, 0), new Location(map.getWorld(), 27.5, 67, 6.5, 30, 0), new Location(map.getWorld(), 22.5, 67, 4.5, 15, 0), new Location(map.getWorld(), 16.5, 67, 3.5, 0, 0), new Location(map.getWorld(), 10.5, 67, 4.5, -15, 0), new Location(map.getWorld(), 5.5, 67, 6.5, -30, 0), new Location(map.getWorld(), 0.5, 67, 10.5, -45, 0), new Location(map.getWorld(), -3.5, 67, 15.5, -60, 0), new Location(map.getWorld(), -5.5, 67, 20.5, -75, 0), new Location(map.getWorld(), -6.5, 67, 26.5, -90, 0), new Location(map.getWorld(), -5.5, 67, 32.5, -105, 0), new Location(map.getWorld(), -3.5, 67, 37.5, -120, 0), new Location(map.getWorld(), 0.5, 67, 42.5, -135, 0), new Location(map.getWorld(), 5.5, 67, 46.5, -150, 0), new Location(map.getWorld(), 10.5, 67, 48.5, -165, 0)));
            sgMaps.add(map);
        }
        {
            MiniGamesMap map = new MiniGamesMap("Estyr", "Team Dmon", MiniGameType.SURVIVAL_GAMES, "Estyr");
            map.setSpectatorLocation(new Location(map.getWorld(), -0.5, 92.5, 0.5, 90, 90));
            map.setSpawns(Arrays.asList(new Location(map.getWorld(), -0.5, 74, 25.5, 180, 0), new Location(map.getWorld(), 6.5, 74, 24.5, 165, 0), new Location(map.getWorld(), 12.5, 74, 21.5, 150, 0), new Location(map.getWorld(), 17.5, 74, 18.5, 135, 0), new Location(map.getWorld(), 20.5, 74, 13.5, 120, 0), new Location(map.getWorld(), 23.5, 74, 7.5, 105, 0), new Location(map.getWorld(), 24.5, 74, 0.5, 90, 0), new Location(map.getWorld(), 23.5, 74, -6.5, 75, 0), new Location(map.getWorld(), 20.5, 74, -12.5, 60, 0), new Location(map.getWorld(), 17.5, 74, -17.5, 45, 0), new Location(map.getWorld(), 12.5, 74, -20.5, 30, 0), new Location(map.getWorld(), 6.5, 74, -23.5, 15, 0), new Location(map.getWorld(), -0.5, 74, -24.5, 0, 0), new Location(map.getWorld(), -7.5, 74, -23.5, -15, 0), new Location(map.getWorld(), -13.5, 74, -20.5, -30, 0), new Location(map.getWorld(), -18.5, 74, -17.5, -45, 0), new Location(map.getWorld(), -21.5, 74, -12.5, -60, 0), new Location(map.getWorld(), -24.5, 74, -6.5, -75, 0), new Location(map.getWorld(), -25.5, 74, 0.5, -90, 0), new Location(map.getWorld(), -24.5, 74, 7.5, -105, 0), new Location(map.getWorld(), -21.5, 74, 13.5, -120, 0), new Location(map.getWorld(), -18.5, 74, 18.5, -135, 0), new Location(map.getWorld(), -13.5, 74, 21.5, -150, 0), new Location(map.getWorld(), -7.5, 74, 24.5, -165, 0)));
            sgMaps.add(map);
        }
        {
            MiniGamesMap map = new MiniGamesMap("Rise of the Orient", "Team OPAlien", MiniGameType.SURVIVAL_GAMES, "Rise_of_the_Orient");
            map.setSpectatorLocation(new Location(map.getWorld(), 0.5, 49.5, 0.5, 180, 0));
            map.setSpawns(Arrays.asList(new Location(map.getWorld(), 0.5, 26, 20.5, 180, 0), new Location(map.getWorld(), 5.5, 26, 19.5, 165, 0), new Location(map.getWorld(), 10.5, 26, 17.5, 150, 0), new Location(map.getWorld(), 14.5, 26, 20.5, 135, 0), new Location(map.getWorld(), 17.5, 26, 10.5, 120, 0), new Location(map.getWorld(), 19.5, 26, 5.5, 105, 0), new Location(map.getWorld(), 19.5, 26, 0.5, 90, 0), new Location(map.getWorld(), 19.5, 26, -4.5, 75, 0), new Location(map.getWorld(), 17.5, 26, -9.5, 60, 0), new Location(map.getWorld(), 14.5, 26, -13.5, 45, 0), new Location(map.getWorld(), 10.5, 26, -16.5, 30, 0), new Location(map.getWorld(), 5.5, 26, -18.5, 15, 0), new Location(map.getWorld(), 0.5, 26, -18.5, 0, 0), new Location(map.getWorld(), 4.5, 26, -18.5, -15, 0), new Location(map.getWorld(), -9.5, 26, -16.5, -30, 0), new Location(map.getWorld(), -13.5, 26, -13.5, -45, 0), new Location(map.getWorld(), -16.5, 26, -9.5, -60, 0), new Location(map.getWorld(), -18.5, 26, -4.5, -75, 0), new Location(map.getWorld(), -18.5, 26, 0.5, -90, 0), new Location(map.getWorld(), -18.5, 26, 5.5, -105, 0), new Location(map.getWorld(), -16.5, 26, 10.5, -120, 0), new Location(map.getWorld(), -13.5, 26, 14.5, -135, 0), new Location(map.getWorld(), -9.5, 26, 17.5, -150, 0), new Location(map.getWorld(), -4.5, 26, 19.5, -165, 0)));
            sgMaps.add(map);
        }
        this.maps.put(MiniGameType.SURVIVAL_GAMES, sgMaps);

        List<MiniGamesMap> uhcMaps = new ArrayList<>();
        {
            MiniGamesMap map = new MiniGamesMap("UHC World", "Alderius§7, §6playwarrior§7, §6eekhoorn2000", MiniGameType.ULTRA_HARD_CORE, "UHC_1");
            map.setSpawns(Arrays.asList(new Location(map.getWorld(), -400.5, 100, -300.5, 90, 0), new Location(map.getWorld(), -400.5, 100, -200.5, 90, 0), new Location(map.getWorld(), -400.5, 100, -100.5, 90, 0), new Location(map.getWorld(), -400.5, 100, 0.5, 90, 0), new Location(map.getWorld(), -400.5, 100, 100.5, 90, 0), new Location(map.getWorld(), -400.5, 100, 200.5, 90, 0), new Location(map.getWorld(), -400.5, 100, 300.5, 90, 0), new Location(map.getWorld(), -300.5, 100, -400.5, 90, 0), new Location(map.getWorld(), -300.5, 100, -200.5, 90, 0), new Location(map.getWorld(), -300.5, 100, -100.5, 90, 0), new Location(map.getWorld(), -300.5, 100, 100.5, 90, 0), new Location(map.getWorld(), -300.5, 100, 200.5, 90, 0), new Location(map.getWorld(), -300.5, 100, 400.5, 90, 0), new Location(map.getWorld(), -200.5, 100, -400.5, 90, 0), new Location(map.getWorld(), -200.5, 100, -300.5, 90, 0), new Location(map.getWorld(), -200.5, 100, -100.5, 90, 0), new Location(map.getWorld(), -200.5, 100, 100.5, 90, 0), new Location(map.getWorld(), -200.5, 100, 300.5, 90, 0), new Location(map.getWorld(), -200.5, 100, 400.5, 90, 0), new Location(map.getWorld(), -100.5, 100, -400.5, 90, 0), new Location(map.getWorld(), -100.5, 100, -300.5, 90, 0), new Location(map.getWorld(), -100.5, 100, -200.5, 90, 0), new Location(map.getWorld(), -100.5, 100, 200.5, 90, 0), new Location(map.getWorld(), -100.5, 100, 300.5, 90, 0), new Location(map.getWorld(), -100.5, 100, 400.5, 90, 0), new Location(map.getWorld(), 100.5, 100, -400.5, 90, 0), new Location(map.getWorld(), 100.5, 100, -300.5, 90, 0), new Location(map.getWorld(), 100.5, 100, -200.5, 90, 0), new Location(map.getWorld(), 100.5, 100, 200.5, 90, 0), new Location(map.getWorld(), 100.5, 100, 300.5, 90, 0), new Location(map.getWorld(), 100.5, 100, 400.5, 90, 0), new Location(map.getWorld(), 200.5, 100, -400.5, 90, 0), new Location(map.getWorld(), 200.5, 100, -300.5, 90, 0), new Location(map.getWorld(), 200.5, 100, -100.5, 90, 0), new Location(map.getWorld(), 200.5, 100, 100.5, 90, 0), new Location(map.getWorld(), 200.5, 100, 300.5, 90, 0), new Location(map.getWorld(), 200.5, 100, 400.5, 90, 0), new Location(map.getWorld(), 300.5, 100, -400.5, 90, 0), new Location(map.getWorld(), 300.5, 100, -200.5, 90, 0), new Location(map.getWorld(), 300.5, 100, -100.5, 90, 0), new Location(map.getWorld(), 300.5, 100, 100.5, 90, 0), new Location(map.getWorld(), 300.5, 100, 200.5, 90, 0), new Location(map.getWorld(), 300.5, 100, 400.5, 90, 0), new Location(map.getWorld(), 400.5, 100, -300.5, 90, 0), new Location(map.getWorld(), 400.5, 100, -200.5, 90, 0), new Location(map.getWorld(), 400.5, 100, -100.5, 90, 0), new Location(map.getWorld(), 400.5, 100, 0.5, 90, 0), new Location(map.getWorld(), 400.5, 100, 100.5, 90, 0), new Location(map.getWorld(), 400.5, 100, 200.5, 90, 0), new Location(map.getWorld(), 400.5, 100, 300.5, 90, 0)));
            uhcMaps.add(map);
        }
			/*{
				MiniGamesMap map = new MiniGamesMap("UHC World", "Alderius§7, §6playwarrior§7, §6eekhoorn2000", MiniGameType.ULTRA_HARD_CORE, "UHC_2", true);
				map.setSpawns(Arrays.asList(new Location(map.getWorld(), -400.5, 100, -300.5, 90, 0), new Location(map.getWorld(), -400.5, 100, -200.5, 90, 0), new Location(map.getWorld(), -400.5, 100, -100.5, 90, 0), new Location(map.getWorld(), -400.5, 100, 0.5, 90, 0), new Location(map.getWorld(), -400.5, 100, 100.5, 90, 0), new Location(map.getWorld(), -400.5, 100, 200.5, 90, 0), new Location(map.getWorld(), -400.5, 100, 300.5, 90, 0), new Location(map.getWorld(), -300.5, 100, -400.5, 90, 0), new Location(map.getWorld(), -300.5, 100, -200.5, 90, 0), new Location(map.getWorld(), -300.5, 100, -100.5, 90, 0), new Location(map.getWorld(), -300.5, 100, 100.5, 90, 0), new Location(map.getWorld(), -300.5, 100, 200.5, 90, 0), new Location(map.getWorld(), -300.5, 100, 400.5, 90, 0), new Location(map.getWorld(), -200.5, 100, -400.5, 90, 0), new Location(map.getWorld(), -200.5, 100, -300.5, 90, 0), new Location(map.getWorld(), -200.5, 100, -100.5, 90, 0), new Location(map.getWorld(), -200.5, 100, 100.5, 90, 0), new Location(map.getWorld(), -200.5, 100, 300.5, 90, 0), new Location(map.getWorld(), -200.5, 100, 400.5, 90, 0), new Location(map.getWorld(), -100.5, 100, -400.5, 90, 0), new Location(map.getWorld(), -100.5, 100, -300.5, 90, 0), new Location(map.getWorld(), -100.5, 100, -200.5, 90, 0), new Location(map.getWorld(), -100.5, 100, 200.5, 90, 0), new Location(map.getWorld(), -100.5, 100, 300.5, 90, 0), new Location(map.getWorld(), -100.5, 100, 400.5, 90, 0), new Location(map.getWorld(), 100.5, 100, -400.5, 90, 0), new Location(map.getWorld(), 100.5, 100, -300.5, 90, 0), new Location(map.getWorld(), 100.5, 100, -200.5, 90, 0), new Location(map.getWorld(), 100.5, 100, 200.5, 90, 0), new Location(map.getWorld(), 100.5, 100, 300.5, 90, 0), new Location(map.getWorld(), 100.5, 100, 400.5, 90, 0), new Location(map.getWorld(), 200.5, 100, -400.5, 90, 0), new Location(map.getWorld(), 200.5, 100, -300.5, 90, 0), new Location(map.getWorld(), 200.5, 100, -100.5, 90, 0), new Location(map.getWorld(), 200.5, 100, 100.5, 90, 0), new Location(map.getWorld(), 200.5, 100, 300.5, 90, 0), new Location(map.getWorld(), 200.5, 100, 400.5, 90, 0), new Location(map.getWorld(), 300.5, 100, -400.5, 90, 0), new Location(map.getWorld(), 300.5, 100, -200.5, 90, 0), new Location(map.getWorld(), 300.5, 100, -100.5, 90, 0), new Location(map.getWorld(), 300.5, 100, 100.5, 90, 0), new Location(map.getWorld(), 300.5, 100, 200.5, 90, 0), new Location(map.getWorld(), 300.5, 100, 400.5, 90, 0), new Location(map.getWorld(), 400.5, 100, -300.5, 90, 0), new Location(map.getWorld(), 400.5, 100, -200.5, 90, 0), new Location(map.getWorld(), 400.5, 100, -100.5, 90, 0), new Location(map.getWorld(), 400.5, 100, 0.5, 90, 0), new Location(map.getWorld(), 400.5, 100, 100.5, 90, 0), new Location(map.getWorld(), 400.5, 100, 200.5, 90, 0), new Location(map.getWorld(), 400.5, 100, 300.5, 90, 0)));
				uhcmaps.add(map);
			}
			{
				MiniGamesMap map = new MiniGamesMap("UHC World", "Alderius§7, §6playwarrior§7, §6eekhoorn2000", MiniGameType.ULTRA_HARD_CORE, "UHC_3", true);
				map.setSpawns(Arrays.asList(new Location(map.getWorld(), -400.5, 100, -300.5, 90, 0), new Location(map.getWorld(), -400.5, 100, -200.5, 90, 0), new Location(map.getWorld(), -400.5, 100, -100.5, 90, 0), new Location(map.getWorld(), -400.5, 100, 0.5, 90, 0), new Location(map.getWorld(), -400.5, 100, 100.5, 90, 0), new Location(map.getWorld(), -400.5, 100, 200.5, 90, 0), new Location(map.getWorld(), -400.5, 100, 300.5, 90, 0), new Location(map.getWorld(), -300.5, 100, -400.5, 90, 0), new Location(map.getWorld(), -300.5, 100, -200.5, 90, 0), new Location(map.getWorld(), -300.5, 100, -100.5, 90, 0), new Location(map.getWorld(), -300.5, 100, 100.5, 90, 0), new Location(map.getWorld(), -300.5, 100, 200.5, 90, 0), new Location(map.getWorld(), -300.5, 100, 400.5, 90, 0), new Location(map.getWorld(), -200.5, 100, -400.5, 90, 0), new Location(map.getWorld(), -200.5, 100, -300.5, 90, 0), new Location(map.getWorld(), -200.5, 100, -100.5, 90, 0), new Location(map.getWorld(), -200.5, 100, 100.5, 90, 0), new Location(map.getWorld(), -200.5, 100, 300.5, 90, 0), new Location(map.getWorld(), -200.5, 100, 400.5, 90, 0), new Location(map.getWorld(), -100.5, 100, -400.5, 90, 0), new Location(map.getWorld(), -100.5, 100, -300.5, 90, 0), new Location(map.getWorld(), -100.5, 100, -200.5, 90, 0), new Location(map.getWorld(), -100.5, 100, 200.5, 90, 0), new Location(map.getWorld(), -100.5, 100, 300.5, 90, 0), new Location(map.getWorld(), -100.5, 100, 400.5, 90, 0), new Location(map.getWorld(), 100.5, 100, -400.5, 90, 0), new Location(map.getWorld(), 100.5, 100, -300.5, 90, 0), new Location(map.getWorld(), 100.5, 100, -200.5, 90, 0), new Location(map.getWorld(), 100.5, 100, 200.5, 90, 0), new Location(map.getWorld(), 100.5, 100, 300.5, 90, 0), new Location(map.getWorld(), 100.5, 100, 400.5, 90, 0), new Location(map.getWorld(), 200.5, 100, -400.5, 90, 0), new Location(map.getWorld(), 200.5, 100, -300.5, 90, 0), new Location(map.getWorld(), 200.5, 100, -100.5, 90, 0), new Location(map.getWorld(), 200.5, 100, 100.5, 90, 0), new Location(map.getWorld(), 200.5, 100, 300.5, 90, 0), new Location(map.getWorld(), 200.5, 100, 400.5, 90, 0), new Location(map.getWorld(), 300.5, 100, -400.5, 90, 0), new Location(map.getWorld(), 300.5, 100, -200.5, 90, 0), new Location(map.getWorld(), 300.5, 100, -100.5, 90, 0), new Location(map.getWorld(), 300.5, 100, 100.5, 90, 0), new Location(map.getWorld(), 300.5, 100, 200.5, 90, 0), new Location(map.getWorld(), 300.5, 100, 400.5, 90, 0), new Location(map.getWorld(), 400.5, 100, -300.5, 90, 0), new Location(map.getWorld(), 400.5, 100, -200.5, 90, 0), new Location(map.getWorld(), 400.5, 100, -100.5, 90, 0), new Location(map.getWorld(), 400.5, 100, 0.5, 90, 0), new Location(map.getWorld(), 400.5, 100, 100.5, 90, 0), new Location(map.getWorld(), 400.5, 100, 200.5, 90, 0), new Location(map.getWorld(), 400.5, 100, 300.5, 90, 0)));
				uhcmaps.add(map);
			}*/
        this.maps.put(MiniGameType.ULTRA_HARD_CORE, uhcMaps);

        List<MiniGamesMap> swmaps = new ArrayList<>();
        {
            MiniGamesMap map = new MiniGamesMap("Balloons", "partycasper", MiniGameType.SKYWARS, "Balloons");
            map.setSpectatorLocation(new Location(map.getWorld(), 0.5, 84, 0.5, 180, 0));
            map.setSpawns(Arrays.asList(new Location(map.getWorld(), 11.5, 110, 25.5, 156, 0), new Location(map.getWorld(), -10.5, 110, 25.5, -156, 0), new Location(map.getWorld(), -20.5, 110, 9.5, -113, 0), new Location(map.getWorld(), -20.5, 110, -8.5, -66, 0), new Location(map.getWorld(), -10.5, 110, -24.5, -24, 0), new Location(map.getWorld(), 11.5, 110, -24.5, 24, 0), new Location(map.getWorld(), 21.5, 110, -8.5, 67, 0), new Location(map.getWorld(), 21.5, 110, 9.5, 113, 0)));
            map.setSWTier2Chests(Arrays.asList(new Location(map.getWorld(), 1, 72, 3), new Location(map.getWorld(), -1, 72, 3), new Location(map.getWorld(), 2, 72, -1), new Location(map.getWorld(), -1, 72, -3), new Location(map.getWorld(), 0, 72, -4)));
            swmaps.add(map);
        }
        {
            MiniGamesMap map = new MiniGamesMap("Candy", "Koning_paul§7, §6Brutuske02", MiniGameType.SKYWARS, "Candy");
            map.setSpectatorLocation(new Location(map.getWorld(), 1.5, 86, -1.5, 180, 0));
            map.setSpawns(Arrays.asList(new Location(map.getWorld(), -16.5, 87, -17.5, -50, 0), new Location(map.getWorld(), 1.5, 87, -29.5, 0, 0), new Location(map.getWorld(), 19.5, 87, -17.5, 50, 0), new Location(map.getWorld(), 31.5, 87, 0.5, 90, 0), new Location(map.getWorld(), 19.5, 87, 18.5, 138, 0), new Location(map.getWorld(), 1.5, 87, 30.5, 180, 0), new Location(map.getWorld(), -16.5, 87, 18.5, 138, 0), new Location(map.getWorld(), -28.5, 87, 0.5, -90, 0)));
            map.setSWTier2Chests(Arrays.asList(new Location(map.getWorld(), 1, 84, -5), new Location(map.getWorld(), 3, 80, -2), new Location(map.getWorld(), 1, 80, 0), new Location(map.getWorld(), -1, 80, -2), new Location(map.getWorld(), 9, 80, -5), new Location(map.getWorld(), 2, 73, -5), new Location(map.getWorld(), -5, 72, -2), new Location(map.getWorld(), 1, 70, 5)));
            swmaps.add(map);
        }
        {
            MiniGamesMap map = new MiniGamesMap("Citadel", "Alderius", MiniGameType.SKYWARS, "Citadel");
            map.setSpectatorLocation(new Location(map.getWorld(), 0.5, 85.5, 0.5, 180, 0));
            map.setSpawns(Arrays.asList(new Location(map.getWorld(), -23.5, 92, 0.5, -90, 0), new Location(map.getWorld(), -17.5, 92, -17.5, -45, 0), new Location(map.getWorld(), 0.5, 92, -23.5, 0, 0), new Location(map.getWorld(), 18.5, 92, -17.5, 45, 0), new Location(map.getWorld(), 24.5, 92, 0.5, 90, 0), new Location(map.getWorld(), 18.5, 92, 18.5, 135, 0), new Location(map.getWorld(), 0.5, 92, 24.5, 180, 0), new Location(map.getWorld(), -17.5, 92, 18.5, -135, 0)));
            map.setSWTier2Chests(Arrays.asList(new Location(map.getWorld(), 0, 70, 4), new Location(map.getWorld(), 0, 70, -4), new Location(map.getWorld(), 1, 64, -2), new Location(map.getWorld(), 1, 64, 2), new Location(map.getWorld(), -1, 64, 2), new Location(map.getWorld(), -1, 64, -2)));
            swmaps.add(map);
        }
        {
            MiniGamesMap map = new MiniGamesMap("Mesa", "Koning_paul§7, §6Brutuske02§7, §6partycasper", MiniGameType.SKYWARS, "Mesa");
            map.setSpectatorLocation(new Location(map.getWorld(), 0.5, 77, 0.5, 180, 0));
            map.setSpawns(Arrays.asList(new Location(map.getWorld(), 6.5, 92, -24.5, 15, 0), new Location(map.getWorld(), 24.5, 92, -15.5, 55, 0), new Location(map.getWorld(), 29.5, 92, 0.5, 90, 0), new Location(map.getWorld(), 22.5, 92, 13.5, 120, 0), new Location(map.getWorld(), 1.5, 92, 28.5, -180, 0), new Location(map.getWorld(), -15.5, 92, 22.5, -142, 0), new Location(map.getWorld(), -24.5, 92, 1.5, -90, 0), new Location(map.getWorld(), -12.5, 92, -21.5, -30, 0)));
            map.setSWTier2Chests(Arrays.asList(new Location(map.getWorld(), 6, 69, -7), new Location(map.getWorld(), 8, 70, 7), new Location(map.getWorld(), -7, 71, 11), new Location(map.getWorld(), -8, 70, -6), new Location(map.getWorld(), 2, 70, -3), new Location(map.getWorld(), 2, 70, 3), new Location(map.getWorld(), -2, 70, 3), new Location(map.getWorld(), -2, 70, -3), new Location(map.getWorld(), 2, 66, 0)));
            swmaps.add(map);
        }
        {
            MiniGamesMap map = new MiniGamesMap("Snowy", "Koning_paul§7, §6Brutuske02", MiniGameType.SKYWARS, "Snowy");
            map.setSpectatorLocation(new Location(map.getWorld(), 0.5, 76.25, 0.5, 180, 0));
            map.setSpawns(Arrays.asList(new Location(map.getWorld(), -5.5,  78, -39.5, -8, 0), new Location(map.getWorld(), 6.5, 85, 39.5, 9, 0), new Location(map.getWorld(), 6.5, 85, -27.5, 13, 0), new Location(map.getWorld(), -5.5, 85, -27.5, -12, 0), new Location(map.getWorld(), 6.5, 85, 40.5, 171, 0), new Location(map.getWorld(), -5.5, 85, 40.5, -171, 0), new Location(map.getWorld(), -5.5, 85, 28.5, -168, 0), new Location(map.getWorld(), 6.5, 85, 28.5, 168, 0)));
            map.setSWTier2Chests(Arrays.asList(new Location(map.getWorld(), -10, 72, 3), new Location(map.getWorld(), 9, 73, 12), new Location(map.getWorld(), 3, 73, -2), new Location(map.getWorld(), -6, 76, -8), new Location(map.getWorld(), -1, 69, 1), new Location(map.getWorld(), -1, 69, -1), new Location(map.getWorld(), 1, 69, -1), new Location(map.getWorld(), 1, 69, 1), new Location(map.getWorld(), 0, 64, 0)));
            swmaps.add(map);
        }
        {
            MiniGamesMap map = new MiniGamesMap("Transparent", "playwarrior§7, §6Iefo", MiniGameType.SKYWARS, "Transparent");
            map.setSpectatorLocation(new Location(map.getWorld(), 0.5, 70.75, 0.5, 180, 0));
            map.setSpawns(Arrays.asList(new Location(map.getWorld(), 0.5, 87, -33.5, 0, 0), new Location(map.getWorld(), 23.5, 87, -23.5, 45, 0), new Location(map.getWorld(), 35.5, 87, 0.5, 90, 0), new Location(map.getWorld(), 24.5, 87, 23.5, 132, 0), new Location(map.getWorld(), 0.5, 87, 35.5, 180, 0), new Location(map.getWorld(), -22.5, 87, 24.5, -138, 0), new Location(map.getWorld(), -34.5, 87, 0.5, -90, 0), new Location(map.getWorld(), -23.5, 87, -22.5, -45, 0)));
            map.setSWTier2Chests(Arrays.asList(new Location(map.getWorld(), 0, 78, -6), new Location(map.getWorld(), 6, 78, 0), new Location(map.getWorld(), 0, 78, 6), new Location(map.getWorld(), -6, 78, 0), new Location(map.getWorld(), 0, 64, 0)));
            swmaps.add(map);
        }
        {
            MiniGamesMap map = new MiniGamesMap("Village", "Alderius", MiniGameType.SKYWARS, "Village");
            map.setSpectatorLocation(new Location(map.getWorld(), 1, 76, 0.5, 180, 0));
            map.setSpawns(Arrays.asList(new Location(map.getWorld(), -30.5, 94, 15.5, -117, 0), new Location(map.getWorld(), -30.5, 94, -14.5, -64, 0), new Location(map.getWorld(), -14.5, 94, -30.5, -27, 0), new Location(map.getWorld(), -15.5, 94, -30.5, 27, 0), new Location(map.getWorld(), 31.5, 94, -14.5, 63, 0), new Location(map.getWorld(), 31.5, 94, 15.5, 116, 0), new Location(map.getWorld(), 15.5, 94, 31.5, 154, 0), new Location(map.getWorld(), -14.5, 94, 31.5, -154, 0)));
            map.setSWTier2Chests(Arrays.asList(new Location(map.getWorld(), 4, 69, -2), new Location(map.getWorld(), 4, 69, 2), new Location(map.getWorld(), -3, 69, 2), new Location(map.getWorld(), -3, 69, -2), new Location(map.getWorld(), -1, 70, 2), new Location(map.getWorld(), -1, 70, 1), new Location(map.getWorld(), 2, 70, -2), new Location(map.getWorld(), 2, 70, -1), new Location(map.getWorld(), -1, 65, 2), new Location(map.getWorld(), -1, 65, 1)));
            swmaps.add(map);
        }
        this.maps.put(MiniGameType.SKYWARS, swmaps);

        List<MiniGamesMap> cfMaps = new ArrayList<>();
        {
            MiniGamesMap map = new MiniGamesMap("Herobrine's Arena", "sharewoods§7, §6eekhoorn2000§7, §6Selasie", MiniGameType.CHICKEN_FIGHT, "HerobrinesArena");
            map.setSpectatorLocation(new Location(map.getWorld(), 4.5, 20, 3.5, 180, 0));
            map.setSpawns(Arrays.asList(new Location(map.getWorld(), -0.5, 8, 15.5, -160, 0), new Location(map.getWorld(), -7.5, 8, 7.5, -108, 0), new Location(map.getWorld(), 2.5, 8, -8.5, -10, 0), new Location(map.getWorld(), 10.5, 8, 8.5, 130, 0), new Location(map.getWorld(), 17.5, 8, 0.5, 77, 0), new Location(map.getWorld(), 8.5, 8, 16.5, 162, 0), new Location(map.getWorld(), -6.5, 8, 13.5, -132, 0), new Location(map.getWorld(), -7.5, 8, -8.5, -45, 0), new Location(map.getWorld(), 13.5, 8, -5.5, 45, 0), new Location(map.getWorld(), 0.5, 8, 0.5, -54, 0), new Location(map.getWorld(), 16.5, 8, 13.5, 130, 0), new Location(map.getWorld(), 16.5, 8, 5.5, 99, 0), new Location(map.getWorld(), 3.5, 8, 9.5, -169, 0), new Location(map.getWorld(), -11.5, 8, 2.5, -86, 0), new Location(map.getWorld(), 8.5, 8, -14.5, 12, 0), new Location(map.getWorld(), 12.5, 8, 1.5, 75, 0)));
            cfMaps.add(map);
        }
        {
            MiniGamesMap map = new MiniGamesMap("Lava Island", "O_o_Fadi_o_O", MiniGameType.CHICKEN_FIGHT, "LavaIsland");
            map.setSpectatorLocation(new Location(map.getWorld(), 4.5, 80, 2.5, 180, 0));
            map.setSpawns(Arrays.asList(new Location(map.getWorld(), -2.5, 64, -9.5, -30, 0), new Location(map.getWorld(), -1.5, 66, -0.5, -90, 0), new Location(map.getWorld(), 4.5, 64, 11.5, 163, 0), new Location(map.getWorld(), 14.5, 64, 4.5, 111, 0), new Location(map.getWorld(), 10.5, 64, -2.5, 74, 0), new Location(map.getWorld(), 9.5, 65, 9.5, 140, 0), new Location(map.getWorld(), -0.5, 64, 8.5, -165, 0), new Location(map.getWorld(), 1.5, 67, 1.5, -164, 0), new Location(map.getWorld(), 4.5, 64, -8.5, 15, 0), new Location(map.getWorld(), -7.5, 64, -3.5, -76, 0), new Location(map.getWorld(), -5.5, 64, 2.5, -115, 0), new Location(map.getWorld(), -6.5, 64, 10.5, -145, 0), new Location(map.getWorld(), 10.5, 66, 16.5, 153, 0), new Location(map.getWorld(), 5.5, 64, 17.5, 167, 0), new Location(map.getWorld(), -5.5, 64, 14.5, -154, 0), new Location(map.getWorld(), 8.5, 64, 4.5, 130, 0)));
            cfMaps.add(map);
        }
        {
            MiniGamesMap map = new MiniGamesMap("The Netherlands", "Alderius§7, §6casidas§7, §6jim5491158", MiniGameType.CHICKEN_FIGHT, "TheNetherlands");
            map.setSpectatorLocation(new Location(map.getWorld(), -5.5, 50, 8.5, 180, 0));
            map.setSpawns(Arrays.asList(new Location(map.getWorld(), -17.5, 38, 0.5, -57, 0), new Location(map.getWorld(), -5.5, 40, -9.5, 0, 0), new Location(map.getWorld(), 4.5, 38, -2.5, 42, 0), new Location(map.getWorld(), 4.5, 40, 9.5, 95, 0), new Location(map.getWorld(), 2.5, 38, 20.5, 147, 0), new Location(map.getWorld(), -11.5, 38, 32.5, -166, 0), new Location(map.getWorld(), -27.5, 38, 16.5, -111, 0), new Location(map.getWorld(), -32.5, 38, 10.5, -94, 0), new Location(map.getWorld(), -27.5, 38, -2.5, -63, 0), new Location(map.getWorld(), -17.5, 38, -13.5, -29, 0), new Location(map.getWorld(), 14.5, 38, 0.5, 69, 0), new Location(map.getWorld(), 13.5, 38, 20.5, 122, 0), new Location(map.getWorld(), 21.5, 38, 10.5, 92, 0), new Location(map.getWorld(), 6.5, 38, -10.5, 32, 0), new Location(map.getWorld(), -12.5, 40, 13.5, -125, 0), new Location(map.getWorld(), -9.5, 38, -3.5, -18, 0)));
            cfMaps.add(map);
        }
        this.maps.put(MiniGameType.CHICKEN_FIGHT, cfMaps);

        List<MiniGamesMap> gaMaps = new ArrayList<>();
        {
            MiniGamesMap map = new MiniGamesMap("Test Arena", "Builder", MiniGameType.GHOST_ATTACK, "LavaIsland");
            map.setSpectatorLocation(new Location(map.getWorld(), 4.5, 80, 2.5, 180, 0));
            map.setSpawns(Arrays.asList(new Location(map.getWorld(), -2.5, 64, -9.5, -30, 0), new Location(map.getWorld(), -1.5, 66, -0.5, -90, 0), new Location(map.getWorld(), 4.5, 64, 11.5, 163, 0), new Location(map.getWorld(), 14.5, 64, 4.5, 111, 0), new Location(map.getWorld(), 10.5, 64, -2.5, 74, 0), new Location(map.getWorld(), 9.5, 65, 9.5, 140, 0), new Location(map.getWorld(), -0.5, 64, 8.5, -165, 0), new Location(map.getWorld(), 1.5, 67, 1.5, -164, 0), new Location(map.getWorld(), 4.5, 64, -8.5, 15, 0), new Location(map.getWorld(), -7.5, 64, -3.5, -76, 0), new Location(map.getWorld(), -5.5, 64, 2.5, -115, 0), new Location(map.getWorld(), -6.5, 64, 10.5, -145, 0), new Location(map.getWorld(), 10.5, 66, 16.5, 153, 0), new Location(map.getWorld(), 5.5, 64, 17.5, 167, 0), new Location(map.getWorld(), -5.5, 64, 14.5, -154, 0), new Location(map.getWorld(), 8.5, 64, 4.5, 130, 0)));
            gaMaps.add(map);
        }
        this.maps.put(MiniGameType.GHOST_ATTACK, gaMaps);
    }

    private void registerArenas(){
        this.arenas = new ArrayList<>();
        this.arenas.add(new Arena(MiniGameType.SURVIVAL_GAMES, 1, new Location(getLobby(), -0.5, 70, 0.5, 180, 0)));
        this.arenas.add(new Arena(MiniGameType.SURVIVAL_GAMES, 2, new Location(getLobby(), 1000.5, 70, 0.5, 180, 0)));
        this.arenas.add(new Arena(MiniGameType.SURVIVAL_GAMES, 3, new Location(getLobby(), 0.5, 70, 1000.5, 180, 0)));
        this.arenas.add(new Arena(MiniGameType.ULTRA_HARD_CORE, 1, new Location(getLobby(), -999.5, 70, 0.5, 180, 0)));
        this.arenas.add(new Arena(MiniGameType.SKYWARS, 1, new Location(getLobby(), 0.5, 70, -999.5, 180, 0)));
        this.arenas.add(new Arena(MiniGameType.SKYWARS, 2, new Location(getLobby(), 0.5, 70, 2000.5, 180, 0)));
        this.arenas.add(new Arena(MiniGameType.SKYWARS, 3, new Location(getLobby(), 0.5, 70, -1999.5, 180, 0)));
        this.arenas.add(new Arena(MiniGameType.CHICKEN_FIGHT, 1, new Location(getLobby(), 2000.5, 70, 0.5, 180, 0)));
        this.arenas.add(new Arena(MiniGameType.CHICKEN_FIGHT, 2, new Location(getLobby(), -1999.5, 70, 0.5, 180, 0)));
        this.arenas.add(new Arena(MiniGameType.CHICKEN_FIGHT, 3, new Location(getLobby(), 1000.5, 70, 1000.5, 180, 0)));
        this.arenas.add(new Arena(MiniGameType.GHOST_ATTACK, 1, new Location(getLobby(), -999.5, 70, -999.5, 180, 0)));
    }

    private void spawnNPCs(){
        for(Arena arena : getArenas()){
            arena.spawnNPCs();
        }
    }

    private void registerSGChestItems(){
        this.sgChestItems = new ArrayList<>();
        this.sgChestItems.add(new ChestItem(Material.STONE_SWORD, 1, 1, null, 0, 8));
        this.sgChestItems.add(new ChestItem(Material.COOKED_BEEF, 1, 2, null, 0, 30));
        this.sgChestItems.add(new ChestItem(Material.DIAMOND, 1, 1, null, 0, 2));
        this.sgChestItems.add(new ChestItem(Material.GOLD_AXE, 1, 1, null, 0, 10));
        this.sgChestItems.add(new ChestItem(Material.CAKE, 1, 1, null, 0, 15));
        this.sgChestItems.add(new ChestItem(Material.COOKED_CHICKEN, 1, 3, "§cChili Chicken", 0, 35));
        this.sgChestItems.add(new ChestItem(Material.BAKED_POTATO, 1, 4, null, 0, 40));
        this.sgChestItems.add(new ChestItem(Material.PORK, 1, 3, null, 0, 38));
        this.sgChestItems.add(new ChestItem(Material.WOOD_SWORD, 1, 1, null, 0, 10));
        this.sgChestItems.add(new ChestItem(Material.CHAINMAIL_CHESTPLATE, 1, 1, null, 0, 9));
        this.sgChestItems.add(new ChestItem(Material.CHAINMAIL_LEGGINGS, 1, 1, null, 0, 8));
        this.sgChestItems.add(new ChestItem(Material.CHAINMAIL_HELMET, 1, 1, null, 0, 10));
        this.sgChestItems.add(new ChestItem(Material.CHAINMAIL_BOOTS, 1, 1, null, 0, 11));
        this.sgChestItems.add(new ChestItem(Material.LEATHER_CHESTPLATE, 1, 1, null, 0, 21));
        this.sgChestItems.add(new ChestItem(Material.LEATHER_LEGGINGS, 1, 1, null, 0, 22));
        this.sgChestItems.add(new ChestItem(Material.LEATHER_HELMET, 1, 1, null, 0, 26));
        this.sgChestItems.add(new ChestItem(Material.LEATHER_BOOTS, 1, 1, null, 0, 26));
        this.sgChestItems.add(new ChestItem(Material.IRON_HELMET, 1, 1, null, 0, 8));
        this.sgChestItems.add(new ChestItem(Material.IRON_BOOTS, 1, 1, null, 0, 8));
        this.sgChestItems.add(new ChestItem(Material.IRON_CHESTPLATE, 1, 1, null, 0, 5));
        this.sgChestItems.add(new ChestItem(Material.IRON_LEGGINGS, 1, 1, null, 0, 5));
        this.sgChestItems.add(new ChestItem(Material.GOLD_HELMET, 1, 1, null, 0, 16));
        this.sgChestItems.add(new ChestItem(Material.GOLD_BOOTS, 1, 1, null, 0, 17));
        this.sgChestItems.add(new ChestItem(Material.GOLD_CHESTPLATE, 1, 1, null, 0, 14));
        this.sgChestItems.add(new ChestItem(Material.GOLD_LEGGINGS, 1, 1, null, 0, 13));
        this.sgChestItems.add(new ChestItem(Material.STICK, 1, 2, null, 0, 13));
        this.sgChestItems.add(new ChestItem(Material.APPLE, 1, 2, null, 0, 17));
        this.sgChestItems.add(new ChestItem(Material.GOLD_INGOT, 1, 5, null, 0, 14));
        this.sgChestItems.add(new ChestItem(Material.IRON_INGOT, 1, 1, null, 0, 4));
        this.sgChestItems.add(new ChestItem(Material.WOOD_AXE, 1, 1, null, 0, 25));
        this.sgChestItems.add(new ChestItem(Material.STONE_AXE, 1, 1, null, 0, 9));
        this.sgChestItems.add(new ChestItem(Material.BOW, 1, 1, null, 0, 10));
        this.sgChestItems.add(new ChestItem(Material.ARROW, 1, 5, null, 0, 15));
        this.sgChestItems.add(new ChestItem(Material.ROTTEN_FLESH, 1, 4, null, 0, 45));
        this.sgChestItems.add(new ChestItem(Material.BOWL, 1, 4, null, 0, 30));
        this.sgChestItems.add(new ChestItem(Material.EXP_BOTTLE, 1, 5, null, 0, 13));
        this.sgChestItems.add(new ChestItem(Material.GOLD_SWORD, 1, 1, null, 0, 11));
        this.sgChestItems.add(new ChestItem(Material.FISHING_ROD, 1, 1, null, 0, 16));
        this.sgChestItems.add(new ChestItem(Material.FLINT, 1, 3, null, 0, 13));
        this.sgChestItems.add(new ChestItem(Material.FEATHER, 1, 3, null, 0, 11));
        this.sgChestItems.add(new ChestItem(Material.STRING, 1, 2, null, 0, 16));
        this.sgChestItems.add(new ChestItem(Material.FLINT_AND_STEEL, 1, 1, null, 64, 6));
        this.sgChestItems.add(new ChestItem(Material.BOAT, 1, 1, null, 0, 12));
        this.sgChestItems.add(new ChestItem(Material.WHEAT, 1, 5, null, 0, 8));
        this.sgChestItems.add(new ChestItem(Material.COOKIE, 1, 3, null, 0, 6));
        this.sgChestItems.add(new ChestItem(Material.MELON, 1, 5, null, 0, 8));
        this.sgChestItems.add(new ChestItem(Material.COOKED_FISH, 1, 2, null, 0, 9));
        this.sgChestItems.add(new ChestItem(Material.RAW_FISH, 1, 4, null, 0, 8));
        this.sgChestItems.add(new ChestItem(Material.CARROT_ITEM, 1, 2, null, 0, 11));
        this.sgChestItems.add(new ChestItem(Material.POTATO_ITEM, 1, 6, null, 0, 13));
        this.sgChestItems.add(new ChestItem(Material.COMPASS, 1, 1, "§6§lPlayer Tracker", 0, 4));
        this.sgChestItems.add(new ChestItem(Material.TNT, 1, 3, null, 0, 6));

        this.sgChestPotions = new ArrayList<>();
        this.sgChestPotions.add(new ChestItem(Material.POTION, 1, 1, null, 8193, 10));
        this.sgChestPotions.add(new ChestItem(Material.POTION, 1, 1, null, 8225, 5));
        this.sgChestPotions.add(new ChestItem(Material.POTION, 1, 1, null, 8194, 15));
        this.sgChestPotions.add(new ChestItem(Material.POTION, 1, 1, null, 8226, 5));
        this.sgChestPotions.add(new ChestItem(Material.POTION, 1, 1, null, 8227, 8));
        this.sgChestPotions.add(new ChestItem(Material.POTION, 1, 1, null, 8259, 8));
        this.sgChestPotions.add(new ChestItem(Material.POTION, 1, 1, null, 16388, 7));
        this.sgChestPotions.add(new ChestItem(Material.POTION, 1, 1, null, 16420, 7));
        this.sgChestPotions.add(new ChestItem(Material.POTION, 1, 1, null, 16452, 7));
        this.sgChestPotions.add(new ChestItem(Material.POTION, 1, 1, null, 16453, 5));
        this.sgChestPotions.add(new ChestItem(Material.POTION, 1, 1, null, 16421, 2));
        this.sgChestPotions.add(new ChestItem(Material.POTION, 1, 1, null, 8261, 7));
        this.sgChestPotions.add(new ChestItem(Material.POTION, 1, 1, null, 8262, 8));
        this.sgChestPotions.add(new ChestItem(Material.POTION, 1, 1, null, 16424, 7));
        this.sgChestPotions.add(new ChestItem(Material.POTION, 1, 1, null, 16456, 7));
        this.sgChestPotions.add(new ChestItem(Material.POTION, 1, 1, null, 8201, 2));
        this.sgChestPotions.add(new ChestItem(Material.POTION, 1, 1, null, 16426, 7));
        this.sgChestPotions.add(new ChestItem(Material.POTION, 1, 1, null, 16458, 7));
        this.sgChestPotions.add(new ChestItem(Material.POTION, 1, 1, null, 8203, 4));
        this.sgChestPotions.add(new ChestItem(Material.POTION, 1, 1, null, 8235, 4));
        this.sgChestPotions.add(new ChestItem(Material.POTION, 1, 1, null, 16460, 6));
        this.sgChestPotions.add(new ChestItem(Material.POTION, 1, 1, null, 16428, 6));
        this.sgChestPotions.add(new ChestItem(Material.POTION, 1, 1, null, 8269, 7));
        this.sgChestPotions.add(new ChestItem(Material.POTION, 1, 1, null, 8238, 3));
        this.sgChestPotions.add(new ChestItem(Material.POTION, 1, 1, null, 8270, 2));
    }

    private void registerSWChestItems(){
        this.swChestItems = new ArrayList<>();
        this.swChestItems.add(new ChestItem(Material.STONE_SWORD, 1, 1, null, 0, 8));
        this.swChestItems.add(new ChestItem(Material.STONE_SWORD, 1, 1, null, 0, 4, Enchantment.DAMAGE_ALL, 1));
        this.swChestItems.add(new ChestItem(Material.COOKED_BEEF, 1, 16, null, 0, 30));
        this.swChestItems.add(new ChestItem(Material.BAKED_POTATO, 1, 16, null, 0, 40));
        this.swChestItems.add(new ChestItem(Material.WOOD_SWORD, 1, 1, null, 0, 30));
        this.swChestItems.add(new ChestItem(Material.CHAINMAIL_CHESTPLATE, 1, 1, null, 0, 4));
        this.swChestItems.add(new ChestItem(Material.CHAINMAIL_LEGGINGS, 1, 1, null, 0, 4));
        this.swChestItems.add(new ChestItem(Material.CHAINMAIL_HELMET, 1, 1, null, 0, 5));
        this.swChestItems.add(new ChestItem(Material.CHAINMAIL_BOOTS, 1, 1, null, 0, 5));
        this.swChestItems.add(new ChestItem(Material.LEATHER_CHESTPLATE, 1, 1, null, 0, 20));
        this.swChestItems.add(new ChestItem(Material.LEATHER_LEGGINGS, 1, 1, null, 0, 25));
        this.swChestItems.add(new ChestItem(Material.LEATHER_HELMET, 1, 1, null, 0, 25));
        this.swChestItems.add(new ChestItem(Material.LEATHER_BOOTS, 1, 1, null, 0, 20));
        this.swChestItems.add(new ChestItem(Material.IRON_HELMET, 1, 1, null, 0, 5));
        this.swChestItems.add(new ChestItem(Material.IRON_BOOTS, 1, 1, null, 0, 5));
        this.swChestItems.add(new ChestItem(Material.IRON_CHESTPLATE, 1, 1, null, 0, 4));
        this.swChestItems.add(new ChestItem(Material.IRON_LEGGINGS, 1, 1, null, 0, 4));
        this.swChestItems.add(new ChestItem(Material.GOLD_HELMET, 1, 1, null, 0, 6));
        this.swChestItems.add(new ChestItem(Material.GOLD_BOOTS, 1, 1, null, 0, 6));
        this.swChestItems.add(new ChestItem(Material.GOLD_CHESTPLATE, 1, 1, null, 0, 4));
        this.swChestItems.add(new ChestItem(Material.GOLD_LEGGINGS, 1, 1, null, 0, 4));
        this.swChestItems.add(new ChestItem(Material.GOLDEN_APPLE, 1, 3, null, 0, 3));
        this.swChestItems.add(new ChestItem(Material.WOOD_AXE, 1, 1, null, 0, 30));
        this.swChestItems.add(new ChestItem(Material.STONE_AXE, 1, 1, null, 0, 6));
        this.swChestItems.add(new ChestItem(Material.STONE_AXE, 1, 1, null, 0, 6, Enchantment.DIG_SPEED, 2));
        this.swChestItems.add(new ChestItem(Material.BOW, 1, 1, null, 0, 4));
        this.swChestItems.add(new ChestItem(Material.BOW, 1, 1, null, 0, 2, Enchantment.ARROW_DAMAGE, 1));
        this.swChestItems.add(new ChestItem(Material.BOW, 1, 1, null, 0, 2, Enchantment.ARROW_KNOCKBACK, 1));
        this.swChestItems.add(new ChestItem(Material.ARROW, 1, 20, null, 0, 10));
        this.swChestItems.add(new ChestItem(Material.EXP_BOTTLE, 1, 32, null, 0, 10));
        this.swChestItems.add(new ChestItem(Material.FISHING_ROD, 1, 1, null, 0, 10));
        this.swChestItems.add(new ChestItem(Material.COOKIE, 1, 64, null, 0, 6));
        this.swChestItems.add(new ChestItem(Material.COOKED_FISH, 1, 20, null, 0, 8));
        this.swChestItems.add(new ChestItem(Material.COMPASS, 1, 1, "§6§lPlayer Tracker", 0, 1));
        this.swChestItems.add(new ChestItem(Material.ENDER_PEARL, 1, 3, null, 0, 2));
        this.swChestItems.add(new ChestItem(Material.SNOW_BALL, 1, 16, null, 0, 20));
        this.swChestItems.add(new ChestItem(Material.EGG, 1, 16, null, 0, 20));
        this.swChestItems.add(new ChestItem(Material.LOG, 1, 16, null, 0, 20));
        this.swChestItems.add(new ChestItem(Material.STONE, 10, 48, null, 0, 30));
        this.swChestItems.add(new ChestItem(Material.COBBLESTONE, 16, 64, null, 0, 20));
        this.swChestItems.add(new ChestItem(Material.WOOD, 16, 32, null, 0, 30));
        this.swChestItems.add(new ChestItem(Material.FLINT_AND_STEEL, 1, 1, null, 0, 2));
        this.swChestItems.add(new ChestItem(Material.LAVA_BUCKET, 1, 1, null, 0, 2));
        this.swChestItems.add(new ChestItem(Material.IRON_SWORD, 1, 1, null, 0, 4));
        this.swChestItems.add(new ChestItem(Material.STONE_PICKAXE, 1, 1, null, 0, 8));
        this.swChestItems.add(new ChestItem(Material.IRON_PICKAXE, 1, 1, null, 0, 7));

        this.swTier2ChestItems = new ArrayList<>();
        this.swTier2ChestItems.add(new ChestItem(Material.COMPASS, 1, 1, "§6§lPlayer Tracker", 0, 5));
        this.swTier2ChestItems.add(new ChestItem(Material.TNT, 1, 13, null, 0, 5));
        this.swTier2ChestItems.add(new ChestItem(Material.ENDER_PEARL, 1, 3, null, 0, 5));
        this.swTier2ChestItems.add(new ChestItem(Material.BOW, 1, 1, null, 0, 8));
        this.swTier2ChestItems.add(new ChestItem(Material.BOW, 1, 1, null, 0, 4, Enchantment.ARROW_DAMAGE, 1));
        this.swTier2ChestItems.add(new ChestItem(Material.BOW, 1, 1, null, 0, 2, Enchantment.ARROW_DAMAGE, 2));
        this.swTier2ChestItems.add(new ChestItem(Material.BOW, 1, 1, null, 0, 4, Enchantment.ARROW_KNOCKBACK, 1));
        this.swTier2ChestItems.add(new ChestItem(Material.EXP_BOTTLE, 1, 32, null, 0, 10));
        this.swTier2ChestItems.add(new ChestItem(Material.FISHING_ROD, 1, 1, null, 0, 10));
        this.swTier2ChestItems.add(new ChestItem(Material.ARROW, 1, 20, null, 0, 10));
        this.swTier2ChestItems.add(new ChestItem(Material.STONE_AXE, 1, 1, null, 0, 6, Enchantment.DIG_SPEED, 2));
        this.swTier2ChestItems.add(new ChestItem(Material.DIAMOND_CHESTPLATE, 1, 1, null, 0, 1));
        this.swTier2ChestItems.add(new ChestItem(Material.DIAMOND_HELMET, 1, 1, null, 0, 2));
        this.swTier2ChestItems.add(new ChestItem(Material.DIAMOND_BOOTS, 1, 1, null, 0, 2));
        this.swTier2ChestItems.add(new ChestItem(Material.DIAMOND_LEGGINGS, 1, 1, null, 0, 1));
        this.swTier2ChestItems.add(new ChestItem(Material.DIAMOND_LEGGINGS, 1, 1, null, 0, 1, Enchantment.PROTECTION_ENVIRONMENTAL, 1));
        this.swTier2ChestItems.add(new ChestItem(Material.DIAMOND_HELMET, 1, 1, null, 0, 1, Enchantment.PROTECTION_ENVIRONMENTAL, 1));
        this.swTier2ChestItems.add(new ChestItem(Material.DIAMOND_BOOTS, 1, 1, null, 0, 1, Enchantment.PROTECTION_PROJECTILE, 2));
        this.swTier2ChestItems.add(new ChestItem(Material.IRON_HELMET, 1, 1, null, 0, 5));
        this.swTier2ChestItems.add(new ChestItem(Material.IRON_BOOTS, 1, 1, null, 0, 5));
        this.swTier2ChestItems.add(new ChestItem(Material.IRON_BOOTS, 1, 1, null, 0, 5, Enchantment.PROTECTION_FALL, 3));
        this.swTier2ChestItems.add(new ChestItem(Material.IRON_CHESTPLATE, 1, 1, null, 0, 4));
        this.swTier2ChestItems.add(new ChestItem(Material.IRON_CHESTPLATE, 1, 1, null, 0, 3, Enchantment.PROTECTION_EXPLOSIONS, 3));
        this.swTier2ChestItems.add(new ChestItem(Material.IRON_CHESTPLATE, 1, 1, null, 0, 2, Enchantment.PROTECTION_ENVIRONMENTAL, 2));
        this.swTier2ChestItems.add(new ChestItem(Material.IRON_CHESTPLATE, 1, 1, null, 0, 2, Enchantment.PROTECTION_ENVIRONMENTAL, 1));
        this.swTier2ChestItems.add(new ChestItem(Material.IRON_LEGGINGS, 1, 1, null, 0, 4));
        this.swTier2ChestItems.add(new ChestItem(Material.GOLD_HELMET, 1, 1, null, 0, 7));
        this.swTier2ChestItems.add(new ChestItem(Material.GOLD_BOOTS, 1, 1, null, 0, 7));
        this.swTier2ChestItems.add(new ChestItem(Material.GOLD_CHESTPLATE, 1, 1, null, 0, 5));
        this.swTier2ChestItems.add(new ChestItem(Material.GOLD_LEGGINGS, 1, 1, null, 0, 5));
        this.swTier2ChestItems.add(new ChestItem(Material.GOLDEN_APPLE, 1, 3, null, 0, 5));
        this.swTier2ChestItems.add(new ChestItem(Material.CHAINMAIL_CHESTPLATE, 1, 1, null, 0, 8));
        this.swTier2ChestItems.add(new ChestItem(Material.CHAINMAIL_LEGGINGS, 1, 1, null, 0, 8));
        this.swTier2ChestItems.add(new ChestItem(Material.CHAINMAIL_HELMET, 1, 1, null, 0, 6));
        this.swTier2ChestItems.add(new ChestItem(Material.CHAINMAIL_BOOTS, 1, 1, null, 0, 6));
        this.swTier2ChestItems.add(new ChestItem(Material.STONE_SWORD, 1, 1, null, 0, 8));
        this.swTier2ChestItems.add(new ChestItem(Material.STONE_SWORD, 1, 1, null, 0, 4, Enchantment.DAMAGE_ALL, 1));
        this.swTier2ChestItems.add(new ChestItem(Material.COOKED_BEEF, 1, 16, null, 0, 30));
        this.swTier2ChestItems.add(new ChestItem(Material.BAKED_POTATO, 1, 16, null, 0, 40));
        this.swTier2ChestItems.add(new ChestItem(Material.SNOW_BALL, 1, 16, null, 0, 30));
        this.swTier2ChestItems.add(new ChestItem(Material.EGG, 1, 16, null, 0, 30));
        this.swTier2ChestItems.add(new ChestItem(Material.LOG, 16, 32, null, 0, 7));
        this.swTier2ChestItems.add(new ChestItem(Material.STONE, 16, 32, null, 0, 8));
        this.swTier2ChestItems.add(new ChestItem(Material.COBBLESTONE, 16, 64, null, 0, 10));
        this.swTier2ChestItems.add(new ChestItem(Material.WOOD, 16, 64, null, 0, 8));
        this.swTier2ChestItems.add(new ChestItem(Material.FLINT_AND_STEEL, 1, 1, null, 0, 5));
        this.swTier2ChestItems.add(new ChestItem(Material.LAVA_BUCKET, 1, 1, null, 0, 5));
        this.swTier2ChestItems.add(new ChestItem(Material.IRON_SWORD, 1, 1, null, 0, 8));
        this.swTier2ChestItems.add(new ChestItem(Material.DIAMOND_SWORD, 1, 1, null, 0, 4));
        this.swTier2ChestItems.add(new ChestItem(Material.DIAMOND_PICKAXE, 1, 1, null, 0, 8));
        this.swTier2ChestItems.add(new ChestItem(Material.IRON_PICKAXE, 1, 1, null, 0, 7));
    }

    private void spawnNpcs(){
        for(Arena arena : getArenas()){
            arena.spawnNPCs();
        }
    }
}
