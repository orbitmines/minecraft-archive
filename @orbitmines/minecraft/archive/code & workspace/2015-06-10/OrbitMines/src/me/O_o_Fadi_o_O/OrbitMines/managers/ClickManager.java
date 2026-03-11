package me.O_o_Fadi_o_O.OrbitMines.managers;

import java.util.List;

import me.O_o_Fadi_o_O.OrbitMines.Start;
import me.O_o_Fadi_o_O.OrbitMines.utils.Inventories.ChatColorInv;
import me.O_o_Fadi_o_O.OrbitMines.utils.Inventories.ConfirmInv;
import me.O_o_Fadi_o_O.OrbitMines.utils.Inventories.CosmeticPerksInv;
import me.O_o_Fadi_o_O.OrbitMines.utils.Inventories.DisguiseInv;
import me.O_o_Fadi_o_O.OrbitMines.utils.Inventories.FireworkInv;
import me.O_o_Fadi_o_O.OrbitMines.utils.Inventories.GadgetInv;
import me.O_o_Fadi_o_O.OrbitMines.utils.Inventories.HatInv;
import me.O_o_Fadi_o_O.OrbitMines.utils.Inventories.OMTShopInv;
import me.O_o_Fadi_o_O.OrbitMines.utils.Inventories.PetInv;
import me.O_o_Fadi_o_O.OrbitMines.utils.Inventories.PetRenameGUI;
import me.O_o_Fadi_o_O.OrbitMines.utils.Inventories.ServerSelectorInv;
import me.O_o_Fadi_o_O.OrbitMines.utils.Inventories.SettingsInv;
import me.O_o_Fadi_o_O.OrbitMines.utils.Inventories.TrailInv;
import me.O_o_Fadi_o_O.OrbitMines.utils.Inventories.TrailSettingsInv;
import me.O_o_Fadi_o_O.OrbitMines.utils.Inventories.WardrobeInv;
import me.O_o_Fadi_o_O.OrbitMines.utils.Kit;
import me.O_o_Fadi_o_O.OrbitMines.utils.OMPlayer;
import me.O_o_Fadi_o_O.OrbitMines.utils.ServerData;
import me.O_o_Fadi_o_O.OrbitMines.utils.Utils;
import me.O_o_Fadi_o_O.OrbitMines.utils.Utils.ChatColor;
import me.O_o_Fadi_o_O.OrbitMines.utils.Utils.Currency;
import me.O_o_Fadi_o_O.OrbitMines.utils.Utils.Disguise;
import me.O_o_Fadi_o_O.OrbitMines.utils.Utils.Gadget;
import me.O_o_Fadi_o_O.OrbitMines.utils.Utils.Hat;
import me.O_o_Fadi_o_O.OrbitMines.utils.Utils.Pet;
import me.O_o_Fadi_o_O.OrbitMines.utils.Utils.Server;
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
import me.O_o_Fadi_o_O.OrbitMines.utils.kitpvp.ActiveBooster;
import me.O_o_Fadi_o_O.OrbitMines.utils.kitpvp.Inventories.BoosterInv;
import me.O_o_Fadi_o_O.OrbitMines.utils.kitpvp.Inventories.KitInv;
import me.O_o_Fadi_o_O.OrbitMines.utils.kitpvp.Inventories.KitSelectorInv;
import me.O_o_Fadi_o_O.OrbitMines.utils.kitpvp.Inventories.MasteryInv;
import me.O_o_Fadi_o_O.OrbitMines.utils.kitpvp.Inventories.TeleporterInv;
import me.O_o_Fadi_o_O.OrbitMines.utils.kitpvp.KitPvPPlayer;
import me.O_o_Fadi_o_O.OrbitMines.utils.kitpvp.KitPvPUtils.Booster;
import me.O_o_Fadi_o_O.OrbitMines.utils.kitpvp.KitPvPUtils.KitPvPKit;
import me.O_o_Fadi_o_O.OrbitMines.utils.kitpvp.KitPvPUtils.Mastery;
import me.O_o_Fadi_o_O.OrbitMines.utils.kitpvp.Masteries;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

public class ClickManager {

	private InventoryClickEvent e;
	private Player p;
	private OMPlayer omp;
	private ItemStack item;
	private InventoryAction a;
	
	public ClickManager(InventoryClickEvent e){
		this.e = e;
		this.p = (Player) e.getWhoClicked();
		this.omp = OMPlayer.getOMPlayer(p);
		this.item = e.getCurrentItem();
		this.a = e.getAction();
	}
	
	public void handleServerSelector(){
		if(e.getInventory().getName().equals(new ServerSelectorInv().getInventory().getTitle())){
			e.setCancelled(true);
			
			if(item.getType() == Material.IRON_SWORD){
				omp.toServer(Server.KITPVP);
			}
			else if(item.getType() == Material.DIAMOND_PICKAXE){
				omp.toServer(Server.PRISON);
			}
			else if(item.getType() == Material.WOOD_AXE){
				omp.toServer(Server.CREATIVE);
			}
			else if(item.getType() == Material.WATCH){
				omp.toServer(Server.HUB);
			}
			else if(item.getType() == Material.STONE_HOE){
				omp.toServer(Server.SURVIVAL);
			}
			else if(item.getType() == Material.FISHING_ROD){
				omp.toServer(Server.SKYBLOCK);
			}
			else if(item.getType() == Material.BOW){
				omp.toServer(Server.MINIGAMES);
			}
			else{}
		}
	}
	
