package om.fog;

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
import om.fog.handlers.FoGScoreBoard;
import om.fog.handlers.players.FoGPlayer;
import om.fog.runnables.FoGPlayerRunnable;

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
	
	public void onEnable() {
		api = API.getInstance();
		fog = this;

		api.setSpawnBuilders("§b§lMod §brienk222");
		api.setScoreboardManager(new FoGScoreBoard());
		lobbyWorld = Bukkit.getWorld("FoGLobby");
		galaxyWorld = Bukkit.getWorld("FoGGalaxy");
		spawn = new Location(getLobbyWorld(), 0.5, 74, 0.5, 140, 0);
		players = new HashMap<Player, FoGPlayer>();
		fogPlayers = new ArrayList<FoGPlayer>();
		
		registerEvents();
		registerRunnables();
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
			h.addLine("§e§lTutorial");
			h.addLine("§7Click on the Villager to choose a Faction.");
			h.addLine("§cWARNING! §7This cannot be undone!");
			h.create();
		}
	}
}
