package me.O_o_Fadi_o_O.OMHub;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import me.O_o_Fadi_o_O.OMHub.events.AnimationEvent;
import me.O_o_Fadi_o_O.OMHub.events.BlockChangeEvent;
import me.O_o_Fadi_o_O.OMHub.events.BlockFormEvent;
import me.O_o_Fadi_o_O.OMHub.events.BreakEvent;
import me.O_o_Fadi_o_O.OMHub.events.ClickEvent;
import me.O_o_Fadi_o_O.OMHub.events.CommandPreprocessEvent;
import me.O_o_Fadi_o_O.OMHub.events.DamageEvent;
import me.O_o_Fadi_o_O.OMHub.events.DespawnEvent;
import me.O_o_Fadi_o_O.OMHub.events.DropEvent;
import me.O_o_Fadi_o_O.OMHub.events.EntityDamage;
import me.O_o_Fadi_o_O.OMHub.events.EntityDeath;
import me.O_o_Fadi_o_O.OMHub.events.EntityInteractEvent;
import me.O_o_Fadi_o_O.OMHub.events.ExplodeEvent;
import me.O_o_Fadi_o_O.OMHub.events.FoodEvent;
import me.O_o_Fadi_o_O.OMHub.events.InteractAtEntityEvent;
import me.O_o_Fadi_o_O.OMHub.events.JoinEvent;
import me.O_o_Fadi_o_O.OMHub.events.PickupEvent;
import me.O_o_Fadi_o_O.OMHub.events.PlaceEvent;
import me.O_o_Fadi_o_O.OMHub.events.PlayerChat;
import me.O_o_Fadi_o_O.OMHub.events.PlayerInteract;
import me.O_o_Fadi_o_O.OMHub.events.PlayerMove;
import me.O_o_Fadi_o_O.OMHub.events.ProjectileHit;
import me.O_o_Fadi_o_O.OMHub.events.QuitEvent;
import me.O_o_Fadi_o_O.OMHub.events.SignEvent;
import me.O_o_Fadi_o_O.OMHub.events.VoteEvent;
import me.O_o_Fadi_o_O.OMHub.managers.BungeeManager;
import me.O_o_Fadi_o_O.OMHub.managers.ConfigManager;
import me.O_o_Fadi_o_O.OMHub.runnables.Runnables;
import me.O_o_Fadi_o_O.OMHub.utils.orbitmines.Commands;
import me.O_o_Fadi_o_O.OMHub.utils.orbitmines.Database;
import me.O_o_Fadi_o_O.OMHub.utils.orbitmines.Inventories.ServerSelectorInv;
import me.O_o_Fadi_o_O.OMHub.utils.orbitmines.OMPlayer;
import me.O_o_Fadi_o_O.OMHub.utils.orbitmines.ServerData;
import me.O_o_Fadi_o_O.OMHub.utils.orbitmines.ServerData.ServerStorage;
import me.O_o_Fadi_o_O.OMHub.utils.orbitmines.Utils;
import net.milkbowl.vault.permission.Permission;