	public void handleSettings(){
		if(e.getInventory().getName().equals(new SettingsInv(p).getInventory().getTitle())){
			e.setCancelled(true);
		
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
	
	public void handleCosmeticPerks(){
		if(e.getInventory().getName().equals(new CosmeticPerksInv().getInventory().getTitle())){
			e.setCancelled(true);
		
			if(item.getType() == Material.MONSTER_EGG){
				new PetInv().open(p);
			}
			else if(item.getType() == Material.INK_SACK){
				new ChatColorInv().open(p);
			}
			else if(item.getType() == Material.SKULL_ITEM){
				new DisguiseInv().open(p);
			}
			else if(item.getType() == Material.COMPASS){
				new GadgetInv().open(p);
			}
			else if(item.getType() == Material.LEATHER_CHESTPLATE){
				new WardrobeInv().open(p);
			}
			else if(item.getType() == Material.STRING){
				new TrailInv().open(p);
			}
			else if(item.getType() == Material.PUMPKIN){
				new HatInv().open(p);
			}
			else if(item.getType() == Material.FIREWORK){
				new FireworkInv().open(p);
			}
			else{}
		}
	}
	
	public void handleAnvilInventory(){
		if(ServerData.isServer(Server.HUB, Server.MINIGAMES) && p.getOpenInventory().getTopInventory() instanceof AnvilInventory && p.getOpenInventory().getBottomInventory() == e.getInventory() && item.getType() == Material.ENDER_CHEST){
			new CosmeticPerksInv().open(p);
		}
	}
	
	public void handleFireworks(){
		if(e.getInventory().getName().equals(new FireworkInv().getInventory().getTitle())){
			e.setCancelled(true);
		
			if(item.getItemMeta().getDisplayName().startsWith("§7Color 1:")){
				omp.getFWSettings().nextColor1();
				new FireworkInv().open(p);
			}
			else if(item.getItemMeta().getDisplayName().startsWith("§7Color 2:")){
				omp.getFWSettings().nextColor2();
				new FireworkInv().open(p);
			}
			else if(item.getItemMeta().getDisplayName().startsWith("§7Fade 1:")){
				omp.getFWSettings().nextFade1();
				new FireworkInv().open(p);
			}
			else if(item.getItemMeta().getDisplayName().startsWith("§7Fade 2:")){
				omp.getFWSettings().nextFade2();
				new FireworkInv().open(p);
			}
			else if(item.getItemMeta().getDisplayName().startsWith("§7Trail:")){
				omp.getFWSettings().nextTrail();
				new FireworkInv().open(p);
			}
			else if(item.getItemMeta().getDisplayName().startsWith("§7Flicker:")){
				omp.getFWSettings().nextFlicker();
				new FireworkInv().open(p);
			}
			else if(item.getItemMeta().getDisplayName().startsWith("§7Type:")){
				omp.getFWSettings().nextType();
				new FireworkInv().open(p);
			}
			else if(item.getType() == Material.ENDER_CHEST){	
				new CosmeticPerksInv().open(p);
			}
			else if(item.getType() == Material.ANVIL){
				omp.giveFireworkGun();
			}
			else if(item.getType() == Material.EMPTY_MAP && item.getItemMeta().getDisplayName().equals("§6§l+5 Firework Passes")){
				if(omp.hasVIPPoints(2)){
					new ConfirmInv(item, Currency.VIP_POINTS, 2).open(p);;
				}
				else{
					omp.requiredVIPPoints(2);
				}
			}
			else if(item.getType() == Material.EMPTY_MAP && item.getItemMeta().getDisplayName().equals("§6§l+25 Firework Passes")){
				if(omp.hasVIPPoints(10)){
					new ConfirmInv(item, Currency.VIP_POINTS, 10).open(p);;
				}
				else{
					omp.requiredVIPPoints(10);
				}
			}
			else{}
		}
	}
	
	public void handleHats(){
		if(e.getInventory().getName().equals(new HatInv().getInventory().getTitle())){
			e.setCancelled(true);
		
			if(item.getType() == Material.EMPTY_MAP && item.getItemMeta().getDisplayName().equals("§e§nMore Hats >>")){
				omp.nextHatsPage();
				new HatInv().open(p);
			}
			else if(item.getType() == Material.EMPTY_MAP && item.getItemMeta().getDisplayName().equals("§e§n<< More Hats")){
				omp.prevHatsPage();
				new HatInv().open(p);
			}
			else if(item.getType() == Material.STAINED_GLASS_PANE && item.getItemMeta().getDisplayName().startsWith("§7Hat Block Trail:")){
				if(omp.hasUnlockedHatsBlockTrail()){
					p.closeInventory();
					omp.setHatsBlockTrail(!omp.hasHatsBlockTrail());
				}
				else{
					if(omp.hasHat()){
						if(omp.hasVIPPoints(750)){
							new ConfirmInv(item, Currency.VIP_POINTS, 750);
						}
						else{
							omp.requiredVIPPoints(750);
						}
					}
					else{
						p.playSound(p.getLocation(), Sound.LAVA_POP, 5, 1);
						p.sendMessage("§4§lDENIED §7Required: §6An Hat§7!");
					}
				}
			}
			else if(item.getType() == Material.ENDER_CHEST && !item.getItemMeta().getDisplayName().equals(Hat.CHEST.getName())){
				new CosmeticPerksInv().open(p);
			}
			else if(item.getType() == Material.LAVA_BUCKET){
				if(omp.hasHatEnabled()){
					omp.disableHat();
					p.playSound(p.getLocation(), Sound.FIRE, 5, 1);
					p.closeInventory();
				}
				else{
					p.sendMessage("§7You don't have a Hat §a§lENABLED§7!");
				}
			}
			else{
				for(Hat hat : Hat.values()){
					if(item.getType() == hat.getMaterial() && item.getItemMeta().getDisplayName().equals(hat.getName())){
						if(hat.hasHat(omp)){
							p.closeInventory();
							omp.setHat(hat);
						}
						else{
							if(hat.getVIPRank() != null){
								p.playSound(p.getLocation(), Sound.LAVA_POP, 5, 1);
								p.sendMessage("§4§lDENIED §7Required: " + hat.getVIPRank().getRankString() + " VIP§7!");
							}
							else{
								if(omp.hasVIPPoints(hat.getPrice())){
									new ConfirmInv(item, Currency.VIP_POINTS, hat.getPrice()).open(p);;
								}
								else{
									omp.requiredVIPPoints(hat.getPrice());
								}
							}
						}
					}
				}
			}
		}
	}
	
	public void handleGadgets(){
		if(e.getInventory().getName().equals(new GadgetInv().getInventory().getTitle())){
			e.setCancelled(true);
			
			if(item.getItemMeta().getLore() != null && item.getItemMeta().getLore().contains("§c§lDISABLE §bGadget")){
				p.closeInventory();
				p.playSound(p.getLocation(), Sound.FIRE, 5, 1);
				omp.disableGadget();
			}
			else if(item.getType() == Material.ENDER_CHEST){
				new CosmeticPerksInv().open(p);
			}
			else{
				for(Gadget gadget : Gadget.values()){
					if(item.getType() == gadget.getMaterial() && item.getItemMeta().getDisplayName().equals(gadget.getName())){
						if(gadget.hasGadget(omp)){
							p.closeInventory();
							omp.enableGadget(gadget);
						}
						else{
							if(omp.hasVIPPoints(gadget.getPrice())){
								new ConfirmInv(item, Currency.VIP_POINTS, gadget.getPrice()).open(p);;
							}
							else{
								omp.requiredVIPPoints(gadget.getPrice());
							}
						}
					}
				}
			}
		}
	}
	
	public void handleTrails(){
		if(e.getInventory().getName().equals(new TrailInv().getInventory().getTitle())){
			e.setCancelled(true);
			
			if(item.getType() == Material.REDSTONE_COMPARATOR && item.getItemMeta().getDisplayName().equals("§f§nTrail Settings")){
				if(omp.hasTrail()){
					new TrailSettingsInv().open(p);
				}
				else{
					p.playSound(p.getLocation(), Sound.LAVA_POP, 5, 1);
					p.sendMessage("§4§lDENIED§7! Required: §fA Trail§7.");
				}
			}
			else if(item.getType() == Material.ENDER_CHEST){
				new CosmeticPerksInv().open(p);
			}
			else if(item.getType() == Material.LAVA_BUCKET && !item.getItemMeta().getDisplayName().equals("§6Lava Trail")){
				if(omp.hasTrailEnabled()){
					p.closeInventory();
					p.playSound(p.getLocation(), Sound.FIRE, 5, 1);
					omp.disableTrail();
				}
				else{
					p.sendMessage("§7You don't have a Trail §a§lENABLED§7!");
				}
			}
			else{
				for(Trail trail : Trail.values()){
					if(item.getType() == trail.getMaterial() && item.getItemMeta().getDisplayName().equals(trail.getName())){
						if(trail.hasTrail(omp)){
							p.closeInventory();
							omp.setTrail(trail);
						}
						else{
							if(trail.getVIPRank() != null){
								p.playSound(p.getLocation(), Sound.LAVA_POP, 5, 1);
								p.sendMessage("§4§lDENIED §7Required: " + trail.getVIPRank().getRankString() + " VIP§7!");
							}
							else{
								if(omp.hasVIPPoints(trail.getPrice())){
									new ConfirmInv(item, Currency.VIP_POINTS, trail.getPrice()).open(p);;
								}
								else{
									omp.requiredVIPPoints(trail.getPrice());
								}
							}
						}
					}
				}
			}
		}
	}
	
	public void handleWardrobe(){
		if(e.getInventory().getName().equals(new WardrobeInv().getInventory().getTitle())){
			e.setCancelled(true);
			
			if(item.getType() == Material.LEATHER_CHESTPLATE && item.getItemMeta().getDisplayName().contains("Disco Armor")){
				if(omp.getWardrobe().size() >= 2){
					if(omp.hasUnlockedWardrobeDisco()){
						p.closeInventory();
						omp.setWardrobeDisco(true);
					}
					else{
						if(omp.hasVIPPoints(500)){
							new ConfirmInv(item, Currency.VIP_POINTS, 500).open(p);;
						}
						else{
							omp.requiredVIPPoints(500);
						}
					}
				}
				else{
					p.sendMessage("§4§lDENIED §7Required: " + item.getItemMeta().getLore().get(3).substring(12));
					p.playSound(p.getLocation(), Sound.LAVA_POP, 5, 1);
				}
			}
			else if(item.getType() == Material.IRON_CHESTPLATE && item.getItemMeta().getDisplayName().equals("§7Iron Armor")){
				if(omp.hasPerms(VIPRank.Iron_VIP)){
					p.closeInventory();
					omp.wardrobe(VIPRank.Iron_VIP);
				}
				else{
					p.playSound(p.getLocation(), Sound.LAVA_POP, 5, 1);
					p.sendMessage("§4§lDENIED §7Required: §7§lIron VIP§7!");
				}
			}
			else if(item.getType() == Material.GOLD_CHESTPLATE && item.getItemMeta().getDisplayName().equals("§6Gold Armor")){
				if(omp.hasPerms(VIPRank.Gold_VIP)){
					p.closeInventory();
					omp.wardrobe(VIPRank.Gold_VIP);
				}
				else{
					p.playSound(p.getLocation(), Sound.LAVA_POP, 5, 1);
					p.sendMessage("§4§lDENIED §7Required: §6§lGold VIP§7!");
				}
			}
			else if(item.getType() == Material.DIAMOND_CHESTPLATE && item.getItemMeta().getDisplayName().equals("§bDiamond Armor")){
				if(omp.hasPerms(VIPRank.Diamond_VIP)){
					p.closeInventory();
					omp.wardrobe(VIPRank.Diamond_VIP);
				}
				else{
					p.playSound(p.getLocation(), Sound.LAVA_POP, 5, 1);
					p.sendMessage("§4§lDENIED §7Required: §9§lDiamond VIP§7!");
				}
			}
			else if(item.getType() == Material.CHAINMAIL_CHESTPLATE && item.getItemMeta().getDisplayName().equals("§7Chainmail Armor")){
				if(omp.hasPerms(VIPRank.Emerald_VIP)){
					p.closeInventory();
					omp.wardrobe(VIPRank.Emerald_VIP);
				}
				else{
					p.playSound(p.getLocation(), Sound.LAVA_POP, 5, 1);
					p.sendMessage("§4§lDENIED §7Required: §a§lEmerald VIP§7!");
				}
			}
			else if(item.getType() == Material.ENDER_CHEST){
				new CosmeticPerksInv().open(p);
			}
			else if(item.getType() == Material.LAVA_BUCKET){
				if(omp.hasWardrobeEnabled()){
					p.closeInventory();
					p.playSound(p.getLocation(), Sound.FIRE, 5, 1);
					omp.disableWardrobe();
				}
				else{
					p.sendMessage("§7You don't have any Armor §a§lENABLED§7!");
				}
			}
			else{
				for(Color color : Utils.getWardrobeColors()){
					if(item.getType() == Material.LEATHER_CHESTPLATE && item.getItemMeta().getDisplayName().equals(Utils.getColorName(color) + " Armor")){
						if(omp.hasWardrobe(color)){
							p.closeInventory();
							omp.wardrobe(color);
						}
						else{
							if(omp.hasVIPPoints(Utils.getColorPrice(color))){
								new ConfirmInv(item, Currency.VIP_POINTS, Utils.getColorPrice(color)).open(p);;
							}
							else{
								omp.requiredVIPPoints(Utils.getColorPrice(color));
							}
						}
					}
				}
			}
		}
	}
	
	public void handleChatColors(){
		if(e.getInventory().getName().equals(new ChatColorInv().getInventory().getTitle())){
			e.setCancelled(true);
			
			if(item.getType() == Material.STAINED_GLASS_PANE && item.getItemMeta().getDisplayName().contains("Bold ChatColor")){
				if(omp.hasUnlockedBold()){
					p.closeInventory();
					
					if(omp.isBold()){
						omp.setBold(false);
					}
					else{
						omp.setBold(true);
					}
				}
				else{
					if(omp.hasPerms(VIPRank.Emerald_VIP)){
						if(omp.hasVIPPoints(3000)){
							new ConfirmInv(item, Currency.VIP_POINTS, 3000).open(p);;
						}
						else{
							omp.requiredVIPPoints(3000);
						}
					}
					else{
						p.playSound(p.getLocation(), Sound.LAVA_POP, 5, 1);
						p.sendMessage("§4§lDENIED §7Required: §a§lEmerald VIP§7!");
					}
				}
			}
			else if(item.getType() == Material.STAINED_GLASS_PANE && item.getItemMeta().getDisplayName().contains("Cursive ChatColor")){
				if(omp.hasUnlockedCursive()){
					p.closeInventory();
					
					if(omp.isCursive()){
						omp.setCursive(false);
					}
					else{
						omp.setCursive(true);
					}
				}
				else{
					if(omp.hasPerms(VIPRank.Diamond_VIP)){
						if(omp.hasVIPPoints(2000)){
							new ConfirmInv(item, Currency.VIP_POINTS, 2000).open(p);;
						}
						else{
							omp.requiredVIPPoints(2000);
						}
					}
					else{
						p.playSound(p.getLocation(), Sound.LAVA_POP, 5, 1);
						p.sendMessage("§4§lDENIED §7Required: §9§lDiamond VIP§7!");
					}
				}
			}
			else if(item.getType() == Material.ENDER_CHEST){
				new CosmeticPerksInv().open(p);
			}
			else{
				for(ChatColor chatcolor : ChatColor.values()){
					if(item.getType() == chatcolor.getMaterial() && item.getItemMeta().getDisplayName().equals(chatcolor.getName())){
						if(chatcolor.hasChatColor(omp)){
							p.closeInventory();
							omp.setChatColor(chatcolor);
						}
						else{
							if(chatcolor.getVIPRank() != null){
								p.playSound(p.getLocation(), Sound.LAVA_POP, 5, 1);
								p.sendMessage("§4§lDENIED §7Required: " + chatcolor.getVIPRank().getRankString() + " VIP§7!");
							}
							else{
								if(omp.hasVIPPoints(chatcolor.getPrice())){
									new ConfirmInv(item, Currency.VIP_POINTS, chatcolor.getPrice()).open(p);;
								}
								else{
									omp.requiredVIPPoints(chatcolor.getPrice());
								}
							}
						}
					}
				}
			}
		}
	}
	
	public void handleDisguises(){
		if(e.getInventory().getName().equals(new DisguiseInv().getInventory().getTitle())){
			e.setCancelled(true);
			
			if(item.getType() == Material.ENDER_CHEST){
				new CosmeticPerksInv().open(p);
			}
			else if(item.getType() == Material.LAVA_BUCKET){
				if(omp.isDisguised()){
					p.closeInventory();
					p.playSound(p.getLocation(), Sound.FIRE, 5, 1);
					omp.undisguise();
				}
				else{
					p.sendMessage("§7You don't have a Disguise §a§lENABLED§7!");
				}
			}
			else{
				for(Disguise disguise : Disguise.values()){
					if(item.getType() == disguise.getMaterial() && item.getItemMeta().getDisplayName().equals(disguise.getName())){
						if(disguise.hasDisguise(omp)){
							p.closeInventory();
							p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 5, 1);
							p.sendMessage("§a§lENABLED §7your " + disguise.getName() + "§7.");
						
							omp.disguiseAsMob(disguise.getEntityType(), Bukkit.getOnlinePlayers().toArray(new Player[Bukkit.getOnlinePlayers().size()]));
						}
						else{
							if(disguise.getVIPRank() != null){
								p.playSound(p.getLocation(), Sound.LAVA_POP, 5, 1);
								p.sendMessage("§4§lDENIED §7Required: " + disguise.getVIPRank().getRankString() + " VIP§7!");
							}
							else{
								if(omp.hasVIPPoints(disguise.getPrice())){
									new ConfirmInv(item, Currency.VIP_POINTS, disguise.getPrice()).open(p);;
								}
								else{
									omp.requiredVIPPoints(disguise.getPrice());
								}
							}
						}
					}
				}
			}
		}
	}
	
	public void handlePets(){
		if(e.getInventory().getName().equals(new PetInv().getInventory().getTitle())){
			e.setCancelled(true);
			
			if(item.getType() == Material.NAME_TAG && item.getItemMeta().getDisplayName().startsWith("§fRename ")){
				if(omp.getPetEnabled() != null){
					for(Pet pet : Pet.values()){
						if(item.getItemMeta().getDisplayName().endsWith(pet.getName())){
							new PetRenameGUI(omp, pet).open();;
						}
					}
				}
				else{
					p.sendMessage("§7You don't have a Pet §a§lENABLED§7!");
				}
			}
			else if(item.getType() == Material.ENDER_CHEST){
				new CosmeticPerksInv().open(p);
			}
			else if(item.getType() == Material.LAVA_BUCKET){
				if(omp.getPetEnabled() != null){
					p.closeInventory();
					p.playSound(p.getLocation(), Sound.FIRE, 5, 1);
					omp.disablePet();
				}
				else{
					p.sendMessage("§7You don't have a Pet §a§lENABLED§7!");
				}
			}
			else{
				for(Pet pet : Pet.values()){
					if(item.getType() == pet.getMaterial() && item.getItemMeta().getDisplayName().equals(pet.getName())){
						if(pet.hasPet(omp)){
							p.closeInventory();
							
							if(omp.getPetEnabled() != null){
								omp.disablePet();
							}
							omp.spawnPet(pet);
						}
						else{
							if(omp.hasVIPPoints(pet.getPrice())){
								new ConfirmInv(item, Currency.VIP_POINTS, pet.getPrice()).open(p);;
							}
							else{
								omp.requiredVIPPoints(pet.getPrice());
							}
						}
					}
				}
			}
		}
	}
	
	public void handleTrailSettings(){
		if(e.getInventory().getName().equals(new TrailSettingsInv().getInventory().getTitle())){
			e.setCancelled(true);
			
			if(item.getType() == Material.STAINED_GLASS_PANE && item.getItemMeta().getDisplayName().startsWith("§7§lSpecial Trail")){
				if(omp.hasUnlockedSpecialTrail()){
					omp.setSpecialTrail(!omp.hasSpecialTrail());
					new TrailSettingsInv().open(p);
				}
				else{
					if(omp.hasVIPPoints(750)){
						new ConfirmInv(item, Currency.VIP_POINTS, 750).open(p);;
					}
					else{
						omp.requiredVIPPoints(750);
					}
				}
			}
			else if(item.getType() == Material.NETHER_STAR && item.getItemMeta().getDisplayName().startsWith("§7§lParticle Amount")){
				if(omp.hasPerms(VIPRank.Gold_VIP)){
					omp.addTrailPA();
					
					item.setAmount(omp.getTrailPA());
					ItemMeta meta = item.getItemMeta();
					meta.setDisplayName("§7§lParticle Amount: §f§l" + omp.getTrailPA());
					item.setItemMeta(meta);
				}
				else{
					p.playSound(p.getLocation(), Sound.LAVA_POP, 5, 1);
					p.sendMessage("§4§lDENIED §7Required: §6§lGold VIP§7!");
				}
			}
			else if(item.getType() == Material.ENDER_CHEST){
				new CosmeticPerksInv().open(p);
			}
			else{
				for(TrailType trailtype : TrailType.values()){
					if(item.getType() == Material.STAINED_GLASS_PANE && item.getItemMeta().getDisplayName().startsWith(trailtype.getName())){
						if(trailtype.hasTrailType(omp)){
							if(omp.getTrailType() != trailtype){
								omp.setTrailType(trailtype);
								new TrailSettingsInv().open(p);
							}
						}
						else{
							if(omp.hasVIPPoints(trailtype.getPrice())){
								new ConfirmInv(item, Currency.VIP_POINTS, trailtype.getPrice()).open(p);;
							}
							else{
								omp.requiredVIPPoints(trailtype.getPrice());
							}
						}
					}
				}
			}
		}
	}
	
	public void handleConfirm(){
		if(e.getInventory().getName().equals(new ConfirmInv(null, null, 0).getInventory().getTitle())){
			e.setCancelled(true);
		
			if(item.getType() == Material.STAINED_GLASS_PANE && item.getItemMeta().getDisplayName().equals("§a§lConfirm")){
				ItemStack bitem = e.getInventory().getItem(13);
				ItemStack pitem = e.getInventory().getItem(31);
				
				if(!bitem.getItemMeta().getDisplayName().endsWith("Booster") || !ServerData.isServer(Server.KITPVP)){
					p.sendMessage("§7Item Bought: " + bitem.getItemMeta().getDisplayName() + "§7.");
					p.sendMessage("§7Price: " + pitem.getItemMeta().getDisplayName().substring(9) + "§7.");
				}
				p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 1);
				
				if(bitem.getItemMeta().getDisplayName().equals("§6§l5 Firework Passes")){
					omp.removeVIPPoints(2);
					omp.addFireworkPasses(5);
					new FireworkInv().open(p);
				}
				else if(bitem.getItemMeta().getDisplayName().equals("§6§l25 Firework Passes")){
					omp.removeVIPPoints(10);
					omp.addFireworkPasses(25);
					new FireworkInv().open(p);
				}
				else if(bitem.getItemMeta().getDisplayName().equals("§7Hat Block Trail")){
					omp.removeVIPPoints(750);
					omp.setUnlockedHatsBlockTrail(true);
					new HatInv().open(p);
				}
				else if(bitem.getItemMeta().getDisplayName().endsWith("Disco Armor")){
					omp.removeVIPPoints(500);
					omp.setUnlockedWardrobeDisco(true);
					new WardrobeInv().open(p);
				}
				else if(bitem.getItemMeta().getDisplayName().contains("Bold ChatColor")){
					omp.removeVIPPoints(3000);
					omp.setUnlockedBold(true);
					new ChatColorInv().open(p);
				}
				else if(bitem.getItemMeta().getDisplayName().contains("Cursive ChatColor")){
					omp.removeVIPPoints(2000);
					omp.setUnlockedCursive(true);
					new ChatColorInv().open(p);
				}
				else if(bitem.getItemMeta().getDisplayName().equals("§7§lSpecial Trail")){
					omp.removeVIPPoints(750);
					omp.setUnlockedSpecialTrail(true);
					new TrailSettingsInv().open(p);
				}
				else if(ServerData.isServer(Server.KITPVP) && bitem.getItemMeta().getDisplayName().equals("§6§l+200 Coins")){
					omp.removeOrbitMinesTokens(1);
					omp.getKitPvPPlayer().addMoney(200);
					new OMTShopInv().open(p);
				}
				else if(ServerData.isServer(Server.KITPVP) && bitem.getItemMeta().getDisplayName().equals("§6§l+1000 Coins")){
					omp.removeOrbitMinesTokens(4);
					omp.getKitPvPPlayer().addMoney(1000);
					new OMTShopInv().open(p);
				}
				else if(ServerData.isServer(Server.KITPVP) && bitem.getItemMeta().getDisplayName().equals("§6§l+2500 Coins")){
					omp.removeOrbitMinesTokens(9);
					omp.getKitPvPPlayer().addMoney(2500);
					new OMTShopInv().open(p);
				}
				else if(ServerData.isServer(Server.KITPVP) && bitem.getItemMeta().getDisplayName().equals("§6§l+5000 Coins")){
					omp.removeOrbitMinesTokens(16);
					omp.getKitPvPPlayer().addMoney(5000);
					new OMTShopInv().open(p);
				}
				else if(ServerData.isServer(Server.KITPVP) && bitem.getItemMeta().getDisplayName().equals("§c§l-2% Melee Damage")){
					omp.removeVIPPoints(20);
					Masteries m = omp.getKitPvPPlayer().getMasteries();
					m.setMelee(m.getMelee() -1);
					m.addPoints(1);
					new MasteryInv().open(p);
				}
				else if(ServerData.isServer(Server.KITPVP) && bitem.getItemMeta().getDisplayName().equals("§a§l+2% Melee Damage Taken")){
					omp.removeVIPPoints(20);
					Masteries m = omp.getKitPvPPlayer().getMasteries();
					m.setMeleeProtection(m.getMeleeProtection() -1);
					m.addPoints(1);
					new MasteryInv().open(p);
				}
				else if(ServerData.isServer(Server.KITPVP) && bitem.getItemMeta().getDisplayName().equals("§c§l-4% Range Damage")){
					omp.removeVIPPoints(20);
					Masteries m = omp.getKitPvPPlayer().getMasteries();
					m.setRange(m.getRange() -1);
					m.addPoints(1);
					new MasteryInv().open(p);
				}
				else if(ServerData.isServer(Server.KITPVP) && bitem.getItemMeta().getDisplayName().equals("§a§l+3% Range Damage Taken")){
					omp.removeVIPPoints(20);
					Masteries m = omp.getKitPvPPlayer().getMasteries();
					m.setRangeProtection(m.getRangeProtection() -1);
					m.addPoints(1);
					new MasteryInv().open(p);
				}
				else if(ServerData.isServer(Server.CREATIVE) && bitem.getItemMeta().getDisplayName().equals("§7WorldEdit Command: §d§l//set")){
					CreativePlayer cp = omp.getCreativePlayer();
					omp.removeOrbitMinesTokens(75);
					cp.addWECommand("//set");
					new OMTShopInv().open(p);
				}
				else if(ServerData.isServer(Server.CREATIVE) && bitem.getItemMeta().getDisplayName().equals("§7WorldEdit Command: §d§l//walls")){
					CreativePlayer cp = omp.getCreativePlayer();
					omp.removeOrbitMinesTokens(75);
					cp.addWECommand("//walls");
					new OMTShopInv().open(p);
				}
				else if(ServerData.isServer(Server.CREATIVE) && bitem.getItemMeta().getDisplayName().equals("§7WorldEdit Command: §d§l//line")){
					CreativePlayer cp = omp.getCreativePlayer();
					omp.removeOrbitMinesTokens(50);
					cp.addWECommand("//line");
					new OMTShopInv().open(p);
				}
				else if(ServerData.isServer(Server.CREATIVE) && bitem.getItemMeta().getDisplayName().equals("§7WorldEdit Command: §d§l//replace")){
					CreativePlayer cp = omp.getCreativePlayer();
					omp.removeOrbitMinesTokens(175);
					cp.addWECommand("//replace");
					new OMTShopInv().open(p);
				}
				else{
					for(Gadget gadget : Gadget.values()){
						if(bitem.getItemMeta().getDisplayName().equals(gadget.getName())){
							omp.removeVIPPoints(gadget.getPrice());
							omp.addGadget(gadget);
							new GadgetInv().open(p);
						}
					}
					for(Hat hat : Hat.values()){
						if(bitem.getItemMeta().getDisplayName().equals(hat.getName())){
							omp.removeVIPPoints(hat.getPrice());
							omp.addHat(hat);
							new HatInv().open(p);
						}
					}
					for(Trail trail : Trail.values()){
						if(bitem.getItemMeta().getDisplayName().equals(trail.getName())){
							omp.removeVIPPoints(trail.getPrice());
							omp.addTrail(trail);
							new TrailInv().open(p);
						}
					}
					for(Color color : Utils.getWardrobeColors()){
						if(bitem.getItemMeta().getDisplayName().equals(Utils.getColorName(color) + " Armor")){
							omp.removeVIPPoints(Utils.getColorPrice(color));
							omp.addWardrobe(color);
							new WardrobeInv().open(p);
						}
					}
					for(ChatColor chatcolor : ChatColor.values()){
						if(bitem.getItemMeta().getDisplayName().equals(chatcolor.getName())){
							omp.removeVIPPoints(chatcolor.getPrice());
							omp.addChatColor(chatcolor);
							new ChatColorInv().open(p);
						}
					}
					for(Disguise disguise : Disguise.values()){
						if(bitem.getItemMeta().getDisplayName().equals(disguise.getName())){
							omp.removeVIPPoints(disguise.getPrice());
							omp.addDisguise(disguise);
							new DisguiseInv().open(p);
						}
					}
					for(Pet pet : Pet.values()){
						if(bitem.getItemMeta().getDisplayName().equals(pet.getName())){
							omp.removeVIPPoints(pet.getPrice());
							omp.addPet(pet);
							new PetInv().open(p);
						}
					}
					for(TrailType trailtype : TrailType.values()){
						if(bitem.getItemMeta().getDisplayName().equals(trailtype.getName())){
							omp.removeVIPPoints(trailtype.getPrice());
							omp.addTrailType(trailtype);
							new TrailSettingsInv().open(p);
						}
					}
					if(ServerData.isServer(Server.KITPVP)){
						for(Booster booster : Booster.values()){
							if(bitem.getItemMeta().getDisplayName().equals(booster.getName())){
								omp.removeVIPPoints(booster.getPrice());

								p.sendMessage("§7Item Bought: " + booster.getName() + "§7. (§a30 Minutes§7)");
								p.sendMessage("§7Price: §b§l" + booster.getPrice() + " VIP Points§7.");
								
								p.closeInventory();
							
								ServerData.getKitPvP().setBooster(new ActiveBooster(booster, p.getName(), 30, 0));
								Bukkit.broadcastMessage("" + omp.getName() + " §7activated a §aBooster§7! (§ax" + booster.getMultiplier() + "§7)");
							}
						}
					}
				}
			}
			if(item.getType() == Material.STAINED_GLASS_PANE && item.getItemMeta().getDisplayName().equals("§c§lCancel")){
				ItemStack bitem = e.getInventory().getItem(13);
				
				p.sendMessage("§c§lCancelled §7purchase! (" + bitem.getItemMeta().getDisplayName() + "§7)");
				
				if(bitem.getItemMeta().getDisplayName().contains("Firework")){
					new FireworkInv().open(p);
				}
				else if(bitem.getItemMeta().getDisplayName().contains("Hat")){
					new HatInv().open(p);
				}
				else if(bitem.getItemMeta().getDisplayName().contains("Trail") && !bitem.getItemMeta().getDisplayName().equals("§7Hat Block Trail") && !bitem.getItemMeta().getDisplayName().startsWith("§7§l")){
					new TrailInv().open(p);	
				}
				else if(bitem.getItemMeta().getDisplayName().endsWith("Armor")){
					new WardrobeInv().open(p);	
				}
				else if(bitem.getItemMeta().getDisplayName().endsWith("ChatColor")){
					new ChatColorInv().open(p);	
				}
				else if(bitem.getItemMeta().getDisplayName().endsWith("Disguise")){
					new DisguiseInv().open(p);	
				}
				else if(bitem.getItemMeta().getDisplayName().endsWith("Pet")){
					new PetInv().open(p);	
				}
				else if(bitem.getItemMeta().getDisplayName().endsWith("Trail") && bitem.getItemMeta().getDisplayName().startsWith("§7§l")){
					new TrailSettingsInv().open(p);	
				}
				else if(ServerData.isServer(Server.KITPVP) && bitem.getItemMeta().getDisplayName().endsWith("Booster")){
					new BoosterInv().open(p);	
				}
				else if(ServerData.isServer(Server.KITPVP) && bitem.getItemMeta().getDisplayName().endsWith("Coins")){
					new OMTShopInv().open(p);	
				}
				else if(ServerData.isServer(Server.KITPVP) && bitem.getItemMeta().getDisplayName().equals("§c§l-2% Melee Damage")){
					new MasteryInv().open(p);
				}
				else if(ServerData.isServer(Server.KITPVP) && bitem.getItemMeta().getDisplayName().equals("§a§l+2% Melee Damage Taken")){
					new MasteryInv().open(p);
				}
				else if(ServerData.isServer(Server.KITPVP) && bitem.getItemMeta().getDisplayName().equals("§c§l-4% Range Damage")){
					new MasteryInv().open(p);
				}
				else if(ServerData.isServer(Server.KITPVP) && bitem.getItemMeta().getDisplayName().equals("§a§l+3% Range Damage Taken")){
					new MasteryInv().open(p);
				}
				else if(ServerData.isServer(Server.CREATIVE) && bitem.getItemMeta().getDisplayName().startsWith("§7WorldEdit Command")){
					new OMTShopInv().open(p);
				}
				else{
					for(Gadget gadget : Gadget.values()){
						if(bitem.getItemMeta().getDisplayName().equals(gadget.getName())){
							new GadgetInv().open(p);
						}
					}
				}
			}
		}
	}
	
