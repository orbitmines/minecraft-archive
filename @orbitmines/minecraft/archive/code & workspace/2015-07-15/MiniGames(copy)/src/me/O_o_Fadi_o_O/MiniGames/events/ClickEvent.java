package me.O_o_Fadi_o_O.MiniGames.events;

import me.O_o_Fadi_o_O.MiniGames.DisguisePlayer;
import me.O_o_Fadi_o_O.MiniGames.Start;
import me.O_o_Fadi_o_O.MiniGames.CosmeticPerks.ChatColors;
import me.O_o_Fadi_o_O.MiniGames.CosmeticPerks.CosmeticPerks;
import me.O_o_Fadi_o_O.MiniGames.CosmeticPerks.Disguises;
import me.O_o_Fadi_o_O.MiniGames.CosmeticPerks.Fireworks;
import me.O_o_Fadi_o_O.MiniGames.CosmeticPerks.Gadgets;
import me.O_o_Fadi_o_O.MiniGames.CosmeticPerks.Hats;
import me.O_o_Fadi_o_O.MiniGames.CosmeticPerks.PetRenameGUI;
import me.O_o_Fadi_o_O.MiniGames.CosmeticPerks.Pets;
import me.O_o_Fadi_o_O.MiniGames.CosmeticPerks.Trails;
import me.O_o_Fadi_o_O.MiniGames.CosmeticPerks.Wardrobe;
import me.O_o_Fadi_o_O.MiniGames.Inventories.ChickenFightInv;
import me.O_o_Fadi_o_O.MiniGames.Inventories.MiniGameSelector;
import me.O_o_Fadi_o_O.MiniGames.Inventories.SurvivalGamesInv;
import me.O_o_Fadi_o_O.MiniGames.managers.ChickenFight;
import me.O_o_Fadi_o_O.MiniGames.managers.ConfirmManager;
import me.O_o_Fadi_o_O.MiniGames.managers.DatabaseManager;
import me.O_o_Fadi_o_O.MiniGames.managers.PlayerManager;
import me.O_o_Fadi_o_O.MiniGames.managers.StorageManager;
import me.O_o_Fadi_o_O.MiniGames.managers.SurvivalGames;
import me.O_o_Fadi_o_O.MiniGames.utils.ChatColor;
import me.O_o_Fadi_o_O.MiniGames.utils.Currency;
import me.O_o_Fadi_o_O.MiniGames.utils.Disguise;
import me.O_o_Fadi_o_O.MiniGames.utils.Game;
import me.O_o_Fadi_o_O.MiniGames.utils.Hat;
import me.O_o_Fadi_o_O.MiniGames.utils.Pet;
import me.O_o_Fadi_o_O.MiniGames.utils.ReflectionUtil;
import me.O_o_Fadi_o_O.MiniGames.utils.Server;
import me.O_o_Fadi_o_O.MiniGames.utils.StaffRank;
import me.O_o_Fadi_o_O.MiniGames.utils.Trail;
import me.O_o_Fadi_o_O.MiniGames.utils.TrailType;
import me.O_o_Fadi_o_O.MiniGames.utils.VIPRank;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Result;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.scheduler.BukkitRunnable;

public class ClickEvent implements Listener {
	