import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class Start extends JavaPlugin {
	
	private static Start plugin;
	
	public Permission permission = null;
	
	public void onEnable(){
		plugin = this;
		setupPermissions();
		ConfigManager.setup(this);
		Utils.removeAllEntities();
		
		new Database("sql-3.verygames.net", 3306, "minecraft4268", "minecraft4268", "hnfxy5h48");
		new ServerData.HubServer();
		
		Bukkit.getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        Bukkit.getMessenger().registerIncomingPluginChannel(this, "BungeeCord", new BungeeManager());
        Bukkit.getMessenger().registerOutgoingPluginChannel(this, ServerData.getServer().toString() + "Bungee");
        Bukkit.getMessenger().registerIncomingPluginChannel(this, ServerData.getServer().toString() + "Bungee", new BungeeManager());

        registerCommands();
		registerAllEvents();
		
		new ServerSelectorInv();
		new Runnables().start(this);
		
		if(ConfigManager.playerdata.getStringList("VoteRewardsNotGiven") != null){
			List<String> list = new ArrayList<String>();
			for(String s : ConfigManager.playerdata.getStringList("VoteRewardsNotGiven")){
				list.add(s);
			}
			ServerStorage.pendingvoters.clear();
			ServerStorage.pendingvoters.addAll(list);
		}
	}
	
	public void onDisable(){
		for(OMPlayer omplayer : OMPlayer.getOMPlayers()){
			omplayer.logOut();
		}
	}
	
	private void registerAllEvents(){
		getServer().getPluginManager().registerEvents(new JoinEvent(), this);
		getServer().getPluginManager().registerEvents(new QuitEvent(), this);
		getServer().getPluginManager().registerEvents(new ClickEvent(), this);
		getServer().getPluginManager().registerEvents(new DropEvent(), this);
		getServer().getPluginManager().registerEvents(new PlayerInteract(), this);
		getServer().getPluginManager().registerEvents(new PlayerChat(), this);
		getServer().getPluginManager().registerEvents(new FoodEvent(), this);
		getServer().getPluginManager().registerEvents(new DamageEvent(), this);
		getServer().getPluginManager().registerEvents(new EntityDamage(), this);
		getServer().getPluginManager().registerEvents(new PickupEvent(), this);
		getServer().getPluginManager().registerEvents(new PlayerMove(), this);
		getServer().getPluginManager().registerEvents(new BlockChangeEvent(), this);
		getServer().getPluginManager().registerEvents(new ProjectileHit(), this);
		getServer().getPluginManager().registerEvents(new ExplodeEvent(), this);
		getServer().getPluginManager().registerEvents(new EntityDeath(), this);
		getServer().getPluginManager().registerEvents(new EntityInteractEvent(), this);
		getServer().getPluginManager().registerEvents(new CommandPreprocessEvent(), this);
		getServer().getPluginManager().registerEvents(new DespawnEvent(), this);
		getServer().getPluginManager().registerEvents(new VoteEvent(), this);
		getServer().getPluginManager().registerEvents(new AnimationEvent(), this);
		getServer().getPluginManager().registerEvents(new BreakEvent(), this);
		getServer().getPluginManager().registerEvents(new PlaceEvent(), this);
		getServer().getPluginManager().registerEvents(new SignEvent(), this);
		getServer().getPluginManager().registerEvents(new BlockFormEvent(), this);
		getServer().getPluginManager().registerEvents(new InteractAtEntityEvent(), this);
	}
	
	private void registerCommands(){
		Commands commands = new Commands();
		getCommand("servers").setExecutor(commands);
		getCommand("perks").setExecutor(commands);
		getCommand("opmode").setExecutor(commands);
		getCommand("topvoters").setExecutor(commands);
		getCommand("feed").setExecutor(commands);
		getCommand("eat").setExecutor(commands);
		getCommand("vote").setExecutor(commands);
		getCommand("fly").setExecutor(commands);
		getCommand("gamemode").setExecutor(commands);
		getCommand("gm").setExecutor(commands);
		getCommand("gmc").setExecutor(commands);
		getCommand("gms").setExecutor(commands);
		getCommand("gma").setExecutor(commands);
		getCommand("gmspec").setExecutor(commands);
		getCommand("skull").setExecutor(commands);
		getCommand("give").setExecutor(commands);
		getCommand("tp").setExecutor(commands);
		getCommand("teleport").setExecutor(commands);
		getCommand("disguise").setExecutor(commands);
		getCommand("undisguise").setExecutor(commands);
		getCommand("nick").setExecutor(commands);
		getCommand("afk").setExecutor(commands);
		getCommand("ban").setExecutor(commands);
		getCommand("unban").setExecutor(commands);
		getCommand("kick").setExecutor(commands);
		getCommand("kickall").setExecutor(commands);
		getCommand("dis").setExecutor(commands);
		getCommand("d").setExecutor(commands);
		getCommand("builder").setExecutor(commands);
		getCommand("setlastdonator").setExecutor(commands);
		getCommand("setvip").setExecutor(commands);
		getCommand("setstaff").setExecutor(commands);
		getCommand("resetMonthlyVIPPoints").setExecutor(commands);
		getCommand("giveMonthlyVIPPoints").setExecutor(commands);
		getCommand("votes").setExecutor(commands);
		getCommand("vippoints").setExecutor(commands);
		getCommand("omt").setExecutor(commands);
	}
	
	public static Start getInstance(){
		return plugin;
	}
	protected static Field mapStringToClassField, mapClassToStringField, mapIdToClassField, mapClassToIdField, mapStringToIdField;

	static{
	   try{
	        mapStringToClassField = net.minecraft.server.v1_8_R2.EntityTypes.class.getDeclaredField("c");
	        mapClassToStringField = net.minecraft.server.v1_8_R2.EntityTypes.class.getDeclaredField("d");
	        mapClassToIdField = net.minecraft.server.v1_8_R2.EntityTypes.class.getDeclaredField("f");
	        mapStringToIdField = net.minecraft.server.v1_8_R2.EntityTypes.class.getDeclaredField("g");
	 
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
