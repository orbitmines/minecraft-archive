package me.O_o_Fadi_o_O.KitPvP;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

import me.O_o_Fadi_o_O.KitPvP.Inv.CosmeticPerks;
import me.O_o_Fadi_o_O.KitPvP.Inv.ServerSelector;
import me.O_o_Fadi_o_O.KitPvP.Inv.VIPPoints;
import me.O_o_Fadi_o_O.KitPvP.events.BowShot;
import me.O_o_Fadi_o_O.KitPvP.events.ClickEvent;
import me.O_o_Fadi_o_O.KitPvP.events.ClickEvent2;
import me.O_o_Fadi_o_O.KitPvP.events.DamageEvent;
import me.O_o_Fadi_o_O.KitPvP.events.DeathEvent;
import me.O_o_Fadi_o_O.KitPvP.events.EXPPickup;
import me.O_o_Fadi_o_O.KitPvP.events.EntityDamage;
import me.O_o_Fadi_o_O.KitPvP.events.ExplodeEvent;
import me.O_o_Fadi_o_O.KitPvP.events.InventoryClick;
import me.O_o_Fadi_o_O.KitPvP.events.JoinEvent;
import me.O_o_Fadi_o_O.KitPvP.events.PlayerChat;
import me.O_o_Fadi_o_O.KitPvP.events.PlayerMove;
import me.O_o_Fadi_o_O.KitPvP.events.QuitEvent;
import me.O_o_Fadi_o_O.KitPvP.events.SignsColor;
import me.confuser.barapi.BarAPI;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.Listener;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

public class Start extends JavaPlugin implements Listener {
	
	public static HashMap<Player, String> chatcolor = new HashMap<Player, String>();
	public static HashMap<Player, String> trail = new HashMap<Player, String>();
	
	public static HashMap<String, Integer> points = new HashMap<String, Integer>();
	public static HashMap<String, Integer> coins = new HashMap<String, Integer>();
	
	public static HashMap<String, String> mushroomcow = new HashMap<String, String>();
	public static HashMap<String, String> pig = new HashMap<String, String>();
	public static HashMap<String, String> wolf = new HashMap<String, String>();
	public static HashMap<String, String> sheep = new HashMap<String, String>();
	public static HashMap<String, String> horse = new HashMap<String, String>();
	public static HashMap<String, String> magmacube = new HashMap<String, String>();
	public static HashMap<String, String> slime = new HashMap<String, String>();
	public static HashMap<String, String> cow = new HashMap<String, String>();
	public static HashMap<String, String> silverfish = new HashMap<String, String>();
	public static HashMap<String, String> ocelot = new HashMap<String, String>();
	
	public static HashMap<String, Integer> white = new HashMap<String, Integer>();
	public static HashMap<String, Integer> blue = new HashMap<String, Integer>();
	public static HashMap<String, Integer> green = new HashMap<String, Integer>();
	public static HashMap<String, Integer> black = new HashMap<String, Integer>();
	public static HashMap<String, Integer> lightblue = new HashMap<String, Integer>();
	public static HashMap<String, Integer> pink = new HashMap<String, Integer>();
	public static HashMap<String, Integer> lightgreen = new HashMap<String, Integer>();
	public static HashMap<String, Integer> darkblue = new HashMap<String, Integer>();
	public static HashMap<String, Integer> purple = new HashMap<String, Integer>();
	public static HashMap<String, Integer> orange = new HashMap<String, Integer>();
	public static HashMap<String, Integer> red = new HashMap<String, Integer>();
	public static HashMap<String, Integer> cyan = new HashMap<String, Integer>();
	public static HashMap<String, Integer> yellow = new HashMap<String, Integer>();
	public static HashMap<String, Integer> gray = new HashMap<String, Integer>();
	
	public static HashMap<String, Integer> chatcolorwhite = new HashMap<String, Integer>();
	public static HashMap<String, Integer> chatcolordarkred = new HashMap<String, Integer>();
	public static HashMap<String, Integer> chatcolorblue = new HashMap<String, Integer>();
	public static HashMap<String, Integer> chatcolorgreen = new HashMap<String, Integer>();
	public static HashMap<String, Integer> chatcolorblack = new HashMap<String, Integer>();
	public static HashMap<String, Integer> chatcolorlightblue = new HashMap<String, Integer>();
	public static HashMap<String, Integer> chatcolorpink = new HashMap<String, Integer>();
	public static HashMap<String, Integer> chatcolorlightgreen = new HashMap<String, Integer>();
	public static HashMap<String, Integer> chatcolordarkblue = new HashMap<String, Integer>();
	public static HashMap<String, Integer> chatcolorred = new HashMap<String, Integer>();
	public static HashMap<String, Integer> chatcolordarkgray = new HashMap<String, Integer>();
	
	public static HashMap<String, Integer> trailsfirework = new HashMap<String, Integer>();
	public static HashMap<String, Integer> trailshappyvillager = new HashMap<String, Integer>();
	public static HashMap<String, Integer> trailshearts = new HashMap<String, Integer>();
	public static HashMap<String, Integer> trailsexplode = new HashMap<String, Integer>();
	public static HashMap<String, Integer> trailsslime = new HashMap<String, Integer>();
	public static HashMap<String, Integer> trailssmoke = new HashMap<String, Integer>();
	public static HashMap<String, Integer> trailswitch = new HashMap<String, Integer>();
	public static HashMap<String, Integer> trailscrit = new HashMap<String, Integer>();
	public static HashMap<String, Integer> trailswater = new HashMap<String, Integer>();
	public static HashMap<String, Integer> trailsnote = new HashMap<String, Integer>();
	public static HashMap<String, Integer> trailssnow = new HashMap<String, Integer>();
	public static HashMap<String, Integer> trailsenchantmenttable = new HashMap<String, Integer>();
	public static HashMap<String, Integer> trailsbubbles = new HashMap<String, Integer>();
	public static HashMap<String, Integer> trailsangryvillager = new HashMap<String, Integer>();
	public static HashMap<String, Integer> trailsmobspawner = new HashMap<String, Integer>();
	
	public static HashMap<String, Integer> hatsStoneBricks = new HashMap<String, Integer>();
	public static HashMap<String, Integer> hatsGreenGlass = new HashMap<String, Integer>();
	public static HashMap<String, Integer> hatsCactus = new HashMap<String, Integer>();
	public static HashMap<String, Integer> hatsSnow = new HashMap<String, Integer>();
	public static HashMap<String, Integer> hatsTNT = new HashMap<String, Integer>();
	public static HashMap<String, Integer> hatsCoalOre = new HashMap<String, Integer>();
	public static HashMap<String, Integer> hatsBlackGlass = new HashMap<String, Integer>();
	public static HashMap<String, Integer> hatsFurnace = new HashMap<String, Integer>();
	public static HashMap<String, Integer> hatsQuartzOre = new HashMap<String, Integer>();
	public static HashMap<String, Integer> hatsHayBale = new HashMap<String, Integer>();
	public static HashMap<String, Integer> hatsRedstoneOre = new HashMap<String, Integer>();
	public static HashMap<String, Integer> hatsIce = new HashMap<String, Integer>();
	public static HashMap<String, Integer> hatsWorkbench = new HashMap<String, Integer>();
	public static HashMap<String, Integer> hatsGrass = new HashMap<String, Integer>();
	public static HashMap<String, Integer> hatsRedGlass = new HashMap<String, Integer>();
	public static HashMap<String, Integer> hatsBedrock = new HashMap<String, Integer>();
	public static HashMap<String, Integer> hatsLapisOre = new HashMap<String, Integer>();
	public static HashMap<String, Integer> hatsRedstoneBlock = new HashMap<String, Integer>();
	public static HashMap<String, Integer> hatsQuartzBlock = new HashMap<String, Integer>();
	public static HashMap<String, Integer> hatsLapisBlock = new HashMap<String, Integer>();
	public static HashMap<String, Integer> hatsMagentaGlass = new HashMap<String, Integer>();
	public static HashMap<String, Integer> hatsCoalBlock = new HashMap<String, Integer>();
	public static HashMap<String, Integer> hatsMelon = new HashMap<String, Integer>();
	public static HashMap<String, Integer> hatsGlass = new HashMap<String, Integer>();
	public static HashMap<String, Integer> hatsYellowGlass = new HashMap<String, Integer>();
	public static HashMap<String, Integer> hatsMycelium = new HashMap<String, Integer>();
	public static HashMap<String, Integer> hatsLeaves = new HashMap<String, Integer>();
	public static HashMap<String, Integer> hatsOrangeGlass = new HashMap<String, Integer>();
	
	public static HashMap<String, Integer> disWitch = new HashMap<String, Integer>();
	public static HashMap<String, Integer> disBat = new HashMap<String, Integer>();
	public static HashMap<String, Integer> disChicken = new HashMap<String, Integer>();
	public static HashMap<String, Integer> disOcelot = new HashMap<String, Integer>();
	public static HashMap<String, Integer> disMushroomCow = new HashMap<String, Integer>();
	public static HashMap<String, Integer> disSquid = new HashMap<String, Integer>();
	public static HashMap<String, Integer> disSlime = new HashMap<String, Integer>();
	public static HashMap<String, Integer> disZombiePigman = new HashMap<String, Integer>();
	public static HashMap<String, Integer> disMagmaCube = new HashMap<String, Integer>();
	public static HashMap<String, Integer> disSkeleton = new HashMap<String, Integer>();
	public static HashMap<String, Integer> disCow = new HashMap<String, Integer>();
	public static HashMap<String, Integer> disWolf = new HashMap<String, Integer>();
	public static HashMap<String, Integer> disSpider = new HashMap<String, Integer>();
	public static HashMap<String, Integer> disSilverfish = new HashMap<String, Integer>();
	public static HashMap<String, Integer> disSheep = new HashMap<String, Integer>();
	public static HashMap<String, Integer> disCaveSpider = new HashMap<String, Integer>();
	public static HashMap<String, Integer> disCreeper = new HashMap<String, Integer>();
	
	public static HashMap<UUID, Player> owners = new HashMap<UUID, Player>();
	public static HashMap<Player, UUID> ownerpets = new HashMap<Player, UUID>();
	
	public int i = 0;
	public int i2 = 0;
	public int i3 = 0;
	
	public int bi = 0;
	
	ServerSelector sinv = new ServerSelector();
	
	public static boolean FreeKitEnabled = false;
	// KIT LEVEL HASHMAPS
	public static HashMap<String, Integer> knight = new HashMap<String, Integer>();
	public static HashMap<String, Integer> archer = new HashMap<String, Integer>();
	public static HashMap<String, Integer> soldier = new HashMap<String, Integer>();
	public static HashMap<String, Integer> wizard = new HashMap<String, Integer>();
	public static HashMap<String, Integer> tank = new HashMap<String, Integer>();
	public static HashMap<String, Integer> drunk = new HashMap<String, Integer>();
	public static HashMap<String, Integer> pyro = new HashMap<String, Integer>();
	public static HashMap<String, Integer> bunny = new HashMap<String, Integer>();
	public static HashMap<String, Integer> necromancer = new HashMap<String, Integer>();
	public static HashMap<String, Integer> king = new HashMap<String, Integer>();
	public static HashMap<String, Integer> tree = new HashMap<String, Integer>();
	public static HashMap<String, Integer> blaze = new HashMap<String, Integer>();
	public static HashMap<String, Integer> tnt = new HashMap<String, Integer>();
	public static HashMap<String, Integer> fisherman = new HashMap<String, Integer>();
	public static HashMap<String, Integer> snowgolem = new HashMap<String, Integer>();
	public static HashMap<String, Integer> librarian = new HashMap<String, Integer>();
	public static HashMap<String, Integer> spider = new HashMap<String, Integer>();
	public static HashMap<String, Integer> villager = new HashMap<String, Integer>();
	public static HashMap<String, Integer> assassin = new HashMap<String, Integer>();
	public static HashMap<String, Integer> lord = new HashMap<String, Integer>();
	public static HashMap<String, Integer> vampire = new HashMap<String, Integer>();
	public static HashMap<String, Integer> darkmage = new HashMap<String, Integer>();
	public static HashMap<String, Integer> beast = new HashMap<String, Integer>();
	public static HashMap<String, Integer> fish = new HashMap<String, Integer>();
	public static HashMap<String, Integer> heavy = new HashMap<String, Integer>();
	public static HashMap<String, Integer> grimreaper = new HashMap<String, Integer>();
	public static HashMap<String, Integer> miner = new HashMap<String, Integer>();
	
	public static HashMap<String, Integer> money = new HashMap<String, Integer>();
	public static HashMap<String, Integer> omt = new HashMap<String, Integer>();
	
	public static HashMap<String, Integer> kills = new HashMap<String, Integer>();
	public static HashMap<String, Integer> deaths = new HashMap<String, Integer>();
	public static HashMap<String, Integer> levels = new HashMap<String, Integer>();
	
	public static ArrayList<Projectile> arrow = new ArrayList<Projectile>();
	public static ArrayList<Projectile> necromancerarrowlvl1 = new ArrayList<Projectile>();
	public static ArrayList<Projectile> necromancerarrowlvl2 = new ArrayList<Projectile>();
	public static ArrayList<Projectile> tntlvl2 = new ArrayList<Projectile>();
	
	public static String TAG = "§8[§c§lKitPvP§8]§7 ";
	
	private String getquery = "";
	private String containsquery = "";
	
	static Connection connection;
	
	private KitSelector kitselector = new KitSelector(this);
	
	public static int n = 1;
	static World KitPvP = Bukkit.getWorld("KitPvPMap");
	
