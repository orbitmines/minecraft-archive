package me.O_o_Fadi_o_O.SkyBlock;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import me.O_o_Fadi_o_O.SkyBlock.Inv.ServerSelector;
import me.O_o_Fadi_o_O.SkyBlock.NMS.CustomBlaze;
import me.O_o_Fadi_o_O.SkyBlock.NMS.CustomCreeper;
import me.O_o_Fadi_o_O.SkyBlock.NMS.CustomSkeleton;
import me.O_o_Fadi_o_O.SkyBlock.NMS.CustomVillager;
import me.O_o_Fadi_o_O.SkyBlock.NMS.PetChicken;
import me.O_o_Fadi_o_O.SkyBlock.NMS.PetCow;
import me.O_o_Fadi_o_O.SkyBlock.NMS.PetCreeper;
import me.O_o_Fadi_o_O.SkyBlock.NMS.PetMagmaCube;
import me.O_o_Fadi_o_O.SkyBlock.NMS.PetMushroomCow;
import me.O_o_Fadi_o_O.SkyBlock.NMS.PetOcelot;
import me.O_o_Fadi_o_O.SkyBlock.NMS.PetPig;
import me.O_o_Fadi_o_O.SkyBlock.NMS.PetSheep;
import me.O_o_Fadi_o_O.SkyBlock.NMS.PetSilverfish;
import me.O_o_Fadi_o_O.SkyBlock.NMS.PetSlime;
import me.O_o_Fadi_o_O.SkyBlock.NMS.PetSpider;
import me.O_o_Fadi_o_O.SkyBlock.NMS.PetSquid;
import me.O_o_Fadi_o_O.SkyBlock.NMS.PetWolf;
import me.O_o_Fadi_o_O.SkyBlock.events.BreakEvent;
import me.O_o_Fadi_o_O.SkyBlock.events.ClickEvent;
import me.O_o_Fadi_o_O.SkyBlock.events.ClickEvent2;
import me.O_o_Fadi_o_O.SkyBlock.events.CommandPreprocessEvent;
import me.O_o_Fadi_o_O.SkyBlock.events.DamageByEntityEvent;
import me.O_o_Fadi_o_O.SkyBlock.events.EntityDamage;
import me.O_o_Fadi_o_O.SkyBlock.events.EntityDeath;
import me.O_o_Fadi_o_O.SkyBlock.events.EntityInteractEvent;
import me.O_o_Fadi_o_O.SkyBlock.events.InteractAtEntityEvent;
import me.O_o_Fadi_o_O.SkyBlock.events.InteractEvent;
import me.O_o_Fadi_o_O.SkyBlock.events.JoinEvent;
import me.O_o_Fadi_o_O.SkyBlock.events.PlaceEvent;
import me.O_o_Fadi_o_O.SkyBlock.events.PlayerChat;
import me.O_o_Fadi_o_O.SkyBlock.events.PlayerMove;
import me.O_o_Fadi_o_O.SkyBlock.events.QuitEvent;
import me.O_o_Fadi_o_O.SkyBlock.events.VoteEvent;
import me.O_o_Fadi_o_O.SkyBlock.inventories.ChallengesInv;
import me.O_o_Fadi_o_O.SkyBlock.inventories.IslandInfoInv;
import me.O_o_Fadi_o_O.SkyBlock.island.IslandCreator;
import me.O_o_Fadi_o_O.SkyBlock.managers.BungeeManager;
import me.O_o_Fadi_o_O.SkyBlock.managers.ConfigManager;
import me.O_o_Fadi_o_O.SkyBlock.managers.DatabaseManager;
import me.O_o_Fadi_o_O.SkyBlock.managers.EmptyWorldGenerator;
import me.O_o_Fadi_o_O.SkyBlock.managers.IslandManager;
import me.O_o_Fadi_o_O.SkyBlock.managers.NPCManager;
import me.O_o_Fadi_o_O.SkyBlock.managers.PlayerManager;
import me.O_o_Fadi_o_O.SkyBlock.managers.ScoreboardTitleManager;
import me.O_o_Fadi_o_O.SkyBlock.managers.StorageManager;
import me.O_o_Fadi_o_O.SkyBlock.managers.TitleManager;
import me.O_o_Fadi_o_O.SkyBlock.managers.VoteManager;
import me.O_o_Fadi_o_O.SkyBlock.runnables.DatabaseRunnable;
import me.O_o_Fadi_o_O.SkyBlock.runnables.PetRunnable;
import me.O_o_Fadi_o_O.SkyBlock.runnables.PlayerRunnable;
import me.O_o_Fadi_o_O.SkyBlock.runnables.ScoreboardRunnable;
import me.O_o_Fadi_o_O.SkyBlock.runnables.ServerSelectorRunnable;
import me.O_o_Fadi_o_O.SkyBlock.utils.ArmorStandType;
import me.O_o_Fadi_o_O.SkyBlock.utils.ChatColor;
import me.O_o_Fadi_o_O.SkyBlock.utils.Disguise;
import me.O_o_Fadi_o_O.SkyBlock.utils.IslandRank;
import me.O_o_Fadi_o_O.SkyBlock.utils.Server;
import me.O_o_Fadi_o_O.SkyBlock.utils.StaffRank;
import me.O_o_Fadi_o_O.SkyBlock.utils.Trail;
import me.O_o_Fadi_o_O.SkyBlock.utils.TrailType;
import me.O_o_Fadi_o_O.SkyBlock.utils.VIPRank;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.EulerAngle;

