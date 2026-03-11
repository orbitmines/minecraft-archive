package me.O_o_Fadi_o_O.SkyBlock;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import me.O_o_Fadi_o_O.SkyBlock.events.BreakEvent;
import me.O_o_Fadi_o_O.SkyBlock.events.ClickEvent;
import me.O_o_Fadi_o_O.SkyBlock.events.CommandPreprocessEvent;
import me.O_o_Fadi_o_O.SkyBlock.events.DamageByEntityEvent;
import me.O_o_Fadi_o_O.SkyBlock.events.EntityInteractEvent;
import me.O_o_Fadi_o_O.SkyBlock.events.InteractEvent;
import me.O_o_Fadi_o_O.SkyBlock.events.JoinEvent;
import me.O_o_Fadi_o_O.SkyBlock.events.PlaceEvent;
import me.O_o_Fadi_o_O.SkyBlock.events.QuitEvent;
import me.O_o_Fadi_o_O.SkyBlock.inventories.ChallengesInv;
import me.O_o_Fadi_o_O.SkyBlock.inventories.IslandInfoInv;
import me.O_o_Fadi_o_O.SkyBlock.island.IslandCreator;
import me.O_o_Fadi_o_O.SkyBlock.managers.ConfigManager;
import me.O_o_Fadi_o_O.SkyBlock.managers.EmptyWorldGenerator;
import me.O_o_Fadi_o_O.SkyBlock.managers.IslandManager;
import me.O_o_Fadi_o_O.SkyBlock.managers.PlayerManager;
import me.O_o_Fadi_o_O.SkyBlock.managers.StorageManager;
import me.O_o_Fadi_o_O.SkyBlock.utils.IslandRank;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class Start extends JavaPlugin {
	
	public static String TAG = "§5§lSkyBlock §8| §7";
	
	public boolean GenerateSkyBlockWorld = true;
	
	public IslandCreator islandCreator = new IslandCreator(this);
	public JoinEvent joinEvent = new JoinEvent();
	
	public ChunkGenerator getDefaultWorldGenerator(String w, String id){
		
		return new EmptyWorldGenerator(this);
	}
	
	public void onEnable(){
		
		if (Bukkit.getServer().getPluginManager().isPluginEnabled("Multiverse-Core")) {
			
			for(World w : Bukkit.getWorlds()){
				
				if(w.getName().equals("SkyBlock")){
					GenerateSkyBlockWorld = false;
				}
			}
			if(GenerateSkyBlockWorld == true){
				Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "mv create SkyBlock normal -g SkyBlock");
			}
		}
		
		Bukkit.getServer().getPluginManager().registerEvents(new CommandPreprocessEvent(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new JoinEvent(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new BreakEvent(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new PlaceEvent(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new InteractEvent(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new DamageByEntityEvent(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new EntityInteractEvent(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new ClickEvent(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new QuitEvent(), this);
		
		
		ConfigManager.setup(this);
		
		if(!(new File(getDataFolder(), "config.yml")).exists()){
			saveDefaultConfig();
		}
		
		StorageManager.IslandsAmount = getConfig().getInt("Islands");
		
		// GATHER CHALLENGES
		StorageManager.Challenges.add("Cobblestone Generator");
		StorageManager.Challenges.add("Apples");
		StorageManager.Challenges.add("Jack the Lumberjack");
		
		StorageManager.Challenges.add("More Smoothness");
		StorageManager.Challenges.add("Fishing Time!");
		StorageManager.Challenges.add("Chop Chop");
		
		StorageManager.Challenges.add("Navigation");
		StorageManager.Challenges.add("Librarian");
		StorageManager.Challenges.add("The Dark Forest");
		
		StorageManager.Challenges.add("It's Time");
		StorageManager.Challenges.add("Stained Clay");
		
		StorageManager.Challenges.add("Sheep Shearing");
		StorageManager.Challenges.add("Windows");
		
		StorageManager.Challenges.add("Wood Factory");
		StorageManager.Challenges.add("Lucky Fishing");
		//
		
		// FARM CHALLENGES
		StorageManager.Challenges.add("Melons");
		StorageManager.Challenges.add("Halloween Party");
		StorageManager.Challenges.add("Sugarcane");
		
		StorageManager.Challenges.add("Melon Blocks?!");
		StorageManager.Challenges.add("Mushroom Hunting");
		StorageManager.Challenges.add("Bacon");
		
		StorageManager.Challenges.add("Rabbits");
		StorageManager.Challenges.add("Baker");
		StorageManager.Challenges.add("Potatoes");
		
		StorageManager.Challenges.add("Cookie Monster");
		StorageManager.Challenges.add("Cactus");
		
		StorageManager.Challenges.add("KFC Delivery");
		StorageManager.Challenges.add("Steak");
		
		StorageManager.Challenges.add("Grandmother's Kitchen");
		
		StorageManager.Challenges.add("Cakes");
		//
		
		// MOB CHALLENGES
		StorageManager.Challenges.add("Skeletons");
		StorageManager.Challenges.add("Arrows!");
		
		StorageManager.Challenges.add("Zombie Apocalypse");
		StorageManager.Challenges.add("Creepy Creeper");
		
		StorageManager.Challenges.add("Destroy the Webs!");
		StorageManager.Challenges.add("Spider Eyes");
		
		StorageManager.Challenges.add("Enderwoman");
		
		//
		if(ConfigManager.islands.contains("islands")){
			for(String s : ConfigManager.islands.getConfigurationSection("islands").getKeys(false)){
				int IslandNumber = Integer.parseInt(s);
				
				StorageManager.IslandLocation.put(IslandNumber, IslandManager.getIslandLocation(IslandNumber));
				StorageManager.IslandMembers.put(IslandNumber, IslandManager.getIslandMembers(IslandNumber));
				StorageManager.IslandOwner.put(IslandNumber, IslandManager.getIslandOwner(IslandNumber));
				StorageManager.IslandCreatedDate.put(IslandNumber, IslandManager.getIslandCreatedDate(IslandNumber));
				StorageManager.IslandTeleportEnabled.put(IslandNumber, IslandManager.getIslandTeleportEnabled(IslandNumber));
			}
		}
		
		for(Player p : Bukkit.getOnlinePlayers()){
			joinEvent.loadPlayerIslandInfo(p);
		}
	}
	
	public void onDisable(){
		
	}
	
	public void setIslandAmount(int IslandAmount){
		StorageManager.IslandsAmount = IslandAmount;
		getConfig().set("Islands", IslandAmount);
		saveConfig();
	}
	
	public static List<String> getHelpMessage(){
		
		List<String> list = new ArrayList<String>();
		list.add(TAG + "§lHelp Menu:");
		list.add(TAG + "§d/is home | h §7§o(Teleport to your Island)");
		list.add(TAG + "§d/is sethome §7§o(Set your Island Home)");
		list.add(TAG + "§d/is help §7§o(Show this Menu)");
		list.add(TAG + "§d/is invite <player> §7§o(Invite a Player)");
		list.add(TAG + "§d/is remove <player> §7§o(Remove a Player)");
		list.add(TAG + "§d/is accept §7§o(Accept an Island invite)");
		list.add(TAG + "§d/is deny §7§o(Deny an Island invite)");
		list.add(TAG + "§d/is tp | teleport <player> §7§o(Teleport to an Island)");
		list.add(TAG + "§d/is c | challenge §7§o(Open the Challenges Menu)");
		list.add(TAG + "§d/is leave §7§o(Leave your Island)");
		list.add(TAG + "§d/is restart §7§o(Restart your Island)");
		list.add(TAG + "§d/is info §7§o(Open the Island Info Menu)");
		
		return list;
	}
	
	public static void clearInventory(Player p){
		p.getInventory().clear();
		p.getInventory().setHelmet(null);
		p.getInventory().setChestplate(null);
		p.getInventory().setLeggings(null);
		p.getInventory().setBoots(null);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String l, String[] a) {

		if(cmd.getName().equalsIgnoreCase("is") || cmd.getName().equalsIgnoreCase("island")){
			if(sender instanceof Player){
				final Player p = (Player) sender;
				
				if(a.length == 0){
					for(String s : getHelpMessage()){
						p.sendMessage(s);
					}
				}
				else if(a[0].equalsIgnoreCase("help")){
					for(String s : getHelpMessage()){
						p.sendMessage(s);
					}
				}
				else if(a[0].equalsIgnoreCase("restart")){
					if(PlayerManager.hasIsland(p)){
						p.chat("/is leave");
						p.chat("/is h");
					}
					else{
						p.sendMessage(Start.TAG + "§4§lDENIED§7 You don't have an §dIsland§7!");
					}
				}
				else if(a[0].equalsIgnoreCase("home") || a[0].equalsIgnoreCase("h")){
					
					if(PlayerManager.hasIsland(p)){
						if(!PlayerManager.isInTheVoid(p)){
							p.sendMessage(Start.TAG + "§7Teleporting to your §dIsland§7...");
							new BukkitRunnable(){
								public void run(){
									p.teleport(StorageManager.PlayersIslandHomeLocation.get(p));
									p.playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 5, 1);
								}
							}.runTaskLater(this, 10);
						}
						else{
							p.sendMessage(Start.TAG + "§4§lDENIED§7 You're in the Void!");
						}
					}
					else{
						islandCreator.createIsland(StorageManager.IslandsAmount +1, p);
					}
					
				}
				else if(a[0].equalsIgnoreCase("sethome") || a[0].equalsIgnoreCase("h")){
					if(PlayerManager.hasIsland(p)){
						if(PlayerManager.isOnOwnIsland(p)){
							if(!PlayerManager.isInTheVoid(p)){
								if(p.getWorld().getName().equals("SkyBlock")){
									IslandManager.setPlayersIslandHomeLocation(p, p.getLocation());
									p.sendMessage(Start.TAG + "§dIsland Home§7 set!");
								}
								else{
									p.sendMessage(Start.TAG + "§4§lDENIED§7 You can't set your §dIsland Home§7 here!");
								}
							}
							else{
								p.sendMessage(Start.TAG + "§4§lDENIED§7 You're in the Void!");
							}
						}
						else{
							p.sendMessage(Start.TAG + "§4§lDENIED§7 You're not on your own §dIsland§7!");
						}
					}
					else{
						p.sendMessage(Start.TAG + "§4§lDENIED§7 You don't have an §dIsland§7!");
					}
				}
				else if(a[0].equalsIgnoreCase("invite")){
					if(PlayerManager.hasIsland(p)){
						if(StorageManager.PlayersIslandRank.get(p) == IslandRank.OWNER){
							if(a.length == 2){
							
								if(StorageManager.IslandMembers.get(StorageManager.PlayersIslandNumber.get(p)).size() != 9){
									Player p1 = null;
									for(Player player : Bukkit.getOnlinePlayers()){
										if(player.getName().equals(a[1])){
											p1 = player;
										}
									}
								
									
									if(p1 != null){
										int IslandNumber =  StorageManager.PlayersIslandNumber.get(p);
										List<String> members = StorageManager.IslandMembers.get(IslandNumber);
										if(!StorageManager.PlayerHasIsland.containsKey(p1)){
											p.sendMessage(Start.TAG + "§d" + a[1] + "§7 has been invited to your §dIsland§7!");
											p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 5, 1);
											StorageManager.PlayersIslandInvite.put(p1, IslandNumber);
											p1.sendMessage(Start.TAG + "§d" + p.getName() + "§7 has invited you to their §dIsland§7!");
											p1.sendMessage(Start.TAG + "Type §d/is accept§7 to §aAccept§7 or §d/is deny§7 to §cDeny§7.");
											p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 5, 1);
											
											for(Player player : Bukkit.getOnlinePlayers()){
												if(members.contains(player.getName())){
													player.sendMessage(Start.TAG + "§d" + p.getName() + "§7 invited §d" + a[1] + "§7 to your §dIsland§7!");
													player.playSound(player.getLocation(), Sound.SUCCESSFUL_HIT, 5, 1);
												}
											}
										}
										else{
											if(StorageManager.PlayerHasIsland.get(p1) == false){
												p.sendMessage(Start.TAG + "§d" + a[1] + "§7 has been invited to your §dIsland§7!");
												p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 5, 1);
												StorageManager.PlayersIslandInvite.put(p1, IslandNumber);
												p1.sendMessage(Start.TAG + "§d" + p.getName() + "§7 has invited you to their §dIsland§7!");
												p1.sendMessage(Start.TAG + "Type §d/is accept§7 to §aAccept§7 or §d/is deny§7 to §cDeny§7.");
												p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 5, 1);
												
												for(Player player : Bukkit.getOnlinePlayers()){
													if(members.contains(player.getName())){
														player.sendMessage(Start.TAG + "§d" + p.getName() + "§7 invited §d" + a[1] + "§7 to your §dIsland§7!");
														player.playSound(player.getLocation(), Sound.SUCCESSFUL_HIT, 5, 1);
													}
												}
											}
											else{
												p.sendMessage(Start.TAG + "§4§lDENIED§7 §d" + a[1] + "§7 is already on an §dIsland§7!");
											}
										}
									}
									else{
										p.sendMessage(Start.TAG + "§4§lERROR§7 Player §d" + a[1] +" §7isn't §aOnline§7!");
									}
								}
								else{
									p.sendMessage(Start.TAG + "§4§lDENIED§7 Your §dIsland§7 reached the §aPlayer Limit§7!");
								}
								
							}
							else{
								p.sendMessage(TAG + "Incorrect Use!");
								p.sendMessage(TAG + "Use: §d/is invite <player>");
							}
						}
						else{
							p.sendMessage(Start.TAG + "§4§lDENIED§7 You aren't the §downer§7 of your §dIsland§7!");
						}
					}
					else{
						p.sendMessage(Start.TAG + "§4§lDENIED§7 You don't have an §dIsland§7!");
					}
					
				}
				else if(a[0].equalsIgnoreCase("remove")){
					if(PlayerManager.hasIsland(p)){
						if(StorageManager.PlayersIslandRank.get(p) == IslandRank.OWNER){
							if(a.length == 2){
								int IslandNumber = StorageManager.PlayersIslandNumber.get(p);
								
								if(!a[1].equals(p.getName())){
									if(StorageManager.IslandMembers.get(IslandNumber).contains(a[1])){
										
										List<String> members = StorageManager.IslandMembers.get(IslandNumber);
										members.remove(a[1]);
										IslandManager.setIslandMembers(IslandNumber, members);
										
										ConfigManager.playerdata.set("players." + a[1], null);
										ConfigManager.savePlayerdata();
										
										for(Player player : Bukkit.getOnlinePlayers()){
											if(members.contains(player.getName())){
												player.sendMessage(Start.TAG + "§d" + p.getName() + "§7 removed §d" + a[1] + "§7 from your §dIsland§7!");
												player.playSound(player.getLocation(), Sound.SUCCESSFUL_HIT, 5, 1);
											}
											if(player.getName().equals(a[1])){
												StorageManager.PlayerHasIsland.put(player, false);
												player.sendMessage(Start.TAG + "§d" + p.getName() + "§7 removed you from their §dIsland§7!");
												player.playSound(player.getLocation(), Sound.SUCCESSFUL_HIT, 5, 1);
												clearInventory(player);
												player.chat("/spawn");
											}
										}
										
										p.sendMessage(Start.TAG + "You removed §d" + a[1] + "§7 from your §dIsland§7!");
										p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 5, 1);
										
									}
									else{
										p.sendMessage(Start.TAG + "§4§lERROR§7 Player §d" + a[1] +" §7isn't on your §dIsland§7!");
									}
								}
								else{
									p.sendMessage(Start.TAG + "§4§lERROR§7 You can't remove yourself from your §dIsland§7!");
								}
							}
							else{
								p.sendMessage(TAG + "Incorrect Use!");
								p.sendMessage(TAG + "Use: §d/is remove <player>");
							}
						}
						else{
							p.sendMessage(Start.TAG + "§4§lDENIED§7 You aren't the §downer§7 of your §dIsland§7!");
						}
					}
					else{
						p.sendMessage(Start.TAG + "§4§lDENIED§7 You don't have an §dIsland§7!");
					}
				}
				else if(a[0].equalsIgnoreCase("accept")){
					if(StorageManager.PlayersIslandInvite.containsKey(p)){
						if(!PlayerManager.hasIsland(p)){
							int IslandNumber = StorageManager.PlayersIslandInvite.get(p);
							List<String> members = StorageManager.IslandMembers.get(IslandNumber);
							if(members.size() != 9){
								StorageManager.PlayerHasIsland.put(p, true);
								StorageManager.PlayersIslandInvite.remove(p);
								for(Player player : Bukkit.getOnlinePlayers()){
									if(members.contains(player.getName()) || StorageManager.IslandOwner.get(IslandNumber).equals(player.getName())){
										player.sendMessage(Start.TAG + "§d" + p.getName() + "§7 joined your §dIsland§7!");
										player.playSound(player.getLocation(), Sound.SUCCESSFUL_HIT, 5, 1);
									}
								}
								p.sendMessage(Start.TAG + "You joined §d" + StorageManager.IslandOwner.get(IslandNumber) + "s Island§7!");
								p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 5, 1);
								
								IslandManager.setPlayersIslandNumber(p, IslandNumber);
								IslandManager.setPlayersIslandHomeLocation(p, IslandManager.getOfflinePlayersIslandHomeLocation(StorageManager.IslandOwner.get(IslandNumber)));
								IslandManager.setPlayersIslandRank(p, IslandRank.MEMBER);
								
								IslandManager.addIslandMember(IslandNumber, p);
								IslandManager.resetChallenges(p);
								
								clearInventory(p);
								p.sendMessage(Start.TAG + "§7Teleporting to your §dIsland§7...");
								new BukkitRunnable(){
									public void run(){
										p.teleport(StorageManager.PlayersIslandHomeLocation.get(p));
										p.playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 5, 1);
									}
								}.runTaskLater(this, 10);
							}
							else{
								p.sendMessage(Start.TAG + "§4§lDENIED§7 That §dIsland§7 reached the §aPlayer Limit§7!");
								StorageManager.PlayersIslandInvite.remove(p);
							}
						}
						else{
							p.sendMessage(Start.TAG + "§4§lDENIED§7 You already have an §dIsland§7!");
						}
					}
					else{
						p.sendMessage(Start.TAG + "§4§lDENIED§7 Nobody has §dinvited§7 you yet!");
					}
					
				}
				else if(a[0].equalsIgnoreCase("deny")){
					if(StorageManager.PlayersIslandInvite.containsKey(p)){
						if(!PlayerManager.hasIsland(p)){
							int IslandNumber = StorageManager.PlayersIslandInvite.get(p);
							StorageManager.PlayersIslandInvite.remove(p);
							
							for(Player player : Bukkit.getOnlinePlayers()){
								if(StorageManager.IslandOwner.get(IslandNumber).equals(player.getName())){
									player.sendMessage(Start.TAG + "§d" + p.getName() + "§7 denied your §dinvite§7!");
									player.playSound(player.getLocation(), Sound.SUCCESSFUL_HIT, 5, 1);
								}
							}
							p.sendMessage(Start.TAG + "You denied §d" + StorageManager.IslandOwner.get(IslandNumber) + "s invite§7!");
							p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 5, 1);
						}
						else{
							p.sendMessage(Start.TAG + "§4§lDENIED§7 You already have an §dIsland§7!");
						}
					}
					else{
						p.sendMessage(Start.TAG + "§4§lDENIED§7 Nobody has §dinvited§7 you yet!");
					}
				}
				else if(a[0].equalsIgnoreCase("leave")){
					if(PlayerManager.hasIsland(p)){
						IslandRank rank = StorageManager.PlayersIslandRank.get(p);
						int IslandNumber = StorageManager.PlayersIslandNumber.get(p);
						List<String> members = StorageManager.IslandMembers.get(IslandNumber);
						if(rank == IslandRank.OWNER){
							if(members.size() == 0){
								
								ConfigManager.playerdata.set("players." + p.getName(), null);
								ConfigManager.savePlayerdata();
								
								
								StorageManager.PlayerHasIsland.put(p, false);
								p.chat("/spawn");
								clearInventory(p);
								p.sendMessage(Start.TAG + "You left your §dIsland§7!");
								p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 5, 1);
							}
							else{
								String pl = members.get(0);
								
								members.remove(pl);
								IslandManager.setIslandMembers(IslandNumber, members);
								IslandManager.setIslandOfflineOwner(IslandNumber, pl);
								IslandManager.setOfflinePlayersIslandRank(pl, IslandRank.OWNER);
								
								ConfigManager.playerdata.set("players." + p.getName(), null);
								ConfigManager.savePlayerdata();
								StorageManager.PlayerHasIsland.put(p, false);
								clearInventory(p);
								p.chat("/spawn");
								p.sendMessage(Start.TAG + "You left your §dIsland§7!");
								p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 5, 1);
								
								
								for(Player player : Bukkit.getOnlinePlayers()){
									if(members.contains(player.getName())){
										player.sendMessage(Start.TAG + "§d" + p.getName() + "§7 left your §dIsland§7!");
										player.sendMessage(Start.TAG + "§d" + pl + "§7 is now the §downer§7 of your §dIsland§7!");
										player.playSound(player.getLocation(), Sound.SUCCESSFUL_HIT, 5, 1);
									}
									if(player.getName().equals(StorageManager.IslandOwner.get(IslandNumber))){
										player.sendMessage(Start.TAG + "§d" + p.getName() + "§7 left your §dIsland§7!");
										player.sendMessage(Start.TAG + "You are now the §downer§7 of your §dIsland§7!");
										player.playSound(player.getLocation(), Sound.SUCCESSFUL_HIT, 5, 1);
									}
								}

							}
						}
						if(rank == IslandRank.MEMBER){
						
							members.remove(p.getName());
							IslandManager.setIslandMembers(IslandNumber, members);
							
							ConfigManager.playerdata.set("players." + p.getName(), null);
							ConfigManager.savePlayerdata();
							
							for(Player player : Bukkit.getOnlinePlayers()){
								if(members.contains(player.getName()) || player.getName().equals(StorageManager.IslandOwner.get(IslandNumber))){
									player.sendMessage(Start.TAG + "§d" + p.getName() + "§7 left your §dIsland§7!");
									player.playSound(player.getLocation(), Sound.SUCCESSFUL_HIT, 5, 1);
								}
							}
							StorageManager.PlayerHasIsland.put(p, false);
							p.chat("/spawn");
							clearInventory(p);
							p.sendMessage(Start.TAG + "You left your §dIsland§7!");
							p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 5, 1);
						}
					}
					else{
						p.sendMessage(Start.TAG + "§4§lDENIED§7 You don't have an §dIsland§7!");
					}
				}
				else if(a[0].equalsIgnoreCase("tp") || a[0].equalsIgnoreCase("teleport")){
					if(a.length == 2){
						
						Player p1 = null;
						for(Player player : Bukkit.getOnlinePlayers()){
							if(player.getName().equals(a[1])){
								p1 = player;
							}
						}
						
						if(p1 != null){
							if(PlayerManager.hasIsland(p1)){
								int IslandNumber = StorageManager.PlayersIslandNumber.get(p1);
								if(StorageManager.IslandTeleportEnabled.get(IslandNumber) == true){
									if(!PlayerManager.isInTheVoid(p)){
										teleportToIsland(p, p1);
									}
									else{
										p.sendMessage(Start.TAG + "§4§lDENIED§7 You're in the Void!");
									}
								}
								else{
									p.sendMessage(Start.TAG + "§d" + p1.getName() + "s Island§7 has §c§lDISABLED §3Teleporting§7!");
								}
							}
							else{
								p.sendMessage(Start.TAG + "§4§lDENIED§7 §d" + p1.getName() + "§7 doesn't have an §dIsland§7!");
							}
						}
						else{
							p.sendMessage(Start.TAG + "§4§lERROR§7 Player §d" + a[1] +" §7isn't §aOnline§7!");
						}
					}
					else{
						p.sendMessage(TAG + "Incorrect Use!");
						p.sendMessage(TAG + "Use: §d/is tp|teleport <player>");
					}
				}
				else if(a[0].equalsIgnoreCase("c") || a[0].equalsIgnoreCase("challenge")){
					if(PlayerManager.hasIsland(p)){
						p.openInventory(ChallengesInv.getChallengesInv(p));
						p.playSound(p.getLocation(), Sound.WITHER_IDLE, 5, 1);
					}
					else{
						p.sendMessage(Start.TAG + "§4§lDENIED§7 You don't have an §dIsland§7!");
					}
				}
				else if(a[0].equalsIgnoreCase("info")){
					if(PlayerManager.hasIsland(p)){
						p.playSound(p.getLocation(), Sound.CHEST_OPEN, 5, 1);
						p.openInventory(IslandInfoInv.getIslandInfoInv(p));
					}
					else{
						p.sendMessage(Start.TAG + "§4§lDENIED§7 You don't have an §dIsland§7!");
					}
				}
				else{
					
					p.sendMessage(TAG + "Incorrect Use!");
					p.sendMessage(TAG + "Use: §d/is§7 for Help!");
					
				}
				
			}
			else{
				sender.sendMessage(TAG + "§cYou can't perform commands through the Console!");
			}
		}
		return false;
	}
	
	public void teleportToIsland(final Player p1, final Player p2){
		p1.sendMessage(Start.TAG + "§7Teleporting to §d" + p2.getName() + "s §dIsland§7...");
		new BukkitRunnable(){
			public void run(){
				p1.teleport(StorageManager.PlayersIslandHomeLocation.get(p2));
				p1.playSound(p1.getLocation(), Sound.ENDERMAN_TELEPORT, 5, 1);
			}
		}.runTaskLater(this, 10);
	}

}
