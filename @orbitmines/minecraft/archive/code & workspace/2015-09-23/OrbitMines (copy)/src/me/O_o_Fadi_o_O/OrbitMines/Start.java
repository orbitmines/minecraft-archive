package me.O_o_Fadi_o_O.OrbitMines;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import me.O_o_Fadi_o_O.OrbitMines.NMS.CustomBat;
import me.O_o_Fadi_o_O.OrbitMines.NMS.CustomBlaze;
import me.O_o_Fadi_o_O.OrbitMines.NMS.CustomCaveSpider;
import me.O_o_Fadi_o_O.OrbitMines.NMS.CustomChicken;
import me.O_o_Fadi_o_O.OrbitMines.NMS.CustomCow;
import me.O_o_Fadi_o_O.OrbitMines.NMS.CustomCreeper;
import me.O_o_Fadi_o_O.OrbitMines.NMS.CustomEnderman;
import me.O_o_Fadi_o_O.OrbitMines.NMS.CustomEndermite;
import me.O_o_Fadi_o_O.OrbitMines.NMS.CustomGhast;
import me.O_o_Fadi_o_O.OrbitMines.NMS.CustomHorse;
import me.O_o_Fadi_o_O.OrbitMines.NMS.CustomIronGolem;
import me.O_o_Fadi_o_O.OrbitMines.NMS.CustomMagmaCube;
import me.O_o_Fadi_o_O.OrbitMines.NMS.CustomMushroomCow;
import me.O_o_Fadi_o_O.OrbitMines.NMS.CustomOcelot;
import me.O_o_Fadi_o_O.OrbitMines.NMS.CustomPig;
import me.O_o_Fadi_o_O.OrbitMines.NMS.CustomPigZombie;
import me.O_o_Fadi_o_O.OrbitMines.NMS.CustomRabbit;
import me.O_o_Fadi_o_O.OrbitMines.NMS.CustomSheep;
import me.O_o_Fadi_o_O.OrbitMines.NMS.CustomSilverfish;
import me.O_o_Fadi_o_O.OrbitMines.NMS.CustomSkeleton;
import me.O_o_Fadi_o_O.OrbitMines.NMS.CustomSlime;
import me.O_o_Fadi_o_O.OrbitMines.NMS.CustomSnowman;
import me.O_o_Fadi_o_O.OrbitMines.NMS.CustomSpider;
import me.O_o_Fadi_o_O.OrbitMines.NMS.CustomSquid;
import me.O_o_Fadi_o_O.OrbitMines.NMS.CustomVillager;
import me.O_o_Fadi_o_O.OrbitMines.NMS.CustomWitch;
import me.O_o_Fadi_o_O.OrbitMines.NMS.CustomWolf;
import me.O_o_Fadi_o_O.OrbitMines.NMS.CustomZombie;
import me.O_o_Fadi_o_O.OrbitMines.NMS.MovingSkeleton;
import me.O_o_Fadi_o_O.OrbitMines.NMS.NoAttackPigZombie;
import me.O_o_Fadi_o_O.OrbitMines.NMS.PetChicken;
import me.O_o_Fadi_o_O.OrbitMines.NMS.PetCow;
import me.O_o_Fadi_o_O.OrbitMines.NMS.PetCreeper;
import me.O_o_Fadi_o_O.OrbitMines.NMS.PetMagmaCube;
import me.O_o_Fadi_o_O.OrbitMines.NMS.PetMushroomCow;
import me.O_o_Fadi_o_O.OrbitMines.NMS.PetOcelot;
import me.O_o_Fadi_o_O.OrbitMines.NMS.PetPig;
import me.O_o_Fadi_o_O.OrbitMines.NMS.PetSheep;
import me.O_o_Fadi_o_O.OrbitMines.NMS.PetSilverfish;
import me.O_o_Fadi_o_O.OrbitMines.NMS.PetSlime;
import me.O_o_Fadi_o_O.OrbitMines.NMS.PetSpider;
import me.O_o_Fadi_o_O.OrbitMines.NMS.PetSquid;
import me.O_o_Fadi_o_O.OrbitMines.NMS.PetWolf;
import me.O_o_Fadi_o_O.OrbitMines.events.AnimationEvent;
import me.O_o_Fadi_o_O.OrbitMines.events.BlockChangeEvent;
import me.O_o_Fadi_o_O.OrbitMines.events.BlockFormEvent;
import me.O_o_Fadi_o_O.OrbitMines.events.BreakEvent;
import me.O_o_Fadi_o_O.OrbitMines.events.ChunkEvent;
import me.O_o_Fadi_o_O.OrbitMines.events.ClickEvent;
import me.O_o_Fadi_o_O.OrbitMines.events.CommandPreprocessEvent;
import me.O_o_Fadi_o_O.OrbitMines.events.DamageEvent;
import me.O_o_Fadi_o_O.OrbitMines.events.DespawnEvent;
import me.O_o_Fadi_o_O.OrbitMines.events.DropEvent;
import me.O_o_Fadi_o_O.OrbitMines.events.EntityDamage;
import me.O_o_Fadi_o_O.OrbitMines.events.EntityDeath;
import me.O_o_Fadi_o_O.OrbitMines.events.EntityInteractEvent;
import me.O_o_Fadi_o_O.OrbitMines.events.ExpChangeEvent;
import me.O_o_Fadi_o_O.OrbitMines.events.ExplodeEvent;
import me.O_o_Fadi_o_O.OrbitMines.events.FadeEvent;
import me.O_o_Fadi_o_O.OrbitMines.events.FoodEvent;
import me.O_o_Fadi_o_O.OrbitMines.events.InteractAtEntityEvent;
import me.O_o_Fadi_o_O.OrbitMines.events.InvEvent;
import me.O_o_Fadi_o_O.OrbitMines.events.JoinEvent;
import me.O_o_Fadi_o_O.OrbitMines.events.PickupEvent;
import me.O_o_Fadi_o_O.OrbitMines.events.PlaceEvent;
import me.O_o_Fadi_o_O.OrbitMines.events.PlayerChat;
import me.O_o_Fadi_o_O.OrbitMines.events.PlayerInteract;
import me.O_o_Fadi_o_O.OrbitMines.events.PlayerMove;
import me.O_o_Fadi_o_O.OrbitMines.events.ProjectileHit;
import me.O_o_Fadi_o_O.OrbitMines.events.QuitEvent;
import me.O_o_Fadi_o_O.OrbitMines.events.ShootBowEvent;
import me.O_o_Fadi_o_O.OrbitMines.events.SignEvent;
import me.O_o_Fadi_o_O.OrbitMines.events.SpawnEvent;
import me.O_o_Fadi_o_O.OrbitMines.events.TeleportEvent;
import me.O_o_Fadi_o_O.OrbitMines.events.ToggleFlightEvent;
import me.O_o_Fadi_o_O.OrbitMines.events.VoteEvent;
import me.O_o_Fadi_o_O.OrbitMines.events.WorldChangeEvent;
import me.O_o_Fadi_o_O.OrbitMines.managers.BungeeManager;
import me.O_o_Fadi_o_O.OrbitMines.managers.ConfigManager;
import me.O_o_Fadi_o_O.OrbitMines.runnables.Runnables;
import me.O_o_Fadi_o_O.OrbitMines.utils.Commands;
import me.O_o_Fadi_o_O.OrbitMines.utils.OMPlayer;
import me.O_o_Fadi_o_O.OrbitMines.utils.ServerData;
import me.O_o_Fadi_o_O.OrbitMines.utils.ServerData.ServerStorage;
import me.O_o_Fadi_o_O.OrbitMines.utils.Utils;
import me.O_o_Fadi_o_O.OrbitMines.utils.Utils.Server;
import me.O_o_Fadi_o_O.OrbitMines.utils.hub.Inventories.ServerSelectorInv;
import net.milkbowl.vault.permission.Permission;
import om.api.handlers.Database;

