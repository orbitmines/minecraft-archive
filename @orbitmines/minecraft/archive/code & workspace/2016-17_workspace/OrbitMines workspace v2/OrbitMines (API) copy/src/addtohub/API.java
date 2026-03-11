package addtohub;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class API extends JavaPlugin {

	private List<MGArena> mgArenas;

	
	public void onEnable(){
		//api = this;
		
		//configManager = new ConfigManager();
		//getConfigManager().setup(Config.SETTINGS, Config.VOTERS);
		//this.server = Server.valueOf(getConfigManager().get(Config.SETTINGS).getString("server"));
		
		//registerNMS();
		//registerCommands();
		//registerEvents();
		//registerRunnables();
		
		//voteManager = new VoteManager();
		//serverSelector = new ServerSelectorInv();
		
		//players = new HashMap<Player, OMPlayer>();
		//omplayers = new ArrayList<OMPlayer>();
		//onlinePlayers = new HashMap<Server, Integer>();
		//voters = new ArrayList<StringInt>();
		//pendingVoters = new ArrayList<StringInt>();
		//holograms = new ArrayList<Hologram>();
		//kits = new ArrayList<Kit>();
		//npcs = new ArrayList<NPC>();
		//npcChunks = new ArrayList<Chunk>();
		//npcarmorstands = new ArrayList<NPCArmorStand>();
		mgArenas = new ArrayList<MGArena>();
		//gadgetSlot = 5;

		
		//database = new Database("127.0.0.1", 3306, "OrbitMines", "root", DBPassword.dbpw);
		
		//getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
		//getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", new BungeeManager());
		///getServer().getMessenger().registerOutgoingPluginChannel(this, "OrbitMinesAPI");
		//getServer().getMessenger().registerIncomingPluginChannel(this, "OrbitMinesAPI", new BungeeManager());
		
		//loadPendingVotes();
	}

	public List<MGArena> getMGArenas() {
		return mgArenas;
	}


	private void registerCommands(){
		Commands commands = new Commands();
		List<String> commandlist = Arrays.asList("setlastdonator", "setvip", "builderworld", "builderworld2", "setstaff", "resetMonthlyVIPPoints", "giveMonthlyVIPPoints", "votes", "vippoints", "omt", "money", "addhomes", "addshops", "addwarps");

		for(String command : commandlist){
			getCommand(command).setExecutor(commands);
		}
	}
}