	public void handleOMTShop(){
		if(e.getInventory().getName().equals(new OMTShopInv().getInventory().getName())){
			e.setCancelled(true);
			
			if(ServerData.isServer(Server.KITPVP)){
				if(item.getType() == Material.GOLD_NUGGET){
					if(item.getItemMeta().getDisplayName().equals("§6§l+200 Coins")){
						if(omp.hasOrbitMinesTokens(1)){
							new ConfirmInv(item, Currency.ORBITMINES_TOKENS, 1).open(p);
						}
						else{
							omp.requiredOMT(1);
						}
					}
					else if(item.getItemMeta().getDisplayName().equals("§6§l+1000 Coins")){
						if(omp.hasOrbitMinesTokens(4)){
							new ConfirmInv(item, Currency.ORBITMINES_TOKENS, 4).open(p);
						}
						else{
							omp.requiredOMT(4);
						}
					}
					else if(item.getItemMeta().getDisplayName().equals("§6§l+2500 Coins")){
						if(omp.hasOrbitMinesTokens(9)){
							new ConfirmInv(item, Currency.ORBITMINES_TOKENS, 9).open(p);
						}
						else{
							omp.requiredOMT(9);
						}
					}
					else if(item.getItemMeta().getDisplayName().equals("§6§l+5000 Coins")){
						if(omp.hasOrbitMinesTokens(16)){
							new ConfirmInv(item, Currency.ORBITMINES_TOKENS, 16).open(p);
						}
						else{
							omp.requiredOMT(16);
						}
					}
					else{}
				}
			}
			else if(ServerData.isServer(Server.CREATIVE)){
				if(item.getType() == Material.WOOD_AXE){
					if(item.getItemMeta().getDisplayName().equals("§7WorldEdit Command: §d§l//set") && !item.getItemMeta().getLore().contains("§a§lUnlocked")){
						if(omp.hasOrbitMinesTokens(75)){
							new ConfirmInv(item, Currency.ORBITMINES_TOKENS, 75).open(p);
						}
						else{
							omp.requiredOMT(75);
						}
					}
					if(item.getItemMeta().getDisplayName().equals("§7WorldEdit Command: §d§l//walls") && !item.getItemMeta().getLore().contains("§a§lUnlocked")){
						if(omp.hasOrbitMinesTokens(75)){
							new ConfirmInv(item, Currency.ORBITMINES_TOKENS, 75).open(p);
						}
						else{
							omp.requiredOMT(75);
						}
					}
					if(item.getItemMeta().getDisplayName().equals("§7WorldEdit Command: §d§l//line") && !item.getItemMeta().getLore().contains("§a§lUnlocked")){
						if(omp.hasOrbitMinesTokens(50)){
							new ConfirmInv(item, Currency.ORBITMINES_TOKENS, 50).open(p);
						}
						else{
							omp.requiredOMT(50);
						}
					}
					if(item.getItemMeta().getDisplayName().equals("§7WorldEdit Command: §d§l//replace") && !item.getItemMeta().getLore().contains("§a§lUnlocked")){
						if(omp.hasOrbitMinesTokens(175)){
							new ConfirmInv(item, Currency.ORBITMINES_TOKENS, 175).open(p);
						}
						else{
							omp.requiredOMT(175);
						}
					}
				}
			}
			else{}
			//TODO Other SERVERS.
		}
	}
	
