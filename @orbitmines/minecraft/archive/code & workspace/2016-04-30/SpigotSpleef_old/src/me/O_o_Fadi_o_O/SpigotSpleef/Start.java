package me.O_o_Fadi_o_O.SpigotSpleef;

import com.sun.webpane.platform.ConfigManager;
import me.O_o_Fadi_o_O.SpigotSpleef.NMS.*;
import me.O_o_Fadi_o_O.SpigotSpleef.events.*;
import me.O_o_Fadi_o_O.SpigotSpleef.managers.*;
import me.O_o_Fadi_o_O.SpigotSpleef.utils.*;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class Start extends JavaPlugin {

	public static Start plugin;
	public static Economy economy = null;
	
	public void onEnable(){
		plugin = this;
		registerNMS();
		
		setupEconomy();
		
		ConfigManager.setup();
		
		SettingsManager.loadDatabaseSettings();
		loadDatabase();

		checkforNewVersion();
		checkForOlderConfig();
		
		Bukkit.getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        Bukkit.getMessenger().registerIncomingPluginChannel(this, "BungeeCord", new BungeeManager());
		Bukkit.getMessenger().registerOutgoingPluginChannel(this, "SpigotSpleef");
        Bukkit.getMessenger().registerIncomingPluginChannel(this, "SpigotSpleef", new BungeeManager());
		
		registerEvents();
		
		new BukkitRunnable(){
			public void run(){
				loadConfigData();
				loadMessagesData();
				loadMapsData();
				loadArenasData();
				
				if(StorageManager.usemysql == true){
					DatabaseManager.createTable();
					insertPlayerDataToMySQL();
				}
				
				for(Player p : Bukkit.getOnlinePlayers()){
					PlayerDataManager.loadPlayerData(p);
				}
				
				clearNPCs();
			}
		}.runTaskLater(this, 100);
	}
	
	public void onDisable(){
		removeAllNPCs();

		if(StorageManager.bungeecord == true && StorageManager.ishubserver == false || StorageManager.bungeecord == false){
			for(Arena arena : StorageManager.arenas){
				for(SpleefPlayer sp : arena.getAllPlayers()){
					sp.getPlayer().setGameMode(GameMode.SURVIVAL);
					sp.getPlayer().setAllowFlight(false);
					sp.getPlayer().setFlying(false);
					sp.clearInventory();
					sp.teleportToLobby();
					sp.setArena(null);
				}
				arena.resetScoreboard();
				arena.resetMap();
				arena.cancelArena();
			}
		}
		if(StorageManager.bungeecord == true){
			for(SpleefPlayer sp : StorageManager.spleefplayer.values()){
				sp.teleportToLobby();
			}
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
		SettingsManager.loadDatabaseSettings();
		SignManager.loadSigns();
		ScoreboardManager.loadScoreboards();
		LobbyManager.loadLobbyInventory();
		SpectatorManager.loadSpectatorInventory();
		ArenaSelectorManager.loadArenaSelector();
		KitSelectorManager.loadKitSelector();
		KitManager.loadKits();
		AbilityManager.loadAbilityMotiviers();
		BlockManager.loadBlocks();
	}
	
	public static Start getInstance(){
		return plugin;
	}
	
	private void loadDatabase(){
		if(StorageManager.usemysql == true){
			DatabaseManager.openConnection(StorageManager.databasehostname, StorageManager.databaseport, StorageManager.databasename, StorageManager.databaseusername, StorageManager.databasepassword);
		}
	}
	
	private void insertPlayerDataToMySQL(){
		if(StorageManager.usemysql == true && StorageManager.importfromplayerdata == true){
			for(String uuidstring : ConfigManager.playerdata.getConfigurationSection("players").getKeys(false)){
				if(!DatabaseManager.containsPath("uuid", "SpigotSpleef", UUID.fromString(uuidstring))){
					DatabaseManager.insertNewPlayer(UUID.fromString(uuidstring), ConfigManager.playerdata.getString("players." + uuidstring + ".Kits"), ConfigManager.playerdata.getInt("players." + uuidstring + ".Kills"), ConfigManager.playerdata.getInt("players." + uuidstring + ".Wins"), ConfigManager.playerdata.getInt("players." + uuidstring + ".Loses"), ConfigManager.playerdata.getInt("players." + uuidstring + ".Tokens"));
				}
			}
		}
	}
	
    public boolean setupEconomy(){
    	if(StorageManager.usevault == true){
    		if(getServer().getPluginManager().getPlugin("Vault") != null){
		        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
		        if(economyProvider != null){
		            economy = economyProvider.getProvider();
		        }
    		}
    		else{
    			Bukkit.getConsoleSender().sendMessage("");
    			Bukkit.getConsoleSender().sendMessage("[SpigotSpleef] §cCannot find the 'Vault' Plugin!");
    			Bukkit.getConsoleSender().sendMessage("[SpigotSpleef] §cInstall it first before starting to use this plugin. (With UseVault set to true)");
    			Bukkit.getConsoleSender().sendMessage("");
    		}
    	}

        return (economy != null);
    }
	
	private void sendNewConfigurationPathMessage(String version, String path){
		Bukkit.getConsoleSender().sendMessage("[SpigotSpleef] §eCreating new Configuration Path for " + version + "! (" + path + ")");
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
		getServer().getPluginManager().registerEvents(new PingEvent(), this);
		getServer().getPluginManager().registerEvents(new PlaceEvent(), this);
		getServer().getPluginManager().registerEvents(new PlayerChat(), this);
		getServer().getPluginManager().registerEvents(new ProjHitEvent(), this);
		getServer().getPluginManager().registerEvents(new ProjShootEvent(), this);
		getServer().getPluginManager().registerEvents(new QuitEvent(), this);
		getServer().getPluginManager().registerEvents(new UnloadChunkEvent(), this);
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
				if(a[0].equalsIgnoreCase("reload")){
					if(hasPermission(sender, "reload", "*")){
						checkforNewVersion();
						
						sender.sendMessage("§7Saving §6NPCs§7...");
						ArenaManager.saveArenaSelectors();
						ArenaManager.saveKitSelectors();
						sender.sendMessage("§7Cleaning up §6Arenas§7, §6NPCs§7, §6Player Data §7& §6Maps§7...");
						onDisable();
						StorageManager.vips.clear();
						StorageManager.vippermission.clear();
						StorageManager.vipmultiplier.clear();
						StorageManager.vipmultipliername.clear();
						StorageManager.mapsetup.clear();
						StorageManager.arenasetup.clear();
						StorageManager.kits.clear();
						StorageManager.arenas.clear();
						StorageManager.maps.clear();
						StorageManager.signs.clear();
						StorageManager.npcs.clear();
						StorageManager.messages.clear();
						StorageManager.abilities.clear();
						StorageManager.instanttnt.clear();
						StorageManager.projectiles.clear();
						StorageManager.spectatorinv.clear();
						StorageManager.lobbyinv.clear();
						StorageManager.spleefplayer.clear();
						StorageManager.scoreboardwaitingrows.clear();
						StorageManager.scoreboardwaitingscores.clear();
						StorageManager.scoreboardenoughplayersrows.clear();
						StorageManager.scoreboardenoughplayersscores.clear();
						StorageManager.scoreboardingamerows.clear();
						StorageManager.scoreboardingamescores.clear();
						StorageManager.signswaiting.clear();
						StorageManager.signsenoughplayers.clear();
						StorageManager.signsfull.clear();
						StorageManager.signsingame.clear();
						StorageManager.signsrestarting.clear();
						StorageManager.npcinvwaitinglore.clear();
						StorageManager.npcinvenoughplayerslore.clear();
						StorageManager.npcinvfulllore.clear();
						StorageManager.npcinvingamelore.clear();
						StorageManager.npcinvrestartinglore.clear();
						StorageManager.blocktoblock.clear();
						if(StorageManager.bungeecord == true){
							sender.sendMessage("§7Cleaning up §6BungeeCord Data§7...");
							StorageManager.bungeearenas.clear();
							StorageManager.bungeeservers.clear();
						}
						sender.sendMessage("§7Reloading §6config.yml§7...");
						ConfigManager.reloadConfig();
						loadConfigData();
						sender.sendMessage("§7Reloading §6messages.yml§7...");
						ConfigManager.reloadMessages();
						loadMessagesData();
						sender.sendMessage("§7Reloading §6maps.yml§7...");
						ConfigManager.reloadMaps();
						loadMapsData();
						sender.sendMessage("§7Reloading §6arenas.yml§7...");
						ConfigManager.reloadArenas();
						loadArenasData();
						sender.sendMessage("§7Reloading §6playerdata.yml§7...");
						ConfigManager.reloadPlayerData();
						
						if(StorageManager.usemysql == true){
							sender.sendMessage("§7Preparing §6MySQL§7...");
							DatabaseManager.createTable();
							insertPlayerDataToMySQL();
						}
						
						int players = Bukkit.getOnlinePlayers().size();
						
						if(players != 1){
							sender.sendMessage("§7Restoring Player Data for §6" + players + " Players§7...");
						}
						else{
							sender.sendMessage("§7Restoring Player Data for §6" + players + " Player§7...");
						}
						
						for(Player p : Bukkit.getOnlinePlayers()){
							PlayerDataManager.loadPlayerData(p);
						}
						
						sender.sendMessage("§7Reload §aComplete§7!");
					}
					else{
						Message m = Message.getMessage(MessageName.NO_PERM_RELOAD);
						m.replace("&", "§");
						m.send(sender);
					}
				}
				else if(a[0].equalsIgnoreCase("setlobby")){
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
				else if(a[0].equalsIgnoreCase("editnpc")){
					if(hasPermission(sender, "npc.edit", "npc.*", "*")){
						if(sender instanceof Player){
							Player p = (Player) sender;
							
							NPC closest = StorageManager.spleefplayer.get(p).getNearestNPC();
							
							if(closest != null){
								NPCManager.openNPCInventory(p, closest);
							}
							else{
								Message m = Message.getMessage(MessageName.NO_NEARBY_NPCS);
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
						Message m = Message.getMessage(MessageName.NO_PERM_EDIT_NPC);
						m.replace("&", "§");
						m.send(sender);
					}
				}
				else if(a[0].equalsIgnoreCase("openas") || a[0].equalsIgnoreCase("openarenaselector")){
					if(sender instanceof Player){
						Player p = (Player) sender;
						SpleefPlayer sp = StorageManager.spleefplayer.get(p);	
						
						sp.openArenaSelector();
					}
					else{
						Message m = Message.getMessage(MessageName.THROUGH_CONSOLE);
						m.replace("&", "§");
						m.send(sender);
					}
				}
				else if(a[0].equalsIgnoreCase("openks") || a[0].equalsIgnoreCase("openkitselector")){
					if(sender instanceof Player){
						Player p = (Player) sender;
						if(a.length == 2){
							try{
								SpleefPlayer sp = StorageManager.spleefplayer.get(p);	
								
								sp.openKitSelector(Kit.getKitFromID(Integer.parseInt(a[1])));
							}catch(NumberFormatException ex){
								Message m = Message.getMessage(MessageName.WRONG_USAGE_OPEN_KIT_SELECTOR);
								m.replace("&", "§");
								m.send(p);
							}
						}
						else{
							Message m = Message.getMessage(MessageName.WRONG_USAGE_OPEN_KIT_SELECTOR);
							m.replace("&", "§");
							m.send(p);
						}
					}
					else{
						Message m = Message.getMessage(MessageName.THROUGH_CONSOLE);
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
				else if(a[0].equalsIgnoreCase("removeas") || a[0].equalsIgnoreCase("removearenaselector")){
					if(hasPermission(sender, "arenaselector.remove", "arenaselector.*", "*")){
						if(sender instanceof Player){
							Player p = (Player) sender;

							NPC closest = StorageManager.spleefplayer.get(p).getNearestNPC();
							
							if(closest != null && closest.getNPCType() == NPCType.ARENA_SELECTOR){
								closest.getEntity().remove();
								closest.getItem().remove();
								StorageManager.npcs.remove(closest);
								ArenaManager.saveArenaSelectors();

								Message m = Message.getMessage(MessageName.REMOVE_ARENA_SELECTOR);
								m.replace("&", "§");
								m.send(sender);
							}
							else{
								Message m = Message.getMessage(MessageName.NO_NEARBY_NPCS);
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
				else if(a[0].equalsIgnoreCase("addks") || a[0].equalsIgnoreCase("addkitselector")){
					if(hasPermission(sender, "addkitselector.add", "addkitselector.*", "*")){
						if(sender instanceof Player){
							Player p = (Player) sender;
								
							NPC npc = new NPC(NPCType.KIT_SELECTOR, EntityType.BLAZE, "&7&lSelect &6&l%kit-name%", null, null, StorageManager.kits.get(0));
							npc.newEntity(EntityType.BLAZE, p.getLocation(), "&7&lSelect &6&l" + StorageManager.kits.get(0).getName());
							StorageManager.npcs.add(npc);
							ArenaManager.saveKitSelectors();

							Message m = Message.getMessage(MessageName.SPAWN_KIT_SELECTOR);
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
						Message m = Message.getMessage(MessageName.NO_PERM_ADD_KIT_SELECTOR);
						m.replace("&", "§");
						m.send(sender);
					}
				}
				else if(a[0].equalsIgnoreCase("removeks") || a[0].equalsIgnoreCase("removekitselector")){
					if(hasPermission(sender, "kitselector.remove", "kitselector.*", "*")){
						if(sender instanceof Player){
							Player p = (Player) sender;

							NPC closest = StorageManager.spleefplayer.get(p).getNearestNPC();
							
							if(closest != null && closest.getNPCType() == NPCType.KIT_SELECTOR){
								closest.getEntity().remove();
								closest.getItem().remove();
								StorageManager.npcs.remove(closest);
								ArenaManager.saveKitSelectors();

								Message m = Message.getMessage(MessageName.REMOVE_KIT_SELECTOR);
								m.replace("&", "§");
								m.send(sender);
							}
							else{
								Message m = Message.getMessage(MessageName.NO_NEARBY_NPCS);
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
						Message m = Message.getMessage(MessageName.NO_PERM_REMOVE_KIT_SELECTOR);
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
		addCustomEntity(CustomBlaze.class, "CustomBlaze", 61);
		addCustomEntity(CustomCaveSpider.class, "CustomCaveSpider", 59);
		addCustomEntity(CustomChicken.class, "CustomChicken", 93);
		addCustomEntity(CustomCow.class, "CustomCow", 92);
		addCustomEntity(CustomCreeper.class, "CustomCreeper", 50);
		addCustomEntity(CustomEnderman.class, "CustomEnderman", 58);
		addCustomEntity(CustomEndermite.class, "CustomEndermite", 67);
		addCustomEntity(CustomGhast.class, "CustomGhast", 56);
	//	addCustomEntity(CustomGuardian.class, "CustomGuardian", 68);
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
	//	addCustomEntity(CustomWither.class, "CustomWither", 64);
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

}
