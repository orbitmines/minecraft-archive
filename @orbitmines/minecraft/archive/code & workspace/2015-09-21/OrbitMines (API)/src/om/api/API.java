package om.api;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.milkbowl.vault.permission.Permission;
import om.api.handlers.Database;
import om.api.handlers.Hologram;
import om.api.handlers.Kit;
import om.api.handlers.MGArena;
import om.api.handlers.NPC;
import om.api.handlers.NPCArmorStand;
import om.api.handlers.StringInt;
import om.api.managers.BungeeManager;
import om.api.managers.ConfigManager;
import om.api.nms.CustomBat;
import om.api.nms.CustomBlaze;
import om.api.nms.CustomCaveSpider;
import om.api.nms.CustomChicken;
import om.api.nms.CustomCow;
import om.api.nms.CustomCreeper;
import om.api.nms.CustomEnderman;
import om.api.nms.CustomEndermite;
import om.api.nms.CustomGhast;
import om.api.nms.CustomHorse;
import om.api.nms.CustomIronGolem;
import om.api.nms.CustomMagmaCube;
import om.api.nms.CustomMushroomCow;
import om.api.nms.CustomOcelot;
import om.api.nms.CustomPig;
import om.api.nms.CustomPigZombie;
import om.api.nms.CustomRabbit;
import om.api.nms.CustomSheep;
import om.api.nms.CustomSilverfish;
import om.api.nms.CustomSkeleton;
import om.api.nms.CustomSlime;
import om.api.nms.CustomSnowman;
import om.api.nms.CustomSpider;
import om.api.nms.CustomSquid;
import om.api.nms.CustomVillager;
import om.api.nms.CustomWitch;
import om.api.nms.CustomWolf;
import om.api.nms.CustomZombie;
import om.api.nms.MovingSkeleton;
import om.api.nms.NoAttackPigZombie;
import om.api.nms.PetChicken;
import om.api.nms.PetCow;
import om.api.nms.PetCreeper;
import om.api.nms.PetMagmaCube;
import om.api.nms.PetMushroomCow;
import om.api.nms.PetOcelot;
import om.api.nms.PetPig;
import om.api.nms.PetSheep;
import om.api.nms.PetSilverfish;
import om.api.nms.PetSlime;
import om.api.nms.PetSpider;
import om.api.nms.PetSquid;
import om.api.nms.PetWolf;
import om.api.utils.ColorUtils;
import om.api.utils.ConfigUtils;
import om.api.utils.ItemUtils;
import om.api.utils.PlayerUtils;
import om.api.utils.ReflectionUtils;
import om.api.utils.UUIDUtils;
import om.api.utils.Utils;
import om.api.utils.WorldUtils;
import om.api.utils.enums.Server;

