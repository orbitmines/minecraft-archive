package om.kitpvp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import om.api.API;
import om.api.events.VoteEvent;
import om.api.events.WorldChangeEvent;
import om.api.handlers.Hologram;
import om.api.handlers.Kit;
import om.api.handlers.NPC;
import om.api.handlers.NPCArmorStand;
import om.api.runnables.OMRunnable.Duration;
import om.api.utils.ItemUtils;
import om.api.utils.WorldUtils;
import om.api.utils.enums.NPCType;
import om.kitpvp.cmd.FreeKitCommand;
import om.kitpvp.cmd.KitCommand;
import om.kitpvp.events.BlockChangeEvent;
import om.kitpvp.events.BreakEvent;
import om.kitpvp.events.ClickEvent;
import om.kitpvp.events.CommandPreprocessEvent;
import om.kitpvp.events.DamageEvent;
import om.kitpvp.events.DropEvent;
import om.kitpvp.events.EntityDamage;
import om.kitpvp.events.EntityDeath;
import om.kitpvp.events.EntityInteractEvent;
import om.kitpvp.events.ExpChangeEvent;
import om.kitpvp.events.ExplodeEvent;
import om.kitpvp.events.FadeEvent;
import om.kitpvp.events.FoodEvent;
import om.kitpvp.events.InteractAtEntityEvent;
import om.kitpvp.events.JoinEvent;
import om.kitpvp.events.PickupEvent;
import om.kitpvp.events.PlaceEvent;
import om.kitpvp.events.PlayerChat;
import om.kitpvp.events.PlayerInteract;
import om.kitpvp.events.PlayerMove;
import om.kitpvp.events.ProjectileHit;
import om.kitpvp.events.ShootBowEvent;
import om.kitpvp.events.SignEvent;
import om.kitpvp.events.TeleportEvent;
import om.kitpvp.handlers.ActiveBooster;
import om.kitpvp.handlers.KitPvPMap;
import om.kitpvp.handlers.KitPvPScoreBoard;
import om.kitpvp.handlers.players.KitPvPPlayer;
import om.kitpvp.invs.KitPvPTeleporterInv;
import om.kitpvp.runnables.BoosterRunnable;
import om.kitpvp.runnables.FreeKitRunnable;
import om.kitpvp.runnables.KitPvPNPCRunnable;
import om.kitpvp.runnables.KitPvPPlayerRunnable;
import om.kitpvp.runnables.MapRunnable;
import om.kitpvp.runnables.MasteryNPCRunnable;
import om.kitpvp.utils.KitPvPUtils;
import om.kitpvp.utils.enums.ArmorType;
import om.kitpvp.utils.enums.ItemType;
import om.kitpvp.utils.enums.KitPvPKit;
import om.kitpvp.utils.enums.ProjectileType;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Skeleton.SkeletonType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.EulerAngle;

public class KitPvP extends JavaPlugin {

	private API api;
	private static KitPvP kitpvp;
	
	private boolean freeKitEnabled;
	private World lobbyWorld;
	private World arenaWorld;
	private Location spawn;
	private List<KitPvPMap> maps;
	private KitPvPMap currentMap;
	private Map<Projectile, ProjectileType> projectiles;
	private ActiveBooster booster;
	private Map<Block, Integer> paintballBlocks = new HashMap<Block, Integer>();
	private Map<Block, Player> paintballBlockPlayers = new HashMap<Block, Player>();
	private Map<Player, KitPvPPlayer> players;
	private List<KitPvPPlayer> kitpvpPlayers;
	private KitPvPTeleporterInv teleporterInv;
	
	public void onEnable() {
		api = API.getInstance();
		kitpvp = this;

		api.setSpawnBuilders("§b§lMod §brienk222");
		api.setScoreboardManager(new KitPvPScoreBoard());
		lobbyWorld = Bukkit.getWorld("KitPvPLobby");
		arenaWorld = Bukkit.getWorld("KitPvPArenas");
		spawn = new Location(getLobbyWorld(), -0.5, 74, -0.5, 90, 0);
		projectiles = new HashMap<Projectile, ProjectileType>();
		players = new HashMap<Player, KitPvPPlayer>();
		kitpvpPlayers = new ArrayList<KitPvPPlayer>();
		teleporterInv = new KitPvPTeleporterInv();
		
		registerRunnables();
		registerEvents();
		registerCommands();
		registerLobbyKit();
		registerSpectatorKit();
		registerMaps();
		registerKits();
		setRandomMap();
		
		new BukkitRunnable(){
			public void run(){
				WorldUtils.removeAllEntities();
				spawnNPCs();
			}
		}.runTaskLater(this, 100);
	}
	
	public void onDisable(){
		WorldUtils.removeAllEntitiesIF();
	}
	
	public API getAPI() {
		return api;
	}
	public static KitPvP getInstance() {
		return kitpvp;
	}
	
	public boolean isFreeKitEnabled(){
		return freeKitEnabled;
	}
	public void setFreeKitEnabled(boolean freeKitEnabled){
		this.freeKitEnabled = freeKitEnabled;
	}
	
	public World getLobbyWorld(){
		return lobbyWorld;
	}
	public void setLobbyWorld(World lobbyWorld){
		this.lobbyWorld = lobbyWorld;
	}
	
	public World getArenaWorld(){
		return arenaWorld;
	}
	public void setArenaWorld(World arenaWorld){
		this.arenaWorld = arenaWorld;
	}

	public Location getSpawn(){
		return spawn;
	}
	public void setSpawn(Location spawn){
		this.spawn = spawn;
	}
	
	public List<KitPvPMap> getMaps(){
		return maps;
	}
	public void setMaps(List<KitPvPMap> maps){
		this.maps = maps;
	}
	
	public KitPvPMap getCurrentMap(){
		return currentMap;
	}
	public void setCurrentMap(KitPvPMap currentMap){
		this.currentMap = currentMap;
	}
	public void setRandomMap(){
		currentMap = maps.get(new Random().nextInt(maps.size()));
		currentMap.resetTimer();
	}
	public void setNextMap(){
		List<KitPvPMap> maps = KitPvPUtils.asNewMapList(this.maps);
		if(currentMap != null){
			maps.remove(currentMap);
		}
		currentMap = maps.get(new Random().nextInt(maps.size()));
		currentMap.resetTimer();
	}
	
	public Map<Projectile, ProjectileType> getProjectiles(){
		return projectiles;
	}
	public void setProjectiles(Map<Projectile, ProjectileType> projectiles){
		this.projectiles = projectiles;
	}
	public boolean isProjectile(Projectile projectile){
		return projectiles.containsKey(projectile);
	}
	public ProjectileType getProjectileType(Projectile projectile){
		return this.projectiles.get(projectile);
	}
	public void addProjectile(Projectile projectile, ProjectileType type){
		this.projectiles.put(projectile, type);
	}
	public void removeProjectile(Projectile projectile){
		this.projectiles.remove(projectile);
	}
	
	public ActiveBooster getBooster(){
		return booster;
	}
	public void setBooster(ActiveBooster booster){
		this.booster = booster;
	}
	public boolean hasBooster(){
		return booster != null;
	}
	
	public Map<Block, Integer> getPaintballBlocks(){
		return paintballBlocks;
	}
	public void setPaintballBlocks(Map<Block, Integer> paintballBlocks){
		this.paintballBlocks = paintballBlocks;
	}
	
	public Map<Block, Player> getPaintballBlockPlayers(){
		return paintballBlockPlayers;
	}
	public void setPaintballBlockPlayers(Map<Block, Player> paintballBlockPlayers){
		this.paintballBlockPlayers = paintballBlockPlayers;
	}

	public Map<Player, KitPvPPlayer> getPlayers() {
		return players;
	}
	public List<KitPvPPlayer> getKitPvPPlayers() {
		return kitpvpPlayers;
	}
	
	public KitPvPTeleporterInv getTeleporterInv() {
		return teleporterInv;
	}

	public void giveLobbyKit(KitPvPPlayer omp){
		Kit.getKit("Lobby").setItems(omp.getPlayer());
	}
	
	public void giveSpectatorKit(KitPvPPlayer omp){
		Kit.getKit("Spectator").setItems(omp.getPlayer());
	}
	
	private void registerMaps(){
		List<KitPvPMap> maps = new ArrayList<KitPvPMap>();
		{
			KitPvPMap map = new KitPvPMap("Snow Town");
			map.setBuilders("§4§lOwner §4O_o_Fadi_o_O\n§b§lMod §brienk222");
			map.setSpawns(Arrays.asList(new Location(getArenaWorld(), -63.5, 9, -1182.5, -45, 0), new Location(getArenaWorld(), -92.5, 14, -1079.5, -166, 0), new Location(getArenaWorld(), -134.5, 9, -1131.5, -113, 0), new Location(getArenaWorld(), -115, 12.5, -1187.5, -70, 0), new Location(getArenaWorld(), -90.5, 9, -1171.5, 143, 0), new Location(getArenaWorld(), -62.5, 9, -1138.5, -71, 0), new Location(getArenaWorld(), -108.5, 11, -1150.5, -139, 0), new Location(getArenaWorld(), -91.5, 10, -1205.5, -30, 0), new Location(getArenaWorld(), -144.5, 10, -1165.5, -75, 0), new Location(getArenaWorld(), -66.5, 10, -1098.5, -126, 0)));
			map.setSpectatorSpawn(new Location(getArenaWorld(), -93.5, 22, -1154.5, 145, 0));
			map.setMaxY(30);
			maps.add(map);
		}
		{
			KitPvPMap map = new KitPvPMap("Mountain Village");
			map.setBuilders("§b§lMod §brienk222\n§b§lMod §bsharewoods\n§4§lOwner §4O_o_Fadi_o_O");
			map.setSpawns(Arrays.asList(new Location(getArenaWorld(), -352.5, 5, -1366.5, -54, 0), new Location(getArenaWorld(), -317.5, 4, -1329.5, 46, 0), new Location(getArenaWorld(), -283.5, 4, -1296.5, 165, 0), new Location(getArenaWorld(), -303.5, 5, -1315.5, 39, 0), new Location(getArenaWorld(), -284.5, 4, -1348.5, 168, 0), new Location(getArenaWorld(), -303.5, 4, -1345.5, 135, 0), new Location(getArenaWorld(), -316.5, 9, -1364.5, 30, 0), new Location(getArenaWorld(), -337.5, 5, -1344.5, -126, 0), new Location(getArenaWorld(), -349.5, 4, -1327.5, -113, 0), new Location(getArenaWorld(), -323.5, 5, -1296.5, -161, 0)));
			map.setSpectatorSpawn(new Location(getArenaWorld(), -308.5, 16, -1326.5, 137, 0));
			map.setMaxY(26);
			maps.add(map);
		}
		{
			KitPvPMap map = new KitPvPMap("Desert");
			map.setBuilders("§b§lMod §brienk222");
			map.setSpawns(Arrays.asList(new Location(getArenaWorld(), -463.5, 29.5, -1139, 20, 0), new Location(getArenaWorld(), -422.5, 36, -1140.5, 105, 0), new Location(getArenaWorld(), -484, 33, -1060, 155, 0), new Location(getArenaWorld(), -523, 40, -1084, -90, 0), new Location(getArenaWorld(), -516.5, 37, -1123.5, 140, 0), new Location(getArenaWorld(), -450, 29, -1042.5, 156, 0), new Location(getArenaWorld(), -430.5, 34, -1080.5, 93, 0), new Location(getArenaWorld(), -451.5, 29, -1098.5, 20, 0), new Location(getArenaWorld(), -430.5, 38, -1035.5, 135, 0), new Location(getArenaWorld(), -510.5, 36, -1099.5, -123, 0)));
			map.setSpectatorSpawn(new Location(getArenaWorld(), -465, 43.5, -1085, 89, 17));
			map.setMaxY(47);
			maps.add(map);
		}
		
		this.maps = maps;
	}
	
	private void registerKits(){
		{// Knight Level 1 \\
			Kit kit = new Kit(KitPvPKit.KNIGHT.getName() + " 1");
			kit.setItem(0, ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.setDisplayname(new ItemStack(Material.STONE_SWORD), "§b§lKnight §a§lLvL 1§8 || §bWeapon")), 4));
			kit.setItem(1, ItemUtils.setDurability(ItemUtils.setDisplayname(new ItemStack(Material.POTION), "§b§lKnight §a§lLvL 1§8 || §cHealing Potion"), 8197));

