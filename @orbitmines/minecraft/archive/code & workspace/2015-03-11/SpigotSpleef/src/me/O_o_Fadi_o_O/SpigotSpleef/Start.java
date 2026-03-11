package me.O_o_Fadi_o_O.SpigotSpleef;

import java.lang.reflect.Field;
import java.util.Map;

import me.O_o_Fadi_o_O.SpigotSpleef.NMS.CustomBlaze;
import me.O_o_Fadi_o_O.SpigotSpleef.events.BreakEvent;
import me.O_o_Fadi_o_O.SpigotSpleef.events.ClickEvent;
import me.O_o_Fadi_o_O.SpigotSpleef.events.CommandPreprocessEvent;
import me.O_o_Fadi_o_O.SpigotSpleef.events.DamageByEntityEvent;
import me.O_o_Fadi_o_O.SpigotSpleef.events.DamageEvent;
import me.O_o_Fadi_o_O.SpigotSpleef.events.EntityInteractEvent;
import me.O_o_Fadi_o_O.SpigotSpleef.events.InteractAtEntityEvent;
import me.O_o_Fadi_o_O.SpigotSpleef.events.InteractEvent;
import me.O_o_Fadi_o_O.SpigotSpleef.events.JoinEvent;
import me.O_o_Fadi_o_O.SpigotSpleef.events.MoveEvent;
import me.O_o_Fadi_o_O.SpigotSpleef.events.PlaceEvent;
import me.O_o_Fadi_o_O.SpigotSpleef.events.PlayerChat;
import me.O_o_Fadi_o_O.SpigotSpleef.events.QuitEvent;
import me.O_o_Fadi_o_O.SpigotSpleef.managers.ArenaManager;
import me.O_o_Fadi_o_O.SpigotSpleef.managers.ArenaSelectorManager;
import me.O_o_Fadi_o_O.SpigotSpleef.managers.ConfigManager;
import me.O_o_Fadi_o_O.SpigotSpleef.managers.KitManager;
import me.O_o_Fadi_o_O.SpigotSpleef.managers.MapManager;
import me.O_o_Fadi_o_O.SpigotSpleef.managers.MessageManager;
import me.O_o_Fadi_o_O.SpigotSpleef.managers.PlayerDataManager;
import me.O_o_Fadi_o_O.SpigotSpleef.managers.StorageManager;
import me.O_o_Fadi_o_O.SpigotSpleef.utils.Arena;
import me.O_o_Fadi_o_O.SpigotSpleef.utils.NPC;
import me.O_o_Fadi_o_O.SpigotSpleef.utils.NPCType;
import me.O_o_Fadi_o_O.SpigotSpleef.utils.SpleefPlayer;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Start extends JavaPlugin {

	public static Start plugin;
	
	public void onEnable(){
		plugin = this;
		registerNMS();
		
		ConfigManager.setup();
		
		registerEvents();
		
		loadConfigData();
		MapManager.loadMaps();
		ArenaManager.loadArenas();
		
		for(Player p : Bukkit.getOnlinePlayers()){
			PlayerDataManager.loadPlayerData(p);
		}
	}
	
	public void onDisable(){
		removeAllNPCs();
		
		for(Arena arena : StorageManager.arenas){
			for(SpleefPlayer sp : arena.getPlayers()){
				sp.teleportToLobby();
			}
		}
	}
	
	public static void loadConfigData(){
		MessageManager.loadMessages();
		KitManager.loadKits();
		ArenaSelectorManager.loadArenaSelector();
		//TODO LOAD MESSAGES
		//TODO LOAD BLOCKS
		//TODO LOAD SETTINGS
		//TODO LOAD ABILITY MOTIVIERS
	}
	
	public static Start getInstance(){
		return plugin;
	}
	
	private void registerEvents(){
		getServer().getPluginManager().registerEvents(new BreakEvent(), this);
		getServer().getPluginManager().registerEvents(new ClickEvent(), this);
		getServer().getPluginManager().registerEvents(new CommandPreprocessEvent(), this);
		getServer().getPluginManager().registerEvents(new DamageByEntityEvent(), this);
		getServer().getPluginManager().registerEvents(new DamageEvent(), this);
		getServer().getPluginManager().registerEvents(new EntityInteractEvent(), this);
		getServer().getPluginManager().registerEvents(new InteractAtEntityEvent(), this);
		getServer().getPluginManager().registerEvents(new InteractEvent(), this);
		getServer().getPluginManager().registerEvents(new JoinEvent(), this);
		getServer().getPluginManager().registerEvents(new MoveEvent(), this);
		getServer().getPluginManager().registerEvents(new PlaceEvent(), this);
		getServer().getPluginManager().registerEvents(new PlayerChat(), this);
		getServer().getPluginManager().registerEvents(new QuitEvent(), this);
	}
	
	public void sendHelpMessage(CommandSender sender){
		sender.sendMessage("");
		sender.sendMessage("§6§lSpigot§7Spleef §7- §6" + StorageManager.version);
		sender.sendMessage(" §6/spleef help §7- Show the Help Menu.");
		sender.sendMessage(" §6/spleef addAS §7- Add an Arena Selector at your location.");
		sender.sendMessage(" §6/spleef removeAS §7- Remove the closest Arena Selector.");
		sender.sendMessage(" §6/spleef setupmap <map-id> §7- Enter the Map Setup Mode.");
		sender.sendMessage(" §6/spleef setuparena <arena-id> §7- Enter the Arena Setup Mode.");
		sender.sendMessage(" §6/spleef status <player> §7- Show the Statistics of a Player.");
		sender.sendMessage(" §6/spleef tokens show <player> §7- Show Tokens.");
		sender.sendMessage(" §6/spleef tokens add <player> <amount> §7- Add Tokens.");
		sender.sendMessage(" §6/spleef tokens remove <player> <amount> §7- Remove Tokens.");
	}
	
	public void sendIncorrectUsage(CommandSender sender, String incorrect){
		sender.sendMessage("§6§lSpigot§7§lSpleef §7| §7Use " + incorrect + "§7.");
	}
	public void sendNotOnline(CommandSender sender, String incorrect){
		sender.sendMessage("§6§lSpigot§7§lSpleef §7| §7Player §6" + incorrect + "§7 isn't §aOnline§7!");
	}
	public void sendNotNumber(CommandSender sender, String incorrect){
		sender.sendMessage("§6§lSpigot§7§lSpleef §7| §6" + incorrect + "§7 isn't a number!");
	}
	
	public static boolean hasPermission(CommandSender sender, String... perms){
		for(String perm : perms){
			if(sender.hasPermission("SpigotSpleef." + perm)){
				return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String l, String[] a) {

		if(cmd.getName().equalsIgnoreCase("spleef")){
			if(a.length > 0){
				if(a[0].equalsIgnoreCase("addas") || a[0].equalsIgnoreCase("addarenaselector")){
					if(hasPermission(sender, "arenaselector.add", "arenaselector.*", "*")){
						if(sender instanceof Player){
							Player p = (Player) sender;
								
							NPC npc = new NPC(NPCType.ARENA_SELECTOR, "&6&lSpigot&7&lSpleef", null, null, null);
							npc.newEntity(EntityType.BLAZE, p.getLocation(), "§6§lSpigot§7§lSpleef");
							StorageManager.npcs.add(npc);
							ArenaManager.saveArenaSelectors();
							
							p.sendMessage("§6§lSpigot§7§lSpleef §7| Spawned an §6Arena Selector§7!");	
						}
						else{
							sender.sendMessage("§6§lSpigot§7§lSpleef §7| You cannot setup maps through the Console!");
						}
					}
					else{
						//TODO: perm
					}
				}
				if(a[0].equalsIgnoreCase("removeas") || a[0].equalsIgnoreCase("removearenaselector")){
					if(hasPermission(sender, "arenaselector.remove", "arenaselector.*", "*")){
						if(sender instanceof Player){
							Player p = (Player) sender;
							
							NPC closest = null;
							double distance = 100;
							
							for(NPC npc : StorageManager.npcs){
								if(npc.getNPCType() == NPCType.ARENA_SELECTOR && npc.getEntity().getWorld().getName().equals(p.getWorld().getName())){
									double distance2 = npc.getEntity().getLocation().distance(p.getLocation());
									if(distance2 < 10){
										if(distance2 < distance){
											closest = npc;
											distance = distance2;
										}
									}
								}
							}
							
							if(closest != null){
								closest.getEntity().remove();
								closest.getItem().remove();
								StorageManager.npcs.remove(closest);
								ArenaManager.saveArenaSelectors();
								
								p.sendMessage("§6§lSpigot§7§lSpleef §7| Removed an §6Arena Selector§7!");
							}
							else{
								p.sendMessage("§6§lSpigot§7§lSpleef §7| Cannot find any nearby §6Arena Selectors§7!");
							}
						}
						else{
							sender.sendMessage("§6§lSpigot§7§lSpleef §7| You cannot setup maps through the Console!");
						}
					}
					else{
						//TODO: perm
					}
				}
				if(a[0].equalsIgnoreCase("setupmap")){
					if(hasPermission(sender, "setupmap", "*")){
						if(sender instanceof Player){
							Player p = (Player) sender;
							
							if(a.length == 2){
								try{
									int mapid = Integer.parseInt(a[1]);
									
									MapManager.enterSetupMode(p, mapid);
								}catch(NumberFormatException ex){
									sendIncorrectUsage(sender, "§6/spleef setupmap <map-id>");
								}
							}
							else{
								sendIncorrectUsage(sender, "§6/spleef setupmap <map-id>");
							}
						}
						else{
							sender.sendMessage("§6§lSpigot§7§lSpleef §7| You cannot setup maps through the Console!");
						}
					}
					else{
						//TODO no perm
					}
				}
				else if(a[0].equalsIgnoreCase("setuparena")){
					if(hasPermission(sender, "setuparena", "*")){
						if(sender instanceof Player){
							Player p = (Player) sender;
							
							if(a.length == 2){
								try{
									int arenaid = Integer.parseInt(a[1]);
									
									ArenaManager.enterSetupMode(p, arenaid);
								}catch(NumberFormatException ex){
									sendIncorrectUsage(sender, "§6/spleef setuparena <arena-id>");
								}
							}
							else{
								sendIncorrectUsage(sender, "§6/spleef setuparena <arena-id>");
							}
						}
						else{
							sender.sendMessage("§6§lSpigot§7§lSpleef §7| You cannot setup arenas through the Console!");
						}
					}
					else{
						//TODO no perm
					}
				}
				else if(a[0].equalsIgnoreCase("status")){
					//TODO: show stats
				}
				else if(a[0].equalsIgnoreCase("tokens")){
					if(a.length > 1){
						if(a[1].equalsIgnoreCase("show")){
							if(hasPermission(sender, "tokens.show", "tokens.*", "*")){
								if(a.length == 3){
									Player p = null;
									
									for(Player player : Bukkit.getOnlinePlayers()){
										if(player.getName().equalsIgnoreCase(a[2])){
											p = player;
										}
									}
									
									if(p != null){
										sender.sendMessage("§6§lSpigot§7§lSpleef §7| §6" + p.getName() + "§7 has §6" + StorageManager.spleefplayer.get(p).getTokens() + " Tokens§7.");
									}
									else{
										sendNotOnline(sender, a[2]);
									}
								}
								else{
									sendIncorrectUsage(sender, "§6/spleef tokens show <player>");
								}
							}
							else{
								//TODO: no perm
							}
						}
						else if(a[1].equalsIgnoreCase("add")){
							if(hasPermission(sender, "tokens.add", "tokens.*", "*")){
								if(a.length == 4){
									Player p = null;
									
									for(Player player : Bukkit.getOnlinePlayers()){
										if(player.getName().equalsIgnoreCase(a[2])){
											p = player;
										}
									}
									
									if(p != null){
										try{
											int amount = Integer.parseInt(a[3].replace("-", "").replace("+", "").replace("+", "").replace("/", ""));	
											
											SpleefPlayer sp = StorageManager.spleefplayer.get(p);
											sp.addTokens(amount);
											sender.sendMessage("§6§lSpigot§7§lSpleef §7| You gave §6" + p.getName() + " §6" + amount + " Tokens§7!");
											p.sendMessage("§6§lSpigot§7§lSpleef §7| You received §6" + amount + " Tokens§7!");
											
										}catch(NumberFormatException ex){
											sendNotNumber(sender, a[3]);
										}
									}
									else{
										sendNotOnline(sender, a[2]);
									}
								}
								else{
									sendIncorrectUsage(sender, "§6/spleef tokens add <player> <amount>");
								}
							}
							else{
								//TODO: no perm
							}
						}
						else if(a[1].equalsIgnoreCase("remove")){
							if(hasPermission(sender, "tokens.remove", "tokens.*", "*")){
								if(a.length == 4){
									Player p = null;
									
									for(Player player : Bukkit.getOnlinePlayers()){
										if(player.getName().equalsIgnoreCase(a[2])){
											p = player;
										}
									}
									
									if(p != null){
										try{
											int amount = Integer.parseInt(a[3].replace("-", "").replace("+", "").replace("+", "").replace("/", ""));	
											
											SpleefPlayer sp = StorageManager.spleefplayer.get(p);
											sp.addTokens(amount);
											sender.sendMessage("§6§lSpigot§7§lSpleef §7| You removed §6" + amount + " Tokens§7 from §6" + p.getName() + "§7!");
											p.sendMessage("§6§lSpigot§7§lSpleef §7| §6" + amount + " Tokens§7 were taken from you!");
											
										}catch(NumberFormatException ex){
											sendNotNumber(sender, a[3]);
										}
									}
									else{
										sendNotOnline(sender, a[2]);
									}
								}
								else{
									sendIncorrectUsage(sender, "§6/spleef tokens remoev <player> <amount>");
								}
							}
							else{
								//TODO: no perm
							}
						}
						else{
							sendIncorrectUsage(sender, "§6/spleef tokens add|remove|show <player> (amount)");
						}
					}
					else{
						sendIncorrectUsage(sender, "§6/spleef tokens add|remove|show <player> (amount)");
					}
				}
			}
			else{
				sendHelpMessage(sender);
			}
		}
		
		return false;
	}
	
	private void registerNMS(){

		//addCustomEntity(PetSquid.class, "PetSquid", 94);
		//addCustomEntity(PetSpider.class, "PetSpider", 52);
		//addCustomEntity(PetMushroomCow.class, "PetMushroomCow", 96);
		//addCustomEntity(PetMagmaCube.class, "PetMagmaCube", 62);
		//addCustomEntity(PetPig.class, "PetPig", 90);
		//addCustomEntity(PetSlime.class, "PetSlime", 55);
		//addCustomEntity(PetWolf.class, "PetWolf", 95);
		//addCustomEntity(PetCow.class, "PetCow", 92);
		//addCustomEntity(PetSheep.class, "PetSheep", 91);
		//addCustomEntity(PetSilverfish.class, "PetSilverfish", 60);
		//addCustomEntity(PetOcelot.class, "PetHorse", 98);
		//addCustomEntity(PetCreeper.class, "PetCreeper", 50);
		//addCustomEntity(PetChicken.class, "PetChicken", 93);
		
		//addCustomEntity(CustomVillager.class, "CustomVillager", 120);
		//addCustomEntity(CustomCreeper.class, "CustomCreeper", 50);
		//addCustomEntity(CustomSkeleton.class, "CustomSkeleton", 51);
		addCustomEntity(CustomBlaze.class, "CustomBlaze", 61);
	}
	
	private void removeAllNPCs(){
		for(NPC npc : StorageManager.npcs){
			npc.getEntity().remove();
			npc.getItem().remove();
		}
		StorageManager.npcs.clear();
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
	    catch(Exception e){}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected static void addCustomEntity(Class entityClass, String name, int id){
	    if (mapStringToClassField == null || mapStringToIdField == null || mapClassToStringField == null || mapClassToIdField == null){
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
	        catch(Exception e){}
	    }
	}
}