	public void onEnable(){
		
		setupPermissions();
		setupChat();
		setupEconomy();

		Bukkit.getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
		
		getServer().getPluginManager().registerEvents(this, this);
		getServer().getPluginManager().registerEvents(new BowShot(), this);
		getServer().getPluginManager().registerEvents(new ClickEvent(this), this);
		getServer().getPluginManager().registerEvents(new ClickEvent2(this), this);
		getServer().getPluginManager().registerEvents(new DamageEvent(), this);
		getServer().getPluginManager().registerEvents(new DeathEvent(this), this);
		getServer().getPluginManager().registerEvents(new ExplodeEvent(), this);
		getServer().getPluginManager().registerEvents(new EXPPickup(this), this);
		getServer().getPluginManager().registerEvents(new InventoryClick(this), this);
		getServer().getPluginManager().registerEvents(new JoinEvent(this), this);
		getServer().getPluginManager().registerEvents(new PlayerMove(this), this);	
		getServer().getPluginManager().registerEvents(new SignsColor(), this);
		getServer().getPluginManager().registerEvents(new PlayerChat(this), this);
		getServer().getPluginManager().registerEvents(new QuitEvent(), this);
		getServer().getPluginManager().registerEvents(new EntityDamage(), this);
		
		if(!(new File(getDataFolder(), "config.yml")).exists()){
			saveDefaultConfig();
		}
		
		FreeKitEnabled = getConfig().getBoolean("FreeKitEnabled");
		
		new BukkitRunnable(){

			@Override
			public void run() {
				
				i2++;
					
				ServerSelector.KitPvPServerItem(ServerSelector.sinv, i2);
				ServerSelector.PrisonServerItem(ServerSelector.sinv, i2);;
				ServerSelector.CreativeServerItem(ServerSelector.sinv, i2);
				ServerSelector.SurvivalServerItem(ServerSelector.sinv, i2);
				ServerSelector.SkyBlockServerItem(ServerSelector.sinv, i2);
				ServerSelector.MiniGamesServerItem(ServerSelector.sinv, i2);
				
				if(i2 == 2){
					i2 = 0;
				}
				
			}
		}.runTaskTimer(this, 0, 3);
		
		new BukkitRunnable(){

			@Override
			public void run() {
				
				i++;
					
				VIPPoints.getVIPPoints1(VIPPoints.inv, i);
				VIPPoints.getVIPPoints5(VIPPoints.inv, i);
				VIPPoints.getVIPPoints10(VIPPoints.inv, i);
				VIPPoints.getVIPPoints25(VIPPoints.inv, i);
				
				if(i == 5){
					i = 0;
				}
				
			}
		}.runTaskTimer(this, 0, 20);
		
		new BukkitRunnable(){

			@Override
			public void run() {
				
				for(Player p : Bukkit.getOnlinePlayers()){
					
					if(!BarAPI.hasBar(p)){
						
						if(bi == 0){
							BarAPI.setMessage(p, "§6§lOrbitMines§4§lNetwork §8| §6Welcome §6§l" + p.getName() + "§6!", 10);
							bi++;
						}
						else if(bi == 1){
							BarAPI.setMessage(p, "§6§lOrbitMines§4§lNetwork §8| §6You have §6§l" + Start.omt.get(p.getName()) + "§6 OMT", 10);
							bi++;
						}
						else if(bi == 2){
							BarAPI.setMessage(p, "§6§lOrbitMines§4§lNetwork §8| §6§lwww.orbitmines.com", 10);
							bi++;
						}
						else if(bi == 3){
							BarAPI.setMessage(p, "§6§lOrbitMines§4§lNetwork §8| §6IP: §6§lHub.OrbitMinesMC.com", 10);
							bi++;
						}
						else if(bi == 4){
							BarAPI.setMessage(p, "§6§lOrbitMines§4§lNetwork §8| §6You have §6§l" + Start.points.get(p.getName()) + "§6 VIP Points", 10);
							bi = 0;
						}
					}
					
				}

			}
		}.runTaskTimer(this, 0, 20);
		
		new BukkitRunnable(){

			@Override
			public void run() {
				
				for(Player p : Bukkit.getOnlinePlayers()){
					if(Start.ownerpets.containsKey(p)){
						UUID uuid = Start.ownerpets.get(p);
						if(Start.owners.containsKey(uuid)){
							for(World w : Bukkit.getWorlds()){
								for(Entity en : w.getEntities()){
									if(en.getUniqueId() == uuid){
										if(en instanceof LivingEntity){
											LivingEntity ent = (LivingEntity) en;
											Location l = Start.owners.get(uuid).getLocation();
											
											if(ClickEvent.getDistance(l, ent.getLocation()) < 20){
												ClickEvent.navigate(ent, l, 1.2);
											}
											else{
												ent.teleport(l);
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}.runTaskTimer(this, 20, 3);
		
		new BukkitRunnable(){

			@Override
			public void run() {
				
				openConnection();
				
			}
		}.runTaskAsynchronously(this);
	}
	
	public void onDisable(){
		try{
			for(World w : Bukkit.getWorlds()){
				for(Entity en : w.getEntities()){
					for(Player p : Bukkit.getOnlinePlayers()){
						if(Start.ownerpets.containsKey(p)){
							if(en.getUniqueId() == Start.ownerpets.get(p)){
								if(en instanceof LivingEntity){
									en.remove();
									Start.owners.remove(Start.ownerpets.get(p));
									Start.ownerpets.remove(p);
								}
							}
						}
					}
				}
			}
		}catch(Exception ex){
			
		}
	}
	
	public boolean containsPath(String name, String Table, String player){

		containsquery = "SELECT `" + name + "` FROM `" + Table + "` WHERE `" + name + "`='" + player + "'";
		
		try {
			ResultSet rs = connection.prepareStatement(containsquery).executeQuery();
			
			boolean temp = rs.next();
			rs.close();
			return temp;
			
		} catch (SQLException ex) {
			ex.printStackTrace();
			return true;
		}
	}
	public synchronized void insertPath(String player, String Table, String column, int i){

		getquery = "INSERT INTO `" + Table + "` (`name`, `" + column + "`) VALUES ('" + player + "', '" + i + "')";
		
		
		try{
			PreparedStatement ps = connection.prepareStatement(getquery);
			ps.execute();
			ps.close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
	}
	
	public boolean containsHats(String name, String Table, String player){

		containsquery = "SELECT `" + name + "` FROM `" + Table + "` WHERE `" + name + "`='" + player + "'";
		
		try {
			ResultSet rs = connection.prepareStatement(containsquery).executeQuery();
			
			boolean temp = rs.next();
			rs.close();
			return temp;
			
		} catch (SQLException ex) {
			ex.printStackTrace();
			return true;
		}
	}
	public boolean containsDisguises(String name, String Table, String player){

		containsquery = "SELECT `" + name + "` FROM `" + Table + "` WHERE `" + name + "`='" + player + "'";
		
		try {
			ResultSet rs = connection.prepareStatement(containsquery).executeQuery();
			
			boolean temp = rs.next();
			rs.close();
			return temp;
			
		} catch (SQLException ex) {
			ex.printStackTrace();
			return true;
		}
	}
	public synchronized void insertHats(String player, String Table, String hat, int i){

		getquery = "INSERT INTO `" + Table + "` (`name`, `" + hat + "`) VALUES ('" + player + "', '" + i + "')";
		
		
		try{
			PreparedStatement ps = connection.prepareStatement(getquery);
			ps.execute();
			ps.close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
	}
	public synchronized void insertDisguises(String player, String Table, String disguise, int i){

		getquery = "INSERT INTO `" + Table + "` (`name`, `" + disguise + "`) VALUES ('" + player + "', '" + i + "')";
		
		
		try{
			PreparedStatement ps = connection.prepareStatement(getquery);
			ps.execute();
			ps.close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
	}
	public synchronized void insertColorCode(String player, String Type, String stats){

		if(Type.equals("CHATCOLOR")){
			getquery = "INSERT INTO `ChatColors` (`name`, `color`) VALUES ('" + player + "', '" + stats + "')";
		}
		
		try{
			PreparedStatement ps = connection.prepareStatement(getquery);
			ps.execute();
			ps.close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
	}
	
	public synchronized void insertTrail(String player, String Type, int stats){
		if(Type.equals("ANGRYVILLAGER")){
			getquery = "INSERT INTO `Trails-AngryVillager` (`name`, `angryvillager`) VALUES ('" + player + "', '" + stats + "')";
		}
		if(Type.equals("BUBBLE")){
			getquery = "INSERT INTO `Trails-Bubble` (`name`, `bubble`) VALUES ('" + player + "', '" + stats + "')";
		}
		if(Type.equals("CRIT")){
			getquery = "INSERT INTO `Trails-Crit` (`name`, `crit`) VALUES ('" + player + "', '" + stats + "')";
		}
		if(Type.equals("ETABLE")){
			getquery = "INSERT INTO `Trails-ETable` (`name`, `etable`) VALUES ('" + player + "', '" + stats + "')";
		}
		if(Type.equals("EXPLODE")){
			getquery = "INSERT INTO `Trails-Explode` (`name`, `explode`) VALUES ('" + player + "', '" + stats + "')";
		}
		if(Type.equals("FIREWORK")){
			getquery = "INSERT INTO `Trails-Firework` (`name`, `firework`) VALUES ('" + player + "', '" + stats + "')";
		}
		if(Type.equals("HAPPYVILLAGER")){
			getquery = "INSERT INTO `Trails-HappyVillager` (`name`, `happyvillager`) VALUES ('" + player + "', '" + stats + "')";
		}
		if(Type.equals("HEARTS")){
			getquery = "INSERT INTO `Trails-Hearts` (`name`, `hearts`) VALUES ('" + player + "', '" + stats + "')";
		}
		if(Type.equals("MOBSPAWNER")){
			getquery = "INSERT INTO `Trails-MobSpawner` (`name`, `mobspawner`) VALUES ('" + player + "', '" + stats + "')";
		}
		if(Type.equals("MUSIC")){
			getquery = "INSERT INTO `Trails-Music` (`name`, `music`) VALUES ('" + player + "', '" + stats + "')";
		}
		if(Type.equals("SLIME")){
			getquery = "INSERT INTO `Trails-Slime` (`name`, `slime`) VALUES ('" + player + "', '" + stats + "')";
		}
		if(Type.equals("SMOKE")){
			getquery = "INSERT INTO `Trails-Smoke` (`name`, `smoke`) VALUES ('" + player + "', '" + stats + "')";
		}
		if(Type.equals("SNOW")){
			getquery = "INSERT INTO `Trails-Snow` (`name`, `snow`) VALUES ('" + player + "', '" + stats + "')";
		}
		if(Type.equals("WATER")){
			getquery = "INSERT INTO `Trails-Water` (`name`, `water`) VALUES ('" + player + "', '" + stats + "')";
		}
		if(Type.equals("WITCH")){
			getquery = "INSERT INTO `Trails-Witch` (`name`, `witch`) VALUES ('" + player + "', '" + stats + "')";
		}
		
		try{
			PreparedStatement ps = connection.prepareStatement(getquery);
			ps.execute();
			ps.close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
	}
	
	public synchronized void insertPets(String player, String Type, String petname){
		if(Type.equals("MUSHROOMCOW")){
			getquery = "INSERT INTO `Pets-MushroomCow` (`name`, `petname`) VALUES ('" + player + "', '" + petname + "')";
		}
		if(Type.equals("PIG")){
			getquery = "INSERT INTO `Pets-Pig` (`name`, `petname`) VALUES ('" + player + "', '" + petname + "')";
		}
		if(Type.equals("WOLF")){
			getquery = "INSERT INTO `Pets-Wolf` (`name`, `petname`) VALUES ('" + player + "', '" + petname + "')";
		}
		if(Type.equals("SHEEP")){
			getquery = "INSERT INTO `Pets-Sheep` (`name`, `petname`) VALUES ('" + player + "', '" + petname + "')";
		}
		if(Type.equals("HORSE")){
			getquery = "INSERT INTO `Pets-Horse` (`name`, `petname`) VALUES ('" + player + "', '" + petname + "')";
		}
		if(Type.equals("MAGMACUBE")){
			getquery = "INSERT INTO `Pets-MagmaCube` (`name`, `petname`) VALUES ('" + player + "', '" + petname + "')";
		}
		if(Type.equals("SLIME")){
			getquery = "INSERT INTO `Pets-Slime` (`name`, `petname`) VALUES ('" + player + "', '" + petname + "')";
		}
		if(Type.equals("COW")){
			getquery = "INSERT INTO `Pets-Cow` (`name`, `petname`) VALUES ('" + player + "', '" + petname + "')";
		}
		if(Type.equals("SILVERFISH")){
			getquery = "INSERT INTO `Pets-Silverfish` (`name`, `petname`) VALUES ('" + player + "', '" + petname + "')";
		}
		if(Type.equals("OCELOT")){
			getquery = "INSERT INTO `Pets-Ocelot` (`name`, `petname`) VALUES ('" + player + "', '" + petname + "')";
		}
		
		try{
			PreparedStatement ps = connection.prepareStatement(getquery);
			ps.execute();
			ps.close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
	}
	public synchronized void insertChatColors(String player, String Type, int yes){
		if(Type.equals("DARKRED")){
			getquery = "INSERT INTO `ChatColors-DarkRed` (`name`, `darkred`) VALUES ('" + player + "', '" + yes + "')";
		}
		if(Type.equals("LIGHTGREEN")){
			getquery = "INSERT INTO `ChatColors-LightGreen` (`name`, `lightgreen`) VALUES ('" + player + "', '" + yes + "')";
		}
		if(Type.equals("DARKGRAY")){
			getquery = "INSERT INTO `ChatColors-DarkGray` (`name`, `darkgray`) VALUES ('" + player + "', '" + yes + "')";
		}
		if(Type.equals("RED")){
			getquery = "INSERT INTO `ChatColors-Red` (`name`, `red`) VALUES ('" + player + "', '" + yes + "')";
		}
		if(Type.equals("WHITE")){
			getquery = "INSERT INTO `ChatColors-White` (`name`, `white`) VALUES ('" + player + "', '" + yes + "')";
		}
		if(Type.equals("LIGHTBLUE")){
			getquery = "INSERT INTO `ChatColors-LightBlue` (`name`, `lightblue`) VALUES ('" + player + "', '" + yes + "')";
		}
		if(Type.equals("PINK")){
			getquery = "INSERT INTO `ChatColors-Pink` (`name`, `pink`) VALUES ('" + player + "', '" + yes + "')";
		}
		if(Type.equals("BLUE")){
			getquery = "INSERT INTO `ChatColors-Blue` (`name`, `blue`) VALUES ('" + player + "', '" + yes + "')";
		}
		if(Type.equals("DARKBLUE")){
			getquery = "INSERT INTO `ChatColors-DarkBlue` (`name`, `darkblue`) VALUES ('" + player + "', '" + yes + "')";
		}
		if(Type.equals("GREEN")){
			getquery = "INSERT INTO `ChatColors-Green` (`name`, `green`) VALUES ('" + player + "', '" + yes + "')";
		}
		if(Type.equals("BLACK")){
			getquery = "INSERT INTO `ChatColors-Black` (`name`, `black`) VALUES ('" + player + "', '" + yes + "')";
		}
		
		try{
			PreparedStatement ps = connection.prepareStatement(getquery);
			ps.execute();
			ps.close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
	}
	public synchronized void insertWardrobe(String player, String Type, int yes){
		if(Type.equals("WHITE")){
			getquery = "INSERT INTO `Wardrobe-White` (`name`, `white`) VALUES ('" + player + "', '" + yes + "')";
		}
		if(Type.equals("BLUE")){
			getquery = "INSERT INTO `Wardrobe-Blue` (`name`, `blue`) VALUES ('" + player + "', '" + yes + "')";
		}
		if(Type.equals("GREEN")){
			getquery = "INSERT INTO `Wardrobe-Green` (`name`, `green`) VALUES ('" + player + "', '" + yes + "')";
		}
		if(Type.equals("BLACK")){
			getquery = "INSERT INTO `Wardrobe-Black` (`name`, `black`) VALUES ('" + player + "', '" + yes + "')";
		}
		if(Type.equals("LIGHTBLUE")){
			getquery = "INSERT INTO `Wardrobe-LightBlue` (`name`, `lightblue`) VALUES ('" + player + "', '" + yes + "')";
		}
		if(Type.equals("PINK")){
			getquery = "INSERT INTO `Wardrobe-Pink` (`name`, `pink`) VALUES ('" + player + "', '" + yes + "')";
		}
		if(Type.equals("LIGHTGREEN")){
			getquery = "INSERT INTO `Wardrobe-LightGreen` (`name`, `lightgreen`) VALUES ('" + player + "', '" + yes + "')";
		}
		if(Type.equals("DARKBLUE")){
			getquery = "INSERT INTO `Wardrobe-DarkBlue` (`name`, `darkblue`) VALUES ('" + player + "', '" + yes + "')";
		}
		if(Type.equals("PURPLE")){
			getquery = "INSERT INTO `Wardrobe-Purple` (`name`, `purple`) VALUES ('" + player + "', '" + yes + "')";
		}
		if(Type.equals("ORANGE")){
			getquery = "INSERT INTO `Wardrobe-Orange` (`name`, `orange`) VALUES ('" + player + "', '" + yes + "')";
		}
		if(Type.equals("RED")){
			getquery = "INSERT INTO `Wardrobe-Red` (`name`, `red`) VALUES ('" + player + "', '" + yes + "')";
		}
		if(Type.equals("CYAN")){
			getquery = "INSERT INTO `Wardrobe-Cyan` (`name`, `cyan`) VALUES ('" + player + "', '" + yes + "')";
		}
		if(Type.equals("YELLOW")){
			getquery = "INSERT INTO `Wardrobe-Yellow` (`name`, `yellow`) VALUES ('" + player + "', '" + yes + "')";
		}
		if(Type.equals("GRAY")){
			getquery = "INSERT INTO `Wardrobe-Gray` (`name`, `gray`) VALUES ('" + player + "', '" + yes + "')";
		}
		
		try{
			PreparedStatement ps = connection.prepareStatement(getquery);
			ps.execute();
			ps.close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
	}
	
	public int getVIPPoints(Player p){
		int points = 0;
		
		String query = "SELECT `points` FROM `VIPPoints` WHERE `name` = '" + p.getName() + "'"; 
		
		try{
			ResultSet rs = connection.prepareStatement(query).executeQuery();
			
			while (rs.next()) {
				points = rs.getInt("points");
			}
			
			rs.close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
		return points;
	}
	public String getMushroomCowName(Player p){
		String petname = "";
		
		String query = "SELECT `petname` FROM `Pets-MushroomCow` WHERE `name` = '" + p.getName() + "'"; 
		
		try{
			ResultSet rs = connection.prepareStatement(query).executeQuery();
			
			while (rs.next()) {
				petname = rs.getString("petname");
			}
			
			rs.close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
		return petname;
	}
	public String getPigName(Player p){
		String petname = "";
		
		String query = "SELECT `petname` FROM `Pets-Pig` WHERE `name` = '" + p.getName() + "'"; 
		
		try{
			ResultSet rs = connection.prepareStatement(query).executeQuery();
			
			while (rs.next()) {
				petname = rs.getString("petname");
			}
			
			rs.close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
		return petname;
	}
	public String getWolfName(Player p){
		String petname = "";
		
		String query = "SELECT `petname` FROM `Pets-Wolf` WHERE `name` = '" + p.getName() + "'"; 
		
		try{
			ResultSet rs = connection.prepareStatement(query).executeQuery();
			
			while (rs.next()) {
				petname = rs.getString("petname");
			}
			
			rs.close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
		return petname;
	}
	public String getSheepName(Player p){
		String petname = "";
		
		String query = "SELECT `petname` FROM `Pets-Sheep` WHERE `name` = '" + p.getName() + "'"; 
		
		try{
			ResultSet rs = connection.prepareStatement(query).executeQuery();
			
			while (rs.next()) {
				petname = rs.getString("petname");
			}
			
			rs.close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
		return petname;
	}
	public String getHorseName(Player p){
		String petname = "";
		
		String query = "SELECT `petname` FROM `Pets-Horse` WHERE `name` = '" + p.getName() + "'"; 
		
		try{
			ResultSet rs = connection.prepareStatement(query).executeQuery();
			
			while (rs.next()) {
				petname = rs.getString("petname");
			}
			
			rs.close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
		return petname;
	}
	public String getMagmaCubeName(Player p){
		String petname = "";
		
		String query = "SELECT `petname` FROM `Pets-MagmaCube` WHERE `name` = '" + p.getName() + "'"; 
		
		try{
			ResultSet rs = connection.prepareStatement(query).executeQuery();
			
			while (rs.next()) {
				petname = rs.getString("petname");
			}
			
			rs.close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
		return petname;
	}
	public String getSlimeName(Player p){
		String petname = "";
		
		String query = "SELECT `petname` FROM `Pets-Slime` WHERE `name` = '" + p.getName() + "'"; 
		
		try{
			ResultSet rs = connection.prepareStatement(query).executeQuery();
			
			while (rs.next()) {
				petname = rs.getString("petname");
			}
			
			rs.close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
		return petname;
	}
	public String getCowName(Player p){
		String petname = "";
		
		String query = "SELECT `petname` FROM `Pets-Cow` WHERE `name` = '" + p.getName() + "'"; 
		
		try{
			ResultSet rs = connection.prepareStatement(query).executeQuery();
			
			while (rs.next()) {
				petname = rs.getString("petname");
			}
			
			rs.close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
		return petname;
	}
	public String getSilverfishName(Player p){
		String petname = "";
		
		String query = "SELECT `petname` FROM `Pets-Silverfish` WHERE `name` = '" + p.getName() + "'"; 
		
		try{
			ResultSet rs = connection.prepareStatement(query).executeQuery();
			
			while (rs.next()) {
				petname = rs.getString("petname");
			}
			
			rs.close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
		return petname;
	}
	public String getOcelotName(Player p){
		String petname = "";
		
		String query = "SELECT `petname` FROM `Pets-Ocelot` WHERE `name` = '" + p.getName() + "'"; 
		
		try{
			ResultSet rs = connection.prepareStatement(query).executeQuery();
			
			while (rs.next()) {
				petname = rs.getString("petname");
			}
			
			rs.close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
		return petname;
	}
	public int getWhiteWardrobe(Player p){
		int armor = 0;
		
		String query = "SELECT `white` FROM `Wardrobe-White` WHERE `name` = '" + p.getName() + "'"; 
		
		try{
			ResultSet rs = connection.prepareStatement(query).executeQuery();
			
			while (rs.next()) {
				armor = rs.getInt("white");
			}
			
			rs.close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
		return armor;
	}
	public int getBlueWardrobe(Player p){
		int armor = 0;
		
		String query = "SELECT `blue` FROM `Wardrobe-Blue` WHERE `name` = '" + p.getName() + "'"; 
		
		try{
			ResultSet rs = connection.prepareStatement(query).executeQuery();
			
			while (rs.next()) {
				armor = rs.getInt("blue");
			}
			
			rs.close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
		return armor;
	}
	public int getGreenWardrobe(Player p){
		int armor = 0;
		
		String query = "SELECT `green` FROM `Wardrobe-Green` WHERE `name` = '" + p.getName() + "'"; 
		
		try{
			ResultSet rs = connection.prepareStatement(query).executeQuery();
			
			while (rs.next()) {
				armor = rs.getInt("green");
			}
			
			rs.close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
		return armor;
	}
	public int getBlackWardrobe(Player p){
		int armor = 0;
		
		String query = "SELECT `black` FROM `Wardrobe-Black` WHERE `name` = '" + p.getName() + "'"; 
		
		try{
			ResultSet rs = connection.prepareStatement(query).executeQuery();
			
			while (rs.next()) {
				armor = rs.getInt("black");
			}
			
			rs.close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
		return armor;
	}
	public int getLightBlueWardrobe(Player p){
		int armor = 0;
		
		String query = "SELECT `lightblue` FROM `Wardrobe-LightBlue` WHERE `name` = '" + p.getName() + "'"; 
		
		try{
			ResultSet rs = connection.prepareStatement(query).executeQuery();
			
			while (rs.next()) {
				armor = rs.getInt("lightblue");
			}
			
			rs.close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
		return armor;
	}
	public int getPinkWardrobe(Player p){
		int armor = 0;
		
		String query = "SELECT `pink` FROM `Wardrobe-Pink` WHERE `name` = '" + p.getName() + "'"; 
		
		try{
			ResultSet rs = connection.prepareStatement(query).executeQuery();
			
			while (rs.next()) {
				armor = rs.getInt("pink");
			}
			
			rs.close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
		return armor;
	}
	public int getLightGreenWardrobe(Player p){
		int armor = 0;
		
		String query = "SELECT `lightgreen` FROM `Wardrobe-LightGreen` WHERE `name` = '" + p.getName() + "'"; 
		
		try{
			ResultSet rs = connection.prepareStatement(query).executeQuery();
			
			while (rs.next()) {
				armor = rs.getInt("lightgreen");
			}
			
			rs.close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
		return armor;
	}
	public int getDarkBlueWardrobe(Player p){
		int armor = 0;
		
		String query = "SELECT `darkblue` FROM `Wardrobe-DarkBlue` WHERE `name` = '" + p.getName() + "'"; 
		
		try{
			ResultSet rs = connection.prepareStatement(query).executeQuery();
			
			while (rs.next()) {
				armor = rs.getInt("darkblue");
			}
			
			rs.close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
		return armor;
	}
	public int getPurpleWardrobe(Player p){
		int armor = 0;
		
		String query = "SELECT `purple` FROM `Wardrobe-Purple` WHERE `name` = '" + p.getName() + "'"; 
		
		try{
			ResultSet rs = connection.prepareStatement(query).executeQuery();
			
			while (rs.next()) {
				armor = rs.getInt("purple");
			}
			
			rs.close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
		return armor;
	}
	public int getOrangeWardrobe(Player p){
		int armor = 0;
		
		String query = "SELECT `orange` FROM `Wardrobe-Orange` WHERE `name` = '" + p.getName() + "'"; 
		
		try{
			ResultSet rs = connection.prepareStatement(query).executeQuery();
			
			while (rs.next()) {
				armor = rs.getInt("orange");
			}
			
			rs.close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
		return armor;
	}
	public int getRedWardrobe(Player p){
		int armor = 0;
		
		String query = "SELECT `red` FROM `Wardrobe-Red` WHERE `name` = '" + p.getName() + "'"; 
		
		try{
			ResultSet rs = connection.prepareStatement(query).executeQuery();
			
			while (rs.next()) {
				armor = rs.getInt("red");
			}
			
			rs.close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
		return armor;
	}
	public int getCyanWardrobe(Player p){
		int armor = 0;
		
		String query = "SELECT `cyan` FROM `Wardrobe-Cyan` WHERE `name` = '" + p.getName() + "'"; 
		
		try{
			ResultSet rs = connection.prepareStatement(query).executeQuery();
			
			while (rs.next()) {
				armor = rs.getInt("cyan");
			}
			
			rs.close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
		return armor;
	}
	public int getYellowWardrobe(Player p){
		int armor = 0;
		
		String query = "SELECT `yellow` FROM `Wardrobe-Yellow` WHERE `name` = '" + p.getName() + "'"; 
		
		try{
			ResultSet rs = connection.prepareStatement(query).executeQuery();
			
			while (rs.next()) {
				armor = rs.getInt("yellow");
			}
			
			rs.close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
		return armor;
	}
	public int getGrayWardrobe(Player p){
		int armor = 0;
		
		String query = "SELECT `gray` FROM `Wardrobe-Gray` WHERE `name` = '" + p.getName() + "'"; 
		
		try{
			ResultSet rs = connection.prepareStatement(query).executeQuery();
			
			while (rs.next()) {
				armor = rs.getInt("gray");
			}
			
			rs.close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
		return armor;
	}
	public String getChatColor(Player p){
		String color = "";
		
		String query = "SELECT `color` FROM `ChatColors` WHERE `name` = '" + p.getName() + "'"; 
		
		try{
			ResultSet rs = connection.prepareStatement(query).executeQuery();
			
			while (rs.next()) {
				color = rs.getString("color");
			}
			
			rs.close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
		return color;
	}
	public int getChatColorDarkRed(Player p){
		int color = 0;
		
		String query = "SELECT `darkred` FROM `ChatColors-DarkRed` WHERE `name` = '" + p.getName() + "'"; 
		
		try{
			ResultSet rs = connection.prepareStatement(query).executeQuery();
			
			while (rs.next()) {
				color = rs.getInt("darkred");
			}
			
			rs.close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
		return color;
	}
	public int getChatColorLightGreen(Player p){
		int color = 0;
		
		String query = "SELECT `lightgreen` FROM `ChatColors-LightGreen` WHERE `name` = '" + p.getName() + "'"; 
		
		try{
			ResultSet rs = connection.prepareStatement(query).executeQuery();
			
			while (rs.next()) {
				color = rs.getInt("lightgreen");
			}
			
			rs.close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
		return color;
	}
	public int getChatColorDarkGray(Player p){
		int color = 0;
		
		String query = "SELECT `darkgray` FROM `ChatColors-DarkGray` WHERE `name` = '" + p.getName() + "'"; 
		
		try{
			ResultSet rs = connection.prepareStatement(query).executeQuery();
			
			while (rs.next()) {
				color = rs.getInt("darkgray");
			}
			
			rs.close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
		return color;
	}
	public int getChatColorRed(Player p){
		int color = 0;
		
		String query = "SELECT `red` FROM `ChatColors-Red` WHERE `name` = '" + p.getName() + "'"; 
		
		try{
			ResultSet rs = connection.prepareStatement(query).executeQuery();
			
			while (rs.next()) {
				color = rs.getInt("red");
			}
			
			rs.close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
		return color;
	}
	public int getChatColorWhite(Player p){
		int color = 0;
		
		String query = "SELECT `white` FROM `ChatColors-White` WHERE `name` = '" + p.getName() + "'"; 
		
		try{
			ResultSet rs = connection.prepareStatement(query).executeQuery();
			
			while (rs.next()) {
				color = rs.getInt("white");
			}
			
			rs.close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
		return color;
	}
	public int getChatColorLightBlue(Player p){
		int color = 0;
		
		String query = "SELECT `lightblue` FROM `ChatColors-LightBlue` WHERE `name` = '" + p.getName() + "'"; 
		
		try{
			ResultSet rs = connection.prepareStatement(query).executeQuery();
			
			while (rs.next()) {
				color = rs.getInt("lightblue");
			}
			
			rs.close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
		return color;
	}
	public int getChatColorPink(Player p){
		int color = 0;
		
		String query = "SELECT `pink` FROM `ChatColors-Pink` WHERE `name` = '" + p.getName() + "'"; 
		
		try{
			ResultSet rs = connection.prepareStatement(query).executeQuery();
			
			while (rs.next()) {
				color = rs.getInt("pink");
			}
			
			rs.close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
		return color;
	}
	public int getChatColorBlue(Player p){
		int color = 0;
		
		String query = "SELECT `blue` FROM `ChatColors-Blue` WHERE `name` = '" + p.getName() + "'"; 
		
		try{
			ResultSet rs = connection.prepareStatement(query).executeQuery();
			
			while (rs.next()) {
				color = rs.getInt("blue");
			}
			
			rs.close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
		return color;
	}
	public int getChatColorDarkBlue(Player p){
		int color = 0;
		
		String query = "SELECT `darkblue` FROM `ChatColors-DarkBlue` WHERE `name` = '" + p.getName() + "'"; 
		
		try{
			ResultSet rs = connection.prepareStatement(query).executeQuery();
			
			while (rs.next()) {
				color = rs.getInt("darkblue");
			}
			
			rs.close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
		return color;
	}
	public int getChatColorGreen(Player p){
		int color = 0;
		
		String query = "SELECT `green` FROM `ChatColors-Green` WHERE `name` = '" + p.getName() + "'"; 
		
		try{
			ResultSet rs = connection.prepareStatement(query).executeQuery();
			
			while (rs.next()) {
				color = rs.getInt("green");
			}
			
			rs.close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
		return color;
	}
	public int getChatColorBlack(Player p){
		int color = 0;
		
		String query = "SELECT `black` FROM `ChatColors-Black` WHERE `name` = '" + p.getName() + "'"; 
		
		try{
			ResultSet rs = connection.prepareStatement(query).executeQuery();
			
			while (rs.next()) {
				color = rs.getInt("black");
			}
			
			rs.close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
		return color;
	}
	public int getTrailAngryVillager(Player p){
		int armor = 0;
		
		String query = "SELECT `angryvillager` FROM `Trails-AngryVillager` WHERE `name` = '" + p.getName() + "'"; 
		
		try{
			ResultSet rs = connection.prepareStatement(query).executeQuery();
			
			while (rs.next()) {
				armor = rs.getInt("angryvillager");
			}
			
			rs.close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
		return armor;
	}
	public int getTrailBubble(Player p){
		int armor = 0;
		
		String query = "SELECT `bubble` FROM `Trails-Bubble` WHERE `name` = '" + p.getName() + "'"; 
		
		try{
			ResultSet rs = connection.prepareStatement(query).executeQuery();
			
			while (rs.next()) {
				armor = rs.getInt("bubble");
			}
			
			rs.close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
		return armor;
	}
	public int getTrailCrit(Player p){
		int armor = 0;
		
		String query = "SELECT `crit` FROM `Trails-Crit` WHERE `name` = '" + p.getName() + "'"; 
		
		try{
			ResultSet rs = connection.prepareStatement(query).executeQuery();
			
			while (rs.next()) {
				armor = rs.getInt("crit");
			}
			
			rs.close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
		return armor;
	}
	public int getTrailETable(Player p){
		int armor = 0;
		
		String query = "SELECT `etable` FROM `Trails-ETable` WHERE `name` = '" + p.getName() + "'"; 
		
		try{
			ResultSet rs = connection.prepareStatement(query).executeQuery();
			
			while (rs.next()) {
				armor = rs.getInt("etable");
			}
			
			rs.close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
		return armor;
	}
	public int getTrailExplode(Player p){
		int armor = 0;
		
		String query = "SELECT `explode` FROM `Trails-Explode` WHERE `name` = '" + p.getName() + "'"; 
		
		try{
			ResultSet rs = connection.prepareStatement(query).executeQuery();
			
			while (rs.next()) {
				armor = rs.getInt("explode");
			}
			
			rs.close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
		return armor;
	}
	public int getTrailFirework(Player p){
		int armor = 0;
		
		String query = "SELECT `firework` FROM `Trails-Firework` WHERE `name` = '" + p.getName() + "'"; 
		
		try{
			ResultSet rs = connection.prepareStatement(query).executeQuery();
			
			while (rs.next()) {
				armor = rs.getInt("firework");
			}
			
			rs.close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
		return armor;
	}
	public int getTrailHappyVillager(Player p){
		int armor = 0;
		
		String query = "SELECT `happyvillager` FROM `Trails-HappyVillager` WHERE `name` = '" + p.getName() + "'"; 
		
		try{
			ResultSet rs = connection.prepareStatement(query).executeQuery();
			
			while (rs.next()) {
				armor = rs.getInt("happyvillager");
			}
			
			rs.close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
		return armor;
	}
	public int getTrailHearts(Player p){
		int armor = 0;
		
		String query = "SELECT `hearts` FROM `Trails-Hearts` WHERE `name` = '" + p.getName() + "'"; 
		
		try{
			ResultSet rs = connection.prepareStatement(query).executeQuery();
			
			while (rs.next()) {
				armor = rs.getInt("hearts");
			}
			
			rs.close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
		return armor;
	}
	public int getTrailMobSpawner(Player p){
		int armor = 0;
		
		String query = "SELECT `mobspawner` FROM `Trails-MobSpawner` WHERE `name` = '" + p.getName() + "'"; 
		
		try{
			ResultSet rs = connection.prepareStatement(query).executeQuery();
			
			while (rs.next()) {
				armor = rs.getInt("mobspawner");
			}
			
			rs.close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
		return armor;
	}
	public int getTrailMusic(Player p){
		int armor = 0;
		
		String query = "SELECT `music` FROM `Trails-Music` WHERE `name` = '" + p.getName() + "'"; 
		
		try{
			ResultSet rs = connection.prepareStatement(query).executeQuery();
			
			while (rs.next()) {
				armor = rs.getInt("music");
			}
			
			rs.close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
		return armor;
	}
	public int getTrailSlime(Player p){
		int armor = 0;
		
		String query = "SELECT `slime` FROM `Trails-Slime` WHERE `name` = '" + p.getName() + "'"; 
		
		try{
			ResultSet rs = connection.prepareStatement(query).executeQuery();
			
			while (rs.next()) {
				armor = rs.getInt("slime");
			}
			
			rs.close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
		return armor;
	}
	public int getTrailSmoke(Player p){
		int armor = 0;
		
		String query = "SELECT `smoke` FROM `Trails-Smoke` WHERE `name` = '" + p.getName() + "'"; 
		
		try{
			ResultSet rs = connection.prepareStatement(query).executeQuery();
			
			while (rs.next()) {
				armor = rs.getInt("smoke");
			}
			
			rs.close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
		return armor;
	}
	public int getTrailSnow(Player p){
		int armor = 0;
		
		String query = "SELECT `snow` FROM `Trails-Snow` WHERE `name` = '" + p.getName() + "'"; 
		
		try{
			ResultSet rs = connection.prepareStatement(query).executeQuery();
			
			while (rs.next()) {
				armor = rs.getInt("snow");
			}
			
			rs.close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
		return armor;
	}
	public int getTrailWater(Player p){
		int armor = 0;
		
		String query = "SELECT `water` FROM `Trails-Water` WHERE `name` = '" + p.getName() + "'"; 
		
		try{
			ResultSet rs = connection.prepareStatement(query).executeQuery();
			
			while (rs.next()) {
				armor = rs.getInt("water");
			}
			
			rs.close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
		return armor;
	}
	public int getTrailWitch(Player p){
		int armor = 0;
		
		String query = "SELECT `witch` FROM `Trails-Witch` WHERE `name` = '" + p.getName() + "'"; 
		
		try{
			ResultSet rs = connection.prepareStatement(query).executeQuery();
			
			while (rs.next()) {
				armor = rs.getInt("witch");
			}
			
			rs.close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
		return armor;
	}
	public int getHat(Player p, String Table, String hat){
		int hatI = 0;
		
		String query = "SELECT `" + hat + "` FROM `" + Table + "` WHERE `name` = '" + p.getName() + "'"; 
		
		try{
			ResultSet rs = connection.prepareStatement(query).executeQuery();
			
			while (rs.next()) {
				hatI = rs.getInt(hat);
			}
			
			rs.close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
		return hatI;
	}
	public int getDisguise(Player p, String Table, String disguise){
		int hatI = 0;
		
		String query = "SELECT `" + disguise + "` FROM `" + Table + "` WHERE `name` = '" + p.getName() + "'"; 
		
		try{
			ResultSet rs = connection.prepareStatement(query).executeQuery();
			
			while (rs.next()) {
				hatI = rs.getInt(disguise);
			}
			
			rs.close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
		return hatI;
	}
	
	
	public static int getMiniGameCoins(Player p){
		int coins = 0;
		
		String query = "SELECT `coins` FROM `MiniGameCoins` WHERE `name` = '" + p.getName() + "'"; 
		
		try{
			ResultSet rs = connection.prepareStatement(query).executeQuery();
			
			while (rs.next()) {
				coins = rs.getInt("coins");
			}
			
			rs.close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
		return coins;
	}
	
	public void addVIPPoints(Player p, int points) throws SQLException{
		Statement s = connection.createStatement();
		s.executeUpdate("UPDATE `VIPPoints` SET `points` = '" + (getVIPPoints(p) + points) + "' WHERE `name` = '" + p.getName() + "'");
		Start.points.put(p.getName(), getVIPPoints(p));
	}
	public void removeVIPPoints(Player p, int points) throws SQLException{
		Statement s = connection.createStatement();
		s.executeUpdate("UPDATE `VIPPoints` SET `points` = '" + (getVIPPoints(p) - points) + "' WHERE `name` = '" + p.getName() + "'");
		Start.points.put(p.getName(), getVIPPoints(p));
	}
	public void setMushroomCowName(Player p, String petname) throws SQLException{
		Statement s = connection.createStatement();
		s.executeUpdate("UPDATE `Pets-MushroomCow` SET `petname` = '" + petname + "' WHERE `name` = '" + p.getName() + "'");
		Start.mushroomcow.put(p.getName(), petname);
	}
	public void setPigName(Player p, String petname) throws SQLException{
		Statement s = connection.createStatement();
		s.executeUpdate("UPDATE `Pets-Pig` SET `petname` = '" + petname + "' WHERE `name` = '" + p.getName() + "'");
		Start.pig.put(p.getName(), petname);
	}
	public void setWolfName(Player p, String petname) throws SQLException{
		Statement s = connection.createStatement();
		s.executeUpdate("UPDATE `Pets-Wolf` SET `petname` = '" + petname + "' WHERE `name` = '" + p.getName() + "'");
		Start.wolf.put(p.getName(), petname);
	}
	public void setSheepName(Player p, String petname) throws SQLException{
		Statement s = connection.createStatement();
		s.executeUpdate("UPDATE `Pets-Sheep` SET `petname` = '" + petname + "' WHERE `name` = '" + p.getName() + "'");
		Start.sheep.put(p.getName(), petname);
	}
	public void setHorseName(Player p, String petname) throws SQLException{
		Statement s = connection.createStatement();
		s.executeUpdate("UPDATE `Pets-Horse` SET `petname` = '" + petname + "' WHERE `name` = '" + p.getName() + "'");
		Start.horse.put(p.getName(), petname);
	}
	public void setMagmaCubeName(Player p, String petname) throws SQLException{
		Statement s = connection.createStatement();
		s.executeUpdate("UPDATE `Pets-MagmaCube` SET `petname` = '" + petname + "' WHERE `name` = '" + p.getName() + "'");
		Start.magmacube.put(p.getName(), petname);
	}
	public void setSlimeName(Player p, String petname) throws SQLException{
		Statement s = connection.createStatement();
		s.executeUpdate("UPDATE `Pets-Slime` SET `petname` = '" + petname + "' WHERE `name` = '" + p.getName() + "'");
		Start.slime.put(p.getName(), petname);
	}
	public void setCowName(Player p, String petname) throws SQLException{
		Statement s = connection.createStatement();
		s.executeUpdate("UPDATE `Pets-Cow` SET `petname` = '" + petname + "' WHERE `name` = '" + p.getName() + "'");
		Start.cow.put(p.getName(), petname);
	}
	public void setSilverfishName(Player p, String petname) throws SQLException{
		Statement s = connection.createStatement();
		s.executeUpdate("UPDATE `Pets-Silverfish` SET `petname` = '" + petname + "' WHERE `name` = '" + p.getName() + "'");
		Start.silverfish.put(p.getName(), petname);
	}
	public void setOcelotName(Player p, String petname) throws SQLException{
		Statement s = connection.createStatement();
		s.executeUpdate("UPDATE `Pets-Ocelot` SET `petname` = '" + petname + "' WHERE `name` = '" + p.getName() + "'");
		Start.ocelot.put(p.getName(), petname);
	}
	public void setWhiteWardrobe(Player p, int i) throws SQLException{
		Statement s = connection.createStatement();
		s.executeUpdate("UPDATE `Wardrobe-White` SET `white` = '" + i + "' WHERE `name` = '" + p.getName() + "'");
		Start.white.put(p.getName(), i);
	}
	public void setBlueWardrobe(Player p, int i) throws SQLException{
		Statement s = connection.createStatement();
		s.executeUpdate("UPDATE `Wardrobe-Blue` SET `blue` = '" + i + "' WHERE `name` = '" + p.getName() + "'");
		Start.blue.put(p.getName(), i);
	}
	public void setGreenWardrobe(Player p, int i) throws SQLException{
		Statement s = connection.createStatement();
		s.executeUpdate("UPDATE `Wardrobe-Green` SET `green` = '" + i + "' WHERE `name` = '" + p.getName() + "'");
		Start.green.put(p.getName(), i);
	}
	public void setBlackWardrobe(Player p, int i) throws SQLException{
		Statement s = connection.createStatement();
		s.executeUpdate("UPDATE `Wardrobe-Black` SET `black` = '" + i + "' WHERE `name` = '" + p.getName() + "'");
		Start.black.put(p.getName(), i);
	}
	public void setLightBlueWardrobe(Player p, int i) throws SQLException{
		Statement s = connection.createStatement();
		s.executeUpdate("UPDATE `Wardrobe-LightBlue` SET `lightblue` = '" + i + "' WHERE `name` = '" + p.getName() + "'");
		Start.lightblue.put(p.getName(), i);
	}
	public void setPinkWardrobe(Player p, int i) throws SQLException{
		Statement s = connection.createStatement();
		s.executeUpdate("UPDATE `Wardrobe-Pink` SET `pink` = '" + i + "' WHERE `name` = '" + p.getName() + "'");
		Start.pink.put(p.getName(), i);
	}
	public void setLightGreenWardrobe(Player p, int i) throws SQLException{
		Statement s = connection.createStatement();
		s.executeUpdate("UPDATE `Wardrobe-LightGreen` SET `lightgreen` = '" + i + "' WHERE `name` = '" + p.getName() + "'");
		Start.lightgreen.put(p.getName(), i);
	}
	public void setDarkBlueWardrobe(Player p, int i) throws SQLException{
		Statement s = connection.createStatement();
		s.executeUpdate("UPDATE `Wardrobe-DarkBlue` SET `darkblue` = '" + i + "' WHERE `name` = '" + p.getName() + "'");
		Start.darkblue.put(p.getName(), i);
	}
	public void setPurpleWardrobe(Player p, int i) throws SQLException{
		Statement s = connection.createStatement();
		s.executeUpdate("UPDATE `Wardrobe-Purple` SET `purple` = '" + i + "' WHERE `name` = '" + p.getName() + "'");
		Start.purple.put(p.getName(), i);
	}
	public void setOrangeWardrobe(Player p, int i) throws SQLException{
		Statement s = connection.createStatement();
		s.executeUpdate("UPDATE `Wardrobe-Orange` SET `orange` = '" + i + "' WHERE `name` = '" + p.getName() + "'");
		Start.orange.put(p.getName(), i);
	}
	public void setRedWardrobe(Player p, int i) throws SQLException{
		Statement s = connection.createStatement();
		s.executeUpdate("UPDATE `Wardrobe-Red` SET `red` = '" + i + "' WHERE `name` = '" + p.getName() + "'");
		Start.red.put(p.getName(), i);
	}
	public void setCyanWardrobe(Player p, int i) throws SQLException{
		Statement s = connection.createStatement();
		s.executeUpdate("UPDATE `Wardrobe-Cyan` SET `cyan` = '" + i + "' WHERE `name` = '" + p.getName() + "'");
		Start.cyan.put(p.getName(), i);
	}
	public void setYellowWardrobe(Player p, int i) throws SQLException{
		Statement s = connection.createStatement();
		s.executeUpdate("UPDATE `Wardrobe-Yellow` SET `yellow` = '" + i + "' WHERE `name` = '" + p.getName() + "'");
		Start.yellow.put(p.getName(), i);
	}
	public void setGrayWardrobe(Player p, int i) throws SQLException{
		Statement s = connection.createStatement();
		s.executeUpdate("UPDATE `Wardrobe-Gray` SET `gray` = '" + i + "' WHERE `name` = '" + p.getName() + "'");
		Start.gray.put(p.getName(), i);
	}
	public void setChatColor(Player p, String i) throws SQLException{
		Statement s = connection.createStatement();
		s.executeUpdate("UPDATE `ChatColors` SET `color` = '" + i + "' WHERE `name` = '" + p.getName() + "'");
		Start.chatcolor.put(p, i);
	}
	public void setChatColorDarkRed(Player p, int i) throws SQLException{
		Statement s = connection.createStatement();
		s.executeUpdate("UPDATE `ChatColors-DarkRed` SET `darkred` = '" + i + "' WHERE `name` = '" + p.getName() + "'");
		Start.chatcolordarkred.put(p.getName(), i);
	}
	public void setChatColorLightGreen(Player p, int i) throws SQLException{
		Statement s = connection.createStatement();
		s.executeUpdate("UPDATE `ChatColors-LightGreen` SET `lightgreen` = '" + i + "' WHERE `name` = '" + p.getName() + "'");
		Start.chatcolorlightgreen.put(p.getName(), i);
	}
	public void setChatColorDarkGray(Player p, int i) throws SQLException{
		Statement s = connection.createStatement();
		s.executeUpdate("UPDATE `ChatColors-DarkGray` SET `darkgray` = '" + i + "' WHERE `name` = '" + p.getName() + "'");
		Start.chatcolordarkgray.put(p.getName(), i);
	}
	public void setChatColorRed(Player p, int i) throws SQLException{
		Statement s = connection.createStatement();
		s.executeUpdate("UPDATE `ChatColors-Red` SET `red` = '" + i + "' WHERE `name` = '" + p.getName() + "'");
		Start.chatcolorred.put(p.getName(), i);
	}
	public void setChatColorWhite(Player p, int i) throws SQLException{
		Statement s = connection.createStatement();
		s.executeUpdate("UPDATE `ChatColors-White` SET `white` = '" + i + "' WHERE `name` = '" + p.getName() + "'");
		Start.chatcolorwhite.put(p.getName(), i);
	}
	public void setChatColorLightBlue(Player p, int i) throws SQLException{
		Statement s = connection.createStatement();
		s.executeUpdate("UPDATE `ChatColors-LightBlue` SET `lightblue` = '" + i + "' WHERE `name` = '" + p.getName() + "'");
		Start.chatcolorlightblue.put(p.getName(), i);
	}
	public void setChatColorPink(Player p, int i) throws SQLException{
		Statement s = connection.createStatement();
		s.executeUpdate("UPDATE `ChatColors-Pink` SET `pink` = '" + i + "' WHERE `name` = '" + p.getName() + "'");
		Start.chatcolorpink.put(p.getName(), i);
	}
	public void setChatColorBlue(Player p, int i) throws SQLException{
		Statement s = connection.createStatement();
		s.executeUpdate("UPDATE `ChatColors-Blue` SET `blue` = '" + i + "' WHERE `name` = '" + p.getName() + "'");
		Start.chatcolorblue.put(p.getName(), i);
	}
	public void setChatColorDarkBlue(Player p, int i) throws SQLException{
		Statement s = connection.createStatement();
		s.executeUpdate("UPDATE `ChatColors-DarkBlue` SET `darkblue` = '" + i + "' WHERE `name` = '" + p.getName() + "'");
		Start.chatcolordarkblue.put(p.getName(), i);
	}
	public void setChatColorGreen(Player p, int i) throws SQLException{
		Statement s = connection.createStatement();
		s.executeUpdate("UPDATE `ChatColors-Green` SET `green` = '" + i + "' WHERE `name` = '" + p.getName() + "'");
		Start.chatcolorgreen.put(p.getName(), i);
	}
	public void setChatColorBlack(Player p, int i) throws SQLException{
		Statement s = connection.createStatement();
		s.executeUpdate("UPDATE `ChatColors-Black` SET `black` = '" + i + "' WHERE `name` = '" + p.getName() + "'");
		Start.chatcolorblack.put(p.getName(), i);
	}
	public static void addMiniGameCoins(Player p, int points) throws SQLException{
		Statement s = connection.createStatement();
		s.executeUpdate("UPDATE `MiniGameCoins` SET `coins` = '" + (getMiniGameCoins(p) + points) + "' WHERE `name` = '" + p.getName() + "'");
		Start.coins.put(p.getName(), getMiniGameCoins(p));
	}
	public void removeMiniGameCoins(Player p, int points) throws SQLException{
		Statement s = connection.createStatement();
		s.executeUpdate("UPDATE `MiniGameCoins` SET `coins` = '" + (getMiniGameCoins(p) - points) + "' WHERE `name` = '" + p.getName() + "'");
		Start.coins.put(p.getName(), getVIPPoints(p));
	}
	
	public static void RandomSpawn(Player p){
		
        Random Lightning = new Random();
        int LightningInt;
        
        for(int LightningCount = 1; LightningCount <= 1; LightningCount++){
        	LightningInt = 1 + Lightning.nextInt(7);
        	
        	if(LightningInt == 1){
        		p.teleport(new Location(p.getWorld(), 824, 5, 485));
        	}
        	else if(LightningInt == 2){
        		p.teleport(new Location(p.getWorld(), 796, 5, 509));
        	}
        	else if(LightningInt == 3){
        		p.teleport(new Location(p.getWorld(), 788, 5, 466));
        	}
        	else if(LightningInt == 4){
        		p.teleport(new Location(p.getWorld(), 811, 9, 450));
        	}
        	else if(LightningInt == 5){
        		p.teleport(new Location(p.getWorld(), 853, 13, 494));
        	}
        	else if(LightningInt == 6){
        		p.teleport(new Location(p.getWorld(), 825, 5, 522));
        	}
        	else if(LightningInt == 7){
        		p.teleport(new Location(p.getWorld(), 768, 5, 502));
        	}
        }
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2,
			String[] a) {

			if(sender instanceof Player){
				Player p = (Player)sender;
				
				if(cmd.getName().equalsIgnoreCase("kit")){
					p.sendMessage(Start.TAG + "Opening the Kit Selector...");
					p.openInventory(kitselector.getKitSelector(p));
					
				}
				else if(cmd.getName().equalsIgnoreCase("kitpvp")){
					if(p.hasPermission("KitPvP.Admin")){
						
						if(a.length == 0){
							p.sendMessage(TAG + "§cIncorrect Use!");
						}
						else if(a[0].equalsIgnoreCase("togglefreekit")){
							
							
							if(FreeKitEnabled == true){
								
								getConfig().set("FreeKitEnabled", false);
								saveConfig();
								FreeKitEnabled = false;
							}
							else if(FreeKitEnabled == false){
								
								getConfig().set("FreeKitEnabled", true);
								saveConfig();
								FreeKitEnabled = true;
							}
							
						}
						else{
							p.sendMessage(TAG + "§cIncorrect Use!");
						}
						
					}
					else{
						p.sendMessage(TAG + "§cAccess Denied!");
					}
				}
				else if(cmd.getName().equalsIgnoreCase("tradecoins")){
					
						
						int money = (int) economy.getBalance(p.getName());
							
						try {
							addMoney(p, money);
						} catch (SQLException ex) {
							ex.printStackTrace();
						}
						p.sendMessage("§8[§c§lKitPvP§8] §7Traded §e" + money + " Money§7 for §6" + money + " Coins§7!");
						Start.money.put(p.getName(), getMoney(p));
						economy.withdrawPlayer(p.getName(), money);
						
					}
			}
			else{
				sender.sendMessage(TAG + "§7You can't perform commands through the console!");
			}
			if(cmd.getName().equalsIgnoreCase("vippoints")){
				
				if(sender.hasPermission("Rank.Owner")){
					
					if(a.length == 3){
						
						if(a[0].equalsIgnoreCase("give")){
							
							int i = Integer.parseInt(a[2]);
							
							Player player = null;
							
							for(Player p : Bukkit.getOnlinePlayers()){
								
								if(p.getName().equals(a[1])){
									player = p;
								}
							}
							
							if(player != null){
								
								try {
									addVIPPoints(player, i);
								} catch (SQLException e) {
									e.printStackTrace();
								}
								
								sender.sendMessage("You gave " + a[1] + " " + a[2] + " VIP Points!");
								
							}
							else{
								sender.sendMessage("Player " + a[1] + " not Found!");
							}
							
						}
						else if(a[0].equalsIgnoreCase("remove")){
							
							int i = Integer.parseInt(a[2]);
							
							Player player = null;
							
							for(Player p : Bukkit.getOnlinePlayers()){
								
								if(p.getName().equals(a[1])){
									player = p;
								}
							}
							
							if(player != null){
								
								try {
									removeVIPPoints(player, i);
								} catch (SQLException e) {
									e.printStackTrace();
								}
								
								sender.sendMessage("You removed " + a[2] + " VIP Points from " + a[1] + "!");
								
							}
							else{
								sender.sendMessage("Player " + a[1] + " not Found!");
							}
							
						}
						else{
							sender.sendMessage("Incorrect Use!");
						}
						
					}
					else{
						sender.sendMessage("Incorrect Use!");
					}
					
				}
			}
			
			if(cmd.getName().equalsIgnoreCase("omt")){
				
				if(sender.hasPermission("Rank.Owner")){
					
					if(a.length == 3){
						
						if(a[0].equalsIgnoreCase("give")){
							
							int i = Integer.parseInt(a[2]);
							
							Player player = null;
							
							for(Player p : Bukkit.getOnlinePlayers()){
								
								if(p.getName().equals(a[1])){
									player = p;
								}
							}
							
							if(player != null){
								
								try {
									addOMT(player, i);
								} catch (SQLException e) {
									e.printStackTrace();
								}
								
								sender.sendMessage("You gave " + a[1] + " " + a[2] + " OMT!");
								
							}
							else{
								sender.sendMessage("Player " + a[1] + " not Found!");
							}
							
						}
						else if(a[0].equalsIgnoreCase("remove")){
							
							int i = Integer.parseInt(a[2]);
							
							Player player = null;
							
							for(Player p : Bukkit.getOnlinePlayers()){
								
								if(p.getName().equals(a[1])){
									player = p;
								}
							}
							
							if(player != null){
								
								try {
									removeOMT(player, i);
								} catch (SQLException e) {
									e.printStackTrace();
								}
								
								sender.sendMessage("You removed " + a[2] + " OMT from " + a[1] + "!");
								
							}
							else{
								sender.sendMessage("Player " + a[1] + " not Found!");
							}
							
						}
						else{
							sender.sendMessage("Incorrect Use!");
						}
						
					}
					else{
						sender.sendMessage("Incorrect Use!");
					}
					
				}
				else{
					sender.sendMessage("§8[§e§lOMT§8] §7Your OMT balance: §e§l" + Start.omt.get(sender.getName()));
				}
			}
			
			if(cmd.getName().equalsIgnoreCase("servers")){
				if(sender instanceof Player){
					Player p = (Player) sender;
					p.playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 5, 1);
					p.openInventory(sinv.getServerSelectorInv(p, 1));
					
				}
			}
			if(cmd.getName().equalsIgnoreCase("perks")){
				if(sender instanceof Player){
					Player p = (Player) sender;
					p.playSound(p.getLocation(), Sound.CHEST_OPEN, 5, 1);
					p.openInventory(CosmeticPerks.getCosmeticPerks(p));
					
				}
			}
			
		return false;
	}
	// OPEN MYSQL
	public synchronized void openConnection(){
		
		try {
			connection = DriverManager.getConnection("jdbc:mysql://sql-3.verygames.net:3306/minecraft4268", "minecraft4268", "hnfxy5h48");
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		
	}
	// IF PLAYER IS REGISTERED
	public boolean containsElement(String field, String Type, String player){
		if(Type.equals("CHATCOLOR")){
			containsquery = "SELECT `" + field + "` FROM `ChatColors` WHERE `" + field + "`='" + player + "'";
		}
		if(Type.equals("OMT")){
			containsquery = "SELECT `" + field + "` FROM `OrbitMinesTokens` WHERE `" + field + "`='" + player + "'";
		}
		if(Type.equals("POINTS")){
			containsquery = "SELECT `" + field + "` FROM `VIPPoints` WHERE `" + field + "`='" + player + "'";
		}
		if(Type.equals("MUSHROOMCOW")){
			containsquery = "SELECT `" + field + "` FROM `Pets-MushroomCow` WHERE `" + field + "`='" + player + "'";
		}
		if(Type.equals("PIG")){
			containsquery = "SELECT `" + field + "` FROM `Pets-Pig` WHERE `" + field + "`='" + player + "'";
		}
		if(Type.equals("WOLF")){
			containsquery = "SELECT `" + field + "` FROM `Pets-Wolf` WHERE `" + field + "`='" + player + "'";
		}
		if(Type.equals("SHEEP")){
			containsquery = "SELECT `" + field + "` FROM `Pets-Sheep` WHERE `" + field + "`='" + player + "'";
		}
		if(Type.equals("HORSE")){
			containsquery = "SELECT `" + field + "` FROM `Pets-Horse` WHERE `" + field + "`='" + player + "'";
		}
		if(Type.equals("MAGMACUBE")){
			containsquery = "SELECT `" + field + "` FROM `Pets-MagmaCube` WHERE `" + field + "`='" + player + "'";
		}
		if(Type.equals("SLIME")){
			containsquery = "SELECT `" + field + "` FROM `Pets-Slime` WHERE `" + field + "`='" + player + "'";
		}
		if(Type.equals("COW")){
			containsquery = "SELECT `" + field + "` FROM `Pets-Cow` WHERE `" + field + "`='" + player + "'";
		}
		if(Type.equals("SILVERFISH")){
			containsquery = "SELECT `" + field + "` FROM `Pets-Silverfish` WHERE `" + field + "`='" + player + "'";
		}
		if(Type.equals("OCELOT")){
			containsquery = "SELECT `" + field + "` FROM `Pets-Ocelot` WHERE `" + field + "`='" + player + "'";
		}
		if(Type.equals("WHITE")){
			containsquery = "SELECT `" + field + "` FROM `Wardrobe-White` WHERE `" + field + "`='" + player + "'";
		}
		if(Type.equals("BLUE")){
			containsquery = "SELECT `" + field + "` FROM `Wardrobe-Blue` WHERE `" + field + "`='" + player + "'";
		}
		if(Type.equals("GREEN")){
			containsquery = "SELECT `" + field + "` FROM `Wardrobe-Green` WHERE `" + field + "`='" + player + "'";
		}
		if(Type.equals("BLACK")){
			containsquery = "SELECT `" + field + "` FROM `Wardrobe-Black` WHERE `" + field + "`='" + player + "'";
		}
		if(Type.equals("LIGHTBLUE")){
			containsquery = "SELECT `" + field + "` FROM `Wardrobe-LightBlue` WHERE `" + field + "`='" + player + "'";
		}
		if(Type.equals("PINK")){
			containsquery = "SELECT `" + field + "` FROM `Wardrobe-Pink` WHERE `" + field + "`='" + player + "'";
		}
		if(Type.equals("LIGHTGREEN")){
			containsquery = "SELECT `" + field + "` FROM `Wardrobe-LightGreen` WHERE `" + field + "`='" + player + "'";
		}
		if(Type.equals("DARKBLUE")){
			containsquery = "SELECT `" + field + "` FROM `Wardrobe-DarkBlue` WHERE `" + field + "`='" + player + "'";
		}
		if(Type.equals("PURPLE")){
			containsquery = "SELECT `" + field + "` FROM `Wardrobe-Purple` WHERE `" + field + "`='" + player + "'";
		}
		if(Type.equals("ORANGE")){
			containsquery = "SELECT `" + field + "` FROM `Wardrobe-Orange` WHERE `" + field + "`='" + player + "'";
		}
		if(Type.equals("RED")){
			containsquery = "SELECT `" + field + "` FROM `Wardrobe-Red` WHERE `" + field + "`='" + player + "'";
		}
		if(Type.equals("CYAN")){
			containsquery = "SELECT `" + field + "` FROM `Wardrobe-Cyan` WHERE `" + field + "`='" + player + "'";
		}
		if(Type.equals("YELLOW")){
			containsquery = "SELECT `" + field + "` FROM `Wardrobe-Yellow` WHERE `" + field + "`='" + player + "'";
		}
		if(Type.equals("GRAY")){
			containsquery = "SELECT `" + field + "` FROM `Wardrobe-Gray` WHERE `" + field + "`='" + player + "'";
		}
		if(Type.equals("CHATCOLORDARKRED")){
			containsquery = "SELECT `" + field + "` FROM `ChatColors-DarkRed` WHERE `" + field + "`='" + player + "'";
		}
		if(Type.equals("CHATCOLORLIGHTGREEN")){
			containsquery = "SELECT `" + field + "` FROM `ChatColors-LightGreen` WHERE `" + field + "`='" + player + "'";
		}
		if(Type.equals("CHATCOLORDARKGRAY")){
			containsquery = "SELECT `" + field + "` FROM `ChatColors-DarkGray` WHERE `" + field + "`='" + player + "'";
		}
		if(Type.equals("CHATCOLORRED")){
			containsquery = "SELECT `" + field + "` FROM `ChatColors-Red` WHERE `" + field + "`='" + player + "'";
		}
		if(Type.equals("CHATCOLORWHITE")){
			containsquery = "SELECT `" + field + "` FROM `ChatColors-White` WHERE `" + field + "`='" + player + "'";
		}
		if(Type.equals("CHATCOLORLIGHTBLUE")){
			containsquery = "SELECT `" + field + "` FROM `ChatColors-LightBlue` WHERE `" + field + "`='" + player + "'";
		}
		if(Type.equals("CHATCOLORPINK")){
			containsquery = "SELECT `" + field + "` FROM `ChatColors-Pink` WHERE `" + field + "`='" + player + "'";
		}
		if(Type.equals("CHATCOLORBLUE")){
			containsquery = "SELECT `" + field + "` FROM `ChatColors-Blue` WHERE `" + field + "`='" + player + "'";
		}
		if(Type.equals("CHATCOLORDARKBLUE")){
			containsquery = "SELECT `" + field + "` FROM `ChatColors-DarkBlue` WHERE `" + field + "`='" + player + "'";
		}
		if(Type.equals("CHATCOLORDARKGREEN")){
			containsquery = "SELECT `" + field + "` FROM `ChatColors-DarkGreen` WHERE `" + field + "`='" + player + "'";
		}
		if(Type.equals("CHATCOLORBLACK")){
			containsquery = "SELECT `" + field + "` FROM `ChatColors-Black` WHERE `" + field + "`='" + player + "'";
		}
		if(Type.equals("TRAILSANGRYVILLAGER")){
			containsquery = "SELECT `" + field + "` FROM `Trails-AngryVillager` WHERE `" + field + "`='" + player + "'";
		}
		if(Type.equals("TRAILSBUBBLE")){
			containsquery = "SELECT `" + field + "` FROM `Trails-Bubble` WHERE `" + field + "`='" + player + "'";
		}
		if(Type.equals("TRAILSCRIT")){
			containsquery = "SELECT `" + field + "` FROM `Trails-Crit` WHERE `" + field + "`='" + player + "'";
		}
		if(Type.equals("TRAILSETABLE")){
			containsquery = "SELECT `" + field + "` FROM `Trails-ETable` WHERE `" + field + "`='" + player + "'";
		}
		if(Type.equals("TRAILSEXPLODE")){
			containsquery = "SELECT `" + field + "` FROM `Trails-Explode` WHERE `" + field + "`='" + player + "'";
		}
		if(Type.equals("TRAILSFIREWORK")){
			containsquery = "SELECT `" + field + "` FROM `Trails-Firework` WHERE `" + field + "`='" + player + "'";
		}
		if(Type.equals("TRAILSHAPPYVILLAGER")){
			containsquery = "SELECT `" + field + "` FROM `Trails-HappyVillager` WHERE `" + field + "`='" + player + "'";
		}
		if(Type.equals("TRAILSHEARTS")){
			containsquery = "SELECT `" + field + "` FROM `Trails-Hearts` WHERE `" + field + "`='" + player + "'";
		}
		if(Type.equals("TRAILSMOBSPAWNER")){
			containsquery = "SELECT `" + field + "` FROM `Trails-MobSpawner` WHERE `" + field + "`='" + player + "'";
		}
		if(Type.equals("TRAILSMUSIC")){
			containsquery = "SELECT `" + field + "` FROM `Trails-Music` WHERE `" + field + "`='" + player + "'";
		}
		if(Type.equals("TRAILSSLIME")){
			containsquery = "SELECT `" + field + "` FROM `Trails-Slime` WHERE `" + field + "`='" + player + "'";
		}
		if(Type.equals("TRAILSSMOKE")){
			containsquery = "SELECT `" + field + "` FROM `Trails-Smoke` WHERE `" + field + "`='" + player + "'";
		}
		if(Type.equals("TRAILSSNOW")){
			containsquery = "SELECT `" + field + "` FROM `Trails-Snow` WHERE `" + field + "`='" + player + "'";
		}
		if(Type.equals("TRAILSWATER")){
			containsquery = "SELECT `" + field + "` FROM `Trails-Water` WHERE `" + field + "`='" + player + "'";
		}
		if(Type.equals("TRAILSWITCH")){
			containsquery = "SELECT `" + field + "` FROM `Trails-Witch` WHERE `" + field + "`='" + player + "'";
		}
		if(Type.equals("OMT")){
			containsquery = "SELECT `" + field + "` FROM `OrbitMinesTokens` WHERE `" + field + "`='" + player + "'";
		}
		if(Type.equals("Kills")){
			containsquery = "SELECT `" + field + "` FROM `KitPvP-Kills` WHERE `" + field + "`='" + player + "'";
		}
		if(Type.equals("Deaths")){
			containsquery = "SELECT `" + field + "` FROM `KitPvP-Deaths` WHERE `" + field + "`='" + player + "'";
		}
		if(Type.equals("Money")){
			containsquery = "SELECT `" + field + "` FROM `KitPvP-Money` WHERE `" + field + "`='" + player + "'";
		}
		if(Type.equals("Levels")){
			containsquery = "SELECT `" + field + "` FROM `KitPvP-Levels` WHERE `" + field + "`='" + player + "'";
		}
		if(Type.equals("Knight")){
			containsquery = "SELECT `" + field + "` FROM `Kits-Knight` WHERE `" + field + "`='" + player + "'";
		}
		if(Type.equals("Archer")){
			containsquery = "SELECT `" + field + "` FROM `Kits-Archer` WHERE `" + field + "`='" + player + "'";
		}
		if(Type.equals("Soldier")){
			containsquery = "SELECT `" + field + "` FROM `Kits-Soldier` WHERE `" + field + "`='" + player + "'";
		}
		if(Type.equals("Wizard")){
			containsquery = "SELECT `" + field + "` FROM `Kits-Wizard` WHERE `" + field + "`='" + player + "'";
		}
		if(Type.equals("Tank")){
			containsquery = "SELECT `" + field + "` FROM `Kits-Tank` WHERE `" + field + "`='" + player + "'";
		}
		if(Type.equals("Drunk")){
			containsquery = "SELECT `" + field + "` FROM `Kits-Drunk` WHERE `" + field + "`='" + player + "'";
		}
		if(Type.equals("Pyro")){
			containsquery = "SELECT `" + field + "` FROM `Kits-Pyro` WHERE `" + field + "`='" + player + "'";
		}
		if(Type.equals("Bunny")){
			containsquery = "SELECT `" + field + "` FROM `Kits-Bunny` WHERE `" + field + "`='" + player + "'";
		}
		if(Type.equals("Necromancer")){
			containsquery = "SELECT `" + field + "` FROM `Kits-Necromancer` WHERE `" + field + "`='" + player + "'";
		}
		if(Type.equals("King")){
			containsquery = "SELECT `" + field + "` FROM `Kits-King` WHERE `" + field + "`='" + player + "'";
		}
		if(Type.equals("Tree")){
			containsquery = "SELECT `" + field + "` FROM `Kits-Tree` WHERE `" + field + "`='" + player + "'";
		}
		if(Type.equals("Blaze")){
			containsquery = "SELECT `" + field + "` FROM `Kits-Blaze` WHERE `" + field + "`='" + player + "'";
		}
		if(Type.equals("TNT")){
			containsquery = "SELECT `" + field + "` FROM `Kits-TNT` WHERE `" + field + "`='" + player + "'";
		}
		if(Type.equals("Fisherman")){
			containsquery = "SELECT `" + field + "` FROM `Kits-Fisherman` WHERE `" + field + "`='" + player + "'";
		}
		if(Type.equals("SnowGolem")){
			containsquery = "SELECT `" + field + "` FROM `Kits-SnowGolem` WHERE `" + field + "`='" + player + "'";
		}
		if(Type.equals("Librarian")){
			containsquery = "SELECT `" + field + "` FROM `Kits-Librarian` WHERE `" + field + "`='" + player + "'";
		}
		if(Type.equals("Spider")){
			containsquery = "SELECT `" + field + "` FROM `Kits-Spider` WHERE `" + field + "`='" + player + "'";
		}
		if(Type.equals("Villager")){
			containsquery = "SELECT `" + field + "` FROM `Kits-Villager` WHERE `" + field + "`='" + player + "'";
		}
		if(Type.equals("Assassin")){
			containsquery = "SELECT `" + field + "` FROM `Kits-Assassin` WHERE `" + field + "`='" + player + "'";
		}
		if(Type.equals("Lord")){
			containsquery = "SELECT `" + field + "` FROM `Kits-Lord` WHERE `" + field + "`='" + player + "'";
		}
		if(Type.equals("Vampire")){
			containsquery = "SELECT `" + field + "` FROM `Kits-Vampire` WHERE `" + field + "`='" + player + "'";
		}
		if(Type.equals("DarkMage")){
			containsquery = "SELECT `" + field + "` FROM `Kits-DarkMage` WHERE `" + field + "`='" + player + "'";
		}
		if(Type.equals("Beast")){
			containsquery = "SELECT `" + field + "` FROM `Kits-Beast` WHERE `" + field + "`='" + player + "'";
		}
		if(Type.equals("Fish")){
			containsquery = "SELECT `" + field + "` FROM `Kits-Fish` WHERE `" + field + "`='" + player + "'";
		}
		if(Type.equals("Heavy")){
			containsquery = "SELECT `" + field + "` FROM `Kits-Heavy` WHERE `" + field + "`='" + player + "'";
		}
		if(Type.equals("GrimReaper")){
			containsquery = "SELECT `" + field + "` FROM `Kits-GrimReaper` WHERE `" + field + "`='" + player + "'";
		}
		if(Type.equals("Miner")){
			containsquery = "SELECT `" + field + "` FROM `Kits-Miner` WHERE `" + field + "`='" + player + "'";
		}
		
		try {
			ResultSet rs = connection.prepareStatement(containsquery).executeQuery();
			
			boolean temp = rs.next();
			rs.close();
			return temp;
			
		} catch (SQLException ex) {
			ex.printStackTrace();
			return true;
		}
	}
	// INSERT NEW ELEMENTS
	public synchronized void insertElement(String player, String Type, int stats){
		// OMT
		if(Type.equals("OMT")){
			getquery = "INSERT INTO `OrbitMinesTokens` (`name`, `omt`) VALUES ('" + player + "', '" + stats + "')";
		}
		if(Type.equals("POINTS")){
			getquery = "INSERT INTO `VIPPoints` (`name`, `points`) VALUES ('" + player + "', '" + stats + "')";
		}
		// KILLS
		if(Type.equals("Kills")){
			getquery = "INSERT INTO `KitPvP-Kills` (`name`, `kills`) VALUES ('" + player + "', '" + stats + "')";
		}
		// DEATHS
		if(Type.equals("Deaths")){
			getquery = "INSERT INTO `KitPvP-Deaths` (`name`, `deaths`) VALUES ('" + player + "', '" + stats + "')";
		}
		// MONEY
		if(Type.equals("Money")){
			getquery = "INSERT INTO `KitPvP-Money` (`name`, `money`) VALUES ('" + player + "', '" + stats + "')";
		}
		// LEVEL
		if(Type.equals("Levels")){
			getquery = "INSERT INTO `KitPvP-Levels` (`name`, `levels`) VALUES ('" + player + "', '" + stats + "')";
		}
		// KNIGHT
		if(Type.equals("Knight")){
			getquery = "INSERT INTO `Kits-Knight` (`name`, `knight`) VALUES ('" + player + "', '" + stats + "')";
		}
		// ARCHER
		if(Type.equals("Archer")){
			getquery = "INSERT INTO `Kits-Archer` (`name`, `archer`) VALUES ('" + player + "', '" + stats + "')";
		}
		// SOLDIER
		if(Type.equals("Soldier")){
			getquery = "INSERT INTO `Kits-Soldier` (`name`, `soldier`) VALUES ('" + player + "', '" + stats + "')";
		}
		// WIZARD
		if(Type.equals("Wizard")){
			getquery = "INSERT INTO `Kits-Wizard` (`name`, `wizard`) VALUES ('" + player + "', '" + stats + "')";
		}
		// TANK
		if(Type.equals("Tank")){
			getquery = "INSERT INTO `Kits-Tank` (`name`, `tank`) VALUES ('" + player + "', '" + stats + "')";
		}
		// DRUNK
		if(Type.equals("Drunk")){
			getquery = "INSERT INTO `Kits-Drunk` (`name`, `drunk`) VALUES ('" + player + "', '" + stats + "')";
		}
		// PYRO
		if(Type.equals("Pyro")){
			getquery = "INSERT INTO `Kits-Pyro` (`name`, `pyro`) VALUES ('" + player + "', '" + stats + "')";
		}
		// BUNNT
		if(Type.equals("Bunny")){
			getquery = "INSERT INTO `Kits-Bunny` (`name`, `bunny`) VALUES ('" + player + "', '" + stats + "')";
		}
		// NECROMANCER
		if(Type.equals("Necromancer")){
			getquery = "INSERT INTO `Kits-Necromancer` (`name`, `necromancer`) VALUES ('" + player + "', '" + stats + "')";
		}
		// KING
		if(Type.equals("King")){
			getquery = "INSERT INTO `Kits-King` (`name`, `king`) VALUES ('" + player + "', '" + stats + "')";
		}
		// TREE
		if(Type.equals("Tree")){
			getquery = "INSERT INTO `Kits-Tree` (`name`, `tree`) VALUES ('" + player + "', '" + stats + "')";
		}
		// BLAZE
		if(Type.equals("Blaze")){
			getquery = "INSERT INTO `Kits-Blaze` (`name`, `blaze`) VALUES ('" + player + "', '" + stats + "')";
		}
		// TNT
		if(Type.equals("TNT")){
			getquery = "INSERT INTO `Kits-TNT` (`name`, `tnt`) VALUES ('" + player + "', '" + stats + "')";
		}
		// FISHERMAN
		if(Type.equals("Fisherman")){
			getquery = "INSERT INTO `Kits-Fisherman` (`name`, `fisherman`) VALUES ('" + player + "', '" + stats + "')";
		}
		// SNOWGOLEM
		if(Type.equals("SnowGolem")){
			getquery = "INSERT INTO `Kits-SnowGolem` (`name`, `snowgolem`) VALUES ('" + player + "', '" + stats + "')";
		}
		// LIBRARIAN
		if(Type.equals("Librarian")){
			getquery = "INSERT INTO `Kits-Librarian` (`name`, `librarian`) VALUES ('" + player + "', '" + stats + "')";
		}
		// SPIDER
		if(Type.equals("Spider")){
			getquery = "INSERT INTO `Kits-Spider` (`name`, `spider`) VALUES ('" + player + "', '" + stats + "')";
		}
		// VILLAGER
		if(Type.equals("Villager")){
			getquery = "INSERT INTO `Kits-Villager` (`name`, `villager`) VALUES ('" + player + "', '" + stats + "')";
		}
		// ASSASSIN
		if(Type.equals("Assassin")){
			getquery = "INSERT INTO `Kits-Assassin` (`name`, `assassin`) VALUES ('" + player + "', '" + stats + "')";
		}
		// LORD
		if(Type.equals("Lord")){
			getquery = "INSERT INTO `Kits-Lord` (`name`, `lord`) VALUES ('" + player + "', '" + stats + "')";
		}
		// VAMPIRE
		if(Type.equals("Vampire")){
			getquery = "INSERT INTO `Kits-Vampire` (`name`, `vampire`) VALUES ('" + player + "', '" + stats + "')";
		}
		// DARKMAGE
		if(Type.equals("DarkMage")){
			getquery = "INSERT INTO `Kits-DarkMage` (`name`, `darkmage`) VALUES ('" + player + "', '" + stats + "')";
		}
		// BEAST
		if(Type.equals("Beast")){
			getquery = "INSERT INTO `Kits-Beast` (`name`, `beast`) VALUES ('" + player + "', '" + stats + "')";
		}
		// FISH
		if(Type.equals("Fish")){
			getquery = "INSERT INTO `Kits-Fish` (`name`, `fish`) VALUES ('" + player + "', '" + stats + "')";
		}
		// HEAVY
		if(Type.equals("Heavy")){
			getquery = "INSERT INTO `Kits-Heavy` (`name`, `heavy`) VALUES ('" + player + "', '" + stats + "')";
		}
		// GRIMREAPER
		if(Type.equals("GrimReaper")){
			getquery = "INSERT INTO `Kits-GrimReaper` (`name`, `grimreaper`) VALUES ('" + player + "', '" + stats + "')";
		}
		// MINER
		if(Type.equals("Miner")){
			getquery = "INSERT INTO `Kits-Miner` (`name`, `miner`) VALUES ('" + player + "', '" + stats + "')";
		}
		
		try{
			PreparedStatement ps = connection.prepareStatement(getquery);
			ps.execute();
			ps.close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
	}
	// GET KILLS
	public int getKills(Player p){
		int kills = 0;
		
		String query = "SELECT `kills` FROM `KitPvP-Kills` WHERE `name` = '" + p.getName() + "'"; 
		
		try{
			ResultSet rs = connection.prepareStatement(query).executeQuery();
			
			while (rs.next()) {
				kills = rs.getInt("kills");
			}
			
			rs.close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
		return kills;
	}
	// GET DEATHS
	public int getDeaths(Player p){
		int deaths = 0;
		
		String query = "SELECT `deaths` FROM `KitPvP-Deaths` WHERE `name` = '" + p.getName() + "'"; 
		
		try{
			ResultSet rs = connection.prepareStatement(query).executeQuery();
			
			while (rs.next()) {
				deaths = rs.getInt("deaths");
			}
			
			rs.close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
		return deaths;
	}
	// GET MONEY
	public int getMoney(Player p){
		int money = 0;
		
		String query = "SELECT `money` FROM `KitPvP-Money` WHERE `name` = '" + p.getName() + "'"; 
		
		try{
			ResultSet rs = connection.prepareStatement(query).executeQuery();
			
			while (rs.next()) {
				money = rs.getInt("money");
			}
			
			rs.close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
		return money;
	}
	// GET LEVEL
	public int getLevel(Player p){
		int level = 0;
		
		String query = "SELECT `levels` FROM `KitPvP-Levels` WHERE `name` = '" + p.getName() + "'"; 
		
		try{
			ResultSet rs = connection.prepareStatement(query).executeQuery();
			
			while (rs.next()) {
				level = rs.getInt("levels");
			}
			
			rs.close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
		return level;
	}
	// GET KNIGHT LEVEL
	public int getKnightLvL(Player p){
		int kit = 0;
		
		String query = "SELECT `knight` FROM `Kits-Knight` WHERE `name` = '" + p.getName() + "'"; 
		
		try{
			ResultSet rs = connection.prepareStatement(query).executeQuery();
			
			while (rs.next()) {
				kit = rs.getInt("knight");
			}
			
			rs.close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
		return kit;
	}
	// GET ARCHER LEVEL
	public int getArcherLvL(Player p){
		int kit = 0;
		
		String query = "SELECT `archer` FROM `Kits-Archer` WHERE `name` = '" + p.getName() + "'"; 
		
		try{
			ResultSet rs = connection.prepareStatement(query).executeQuery();
			
			while (rs.next()) {
				kit = rs.getInt("archer");
			}
			
			rs.close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
		return kit;
	}
	// GET SOLDIER LEVEL
	public int getSoldierLvL(Player p){
		int kit = 0;
		
		String query = "SELECT `soldier` FROM `Kits-Soldier` WHERE `name` = '" + p.getName() + "'"; 
		
		try{
			ResultSet rs = connection.prepareStatement(query).executeQuery();
			
			while (rs.next()) {
				kit = rs.getInt("soldier");
			}
			
			rs.close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
		return kit;
	}
	// GET WIZARD LEVEL
	public int getWizardLvL(Player p){
		int kit = 0;
		
		String query = "SELECT `wizard` FROM `Kits-Wizard` WHERE `name` = '" + p.getName() + "'"; 
		
		try{
			ResultSet rs = connection.prepareStatement(query).executeQuery();
			
			while (rs.next()) {
				kit = rs.getInt("wizard");
			}
			
			rs.close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
		return kit;
	}
	// GET TANK LEVEL
	public int getTankLvL(Player p){
		int kit = 0;
		
		String query = "SELECT `tank` FROM `Kits-Tank` WHERE `name` = '" + p.getName() + "'"; 
		
		try{
			ResultSet rs = connection.prepareStatement(query).executeQuery();
			
			while (rs.next()) {
				kit = rs.getInt("tank");
			}
			
			rs.close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
		return kit;
	}
	// GET DRUNK LEVEL
	public int getDrunkLvL(Player p){
		int kit = 0;
		
		String query = "SELECT `drunk` FROM `Kits-Drunk` WHERE `name` = '" + p.getName() + "'"; 
		
		try{
			ResultSet rs = connection.prepareStatement(query).executeQuery();
			
			while (rs.next()) {
				kit = rs.getInt("drunk");
			}
			
			rs.close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
		return kit;
	}
	// GET PYRO LEVEL
	public int getPyroLvL(Player p){
		int kit = 0;
		
		String query = "SELECT `pyro` FROM `Kits-Pyro` WHERE `name` = '" + p.getName() + "'"; 
		
		try{
			ResultSet rs = connection.prepareStatement(query).executeQuery();
			
			while (rs.next()) {
				kit = rs.getInt("pyro");
			}
			
			rs.close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
		return kit;
	}
	// GET BUNNY LEVEL
	public int getBunnyLvL(Player p){
		int kit = 0;
		
		String query = "SELECT `bunny` FROM `Kits-Bunny` WHERE `name` = '" + p.getName() + "'"; 
		
		try{
			ResultSet rs = connection.prepareStatement(query).executeQuery();
			
			while (rs.next()) {
				kit = rs.getInt("bunny");
			}
			
			rs.close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
		return kit;
	}
	// GET NECROMANCER LEVEL
	public int getNecromancerLvL(Player p){
		int kit = 0;
		
		String query = "SELECT `necromancer` FROM `Kits-Necromancer` WHERE `name` = '" + p.getName() + "'"; 
		
		try{
			ResultSet rs = connection.prepareStatement(query).executeQuery();
			
			while (rs.next()) {
				kit = rs.getInt("necromancer");
			}
			
			rs.close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
		return kit;
	}
	// GET KING LEVEL
	public int getKingLvL(Player p){
		int kit = 0;
		
		String query = "SELECT `king` FROM `Kits-King` WHERE `name` = '" + p.getName() + "'"; 
		
		try{
			ResultSet rs = connection.prepareStatement(query).executeQuery();
			
			while (rs.next()) {
				kit = rs.getInt("king");
			}
			
			rs.close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
		return kit;
	}
	// GET TREE LEVEL
	public int getTreeLvL(Player p){
		int kit = 0;
		
		String query = "SELECT `tree` FROM `Kits-Tree` WHERE `name` = '" + p.getName() + "'"; 
		
		try{
			ResultSet rs = connection.prepareStatement(query).executeQuery();
			
			while (rs.next()) {
				kit = rs.getInt("tree");
			}
			
			rs.close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
		return kit;
	}
	// GET BLAZE LEVEL
	public int getBlazeLvL(Player p){
		int kit = 0;
		
		String query = "SELECT `blaze` FROM `Kits-Blaze` WHERE `name` = '" + p.getName() + "'"; 
		
		try{
			ResultSet rs = connection.prepareStatement(query).executeQuery();
			
			while (rs.next()) {
				kit = rs.getInt("blaze");
			}
			
			rs.close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
		return kit;
	}
	// GET TNT LEVEL
	public int getTNTLvL(Player p){
		int kit = 0;
		
		String query = "SELECT `tnt` FROM `Kits-TNT` WHERE `name` = '" + p.getName() + "'"; 
		
		try{
			ResultSet rs = connection.prepareStatement(query).executeQuery();
			
			while (rs.next()) {
				kit = rs.getInt("tnt");
			}
			
			rs.close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
		return kit;
	}
	// GET FISHERMAN LEVEL
	public int getFishermanLvL(Player p){
		int kit = 0;
		
		String query = "SELECT `fisherman` FROM `Kits-Fisherman` WHERE `name` = '" + p.getName() + "'"; 
		
		try{
			ResultSet rs = connection.prepareStatement(query).executeQuery();
			
			while (rs.next()) {
				kit = rs.getInt("fisherman");
			}
			
			rs.close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
		return kit;
	}
	// GET SNOWGOLEM LEVEL
	public int getSnowGolemLvL(Player p){
		int kit = 0;
		
		String query = "SELECT `snowgolem` FROM `Kits-SnowGolem` WHERE `name` = '" + p.getName() + "'"; 
		
		try{
			ResultSet rs = connection.prepareStatement(query).executeQuery();
			
			while (rs.next()) {
				kit = rs.getInt("snowgolem");
			}
			
			rs.close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
		return kit;
	}
	// GET LIBRARIAN LEVEL
	public int getLibrarianLvL(Player p){
		int kit = 0;
		
		String query = "SELECT `librarian` FROM `Kits-Librarian` WHERE `name` = '" + p.getName() + "'"; 
		
		try{
			ResultSet rs = connection.prepareStatement(query).executeQuery();
			
			while (rs.next()) {
				kit = rs.getInt("librarian");
			}
			
			rs.close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
		return kit;
	}
	// GET SPIDER LEVEL
	public int getSpiderLvL(Player p){
		int kit = 0;
		
		String query = "SELECT `spider` FROM `Kits-Spider` WHERE `name` = '" + p.getName() + "'"; 
		
		try{
			ResultSet rs = connection.prepareStatement(query).executeQuery();
			
			while (rs.next()) {
				kit = rs.getInt("spider");
			}
			
			rs.close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
		return kit;
	}
	// GET VILLAGER LEVEL
	public int getVillagerLvL(Player p){
		int kit = 0;
		
		String query = "SELECT `villager` FROM `Kits-Villager` WHERE `name` = '" + p.getName() + "'"; 
		
		try{
			ResultSet rs = connection.prepareStatement(query).executeQuery();
			
			while (rs.next()) {
				kit = rs.getInt("villager");
			}
			
			rs.close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
		return kit;
	}
	// GET ASSASSIN LEVEL
	public int getAssassinLvL(Player p){
		int kit = 0;
		
		String query = "SELECT `assassin` FROM `Kits-Assassin` WHERE `name` = '" + p.getName() + "'"; 
		
		try{
			ResultSet rs = connection.prepareStatement(query).executeQuery();
			
			while (rs.next()) {
				kit = rs.getInt("assassin");
			}
			
			rs.close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
		return kit;
	}
	// GET LORD LEVEL
	public int getLordLvL(Player p){
		int kit = 0;
		
		String query = "SELECT `lord` FROM `Kits-Lord` WHERE `name` = '" + p.getName() + "'"; 
		
		try{
			ResultSet rs = connection.prepareStatement(query).executeQuery();
			
			while (rs.next()) {
				kit = rs.getInt("lord");
			}
			
			rs.close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
		return kit;
	}
	// GET VAMPIRE LEVEL
	public int getVampireLvL(Player p){
		int kit = 0;
		
		String query = "SELECT `vampire` FROM `Kits-Vampire` WHERE `name` = '" + p.getName() + "'"; 
		
		try{
			ResultSet rs = connection.prepareStatement(query).executeQuery();
			
			while (rs.next()) {
				kit = rs.getInt("vampire");
			}
			
			rs.close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
		return kit;
	}
	// GET DARKMAGE LEVEL
	public int getDarkMageLvL(Player p){
		int kit = 0;
		
		String query = "SELECT `darkmage` FROM `Kits-DarkMage` WHERE `name` = '" + p.getName() + "'"; 
		
		try{
			ResultSet rs = connection.prepareStatement(query).executeQuery();
			
			while (rs.next()) {
				kit = rs.getInt("darkmage");
			}
			
			rs.close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
		return kit;
	}
	// GET BEAST LEVEL
	public int getBeastLvL(Player p){
		int kit = 0;
		
		String query = "SELECT `beast` FROM `Kits-Beast` WHERE `name` = '" + p.getName() + "'"; 
		
		try{
			ResultSet rs = connection.prepareStatement(query).executeQuery();
			
			while (rs.next()) {
				kit = rs.getInt("beast");
			}
			
			rs.close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
		return kit;
	}
	// GET FISH LEVEL
	public int getFishLvL(Player p){
		int kit = 0;
		
		String query = "SELECT `fish` FROM `Kits-Fish` WHERE `name` = '" + p.getName() + "'"; 
		
		try{
			ResultSet rs = connection.prepareStatement(query).executeQuery();
			
			while (rs.next()) {
				kit = rs.getInt("fish");
			}
			
			rs.close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
		return kit;
	}
	// GET HEAVY LEVEL
	public int getHeavyLvL(Player p){
		int kit = 0;
		
		String query = "SELECT `heavy` FROM `Kits-Heavy` WHERE `name` = '" + p.getName() + "'"; 
		
		try{
			ResultSet rs = connection.prepareStatement(query).executeQuery();
			
			while (rs.next()) {
				kit = rs.getInt("heavy");
			}
			
			rs.close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
		return kit;
	}
	// GET GRIMREAPER LEVEL
	public int getGrimReaperLvL(Player p){
		int kit = 0;
		
		String query = "SELECT `grimreaper` FROM `Kits-GrimReaper` WHERE `name` = '" + p.getName() + "'"; 
		
		try{
			ResultSet rs = connection.prepareStatement(query).executeQuery();
			
			while (rs.next()) {
				kit = rs.getInt("grimreaper");
			}
			
			rs.close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
		return kit;
	}
	// GET MINER LEVEL
	public int getMinerLvL(Player p){
		int kit = 0;
		
		String query = "SELECT `miner` FROM `Kits-Miner` WHERE `name` = '" + p.getName() + "'"; 
		
		try{
			ResultSet rs = connection.prepareStatement(query).executeQuery();
			
			while (rs.next()) {
				kit = rs.getInt("miner");
			}
			
			rs.close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
		return kit;
	}
	// GET OMT
	public int getOMT(Player p){
		int kit = 0;
		
		String query = "SELECT `omt` FROM `OrbitMinesTokens` WHERE `name` = '" + p.getName() + "'"; 
		
		try{
			ResultSet rs = connection.prepareStatement(query).executeQuery();
			
			while (rs.next()) {
				kit = rs.getInt("omt");
			}
			
			rs.close();
		} catch (SQLException ex){
			ex.printStackTrace();
		}
		return kit;
	}
	// ADD OMT
	public void addOMT(Player p, int OMT) throws SQLException{
		Statement s = connection.createStatement();
		s.executeUpdate("UPDATE `OrbitMinesTokens` SET `omt` = '" + (getOMT(p) + OMT) + "' WHERE `name` = '" + p.getName() + "'");
		Start.omt.put(p.getName(), getOMT(p));
		onScoreboard(p);
	}
	// ADD OMT
	public void removeOMT(Player p, int OMT) throws SQLException{
		Statement s = connection.createStatement();
		s.executeUpdate("UPDATE `OrbitMinesTokens` SET `omt` = '" + (getOMT(p) - OMT) + "' WHERE `name` = '" + p.getName() + "'");
		Start.omt.put(p.getName(), getOMT(p));
		onScoreboard(p);
	}
	// ADD KILLS
	public void addKills(Player p, int kills) throws SQLException{
		Statement s = connection.createStatement();
		s.executeUpdate("UPDATE `KitPvP-Kills` SET `kills` = '" + (getKills(p) + kills) + "' WHERE `name` = '" + p.getName() + "'");
		
	}
	// ADD DEATHS
	public void addDeaths(Player p, int deaths) throws SQLException{
		Statement s = connection.createStatement();
		s.executeUpdate("UPDATE `KitPvP-Deaths` SET `deaths` = '" + (getDeaths(p) + deaths) + "' WHERE `name` = '" + p.getName() + "'");
		
	}
	// ADD COINS
	public void addMoney(Player p, int money) throws SQLException{
		Statement s = connection.createStatement();
		s.executeUpdate("UPDATE `KitPvP-Money` SET `money` = '" + (getMoney(p) + money) + "' WHERE `name` = '" + p.getName() + "'");
		Start.money.put(p.getName(), getMoney(p));
		onScoreboard(p);
	}
	// REMOVE COINS
	public void removeMoney(Player p, int money) throws SQLException{
		Statement s = connection.createStatement();
		s.executeUpdate("UPDATE `KitPvP-Money` SET `money` = '" + (getMoney(p) - money) + "' WHERE `name` = '" + p.getName() + "'");
		Start.money.put(p.getName(), getMoney(p));
		onScoreboard(p);
	}
	// ADD LEVELS
	public void addLevel(Player p, int level) throws SQLException{
		Statement s = connection.createStatement();
		s.executeUpdate("UPDATE `KitPvP-Levels` SET `levels` = '" + (getLevel(p) + level) + "' WHERE `name` = '" + p.getName() + "'");
		Bukkit.broadcastMessage(TAG + "§6" + p.getName() + " §7reached level §6" + getLevel(p) + "§7!");
		
		p.setLevel(getLevel(p));
		p.setExp(1);
	}
	// ADD KNIGHT LVL
	public void addKnightLvL(Player p, int lvl) throws SQLException{
		Statement s = connection.createStatement();
		s.executeUpdate("UPDATE `Kits-Knight` SET `knight` = '" + (getKnightLvL(p) + lvl) + "' WHERE `name` = '" + p.getName() + "'");
		knight.put(p.getName(), getKnightLvL(p));
		p.playSound(p.getLocation(), Sound.ANVIL_LAND, 6, 1);
	}
	// ADD ARCHER LVL
	public void addArcherLvL(Player p, int lvl) throws SQLException{
		Statement s = connection.createStatement();
		s.executeUpdate("UPDATE `Kits-Archer` SET `archer` = '" + (getArcherLvL(p) + lvl) + "' WHERE `name` = '" + p.getName() + "'");
		archer.put(p.getName(), getArcherLvL(p));
		p.playSound(p.getLocation(), Sound.ANVIL_LAND, 6, 1);
	}
	// ADD SOLDIER LVL
	public void addSoldierLvL(Player p, int lvl) throws SQLException{
		Statement s = connection.createStatement();
		s.executeUpdate("UPDATE `Kits-Soldier` SET `soldier` = '" + (getSoldierLvL(p) + lvl) + "' WHERE `name` = '" + p.getName() + "'");
		soldier.put(p.getName(), getSoldierLvL(p));
		p.playSound(p.getLocation(), Sound.ANVIL_LAND, 6, 1);
	}
	// ADD WIZARD LVL
	public void addWizardLvL(Player p, int lvl) throws SQLException{
		Statement s = connection.createStatement();
		s.executeUpdate("UPDATE `Kits-Wizard` SET `wizard` = '" + (getWizardLvL(p) + lvl) + "' WHERE `name` = '" + p.getName() + "'");
		wizard.put(p.getName(), getWizardLvL(p));
		p.playSound(p.getLocation(), Sound.ANVIL_LAND, 6, 1);
	}
	// ADD TANK LVL
	public void addTankLvL(Player p, int lvl) throws SQLException{
		Statement s = connection.createStatement();
		s.executeUpdate("UPDATE `Kits-Tank` SET `tank` = '" + (getTankLvL(p) + lvl) + "' WHERE `name` = '" + p.getName() + "'");
		tank.put(p.getName(), getTankLvL(p));
		p.playSound(p.getLocation(), Sound.ANVIL_LAND, 6, 1);
	}
	// ADD DRUNK LVL
	public void addDrunkLvL(Player p, int lvl) throws SQLException{
		Statement s = connection.createStatement();
		s.executeUpdate("UPDATE `Kits-Drunk` SET `drunk` = '" + (getDrunkLvL(p) + lvl) + "' WHERE `name` = '" + p.getName() + "'");
		drunk.put(p.getName(), getDrunkLvL(p));
		p.playSound(p.getLocation(), Sound.ANVIL_LAND, 6, 1);
	}
	// ADD PYRO LVL
	public void addPyroLvL(Player p, int lvl) throws SQLException{
		Statement s = connection.createStatement();
		s.executeUpdate("UPDATE `Kits-Pyro` SET `pyro` = '" + (getPyroLvL(p) + lvl) + "' WHERE `name` = '" + p.getName() + "'");
		pyro.put(p.getName(), getPyroLvL(p));
		p.playSound(p.getLocation(), Sound.ANVIL_LAND, 6, 1);
	}
	// ADD BUNNY LVL
	public void addBunnyLvL(Player p, int lvl) throws SQLException{
		Statement s = connection.createStatement();
		s.executeUpdate("UPDATE `Kits-Bunny` SET `bunny` = '" + (getBunnyLvL(p) + lvl) + "' WHERE `name` = '" + p.getName() + "'");
		bunny.put(p.getName(), getBunnyLvL(p));
		p.playSound(p.getLocation(), Sound.ANVIL_LAND, 6, 1);
	}
	// ADD NECROMANCER LVL
	public void addNecromancerLvL(Player p, int lvl) throws SQLException{
		Statement s = connection.createStatement();
		s.executeUpdate("UPDATE `Kits-Necromancer` SET `necromancer` = '" + (getNecromancerLvL(p) + lvl) + "' WHERE `name` = '" + p.getName() + "'");
		necromancer.put(p.getName(), getNecromancerLvL(p));
		p.playSound(p.getLocation(), Sound.ANVIL_LAND, 6, 1);
	}
	// ADD KING LVL
	public void addKingLvL(Player p, int lvl) throws SQLException{
		Statement s = connection.createStatement();
		s.executeUpdate("UPDATE `Kits-King` SET `king` = '" + (getKingLvL(p) + lvl) + "' WHERE `name` = '" + p.getName() + "'");
		king.put(p.getName(), getKingLvL(p));
		p.playSound(p.getLocation(), Sound.ANVIL_LAND, 6, 1);
	}
	// ADD TREE LVL
	public void addTreeLvL(Player p, int lvl) throws SQLException{
		Statement s = connection.createStatement();
		s.executeUpdate("UPDATE `Kits-Tree` SET `tree` = '" + (getTreeLvL(p) + lvl) + "' WHERE `name` = '" + p.getName() + "'");
		tree.put(p.getName(), getTreeLvL(p));
		p.playSound(p.getLocation(), Sound.ANVIL_LAND, 6, 1);
	}
	// ADD BLAZE LVL
	public void addBlazeLvL(Player p, int lvl) throws SQLException{
		Statement s = connection.createStatement();
		s.executeUpdate("UPDATE `Kits-Blaze` SET `blaze` = '" + (getBlazeLvL(p) + lvl) + "' WHERE `name` = '" + p.getName() + "'");
		blaze.put(p.getName(), getBlazeLvL(p));
		p.playSound(p.getLocation(), Sound.ANVIL_LAND, 6, 1);
	}
	// ADD TNT LVL
	public void addTNTLvL(Player p, int lvl) throws SQLException{
		Statement s = connection.createStatement();
		s.executeUpdate("UPDATE `Kits-TNT` SET `tnt` = '" + (getTNTLvL(p) + lvl) + "' WHERE `name` = '" + p.getName() + "'");
		tnt.put(p.getName(), getTNTLvL(p));
		p.playSound(p.getLocation(), Sound.ANVIL_LAND, 6, 1);
	}
	// ADD FISHERMAN LVL
	public void addFishermanLvL(Player p, int lvl) throws SQLException{
		Statement s = connection.createStatement();
		s.executeUpdate("UPDATE `Kits-Fisherman` SET `fisherman` = '" + (getFishermanLvL(p) + lvl) + "' WHERE `name` = '" + p.getName() + "'");
		fisherman.put(p.getName(), getFishermanLvL(p));
		p.playSound(p.getLocation(), Sound.ANVIL_LAND, 6, 1);
	}
	// ADD SNOWGOLEM LVL
	public void addSnowGolemLvL(Player p, int lvl) throws SQLException{
		Statement s = connection.createStatement();
		s.executeUpdate("UPDATE `Kits-SnowGolem` SET `snowgolem` = '" + (getSnowGolemLvL(p) + lvl) + "' WHERE `name` = '" + p.getName() + "'");
		snowgolem.put(p.getName(), getSnowGolemLvL(p));
		p.playSound(p.getLocation(), Sound.ANVIL_LAND, 6, 1);
	}
	// ADD LIBRARIAN LVL
	public void addLibrarianLvL(Player p, int lvl) throws SQLException{
		Statement s = connection.createStatement();
		s.executeUpdate("UPDATE `Kits-Librarian` SET `librarian` = '" + (getLibrarianLvL(p) + lvl) + "' WHERE `name` = '" + p.getName() + "'");
		librarian.put(p.getName(), getLibrarianLvL(p));
		p.playSound(p.getLocation(), Sound.ANVIL_LAND, 6, 1);
	}
	// ADD SPIDER LVL
	public void addSpiderLvL(Player p, int lvl) throws SQLException{
		Statement s = connection.createStatement();
		s.executeUpdate("UPDATE `Kits-Spider` SET `spider` = '" + (getSpiderLvL(p) + lvl) + "' WHERE `name` = '" + p.getName() + "'");
		spider.put(p.getName(), getSpiderLvL(p));
		p.playSound(p.getLocation(), Sound.ANVIL_LAND, 6, 1);
	}
	// ADD VILLAGER LVL
	public void addVillagerLvL(Player p, int lvl) throws SQLException{
		Statement s = connection.createStatement();
		s.executeUpdate("UPDATE `Kits-Villager` SET `villager` = '" + (getVillagerLvL(p) + lvl) + "' WHERE `name` = '" + p.getName() + "'");
		villager.put(p.getName(), getVillagerLvL(p));
		p.playSound(p.getLocation(), Sound.ANVIL_LAND, 6, 1);
	}
	// ADD ASSASSIN LVL
	public void addAssassinLvL(Player p, int lvl) throws SQLException{
		Statement s = connection.createStatement();
		s.executeUpdate("UPDATE `Kits-Assassin` SET `assassin` = '" + (getAssassinLvL(p) + lvl) + "' WHERE `name` = '" + p.getName() + "'");
		assassin.put(p.getName(), getAssassinLvL(p));
		p.playSound(p.getLocation(), Sound.ANVIL_LAND, 6, 1);
	}
	// ADD LORD LVL
	public void addLordLvL(Player p, int lvl) throws SQLException{
		Statement s = connection.createStatement();
		s.executeUpdate("UPDATE `Kits-Lord` SET `lord` = '" + (getLordLvL(p) + lvl) + "' WHERE `name` = '" + p.getName() + "'");
		lord.put(p.getName(), getLordLvL(p));
		p.playSound(p.getLocation(), Sound.ANVIL_LAND, 6, 1);
	}
	// ADD VAMPIRE LVL
	public void addVampireLvL(Player p, int lvl) throws SQLException{
		Statement s = connection.createStatement();
		s.executeUpdate("UPDATE `Kits-Vampire` SET `vampire` = '" + (getVampireLvL(p) + lvl) + "' WHERE `name` = '" + p.getName() + "'");
		vampire.put(p.getName(), getVampireLvL(p));
		p.playSound(p.getLocation(), Sound.ANVIL_LAND, 6, 1);
	}
	// ADD DARKMAGE LVL
	public void addDarkMageLvL(Player p, int lvl) throws SQLException{
		Statement s = connection.createStatement();
		s.executeUpdate("UPDATE `Kits-DarkMage` SET `darkmage` = '" + (getDarkMageLvL(p) + lvl) + "' WHERE `name` = '" + p.getName() + "'");
		darkmage.put(p.getName(), getDarkMageLvL(p));
		p.playSound(p.getLocation(), Sound.ANVIL_LAND, 6, 1);
	}
	// ADD BEAST LVL
	public void addBeastLvL(Player p, int lvl) throws SQLException{
		Statement s = connection.createStatement();
		s.executeUpdate("UPDATE `Kits-Beast` SET `beast` = '" + (getBeastLvL(p) + lvl) + "' WHERE `name` = '" + p.getName() + "'");
		beast.put(p.getName(), getBeastLvL(p));
		p.playSound(p.getLocation(), Sound.ANVIL_LAND, 6, 1);
	}
	// ADD FISH LVL
	public void addFishLvL(Player p, int lvl) throws SQLException{
		Statement s = connection.createStatement();
		s.executeUpdate("UPDATE `Kits-Fish` SET `fish` = '" + (getFishLvL(p) + lvl) + "' WHERE `name` = '" + p.getName() + "'");
		fish.put(p.getName(), getFishLvL(p));
		p.playSound(p.getLocation(), Sound.ANVIL_LAND, 6, 1);
	}
	// ADD HEAVY LVL
	public void addHeavyLvL(Player p, int lvl) throws SQLException{
		Statement s = connection.createStatement();
		s.executeUpdate("UPDATE `Kits-Heavy` SET `heavy` = '" + (getHeavyLvL(p) + lvl) + "' WHERE `name` = '" + p.getName() + "'");
		heavy.put(p.getName(), getHeavyLvL(p));
		p.playSound(p.getLocation(), Sound.ANVIL_LAND, 6, 1);
	}
	// ADD GRIMREAPER LVL
	public void addGrimReaperLvL(Player p, int lvl) throws SQLException{
		Statement s = connection.createStatement();
		s.executeUpdate("UPDATE `Kits-GrimReaper` SET `grimreaper` = '" + (getGrimReaperLvL(p) + lvl) + "' WHERE `name` = '" + p.getName() + "'");
		grimreaper.put(p.getName(), getGrimReaperLvL(p));
		p.playSound(p.getLocation(), Sound.ANVIL_LAND, 6, 1);
	}
	// ADD MINER LVL
	public void addMinerLvL(Player p, int lvl) throws SQLException{
		Statement s = connection.createStatement();
		s.executeUpdate("UPDATE `Kits-Miner` SET `miner` = '" + (getMinerLvL(p) + lvl) + "' WHERE `name` = '" + p.getName() + "'");
		miner.put(p.getName(), getMinerLvL(p));
		p.playSound(p.getLocation(), Sound.ANVIL_LAND, 6, 1);
	}
	
	public void onScoreboard(Player p){
		
		ScoreboardManager s = Bukkit.getScoreboardManager();
		Scoreboard b = s.getNewScoreboard();
		
		Objective o = b.registerNewObjective("Test", "Test2");
		o.setDisplayName("§c§lKitPvP");
		
		o.setDisplaySlot(DisplaySlot.SIDEBAR);

		Score score = o.getScore(("§4§lDeaths:"));
		score.setScore(deaths.get(p.getName()));
		
		Score score1 = o.getScore(("§c§lKills:"));
		score1.setScore(kills.get(p.getName()));
		
		Score score2 = o.getScore(("§6§lCoins:"));
		score2.setScore(money.get(p.getName()));
		
		Score score3 = o.getScore(("§a§lOnline:"));
		score3.setScore(Bukkit.getOnlinePlayers().length);
		
		Score score4 = o.getScore(("§e§lTokens:"));
		score4.setScore(omt.get(p.getName()));
		
		Score score5 = o.getScore(("§b§lVIP Points:"));
		score5.setScore(points.get(p.getName()));
		
		Team IronVIP = b.registerNewTeam("IronVIP");
		IronVIP.setPrefix("§7§lIron §f");
		Team GoldVIP = b.registerNewTeam("GoldVIP");
		GoldVIP.setPrefix("§6§lGold §f");
		Team DiamondVIP = b.registerNewTeam("DiamondVIP");
		DiamondVIP.setPrefix("§9§lDiamond §f");
		Team EmeraldVIP = b.registerNewTeam("EmeraldVIP");
		EmeraldVIP.setPrefix("§a§lEmerald §f");
		Team Builder = b.registerNewTeam("Builder");
		Builder.setPrefix("§d§lBuilder §f");
		Team Moderator = b.registerNewTeam("Moderator");
		Moderator.setPrefix("§b§lMod §f");
		Team Owner = b.registerNewTeam("Owner");
		Owner.setPrefix("§4§lOwner §f");
		
		for(Player player : Bukkit.getOnlinePlayers()){
			if(player.hasPermission("Rank.Owner")){
				Owner.addPlayer(player);
			}
			else if(player.hasPermission("Rank.Moderator")){
				Moderator.addPlayer(player);
			}
			else if(player.hasPermission("Rank.Builder")){
				Builder.addPlayer(player);
			}
			else if(player.hasPermission("Rank.Emerald")){
				EmeraldVIP.addPlayer(player);
			}
			else if(player.hasPermission("Rank.Diamond")){
				DiamondVIP.addPlayer(player);
			}
			else if(player.hasPermission("Rank.Gold")){
				GoldVIP.addPlayer(player);
			}
			else if(player.hasPermission("Rank.Iron")){
				IronVIP.addPlayer(player);
			}
			else{
				
			}
		}
		
		p.setScoreboard(b);
		
	}
	
	// VAULT SETUP
	public static Permission permission = null;
    public static Economy economy = null;
    public static Chat chat = null;

    private boolean setupPermissions()
    {
        RegisteredServiceProvider<Permission> permissionProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.permission.Permission.class);
        if (permissionProvider != null) {
            permission = permissionProvider.getProvider();
        }
        return (permission != null);
    }

    private boolean setupChat()
    {
        RegisteredServiceProvider<Chat> chatProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.chat.Chat.class);
        if (chatProvider != null) {
            chat = chatProvider.getProvider();
        }

        return (chat != null);
    }

    private boolean setupEconomy()
    {
        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
            economy = economyProvider.getProvider();
        }
        
        return (economy != null);
    }
}
