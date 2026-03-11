package me.O_o_Fadi_o_O.OrbitMines.managers;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

import me.O_o_Fadi_o_O.OrbitMines.utils.OMPlayer;
import me.O_o_Fadi_o_O.OrbitMines.utils.ServerData;
import me.O_o_Fadi_o_O.OrbitMines.utils.Utils;
import me.O_o_Fadi_o_O.OrbitMines.utils.Utils.ChatColor;
import me.O_o_Fadi_o_O.OrbitMines.utils.Utils.Currency;
import me.O_o_Fadi_o_O.OrbitMines.utils.Utils.Disguise;
import me.O_o_Fadi_o_O.OrbitMines.utils.Utils.Gadget;
import me.O_o_Fadi_o_O.OrbitMines.utils.Utils.Hat;
import me.O_o_Fadi_o_O.OrbitMines.utils.Utils.MiniGameType;
import me.O_o_Fadi_o_O.OrbitMines.utils.Utils.Pet;
import me.O_o_Fadi_o_O.OrbitMines.utils.Utils.Server;
import me.O_o_Fadi_o_O.OrbitMines.utils.Utils.StaffRank;
import me.O_o_Fadi_o_O.OrbitMines.utils.Utils.Trail;
import me.O_o_Fadi_o_O.OrbitMines.utils.Utils.TrailType;
import me.O_o_Fadi_o_O.OrbitMines.utils.Utils.VIPRank;
import me.O_o_Fadi_o_O.OrbitMines.utils.creative.CreativePlayer;
import me.O_o_Fadi_o_O.OrbitMines.utils.creative.CreativeUtils.PlotType;
import me.O_o_Fadi_o_O.OrbitMines.utils.creative.Inventories.PlotInfoInv;
import me.O_o_Fadi_o_O.OrbitMines.utils.creative.Inventories.PlotKitInv;
import me.O_o_Fadi_o_O.OrbitMines.utils.creative.Inventories.PlotKitNameGUI;
import me.O_o_Fadi_o_O.OrbitMines.utils.creative.Inventories.PlotMembersInv;
import me.O_o_Fadi_o_O.OrbitMines.utils.creative.Inventories.PlotSetupInv;
import me.O_o_Fadi_o_O.OrbitMines.utils.creative.Plot;
import me.O_o_Fadi_o_O.OrbitMines.utils.hub.Inventories.ChatColorInv;
import me.O_o_Fadi_o_O.OrbitMines.utils.hub.Inventories.ConfirmInv;
import me.O_o_Fadi_o_O.OrbitMines.utils.hub.Inventories.CosmeticPerksInv;
import me.O_o_Fadi_o_O.OrbitMines.utils.hub.Inventories.DisguiseInv;
import me.O_o_Fadi_o_O.OrbitMines.utils.hub.Inventories.FireworkInv;
import me.O_o_Fadi_o_O.OrbitMines.utils.hub.Inventories.GadgetInv;
import me.O_o_Fadi_o_O.OrbitMines.utils.hub.Inventories.HatInv;
import me.O_o_Fadi_o_O.OrbitMines.utils.hub.Inventories.InventoryInv;
import me.O_o_Fadi_o_O.OrbitMines.utils.hub.Inventories.OMTShopInv;
import me.O_o_Fadi_o_O.OrbitMines.utils.hub.Inventories.PetInv;
import me.O_o_Fadi_o_O.OrbitMines.utils.hub.Inventories.PetRenameGUI;
import me.O_o_Fadi_o_O.OrbitMines.utils.hub.Inventories.ServerSelectorInv;
import me.O_o_Fadi_o_O.OrbitMines.utils.hub.Inventories.SettingsInv;
import me.O_o_Fadi_o_O.OrbitMines.utils.hub.Inventories.TeleporterInv;
import me.O_o_Fadi_o_O.OrbitMines.utils.hub.Inventories.TrailInv;
import me.O_o_Fadi_o_O.OrbitMines.utils.hub.Inventories.TrailSettingsInv;
import me.O_o_Fadi_o_O.OrbitMines.utils.hub.Inventories.WardrobeInv;
import me.O_o_Fadi_o_O.OrbitMines.utils.kitpvp.Inventories.BoosterInv;
import me.O_o_Fadi_o_O.OrbitMines.utils.kitpvp.Inventories.KitInv;
import me.O_o_Fadi_o_O.OrbitMines.utils.kitpvp.Inventories.KitSelectorInv;
import me.O_o_Fadi_o_O.OrbitMines.utils.kitpvp.Inventories.MasteryInv;
import me.O_o_Fadi_o_O.OrbitMines.utils.kitpvp.KitPvPPlayer;
import me.O_o_Fadi_o_O.OrbitMines.utils.kitpvp.KitPvPUtils.Booster;
import me.O_o_Fadi_o_O.OrbitMines.utils.kitpvp.KitPvPUtils.KitPvPKit;
import me.O_o_Fadi_o_O.OrbitMines.utils.kitpvp.KitPvPUtils.Mastery;
import me.O_o_Fadi_o_O.OrbitMines.utils.minigames.Arena;
import me.O_o_Fadi_o_O.OrbitMines.utils.minigames.Inventories.GameEffectsInv;
import me.O_o_Fadi_o_O.OrbitMines.utils.minigames.Inventories.MGKitInv;
import me.O_o_Fadi_o_O.OrbitMines.utils.minigames.Inventories.MiniGameTicketInv;
import me.O_o_Fadi_o_O.OrbitMines.utils.minigames.Inventories.MiniGamesInv;
import me.O_o_Fadi_o_O.OrbitMines.utils.minigames.Inventories.StatsInv;
import me.O_o_Fadi_o_O.OrbitMines.utils.minigames.Inventories.TicketInv;
import me.O_o_Fadi_o_O.OrbitMines.utils.minigames.MiniGamesUtils.InvType;
import me.O_o_Fadi_o_O.OrbitMines.utils.minigames.MiniGamesUtils.TicketType;
import me.O_o_Fadi_o_O.OrbitMines.utils.prison.Inventories.GoldShopInv;
import me.O_o_Fadi_o_O.OrbitMines.utils.prison.Inventories.MineInv;
import me.O_o_Fadi_o_O.OrbitMines.utils.prison.Inventories.VillagerGambleInv;
import me.O_o_Fadi_o_O.OrbitMines.utils.prison.Mine;
import me.O_o_Fadi_o_O.OrbitMines.utils.prison.PrisonPlayer;
import me.O_o_Fadi_o_O.OrbitMines.utils.prison.PrisonUtils;
import me.O_o_Fadi_o_O.OrbitMines.utils.prison.PrisonUtils.GambleType;
import me.O_o_Fadi_o_O.OrbitMines.utils.prison.PrisonUtils.MineType;
import me.O_o_Fadi_o_O.OrbitMines.utils.prison.PrisonUtils.Rank;
import me.O_o_Fadi_o_O.OrbitMines.utils.skyblock.Challenge;
import me.O_o_Fadi_o_O.OrbitMines.utils.skyblock.Inventories;
import me.O_o_Fadi_o_O.OrbitMines.utils.skyblock.Inventories.ChallengesInv;
import me.O_o_Fadi_o_O.OrbitMines.utils.skyblock.Inventories.IslandInfoInv;
import me.O_o_Fadi_o_O.OrbitMines.utils.skyblock.Inventories.IslandMembersInv;
import me.O_o_Fadi_o_O.OrbitMines.utils.skyblock.ItemData;
import me.O_o_Fadi_o_O.OrbitMines.utils.skyblock.SkyBlockPlayer;
import me.O_o_Fadi_o_O.OrbitMines.utils.skyblock.SkyBlockUtils.ChallengeType;
import me.O_o_Fadi_o_O.OrbitMines.utils.survival.Inventories.WarpEditorInv;
import me.O_o_Fadi_o_O.OrbitMines.utils.survival.Inventories.WarpInv;
import me.O_o_Fadi_o_O.OrbitMines.utils.survival.Inventories.WarpItemEditorInv;
import me.O_o_Fadi_o_O.OrbitMines.utils.survival.Inventories.WarpRenameInv;
import me.O_o_Fadi_o_O.OrbitMines.utils.survival.Region;
import me.O_o_Fadi_o_O.OrbitMines.utils.survival.SurvivalPlayer;
import me.O_o_Fadi_o_O.OrbitMines.utils.survival.Warp;
import om.api.handlers.Kit;
import om.kitpvp.handlers.Masteries;
import om.kitpvp.handlers.players.ActiveBooster;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Result;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.EnchantingInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class ClickManager {
	
	public void handleSettings(){
		if(e.getInventory().getName().equals(new SettingsInv(p).getInventory().getTitle())){
			e.setCancelled(true);
			
			if(!itemIsNull()){
				if(item.getType() == Material.EYE_OF_ENDER && item.getItemMeta().getDisplayName().startsWith("§3§lPlayers")){
					p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 5, 1);
					
					if(omp.hasPlayersEnabled()){
						omp.hidePlayers();
						omp.setPlayersEnabled(false);
						p.sendMessage("§3§l§oPlayers §c§lDISABLED");
					}
					else{
						omp.showPlayers();
						omp.setPlayersEnabled(true);
						p.sendMessage("§3§l§oPlayers §a§lENABLED");
					}
					
					p.closeInventory();
				}
				else if(item.getType() == Material.LEASH && item.getItemMeta().getDisplayName().startsWith("§6§lStacker")){
					p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 5, 1);
					
					if(omp.hasStackerEnabled()){
						omp.setStackerEnabled(false);
						p.sendMessage("§6§l§oStacker §c§lDISABLED");
					}
					else{
						omp.setStackerEnabled(true);
						p.sendMessage("§6§l§oStacker §a§lENABLED");
					}
					
					p.closeInventory();
				}
				else if(item.getType() == Material.PAPER && item.getItemMeta().getDisplayName().startsWith("§f§lScoreboard")){
					p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 5, 1);
					
					if(omp.hasScoreboardEnabled()){
						omp.setScoreboardEnabled(false);
						p.sendMessage("§f§l§oScoreboard §c§lDISABLED");
					}
					else{
						omp.setScoreboardEnabled(true);
						p.sendMessage("§f§l§oScoreboard §a§lENABLED");
					}
					
					p.closeInventory();
				}
				else{}
			}
		}
	}
	
	public void handleAnvilInventory(){
		if(!itemIsNull()){
			if(ServerData.isServer(Server.HUB, Server.MINIGAMES) && p.getOpenInventory().getTopInventory() instanceof AnvilInventory && p.getOpenInventory().getBottomInventory() == e.getInventory() && item.getType() == Material.ENDER_CHEST){
				new CosmeticPerksInv().open(p);
			}
		}
	}
		
	@SuppressWarnings("deprecation")
	public void handlePlotKits(){
		if(e.getInventory().getName().startsWith(new PlotKitInv(null, null).getInventory().getName())){
			if(!itemIsNull() && item.getItemMeta().getDisplayName().endsWith("§7§lEmpty")){
				e.setCancelled(true);
			}
			final ItemStack cursor = e.getCursor();
			final int slot = e.getSlot();
			
			if(slot != 8 && cursor != null && cursor.getType() != Material.AIR){
				if(slot == 0 || slot == 1 || slot == 2 || slot == 3 || slot == 18 || slot == 19 || slot == 20 || slot == 21 || slot == 22 || slot == 23 || slot == 24 || slot == 25 || slot == 26){
					e.getInventory().setItem(slot, cursor);
					if(item.getItemMeta() != null && item.getItemMeta().getDisplayName() != null && item.getItemMeta().getDisplayName().endsWith("§7§lEmpty")){
						e.setCursor(null);	
					}
					else{
						e.setCancelled(true);
						e.setCursor(item);	
					}
				}
				else{
					e.setResult(Result.DENY);
					omp.updateInventory();
				}
				
				p.updateInventory();
			}
			
			if(e.getClickedInventory().getName().startsWith(new PlotKitInv(null, null).getInventory().getName()) && !itemIsNull() && item.getType() == Material.BARRIER && item.getItemMeta().getDisplayName().equals("§c§lDelete Kit")){
				CreativePlayer cp = omp.getCreativePlayer();
				Plot plot = cp.getPlot();
				String kitname = e.getClickedInventory().getName().substring(9);
				
				try{
					for(Kit kit : plot.getPvPKits()){
						if(kit.getKitName().equals(kitname)){
							plot.setSetupFinished(false);
							plot.removeKit(kit);
						}
					}
				}catch(ConcurrentModificationException ex){}
				
				p.closeInventory();
				p.sendMessage("§7Deleted a Kit! (§a§l" + kitname + "§7)");
			}
			
			CreativePlayer cp = omp.getCreativePlayer();
			cp.updateNPCInventory(p.getOpenInventory().getTopInventory());
		}
	}
	
	public void handlePlotInfo(){
		if(e.getInventory().getName().equals(new PlotInfoInv().getInventory().getName())){
			e.setCancelled(true);
			
			if(!itemIsNull()){
				if((item.getType() == PlotType.NORMAL.getMaterial() || item.getType() == PlotType.PVP.getMaterial()) && item.getItemMeta().getDisplayName().startsWith("§7§lPlot Mode")){
					if(omp.hasPerms(VIPRank.Emerald_VIP)){
						CreativePlayer cp = omp.getCreativePlayer();
						Plot plot = cp.getPlot();
						
						if(cp.hasPlot()){
							if(item.getItemMeta().getDisplayName().endsWith("Build Mode")){
								if(plot.isSetupFinished()){
									plot.generatePvPBorder(true);
									plot.setPlotType(PlotType.PVP);
									
									p.sendMessage("§7Your §dPlot§7 has been set to §c§lPvP Mode§7!");
									p.sendMessage("§7Use §d/plot pvpbroadcast§7 to broadcast your §dPlot§7!");
								}
								else{
									p.sendMessage("§7You have to finish the PvP Setup to enable §c§lPvP Mode§7!");
								}
							}
							else{
								plot.generatePvPBorder(false);
								plot.setPlotType(PlotType.NORMAL);
								p.sendMessage("§7Your §dPlot§7 has been set to §d§lBuild Mode§7!");
								
								for(CreativePlayer cplayer : Plot.getPvPPlayers(plot)){
									Player player = cplayer.getPlayer();
									OMPlayer omplayer = OMPlayer.getOMPlayer(player);
									
									omplayer.clearInventory();
									omplayer.clearPotionEffects();
									player.setGameMode(GameMode.CREATIVE);
									cplayer.setPvPPlot(null);
									cplayer.setSelectedKit(null);
	
								    player.playSound(player.getLocation(), Sound.CLICK, 5, 1);
								    
								    if(p != player){
								    	player.sendMessage("");
										player.sendMessage("§d" + p.getName() + "§7 set their §7§lPlot Mode§7 to §d§lBuild Mode§7!");
								    }
								}
							}
							
							new PlotInfoInv().open(p);
						}
					}
					else{
						omp.requiredVIPRank(VIPRank.Emerald_VIP);
					}
				}
				else if(item.getType() == Material.SKULL_ITEM && item.getItemMeta().getDisplayName().startsWith("§6§lPlot Members")){
					new PlotMembersInv().open(p);
				}
				else{}
			}
		}
	}
	
	public void handlePlotMembers(){
		if(e.getInventory().getName().equals(new PlotMembersInv().getInventory().getName())){
			e.setCancelled(true);
			
			if(!itemIsNull()){
				if(item.getType() == Material.PAPER && item.getItemMeta().getDisplayName().equals("§d§lPlot Info")){
					new PlotInfoInv().open(p);
				}
			}
		}
	}
	
	public void handlePlotSetup(){
		if(e.getInventory().getName().equals(new PlotSetupInv().getInventory().getName())){
			e.setCancelled(true);
			
			if(!itemIsNull()){
				CreativePlayer cp = omp.getCreativePlayer();
				Plot plot = cp.getPlot();
				
				if(item.getType() == Material.STAINED_GLASS_PANE){
					if(item.getItemMeta().getDisplayName().startsWith("§7Set Max Players to: §a§l")){
						plot.setPvPMaxPlayers(plot.getPvPMaxPlayers() +1);
						new PlotSetupInv().open(p);
					}
					else if(item.getItemMeta().getDisplayName().startsWith("§7Set Max Players to: §c§l")){
						plot.setPvPMaxPlayers(plot.getPvPMaxPlayers() -1);
						new PlotSetupInv().open(p);
					}
					else if(item.getItemMeta().getDisplayName().startsWith("§7Set §a§lSpawnpoint #")){
						List<Location> spawns = plot.getPvPSpawns();
						spawns.add(p.getLocation());
						plot.setPvPSpawns(spawns);
	
						p.closeInventory();
						p.sendMessage("§7Set §a§lSpawnpoint #" + spawns.size() + "§7 to your location.");
					}
					else if(item.getItemMeta().getDisplayName().equals("§7Reset §c§lSpawnpoints")){
						List<Location> spawns = plot.getPvPSpawns();
						spawns.clear();
						plot.setPvPSpawns(spawns);
						plot.setSetupFinished(false);
						
						p.closeInventory();
						p.sendMessage("§7All §a§lSpawnpoints§7 have been reset.");
					}
					else if(item.getItemMeta().getDisplayName().equals("§7Set §a§lLobby§7")){
						plot.setPvPLobbyLocation(p.getLocation());
	
						p.closeInventory();
						p.sendMessage("§7Set the §a§lLobby§7 to your location.");
					}
					else if(item.getItemMeta().getDisplayName().equals("§a§lENABLE§7 Arrow Entities")){
						plot.setPvPArrows(true);
						new PlotSetupInv().open(p);
					}
					else if(item.getItemMeta().getDisplayName().equals("§c§lDISABLE§7 Arrow Entities")){
						plot.setPvPArrows(false);
						new PlotSetupInv().open(p);
					}
					else if(item.getItemMeta().getDisplayName().equals("§a§lENABLE§7 Building")){
						plot.setPvPBuild(true);
						new PlotSetupInv().open(p);
					}
					else if(item.getItemMeta().getDisplayName().equals("§c§lDISABLE§7 Building")){
						plot.setPvPBuild(false);
						new PlotSetupInv().open(p);
					}
					else if(item.getItemMeta().getDisplayName().equals("§a§lENABLE§7 Item Drops")){
						plot.setPvPDrops(true);
						new PlotSetupInv().open(p);
					}
					else if(item.getItemMeta().getDisplayName().equals("§c§lDISABLE§7 Item Drops")){
						plot.setPvPDrops(false);
						new PlotSetupInv().open(p);
					}
					else if(item.getItemMeta().getDisplayName().equals("§7Add §a§lKit")){
						new PlotKitNameGUI(omp).open();
					}
					else{}
				}
				else if(item.getType() == Material.STAINED_CLAY && item.getItemMeta().getDisplayName().equals("§a§lFinish Setup")){
					plot.setSetupFinished(true);
					
					p.closeInventory();
					p.sendMessage("§7You have §a§lfinished§7 the §dPlot§7 setup!");
					p.sendMessage("§7You can now set your §dPlot§7 to §c§lPvP Mode§7 using §d/plot info§7.");
				}
				else{}
			}
		}
	}

	public void handleRegions(){
		if(e.getInventory().getName().equals(ServerData.getSurvival().getRegionTeleporter().getInventory().getName())){
			e.setCancelled(true);
			
			if(!itemIsNull()){
				if(item.getItemMeta().getDisplayName().startsWith("§7§lRegion")){
					Region r = Region.getRegion(Integer.parseInt(item.getItemMeta().getDisplayName().substring(15)));
					r.teleport(omp);
				}
			}
		}
	}
	
	public void handleWarps(){
		if(e.getInventory().getName().startsWith(new WarpInv(0, false).getInventory().getName())){
			e.setCancelled(true);
			
			if(!itemIsNull()){
				SurvivalPlayer sp = omp.getSurvivalPlayer();
				boolean isfavoritewarps = e.getInventory().getItem(9).getItemMeta().getDisplayName().equals("§a§lFavorite Warps");
				
				if(item.getType() == Material.COMPASS && item.getItemMeta().getDisplayName().equals("§7§lSearch Warps")){
					new WarpInv(0, false).open(p);
				}
				else if(item.getType() == Material.DIAMOND && item.getItemMeta().getDisplayName().equals("§7§lFavorite Warps")){
					new WarpInv(0, true).open(p);
				}
				else if(item.getType() == Material.ENDER_PEARL && item.getItemMeta().getDisplayName().equals("§3Scroll Up")){
					new WarpInv(e.getInventory().getItem(17).getAmount() -1, isfavoritewarps).open(p);
				}
				else if(item.getType() == Material.ENDER_PEARL && item.getItemMeta().getDisplayName().equals("§3Scroll Down")){
					new WarpInv(e.getInventory().getItem(26).getAmount() -1, isfavoritewarps).open(p);
				}
				else if(item.getItemMeta().getDisplayName().startsWith("§7§lWarp: ")){
					Warp warp = Warp.getWarp(item.getItemMeta().getDisplayName().substring(14));
					
					if(a == InventoryAction.PICKUP_HALF){
						if(warp.isEnabled()){
							p.closeInventory();
							warp.teleport(omp);
						}
						else{
							p.sendMessage("§7That warp has been §c§lDISABLED§7!");
						}
					}
					else if(a == InventoryAction.PICKUP_ALL){						
						if(sp.getFavoriteWarps().contains(warp)){
							sp.removeFavoriteWarp(warp);
							new WarpInv(e.getInventory().getItem(26).getAmount() -2, isfavoritewarps).open(p);
						}
						else{
							sp.addFavoriteWarp(warp);
							new WarpInv(e.getInventory().getItem(26).getAmount() -2, isfavoritewarps).open(p);
						}
					}
					else{}
				}
				else{}
			}
		}
	}

	public void handleWarpEditor(){
		if(e.getInventory().getName().startsWith(new WarpEditorInv(null).getInventory().getName())){
			e.setCancelled(true);
			
			if(!itemIsNull()){
				Warp warp = Warp.getWarp(e.getInventory().getName().replace(new WarpEditorInv(null).getInventory().getName(), ""));
				
				if(item.getType() == Material.NAME_TAG && item.getItemMeta().getDisplayName().equals("§e§lRename Warp")){
					new WarpRenameInv(omp, warp).open();
				}
				else if(item.getItemMeta().getDisplayName().equals("§7§lChange Item")){
					new WarpItemEditorInv(warp).open(p);
				}
				else if(item.getType() == Material.MAP && item.getItemMeta().getDisplayName().equals("§6§lSet Location")){
					warp.setLocation(p.getLocation());
					Warp.saveToConfig();
					
					p.closeInventory();
					p.sendMessage("§7Changed the location of warp '§6" + warp.getName() + "§7' to your position!");
				}
				else if((item.getType() == Material.ENDER_PEARL || item.getType() == Material.EYE_OF_ENDER) && item.getItemMeta().getDisplayName().startsWith("§7§lWarp ")){
					warp.setEnabled(!warp.isEnabled());
					Warp.saveToConfig();
					new WarpEditorInv(warp).open(p);
				}
				else{}
			}
		}
	}

	public void handleWarpItemEditor(){
		if(e.getInventory().getName().startsWith(new WarpItemEditorInv(null).getInventory().getName())){
			e.setCancelled(true);
			
			if(!itemIsNull()){
				Warp warp = Warp.getWarp(e.getInventory().getName().replace(new WarpItemEditorInv(null).getInventory().getName(), ""));
				
				if(item.getItemMeta().getDisplayName().startsWith("§7Change to ")){
					warp.updateItemStack(item.getType(), item.getDurability());
					Warp.saveToConfig();
					
					p.closeInventory();
					p.sendMessage("§7Changed the warp item to " + item.getItemMeta().getDisplayName().substring(12) + "§7.");
				}
				else if(item.getType() == Material.BARRIER && item.getItemMeta().getDisplayName().equals("§c§lCancel")){
					new WarpEditorInv(warp).open(p);
				}
				else{}
			}
		}
	}
	
	public void handleIslandInfo(){
		if(e.getInventory().getName().equals(new IslandInfoInv().getInventory().getName())){
			e.setCancelled(true);
		
			if(!itemIsNull()){
				SkyBlockPlayer sbp = omp.getSkyBlockPlayer();
				
				if(item.getType() == Material.SKULL_ITEM && item.getItemMeta().getDisplayName().startsWith("§6§lIsland Party")){
					new IslandMembersInv().open(p);
				}
				else if((item.getType() == Material.ENDER_PEARL || item.getType() == Material.EYE_OF_ENDER) && item.getItemMeta().getDisplayName().startsWith("§3§lTeleport")){
					if(sbp.isOwner()){
						sbp.getIsland().setTeleportEnabled(!sbp.getIsland().isTeleportEnabled());
						new IslandInfoInv().open(sbp);
					}
					else{
						p.sendMessage("§7You're not the owner of your §dIsland§7!");
					}
				}
				else if(item.getType() == Material.STAINED_GLASS_PANE && item.getItemMeta().getDisplayName().startsWith("§7§lIsland Protection")){
					if(sbp.isOwner()){
						sbp.getIsland().setIslandProtection(!sbp.getIsland().hasIslandProtection());
						new IslandInfoInv().open(sbp);
					}
					else{
						p.sendMessage("§7You're not the owner of your §dIsland§7!");
					}
				}
				else{}
			}
		}
	}
	
	public void handleIslandMembers(){
		if(e.getInventory().getName().equals(new IslandMembersInv().getInventory().getName())){
			e.setCancelled(true);
		
			if(!itemIsNull()){
				SkyBlockPlayer sbp = omp.getSkyBlockPlayer();
				
				if(item.getType() == Material.PAPER && item.getItemMeta().getDisplayName().equals("§d§lIsland Info")){
					new IslandInfoInv().open(sbp);
				}
			}
		}
	}
	
	public void handleChallenges(){
		if(e.getInventory().getName().equals(new ChallengesInv(null).getInventory().getName())){
			e.setCancelled(true);
		
			if(!itemIsNull()){
				if(item.getType() == Material.COBBLESTONE && item.getItemMeta().getDisplayName().equals("§f§lGather Challenges")){
					new ChallengesInv(ChallengeType.GATHER).open(p);
				}
				else if(item.getType() == Material.WHEAT && item.getItemMeta().getDisplayName().equals("§f§lFarm Challenges")){
					new ChallengesInv(ChallengeType.FARM).open(p);
				}
				else if(item.getType() == Material.SPIDER_EYE && item.getItemMeta().getDisplayName().equals("§f§lMob Challenges")){
					new ChallengesInv(ChallengeType.MOB).open(p);
				}
				else{
					SkyBlockPlayer sbp = omp.getSkyBlockPlayer();
					
					for(Challenge c : Challenge.getChallenges()){
						if(item.getType() == c.getItem().getMaterial()){
							if(item.getItemMeta().getDisplayName().equals("§a§l" + c.getItem().getDisplayName() + " ")){
								if(p.getInventory().firstEmpty() != -1){
									boolean cancomplete = true;
									
									for(ItemData required : c.getRequirements()){
										if(cancomplete){
											int amount = 0;
											
											for(ItemStack item : p.getInventory().getContents()){
												if(item != null && item.getType() == required.getMaterial() && item.getDurability() == required.getDurability()){
													amount += item.getAmount();
												}
											}
											
											if(amount < required.getAmount()){
												cancomplete = false;
											}
										}
									}
									
									if(cancomplete){
										for(ItemData required : c.getRequirements()){
											List<ItemStack> items = new ArrayList<ItemStack>();
											for(ItemStack item : p.getInventory().getContents()){
												if(item != null && item.getType() == required.getMaterial() && item.getDurability() == required.getDurability()){
													p.getInventory().remove(item);
													items.add(item);
												}
											}
											List<ItemStack> itemstoremove = new ArrayList<ItemStack>();
											List<ItemStack> itemstoadd = new ArrayList<ItemStack>();
											
											int amount = 0;
											for(ItemStack item : items){
												if(amount != required.getAmount()){
													if(amount + item.getAmount() <= required.getAmount()){
														itemstoremove.add(item);
														
														amount += item.getAmount();
													}
													else{
														itemstoremove.add(item);
														
														ItemStack item3 = new ItemStack(item);
														item3.setAmount(item.getAmount() - (required.getAmount() - amount));
														itemstoadd.add(item3);
														
														amount = required.getAmount();
													}
												}
											}
											
											for(ItemStack item : itemstoremove){
												items.remove(item);
											}
											for(ItemStack item : itemstoadd){
												items.add(item);
											}
											
											p.getInventory().addItem(items.toArray(new ItemStack[items.size()]));
										}
										
										String rewardstring = "";
										for(ItemData reward : c.getRewards()){
											ItemStack item = new ItemStack(reward.getMaterial(), reward.getAmount());
											item.setDurability(reward.getDurability());
											
											p.getInventory().addItem(item);
											
											if(rewardstring.equals("")){
												rewardstring = reward.getDisplayName();
											}
											else{
												rewardstring += "§7, §f§l" + reward.getDisplayName();
											}
										}
										
										omp.updateInventory();
										sbp.setChallengeCompleted(c);
	
										p.sendMessage("");
										p.sendMessage("§dChallenge Completed: §f§l" + c.getItem().getDisplayName());
										p.sendMessage("§dTimes Completed: §f§l" + sbp.getChallengeCompleted(c));
										p.sendMessage("§dReward: §f§l" + rewardstring);
										p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 1);
										
										if(e.getInventory().getItem(0).getItemMeta().getDisplayName().equals("§a§lGather Challenges")){
											new ChallengesInv(ChallengeType.GATHER).open(p);
										}
										else if(e.getInventory().getItem(9).getItemMeta().getDisplayName().equals("§a§lFarm Challenges")){
											new ChallengesInv(ChallengeType.FARM).open(p);
										}
										else if(e.getInventory().getItem(18).getItemMeta().getDisplayName().equals("§a§lMob Challenges")){
											new ChallengesInv(ChallengeType.MOB).open(p);
										}
										else{}
									}
									else{
										p.sendMessage("§7You don't have enough items!");
									}
								}
								else{
									p.sendMessage("§7Your inventory is full!");
								}
							}
							else if(item.getItemMeta().getDisplayName().equals("§c§l" + c.getItem().getDisplayName() + " ")){
								String requiredstring = "";
								for(Challenge challenge : c.getRequired()){
									if(requiredstring.equals("")){
										requiredstring = challenge.getItem().getDisplayName();
									}
									else{
										requiredstring += "§7, §d" + challenge.getItem().getDisplayName();
									}
								}
								
								p.sendMessage("§7You have to complete the following challenges: §d" + requiredstring + "§7.");
							}
							else{}
						}
					}
				}
			}
		}
	}
	
	public void handleSkyBlockKits(){
		if(e.getInventory().getName().equals(new Inventories.KitInv().getInventory().getName())){
			e.setCancelled(true);
		
			if(!itemIsNull()){
				SkyBlockPlayer sbp = omp.getSkyBlockPlayer();
				boolean found = false;
				
				for(VIPRank viprank : VIPRank.values()){
					if(!found && viprank != VIPRank.User){
						if(item.getType() == viprank.getMaterial() && item.getItemMeta().getDisplayName().equals(viprank.getRankString() + " VIP Kit")){
							if(omp.getVIPRank() == viprank || omp.getStaffRank() == StaffRank.Owner){
								if(sbp.canUseKit()){
									sbp.useKit(viprank);
									p.sendMessage("§7You used the " + viprank.getRankString() + " VIP Kit§7!");
									p.closeInventory();
								}
								else{
									p.sendMessage("§7You can only use this kit once every 24 hours!");
								}
							}
							else{
								omp.requiredVIPRank(viprank);
							}
							found = true;
						}
					}
				}
			}
		}
	}
	
	public void handleMines(){
		if(e.getInventory().getName().equals(new MineInv().getInventory().getName())){
			e.setCancelled(true);
			
			if(!itemIsNull()){
			    if(item.getType() == Material.STAINED_GLASS_PANE && item.getDurability() == 5){
			    	Mine mine = Mine.getMine(Rank.fromID(item.getAmount() -1), MineType.NORMAL);
			    	
			    	p.teleport(mine.getSpawn());
			    	p.sendMessage("§7Teleported to Mine §a§l" + mine.getRank().toString() + "§7.");
			    	p.playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 5, 1);
			    }
			}
		}
	}
	
	public void handleGoldShop(){
		if(e.getInventory().getName().equals(new GoldShopInv().getInventory().getName())){
			e.setCancelled(true);
			
			if(!itemIsNull()){
				if(item.getType() == Material.LOG && item.getItemMeta().getDisplayName().equals("§eWood") || item.getType() == Material.COBBLESTONE && item.getItemMeta().getDisplayName().equals("§7Stone") || item.getType() == Material.COAL && item.getItemMeta().getDisplayName().equals("§8Coal") || item.getType() == Material.IRON_INGOT && item.getItemMeta().getDisplayName().equals("§7Iron") || item.getType() == Material.DIAMOND && item.getItemMeta().getDisplayName().equals("§bDiamond") || item.getType() == Material.EMERALD && item.getItemMeta().getDisplayName().equals("§aEmerald") || item.getType() == Material.REDSTONE && item.getItemMeta().getDisplayName().equals("§cRedstone") || item.getType() == Material.INK_SACK && item.getItemMeta().getDisplayName().equals("§1Lapis Lazuli") || item.getType() == Material.GOLD_INGOT && item.getItemMeta().getDisplayName().equals("§6Gold")){
					if(item.getEnchantments() == null || item.getEnchantments().size() == 0){
						new GoldShopInv().open(omp, item.getType());
					}
				}
				else{
					if(item.getType() != Material.STAINED_GLASS_PANE && item.getItemMeta().getLore() != null && item.getItemMeta().getLore().get(0).startsWith(" §7Reward:")){
						List<ItemStack> items = new ArrayList<ItemStack>();
						for(ItemStack item : p.getInventory().getContents()){
							if(item != null && item.getType() == this.item.getType() && item.getDurability() == this.item.getDurability()){
								p.getInventory().remove(item);
								items.add(item);
							}
						}
						List<ItemStack> itemstoremove = new ArrayList<ItemStack>();
						List<ItemStack> itemstoadd = new ArrayList<ItemStack>();
						
						int amount = 0;
						for(ItemStack item : items){
							if(amount != this.item.getAmount()){
								if(amount + item.getAmount() <= this.item.getAmount()){
									itemstoremove.add(item);
									
									amount += item.getAmount();
								}
								else{
									itemstoremove.add(item);
									
									ItemStack item3 = new ItemStack(item);
									item3.setAmount(item.getAmount() - (this.item.getAmount() - amount));
									itemstoadd.add(item3);
									
									amount = this.item.getAmount();
								}
							}
						}
						
						for(ItemStack item : itemstoremove){
							items.remove(item);
						}
						for(ItemStack item : itemstoadd){
							items.add(item);
						}
						
						p.getInventory().addItem(items.toArray(new ItemStack[items.size()]));
						omp.updateInventory();

						Material m = item.getType();
						if(PrisonUtils.getSecondMaterial(item.getType()) == null){
							m = PrisonUtils.getFirstMaterial(item.getType());
						}
						int reward = (int) (PrisonUtils.getReward(item.getType()) * item.getAmount() * omp.getPrisonPlayer().getGoldMultiplier());
						
						p.playSound(p.getLocation(), Sound.CLICK, 5, 1);
						p.sendMessage("§7Sold " + item.getItemMeta().getDisplayName()  + "§7 for §6§l" + reward + " Gold§7!");
						new GoldShopInv().open(omp, m);
						
						omp.getPrisonPlayer().addGold(reward);
					}
				}
			}
		}
	}
	
	public void handleStats(){
		if(e.getInventory().getName().equals(new StatsInv().getInventory().getName())){
			e.setCancelled(true);
			
			if(!itemIsNull()){
				for(MiniGameType type : MiniGameType.values()){
					if(item.getType() == type.getMaterial() && item.getItemMeta().getDisplayName().equals("§f§l" + type.getName())){
			    		new MiniGameTicketInv(type, InvType.KITS).open(p);
			    	}
				}
			}
		}
	}
	
	public void handleMiniGames(){
		if(e.getInventory().getName().equals(new MiniGamesInv().getInventory().getName())){
			e.setCancelled(true);
			
			if(!itemIsNull()){
			    if(item.getType() == Material.SNOW_BALL && item.getItemMeta().getDisplayName().equals("§f§lTicket Gamble")){
			    	new TicketInv(false).open(p);
			    }
				else if(item.getType() == Material.SKULL_ITEM && item.getItemMeta().getDisplayName().equals("§2§lStats")){
					new StatsInv().open(p);
				}
				else{}
			}
		}
	}
	
	public void handleMiniGameTickets(){
		for(MiniGameType type : MiniGameType.values()){
			if(e.getInventory().getName().equals(new MiniGameTicketInv(type, InvType.KITS).getInventory().getName())){
				e.setCancelled(true);
				
				if(!itemIsNull()){
					if(item.getType() == Material.WORKBENCH && item.getItemMeta().getDisplayName().equals("§7§lKits")){
			    		new MiniGameTicketInv(type, InvType.KITS).open(p);
			    	}
					else if(item.getType() == Material.SKULL_ITEM && item.getItemMeta().getDisplayName().equals("§2§lStats")){
						new StatsInv().open(p);
			    	}
					else if(item.getType() == Material.APPLE && item.getItemMeta().getDisplayName().equals("§7§lPerks")){
			    		new MiniGameTicketInv(type, InvType.PERKS).open(p);
			    	}
					else{}
				}
			}	
		}
	}
	
	public void handleTicketGamble(){
		if(e.getInventory().getName().equals(new TicketInv(false).getInventory().getName())){
			e.setCancelled(true);
			
			if(!itemIsNull()){
				if(item.getType() == Material.STAINED_GLASS_PANE && item.getItemMeta().getDisplayName().equals("§a§lStart Ticket Gamble")){
					if(omp.hasMGTickets(3)){
						omp.removeMGTickets(3);
						TicketInv ticketinv = new TicketInv(true);
						ticketinv.update(p);
					}
					else{
						omp.requiredMGTickets(3);
					}
			    }
				else if(item.getType() == Material.STAINED_GLASS_PANE && item.getItemMeta().getDisplayName().equals("§c§lCancel")){
					new MiniGamesInv().open(p);
				}
				else if(item.getType() == Material.EMPTY_MAP && item.getItemMeta().getDisplayName().equals("§f§l+5 Tickets")){
					if(omp.hasVIPPoints(40)){
						new ConfirmInv(item, Currency.VIP_POINTS, 40).open(p);
					}
					else{
						omp.requiredVIPPoints(40);
					}
				}
				else if(item.getType() == Material.EMPTY_MAP && item.getItemMeta().getDisplayName().equals("§0§f§l+3 Tickets")){
					if(omp.hasOrbitMinesTokens(3)){
						new ConfirmInv(item, Currency.ORBITMINES_TOKENS, 3).open(p);
					}
					else{
						omp.requiredOMT(3);
					}
				}
				else if(item.getType() == Material.EMPTY_MAP && item.getItemMeta().getDisplayName().equals("§f§l+3 Tickets")){
					if(omp.hasMiniGameCoins(100)){
						new ConfirmInv(item, Currency.MINIGAME_POINTS, 100).open(p);
					}
					else{
						omp.requiredMiniGameCoins(100);
					}
				}
				else{}
			}
		}
	}
	
	public void handleKitSelect(){
		for(TicketType type : TicketType.getKits()){
			if(e.getInventory().getName().equals(new MGKitInv(type).getInventory().getName())){
				e.setCancelled(true);
				
				if(!itemIsNull()){
					if(item.getType() == type.getItemStack().getType() && item.getItemMeta().getDisplayName().equals("§f§lSelect " + type.getName())){
						Arena arena = omp.getArena();
						
						if(arena != null){
							if(omp.getTicketAmount(type) != 0){
								switch(arena.getType()){
									case CHICKEN_FIGHT:
										omp.getCFPlayer().setKit(type.getKit());
										break;
									case GHOST_ATTACK:
										omp.getGAPlayer().setKit(type.getKit());
										break;
									case SKYWARS:
										omp.getSWPlayer().setKit(type.getKit());
										break;
									case SPLATCRAFT:
										omp.getSCPlayer().setKit(type.getKit());
										break;
									case SPLEEF:
										omp.getSPPlayer().setKit(type.getKit());
										break;
									case SURVIVAL_GAMES:
										omp.getSGPlayer().setKit(type.getKit());
										break;
									case ULTRA_HARD_CORE:
										omp.getUHCPlayer().setKit(type.getKit());
										break;
								}
								
								p.closeInventory();
								p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 5, 1);
								p.sendMessage("§7Selected §f§l" + type.getName() + "§7.");
							}
							else{
								p.sendMessage("§7You cannot select this Kit!");
							}
						}
					}
				}
			}
		}
	}
	
	public void handleGameEffects(){
		if(e.getInventory().getName().equals(new GameEffectsInv(null).getInventory().getName())){
			e.setCancelled(true);
			Arena arena = omp.getArena();
			
			if(arena != null){
				if(!itemIsNull() && item.getItemMeta().getLore() != null){
					List<String> lore = item.getItemMeta().getLore();
					
					switch(arena.getType()){
						case CHICKEN_FIGHT:
							for(TicketType type : TicketType.getCFPerks()){
								if(item.getType() == type.getItemStack().getType() && item.getItemMeta().getDisplayName().equals(type.getRarity().getColor() + type.getName())){
									if(!lore.contains(" §7Status: §a§lActivated")){
										if(lore.contains(" §7Status: §a§lClick to Activate")){
											if(type == TicketType.JUMP_BOOST){
												omp.removeTicket(TicketType.JUMP_BOOST);
												arena.playSound(Sound.SUCCESSFUL_HIT, 5, 1);
												arena.sendMessage(omp.getName() + " §7activated " + item.getItemMeta().getDisplayName() + "§7.");
												arena.getCF().setJumpBoost(true);
												
												for(OMPlayer omplayer : arena.getPlayers()){
													if(omplayer.getPlayer().getOpenInventory().getTopInventory() != null && omplayer.getPlayer().getOpenInventory().getTopInventory().getName() != null && omplayer.getPlayer().getOpenInventory().getTopInventory().getName().equals(new GameEffectsInv(null).getInventory().getName())){
														new GameEffectsInv(MiniGameType.CHICKEN_FIGHT).open(omplayer.getPlayer());
													}
												}
												p.closeInventory();
											}
											else if(type == TicketType.SPEED_BOOST){
												omp.removeTicket(TicketType.SPEED_BOOST);
												arena.playSound(Sound.SUCCESSFUL_HIT, 5, 1);
												arena.sendMessage(omp.getName() + " §7activated " + item.getItemMeta().getDisplayName() + "§7.");
												arena.getCF().setSpeedBoost(true);
												
												for(OMPlayer omplayer : arena.getPlayers()){
													if(omplayer.getPlayer().getOpenInventory().getTopInventory() != null && omplayer.getPlayer().getOpenInventory().getTopInventory().getName() != null && omplayer.getPlayer().getOpenInventory().getTopInventory().getName().equals(new GameEffectsInv(null).getInventory().getName())){
														new GameEffectsInv(MiniGameType.CHICKEN_FIGHT).open(omplayer.getPlayer());
													}
												}
												p.closeInventory();
											}
											else{}
										}
									}
								}
							}
							break;
						case GHOST_ATTACK:
							break;
						case SKYWARS:
							for(TicketType type : TicketType.getSkywarsPerks()){
								if(item.getType() == type.getItemStack().getType() && item.getItemMeta().getDisplayName().equals(type.getRarity().getColor() + type.getName())){
									if(!lore.contains(" §7Status: §a§lActivated")){
										if(lore.contains(" §7Status: §a§lClick to Activate")){
											omp.getSWPlayer().setCage(type);
											
											p.sendMessage("§7Activated " + item.getItemMeta().getDisplayName() + "§7.");
											p.closeInventory();
										}
									}
								}
							}
							break;
						case SPLATCRAFT:
							break;
						case SPLEEF:
							break;
						case SURVIVAL_GAMES:
							for(TicketType type : TicketType.getSGPerks()){
								if(item.getType() == type.getItemStack().getType() && item.getItemMeta().getDisplayName().equals(type.getRarity().getColor() + type.getName())){
									if(!lore.contains(" §7Status: §a§lActivated")){
										if(lore.contains(" §7Status: §a§lClick to Activate")){
											if(type == TicketType.ENABLE_POTIONS){
												omp.removeTicket(TicketType.ENABLE_POTIONS);
												arena.playSound(Sound.SUCCESSFUL_HIT, 5, 1);
												arena.sendMessage(omp.getName() + " §7activated " + item.getItemMeta().getDisplayName() + "§7.");
												arena.getSG().setEnablePotions(true);
												
												for(OMPlayer omplayer : arena.getPlayers()){
													if(omplayer.getSGPlayer().isEnablePotions()){
														omplayer.getSGPlayer().setEnablePotions(false);
														omplayer.addTicketAmount(TicketType.ENABLE_POTIONS_PLAYER, 1);
													}
													if(omplayer.getPlayer().getOpenInventory().getTopInventory() != null && omplayer.getPlayer().getOpenInventory().getTopInventory().getName() != null && omplayer.getPlayer().getOpenInventory().getTopInventory().getName().equals(new GameEffectsInv(null).getInventory().getName())){
														new GameEffectsInv(MiniGameType.SURVIVAL_GAMES).open(omplayer.getPlayer());
													}
												}
												p.closeInventory();
											}
											else if(type == TicketType.ENABLE_POTIONS_PLAYER){
												omp.removeTicket(TicketType.ENABLE_POTIONS_PLAYER);
												arena.playSound(Sound.SUCCESSFUL_HIT, 5, 1);
												arena.sendMessage(omp.getName() + " §7activated " + item.getItemMeta().getDisplayName() + "§7.");
												omp.getSGPlayer().setEnablePotions(true);
												p.closeInventory();
												
												for(OMPlayer omplayer : arena.getPlayers()){
													if(omplayer.getPlayer().getOpenInventory().getTopInventory() != null && omplayer.getPlayer().getOpenInventory().getTopInventory().getName() != null && omplayer.getPlayer().getOpenInventory().getTopInventory().getName().equals(new GameEffectsInv(null).getInventory().getName())){
														new GameEffectsInv(MiniGameType.SURVIVAL_GAMES).open(omplayer.getPlayer());
													}
												}
											}
											else if(type == TicketType.DOUBLE_LOOT){
												omp.removeTicket(TicketType.DOUBLE_LOOT);
												arena.playSound(Sound.SUCCESSFUL_HIT, 5, 1);
												arena.sendMessage(omp.getName() + " §7activated " + item.getItemMeta().getDisplayName() + "§7.");
												arena.getSG().setDoubleLoot(true);
												
												for(OMPlayer omplayer : arena.getPlayers()){
													if(omplayer.getSGPlayer().isDoubleLoot()){
														omplayer.getSGPlayer().setDoubleLoot(false);
														omplayer.addTicketAmount(TicketType.DOUBLE_LOOT_PLAYER, 1);
													}
													if(omplayer.getPlayer().getOpenInventory().getTopInventory() != null && omplayer.getPlayer().getOpenInventory().getTopInventory().getName() != null && omplayer.getPlayer().getOpenInventory().getTopInventory().getName().equals(new GameEffectsInv(null).getInventory().getName())){
														new GameEffectsInv(MiniGameType.SURVIVAL_GAMES).open(omplayer.getPlayer());
													}
												}
												p.closeInventory();
											}
											else if(type == TicketType.DOUBLE_LOOT_PLAYER){
												omp.removeTicket(TicketType.DOUBLE_LOOT_PLAYER);
												arena.playSound(Sound.SUCCESSFUL_HIT, 5, 1);
												arena.sendMessage(omp.getName() + " §7activated " + item.getItemMeta().getDisplayName() + "§7.");
												omp.getSGPlayer().setDoubleLoot(true);
												p.closeInventory();
												
												for(OMPlayer omplayer : arena.getPlayers()){
													if(omplayer.getPlayer().getOpenInventory().getTopInventory() != null && omplayer.getPlayer().getOpenInventory().getTopInventory().getName() != null && omplayer.getPlayer().getOpenInventory().getTopInventory().getName().equals(new GameEffectsInv(null).getInventory().getName())){
														new GameEffectsInv(MiniGameType.SURVIVAL_GAMES).open(omplayer.getPlayer());
													}
												}
											}
											else{
												Color color = ((LeatherArmorMeta) type.getItemStack().getItemMeta()).getColor();
												omp.getSGPlayer().setLeatherColor(color);
												
												p.sendMessage("§7Activated " + item.getItemMeta().getDisplayName() + "§7.");
												p.closeInventory();
											}
										}
									}
								}
							}
							break;
						case ULTRA_HARD_CORE:
							for(TicketType type : TicketType.getUHCPerks()){
								if(item.getType() == type.getItemStack().getType() && item.getItemMeta().getDisplayName().equals(type.getRarity().getColor() + type.getName())){
									if(!lore.contains(" §7Status: §a§lActivated")){
										if(lore.contains(" §7Status: §a§lClick to Activate")){
											if(type == TicketType.DOUBLE_IRON){
												omp.removeTicket(TicketType.DOUBLE_IRON);
												arena.playSound(Sound.SUCCESSFUL_HIT, 5, 1);
												arena.sendMessage(omp.getName() + " §7activated " + item.getItemMeta().getDisplayName() + "§7.");
												arena.getUHC().setDoubleIron(true);
												
												for(OMPlayer omplayer : arena.getPlayers()){
													if(omplayer.getUHCPlayer().isDoubleIron()){
														omplayer.getUHCPlayer().setDoubleIron(false);
														omplayer.addTicketAmount(TicketType.DOUBLE_IRON_PLAYER, 1);
													}
													if(omplayer.getPlayer().getOpenInventory().getTopInventory() != null && omplayer.getPlayer().getOpenInventory().getTopInventory().getName() != null && omplayer.getPlayer().getOpenInventory().getTopInventory().getName().equals(new GameEffectsInv(null).getInventory().getName())){
														new GameEffectsInv(MiniGameType.ULTRA_HARD_CORE).open(omplayer.getPlayer());
													}
												}
												p.closeInventory();
											}
											else if(type == TicketType.DOUBLE_IRON_PLAYER){
												omp.removeTicket(TicketType.DOUBLE_IRON_PLAYER);
												arena.playSound(Sound.SUCCESSFUL_HIT, 5, 1);
												arena.sendMessage(omp.getName() + " §7activated " + item.getItemMeta().getDisplayName() + "§7.");
												omp.getUHCPlayer().setDoubleIron(true);
												p.closeInventory();
												
												for(OMPlayer omplayer : arena.getPlayers()){
													if(omplayer.getPlayer().getOpenInventory().getTopInventory() != null && omplayer.getPlayer().getOpenInventory().getTopInventory().getName() != null && omplayer.getPlayer().getOpenInventory().getTopInventory().getName().equals(new GameEffectsInv(null).getInventory().getName())){
														new GameEffectsInv(MiniGameType.ULTRA_HARD_CORE).open(omplayer.getPlayer());
													}
												}
											}
											else if(type == TicketType.GOLD_FROM_LAPIS){
												omp.removeTicket(TicketType.GOLD_FROM_LAPIS);
												arena.playSound(Sound.SUCCESSFUL_HIT, 5, 1);
												arena.sendMessage(omp.getName() + " §7activated " + item.getItemMeta().getDisplayName() + "§7.");
												omp.getUHCPlayer().setBlueGold(true);
												p.closeInventory();
												
												for(OMPlayer omplayer : arena.getPlayers()){
													if(omplayer.getPlayer().getOpenInventory().getTopInventory() != null && omplayer.getPlayer().getOpenInventory().getTopInventory().getName() != null && omplayer.getPlayer().getOpenInventory().getTopInventory().getName().equals(new GameEffectsInv(null).getInventory().getName())){
														new GameEffectsInv(MiniGameType.ULTRA_HARD_CORE).open(omplayer.getPlayer());
													}
												}
											}
											else if(type == TicketType.GOLD_FROM_REDSTONE){
												omp.removeTicket(TicketType.GOLD_FROM_REDSTONE);
												arena.playSound(Sound.SUCCESSFUL_HIT, 5, 1);
												arena.sendMessage(omp.getName() + " §7activated " + item.getItemMeta().getDisplayName() + "§7.");
												omp.getUHCPlayer().setRedGold(true);
												p.closeInventory();
												
												for(OMPlayer omplayer : arena.getPlayers()){
													if(omplayer.getPlayer().getOpenInventory().getTopInventory() != null && omplayer.getPlayer().getOpenInventory().getTopInventory().getName() != null && omplayer.getPlayer().getOpenInventory().getTopInventory().getName().equals(new GameEffectsInv(null).getInventory().getName())){
														new GameEffectsInv(MiniGameType.ULTRA_HARD_CORE).open(omplayer.getPlayer());
													}
												}
											}
											else{}
										}
									}
								}
							}
							break;
					}
				}
			}
		}
	}
	
	public void handleVillagerGamble(){
		if(e.getInventory().getName().equals(new VillagerGambleInv(false).getInventory().getName())){
			e.setCancelled(true);
			
			if(!itemIsNull()){
				PrisonPlayer pp = omp.getPrisonPlayer();
				
				if(item.getType() == Material.IRON_BLOCK && item.getItemMeta().getDisplayName().equals("§7§lStart Iron Gamble Machine")){
					if(pp.hasGambleTickets(1)){
						pp.removeGambleTickets(1);
						VillagerGambleInv gambleinv = new VillagerGambleInv(true);
						gambleinv.update(p, GambleType.IRON_MACHINE);
					}
					else{
						pp.requiredGambleTickets(1);
					}
			    }
				else if(item.getType() == Material.GOLD_BLOCK && item.getItemMeta().getDisplayName().equals("§6§lStart Gold Gamble Machine")){
					if(pp.hasGambleTickets(3)){
						pp.removeGambleTickets(3);
						VillagerGambleInv gambleinv = new VillagerGambleInv(true);
						gambleinv.update(p, GambleType.GOLD_MACHINE);
					}
					else{
						pp.requiredGambleTickets(3);
					}
			    }
				else if(item.getType() == Material.DIAMOND_BLOCK && item.getItemMeta().getDisplayName().equals("§b§lStart Diamond Gamble Machine")){
					if(pp.hasGambleTickets(5)){
						pp.removeGambleTickets(5);
						VillagerGambleInv gambleinv = new VillagerGambleInv(true);
						gambleinv.update(p, GambleType.DIAMOND_MACHINE);
					}
					else{
						pp.requiredGambleTickets(5);
					}
			    }
				else if(item.getType() == Material.EMERALD && item.getItemMeta().getDisplayName().equals("§a§l+5 Gamble Tickets")){
					if(omp.hasVIPPoints(250)){
						new ConfirmInv(item, Currency.VIP_POINTS, 250).open(p);
					}
					else{
						omp.requiredVIPPoints(250);
					}
				}
				else if(item.getType() == Material.EMERALD && item.getItemMeta().getDisplayName().equals("§0§a§l+1 Gamble Ticket")){
					if(omp.hasOrbitMinesTokens(5)){
						new ConfirmInv(item, Currency.ORBITMINES_TOKENS, 5).open(p);
					}
					else{
						omp.requiredOMT(5);
					}
				}
				else if(item.getType() == Material.EMERALD && item.getItemMeta().getDisplayName().equals("§a§l+1 Gamble Ticket")){
					if(omp.getPrisonPlayer().hasGold(10000)){
						new ConfirmInv(item, Currency.PRISON_GOLD, 10000).open(p);
					}
					else{
						omp.getPrisonPlayer().requiredGold(10000);
					}
				}
				else{}
			}
		}
	}
	
	public void handleMGSpectator(){
		Arena arena = omp.getArena();
		
		if(arena != null && arena.isSpectator(omp)){
			if(!itemIsNull()){
				if(item.getType() == Material.NAME_TAG && item.getItemMeta().getDisplayName().equals("§e§nTeleporter")){
					e.setCancelled(true);
					
					new TeleporterInv().open(p);
				}
				else if(item.getType() == Material.ENDER_PEARL && item.getItemMeta().getDisplayName().equals("§3§nBack to the Hub")){
					e.setCancelled(true);
					
					if(omp.getArena() != null){
						omp.getArena().leave(omp);
					}
				}
				else{}
			}
		}
	}
}