import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class Start extends JavaPlugin {
	
	public void onEnable(){
        registerCommands();
		registerAllEvents();
		
		new Runnables().start(this);
	}
	
	public void onDisable(){
		if(ServerData.isServer(Server.KITPVP)){
			Utils.removeAllEntities();
		}
	}
	
	private void registerAllEvents(){
		getServer().getPluginManager().registerEvents(new BreakEvent(), this);//Hub, KitPvP, Creative, Survival, SkyBlock, MiniGames, Prison
		getServer().getPluginManager().registerEvents(new ClickEvent(), this);//Hub, KitPvP, Creative, Survival, SkyBlock, MiniGames, Prison
		getServer().getPluginManager().registerEvents(new CommandPreprocessEvent(), this);//Hub, KitPvP, Creative, Survival, SkyBlock, MiniGames, Prison
		getServer().getPluginManager().registerEvents(new DamageEvent(), this);//Hub, KitPvP, Creative, Survival, SkyBlock, MiniGames, Prison
		getServer().getPluginManager().registerEvents(new EntityDamage(), this);//Hub, KitPvP, Creative, Survival, SkyBlock, MiniGames, Prison
		getServer().getPluginManager().registerEvents(new EntityDeath(), this);//Hub, KitPvP, Creative, Survival, SkyBlock, MiniGames, Prison
		getServer().getPluginManager().registerEvents(new EntityInteractEvent(), this);//Hub, KitPvP, Creative, Survival, SkyBlock, MiniGames, Prison
		getServer().getPluginManager().registerEvents(new InteractAtEntityEvent(), this);//Hub, KitPvP, Creative, Survival, SkyBlock, MiniGames, Prison
		getServer().getPluginManager().registerEvents(new JoinEvent(), this);//Hub, KitPvP, Creative, Survival, SkyBlock, MiniGames, Prison
		getServer().getPluginManager().registerEvents(new PlaceEvent(), this);//Hub, KitPvP, Creative, Survival, SkyBlock, MiniGames, Prison
		getServer().getPluginManager().registerEvents(new PlayerChat(), this);//Hub, KitPvP, Creative, Survival, SkyBlock, MiniGames, Prison
		getServer().getPluginManager().registerEvents(new PlayerInteract(), this);//Hub, KitPvP, Creative, Survival, SkyBlock, MiniGames, Prison
		getServer().getPluginManager().registerEvents(new PlayerMove(), this);//Hub, KitPvP, Creative, Survival, SkyBlock, MiniGames, Prison
		getServer().getPluginManager().registerEvents(new QuitEvent(), this);//Hub, KitPvP, Creative, Survival, SkyBlock, MiniGames, Prison
		getServer().getPluginManager().registerEvents(new SignEvent(), this);//Hub, KitPvP, Creative, Survival, SkyBlock, MiniGames, Prison
		getServer().getPluginManager().registerEvents(new VoteEvent(), this);//Hub, KitPvP, Creative, Survival, SkyBlock, MiniGames, Prison
		getServer().getPluginManager().registerEvents(new WorldChangeEvent(), this);//Hub, KitPvP, Creative, Survival, SkyBlock, MiniGames, Prison
		
		if(ServerData.isServer(Server.HUB, Server.KITPVP, Server.CREATIVE, Server.MINIGAMES)){
			getServer().getPluginManager().registerEvents(new BlockChangeEvent(), this);//Hub, KitPvP, Creative, MiniGames
			getServer().getPluginManager().registerEvents(new DropEvent(), this);//Hub, KitPvP, Creative, MiniGames
			getServer().getPluginManager().registerEvents(new ExplodeEvent(), this);//Hub, KitPvP, Creative, MiniGames
			getServer().getPluginManager().registerEvents(new FoodEvent(), this);//Hub, KitPvP, Creative, MiniGames
			getServer().getPluginManager().registerEvents(new PickupEvent(), this);//Hub, KitPvP, Creative, MiniGames
			getServer().getPluginManager().registerEvents(new ProjectileHit(), this);//Hub, KitPvP, Creative, MiniGames
		}
		
		if(ServerData.isServer(Server.HUB, Server.MINIGAMES)){
			getServer().getPluginManager().registerEvents(new AnimationEvent(), this);//Hub, MiniGames
			getServer().getPluginManager().registerEvents(new DespawnEvent(), this);//Hub, MiniGames
		}
		if(ServerData.isServer(Server.KITPVP)){
			getServer().getPluginManager().registerEvents(new ExpChangeEvent(), this);//KitPvP
			getServer().getPluginManager().registerEvents(new FadeEvent(), this);//KitPvP
			getServer().getPluginManager().registerEvents(new ShootBowEvent(), this);//KitPvP
			getServer().getPluginManager().registerEvents(new TeleportEvent(), this);//KitPvP
		}
		if(ServerData.isServer(Server.HUB, Server.CREATIVE, Server.MINIGAMES)){
			getServer().getPluginManager().registerEvents(new BlockFormEvent(), this);//Hub, Creative, MiniGames
		}
		if(ServerData.isServer(Server.PRISON, Server.MINIGAMES)){
			getServer().getPluginManager().registerEvents(new ChunkEvent(), this);//KitPvP
		}
		if(ServerData.isServer(Server.MINIGAMES)){
			getServer().getPluginManager().registerEvents(new InvEvent(), this);//MiniGames
			getServer().getPluginManager().registerEvents(new ToggleFlightEvent(), this);//MiniGames
		}
		if(ServerData.isServer(Server.CREATIVE)){
			getServer().getPluginManager().registerEvents(new SpawnEvent(), this);//Creative
		}
	}
	
	private void registerCommands(){
		Commands commands = new Commands();
		List<String> commandlist = Arrays.asList("setlastdonator", "setvip", "setstaff", "resetMonthlyVIPPoints", "giveMonthlyVIPPoints", "votes", "vippoints", "omt", "money", "addhomes", "addshops", "addwarps");
		
		for(String command : commandlist){
			getCommand(command).setExecutor(commands);
		}
	}
	
	public static Start getInstance(){
		return plugin;
	}
}