public class Start extends JavaPlugin {
	
	@SuppressWarnings("deprecation")
	public void onEnable(){
		
			removeAllEntities();
			npcmanager.spawnServerSelector(StorageManager.lobbyworld, new Location(StorageManager.lobbyworld, 3.5, 76, 5.5, 0, 0), "§3§lServer Selector");
			npcmanager.spawnOMTShop(StorageManager.lobbyworld, new Location(StorageManager.lobbyworld, -2.5, 75, -5.5, 0, 0), "§e§lOMT Shop");
			
			npcmanager.spawnArmorStand(
					StorageManager.lobbyworld, 
					new Location(StorageManager.lobbyworld, 0.5, 72.5, 20.5, 180, 0), 
					"§dJump down to §d§lPlay§d!",
					true, 
					true, 
					true, 
					EulerAngle.ZERO.setX(-0.1).setY(0).setZ(0), 
					false, 
					EulerAngle.ZERO.setX(-0.15).setY(0).setZ(0), 
					EulerAngle.ZERO.setX(0).setY(0).setZ(0), 
					EulerAngle.ZERO.setX(-0.5).setY(0).setZ(0), 
					EulerAngle.ZERO.setX(-0.75).setY(0).setZ(0), 
					EulerAngle.ZERO.setX(0.5).setY(0).setZ(0), 
					false,
					new ItemStack(Material.LEATHER_HELMET, 1),
					new ItemStack(Material.LEATHER_CHESTPLATE, 1),
					new ItemStack(Material.LEATHER_LEGGINGS, 1),
					new ItemStack(Material.LEATHER_BOOTS, 1),
					new ItemStack(Material.FISHING_ROD, 1),
					ArmorStandType.NORMAL);
			npcmanager.spawnArmorStand(
					StorageManager.lobbyworld, 
					new Location(StorageManager.lobbyworld, 2.5, 74, 12.5, -12, 0), 
					null,
					false, 
					true, 
					true, 
					EulerAngle.ZERO.setX(-0.1).setY(0).setZ(0), 
					false, 
					EulerAngle.ZERO.setX(-0.15).setY(0).setZ(0), 
					EulerAngle.ZERO.setX(0).setY(0).setZ(0), 
					EulerAngle.ZERO.setX(-0.5).setY(0).setZ(0), 
					EulerAngle.ZERO.setX(-0.75).setY(0).setZ(0), 
					EulerAngle.ZERO.setX(0.5).setY(0).setZ(0), 
					false,
					null,
					null,
					null,
					null,
					new ItemStack(Material.GRASS, 1),
					ArmorStandType.NORMAL);
			npcmanager.spawnArmorStand(
					StorageManager.lobbyworld, 
					new Location(StorageManager.lobbyworld, 3.25, 73.75, 12.13, -38, 0), 
					null,
					false, 
					true, 
					true, 
					EulerAngle.ZERO.setX(-0.1).setY(0).setZ(0), 
					false, 
					EulerAngle.ZERO.setX(-0.15).setY(0).setZ(0), 
					EulerAngle.ZERO.setX(0).setY(0).setZ(0), 
					EulerAngle.ZERO.setX(-0.5).setY(0).setZ(0), 
					EulerAngle.ZERO.setX(-0.75).setY(0).setZ(0), 
					EulerAngle.ZERO.setX(0.5).setY(0).setZ(0), 
					false,
					null,
					null,
					null,
					null,
					new ItemStack(Material.GRASS, 1),
					ArmorStandType.NORMAL);
			npcmanager.spawnArmorStand(
					StorageManager.lobbyworld, 
					new Location(StorageManager.lobbyworld, 5, 73.75, 11.79, 50, 0), 
					null,
					false, 
					true, 
					true, 
					EulerAngle.ZERO.setX(-0.1).setY(0).setZ(0), 
					false, 
					EulerAngle.ZERO.setX(-0.15).setY(0).setZ(0), 
					EulerAngle.ZERO.setX(0).setY(0).setZ(0), 
					EulerAngle.ZERO.setX(-0.5).setY(0).setZ(0), 
					EulerAngle.ZERO.setX(-0.75).setY(0).setZ(0), 
					EulerAngle.ZERO.setX(0.5).setY(0).setZ(0), 
					false,
					null,
					null,
					null,
					null,
					new ItemStack(Material.GRASS, 1),
					ArmorStandType.NORMAL);
			npcmanager.spawnArmorStand(
					StorageManager.lobbyworld, 
					new Location(StorageManager.lobbyworld, 3.85, 75.1, 12.32, 47, 0), 
					null,
					true, 
					true, 
					true, 
					EulerAngle.ZERO.setX(0.15).setY(0).setZ(0), 
					false, 
					EulerAngle.ZERO.setX(-0.05 + 0.5).setY(0).setZ(0), 
					EulerAngle.ZERO.setX(0.75 + 0.5).setY(0).setZ(0), 
					EulerAngle.ZERO.setX(-0.75 + 0.1).setY(0).setZ(0), 
					EulerAngle.ZERO.setX(-0.75 + 0.5).setY(0).setZ(0), 
					EulerAngle.ZERO.setX(0.6 + 0.5).setY(0).setZ(0), 
					false,
					new ItemStack(Material.LEATHER_HELMET, 1),
					new ItemStack(Material.LEATHER_CHESTPLATE, 1),
					new ItemStack(Material.LEATHER_LEGGINGS, 1),
					new ItemStack(Material.LEATHER_BOOTS, 1),
					null,
					ArmorStandType.NORMAL);
		
		
		StorageManager.islandsamount = getConfig().getInt("Islands");
		
		// GATHER CHALLENGES
		StorageManager.challenges.add("Cobblestone Generator");
		StorageManager.challenges.add("Apples");
		StorageManager.challenges.add("Jack the Lumberjack");
		
		StorageManager.challenges.add("More Smoothness");
		StorageManager.challenges.add("Fishing Time!");
		StorageManager.challenges.add("Chop Chop");
		
		StorageManager.challenges.add("Navigation");
		StorageManager.challenges.add("Librarian");
		StorageManager.challenges.add("The Dark Forest");
		
		StorageManager.challenges.add("It's Time");
		StorageManager.challenges.add("Stained Clay");
		
		StorageManager.challenges.add("Sheep Shearing");
		StorageManager.challenges.add("Windows");
		
		StorageManager.challenges.add("Wood Factory");
		StorageManager.challenges.add("Lucky Fishing");
		//
		
		// FARM CHALLENGES
		StorageManager.challenges.add("Melons");
		StorageManager.challenges.add("Halloween Party");
		StorageManager.challenges.add("Sugarcane");
		
		StorageManager.challenges.add("Melon Blocks?!");
		StorageManager.challenges.add("Mushroom Hunting");
		StorageManager.challenges.add("Bacon");
		
		StorageManager.challenges.add("Rabbits");
		StorageManager.challenges.add("Baker");
		StorageManager.challenges.add("Potatoes");
		
		StorageManager.challenges.add("Cookie Monster");
		StorageManager.challenges.add("Cactus");
		
		StorageManager.challenges.add("KFC Delivery");
		StorageManager.challenges.add("Steak");
		
		StorageManager.challenges.add("Grandmother's Kitchen");
		
		StorageManager.challenges.add("Cakes");
		//
		
		// MOB CHALLENGES
		StorageManager.challenges.add("Skeletons");
		StorageManager.challenges.add("Arrows!");
		
		StorageManager.challenges.add("Zombie Apocalypse");
		StorageManager.challenges.add("Creepy Creeper");
		
		StorageManager.challenges.add("Destroy the Webs!");
		StorageManager.challenges.add("Spider Eyes");
		
		StorageManager.challenges.add("Enderwoman");
	}
	