	public void handleKitSelector(){
		if(e.getInventory().getName().equals(new KitSelectorInv().getInventory().getName())){
			e.setCancelled(true);
			KitPvPPlayer kp = omp.getKitPvPPlayer();
			
			for(KitPvPKit kitpvpkit : KitPvPKit.values()){
				if(item.getType() == kitpvpkit.getMaterial() && item.getItemMeta().getLore().contains("§4§lLocked")){
					if(a == InventoryAction.PICKUP_HALF){
						p.sendMessage("§7This kit is §4§lLocked§7 for you! Buy it with §6Left Click§7!");
					}
					if(a == InventoryAction.PICKUP_ALL){						
						new KitInv(kitpvpkit, 1).open(p);
					}
				}
				
				for(int level = 1; level <= kitpvpkit.getHighestLevel(); level++){
					if(item.getType() == kitpvpkit.getMaterial() && item.getItemMeta().getLore().contains("§a§lUnlocked §7§o(§a§oLvL " + level + "§7§o)") || level == 1 && item.getType() == kitpvpkit.getMaterial() && item.getItemMeta().getLore().contains("§d§lFREE Kit Saturday!")){
						if(a == InventoryAction.PICKUP_HALF){
							kp.giveKit(kitpvpkit, level);
							kp.teleportToMap();
							p.closeInventory();
						}
						if(a == InventoryAction.PICKUP_ALL){
							if(level == kitpvpkit.getHighestLevel()){					
								new KitInv(kitpvpkit, level).open(p);
							}
							else{					
								new KitInv(kitpvpkit, level +1).open(p);
							}
						}
					}
				}
			}
		}
	}
	
