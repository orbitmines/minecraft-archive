package me.O_o_Fadi_o_O.Hub;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import me.O_o_Fadi_o_O.Hub.Inv.CosmeticPerks;
import me.O_o_Fadi_o_O.Hub.Inv.ServerSelector;
import me.O_o_Fadi_o_O.Hub.NMS.CustomCow;
import me.O_o_Fadi_o_O.Hub.NMS.CustomMagmaCube;
import me.O_o_Fadi_o_O.Hub.NMS.CustomMushroomCow;
import me.O_o_Fadi_o_O.Hub.NMS.CustomOcelot;
import me.O_o_Fadi_o_O.Hub.NMS.CustomPig;
import me.O_o_Fadi_o_O.Hub.NMS.CustomSheep;
import me.O_o_Fadi_o_O.Hub.NMS.CustomSilverfish;
import me.O_o_Fadi_o_O.Hub.NMS.CustomSlime;
import me.O_o_Fadi_o_O.Hub.NMS.CustomWolf;
import me.O_o_Fadi_o_O.Hub.events.BlockChangeEvent;
import me.O_o_Fadi_o_O.Hub.events.ClickEventENG;
import me.O_o_Fadi_o_O.Hub.events.CommandPreprocessEvent;
import me.O_o_Fadi_o_O.Hub.events.DamageEvent;
import me.O_o_Fadi_o_O.Hub.events.DropEvent;
import me.O_o_Fadi_o_O.Hub.events.EntityDamage;
import me.O_o_Fadi_o_O.Hub.events.EntityDeath;
import me.O_o_Fadi_o_O.Hub.events.EntityInteractEvent;
import me.O_o_Fadi_o_O.Hub.events.ExplodeEvent;
import me.O_o_Fadi_o_O.Hub.events.FoodEvent;
import me.O_o_Fadi_o_O.Hub.events.JoinEvent;
import me.O_o_Fadi_o_O.Hub.events.PickupEvent;
import me.O_o_Fadi_o_O.Hub.events.PlayerChat;
import me.O_o_Fadi_o_O.Hub.events.PlayerInteract;
import me.O_o_Fadi_o_O.Hub.events.PlayerMove;
import me.O_o_Fadi_o_O.Hub.events.ProjectileHit;
import me.O_o_Fadi_o_O.Hub.events.QuitEvent;
import me.O_o_Fadi_o_O.Hub.managers.DatabaseManager;
import me.O_o_Fadi_o_O.Hub.managers.PlayerManager;
import me.O_o_Fadi_o_O.Hub.managers.StorageManager;
import me.O_o_Fadi_o_O.Hub.managers.VoteManager;
import me.confuser.barapi.BarAPI;
import net.minecraft.server.v1_7_R3.PacketPlayOutWorldParticles;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_7_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;
import org.bukkit.util.Vector;

public class Hub extends JavaPlugin {
	
	public int i = 0;
	public int i2 = 0;
	public int i3 = 0;
	
	public int bi = 0;
	
	static Connection connection;
    
	JoinEvent joinEvent = new JoinEvent(this);
	ServerSelector sinv = new ServerSelector();
	
	protected static Field mapStringToClassField, mapClassToStringField, mapClassToIdField, mapStringToIdField;
	//protected static Field mapIdToClassField;
	 
