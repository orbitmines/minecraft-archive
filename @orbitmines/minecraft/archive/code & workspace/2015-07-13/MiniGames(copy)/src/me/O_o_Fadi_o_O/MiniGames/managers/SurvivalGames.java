package me.O_o_Fadi_o_O.MiniGames.managers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import me.O_o_Fadi_o_O.MiniGames.DisguisePlayer;
import me.O_o_Fadi_o_O.MiniGames.Start;
import me.O_o_Fadi_o_O.MiniGames.Kits.Others.LobbyKit;
import me.O_o_Fadi_o_O.MiniGames.Kits.Others.MiniGamesLobbyKit;
import me.O_o_Fadi_o_O.MiniGames.Kits.Others.SpectatorKit;
import me.O_o_Fadi_o_O.MiniGames.utils.Game;
import me.O_o_Fadi_o_O.MiniGames.utils.SurvivalGamesState;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Chest;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

public class SurvivalGames {
	
	@SuppressWarnings("deprecation")
	public static void prepareStart(int arena){
		StorageManager.survivalgamesstate.put(arena, SurvivalGamesState.LASTSECONDS);
		for(Player p : Bukkit.getOnlinePlayers()){
			
			if(StorageManager.playersinsurvivalgames.contains(p)){
				
				if(StorageManager.playersarena.get(p).equals(arena)){
				
					if(StorageManager.petentity.containsKey(p)){
						PlayerManager.removePet(p, "§7Pet");
					}
					if(PlayerManager.hasWardrobeArmor(p)){
						PlayerManager.removeWardrobeArmor(p);
					}
					if(StorageManager.disguise.containsKey(p)){
						DisguisePlayer.undisguisePlayer(p);
					}
					PlayerManager.removeGadget(p);
					if(PlayerManager.hasHat(p)){
						PlayerManager.removeHat(p);
					}
					
					if(p.getOpenInventory().getTopInventory() != null){
						p.closeInventory();
					}
					if(p.getVehicle() != null){
						p.leaveVehicle();
					}
				}
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	public static void startGame(int arena){
		StorageManager.survivalgamesstate.put(arena, SurvivalGamesState.INGAME);
		StorageManager.survivalgamesminutes.put(arena, 20);
		StorageManager.survivalgamesseconds.put(arena, 0);
		
		for(Player p : Bukkit.getOnlinePlayers()){
			
			if(StorageManager.playersinsurvivalgames.contains(p)){
				
				if(StorageManager.playersarena.get(p).equals(arena)){
				
					p.setFoodLevel(20);
					p.setHealth(20D);
					p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 5, 1);
					p.setLevel(0);
					p.setExp(0);
					p.playSound(p.getLocation(), Sound.ITEM_PICKUP, 5, 1);
					p.getInventory().setHeldItemSlot(0);
					
				}	
			}
		}
		
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "day day " + StorageManager.survivalgamesspectatorlocation.get(StorageManager.survivalgamescurrentmap.get(arena)).getWorld().getName());
	}
	
	@SuppressWarnings("deprecation")
	public static void joinArena(Player p, int arena){
		if(StorageManager.petentity.containsKey(p)){
			PlayerManager.removePet(p, "§7Pet");
		}
		if(StorageManager.disguise.containsKey(p)){
			DisguisePlayer.undisguisePlayer(p);
		}
		
		if(p.getVehicle() != null){
			p.leaveVehicle();
		}
		if(p.getPassenger() != null){
			if(p.getPassenger().getVehicle() != null){
				p.getPassenger().leaveVehicle();
			}
		}
	    ((CraftPlayer) p).getHandle().setInvisible(false);
		
	    StorageManager.playersgame.put(p, Game.SURVIVALGAMES);
	    StorageManager.playersarena.put(p, arena);
	    
		if(StorageManager.survivalgamesstate.get(arena).equals(SurvivalGamesState.LOBBY) || StorageManager.survivalgamesstate.get(arena).equals(SurvivalGamesState.LASTSECONDS)){
			
			StorageManager.playersinsurvivalgames.add(p);
			StorageManager.survivalgamesroundkills.put(p, 0);
			StorageManager.survivalgamesplayers.put(arena, StorageManager.survivalgamesplayers.get(arena) +1);
			
			for(Player player : Bukkit.getOnlinePlayers()){
				if(StorageManager.playersgame.containsKey(player)){
					if(StorageManager.playersgame.get(player) == Game.SURVIVALGAMES){
						if(StorageManager.playersarena.get(player).equals(arena)){
							player.sendMessage("§2§l§o>> " + p.getName() + " §2§o(§a§oSurvivalGames " + arena + "§2§o)");
						}
					}
				}
			}
			
			p.teleport(StorageManager.survivalgameslobbylocation.get(arena));
			LobbyKit.giveInventory(p);
		}
		else{
			StorageManager.spectatorsinsurvivalgames.add(p);
			StorageManager.survivalgamesspectators.put(arena, StorageManager.survivalgamesspectators.get(arena) +1);
			StorageManager.survivalgamesroundkills.put(p, 0);

			for(Player player : Bukkit.getOnlinePlayers()){
				if(StorageManager.playersgame.containsKey(player)){
					if(StorageManager.playersgame.get(player) == Game.SURVIVALGAMES){
						if(StorageManager.playersarena.get(player).equals(arena)){
							player.sendMessage("§2§l§o>> " + p.getName() + " §2§o(§a§oSurvivalGames " + arena + "§2§o) §c§o[Spectator]");
						}
					}
				}
			}
		    p.teleport(StorageManager.survivalgamesspectatorlocation.get(StorageManager.survivalgamescurrentmap.get(arena)));
		    ((CraftPlayer) p).getHandle().setInvisible(true);
		    SpectatorKit.giveInventory(p);
			p.setAllowFlight(true);
			p.setFlying(true);
		}
	}
	
	@SuppressWarnings("deprecation")
	public static void leaveArena(Player p, int arena){
		if(StorageManager.petentity.containsKey(p)){
			PlayerManager.removePet(p, "§7Pet");
		}
		if(StorageManager.disguise.containsKey(p)){
			DisguisePlayer.undisguisePlayer(p);
		}
	    ((CraftPlayer) p).getHandle().setInvisible(false);
		if(p.getVehicle() != null){
			p.leaveVehicle();
		}
		if(p.getPassenger() != null){
			if(p.getPassenger().getVehicle() != null){
				p.getPassenger().leaveVehicle();
			}
		}
		if(StorageManager.playersinsurvivalgames.contains(p)){
			StorageManager.playersinsurvivalgames.remove(p);
			StorageManager.survivalgamesplayers.put(arena, StorageManager.survivalgamesplayers.get(arena) -1);
			StorageManager.survivalgamesroundkills.remove(p);
			
			if(StorageManager.survivalgamesstate.get(arena) != SurvivalGamesState.ENDING){
				for(Player player : Bukkit.getOnlinePlayers()){
					if(StorageManager.playersgame.containsKey(player)){
						if(StorageManager.playersgame.get(player) == Game.SURVIVALGAMES){
							if(StorageManager.playersarena.get(player).equals(arena)){
								player.sendMessage("§4§l§o<< " + p.getName() + " §4§o(§c§oSurvivalGames " + arena + "§4§o)");
								p.showPlayer(player);
								player.showPlayer(p);
							}
						}
					}
				}
			}
			
		    StorageManager.playersgame.remove(p);
		    StorageManager.playersarena.remove(p);
			
			p.setAllowFlight(false);
			p.setFlying(false);
			p.setLevel(0);
			p.setExp(0);
			
			if(StorageManager.survivalgamesplayerspawn.containsKey(p)){
				StorageManager.survivalgamesplayerspawn.remove(p);
			}
			
			if(StorageManager.survivalgamesstate.get(arena).equals(SurvivalGamesState.INGAME) || StorageManager.survivalgamesstate.get(arena).equals(SurvivalGamesState.WARMUP) || StorageManager.survivalgamesstate.get(arena).equals(SurvivalGamesState.DEATHMATCH)){
				if(StorageManager.survivalgamesplayers.get(arena) == 1){
					endGame(arena);
				}
			}
			
			MiniGamesLobbyKit.giveInventory(p, true);
		}
		if(StorageManager.spectatorsinsurvivalgames.contains(p)){
			
			if(StorageManager.survivalgamesstate.get(arena) != SurvivalGamesState.ENDING){
				for(Player player : Bukkit.getOnlinePlayers()){
					if(StorageManager.playersgame.containsKey(player)){
						if(StorageManager.playersgame.get(player) == Game.SURVIVALGAMES){
							if(StorageManager.playersarena.get(player).equals(arena)){
								player.sendMessage("§4§l§o<< " + p.getName() + " §4§o(§c§oSurvivalGames " + arena + "§4§o) §c§o[Spectator]");
								p.showPlayer(player);
								player.showPlayer(p);
							}
						}
					}
				}
			}
		    StorageManager.playersgame.remove(p);
		    StorageManager.playersarena.remove(p);
			StorageManager.spectatorsinsurvivalgames.remove(p);
			StorageManager.survivalgamesspectators.put(arena, StorageManager.survivalgamesspectators.get(arena) -1);
			p.setAllowFlight(false);
			p.setFlying(false);
		}
		if(StorageManager.deadplayersinsurvivalgames.contains(p)){
			StorageManager.deadplayersinsurvivalgames.remove(p);
		}
		if(StorageManager.usedeffectinsurvivalgames.contains(p)){
			StorageManager.usedeffectinsurvivalgames.remove(p);
		}
		Start.teleportToLobby(p);
		Start.clearInventory(p);
		MiniGamesLobbyKit.giveInventory(p, true);
	}
	
	/*
	 * 
	 * Storing & Saving Data:
	 * 
	 */
	
	public static ArrayList<ItemStack> chestContents = new ArrayList<ItemStack>();
	
	public static void setRandomChestContents(Chest c){
		
		c.getInventory().clear();
		
		int i = c.getInventory().getSize();
		
		int itemamount = getRandomAmount(6);
		
		for(int in = 1; in <= itemamount; in++){
			
			chestContents.clear();
			registerAllItemStacksToContents();
			
			Random r2 = new Random();
			int i3 = r2.nextInt(chestContents.size());
			ItemStack item = chestContents.get(i3);
			
			Random r = new Random();
			int i2 = r.nextInt(i);
			c.getInventory().setItem(i2, item);
			
		}
		
		c.update();
		
	}
	
	public static void registerAllItemStacksToContents(){
		registerItemStackToContents(Material.STONE_SWORD, 1, false, "", (byte) 0, 8);
		registerItemStackToContents(Material.COOKED_BEEF, getRandomAmount(2), false, "", (byte) 0, 30);
		registerItemStackToContents(Material.DIAMOND, 1, false, "", (byte) 0, 2);
		registerItemStackToContents(Material.GOLD_AXE, 1, false, "", (byte) 0, 10);
		registerItemStackToContents(Material.CAKE, 1, false, "", (byte) 0, 15);
		registerItemStackToContents(Material.COOKED_CHICKEN, getRandomAmount(3), true, "§cChili Chicken", (byte) 0, 35);
		registerItemStackToContents(Material.BAKED_POTATO, getRandomAmount(4), false, "", (byte) 0, 40);
		registerItemStackToContents(Material.PORK, getRandomAmount(3), false, "", (byte) 0, 38);
		registerItemStackToContents(Material.WOOD_SWORD, 1, false, "", (byte) 0, 10);
		registerItemStackToContents(Material.CHAINMAIL_CHESTPLATE, 1, false, "", (byte) 0, 9);
		registerItemStackToContents(Material.CHAINMAIL_LEGGINGS, 1, false, "", (byte) 0, 8);
		registerItemStackToContents(Material.CHAINMAIL_HELMET, 1, false, "", (byte) 0, 10);
		registerItemStackToContents(Material.CHAINMAIL_BOOTS, 1, false, "", (byte) 0, 11);
		registerItemStackToContents(Material.LEATHER_CHESTPLATE, 1, false, "", (byte) 0, 21);
		registerItemStackToContents(Material.LEATHER_LEGGINGS, 1, false, "", (byte) 0, 22);
		registerItemStackToContents(Material.LEATHER_HELMET, 1, false, "", (byte) 0, 26);
		registerItemStackToContents(Material.LEATHER_BOOTS, 1, false, "", (byte) 0, 26);
		registerItemStackToContents(Material.IRON_HELMET, 1, false, "", (byte) 0, 8);
		registerItemStackToContents(Material.IRON_BOOTS, 1, false, "", (byte) 0, 8);
		registerItemStackToContents(Material.IRON_CHESTPLATE, 1, false, "", (byte) 0, 5);
		registerItemStackToContents(Material.IRON_LEGGINGS, 1, false, "", (byte) 0, 5);
		registerItemStackToContents(Material.GOLD_HELMET, 1, false, "", (byte) 0, 16);
		registerItemStackToContents(Material.GOLD_BOOTS, 1, false, "", (byte) 0, 17);
		registerItemStackToContents(Material.GOLD_CHESTPLATE, 1, false, "", (byte) 0, 14);
		registerItemStackToContents(Material.GOLD_LEGGINGS, 1, false, "", (byte) 0, 13);
		registerItemStackToContents(Material.STICK, getRandomAmount(2), false, "", (byte) 0, 13);
		registerItemStackToContents(Material.APPLE, getRandomAmount(2), false, "", (byte) 0, 17);
		registerItemStackToContents(Material.GOLD_INGOT, getRandomAmount(5), false, "", (byte) 0, 14);
		registerItemStackToContents(Material.IRON_INGOT, 1, false, "", (byte) 0, 4);
		registerItemStackToContents(Material.WOOD_AXE, 1, false, "", (byte) 0, 25);
		registerItemStackToContents(Material.STONE_AXE, 1, false, "", (byte) 0, 9);
		registerItemStackToContents(Material.BOW, 1, false, "", (byte) 0, 10);
		registerItemStackToContents(Material.ARROW, getRandomAmount(5), false, "", (byte) 0, 15);
		registerItemStackToContents(Material.ROTTEN_FLESH, getRandomAmount(4), false, "", (byte) 0, 45);
		registerItemStackToContents(Material.BOWL, getRandomAmount(4), false, "", (byte) 0, 30);
		registerItemStackToContents(Material.EXP_BOTTLE, getRandomAmount(5), false, "", (byte) 0, 13);
		registerItemStackToContents(Material.GOLD_SWORD, 1, false, "", (byte) 0, 11);
		registerItemStackToContents(Material.FISHING_ROD, 1, false, "", (byte) 0, 16);
		registerItemStackToContents(Material.FLINT, getRandomAmount(3), false, "", (byte) 0, 13);
		registerItemStackToContents(Material.FEATHER, getRandomAmount(3), false, "", (byte) 0, 11);
		registerItemStackToContents(Material.STRING, getRandomAmount(2), false, "", (byte) 0, 16);
		registerItemStackToContents(Material.FLINT_AND_STEEL, 1, false, "", (byte) 64, 6);
		registerItemStackToContents(Material.BOAT, 1, false, "", (byte) 0, 12);
		registerItemStackToContents(Material.WHEAT, getRandomAmount(5), false, "", (byte) 0, 8);
		registerItemStackToContents(Material.COOKIE, getRandomAmount(3), false, "", (byte) 0, 6);
		registerItemStackToContents(Material.MELON, getRandomAmount(5), false, "", (byte) 0, 8);
		registerItemStackToContents(Material.COOKED_FISH, getRandomAmount(2), false, "", (byte) 0, 9);
		registerItemStackToContents(Material.RAW_FISH, getRandomAmount(4), false, "", (byte) 0, 8);
		registerItemStackToContents(Material.CARROT_ITEM, getRandomAmount(2), false, "", (byte) 0, 11);
		registerItemStackToContents(Material.POTATO_ITEM, getRandomAmount(6), false, "", (byte) 0, 13);
		registerItemStackToContents(Material.COMPASS, 1, true, "§6§lPlayer Tracker", (byte) 0, 4);
		registerItemStackToContents(Material.TNT, getRandomAmount(3), false, "", (byte) 0, 6);
	}
	
	public static void registerItemStackToContents(Material material, int amount, boolean useCustomDisplayname, String Displayname, byte durability, int precent){
		for(int in = 1; in <= precent; in++){
			ItemStack item = new ItemStack(material, amount);
			if(useCustomDisplayname == true){
				ItemMeta meta = item.getItemMeta();
				meta.setDisplayName(Displayname);
				item.setItemMeta(meta);
			}
			item.setDurability(durability);
			
			chestContents.add(item);
		}
	}
	
    public static int getRandomAmount(int highest) {
        Random random = new Random();
        return random.nextInt((highest - 1) + 1) + 1;
    }
	
	public static void startArenaData(int arena){
		
		if(arena == 1){
			for(int in = 1; in <= StorageManager.survivalgamesallchestsarena1.size(); in++){
				
				Chest c = StorageManager.survivalgamesallchestsarena1.get(in);
				
				StorageManager.survivalgameschests.remove(c);
			}
			StorageManager.survivalgamesallchestsarena1.clear();
		}
		
		StorageManager.survivalgamesminutes.put(arena, 1);
		StorageManager.survivalgamesseconds.put(arena, 0);
		
		StorageManager.survivalgamesboostermultiply.put(arena, 0);
		
		StorageManager.survivalgameswinner.put(arena, null);
		
		StorageManager.survivalgamesplayers.put(arena, 0);
		StorageManager.survivalgamesspectators.put(arena, 0);
		StorageManager.survivalgamesplayersdied.put(arena, 0);
	
		if(StorageManager.survivalgamescurrentmap.containsKey(arena)){
			StorageManager.survivalgamescurrentmap.remove(arena);
		}
		StorageManager.survivalgamescurrentmap.put(arena, getRandomMap(arena));
		
		StorageManager.survivalgamesstate.put(arena, SurvivalGamesState.LOBBY);
		spawns.put(arena, 0);
	}
}
