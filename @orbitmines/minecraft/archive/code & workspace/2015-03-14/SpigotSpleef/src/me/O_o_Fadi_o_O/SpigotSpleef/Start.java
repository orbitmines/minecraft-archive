package me.O_o_Fadi_o_O.SpigotSpleef;

import java.lang.reflect.Field;
import java.util.Map;

import me.O_o_Fadi_o_O.SpigotSpleef.NMS.CustomBat;
import me.O_o_Fadi_o_O.SpigotSpleef.NMS.CustomBlaze;
import me.O_o_Fadi_o_O.SpigotSpleef.NMS.CustomCaveSpider;
import me.O_o_Fadi_o_O.SpigotSpleef.NMS.CustomChicken;
import me.O_o_Fadi_o_O.SpigotSpleef.NMS.CustomCow;
import me.O_o_Fadi_o_O.SpigotSpleef.NMS.CustomCreeper;
import me.O_o_Fadi_o_O.SpigotSpleef.NMS.CustomEnderman;
import me.O_o_Fadi_o_O.SpigotSpleef.NMS.CustomEndermite;
import me.O_o_Fadi_o_O.SpigotSpleef.NMS.CustomGhast;
import me.O_o_Fadi_o_O.SpigotSpleef.NMS.CustomGuardian;
import me.O_o_Fadi_o_O.SpigotSpleef.NMS.CustomHorse;
import me.O_o_Fadi_o_O.SpigotSpleef.NMS.CustomIronGolem;
import me.O_o_Fadi_o_O.SpigotSpleef.NMS.CustomMagmaCube;
import me.O_o_Fadi_o_O.SpigotSpleef.NMS.CustomMushroomCow;
import me.O_o_Fadi_o_O.SpigotSpleef.NMS.CustomOcelot;
import me.O_o_Fadi_o_O.SpigotSpleef.NMS.CustomPig;
import me.O_o_Fadi_o_O.SpigotSpleef.NMS.CustomPigZombie;
import me.O_o_Fadi_o_O.SpigotSpleef.NMS.CustomRabbit;
import me.O_o_Fadi_o_O.SpigotSpleef.NMS.CustomSheep;
import me.O_o_Fadi_o_O.SpigotSpleef.NMS.CustomSilverfish;
import me.O_o_Fadi_o_O.SpigotSpleef.NMS.CustomSkeleton;
import me.O_o_Fadi_o_O.SpigotSpleef.NMS.CustomSlime;
import me.O_o_Fadi_o_O.SpigotSpleef.NMS.CustomSnowman;
import me.O_o_Fadi_o_O.SpigotSpleef.NMS.CustomSpider;
import me.O_o_Fadi_o_O.SpigotSpleef.NMS.CustomSquid;
import me.O_o_Fadi_o_O.SpigotSpleef.NMS.CustomVillager;
import me.O_o_Fadi_o_O.SpigotSpleef.NMS.CustomWitch;
import me.O_o_Fadi_o_O.SpigotSpleef.NMS.CustomWither;
import me.O_o_Fadi_o_O.SpigotSpleef.NMS.CustomWolf;
import me.O_o_Fadi_o_O.SpigotSpleef.NMS.CustomZombie;
import me.O_o_Fadi_o_O.SpigotSpleef.events.BreakEvent;
import me.O_o_Fadi_o_O.SpigotSpleef.events.ClickEvent;
import me.O_o_Fadi_o_O.SpigotSpleef.events.CommandPreprocessEvent;
import me.O_o_Fadi_o_O.SpigotSpleef.events.DamageByEntityEvent;
import me.O_o_Fadi_o_O.SpigotSpleef.events.DamageEvent;
import me.O_o_Fadi_o_O.SpigotSpleef.events.DropEvent;
import me.O_o_Fadi_o_O.SpigotSpleef.events.EntityInteractEvent;
import me.O_o_Fadi_o_O.SpigotSpleef.events.ExplodeEvent;
import me.O_o_Fadi_o_O.SpigotSpleef.events.FoodEvent;
import me.O_o_Fadi_o_O.SpigotSpleef.events.InteractAtEntityEvent;
import me.O_o_Fadi_o_O.SpigotSpleef.events.InteractEvent;
import me.O_o_Fadi_o_O.SpigotSpleef.events.JoinEvent;
import me.O_o_Fadi_o_O.SpigotSpleef.events.MoveEvent;
import me.O_o_Fadi_o_O.SpigotSpleef.events.PickupEvent;
import me.O_o_Fadi_o_O.SpigotSpleef.events.PlaceEvent;
import me.O_o_Fadi_o_O.SpigotSpleef.events.PlayerChat;
import me.O_o_Fadi_o_O.SpigotSpleef.events.ProjHitEvent;
import me.O_o_Fadi_o_O.SpigotSpleef.events.ProjShootEvent;
import me.O_o_Fadi_o_O.SpigotSpleef.events.QuitEvent;
import me.O_o_Fadi_o_O.SpigotSpleef.managers.AbilityManager;
import me.O_o_Fadi_o_O.SpigotSpleef.managers.ArenaManager;
import me.O_o_Fadi_o_O.SpigotSpleef.managers.ArenaSelectorManager;
import me.O_o_Fadi_o_O.SpigotSpleef.managers.BlockManager;
import me.O_o_Fadi_o_O.SpigotSpleef.managers.ConfigManager;
import me.O_o_Fadi_o_O.SpigotSpleef.managers.KitManager;
import me.O_o_Fadi_o_O.SpigotSpleef.managers.LobbyManager;
import me.O_o_Fadi_o_O.SpigotSpleef.managers.MapManager;
import me.O_o_Fadi_o_O.SpigotSpleef.managers.MessageManager;
import me.O_o_Fadi_o_O.SpigotSpleef.managers.NPCManager;
import me.O_o_Fadi_o_O.SpigotSpleef.managers.PlayerDataManager;
import me.O_o_Fadi_o_O.SpigotSpleef.managers.ScoreboardManager;
import me.O_o_Fadi_o_O.SpigotSpleef.managers.SettingsManager;
import me.O_o_Fadi_o_O.SpigotSpleef.managers.SignManager;
import me.O_o_Fadi_o_O.SpigotSpleef.managers.SpectatorManager;
import me.O_o_Fadi_o_O.SpigotSpleef.managers.StorageManager;
import me.O_o_Fadi_o_O.SpigotSpleef.utils.Arena;
import me.O_o_Fadi_o_O.SpigotSpleef.utils.Message;
import me.O_o_Fadi_o_O.SpigotSpleef.utils.MessageName;
import me.O_o_Fadi_o_O.SpigotSpleef.utils.NPC;
import me.O_o_Fadi_o_O.SpigotSpleef.utils.NPCType;
import me.O_o_Fadi_o_O.SpigotSpleef.utils.SpleefPlayer;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class Start extends JavaPlugin {

	public static Start plugin;
	
	public void onEnable(){
		plugin = this;
		registerNMS();
		
		ConfigManager.setup();
		
		registerEvents();
		
		new BukkitRunnable(){
			public void run(){
				loadConfigData();
				loadMessagesData();
				loadMapsData();
				loadArenasData();
				
				for(Player p : Bukkit.getOnlinePlayers()){
					PlayerDataManager.loadPlayerData(p);
				}
				
				clearNPCs();
			}
		}.runTaskLater(this, 40);
	}
	
	public void onDisable(){
		removeAllNPCs();
		
		for(Arena arena : StorageManager.arenas){
			for(SpleefPlayer sp : arena.getPlayers()){
				sp.clearInventory();
				sp.teleportToLobby();
			}
			arena.resetScoreboard();
			arena.resetMap();
		}
	}
	
	public static void loadMessagesData(){
		MessageManager.loadMessages();
	}
	
	public static void loadMapsData(){
		MapManager.loadMaps();
	}
	
	public static void loadArenasData(){
		ArenaManager.loadArenas();
	}
	
	public static void loadConfigData(){
		SettingsManager.loadSettings();
		SignManager.loadSigns();
		ScoreboardManager.loadScoreboards();
		LobbyManager.loadLobbyInventory();
		SpectatorManager.loadSpectatorInventory();
		ArenaSelectorManager.loadArenaSelector();
		//TODO KIT SELECTOR
		KitManager.loadKits();
		AbilityManager.loadAbilityMotiviers();
		BlockManager.loadBlocks();
	}
	
	public static Start getInstance(){
		return plugin;
	}
	
	private void clearNPCs(){
		for(NPC npc : StorageManager.npcs){
			for(Entity en : npc.getEntity().getNearbyEntities(0.01, 0.01, 0.01)){
				if(!(en instanceof Player)){
					en.remove();
				}
			}
		}
	}
	
	private void registerEvents(){
		getServer().getPluginManager().registerEvents(new BreakEvent(), this);
		getServer().getPluginManager().registerEvents(new ClickEvent(), this);
		getServer().getPluginManager().registerEvents(new CommandPreprocessEvent(), this);
		getServer().getPluginManager().registerEvents(new DamageByEntityEvent(), this);
		getServer().getPluginManager().registerEvents(new DamageEvent(), this);
		getServer().getPluginManager().registerEvents(new DropEvent(), this);
		getServer().getPluginManager().registerEvents(new EntityInteractEvent(), this);
		getServer().getPluginManager().registerEvents(new ExplodeEvent(), this);
		getServer().getPluginManager().registerEvents(new FoodEvent(), this);
		getServer().getPluginManager().registerEvents(new InteractAtEntityEvent(), this);
		getServer().getPluginManager().registerEvents(new InteractEvent(), this);
		getServer().getPluginManager().registerEvents(new JoinEvent(), this);
		getServer().getPluginManager().registerEvents(new MoveEvent(), this);
		getServer().getPluginManager().registerEvents(new PickupEvent(), this);
		getServer().getPluginManager().registerEvents(new PlaceEvent(), this);
		getServer().getPluginManager().registerEvents(new PlayerChat(), this);
		getServer().getPluginManager().registerEvents(new ProjHitEvent(), this);
		getServer().getPluginManager().registerEvents(new ProjShootEvent(), this);
		getServer().getPluginManager().registerEvents(new QuitEvent(), this);
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
				if(a[0].equalsIgnoreCase("setlobby")){
					if(hasPermission(sender, "setlobby", "*")){
						if(sender instanceof Player){
							Player p = (Player) sender;
								
							ArenaManager.setMiniGamesLobby(p);
							
							Message m = Message.getMessage(MessageName.SET_LOBBY);
							m.replace("&", "§");
							m.send(sender);
						}
						else{
							Message m = Message.getMessage(MessageName.THROUGH_CONSOLE);
							m.replace("&", "§");
							m.send(sender);
						}
					}
					else{
						Message m = Message.getMessage(MessageName.NO_PERM_SET_LOBBY);
						m.replace("&", "§");
						m.send(sender);
					}
				}
				else if(a[0].equalsIgnoreCase("addas") || a[0].equalsIgnoreCase("addarenaselector")){
					if(hasPermission(sender, "arenaselector.add", "arenaselector.*", "*")){
						if(sender instanceof Player){
							Player p = (Player) sender;
								
							NPC npc = new NPC(NPCType.ARENA_SELECTOR, EntityType.BLAZE, "&6&lSpigot&7&lSpleef", null, null, null);
							npc.newEntity(EntityType.BLAZE, p.getLocation(), "§6§lSpigot§7§lSpleef");
							StorageManager.npcs.add(npc);
							ArenaManager.saveArenaSelectors();
							

							Message m = Message.getMessage(MessageName.SPAWN_ARENA_SELECTOR);
							m.replace("&", "§");
							m.send(sender);
						}
						else{
							Message m = Message.getMessage(MessageName.THROUGH_CONSOLE);
							m.replace("&", "§");
							m.send(sender);
						}
					}
					else{
						Message m = Message.getMessage(MessageName.NO_PERM_ADD_ARENA_SELECTOR);
						m.replace("&", "§");
						m.send(sender);
					}
				}
				else if(a[0].equalsIgnoreCase("editas") || a[0].equalsIgnoreCase("editarenaselector")){
					if(hasPermission(sender, "arenaselector.edit", "arenaselector.*", "*")){
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
								NPCManager.openNPCInventory(p, closest);
							}
							else{
								Message m = Message.getMessage(MessageName.NO_NEARBY_ARENA_SELECTOR);
								m.replace("&", "§");
								m.send(sender);
							}
						}
						else{
							Message m = Message.getMessage(MessageName.THROUGH_CONSOLE);
							m.replace("&", "§");
							m.send(sender);
						}
					}
					else{
						Message m = Message.getMessage(MessageName.NO_PERM_EDIT_ARENA_SELECTOR);
						m.replace("&", "§");
						m.send(sender);
					}
				}
				else if(a[0].equalsIgnoreCase("removeas") || a[0].equalsIgnoreCase("removearenaselector")){
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

								Message m = Message.getMessage(MessageName.REMOVE_ARENA_SELECTOR);
								m.replace("&", "§");
								m.send(sender);
							}
							else{
								Message m = Message.getMessage(MessageName.NO_NEARBY_ARENA_SELECTOR);
								m.replace("&", "§");
								m.send(sender);
							}
						}
						else{
							Message m = Message.getMessage(MessageName.THROUGH_CONSOLE);
							m.replace("&", "§");
							m.send(sender);
						}
					}
					else{
						Message m = Message.getMessage(MessageName.NO_PERM_REMOVE_ARENA_SELECTOR);
						m.replace("&", "§");
						m.send(sender);
					}
				}
				else if(a[0].equalsIgnoreCase("setupmap")){
					if(hasPermission(sender, "setupmap", "*")){
						if(sender instanceof Player){
							Player p = (Player) sender;
							
							if(a.length == 2){
								try{
									int mapid = Integer.parseInt(a[1]);
									
									MapManager.enterSetupMode(p, mapid);
								}catch(NumberFormatException ex){
									Message m = Message.getMessage(MessageName.NOT_A_NUMBER);
									m.replace("&", "§");
									m.replace("%arg%", a[1]);
									m.send(sender);
								}
							}
							else{
								Message m = Message.getMessage(MessageName.WRONG_USAGE_SETUP_MAP);
								m.replace("&", "§");
								m.send(sender);
							}
						}
						else{
							Message m = Message.getMessage(MessageName.THROUGH_CONSOLE);
							m.replace("&", "§");
							m.send(sender);
						}
					}
					else{
						Message m = Message.getMessage(MessageName.NO_PERM_SETUP_MAP);
						m.replace("&", "§");
						m.send(sender);
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
									Message m = Message.getMessage(MessageName.NOT_A_NUMBER);
									m.replace("&", "§");
									m.replace("%arg%", a[1]);
									m.send(sender);
								}
							}
							else{
								Message m = Message.getMessage(MessageName.WRONG_USAGE_SETUP_ARENA);
								m.replace("&", "§");
								m.send(sender);
							}
						}
						else{
							Message m = Message.getMessage(MessageName.THROUGH_CONSOLE);
							m.replace("&", "§");
							m.send(sender);
						}
					}
					else{
						Message m = Message.getMessage(MessageName.NO_PERM_SETUP_ARENA);
						m.replace("&", "§");
						m.send(sender);
					}
				}
				else if(a[0].equalsIgnoreCase("stats")){
					if(hasPermission(sender, "stats", "*")){
						if(a.length == 2){
							Player p = null;
							
							for(Player player : Bukkit.getOnlinePlayers()){
								if(player.getName().equalsIgnoreCase(a[1])){
									p = player;
								}
							}
							
							if(p != null){
								SpleefPlayer sp = StorageManager.spleefplayer.get(p);
								
								Message m = Message.getMessage(MessageName.STATS);
								m.replace("&", "§");
								m.replace("%player%", p.getName());
								m.replace("%won%", "" + sp.getWins());
								m.replace("%lost%", "" + sp.getLoses());
								m.replace("%kills%", "" + sp.getKills());
								m.replace("%tokens%", "" + sp.getTokens());
								m.send(sender);
							}
							else{
								Message m = Message.getMessage(MessageName.NOT_ONLINE);
								m.replace("&", "§");
								m.replace("%player%", a[1]);
								m.send(sender);
							}
						}
						else{
							Message m = Message.getMessage(MessageName.WRONG_USAGE_STATS);
							m.replace("&", "§");
							m.send(sender);
						}
					}
					else{
						Message m = Message.getMessage(MessageName.NO_PERM_STATS);
						m.replace("&", "§");
						m.send(sender);
					}
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
										Message m = Message.getMessage(MessageName.TOKENS_SHOW);
										m.replace("&", "§");
										m.replace("%player%", p.getName());
										m.replace("%tokens%", "" + StorageManager.spleefplayer.get(p).getTokens());
										m.send(sender);
									}
									else{
										Message m = Message.getMessage(MessageName.NOT_ONLINE);
										m.replace("&", "§");
										m.replace("%player%", a[2]);
										m.send(sender);
									}
								}
								else{
									Message m = Message.getMessage(MessageName.WRONG_USAGE_TOKENS_SHOW);
									m.replace("&", "§");
									m.send(sender);
								}
							}
							else{
								Message m = Message.getMessage(MessageName.NO_PERM_TOKENS_SHOW);
								m.replace("&", "§");
								m.send(sender);
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
											
											{
												Message m = Message.getMessage(MessageName.TOKENS_ADD_TO_SENDER);
												m.replace("&", "§");
												m.replace("%player%", p.getName());
												m.replace("%amount%", "" + amount);
												m.send(sender);
											}

											Message m = Message.getMessage(MessageName.TOKENS_ADD_TO_RECEIVER);
											m.replace("&", "§");
											m.replace("%amount%", "" + amount);
											m.send(p);
											
										}catch(NumberFormatException ex){
											Message m = Message.getMessage(MessageName.NOT_A_NUMBER);
											m.replace("&", "§");
											m.replace("%arg%", a[3]);
											m.send(sender);
										}
									}
									else{
										Message m = Message.getMessage(MessageName.NOT_ONLINE);
										m.replace("&", "§");
										m.replace("%player%", a[2]);
										m.send(sender);
									}
								}
								else{
									Message m = Message.getMessage(MessageName.WRONG_USAGE_TOKENS_ADD);
									m.replace("&", "§");
									m.send(sender);
								}
							}
							else{
								Message m = Message.getMessage(MessageName.NO_PERM_TOKENS_ADD);
								m.replace("&", "§");
								m.send(sender);
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
											sp.removeTokens(amount);
											
											{
												Message m = Message.getMessage(MessageName.TOKENS_REMOVE_TO_SENDER);
												m.replace("&", "§");
												m.replace("%player%", p.getName());
												m.replace("%amount%", "" + amount);
												m.send(sender);
											}

											Message m = Message.getMessage(MessageName.TOKENS_REMOVE_TO_RECEIVER);
											m.replace("&", "§");
											m.replace("%amount%", "" + amount);
											m.send(p);
											
										}catch(NumberFormatException ex){
											Message m = Message.getMessage(MessageName.NOT_A_NUMBER);
											m.replace("&", "§");
											m.replace("%arg%", a[3]);
											m.send(sender);
										}
									}
									else{
										Message m = Message.getMessage(MessageName.NOT_ONLINE);
										m.replace("&", "§");
										m.replace("%player%", a[2]);
										m.send(sender);
									}
								}
								else{
									Message m = Message.getMessage(MessageName.WRONG_USAGE_TOKENS_REMOVE);
									m.replace("&", "§");
									m.send(sender);
								}
							}
							else{
								Message m = Message.getMessage(MessageName.NO_PERM_TOKENS_REMOVE);
								m.replace("&", "§");
								m.send(sender);
							}
						}
						else{
							if(hasPermission(sender, "tokens.show", "tokens.add", "tokens.remove", "tokens.*", "*")){
								Message m = Message.getMessage(MessageName.WRONG_USAGE_TOKENS);
								m.replace("&", "§");
								m.send(sender);
							}
							else{
								Message m = Message.getMessage(MessageName.NO_PERM_TOKENS_ADD);
								m.replace("&", "§");
								m.send(sender);
							}
						}
					}
					else{
						if(hasPermission(sender, "tokens.show", "tokens.add", "tokens.remove", "tokens.*", "*")){
							Message m = Message.getMessage(MessageName.WRONG_USAGE_TOKENS);
							m.replace("&", "§");
							m.send(sender);
						}
						else{
							Message m = Message.getMessage(MessageName.NO_PERM_TOKENS_ADD);
							m.replace("&", "§");
							m.send(sender);
						}
					}
				}
				else{
					if(hasPermission(sender, "help", "*")){
						Message m = Message.getMessage(MessageName.HELP_MESSAGE);
						m.replace("&", "§");
						m.replace("%version%", StorageManager.version);
						m.send(sender);
					}
					else{
						Message m = Message.getMessage(MessageName.NO_PERM_HELP);
						m.replace("&", "§");
						m.send(sender);
					}
				}
			}
			else{
				if(hasPermission(sender, "help", "*")){
					Message m = Message.getMessage(MessageName.HELP_MESSAGE);
					m.replace("&", "§");
					m.replace("%version%", StorageManager.version);
					m.send(sender);
				}
				else{
					Message m = Message.getMessage(MessageName.NO_PERM_HELP);
					m.replace("&", "§");
					m.send(sender);
				}
			}
		}
		
		return false;
	}
	
	private void registerNMS(){
		addCustomEntity(CustomBat.class, "CustomBat", 65);
		addCustomEntity(CustomBlaze.class, "CustomBlaze", 61);
		addCustomEntity(CustomCaveSpider.class, "CustomCaveSpider", 59);
		addCustomEntity(CustomChicken.class, "CustomChicken", 93);
		addCustomEntity(CustomCow.class, "CustomCow", 92);
		addCustomEntity(CustomCreeper.class, "CustomCreeper", 50);
		addCustomEntity(CustomEnderman.class, "CustomEnderman", 58);
		addCustomEntity(CustomEndermite.class, "CustomEndermite", 67);
		addCustomEntity(CustomGhast.class, "CustomGhast", 56);
		addCustomEntity(CustomGuardian.class, "CustomGuardian", 68);
		addCustomEntity(CustomHorse.class, "CustomCreeper", 100);
		addCustomEntity(CustomIronGolem.class, "CustomIronGolem", 99);
		addCustomEntity(CustomMagmaCube.class, "CustomMagmaCube", 62);
		addCustomEntity(CustomMushroomCow.class, "CustomMushroomCow", 96);
		addCustomEntity(CustomOcelot.class, "CustomOcelot", 98);
		addCustomEntity(CustomPig.class, "CustomPig", 90);
		addCustomEntity(CustomPigZombie.class, "CustomPigZombie", 57);
		addCustomEntity(CustomRabbit.class, "CustomRabbit", 101);
		addCustomEntity(CustomSheep.class, "CustomSheep", 91);
		addCustomEntity(CustomSilverfish.class, "CustomSilverfish", 60);
		addCustomEntity(CustomSkeleton.class, "CustomSkeleton", 51);
		addCustomEntity(CustomSlime.class, "CustomSlime", 55);
		addCustomEntity(CustomSnowman.class, "CustomSnowman", 97);
		addCustomEntity(CustomSpider.class, "CustomSpider", 52);
		addCustomEntity(CustomSquid.class, "CustomSquid", 94);
		addCustomEntity(CustomVillager.class, "CustomVillager", 120);
		addCustomEntity(CustomWitch.class, "CustomWitch", 66);
		addCustomEntity(CustomWither.class, "CustomWither", 64);
		addCustomEntity(CustomWolf.class, "CustomWolf", 95);
		addCustomEntity(CustomZombie.class, "CustomZombie", 54);
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