	public void setIslandAmount(int IslandAmount){
		StorageManager.islandsamount = IslandAmount;
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
	
	@SuppressWarnings("deprecation")
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
				else if(a[0].equalsIgnoreCase("home") || a[0].equalsIgnoreCase("h") || a[0].equalsIgnoreCase("start")){
					
					if(PlayerManager.hasIsland(p)){
						if(!PlayerManager.isInTheVoid(p)){
							p.sendMessage(Start.TAG + "§7Teleporting to your §dIsland§7...");
							new BukkitRunnable(){
								public void run(){
									p.teleport(StorageManager.playersislandhomelocation.get(p));
									p.playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 5, 1);
									TitleManager.setTitle(p, "", "§7Teleported to your §dIsland§7.");
								}
							}.runTaskLater(this, 10);
						}
						else{
							p.sendMessage(Start.TAG + "§4§lDENIED§7 You're in the Void!");
						}
					}
					else{
						for(String s : ConfigManager.islands.getConfigurationSection("islands").getKeys(false)){
							int IslandNumber = Integer.parseInt(s);
							
							StorageManager.islandlocation.put(IslandNumber, IslandManager.getIslandLocation(IslandNumber));
							StorageManager.islandmembers.put(IslandNumber, IslandManager.getIslandMembers(IslandNumber));
							StorageManager.islandowner.put(IslandNumber, IslandManager.getIslandOwner(IslandNumber));
							StorageManager.islandcreateddate.put(IslandNumber, IslandManager.getIslandCreatedDate(IslandNumber));
							StorageManager.islandteleportenabled.put(IslandNumber, IslandManager.getIslandTeleportEnabled(IslandNumber));
						}
						islandCreator.createIsland(StorageManager.islandsamount +1, p);
						for(Player player : Bukkit.getOnlinePlayers()){
							joinEvent.loadPlayerIslandInfo(player);
						}
					}
					
				}
				else if(a[0].equalsIgnoreCase("sethome") || a[0].equalsIgnoreCase("h")){
					if(PlayerManager.hasIsland(p)){
						if(PlayerManager.isOnOwnIsland(p, p.getLocation())){
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
						if(StorageManager.playersislandrank.get(p) == IslandRank.OWNER){
							if(a.length == 2){
							
								if(StorageManager.islandmembers.get(StorageManager.playersislandnumber.get(p)).size() != 9){
									Player p1 = null;
									for(Player player : Bukkit.getOnlinePlayers()){
										if(player.getName().equals(a[1])){
											p1 = player;
										}
									}
								
									
									if(p1 != null){
										int IslandNumber =  StorageManager.playersislandnumber.get(p);
										List<UUID> membersuuids = StorageManager.islandmembers.get(IslandNumber);
										if(!StorageManager.playerhasisland.containsKey(p1)){
											p.sendMessage(Start.TAG + "§d" + a[1] + "§7 has been invited to your §dIsland§7!");
											p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 5, 1);
											StorageManager.playersislandinvite.put(p1, IslandNumber);
											p1.sendMessage(Start.TAG + "§d" + p.getName() + "§7 has invited you to their §dIsland§7!");
											p1.sendMessage(Start.TAG + "Type §d/is accept§7 to §aAccept§7 or §d/is deny§7 to §cDeny§7.");
											p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 5, 1);
											
											for(Player player : Bukkit.getOnlinePlayers()){
												if(membersuuids.contains(player.getUniqueId())){
													player.sendMessage(Start.TAG + "§d" + p.getName() + "§7 invited §d" + a[1] + "§7 to your §dIsland§7!");
													player.playSound(player.getLocation(), Sound.SUCCESSFUL_HIT, 5, 1);
												}
											}
											
											TitleManager.setTitle(p, "", "§7Invite sent!");
										}
										else{
											if(StorageManager.playerhasisland.get(p1) == false){
												p.sendMessage(Start.TAG + "§d" + a[1] + "§7 has been invited to your §dIsland§7!");
												p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 5, 1);
												StorageManager.playersislandinvite.put(p1, IslandNumber);
												p1.sendMessage(Start.TAG + "§d" + p.getName() + "§7 has invited you to their §dIsland§7!");
												p1.sendMessage(Start.TAG + "Type §d/is accept§7 to §aAccept§7 or §d/is deny§7 to §cDeny§7.");
												p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 5, 1);
												
												for(Player player : Bukkit.getOnlinePlayers()){
													if(membersuuids.contains(player.getUniqueId())){
														player.sendMessage(Start.TAG + "§d" + p.getName() + "§7 invited §d" + a[1] + "§7 to your §dIsland§7!");
														player.playSound(player.getLocation(), Sound.SUCCESSFUL_HIT, 5, 1);
													}
												}
												
												TitleManager.setTitle(p, "", "§7Invite sent!");
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
						if(StorageManager.playersislandrank.get(p) == IslandRank.OWNER){
							if(a.length == 2){
								int IslandNumber = StorageManager.playersislandnumber.get(p);
								
								if(!a[1].equals(p.getName())){
									if(StorageManager.islandmembers.get(IslandNumber).contains(getUUIDfromString(a[1]))){
										
										List<UUID> membersuuids = StorageManager.islandmembers.get(IslandNumber);
										membersuuids.remove(getUUIDfromString(a[1]));
										IslandManager.setIslandMembers(IslandNumber, membersuuids);
										
										ConfigManager.playerdata.set("players." + getUUIDfromString(a[1]), null);
										ConfigManager.savePlayerdata();
										
										for(Player player : Bukkit.getOnlinePlayers()){
											if(membersuuids.contains(player.getUniqueId())){
												player.sendMessage(Start.TAG + "§d" + p.getName() + "§7 removed §d" + a[1] + "§7 from your §dIsland§7!");
												player.playSound(player.getLocation(), Sound.SUCCESSFUL_HIT, 5, 1);
											}
											if(player.getName().equals(a[1])){
												StorageManager.playerhasisland.put(player, false);
												player.sendMessage(Start.TAG + "§d" + p.getName() + "§7 removed you from their §dIsland§7!");
												player.playSound(player.getLocation(), Sound.SUCCESSFUL_HIT, 5, 1);
												clearInventory(player);
												player.chat("/spawn");
											}
										}
										
										p.sendMessage(Start.TAG + "You removed §d" + a[1] + "§7 from your §dIsland§7!");
										p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 5, 1);
										
										for(String s : ConfigManager.islands.getConfigurationSection("islands").getKeys(false)){
											int IslandNumbers = Integer.parseInt(s);
											
											StorageManager.islandlocation.put(IslandNumbers, IslandManager.getIslandLocation(IslandNumbers));
											StorageManager.islandmembers.put(IslandNumbers, IslandManager.getIslandMembers(IslandNumbers));
											StorageManager.islandowner.put(IslandNumbers, IslandManager.getIslandOwner(IslandNumbers));
											StorageManager.islandcreateddate.put(IslandNumbers, IslandManager.getIslandCreatedDate(IslandNumbers));
											StorageManager.islandteleportenabled.put(IslandNumbers, IslandManager.getIslandTeleportEnabled(IslandNumbers));
										}
										for(Player player : Bukkit.getOnlinePlayers()){
											joinEvent.loadPlayerIslandInfo(player);
										}
										
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
					if(StorageManager.playersislandinvite.containsKey(p)){
						if(!PlayerManager.hasIsland(p)){
							int IslandNumber = StorageManager.playersislandinvite.get(p);
							List<UUID> membersuuids = StorageManager.islandmembers.get(IslandNumber);
							if(membersuuids.size() != 9){
								StorageManager.playerhasisland.put(p, true);
								StorageManager.playersislandinvite.remove(p);
								for(Player player : Bukkit.getOnlinePlayers()){
									if(membersuuids.contains(player.getUniqueId()) || StorageManager.islandowner.get(IslandNumber) == player.getUniqueId()){
										player.sendMessage(Start.TAG + "§d" + p.getName() + "§7 joined your §dIsland§7!");
										player.playSound(player.getLocation(), Sound.SUCCESSFUL_HIT, 5, 1);
									}
								}
								p.sendMessage(Start.TAG + "You joined §d" + getPlayerNamefromUUID(StorageManager.islandowner.get(IslandNumber)) + "s Island§7!");
								p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 5, 1);
								
								IslandManager.setPlayersIslandNumber(p, IslandNumber);
								IslandManager.setPlayersIslandHomeLocation(p, IslandManager.getOfflinePlayersIslandHomeLocation(StorageManager.islandowner.get(IslandNumber)));
								IslandManager.setPlayersIslandRank(p, IslandRank.MEMBER);
								
								IslandManager.addIslandMember(IslandNumber, p);
								IslandManager.resetChallenges(p);
								
								clearInventory(p);
								p.sendMessage(Start.TAG + "§7Teleporting to your §dIsland§7...");
								new BukkitRunnable(){
									public void run(){
										p.teleport(StorageManager.playersislandhomelocation.get(p));
										p.playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 5, 1);
										TitleManager.setTitle(p, "", "§7Teleported to your §dIsland§7.");
									}
								}.runTaskLater(this, 10);
								
								for(String s : ConfigManager.islands.getConfigurationSection("islands").getKeys(false)){
									int IslandNumbers = Integer.parseInt(s);
									
									StorageManager.islandlocation.put(IslandNumbers, IslandManager.getIslandLocation(IslandNumbers));
									StorageManager.islandmembers.put(IslandNumbers, IslandManager.getIslandMembers(IslandNumbers));
									StorageManager.islandowner.put(IslandNumbers, IslandManager.getIslandOwner(IslandNumbers));
									StorageManager.islandcreateddate.put(IslandNumbers, IslandManager.getIslandCreatedDate(IslandNumbers));
									StorageManager.islandteleportenabled.put(IslandNumbers, IslandManager.getIslandTeleportEnabled(IslandNumbers));
								}
								for(Player player : Bukkit.getOnlinePlayers()){
									joinEvent.loadPlayerIslandInfo(player);
								}
							}
							else{
								p.sendMessage(Start.TAG + "§4§lDENIED§7 That §dIsland§7 reached the §aPlayer Limit§7!");
								StorageManager.playersislandinvite.remove(p);
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
					if(StorageManager.playersislandinvite.containsKey(p)){
						if(!PlayerManager.hasIsland(p)){
							int IslandNumber = StorageManager.playersislandinvite.get(p);
							StorageManager.playersislandinvite.remove(p);
							
							for(Player player : Bukkit.getOnlinePlayers()){
								if(StorageManager.islandowner.get(IslandNumber) == player.getUniqueId()){
									player.sendMessage(Start.TAG + "§d" + p.getName() + "§7 denied your §dinvite§7!");
									player.playSound(player.getLocation(), Sound.SUCCESSFUL_HIT, 5, 1);
								}
							}
							p.sendMessage(Start.TAG + "You denied §d" + getPlayerNamefromUUID(StorageManager.islandowner.get(IslandNumber)) + "s invite§7!");
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
						IslandRank rank = StorageManager.playersislandrank.get(p);
						int IslandNumber = StorageManager.playersislandnumber.get(p);
						List<UUID> members = StorageManager.islandmembers.get(IslandNumber);
						if(rank == IslandRank.OWNER){
							if(members.size() == 0){
								
								ConfigManager.playerdata.set("players." + p.getUniqueId().toString(), null);
								ConfigManager.savePlayerdata();
								
								
								StorageManager.playerhasisland.put(p, false);
								p.teleport(StorageManager.spawn);
								clearInventory(p);
								p.sendMessage(Start.TAG + "You left your §dIsland§7!");
								TitleManager.setTitle(p, "", "§7You left your §dIsland§7!");
								p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 5, 1);
							}
							else{
								UUID pluuid = members.get(0);
								
								members.remove(pluuid);
								IslandManager.setIslandMembers(IslandNumber, members);
								IslandManager.setIslandOfflineOwner(IslandNumber, pluuid);
								IslandManager.setOfflinePlayersIslandRank(pluuid, IslandRank.OWNER);
								
								ConfigManager.playerdata.set("players." + p.getName(), null);
								ConfigManager.savePlayerdata();
								StorageManager.playerhasisland.put(p, false);
								clearInventory(p);
								p.teleport(StorageManager.spawn);
								p.sendMessage(Start.TAG + "You left your §dIsland§7!");
								TitleManager.setTitle(p, "", "§7You left your §dIsland§7!");
								p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 5, 1);
								
								
								for(Player player : Bukkit.getOnlinePlayers()){
									if(members.contains(player.getName())){
										player.sendMessage(Start.TAG + "§d" + p.getName() + "§7 left your §dIsland§7!");
										player.sendMessage(Start.TAG + "§d" + getPlayerNamefromUUID(pluuid) + "§7 is now the §downer§7 of your §dIsland§7!");
										player.playSound(player.getLocation(), Sound.SUCCESSFUL_HIT, 5, 1);
									}
									if(player.getUniqueId() == StorageManager.islandowner.get(IslandNumber)){
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
								if(members.contains(player.getName()) || player.getUniqueId() == StorageManager.islandowner.get(IslandNumber)){
									player.sendMessage(Start.TAG + "§d" + p.getName() + "§7 left your §dIsland§7!");
									player.playSound(player.getLocation(), Sound.SUCCESSFUL_HIT, 5, 1);
								}
							}
							StorageManager.playerhasisland.put(p, false);
							p.teleport(StorageManager.spawn);
							clearInventory(p);
							p.sendMessage(Start.TAG + "You left your §dIsland§7!");
							TitleManager.setTitle(p, "", "§7You left your §dIsland§7!");
							p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 5, 1);
						}
						
						for(String s : ConfigManager.islands.getConfigurationSection("islands").getKeys(false)){
							int IslandNumbers = Integer.parseInt(s);
							
							StorageManager.islandlocation.put(IslandNumbers, IslandManager.getIslandLocation(IslandNumbers));
							StorageManager.islandmembers.put(IslandNumbers, IslandManager.getIslandMembers(IslandNumbers));
							StorageManager.islandowner.put(IslandNumbers, IslandManager.getIslandOwner(IslandNumbers));
							StorageManager.islandcreateddate.put(IslandNumbers, IslandManager.getIslandCreatedDate(IslandNumbers));
							StorageManager.islandteleportenabled.put(IslandNumbers, IslandManager.getIslandTeleportEnabled(IslandNumbers));
						}
						for(Player player : Bukkit.getOnlinePlayers()){
							joinEvent.loadPlayerIslandInfo(player);
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
								int IslandNumber = StorageManager.playersislandnumber.get(p1);
								if(StorageManager.islandteleportenabled.get(IslandNumber) == true){
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
				p1.teleport(StorageManager.playersislandhomelocation.get(p2));
				p1.playSound(p1.getLocation(), Sound.ENDERMAN_TELEPORT, 5, 1);
				TitleManager.setTitle(p1, "", "§7Teleported to §d" + p2.getName() + "s Island§7.");
			}
		}.runTaskLater(this, 10);
	}

}
