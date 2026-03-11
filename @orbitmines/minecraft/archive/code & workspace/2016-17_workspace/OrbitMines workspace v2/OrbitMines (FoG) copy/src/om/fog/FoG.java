package om.fog;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import om.api.API;
import om.api.handlers.Hologram;
import om.api.handlers.NPC;
import om.api.handlers.NPCArmorStand;
import om.api.runnables.OMRunnable.Duration;
import om.api.utils.ItemUtils;
import om.api.utils.WorldUtils;
import om.api.utils.enums.NPCType;
import om.fog.cmd.GiveEnchCommand;
import om.fog.events.BreakEvent;
import om.fog.events.ClickEvent;
import om.fog.events.CloseEvent;
import om.fog.events.CommandPreprocessEvent;
import om.fog.events.DamageEvent;
import om.fog.events.DropEvent;
import om.fog.events.EntityDamage;
import om.fog.events.EntityDeath;
import om.fog.events.EntityInteractEvent;
import om.fog.events.ExpChangeEvent;
import om.fog.events.FoodEvent;
import om.fog.events.InteractAtEntityEvent;
import om.fog.events.JoinEvent;
import om.fog.events.PlaceEvent;
import om.fog.events.PlayerChat;
import om.fog.events.PlayerInteract;
import om.fog.events.PlayerMove;
import om.fog.events.QuitEvent;
import om.fog.events.SignEvent;
import om.fog.events.TargetEvent;
import om.fog.events.UnloadEvent;
import om.fog.handlers.FoGScoreBoard;
import om.fog.handlers.Quest;
import om.fog.handlers.map.FoGMap;
import om.fog.handlers.map.OreBlock;
import om.fog.handlers.map.SaveZone;
import om.fog.handlers.map.SpawnArea;
import om.fog.handlers.players.FoGPlayer;
import om.fog.handlers.quests.MobKillQuest;
import om.fog.handlers.quests.OreCollectQuest;
import om.fog.handlers.quests.OreMineQuest;
import om.fog.nms.zombie.Zombie;
import om.fog.nms.zombiearcher.ZombieArcher;
import om.fog.runnables.FoGPlayerRunnable;
import om.fog.utils.enums.Faction;
import om.fog.utils.enums.Mob;
import om.fog.utils.enums.Mob.MobLevel;
import om.fog.utils.enums.Ore;
import om.fog.utils.enums.Rarity;
import om.fog.utils.enums.Repeat;
import om.fog.utils.enums.Suit;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton.SkeletonType;
import org.bukkit.entity.Villager.Profession;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class FoG extends JavaPlugin {

	private API api;
	private static FoG fog;

	private World lobbyWorld;
	private World galaxyWorld;
	private Location spawn;
	private Map<Player, FoGPlayer> players;
	private List<FoGPlayer> fogPlayers;
	private List<FoGMap> maps;
	private List<SpawnArea> spawnAreas;
	private List<Quest> quests;
	
	public void onEnable() {
		api = API.getInstance();
		fog = this;

		WorldUtils.removeAllEntitiesIF();
		api.setSpawnBuilders("§b§lMod §brienk222");
		api.setScoreboardManager(new FoGScoreBoard());
		lobbyWorld = Bukkit.getWorld("FoGLobby");
		galaxyWorld = Bukkit.getWorld("FoGGalaxy");
		spawn = new Location(getLobbyWorld(), 0.5, 74, 0.5, 140, 0);
		players = new HashMap<Player, FoGPlayer>();
		fogPlayers = new ArrayList<FoGPlayer>();

		registerNMS();
		registerCommands();
		registerEvents();
		registerRunnables();
		registerMaps();
		registerQuests();
		spawnNPCs();
	}
	
	public void onDisable(){
		WorldUtils.removeAllEntitiesIF();
		
		for(FoGPlayer fog : getFoGPlayers()){
			fog.setInCombat(false);
		}
		
		for(SpawnArea area : getSpawnAreas()){
			for(Ore ore : Ore.values()){
				if(area.getOreBlocks(ore) != null){
					List<OreBlock> newList = new ArrayList<OreBlock>();
					newList.addAll(area.getOreBlocks(ore));
					
					for(OreBlock b : newList){
						b.despawn();
					}
				}
			}
		}
	}
	
	public API getAPI() {
		return api;
	}
	public static FoG getInstance() {
		return fog;
	}
	
	public World getLobbyWorld() {
		return lobbyWorld;
	}
	public World getGalaxyWorld() {
		return galaxyWorld;
	}
	public Location getSpawn() {
		return spawn;
	}
	public Map<Player, FoGPlayer> getPlayers() {
		return players;
	}
	public List<FoGPlayer> getFoGPlayers() {
		return fogPlayers;
	}
	
	public List<FoGMap> getMaps() {
		return maps;
	}
	
	public List<SpawnArea> getSpawnAreas() {
		return spawnAreas;
	}
	
	public List<Quest> getQuests() {
		return quests;
	}
	
	private void registerEvents(){
		getServer().getPluginManager().registerEvents(new BreakEvent(), this);
		getServer().getPluginManager().registerEvents(new ClickEvent(), this);
		getServer().getPluginManager().registerEvents(new CloseEvent(), this);
		getServer().getPluginManager().registerEvents(new CommandPreprocessEvent(), this);
		getServer().getPluginManager().registerEvents(new DamageEvent(), this);
		getServer().getPluginManager().registerEvents(new DropEvent(), this);
		getServer().getPluginManager().registerEvents(new EntityDamage(), this);
		getServer().getPluginManager().registerEvents(new EntityDeath(), this);
		getServer().getPluginManager().registerEvents(new EntityInteractEvent(), this);
		getServer().getPluginManager().registerEvents(new ExpChangeEvent(), this);
		getServer().getPluginManager().registerEvents(new FoodEvent(), this);
		getServer().getPluginManager().registerEvents(new InteractAtEntityEvent(), this);
		getServer().getPluginManager().registerEvents(new JoinEvent(), this);
		getServer().getPluginManager().registerEvents(new PlaceEvent(), this);
		getServer().getPluginManager().registerEvents(new PlayerChat(), this);
		getServer().getPluginManager().registerEvents(new PlayerInteract(), this);
		getServer().getPluginManager().registerEvents(new PlayerMove(), this);
		getServer().getPluginManager().registerEvents(new QuitEvent(), this);
		getServer().getPluginManager().registerEvents(new SignEvent(), this);
		getServer().getPluginManager().registerEvents(new TargetEvent(), this);
		getServer().getPluginManager().registerEvents(new UnloadEvent(), this);
	}
    
    private void registerCommands(){
    	new GiveEnchCommand();
    }
	
	private void registerRunnables(){
		new FoGPlayerRunnable(Duration.ONE_SECOND);
	}
	
	private void spawnNPCs(){
		{
			NPCArmorStand npcas = new NPCArmorStand(NPCType.SERVER_SELECTOR, new Location(getLobbyWorld(), 2, 74, 2, 135, 0));
			npcas.setGravity(false);
			npcas.setVisible(false);
			npcas.setSmall(true);
			npcas.setUseItem(true);
			npcas.setItemStack(new ItemStack(Material.ENDER_PEARL, 1));
			npcas.setItemName("§3§lServer Selector");
			npcas.spawn();
		}
		{
			NPC npc = new NPC(NPCType.FACTION_SELECT);
			npc.newEntity(EntityType.VILLAGER, new Location(getLobbyWorld(), -11.5, 74, -14.5, -45, 0), "§e§lChoose a Faction", false);
			npc.setVillagerProfession(Profession.BLACKSMITH);
		}
		{
			NPC npc = new NPC(NPCType.FOG_SHOP);
			npc.newEntity(EntityType.VILLAGER, new Location(getGalaxyWorld(), -3322.5, 8, 1863.5, -90, 0), "§e§lShop", false);
			npc.setVillagerProfession(Profession.PRIEST);
		}
		{
			NPC npc = new NPC(NPCType.BANK);
			npc.newEntity(EntityType.VILLAGER, new Location(getGalaxyWorld(), -3284, 8, 1871, 90, 0), "§e§lBank", false);
			npc.setVillagerProfession(Profession.LIBRARIAN);
		}
		{
			NPCArmorStand npcas = new NPCArmorStand(NPCType.TRAINING_SUIT, new Location(getGalaxyWorld(), -3309, 11, 1899.5, 90, 0));
			npcas.setGravity(false);
			npcas.setVisible(false);
			npcas.setHelmet(ItemUtils.addColor(new ItemStack(Material.LEATHER_HELMET), Color.YELLOW));
			npcas.setChestplate(ItemUtils.addColor(new ItemStack(Material.LEATHER_CHESTPLATE), Color.YELLOW));
			npcas.setLeggings(ItemUtils.addColor(new ItemStack(Material.LEATHER_LEGGINGS), Color.YELLOW));
			npcas.setBoots(ItemUtils.addColor(new ItemStack(Material.LEATHER_BOOTS), Color.YELLOW));
			npcas.setCustomName(Faction.ALPHA.getColor() + Suit.TRAINING_SUIT.getName(Faction.ALPHA));
			npcas.setCustomNameVisible(true);
			npcas.spawn();
			
			Faction.ALPHA.getSuitNPCs().put(Suit.TRAINING_SUIT, npcas);
		}
		{
			NPC npc = new NPC(NPCType.FOG_SHOP);
			npc.newEntity(EntityType.VILLAGER, new Location(getGalaxyWorld(), 261.5, 15, 3807.5, 90, 0), "§e§lShop", false);
			npc.setVillagerProfession(Profession.PRIEST);
		}
		{
			NPC npc = new NPC(NPCType.BANK);
			npc.newEntity(EntityType.VILLAGER, new Location(getGalaxyWorld(), 241.5, 11, 3825, 180, 0), "§e§lBank", false);
			npc.setVillagerProfession(Profession.LIBRARIAN);
		}
		{
			NPCArmorStand npcas = new NPCArmorStand(NPCType.TRAINING_SUIT, new Location(getGalaxyWorld(), 268.5, 20, 3842.5, 90, 0));
			npcas.setGravity(false);
			npcas.setVisible(false);
			npcas.setHelmet(ItemUtils.addColor(new ItemStack(Material.LEATHER_HELMET), Color.BLUE));
			npcas.setChestplate(ItemUtils.addColor(new ItemStack(Material.LEATHER_CHESTPLATE), Color.BLUE));
			npcas.setLeggings(ItemUtils.addColor(new ItemStack(Material.LEATHER_LEGGINGS), Color.BLUE));
			npcas.setBoots(ItemUtils.addColor(new ItemStack(Material.LEATHER_BOOTS), Color.BLUE));
			npcas.setCustomName(Faction.BETA.getColor() + Suit.TRAINING_SUIT.getName(Faction.BETA));
			npcas.setCustomNameVisible(true);
			npcas.spawn();
			
			Faction.BETA.getSuitNPCs().put(Suit.TRAINING_SUIT, npcas);
		}
		{
			NPC npc = new NPC(NPCType.FOG_SHOP);
			npc.newEntity(EntityType.SKELETON, new Location(getGalaxyWorld(), 3377.5, 8, 1844, 90, 0), "§e§lShop", false);
			npc.setSkeletonType(SkeletonType.WITHER);
		}
		{
			NPC npc = new NPC(NPCType.BANK);
			npc.newEntity(EntityType.SKELETON, new Location(getGalaxyWorld(), 3348.5, 8, 1866.5, 180, 0), "§e§lBank", false);
			npc.setSkeletonType(SkeletonType.WITHER);
		}
		{
			NPCArmorStand npcas = new NPCArmorStand(NPCType.TRAINING_SUIT, new Location(getGalaxyWorld(), 3376.5, 8, 1881.5, 90, 0));
			npcas.setGravity(false);
			npcas.setVisible(false);
			npcas.setHelmet(ItemUtils.addColor(new ItemStack(Material.LEATHER_HELMET), Color.RED));
			npcas.setChestplate(ItemUtils.addColor(new ItemStack(Material.LEATHER_CHESTPLATE), Color.RED));
			npcas.setLeggings(ItemUtils.addColor(new ItemStack(Material.LEATHER_LEGGINGS), Color.RED));
			npcas.setBoots(ItemUtils.addColor(new ItemStack(Material.LEATHER_BOOTS), Color.RED));
			npcas.setCustomName(Faction.OMEGA.getColor() + Suit.TRAINING_SUIT.getName(Faction.OMEGA));
			npcas.setCustomNameVisible(true);
			npcas.spawn();
			
			Faction.OMEGA.getSuitNPCs().put(Suit.TRAINING_SUIT, npcas);
		}
		{
			Hologram h = new Hologram(new Location(getLobbyWorld(), -3.5, 74, -3.5, -45, 0));
			h.addLine("§7Welcome to §e§lFractals of the Galaxy");
			h.addLine("§7Start your adventure here!");
			h.create();
		}
		{
			Hologram h = new Hologram(new Location(getGalaxyWorld(), -3307.5, 10, 1876.5, 0, 0));
			h.addLine("§7Welcome to " + Faction.ALPHA.getName() + "§7!");
			h.addLine("§7Total population: " + Faction.ALPHA.getColor() + Faction.ALPHA.getPlayerAmount() + "§7.");
			h.create();
			
			Faction.ALPHA.setPopulationHologram(h);
		}
		{
			Hologram h = new Hologram(new Location(getGalaxyWorld(), 241.5, 18, 3824.5, -33, 0));
			h.addLine("§7Welcome to " + Faction.BETA.getName() + "§7!");
			h.addLine("§7Total population: " + Faction.BETA.getColor() + Faction.BETA.getPlayerAmount() + "§7.");
			h.create();
			
			Faction.BETA.setPopulationHologram(h);
		}
		{
			Hologram h = new Hologram(new Location(getGalaxyWorld(), 3378.5, 13, 1859.5, -33, 0));
			h.addLine("§7Welcome to " + Faction.OMEGA.getName() + "§7!");
			h.addLine("§7Total population: " + Faction.OMEGA.getColor() + Faction.OMEGA.getPlayerAmount() + "§7.");
			h.create();
			
			Faction.OMEGA.setPopulationHologram(h);
		}
		/*{
		NPCArmorStand npcas = new NPCArmorStand(NPCType.DEFENDER_SUIT, new Location(getGalaxyWorld(), 248.5, 20, 3842.5, -90, 0));
		npcas.setGravity(false);
		npcas.setVisible(false);
		npcas.setHelmet(ItemUtils.addColor(new ItemStack(Material.LEATHER_HELMET), Color.BLUE));
		npcas.setChestplate(new ItemStack(Material.GOLD_CHESTPLATE));
		npcas.setLeggings(new ItemStack(Material.GOLD_LEGGINGS));
		npcas.setBoots(ItemUtils.addColor(new ItemStack(Material.LEATHER_BOOTS), Color.BLUE));
		npcas.setCustomName(Faction.BETA.getColor() + Suit.DEFENDER_SUIT.getName(Faction.BETA));
		npcas.setCustomNameVisible(true);
		npcas.spawn();
		}*/
		//{
		//	NPC npc = new NPC(NPCType.OMT_SHOP);
		//	npc.newEntity(EntityType.BLAZE, new Location(getLobbyWorld(), -4.5, 75, -11.5, 0, 0), "§e§lOMT Shop", false);
		//}
	}
	
	private void registerMaps(){
		this.maps = new ArrayList<FoGMap>();
		this.spawnAreas = new ArrayList<SpawnArea>();
		{
			FoGMap map = new FoGMap(Faction.ALPHA, "A-1", -3500, 1500, null);
			
			{
				new SaveZone(map, -3272, 1834, -3373, 1943);
			}
			
			{
				SpawnArea sa = new SpawnArea(map, -3146, 1814, -3259, 1872);
				sa.addMob(Mob.ZOMBIE, 1, 50, 1);
				sa.addOre(Ore.COPPER, 5, 30);
				this.spawnAreas.add(sa);
			}
			{
				SpawnArea sa = new SpawnArea(map, -3324, 1778, -3233, 1670);
				sa.addMob(Mob.ZOMBIE, 2, 30, 1);
				sa.addMob(Mob.ZOMBIE_ARCHER, 1, 10, 3);
				sa.addOre(Ore.COPPER, 5, 14);
				sa.addOre(Ore.COBALT, 5, 17);
				this.spawnAreas.add(sa);
			}
			{
				SpawnArea sa = new SpawnArea(map, -3243, 1636, -3297, 1602);
				sa.addMob(Mob.ZOMBIE, 2, 13, 1);
				sa.addMob(Mob.ZOMBIE_ARCHER, 1, 7, 3);
				this.spawnAreas.add(sa);
			}
			{
				SpawnArea sa = new SpawnArea(map, -3137, 1635, -3217, 1758);
				sa.addMob(Mob.ZOMBIE, 2, 20, 1);
				sa.addMob(Mob.ZOMBIE_ARCHER, 2, 20, 3);
				sa.addOre(Ore.COBALT, 10, 15);
				this.spawnAreas.add(sa);
			}
		}
		{
			FoGMap map = new FoGMap(Faction.BETA, "B-1", 0, 3500, null);
			
			{
				new SaveZone(map, 233, 3797, 271, 3879);
			}
			
			{
				SpawnArea sa = new SpawnArea(map, 216, 3813, 123, 3793);
				sa.addMob(Mob.ZOMBIE, 1, 15, 1);
				sa.addOre(Ore.COPPER, 5, 15);
				this.spawnAreas.add(sa);
			}
			{
				SpawnArea sa = new SpawnArea(map, 252, 3792, 123, 3725);
				sa.addMob(Mob.ZOMBIE, 1, 40, 1);
				sa.addOre(Ore.COPPER, 10, 20);
				this.spawnAreas.add(sa);
			}
			{
				SpawnArea sa = new SpawnArea(map, 252, 3724, 123, 3657);
				sa.addMob(Mob.ZOMBIE, 2, 30, 1);
				sa.addMob(Mob.ZOMBIE_ARCHER, 1, 10, 3);
				sa.addOre(Ore.COPPER, 5, 14);
				sa.addOre(Ore.COBALT, 5, 17);
				this.spawnAreas.add(sa);
			}
			{
				SpawnArea sa = new SpawnArea(map, 100, 3806, 118, 3846);
				sa.addMob(Mob.ZOMBIE, 2, 13, 1);
				sa.addMob(Mob.ZOMBIE_ARCHER, 1, 7, 3);
				this.spawnAreas.add(sa);
			}
			{
				SpawnArea sa = new SpawnArea(map, 252, 3656, 123, 3590);
				sa.addMob(Mob.ZOMBIE, 2, 20, 1);
				sa.addMob(Mob.ZOMBIE_ARCHER, 2, 20, 3);
				sa.addOre(Ore.COBALT, 10, 15);
				this.spawnAreas.add(sa);
			}
		}
		{
			FoGMap map = new FoGMap(Faction.OMEGA, "O-1", 3000, 1500, null);
			
			{
				new SaveZone(map, 3341, 1830, 3414, 1912);
			}
			
			{
				SpawnArea sa = new SpawnArea(map, 3250, 1830, 3338, 1755);
				sa.addMob(Mob.ZOMBIE, 1, 45, 1);
				sa.addOre(Ore.COPPER, 5, 30);
				this.spawnAreas.add(sa);
			}
			{
				SpawnArea sa = new SpawnArea(map, 3255, 1737, 3207, 1670);
				sa.addMob(Mob.ZOMBIE, 2, 30, 1);
				sa.addMob(Mob.ZOMBIE_ARCHER, 1, 10, 3);
				sa.addOre(Ore.COPPER, 5, 14);
				sa.addOre(Ore.COBALT, 5, 17);
				this.spawnAreas.add(sa);
			}
			{
				SpawnArea sa = new SpawnArea(map, 3194, 1850, 3207, 1867);
				sa.addMob(Mob.ZOMBIE, 2, 13, 1);
				sa.addMob(Mob.ZOMBIE_ARCHER, 1, 7, 3);
				this.spawnAreas.add(sa);
			}
			{
				SpawnArea sa = new SpawnArea(map, 3239, 1640, 3339, 1678);
				sa.addMob(Mob.ZOMBIE, 2, 20, 1);
				sa.addMob(Mob.ZOMBIE_ARCHER, 2, 20, 3);
				sa.addOre(Ore.COBALT, 10, 15);
				this.spawnAreas.add(sa);
			}
		}
	}
	
	private void registerQuests(){
		this.quests = new ArrayList<Quest>();
		
		// id, name, level, exp, repeat
		new MobKillQuest(0, "First Training", 0, 50, Repeat.NEVER, true) {
			
			@Override
			public void giveReward(FoGPlayer omp) {
				omp.addSilver(150);
				omp.getPlayer().sendMessage("§7You have received §7§l150 Silver§7.");
			}
			
			@Override
			public MobLevel getMob() {
				return new MobLevel(Mob.ZOMBIE, 1);
			}
			
			@Override
			public int getAmount() {
				return 3;
			}

			@Override
			public List<String> getDiscription(FoGPlayer omp) {
				return Arrays.asList(" §7Kill §f3 Zombies (LvL 1)§7. ");
			}

			@Override
			public List<String> getReward() {
				return Arrays.asList(" §7Reward: §7§l150 Silver ");
			}
		};
		
		new OreMineQuest(1, "Copper Mining", 0, 50, Repeat.NEVER, true) {
			
			@Override
			public void giveReward(FoGPlayer omp) {
				omp.addSilver(250);
				omp.getPlayer().sendMessage("§7You have received §7§l250 Silver§7.");
			}

			@Override
			public Ore getOre() {
				return Ore.COPPER;
			}
			
			@Override
			public int getAmount() {
				return 10;
			}

			@Override
			public List<String> getDiscription(FoGPlayer omp) {
				return Arrays.asList(" §7Mine §f10 Copper Ore§7. ");
			}

			@Override
			public List<String> getReward() {
				return Arrays.asList(" §7Reward: §7§l250 Silver ");
			}
		};
		
		new OreCollectQuest(2, "Collectables (1)", 0, 75, Repeat.DAILY, false) {
			
			@Override
			public void giveReward(FoGPlayer omp) {
				omp.addSilver(575);
				omp.getPlayer().sendMessage("§7You have received §7§l575 Silver§7.");
			}

			@Override
			public Ore getOre() {
				return Ore.COPPER;
			}
			
			@Override
			public int getAmount() {
				return 40;
			}

			@Override
			public List<String> getDiscription(FoGPlayer omp) {
				return Arrays.asList(" §7Collect §f40 Copper§7. ");
			}

			@Override
			public List<String> getReward() {
				return Arrays.asList(" §7Reward: §7§l575 Silver ");
			}
		};
		
		new MobKillQuest(3, "Zombies (1)", 0, 75, Repeat.NEVER, true) {
			
			@Override
			public void giveReward(FoGPlayer omp) {
				omp.addSilver(750);
				omp.getPlayer().sendMessage("§7You have received §7§l750 Silver§7.");
			}
			
			@Override
			public MobLevel getMob() {
				return new MobLevel(Mob.ZOMBIE, 1);
			}
			
			@Override
			public int getAmount() {
				return 50;
			}

			@Override
			public List<String> getDiscription(FoGPlayer omp) {
				return Arrays.asList(" §7Kill §f50 Zombies (LvL 1)§7. ");
			}

			@Override
			public List<String> getReward() {
				return Arrays.asList(" §7Reward: §7§l750 Silver ");
			}
		};
		
		new MobKillQuest(4, "Zombies (2)", 1, 75, Repeat.NEVER, true) {
			
			@Override
			public void giveReward(FoGPlayer omp) {
				omp.addSilver(1000);
				omp.getPlayer().sendMessage("§7You have received §7§l1000 Silver§7.");
			}
			
			@Override
			public MobLevel getMob() {
				return new MobLevel(Mob.ZOMBIE, 1);
			}
			
			@Override
			public int getAmount() {
				return 100;
			}

			@Override
			public List<String> getDiscription(FoGPlayer omp) {
				return Arrays.asList(" §7Kill §f100 Zombies (LvL 1)§7. ");
			}

			@Override
			public List<String> getReward() {
				return Arrays.asList(" §7Reward: §7§l1000 Silver ");
			}
		};
		
		new OreCollectQuest(5, "Collectables (2)", 1, 75, Repeat.DAILY, false) {
			
			@Override
			public void giveReward(FoGPlayer omp) {
				omp.addSilver(1250);
				omp.getPlayer().sendMessage("§7You have received §7§l1250 Silver§7.");
			}

			@Override
			public Ore getOre() {
				return Ore.COBALT;
			}
			
			@Override
			public int getAmount() {
				return 60;
			}

			@Override
			public List<String> getDiscription(FoGPlayer omp) {
				return Arrays.asList(" §7Collect §f60 Cobalt§7. ");
			}

			@Override
			public List<String> getReward() {
				return Arrays.asList(" §7Reward: §7§l1250 Silver ");
			}
		};
		
		new MobKillQuest(6, "Zombies (3)", 1, 75, Repeat.NEVER, true) {
			
			@Override
			public void giveReward(FoGPlayer omp) {
				omp.getPlayer().getInventory().addItem(Rarity.COMMON.getRandomItem());
				omp.getPlayer().sendMessage("§7You have received a §aCommon Enchantment§7.");
			}
			
			@Override
			public MobLevel getMob() {
				return new MobLevel(Mob.ZOMBIE, 2);
			}
			
			@Override
			public int getAmount() {
				return 40;
			}

			@Override
			public List<String> getDiscription(FoGPlayer omp) {
				return Arrays.asList(" §7Kill §f40 Zombies (LvL 2)§7. ");
			}

			@Override
			public List<String> getReward() {
				return Arrays.asList(" §7Reward: §a1 Common Enchantment ");
			}
		};
		
		new OreMineQuest(7, "Cobalt Mining (1)", 2, 100, Repeat.NEVER, true) {
			
			@Override
			public void giveReward(FoGPlayer omp) {
				omp.addSilver(700);
				omp.getPlayer().sendMessage("§7You have received §7§l700 Silver§7.");
			}

			@Override
			public Ore getOre() {
				return Ore.COBALT;
			}
			
			@Override
			public int getAmount() {
				return 30;
			}

			@Override
			public List<String> getDiscription(FoGPlayer omp) {
				return Arrays.asList(" §7Mine §f30 Cobalt Ore§7. ");
			}

			@Override
			public List<String> getReward() {
				return Arrays.asList(" §7Reward: §7§l700 Silver ");
			}
		};
		
		new MobKillQuest(8, "Zombies (4)", 3, 75, Repeat.NEVER, true) {
			
			@Override
			public void giveReward(FoGPlayer omp) {
				omp.getPlayer().getInventory().addItem(Rarity.UNCOMMON.getRandomItem());
				omp.getPlayer().sendMessage("§7You have received a §bUncommon Enchantment§7.");
			}
			
			@Override
			public MobLevel getMob() {
				return new MobLevel(Mob.ZOMBIE_ARCHER, 1);
			}
			
			@Override
			public int getAmount() {
				return 60;
			}

			@Override
			public List<String> getDiscription(FoGPlayer omp) {
				return Arrays.asList(" §7Kill §f60 Archer Zombies (LvL 1)§7. ");
			}

			@Override
			public List<String> getReward() {
				return Arrays.asList(" §7Reward: §b1 Uncommon Enchantment ");
			}
		};
		
		new OreMineQuest(9, "Cobalt Mining (2)", 4, 110, Repeat.NEVER, true) {
			
			@Override
			public void giveReward(FoGPlayer omp) {
				omp.addSilver(2500);
				omp.getPlayer().sendMessage("§7You have received §7§l2500 Silver§7.");
			}

			@Override
			public Ore getOre() {
				return Ore.COBALT;
			}
			
			@Override
			public int getAmount() {
				return 100;
			}

			@Override
			public List<String> getDiscription(FoGPlayer omp) {
				return Arrays.asList(" §7Mine §f100 Cobalt Ore§7. ");
			}

			@Override
			public List<String> getReward() {
				return Arrays.asList(" §7Reward: §7§l2500 Silver ");
			}
		};
		
		new MobKillQuest(10, "Zombies (5)", 4, 85, Repeat.NEVER, true) {
			
			@Override
			public void giveReward(FoGPlayer omp) {
				omp.getPlayer().getInventory().addItem(Rarity.UNCOMMON.getRandomItem());
				omp.getPlayer().sendMessage("§7You have received a §bUncommon Enchantment§7.");
			}
			
			@Override
			public MobLevel getMob() {
				return new MobLevel(Mob.ZOMBIE, 2);
			}
			
			@Override
			public int getAmount() {
				return 150;
			}

			@Override
			public List<String> getDiscription(FoGPlayer omp) {
				return Arrays.asList(" §7Kill §f150 Zombies (LvL 2)§7. ");
			}

			@Override
			public List<String> getReward() {
				return Arrays.asList(" §7Reward: §b1 Uncommon Enchantment ");
			}
		};
		
		new MobKillQuest(11, "Zombies (6)", 5, 150, Repeat.NEVER, true) {
			
			@Override
			public void giveReward(FoGPlayer omp) {
				omp.getPlayer().getInventory().addItem(Rarity.UNCOMMON.getRandomItem());
				omp.getPlayer().sendMessage("§7You have received a §bUncommon Enchantment§7.");
				omp.addSilver(1000);
				omp.getPlayer().sendMessage("§7You have received §7§l1000 Silver§7.");
			}
			
			@Override
			public MobLevel getMob() {
				return new MobLevel(Mob.ZOMBIE_ARCHER, 2);
			}
			
			@Override
			public int getAmount() {
				return 100;
			}

			@Override
			public List<String> getDiscription(FoGPlayer omp) {
				return Arrays.asList(" §7Kill §f100 Zombie Archers (LvL 2)§7. ");
			}

			@Override
			public List<String> getReward() {
				return Arrays.asList(" §7Rewards: ", " §7- §b1 Uncommon Enchantment ", " §7- §7§l1000 Silver ");
			}
		};
		
		new OreMineQuest(12, "Copper Mining (2)", 5, 100, Repeat.NEVER, true) {
			
			@Override
			public void giveReward(FoGPlayer omp) {
				omp.addSilver(3000);
				omp.getPlayer().sendMessage("§7You have received §7§l3000 Silver§7.");
			}

			@Override
			public Ore getOre() {
				return Ore.COPPER;
			}
			
			@Override
			public int getAmount() {
				return 200;
			}

			@Override
			public List<String> getDiscription(FoGPlayer omp) {
				return Arrays.asList(" §7Mine §f200 Copper Ore§7. ");
			}

			@Override
			public List<String> getReward() {
				return Arrays.asList(" §7Reward: §7§l3000 Silver ");
			}
		};
		
		new OreCollectQuest(13, "Collectables (3)", 6, 200, Repeat.DAILY, false) {
			
			@Override
			public void giveReward(FoGPlayer omp) {
				omp.addSilver(12000);
				omp.getPlayer().sendMessage("§7You have received §7§l12000 Silver§7.");
			}

			@Override
			public Ore getOre() {
				return Ore.AMETHYST;
			}
			
			@Override
			public int getAmount() {
				return 50;
			}

			@Override
			public List<String> getDiscription(FoGPlayer omp) {
				return Arrays.asList(" §7Collect §f50 Amethyst§7. ");
			}

			@Override
			public List<String> getReward() {
				return Arrays.asList(" §7Reward: §7§l12000 Silver ");
			}
		};
	}
	
	private void registerNMS(){
		addCustomEntity(Zombie.class, "Mob-Zombie", 54);
		addCustomEntity(ZombieArcher.class, "Mob-ZombieArcher", 54);
	}
	
	protected static Field mapStringToClassField, mapClassToStringField, mapIdToClassField, mapClassToIdField, mapStringToIdField;

	static{
	   try{
	        mapStringToClassField = net.minecraft.server.v1_8_R3.EntityTypes.class.getDeclaredField("c");
	        mapClassToStringField = net.minecraft.server.v1_8_R3.EntityTypes.class.getDeclaredField("d");
	        mapClassToIdField = net.minecraft.server.v1_8_R3.EntityTypes.class.getDeclaredField("f");
	        mapStringToIdField = net.minecraft.server.v1_8_R3.EntityTypes.class.getDeclaredField("g");
	 
	        mapStringToClassField.setAccessible(true);
	        mapClassToStringField.setAccessible(true);
	        mapClassToIdField.setAccessible(true);
	        mapStringToIdField.setAccessible(true);
	    }
	    catch(Exception ex){}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected static void addCustomEntity(Class entityClass, String name, int id){
	    if(mapStringToClassField == null || mapStringToIdField == null || mapClassToStringField == null || mapClassToIdField == null){
	        return;
	    }
	    else{
	        try{
	            Map mapStringToClass = (Map) mapStringToClassField.get(null);
	            Map mapStringToId = (Map) mapStringToIdField.get(null);
	            Map mapClasstoString = (Map) mapClassToStringField.get(null);
	            Map mapClassToId = (Map) mapClassToIdField.get(null);
	 
	            mapStringToClass.put(name, entityClass);
	            mapStringToId.put(name, Integer.valueOf(id));
	            mapClasstoString.put(entityClass, name);
	            mapClassToId.put(entityClass, Integer.valueOf(id));
	 
	            mapStringToClassField.set(null, mapStringToClass);
	            mapStringToIdField.set(null, mapStringToId);
	            mapClassToStringField.set(null, mapClasstoString);
	            mapClassToIdField.set(null, mapClassToId);
	        }
	        catch(Exception ex){}
	    }
	}
}