	static
	{
	    try
	    {
	        mapStringToClassField = net.minecraft.server.v1_7_R3.EntityTypes.class.getDeclaredField("c");
	        mapClassToStringField = net.minecraft.server.v1_7_R3.EntityTypes.class.getDeclaredField("d");
	        //mapIdtoClassField = net.minecraft.server.v1_7_R3.EntityTypes.class.getDeclaredField("e");
	        mapClassToIdField = net.minecraft.server.v1_7_R3.EntityTypes.class.getDeclaredField("f");
	        mapStringToIdField = net.minecraft.server.v1_7_R3.EntityTypes.class.getDeclaredField("g");
	 
	        mapStringToClassField.setAccessible(true);
	        mapClassToStringField.setAccessible(true);
	        //mapIdToClassField.setAccessible(true);
	        mapClassToIdField.setAccessible(true);
	        mapStringToIdField.setAccessible(true);
	    }
	    catch(Exception e) {e.printStackTrace();}
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected static void addCustomEntity(Class entityClass, String name, int id)
	{
	    if (mapStringToClassField == null || mapStringToIdField == null || mapClassToStringField == null || mapClassToIdField == null)
	    {
	        return;
	    }
	    else
	    {
	        try
	        {
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
	        catch (Exception e)
	        {
	            e.printStackTrace();
	        }
	    }
	}
	
	public void onEnable(){
		
		addCustomEntity(CustomMushroomCow.class, "CustomMushroomCow", 96);
		addCustomEntity(CustomPig.class, "CustomPig", 90);
		addCustomEntity(CustomWolf.class, "CustomWolf", 95);
		addCustomEntity(CustomSheep.class, "CustomSheep", 91);
		addCustomEntity(CustomMagmaCube.class, "CustomMagmaCube", 62);
		addCustomEntity(CustomSlime.class, "CustomSlime", 55);
		addCustomEntity(CustomCow.class, "CustomCow", 92);
		addCustomEntity(CustomSilverfish.class, "CustomSilverfish", 60);
		addCustomEntity(CustomOcelot.class, "CustomOcelot", 98);
		
		try{
			for(World w : Bukkit.getWorlds()){
				for(Entity en : w.getEntities()){
					if(en instanceof Player){
						
					}
					else{
						en.remove();
					}
				}
			}
		}catch(Exception ex){
			
		}
		
		Bukkit.getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
		
		Bukkit.getServer().getPluginManager().registerEvents(new JoinEvent(this), this);
		Bukkit.getServer().getPluginManager().registerEvents(new QuitEvent(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new ClickEventENG(this), this);
		Bukkit.getServer().getPluginManager().registerEvents(new DropEvent(this), this);
		Bukkit.getServer().getPluginManager().registerEvents(new PlayerInteract(this), this);
		Bukkit.getServer().getPluginManager().registerEvents(new PlayerChat(this), this);
		Bukkit.getServer().getPluginManager().registerEvents(new FoodEvent(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new DamageEvent(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new EntityDamage(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new PickupEvent(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new PlayerMove(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new BlockChangeEvent(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new ProjectileHit(this), this);
		Bukkit.getServer().getPluginManager().registerEvents(new ExplodeEvent(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new EntityDeath(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new EntityInteractEvent(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new CommandPreprocessEvent(), this);
		
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
							BarAPI.setMessage(p, "§6§lOrbitMines§4§lNetwork §8| §6You have §6§l" + StorageManager.omt.get(p.getName()) + "§6 OMT", 10);
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
							BarAPI.setMessage(p, "§6§lOrbitMines§4§lNetwork §8| §6You have §6§l" + StorageManager.points.get(p.getName()) + "§6 VIP Points", 10);
							bi++;
						}
						else if(bi == 5){
							BarAPI.setMessage(p, "§6§lOrbitMines§4§lNetwork §8| §6You have §6§l" + StorageManager.votes.get(p.getName()) + "§6 Votes", 10);
							bi = 0;
						}
					}
					
					if(StorageManager.EnablePlayers.containsKey(p.getName())){
						if(StorageManager.EnablePlayers.get(p.getName()) == false){
							for(Player player : Bukkit.getOnlinePlayers()){
								p.hidePlayer(player);
							}
						}
					}
				}

			}
		}.runTaskTimer(this, 0, 20);
		
		new BukkitRunnable(){
			
			@SuppressWarnings("deprecation")
			@Override
			public void run(){
				
				try{
			        FallingBlock block1 = Bukkit.getWorld("Hub").spawnFallingBlock(new Location(Bukkit.getWorld("Hub"), -419, 14, 1054), Material.WATER, (byte) 0);
		            float x1 = (float) -0.03 + (float) (Math.random() * ((0.03 - -0.03) + 0.3));
		            float y1 = (float) -0.03 + (float) (Math.random() * ((0.03 - -0.03) + 0.3));
		            float z1 = (float) -0.03 + (float) (Math.random() * ((0.03 - -0.03) + 0.3));
		            
			        FallingBlock block2 = Bukkit.getWorld("Hub").spawnFallingBlock(new Location(Bukkit.getWorld("Hub"), -375, 14, 1054), Material.WATER, (byte) 0);
		            float x2 = (float) -0.03 + (float) (Math.random() * ((0.03 - -0.03) + 0.3));
		            float y2 = (float) -0.03 + (float) (Math.random() * ((0.03 - -0.03) + 0.3));
		            float z2 = (float) -0.03 + (float) (Math.random() * ((0.03 - -0.03) + 0.3));
		            
			        FallingBlock block3 = Bukkit.getWorld("Hub").spawnFallingBlock(new Location(Bukkit.getWorld("Hub"), -376, 14, 1011), Material.WATER, (byte) 0);
		            float x3 = (float) -0.03 + (float) (Math.random() * ((0.03 - -0.03) + 0.3));
		            float y3 = (float) -0.03 + (float) (Math.random() * ((0.03 - -0.03) + 0.3));
		            float z3 = (float) -0.03 + (float) (Math.random() * ((0.03 - -0.03) + 0.3));
		            
			        FallingBlock block4 = Bukkit.getWorld("Hub").spawnFallingBlock(new Location(Bukkit.getWorld("Hub"), -419, 14, 1011), Material.WATER, (byte) 0);
		            float x4 = (float) -0.03 + (float) (Math.random() * ((0.03 - -0.03) + 0.3));
		            float y4 = (float) -0.03 + (float) (Math.random() * ((0.03 - -0.03) + 0.3));
		            float z4 = (float) -0.03 + (float) (Math.random() * ((0.03 - -0.03) + 0.3));
					
					for(Player player : Bukkit.getOnlinePlayers()){
						((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutWorldParticles("splash", (float) block1.getLocation().getX(), (float) block1.getLocation().getY(), (float) block1.getLocation().getZ(), 1, 1, 1,(float) 0, 28));
						((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutWorldParticles("splash", (float) block2.getLocation().getX(), (float) block2.getLocation().getY(), (float) block2.getLocation().getZ(), 1, 1, 1,(float) 0, 28));
						((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutWorldParticles("splash", (float) block3.getLocation().getX(), (float) block3.getLocation().getY(), (float) block3.getLocation().getZ(), 1, 1, 1,(float) 0, 28));
						((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutWorldParticles("splash", (float) block4.getLocation().getX(), (float) block4.getLocation().getY(), (float) block4.getLocation().getZ(), 1, 1, 1,(float) 0, 28));
					}
		            
		            Random i = new Random();
		            int iInt;
		            
		            for(int iCount = 1; iCount <= 1; iCount++){
		            	iInt = 1 + i.nextInt(4);
		            	
		            	if(iInt == 1){
		            		block1.setVelocity(new Vector(x1 -0.2, y1, z1 -0.2));
		            		block2.setVelocity(new Vector(x2 -0.2, y2, z2 -0.2));
		            		block3.setVelocity(new Vector(x3 -0.2, y3, z3 -0.2));
		            		block4.setVelocity(new Vector(x4 -0.2, y4, z4 -0.2));
		            	}
		            	else if(iInt == 2){
		            		block1.setVelocity(new Vector(x1, y1, z1));
		            		block2.setVelocity(new Vector(x2, y2, z2));
		            		block3.setVelocity(new Vector(x3, y3, z3));
		            		block4.setVelocity(new Vector(x4, y4, z4));
		            	}
		            	else if(iInt == 3){
		            		block1.setVelocity(new Vector(x1 -0.2, y1, z1));
		            		block2.setVelocity(new Vector(x2 -0.2, y2, z2));
		            		block3.setVelocity(new Vector(x3 -0.2, y3, z3));
		            		block4.setVelocity(new Vector(x4 -0.2, y4, z4));
		            	}
		              	else if(iInt == 4){
		            		block1.setVelocity(new Vector(x1, y1, z1 -0.2));
		            		block2.setVelocity(new Vector(x2, y2, z2 -0.2));
		            		block3.setVelocity(new Vector(x3, y3, z3 -0.2));
		            		block4.setVelocity(new Vector(x4, y4, z4 -0.2));
		            	}
		            }
					
					for(Entity en : Bukkit.getWorld("Hub").getEntities()){
						if(en instanceof Item){
							if(((Item) en).getItemStack().getType() != Material.PAPER){
								en.remove();
							}
						}
					}
				}catch(Exception ex){
					
				}
			}
			
		}.runTaskTimer(this, 40, 2);
		
		new BukkitRunnable(){

			@Override
			public void run() {
				
				for(Player p : Bukkit.getOnlinePlayers()){
					
					ScoreboardManager sm = Bukkit.getScoreboardManager();
					Scoreboard b = sm.getNewScoreboard();

					Objective o = b.registerNewObjective("Test", "Test2");
					
					if(StorageManager.EnableScoreboard.get(p.getName()) != null && StorageManager.EnableScoreboard.get(p.getName()) == true){
						
						o.setDisplayName("§6§lOrbitMines");
						
						o.setDisplaySlot(DisplaySlot.SIDEBAR);
					
						Score score1 = o.getScore("");
						score1.setScore(12);
	
						Team omt = b.registerNewTeam("OMT");
						omt.setSuffix(" Tokens");
						@SuppressWarnings("deprecation")
						OfflinePlayer omt1 = getServer().getOfflinePlayer("§e§lOrbitMines");
						omt.addPlayer(omt1);
						
						Score score2 = o.getScore(omt1.getName());
						score2.setScore(11);
	
						if(StorageManager.omt.get(p.getName()) != null){
							Score score3 = o.getScore(" " + StorageManager.omt.get(p.getName()) + "  ");
							score3.setScore(10);
						}
						else{
							Score score3 = o.getScore(" " + "Loading..." + "  ");
							score3.setScore(10);
						}
					
						Score score4 = o.getScore(" ");
						score4.setScore(9);
	
						Score score5 = o.getScore("§b§lVIP Points");
						score5.setScore(8);
	
						if(StorageManager.points.get(p.getName()) != null){
							Score score6 = o.getScore(" " + StorageManager.points.get(p.getName()) + "");
							score6.setScore(7);
						}
						else{
							Score score6 = o.getScore(" " + "Loading..." + "");
							score6.setScore(7);
						}
	
						Score score7 = o.getScore("  ");
						score7.setScore(6);
						
						Team coin = b.registerNewTeam("Coins");
						coin.setSuffix(" Coins");
						@SuppressWarnings("deprecation")
						OfflinePlayer coins1 = getServer().getOfflinePlayer("§f§lMiniGame");
						coin.addPlayer(coins1);
						
						Score score8 = o.getScore(coins1.getName());
						score8.setScore(5);
						
						if(StorageManager.coins.get(p.getName()) != null){
							Score score9 = o.getScore(" " + StorageManager.coins.get(p.getName()) + " ");
							score9.setScore(4);
						}
						else{
							Score score9 = o.getScore(" " + "Loading..." + " ");
							score9.setScore(4);
						}
				
						Score score10 = o.getScore("   ");
						score10.setScore(3);
					}
					
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
					
					if(StorageManager.EnableScoreboard.get(p.getName()) != null && StorageManager.EnableScoreboard.get(p.getName()) == true){
						Score score11 = o.getScore("§c§lRank");
						score11.setScore(2);
						
						if(p.hasPermission("Rank.Owner")){
							Score score12 = o.getScore(" §4§lOwner");
							score12.setScore(1);
						}
						else if(p.hasPermission("Rank.Moderator")){
							Score score12 = o.getScore(" §b§lModerator");
							score12.setScore(1);
						}
						else if(p.hasPermission("Rank.Builder")){
							Score score12 = o.getScore(" §d§lBuilder");
							score12.setScore(1);
						}
						else if(p.hasPermission("Rank.Emerald")){
							Score score12 = o.getScore(" §a§lEmerald VIP");
							score12.setScore(1);
						}
						else if(p.hasPermission("Rank.Diamond")){
							Score score12 = o.getScore(" §b§lDiamond VIP");
							score12.setScore(1);
						}
						else if(p.hasPermission("Rank.Gold")){
							Score score12 = o.getScore(" §6§lGold VIP");
							score12.setScore(1);
						}
						else if(p.hasPermission("Rank.Iron")){
							Score score12 = o.getScore(" §7§lIron VIP");
							score12.setScore(1);
						}
						else{
							Score score12 = o.getScore(" §fNo Rank");
							score12.setScore(1);
						}
						
						Score score13 = o.getScore("    ");
						score13.setScore(0);
						
						o.setDisplayName("§6§lOrbitMines§4§lNetwork");
					}
					
					p.setScoreboard(b);
					
				}

			}
		}.runTaskTimer(this, 0, 100);
		
		new BukkitRunnable(){

			@Override
			public void run() {
				
				for(Player p : Bukkit.getOnlinePlayers()){
					if(StorageManager.ownerpets.containsKey(p)){
						UUID uuid = StorageManager.ownerpets.get(p);
						if(StorageManager.owners.containsKey(uuid)){
							for(World w : Bukkit.getWorlds()){
								for(Entity en : w.getEntities()){
									if(en.getUniqueId() == uuid){
										if(en instanceof LivingEntity){
											LivingEntity ent = (LivingEntity) en;
											Location l = StorageManager.owners.get(uuid).getLocation();
											
											if(ClickEventENG.getDistance(l, ent.getLocation()) < 20){
												ClickEventENG.navigate(ent, l, 1.2);
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
				
				DatabaseManager.openConnection();
				
			}
		}.runTaskAsynchronously(this);
		
		for(Player p : Bukkit.getOnlinePlayers()){
			joinEvent.loadAfter5Seconds(p);
		}
	}
	
	public void onDisable(){
		
		for(Player p : Bukkit.getOnlinePlayers()){
			if(StorageManager.FireworkPasses.get(p.getName()) != null){
				try {
					DatabaseManager.setFireworksPasses(p, StorageManager.FireworkPasses.get(p.getName()));
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String l, String[] a) {

		if(cmd.getName().equalsIgnoreCase("vote")){
			if(sender instanceof Player){
				//Player p = (Player) sender;
				//p.sendMessage("");
				//p.sendMessage("§6§lOrbitMines§b§lVote §8| §7Vote for §b§lRewards§7!");
				//p.sendMessage("§6§lOrbitMines§b§lVote §8| §7Reward in the §3§lHub§7 Server:");
				//p.sendMessage("§6§lOrbitMines§b§lVote §8| §7");
				//p.sendMessage("§6§lOrbitMines§b§lVote §8| §7  - §e§l1 OrbitMines Token");
				//p.sendMessage("§6§lOrbitMines§b§lVote §8| §7");
				//p.sendMessage("§6§lOrbitMines§b§lVote §8| §7Vote at §b§lwww.orbitmines.com");
				//p.sendMessage("§6§lOrbitMines§b§lVote §8| §7Your Total Votes this Month: §b§l" + StorageManager.votes.get(p.getName()));
				//p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 5, 1);
			}
		}
		if(cmd.getName().equalsIgnoreCase("votes")){
			
			if(sender.hasPermission("Rank.Owner")){
				
				if(a.length == 2){
					
					if(a[0].equalsIgnoreCase("add")){
						
						Player player = null;
						
						for(Player p : Bukkit.getOnlinePlayers()){
							
							if(p.getName().equals(a[1])){
								player = p;
							}
						}
						
						if(player != null){
							
							try {
								DatabaseManager.addVote(player);
							} catch (SQLException e) {
								e.printStackTrace();
							}
							
							sender.sendMessage("You gave " + a[1] + " a Vote!");
							VoteManager.registerVote(player);
							
						}
						else{
							sender.sendMessage("Player " + a[1] + " not Found!");
						}
						
					}
				}
				else{
					sender.sendMessage("Incorrect Use!");
				}
				
			}
			else{
				sender.sendMessage("§6§lOrbitMines§b§lVote §8| §7Your Total Votes this Month: §b§l" + StorageManager.votes.get(sender.getName()));
			}
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
								DatabaseManager.addVIPPoints(player, i);
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
								DatabaseManager.removeVIPPoints(player, i);
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
								DatabaseManager.addOMT(player, i);
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
								DatabaseManager.removeOMT(player, i);
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
				sender.sendMessage("§e§lOMT §8| §7Your OMT balance: §e§l" + StorageManager.omt.get(sender.getName()));
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
				if(PlayerManager.isLoaded(p)){
					p.playSound(p.getLocation(), Sound.CHEST_OPEN, 5, 1);
					p.openInventory(CosmeticPerks.getCosmeticPerks(p));
				}
				else{
					PlayerManager.warnPlayerNotLoaded(p);
				}
			}
		}

		return false;
	}
}
