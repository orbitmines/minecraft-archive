package om.fog;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import om.api.API;
import om.api.handlers.Hologram;
import om.api.handlers.NPC;
import om.api.handlers.NPCArmorStand;
import om.api.runnables.OMRunnable.Duration;
import om.api.utils.WorldUtils;
import om.api.utils.enums.NPCType;
import om.fog.events.BreakEvent;
import om.fog.events.ClickEvent;
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
import om.fog.events.SignEvent;
import om.fog.handlers.FoGMap;
import om.fog.handlers.FoGScoreBoard;
import om.fog.handlers.SpawnArea;
import om.fog.handlers.players.FoGPlayer;
import om.fog.nms.zombie.Zombie;
import om.fog.runnables.FoGPlayerRunnable;
import om.fog.utils.enums.Faction;
import om.fog.utils.enums.Mob;
import om.fog.utils.enums.Suit;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
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
		registerEvents();
		registerRunnables();
		registerMaps();
		spawnNPCs();
	}
	
	public void onDisable(){
		WorldUtils.removeAllEntitiesIF();
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
	
	private void registerEvents(){
		getServer().getPluginManager().registerEvents(new BreakEvent(), this);
		getServer().getPluginManager().registerEvents(new ClickEvent(), this);
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
		getServer().getPluginManager().registerEvents(new SignEvent(), this);
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
		//{
		//	NPC npc = new NPC(NPCType.OMT_SHOP);
		//	npc.newEntity(EntityType.BLAZE, new Location(getLobbyWorld(), -4.5, 75, -11.5, 0, 0), "§e§lOMT Shop", false);
		//}
		{
			Hologram h = new Hologram(new Location(getLobbyWorld(), -3.5, 74, -3.5, -45, 0));
			h.addLine("§7Welcome to §e§lFractals of the Galaxy");
			h.addLine("§7Start your adventure here!");
			h.create();
		}
		{
			Hologram h = new Hologram(new Location(getLobbyWorld(), -7.5, 74, -10.5, -45, 0));
			h.addLine("§e§lTutorial §7(1/..)");
			h.addLine("§7Click on the Villager to choose a Faction.");
			h.addLine("§cWARNING! §7This cannot be undone!");
			h.create();
		}
		{
			Hologram h = new Hologram(new Location(getGalaxyWorld(), 241.5, 18, 3824.5, -33, 0));
			h.addLine("§7Welcome to §9Beta§7!");
			h.addLine("§7Total population: §9" + Faction.BETA.getPlayerAmount() + "§7.");//TODO
			h.create();
		}
		{
			Hologram h = new Hologram(new Location(getGalaxyWorld(), 251.5, 18, 3825.5, 50, 0));
			h.addLine("§e§lTutorial §7(2/..)");
			h.addLine("§7Check out the hangar to equip your suit!");
			h.addLine("§7§m---->");
			h.create();
		}
		{
			Hologram h = new Hologram(new Location(getGalaxyWorld(), 262.5, 21, 3844.5, 165, 0));
			h.addLine("§e§lTutorial §7(3/..)");
			h.addLine("§7You start in our '" + Suit.TRAINING_SUIT.getName(Faction.BETA) + "'.");
			h.addLine("§7As you see this has already been unlocked,");
			h.addLine("§7Click on the armorstand to equip the suit.");
			h.create();
		}
		{
			Hologram h = new Hologram(new Location(getGalaxyWorld(), 265.5, 21, 3840.5, 90, 0));
			h.addLine("§e§lTutorial §7(4/..)");
			h.addLine("§7When your " + Suit.TRAINING_SUIT.getName(Faction.BETA) + " is broken, you can always get a new one.");
			h.addLine("§7(This cannot be done with other suits, you need Repair Tokens for that)");
			h.addLine("§7We've got two more interesting things to show you about our base.");
			h.addLine("§7Go outside and check out the Shop.");
			h.create();
		}
		{
			Hologram h = new Hologram(new Location(getGalaxyWorld(), 259.5, 15, 3812.5, 90, 0));
			h.addLine("§e§lTutorial §7(5/..)");
			h.addLine("§7This is our Shop, here you can sell ores and buy new equipment.");
			h.addLine("§7Next, we have our Bank, turn around and you should see it.");
			h.create();
		}
	}
	
	private void registerMaps(){
		this.maps = new ArrayList<FoGMap>();
		this.spawnAreas = new ArrayList<SpawnArea>();
		{
			FoGMap map = new FoGMap(Faction.BETA, "B-1", 0, 3500, null);
			SpawnArea sa1 = new SpawnArea(map, 252, 3792, 123, 3629);
			sa1.addMob(Mob.ZOMBIE, 1, 100, 1);
			this.spawnAreas.add(sa1);
			
			SpawnArea sa2 = new SpawnArea(map, 216, 3793, 123, 3813);
			sa2.addMob(Mob.ZOMBIE, 1, 15, 2);
			this.spawnAreas.add(sa2);
		}
	}
	
	private void registerNMS(){
		addCustomEntity(Zombie.class, "Mob-Zombie", 54);
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