	Start start = Start.getInstance();
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onClick(InventoryClickEvent e){
		
		if(e.getWhoClicked() instanceof Player){
				
			final Player p = (Player) e.getWhoClicked();
			ItemStack item = e.getCurrentItem();
				
				if(e.getInventory().getName().equals("§0§lStats")){
					
					e.setCancelled(true);
				}
				if(e.getInventory().getName().equals("§0§lGame Effects")){
					
					if(item.getType() != null && item.getType() == Material.SNOW_BALL && item.getItemMeta().getDisplayName().equals("§f§l§o+100% Coins Booster")){
						if(!StorageManager.usedeffectinchickenfight.contains(p)){
							if(StorageManager.minigamecoins.get(p) >= 1){
								
								DatabaseManager.removeVIPPoints(p, 1);
								
								String s = "";
								if(p.hasPermission("Rank.Owner")){
									s = ("§4§lOwner §4" + p.getName() + " §7activated a §f§l+100% Coins Booster§7!");
								}
								else if(p.hasPermission("Rank.Moderator")){
									s = ("§b§lMod §b" + p.getName() + " §7activated a §f§l+100% Coins Booster§7!");
								}
								else if(p.hasPermission("Rank.Builder")){
									s = ("§d§lBuilder §d" + p.getName() + " §7activated a §f§l+100% Coins Booster§7!");
								}
								else if(p.hasPermission("Rank.Emerald")){
									s = ("§a§lEmerald §a" + p.getName() + " §7activated a §f§l+100% Coins Booster§7!");
								}
								else if(p.hasPermission("Rank.Diamond")){
									s = ("§9§lDiamond §9" + p.getName() + " §7activated a §f§l+100% Coins Booster§7!");
								}
								else if(p.hasPermission("Rank.Gold")){
									s = ("§6§lGold §6" + p.getName() + " §7activated a §f§l+100% Coins Booster§7!");
								}
								else if(p.hasPermission("Rank.Iron")){
									s = ("§7§lIron §7" + p.getName() + " §7activated a §f§l+100% Coins Booster§7!");
								}
								else{
									s = ("§8" + p.getName() + " §7activated a §f§l+100% Coins Booster§7!");
								}
								for(Player player : Bukkit.getOnlinePlayers()){
									if(StorageManager.playersgame.containsKey(player)){
										if(StorageManager.playersgame.get(player) == Game.CHICKENFIGHT){
											if(StorageManager.playersarena.get(player).equals(StorageManager.playersarena.get(p))){
												player.playSound(player.getLocation(), Sound.LEVEL_UP, 5, 1);
												player.sendMessage(s);
											}
										}
									}
								}
								StorageManager.usedeffectinchickenfight.add(p);
								StorageManager.chickenfightboostermultiply.put(StorageManager.playersarena.get(p), StorageManager.chickenfightboostermultiply.get(StorageManager.playersarena.get(p)) + 1);
								p.closeInventory();
								
							}
							else{
								p.sendMessage("§9You don't have enough§b VIP Points§9!");
							}
						}
						else{
							p.sendMessage("§c§oYou already activated a Game Effect this round!");
						}
					}
					if(item.getType() != null && item.getType() == Material.SNOW_BALL && item.getItemMeta().getDisplayName().equals("§f§l§o+200% Coins Booster")){
						if(p.hasPermission("Rank.Emerald")){
							if(!StorageManager.usedeffectinchickenfight.contains(p)){
								if(StorageManager.minigamecoins.get(p) >= 1){
									
									DatabaseManager.removeVIPPoints(p, 1);
									
									String s = "";
									if(p.hasPermission("Rank.Owner")){
										s = ("§4§lOwner §4" + p.getName() + " §7activated a §f§l+200% Coins Booster§7!");
									}
									else if(p.hasPermission("Rank.Moderator")){
										s = ("§b§lMod §b" + p.getName() + " §7activated a §f§l+200% Coins Booster§7!");
									}
									else if(p.hasPermission("Rank.Builder")){
										s = ("§d§lBuilder §d" + p.getName() + " §7activated a §f§l+200% Coins Booster§7!");
									}
									else if(p.hasPermission("Rank.Emerald")){
										s = ("§a§lEmerald §a" + p.getName() + " §7activated a §f§l+200% Coins Booster§7!");
									}
									else if(p.hasPermission("Rank.Diamond")){
										s = ("§9§lDiamond §9" + p.getName() + " §7activated a §f§l+200% Coins Booster§7!");
									}
									else if(p.hasPermission("Rank.Gold")){
										s = ("§6§lGold §6" + p.getName() + " §7activated a §f§l+200% Coins Booster§7!");
									}
									else if(p.hasPermission("Rank.Iron")){
										s = ("§7§lIron §7" + p.getName() + " §7activated a §f§l+200% Coins Booster§7!");
									}
									else{
										s = ("§8" + p.getName() + " §7activated a §f§l+200% Coins Booster§7!");
									}
									for(Player player : Bukkit.getOnlinePlayers()){
										if(StorageManager.playersgame.containsKey(player)){
											if(StorageManager.playersgame.get(player) == Game.CHICKENFIGHT){
												if(StorageManager.playersarena.get(player).equals(StorageManager.playersarena.get(p))){
													player.playSound(player.getLocation(), Sound.LEVEL_UP, 5, 1);
													player.sendMessage(s);
												}
											}
										}
									}
									StorageManager.usedeffectinchickenfight.add(p);
									StorageManager.chickenfightboostermultiply.put(StorageManager.playersarena.get(p), StorageManager.chickenfightboostermultiply.get(StorageManager.playersarena.get(p)) + 2);
									p.closeInventory();
									
								}
								else{
									p.sendMessage("§9You don't have enough§b VIP Points§9!");
								}
							}
							else{
								p.sendMessage("§c§oYou already activated a Game Effect this round!");
							}
						}
						else{
							p.playSound(p.getLocation(), Sound.LAVA_POP, 5, 1);
							p.sendMessage("§9Cosmetic Perks §8| §4§lDENIED §7Required: §a§lEmerald VIP§7!");
						}
					}
					if(item.getType() != null && item.getType() == Material.POTION && item.getItemMeta().getDisplayName().equals("§9§l§oSpeed 4")){
						if(!StorageManager.usedeffectinchickenfight.contains(p)){
							if(StorageManager.chickenfightgameeffectspeed.get(StorageManager.playersarena.get(p)) == false){
								if(StorageManager.minigamecoins.get(p) >= 1){
									
									DatabaseManager.removeVIPPoints(p, 1);
									
									String s = "";
									if(p.hasPermission("Rank.Owner")){
										s = ("§4§lOwner §4" + p.getName() + " §7activated a §9§lSpeed 4 Effect§7!");
									}
									else if(p.hasPermission("Rank.Moderator")){
										s = ("§b§lMod §b" + p.getName() + " §7activated a §9§lSpeed 4 Effect§7!");
									}
									else if(p.hasPermission("Rank.Builder")){
										s = ("§d§lBuilder §d" + p.getName() + " §7activated a §9§lSpeed 4 Effect§7!");
									}
									else if(p.hasPermission("Rank.Emerald")){
										s = ("§a§lEmerald §a" + p.getName() + " §7activated a §9§lSpeed 4 Effect§7!");
									}
									else if(p.hasPermission("Rank.Diamond")){
										s = ("§9§lDiamond §9" + p.getName() + " §7activated a §9§lSpeed 4 Effect§7!");
									}
									else if(p.hasPermission("Rank.Gold")){
										s = ("§6§lGold §6" + p.getName() + " §7activated a §9§lSpeed 4 Effect§7!");
									}
									else if(p.hasPermission("Rank.Iron")){
										s = ("§7§lIron §7" + p.getName() + " §7activated a §9§lSpeed 4 Effect§7!");
									}
									else{
										s = ("§8" + p.getName() + " §7activated a §9§lSpeed 4 Effect§7!");
									}
									for(Player player : Bukkit.getOnlinePlayers()){
										if(StorageManager.playersgame.containsKey(player)){
											if(StorageManager.playersgame.get(player) == Game.CHICKENFIGHT){
												if(StorageManager.playersarena.get(player).equals(StorageManager.playersarena.get(p))){
													player.playSound(player.getLocation(), Sound.LEVEL_UP, 5, 1);
													player.sendMessage(s);
												}
											}
										}
									}
									StorageManager.usedeffectinchickenfight.add(p);
									StorageManager.chickenfightgameeffectspeed.put(StorageManager.playersarena.get(p), true);
									p.closeInventory();
									
								}
								else{
									p.sendMessage("§9You don't have enough§b VIP Points§9!");
								}
							}
							else{
								p.sendMessage("§c§oThe §f§lSpeed 4 Effect§c§o has already been activated this round!");
							}
						}
						else{
							p.sendMessage("§c§oYou already activated a Game Effect this round!");
						}
					}
					if(item.getType() != null && item.getType() == Material.INK_SACK && item.getItemMeta().getDisplayName().equals("§8§l§oBlindness")){
						if(!StorageManager.usedeffectinchickenfight.contains(p)){
							if(StorageManager.chickenfightgameeffectblindness.get(StorageManager.playersarena.get(p)) == false){
								if(StorageManager.minigamecoins.get(p) >= 1){
									
									DatabaseManager.removeVIPPoints(p, 1);
									
									String s = "";
									if(p.hasPermission("Rank.Owner")){
										s = ("§4§lOwner §4" + p.getName() + " §7activated a §8§lBlindness Effect§7!");
									}
									else if(p.hasPermission("Rank.Moderator")){
										s = ("§b§lMod §b" + p.getName() + " §7activated a §8§lBlindness Effect§7!");
									}
									else if(p.hasPermission("Rank.Builder")){
										s = ("§d§lBuilder §d" + p.getName() + " §7activated a §8§lBlindness Effect§7!");
									}
									else if(p.hasPermission("Rank.Emerald")){
										s = ("§a§lEmerald §a" + p.getName() + " §7activated a §8§lBlindness Effect§7!");
									}
									else if(p.hasPermission("Rank.Diamond")){
										s = ("§9§lDiamond §9" + p.getName() + " §7activated a §8§lBlindness Effect§7!");
									}
									else if(p.hasPermission("Rank.Gold")){
										s = ("§6§lGold §6" + p.getName() + " §7activated a §8§lBlindness Effect§7!");
									}
									else if(p.hasPermission("Rank.Iron")){
										s = ("§7§lIron §7" + p.getName() + " §7activated a §8§lBlindness Effect§7!");
									}
									else{
										s = ("§8" + p.getName() + " §7activated a §8§lBlindness Effect§7!");
									}
									for(Player player : Bukkit.getOnlinePlayers()){
										if(StorageManager.playersgame.containsKey(player)){
											if(StorageManager.playersgame.get(player) == Game.CHICKENFIGHT){
												if(StorageManager.playersarena.get(player).equals(StorageManager.playersarena.get(p))){
													player.playSound(player.getLocation(), Sound.LEVEL_UP, 5, 1);
													player.sendMessage(s);
												}
											}
										}
									}
									StorageManager.usedeffectinchickenfight.add(p);
									StorageManager.chickenfightgameeffectblindness.put(StorageManager.playersarena.get(p), true);
									p.closeInventory();
									
								}
								else{
									p.sendMessage("§9You don't have enough§b VIP Points§9!");
								}
							}
							else{
								p.sendMessage("§c§oThe §8§lBlindness Effect§c§o has already been activated this round!");
							}
						}
						else{
							p.sendMessage("§c§oYou already activated a Game Effect this round!");
						}
					}
					if(item.getType() != null && item.getType() == Material.LEATHER_BOOTS && item.getItemMeta().getDisplayName().equals("§7§l§oJump Boost 5")){
						if(!StorageManager.usedeffectinchickenfight.contains(p)){
							if(StorageManager.chickenfightgameeffectjump.get(StorageManager.playersarena) == false){
								if(StorageManager.minigamecoins.get(p) >= 1){
									
									DatabaseManager.removeVIPPoints(p, 1);
									
									String s = "";
									if(p.hasPermission("Rank.Owner")){
										s = ("§4§lOwner §4" + p.getName() + " §7activated a §7§lJump Boost 5 Effect§7!");
									}
									else if(p.hasPermission("Rank.Moderator")){
										s = ("§b§lMod §b" + p.getName() + " §7activated a §7§lJump Boost 5 Effect§7!");
									}
									else if(p.hasPermission("Rank.Builder")){
										s = ("§d§lBuilder §d" + p.getName() + " §7activated a §7§lJump Boost 5 Effect§7!");
									}
									else if(p.hasPermission("Rank.Emerald")){
										s = ("§a§lEmerald §a" + p.getName() + " §7activated a §7§lJump Boost 5 Effect§7!");
									}
									else if(p.hasPermission("Rank.Diamond")){
										s = ("§9§lDiamond §9" + p.getName() + " §7activated a §7§lJump Boost 5 Effect§7!");
									}
									else if(p.hasPermission("Rank.Gold")){
										s = ("§6§lGold §6" + p.getName() + " §7activated a §7§lJump Boost 5 Effect§7!");
									}
									else if(p.hasPermission("Rank.Iron")){
										s = ("§7§lIron §7" + p.getName() + " §7activated a §7§lJump Boost 5 Effect§7!");
									}
									else{
										s = ("§8" + p.getName() + " §7activated a §8§lBlindness Effect§7!");
									}
									for(Player player : Bukkit.getOnlinePlayers()){
										if(StorageManager.playersgame.containsKey(player)){
											if(StorageManager.playersgame.get(player) == Game.CHICKENFIGHT){
												if(StorageManager.playersarena.get(player).equals(StorageManager.playersarena.get(p))){
													player.playSound(player.getLocation(), Sound.LEVEL_UP, 5, 1);
													player.sendMessage(s);
												}
											}
										}
									}
									StorageManager.usedeffectinchickenfight.add(p);
									StorageManager.chickenfightgameeffectjump.put(StorageManager.playersarena.get(p), true);
									p.closeInventory();
									
								}
								else{
									p.sendMessage("§9You don't have enough§b VIP Points§9!");
								}
							}
							else{
								p.sendMessage("§c§oThe §7§lJump Boost 5 Effect§c§o has already been activated this round!");
							}
						}
						else{
							p.sendMessage("§c§oYou already activated a Game Effect this round!");
						}
					}
				}
				
				
			}catch(Exception ex){}
		}
	}
}
