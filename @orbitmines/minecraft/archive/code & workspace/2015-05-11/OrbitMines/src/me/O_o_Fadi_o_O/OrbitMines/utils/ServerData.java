package me.O_o_Fadi_o_O.OrbitMines.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import me.O_o_Fadi_o_O.OrbitMines.managers.ConfigManager;
import me.O_o_Fadi_o_O.OrbitMines.utils.Inventories.ServerSelectorInv;
import me.O_o_Fadi_o_O.OrbitMines.utils.Utils.InventoryEnum;
import me.O_o_Fadi_o_O.OrbitMines.utils.Utils.NPCType;
import me.O_o_Fadi_o_O.OrbitMines.utils.Utils.Server;
import me.O_o_Fadi_o_O.OrbitMines.utils.hub.MindCraftPlayer;
import me.O_o_Fadi_o_O.OrbitMines.utils.kitpvp.ActiveBooster;
import me.O_o_Fadi_o_O.OrbitMines.utils.kitpvp.KitPvPPlayer;
import me.O_o_Fadi_o_O.OrbitMines.utils.kitpvp.KitPvPUtils.ArmorType;
import me.O_o_Fadi_o_O.OrbitMines.utils.kitpvp.KitPvPUtils.ItemType;
import me.O_o_Fadi_o_O.OrbitMines.utils.kitpvp.KitPvPUtils.KitPvPKit;
import me.O_o_Fadi_o_O.OrbitMines.utils.kitpvp.KitPvPUtils.ProjectileType;
import me.O_o_Fadi_o_O.OrbitMines.utils.kitpvp.Map;
import me.O_o_Fadi_o_O.OrbitMines.utils.minigames.ChickenFightKit;
import me.O_o_Fadi_o_O.OrbitMines.utils.minigames.ChickenFightPlayer;
import me.O_o_Fadi_o_O.OrbitMines.utils.minigames.SurvivalGamesPlayer;
import net.minecraft.server.v1_8_R2.EnumParticle;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Skeleton.SkeletonType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.EulerAngle;

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
	public static KitPvPServer getKitPvP(){
		return ServerStorage.kitpvp;
	}
	public static World getLobbyWorld(){
		return ServerStorage.lobbyworld;
	}
	public static String getSpawnBuilders(){
		return ServerStorage.spawnbuilders;
	}
	
	public static class ServerStorage {
		public static Database database;
		
		public static List<OMPlayer> omplayers = new ArrayList<OMPlayer>();
		public static List<ChickenFightKit> cfkits = new ArrayList<ChickenFightKit>();
		public static List<ChickenFightPlayer> cfplayers = new ArrayList<ChickenFightPlayer>();
		public static List<KitPvPPlayer> kitpvpplayers = new ArrayList<KitPvPPlayer>();
		public static List<MindCraftPlayer> mcplayers = new ArrayList<MindCraftPlayer>();
		public static List<SurvivalGamesPlayer> sgplayers = new ArrayList<SurvivalGamesPlayer>();
		public static List<Hologram> holograms = new ArrayList<Hologram>();
		public static List<NPC> npcs = new ArrayList<NPC>();
		public static List<NPCArmorStand> asnpcs = new ArrayList<NPCArmorStand>();
		public static List<Kit> kits = new ArrayList<Kit>();
		
		public static HashMap<Player, OMPlayer> players = new HashMap<Player, OMPlayer>();
		public static HashMap<Server, Integer> onlineplayers = new HashMap<Server, Integer>();
		
		public static HashMap<InventoryEnum, ItemStack[]> inventorycontents = new HashMap<InventoryEnum, ItemStack[]>();
		
		public static Server server;
		public static String spawnbuilders;
		public static HubServer hub;
		public static KitPvPServer kitpvp;
		public static World lobbyworld;
		
		public static ServerSelectorInv serverselector;
		public static int serverselectori;
		
		public static HashMap<String, Integer> voters = new HashMap<String, Integer>();
		public static List<String> pendingvoters = new ArrayList<String>();
		 
		public static HashMap<Entity, OMPlayer> swapteleporter = new HashMap<Entity, OMPlayer>();
		public static List<Entity> creeperlaunched = new ArrayList<Entity>();
		public static List<Entity> paintballs = new ArrayList<Entity>();
		public static List<Entity> soccermagmacubes = new ArrayList<Entity>();
		public static List<Entity> eggbombs = new ArrayList<Entity>();
		public static List<Entity> fireballs = new ArrayList<Entity>();
		public static List<Entity> inkbombs = new ArrayList<Entity>();
		public static HashMap<Entity, Integer> inkbombtime = new HashMap<Entity, Integer>();
		public static List<Entity> silverfishbombs = new ArrayList<Entity>();
		public static List<Entity> pets = new ArrayList<Entity>();
		public static List<Entity> snowgolemattackballs = new ArrayList<Entity>();
		public static List<Entity> armorstands = new ArrayList<Entity>();
	}

	public static class HubServer {
		
		private World hubworld;
		private World builderworld;
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
		private ItemStack[] lobbyitems;
		
		public HubServer(){
			Utils.removeAllEntities();
			
			ServerStorage.server = Server.HUB;
			ServerStorage.spawnbuilders = "§b§lMod §brienk222\n§b§lMod §bsharewoods\n§b§lMod §beekhoorn2000\n§d§lBuilder §dcasidas\n§4§lOwner §4O_o_Fadi_o_O";
			ServerStorage.hub = this;
			this.hubworld = Bukkit.getWorld("Hub");
			ServerStorage.lobbyworld = this.hubworld;
			this.builderworld = Bukkit.getWorld("BuilderWorld");
			this.spawn = new Location(getHubWorld(), 0.5, 75, 0.5, 90, 0);
			this.lapisparkour = new Location(getHubWorld(), -36.5, 75, 37.5, 100, 0);
			this.mindcraft = new Location(getHubWorld(), 39.5, 77, 1.5, 0, 0);
			this.topvoter1 = new Location(getHubWorld(), -5, 78, 38);
			this.topvoter2 = new Location(getHubWorld(), -5, 77, 39);
			this.topvoter3 = new Location(getHubWorld(), -5, 76, 37);
			this.topvotersign1 = new Location(getHubWorld(), -6, 77, 38);
			this.topvotersign2 = new Location(getHubWorld(), -6, 76, 39);
			this.topvotersign3 = new Location(getHubWorld(), -6, 75, 37);
			this.lastdonatorsign = new Location(getHubWorld(), -7, 76, 8);
			this.lastdonatorstring = Utils.getName(UUID.fromString(ConfigManager.config.getString("LastDonator")));
			this.mcwinssign = new Location(getHubWorld(), 41, 79, 2);
			this.mcbestgamesign = new Location(getHubWorld(), 37, 79, 2);
			
			spawnNPCs();
			registerLobbyItems();
			registerMindCraft();
			registerServerPortals();
			registerWaterFalls();
			
			for(Player p : Bukkit.getOnlinePlayers()){
				p.kickPlayer("§6§lOrbitMines§4§lNetwork\n    §7Restarting §3§lHub§7 Server...");
			}
		}

		public World getHubWorld(){
			return hubworld;
		}
		public void setHubWorld(World hubworld){
			this.hubworld = hubworld;
			ServerStorage.lobbyworld = hubworld;
		}

		public World getBuilderWorld(){
			return builderworld;
		}
		public void setBuilderWorld(World builderworld){
			this.builderworld = builderworld;
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
			UUID uuid = Utils.getUUID(lastdonatorstring);
			
			if(uuid != null){
				this.lastdonatorstring = lastdonatorstring;
			
				ConfigManager.config.set("LastDonator", Utils.getUUID(lastdonatorstring));
				ConfigManager.saveConfig();
			}
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
		
		public ItemStack[] getLobbyItems(){
			return lobbyitems;
		}
		public void setLobbyItems(ItemStack[] lobbyitems){
			this.lobbyitems = lobbyitems;
		}
		public void giveLobbyItems(OMPlayer omplayer){
			Player p = omplayer.getPlayer();
			
			ItemStack gadgetitem = p.getInventory().getItem(5);
			p.getInventory().setContents(getLobbyItems());
			p.getInventory().setItem(5, gadgetitem);
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
				p.send(Bukkit.getOnlinePlayers());
			}
		}
		
		private void spawnNPCs(){
			{
				NPC npc = NPC.addNPC(NPCType.LAPIS_PARKOUR);
				npc.newEntity(EntityType.SKELETON, new Location(getHubWorld(), -37.5, 75, 40.5, -130, 0), "§1§lLapis Parkour §8| §b§l250 VIP Points");
				npc.setSkeletonType(SkeletonType.WITHER);
				npc.setHelmet(new ItemStack(Material.LAPIS_BLOCK));
				npc.setItemInHand(new ItemStack(Material.DIAMOND));
				npc.setChestplate(Utils.addColor(new ItemStack(Material.LEATHER_CHESTPLATE), Color.NAVY));
				npc.setLeggings(Utils.addColor(new ItemStack(Material.LEATHER_LEGGINGS), Color.NAVY));
				npc.setBoots(Utils.addColor(new ItemStack(Material.LEATHER_BOOTS), Color.NAVY));
			}
			{
				NPC npc = NPC.addNPC(NPCType.MINDCRAFT);
				npc.newEntity(EntityType.SKELETON, new Location(getHubWorld(), 31.5, 75, 5.5, 140, 0), "§c§lMindCraft §7| §e§lClick to Join");
				npc.setSkeletonType(SkeletonType.WITHER);
				npc.setItemInHand(new ItemStack(Material.WOOL));
				npc.setHelmet(Utils.addColor(new ItemStack(Material.LEATHER_HELMET), Color.BLACK));
				npc.setChestplate(Utils.addColor(new ItemStack(Material.LEATHER_CHESTPLATE), Color.BLACK));
				npc.setLeggings(Utils.addColor(new ItemStack(Material.LEATHER_LEGGINGS), Color.BLACK));
				npc.setBoots(Utils.addColor(new ItemStack(Material.LEATHER_BOOTS), Color.BLACK));
			}
			{
				NPC npc = NPC.addNPC(NPCType.ALPHA);
				npc.newEntity(EntityType.SKELETON, new Location(getHubWorld(), 5.5, 75, 5.5, 135, 0), "§e§lAlpha (Test) Server");
				npc.setSkeletonType(SkeletonType.WITHER);
				npc.setItemInHand(new ItemStack(Material.EMPTY_MAP));
				npc.setHelmet(new ItemStack(Material.GOLD_BLOCK));
				npc.setChestplate(new ItemStack(Material.GOLD_CHESTPLATE));
				npc.setLeggings(new ItemStack(Material.GOLD_LEGGINGS));
				npc.setBoots(new ItemStack(Material.GOLD_BOOTS));
			}
			{
				Hologram h = Hologram.addHologram(new Location(getHubWorld(), -3.5, 76, 0.5, -90, 0));
				h.addLine("§6§lOrbitMines§4§lNetwork §3§l1.8");
				h.addLine("§d§oReport bugs at §6§owww.orbitmines.com§d§o!");
				h.create();
			}
			{
				NPCArmorStand npcas = NPCArmorStand.addNPCArmorStand(NPCType.SERVER_SELECTOR, new Location(getHubWorld(), -3.5, 75, -3.5, -45, 0));
				npcas.setGravity(false);
				npcas.setVisible(false);
				npcas.setSmall(true);
				npcas.setUseItem(true);
				npcas.setItemStack(new ItemStack(Material.ENDER_PEARL, 1));
				npcas.setItemName("§3§lServer Selector");
				npcas.spawn();
			}
			{
				NPCArmorStand npcas = NPCArmorStand.addNPCArmorStand(NPCType.TOP_DONATOR, new Location(getHubWorld(), -6.5, 76, 8.5, -140, 0));
				npcas.setCustomName("§7Recent Donator");
				npcas.setCustomNameVisible(true);
				npcas.setBodyPose(EulerAngle.ZERO.setX(-0.1).setY(0).setZ(0));
				npcas.setHeadPose(EulerAngle.ZERO.setX(-0.15).setY(0).setZ(0));
				npcas.setLeftArmPose(EulerAngle.ZERO.setX(0).setY(0).setZ(0));
				npcas.setLeftLegPose(EulerAngle.ZERO.setX(-0.5).setY(0).setZ(0));
				npcas.setRightArmPose(EulerAngle.ZERO.setX(-0.75).setY(0).setZ(0));
				npcas.setRightLegPose(EulerAngle.ZERO.setX(0.5).setY(0).setZ(0));
				npcas.setGravity(false);
				npcas.setVisible(false);
				npcas.setSmall(true);
				npcas.setHelmet(new ItemStack(Material.SKULL_ITEM, 1));
				npcas.setChestplate(new ItemStack(Material.CHAINMAIL_CHESTPLATE, 1));
				npcas.setLeggings(new ItemStack(Material.CHAINMAIL_LEGGINGS, 1));
				npcas.setBoots(new ItemStack(Material.CHAINMAIL_BOOTS, 1));
				npcas.setItemInHand(new ItemStack(Material.ENDER_CHEST, 1));
				npcas.spawn();
			}
			{
				NPCArmorStand npcas = NPCArmorStand.addNPCArmorStand(NPCType.NORMAL, new Location(getHubWorld(), -4.0, 76, 0.5, -90, 0));
				npcas.setCustomName("§7Welcome to the §6§lOrbitMines§4§lNetwork§7!");
				npcas.setCustomNameVisible(true);
				npcas.setBodyPose(EulerAngle.ZERO.setX(0.1).setY(0).setZ(0));
				npcas.setHeadPose(EulerAngle.ZERO.setX(0.15).setY(0).setZ(0));
				npcas.setLeftArmPose(EulerAngle.ZERO.setX(0).setY(0).setZ(0));
				npcas.setLeftLegPose(EulerAngle.ZERO.setX(0).setY(0).setZ(-0.2));
				npcas.setRightArmPose(EulerAngle.ZERO.setX(-0.75).setY(0).setZ(0));
				npcas.setRightLegPose(EulerAngle.ZERO.setX(0).setY(0).setZ(0.2));
				npcas.setGravity(false);
				npcas.setVisible(false);
				npcas.setSmall(true);
				npcas.setHelmet(new ItemStack(Material.DIAMOND_BLOCK, 1));
				npcas.setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE, 1));
				npcas.setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS, 1));
				npcas.setBoots(new ItemStack(Material.DIAMOND_BOOTS, 1));
				npcas.setItemInHand(new ItemStack(Material.DIAMOND_SWORD, 1));
				npcas.spawn();
			}
			{
				NPCArmorStand npcas = NPCArmorStand.addNPCArmorStand(NPCType.NORMAL, new Location(getHubWorld(), -23.5, 77, 8.5, -90, 0));
				npcas.setCustomName("§4§lOwner §4O_o_Fadi_o_O");
				npcas.setBodyPose(EulerAngle.ZERO.setX(0.1).setY(0).setZ(0));
				npcas.setHeadPose(EulerAngle.ZERO.setX(0.15).setY(0).setZ(0));
				npcas.setLeftArmPose(EulerAngle.ZERO.setX(0).setY(0).setZ(0));
				npcas.setLeftLegPose(EulerAngle.ZERO.setX(0).setY(0).setZ(0));
				npcas.setRightArmPose(EulerAngle.ZERO.setX(-0.75).setY(0).setZ(0));
				npcas.setRightLegPose(EulerAngle.ZERO.setX(0).setY(0).setZ(0));
				npcas.setGravity(false);
				npcas.setVisible(false);
				npcas.setSmall(true);
				npcas.setHelmet(Utils.getSkull("O_o_Fadi_o_O"));
				npcas.setChestplate(Utils.addColor(new ItemStack(Material.LEATHER_CHESTPLATE, 1), Color.RED));
				npcas.setLeggings(Utils.addColor(new ItemStack(Material.LEATHER_LEGGINGS, 1), Color.RED));
				npcas.setBoots(Utils.addColor(new ItemStack(Material.LEATHER_BOOTS, 1), Color.RED));
				npcas.setItemInHand(new ItemStack(Material.REDSTONE_COMPARATOR, 1));
				npcas.spawn();
			}
			{
				NPCArmorStand npcas = NPCArmorStand.addNPCArmorStand(NPCType.NORMAL, new Location(getHubWorld(), -23.5, 77, 7.5, -90, 0));
				npcas.setCustomName("§b§lMob §beekhoorn2000");
				npcas.setBodyPose(EulerAngle.ZERO.setX(0.1).setY(0).setZ(0));
				npcas.setHeadPose(EulerAngle.ZERO.setX(0.15).setY(0).setZ(0));
				npcas.setLeftArmPose(EulerAngle.ZERO.setX(0).setY(0).setZ(0));
				npcas.setLeftLegPose(EulerAngle.ZERO.setX(0).setY(0).setZ(0));
				npcas.setRightArmPose(EulerAngle.ZERO.setX(-0.75).setY(0).setZ(0));
				npcas.setRightLegPose(EulerAngle.ZERO.setX(0).setY(0).setZ(0));
				npcas.setGravity(false);
				npcas.setVisible(false);
				npcas.setSmall(true);
				npcas.setHelmet(Utils.getSkull("eekhoorn2000"));
				npcas.setChestplate(Utils.addColor(new ItemStack(Material.LEATHER_CHESTPLATE, 1), Color.AQUA));
				npcas.setLeggings(Utils.addColor(new ItemStack(Material.LEATHER_LEGGINGS, 1), Color.AQUA));
				npcas.setBoots(Utils.addColor(new ItemStack(Material.LEATHER_BOOTS, 1), Color.AQUA));
				npcas.setItemInHand(new ItemStack(Material.COMPASS, 1));
				npcas.spawn();
			}
			{
				NPCArmorStand npcas = NPCArmorStand.addNPCArmorStand(NPCType.NORMAL, new Location(getHubWorld(), -23.5, 77, 6.5, -90, 0));
				npcas.setCustomName("§b§lMob §bsharewoods");
				npcas.setBodyPose(EulerAngle.ZERO.setX(0.1).setY(0).setZ(0));
				npcas.setHeadPose(EulerAngle.ZERO.setX(0.15).setY(0).setZ(0));
				npcas.setLeftArmPose(EulerAngle.ZERO.setX(0).setY(0).setZ(0));
				npcas.setLeftLegPose(EulerAngle.ZERO.setX(0).setY(0).setZ(0));
				npcas.setRightArmPose(EulerAngle.ZERO.setX(-0.75).setY(0).setZ(0));
				npcas.setRightLegPose(EulerAngle.ZERO.setX(0).setY(0).setZ(0));
				npcas.setGravity(false);
				npcas.setVisible(false);
				npcas.setSmall(true);
				npcas.setHelmet(Utils.getSkull("sharewoods"));
				npcas.setChestplate(Utils.addColor(new ItemStack(Material.LEATHER_CHESTPLATE, 1), Color.AQUA));
				npcas.setLeggings(Utils.addColor(new ItemStack(Material.LEATHER_LEGGINGS, 1), Color.AQUA));
				npcas.setBoots(Utils.addColor(new ItemStack(Material.LEATHER_BOOTS, 1), Color.AQUA));
				npcas.setItemInHand(new ItemStack(Material.COMPASS, 1));
				npcas.spawn();
			}
			{
				NPCArmorStand npcas = NPCArmorStand.addNPCArmorStand(NPCType.NORMAL, new Location(getHubWorld(), -23.5, 77, 5.5, -90, 0));
				npcas.setCustomName("§b§lMob §brienk222");
				npcas.setBodyPose(EulerAngle.ZERO.setX(0.1).setY(0).setZ(0));
				npcas.setHeadPose(EulerAngle.ZERO.setX(0.15).setY(0).setZ(0));
				npcas.setLeftArmPose(EulerAngle.ZERO.setX(0).setY(0).setZ(0));
				npcas.setLeftLegPose(EulerAngle.ZERO.setX(0).setY(0).setZ(0));
				npcas.setRightArmPose(EulerAngle.ZERO.setX(-0.75).setY(0).setZ(0));
				npcas.setRightLegPose(EulerAngle.ZERO.setX(0).setY(0).setZ(0));
				npcas.setGravity(false);
				npcas.setVisible(false);
				npcas.setSmall(true);
				npcas.setHelmet(Utils.getSkull("rienk222"));
				npcas.setChestplate(Utils.addColor(new ItemStack(Material.LEATHER_CHESTPLATE, 1), Color.AQUA));
				npcas.setLeggings(Utils.addColor(new ItemStack(Material.LEATHER_LEGGINGS, 1), Color.AQUA));
				npcas.setBoots(Utils.addColor(new ItemStack(Material.LEATHER_BOOTS, 1), Color.AQUA));
				npcas.setItemInHand(new ItemStack(Material.COMPASS, 1));
				npcas.spawn();
			}
			{
				NPCArmorStand npcas = NPCArmorStand.addNPCArmorStand(NPCType.NORMAL, new Location(getHubWorld(), -23.5, 77, 4.5, -90, 0));
				npcas.setCustomName("§d§lBuilder §dcasidas");
				npcas.setBodyPose(EulerAngle.ZERO.setX(0.1).setY(0).setZ(0));
				npcas.setHeadPose(EulerAngle.ZERO.setX(0.15).setY(0).setZ(0));
				npcas.setLeftArmPose(EulerAngle.ZERO.setX(0).setY(0).setZ(0));
				npcas.setLeftLegPose(EulerAngle.ZERO.setX(0).setY(0).setZ(0));
				npcas.setRightArmPose(EulerAngle.ZERO.setX(-0.75).setY(0).setZ(0));
				npcas.setRightLegPose(EulerAngle.ZERO.setX(0).setY(0).setZ(0));
				npcas.setGravity(false);
				npcas.setVisible(false);
				npcas.setSmall(true);
				npcas.setHelmet(Utils.getSkull("casidas"));
				npcas.setChestplate(Utils.addColor(new ItemStack(Material.LEATHER_CHESTPLATE, 1), Color.FUCHSIA));
				npcas.setLeggings(Utils.addColor(new ItemStack(Material.LEATHER_LEGGINGS, 1), Color.FUCHSIA));
				npcas.setBoots(Utils.addColor(new ItemStack(Material.LEATHER_BOOTS, 1), Color.FUCHSIA));
				npcas.setItemInHand(new ItemStack(Material.WOOD_AXE, 1));
				npcas.spawn();
			}
			{
				NPCArmorStand npcas = NPCArmorStand.addNPCArmorStand(NPCType.SERVER_INFO_KITPVP, new Location(getHubWorld(), -49.5, 78, 3.5, -90, 0));
				npcas.setBodyPose(EulerAngle.ZERO.setX(0.1).setY(0).setZ(0));
				npcas.setHeadPose(EulerAngle.ZERO.setX(0.15).setY(0).setZ(0));
				npcas.setLeftArmPose(EulerAngle.ZERO.setX(0).setY(0).setZ(0));
				npcas.setLeftLegPose(EulerAngle.ZERO.setX(0).setY(0).setZ(0));
				npcas.setRightArmPose(EulerAngle.ZERO.setX(-0.75).setY(0).setZ(0));
				npcas.setRightLegPose(EulerAngle.ZERO.setX(0).setY(0).setZ(0));
				npcas.setGravity(false);
				npcas.setVisible(false);
				npcas.setHelmet(new ItemStack(Material.CHAINMAIL_HELMET, 1));
				npcas.setChestplate(new ItemStack(Material.CHAINMAIL_CHESTPLATE, 1));
				npcas.setLeggings(new ItemStack(Material.CHAINMAIL_LEGGINGS, 1));
				npcas.setBoots(new ItemStack(Material.CHAINMAIL_BOOTS, 1));
				npcas.setItemInHand(new ItemStack(Material.IRON_SWORD, 1));
				npcas.spawn();
			}
			{
				NPCArmorStand npcas = NPCArmorStand.addNPCArmorStand(NPCType.SERVER_INFO_PRISON, new Location(getHubWorld(), -43.5, 78, -33.5, -90, 0));
				npcas.setBodyPose(EulerAngle.ZERO.setX(0.1).setY(0).setZ(0));
				npcas.setHeadPose(EulerAngle.ZERO.setX(0.15).setY(0).setZ(0));
				npcas.setLeftArmPose(EulerAngle.ZERO.setX(0).setY(0).setZ(0));
				npcas.setLeftLegPose(EulerAngle.ZERO.setX(0).setY(0).setZ(0));
				npcas.setRightArmPose(EulerAngle.ZERO.setX(-0.75).setY(0).setZ(0));
				npcas.setRightLegPose(EulerAngle.ZERO.setX(0).setY(0).setZ(0));
				npcas.setGravity(false);
				npcas.setVisible(false);
				npcas.setHelmet(new ItemStack(Material.IRON_HELMET, 1));
				npcas.setChestplate(new ItemStack(Material.IRON_CHESTPLATE, 1));
				npcas.setLeggings(new ItemStack(Material.IRON_LEGGINGS, 1));
				npcas.setBoots(new ItemStack(Material.IRON_BOOTS, 1));
				npcas.setItemInHand(Utils.addEnchantment(new ItemStack(Material.DIAMOND_PICKAXE, 1), Enchantment.DIG_SPEED, 1));
				npcas.spawn();
			}
			{
				NPCArmorStand npcas = NPCArmorStand.addNPCArmorStand(NPCType.SERVER_INFO_CREATIVE, new Location(getHubWorld(), -38.5, 78, -63.5, -90, 0));
				npcas.setBodyPose(EulerAngle.ZERO.setX(0.1).setY(0).setZ(0));
				npcas.setHeadPose(EulerAngle.ZERO.setX(0.15).setY(0).setZ(0));
				npcas.setLeftArmPose(EulerAngle.ZERO.setX(0).setY(0).setZ(0));
				npcas.setLeftLegPose(EulerAngle.ZERO.setX(0).setY(0).setZ(0));
				npcas.setRightArmPose(EulerAngle.ZERO.setX(-0.75).setY(0).setZ(0));
				npcas.setRightLegPose(EulerAngle.ZERO.setX(0).setY(0).setZ(0));
				npcas.setGravity(false);
				npcas.setVisible(false);
				npcas.setHelmet(new ItemStack(Material.GOLD_HELMET, 1));
				npcas.setChestplate(new ItemStack(Material.GOLD_CHESTPLATE, 1));
				npcas.setLeggings(new ItemStack(Material.GOLD_LEGGINGS, 1));
				npcas.setBoots(new ItemStack(Material.GOLD_BOOTS, 1));
				npcas.setItemInHand(new ItemStack(Material.WOOD_AXE, 1));
				npcas.spawn();
			}
			{
				NPCArmorStand npcas = NPCArmorStand.addNPCArmorStand(NPCType.SERVER_INFO_SURVIVAL, new Location(getHubWorld(), 7.5, 78, -65.5, 0, 0));
				npcas.setBodyPose(EulerAngle.ZERO.setX(0.1).setY(0).setZ(0));
				npcas.setHeadPose(EulerAngle.ZERO.setX(0.15).setY(0).setZ(0));
				npcas.setLeftArmPose(EulerAngle.ZERO.setX(0).setY(0).setZ(0));
				npcas.setLeftLegPose(EulerAngle.ZERO.setX(0).setY(0).setZ(0));
				npcas.setRightArmPose(EulerAngle.ZERO.setX(-0.75).setY(0).setZ(0));
				npcas.setRightLegPose(EulerAngle.ZERO.setX(0).setY(0).setZ(0));
				npcas.setGravity(false);
				npcas.setVisible(false);
				npcas.setHelmet(new ItemStack(Material.LEATHER_HELMET, 1));
				npcas.setChestplate(new ItemStack(Material.IRON_CHESTPLATE, 1));
				npcas.setLeggings(new ItemStack(Material.LEATHER_LEGGINGS, 1));
				npcas.setBoots(new ItemStack(Material.LEATHER_BOOTS, 1));
				npcas.setItemInHand(new ItemStack(Material.STONE_HOE, 1));
				npcas.spawn();
			}
			{
				NPCArmorStand npcas = NPCArmorStand.addNPCArmorStand(NPCType.SERVER_INFO_SKYBLOCK, new Location(getHubWorld(), -9.5, 78, 74.5, -180, 0));
				npcas.setBodyPose(EulerAngle.ZERO.setX(0.1).setY(0).setZ(0));
				npcas.setHeadPose(EulerAngle.ZERO.setX(0.15).setY(0).setZ(0));
				npcas.setLeftArmPose(EulerAngle.ZERO.setX(0).setY(0).setZ(0));
				npcas.setLeftLegPose(EulerAngle.ZERO.setX(0).setY(0).setZ(0));
				npcas.setRightArmPose(EulerAngle.ZERO.setX(-0.75).setY(0).setZ(0));
				npcas.setRightLegPose(EulerAngle.ZERO.setX(0).setY(0).setZ(0));
				npcas.setGravity(false);
				npcas.setVisible(false);
				npcas.setHelmet(new ItemStack(Material.LEATHER_HELMET, 1));
				npcas.setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE, 1));
				npcas.setLeggings(new ItemStack(Material.LEATHER_LEGGINGS, 1));
				npcas.setBoots(new ItemStack(Material.LEATHER_BOOTS, 1));
				npcas.setItemInHand(new ItemStack(Material.FISHING_ROD, 1));
				npcas.spawn();
			}
			{
				NPCArmorStand npcas = NPCArmorStand.addNPCArmorStand(NPCType.SERVER_INFO_MINIGAMES, new Location(getHubWorld(), -40.5, 78, 48.5, -90, 0));
				npcas.setBodyPose(EulerAngle.ZERO.setX(0.1).setY(0).setZ(0));
				npcas.setHeadPose(EulerAngle.ZERO.setX(0.15).setY(0).setZ(0));
				npcas.setLeftArmPose(EulerAngle.ZERO.setX(0).setY(0).setZ(0));
				npcas.setLeftLegPose(EulerAngle.ZERO.setX(0).setY(0).setZ(0));
				npcas.setRightArmPose(EulerAngle.ZERO.setX(-0.75).setY(0).setZ(0));
				npcas.setRightLegPose(EulerAngle.ZERO.setX(0).setY(0).setZ(0));
				npcas.setGravity(false);
				npcas.setVisible(false);
				npcas.setHelmet(new ItemStack(Material.LEATHER_HELMET, 1));
				npcas.setChestplate(new ItemStack(Material.GOLD_CHESTPLATE, 1));
				npcas.setLeggings(new ItemStack(Material.CHAINMAIL_LEGGINGS, 1));
				npcas.setBoots(new ItemStack(Material.DIAMOND_BOOTS, 1));
				npcas.setItemInHand(new ItemStack(Material.BOW, 1));
				npcas.spawn();
			}
		}
		
		private void registerLobbyItems(){
			ItemStack[] lobbyitems = new ItemStack[36];
			{
				ItemStack item = new ItemStack(Material.WRITTEN_BOOK);
				BookMeta itemmeta = (BookMeta) item.getItemMeta();
				itemmeta.setDisplayName("§4§nServer Rules");
				itemmeta.addPage("1");
				itemmeta.setPage(1, "   §6§lOrbitMines§4§lRules" + "\n" + "§0§m-------------------" + "\n" + "§4DO NOT§0 Advertise!" + "\n" + "§0Watch your Language!" + "\n" + "Listen to Staff!" + "\n" + "§4DO NOT§0 Abuse Bugs!" + "\n" + "§4DO NOT§0 Hack!" + "\n" + "§4DO NOT§0 Spam!" + "\n" + "§4DO NOT§0 Bully Players!" + "\n" + "§0\n" + "§0§lHave Fun!");
				itemmeta.setAuthor("§6§lOrbitMines §4§lNetwork");
				item.setItemMeta(itemmeta);
				lobbyitems[0] = item;
			}
			{
				ItemStack item = new ItemStack(Material.EXP_BOTTLE, 1);
				ItemMeta itemmeta = item.getItemMeta();
				itemmeta.setDisplayName("§d§nAchievements");
				item.setItemMeta(itemmeta);
				lobbyitems[1] = item;
			}
			{
				ItemStack item = new ItemStack(Material.REDSTONE_TORCH_ON, 1);
				ItemMeta itemmeta = item.getItemMeta();
				itemmeta.setDisplayName("§c§nSettings");
				item.setItemMeta(itemmeta);
				lobbyitems[3] = item;
			}
			{
				ItemStack item = new ItemStack(Material.ENDER_PEARL, 1);
				ItemMeta itemmeta = item.getItemMeta();
				itemmeta.setDisplayName("§3§nServer Selector");
				item.setItemMeta(itemmeta);
				lobbyitems[4] = item;
			}
			{
				ItemStack item = new ItemStack(Material.ENDER_CHEST, 1);
				ItemMeta itemmeta = item.getItemMeta();
				itemmeta.setDisplayName("§9§nCosmetic Perks");
				item.setItemMeta(itemmeta);
				lobbyitems[7] = item;
			}
			{
				ItemStack item = new ItemStack(Material.FEATHER, 1);
				ItemMeta itemmeta = item.getItemMeta();
				itemmeta.setDisplayName("§f§nFly");
				item.setItemMeta(itemmeta);
				lobbyitems[8] = item;
			}
			this.lobbyitems = lobbyitems;
		}
		
		private void registerMindCraft(){
			World w = getHubWorld();
			
			HashMap<Integer, List<Block>> blocksforturns = new HashMap<Integer, List<Block>>();
			blocksforturns.put(0, Arrays.asList(w.getBlockAt(new Location(w, 42, 75, 5)), w.getBlockAt(new Location(w, 40, 75, 5)), w.getBlockAt(new Location(w, 38, 75, 5)), w.getBlockAt(new Location(w, 36, 75, 5))));
			blocksforturns.put(1, Arrays.asList(w.getBlockAt(new Location(w, 42, 74, 8)), w.getBlockAt(new Location(w, 40, 74, 8)), w.getBlockAt(new Location(w, 38, 74, 8)), w.getBlockAt(new Location(w, 36, 74, 8))));
			blocksforturns.put(2, Arrays.asList(w.getBlockAt(new Location(w, 42, 74, 10)), w.getBlockAt(new Location(w, 40, 74, 10)), w.getBlockAt(new Location(w, 38, 74, 10)), w.getBlockAt(new Location(w, 36, 74, 10))));
			blocksforturns.put(3, Arrays.asList(w.getBlockAt(new Location(w, 42, 74, 12)), w.getBlockAt(new Location(w, 40, 74, 12)), w.getBlockAt(new Location(w, 38, 74, 12)), w.getBlockAt(new Location(w, 36, 74, 12))));
			blocksforturns.put(4, Arrays.asList(w.getBlockAt(new Location(w, 42, 74, 14)), w.getBlockAt(new Location(w, 40, 74, 14)), w.getBlockAt(new Location(w, 38, 74, 14)), w.getBlockAt(new Location(w, 36, 74, 14))));
			blocksforturns.put(5, Arrays.asList(w.getBlockAt(new Location(w, 42, 74, 16)), w.getBlockAt(new Location(w, 40, 74, 16)), w.getBlockAt(new Location(w, 38, 74, 16)), w.getBlockAt(new Location(w, 36, 74, 16))));
			blocksforturns.put(6, Arrays.asList(w.getBlockAt(new Location(w, 42, 74, 18)), w.getBlockAt(new Location(w, 40, 74, 18)), w.getBlockAt(new Location(w, 38, 74, 18)), w.getBlockAt(new Location(w, 36, 74, 18))));
			blocksforturns.put(7, Arrays.asList(w.getBlockAt(new Location(w, 42, 74, 20)), w.getBlockAt(new Location(w, 40, 74, 20)), w.getBlockAt(new Location(w, 38, 74, 20)), w.getBlockAt(new Location(w, 36, 74, 20))));
			blocksforturns.put(8, Arrays.asList(w.getBlockAt(new Location(w, 42, 74, 22)), w.getBlockAt(new Location(w, 40, 74, 22)), w.getBlockAt(new Location(w, 38, 74, 22)), w.getBlockAt(new Location(w, 36, 74, 22))));
			blocksforturns.put(9, Arrays.asList(w.getBlockAt(new Location(w, 42, 74, 24)), w.getBlockAt(new Location(w, 40, 74, 24)), w.getBlockAt(new Location(w, 38, 74, 24)), w.getBlockAt(new Location(w, 36, 74, 24))));
			blocksforturns.put(10, Arrays.asList(w.getBlockAt(new Location(w, 42, 74, 26)), w.getBlockAt(new Location(w, 40, 74, 26)), w.getBlockAt(new Location(w, 38, 74, 26)), w.getBlockAt(new Location(w, 36, 74, 26))));
			blocksforturns.put(11, Arrays.asList(w.getBlockAt(new Location(w, 42, 76, 29)), w.getBlockAt(new Location(w, 40, 76, 29)), w.getBlockAt(new Location(w, 38, 76, 29)), w.getBlockAt(new Location(w, 36, 76, 29))));
			
			setMCBlocksForTurn(blocksforturns);
			
			HashMap<Integer, List<Block>> statusforturns = new HashMap<Integer, List<Block>>();
			statusforturns.put(1, Arrays.asList(w.getBlockAt(new Location(w, 32, 76, 8)), w.getBlockAt(new Location(w, 32, 77, 8)), w.getBlockAt(new Location(w, 32, 78, 8)), w.getBlockAt(new Location(w, 32, 79, 8))));
			statusforturns.put(2, Arrays.asList(w.getBlockAt(new Location(w, 32, 76, 10)), w.getBlockAt(new Location(w, 32, 77, 10)), w.getBlockAt(new Location(w, 32, 78, 10)), w.getBlockAt(new Location(w, 32, 79, 10))));
			statusforturns.put(3, Arrays.asList(w.getBlockAt(new Location(w, 32, 76, 12)), w.getBlockAt(new Location(w, 32, 77, 12)), w.getBlockAt(new Location(w, 32, 78, 12)), w.getBlockAt(new Location(w, 32, 79, 12))));
			statusforturns.put(4, Arrays.asList(w.getBlockAt(new Location(w, 32, 76, 14)), w.getBlockAt(new Location(w, 32, 77, 14)), w.getBlockAt(new Location(w, 32, 78, 14)), w.getBlockAt(new Location(w, 32, 79, 14))));
			statusforturns.put(5, Arrays.asList(w.getBlockAt(new Location(w, 32, 76, 16)), w.getBlockAt(new Location(w, 32, 77, 16)), w.getBlockAt(new Location(w, 32, 78, 16)), w.getBlockAt(new Location(w, 32, 79, 16))));
			statusforturns.put(6, Arrays.asList(w.getBlockAt(new Location(w, 32, 76, 18)), w.getBlockAt(new Location(w, 32, 77, 18)), w.getBlockAt(new Location(w, 32, 78, 18)), w.getBlockAt(new Location(w, 32, 79, 18))));
			statusforturns.put(7, Arrays.asList(w.getBlockAt(new Location(w, 32, 76, 20)), w.getBlockAt(new Location(w, 32, 77, 20)), w.getBlockAt(new Location(w, 32, 78, 20)), w.getBlockAt(new Location(w, 32, 79, 20))));
			statusforturns.put(8, Arrays.asList(w.getBlockAt(new Location(w, 32, 76, 22)), w.getBlockAt(new Location(w, 32, 77, 22)), w.getBlockAt(new Location(w, 32, 78, 22)), w.getBlockAt(new Location(w, 32, 79, 22))));
			statusforturns.put(9, Arrays.asList(w.getBlockAt(new Location(w, 32, 76, 24)), w.getBlockAt(new Location(w, 32, 77, 24)), w.getBlockAt(new Location(w, 32, 78, 24)), w.getBlockAt(new Location(w, 32, 79, 24))));
			statusforturns.put(10, Arrays.asList(w.getBlockAt(new Location(w, 32, 76, 26)), w.getBlockAt(new Location(w, 32, 77, 26)), w.getBlockAt(new Location(w, 32, 78, 26)), w.getBlockAt(new Location(w, 32, 79, 26))));
			
			setMCBlocksForTurnStatus(statusforturns);
		}
		
		private void registerServerPortals(){
			this.serverportals = new HashMap<Server, List<Block>>();
			setServerPortal(Server.KITPVP, Utils.getBlocksBetween(new Location(getHubWorld(), -52, 75, 6), new Location(getHubWorld(), -52, 87, 0)));
			setServerPortal(Server.PRISON, Utils.getBlocksBetween(new Location(getHubWorld(), -46, 87, -37), new Location(getHubWorld(), -46, 75, -31)));
			setServerPortal(Server.CREATIVE, Utils.getBlocksBetween(new Location(getHubWorld(), -41, 75, -61), new Location(getHubWorld(), -41, 87, -67)));
			setServerPortal(Server.SURVIVAL, Utils.getBlocksBetween(new Location(getHubWorld(), 4, 75, -68), new Location(getHubWorld(), 10, 87, -68)));
			setServerPortal(Server.SKYBLOCK, Utils.getBlocksBetween(new Location(getHubWorld(), -7, 75, 76), new Location(getHubWorld(), -13, 86, 76)));
			setServerPortal(Server.MINIGAMES, Utils.getBlocksBetween(new Location(getHubWorld(), -43, 75, 51), new Location(getHubWorld(), -43, 87, 45)));
		}
		
		private void registerWaterFalls(){
			this.waterfalls = new ArrayList<Location>();
			addWaterfall(new Location(getHubWorld(), -16.5, 74.25, 56.5));
			addWaterfall(new Location(getHubWorld(), 40.5, 81.25, -64.5));
		}
	}
	
	public static class KitPvPServer {
		
		private boolean freekitenabled;
		private World kitpvpworld;
		private World arenaworld;
		private Location spawn;
		private List<Map> maps;
		private Map currentmap;
		private HashMap<Projectile, ProjectileType> projectiles;
		private ActiveBooster booster;
		private ItemStack[] lobbyitems;
		private ItemStack[] spectatoritems;
		
		public KitPvPServer(){
			Utils.removeAllEntities();
			
			ServerStorage.server = Server.KITPVP;
			ServerStorage.spawnbuilders = "§b§lMod §brienk222";
			ServerStorage.kitpvp = this;
			this.kitpvpworld = Bukkit.getWorld("KitPvPLobby");
			ServerStorage.lobbyworld = this.kitpvpworld;
			this.arenaworld = Bukkit.getWorld("KitPvPArenas");
			this.spawn = new Location(getKitPvPWorld(), -0.5, 74, -0.5, 90, 0);
			this.projectiles = new HashMap<Projectile, ProjectileType>();
			
			spawnNPCs();
			registerLobbyItems();
			registerSpectatorItems();
			registerMaps();
			registerKits();
			setRandomMap();
			
			for(Player p : Bukkit.getOnlinePlayers()){
				p.kickPlayer("§6§lOrbitMines§4§lNetwork\n  §7Restarting §c§lKitPvP§7 Server...");
			}
		}

		public boolean isFreeKitEnabled(){
			return freekitenabled;
		}
		public void setFreeKitEnabled(boolean freekitenabled){
			this.freekitenabled = freekitenabled;
		}
		
		public World getKitPvPWorld(){
			return kitpvpworld;
		}
		public void setKitPvPWorld(World kitpvpworld){
			this.kitpvpworld = kitpvpworld;
		}
		
		public World getArenaWorld(){
			return arenaworld;
		}
		public void setArenaWorld(World arenaworld){
			this.arenaworld = arenaworld;
		}

		public Location getSpawn(){
			return spawn;
		}
		public void setSpawn(Location spawn){
			this.spawn = spawn;
		}
		
		public List<Map> getMaps(){
			return maps;
		}
		public void setMaps(List<Map> maps){
			this.maps = maps;
		}
		
		public Map getCurrentMap(){
			return currentmap;
		}
		public void setCurrentMap(Map currentmap){
			this.currentmap = currentmap;
		}
		public void setRandomMap(){
			this.currentmap = maps.get(new Random().nextInt(maps.size()));
		}
		
		public HashMap<Projectile, ProjectileType> getProjectiles(){
			return projectiles;
		}
		public void setProjectiles(HashMap<Projectile, ProjectileType> projectiles){
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

		public ItemStack[] getLobbyItems(){
			return lobbyitems;
		}
		public void setLobbyItems(ItemStack[] lobbyitems){
			this.lobbyitems = lobbyitems;
		}
		public void giveLobbyItems(OMPlayer omplayer){
			Player p = omplayer.getPlayer();
			
			omplayer.clearInventory();
			omplayer.clearPotionEffects();
			omplayer.addPotionEffect(PotionEffectType.SPEED, 100000, 0);
			p.getInventory().setContents(getLobbyItems());
		}
		
		public ItemStack[] getSpectatorItems(){
			return spectatoritems;
		}
		public void setSpectatorItems(ItemStack[] spectatoritems){
			this.spectatoritems = spectatoritems;
		}
		public void giveSpectatorItems(OMPlayer omplayer){
			Player p = omplayer.getPlayer();
			
			omplayer.clearInventory();
			p.getInventory().setContents(getSpectatorItems());
		}
		
		private void registerMaps(){
			List<Map> maps = new ArrayList<Map>();
			{
				Map map = new Map("Snow Town");
				map.setBuilders("§4§lOwner §4O_o_Fadi_o_O\n§b§lMod §brienk222");
				map.setSpawns(Arrays.asList(new Location(getArenaWorld(), -63.5, 9, -1182.5, -45, 0), new Location(getArenaWorld(), -92.5, 14, -1079.5, -166, 0), new Location(getArenaWorld(), -134.5, 9, -1131.5, -113, 0), new Location(getArenaWorld(), -115, 12.5, -1187.5, -70, 0), new Location(getArenaWorld(), -90.5, 9, -1171.5, 143, 0), new Location(getArenaWorld(), -62.5, 9, -1138.5, -71, 0), new Location(getArenaWorld(), -108.5, 11, -1150.5, -139, 0), new Location(getArenaWorld(), -91.5, 10, -1205.5, -30, 0), new Location(getArenaWorld(), -144.5, 10, -1165.5, -75, 0), new Location(getArenaWorld(), -66.5, 10, -1098.5, -126, 0)));
				map.setSpectatorSpawn(new Location(getArenaWorld(), -93.5, 22, -1154.5, 145, 0));
				map.setMaxY(30);
				maps.add(map);
			}
			{
				Map map = new Map("Mountain Village");
				map.setBuilders("§b§lMod §brienk222\n§b§lMod §bsharewoods\n§4§lOwner §4O_o_Fadi_o_O");
				map.setSpawns(Arrays.asList(new Location(getArenaWorld(), -352.5, 5, -1366.5, -54, 0), new Location(getArenaWorld(), -317.5, 4, -1329.5, 46, 0), new Location(getArenaWorld(), -283.5, 4, -1296.5, 165, 0), new Location(getArenaWorld(), -303.5, 5, -1315.5, 39, 0), new Location(getArenaWorld(), -284.5, 4, -1348.5, 168, 0), new Location(getArenaWorld(), -303.5, 4, -1345.5, 135, 0), new Location(getArenaWorld(), -316.5, 9, -1364.5, 30, 0), new Location(getArenaWorld(), -337.5, 5, -1344.5, -126, 0), new Location(getArenaWorld(), -349.5, 4, -1327.5, -113, 0), new Location(getArenaWorld(), -323.5, 5, -1296.5, -161, 0)));
				map.setSpectatorSpawn(new Location(getArenaWorld(), -308.5, 16, -1326.5, 137, 0));
				map.setMaxY(26);
				maps.add(map);
			}
			{
				Map map = new Map("Desert");
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
				kit.setItem(0, Utils.hideFlags(Utils.setUnbreakable(Utils.setDisplayname(new ItemStack(Material.STONE_SWORD), "§b§lKnight §a§lLvL 1§8 || §bWeapon")), 4));
				kit.setItem(1, Utils.setDurability(Utils.setDisplayname(new ItemStack(Material.POTION), "§b§lKnight §a§lLvL 1§8 || §cHealing Potion"), 8197));

				kit.setHelmet(Utils.hideFlags(Utils.setUnbreakable(Utils.setDisplayname(new ItemStack(Material.CHAINMAIL_HELMET), "§b§lKnight §a§lLvL 1§8 || §bHelmet")), 4));
				kit.setChestplate(Utils.hideFlags(Utils.setUnbreakable(Utils.setDisplayname(new ItemStack(Material.CHAINMAIL_CHESTPLATE), "§b§lKnight §a§lLvL 1§8 || §bChestplate")), 4));
				kit.setLeggings(Utils.hideFlags(Utils.setUnbreakable(Utils.setDisplayname(new ItemStack(Material.CHAINMAIL_LEGGINGS), "§b§lKnight §a§lLvL 1§8 || §bLeggings")), 4));
				kit.setBoots(Utils.hideFlags(Utils.setUnbreakable(Utils.setDisplayname(new ItemStack(Material.CHAINMAIL_BOOTS), "§b§lKnight §a§lLvL 1§8 || §bBoots")), 4));
			}
			{// Knight Level 2 \\
				Kit kit = new Kit(KitPvPKit.KNIGHT.getName() + " 2");
				kit.setItem(0, Utils.hideFlags(Utils.setUnbreakable(Utils.setDisplayname(new ItemStack(Material.STONE_SWORD), "§b§lKnight §a§lLvL 2§8 || §bWeapon")), 4));
				kit.setItem(1, Utils.setDurability(Utils.setDisplayname(new ItemStack(Material.POTION), "§b§lKnight §a§lLvL 2§8 || §cHealing Potion"), 16389));

				kit.setHelmet(Utils.hideFlags(Utils.setUnbreakable(Utils.setDisplayname(new ItemStack(Material.CHAINMAIL_HELMET), "§b§lKnight §a§lLvL 2§8 || §bHelmet")), 4));
				kit.setChestplate(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.setDisplayname(new ItemStack(Material.CHAINMAIL_CHESTPLATE), "§b§lKnight §a§lLvL 2§8 || §bChestplate"), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
				kit.setLeggings(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.setDisplayname(new ItemStack(Material.CHAINMAIL_LEGGINGS), "§b§lKnight §a§lLvL 2§8 || §bLeggings"), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
				kit.setBoots(Utils.hideFlags(Utils.setUnbreakable(Utils.setDisplayname(new ItemStack(Material.CHAINMAIL_BOOTS), "§b§lKnight §a§lLvL 2§8 || §bBoots")), 4));
			}
			{// Knight Level 3 \\
				Kit kit = new Kit(KitPvPKit.KNIGHT.getName() + " 3");
				kit.setItem(0, Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.setDisplayname(new ItemStack(Material.STONE_SWORD), "§b§lKnight §a§lLvL 3§8 || §bWeapon"), Enchantment.DAMAGE_ALL, 1)), 4));
				kit.setItem(1, Utils.setDurability(Utils.setDisplayname(new ItemStack(Material.POTION), "§b§lKnight §a§lLvL 3§8 || §cHealing Potion"), 16389));
				kit.setItem(2, Utils.setDurability(Utils.setDisplayname(new ItemStack(Material.POTION), "§b§lKnight §a§lLvL 3§8 || §cHealing Potion"), 16389));

				kit.setHelmet(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.setDisplayname(new ItemStack(Material.CHAINMAIL_HELMET), "§b§lKnight §a§lLvL 3§8 || §bHelmet"), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
				kit.setChestplate(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.setDisplayname(new ItemStack(Material.CHAINMAIL_CHESTPLATE), "§b§lKnight §a§lLvL 3§8 || §bChestplate"), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
				kit.setLeggings(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.setDisplayname(new ItemStack(Material.CHAINMAIL_LEGGINGS), "§b§lKnight §a§lLvL 3§8 || §bLeggings"), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
				kit.setBoots(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.setDisplayname(new ItemStack(Material.CHAINMAIL_BOOTS), "§b§lKnight §a§lLvL 3§8 || §bBoots"), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
			}
			{// Archer Level 1 \\
				Kit kit = new Kit(KitPvPKit.ARCHER.getName() + " 1");
				kit.setItem(0, Utils.hideFlags(Utils.setUnbreakable(Utils.setDisplayname(new ItemStack(Material.STONE_SWORD), "§b§lArcher §a§lLvL 1§8 || §bWeapon")), 4));
				kit.setItem(1, Utils.hideFlags(Utils.setUnbreakable(Utils.setDisplayname(new ItemStack(Material.BOW), "§b§lArcher §a§lLvL 1§8 || §bBow")), 4));
				kit.setItem(2, Utils.setDisplayname(new ItemStack(Material.ARROW, 32), "§b§lArcher §a§lLvL 1§8 || §bArrow"));
				kit.setItem(3, Utils.setDurability(Utils.setDisplayname(new ItemStack(Material.POTION), "§b§lArcher §a§lLvL 1§8 || §cHealing Potion"), 8197));

				kit.setHelmet(Utils.hideFlags(Utils.setUnbreakable(Utils.addColor(Utils.setDisplayname(new ItemStack(Material.LEATHER_HELMET), "§b§lArcher §a§lLvL 1§8 || §bHelmet"), Color.fromBGR(204, 255, 51))), 4));
				kit.setChestplate(Utils.hideFlags(Utils.setUnbreakable(Utils.addColor(Utils.setDisplayname(new ItemStack(Material.LEATHER_CHESTPLATE), "§b§lArcher §a§lLvL 1§8 || §bChestplate"), Color.fromBGR(204, 255, 51))), 4));
				kit.setLeggings(Utils.hideFlags(Utils.setUnbreakable(Utils.addColor(Utils.setDisplayname(new ItemStack(Material.LEATHER_LEGGINGS), "§b§lArcher §a§lLvL 1§8 || §bLeggings"), Color.fromBGR(204, 255, 51))), 4));
				kit.setBoots(Utils.hideFlags(Utils.setUnbreakable(Utils.addColor(Utils.setDisplayname(new ItemStack(Material.LEATHER_BOOTS), "§b§lArcher §a§lLvL 1§8 || §bBoots"), Color.fromBGR(204, 255, 51))), 4));
			}
			{// Archer Level 2 \\
				Kit kit = new Kit(KitPvPKit.ARCHER.getName() + " 2");
				kit.setItem(0, Utils.hideFlags(Utils.setUnbreakable(Utils.setDisplayname(new ItemStack(Material.STONE_SWORD), "§b§lArcher §a§lLvL 2§8 || §bWeapon")), 4));
				kit.setItem(1, Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.setLore(Utils.setDisplayname(new ItemStack(Material.BOW), "§b§lArcher §a§lLvL 2§8 || §bBow"), ProjectileType.LIGHTNING_I.addEnchantment(new ArrayList<String>())), Enchantment.DURABILITY, 1)), 5));
				kit.setItem(2, Utils.setDisplayname(new ItemStack(Material.ARROW, 32), "§b§lArcher §a§lLvL 2§8 || §bArrow"));
				kit.setItem(3, Utils.setDurability(Utils.setDisplayname(new ItemStack(Material.POTION), "§b§lArcher §a§lLvL 2§8 || §cHealing Potion"), 8197));

				kit.setHelmet(Utils.hideFlags(Utils.setUnbreakable(Utils.addColor(Utils.setDisplayname(new ItemStack(Material.LEATHER_HELMET), "§b§lArcher §a§lLvL 2§8 || §bHelmet"), Color.fromBGR(204, 255, 51))), 4));
				kit.setChestplate(Utils.hideFlags(Utils.setUnbreakable(Utils.addColor(Utils.setDisplayname(new ItemStack(Material.LEATHER_CHESTPLATE), "§b§lArcher §a§lLvL 2§8 || §bChestplate"), Color.fromBGR(204, 255, 51))), 4));
				kit.setLeggings(Utils.hideFlags(Utils.setUnbreakable(Utils.addColor(Utils.setDisplayname(new ItemStack(Material.LEATHER_LEGGINGS), "§b§lArcher §a§lLvL 2§8 || §bLeggings"), Color.fromBGR(204, 255, 51))), 4));
				kit.setBoots(Utils.hideFlags(Utils.setUnbreakable(Utils.addColor(Utils.setDisplayname(new ItemStack(Material.LEATHER_BOOTS), "§b§lArcher §a§lLvL 2§8 || §bBoots"), Color.fromBGR(204, 255, 51))), 4));
			}
			{// Archer Level 3 \\
				Kit kit = new Kit(KitPvPKit.ARCHER.getName() + " 3");
				kit.setItem(0, Utils.hideFlags(Utils.setUnbreakable(Utils.setDisplayname(new ItemStack(Material.STONE_SWORD), "§b§lArcher §a§lLvL 3§8 || §bWeapon")), 4));
				kit.setItem(1, Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.setLore(Utils.setDisplayname(new ItemStack(Material.BOW), "§b§lArcher §a§lLvL 3§8 || §bBow"), ProjectileType.LIGHTNING_I.addEnchantment(new ArrayList<String>())), Enchantment.ARROW_DAMAGE, 1)), 4));
				kit.setItem(2, Utils.setDisplayname(new ItemStack(Material.ARROW, 32), "§b§lArcher §a§lLvL 3§8 || §bArrow"));
				kit.setItem(3, Utils.setDurability(Utils.setDisplayname(new ItemStack(Material.POTION), "§b§lArcher §a§lLvL 3§8 || §cHealing Potion"), 16389));

				kit.setHelmet(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.addColor(Utils.setDisplayname(new ItemStack(Material.LEATHER_HELMET), "§b§lArcher §a§lLvL 3§8 || §bHelmet"), Color.fromBGR(204, 255, 51)), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
				kit.setChestplate(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.addColor(Utils.setDisplayname(new ItemStack(Material.LEATHER_CHESTPLATE), "§b§lArcher §a§lLvL 3§8 || §bChestplate"), Color.fromBGR(204, 255, 51)), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
				kit.setLeggings(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.addColor(Utils.setDisplayname(new ItemStack(Material.LEATHER_LEGGINGS), "§b§lArcher §a§lLvL 3§8 || §bLeggings"), Color.fromBGR(204, 255, 51)), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
				kit.setBoots(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.addColor(Utils.setDisplayname(new ItemStack(Material.LEATHER_BOOTS), "§b§lArcher §a§lLvL 3§8 || §bBoots"), Color.fromBGR(204, 255, 51)), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
			}
			{// Soldier Level 1 \\
				Kit kit = new Kit(KitPvPKit.SOLDIER.getName() + " 1");
				kit.setItem(0, Utils.hideFlags(Utils.setUnbreakable(Utils.setDisplayname(new ItemStack(Material.STONE_SWORD), "§b§lSoldier §a§lLvL 1§8 || §bWeapon")), 4));
				kit.setItem(1, Utils.hideFlags(Utils.setUnbreakable(Utils.setDisplayname(new ItemStack(Material.BOW), "§b§lSoldier §a§lLvL 1§8 || §bBow")), 4));
				kit.setItem(2, Utils.setDisplayname(new ItemStack(Material.ARROW, 16), "§b§lSoldier §a§lLvL 1§8 || §bArrow"));
				
				kit.setHelmet(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.setDisplayname(new ItemStack(Material.LEATHER_HELMET), "§b§lSoldier §a§lLvL 1§8 || §bHelmet"), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
				kit.setChestplate(Utils.hideFlags(Utils.setUnbreakable(Utils.setDisplayname(new ItemStack(Material.IRON_CHESTPLATE), "§b§lSoldier §a§lLvL 1§8 || §bChestplate")), 4));
				kit.setLeggings(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.setDisplayname(new ItemStack(Material.LEATHER_LEGGINGS), "§b§lSoldier §a§lLvL 1§8 || §bLeggings"), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
				kit.setBoots(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.setDisplayname(new ItemStack(Material.LEATHER_BOOTS), "§b§lSoldier §a§lLvL 1§8 || §bBoots"), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
			}
			{// Soldier Level 2 \\
				Kit kit = new Kit(KitPvPKit.SOLDIER.getName() + " 2");
				kit.setItem(0, Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.setLore(Utils.setDisplayname(new ItemStack(Material.STONE_SWORD), "§b§lSoldier §a§lLvL 2§8 || §bWeapon"), ItemType.LIGHTNING_I.addEnchantment(new ArrayList<String>())), Enchantment.DURABILITY, 1)), 5));
				kit.setItem(1, Utils.hideFlags(Utils.setUnbreakable(Utils.setDisplayname(new ItemStack(Material.BOW), "§b§lSoldier §a§lLvL 2§8 || §bBow")), 4));
				kit.setItem(2, Utils.setDisplayname(new ItemStack(Material.ARROW, 16), "§b§lSoldier §a§lLvL 2§8 || §bArrow"));
				
				kit.setHelmet(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.setDisplayname(new ItemStack(Material.LEATHER_HELMET), "§b§lSoldier §a§lLvL 2§8 || §bHelmet"), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
				kit.setChestplate(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.setDisplayname(new ItemStack(Material.IRON_CHESTPLATE), "§b§lSoldier §a§lLvL 2§8 || §bChestplate"), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
				kit.setLeggings(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.setDisplayname(new ItemStack(Material.LEATHER_LEGGINGS), "§b§lSoldier §a§lLvL 2§8 || §bLeggings"), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
				kit.setBoots(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.setDisplayname(new ItemStack(Material.LEATHER_BOOTS), "§b§lSoldier §a§lLvL 2§8 || §bBoots"), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
			}
			{// Soldier Level 3 \\
				Kit kit = new Kit(KitPvPKit.SOLDIER.getName() + " 3");
				kit.setItem(0, Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.setLore(Utils.setDisplayname(new ItemStack(Material.STONE_SWORD), "§b§lSoldier §a§lLvL 3§8 || §bWeapon"), ItemType.LIGHTNING_II.addEnchantment(new ArrayList<String>())), Enchantment.DAMAGE_ALL, 1)), 4));
				kit.setItem(1, Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.setDisplayname(new ItemStack(Material.BOW), "§b§lSoldier §a§lLvL 3§8 || §bBow"), Enchantment.ARROW_DAMAGE, 1)), 4));
				kit.setItem(2, Utils.setDisplayname(new ItemStack(Material.ARROW, 20), "§b§lSoldier §a§lLvL 3§8 || §bArrow"));
				
				kit.setHelmet(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.setDisplayname(new ItemStack(Material.LEATHER_HELMET), "§b§lSoldier §a§lLvL 3§8 || §bHelmet"), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
				kit.setChestplate(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.setDisplayname(new ItemStack(Material.IRON_CHESTPLATE), "§b§lSoldier §a§lLvL 3§8 || §bChestplate"), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
				kit.setLeggings(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.setDisplayname(new ItemStack(Material.LEATHER_LEGGINGS), "§b§lSoldier §a§lLvL 3§8 || §bLeggings"), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
				kit.setBoots(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.setDisplayname(new ItemStack(Material.LEATHER_BOOTS), "§b§lSoldier §a§lLvL 3§8 || §bBoots"), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
			}
			{// Wizard Level 1 \\
				Kit kit = new Kit(KitPvPKit.WIZARD.getName() + " 1");
				kit.setItem(0, Utils.hideFlags(Utils.setUnbreakable(Utils.setDisplayname(new ItemStack(Material.STONE_SWORD), "§b§lWizard §a§lLvL 1§8 || §bWeapon")), 4));
				kit.setItem(1, Utils.setDurability(Utils.setDisplayname(new ItemStack(Material.POTION), "§b§lWizard §a§lLvL 1§8 || §dRegeneration Potion"), 8193));
				kit.setItem(2, Utils.setDurability(Utils.setDisplayname(new ItemStack(Material.POTION), "§b§lWizard §a§lLvL 1§8 || §bSpeed Potion"), 8194));
				kit.setItem(3, Utils.setDurability(Utils.setDisplayname(new ItemStack(Material.POTION), "§b§lWizard §a§lLvL 1§8 || §7Weakness Potion"), 16392));
				kit.setItem(4, Utils.setDurability(Utils.setDisplayname(new ItemStack(Material.POTION), "§b§lWizard §a§lLvL 1§8 || §cHealing Potion"), 16389));
				kit.setItem(5, Utils.setDurability(Utils.setDisplayname(new ItemStack(Material.POTION), "§b§lWizard §a§lLvL 1§8 || §cHealing Potion"), 16389));

				kit.setHelmet(Utils.hideFlags(Utils.setUnbreakable(Utils.setDisplayname(new ItemStack(Material.GOLD_HELMET), "§b§lWizard §a§lLvL 1§8 || §bHelmet")), 4));
				kit.setChestplate(Utils.hideFlags(Utils.setUnbreakable(Utils.setDisplayname(new ItemStack(Material.GOLD_CHESTPLATE), "§b§lWizard §a§lLvL 1§8 || §bChestplate")), 4));
				kit.setLeggings(Utils.hideFlags(Utils.setUnbreakable(Utils.setDisplayname(new ItemStack(Material.GOLD_LEGGINGS), "§b§lWizard §a§lLvL 1§8 || §bLeggings")), 4));
				kit.setBoots(Utils.hideFlags(Utils.setUnbreakable(Utils.setDisplayname(new ItemStack(Material.GOLD_BOOTS), "§b§lWizard §a§lLvL 1§8 || §bBoots")), 4));
			}
			{// Wizard Level 2 \\
				Kit kit = new Kit(KitPvPKit.WIZARD.getName() + " 2");
				kit.setItem(0, Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.setDisplayname(new ItemStack(Material.STONE_SWORD), "§b§lWizard §a§lLvL 2§8 || §bWeapon"), Enchantment.DAMAGE_ALL, 1)), 4));
				kit.setItem(1, Utils.setDurability(Utils.setDisplayname(new ItemStack(Material.POTION), "§b§lWizard §a§lLvL 2§8 || §dRegeneration Potion"), 8193));
				kit.setItem(2, Utils.setDurability(Utils.setDisplayname(new ItemStack(Material.POTION), "§b§lWizard §a§lLvL 2§8 || §bSpeed Potion"), 8194));
				kit.setItem(3, Utils.setDurability(Utils.setDisplayname(new ItemStack(Material.POTION), "§b§lWizard §a§lLvL 2§8 || §7Weakness Potion"), 16392));
				kit.setItem(4, Utils.setDurability(Utils.setDisplayname(new ItemStack(Material.POTION), "§b§lWizard §a§lLvL 2§8 || §cHealing Potion"), 16389));
				kit.setItem(5, Utils.setDurability(Utils.setDisplayname(new ItemStack(Material.POTION), "§b§lWizard §a§lLvL 2§8 || §cHealing Potion"), 16389));
				kit.setItem(6, Utils.hideFlags(Utils.addEnchantment(Utils.setLore(Utils.setDisplayname(new ItemStack(Material.BLAZE_ROD), "§b§lWizard §a§lLvL 2§8 || §cFire Wand"), ItemType.FIRE_SPELL_I.addEnchantment(new ArrayList<String>())), Enchantment.DURABILITY, 1), 5));
				
				kit.setHelmet(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.setDisplayname(new ItemStack(Material.GOLD_HELMET), "§b§lWizard §a§lLvL 2§8 || §bHelmet"), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
				kit.setChestplate(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.setDisplayname(new ItemStack(Material.GOLD_CHESTPLATE), "§b§lWizard §a§lLvL 2§8 || §bChestplate"), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
				kit.setLeggings(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.setDisplayname(new ItemStack(Material.GOLD_LEGGINGS), "§b§lWizard §a§lLvL 2§8 || §bLeggings"), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
				kit.setBoots(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.setDisplayname(new ItemStack(Material.GOLD_BOOTS), "§b§lWizard §a§lLvL 2§8 || §bBoots"), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
			}
			{// Wizard Level 3 \\
				Kit kit = new Kit(KitPvPKit.WIZARD.getName() + " 3");
				kit.setItem(0, Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.setDisplayname(new ItemStack(Material.STONE_SWORD), "§b§lWizard §a§lLvL 3§8 || §bWeapon"), Enchantment.DAMAGE_ALL, 1)), 4));
				kit.setItem(1, Utils.setDurability(Utils.setDisplayname(new ItemStack(Material.POTION), "§b§lWizard §a§lLvL 3§8 || §dRegeneration Potion"), 8193));
				kit.setItem(2, Utils.setDurability(Utils.setDisplayname(new ItemStack(Material.POTION), "§b§lWizard §a§lLvL 3§8 || §bSpeed Potion"), 8194));
				kit.setItem(3, Utils.setDurability(Utils.setDisplayname(new ItemStack(Material.POTION), "§b§lWizard §a§lLvL 3§8 || §7Weakness Potion"), 16392));
				kit.setItem(4, Utils.setDurability(Utils.setDisplayname(new ItemStack(Material.POTION), "§b§lWizard §a§lLvL 3§8 || §cHealing Potion"), 16389));
				kit.setItem(5, Utils.setDurability(Utils.setDisplayname(new ItemStack(Material.POTION), "§b§lWizard §a§lLvL 3§8 || §cHealing Potion"), 16389));
				kit.setItem(6, Utils.hideFlags(Utils.addEnchantment(Utils.setLore(Utils.setDisplayname(new ItemStack(Material.BLAZE_ROD), "§b§lWizard §a§lLvL 3§8 || §cFire Wand"), ItemType.FIRE_SPELL_II.addEnchantment(new ArrayList<String>())), Enchantment.DURABILITY, 1), 5));
				
				kit.setHelmet(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.setDisplayname(new ItemStack(Material.GOLD_HELMET), "§b§lWizard §a§lLvL 3§8 || §bHelmet"), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));
				kit.setChestplate(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.setDisplayname(new ItemStack(Material.GOLD_CHESTPLATE), "§b§lWizard §a§lLvL 3§8 || §bChestplate"), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));
				kit.setLeggings(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.setDisplayname(new ItemStack(Material.GOLD_LEGGINGS), "§b§lWizard §a§lLvL 3§8 || §bLeggings"), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));
				kit.setBoots(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.setDisplayname(new ItemStack(Material.GOLD_BOOTS), "§b§lWizard §a§lLvL 3§8 || §bBoots"), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));
			}
			{// Tank Level 1 \\
				Kit kit = new Kit(KitPvPKit.TANK.getName() + " 1");
				kit.setItem(0, Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.setDisplayname(new ItemStack(Material.WOOD_SWORD), "§b§lTank §a§lLvL 1§8 || §bWeapon"), Enchantment.KNOCKBACK, 1)), 4));
				kit.setItem(1, Utils.setDisplayname(new ItemStack(Material.GOLDEN_APPLE, 2), "§b§lTank §a§lLvL 1§8 || §eGolden Apple"));

				kit.setHelmet(Utils.hideFlags(Utils.setUnbreakable(Utils.setDisplayname(new ItemStack(Material.IRON_HELMET), "§b§lTank §a§lLvL 1§8 || §bHelmet")), 4));
				kit.setChestplate(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.setDisplayname(new ItemStack(Material.DIAMOND_CHESTPLATE), "§b§lTank §a§lLvL 1§8 || §bChestplate"), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
				kit.setLeggings(Utils.hideFlags(Utils.setUnbreakable(Utils.setDisplayname(new ItemStack(Material.IRON_LEGGINGS), "§b§lTank §a§lLvL 1§8 || §bLeggings")), 4));
				kit.setBoots(Utils.hideFlags(Utils.setUnbreakable(Utils.setDisplayname(new ItemStack(Material.IRON_BOOTS), "§b§lTank §a§lLvL 1§8 || §bBoots")), 4));
				kit.setPotionEffect(new PotionEffect(PotionEffectType.SLOW, 2000000, 2));
			}
			{// Tank Level 2 \\
				Kit kit = new Kit(KitPvPKit.TANK.getName() + " 2");
				kit.setItem(0, Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.setDisplayname(new ItemStack(Material.STONE_SWORD), "§b§lTank §a§lLvL 2§8 || §bWeapon"), Enchantment.KNOCKBACK, 2)), 4));
				kit.setItem(1, Utils.setDisplayname(new ItemStack(Material.GOLDEN_APPLE, 3), "§b§lTank §a§lLvL 2§8 || §eGolden Apple"));

				kit.setHelmet(Utils.hideFlags(Utils.setUnbreakable(Utils.setDisplayname(new ItemStack(Material.IRON_HELMET), "§b§lTank §a§lLvL 2§8 || §bHelmet")), 4));
				kit.setChestplate(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.setDisplayname(new ItemStack(Material.DIAMOND_CHESTPLATE), "§b§lTank §a§lLvL 2§8 || §bChestplate"), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
				kit.setLeggings(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.setDisplayname(new ItemStack(Material.IRON_LEGGINGS), "§b§lTank §a§lLvL 2§8 || §bLeggings"), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
				kit.setBoots(Utils.hideFlags(Utils.setUnbreakable(Utils.setDisplayname(new ItemStack(Material.IRON_BOOTS), "§b§lTank §a§lLvL 2§8 || §bBoots")), 4));
				kit.setPotionEffect(new PotionEffect(PotionEffectType.SLOW, 2000000, 2));
			}
			{// Tank Level 3 \\
				Kit kit = new Kit(KitPvPKit.TANK.getName() + " 3");
				kit.setItem(0, Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.setDisplayname(new ItemStack(Material.IRON_SWORD), "§b§lTank §a§lLvL 3§8 || §bWeapon"), Enchantment.KNOCKBACK, 2)), 4));
				kit.setItem(1, Utils.setDisplayname(new ItemStack(Material.GOLDEN_APPLE, 4), "§b§lTank §a§lLvL 3§8 || §eGolden Apple"));

				kit.setHelmet(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.setDisplayname(new ItemStack(Material.IRON_HELMET), "§b§lTank §a§lLvL 3§8 || §bHelmet"), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
				kit.setChestplate(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.setDisplayname(new ItemStack(Material.DIAMOND_CHESTPLATE), "§b§lTank §a§lLvL 3§8 || §bChestplate"), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
				kit.setLeggings(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.setDisplayname(new ItemStack(Material.IRON_LEGGINGS), "§b§lTank §a§lLvL 3§8 || §bLeggings"), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
				kit.setBoots(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.setDisplayname(new ItemStack(Material.IRON_BOOTS), "§b§lTank §a§lLvL 3§8 || §bBoots"), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
				kit.setPotionEffect(new PotionEffect(PotionEffectType.SLOW, 2000000, 2));
			}
			{// Drunk Level 1 \\
				Kit kit = new Kit(KitPvPKit.DRUNK.getName() + " 1");
				kit.setItem(0, Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.setDisplayname(new ItemStack(Material.IRON_SWORD), "§b§lDrunk §a§lLvL 1§8 || §bWeapon"), Enchantment.KNOCKBACK, 2)), 4));
				kit.setItem(1, Utils.setDurability(Utils.setDisplayname(new ItemStack(Material.POTION), "§b§lDrunk §a§lLvL 1§8 || §5Strength Potion"), 8201));

				kit.setHelmet(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.setDisplayname(new ItemStack(Material.CHAINMAIL_HELMET), "§b§lDrunk §a§lLvL 1§8 || §bHelmet"), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
				kit.setChestplate(Utils.hideFlags(Utils.setUnbreakable(Utils.setDisplayname(new ItemStack(Material.CHAINMAIL_CHESTPLATE), "§b§lDrunk §a§lLvL 1§8 || §bChestplate")), 4));
				kit.setLeggings(Utils.hideFlags(Utils.setUnbreakable(Utils.setDisplayname(new ItemStack(Material.CHAINMAIL_LEGGINGS), "§b§lDrunk §a§lLvL 1§8 || §bLeggings")), 4));
				kit.setBoots(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.setDisplayname(new ItemStack(Material.CHAINMAIL_BOOTS), "§b§lDrunk §a§lLvL 1§8 || §bBoots"), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
				kit.setPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 2000000, 1));
			}
			{// Drunk Level 2 \\
				Kit kit = new Kit(KitPvPKit.DRUNK.getName() + " 2");
				kit.setItem(0, Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.setLore(Utils.setDisplayname(new ItemStack(Material.IRON_SWORD), "§b§lDrunk §a§lLvL 2§8 || §bWeapon"), ItemType.BLINDNESS_I.addEnchantment(new ArrayList<String>())), Enchantment.KNOCKBACK, 2)), 4));
				kit.setItem(1, Utils.setDurability(Utils.setDisplayname(new ItemStack(Material.POTION), "§b§lDrunk §a§lLvL 2§8 || §5Strength Potion"), 8201));

				kit.setHelmet(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.setDisplayname(new ItemStack(Material.CHAINMAIL_HELMET), "§b§lDrunk §a§lLvL 2§8 || §bHelmet"), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
				kit.setChestplate(Utils.hideFlags(Utils.setUnbreakable(Utils.setDisplayname(new ItemStack(Material.CHAINMAIL_CHESTPLATE), "§b§lDrunk §a§lLvL 2§8 || §bChestplate")), 4));
				kit.setLeggings(Utils.hideFlags(Utils.setUnbreakable(Utils.setDisplayname(new ItemStack(Material.CHAINMAIL_LEGGINGS), "§b§lDrunk §a§lLvL 2§8 || §bLeggings")), 4));
				kit.setBoots(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.setDisplayname(new ItemStack(Material.CHAINMAIL_BOOTS), "§b§lDrunk §a§lLvL 2§8 || §bBoots"), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
				kit.setPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 2000000, 0));
			}
			{// Drunk Level 3 \\
				Kit kit = new Kit(KitPvPKit.DRUNK.getName() + " 3");
				kit.setItem(0, Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.setLore(Utils.setDisplayname(new ItemStack(Material.DIAMOND_SWORD), "§b§lDrunk §a§lLvL 3§8 || §bWeapon"), ItemType.BLINDNESS_II.addEnchantment(new ArrayList<String>())), Enchantment.KNOCKBACK, 2)), 4));
				kit.setItem(1, Utils.setDurability(Utils.setDisplayname(new ItemStack(Material.POTION), "§b§lDrunk §a§lLvL 3§8 || §5Strength Potion"), 8201));

				kit.setHelmet(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.setDisplayname(new ItemStack(Material.CHAINMAIL_HELMET), "§b§lDrunk §a§lLvL 3§8 || §bHelmet"), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
				kit.setChestplate(Utils.hideFlags(Utils.setUnbreakable(Utils.setDisplayname(new ItemStack(Material.CHAINMAIL_CHESTPLATE), "§b§lDrunk §a§lLvL 3§8 || §bChestplate")), 4));
				kit.setLeggings(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.setDisplayname(new ItemStack(Material.CHAINMAIL_LEGGINGS), "§b§lDrunk §a§lLvL 3§8 || §bLeggings"), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
				kit.setBoots(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.setDisplayname(new ItemStack(Material.CHAINMAIL_BOOTS), "§b§lDrunk §a§lLvL 3§8 || §bBoots"), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
				kit.setPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 2000000, 0));
			}
			{// Pyro Level 1 \\
				Kit kit = new Kit(KitPvPKit.PYRO.getName() + " 1");
				kit.setItem(0, Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.setDisplayname(new ItemStack(Material.IRON_SWORD), "§b§lPyro §a§lLvL 1§8 || §bWeapon"), Enchantment.FIRE_ASPECT, 2)), 4));
				
				kit.setHelmet(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.setDisplayname(new ItemStack(Material.GOLD_HELMET), "§b§lPyro §a§lLvL 1§8 || §bHelmet"), Enchantment.PROTECTION_FIRE, 1)), 4));
				kit.setChestplate(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.setDisplayname(new ItemStack(Material.GOLD_CHESTPLATE), "§b§lPyro §a§lLvL 1§8 || §bChestplate"), Enchantment.PROTECTION_FIRE, 1)), 4));
				kit.setLeggings(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.setDisplayname(new ItemStack(Material.GOLD_LEGGINGS), "§b§lPyro §a§lLvL 1§8 || §bLeggings"), Enchantment.PROTECTION_FIRE, 1)), 4));
				kit.setBoots(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.setDisplayname(new ItemStack(Material.GOLD_BOOTS), "§b§lPyro §a§lLvL 1§8 || §bBoots"), Enchantment.PROTECTION_FIRE, 1)), 4));
				kit.setPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 2000000, 3));
			}
			{// Pyro Level 2 \\
				Kit kit = new Kit(KitPvPKit.PYRO.getName() + " 2");
				kit.setItem(0, Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.addEnchantment(Utils.setDisplayname(new ItemStack(Material.IRON_SWORD), "§b§lPyro §a§lLvL 2§8 || §bWeapon"), Enchantment.FIRE_ASPECT, 2), Enchantment.DAMAGE_ALL, 1)), 4));
				
				kit.setHelmet(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.setDisplayname(new ItemStack(Material.GOLD_HELMET), "§b§lPyro §a§lLvL 2§8 || §bHelmet"), Enchantment.PROTECTION_FIRE, 1)), 4));
				kit.setChestplate(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.addEnchantment(Utils.setDisplayname(new ItemStack(Material.GOLD_CHESTPLATE), "§b§lPyro §a§lLvL 2§8 || §bChestplate"), Enchantment.PROTECTION_FIRE, 1), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
				kit.setLeggings(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.setDisplayname(new ItemStack(Material.GOLD_LEGGINGS), "§b§lPyro §a§lLvL 2§8 || §bLeggings"), Enchantment.PROTECTION_FIRE, 1)), 4));
				kit.setBoots(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.addEnchantment(Utils.setDisplayname(new ItemStack(Material.GOLD_BOOTS), "§b§lPyro §a§lLvL 2§8 || §bBoots"), Enchantment.PROTECTION_FIRE, 1), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
				kit.setPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 2000000, 3));
			}
			{// Pyro Level 3 \\
				Kit kit = new Kit(KitPvPKit.PYRO.getName() + " 3");
				kit.setItem(0, Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.addEnchantment(Utils.setDisplayname(new ItemStack(Material.IRON_SWORD), "§b§lPyro §a§lLvL 3§8 || §bWeapon"), Enchantment.FIRE_ASPECT, 2), Enchantment.DAMAGE_ALL, 1)), 4));
				kit.setItem(1, Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.setDisplayname(new ItemStack(Material.BOW), "§b§lPyro §a§lLvL 3§8 || §bBow"), Enchantment.ARROW_FIRE, 1)), 4));
				kit.setItem(2, Utils.setDisplayname(new ItemStack(Material.ARROW, 12), "§b§lPyro §a§lLvL 3§8 || §bArrow"));
				
				kit.setHelmet(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.addEnchantment(Utils.setDisplayname(new ItemStack(Material.GOLD_HELMET), "§b§lPyro §a§lLvL 3§8 || §bHelmet"), Enchantment.PROTECTION_FIRE, 1), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
				kit.setChestplate(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.addEnchantment(Utils.setDisplayname(new ItemStack(Material.GOLD_CHESTPLATE), "§b§lPyro §a§lLvL 3§8 || §bChestplate"), Enchantment.PROTECTION_FIRE, 1), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
				kit.setLeggings(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.addEnchantment(Utils.setDisplayname(new ItemStack(Material.GOLD_LEGGINGS), "§b§lPyro §a§lLvL 3§8 || §bLeggings"), Enchantment.PROTECTION_FIRE, 1), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
				kit.setBoots(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.addEnchantment(Utils.setDisplayname(new ItemStack(Material.GOLD_BOOTS), "§b§lPyro §a§lLvL 3§8 || §bBoots"), Enchantment.PROTECTION_FIRE, 1), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
				kit.setPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 2000000, 3));
			}
			{// Bunny Level 1 \\
				Kit kit = new Kit(KitPvPKit.BUNNY.getName() + " 1");
				kit.setItem(0, Utils.hideFlags(Utils.setUnbreakable(Utils.setDisplayname(new ItemStack(Material.IRON_SWORD), "§b§lBunny §a§lLvL 1§8 || §bWeapon")), 4));
				kit.setItem(1, Utils.setDurability(Utils.setDisplayname(new ItemStack(Material.POTION), "§b§lBunny §a§lLvL 1§8 || §2Poison Potion"), 16420));
				kit.setItem(2, Utils.setDurability(Utils.setDisplayname(new ItemStack(Material.POTION), "§b§lBunny §a§lLvL 1§8 || §4Harming Potion"), 16396));
				kit.setItem(3, Utils.setDurability(Utils.setDisplayname(new ItemStack(Material.POTION), "§b§lBunny §a§lLvL 1§8 || §4Harming Potion"), 16396));

				kit.setHelmet(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.addColor(Utils.setDisplayname(new ItemStack(Material.LEATHER_HELMET), "§b§lBunny §a§lLvL 1§8 || §bHelmet"), Color.WHITE), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
				kit.setChestplate(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.addColor(Utils.setDisplayname(new ItemStack(Material.LEATHER_CHESTPLATE), "§b§lBunny §a§lLvL 1§8 || §bChestplate"), Color.WHITE), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
				kit.setLeggings(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.addColor(Utils.setDisplayname(new ItemStack(Material.LEATHER_LEGGINGS), "§b§lBunny §a§lLvL 1§8 || §bLeggings"), Color.WHITE), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
				kit.setBoots(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.addColor(Utils.setDisplayname(new ItemStack(Material.LEATHER_BOOTS), "§b§lBunny §a§lLvL 1§8 || §bBoots"), Color.WHITE), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
				kit.setPotionEffect(new PotionEffect(PotionEffectType.JUMP, 2000000, 3));
			}
			{// Bunny Level 2 \\
				Kit kit = new Kit(KitPvPKit.BUNNY.getName() + " 2");
				kit.setItem(0, Utils.hideFlags(Utils.setUnbreakable(Utils.setDisplayname(new ItemStack(Material.IRON_SWORD), "§b§lBunny §a§lLvL 2§8 || §bWeapon")), 4));
				kit.setItem(1, Utils.addEnchantment(Utils.addEnchantment(Utils.setDisplayname(new ItemStack(Material.CARROT_ITEM), "§b§lBunny §a§lLvL 2§8 || §6Knockback Carrot"), Enchantment.DAMAGE_ALL, 5), Enchantment.KNOCKBACK, 10));
				kit.setItem(2, Utils.setDurability(Utils.setDisplayname(new ItemStack(Material.POTION), "§b§lBunny §a§lLvL 2§8 || §2Poison Potion"), 16420));
				kit.setItem(3, Utils.setDurability(Utils.setDisplayname(new ItemStack(Material.POTION), "§b§lBunny §a§lLvL 2§8 || §2Poison Potion"), 16420));
				kit.setItem(4, Utils.setDurability(Utils.setDisplayname(new ItemStack(Material.POTION), "§b§lBunny §a§lLvL 2§8 || §4Harming Potion"), 16396));
				kit.setItem(5, Utils.setDurability(Utils.setDisplayname(new ItemStack(Material.POTION), "§b§lBunny §a§lLvL 2§8 || §4Harming Potion"), 16396));

				kit.setHelmet(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.addColor(Utils.setDisplayname(new ItemStack(Material.LEATHER_HELMET), "§b§lBunny §a§lLvL 2§8 || §bHelmet"), Color.WHITE), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));
				kit.setChestplate(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.addColor(Utils.setDisplayname(new ItemStack(Material.LEATHER_CHESTPLATE), "§b§lBunny §a§lLvL 2§8 || §bChestplate"), Color.WHITE), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));
				kit.setLeggings(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.addColor(Utils.setDisplayname(new ItemStack(Material.LEATHER_LEGGINGS), "§b§lBunny §a§lLvL 2§8 || §bLeggings"), Color.WHITE), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));
				kit.setBoots(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.addColor(Utils.setDisplayname(new ItemStack(Material.LEATHER_BOOTS), "§b§lBunny §a§lLvL 2§8 || §bBoots"), Color.WHITE), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));
				kit.setPotionEffect(new PotionEffect(PotionEffectType.JUMP, 2000000, 3));
			}
			{// Bunny Level 3 \\
				Kit kit = new Kit(KitPvPKit.BUNNY.getName() + " 3");
				kit.setItem(0, Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.setDisplayname(new ItemStack(Material.IRON_SWORD), "§b§lBunny §a§lLvL 3§8 || §bWeapon"), Enchantment.DAMAGE_ALL, 1)), 4));
				kit.setItem(1, Utils.addEnchantment(Utils.addEnchantment(Utils.setDisplayname(new ItemStack(Material.CARROT_ITEM), "§b§lBunny §a§lLvL 3§8 || §6Knockback Carrot"), Enchantment.DAMAGE_ALL, 5), Enchantment.KNOCKBACK, 10));
				kit.setItem(2, Utils.setDurability(Utils.setDisplayname(new ItemStack(Material.POTION), "§b§lBunny §a§lLvL 3§8 || §2Poison Potion"), 16420));
				kit.setItem(3, Utils.setDurability(Utils.setDisplayname(new ItemStack(Material.POTION), "§b§lBunny §a§lLvL 3§8 || §2Poison Potion"), 16420));
				kit.setItem(4, Utils.setDurability(Utils.setDisplayname(new ItemStack(Material.POTION), "§b§lBunny §a§lLvL 3§8 || §4Harming Potion"), 16396));
				kit.setItem(5, Utils.setDurability(Utils.setDisplayname(new ItemStack(Material.POTION), "§b§lBunny §a§lLvL 3§8 || §4Harming Potion"), 16396));

				kit.setHelmet(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.addColor(Utils.setDisplayname(new ItemStack(Material.LEATHER_HELMET), "§b§lBunny §a§lLvL 3§8 || §bHelmet"), Color.WHITE), Enchantment.PROTECTION_ENVIRONMENTAL, 3)), 4));
				kit.setChestplate(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.addColor(Utils.setDisplayname(new ItemStack(Material.LEATHER_CHESTPLATE), "§b§lBunny §a§lLvL 3§8 || §bChestplate"), Color.WHITE), Enchantment.PROTECTION_ENVIRONMENTAL, 3)), 4));
				kit.setLeggings(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.addColor(Utils.setDisplayname(new ItemStack(Material.LEATHER_LEGGINGS), "§b§lBunny §a§lLvL 3§8 || §bLeggings"), Color.WHITE), Enchantment.PROTECTION_ENVIRONMENTAL, 3)), 4));
				kit.setBoots(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.addColor(Utils.setDisplayname(new ItemStack(Material.LEATHER_BOOTS), "§b§lBunny §a§lLvL 3§8 || §bBoots"), Color.WHITE), Enchantment.PROTECTION_ENVIRONMENTAL, 3)), 4));
				kit.setPotionEffect(new PotionEffect(PotionEffectType.JUMP, 2000000, 3));
			}
			{// Necromancer Level 1 \\
				Kit kit = new Kit(KitPvPKit.NECROMANCER.getName() + " 1");
				kit.setItem(0, Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.setDisplayname(new ItemStack(Material.GOLD_HOE), "§b§lNecromancer §a§lLvL 1§8 || §bWeapon"), Enchantment.DAMAGE_ALL, 8)), 4));
				kit.setItem(1, Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.setLore(Utils.setDisplayname(new ItemStack(Material.BOW), "§b§lNecromancer §a§lLvL 1§8 || §bBow"), ProjectileType.UNDEATH_I.addEnchantment(new ArrayList<String>())), Enchantment.ARROW_DAMAGE, 1)), 4));
				kit.setItem(2, Utils.setDisplayname(new ItemStack(Material.ARROW, 5), "§b§lNecromancer §a§lLvL 1§8 || §bArrow"));
				
				kit.setHelmet(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.addColor(Utils.setDisplayname(new ItemStack(Material.LEATHER_HELMET), "§b§lNecromancer §a§lLvL 1§8 || §bHelmet"), Color.BLACK), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
				kit.setChestplate(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.addColor(Utils.setDisplayname(new ItemStack(Material.LEATHER_CHESTPLATE), "§b§lNecromancer §a§lLvL 1§8 || §bChestplate"), Color.BLACK), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
				kit.setLeggings(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.addColor(Utils.setDisplayname(new ItemStack(Material.LEATHER_LEGGINGS), "§b§lNecromancer §a§lLvL 1§8 || §bLeggings"), Color.BLACK), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
				kit.setBoots(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.addColor(Utils.setDisplayname(new ItemStack(Material.LEATHER_BOOTS), "§b§lNecromancer §a§lLvL 1§8 || §bBoots"), Color.BLACK), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
			}
			{// Necromancer Level 2 \\
				Kit kit = new Kit(KitPvPKit.NECROMANCER.getName() + " 2");
				kit.setItem(0, Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.setDisplayname(new ItemStack(Material.GOLD_HOE), "§b§lNecromancer §a§lLvL 2§8 || §bWeapon"), Enchantment.DAMAGE_ALL, 8)), 4));
				kit.setItem(1, Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.setLore(Utils.setDisplayname(new ItemStack(Material.BOW), "§b§lNecromancer §a§lLvL 2§8 || §bBow"), ProjectileType.UNDEATH_II.addEnchantment(new ArrayList<String>())), Enchantment.ARROW_DAMAGE, 1)), 4));
				kit.setItem(2, Utils.setDisplayname(new ItemStack(Material.ARROW, 10), "§b§lNecromancer §a§lLvL 2§8 || §bArrow"));
				
				kit.setHelmet(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.addColor(Utils.setDisplayname(new ItemStack(Material.LEATHER_HELMET), "§b§lNecromancer §a§lLvL 2§8 || §bHelmet"), Color.BLACK), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));
				kit.setChestplate(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.addColor(Utils.setDisplayname(new ItemStack(Material.LEATHER_CHESTPLATE), "§b§lNecromancer §a§lLvL 2§8 || §bChestplate"), Color.BLACK), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));
				kit.setLeggings(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.addColor(Utils.setDisplayname(new ItemStack(Material.LEATHER_LEGGINGS), "§b§lNecromancer §a§lLvL 2§8 || §bLeggings"), Color.BLACK), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));
				kit.setBoots(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.addColor(Utils.setDisplayname(new ItemStack(Material.LEATHER_BOOTS), "§b§lNecromancer §a§lLvL 2§8 || §bBoots"), Color.BLACK), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));
			}
			{// Necromancer Level 3 \\
				Kit kit = new Kit(KitPvPKit.NECROMANCER.getName() + " 3");
				kit.setItem(0, Utils.hideFlags(Utils.addEnchantment(Utils.setLore(Utils.setDisplayname(new ItemStack(Material.STICK), "§b§lNecromancer §a§lLvL 3§8 || §8Necromancer's Staff"), ItemType.WITHER_I.addEnchantment(new ArrayList<String>())), Enchantment.DURABILITY, 1), 5));
				kit.setItem(2, Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.setLore(Utils.setDisplayname(new ItemStack(Material.BOW), "§b§lNecromancer §a§lLvL 3§8 || §bBow"), ProjectileType.UNDEATH_II.addEnchantment(new ArrayList<String>())), Enchantment.ARROW_DAMAGE, 1)), 4));
				kit.setItem(2, Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.setLore(Utils.setDisplayname(new ItemStack(Material.BOW), "§b§lNecromancer §a§lLvL 3§8 || §bBow"), ProjectileType.UNDEATH_II.addEnchantment(new ArrayList<String>())), Enchantment.ARROW_DAMAGE, 1)), 4));
				kit.setItem(3, Utils.setDisplayname(new ItemStack(Material.ARROW, 10), "§b§lNecromancer §a§lLvL 3§8 || §bArrow"));
				
				kit.setHelmet(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.addColor(Utils.setDisplayname(new ItemStack(Material.LEATHER_HELMET), "§b§lNecromancer §a§lLvL 3§8 || §bHelmet"), Color.BLACK), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));
				kit.setChestplate(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.addColor(Utils.setDisplayname(new ItemStack(Material.LEATHER_CHESTPLATE), "§b§lNecromancer §a§lLvL 3§8 || §bChestplate"), Color.BLACK), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));
				kit.setLeggings(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.addColor(Utils.setDisplayname(new ItemStack(Material.LEATHER_LEGGINGS), "§b§lNecromancer §a§lLvL 3§8 || §bLeggings"), Color.BLACK), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));
				kit.setBoots(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.addColor(Utils.setDisplayname(new ItemStack(Material.LEATHER_BOOTS), "§b§lNecromancer §a§lLvL 3§8 || §bBoots"), Color.BLACK), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));
			}
			{// King Level 1 \\
				Kit kit = new Kit(KitPvPKit.KING.getName() + " 1");
				kit.setItem(0, Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.setDisplayname(new ItemStack(Material.STONE_SWORD), "§b§lKing §a§lLvL 1§8 || §bWeapon"), Enchantment.DAMAGE_ALL, 1)), 4));
				
				kit.setHelmet(Utils.hideFlags(Utils.setUnbreakable(Utils.setDisplayname(new ItemStack(Material.DIAMOND_HELMET), "§b§lKing §a§lLvL 1§8 || §bHelmet")), 4));
				kit.setChestplate(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.setDisplayname(new ItemStack(Material.GOLD_CHESTPLATE), "§b§lKing §a§lLvL 1§8 || §bChestplate"), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));
				kit.setLeggings(Utils.hideFlags(Utils.setUnbreakable(Utils.setDisplayname(new ItemStack(Material.GOLD_LEGGINGS), "§b§lKing §a§lLvL 1§8 || §bLeggings")), 4));
				kit.setBoots(Utils.hideFlags(Utils.setUnbreakable(Utils.setDisplayname(new ItemStack(Material.GOLD_BOOTS), "§b§lKing §a§lLvL 1§8 || §bBoots")), 4));
			}
			{// King Level 2 \\
				Kit kit = new Kit(KitPvPKit.KING.getName() + " 2");
				kit.setItem(0, Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.setLore(Utils.setDisplayname(new ItemStack(Material.STONE_SWORD), "§b§lKing §a§lLvL 2§8 || §bWeapon"), ItemType.HEALING_I.addEnchantment(new ArrayList<String>())), Enchantment.DAMAGE_ALL, 1)), 4));
				
				kit.setHelmet(Utils.hideFlags(Utils.setUnbreakable(Utils.setDisplayname(new ItemStack(Material.DIAMOND_HELMET), "§b§lKing §a§lLvL 2§8 || §bHelmet")), 4));
				kit.setChestplate(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.setDisplayname(new ItemStack(Material.GOLD_CHESTPLATE), "§b§lKing §a§lLvL 2§8 || §bChestplate"), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));
				kit.setLeggings(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.setDisplayname(new ItemStack(Material.GOLD_LEGGINGS), "§b§lKing §a§lLvL 2§8 || §bLeggings"), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));
				kit.setBoots(Utils.hideFlags(Utils.setUnbreakable(Utils.setDisplayname(new ItemStack(Material.GOLD_BOOTS), "§b§lKing §a§lLvL 2§8 || §bBoots")), 4));
			}
			{// King Level 3 \\
				Kit kit = new Kit(KitPvPKit.KING.getName() + " 3");
				kit.setItem(0, Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.setLore(Utils.setDisplayname(new ItemStack(Material.STONE_SWORD), "§b§lKing §a§lLvL 3§8 || §bWeapon"), ItemType.HEALING_II.addEnchantment(new ArrayList<String>())), Enchantment.DAMAGE_ALL, 1)), 4));
				
				kit.setHelmet(Utils.hideFlags(Utils.setUnbreakable(Utils.setDisplayname(new ItemStack(Material.DIAMOND_HELMET), "§b§lKing §a§lLvL 3§8 || §bHelmet")), 4));
				kit.setChestplate(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.setDisplayname(new ItemStack(Material.GOLD_CHESTPLATE), "§b§lKing §a§lLvL 3§8 || §bChestplate"), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));
				kit.setLeggings(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.setDisplayname(new ItemStack(Material.GOLD_LEGGINGS), "§b§lKing §a§lLvL 3§8 || §bLeggings"), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));
				kit.setBoots(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.setDisplayname(new ItemStack(Material.GOLD_BOOTS), "§b§lKing §a§lLvL 3§8 || §bBoots"), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));
			}
			{// Tree Level 1 \\
				Kit kit = new Kit(KitPvPKit.TREE.getName() + " 1");
				kit.setItem(0, Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.setDisplayname(new ItemStack(Material.LEAVES), "§b§lTree §a§lLvL 1§8 || §bWeapon"), Enchantment.DAMAGE_ALL, 4)), 4));
				
				kit.setHelmet(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.setDisplayname(new ItemStack(Material.LEAVES), "§b§lTree §a§lLvL 1§8 || §bHelmet"), Enchantment.PROTECTION_ENVIRONMENTAL, 3)), 4));
				kit.setChestplate(Utils.hideFlags(Utils.setUnbreakable(Utils.setDisplayname(new ItemStack(Material.CHAINMAIL_CHESTPLATE), "§b§lTree §a§lLvL 1§8 || §bChestplate")), 4));
				kit.setLeggings(Utils.hideFlags(Utils.setUnbreakable(Utils.setDisplayname(new ItemStack(Material.CHAINMAIL_LEGGINGS), "§b§lTree §a§lLvL 1§8 || §bLeggings")), 4));
				kit.setBoots(Utils.hideFlags(Utils.setUnbreakable(Utils.setDisplayname(new ItemStack(Material.CHAINMAIL_BOOTS), "§b§lTree §a§lLvL 1§8 || §bBoots")), 4));
			}
			{// Tree Level 2 \\
				Kit kit = new Kit(KitPvPKit.TREE.getName() + " 2");
				kit.setItem(0, Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.setDisplayname(new ItemStack(Material.LEAVES), "§b§lTree §a§lLvL 2§8 || §bWeapon"), Enchantment.DAMAGE_ALL, 5)), 4));
				kit.setItem(1, Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.setLore(Utils.setDurability(Utils.setDisplayname(new ItemStack(Material.LOG), "§b§lTree §a§lLvL 2§8 || §dBarrier"), 1), ItemType.BARRIER_I.addEnchantment(new ArrayList<String>())), Enchantment.DURABILITY, 1)), 4));
				
				kit.setHelmet(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.setDisplayname(new ItemStack(Material.LEAVES), "§b§lTree §a§lLvL 2§8 || §bHelmet"), Enchantment.PROTECTION_ENVIRONMENTAL, 4)), 4));
				kit.setChestplate(Utils.hideFlags(Utils.setUnbreakable(Utils.setDisplayname(new ItemStack(Material.CHAINMAIL_CHESTPLATE), "§b§lTree §a§lLvL 2§8 || §bChestplate")), 4));
				kit.setLeggings(Utils.hideFlags(Utils.setUnbreakable(Utils.setDisplayname(new ItemStack(Material.CHAINMAIL_LEGGINGS), "§b§lTree §a§lLvL 2§8 || §bLeggings")), 4));
				kit.setBoots(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.setDisplayname(new ItemStack(Material.CHAINMAIL_BOOTS), "§b§lTree §a§lLvL 2§8 || §bBoots"), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
			}
			{// Tree Level 3 \\
				Kit kit = new Kit(KitPvPKit.TREE.getName() + " 3");
				kit.setItem(0, Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.setDisplayname(new ItemStack(Material.LEAVES), "§b§lTree §a§lLvL 3§8 || §bWeapon"), Enchantment.DAMAGE_ALL, 5)), 4));
				kit.setItem(1, Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.setLore(Utils.setDurability(Utils.setDisplayname(new ItemStack(Material.LOG), "§b§lTree §a§lLvL 3§8 || §dBarrier"), 1), ItemType.BARRIER_II.addEnchantment(new ArrayList<String>())), Enchantment.DURABILITY, 1)), 4));
				
				kit.setHelmet(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.setDisplayname(new ItemStack(Material.LEAVES), "§b§lTree §a§lLvL 3§8 || §bHelmet"), Enchantment.PROTECTION_ENVIRONMENTAL, 4)), 4));
				kit.setChestplate(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.setDisplayname(new ItemStack(Material.CHAINMAIL_CHESTPLATE), "§b§lTree §a§lLvL 3§8 || §bChestplate"), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
				kit.setLeggings(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.setDisplayname(new ItemStack(Material.CHAINMAIL_LEGGINGS), "§b§lTree §a§lLvL 3§8 || §bLeggings"), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
				kit.setBoots(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.setDisplayname(new ItemStack(Material.CHAINMAIL_BOOTS), "§b§lTree §a§lLvL 3§8 || §bBoots"), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
			}
			{// Blaze Level 1 \\
				Kit kit = new Kit(KitPvPKit.BLAZE.getName() + " 1");
				kit.setItem(0, Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.addEnchantment(Utils.setDisplayname(new ItemStack(Material.BLAZE_POWDER), "§b§lBlaze §a§lLvL 1§8 || §bWeapon"), Enchantment.DAMAGE_ALL, 4), Enchantment.FIRE_ASPECT, 2)), 4));
				
				kit.setHelmet(Utils.hideFlags(Utils.addEnchantment(Utils.addEnchantment(Utils.setDurability(Utils.setDisplayname(new ItemStack(Material.STAINED_CLAY), "§b§lBlaze §a§lLvL 1§8 || §bHelmet"), 4), Enchantment.PROTECTION_ENVIRONMENTAL, 2), Enchantment.PROTECTION_FIRE, 3), 4));
				kit.setChestplate(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.addEnchantment(Utils.addColor(Utils.setDisplayname(new ItemStack(Material.LEATHER_CHESTPLATE), "§b§lBlaze §a§lLvL 1§8 || §bChestplate"), Color.fromBGR(10, 130, 180)), Enchantment.PROTECTION_ENVIRONMENTAL, 1), Enchantment.PROTECTION_FIRE, 3)), 4));
				kit.setLeggings(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.addEnchantment(Utils.addColor(Utils.setDisplayname(new ItemStack(Material.LEATHER_LEGGINGS), "§b§lBlaze §a§lLvL 1§8 || §bLeggings"), Color.fromBGR(10, 130, 180)), Enchantment.PROTECTION_ENVIRONMENTAL, 1), Enchantment.PROTECTION_FIRE, 3)), 4));
				kit.setBoots(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.addEnchantment(Utils.addColor(Utils.setDisplayname(new ItemStack(Material.LEATHER_BOOTS), "§b§lBlaze §a§lLvL 1§8 || §bBoots"), Color.fromBGR(10, 130, 180)), Enchantment.PROTECTION_ENVIRONMENTAL, 1), Enchantment.PROTECTION_FIRE, 3)), 4));
			}
			{// Blaze Level 2 \\
				Kit kit = new Kit(KitPvPKit.BLAZE.getName() + " 2");
				kit.setItem(0, Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.addEnchantment(Utils.setDisplayname(new ItemStack(Material.BLAZE_POWDER), "§b§lBlaze §a§lLvL 2§8 || §bWeapon"), Enchantment.DAMAGE_ALL, 5), Enchantment.FIRE_ASPECT, 3)), 4));
				
				kit.setHelmet(Utils.hideFlags(Utils.addEnchantment(Utils.addEnchantment(Utils.setDurability(Utils.setDisplayname(new ItemStack(Material.STAINED_CLAY), "§b§lBlaze §a§lLvL 2§8 || §bHelmet"), 4), Enchantment.PROTECTION_ENVIRONMENTAL, 2), Enchantment.PROTECTION_FIRE, 3), 4));
				kit.setChestplate(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.addEnchantment(Utils.addColor(Utils.setDisplayname(new ItemStack(Material.LEATHER_CHESTPLATE), "§b§lBlaze §a§lLvL 2§8 || §bChestplate"), Color.fromBGR(10, 130, 180)), Enchantment.PROTECTION_ENVIRONMENTAL, 2), Enchantment.PROTECTION_FIRE, 3)), 4));
				kit.setLeggings(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.addEnchantment(Utils.addColor(Utils.setDisplayname(new ItemStack(Material.LEATHER_LEGGINGS), "§b§lBlaze §a§lLvL 2§8 || §bLeggings"), Color.fromBGR(10, 130, 180)), Enchantment.PROTECTION_ENVIRONMENTAL, 1), Enchantment.PROTECTION_FIRE, 3)), 4));
				kit.setBoots(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.addEnchantment(Utils.addColor(Utils.setLore(Utils.setDisplayname(new ItemStack(Material.LEATHER_BOOTS), "§b§lBlaze §a§lLvL 2§8 || §bBoots"), ItemType.LIGHT_I.addEnchantment(new ArrayList<String>())), Color.fromBGR(10, 130, 180)), Enchantment.PROTECTION_ENVIRONMENTAL, 1), Enchantment.PROTECTION_FIRE, 3)), 4));
			}
			{// Blaze Level 3 \\
				Kit kit = new Kit(KitPvPKit.BLAZE.getName() + " 3");
				kit.setItem(0, Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.addEnchantment(Utils.setDisplayname(new ItemStack(Material.BLAZE_POWDER), "§b§lBlaze §a§lLvL 3§8 || §bWeapon"), Enchantment.DAMAGE_ALL, 6), Enchantment.FIRE_ASPECT, 4)), 4));
				
				kit.setHelmet(Utils.hideFlags(Utils.addEnchantment(Utils.addEnchantment(Utils.setDurability(Utils.setDisplayname(new ItemStack(Material.STAINED_CLAY), "§b§lBlaze §a§lLvL 3§8 || §bHelmet"), 4), Enchantment.PROTECTION_ENVIRONMENTAL, 3), Enchantment.PROTECTION_FIRE, 3), 4));
				kit.setChestplate(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.addEnchantment(Utils.addColor(Utils.setDisplayname(new ItemStack(Material.LEATHER_CHESTPLATE), "§b§lBlaze §a§lLvL 3§8 || §bChestplate"), Color.fromBGR(10, 130, 180)), Enchantment.PROTECTION_ENVIRONMENTAL, 2), Enchantment.PROTECTION_FIRE, 3)), 4));
				kit.setLeggings(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.addEnchantment(Utils.addColor(Utils.setDisplayname(new ItemStack(Material.LEATHER_LEGGINGS), "§b§lBlaze §a§lLvL 3§8 || §bLeggings"), Color.fromBGR(10, 130, 180)), Enchantment.PROTECTION_ENVIRONMENTAL, 2), Enchantment.PROTECTION_FIRE, 3)), 4));
				kit.setBoots(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.addEnchantment(Utils.addColor(Utils.setLore(Utils.setDisplayname(new ItemStack(Material.LEATHER_BOOTS), "§b§lBlaze §a§lLvL 3§8 || §bBoots"), ItemType.LIGHT_I.addEnchantment(new ArrayList<String>())), Color.fromBGR(10, 130, 180)), Enchantment.PROTECTION_ENVIRONMENTAL, 2), Enchantment.PROTECTION_FIRE, 3)), 4));
			}
			{// TNT Level 1 \\
				Kit kit = new Kit(KitPvPKit.TNT.getName() + " 1");
				kit.setItem(0, Utils.hideFlags(Utils.addEnchantment(Utils.addEnchantment(Utils.setDisplayname(new ItemStack(Material.TNT), "§b§lTNT §a§lLvL 1§8 || §bWeapon"), Enchantment.DAMAGE_ALL, 5), Enchantment.KNOCKBACK, 3), 4));
				kit.setItem(1, Utils.addEnchantment(Utils.setLore(Utils.setDurability(Utils.setDisplayname(new ItemStack(Material.REDSTONE_TORCH_ON), "§b§lTNT §a§lLvL 1§8 || §4TNT Launcher"), 8193), ItemType.TNT_I.addEnchantment(new ArrayList<String>())), Enchantment.DURABILITY, 1));

				kit.setHelmet(Utils.hideFlags(Utils.addEnchantment(Utils.setDisplayname(new ItemStack(Material.TNT), "§b§lTNT §a§lLvL 1§8 || §bHelmet"), Enchantment.PROTECTION_ENVIRONMENTAL, 4), 4));
				kit.setChestplate(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.setDisplayname(new ItemStack(Material.GOLD_CHESTPLATE), "§b§lTNT §a§lLvL 1§8 || §bChestplate"), Enchantment.PROTECTION_EXPLOSIONS, 1)), 4));
				kit.setLeggings(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.setDisplayname(new ItemStack(Material.GOLD_LEGGINGS), "§b§lTNT §a§lLvL 1§8 || §bLeggings"), Enchantment.PROTECTION_EXPLOSIONS, 1)), 4));
				kit.setBoots(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.setDisplayname(new ItemStack(Material.GOLD_BOOTS), "§b§lTNT §a§lLvL 1§8 || §bBoots"), Enchantment.PROTECTION_EXPLOSIONS, 1)), 4));
			}
			{// TNT Level 2 \\
				Kit kit = new Kit(KitPvPKit.TNT.getName() + " 2");
				kit.setItem(0, Utils.hideFlags(Utils.addEnchantment(Utils.addEnchantment(Utils.setDisplayname(new ItemStack(Material.TNT), "§b§lTNT §a§lLvL 2§8 || §bWeapon"), Enchantment.DAMAGE_ALL, 5), Enchantment.KNOCKBACK, 4), 4));
				kit.setItem(1, Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.setLore(Utils.setDisplayname(new ItemStack(Material.BOW), "§b§lTNT §a§lLvL 3§8 || §bBow"), ProjectileType.EXPLOSIVE_I.addEnchantment(new ArrayList<String>())), Enchantment.DURABILITY, 1)), 5));
				kit.setItem(2, Utils.setDisplayname(new ItemStack(Material.ARROW, 2), "§b§lTNT §a§lLvL 2§8 || §bArrow"));
				kit.setItem(3, Utils.addEnchantment(Utils.setLore(Utils.setDurability(Utils.setDisplayname(new ItemStack(Material.REDSTONE_TORCH_ON), "§b§lTNT §a§lLvL 2§8 || §4TNT Launcher"), 8193), ItemType.TNT_I.addEnchantment(new ArrayList<String>())), Enchantment.DURABILITY, 1));

				kit.setHelmet(Utils.hideFlags(Utils.addEnchantment(Utils.setDisplayname(new ItemStack(Material.TNT), "§b§lTNT §a§lLvL 2§8 || §bHelmet"), Enchantment.PROTECTION_ENVIRONMENTAL, 4), 4));
				kit.setChestplate(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.addEnchantment(Utils.setDisplayname(new ItemStack(Material.GOLD_CHESTPLATE), "§b§lTNT §a§lLvL 2§8 || §bChestplate"), Enchantment.PROTECTION_EXPLOSIONS, 2), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
				kit.setLeggings(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.setDisplayname(new ItemStack(Material.GOLD_LEGGINGS), "§b§lTNT §a§lLvL 2§8 || §bLeggings"), Enchantment.PROTECTION_EXPLOSIONS, 2)), 4));
				kit.setBoots(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.setDisplayname(new ItemStack(Material.GOLD_BOOTS), "§b§lTNT §a§lLvL 2§8 || §bBoots"), Enchantment.PROTECTION_EXPLOSIONS, 2)), 4));
			}
			{// TNT Level 3 \\
				Kit kit = new Kit(KitPvPKit.TNT.getName() + " 3");
				kit.setItem(0, Utils.hideFlags(Utils.addEnchantment(Utils.addEnchantment(Utils.setDisplayname(new ItemStack(Material.TNT), "§b§lTNT §a§lLvL 3§8 || §bWeapon"), Enchantment.DAMAGE_ALL, 5), Enchantment.KNOCKBACK, 4), 4));
				kit.setItem(1, Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.setLore(Utils.setDisplayname(new ItemStack(Material.BOW), "§b§lTNT §a§lLvL 3§8 || §bBow"), ProjectileType.EXPLOSIVE_I.addEnchantment(new ArrayList<String>())), Enchantment.DURABILITY, 1)), 5));
				kit.setItem(2, Utils.setDisplayname(new ItemStack(Material.ARROW, 4), "§b§lTNT §a§lLvL 3§8 || §bArrow"));
				kit.setItem(3, Utils.addEnchantment(Utils.setLore(Utils.setDurability(Utils.setDisplayname(new ItemStack(Material.REDSTONE_TORCH_ON), "§b§lTNT §a§lLvL 3§8 || §4TNT Launcher"), 8193), ItemType.TNT_I.addEnchantment(new ArrayList<String>())), Enchantment.DURABILITY, 1));

				kit.setHelmet(Utils.hideFlags(Utils.addEnchantment(Utils.setDisplayname(new ItemStack(Material.TNT), "§b§lTNT §a§lLvL 3§8 || §bHelmet"), Enchantment.PROTECTION_ENVIRONMENTAL, 4), 4));
				kit.setChestplate(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.addEnchantment(Utils.setDisplayname(new ItemStack(Material.GOLD_CHESTPLATE), "§b§lTNT §a§lLvL 3§8 || §bChestplate"), Enchantment.PROTECTION_EXPLOSIONS, 3), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
				kit.setLeggings(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.addEnchantment(Utils.setDisplayname(new ItemStack(Material.GOLD_LEGGINGS), "§b§lTNT §a§lLvL 3§8 || §bLeggings"), Enchantment.PROTECTION_EXPLOSIONS, 3), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
				kit.setBoots(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.addEnchantment(Utils.setDisplayname(new ItemStack(Material.GOLD_BOOTS), "§b§lTNT §a§lLvL 3§8 || §bBoots"), Enchantment.PROTECTION_EXPLOSIONS, 3), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
			}
			{// Fisherman Level 1 \\
				Kit kit = new Kit(KitPvPKit.FISHERMAN.getName() + " 1");
				kit.setItem(0, Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.setDisplayname(new ItemStack(Material.FISHING_ROD), "§b§lFisherman §a§lLvL 1§8 || §bWeapon"), Enchantment.DAMAGE_ALL, 6)), 4));
				kit.setItem(1, Utils.hideFlags(Utils.addEnchantment(Utils.addEnchantment(Utils.setDurability(Utils.setDisplayname(new ItemStack(Material.RAW_FISH), "§b§lFisherman §a§lLvL 1§8 || §bFish"), 1), Enchantment.KNOCKBACK, 4), Enchantment.FIRE_ASPECT, 2), 4));
				
				kit.setHelmet(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.addColor(Utils.setDisplayname(new ItemStack(Material.LEATHER_HELMET), "§b§lFisherman §a§lLvL 1§8 || §bHelmet"), Color.fromBGR(10, 130, 120)), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));
				kit.setChestplate(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.addColor(Utils.setDisplayname(new ItemStack(Material.LEATHER_CHESTPLATE), "§b§lFisherman §a§lLvL 1§8 || §bChestplate"), Color.fromBGR(10, 130, 120)), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
				kit.setLeggings(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.addColor(Utils.setDisplayname(new ItemStack(Material.LEATHER_LEGGINGS), "§b§lFisherman §a§lLvL 1§8 || §bLeggings"), Color.fromBGR(10, 130, 120)), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
				kit.setBoots(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.addColor(Utils.setDisplayname(new ItemStack(Material.LEATHER_BOOTS), "§b§lFisherman §a§lLvL 1§8 || §bBoots"), Color.fromBGR(10, 130, 120)), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));
			}
			{// Fisherman Level 2 \\
				Kit kit = new Kit(KitPvPKit.FISHERMAN.getName() + " 2");
				kit.setItem(0, Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.setDisplayname(new ItemStack(Material.FISHING_ROD), "§b§lFisherman §a§lLvL 2§8 || §bWeapon"), Enchantment.DAMAGE_ALL, 6)), 4));
				kit.setItem(1, Utils.hideFlags(Utils.addEnchantment(Utils.addEnchantment(Utils.setDurability(Utils.setDisplayname(new ItemStack(Material.RAW_FISH), "§b§lFisherman §a§lLvL 2§8 || §bFish"), 1), Enchantment.KNOCKBACK, 4), Enchantment.FIRE_ASPECT, 2), 4));
				kit.setItem(2, Utils.hideFlags(Utils.addEnchantment(Utils.setLore(Utils.setDurability(Utils.setDisplayname(new ItemStack(Material.RAW_FISH), "§b§lFisherman §a§lLvL 2§8 || §9Fish Attack"), 3), ItemType.FISH_ATTACK_I.addEnchantment(new ArrayList<String>())), Enchantment.DURABILITY, 1), 5));
				
				kit.setHelmet(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.addColor(Utils.setDisplayname(new ItemStack(Material.LEATHER_HELMET), "§b§lFisherman §a§lLvL 2§8 || §bHelmet"), Color.fromBGR(10, 130, 120)), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));
				kit.setChestplate(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.addColor(Utils.setDisplayname(new ItemStack(Material.LEATHER_CHESTPLATE), "§b§lFisherman §a§lLvL 2§8 || §bChestplate"), Color.fromBGR(10, 130, 120)), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));
				kit.setLeggings(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.addColor(Utils.setDisplayname(new ItemStack(Material.LEATHER_LEGGINGS), "§b§lFisherman §a§lLvL 2§8 || §bLeggings"), Color.fromBGR(10, 130, 120)), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));
				kit.setBoots(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.addColor(Utils.setDisplayname(new ItemStack(Material.LEATHER_BOOTS), "§b§lFisherman §a§lLvL 2§8 || §bBoots"), Color.fromBGR(10, 130, 120)), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));
			}
			{// Fisherman Level 3 \\
				Kit kit = new Kit(KitPvPKit.FISHERMAN.getName() + " 3");
				kit.setItem(0, Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.setDisplayname(new ItemStack(Material.FISHING_ROD), "§b§lFisherman §a§lLvL 3§8 || §bWeapon"), Enchantment.DAMAGE_ALL, 6)), 4));
				kit.setItem(1, Utils.hideFlags(Utils.addEnchantment(Utils.addEnchantment(Utils.setDurability(Utils.setDisplayname(new ItemStack(Material.RAW_FISH), "§b§lFisherman §a§lLvL 3§8 || §bFish"), 1), Enchantment.KNOCKBACK, 5), Enchantment.FIRE_ASPECT, 2), 4));
				kit.setItem(2, Utils.hideFlags(Utils.addEnchantment(Utils.setLore(Utils.setDurability(Utils.setDisplayname(new ItemStack(Material.RAW_FISH), "§b§lFisherman §a§lLvL 3§8 || §9Fish Attack"), 3), ItemType.FISH_ATTACK_I.addEnchantment(new ArrayList<String>())), Enchantment.DURABILITY, 1), 5));
				
				kit.setHelmet(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.addColor(Utils.setDisplayname(new ItemStack(Material.LEATHER_HELMET), "§b§lFisherman §a§lLvL 3§8 || §bHelmet"), Color.fromBGR(10, 130, 120)), Enchantment.PROTECTION_ENVIRONMENTAL, 3)), 4));
				kit.setChestplate(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.addColor(Utils.setDisplayname(new ItemStack(Material.LEATHER_CHESTPLATE), "§b§lFisherman §a§lLvL 3§8 || §bChestplate"), Color.fromBGR(10, 130, 120)), Enchantment.PROTECTION_ENVIRONMENTAL, 3)), 4));
				kit.setLeggings(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.addColor(Utils.setDisplayname(new ItemStack(Material.LEATHER_LEGGINGS), "§b§lFisherman §a§lLvL 3§8 || §bLeggings"), Color.fromBGR(10, 130, 120)), Enchantment.PROTECTION_ENVIRONMENTAL, 3)), 4));
				kit.setBoots(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.addColor(Utils.setDisplayname(new ItemStack(Material.LEATHER_BOOTS), "§b§lFisherman §a§lLvL 3§8 || §bBoots"), Color.fromBGR(10, 130, 120)), Enchantment.PROTECTION_ENVIRONMENTAL, 3)), 4));
			}
			{// SnowGolem Level 1 \\
				Kit kit = new Kit(KitPvPKit.SNOWGOLEM.getName() + " 1");
				kit.setItem(0, Utils.hideFlags(Utils.addEnchantment(Utils.addEnchantment(Utils.setDisplayname(new ItemStack(Material.SNOW_BALL), "§b§lSnowGolem §a§lLvL 1§8 || §bWeapon"), Enchantment.DAMAGE_ALL, 6), Enchantment.KNOCKBACK, 2), 4));
				
				kit.setHelmet(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.setDisplayname(new ItemStack(Material.PUMPKIN), "§b§lSnowGolem §a§lLvL 1§8 || §bHelmet"), Enchantment.PROTECTION_ENVIRONMENTAL, 5)), 4));
				kit.setChestplate(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.addColor(Utils.setDisplayname(new ItemStack(Material.LEATHER_CHESTPLATE), "§b§lSnowGolem §a§lLvL 1§8 || §bChestplate"), Color.fromBGR(200, 200, 200)), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));
				kit.setLeggings(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.addColor(Utils.setDisplayname(new ItemStack(Material.LEATHER_LEGGINGS), "§b§lSnowGolem §a§lLvL 1§8 || §bLeggings"), Color.fromBGR(200, 200, 200)), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
				kit.setBoots(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.addColor(Utils.setDisplayname(new ItemStack(Material.LEATHER_BOOTS), "§b§lSnowGolem §a§lLvL 1§8 || §bBoots"), Color.fromBGR(200, 200, 200)), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
				kit.setPotionEffect(new PotionEffect(PotionEffectType.SPEED, 2000000, 0));
			}
			{// SnowGolem Level 2 \\
				Kit kit = new Kit(KitPvPKit.SNOWGOLEM.getName() + " 2");
				kit.setItem(0, Utils.hideFlags(Utils.addEnchantment(Utils.addEnchantment(Utils.setDisplayname(new ItemStack(Material.SNOW_BALL), "§b§lSnowGolem §a§lLvL 2§8 || §bWeapon"), Enchantment.DAMAGE_ALL, 6), Enchantment.KNOCKBACK, 2), 4));
				kit.setItem(1, Utils.hideFlags(Utils.addEnchantment(Utils.setLore(Utils.setDurability(Utils.setDisplayname(new ItemStack(Material.IRON_FENCE), "§b§lSnowGolem §a§lLvL 2§8 || §7Shield"), 3), ItemType.SHIELD_I.addEnchantment(new ArrayList<String>())), Enchantment.DURABILITY, 1), 5));
				
				kit.setHelmet(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.setDisplayname(new ItemStack(Material.PUMPKIN), "§b§lSnowGolem §a§lLvL 2§8 || §bHelmet"), Enchantment.PROTECTION_ENVIRONMENTAL, 5)), 4));
				kit.setChestplate(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.addColor(Utils.setDisplayname(new ItemStack(Material.LEATHER_CHESTPLATE), "§b§lSnowGolem §a§lLvL 2§8 || §bChestplate"), Color.fromBGR(200, 200, 200)), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));
				kit.setLeggings(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.addColor(Utils.setDisplayname(new ItemStack(Material.LEATHER_LEGGINGS), "§b§lSnowGolem §a§lLvL 2§8 || §bLeggings"), Color.fromBGR(200, 200, 200)), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));
				kit.setBoots(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.addColor(Utils.setDisplayname(new ItemStack(Material.LEATHER_BOOTS), "§b§lSnowGolem §a§lLvL 2§8 || §bBoots"), Color.fromBGR(200, 200, 200)), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));
				kit.setPotionEffect(new PotionEffect(PotionEffectType.SPEED, 2000000, 0));
			}
			{// SnowGolem Level 3 \\
				Kit kit = new Kit(KitPvPKit.SNOWGOLEM.getName() + " 3");
				kit.setItem(0, Utils.hideFlags(Utils.addEnchantment(Utils.addEnchantment(Utils.setDisplayname(new ItemStack(Material.SNOW_BALL), "§b§lSnowGolem §a§lLvL 3§8 || §bWeapon"), Enchantment.DAMAGE_ALL, 7), Enchantment.KNOCKBACK, 3), 4));
				kit.setItem(1, Utils.hideFlags(Utils.addEnchantment(Utils.setLore(Utils.setDurability(Utils.setDisplayname(new ItemStack(Material.IRON_FENCE), "§b§lSnowGolem §a§lLvL 3§8 || §7Shield"), 3), ItemType.SHIELD_II.addEnchantment(new ArrayList<String>())), Enchantment.DURABILITY, 1), 5));
				
				kit.setHelmet(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.setDisplayname(new ItemStack(Material.PUMPKIN), "§b§lSnowGolem §a§lLvL 3§8 || §bHelmet"), Enchantment.PROTECTION_ENVIRONMENTAL, 5)), 4));
				kit.setChestplate(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.addColor(Utils.setDisplayname(new ItemStack(Material.LEATHER_CHESTPLATE), "§b§lSnowGolem §a§lLvL 3§8 || §bChestplate"), Color.fromBGR(200, 200, 200)), Enchantment.PROTECTION_ENVIRONMENTAL, 3)), 4));
				kit.setLeggings(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.addColor(Utils.setDisplayname(new ItemStack(Material.LEATHER_LEGGINGS), "§b§lSnowGolem §a§lLvL 3§8 || §bLeggings"), Color.fromBGR(200, 200, 200)), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));
				kit.setBoots(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.addColor(Utils.setDisplayname(new ItemStack(Material.LEATHER_BOOTS), "§b§lSnowGolem §a§lLvL 3§8 || §bBoots"), Color.fromBGR(200, 200, 200)), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));
				kit.setPotionEffect(new PotionEffect(PotionEffectType.SPEED, 2000000, 0));
			}
			{// Librarian Level 1 \\
				Kit kit = new Kit(KitPvPKit.LIBRARIAN.getName() + " 1");
				kit.setItem(0, Utils.hideFlags(Utils.addEnchantment(Utils.setDisplayname(new ItemStack(Material.PAPER), "§b§lLibrarian §a§lLvL 1§8 || §bWeapon"), Enchantment.DAMAGE_ALL, 8), 4));
				
				kit.setHelmet(Utils.hideFlags(Utils.addEnchantment(Utils.setDisplayname(new ItemStack(Material.BOOKSHELF), "§b§lLibrarian §a§lLvL 1§8 || §bHelmet"), Enchantment.PROTECTION_ENVIRONMENTAL, 6), 4));
				kit.setChestplate(Utils.hideFlags(Utils.setUnbreakable(Utils.setDisplayname(new ItemStack(Material.CHAINMAIL_CHESTPLATE), "§b§lLibrarian §a§lLvL 1§8 || §bChestplate")), 4));
				kit.setLeggings(Utils.hideFlags(Utils.setUnbreakable(Utils.setDisplayname(new ItemStack(Material.CHAINMAIL_LEGGINGS), "§b§lLibrarian §a§lLvL 1§8 || §bLeggings")), 4));
				kit.setBoots(Utils.hideFlags(Utils.setUnbreakable(Utils.setDisplayname(new ItemStack(Material.CHAINMAIL_BOOTS), "§b§lLibrarian §a§lLvL 1§8 || §bBoots")), 4));
			}
			{// Librarian Level 2 \\
				Kit kit = new Kit(KitPvPKit.LIBRARIAN.getName() + " 2");
				kit.setItem(0, Utils.hideFlags(Utils.addEnchantment(Utils.setDisplayname(new ItemStack(Material.PAPER), "§b§lLibrarian §a§lLvL 2§8 || §bWeapon"), Enchantment.DAMAGE_ALL, 8), 4));
				kit.setItem(1, Utils.hideFlags(Utils.addEnchantment(Utils.setDisplayname(new ItemStack(Material.BOOK), "§b§lLibrarian §a§lLvL 2§8 || §6Knockback Book"), Enchantment.KNOCKBACK, 5), 4));
				
				kit.setHelmet(Utils.hideFlags(Utils.addEnchantment(Utils.setDisplayname(new ItemStack(Material.BOOKSHELF), "§b§lLibrarian §a§lLvL 2§8 || §bHelmet"), Enchantment.PROTECTION_ENVIRONMENTAL, 6), 4));
				kit.setChestplate(Utils.hideFlags(Utils.setUnbreakable(Utils.setDisplayname(new ItemStack(Material.CHAINMAIL_CHESTPLATE), "§b§lLibrarian §a§lLvL 2§8 || §bChestplate")), 4));
				kit.setLeggings(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.setDisplayname(new ItemStack(Material.CHAINMAIL_LEGGINGS), "§b§lLibrarian §a§lLvL 2§8 || §bLeggings"), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
				kit.setBoots(Utils.hideFlags(Utils.setUnbreakable(Utils.setDisplayname(new ItemStack(Material.CHAINMAIL_BOOTS), "§b§lLibrarian §a§lLvL 2§8 || §bBoots")), 4));
			}
			{// Librarian Level 3 \\
				Kit kit = new Kit(KitPvPKit.LIBRARIAN.getName() + " 3");
				kit.setItem(0, Utils.hideFlags(Utils.addEnchantment(Utils.setDisplayname(new ItemStack(Material.PAPER), "§b§lLibrarian §a§lLvL 3§8 || §bWeapon"), Enchantment.DAMAGE_ALL, 8), 4));
				kit.setItem(1, Utils.hideFlags(Utils.addEnchantment(Utils.setDisplayname(new ItemStack(Material.BOOK), "§b§lLibrarian §a§lLvL 3§8 || §6Knockback Book"), Enchantment.KNOCKBACK, 6), 4));
				
				kit.setHelmet(Utils.hideFlags(Utils.addEnchantment(Utils.setDisplayname(new ItemStack(Material.BOOKSHELF), "§b§lLibrarian §a§lLvL 3§8 || §bHelmet"), Enchantment.PROTECTION_ENVIRONMENTAL, 6), 4));
				kit.setChestplate(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.setDisplayname(new ItemStack(Material.CHAINMAIL_CHESTPLATE), "§b§lLibrarian §a§lLvL 3§8 || §bChestplate"), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));
				kit.setLeggings(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.setDisplayname(new ItemStack(Material.CHAINMAIL_LEGGINGS), "§b§lLibrarian §a§lLvL 3§8 || §bLeggings"), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
				kit.setBoots(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.setDisplayname(new ItemStack(Material.CHAINMAIL_BOOTS), "§b§lLibrarian §a§lLvL 3§8 || §bBoots"), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
			}
			{// Spider Level 1 \\
				Kit kit = new Kit(KitPvPKit.SPIDER.getName() + " 1");
				kit.setItem(0, Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.setLore(Utils.setDisplayname(new ItemStack(Material.SPIDER_EYE), "§b§lSpider §a§lLvL 1§8 || §bWeapon"), ItemType.ARTHROPODS_I.addEnchantment(new ArrayList<String>())), Enchantment.DAMAGE_ALL, 6)), 4));
				
				kit.setHelmet(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.addColor(Utils.setDisplayname(new ItemStack(Material.LEATHER_HELMET), "§b§lSpider §a§lLvL 1§8 || §bHelmet"), Color.BLACK), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
				kit.setChestplate(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.addColor(Utils.setDisplayname(new ItemStack(Material.LEATHER_CHESTPLATE), "§b§lSpider §a§lLvL 1§8 || §bChestplate"), Color.BLACK), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
				kit.setLeggings(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.addColor(Utils.setDisplayname(new ItemStack(Material.LEATHER_LEGGINGS), "§b§lSpider §a§lLvL 1§8 || §bLeggings"), Color.BLACK), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
				kit.setBoots(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.addColor(Utils.setDisplayname(new ItemStack(Material.LEATHER_BOOTS), "§b§lSpider §a§lLvL 1§8 || §bBoots"), Color.BLACK), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
			}
			{// Spider Level 2 \\
				Kit kit = new Kit(KitPvPKit.SPIDER.getName() + " 2");
				kit.setItem(0, Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.setLore(Utils.setDisplayname(new ItemStack(Material.SPIDER_EYE), "§b§lSpider §a§lLvL 2§8 || §bWeapon"), ItemType.ARTHROPODS_II.addEnchantment(new ArrayList<String>())), Enchantment.DAMAGE_ALL, 6)), 4));
				
				kit.setHelmet(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.addColor(Utils.setDisplayname(new ItemStack(Material.LEATHER_HELMET), "§b§lSpider §a§lLvL 2§8 || §bHelmet"), Color.BLACK), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
				kit.setChestplate(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.addColor(Utils.setDisplayname(new ItemStack(Material.LEATHER_CHESTPLATE), "§b§lSpider §a§lLvL 2§8 || §bChestplate"), Color.BLACK), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));
				kit.setLeggings(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.addColor(Utils.setDisplayname(new ItemStack(Material.LEATHER_LEGGINGS), "§b§lSpider §a§lLvL 2§8 || §bLeggings"), Color.BLACK), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
				kit.setBoots(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.addColor(Utils.setDisplayname(new ItemStack(Material.LEATHER_BOOTS), "§b§lSpider §a§lLvL 2§8 || §bBoots"), Color.BLACK), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));
			}
			{// Spider Level 3 \\
				Kit kit = new Kit(KitPvPKit.SPIDER.getName() + " 3");
				kit.setItem(0, Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.setLore(Utils.setDisplayname(new ItemStack(Material.SPIDER_EYE), "§b§lSpider §a§lLvL 3§8 || §bWeapon"), ItemType.ARTHROPODS_III.addEnchantment(new ArrayList<String>())), Enchantment.DAMAGE_ALL, 6)), 4));
				
				kit.setHelmet(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.addColor(Utils.setDisplayname(new ItemStack(Material.LEATHER_HELMET), "§b§lSpider §a§lLvL 3§8 || §bHelmet"), Color.BLACK), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));
				kit.setChestplate(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.addColor(Utils.setDisplayname(new ItemStack(Material.LEATHER_CHESTPLATE), "§b§lSpider §a§lLvL 3§8 || §bChestplate"), Color.BLACK), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));
				kit.setLeggings(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.addColor(Utils.setDisplayname(new ItemStack(Material.LEATHER_LEGGINGS), "§b§lSpider §a§lLvL 3§8 || §bLeggings"), Color.BLACK), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));
				kit.setBoots(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.addColor(Utils.setDisplayname(new ItemStack(Material.LEATHER_BOOTS), "§b§lSpider §a§lLvL 3§8 || §bBoots"), Color.BLACK), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));
			}
			{// Villager Level 1 \\
				Kit kit = new Kit(KitPvPKit.VILLAGER.getName() + " 1");
				kit.setItem(0, Utils.hideFlags(Utils.addEnchantment(Utils.setDisplayname(new ItemStack(Material.EMERALD), "§b§lVillager §a§lLvL 1§8 || §bWeapon"), Enchantment.DAMAGE_ALL, 9), 4));
				
				kit.setHelmet(Utils.hideFlags(Utils.setUnbreakable(Utils.setDisplayname(new ItemStack(Material.CHAINMAIL_HELMET), "§b§lVillager §a§lLvL 1§8 || §bHelmet")), 4));
				kit.setChestplate(Utils.hideFlags(Utils.setUnbreakable(Utils.setDisplayname(new ItemStack(Material.CHAINMAIL_CHESTPLATE), "§b§lVillager §a§lLvL 1§8 || §bChestplate")), 4));
				kit.setLeggings(Utils.hideFlags(Utils.setUnbreakable(Utils.setDisplayname(new ItemStack(Material.CHAINMAIL_LEGGINGS), "§b§lVillager §a§lLvL 1§8 || §bLeggings")), 4));
				kit.setBoots(Utils.hideFlags(Utils.setUnbreakable(Utils.setDisplayname(new ItemStack(Material.CHAINMAIL_BOOTS), "§b§lVillager §a§lLvL 1§8 || §bBoots")), 4));
			}
			{// Villager Level 2 \\
				Kit kit = new Kit(KitPvPKit.VILLAGER.getName() + " 2");
				kit.setItem(0, Utils.hideFlags(Utils.addEnchantment(Utils.addEnchantment(Utils.setDisplayname(new ItemStack(Material.EMERALD), "§b§lVillager §a§lLvL 2§8 || §bWeapon"), Enchantment.DAMAGE_ALL, 9), Enchantment.FIRE_ASPECT, 1), 4));
				
				kit.setHelmet(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.setDisplayname(new ItemStack(Material.CHAINMAIL_HELMET), "§b§lVillager §a§lLvL 2§8 || §bHelmet"), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
				kit.setChestplate(Utils.hideFlags(Utils.setUnbreakable(Utils.setDisplayname(new ItemStack(Material.CHAINMAIL_CHESTPLATE), "§b§lVillager §a§lLvL 2§8 || §bChestplate")), 4));
				kit.setLeggings(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.setDisplayname(new ItemStack(Material.CHAINMAIL_LEGGINGS), "§b§lVillager §a§lLvL 2§8 || §bLeggings"), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
				kit.setBoots(Utils.hideFlags(Utils.setUnbreakable(Utils.setDisplayname(new ItemStack(Material.CHAINMAIL_BOOTS), "§b§lVillager §a§lLvL 2§8 || §bBoots")), 4));
			}
			{// Villager Level 3 \\
				Kit kit = new Kit(KitPvPKit.VILLAGER.getName() + " 3");
				kit.setItem(0, Utils.hideFlags(Utils.addEnchantment(Utils.addEnchantment(Utils.setDisplayname(new ItemStack(Material.EMERALD), "§b§lVillager §a§lLvL 3§8 || §bWeapon"), Enchantment.DAMAGE_ALL, 9), Enchantment.FIRE_ASPECT, 1), 4));
				kit.setItem(1, Utils.hideFlags(Utils.addEnchantment(Utils.setLore(Utils.setDurability(Utils.setDisplayname(new ItemStack(Material.BREAD), "§b§lVillager §a§lLvL 3§8 || §aTrade System"), 3), ItemType.TRADE_I.addEnchantment(new ArrayList<String>())), Enchantment.DURABILITY, 1), 5));
				
				kit.setHelmet(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.setDisplayname(new ItemStack(Material.CHAINMAIL_HELMET), "§b§lVillager §a§lLvL 3§8 || §bHelmet"), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
				kit.setChestplate(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.setDisplayname(new ItemStack(Material.CHAINMAIL_CHESTPLATE), "§b§lVillager §a§lLvL 3§8 || §bChestplate"), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
				kit.setLeggings(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.setDisplayname(new ItemStack(Material.CHAINMAIL_LEGGINGS), "§b§lVillager §a§lLvL 3§8 || §bLeggings"), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
				kit.setBoots(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.setDisplayname(new ItemStack(Material.CHAINMAIL_BOOTS), "§b§lVillager §a§lLvL 3§8 || §bBoots"), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
			}
			{// Assassin Level 1 \\
				Kit kit = new Kit(KitPvPKit.ASSASSIN.getName() + " 1");
				kit.setItem(0, Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.setDisplayname(new ItemStack(Material.DIAMOND_SWORD), "§b§lAssassin §a§lLvL 1§8 || §bWeapon"), Enchantment.DAMAGE_ALL, 1)), 4));
				
				kit.setHelmet(Utils.hideFlags(Utils.setUnbreakable(Utils.setDisplayname(new ItemStack(Material.LEATHER_HELMET), "§b§lAssassin §a§lLvL 1§8 || §bHelmet")), 4));
				kit.setChestplate(Utils.hideFlags(Utils.setUnbreakable(Utils.setDisplayname(new ItemStack(Material.LEATHER_CHESTPLATE), "§b§lAssassin §a§lLvL 1§8 || §bChestplate")), 4));
				kit.setLeggings(Utils.hideFlags(Utils.setUnbreakable(Utils.setDisplayname(new ItemStack(Material.LEATHER_LEGGINGS), "§b§lAssassin §a§lLvL 1§8 || §bLeggings")), 4));
				kit.setBoots(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.addEnchantment(Utils.setDisplayname(new ItemStack(Material.DIAMOND_BOOTS), "§b§lAssassin §a§lLvL 1§8 || §bBoots"), Enchantment.PROTECTION_ENVIRONMENTAL, 1), Enchantment.PROTECTION_FALL, 3)), 4));
				kit.setPotionEffect(new PotionEffect(PotionEffectType.SPEED, 2000000, 1));
			}
			{// Lord Level 1 \\
				Kit kit = new Kit(KitPvPKit.LORD.getName() + " 1");
				kit.setItem(0, Utils.hideFlags(Utils.setUnbreakable(Utils.setDisplayname(new ItemStack(Material.GOLD_SWORD), "§b§lLord §a§lLvL 1§8 || §bWeapon")), 4));
				kit.setItem(1, Utils.setDisplayname(new ItemStack(Material.GOLDEN_APPLE, 5), "§b§lLord §a§lLvL 1§8 || §eGolden Apple"));
				
				kit.setHelmet(Utils.hideFlags(Utils.setUnbreakable(Utils.setDisplayname(new ItemStack(Material.GOLD_HELMET), "§b§lLord §a§lLvL 1§8 || §bHelmet")), 4));
				kit.setChestplate(Utils.hideFlags(Utils.setUnbreakable(Utils.setDisplayname(new ItemStack(Material.CHAINMAIL_CHESTPLATE), "§b§lLord §a§lLvL 1§8 || §bChestplate")), 4));
				kit.setLeggings(Utils.hideFlags(Utils.setUnbreakable(Utils.setDisplayname(new ItemStack(Material.CHAINMAIL_LEGGINGS), "§b§lLord §a§lLvL 1§8 || §bLeggings")), 4));
				kit.setBoots(Utils.hideFlags(Utils.setUnbreakable(Utils.setDisplayname(new ItemStack(Material.CHAINMAIL_BOOTS), "§b§lLord §a§lLvL 1§8 || §bBoots")), 4));
				kit.setPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 2000000, 1));
			}
			{// Vampire Level 1 \\
				Kit kit = new Kit(KitPvPKit.VAMPIRE.getName() + " 1");
				kit.setItem(0, Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.setLore(Utils.setDisplayname(new ItemStack(Material.IRON_SWORD), "§b§lVampire §a§lLvL 1§8 || §bWeapon"), ItemType.VAMPIRE_I.addEnchantment(new ArrayList<String>())), Enchantment.DURABILITY, 1)), 5));
				
				kit.setHelmet(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.addColor(Utils.setDisplayname(new ItemStack(Material.LEATHER_HELMET), "§b§lVampire §a§lLvL 1§8 || §bHelmet"), Color.fromBGR(50, 10, 150)), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));
				kit.setChestplate(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.addColor(Utils.setDisplayname(new ItemStack(Material.LEATHER_CHESTPLATE), "§b§lVampire §a§lLvL 1§8 || §bChestplate"), Color.fromBGR(50, 10, 150)), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));
				kit.setLeggings(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.addColor(Utils.setDisplayname(new ItemStack(Material.LEATHER_LEGGINGS), "§b§lVampire §a§lLvL 1§8 || §bLeggings"), Color.fromBGR(50, 10, 150)), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));
				kit.setBoots(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.addColor(Utils.setDisplayname(new ItemStack(Material.LEATHER_BOOTS), "§b§lVampire §a§lLvL 1§8 || §bBoots"), Color.fromBGR(50, 10, 150)), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));
			}
			{// DarkMage Level 1 \\
				Kit kit = new Kit(KitPvPKit.DARKMAGE.getName() + " 1");
				kit.setItem(0, Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.setLore(Utils.setDisplayname(new ItemStack(Material.STONE_SWORD), "§b§lDarkMage §a§lLvL 1§8 || §bWeapon"), ItemType.MAGIC_I.addEnchantment(new ArrayList<String>())), Enchantment.DAMAGE_ALL, 1)), 4));
				kit.setItem(1, Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.setLore(Utils.setDisplayname(new ItemStack(Material.BREWING_STAND_ITEM), "§b§lDarkMage §a§lLvL 1§8 || §ePotion Launcher"), ItemType.POTION_LAUNCHER_I.addEnchantment(new ArrayList<String>())), Enchantment.DURABILITY, 1)), 5));
				
				kit.setHelmet(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.addColor(Utils.setDisplayname(new ItemStack(Material.LEATHER_HELMET), "§b§lDarkMage §a§lLvL 1§8 || §bHelmet"), Color.BLACK), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
				kit.setChestplate(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.addColor(Utils.setLore(Utils.setDisplayname(new ItemStack(Material.LEATHER_CHESTPLATE), "§b§lDarkMage §a§lLvL 1§8 || §bChestplate"), ArmorType.WITHER_ARMOR_I.addEnchantment(new ArrayList<String>())), Color.BLACK), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));
				kit.setLeggings(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.addColor(Utils.setDisplayname(new ItemStack(Material.LEATHER_LEGGINGS), "§b§lDarkMage §a§lLvL 1§8 || §bLeggings"), Color.BLACK), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));
				kit.setBoots(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.addColor(Utils.setDisplayname(new ItemStack(Material.LEATHER_BOOTS), "§b§lDarkMage §a§lLvL 1§8 || §bBoots"), Color.BLACK), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
			}
			{// Beast Level 1 \\
				Kit kit = new Kit(KitPvPKit.BEAST.getName() + " 1");
				kit.setItem(0, Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.setLore(Utils.setDisplayname(new ItemStack(Material.IRON_AXE), "§b§lBeast §a§lLvL 1§8 || §bWeapon"), ItemType.KNOCKUP_I.addEnchantment(new ArrayList<String>())), Enchantment.DAMAGE_ALL, 1)), 4));
				
				kit.setHelmet(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.addColor(Utils.setDisplayname(new ItemStack(Material.LEATHER_HELMET), "§b§lBeast §a§lLvL 1§8 || §bHelmet"), Color.BLACK), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
				kit.setChestplate(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.addColor(Utils.setDisplayname(new ItemStack(Material.LEATHER_CHESTPLATE), "§b§lBeast §a§lLvL 1§8 || §bChestplate"), Color.BLACK), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
				kit.setLeggings(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.addColor(Utils.setDisplayname(new ItemStack(Material.LEATHER_LEGGINGS), "§b§lBeast §a§lLvL 1§8 || §bLeggings"), Color.BLACK), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
				kit.setBoots(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.addColor(Utils.setDisplayname(new ItemStack(Material.LEATHER_BOOTS), "§b§lBeast §a§lLvL 1§8 || §bBoots"), Color.BLACK), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
			}
			{// Fish Level 1 \\
				Kit kit = new Kit(KitPvPKit.FISH.getName() + " 1");
				kit.setItem(0, Utils.hideFlags(Utils.addEnchantment(Utils.setDisplayname(new ItemStack(Material.RAW_FISH), "§b§lFish §a§lLvL 1§8 || §bWeapon"), Enchantment.DAMAGE_ALL, 8), 4));
				kit.setItem(1, Utils.hideFlags(Utils.addEnchantment(Utils.setDurability(Utils.setDisplayname(new ItemStack(Material.RAW_FISH), "§b§lFish §a§lLvL 1§8 || §6Knockback Fish"), 2), Enchantment.KNOCKBACK, 5), 4));
				kit.setItem(2, Utils.hideFlags(Utils.addEnchantment(Utils.addEnchantment(Utils.setDisplayname(new ItemStack(Material.COOKED_FISH), "§b§lFish §a§lLvL 1§8 || §4Hot Fish"), Enchantment.DAMAGE_ALL, 2), Enchantment.FIRE_ASPECT, 2), 4));
				
				kit.setHelmet(Utils.hideFlags(Utils.setUnbreakable(Utils.setDisplayname(new ItemStack(Material.CHAINMAIL_HELMET), "§b§lFish §a§lLvL 1§8 || §bHelmet")), 4));
				kit.setChestplate(Utils.hideFlags(Utils.setUnbreakable(Utils.setDisplayname(new ItemStack(Material.CHAINMAIL_CHESTPLATE), "§b§lFish §a§lLvL 1§8 || §bChestplate")), 4));
				kit.setLeggings(Utils.hideFlags(Utils.setUnbreakable(Utils.setDisplayname(new ItemStack(Material.CHAINMAIL_LEGGINGS), "§b§lFish §a§lLvL 1§8 || §bLeggings")), 4));
				kit.setBoots(Utils.hideFlags(Utils.setUnbreakable(Utils.setDisplayname(new ItemStack(Material.CHAINMAIL_BOOTS), "§b§lFish §a§lLvL 1§8 || §bBoots")), 4));
				kit.setPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, 2000000, 2));
			}
			{// Heavy Level 1 \\
				Kit kit = new Kit(KitPvPKit.HEAVY.getName() + " 1");
				kit.setItem(0, Utils.hideFlags(Utils.setUnbreakable(Utils.setDisplayname(new ItemStack(Material.IRON_SWORD), "§b§lHeavy §a§lLvL 1§8 || §bWeapon")), 4));
				kit.setItem(1, Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.setLore(Utils.setDisplayname(new ItemStack(Material.BOW), "§b§lHeavy §a§lLvL 1§8 || §bBow"), ProjectileType.ARROW_SPLIT_I.addEnchantment(new ArrayList<String>())), Enchantment.DURABILITY, 1)), 5));
				kit.setItem(2, Utils.setDisplayname(new ItemStack(Material.ARROW, 8), "§b§lHeavy §a§lLvL 1§8 || §bArrow"));

				kit.setHelmet(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.addColor(Utils.setDisplayname(new ItemStack(Material.LEATHER_HELMET), "§b§lHeavy §a§lLvL 1§8 || §bHelmet"), Color.fromBGR(204, 100, 2)), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));
				kit.setChestplate(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.addColor(Utils.setDisplayname(new ItemStack(Material.LEATHER_CHESTPLATE), "§b§lHeavy §a§lLvL 1§8 || §bChestplate"), Color.fromBGR(204, 100, 2)), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));
				kit.setLeggings(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.addColor(Utils.setDisplayname(new ItemStack(Material.LEATHER_LEGGINGS), "§b§lHeavy §a§lLvL 1§8 || §bLeggings"), Color.fromBGR(204, 100, 2)), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));
				kit.setBoots(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.addColor(Utils.setDisplayname(new ItemStack(Material.LEATHER_BOOTS), "§b§lHeavy §a§lLvL 1§8 || §bBoots"), Color.fromBGR(204, 100, 2)), Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 4));
			}
			{// GrimReaper Level 1 \\
				Kit kit = new Kit(KitPvPKit.GRIMREAPER.getName() + " 1");
				kit.setItem(0, Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.setDisplayname(new ItemStack(Material.STONE_AXE), "§b§lGrimReaper §a§lLvL 1§8 || §bWeapon"), Enchantment.DAMAGE_ALL, 3)), 4));
				
				kit.setHelmet(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.setDurability(Utils.setDisplayname(new ItemStack(Material.SKULL_ITEM), "§b§lGrimReaper §a§lLvL 1§8 || §bHelmet"), 1), Enchantment.PROTECTION_ENVIRONMENTAL, 7)), 4));
				kit.setChestplate(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.setLore(Utils.addColor(Utils.setDisplayname(new ItemStack(Material.LEATHER_CHESTPLATE), "§b§lGrimReaper §a§lLvL 1§8 || §bChestplate"), Color.fromBGR(100, 100, 100)), ArmorType.MOLTEN_ARMOR_I.addEnchantment(new ArrayList<String>())), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
				kit.setLeggings(Utils.hideFlags(Utils.setUnbreakable(Utils.addColor(Utils.setDisplayname(new ItemStack(Material.LEATHER_LEGGINGS), "§b§lGrimReaper §a§lLvL 1§8 || §bLeggings"), Color.fromBGR(100, 100, 100))), 4));
				kit.setBoots(Utils.hideFlags(Utils.setUnbreakable(Utils.addColor(Utils.setDisplayname(new ItemStack(Material.LEATHER_BOOTS), "§b§lGrimReaper §a§lLvL 1§8 || §bBoots"), Color.fromBGR(100, 100, 100))), 4));
				kit.setPotionEffect(new PotionEffect(PotionEffectType.SLOW, 2000000, 0));
			}
			{// Miner Level 1 \\
				Kit kit = new Kit(KitPvPKit.MINER.getName() + " 1");
				kit.setItem(0, Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.setDisplayname(new ItemStack(Material.GOLD_PICKAXE), "§b§lMiner §a§lLvL 1§8 || §bWeapon"), Enchantment.DAMAGE_ALL, 5)), 4));
				kit.setItem(1, Utils.hideFlags(Utils.addEnchantment(Utils.setLore(Utils.setDisplayname(new ItemStack(Material.COAL), "§b§lHeavy §a§lLvL 1§8 || §1Miner Power"), ItemType.HEALING_KIT_I.addEnchantment(new ArrayList<String>())), Enchantment.DURABILITY, 1), 5));
				
				kit.setHelmet(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.addColor(Utils.setDisplayname(new ItemStack(Material.LEATHER_HELMET), "§b§lMiner §a§lLvL 1§8 || §bHelmet"), Color.fromBGR(150, 150, 150)), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
				kit.setChestplate(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.addColor(Utils.setDisplayname(new ItemStack(Material.LEATHER_CHESTPLATE), "§b§lMiner §a§lLvL 1§8 || §bChestplate"), Color.fromBGR(150, 150, 150)), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
				kit.setLeggings(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.addColor(Utils.setDisplayname(new ItemStack(Material.LEATHER_LEGGINGS), "§b§lMiner §a§lLvL 1§8 || §bLeggings"), Color.fromBGR(150, 150, 150)), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
				kit.setBoots(Utils.hideFlags(Utils.setUnbreakable(Utils.addEnchantment(Utils.addColor(Utils.setDisplayname(new ItemStack(Material.LEATHER_BOOTS), "§b§lMiner §a§lLvL 1§8 || §bBoots"), Color.fromBGR(150, 150, 150)), Enchantment.PROTECTION_ENVIRONMENTAL, 1)), 4));
				kit.setPotionEffect(new PotionEffect(PotionEffectType.SLOW, 2000000, 0));
			}
		}
		
		private void spawnNPCs(){
			{
				NPCArmorStand npcas = NPCArmorStand.addNPCArmorStand(NPCType.SERVER_SELECTOR, new Location(getKitPvPWorld(), -5.5, 74, -7.5, -45, 0));
				npcas.setGravity(false);
				npcas.setVisible(false);
				npcas.setSmall(true);
				npcas.setUseItem(true);
				npcas.setItemStack(new ItemStack(Material.ENDER_PEARL, 1));
				npcas.setItemName("§3§lServer Selector");
				npcas.spawn();
			}
			{
				NPC npc = NPC.addNPC(NPCType.KIT_SELECTOR);
				npc.newEntity(EntityType.SKELETON, new Location(getKitPvPWorld(), -11.5, 74, 10.5, -135, 0), "§b§lKit Selector");
				npc.setSkeletonType(SkeletonType.WITHER);
				npc.setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
			}
			{
				NPC npc = NPC.addNPC(NPCType.SPECTATE);
				npc.newEntity(EntityType.SKELETON, new Location(getKitPvPWorld(), 10.5, 74, -11.5, 45, 0), "§e§lSpectate");
				npc.setSkeletonType(SkeletonType.WITHER);
				npc.setItemInHand(new ItemStack(Material.ENDER_PEARL));
			}
			{
				NPC npc = NPC.addNPC(NPCType.OMT_SHOP);
				npc.newEntity(EntityType.BLAZE, new Location(getKitPvPWorld(), -4.5, 75, -11.5, 0, 0), "§e§lOMT Shop");
			}
			{
				NPCArmorStand npcas = NPCArmorStand.addNPCArmorStand(NPCType.NORMAL, new Location(getKitPvPWorld(), -2.5, 75, 1, -135, 0));
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
				NPCArmorStand npcas = NPCArmorStand.addNPCArmorStand(NPCType.NORMAL, new Location(getKitPvPWorld(), -2, 75, -2.5, -45, 0));
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
				NPCArmorStand npcas = NPCArmorStand.addNPCArmorStand(NPCType.NORMAL, new Location(getKitPvPWorld(), 1.5, 75, -2, 45, 0));
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
				NPCArmorStand npcas = NPCArmorStand.addNPCArmorStand(NPCType.NORMAL, new Location(getKitPvPWorld(), 1, 75, 1.5, 135, 0));
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
				NPCArmorStand npcas = NPCArmorStand.addNPCArmorStand(NPCType.NORMAL, new Location(getKitPvPWorld(), -9.25, 73.5, 11.92, 90, 0));
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
				NPCArmorStand npcas = NPCArmorStand.addNPCArmorStand(NPCType.NORMAL, new Location(getKitPvPWorld(), -10.25, 73.1, 9.875, 90, 0));
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
				NPCArmorStand npcas = NPCArmorStand.addNPCArmorStand(NPCType.NORMAL, new Location(getKitPvPWorld(), -10.1, 73.15, 9.8, 60, 0));
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
				NPCArmorStand npcas = NPCArmorStand.addNPCArmorStand(NPCType.NORMAL, new Location(getKitPvPWorld(), 9.5, 75, -12.5, 0, 0));
				npcas.setGravity(false);
				npcas.setVisible(false);
				npcas.setUseItem(true);
				npcas.setItemStack(new ItemStack(Material.ENDER_PEARL, 1));
				npcas.spawn();
			}
			{
				NPCArmorStand npcas = NPCArmorStand.addNPCArmorStand(NPCType.NORMAL, new Location(getKitPvPWorld(), 10.5, 74.5, -9.5, 0, 0));
				npcas.setGravity(false);
				npcas.setVisible(false);
				npcas.setUseItem(true);
				npcas.setItemStack(new ItemStack(Material.ENDER_PEARL, 1));
				npcas.spawn();
			}
		}
		
		private void registerLobbyItems(){
			ItemStack[] lobbyitems = new ItemStack[36];
			{
				ItemStack item = new ItemStack(Material.WRITTEN_BOOK);
				BookMeta itemmeta = (BookMeta) item.getItemMeta();
				itemmeta.setDisplayName("§c§nBook of Enchantments");
				itemmeta.addPage("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25");
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
				itemmeta.setAuthor("§6§lOrbitMines§c§lKitPvP");
				item.setItemMeta(itemmeta);
				lobbyitems[0] = item;
				
			}
			{
				ItemStack item = new ItemStack(Material.EXP_BOTTLE, 1);
				ItemMeta itemmeta = item.getItemMeta();
				itemmeta.setDisplayName("§d§nAchievements");
				item.setItemMeta(itemmeta);
				lobbyitems[1] = item;
			}
			{
				ItemStack item = new ItemStack(Material.ENDER_PEARL, 1);
				ItemMeta itemmeta = item.getItemMeta();
				itemmeta.setDisplayName("§3§nServer Selector");
				item.setItemMeta(itemmeta);
				lobbyitems[4] = item;
			}
			{
				ItemStack item = new ItemStack(Material.GOLD_NUGGET, 1);
				ItemMeta itemmeta = item.getItemMeta();
				itemmeta.setDisplayName("§a§nBoosters");
				item.setItemMeta(itemmeta);
				lobbyitems[5] = item;
			}
			{
				ItemStack item = new ItemStack(Material.ENDER_CHEST, 1);
				ItemMeta itemmeta = item.getItemMeta();
				itemmeta.setDisplayName("§9§nCosmetic Perks");
				item.setItemMeta(itemmeta);
				lobbyitems[7] = item;
			}
			{
				ItemStack item = new ItemStack(Material.DIAMOND_CHESTPLATE, 1);
				ItemMeta itemmeta = item.getItemMeta();
				itemmeta.setDisplayName("§b§nKit Selector");
				item.setItemMeta(itemmeta);
				lobbyitems[8] = item;
			}
			
			this.lobbyitems = lobbyitems;
		}
	
		private void registerSpectatorItems(){
			ItemStack[] spectatoritems = new ItemStack[36];
			
			{
				ItemStack item = new ItemStack(Material.ENDER_PEARL, 1);
				ItemMeta itemmeta = item.getItemMeta();
				itemmeta.setDisplayName("§3§nBack to the Lobby");
				item.setItemMeta(itemmeta);
				lobbyitems[4] = item;
			}
			{
				ItemStack item = new ItemStack(Material.NAME_TAG, 1);
				ItemMeta itemmeta = item.getItemMeta();
				itemmeta.setDisplayName("§e§nTeleporter");
				item.setItemMeta(itemmeta);
				lobbyitems[6] = item;
			}
			
			this.spectatoritems = spectatoritems;
		}
	}
}