			kit.setHelmet(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.setDisplayname(new ItemStack(Material.CHAINMAIL_HELMET), "§b§lKnight §a§lLvL 1§8 || §bHelmet")), 4));
			kit.setChestplate(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.setDisplayname(new ItemStack(Material.CHAINMAIL_CHESTPLATE), "§b§lKnight §a§lLvL 1§8 || §bChestplate")), 4));
			kit.setLeggings(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.setDisplayname(new ItemStack(Material.CHAINMAIL_LEGGINGS), "§b§lKnight §a§lLvL 1§8 || §bLeggings")), 4));
			kit.setBoots(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.setDisplayname(new ItemStack(Material.CHAINMAIL_BOOTS), "§b§lKnight §a§lLvL 1§8 || §bBoots")), 4));
		}
		{// Knight Level 2 \\
			Kit kit = new Kit(KitPvPKit.KNIGHT.getName() + " 2");
			kit.setItem(0, ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.setDisplayname(new ItemStack(Material.STONE_SWORD), "§b§lKnight §a§lLvL 2§8 || §bWeapon")), 4));
			kit.setItem(1, ItemUtils.setDurability(ItemUtils.setDisplayname(new ItemStack(Material.POTION), "§b§lKnight §a§lLvL 2§8 || §cHealing Potion"), 16389));

			kit.setHelmet(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.setDisplayname(new ItemStack(Material.CHAINMAIL_HELMET), "§b§lKnight §a§lLvL 2§8 || §bHelmet")), 4));
			kit.setChestplate(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.setDisplayname(new ItemStack(Material.CHAINMAIL_CHESTPLATE), "§b§lKnight §a§lLvL 2§8 || §bChestplate"), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
			kit.setLeggings(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.setDisplayname(new ItemStack(Material.CHAINMAIL_LEGGINGS), "§b§lKnight §a§lLvL 2§8 || §bLeggings"), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
			kit.setBoots(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.setDisplayname(new ItemStack(Material.CHAINMAIL_BOOTS), "§b§lKnight §a§lLvL 2§8 || §bBoots")), 4));
		}
		{// Knight Level 3 \\
			Kit kit = new Kit(KitPvPKit.KNIGHT.getName() + " 3");
			kit.setItem(0, ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.setDisplayname(new ItemStack(Material.STONE_SWORD), "§b§lKnight §a§lLvL 3§8 || §bWeapon"), Enchantment.DAMAGE_ALL, 1)), 4));
			kit.setItem(1, ItemUtils.setDurability(ItemUtils.setDisplayname(new ItemStack(Material.POTION), "§b§lKnight §a§lLvL 3§8 || §cHealing Potion"), 16389));
			kit.setItem(2, ItemUtils.setDurability(ItemUtils.setDisplayname(new ItemStack(Material.POTION), "§b§lKnight §a§lLvL 3§8 || §cHealing Potion"), 16389));

			kit.setHelmet(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.setDisplayname(new ItemStack(Material.CHAINMAIL_HELMET), "§b§lKnight §a§lLvL 3§8 || §bHelmet"), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
			kit.setChestplate(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.setDisplayname(new ItemStack(Material.CHAINMAIL_CHESTPLATE), "§b§lKnight §a§lLvL 3§8 || §bChestplate"), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
			kit.setLeggings(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.setDisplayname(new ItemStack(Material.CHAINMAIL_LEGGINGS), "§b§lKnight §a§lLvL 3§8 || §bLeggings"), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
			kit.setBoots(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.setDisplayname(new ItemStack(Material.CHAINMAIL_BOOTS), "§b§lKnight §a§lLvL 3§8 || §bBoots"), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
		}
		{// Archer Level 1 \\
			Kit kit = new Kit(KitPvPKit.ARCHER.getName() + " 1");
			kit.setItem(0, ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.setDisplayname(new ItemStack(Material.STONE_SWORD), "§b§lArcher §a§lLvL 1§8 || §bWeapon")), 4));
			kit.setItem(1, ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.setDisplayname(new ItemStack(Material.BOW), "§b§lArcher §a§lLvL 1§8 || §bBow")), 4));
			kit.setItem(2, ItemUtils.setDisplayname(new ItemStack(Material.ARROW, 32), "§b§lArcher §a§lLvL 1§8 || §bArrow"));
			kit.setItem(3, ItemUtils.setDurability(ItemUtils.setDisplayname(new ItemStack(Material.POTION), "§b§lArcher §a§lLvL 1§8 || §cHealing Potion"), 8197));

			kit.setHelmet(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addColor(ItemUtils.setDisplayname(new ItemStack(Material.LEATHER_HELMET), "§b§lArcher §a§lLvL 1§8 || §bHelmet"), Color.fromBGR(204, 255, 51))), 4));
			kit.setChestplate(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addColor(ItemUtils.setDisplayname(new ItemStack(Material.LEATHER_CHESTPLATE), "§b§lArcher §a§lLvL 1§8 || §bChestplate"), Color.fromBGR(204, 255, 51))), 4));
			kit.setLeggings(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addColor(ItemUtils.setDisplayname(new ItemStack(Material.LEATHER_LEGGINGS), "§b§lArcher §a§lLvL 1§8 || §bLeggings"), Color.fromBGR(204, 255, 51))), 4));
			kit.setBoots(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addColor(ItemUtils.setDisplayname(new ItemStack(Material.LEATHER_BOOTS), "§b§lArcher §a§lLvL 1§8 || §bBoots"), Color.fromBGR(204, 255, 51))), 4));
		}
		{// Archer Level 2 \\
			Kit kit = new Kit(KitPvPKit.ARCHER.getName() + " 2");
			kit.setItem(0, ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.setDisplayname(new ItemStack(Material.STONE_SWORD), "§b§lArcher §a§lLvL 2§8 || §bWeapon")), 4));
			kit.setItem(1, ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.setLore(ItemUtils.setDisplayname(new ItemStack(Material.BOW), "§b§lArcher §a§lLvL 2§8 || §bBow"), ProjectileType.LIGHTNING_I.addEnchantment(new ArrayList<String>())), Enchantment.DURABILITY, 1)), 5));
			kit.setItem(2, ItemUtils.setDisplayname(new ItemStack(Material.ARROW, 32), "§b§lArcher §a§lLvL 2§8 || §bArrow"));
			kit.setItem(3, ItemUtils.setDurability(ItemUtils.setDisplayname(new ItemStack(Material.POTION), "§b§lArcher §a§lLvL 2§8 || §cHealing Potion"), 8197));

			kit.setHelmet(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addColor(ItemUtils.setDisplayname(new ItemStack(Material.LEATHER_HELMET), "§b§lArcher §a§lLvL 2§8 || §bHelmet"), Color.fromBGR(204, 255, 51))), 4));
			kit.setChestplate(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addColor(ItemUtils.setDisplayname(new ItemStack(Material.LEATHER_CHESTPLATE), "§b§lArcher §a§lLvL 2§8 || §bChestplate"), Color.fromBGR(204, 255, 51))), 4));
			kit.setLeggings(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addColor(ItemUtils.setDisplayname(new ItemStack(Material.LEATHER_LEGGINGS), "§b§lArcher §a§lLvL 2§8 || §bLeggings"), Color.fromBGR(204, 255, 51))), 4));
			kit.setBoots(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addColor(ItemUtils.setDisplayname(new ItemStack(Material.LEATHER_BOOTS), "§b§lArcher §a§lLvL 2§8 || §bBoots"), Color.fromBGR(204, 255, 51))), 4));
		}
		{// Archer Level 3 \\
			Kit kit = new Kit(KitPvPKit.ARCHER.getName() + " 3");
			kit.setItem(0, ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.setDisplayname(new ItemStack(Material.STONE_SWORD), "§b§lArcher §a§lLvL 3§8 || §bWeapon")), 4));
			kit.setItem(1, ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.setLore(ItemUtils.setDisplayname(new ItemStack(Material.BOW), "§b§lArcher §a§lLvL 3§8 || §bBow"), ProjectileType.LIGHTNING_I.addEnchantment(new ArrayList<String>())), Enchantment.ARROW_DAMAGE, 1)), 4));
			kit.setItem(2, ItemUtils.setDisplayname(new ItemStack(Material.ARROW, 32), "§b§lArcher §a§lLvL 3§8 || §bArrow"));
			kit.setItem(3, ItemUtils.setDurability(ItemUtils.setDisplayname(new ItemStack(Material.POTION), "§b§lArcher §a§lLvL 3§8 || §cHealing Potion"), 16389));

			kit.setHelmet(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.setDisplayname(new ItemStack(Material.LEATHER_HELMET), "§b§lArcher §a§lLvL 3§8 || §bHelmet"), Color.fromBGR(204, 255, 51)), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
			kit.setChestplate(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.setDisplayname(new ItemStack(Material.LEATHER_CHESTPLATE), "§b§lArcher §a§lLvL 3§8 || §bChestplate"), Color.fromBGR(204, 255, 51)), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
			kit.setLeggings(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.setDisplayname(new ItemStack(Material.LEATHER_LEGGINGS), "§b§lArcher §a§lLvL 3§8 || §bLeggings"), Color.fromBGR(204, 255, 51)), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
			kit.setBoots(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.setDisplayname(new ItemStack(Material.LEATHER_BOOTS), "§b§lArcher §a§lLvL 3§8 || §bBoots"), Color.fromBGR(204, 255, 51)), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
		}
		{// Soldier Level 1 \\
			Kit kit = new Kit(KitPvPKit.SOLDIER.getName() + " 1");
			kit.setItem(0, ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.setDisplayname(new ItemStack(Material.STONE_SWORD), "§b§lSoldier §a§lLvL 1§8 || §bWeapon")), 4));
			kit.setItem(1, ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.setDisplayname(new ItemStack(Material.BOW), "§b§lSoldier §a§lLvL 1§8 || §bBow")), 4));
			kit.setItem(2, ItemUtils.setDisplayname(new ItemStack(Material.ARROW, 16), "§b§lSoldier §a§lLvL 1§8 || §bArrow"));
			
			kit.setHelmet(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.setDisplayname(new ItemStack(Material.LEATHER_HELMET), "§b§lSoldier §a§lLvL 1§8 || §bHelmet"), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
			kit.setChestplate(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.setDisplayname(new ItemStack(Material.IRON_CHESTPLATE), "§b§lSoldier §a§lLvL 1§8 || §bChestplate")), 4));
			kit.setLeggings(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.setDisplayname(new ItemStack(Material.LEATHER_LEGGINGS), "§b§lSoldier §a§lLvL 1§8 || §bLeggings"), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
			kit.setBoots(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.setDisplayname(new ItemStack(Material.LEATHER_BOOTS), "§b§lSoldier §a§lLvL 1§8 || §bBoots"), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
		}
		{// Soldier Level 2 \\
			Kit kit = new Kit(KitPvPKit.SOLDIER.getName() + " 2");
			kit.setItem(0, ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.setLore(ItemUtils.setDisplayname(new ItemStack(Material.STONE_SWORD), "§b§lSoldier §a§lLvL 2§8 || §bWeapon"), ItemType.LIGHTNING_I.addEnchantment(new ArrayList<String>())), Enchantment.DURABILITY, 1)), 5));
			kit.setItem(1, ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.setDisplayname(new ItemStack(Material.BOW), "§b§lSoldier §a§lLvL 2§8 || §bBow")), 4));
			kit.setItem(2, ItemUtils.setDisplayname(new ItemStack(Material.ARROW, 16), "§b§lSoldier §a§lLvL 2§8 || §bArrow"));
			
			kit.setHelmet(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.setDisplayname(new ItemStack(Material.LEATHER_HELMET), "§b§lSoldier §a§lLvL 2§8 || §bHelmet"), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
			kit.setChestplate(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.setDisplayname(new ItemStack(Material.IRON_CHESTPLATE), "§b§lSoldier §a§lLvL 2§8 || §bChestplate"), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
			kit.setLeggings(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.setDisplayname(new ItemStack(Material.LEATHER_LEGGINGS), "§b§lSoldier §a§lLvL 2§8 || §bLeggings"), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
			kit.setBoots(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.setDisplayname(new ItemStack(Material.LEATHER_BOOTS), "§b§lSoldier §a§lLvL 2§8 || §bBoots"), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
		}
		{// Soldier Level 3 \\
			Kit kit = new Kit(KitPvPKit.SOLDIER.getName() + " 3");
			kit.setItem(0, ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.setLore(ItemUtils.setDisplayname(new ItemStack(Material.STONE_SWORD), "§b§lSoldier §a§lLvL 3§8 || §bWeapon"), ItemType.LIGHTNING_II.addEnchantment(new ArrayList<String>())), Enchantment.DAMAGE_ALL, 1)), 4));
			kit.setItem(1, ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.setDisplayname(new ItemStack(Material.BOW), "§b§lSoldier §a§lLvL 3§8 || §bBow"), Enchantment.ARROW_DAMAGE, 1)), 4));
			kit.setItem(2, ItemUtils.setDisplayname(new ItemStack(Material.ARROW, 20), "§b§lSoldier §a§lLvL 3§8 || §bArrow"));
			
			kit.setHelmet(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.setDisplayname(new ItemStack(Material.LEATHER_HELMET), "§b§lSoldier §a§lLvL 3§8 || §bHelmet"), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
			kit.setChestplate(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.setDisplayname(new ItemStack(Material.IRON_CHESTPLATE), "§b§lSoldier §a§lLvL 3§8 || §bChestplate"), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
			kit.setLeggings(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.setDisplayname(new ItemStack(Material.LEATHER_LEGGINGS), "§b§lSoldier §a§lLvL 3§8 || §bLeggings"), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
			kit.setBoots(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.setDisplayname(new ItemStack(Material.LEATHER_BOOTS), "§b§lSoldier §a§lLvL 3§8 || §bBoots"), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
		}
		{// Wizard Level 1 \\
			Kit kit = new Kit(KitPvPKit.WIZARD.getName() + " 1");
			kit.setItem(0, ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.setDisplayname(new ItemStack(Material.STONE_SWORD), "§b§lWizard §a§lLvL 1§8 || §bWeapon")), 4));
			kit.setItem(1, ItemUtils.setDurability(ItemUtils.setDisplayname(new ItemStack(Material.POTION), "§b§lWizard §a§lLvL 1§8 || §dRegeneration Potion"), 8193));
			kit.setItem(2, ItemUtils.setDurability(ItemUtils.setDisplayname(new ItemStack(Material.POTION), "§b§lWizard §a§lLvL 1§8 || §bSpeed Potion"), 8194));
			kit.setItem(3, ItemUtils.setDurability(ItemUtils.setDisplayname(new ItemStack(Material.POTION), "§b§lWizard §a§lLvL 1§8 || §7Weakness Potion"), 16392));
			kit.setItem(4, ItemUtils.setDurability(ItemUtils.setDisplayname(new ItemStack(Material.POTION), "§b§lWizard §a§lLvL 1§8 || §cHealing Potion"), 16389));
			kit.setItem(5, ItemUtils.setDurability(ItemUtils.setDisplayname(new ItemStack(Material.POTION), "§b§lWizard §a§lLvL 1§8 || §cHealing Potion"), 16389));

			kit.setHelmet(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.setDisplayname(new ItemStack(Material.GOLD_HELMET), "§b§lWizard §a§lLvL 1§8 || §bHelmet")), 4));
			kit.setChestplate(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.setDisplayname(new ItemStack(Material.GOLD_CHESTPLATE), "§b§lWizard §a§lLvL 1§8 || §bChestplate")), 4));
			kit.setLeggings(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.setDisplayname(new ItemStack(Material.GOLD_LEGGINGS), "§b§lWizard §a§lLvL 1§8 || §bLeggings")), 4));
			kit.setBoots(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.setDisplayname(new ItemStack(Material.GOLD_BOOTS), "§b§lWizard §a§lLvL 1§8 || §bBoots")), 4));
		}
		{// Wizard Level 2 \\
			Kit kit = new Kit(KitPvPKit.WIZARD.getName() + " 2");
			kit.setItem(0, ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.setDisplayname(new ItemStack(Material.STONE_SWORD), "§b§lWizard §a§lLvL 2§8 || §bWeapon"), Enchantment.DAMAGE_ALL, 1)), 4));
			kit.setItem(1, ItemUtils.setDurability(ItemUtils.setDisplayname(new ItemStack(Material.POTION), "§b§lWizard §a§lLvL 2§8 || §dRegeneration Potion"), 8193));
			kit.setItem(2, ItemUtils.setDurability(ItemUtils.setDisplayname(new ItemStack(Material.POTION), "§b§lWizard §a§lLvL 2§8 || §bSpeed Potion"), 8194));
			kit.setItem(3, ItemUtils.setDurability(ItemUtils.setDisplayname(new ItemStack(Material.POTION), "§b§lWizard §a§lLvL 2§8 || §7Weakness Potion"), 16392));
			kit.setItem(4, ItemUtils.setDurability(ItemUtils.setDisplayname(new ItemStack(Material.POTION), "§b§lWizard §a§lLvL 2§8 || §cHealing Potion"), 16389));
			kit.setItem(5, ItemUtils.setDurability(ItemUtils.setDisplayname(new ItemStack(Material.POTION), "§b§lWizard §a§lLvL 2§8 || §cHealing Potion"), 16389));
			kit.setItem(6, ItemUtils.hideFlags(ItemUtils.addEnchantment(ItemUtils.setLore(ItemUtils.setDisplayname(new ItemStack(Material.BLAZE_ROD), "§b§lWizard §a§lLvL 2§8 || §cFire Wand"), ItemType.FIRE_SPELL_I.addEnchantment(new ArrayList<String>())), Enchantment.DURABILITY, 1), 5));
			
			kit.setHelmet(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.setDisplayname(new ItemStack(Material.GOLD_HELMET), "§b§lWizard §a§lLvL 2§8 || §bHelmet"), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
			kit.setChestplate(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.setDisplayname(new ItemStack(Material.GOLD_CHESTPLATE), "§b§lWizard §a§lLvL 2§8 || §bChestplate"), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
			kit.setLeggings(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.setDisplayname(new ItemStack(Material.GOLD_LEGGINGS), "§b§lWizard §a§lLvL 2§8 || §bLeggings"), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
			kit.setBoots(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.setDisplayname(new ItemStack(Material.GOLD_BOOTS), "§b§lWizard §a§lLvL 2§8 || §bBoots"), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
		}
		{// Wizard Level 3 \\
			Kit kit = new Kit(KitPvPKit.WIZARD.getName() + " 3");
			kit.setItem(0, ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.setDisplayname(new ItemStack(Material.STONE_SWORD), "§b§lWizard §a§lLvL 3§8 || §bWeapon"), Enchantment.DAMAGE_ALL, 1)), 4));
			kit.setItem(1, ItemUtils.setDurability(ItemUtils.setDisplayname(new ItemStack(Material.POTION), "§b§lWizard §a§lLvL 3§8 || §dRegeneration Potion"), 8193));
			kit.setItem(2, ItemUtils.setDurability(ItemUtils.setDisplayname(new ItemStack(Material.POTION), "§b§lWizard §a§lLvL 3§8 || §bSpeed Potion"), 8194));
			kit.setItem(3, ItemUtils.setDurability(ItemUtils.setDisplayname(new ItemStack(Material.POTION), "§b§lWizard §a§lLvL 3§8 || §7Weakness Potion"), 16392));
			kit.setItem(4, ItemUtils.setDurability(ItemUtils.setDisplayname(new ItemStack(Material.POTION), "§b§lWizard §a§lLvL 3§8 || §cHealing Potion"), 16389));
			kit.setItem(5, ItemUtils.setDurability(ItemUtils.setDisplayname(new ItemStack(Material.POTION), "§b§lWizard §a§lLvL 3§8 || §cHealing Potion"), 16389));
			kit.setItem(6, ItemUtils.hideFlags(ItemUtils.addEnchantment(ItemUtils.setLore(ItemUtils.setDisplayname(new ItemStack(Material.BLAZE_ROD), "§b§lWizard §a§lLvL 3§8 || §cFire Wand"), ItemType.FIRE_SPELL_II.addEnchantment(new ArrayList<String>())), Enchantment.DURABILITY, 1), 5));
			
			kit.setHelmet(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.setDisplayname(new ItemStack(Material.GOLD_HELMET), "§b§lWizard §a§lLvL 3§8 || §bHelmet"), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));
			kit.setChestplate(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.setDisplayname(new ItemStack(Material.GOLD_CHESTPLATE), "§b§lWizard §a§lLvL 3§8 || §bChestplate"), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));
			kit.setLeggings(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.setDisplayname(new ItemStack(Material.GOLD_LEGGINGS), "§b§lWizard §a§lLvL 3§8 || §bLeggings"), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));
			kit.setBoots(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.setDisplayname(new ItemStack(Material.GOLD_BOOTS), "§b§lWizard §a§lLvL 3§8 || §bBoots"), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));
		}
		{// Tank Level 1 \\
			Kit kit = new Kit(KitPvPKit.TANK.getName() + " 1");
			kit.setItem(0, ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.setDisplayname(new ItemStack(Material.WOOD_SWORD), "§b§lTank §a§lLvL 1§8 || §bWeapon"), Enchantment.KNOCKBACK, 1)), 4));
			kit.setItem(1, ItemUtils.setDisplayname(new ItemStack(Material.GOLDEN_APPLE, 2), "§b§lTank §a§lLvL 1§8 || §eGolden Apple"));

			kit.setHelmet(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.setDisplayname(new ItemStack(Material.IRON_HELMET), "§b§lTank §a§lLvL 1§8 || §bHelmet")), 4));
			kit.setChestplate(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.setDisplayname(new ItemStack(Material.DIAMOND_CHESTPLATE), "§b§lTank §a§lLvL 1§8 || §bChestplate"), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
			kit.setLeggings(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.setDisplayname(new ItemStack(Material.IRON_LEGGINGS), "§b§lTank §a§lLvL 1§8 || §bLeggings")), 4));
			kit.setBoots(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.setDisplayname(new ItemStack(Material.IRON_BOOTS), "§b§lTank §a§lLvL 1§8 || §bBoots")), 4));
			kit.setPotionEffect(new PotionEffect(PotionEffectType.SLOW, 2000000, 2));
		}
		{// Tank Level 2 \\
			Kit kit = new Kit(KitPvPKit.TANK.getName() + " 2");
			kit.setItem(0, ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.setDisplayname(new ItemStack(Material.STONE_SWORD), "§b§lTank §a§lLvL 2§8 || §bWeapon"), Enchantment.KNOCKBACK, 2)), 4));
			kit.setItem(1, ItemUtils.setDisplayname(new ItemStack(Material.GOLDEN_APPLE, 3), "§b§lTank §a§lLvL 2§8 || §eGolden Apple"));

			kit.setHelmet(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.setDisplayname(new ItemStack(Material.IRON_HELMET), "§b§lTank §a§lLvL 2§8 || §bHelmet")), 4));
			kit.setChestplate(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.setDisplayname(new ItemStack(Material.DIAMOND_CHESTPLATE), "§b§lTank §a§lLvL 2§8 || §bChestplate"), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
			kit.setLeggings(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.setDisplayname(new ItemStack(Material.IRON_LEGGINGS), "§b§lTank §a§lLvL 2§8 || §bLeggings"), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
			kit.setBoots(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.setDisplayname(new ItemStack(Material.IRON_BOOTS), "§b§lTank §a§lLvL 2§8 || §bBoots")), 4));
			kit.setPotionEffect(new PotionEffect(PotionEffectType.SLOW, 2000000, 2));
		}
		{// Tank Level 3 \\
			Kit kit = new Kit(KitPvPKit.TANK.getName() + " 3");
			kit.setItem(0, ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.setDisplayname(new ItemStack(Material.IRON_SWORD), "§b§lTank §a§lLvL 3§8 || §bWeapon"), Enchantment.KNOCKBACK, 2)), 4));
			kit.setItem(1, ItemUtils.setDisplayname(new ItemStack(Material.GOLDEN_APPLE, 4), "§b§lTank §a§lLvL 3§8 || §eGolden Apple"));

			kit.setHelmet(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.setDisplayname(new ItemStack(Material.IRON_HELMET), "§b§lTank §a§lLvL 3§8 || §bHelmet"), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
			kit.setChestplate(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.setDisplayname(new ItemStack(Material.DIAMOND_CHESTPLATE), "§b§lTank §a§lLvL 3§8 || §bChestplate"), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
			kit.setLeggings(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.setDisplayname(new ItemStack(Material.IRON_LEGGINGS), "§b§lTank §a§lLvL 3§8 || §bLeggings"), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
			kit.setBoots(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.setDisplayname(new ItemStack(Material.IRON_BOOTS), "§b§lTank §a§lLvL 3§8 || §bBoots"), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
			kit.setPotionEffect(new PotionEffect(PotionEffectType.SLOW, 2000000, 2));
		}
		{// Drunk Level 1 \\
			Kit kit = new Kit(KitPvPKit.DRUNK.getName() + " 1");
			kit.setItem(0, ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.setDisplayname(new ItemStack(Material.STONE_SWORD), "§b§lDrunk §a§lLvL 1§8 || §bWeapon"), Enchantment.KNOCKBACK, 2)), 4));
			kit.setItem(1, ItemUtils.setDurability(ItemUtils.setDisplayname(new ItemStack(Material.POTION), "§b§lDrunk §a§lLvL 1§8 || §5Strength Potion"), 8201));

			kit.setHelmet(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.setDisplayname(new ItemStack(Material.CHAINMAIL_HELMET), "§b§lDrunk §a§lLvL 1§8 || §bHelmet")), 4));
			kit.setChestplate(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.setDisplayname(new ItemStack(Material.CHAINMAIL_CHESTPLATE), "§b§lDrunk §a§lLvL 1§8 || §bChestplate")), 4));
			kit.setLeggings(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.setDisplayname(new ItemStack(Material.CHAINMAIL_LEGGINGS), "§b§lDrunk §a§lLvL 1§8 || §bLeggings")), 4));
			kit.setBoots(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.setDisplayname(new ItemStack(Material.CHAINMAIL_BOOTS), "§b§lDrunk §a§lLvL 1§8 || §bBoots")), 4));
			kit.setPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 2000000, 1));
		}
		{// Drunk Level 2 \\
			Kit kit = new Kit(KitPvPKit.DRUNK.getName() + " 2");
			kit.setItem(0, ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.setLore(ItemUtils.setDisplayname(new ItemStack(Material.STONE_SWORD), "§b§lDrunk §a§lLvL 2§8 || §bWeapon"), ItemType.BLINDNESS_I.addEnchantment(new ArrayList<String>())), Enchantment.KNOCKBACK, 2)), 4));
			kit.setItem(1, ItemUtils.setDurability(ItemUtils.setDisplayname(new ItemStack(Material.POTION), "§b§lDrunk §a§lLvL 2§8 || §5Strength Potion"), 8201));

			kit.setHelmet(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.setDisplayname(new ItemStack(Material.CHAINMAIL_HELMET), "§b§lDrunk §a§lLvL 2§8 || §bHelmet"), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
			kit.setChestplate(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.setDisplayname(new ItemStack(Material.CHAINMAIL_CHESTPLATE), "§b§lDrunk §a§lLvL 2§8 || §bChestplate")), 4));
			kit.setLeggings(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.setDisplayname(new ItemStack(Material.CHAINMAIL_LEGGINGS), "§b§lDrunk §a§lLvL 2§8 || §bLeggings")), 4));
			kit.setBoots(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.setDisplayname(new ItemStack(Material.CHAINMAIL_BOOTS), "§b§lDrunk §a§lLvL 2§8 || §bBoots"), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
			kit.setPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 2000000, 0));
		}
		{// Drunk Level 3 \\
			Kit kit = new Kit(KitPvPKit.DRUNK.getName() + " 3");
			kit.setItem(0, ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.setLore(ItemUtils.setDisplayname(new ItemStack(Material.IRON_SWORD), "§b§lDrunk §a§lLvL 3§8 || §bWeapon"), ItemType.BLINDNESS_II.addEnchantment(new ArrayList<String>())), Enchantment.KNOCKBACK, 2)), 4));
			kit.setItem(1, ItemUtils.setDurability(ItemUtils.setDisplayname(new ItemStack(Material.POTION), "§b§lDrunk §a§lLvL 3§8 || §5Strength Potion"), 8201));

			kit.setHelmet(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.setDisplayname(new ItemStack(Material.CHAINMAIL_HELMET), "§b§lDrunk §a§lLvL 3§8 || §bHelmet"), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
			kit.setChestplate(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.setDisplayname(new ItemStack(Material.CHAINMAIL_CHESTPLATE), "§b§lDrunk §a§lLvL 3§8 || §bChestplate")), 4));
			kit.setLeggings(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.setDisplayname(new ItemStack(Material.CHAINMAIL_LEGGINGS), "§b§lDrunk §a§lLvL 3§8 || §bLeggings"), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
			kit.setBoots(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.setDisplayname(new ItemStack(Material.CHAINMAIL_BOOTS), "§b§lDrunk §a§lLvL 3§8 || §bBoots"), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
			kit.setPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 2000000, 0));
		}
		{// Pyro Level 1 \\
			Kit kit = new Kit(KitPvPKit.PYRO.getName() + " 1");
			kit.setItem(0, ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.setDisplayname(new ItemStack(Material.IRON_SWORD), "§b§lPyro §a§lLvL 1§8 || §bWeapon"), Enchantment.FIRE_ASPECT, 2)), 4));
			
			kit.setHelmet(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.setDisplayname(new ItemStack(Material.GOLD_HELMET), "§b§lPyro §a§lLvL 1§8 || §bHelmet"), Enchantment.PROTECTION_FIRE, 1)), 4));
			kit.setChestplate(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.setDisplayname(new ItemStack(Material.GOLD_CHESTPLATE), "§b§lPyro §a§lLvL 1§8 || §bChestplate"), Enchantment.PROTECTION_FIRE, 1)), 4));
			kit.setLeggings(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.setDisplayname(new ItemStack(Material.GOLD_LEGGINGS), "§b§lPyro §a§lLvL 1§8 || §bLeggings"), Enchantment.PROTECTION_FIRE, 1)), 4));
			kit.setBoots(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.setDisplayname(new ItemStack(Material.GOLD_BOOTS), "§b§lPyro §a§lLvL 1§8 || §bBoots"), Enchantment.PROTECTION_FIRE, 1)), 4));
			kit.setPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 2000000, 3));
		}
		{// Pyro Level 2 \\
			Kit kit = new Kit(KitPvPKit.PYRO.getName() + " 2");
			kit.setItem(0, ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addEnchantment(ItemUtils.setDisplayname(new ItemStack(Material.IRON_SWORD), "§b§lPyro §a§lLvL 2§8 || §bWeapon"), Enchantment.FIRE_ASPECT, 2), Enchantment.DAMAGE_ALL, 1)), 4));
			
			kit.setHelmet(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.setDisplayname(new ItemStack(Material.GOLD_HELMET), "§b§lPyro §a§lLvL 2§8 || §bHelmet"), Enchantment.PROTECTION_FIRE, 1)), 4));
			kit.setChestplate(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addEnchantment(ItemUtils.setDisplayname(new ItemStack(Material.GOLD_CHESTPLATE), "§b§lPyro §a§lLvL 2§8 || §bChestplate"), Enchantment.PROTECTION_FIRE, 1), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
			kit.setLeggings(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.setDisplayname(new ItemStack(Material.GOLD_LEGGINGS), "§b§lPyro §a§lLvL 2§8 || §bLeggings"), Enchantment.PROTECTION_FIRE, 1)), 4));
			kit.setBoots(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addEnchantment(ItemUtils.setLore(ItemUtils.setDisplayname(new ItemStack(Material.GOLD_BOOTS), "§b§lPyro §a§lLvL 2§8 || §bBoots"), ArmorType.FIRE_TRAIL_I.addEnchantment(new ArrayList<String>())), Enchantment.PROTECTION_FIRE, 1), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
			kit.setPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 2000000, 3));
		}
		{// Pyro Level 3 \\
			Kit kit = new Kit(KitPvPKit.PYRO.getName() + " 3");
			kit.setItem(0, ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addEnchantment(ItemUtils.setDisplayname(new ItemStack(Material.IRON_SWORD), "§b§lPyro §a§lLvL 3§8 || §bWeapon"), Enchantment.FIRE_ASPECT, 2), Enchantment.DAMAGE_ALL, 1)), 4));
			kit.setItem(1, ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.setDisplayname(new ItemStack(Material.BOW), "§b§lPyro §a§lLvL 3§8 || §bBow"), Enchantment.ARROW_FIRE, 1)), 4));
			kit.setItem(2, ItemUtils.setDisplayname(new ItemStack(Material.ARROW, 12), "§b§lPyro §a§lLvL 3§8 || §bArrow"));
			
			kit.setHelmet(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addEnchantment(ItemUtils.setDisplayname(new ItemStack(Material.GOLD_HELMET), "§b§lPyro §a§lLvL 3§8 || §bHelmet"), Enchantment.PROTECTION_FIRE, 1), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
			kit.setChestplate(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addEnchantment(ItemUtils.setDisplayname(new ItemStack(Material.GOLD_CHESTPLATE), "§b§lPyro §a§lLvL 3§8 || §bChestplate"), Enchantment.PROTECTION_FIRE, 1), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
			kit.setLeggings(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addEnchantment(ItemUtils.setDisplayname(new ItemStack(Material.GOLD_LEGGINGS), "§b§lPyro §a§lLvL 3§8 || §bLeggings"), Enchantment.PROTECTION_FIRE, 1), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
			kit.setBoots(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addEnchantment(ItemUtils.setLore(ItemUtils.setDisplayname(new ItemStack(Material.GOLD_BOOTS), "§b§lPyro §a§lLvL 3§8 || §bBoots"), ArmorType.FIRE_TRAIL_I.addEnchantment(new ArrayList<String>())), Enchantment.PROTECTION_FIRE, 1), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
			kit.setPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 2000000, 3));
		}
		{// Bunny Level 1 \\
			Kit kit = new Kit(KitPvPKit.BUNNY.getName() + " 1");
			kit.setItem(0, ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.setDisplayname(new ItemStack(Material.IRON_SWORD), "§b§lBunny §a§lLvL 1§8 || §bWeapon")), 4));
			kit.setItem(1, ItemUtils.setDurability(ItemUtils.setDisplayname(new ItemStack(Material.POTION), "§b§lBunny §a§lLvL 1§8 || §2Poison Potion"), 16420));
			kit.setItem(2, ItemUtils.setDurability(ItemUtils.setDisplayname(new ItemStack(Material.POTION), "§b§lBunny §a§lLvL 1§8 || §4Harming Potion"), 16396));
			kit.setItem(3, ItemUtils.setDurability(ItemUtils.setDisplayname(new ItemStack(Material.POTION), "§b§lBunny §a§lLvL 1§8 || §4Harming Potion"), 16396));

			kit.setHelmet(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.setDisplayname(new ItemStack(Material.LEATHER_HELMET), "§b§lBunny §a§lLvL 1§8 || §bHelmet"), Color.WHITE), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
			kit.setChestplate(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.setDisplayname(new ItemStack(Material.LEATHER_CHESTPLATE), "§b§lBunny §a§lLvL 1§8 || §bChestplate"), Color.WHITE), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
			kit.setLeggings(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.setDisplayname(new ItemStack(Material.LEATHER_LEGGINGS), "§b§lBunny §a§lLvL 1§8 || §bLeggings"), Color.WHITE), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
			kit.setBoots(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.setDisplayname(new ItemStack(Material.LEATHER_BOOTS), "§b§lBunny §a§lLvL 1§8 || §bBoots"), Color.WHITE), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
			kit.setPotionEffect(new PotionEffect(PotionEffectType.JUMP, 2000000, 3));
		}
		{// Bunny Level 2 \\
			Kit kit = new Kit(KitPvPKit.BUNNY.getName() + " 2");
			kit.setItem(0, ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.setDisplayname(new ItemStack(Material.IRON_SWORD), "§b§lBunny §a§lLvL 2§8 || §bWeapon")), 4));
			kit.setItem(1, ItemUtils.addEnchantment(ItemUtils.addEnchantment(ItemUtils.setDisplayname(new ItemStack(Material.CARROT_ITEM), "§b§lBunny §a§lLvL 2§8 || §6Knockback Carrot"), Enchantment.DAMAGE_ALL, 5), Enchantment.KNOCKBACK, 10));
			kit.setItem(2, ItemUtils.setDurability(ItemUtils.setDisplayname(new ItemStack(Material.POTION), "§b§lBunny §a§lLvL 2§8 || §2Poison Potion"), 16420));
			kit.setItem(3, ItemUtils.setDurability(ItemUtils.setDisplayname(new ItemStack(Material.POTION), "§b§lBunny §a§lLvL 2§8 || §2Poison Potion"), 16420));
			kit.setItem(4, ItemUtils.setDurability(ItemUtils.setDisplayname(new ItemStack(Material.POTION), "§b§lBunny §a§lLvL 2§8 || §4Harming Potion"), 16396));
			kit.setItem(5, ItemUtils.setDurability(ItemUtils.setDisplayname(new ItemStack(Material.POTION), "§b§lBunny §a§lLvL 2§8 || §4Harming Potion"), 16396));

			kit.setHelmet(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.setDisplayname(new ItemStack(Material.LEATHER_HELMET), "§b§lBunny §a§lLvL 2§8 || §bHelmet"), Color.WHITE), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));
			kit.setChestplate(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.setDisplayname(new ItemStack(Material.LEATHER_CHESTPLATE), "§b§lBunny §a§lLvL 2§8 || §bChestplate"), Color.WHITE), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));
			kit.setLeggings(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.setDisplayname(new ItemStack(Material.LEATHER_LEGGINGS), "§b§lBunny §a§lLvL 2§8 || §bLeggings"), Color.WHITE), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));
			kit.setBoots(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.setDisplayname(new ItemStack(Material.LEATHER_BOOTS), "§b§lBunny §a§lLvL 2§8 || §bBoots"), Color.WHITE), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));
			kit.setPotionEffect(new PotionEffect(PotionEffectType.JUMP, 2000000, 3));
		}
		{// Bunny Level 3 \\
			Kit kit = new Kit(KitPvPKit.BUNNY.getName() + " 3");
			kit.setItem(0, ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.setDisplayname(new ItemStack(Material.IRON_SWORD), "§b§lBunny §a§lLvL 3§8 || §bWeapon"), Enchantment.DAMAGE_ALL, 1)), 4));
			kit.setItem(1, ItemUtils.addEnchantment(ItemUtils.addEnchantment(ItemUtils.setDisplayname(new ItemStack(Material.CARROT_ITEM), "§b§lBunny §a§lLvL 3§8 || §6Knockback Carrot"), Enchantment.DAMAGE_ALL, 5), Enchantment.KNOCKBACK, 10));
			kit.setItem(2, ItemUtils.setDurability(ItemUtils.setDisplayname(new ItemStack(Material.POTION), "§b§lBunny §a§lLvL 3§8 || §2Poison Potion"), 16420));
			kit.setItem(3, ItemUtils.setDurability(ItemUtils.setDisplayname(new ItemStack(Material.POTION), "§b§lBunny §a§lLvL 3§8 || §2Poison Potion"), 16420));
			kit.setItem(4, ItemUtils.setDurability(ItemUtils.setDisplayname(new ItemStack(Material.POTION), "§b§lBunny §a§lLvL 3§8 || §4Harming Potion"), 16396));
			kit.setItem(5, ItemUtils.setDurability(ItemUtils.setDisplayname(new ItemStack(Material.POTION), "§b§lBunny §a§lLvL 3§8 || §4Harming Potion"), 16396));

			kit.setHelmet(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.setDisplayname(new ItemStack(Material.LEATHER_HELMET), "§b§lBunny §a§lLvL 3§8 || §bHelmet"), Color.WHITE), Enchantment.PROTECTION_ENVIRONMENTAL, 3)), 4));
			kit.setChestplate(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.setDisplayname(new ItemStack(Material.LEATHER_CHESTPLATE), "§b§lBunny §a§lLvL 3§8 || §bChestplate"), Color.WHITE), Enchantment.PROTECTION_ENVIRONMENTAL, 3)), 4));
			kit.setLeggings(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.setDisplayname(new ItemStack(Material.LEATHER_LEGGINGS), "§b§lBunny §a§lLvL 3§8 || §bLeggings"), Color.WHITE), Enchantment.PROTECTION_ENVIRONMENTAL, 3)), 4));
			kit.setBoots(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.setDisplayname(new ItemStack(Material.LEATHER_BOOTS), "§b§lBunny §a§lLvL 3§8 || §bBoots"), Color.WHITE), Enchantment.PROTECTION_ENVIRONMENTAL, 3)), 4));
			kit.setPotionEffect(new PotionEffect(PotionEffectType.JUMP, 2000000, 3));
		}
		{// Necromancer Level 1 \\
			Kit kit = new Kit(KitPvPKit.NECROMANCER.getName() + " 1");
			kit.setItem(0, ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.setDisplayname(new ItemStack(Material.GOLD_HOE), "§b§lNecromancer §a§lLvL 1§8 || §bWeapon"), Enchantment.DAMAGE_ALL, 8)), 4));
			kit.setItem(1, ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.setLore(ItemUtils.setDisplayname(new ItemStack(Material.BOW), "§b§lNecromancer §a§lLvL 1§8 || §bBow"), ProjectileType.UNDEATH_I.addEnchantment(new ArrayList<String>())), Enchantment.ARROW_DAMAGE, 1)), 4));
			kit.setItem(2, ItemUtils.setDisplayname(new ItemStack(Material.ARROW, 5), "§b§lNecromancer §a§lLvL 1§8 || §bArrow"));
			
			kit.setHelmet(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.setDisplayname(new ItemStack(Material.LEATHER_HELMET), "§b§lNecromancer §a§lLvL 1§8 || §bHelmet"), Color.BLACK), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
			kit.setChestplate(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.setDisplayname(new ItemStack(Material.LEATHER_CHESTPLATE), "§b§lNecromancer §a§lLvL 1§8 || §bChestplate"), Color.BLACK), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
			kit.setLeggings(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.setDisplayname(new ItemStack(Material.LEATHER_LEGGINGS), "§b§lNecromancer §a§lLvL 1§8 || §bLeggings"), Color.BLACK), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
			kit.setBoots(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.setDisplayname(new ItemStack(Material.LEATHER_BOOTS), "§b§lNecromancer §a§lLvL 1§8 || §bBoots"), Color.BLACK), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
		}
		{// Necromancer Level 2 \\
			Kit kit = new Kit(KitPvPKit.NECROMANCER.getName() + " 2");
			kit.setItem(0, ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.setDisplayname(new ItemStack(Material.GOLD_HOE), "§b§lNecromancer §a§lLvL 2§8 || §bWeapon"), Enchantment.DAMAGE_ALL, 8)), 4));
			kit.setItem(1, ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.setLore(ItemUtils.setDisplayname(new ItemStack(Material.BOW), "§b§lNecromancer §a§lLvL 2§8 || §bBow"), ProjectileType.UNDEATH_II.addEnchantment(new ArrayList<String>())), Enchantment.ARROW_DAMAGE, 1)), 4));
			kit.setItem(2, ItemUtils.setDisplayname(new ItemStack(Material.ARROW, 10), "§b§lNecromancer §a§lLvL 2§8 || §bArrow"));
			
			kit.setHelmet(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.setDisplayname(new ItemStack(Material.LEATHER_HELMET), "§b§lNecromancer §a§lLvL 2§8 || §bHelmet"), Color.BLACK), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));
			kit.setChestplate(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.setDisplayname(new ItemStack(Material.LEATHER_CHESTPLATE), "§b§lNecromancer §a§lLvL 2§8 || §bChestplate"), Color.BLACK), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));
			kit.setLeggings(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.setDisplayname(new ItemStack(Material.LEATHER_LEGGINGS), "§b§lNecromancer §a§lLvL 2§8 || §bLeggings"), Color.BLACK), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));
			kit.setBoots(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.setDisplayname(new ItemStack(Material.LEATHER_BOOTS), "§b§lNecromancer §a§lLvL 2§8 || §bBoots"), Color.BLACK), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));
		}
		{// Necromancer Level 3 \\
			Kit kit = new Kit(KitPvPKit.NECROMANCER.getName() + " 3");
			kit.setItem(0, ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.setDisplayname(new ItemStack(Material.GOLD_HOE), "§b§lNecromancer §a§lLvL 2§8 || §bWeapon"), Enchantment.DAMAGE_ALL, 8)), 4));
			kit.setItem(1, ItemUtils.hideFlags(ItemUtils.addEnchantment(ItemUtils.setLore(ItemUtils.setDisplayname(new ItemStack(Material.STICK), "§b§lNecromancer §a§lLvL 3§8 || §8Necromancer's Staff"), ItemType.WITHER_I.addEnchantment(new ArrayList<String>())), Enchantment.DURABILITY, 1), 5));
			kit.setItem(2, ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.setLore(ItemUtils.setDisplayname(new ItemStack(Material.BOW), "§b§lNecromancer §a§lLvL 3§8 || §bBow"), ProjectileType.UNDEATH_II.addEnchantment(new ArrayList<String>())), Enchantment.ARROW_DAMAGE, 1)), 4));
			kit.setItem(3, ItemUtils.setDisplayname(new ItemStack(Material.ARROW, 10), "§b§lNecromancer §a§lLvL 3§8 || §bArrow"));
			
			kit.setHelmet(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.setDisplayname(new ItemStack(Material.LEATHER_HELMET), "§b§lNecromancer §a§lLvL 3§8 || §bHelmet"), Color.BLACK), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));
			kit.setChestplate(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.setDisplayname(new ItemStack(Material.LEATHER_CHESTPLATE), "§b§lNecromancer §a§lLvL 3§8 || §bChestplate"), Color.BLACK), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));
			kit.setLeggings(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.setDisplayname(new ItemStack(Material.LEATHER_LEGGINGS), "§b§lNecromancer §a§lLvL 3§8 || §bLeggings"), Color.BLACK), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));
			kit.setBoots(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.setDisplayname(new ItemStack(Material.LEATHER_BOOTS), "§b§lNecromancer §a§lLvL 3§8 || §bBoots"), Color.BLACK), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));
		}
		{// King Level 1 \\
			Kit kit = new Kit(KitPvPKit.KING.getName() + " 1");
			kit.setItem(0, ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.setDisplayname(new ItemStack(Material.STONE_SWORD), "§b§lKing §a§lLvL 1§8 || §bWeapon"), Enchantment.DAMAGE_ALL, 1)), 4));
			
			kit.setHelmet(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.setDisplayname(new ItemStack(Material.DIAMOND_HELMET), "§b§lKing §a§lLvL 1§8 || §bHelmet")), 4));
			kit.setChestplate(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.setDisplayname(new ItemStack(Material.GOLD_CHESTPLATE), "§b§lKing §a§lLvL 1§8 || §bChestplate"), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));
			kit.setLeggings(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.setDisplayname(new ItemStack(Material.GOLD_LEGGINGS), "§b§lKing §a§lLvL 1§8 || §bLeggings")), 4));
			kit.setBoots(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.setDisplayname(new ItemStack(Material.GOLD_BOOTS), "§b§lKing §a§lLvL 1§8 || §bBoots")), 4));
		}
		{// King Level 2 \\
			Kit kit = new Kit(KitPvPKit.KING.getName() + " 2");
			kit.setItem(0, ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.setLore(ItemUtils.setDisplayname(new ItemStack(Material.STONE_SWORD), "§b§lKing §a§lLvL 2§8 || §bWeapon§a"), ItemType.HEALING_I.addEnchantment(new ArrayList<String>())), Enchantment.DAMAGE_ALL, 1)), 4));
			
			kit.setHelmet(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.setDisplayname(new ItemStack(Material.DIAMOND_HELMET), "§b§lKing §a§lLvL 2§8 || §bHelmet")), 4));
			kit.setChestplate(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.setDisplayname(new ItemStack(Material.GOLD_CHESTPLATE), "§b§lKing §a§lLvL 2§8 || §bChestplate"), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));
			kit.setLeggings(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.setDisplayname(new ItemStack(Material.GOLD_LEGGINGS), "§b§lKing §a§lLvL 2§8 || §bLeggings"), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));
			kit.setBoots(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.setDisplayname(new ItemStack(Material.GOLD_BOOTS), "§b§lKing §a§lLvL 2§8 || §bBoots")), 4));
		}
		{// King Level 3 \\
			Kit kit = new Kit(KitPvPKit.KING.getName() + " 3");
			kit.setItem(0, ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.setLore(ItemUtils.setDisplayname(new ItemStack(Material.STONE_SWORD), "§b§lKing §a§lLvL 3§8 || §bWeapon§a"), ItemType.HEALING_II.addEnchantment(new ArrayList<String>())), Enchantment.DAMAGE_ALL, 1)), 4));
			
			kit.setHelmet(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.setDisplayname(new ItemStack(Material.DIAMOND_HELMET), "§b§lKing §a§lLvL 3§8 || §bHelmet")), 4));
			kit.setChestplate(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.setDisplayname(new ItemStack(Material.GOLD_CHESTPLATE), "§b§lKing §a§lLvL 3§8 || §bChestplate"), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));
			kit.setLeggings(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.setDisplayname(new ItemStack(Material.GOLD_LEGGINGS), "§b§lKing §a§lLvL 3§8 || §bLeggings"), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));
			kit.setBoots(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.setDisplayname(new ItemStack(Material.GOLD_BOOTS), "§b§lKing §a§lLvL 3§8 || §bBoots"), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));
		}
		{// Tree Level 1 \\
			Kit kit = new Kit(KitPvPKit.TREE.getName() + " 1");
			kit.setItem(0, ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.setDisplayname(new ItemStack(Material.LEAVES), "§b§lTree §a§lLvL 1§8 || §bWeapon"), Enchantment.DAMAGE_ALL, 4)), 4));
			
			kit.setHelmet(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.setDisplayname(new ItemStack(Material.LEAVES), "§b§lTree §a§lLvL 1§8 || §bHelmet"), Enchantment.PROTECTION_ENVIRONMENTAL, 3)), 4));
			kit.setChestplate(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.setDisplayname(new ItemStack(Material.CHAINMAIL_CHESTPLATE), "§b§lTree §a§lLvL 1§8 || §bChestplate")), 4));
			kit.setLeggings(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.setDisplayname(new ItemStack(Material.CHAINMAIL_LEGGINGS), "§b§lTree §a§lLvL 1§8 || §bLeggings")), 4));
			kit.setBoots(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.setDisplayname(new ItemStack(Material.CHAINMAIL_BOOTS), "§b§lTree §a§lLvL 1§8 || §bBoots")), 4));
		}
		{// Tree Level 2 \\
			Kit kit = new Kit(KitPvPKit.TREE.getName() + " 2");
			kit.setItem(0, ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.setDisplayname(new ItemStack(Material.LEAVES), "§b§lTree §a§lLvL 2§8 || §bWeapon"), Enchantment.DAMAGE_ALL, 5)), 4));
			kit.setItem(1, ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.setLore(ItemUtils.setDurability(ItemUtils.setDisplayname(new ItemStack(Material.LOG), "§b§lTree §a§lLvL 2§8 || §dBarrier"), 1), ItemType.BARRIER_I.addEnchantment(new ArrayList<String>())), Enchantment.DURABILITY, 1)), 4));
			
			kit.setHelmet(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.setDisplayname(new ItemStack(Material.LEAVES), "§b§lTree §a§lLvL 2§8 || §bHelmet"), Enchantment.PROTECTION_ENVIRONMENTAL, 4)), 4));
			kit.setChestplate(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.setDisplayname(new ItemStack(Material.CHAINMAIL_CHESTPLATE), "§b§lTree §a§lLvL 2§8 || §bChestplate")), 4));
			kit.setLeggings(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.setDisplayname(new ItemStack(Material.CHAINMAIL_LEGGINGS), "§b§lTree §a§lLvL 2§8 || §bLeggings")), 4));
			kit.setBoots(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.setDisplayname(new ItemStack(Material.CHAINMAIL_BOOTS), "§b§lTree §a§lLvL 2§8 || §bBoots"), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
		}
		{// Tree Level 3 \\
			Kit kit = new Kit(KitPvPKit.TREE.getName() + " 3");
			kit.setItem(0, ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.setDisplayname(new ItemStack(Material.LEAVES), "§b§lTree §a§lLvL 3§8 || §bWeapon"), Enchantment.DAMAGE_ALL, 5)), 4));
			kit.setItem(1, ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.setLore(ItemUtils.setDurability(ItemUtils.setDisplayname(new ItemStack(Material.LOG), "§b§lTree §a§lLvL 3§8 || §dBarrier"), 1), ItemType.BARRIER_II.addEnchantment(new ArrayList<String>())), Enchantment.DURABILITY, 1)), 4));
			
			kit.setHelmet(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.setDisplayname(new ItemStack(Material.LEAVES), "§b§lTree §a§lLvL 3§8 || §bHelmet"), Enchantment.PROTECTION_ENVIRONMENTAL, 4)), 4));
			kit.setChestplate(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.setDisplayname(new ItemStack(Material.CHAINMAIL_CHESTPLATE), "§b§lTree §a§lLvL 3§8 || §bChestplate"), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
			kit.setLeggings(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.setDisplayname(new ItemStack(Material.CHAINMAIL_LEGGINGS), "§b§lTree §a§lLvL 3§8 || §bLeggings"), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
			kit.setBoots(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.setDisplayname(new ItemStack(Material.CHAINMAIL_BOOTS), "§b§lTree §a§lLvL 3§8 || §bBoots"), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
		}
		{// Blaze Level 1 \\
			Kit kit = new Kit(KitPvPKit.BLAZE.getName() + " 1");
			kit.setItem(0, ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addEnchantment(ItemUtils.setDisplayname(new ItemStack(Material.BLAZE_POWDER), "§b§lBlaze §a§lLvL 1§8 || §bWeapon"), Enchantment.DAMAGE_ALL, 4), Enchantment.FIRE_ASPECT, 2)), 4));
			
			kit.setHelmet(ItemUtils.hideFlags(ItemUtils.addEnchantment(ItemUtils.addEnchantment(ItemUtils.setDurability(ItemUtils.setDisplayname(new ItemStack(Material.STAINED_CLAY), "§b§lBlaze §a§lLvL 1§8 || §bHelmet"), 4), Enchantment.PROTECTION_ENVIRONMENTAL, 2), Enchantment.PROTECTION_FIRE, 3), 4));
			kit.setChestplate(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.setDisplayname(new ItemStack(Material.LEATHER_CHESTPLATE), "§b§lBlaze §a§lLvL 1§8 || §bChestplate"), Color.fromBGR(10, 130, 180)), Enchantment.PROTECTION_ENVIRONMENTAL, 1), Enchantment.PROTECTION_FIRE, 3)), 4));
			kit.setLeggings(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.setDisplayname(new ItemStack(Material.LEATHER_LEGGINGS), "§b§lBlaze §a§lLvL 1§8 || §bLeggings"), Color.fromBGR(10, 130, 180)), Enchantment.PROTECTION_ENVIRONMENTAL, 1), Enchantment.PROTECTION_FIRE, 3)), 4));
			kit.setBoots(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.setDisplayname(new ItemStack(Material.LEATHER_BOOTS), "§b§lBlaze §a§lLvL 1§8 || §bBoots"), Color.fromBGR(10, 130, 180)), Enchantment.PROTECTION_ENVIRONMENTAL, 1), Enchantment.PROTECTION_FIRE, 3)), 4));
		}
		{// Blaze Level 2 \\
			Kit kit = new Kit(KitPvPKit.BLAZE.getName() + " 2");
			kit.setItem(0, ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addEnchantment(ItemUtils.setDisplayname(new ItemStack(Material.BLAZE_POWDER), "§b§lBlaze §a§lLvL 2§8 || §bWeapon"), Enchantment.DAMAGE_ALL, 5), Enchantment.FIRE_ASPECT, 3)), 4));
			
			kit.setHelmet(ItemUtils.hideFlags(ItemUtils.addEnchantment(ItemUtils.addEnchantment(ItemUtils.setDurability(ItemUtils.setDisplayname(new ItemStack(Material.STAINED_CLAY), "§b§lBlaze §a§lLvL 2§8 || §bHelmet"), 4), Enchantment.PROTECTION_ENVIRONMENTAL, 2), Enchantment.PROTECTION_FIRE, 3), 4));
			kit.setChestplate(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.setDisplayname(new ItemStack(Material.LEATHER_CHESTPLATE), "§b§lBlaze §a§lLvL 2§8 || §bChestplate"), Color.fromBGR(10, 130, 180)), Enchantment.PROTECTION_ENVIRONMENTAL, 2), Enchantment.PROTECTION_FIRE, 3)), 4));
			kit.setLeggings(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.setDisplayname(new ItemStack(Material.LEATHER_LEGGINGS), "§b§lBlaze §a§lLvL 2§8 || §bLeggings"), Color.fromBGR(10, 130, 180)), Enchantment.PROTECTION_ENVIRONMENTAL, 1), Enchantment.PROTECTION_FIRE, 3)), 4));
			kit.setBoots(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.setLore(ItemUtils.setDisplayname(new ItemStack(Material.LEATHER_BOOTS), "§b§lBlaze §a§lLvL 2§8 || §bBoots"), ArmorType.LIGHT_I.addEnchantment(new ArrayList<String>())), Color.fromBGR(10, 130, 180)), Enchantment.PROTECTION_ENVIRONMENTAL, 1), Enchantment.PROTECTION_FIRE, 3)), 4));
		}
		{// Blaze Level 3 \\
			Kit kit = new Kit(KitPvPKit.BLAZE.getName() + " 3");
			kit.setItem(0, ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addEnchantment(ItemUtils.setDisplayname(new ItemStack(Material.BLAZE_POWDER), "§b§lBlaze §a§lLvL 3§8 || §bWeapon"), Enchantment.DAMAGE_ALL, 6), Enchantment.FIRE_ASPECT, 4)), 4));
			
			kit.setHelmet(ItemUtils.hideFlags(ItemUtils.addEnchantment(ItemUtils.addEnchantment(ItemUtils.setDurability(ItemUtils.setDisplayname(new ItemStack(Material.STAINED_CLAY), "§b§lBlaze §a§lLvL 3§8 || §bHelmet"), 4), Enchantment.PROTECTION_ENVIRONMENTAL, 3), Enchantment.PROTECTION_FIRE, 3), 4));
			kit.setChestplate(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.setDisplayname(new ItemStack(Material.LEATHER_CHESTPLATE), "§b§lBlaze §a§lLvL 3§8 || §bChestplate"), Color.fromBGR(10, 130, 180)), Enchantment.PROTECTION_ENVIRONMENTAL, 2), Enchantment.PROTECTION_FIRE, 3)), 4));
			kit.setLeggings(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.setDisplayname(new ItemStack(Material.LEATHER_LEGGINGS), "§b§lBlaze §a§lLvL 3§8 || §bLeggings"), Color.fromBGR(10, 130, 180)), Enchantment.PROTECTION_ENVIRONMENTAL, 2), Enchantment.PROTECTION_FIRE, 3)), 4));
			kit.setBoots(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.setLore(ItemUtils.setDisplayname(new ItemStack(Material.LEATHER_BOOTS), "§b§lBlaze §a§lLvL 3§8 || §bBoots"), ArmorType.LIGHT_I.addEnchantment(new ArrayList<String>())), Color.fromBGR(10, 130, 180)), Enchantment.PROTECTION_ENVIRONMENTAL, 2), Enchantment.PROTECTION_FIRE, 3)), 4));
		}
		{// TNT Level 1 \\
			Kit kit = new Kit(KitPvPKit.TNT.getName() + " 1");
			kit.setItem(0, ItemUtils.hideFlags(ItemUtils.addEnchantment(ItemUtils.addEnchantment(ItemUtils.setDisplayname(new ItemStack(Material.TNT), "§b§lTNT §a§lLvL 1§8 || §bWeapon"), Enchantment.DAMAGE_ALL, 5), Enchantment.KNOCKBACK, 3), 4));
			kit.setItem(1, ItemUtils.addEnchantment(ItemUtils.setLore(ItemUtils.setDurability(ItemUtils.setDisplayname(new ItemStack(Material.REDSTONE_TORCH_ON), "§b§lTNT §a§lLvL 1§8 || §4TNT Launcher"), 8193), ItemType.TNT_I.addEnchantment(new ArrayList<String>())), Enchantment.DURABILITY, 1));

			kit.setHelmet(ItemUtils.hideFlags(ItemUtils.addEnchantment(ItemUtils.setDisplayname(new ItemStack(Material.TNT), "§b§lTNT §a§lLvL 1§8 || §bHelmet"), Enchantment.PROTECTION_ENVIRONMENTAL, 4), 4));
			kit.setChestplate(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.setDisplayname(new ItemStack(Material.GOLD_CHESTPLATE), "§b§lTNT §a§lLvL 1§8 || §bChestplate"), Enchantment.PROTECTION_EXPLOSIONS, 1)), 4));
			kit.setLeggings(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.setDisplayname(new ItemStack(Material.GOLD_LEGGINGS), "§b§lTNT §a§lLvL 1§8 || §bLeggings"), Enchantment.PROTECTION_EXPLOSIONS, 1)), 4));
			kit.setBoots(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.setDisplayname(new ItemStack(Material.GOLD_BOOTS), "§b§lTNT §a§lLvL 1§8 || §bBoots"), Enchantment.PROTECTION_EXPLOSIONS, 1)), 4));
		}
		{// TNT Level 2 \\
			Kit kit = new Kit(KitPvPKit.TNT.getName() + " 2");
			kit.setItem(0, ItemUtils.hideFlags(ItemUtils.addEnchantment(ItemUtils.addEnchantment(ItemUtils.setDisplayname(new ItemStack(Material.TNT), "§b§lTNT §a§lLvL 2§8 || §bWeapon"), Enchantment.DAMAGE_ALL, 5), Enchantment.KNOCKBACK, 4), 4));
			kit.setItem(1, ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.setLore(ItemUtils.setDisplayname(new ItemStack(Material.BOW), "§b§lTNT §a§lLvL 2§8 || §bBow"), ProjectileType.EXPLOSIVE_I.addEnchantment(new ArrayList<String>())), Enchantment.DURABILITY, 1)), 5));
			kit.setItem(2, ItemUtils.setDisplayname(new ItemStack(Material.ARROW, 2), "§b§lTNT §a§lLvL 2§8 || §bArrow"));
			kit.setItem(3, ItemUtils.addEnchantment(ItemUtils.setLore(ItemUtils.setDurability(ItemUtils.setDisplayname(new ItemStack(Material.REDSTONE_TORCH_ON), "§b§lTNT §a§lLvL 2§8 || §4TNT Launcher"), 8193), ItemType.TNT_I.addEnchantment(new ArrayList<String>())), Enchantment.DURABILITY, 1));

			kit.setHelmet(ItemUtils.hideFlags(ItemUtils.addEnchantment(ItemUtils.setDisplayname(new ItemStack(Material.TNT), "§b§lTNT §a§lLvL 2§8 || §bHelmet"), Enchantment.PROTECTION_ENVIRONMENTAL, 4), 4));
			kit.setChestplate(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addEnchantment(ItemUtils.setDisplayname(new ItemStack(Material.GOLD_CHESTPLATE), "§b§lTNT §a§lLvL 2§8 || §bChestplate"), Enchantment.PROTECTION_EXPLOSIONS, 2), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
			kit.setLeggings(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.setDisplayname(new ItemStack(Material.GOLD_LEGGINGS), "§b§lTNT §a§lLvL 2§8 || §bLeggings"), Enchantment.PROTECTION_EXPLOSIONS, 2)), 4));
			kit.setBoots(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.setDisplayname(new ItemStack(Material.GOLD_BOOTS), "§b§lTNT §a§lLvL 2§8 || §bBoots"), Enchantment.PROTECTION_EXPLOSIONS, 2)), 4));
		}
		{// TNT Level 3 \\
			Kit kit = new Kit(KitPvPKit.TNT.getName() + " 3");
			kit.setItem(0, ItemUtils.hideFlags(ItemUtils.addEnchantment(ItemUtils.addEnchantment(ItemUtils.setDisplayname(new ItemStack(Material.TNT), "§b§lTNT §a§lLvL 3§8 || §bWeapon"), Enchantment.DAMAGE_ALL, 5), Enchantment.KNOCKBACK, 4), 4));
			kit.setItem(1, ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.setLore(ItemUtils.setDisplayname(new ItemStack(Material.BOW), "§b§lTNT §a§lLvL 3§8 || §bBow"), ProjectileType.EXPLOSIVE_I.addEnchantment(new ArrayList<String>())), Enchantment.DURABILITY, 1)), 5));
			kit.setItem(2, ItemUtils.setDisplayname(new ItemStack(Material.ARROW, 4), "§b§lTNT §a§lLvL 3§8 || §bArrow"));
			kit.setItem(3, ItemUtils.addEnchantment(ItemUtils.setLore(ItemUtils.setDurability(ItemUtils.setDisplayname(new ItemStack(Material.REDSTONE_TORCH_ON), "§b§lTNT §a§lLvL 3§8 || §4TNT Launcher"), 8193), ItemType.TNT_I.addEnchantment(new ArrayList<String>())), Enchantment.DURABILITY, 1));

			kit.setHelmet(ItemUtils.hideFlags(ItemUtils.addEnchantment(ItemUtils.setDisplayname(new ItemStack(Material.TNT), "§b§lTNT §a§lLvL 3§8 || §bHelmet"), Enchantment.PROTECTION_ENVIRONMENTAL, 4), 4));
			kit.setChestplate(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addEnchantment(ItemUtils.setDisplayname(new ItemStack(Material.GOLD_CHESTPLATE), "§b§lTNT §a§lLvL 3§8 || §bChestplate"), Enchantment.PROTECTION_EXPLOSIONS, 3), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
			kit.setLeggings(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addEnchantment(ItemUtils.setDisplayname(new ItemStack(Material.GOLD_LEGGINGS), "§b§lTNT §a§lLvL 3§8 || §bLeggings"), Enchantment.PROTECTION_EXPLOSIONS, 3), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
			kit.setBoots(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addEnchantment(ItemUtils.setDisplayname(new ItemStack(Material.GOLD_BOOTS), "§b§lTNT §a§lLvL 3§8 || §bBoots"), Enchantment.PROTECTION_EXPLOSIONS, 3), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
		}
		{// Fisherman Level 1 \\
			Kit kit = new Kit(KitPvPKit.FISHERMAN.getName() + " 1");
			kit.setItem(0, ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.setDisplayname(new ItemStack(Material.FISHING_ROD), "§b§lFisherman §a§lLvL 1§8 || §bWeapon"), Enchantment.DAMAGE_ALL, 6)), 4));
			kit.setItem(1, ItemUtils.hideFlags(ItemUtils.addEnchantment(ItemUtils.addEnchantment(ItemUtils.setDurability(ItemUtils.setDisplayname(new ItemStack(Material.RAW_FISH), "§b§lFisherman §a§lLvL 1§8 || §bFish"), 1), Enchantment.KNOCKBACK, 4), Enchantment.FIRE_ASPECT, 2), 4));
			
			kit.setHelmet(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.setDisplayname(new ItemStack(Material.LEATHER_HELMET), "§b§lFisherman §a§lLvL 1§8 || §bHelmet"), Color.fromBGR(10, 130, 120)), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));
			kit.setChestplate(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.setDisplayname(new ItemStack(Material.LEATHER_CHESTPLATE), "§b§lFisherman §a§lLvL 1§8 || §bChestplate"), Color.fromBGR(10, 130, 120)), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
			kit.setLeggings(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.setDisplayname(new ItemStack(Material.LEATHER_LEGGINGS), "§b§lFisherman §a§lLvL 1§8 || §bLeggings"), Color.fromBGR(10, 130, 120)), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
			kit.setBoots(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.setDisplayname(new ItemStack(Material.LEATHER_BOOTS), "§b§lFisherman §a§lLvL 1§8 || §bBoots"), Color.fromBGR(10, 130, 120)), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));
		}
		{// Fisherman Level 2 \\
			Kit kit = new Kit(KitPvPKit.FISHERMAN.getName() + " 2");
			kit.setItem(0, ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.setDisplayname(new ItemStack(Material.FISHING_ROD), "§b§lFisherman §a§lLvL 2§8 || §bWeapon"), Enchantment.DAMAGE_ALL, 6)), 4));
			kit.setItem(1, ItemUtils.hideFlags(ItemUtils.addEnchantment(ItemUtils.addEnchantment(ItemUtils.setDurability(ItemUtils.setDisplayname(new ItemStack(Material.RAW_FISH), "§b§lFisherman §a§lLvL 2§8 || §bFish"), 1), Enchantment.KNOCKBACK, 4), Enchantment.FIRE_ASPECT, 2), 4));
			kit.setItem(2, ItemUtils.hideFlags(ItemUtils.addEnchantment(ItemUtils.setLore(ItemUtils.setDurability(ItemUtils.setDisplayname(new ItemStack(Material.RAW_FISH), "§b§lFisherman §a§lLvL 2§8 || §9Fish Attack"), 3), ItemType.FISH_ATTACK_I.addEnchantment(new ArrayList<String>())), Enchantment.DURABILITY, 1), 5));
			
			kit.setHelmet(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.setDisplayname(new ItemStack(Material.LEATHER_HELMET), "§b§lFisherman §a§lLvL 2§8 || §bHelmet"), Color.fromBGR(10, 130, 120)), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));
			kit.setChestplate(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.setDisplayname(new ItemStack(Material.LEATHER_CHESTPLATE), "§b§lFisherman §a§lLvL 2§8 || §bChestplate"), Color.fromBGR(10, 130, 120)), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));
			kit.setLeggings(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.setDisplayname(new ItemStack(Material.LEATHER_LEGGINGS), "§b§lFisherman §a§lLvL 2§8 || §bLeggings"), Color.fromBGR(10, 130, 120)), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));
			kit.setBoots(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.setDisplayname(new ItemStack(Material.LEATHER_BOOTS), "§b§lFisherman §a§lLvL 2§8 || §bBoots"), Color.fromBGR(10, 130, 120)), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));
		}
		{// Fisherman Level 3 \\
			Kit kit = new Kit(KitPvPKit.FISHERMAN.getName() + " 3");
			kit.setItem(0, ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.setDisplayname(new ItemStack(Material.FISHING_ROD), "§b§lFisherman §a§lLvL 3§8 || §bWeapon"), Enchantment.DAMAGE_ALL, 6)), 4));
			kit.setItem(1, ItemUtils.hideFlags(ItemUtils.addEnchantment(ItemUtils.addEnchantment(ItemUtils.setDurability(ItemUtils.setDisplayname(new ItemStack(Material.RAW_FISH), "§b§lFisherman §a§lLvL 3§8 || §bFish"), 1), Enchantment.KNOCKBACK, 5), Enchantment.FIRE_ASPECT, 2), 4));
			kit.setItem(2, ItemUtils.hideFlags(ItemUtils.addEnchantment(ItemUtils.setLore(ItemUtils.setDurability(ItemUtils.setDisplayname(new ItemStack(Material.RAW_FISH), "§b§lFisherman §a§lLvL 3§8 || §9Fish Attack"), 3), ItemType.FISH_ATTACK_I.addEnchantment(new ArrayList<String>())), Enchantment.DURABILITY, 1), 5));
			
			kit.setHelmet(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.setDisplayname(new ItemStack(Material.LEATHER_HELMET), "§b§lFisherman §a§lLvL 3§8 || §bHelmet"), Color.fromBGR(10, 130, 120)), Enchantment.PROTECTION_ENVIRONMENTAL, 3)), 4));
			kit.setChestplate(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.setDisplayname(new ItemStack(Material.LEATHER_CHESTPLATE), "§b§lFisherman §a§lLvL 3§8 || §bChestplate"), Color.fromBGR(10, 130, 120)), Enchantment.PROTECTION_ENVIRONMENTAL, 3)), 4));
			kit.setLeggings(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.setDisplayname(new ItemStack(Material.LEATHER_LEGGINGS), "§b§lFisherman §a§lLvL 3§8 || §bLeggings"), Color.fromBGR(10, 130, 120)), Enchantment.PROTECTION_ENVIRONMENTAL, 3)), 4));
			kit.setBoots(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.setDisplayname(new ItemStack(Material.LEATHER_BOOTS), "§b§lFisherman §a§lLvL 3§8 || §bBoots"), Color.fromBGR(10, 130, 120)), Enchantment.PROTECTION_ENVIRONMENTAL, 3)), 4));
		}
		{// SnowGolem Level 1 \\
			Kit kit = new Kit(KitPvPKit.SNOWGOLEM.getName() + " 1");
			kit.setItem(0, ItemUtils.hideFlags(ItemUtils.addEnchantment(ItemUtils.addEnchantment(ItemUtils.setDisplayname(new ItemStack(Material.SNOW_BALL), "§b§lSnowGolem §a§lLvL 1§8 || §bWeapon"), Enchantment.DAMAGE_ALL, 6), Enchantment.KNOCKBACK, 2), 4));
			
			kit.setHelmet(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.setDisplayname(new ItemStack(Material.PUMPKIN), "§b§lSnowGolem §a§lLvL 1§8 || §bHelmet"), Enchantment.PROTECTION_ENVIRONMENTAL, 5)), 4));
			kit.setChestplate(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.setDisplayname(new ItemStack(Material.LEATHER_CHESTPLATE), "§b§lSnowGolem §a§lLvL 1§8 || §bChestplate"), Color.fromBGR(200, 200, 200)), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));
			kit.setLeggings(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.setDisplayname(new ItemStack(Material.LEATHER_LEGGINGS), "§b§lSnowGolem §a§lLvL 1§8 || §bLeggings"), Color.fromBGR(200, 200, 200)), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
			kit.setBoots(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.setDisplayname(new ItemStack(Material.LEATHER_BOOTS), "§b§lSnowGolem §a§lLvL 1§8 || §bBoots"), Color.fromBGR(200, 200, 200)), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
			kit.setPotionEffect(new PotionEffect(PotionEffectType.SPEED, 2000000, 0));
		}
		{// SnowGolem Level 2 \\
			Kit kit = new Kit(KitPvPKit.SNOWGOLEM.getName() + " 2");
			kit.setItem(0, ItemUtils.hideFlags(ItemUtils.addEnchantment(ItemUtils.addEnchantment(ItemUtils.setDisplayname(new ItemStack(Material.SNOW_BALL), "§b§lSnowGolem §a§lLvL 2§8 || §bWeapon"), Enchantment.DAMAGE_ALL, 6), Enchantment.KNOCKBACK, 2), 4));
			kit.setItem(1, ItemUtils.hideFlags(ItemUtils.addEnchantment(ItemUtils.setLore(ItemUtils.setDurability(ItemUtils.setDisplayname(new ItemStack(Material.IRON_FENCE), "§b§lSnowGolem §a§lLvL 2§8 || §7Shield"), 3), ItemType.SHIELD_I.addEnchantment(new ArrayList<String>())), Enchantment.DURABILITY, 1), 5));
			
			kit.setHelmet(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.setDisplayname(new ItemStack(Material.PUMPKIN), "§b§lSnowGolem §a§lLvL 2§8 || §bHelmet"), Enchantment.PROTECTION_ENVIRONMENTAL, 5)), 4));
			kit.setChestplate(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.setDisplayname(new ItemStack(Material.LEATHER_CHESTPLATE), "§b§lSnowGolem §a§lLvL 2§8 || §bChestplate"), Color.fromBGR(200, 200, 200)), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));
			kit.setLeggings(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.setDisplayname(new ItemStack(Material.LEATHER_LEGGINGS), "§b§lSnowGolem §a§lLvL 2§8 || §bLeggings"), Color.fromBGR(200, 200, 200)), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));
			kit.setBoots(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.setDisplayname(new ItemStack(Material.LEATHER_BOOTS), "§b§lSnowGolem §a§lLvL 2§8 || §bBoots"), Color.fromBGR(200, 200, 200)), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));
			kit.setPotionEffect(new PotionEffect(PotionEffectType.SPEED, 2000000, 0));
		}
		{// SnowGolem Level 3 \\
			Kit kit = new Kit(KitPvPKit.SNOWGOLEM.getName() + " 3");
			kit.setItem(0, ItemUtils.hideFlags(ItemUtils.addEnchantment(ItemUtils.addEnchantment(ItemUtils.setDisplayname(new ItemStack(Material.SNOW_BALL), "§b§lSnowGolem §a§lLvL 3§8 || §bWeapon"), Enchantment.DAMAGE_ALL, 7), Enchantment.KNOCKBACK, 3), 4));
			kit.setItem(1, ItemUtils.hideFlags(ItemUtils.addEnchantment(ItemUtils.setLore(ItemUtils.setDurability(ItemUtils.setDisplayname(new ItemStack(Material.IRON_FENCE), "§b§lSnowGolem §a§lLvL 3§8 || §7Shield"), 3), ItemType.SHIELD_II.addEnchantment(new ArrayList<String>())), Enchantment.DURABILITY, 1), 5));
			
			kit.setHelmet(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.setDisplayname(new ItemStack(Material.PUMPKIN), "§b§lSnowGolem §a§lLvL 3§8 || §bHelmet"), Enchantment.PROTECTION_ENVIRONMENTAL, 5)), 4));
			kit.setChestplate(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.setDisplayname(new ItemStack(Material.LEATHER_CHESTPLATE), "§b§lSnowGolem §a§lLvL 3§8 || §bChestplate"), Color.fromBGR(200, 200, 200)), Enchantment.PROTECTION_ENVIRONMENTAL, 3)), 4));
			kit.setLeggings(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.setDisplayname(new ItemStack(Material.LEATHER_LEGGINGS), "§b§lSnowGolem §a§lLvL 3§8 || §bLeggings"), Color.fromBGR(200, 200, 200)), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));
			kit.setBoots(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.setDisplayname(new ItemStack(Material.LEATHER_BOOTS), "§b§lSnowGolem §a§lLvL 3§8 || §bBoots"), Color.fromBGR(200, 200, 200)), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));
			kit.setPotionEffect(new PotionEffect(PotionEffectType.SPEED, 2000000, 0));
		}
		{// Librarian Level 1 \\
			Kit kit = new Kit(KitPvPKit.LIBRARIAN.getName() + " 1");
			kit.setItem(0, ItemUtils.hideFlags(ItemUtils.addEnchantment(ItemUtils.setDisplayname(new ItemStack(Material.PAPER), "§b§lLibrarian §a§lLvL 1§8 || §bWeapon"), Enchantment.DAMAGE_ALL, 8), 4));
			
			kit.setHelmet(ItemUtils.hideFlags(ItemUtils.addEnchantment(ItemUtils.setDisplayname(new ItemStack(Material.BOOKSHELF), "§b§lLibrarian §a§lLvL 1§8 || §bHelmet"), Enchantment.PROTECTION_ENVIRONMENTAL, 6), 4));
			kit.setChestplate(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.setDisplayname(new ItemStack(Material.CHAINMAIL_CHESTPLATE), "§b§lLibrarian §a§lLvL 1§8 || §bChestplate")), 4));
			kit.setLeggings(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.setDisplayname(new ItemStack(Material.CHAINMAIL_LEGGINGS), "§b§lLibrarian §a§lLvL 1§8 || §bLeggings")), 4));
			kit.setBoots(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.setDisplayname(new ItemStack(Material.CHAINMAIL_BOOTS), "§b§lLibrarian §a§lLvL 1§8 || §bBoots")), 4));
		}
		{// Librarian Level 2 \\
			Kit kit = new Kit(KitPvPKit.LIBRARIAN.getName() + " 2");
			kit.setItem(0, ItemUtils.hideFlags(ItemUtils.addEnchantment(ItemUtils.setDisplayname(new ItemStack(Material.PAPER), "§b§lLibrarian §a§lLvL 2§8 || §bWeapon"), Enchantment.DAMAGE_ALL, 8), 4));
			kit.setItem(1, ItemUtils.hideFlags(ItemUtils.addEnchantment(ItemUtils.setDisplayname(new ItemStack(Material.BOOK), "§b§lLibrarian §a§lLvL 2§8 || §6Knockback Book"), Enchantment.KNOCKBACK, 5), 4));
			
			kit.setHelmet(ItemUtils.hideFlags(ItemUtils.addEnchantment(ItemUtils.setDisplayname(new ItemStack(Material.BOOKSHELF), "§b§lLibrarian §a§lLvL 2§8 || §bHelmet"), Enchantment.PROTECTION_ENVIRONMENTAL, 6), 4));
			kit.setChestplate(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.setDisplayname(new ItemStack(Material.CHAINMAIL_CHESTPLATE), "§b§lLibrarian §a§lLvL 2§8 || §bChestplate")), 4));
			kit.setLeggings(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.setDisplayname(new ItemStack(Material.CHAINMAIL_LEGGINGS), "§b§lLibrarian §a§lLvL 2§8 || §bLeggings"), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
			kit.setBoots(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.setDisplayname(new ItemStack(Material.CHAINMAIL_BOOTS), "§b§lLibrarian §a§lLvL 2§8 || §bBoots")), 4));
		}
		{// Librarian Level 3 \\
			Kit kit = new Kit(KitPvPKit.LIBRARIAN.getName() + " 3");
			kit.setItem(0, ItemUtils.hideFlags(ItemUtils.addEnchantment(ItemUtils.setDisplayname(new ItemStack(Material.PAPER), "§b§lLibrarian §a§lLvL 3§8 || §bWeapon"), Enchantment.DAMAGE_ALL, 8), 4));
			kit.setItem(1, ItemUtils.hideFlags(ItemUtils.addEnchantment(ItemUtils.setDisplayname(new ItemStack(Material.BOOK), "§b§lLibrarian §a§lLvL 3§8 || §6Knockback Book"), Enchantment.KNOCKBACK, 6), 4));
			
			kit.setHelmet(ItemUtils.hideFlags(ItemUtils.addEnchantment(ItemUtils.setDisplayname(new ItemStack(Material.BOOKSHELF), "§b§lLibrarian §a§lLvL 3§8 || §bHelmet"), Enchantment.PROTECTION_ENVIRONMENTAL, 6), 4));
			kit.setChestplate(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.setDisplayname(new ItemStack(Material.CHAINMAIL_CHESTPLATE), "§b§lLibrarian §a§lLvL 3§8 || §bChestplate"), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));
			kit.setLeggings(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.setDisplayname(new ItemStack(Material.CHAINMAIL_LEGGINGS), "§b§lLibrarian §a§lLvL 3§8 || §bLeggings"), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
			kit.setBoots(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.setDisplayname(new ItemStack(Material.CHAINMAIL_BOOTS), "§b§lLibrarian §a§lLvL 3§8 || §bBoots"), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
		}
		{// Spider Level 1 \\
			Kit kit = new Kit(KitPvPKit.SPIDER.getName() + " 1");
			kit.setItem(0, ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.setLore(ItemUtils.setDisplayname(new ItemStack(Material.SPIDER_EYE), "§b§lSpider §a§lLvL 1§8 || §bWeapon"), ItemType.ARTHROPODS_I.addEnchantment(new ArrayList<String>())), Enchantment.DAMAGE_ALL, 6)), 4));
			
			kit.setHelmet(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.setDisplayname(new ItemStack(Material.LEATHER_HELMET), "§b§lSpider §a§lLvL 1§8 || §bHelmet"), Color.BLACK), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
			kit.setChestplate(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.setDisplayname(new ItemStack(Material.LEATHER_CHESTPLATE), "§b§lSpider §a§lLvL 1§8 || §bChestplate"), Color.BLACK), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
			kit.setLeggings(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.setDisplayname(new ItemStack(Material.LEATHER_LEGGINGS), "§b§lSpider §a§lLvL 1§8 || §bLeggings"), Color.BLACK), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
			kit.setBoots(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.setDisplayname(new ItemStack(Material.LEATHER_BOOTS), "§b§lSpider §a§lLvL 1§8 || §bBoots"), Color.BLACK), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
		}
		{// Spider Level 2 \\
			Kit kit = new Kit(KitPvPKit.SPIDER.getName() + " 2");
			kit.setItem(0, ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.setLore(ItemUtils.setDisplayname(new ItemStack(Material.SPIDER_EYE), "§b§lSpider §a§lLvL 2§8 || §bWeapon"), ItemType.ARTHROPODS_II.addEnchantment(new ArrayList<String>())), Enchantment.DAMAGE_ALL, 6)), 4));
			
			kit.setHelmet(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.setDisplayname(new ItemStack(Material.LEATHER_HELMET), "§b§lSpider §a§lLvL 2§8 || §bHelmet"), Color.BLACK), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
			kit.setChestplate(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.setDisplayname(new ItemStack(Material.LEATHER_CHESTPLATE), "§b§lSpider §a§lLvL 2§8 || §bChestplate"), Color.BLACK), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));
			kit.setLeggings(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.setDisplayname(new ItemStack(Material.LEATHER_LEGGINGS), "§b§lSpider §a§lLvL 2§8 || §bLeggings"), Color.BLACK), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
			kit.setBoots(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.setDisplayname(new ItemStack(Material.LEATHER_BOOTS), "§b§lSpider §a§lLvL 2§8 || §bBoots"), Color.BLACK), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));
		}
		{// Spider Level 3 \\
			Kit kit = new Kit(KitPvPKit.SPIDER.getName() + " 3");
			kit.setItem(0, ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.setLore(ItemUtils.setDisplayname(new ItemStack(Material.SPIDER_EYE), "§b§lSpider §a§lLvL 3§8 || §bWeapon"), ItemType.ARTHROPODS_III.addEnchantment(new ArrayList<String>())), Enchantment.DAMAGE_ALL, 6)), 4));
			
			kit.setHelmet(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.setDisplayname(new ItemStack(Material.LEATHER_HELMET), "§b§lSpider §a§lLvL 3§8 || §bHelmet"), Color.BLACK), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));
			kit.setChestplate(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.setDisplayname(new ItemStack(Material.LEATHER_CHESTPLATE), "§b§lSpider §a§lLvL 3§8 || §bChestplate"), Color.BLACK), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));
			kit.setLeggings(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.setDisplayname(new ItemStack(Material.LEATHER_LEGGINGS), "§b§lSpider §a§lLvL 3§8 || §bLeggings"), Color.BLACK), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));
			kit.setBoots(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.setDisplayname(new ItemStack(Material.LEATHER_BOOTS), "§b§lSpider §a§lLvL 3§8 || §bBoots"), Color.BLACK), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));
		}
		{// Villager Level 1 \\
			Kit kit = new Kit(KitPvPKit.VILLAGER.getName() + " 1");
			kit.setItem(0, ItemUtils.hideFlags(ItemUtils.addEnchantment(ItemUtils.setDisplayname(new ItemStack(Material.EMERALD), "§b§lVillager §a§lLvL 1§8 || §bWeapon"), Enchantment.DAMAGE_ALL, 9), 4));
			
			kit.setHelmet(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.setDisplayname(new ItemStack(Material.CHAINMAIL_HELMET), "§b§lVillager §a§lLvL 1§8 || §bHelmet")), 4));
			kit.setChestplate(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.setDisplayname(new ItemStack(Material.CHAINMAIL_CHESTPLATE), "§b§lVillager §a§lLvL 1§8 || §bChestplate")), 4));
			kit.setLeggings(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.setDisplayname(new ItemStack(Material.CHAINMAIL_LEGGINGS), "§b§lVillager §a§lLvL 1§8 || §bLeggings")), 4));
			kit.setBoots(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.setDisplayname(new ItemStack(Material.CHAINMAIL_BOOTS), "§b§lVillager §a§lLvL 1§8 || §bBoots")), 4));
		}
		{// Villager Level 2 \\
			Kit kit = new Kit(KitPvPKit.VILLAGER.getName() + " 2");
			kit.setItem(0, ItemUtils.hideFlags(ItemUtils.addEnchantment(ItemUtils.addEnchantment(ItemUtils.setDisplayname(new ItemStack(Material.EMERALD), "§b§lVillager §a§lLvL 2§8 || §bWeapon"), Enchantment.DAMAGE_ALL, 9), Enchantment.FIRE_ASPECT, 1), 4));
			
			kit.setHelmet(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.setDisplayname(new ItemStack(Material.CHAINMAIL_HELMET), "§b§lVillager §a§lLvL 2§8 || §bHelmet"), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
			kit.setChestplate(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.setDisplayname(new ItemStack(Material.CHAINMAIL_CHESTPLATE), "§b§lVillager §a§lLvL 2§8 || §bChestplate")), 4));
			kit.setLeggings(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.setDisplayname(new ItemStack(Material.CHAINMAIL_LEGGINGS), "§b§lVillager §a§lLvL 2§8 || §bLeggings"), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
			kit.setBoots(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.setDisplayname(new ItemStack(Material.CHAINMAIL_BOOTS), "§b§lVillager §a§lLvL 2§8 || §bBoots")), 4));
		}
		{// Villager Level 3 \\
			Kit kit = new Kit(KitPvPKit.VILLAGER.getName() + " 3");
			kit.setItem(0, ItemUtils.hideFlags(ItemUtils.addEnchantment(ItemUtils.addEnchantment(ItemUtils.setDisplayname(new ItemStack(Material.EMERALD), "§b§lVillager §a§lLvL 3§8 || §bWeapon"), Enchantment.DAMAGE_ALL, 9), Enchantment.FIRE_ASPECT, 1), 4));
			kit.setItem(1, ItemUtils.hideFlags(ItemUtils.addEnchantment(ItemUtils.setLore(ItemUtils.setDisplayname(new ItemStack(Material.BREAD), "§b§lVillager §a§lLvL 3§8 || §aTrade System"), ItemType.TRADE_I.addEnchantment(new ArrayList<String>())), Enchantment.DURABILITY, 1), 5));
			
			kit.setHelmet(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.setDisplayname(new ItemStack(Material.CHAINMAIL_HELMET), "§b§lVillager §a§lLvL 3§8 || §bHelmet"), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
			kit.setChestplate(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.setDisplayname(new ItemStack(Material.CHAINMAIL_CHESTPLATE), "§b§lVillager §a§lLvL 3§8 || §bChestplate"), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
			kit.setLeggings(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.setDisplayname(new ItemStack(Material.CHAINMAIL_LEGGINGS), "§b§lVillager §a§lLvL 3§8 || §bLeggings"), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
			kit.setBoots(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.setDisplayname(new ItemStack(Material.CHAINMAIL_BOOTS), "§b§lVillager §a§lLvL 3§8 || §bBoots"), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
		}
		{// Assassin Level 1 \\
			Kit kit = new Kit(KitPvPKit.ASSASSIN.getName() + " 1");
			kit.setItem(0, ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.setDisplayname(new ItemStack(Material.DIAMOND_SWORD), "§b§lAssassin §a§lLvL 1§8 || §bWeapon"), Enchantment.DAMAGE_ALL, 1)), 4));
			
			kit.setHelmet(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.setDisplayname(new ItemStack(Material.LEATHER_HELMET), "§b§lAssassin §a§lLvL 1§8 || §bHelmet")), 4));
			kit.setChestplate(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.setDisplayname(new ItemStack(Material.LEATHER_CHESTPLATE), "§b§lAssassin §a§lLvL 1§8 || §bChestplate")), 4));
			kit.setLeggings(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.setDisplayname(new ItemStack(Material.LEATHER_LEGGINGS), "§b§lAssassin §a§lLvL 1§8 || §bLeggings")), 4));
			kit.setBoots(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addEnchantment(ItemUtils.setDisplayname(new ItemStack(Material.DIAMOND_BOOTS), "§b§lAssassin §a§lLvL 1§8 || §bBoots"), Enchantment.PROTECTION_ENVIRONMENTAL, 1), Enchantment.PROTECTION_FALL, 3)), 4));
			kit.setPotionEffect(new PotionEffect(PotionEffectType.SPEED, 2000000, 1));
		}
		{// Lord Level 1 \\
			Kit kit = new Kit(KitPvPKit.LORD.getName() + " 1");
			kit.setItem(0, ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.setDisplayname(new ItemStack(Material.GOLD_SWORD), "§b§lLord §a§lLvL 1§8 || §bWeapon")), 4));
			kit.setItem(1, ItemUtils.setDisplayname(new ItemStack(Material.GOLDEN_APPLE, 5), "§b§lLord §a§lLvL 1§8 || §eGolden Apple"));
			
			kit.setHelmet(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.setDisplayname(new ItemStack(Material.GOLD_HELMET), "§b§lLord §a§lLvL 1§8 || §bHelmet"), Enchantment.PROTECTION_ENVIRONMENTAL, 3)), 4));
			//kit.setChestplate(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.setDisplayname(new ItemStack(Material.CHAINMAIL_CHESTPLATE), "§b§lLord §a§lLvL 1§8 || §bChestplate")), 4));
			//kit.setLeggings(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.setDisplayname(new ItemStack(Material.CHAINMAIL_LEGGINGS), "§b§lLord §a§lLvL 1§8 || §bLeggings")), 4));
			//kit.setBoots(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.setDisplayname(new ItemStack(Material.CHAINMAIL_BOOTS), "§b§lLord §a§lLvL 1§8 || §bBoots")), 4));
			kit.setPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 2000000, 1));
		}
		{// Vampire Level 1 \\
			Kit kit = new Kit(KitPvPKit.VAMPIRE.getName() + " 1");
			kit.setItem(0, ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.setLore(ItemUtils.setDisplayname(new ItemStack(Material.IRON_SWORD), "§b§lVampire §a§lLvL 1§8 || §bWeapon"), ItemType.VAMPIRE_I.addEnchantment(new ArrayList<String>())), Enchantment.DURABILITY, 1)), 5));
			
			kit.setHelmet(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.setDisplayname(new ItemStack(Material.LEATHER_HELMET), "§b§lVampire §a§lLvL 1§8 || §bHelmet"), Color.fromBGR(50, 10, 150)), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));
			kit.setChestplate(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.setDisplayname(new ItemStack(Material.LEATHER_CHESTPLATE), "§b§lVampire §a§lLvL 1§8 || §bChestplate"), Color.fromBGR(50, 10, 150)), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));
			kit.setLeggings(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.setDisplayname(new ItemStack(Material.LEATHER_LEGGINGS), "§b§lVampire §a§lLvL 1§8 || §bLeggings"), Color.fromBGR(50, 10, 150)), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));
			kit.setBoots(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.setDisplayname(new ItemStack(Material.LEATHER_BOOTS), "§b§lVampire §a§lLvL 1§8 || §bBoots"), Color.fromBGR(50, 10, 150)), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));
		}
		{// DarkMage Level 1 \\
			Kit kit = new Kit(KitPvPKit.DARKMAGE.getName() + " 1");
			kit.setItem(0, ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.setLore(ItemUtils.setDisplayname(new ItemStack(Material.STONE_SWORD), "§b§lDarkMage §a§lLvL 1§8 || §bWeapon§5"), ItemType.MAGIC_I.addEnchantment(new ArrayList<String>())), Enchantment.DAMAGE_ALL, 1)), 4));
			kit.setItem(1, ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.setLore(ItemUtils.setDisplayname(new ItemStack(Material.BREWING_STAND_ITEM), "§b§lDarkMage §a§lLvL 1§8 || §ePotion Launcher"), ItemType.POTION_LAUNCHER_I.addEnchantment(new ArrayList<String>())), Enchantment.DURABILITY, 1)), 5));
			
			kit.setHelmet(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.setDisplayname(new ItemStack(Material.LEATHER_HELMET), "§b§lDarkMage §a§lLvL 1§8 || §bHelmet"), Color.BLACK), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
			kit.setChestplate(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.setLore(ItemUtils.setDisplayname(new ItemStack(Material.LEATHER_CHESTPLATE), "§b§lDarkMage §a§lLvL 1§8 || §bChestplate"), ArmorType.WITHER_ARMOR_I.addEnchantment(new ArrayList<String>())), Color.BLACK), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));
			kit.setLeggings(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.setDisplayname(new ItemStack(Material.LEATHER_LEGGINGS), "§b§lDarkMage §a§lLvL 1§8 || §bLeggings"), Color.BLACK), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));
			kit.setBoots(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.setDisplayname(new ItemStack(Material.LEATHER_BOOTS), "§b§lDarkMage §a§lLvL 1§8 || §bBoots"), Color.BLACK), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
		}
		{// Beast Level 1 \\
			Kit kit = new Kit(KitPvPKit.BEAST.getName() + " 1");
			kit.setItem(0, ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.setLore(ItemUtils.setDisplayname(new ItemStack(Material.IRON_AXE), "§b§lBeast §a§lLvL 1§8 || §bWeapon"), ItemType.KNOCKUP_I.addEnchantment(new ArrayList<String>())), Enchantment.DAMAGE_ALL, 1)), 4));
			
			kit.setHelmet(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.setDisplayname(new ItemStack(Material.LEATHER_HELMET), "§b§lBeast §a§lLvL 1§8 || §bHelmet"), Color.BLACK), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
			kit.setChestplate(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.setDisplayname(new ItemStack(Material.LEATHER_CHESTPLATE), "§b§lBeast §a§lLvL 1§8 || §bChestplate"), Color.BLACK), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
			kit.setLeggings(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.setDisplayname(new ItemStack(Material.LEATHER_LEGGINGS), "§b§lBeast §a§lLvL 1§8 || §bLeggings"), Color.BLACK), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
			kit.setBoots(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.setDisplayname(new ItemStack(Material.LEATHER_BOOTS), "§b§lBeast §a§lLvL 1§8 || §bBoots"), Color.BLACK), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
		}
		{// Fish Level 1 \\
			Kit kit = new Kit(KitPvPKit.FISH.getName() + " 1");
			kit.setItem(0, ItemUtils.hideFlags(ItemUtils.addEnchantment(ItemUtils.setDisplayname(new ItemStack(Material.RAW_FISH), "§b§lFish §a§lLvL 1§8 || §bWeapon"), Enchantment.DAMAGE_ALL, 8), 4));
			kit.setItem(1, ItemUtils.hideFlags(ItemUtils.addEnchantment(ItemUtils.setDurability(ItemUtils.setDisplayname(new ItemStack(Material.RAW_FISH), "§b§lFish §a§lLvL 1§8 || §6Knockback Fish"), 2), Enchantment.KNOCKBACK, 5), 4));
			kit.setItem(2, ItemUtils.hideFlags(ItemUtils.addEnchantment(ItemUtils.addEnchantment(ItemUtils.setDisplayname(new ItemStack(Material.COOKED_FISH), "§b§lFish §a§lLvL 1§8 || §4Hot Fish"), Enchantment.DAMAGE_ALL, 2), Enchantment.FIRE_ASPECT, 2), 4));
			
			kit.setHelmet(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.setDisplayname(new ItemStack(Material.CHAINMAIL_HELMET), "§b§lFish §a§lLvL 1§8 || §bHelmet")), 4));
			kit.setChestplate(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.setDisplayname(new ItemStack(Material.CHAINMAIL_CHESTPLATE), "§b§lFish §a§lLvL 1§8 || §bChestplate")), 4));
			kit.setLeggings(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.setDisplayname(new ItemStack(Material.CHAINMAIL_LEGGINGS), "§b§lFish §a§lLvL 1§8 || §bLeggings")), 4));
			kit.setBoots(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.setDisplayname(new ItemStack(Material.CHAINMAIL_BOOTS), "§b§lFish §a§lLvL 1§8 || §bBoots")), 4));
			kit.setPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, 2000000, 2));
		}
		{// Heavy Level 1 \\
			Kit kit = new Kit(KitPvPKit.HEAVY.getName() + " 1");
			kit.setItem(0, ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.setDisplayname(new ItemStack(Material.IRON_SWORD), "§b§lHeavy §a§lLvL 1§8 || §bWeapon")), 4));
			kit.setItem(1, ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.setLore(ItemUtils.setDisplayname(new ItemStack(Material.BOW), "§b§lHeavy §a§lLvL 1§8 || §bBow"), ProjectileType.ARROW_SPLIT_I.addEnchantment(new ArrayList<String>())), Enchantment.DURABILITY, 1)), 5));
			kit.setItem(2, ItemUtils.setDisplayname(new ItemStack(Material.ARROW, 8), "§b§lHeavy §a§lLvL 1§8 || §bArrow"));

			kit.setHelmet(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.setDisplayname(new ItemStack(Material.LEATHER_HELMET), "§b§lHeavy §a§lLvL 1§8 || §bHelmet"), Color.fromBGR(204, 100, 2)), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));
			kit.setChestplate(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.setDisplayname(new ItemStack(Material.LEATHER_CHESTPLATE), "§b§lHeavy §a§lLvL 1§8 || §bChestplate"), Color.fromBGR(204, 100, 2)), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));
			kit.setLeggings(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.setDisplayname(new ItemStack(Material.LEATHER_LEGGINGS), "§b§lHeavy §a§lLvL 1§8 || §bLeggings"), Color.fromBGR(204, 100, 2)), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));
			kit.setBoots(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.setDisplayname(new ItemStack(Material.LEATHER_BOOTS), "§b§lHeavy §a§lLvL 1§8 || §bBoots"), Color.fromBGR(204, 100, 2)), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));
		}
		{// GrimReaper Level 1 \\
			Kit kit = new Kit(KitPvPKit.GRIMREAPER.getName() + " 1");
			kit.setItem(0, ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.setDisplayname(new ItemStack(Material.STONE_AXE), "§b§lGrimReaper §a§lLvL 1§8 || §bWeapon"), Enchantment.DAMAGE_ALL, 3)), 4));
			
			kit.setHelmet(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.setDurability(ItemUtils.setDisplayname(new ItemStack(Material.SKULL_ITEM), "§b§lGrimReaper §a§lLvL 1§8 || §bHelmet"), 1), Enchantment.PROTECTION_ENVIRONMENTAL, 7)), 4));
			kit.setChestplate(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.setLore(ItemUtils.addColor(ItemUtils.setDisplayname(new ItemStack(Material.LEATHER_CHESTPLATE), "§b§lGrimReaper §a§lLvL 1§8 || §bChestplate"), Color.fromBGR(100, 100, 100)), ArmorType.MOLTEN_ARMOR_I.addEnchantment(new ArrayList<String>())), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
			kit.setLeggings(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addColor(ItemUtils.setDisplayname(new ItemStack(Material.LEATHER_LEGGINGS), "§b§lGrimReaper §a§lLvL 1§8 || §bLeggings"), Color.fromBGR(100, 100, 100))), 4));
			kit.setBoots(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addColor(ItemUtils.setDisplayname(new ItemStack(Material.LEATHER_BOOTS), "§b§lGrimReaper §a§lLvL 1§8 || §bBoots"), Color.fromBGR(100, 100, 100))), 4));
			kit.setPotionEffect(new PotionEffect(PotionEffectType.SLOW, 2000000, 0));
		}
		{// Miner Level 1 \\
			Kit kit = new Kit(KitPvPKit.MINER.getName() + " 1");
			kit.setItem(0, ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.setDisplayname(new ItemStack(Material.GOLD_PICKAXE), "§b§lMiner §a§lLvL 1§8 || §bWeapon"), Enchantment.DAMAGE_ALL, 5)), 4));
			kit.setItem(1, ItemUtils.hideFlags(ItemUtils.addEnchantment(ItemUtils.setLore(ItemUtils.setDisplayname(new ItemStack(Material.COAL), "§b§lMiner §a§lLvL 1§8 || §1Miner Power"), ItemType.HEALING_KIT_I.addEnchantment(new ArrayList<String>())), Enchantment.DURABILITY, 1), 5));
			
			kit.setHelmet(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.setDisplayname(new ItemStack(Material.LEATHER_HELMET), "§b§lMiner §a§lLvL 1§8 || §bHelmet"), Color.fromBGR(150, 150, 150)), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
			kit.setChestplate(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.setDisplayname(new ItemStack(Material.LEATHER_CHESTPLATE), "§b§lMiner §a§lLvL 1§8 || §bChestplate"), Color.fromBGR(150, 150, 150)), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
			kit.setLeggings(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.setDisplayname(new ItemStack(Material.LEATHER_LEGGINGS), "§b§lMiner §a§lLvL 1§8 || §bLeggings"), Color.fromBGR(150, 150, 150)), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
			kit.setBoots(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.setDisplayname(new ItemStack(Material.LEATHER_BOOTS), "§b§lMiner §a§lLvL 1§8 || §bBoots"), Color.fromBGR(150, 150, 150)), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
			kit.setPotionEffect(new PotionEffect(PotionEffectType.SLOW, 2000000, 0));
		}
		{// Farmer Level 1 \\
			Kit kit = new Kit(KitPvPKit.FARMER.getName() + " 1");
			kit.setItem(0, ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.setDisplayname(new ItemStack(Material.STONE_SWORD), "§b§lFarmer §a§lLvL 1§8 || §bWeapon"), Enchantment.DAMAGE_ALL, 2)), 4));
			kit.setItem(1, ItemUtils.hideFlags(ItemUtils.addEnchantment(ItemUtils.setLore(ItemUtils.setDisplayname(new ItemStack(Material.HAY_BLOCK), "§b§lFarmer §a§lLvL 1§8 || §eBlock Explosion"), ItemType.BLOCK_EXPLOSION_I.addEnchantment(new ArrayList<String>())), Enchantment.DURABILITY, 1), 5));
			
			kit.setHelmet(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.setDisplayname(new ItemStack(Material.LEATHER_HELMET), "§b§lFarmer §a§lLvL 1§8 || §bHelmet"), Color.YELLOW), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));
			kit.setChestplate(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addColor(ItemUtils.setDisplayname(new ItemStack(Material.LEATHER_CHESTPLATE), "§b§lFarmer §a§lLvL 1§8 || §bChestplate"), Color.YELLOW)), 4));
			kit.setLeggings(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addColor(ItemUtils.setDisplayname(new ItemStack(Material.LEATHER_LEGGINGS), "§b§lFarmer §a§lLvL 1§8 || §bLeggings"), Color.YELLOW)), 4));
			kit.setBoots(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.addColor(ItemUtils.setDisplayname(new ItemStack(Material.LEATHER_BOOTS), "§b§lFarmer §a§lLvL 1§8 || §bBoots"), Color.YELLOW), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));
			kit.setPotionEffect(new PotionEffect(PotionEffectType.SPEED, 2000000, 0));
		}
		{// Undeath King Level 1 \\
			Kit kit = new Kit(KitPvPKit.UNDEATH_KING.getName() + " 1");
			kit.setItem(0, ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.setDisplayname(new ItemStack(Material.GOLD_SWORD), "§b§lUndeath King §a§lLvL 1§8 || §bWeapon"), Enchantment.DAMAGE_ALL, 1)), 4));
			kit.setItem(1, ItemUtils.hideFlags(ItemUtils.addEnchantment(ItemUtils.setLore(ItemUtils.setDisplayname(new ItemStack(Material.ROTTEN_FLESH), "§b§lUndeath King §a§lLvL 1§8 || §dSummon the Undeath"), ItemType.UNDEATH_SUMMON_I.addEnchantment(new ArrayList<String>())), Enchantment.DURABILITY, 1), 5));
			
			kit.setHelmet(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addColor(ItemUtils.setDisplayname(new ItemStack(Material.LEATHER_HELMET), "§b§lUndeath King §a§lLvL 1§8 || §bHelmet"), Color.FUCHSIA)), 4));
			kit.setChestplate(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addColor(ItemUtils.setDisplayname(new ItemStack(Material.LEATHER_CHESTPLATE), "§b§lUndeath King §a§lLvL 1§8 || §bChestplate"), Color.FUCHSIA)), 4));
			kit.setLeggings(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addColor(ItemUtils.setDisplayname(new ItemStack(Material.LEATHER_LEGGINGS), "§b§lUndeath King §a§lLvL 1§8 || §bLeggings"), Color.FUCHSIA)), 4));
			kit.setBoots(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addColor(ItemUtils.setDisplayname(new ItemStack(Material.LEATHER_BOOTS), "§b§lUndeath King §a§lLvL 1§8 || §bBoots"), Color.FUCHSIA)), 4));
		}
		{// Engineer Level 1 \\
			Kit kit = new Kit(KitPvPKit.ENGINEER.getName() + " 1");
			kit.setItem(0, ItemUtils.hideFlags(ItemUtils.addEnchantment(ItemUtils.setLore(ItemUtils.setDisplayname(new ItemStack(Material.ENDER_PEARL), "§b§lEngineer §a§lLvL 1§8 || §c§lRed §8|| §bWeapon§f"), ItemType.PAINTBALLS_I.addEnchantment(new ArrayList<String>())), Enchantment.DURABILITY, 1), 5));
			
			kit.setHelmet(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.setDisplayname(new ItemStack(Material.LEATHER_HELMET), "§b§lEngineer §a§lLvL 1§8 || §bHelmet"), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
			kit.setChestplate(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.setDisplayname(new ItemStack(Material.LEATHER_CHESTPLATE), "§b§lEngineer §a§lLvL 1§8 || §bChestplate"), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
			kit.setLeggings(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.setDisplayname(new ItemStack(Material.LEATHER_LEGGINGS), "§b§lEngineer §a§lLvL 1§8 || §bLeggings"), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
			kit.setBoots(ItemUtils.hideFlags(ItemUtils.setUnbreakable(ItemUtils.addEnchantment(ItemUtils.setDisplayname(new ItemStack(Material.LEATHER_BOOTS), "§b§lEngineer §a§lLvL 1§8 || §bBoots"), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
		}
	}
	
	private void spawnNPCs(){
		{
			NPCArmorStand npcas = new NPCArmorStand(NPCType.SERVER_SELECTOR, new Location(getLobbyWorld(), -5.5, 74, -7.5, -45, 0));
			npcas.setGravity(false);
			npcas.setVisible(false);
			npcas.setSmall(true);
			npcas.setUseItem(true);
			npcas.setItemStack(new ItemStack(Material.ENDER_PEARL, 1));
			npcas.setItemName("§3§lServer Selector");
			npcas.spawn();
		}
		{
			NPC npc = new NPC(NPCType.KIT_SELECTOR);
			npc.newEntity(EntityType.SKELETON, new Location(getLobbyWorld(), -11.5, 74, 10.5, -135, 0), "§b§lKit Selector", false);
			npc.setSkeletonType(SkeletonType.WITHER);
			npc.setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
		}
		{
			NPC npc = new NPC(NPCType.SPECTATE);
			npc.newEntity(EntityType.SKELETON, new Location(getLobbyWorld(), 10.5, 74, -11.5, 45, 0), "§e§lSpectate", false);
			npc.setSkeletonType(SkeletonType.WITHER);
			npc.setItemInHand(new ItemStack(Material.ENDER_PEARL));
		}
		{
			NPC npc = new NPC(NPCType.OMT_SHOP);
			npc.newEntity(EntityType.BLAZE, new Location(getLobbyWorld(), -4.5, 75, -11.5, 0, 0), "§e§lOMT Shop", false);
		}
		{
			NPCArmorStand npcas = new NPCArmorStand(NPCType.NORMAL, new Location(getLobbyWorld(), -2.5, 75, 1, -135, 0));
			npcas.setBodyPose(EulerAngle.ZERO.setX(-0.1).setY(0).setZ(0));
			npcas.setHeadPose(EulerAngle.ZERO.setX(-0.15).setY(0).setZ(0));
			npcas.setLeftArmPose(EulerAngle.ZERO.setX(0).setY(0).setZ(0));
			npcas.setLeftLegPose(EulerAngle.ZERO.setX(-0.5).setY(0).setZ(0));
			npcas.setRightArmPose(EulerAngle.ZERO.setX(-0.75).setY(0).setZ(0));
			npcas.setRightLegPose(EulerAngle.ZERO.setX(0.5).setY(0).setZ(0));
			npcas.setGravity(false);
			npcas.setVisible(false);
			npcas.setItemInHand(new ItemStack(Material.IRON_SWORD, 1));
			npcas.spawn();
		}
		{
			NPCArmorStand npcas = new NPCArmorStand(NPCType.NORMAL, new Location(getLobbyWorld(), -2, 75, -2.5, -45, 0));
			npcas.setBodyPose(EulerAngle.ZERO.setX(-0.1).setY(0).setZ(0));
			npcas.setHeadPose(EulerAngle.ZERO.setX(-0.15).setY(0).setZ(0));
			npcas.setLeftArmPose(EulerAngle.ZERO.setX(0).setY(0).setZ(0));
			npcas.setLeftLegPose(EulerAngle.ZERO.setX(-0.5).setY(0).setZ(0));
			npcas.setRightArmPose(EulerAngle.ZERO.setX(-0.75).setY(0).setZ(0));
			npcas.setRightLegPose(EulerAngle.ZERO.setX(0.5).setY(0).setZ(0));
			npcas.setGravity(false);
			npcas.setVisible(false);
			npcas.setItemInHand(new ItemStack(Material.IRON_SWORD, 1));
			npcas.spawn();
		}
		{
			NPCArmorStand npcas = new NPCArmorStand(NPCType.NORMAL, new Location(getLobbyWorld(), 1.5, 75, -2, 45, 0));
			npcas.setBodyPose(EulerAngle.ZERO.setX(-0.1).setY(0).setZ(0));
			npcas.setHeadPose(EulerAngle.ZERO.setX(-0.15).setY(0).setZ(0));
			npcas.setLeftArmPose(EulerAngle.ZERO.setX(0).setY(0).setZ(0));
			npcas.setLeftLegPose(EulerAngle.ZERO.setX(-0.5).setY(0).setZ(0));
			npcas.setRightArmPose(EulerAngle.ZERO.setX(-0.75).setY(0).setZ(0));
			npcas.setRightLegPose(EulerAngle.ZERO.setX(0.5).setY(0).setZ(0));
			npcas.setGravity(false);
			npcas.setVisible(false);
			npcas.setItemInHand(new ItemStack(Material.IRON_SWORD, 1));
			npcas.spawn();
		}
		{
			NPCArmorStand npcas = new NPCArmorStand(NPCType.NORMAL, new Location(getLobbyWorld(), 1, 75, 1.5, 135, 0));
			npcas.setBodyPose(EulerAngle.ZERO.setX(-0.1).setY(0).setZ(0));
			npcas.setHeadPose(EulerAngle.ZERO.setX(-0.15).setY(0).setZ(0));
			npcas.setLeftArmPose(EulerAngle.ZERO.setX(0).setY(0).setZ(0));
			npcas.setLeftLegPose(EulerAngle.ZERO.setX(-0.5).setY(0).setZ(0));
			npcas.setRightArmPose(EulerAngle.ZERO.setX(-0.75).setY(0).setZ(0));
			npcas.setRightLegPose(EulerAngle.ZERO.setX(0.5).setY(0).setZ(0));
			npcas.setGravity(false);
			npcas.setVisible(false);
			npcas.setItemInHand(new ItemStack(Material.IRON_SWORD, 1));
			npcas.spawn();
		}
		{
			NPCArmorStand npcas = new NPCArmorStand(NPCType.NORMAL, new Location(getLobbyWorld(), -9.25, 73.5, 11.92, 90, 0));
			npcas.setBodyPose(EulerAngle.ZERO.setX(-0.1).setY(0).setZ(0));
			npcas.setHeadPose(EulerAngle.ZERO.setX(-0.15).setY(0).setZ(0));
			npcas.setLeftArmPose(EulerAngle.ZERO.setX(0).setY(0).setZ(0));
			npcas.setLeftLegPose(EulerAngle.ZERO.setX(-0.5).setY(0).setZ(0));
			npcas.setRightArmPose(EulerAngle.ZERO.setX(-2).setY(0).setZ(0.4));
			npcas.setRightLegPose(EulerAngle.ZERO.setX(0.5).setY(0).setZ(0));
			npcas.setGravity(false);
			npcas.setVisible(false);
			npcas.setItemInHand(new ItemStack(Material.DIAMOND_SWORD, 1));
			npcas.spawn();
		}
		{
			NPCArmorStand npcas = new NPCArmorStand(NPCType.NORMAL, new Location(getLobbyWorld(), -10.25, 73.1, 9.875, 90, 0));
			npcas.setBodyPose(EulerAngle.ZERO.setX(-0.1).setY(0).setZ(0));
			npcas.setHeadPose(EulerAngle.ZERO.setX(-0.15).setY(0).setZ(0));
			npcas.setLeftArmPose(EulerAngle.ZERO.setX(0).setY(0).setZ(0));
			npcas.setLeftLegPose(EulerAngle.ZERO.setX(-0.5).setY(0).setZ(0));
			npcas.setRightArmPose(EulerAngle.ZERO.setX(-3.1).setY(0).setZ(0));
			npcas.setRightLegPose(EulerAngle.ZERO.setX(0.5).setY(0).setZ(0));
			npcas.setGravity(false);
			npcas.setVisible(false);
			npcas.setItemInHand(new ItemStack(Material.GOLDEN_APPLE, 1));
			npcas.spawn();
		}
		{
			NPCArmorStand npcas = new NPCArmorStand(NPCType.NORMAL, new Location(getLobbyWorld(), -10.1, 73.15, 9.8, 60, 0));
			npcas.setBodyPose(EulerAngle.ZERO.setX(-0.1).setY(0).setZ(0));
			npcas.setHeadPose(EulerAngle.ZERO.setX(-0.15).setY(0).setZ(0));
			npcas.setLeftArmPose(EulerAngle.ZERO.setX(0).setY(0).setZ(0));
			npcas.setLeftLegPose(EulerAngle.ZERO.setX(-0.5).setY(0).setZ(0));
			npcas.setRightArmPose(EulerAngle.ZERO.setX(-3.1).setY(0).setZ(-0.25));
			npcas.setRightLegPose(EulerAngle.ZERO.setX(0.5).setY(0).setZ(0));
			npcas.setGravity(false);
			npcas.setVisible(false);
			npcas.setItemInHand(new ItemStack(Material.GOLDEN_APPLE, 1));
			npcas.spawn();
		}
		{
			NPCArmorStand npcas = new NPCArmorStand(NPCType.NORMAL, new Location(getLobbyWorld(), 9.5, 75, -12.5, 0, 0));
			npcas.setGravity(false);
			npcas.setVisible(false);
			npcas.setUseItem(true);
			npcas.setItemStack(new ItemStack(Material.ENDER_PEARL, 1));
			npcas.spawn();
		}
		{
			NPCArmorStand npcas = new NPCArmorStand(NPCType.NORMAL, new Location(getLobbyWorld(), 10.5, 74.5, -9.5, 0, 0));
			npcas.setGravity(false);
			npcas.setVisible(false);
			npcas.setUseItem(true);
			npcas.setItemStack(new ItemStack(Material.ENDER_PEARL, 1));
			npcas.spawn();
		}
		{
			NPCArmorStand npcas = new NPCArmorStand(NPCType.MASTERIES, new Location(getLobbyWorld(), 2.5, 76, 8.5, -180, 0));
			npcas.setGravity(false);
			npcas.setVisible(false);
			npcas.setSmall(true);
			npcas.setUseItem(true);
			npcas.setItemStack(new ItemStack(Material.DIAMOND_SWORD, 1));
			npcas.setItemName("§c§lMasteries");
			npcas.spawn();
		}
		{
			NPCArmorStand npcas = new NPCArmorStand(NPCType.CRATES, new Location(getLobbyWorld(), 7.5, 74, 2.5, 90, 0));
			npcas.setGravity(false);
			npcas.setVisible(false);
			npcas.setSmall(true);
			npcas.setUseItem(true);
			npcas.setItemStack(new ItemStack(Material.CHEST, 1));
			npcas.setItemName("§6§lCrates");
			npcas.spawn();
		}
		{
			Hologram h = new Hologram(new Location(getLobbyWorld(), 7.5, 74, 2.5, 90, 0));
			h.addLine("§a§oComing Soon...");
			h.create();
		}
	}
	
	private void registerLobbyKit(){
		Kit kit = new Kit("Lobby");
		{
			ItemStack item = new ItemStack(Material.WRITTEN_BOOK);
			BookMeta itemmeta = (BookMeta) item.getItemMeta();
			itemmeta.setDisplayName("§c§nBook of Enchantments");
			itemmeta.addPage("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28");
			itemmeta.setPage(1, 
			"\n\n          §0§lBook\n"
			+ "         §0§lof \n"
			+ "   §0§lEnchantments");
			itemmeta.setPage(2, 
			"§0§lLightning §0§o(Sword)\n"
			+ " §0§l- Lightning I\n"
			+ "  §0§oOn Hit, 25% chance for a lightning to strike on your opponent.\n"
			+ " §0§l- Lightning II\n"
			+ "  §0§oOn Hit, 33% chance for a lightning to strike on your opponent.\n");
			itemmeta.setPage(3, 
			"§0§lBlindness\n"
			+ " §0§l- Blindness I\n"
			+ "  §0§oOn Hit, giving 2 seconds of Blindness to your opponent.\n"
			+ " §0§l- Blindness II\n"
			+ "  §0§oOn Hit, giving 3 seconds of Blindness to your opponent.\n");
			itemmeta.setPage(4, 
			"§0§lHealing\n"
			+ " §0§l- Healing I\n"
			+ "  §0§oSHIFT + Right Click, giving 4 seconds of Regeneration III to yourself.\n"
			+ " §0§l- Healing II\n"
			+ "  §0§oSHIFT + Right Click, giving 5 seconds of Regeneration III to yourself.\n");
			itemmeta.setPage(5, 
			"§0§lVampire\n"
			+ " §0§l- Vampire I\n"
			+ "  §0§oOn Hit, restoring 1.5 hearts to your health.\n");
			itemmeta.setPage(6, 
			"§0§lMagic\n"
			+ " §0§l- Magic I\n"
			+ "  §0§oOn Hit, giving 4 second of Wither I to your opponent.\n");
			itemmeta.setPage(7, 
			"§0§lKnockup\n"
			+ " §0§l- Knockup I\n"
			+ "  §0§oOn Hit, 50% chance to knockup your opponent in the air.\n");
			itemmeta.setPage(8, 
			"§0§lLightning §0§o(Bow)\n"
			+ " §0§l- Lightning I\n"
			+ "  §0§oOn Hit, striking a lightning on your opponent.\n");
			itemmeta.setPage(9, 
			"§0§lUndeath\n"
			+ " §0§l- Undeath I\n"
			+ "  §0§oOn Hit, spawning 3 Undeath Knights that will attack your opponent.\n"
			+ " §0§l- Undeath II\n"
			+ "  §0§oOn Hit, spawning 3 Undeath Knights and 1 Undeath Archer that will attack your opponent.\n");
			itemmeta.setPage(10, 
			"§0§lExplosive\n"
			+ " §0§l- Explosive I\n"
			+ "  §0§oOn Hit, spawning a TNT at your opponents position.\n");
			itemmeta.setPage(11, 
			"§0§lArrow Split\n"
			+ " §0§l- Arrow Split I\n"
			+ "  §0§oWhen releasing an arrow, 4 more will spawn at its side.\n");
			itemmeta.setPage(12, 
			"§0§lWither Armor\n"
			+ " §0§l- Wither Armor I\n"
			+ "  §0§oWhen getting hit, 5 seconds of Wither II to your opponent.\n");
			itemmeta.setPage(13, 
			"§0§lMolten Armor\n"
			+ " §0§l- Molten Armor I\n"
			+ "  §0§oWhen getting hit, giving 5 seconds of Blindness and 5 seconds of Slowness VI to your opponent.\n");
			itemmeta.setPage(14, 
			"§0§lFire Trail\n"
			+ " §0§l- Fire Trail I\n"
			+ "  §0§oWhen walking, a path of fire will appear behind you.\n");
			itemmeta.setPage(15, 
			"§0§lLight\n"
			+ " §0§l- Light I\n"
			+ "  §0§oWhen walking over water, replacing nearby blocks with Cobblestone.\n");
			itemmeta.setPage(16, 
			"§0§lFly\n"
			+ " §0§l- Fly I\n"
			+ "  §0§oSHIFT, ability to fly!\n");
			itemmeta.setPage(17, 
			"§0§lArthropods\n"
			+ " §0§l- Arthropods I\n"
			+ "  §0§oOn Hit, 16.67% chance for a Spider to spawn on your opponent.\n"
			+ " §0§l- Arthropods II\n"
			+ "  §0§oOn Hit, 20% chance for a Spider Jockey to spawn on your opponent.\n");
			itemmeta.setPage(18,
			" §0§l- Arthropods III\n"
			+ "  §0§oOn Hit, 25% chance for a Spider and a Spider Jockey to spawn on your opponent.\n");
			itemmeta.setPage(19, 
			"§0§lWither\n"
			+ " §0§l- Wither I\n"
			+ "  §0§oRight Click, shooting 4 Wither Skulls from your position. Price: 1 Soul. Killing an opponent will give you a Soul.\n");
			itemmeta.setPage(20,
			"§0§lBarrier\n"
			+ " §0§l- Barrier I\n"
			+ "  §0§oRight Click, spawning a force shield around you, giving 5 seconds of Resistance.\n"
			+ " §0§l- Barrier II\n"
			+ "  §0§oRight Click, spawning a force shield around you, giving 5 seconds of Resistance II.\n");
			itemmeta.setPage(21,
			"§0§lTNT\n"
			+ " §0§l- TNT I\n"
			+ "  §0§oRight Click, shooting a TNT where you're looking at.\n");
			itemmeta.setPage(22,
			"§0§lFish Attack\n"
			+ " §0§l- Fish Attack I\n"
			+ "  §0§oRight Click, giving 4 seconds of Poison III to all nearby opponents.\n");
			itemmeta.setPage(23,
			"§0§lShield\n"
			+ " §0§l- Shield I\n"
			+ "  §0§oRight Click, giving 10 seconds of Resistance to yourself.\n"
			+ " §0§l- Shield II\n"
			+ "  §0§oRight Click, giving 12 seconds of Resistance II to yourself.\n");
			itemmeta.setPage(24,
			"§0§lTrade\n"
			+ " §0§l- Trade I\n"
			+ "  §0§oOn Hit, removing this item and stealing your opponents weapon.\n");
			itemmeta.setPage(25,
			"§0§lHealing Kit\n"
			+ " §0§l- Healing Kit I\n"
			+ "  §0§oRight Click, removing this item and restoring you to full health.\n");
			itemmeta.setPage(26,
			"§0§lBlock Explosion\n"
			+ " §0§l- Block Explosion I\n"
			+ "  §0§oRight Click, creating an explosion of blocks which will blind and slow nearby opponents.\n");
			itemmeta.setPage(27,
			"§0§lUndeath Summon\n"
			+ " §0§l- Undeath Summon I\n"
			+ "  §0§oRight Click, spawning two baby pigmans that will kill for you!\n");
			itemmeta.setPage(28,
			"§0§lPaintballs\n"
			+ " §0§l- Paintballs I\n"
			+ "  §0§oRight Click, creating an area which will give an effect specified to its color.\n  §0§oLeft Click, change color.\n");
			itemmeta.setAuthor("§6§lOrbitMines§c§lKitPvP");
			item.setItemMeta(itemmeta);

			kit.setItem(0, item);
		}
		kit.setItem(1, ItemUtils.setDisplayname(new ItemStack(Material.EXP_BOTTLE), "§d§nAchievements"));
		kit.setItem(4, ItemUtils.setDisplayname(new ItemStack(Material.ENDER_PEARL), "§3§nServer Selector"));
		kit.setItem(5, ItemUtils.setDisplayname(new ItemStack(Material.GOLD_NUGGET), "§a§nBoosters"));
		kit.setItem(7, ItemUtils.setDisplayname(new ItemStack(Material.ENDER_CHEST), "§9§nCosmetic Perks"));
		kit.setItem(8, ItemUtils.setDisplayname(new ItemStack(Material.DIAMOND_CHESTPLATE), "§b§nKit Selector"));
		
		kit.setPotionEffect(new PotionEffect(PotionEffectType.SPEED, 100000, 0));
	}

	private void registerSpectatorKit(){
		Kit kit = new Kit("Spectator");
		kit.setItem(3, ItemUtils.setDisplayname(new ItemStack(Material.ENDER_PEARL), "§3§nBack to the Lobby"));
		kit.setItem(5, ItemUtils.setDisplayname(new ItemStack(Material.NAME_TAG), "§e§nTeleporter"));
	}
	
	private void registerRunnables(){
		new BoosterRunnable(Duration.ONE_SECOND);
		new FreeKitRunnable(Duration.ONE_HOUR);
		new KitPvPNPCRunnable(Duration.TENTH_SECOND);
		new KitPvPPlayerRunnable(Duration.ONE_SECOND);
		new MapRunnable(Duration.ONE_SECOND);
		new MasteryNPCRunnable(Duration.FIVE_SECONDS);
	}
	
    private void registerCommands(){
    	new FreeKitCommand();
    	new KitCommand();
    }
	
	private void registerEvents(){
		getServer().getPluginManager().registerEvents(new BreakEvent(), this);
		getServer().getPluginManager().registerEvents(new ClickEvent(), this);
		getServer().getPluginManager().registerEvents(new CommandPreprocessEvent(), this);
		getServer().getPluginManager().registerEvents(new DamageEvent(), this);
		getServer().getPluginManager().registerEvents(new EntityDamage(), this);
		getServer().getPluginManager().registerEvents(new EntityDeath(), this);
		getServer().getPluginManager().registerEvents(new EntityInteractEvent(), this);
		getServer().getPluginManager().registerEvents(new InteractAtEntityEvent(), this);
		getServer().getPluginManager().registerEvents(new JoinEvent(), this);
		getServer().getPluginManager().registerEvents(new PlaceEvent(), this);
		getServer().getPluginManager().registerEvents(new PlayerChat(), this);
		getServer().getPluginManager().registerEvents(new PlayerInteract(), this);
		getServer().getPluginManager().registerEvents(new PlayerMove(), this);
		getServer().getPluginManager().registerEvents(new SignEvent(), this);
		getServer().getPluginManager().registerEvents(new VoteEvent(), this);
		getServer().getPluginManager().registerEvents(new WorldChangeEvent(), this);
		getServer().getPluginManager().registerEvents(new BlockChangeEvent(), this);
		getServer().getPluginManager().registerEvents(new DropEvent(), this);
		getServer().getPluginManager().registerEvents(new ExplodeEvent(), this);
		getServer().getPluginManager().registerEvents(new FoodEvent(), this);
		getServer().getPluginManager().registerEvents(new PickupEvent(), this);
		getServer().getPluginManager().registerEvents(new ProjectileHit(), this);
		getServer().getPluginManager().registerEvents(new ExpChangeEvent(), this);
		getServer().getPluginManager().registerEvents(new FadeEvent(), this);
		getServer().getPluginManager().registerEvents(new ShootBowEvent(), this);
		getServer().getPluginManager().registerEvents(new TeleportEvent(), this);
	}
}
