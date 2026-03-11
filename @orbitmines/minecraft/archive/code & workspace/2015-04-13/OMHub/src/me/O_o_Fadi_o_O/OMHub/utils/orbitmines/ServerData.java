package me.O_o_Fadi_o_O.OMHub.utils.orbitmines;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import me.O_o_Fadi_o_O.OMHub.utils.orbitmines.Utils.InventoryEnum;
import me.O_o_Fadi_o_O.OMHub.utils.orbitmines.Utils.Server;
import net.minecraft.server.v1_8_R2.EnumParticle;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ServerData {
	
	public static int getOnlinePlayers(Server server){
		return ServerStorage.onlineplayers.get(server);
	}
	public static Server getServer(){
		return ServerStorage.server;
	}
	public static boolean isServer(Server... servers){
		Server thisserver = getServer();
		for(Server server : servers){
			if(thisserver == server){
				return true;
			}
		}
		return false;
	}
	public static HubServer getHub(){
		return ServerStorage.hub;
	}
	public static World getLobbyWorld(){
		return ServerStorage.lobbyworld;
	}
	
	public static class ServerStorage {
		public static List<OMPlayer> omplayers = new ArrayList<OMPlayer>();
		public static List<ChickenFightKit> cfkits = new ArrayList<ChickenFightKit>();
		public static List<ChickenFightPlayer> cfplayers = new ArrayList<ChickenFightPlayer>();
		public static List<KitPvPPlayer> kitpvpplayers = new ArrayList<KitPvPPlayer>();
		public static List<MindCraftPlayer> mcplayers = new ArrayList<MindCraftPlayer>();
		public static List<SurvivalGamesPlayer> sgplayers = new ArrayList<SurvivalGamesPlayer>();
		public static List<FireworkSettings> fwsettings = new ArrayList<FireworkSettings>();
		public static List<Hologram> holograms = new ArrayList<Hologram>();
		public static List<NPC> npcs = new ArrayList<NPC>();
		
		public static HashMap<Player, OMPlayer> players = new HashMap<Player, OMPlayer>();
		public static HashMap<Server, Integer> onlineplayers = new HashMap<Server, Integer>();
		
		public static HashMap<InventoryEnum, ItemStack[]> inventorycontents = new HashMap<InventoryEnum, ItemStack[]>();
		
		public static Server server;
		public static HubServer hub;
		public static World lobbyworld;
		public static Entity serverselector;
		
		public static List<UUID> voters = new ArrayList<UUID>();
		public static List<String> pendingvoters = new ArrayList<String>();
		
		public static HashMap<Entity, Entity> npcItem = new HashMap<Entity, Entity>(); 
		public static HashMap<Entity, Player> swapteleporter = new HashMap<Entity, Player>();
		public static List<Entity> paintballs = new ArrayList<Entity>();
		public static List<Entity> soccermagmacubes = new ArrayList<Entity>();
		public static List<Entity> eggbombs = new ArrayList<Entity>();
		public static List<Entity> fireballs = new ArrayList<Entity>();
		public static List<Entity> inkbombs = new ArrayList<Entity>();
		public static HashMap<Entity, Integer> inkbombtime = new HashMap<Entity, Integer>();
		public static List<Entity> silverfishbombs = new ArrayList<Entity>();
		public static HashMap<Entity, Player> petowner = new HashMap<Entity, Player>();
		public static List<Entity> snowgolemattackballs = new ArrayList<Entity>();
		public static List<Entity> armorstands = new ArrayList<Entity>();
	}

	public static class HubServer {
		
		private World world;
		private World builderworld;
		private Entity alphanpc;
		private Entity lapisparkournpc;
		private Entity mindcraftnpc;
		private Location spawn;
		private Location lapisparkour;
		private Location mindcraft;
		private HashMap<Integer, List<Block>> mcblocksforturn;
		private HashMap<Integer, List<Block>> mcblocksforturnstatus;
		private Location mcwinssign;
		private Location mcbestgamesign;
		private Location topvoter1;
		private Location topvoter2;
		private Location topvoter3;
		private Location topvotersign1;
		private Location topvotersign2;
		private Location topvotersign3;
		private Location lastdonatorsign;
		private String lastdonatorstring;
		private HashMap<Server, List<Block>> serverportals;
		private List<Location> waterfalls;
		
		public HubServer(){
			ServerStorage.server = Server.HUB;
			ServerStorage.hub = this;
		}

		public World getWorld(){
			return world;
		}
		public void setWorld(World world){
			this.world = world;
			ServerStorage.lobbyworld = world;
		}

		public World getBuilderWorld(){
			return builderworld;
		}
		public void setBuilderWorld(World builderworld){
			this.builderworld = builderworld;
		}

		public Entity getAlphaNPC(){
			return alphanpc;
		}
		public void setAlphaNPC(Entity alphanpc){
			this.alphanpc = alphanpc;
		}

		public Entity getLapisParkourNPC(){
			return lapisparkournpc;
		}
		public void setLapisParkourNPC(Entity lapisparkournpc){
			this.lapisparkournpc = lapisparkournpc;
		}

		public Entity getMindCraftNPC(){
			return mindcraftnpc;
		}
		public void setMindCraftNPC(Entity mindcraftnpc){
			this.mindcraftnpc = mindcraftnpc;
		}

		public Location getSpawn(){
			return spawn;
		}
		public void setSpawn(Location spawn){
			this.spawn = spawn;
		}

		public Location getLapisParkour(){
			return lapisparkour;
		}
		public void setLapisParkour(Location lapisparkour){
			this.lapisparkour = lapisparkour;
		}

		public Location getMindCraftLocation(){
			return mindcraft;
		}
		public void setMindCraftLocation(Location mindcraft){
			this.mindcraft = mindcraft;
		}

		public HashMap<Integer, List<Block>> getMCBlocksForTurn(){
			return mcblocksforturn;
		}
		public void setMCBlocksForTurn(HashMap<Integer, List<Block>> mcblocksforturn){
			this.mcblocksforturn = mcblocksforturn;
		}
		public void addMCBlockForTurn(int turn, List<Block> mcblocksforturn){
			this.mcblocksforturn.put(turn, mcblocksforturn);
		}

		public HashMap<Integer, List<Block>> getMCBlocksForTurnStatus(){
			return mcblocksforturnstatus;
		}
		public void setMCBlocksForTurnStatus(HashMap<Integer, List<Block>> mcblocksforturnstatus){
			this.mcblocksforturnstatus = mcblocksforturnstatus;
		}
		public void addMCBlockForTurnStatus(int turn, List<Block> mcblocksforturnstatus){
			this.mcblocksforturnstatus.put(turn, mcblocksforturnstatus);
		}

		public Location getMCWinsSign(){
			return mcwinssign;
		}
		public void setMCWinsSign(Location mcwinssign){
			this.mcwinssign = mcwinssign;
		}

		public Location getMCBestGameSign(){
			return mcbestgamesign;
		}
		public void setMCBestGameSign(Location mcbestgamesign){
			this.mcbestgamesign = mcbestgamesign;
		}

		public Location getTopVoter1(){
			return topvoter1;
		}
		public void setTopVoter1(Location topvoter1){
			this.topvoter1 = topvoter1;
		}

		public Location getTopVoter2(){
			return topvoter2;
		}
		public void setTopVoter2(Location topvoter2){
			this.topvoter2 = topvoter2;
		}

		public Location getTopVoter3(){
			return topvoter3;
		}
		public void setTopVoter3(Location topvoter3){
			this.topvoter3 = topvoter3;
		}

		public Location getTopVoterSign1(){
			return topvotersign1;
		}
		public void setTopVoterSign1(Location topvotersign1){
			this.topvotersign1 = topvotersign1;
		}

		public Location getTopVoterSign2(){
			return topvotersign2;
		}
		public void setTopVoterSign2(Location topvotersign2){
			this.topvotersign2 = topvotersign2;
		}

		public Location getTopVoterSign3(){
			return topvotersign3;
		}
		public void setTopVoterSign3(Location topvotersign3){
			this.topvotersign3 = topvotersign3;
		}

		public Location getLastDonatorSign(){
			return lastdonatorsign;
		}
		public void setLastDonatorSign(Location lastdonatorsign){
			this.lastdonatorsign = lastdonatorsign;
		}

		public String getLastDonatorString(){
			return lastdonatorstring;
		}
		public void setLastDonatorString(String lastdonatorstring){
			this.lastdonatorstring = lastdonatorstring;
		}

		public HashMap<Server, List<Block>> getServerPortals(){
			return serverportals;
		}
		public void setServerPortals(HashMap<Server, List<Block>> serverportals){
			this.serverportals = serverportals;
		}
		public void setServerPortal(Server server, List<Block> serverportal){
			this.serverportals.put(server, serverportal);
		}

		public List<Location> getWaterfalls(){
			return waterfalls;
		}
		public void setWaterfalls(List<Location> waterfalls){
			this.waterfalls = waterfalls;
		}
		public void addWaterfall(Location waterfall){
			this.waterfalls.add(waterfall);
		}
		
		public void updateWaterfalls(){
			for(Location location : getWaterfalls()){
				RandomFallingBlock b = new RandomFallingBlock(location);
				b.setMaterial(Material.STAINED_GLASS);
				b.setDurability((byte) 11);
				b.setDrop(false);
				b.spawn();
				
				Particle p = new Particle(EnumParticle.WATER_SPLASH, location);
				p.setSize(1, 1, 1);
				p.setSpecial(0);
				p.setAmount(1);
				p.send(Bukkit.getOnlinePlayers());
			}
		}
	}
}