import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class API extends JavaPlugin {

	private static API api;
	private Server server;
	private Database database;
	public Permission permission;
	
	private ConfigManager configManager;
	
	private Map<Server, Integer> onlinePlayers;
	private List<StringInt> voters;
	private List<StringInt> pendingVoters;
	private List<Hologram> holograms;
	private List<Kit> kits;
	private List<NPC> npcs;
	private List<NPCArmorStand> npcarmorstands;
	private List<MGArena> mgArenas;
	
	private ColorUtils colorUtils;
	private ConfigUtils configUtils;
	private ItemUtils itemUtils;
	private PlayerUtils playerUtils;
	private ReflectionUtils reflectionUtils;
	private Utils utils;
	private UUIDUtils uuidUtils;
	private WorldUtils worldUtils;
	
	public API(Server server){
		api = this;
		this.server = server;
	
		setupPermissions();
		registerNMS();
		
		configManager = new ConfigManager();
		
		onlinePlayers = new HashMap<Server, Integer>();
		voters = new ArrayList<StringInt>();
		pendingVoters = new ArrayList<StringInt>();
		holograms = new ArrayList<Hologram>();
		kits = new ArrayList<Kit>();
		npcs = new ArrayList<NPC>();
		npcarmorstands = new ArrayList<NPCArmorStand>();
		mgArenas = new ArrayList<MGArena>();
		
		colorUtils = new ColorUtils();
		configUtils = new ConfigUtils();
		itemUtils = new ItemUtils();
		playerUtils = new PlayerUtils();
		utils = new Utils();
		uuidUtils = new UUIDUtils();
		worldUtils = new WorldUtils();
		
		database = new Database("sql-3.verygames.net", 3306, "minecraft4268", "minecraft4268", "hnfxy5h48");
		
		getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
		getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", new BungeeManager());
		getServer().getMessenger().registerOutgoingPluginChannel(this, "OrbitMinesAPI");
		getServer().getMessenger().registerIncomingPluginChannel(this, "OrbitMinesAPI", new BungeeManager());
	}
	
	public static API getInstance() {
		return api;
	}
	public Server server() {
		return server;
	}
	public boolean isServer(Server server){
		return this.server == server;
	}
	
	public Database getDB() {
		return database;
	}
	
	public ConfigManager getConfigManager() {
		return configManager;
	}
	
	public Map<Server, Integer> getOnlinePlayers() {
		return onlinePlayers;
	}
	public int getOnlinePlayers(Server server) {
		return onlinePlayers.get(server);
	}
	public List<StringInt> getVoters() {
		return voters;
	}
	public List<StringInt> getPendingVoters() {
		return pendingVoters;
	}
	public List<Hologram> getHolograms() {
		return holograms;
	}
	public List<Kit> getKits() {
		return kits;
	}
	public List<NPC> getNPCs() {
		return npcs;
	}
	public List<NPCArmorStand> getNPCArmorStands() {
		return npcarmorstands;
	}
	public List<MGArena> getMGArenas() {
		return mgArenas;
	}
	
	public ColorUtils getColorUtils() {
		return colorUtils;
	}
	public ConfigUtils getConfigUtils() {
		return configUtils;
	}
	public ItemUtils getItemUtils() {
		return itemUtils;
	}
	public PlayerUtils getPlayerUtils() {
		return playerUtils;
	}
	public ReflectionUtils getReflectionUtils() {
		return reflectionUtils;
	}
	public Utils getUtils() {
		return utils;
	}
	public UUIDUtils getUUIDUtils() {
		return uuidUtils;
	}
	public WorldUtils getWorldUtils() {
		return worldUtils;
	}
	
	private void registerNMS(){
		addCustomEntity(CustomBat.class, "NPC-Bat", 65);
		addCustomEntity(CustomBlaze.class, "NPC-Blaze", 61);
		addCustomEntity(CustomCaveSpider.class, "NPC-CaveSpider", 59);
		addCustomEntity(CustomChicken.class, "NPC-Chicken", 93);
		addCustomEntity(CustomCow.class, "NPC-Cow", 92);
		addCustomEntity(CustomCreeper.class, "NPC-Creeper", 50);
		addCustomEntity(CustomEnderman.class, "NPC-Enderman", 58);
		addCustomEntity(CustomEndermite.class, "NPC-Endermite", 67);
		addCustomEntity(CustomGhast.class, "NPC-Ghast", 56);
	//	addCustomEntity(CustomGuardian.class, "NPC-Guardian", 68);
		addCustomEntity(CustomHorse.class, "NPC-Creeper", 100);
		addCustomEntity(CustomIronGolem.class, "NPC-IronGolem", 99);
		addCustomEntity(CustomMagmaCube.class, "NPC-MagmaCube", 62);
		addCustomEntity(CustomMushroomCow.class, "NPC-MushroomCow", 96);
		addCustomEntity(CustomOcelot.class, "NPC-Ocelot", 98);
		addCustomEntity(CustomPig.class, "NPC-Pig", 90);
		addCustomEntity(CustomPigZombie.class, "NPC-PigZombie", 57);
		addCustomEntity(CustomRabbit.class, "NPC-Rabbit", 101);
		addCustomEntity(CustomSheep.class, "NPC-Sheep", 91);
		addCustomEntity(CustomSilverfish.class, "NPC-Silverfish", 60);
		addCustomEntity(CustomSkeleton.class, "NPC-Skeleton", 51);
		addCustomEntity(CustomSlime.class, "NPC-Slime", 55);
		addCustomEntity(CustomSnowman.class, "NPC-Snowman", 97);
		addCustomEntity(CustomSpider.class, "NPC-Spider", 52);
		addCustomEntity(CustomSquid.class, "NPC-Squid", 94);
		addCustomEntity(CustomVillager.class, "NPC-Villager", 120);
		addCustomEntity(CustomWitch.class, "NPC-Witch", 66);
	//	addCustomEntity(CustomWither.class, "NPC-Wither", 64);
		addCustomEntity(CustomWolf.class, "NPC-Wolf", 95);
		addCustomEntity(CustomZombie.class, "NPC-Zombie", 54);

		addCustomEntity(PetChicken.class, "Pet-Chicken", 93);
		addCustomEntity(PetCow.class, "Pet-Cow", 92);
		addCustomEntity(PetCreeper.class, "Pet-Creeper", 50);
		addCustomEntity(PetMagmaCube.class, "Pet-MagmaCube", 62);
		addCustomEntity(PetMushroomCow.class, "Pet-MushroomCow", 96);
		addCustomEntity(PetOcelot.class, "Pet-Ocelot", 98);
		addCustomEntity(PetPig.class, "Pet-Pig", 90);
		addCustomEntity(PetSheep.class, "Pet-Sheep", 91);
		addCustomEntity(PetSilverfish.class, "Pet-Silverfish", 60);
		addCustomEntity(PetSlime.class, "Pet-Slime", 55);
		addCustomEntity(PetSpider.class, "Pet-Spider", 52);
		addCustomEntity(PetSquid.class, "Pet-Squid", 94);
		addCustomEntity(PetWolf.class, "Pet-Wolf", 95);

		addCustomEntity(NoAttackPigZombie.class, "NA-PigZombie", 57);
		addCustomEntity(MovingSkeleton.class, "Move-Skeleton", 51);
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
	
    private boolean setupPermissions(){
        RegisteredServiceProvider<Permission> permissionProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.permission.Permission.class);
        if (permissionProvider != null) {
            permission = permissionProvider.getProvider();
        }
        return (permission != null);
    }
}