	public void handleKits(){
		KitPvPPlayer kp = omp.getKitPvPPlayer();
		
		for(KitPvPKit kitpvpkit : KitPvPKit.values()){
			for(int level = 1; level <= kitpvpkit.getHighestLevel(); level++){
				if(e.getInventory().getName().equals(new KitInv(kitpvpkit, level).getInventory().getName())){
					e.setCancelled(true);

					if(item.getType() == Material.REDSTONE_BLOCK && item.getItemMeta().getDisplayName().equals("§4§l§o<< Back")){
						new KitSelectorInv().open(p);
					}
					else if(item.getType() == Material.NETHER_STAR && item.getItemMeta().getDisplayName().equals("§b§l" + kitpvpkit.getName() + " §7§o(§a§oLvL 1§7§o)")){				
						new KitInv(kitpvpkit, 1).open(p);
					}
					else if(item.getType() == Material.NETHER_STAR && item.getItemMeta().getDisplayName().equals("§b§l" + kitpvpkit.getName() + " §7§o(§a§oLvL 2§7§o)")){				
						new KitInv(kitpvpkit, 2).open(p);
					}
					else if(item.getType() == Material.NETHER_STAR && item.getItemMeta().getDisplayName().equals("§b§l" + kitpvpkit.getName() + " §7§o(§a§oLvL 3§7§o)")){				
						new KitInv(kitpvpkit, 3).open(p);
					}
					else if(item.getType() == Material.EMERALD_BLOCK && item.getItemMeta().getLore() != null && item.getItemMeta().getLore().contains("§7Price: §6" + kitpvpkit.getPrice(level) + " Coins")){
						if(kp.hasMoney(kitpvpkit.getPrice(level))){
							kp.removeMoney(kitpvpkit.getPrice(level));
							kp.setUnlockedKitLevel(kitpvpkit, level);
							
							p.sendMessage("§7You have bought the '§b§l" + kitpvpkit.getName() + "§7' kit! §7§o(§a§oLvL " + level + "§7§o)");
							new KitInv(kitpvpkit, level).open(p);
						}
						else{
							p.sendMessage("§7You don't have enough §6Coins§7!");
						}
					}
					else if(item.getType() == Material.EMERALD_BLOCK && item.getItemMeta().getLore() != null && item.getItemMeta().getLore().contains("§7Price: §6" + kitpvpkit.getPrice(level) + " OMT")){
						if(omp.hasOrbitMinesTokens(kitpvpkit.getPrice(level))){
							omp.removeOrbitMinesTokens(kitpvpkit.getPrice(level));
							kp.setUnlockedKitLevel(kitpvpkit, level);
							
							p.sendMessage("§7You have bought the '§b§l" + kitpvpkit.getName() + "§7' kit! §7§o(§a§oLvL " + level + "§7§o)");
							new KitInv(kitpvpkit, level).open(p);
						}
						else{
							p.sendMessage("§7You don't have enough §eOrbitMines Tokens§7!");
						}
					}
					else{}
				}
			}
		}
	}
	
