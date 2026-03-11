package om.api;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import om.api.cmd.AFKCommand;
import om.api.cmd.AddEnchantmentCommand;
import om.api.cmd.ChatColorsCommand;
import om.api.cmd.ClearEntitiesCommand;
import om.api.cmd.DisguiseCommand;
import om.api.cmd.DisguisesCommand;
import om.api.cmd.FeedCommand;
import om.api.cmd.FireworkCommand;
import om.api.cmd.FlyCommand;
import om.api.cmd.GadgetsCommand;
import om.api.cmd.GameModeACommand;
import om.api.cmd.GameModeCCommand;
import om.api.cmd.GameModeCommand;
import om.api.cmd.GameModeSCommand;
import om.api.cmd.GameModeSpecCommand;
import om.api.cmd.GiveCommand;
import om.api.cmd.HatsCommand;
import om.api.cmd.InvSeeCommand;
import om.api.cmd.NickCommand;
import om.api.cmd.OPModeCommand;
import om.api.cmd.PerksCommand;
import om.api.cmd.PetsCommand;
import om.api.cmd.PluginsCommand;
import om.api.cmd.ServersCommand;
import om.api.cmd.SilentCommand;
import om.api.cmd.SkullCommand;
import om.api.cmd.TeleportCommand;
import om.api.cmd.TopVotersCommand;
import om.api.cmd.TrailsCommand;
import om.api.cmd.UUIDCommand;
import om.api.cmd.UndisguiseCommand;
import om.api.cmd.VoteCommand;
import om.api.cmd.WardrobeCommand;
import om.api.events.QuitEvent;
import om.api.events.UnloadEvent;
import om.api.events.VoteEvent;
import om.api.events.WorldChangeEvent;
import om.api.handlers.Command;
import om.api.handlers.Database;
import om.api.handlers.Hologram;
import om.api.handlers.Kit;
import om.api.handlers.MGArena;
import om.api.handlers.NPC;
import om.api.handlers.NPCArmorStand;
import om.api.handlers.StringInt;
import om.api.handlers.players.OMPlayer;
import om.api.invs.others.ServerSelectorInv;
import om.api.managers.BungeeManager;
import om.api.managers.ConfigManager;
import om.api.managers.ScoreBoardManager;
import om.api.managers.VoteManager;
import om.api.nms.npc.CustomBat;
import om.api.nms.npc.CustomBlaze;
import om.api.nms.npc.CustomCaveSpider;
import om.api.nms.npc.CustomChicken;
import om.api.nms.npc.CustomCow;
import om.api.nms.npc.CustomCreeper;
import om.api.nms.npc.CustomEnderman;
import om.api.nms.npc.CustomEndermite;
import om.api.nms.npc.CustomGhast;
import om.api.nms.npc.CustomHorse;
import om.api.nms.npc.CustomIronGolem;
import om.api.nms.npc.CustomMagmaCube;
import om.api.nms.npc.CustomMushroomCow;
import om.api.nms.npc.CustomOcelot;
import om.api.nms.npc.CustomPig;
import om.api.nms.npc.CustomPigZombie;
import om.api.nms.npc.CustomRabbit;
import om.api.nms.npc.CustomSheep;
import om.api.nms.npc.CustomSilverfish;
import om.api.nms.npc.CustomSkeleton;
import om.api.nms.npc.CustomSlime;
import om.api.nms.npc.CustomSnowman;
import om.api.nms.npc.CustomSpider;
import om.api.nms.npc.CustomSquid;
import om.api.nms.npc.CustomVillager;
import om.api.nms.npc.CustomWitch;
import om.api.nms.npc.CustomWolf;
import om.api.nms.npc.CustomZombie;
import om.api.nms.npc.MovingSkeleton;
import om.api.nms.npc.NoAttackPigZombie;
import om.api.nms.pets.PetChicken;
import om.api.nms.pets.PetCow;
import om.api.nms.pets.PetCreeper;
import om.api.nms.pets.PetMagmaCube;
import om.api.nms.pets.PetMushroomCow;
import om.api.nms.pets.PetOcelot;
import om.api.nms.pets.PetPig;
import om.api.nms.pets.PetSheep;
import om.api.nms.pets.PetSilverfish;
import om.api.nms.pets.PetSlime;
import om.api.nms.pets.PetSpider;
import om.api.nms.pets.PetSquid;
import om.api.nms.pets.PetWolf;
import om.api.passes.DBPassword;
import om.api.runnables.DatabaseRunnable;
import om.api.runnables.OMRunnable.Duration;
import om.api.runnables.PlayerDataRunnable;
import om.api.runnables.ScoreboardTitleRunnable;
import om.api.runnables.ServerRunnable;
import om.api.runnables.VoteRunnable;
import om.api.utils.enums.Config;
import om.api.utils.enums.Server;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class API extends JavaPlugin {

	private static API api;
	private Server server;
	private Database database;
	
	private ConfigManager configManager;
	private ScoreBoardManager scoreboardManager;
	private VoteManager voteManager;
	
	private ServerSelectorInv serverSelector;
	
	private Map<Player, OMPlayer> players;
	private List<OMPlayer> omplayers;
	private Map<Server, Integer> onlinePlayers;
	private List<StringInt> voters;
	private List<StringInt> pendingVoters;
	private List<Hologram> holograms;
	private List<Kit> kits;
	private List<NPC> npcs;
	private List<NPCArmorStand> npcarmorstands;
	private List<Chunk> npcChunks;
	private List<MGArena> mgArenas;
	private String spawnBuilders;
	private int gadgetSlot;
	
	private Map<Entity, OMPlayer> swapTeleporter;
	private List<Entity> creeperLaunched;
	private List<Entity> paintBalls;
	private List<Entity> soccerMagmaCubes;
	private List<Entity> eggBombs;
	private List<Entity> fireballs;
	private List<Entity> inkBombs;
	private Map<Entity, Integer> inkBombTime;
	private List<Entity> silverFishBombs;
	private List<Entity> snowGolemAttackBalls;
	
	public void onEnable(){
		api = this;
		
		configManager = new ConfigManager();
		getConfigManager().setup(Config.SETTINGS, Config.VOTERS);
		this.server = Server.valueOf(getConfigManager().get(Config.SETTINGS).getString("server"));
		
		registerNMS();
		registerCommands();
		registerEvents();
		registerRunnables();
		
		voteManager = new VoteManager();
		serverSelector = new ServerSelectorInv();
		
		players = new HashMap<Player, OMPlayer>();
		omplayers = new ArrayList<OMPlayer>();
		onlinePlayers = new HashMap<Server, Integer>();
		voters = new ArrayList<StringInt>();
		pendingVoters = new ArrayList<StringInt>();
		holograms = new ArrayList<Hologram>();
		kits = new ArrayList<Kit>();
		npcs = new ArrayList<NPC>();
		npcChunks = new ArrayList<Chunk>();
		npcarmorstands = new ArrayList<NPCArmorStand>();
		mgArenas = new ArrayList<MGArena>();
		gadgetSlot = 5;
		
		swapTeleporter = new HashMap<Entity, OMPlayer>();
		creeperLaunched = new ArrayList<Entity>();
		paintBalls = new ArrayList<Entity>();
		soccerMagmaCubes = new ArrayList<Entity>();
		eggBombs = new ArrayList<Entity>();
		fireballs = new ArrayList<Entity>();
		inkBombs = new ArrayList<Entity>();
		inkBombTime = new HashMap<Entity, Integer>();
		silverFishBombs = new ArrayList<Entity>();
		snowGolemAttackBalls = new ArrayList<Entity>();
		
		database = new Database("127.0.0.1", 3306, "OrbitMines", "root", DBPassword.dbpw);
		
		getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
		getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", new BungeeManager());
		getServer().getMessenger().registerOutgoingPluginChannel(this, "OrbitMinesAPI");
		getServer().getMessenger().registerIncomingPluginChannel(this, "OrbitMinesAPI", new BungeeManager());
		
		loadPendingVotes();
	}
	
	public void onDisable(){
		for(Player p : Bukkit.getOnlinePlayers()){
			p.kickPlayer("§6§lOrbitMines§4§lNetwork\n    §7Restarting " + getServer().getName() + "§7 Server...");
		}
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
	public ScoreBoardManager getScoreboardManager() {
		return scoreboardManager;
	}
	public void setScoreboardManager(ScoreBoardManager scoreboardManager) {
		this.scoreboardManager = scoreboardManager;
	}
	public VoteManager getVoteManager() {
		return voteManager;
	}
	
	public ServerSelectorInv getServerSelector() {
		return serverSelector;
	}
	
	public Map<Player, OMPlayer> getPlayers(){
		return players;
	}
	public List<OMPlayer> getOMPlayers() {
		return omplayers;
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
	public void setVoters(List<StringInt> voters) {
		this.voters = voters;
	}
	public List<StringInt> getPendingVoters() {
		return pendingVoters;
	}
	public void setPendingVoters(List<StringInt> pendingVoters) {
		this.pendingVoters = pendingVoters;
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
	public List<Chunk> getNPCChunks() {
		return npcChunks;
	}
	public List<NPCArmorStand> getNPCArmorStands() {
		return npcarmorstands;
	}
	public List<MGArena> getMGArenas() {
		return mgArenas;
	}
	
	public String getSpawnBuilders() {
		return spawnBuilders;
	}
	public void setSpawnBuilders(String spawnBuilders) {
		this.spawnBuilders = spawnBuilders;
	}
	
	public int getGadgetSlot() {
		return gadgetSlot;
	}
	
	public boolean dispatchCommands(OMPlayer omp, String[] a){
		for(Command cmd : Command.getCommands()){
			for(String alias : cmd.getCMDs()){
				if(a[0].equalsIgnoreCase(alias)){
					cmd.dispatch(omp, a);
					return true;
				}
			}
		}
		return false;
	}
	
	private void registerRunnables(){
		new DatabaseRunnable().runTaskAsynchronously(this);
		new VoteRunnable(Duration.ONE_HOUR);
		new ServerRunnable(Duration.ONE_SECOND);
		new ScoreboardTitleRunnable(Duration.QUARTER_SECOND);
		new PlayerDataRunnable(Duration.TENTH_SECOND);
	}
    
    private void loadPendingVotes(){
    	FileConfiguration config = getConfigManager().get(Config.VOTERS);
		if(config.getStringList("VoteRewardsNotGiven") != null){
			List<StringInt> list = new ArrayList<StringInt>();
			for(String s : config.getStringList("VoteRewardsNotGiven")){
				String[] sParts = s.split("\\|");
				list.add(new StringInt(sParts[0], Integer.parseInt(sParts[1])));
			}
			pendingVoters.clear();
			pendingVoters.addAll(list);
		}
    }
    
    private void registerEvents(){
    	getServer().getPluginManager().registerEvents(new QuitEvent(), this);
    	getServer().getPluginManager().registerEvents(new UnloadEvent(), this);
    	getServer().getPluginManager().registerEvents(new VoteEvent(), this);
    	getServer().getPluginManager().registerEvents(new WorldChangeEvent(), this);
    }
    
    private void registerCommands(){
    	new AddEnchantmentCommand();
    	new AFKCommand();
    	new ChatColorsCommand();
    	new ClearEntitiesCommand();
    	new DisguiseCommand();
    	new DisguisesCommand();
    	new FeedCommand();
    	new FireworkCommand();
    	new FlyCommand();
    	new GadgetsCommand();
    	new GameModeACommand();
    	new GameModeCCommand();
    	new GameModeCommand();
    	new GameModeSCommand();
    	new GameModeSpecCommand();
    	new GiveCommand();
    	new HatsCommand();
    	new InvSeeCommand();
    	new NickCommand();
    	new OPModeCommand();
    	new PerksCommand();
    	new PetsCommand();
    	new PluginsCommand();
    	new ServersCommand();
    	new SilentCommand();
    	new SkullCommand();
    	new TeleportCommand();
    	new TopVotersCommand();
    	new TrailsCommand();
    	new UndisguiseCommand();
    	new UUIDCommand();
    	new VoteCommand();
    	new WardrobeCommand();
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

	public Map<Entity, OMPlayer> getSwapTeleporter() {
		return swapTeleporter;
	}

	public List<Entity> getCreeperLaunched() {
		return creeperLaunched;
	}

	public List<Entity> getPaintBalls() {
		return paintBalls;
	}

	public List<Entity> getSoccerMagmaCubes() {
		return soccerMagmaCubes;
	}

	public List<Entity> getEggBombs() {
		return eggBombs;
	}

	public List<Entity> getFireballs() {
		return fireballs;
	}

	public List<Entity> getInkBombs() {
		return inkBombs;
	}

	public Map<Entity, Integer> getInkBombTime() {
		return inkBombTime;
	}

	public List<Entity> getSilverFishBombs() {
		return silverFishBombs;
	}

	public List<Entity> getSnowGolemAttackBalls() {
		return snowGolemAttackBalls;
	}
}
