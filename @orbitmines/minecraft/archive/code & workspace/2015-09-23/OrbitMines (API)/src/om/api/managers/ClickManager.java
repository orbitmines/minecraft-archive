package om.api.managers;

import om.api.API;
import om.api.handlers.players.OMPlayer;
import om.api.invs.cp.ChatColorInv;
import om.api.invs.cp.CosmeticPerksInv;
import om.api.invs.cp.DisguiseInv;
import om.api.invs.cp.FireworkInv;
import om.api.invs.cp.GadgetInv;
import om.api.invs.cp.HatInv;
import om.api.invs.cp.PetInv;
import om.api.invs.cp.PetRenameGUI;
import om.api.invs.cp.TrailInv;
import om.api.invs.cp.TrailSettingsInv;
import om.api.invs.cp.WardrobeInv;
import om.api.invs.others.ConfirmInv;
import om.api.invs.others.InventorySeeInv;
import om.api.utils.ColorUtils;
import om.api.utils.enums.Currency;
import om.api.utils.enums.Server;
import om.api.utils.enums.cp.ChatColor;
import om.api.utils.enums.cp.CosmeticPerk;
import om.api.utils.enums.cp.Disguise;
import om.api.utils.enums.cp.Gadget;
import om.api.utils.enums.cp.Hat;
import om.api.utils.enums.cp.Pet;
import om.api.utils.enums.cp.Trail;
import om.api.utils.enums.cp.TrailType;
import om.api.utils.enums.ranks.VIPRank;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.EnchantingInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public abstract class ClickManager {
	
	protected API api;
	protected InventoryClickEvent e;
	protected Player p;
	protected OMPlayer omp;
	protected ItemStack item;
	protected InventoryAction a;
	
	public ClickManager(InventoryClickEvent e){
		this.api = API.getInstance();
		this.e = e;
		this.p = (Player) e.getWhoClicked();
		this.omp = OMPlayer.getOMPlayer(p);
		this.item = e.getCurrentItem();
		this.a = e.getAction();
	}
	
	protected abstract void checkConfirmItems(ItemStack bitem, ItemStack pitem);
	protected abstract void checkCancelItems(ItemStack bitem);
	
	public boolean itemIsNull(){
		return !(item != null && item.getItemMeta() != null && item.getItemMeta().getDisplayName() != null);
	}
	
	public void handleServerSelector(){
		if(e.getInventory().getName().equals(api.getServerSelector().getInventory().getTitle())){
			e.setCancelled(true);
			
			if(!itemIsNull()){
				for(Server server : Server.values()){
					if(item.getType() == server.getMaterial()){
						omp.toServer(server);
					}
				}
			}
		}
	}
	
	public void handleCosmeticPerks(CosmeticPerk... perks){
		if(e.getInventory().getName().equals(new CosmeticPerksInv().getInventory().getTitle())){
			e.setCancelled(true);
			
			if(!itemIsNull()){
				for(CosmeticPerk perk : perks){
					if(item.getType() == perk.getMaterial()){
						perk.openInv(p);
					}
				}
			}
		}
	}
	
	public void handleFireworks(){
		if(e.getInventory().getName().equals(new FireworkInv().getInventory().getTitle())){
			e.setCancelled(true);
			
			if(!itemIsNull()){
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
	}

	public void handleHats(){
		if(e.getInventory().getName().equals(new HatInv().getInventory().getTitle())){
			e.setCancelled(true);

			if(!itemIsNull()){
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
							p.sendMessage("§4§lDENIED §7Required: §6A Hat§7!");
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
									omp.requiredVIPRank(hat.getVIPRank());
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
	}
	
	public void handleGadgets(){
		if(e.getInventory().getName().equals(new GadgetInv().getInventory().getTitle())){
			e.setCancelled(true);
			
			if(!itemIsNull()){
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
	}
	
	public void handleTrails(){
		if(e.getInventory().getName().equals(new TrailInv().getInventory().getTitle())){
			e.setCancelled(true);
			
			if(!itemIsNull()){
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
									omp.requiredVIPRank(trail.getVIPRank());
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
	}
	
	public void handleWardrobe(){
		if(e.getInventory().getName().equals(new WardrobeInv().getInventory().getTitle())){
			e.setCancelled(true);
			
			if(!itemIsNull()){
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
						omp.requiredVIPRank(VIPRank.Iron_VIP);
					}
				}
				else if(item.getType() == Material.GOLD_CHESTPLATE && item.getItemMeta().getDisplayName().equals("§6Gold Armor")){
					if(omp.hasPerms(VIPRank.Gold_VIP)){
						p.closeInventory();
						omp.wardrobe(VIPRank.Gold_VIP);
					}
					else{
						omp.requiredVIPRank(VIPRank.Gold_VIP);
					}
				}
				else if(item.getType() == Material.DIAMOND_CHESTPLATE && item.getItemMeta().getDisplayName().equals("§bDiamond Armor")){
					if(omp.hasPerms(VIPRank.Diamond_VIP)){
						p.closeInventory();
						omp.wardrobe(VIPRank.Diamond_VIP);
					}
					else{
						omp.requiredVIPRank(VIPRank.Diamond_VIP);
					}
				}
				else if(item.getType() == Material.CHAINMAIL_CHESTPLATE && item.getItemMeta().getDisplayName().equals("§7Chainmail Armor")){
					if(omp.hasPerms(VIPRank.Emerald_VIP)){
						p.closeInventory();
						omp.wardrobe(VIPRank.Emerald_VIP);
					}
					else{
						omp.requiredVIPRank(VIPRank.Emerald_VIP);
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
					for(Color color : ColorUtils.getWardrobe()){
						if(item.getType() == Material.LEATHER_CHESTPLATE && item.getItemMeta().getDisplayName().equals(ColorUtils.getName(color) + " Armor")){
							if(omp.hasWardrobe(color)){
								p.closeInventory();
								omp.wardrobe(color);
							}
							else{
								if(omp.hasVIPPoints(250)){
									new ConfirmInv(item, Currency.VIP_POINTS, 250).open(p);;
								}
								else{
									omp.requiredVIPPoints(250);
								}
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
			
			if(!itemIsNull()){
				if(item.getType() == Material.STAINED_GLASS_PANE && item.getItemMeta().getDisplayName().contains("Bold ChatColor")){
					if(omp.hasPerms(VIPRank.Emerald_VIP) && omp.hasUnlockedBold()){
						p.closeInventory();
						
						omp.setBold(!omp.isBold());
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
							omp.requiredVIPRank(VIPRank.Emerald_VIP);
						}
					}
				}
				else if(item.getType() == Material.STAINED_GLASS_PANE && item.getItemMeta().getDisplayName().contains("Cursive ChatColor")){
					if(omp.hasPerms(VIPRank.Diamond_VIP) && omp.hasUnlockedCursive()){
						p.closeInventory();
						
						omp.setCursive(!omp.isCursive());
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
							omp.requiredVIPRank(VIPRank.Diamond_VIP);
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
									omp.requiredVIPRank(chatcolor.getVIPRank());
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
	}
	
	public void handleDisguises(){
		if(e.getInventory().getName().equals(new DisguiseInv().getInventory().getTitle())){
			e.setCancelled(true);
			
			if(!itemIsNull()){
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
							
								omp.disguiseAsMob(disguise.getEntityType(), false, Bukkit.getOnlinePlayers().toArray(new Player[Bukkit.getOnlinePlayers().size()]));
							}
							else{
								if(disguise.getVIPRank() != null){
									omp.requiredVIPRank(disguise.getVIPRank());
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
	}
	
	public void handlePets(){
		if(e.getInventory().getName().equals(new PetInv().getInventory().getTitle())){
			e.setCancelled(true);
			
			if(!itemIsNull()){
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
	}
	
	public void handleTrailSettings(){
		if(e.getInventory().getName().equals(new TrailSettingsInv().getInventory().getTitle())){
			e.setCancelled(true);
			
			if(!itemIsNull()){
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
						omp.requiredVIPRank(VIPRank.Gold_VIP);
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
	}
	
	public void handleConfirm(){
		if(e.getInventory().getName().equals(new ConfirmInv(null, null, 0).getInventory().getTitle())){
			e.setCancelled(true);
			
			if(!itemIsNull()){
				if(item.getType() == Material.STAINED_GLASS_PANE && item.getItemMeta().getDisplayName().equals("§a§lConfirm")){
					ItemStack bitem = e.getInventory().getItem(13);
					ItemStack pitem = e.getInventory().getItem(31);
					
					checkConfirmItems(bitem, pitem);
				}
				if(item.getType() == Material.STAINED_GLASS_PANE && item.getItemMeta().getDisplayName().equals("§c§lCancel")){
					ItemStack bitem = e.getInventory().getItem(13);
					
					p.sendMessage("§c§lCancelled §7purchase! (" + bitem.getItemMeta().getDisplayName() + "§7)");
					
					checkCancelItems(bitem);
				}
			}
		}
	}
	
	public void handleTeleporter(){
		if(e.getInventory().getName().equals("§0§lTeleporter")){
			e.setCancelled(true);
			
			if(!itemIsNull()){
				if(item.getType() == Material.SKULL_ITEM){
					Player player = null;
					if(item.getItemMeta().getDisplayName().split(" ").length > 1){
						player = Bukkit.getPlayer(item.getItemMeta().getDisplayName().split(" ")[1].substring(2));
					}
					else{
						player = Bukkit.getPlayer(item.getItemMeta().getDisplayName().substring(2));
					}
					
					p.closeInventory();
					p.teleport(player);
					p.playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 5, 1);
					p.sendMessage("§eTeleporting to " + item.getItemMeta().getDisplayName() + "§e...");
				}
			}
		}
	}
	
	public void handleChestShopViewer(){
		if(e.getInventory().getName().equals("§0§lChest Shop Viewer")){
			e.setCancelled(true);
		}
	}
	
	public void handleInventoryViewer(){
		if(e.getInventory().getName().endsWith(new InventorySeeInv(null).getInventory().getName())){
			e.setCancelled(true);
		}
	}
	
	public void handleEnchantmentTable(){
		if(e.getClickedInventory() instanceof EnchantingInventory){
			if(item != null && item.getType() == Material.INK_SACK && item.getDurability() == 4){
				e.setCancelled(true);
			}
		}
	}
}