	public void handleBoosters(){
		if(e.getInventory().getName().equals(new BoosterInv().getInventory().getName())){
			e.setCancelled(true);
			
			for(Booster booster : Booster.values()){
				if(item.getType() == booster.getMaterial() && item.getItemMeta().getDisplayName().equals(booster.getName())){
					if(!booster.hasPerms(omp)){
						p.closeInventory();
						omp.requiredVIPRank(booster.getVIPRank());
					}
					else{
						if(omp.hasVIPPoints(booster.getPrice())){
							if(ServerData.getKitPvP().getBooster() == null){
								new ConfirmInv(item, Currency.VIP_POINTS, booster.getPrice()).open(p);;
							}
							else{
								p.sendMessage("§a" + ServerData.getKitPvP().getBooster().getPlayer() + "'s Booster§7 is currently active.");
								p.playSound(p.getLocation(), Sound.ENDERMAN_SCREAM, 5, 1);
							}
						}
						else{
							omp.requiredVIPPoints(booster.getPrice());
						}
					}
				}
			}
		}
	}
	
	public void handleTeleporter(){
		if(e.getInventory().getName().equals(new TeleporterInv().getInventory().getName())){
			e.setCancelled(true);
			
			if(item.getType() == Material.SKULL_ITEM){
				Player player = Bukkit.getPlayer(item.getItemMeta().getDisplayName().split(" ")[1].substring(2));
				
				p.closeInventory();
				p.teleport(player);
				p.playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 5, 1);
				p.sendMessage("§eTeleporting to " + item.getItemMeta().getDisplayName() + "§e...");
			}
		}
	}
	
	public void handleMasteries(){
		if(e.getInventory().getName().equals(new MasteryInv().getInventory().getName())){
			e.setCancelled(true);
			KitPvPPlayer kp = omp.getKitPvPPlayer();
			Masteries m = kp.getMasteries();
			
			if(item.getType() == Material.EMERALD){
				if(item.getItemMeta().getDisplayName().equals("§a§l+2% Melee Damage")){
					if(m.getPoints() > 0){
						m.setPoints(m.getPoints() -1);
						m.setMelee(m.getMelee() +1);
						m.update();
						new MasteryInv().open(p);
						p.sendMessage("§7Item Bought: " + item.getItemMeta().getDisplayName() + "§7.");
						p.sendMessage("§7Price: §c§l1 Mastery Point§7.");
					}
				}
				else if(item.getItemMeta().getDisplayName().equals("§c§l-2% Melee Damage Taken")){
					if(m.getPoints() > 0){
						m.setPoints(m.getPoints() -1);
						m.setMeleeProtection(m.getMeleeProtection() +1);
						m.update();
						new MasteryInv().open(p);
						p.sendMessage("§7Item Bought: " + item.getItemMeta().getDisplayName() + "§7.");
						p.sendMessage("§7Price: §c§l1 Mastery Point§7.");
					}
				}
				else if(item.getItemMeta().getDisplayName().equals("§a§l+4% Range Damage")){
					if(m.getPoints() > 0){
						m.setPoints(m.getPoints() -1);
						m.setRange(m.getRange() +1);
						m.update();
						new MasteryInv().open(p);
						p.sendMessage("§7Item Bought: " + item.getItemMeta().getDisplayName() + "§7.");
						p.sendMessage("§7Price: §c§l1 Mastery Point§7.");
					}
				}
				else if(item.getItemMeta().getDisplayName().equals("§c§l-3% Range Damage Taken")){
					if(m.getPoints() > 0){
						m.setPoints(m.getPoints() -1);
						m.setRangeProtection(m.getRangeProtection() +1);
						m.update();
						new MasteryInv().open(p);
						p.sendMessage("§7Item Bought: " + item.getItemMeta().getDisplayName() + "§7.");
						p.sendMessage("§7Price: §c§l1 Mastery Point§7.");
					}
				}
				else{}
			}
			if(item.getType() == Material.BLAZE_POWDER){
				if(item.getItemMeta().getDisplayName().equals("§c§l-2% Melee Damage")){
					if(m.getMasteryLevel(Mastery.MELEE) > 0){
						new ConfirmInv(item, Currency.VIP_POINTS, 20).open(p);
					}
				}
				else if(item.getItemMeta().getDisplayName().equals("§a§l+2% Melee Damage Taken")){
					if(m.getMasteryLevel(Mastery.MELEE_PROTECTION) > 0){
						new ConfirmInv(item, Currency.VIP_POINTS, 20).open(p);
					}
				}
				else if(item.getItemMeta().getDisplayName().equals("§c§l-4% Range Damage")){
					if(m.getMasteryLevel(Mastery.RANGE) > 0){
						new ConfirmInv(item, Currency.VIP_POINTS, 20).open(p);
					}
				}
				else if(item.getItemMeta().getDisplayName().equals("§a§l+3% Range Damage Taken")){
					if(m.getMasteryLevel(Mastery.RANGE_PROTECTION) > 0){
						new ConfirmInv(item, Currency.VIP_POINTS, 20).open(p);
					}
				}
				else{}
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	public void handlePlotKits(){
		if(e.getInventory().getName().startsWith(new PlotKitInv(null, null).getInventory().getName())){
			if(item.getItemMeta() != null && item.getItemMeta().getDisplayName() != null && item.getItemMeta().getDisplayName().endsWith("§7§lEmpty")){
				e.setCancelled(true);
			}
			final ItemStack cursor = e.getCursor();
			final int slot = e.getSlot();
			
			if(cursor != null && cursor.getType() != Material.AIR){
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
					e.setCancelled(true);
					new BukkitRunnable(){
						public void run(){
							e.setCursor(cursor);
						}
					}.runTaskLater(Start.getInstance(), 1);
					p.sendMessage("Hi");
				}
				
				p.updateInventory();
			}
			
			new BukkitRunnable(){
				public void run(){
					Inventory inv = e.getClickedInventory();
					CreativePlayer cp = omp.getCreativePlayer();
					Plot plot = cp.getPlot();
					String kitname = inv.getName().substring(9);
					
					ItemStack[] contents = new ItemStack[36];
					ItemStack[] armorcontents = new ItemStack[4];
					armorcontents[0] = inv.getItem(0);
					armorcontents[1] = inv.getItem(1);
					armorcontents[2] = inv.getItem(2);
					armorcontents[3] = inv.getItem(3);
					
					contents[0] = inv.getItem(18);
					contents[1] = inv.getItem(19);
					contents[2] = inv.getItem(20);
					contents[3] = inv.getItem(21);
					contents[4] = inv.getItem(22);
					contents[5] = inv.getItem(23);
					contents[6] = inv.getItem(24);
					contents[7] = inv.getItem(25);
					contents[8] = inv.getItem(26);
					
					for(Kit kit : plot.getPvPKits()){
						if(kit.getKitName().equals(kitname)){
							kit.setContents(contents);
							kit.setArmorContents(armorcontents);
							plot.setPvPKits(plot.getPvPKits());
							inv.setContents(plot.getPvPInventories().get(kit).getContects());
						}
					}
				}
			}.runTaskLater(Start.getInstance(), 1);
		}
	}
	
	public void handlePlotInfo(){
		if(e.getInventory().getName().equals(new PlotInfoInv().getInventory().getName())){
			e.setCancelled(true);
			
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
	
	public void handlePlotMembers(){
		if(e.getInventory().getName().equals(new PlotMembersInv().getInventory().getName())){
			e.setCancelled(true);
			
			if(item.getType() == Material.PAPER && item.getItemMeta().getDisplayName().equals("§d§lPlot Info")){
				new PlotInfoInv().open(p);
			}
		}
	}
	
	public void handlePlotSetup(){
		if(e.getInventory().getName().equals(new PlotSetupInv().getInventory().getName())){
			e.setCancelled(true);
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
		}
	}
}
