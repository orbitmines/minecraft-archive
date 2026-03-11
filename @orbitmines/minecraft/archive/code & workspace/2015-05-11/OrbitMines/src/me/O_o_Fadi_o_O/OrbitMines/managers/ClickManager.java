package me.O_o_Fadi_o_O.OrbitMines.managers;

import me.O_o_Fadi_o_O.OrbitMines.utils.Inventories.ChatColorInv;
import me.O_o_Fadi_o_O.OrbitMines.utils.Inventories.ConfirmInv;
import me.O_o_Fadi_o_O.OrbitMines.utils.Inventories.CosmeticPerksInv;
import me.O_o_Fadi_o_O.OrbitMines.utils.Inventories.DisguiseInv;
import me.O_o_Fadi_o_O.OrbitMines.utils.Inventories.FireworkInv;
import me.O_o_Fadi_o_O.OrbitMines.utils.Inventories.GadgetInv;
import me.O_o_Fadi_o_O.OrbitMines.utils.Inventories.HatInv;
import me.O_o_Fadi_o_O.OrbitMines.utils.Inventories.PetInv;
import me.O_o_Fadi_o_O.OrbitMines.utils.Inventories.PetRenameGUI;
import me.O_o_Fadi_o_O.OrbitMines.utils.Inventories.ServerSelectorInv;
import me.O_o_Fadi_o_O.OrbitMines.utils.Inventories.SettingsInv;
import me.O_o_Fadi_o_O.OrbitMines.utils.Inventories.TrailInv;
import me.O_o_Fadi_o_O.OrbitMines.utils.Inventories.TrailSettingsInv;
import me.O_o_Fadi_o_O.OrbitMines.utils.Inventories.WardrobeInv;
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
import me.O_o_Fadi_o_O.OrbitMines.utils.kitpvp.ActiveBooster;
import me.O_o_Fadi_o_O.OrbitMines.utils.kitpvp.Inventories.BoosterInv;
import me.O_o_Fadi_o_O.OrbitMines.utils.kitpvp.Inventories.OMTShopInv;
import me.O_o_Fadi_o_O.OrbitMines.utils.kitpvp.KitPvPUtils.Booster;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ClickManager {

	private InventoryClickEvent e;
	private Player p;
	private OMPlayer omp;
	private ItemStack item;
	
	public ClickManager(InventoryClickEvent e){
		this.e = e;
		this.p = (Player) e.getWhoClicked();
		this.omp = OMPlayer.getOMPlayer(p);
		this.item = e.getCurrentItem();
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
						p.sendMessage("§9Cosmetic Perks §8| §4§lDENIED §7Required: §6An Hat§7!");
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
					p.sendMessage("§9Cosmetic Perks §8| §7You don't have a Hat §a§lENABLED§7!");
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
								p.sendMessage("§9Cosmetic Perks §8| §4§lDENIED §7Required: " + hat.getVIPRank().getRankString() + " VIP§7!");
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
					p.sendMessage("§9Cosmetic Perks §8| §4§lDENIED§7! Required: §fA Trail§7.");
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
					p.sendMessage("§9Cosmetic Perks §8| §7You don't have a Trail §a§lENABLED§7!");
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
								p.sendMessage("§9Cosmetic Perks §8| §4§lDENIED §7Required: " + trail.getVIPRank().getRankString() + " VIP§7!");
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
					p.sendMessage("§9Cosmetic Perks §8| §4§lDENIED §7Required: " + item.getItemMeta().getLore().get(3).substring(12));
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
					p.sendMessage("§9Cosmetic Perks §8| §4§lDENIED §7Required: §7§lIron VIP§7!");
				}
			}
			else if(item.getType() == Material.GOLD_CHESTPLATE && item.getItemMeta().getDisplayName().equals("§6Gold Armor")){
				if(omp.hasPerms(VIPRank.Gold_VIP)){
					p.closeInventory();
					omp.wardrobe(VIPRank.Gold_VIP);
				}
				else{
					p.playSound(p.getLocation(), Sound.LAVA_POP, 5, 1);
					p.sendMessage("§9Cosmetic Perks §8| §4§lDENIED §7Required: §6§lGold VIP§7!");
				}
			}
			else if(item.getType() == Material.DIAMOND_CHESTPLATE && item.getItemMeta().getDisplayName().equals("§bDiamond Armor")){
				if(omp.hasPerms(VIPRank.Diamond_VIP)){
					p.closeInventory();
					omp.wardrobe(VIPRank.Diamond_VIP);
				}
				else{
					p.playSound(p.getLocation(), Sound.LAVA_POP, 5, 1);
					p.sendMessage("§9Cosmetic Perks §8| §4§lDENIED §7Required: §9§lDiamond VIP§7!");
				}
			}
			else if(item.getType() == Material.CHAINMAIL_CHESTPLATE && item.getItemMeta().getDisplayName().equals("§7Chainmail Armor")){
				if(omp.hasPerms(VIPRank.Emerald_VIP)){
					p.closeInventory();
					omp.wardrobe(VIPRank.Emerald_VIP);
				}
				else{
					p.playSound(p.getLocation(), Sound.LAVA_POP, 5, 1);
					p.sendMessage("§9Cosmetic Perks §8| §4§lDENIED §7Required: §a§lEmerald VIP§7!");
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
					p.sendMessage("§9Cosmetic Perks §8| §7You don't have any Armor §a§lENABLED§7!");
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
						p.sendMessage("§9Cosmetic Perks §8| §4§lDENIED §7Required: §a§lEmerald VIP§7!");
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
						p.sendMessage("§9Cosmetic Perks §8| §4§lDENIED §7Required: §9§lDiamond VIP§7!");
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
								p.sendMessage("§9Cosmetic Perks §8| §4§lDENIED §7Required: " + chatcolor.getVIPRank().getRankString() + " VIP§7!");
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
					p.sendMessage("§9Cosmetic Perks §8| §7You don't have a Disguise §a§lENABLED§7!");
				}
			}
			else{
				for(Disguise disguise : Disguise.values()){
					if(item.getType() == disguise.getMaterial() && item.getItemMeta().getDisplayName().equals(disguise.getName())){
						if(disguise.hasDisguise(omp)){
							p.closeInventory();
							p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 5, 1);
							p.sendMessage("§9Cosmetic Perks §8| §a§lENABLED §7your " + disguise.getName() + "§7.");
						
							omp.disguiseAsMob(disguise.getEntityType(), Bukkit.getOnlinePlayers().toArray(new Player[Bukkit.getOnlinePlayers().size()]));
						}
						else{
							if(disguise.getVIPRank() != null){
								p.playSound(p.getLocation(), Sound.LAVA_POP, 5, 1);
								p.sendMessage("§9Cosmetic Perks §8| §4§lDENIED §7Required: " + disguise.getVIPRank().getRankString() + " VIP§7!");
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
					p.sendMessage("§9Cosmetic Perks §8| §7You don't have a Pet §a§lENABLED§7!");
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
					p.sendMessage("§9Cosmetic Perks §8| §7You don't have a Pet §a§lENABLED§7!");
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
					p.sendMessage("§9Cosmetic Perks §8| §4§lDENIED §7Required: §6§lGold VIP§7!");
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
					if(pitem.getType() == Material.DIAMOND){
						p.sendMessage("§9Cosmetic Perks §8| §7Item Bought: " + bitem.getItemMeta().getDisplayName() + "§7.");
						p.sendMessage("§9Cosmetic Perks §8| §7Price: " + pitem.getItemMeta().getDisplayName().substring(9) + "§7.");
					}
					if(pitem.getType() == Material.GOLD_INGOT){
						p.sendMessage("§eOMT Shop §8| §7Item Bought: " + bitem.getItemMeta().getDisplayName() + "§7.");
						p.sendMessage("§eOMT Shop §8| §7Price: " + pitem.getItemMeta().getDisplayName().substring(9) + "§7.");
					}
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

								p.sendMessage("§aBoosters §8| §7Item Bought: " + booster.getName() + "§7. (§a30 Minutes§7)");
								p.sendMessage("§aBoosters §8| §7Price: §b§l" + booster.getPrice() + " VIP Points§7.");
								
								p.closeInventory();
							
								ServerData.getKitPvP().setBooster(new ActiveBooster(booster, p.getName(), 30, 0));
								Bukkit.broadcastMessage("§aBoosters §8| " + omp.getName() + " §7activated a §aBooster§7! (§ax" + booster.getMultiplier() + "§7)");
							}
						}
					}
				}
			}
			if(item.getType() == Material.STAINED_GLASS_PANE && item.getItemMeta().getDisplayName().equals("§c§lCancel")){
				ItemStack bitem = e.getInventory().getItem(13);
				ItemStack pitem = e.getInventory().getItem(31);
				
				if(bitem.getItemMeta().getDisplayName().endsWith("Booster")){
					p.sendMessage("§aBoosters §8| §c§lCancelled §7purchase! (" + bitem.getItemMeta().getDisplayName() + "§7)");
				}
				else if(pitem.getType() == Material.DIAMOND){
					p.sendMessage("§9Cosmetic Perks §8| §c§lCancelled §7purchase! (" + bitem.getItemMeta().getDisplayName() + "§7)");
				}
				else if(pitem.getType() == Material.GOLD_INGOT){
					p.sendMessage("§eOMT Shop §8| §c§lCancelled §7purchase! (" + bitem.getItemMeta().getDisplayName() + "§7)");
				}
				else{}
				
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
		if(!ServerData.isServer(Server.HUB)){
			if(e.getInventory().getName().equals(new OMTShopInv().getInventory().getName())){
				e.setCancelled(true);
				
				if(ServerData.isServer(Server.KITPVP)){
					if(item.getType() == Material.GOLD_NUGGET){
						if(item.getItemMeta().getDisplayName().equals("§6§l+200 Coins")){
							if(omp.hasOrbitMinesTokens(1)){
								new ConfirmInv(item, Currency.ORBITMINES_TOKENS, 1);
							}
							else{
								omp.removeOrbitMinesTokens(1);
							}
						}
						else if(item.getItemMeta().getDisplayName().equals("§6§l+1000 Coins")){
							if(omp.hasOrbitMinesTokens(4)){
								new ConfirmInv(item, Currency.ORBITMINES_TOKENS, 4);
							}
							else{
								omp.removeOrbitMinesTokens(4);
							}
						}
						else if(item.getItemMeta().getDisplayName().equals("§6§l+2500 Coins")){
							if(omp.hasOrbitMinesTokens(9)){
								new ConfirmInv(item, Currency.ORBITMINES_TOKENS, 9);
							}
							else{
								omp.removeOrbitMinesTokens(9);
							}
						}
						else if(item.getItemMeta().getDisplayName().equals("§6§l+5000 Coins")){
							if(omp.hasOrbitMinesTokens(16)){
								new ConfirmInv(item, Currency.ORBITMINES_TOKENS, 16);
							}
							else{
								omp.removeOrbitMinesTokens(16);
							}
						}
						else{}
					}
				}
				//TODO Other SERVERS.
			}
		}
	}
}
