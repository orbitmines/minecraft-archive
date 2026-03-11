package om.fog.managers;

import om.api.invs.cp.ChatColorInv;
import om.api.invs.cp.PetInv;
import om.api.invs.cp.TrailInv;
import om.api.invs.cp.TrailSettingsInv;
import om.api.managers.ClickManager;
import om.api.utils.enums.cp.ChatColor;
import om.api.utils.enums.cp.Trail;
import om.api.utils.enums.cp.TrailType;
import om.fog.handlers.Quest;
import om.fog.handlers.Shields;
import om.fog.handlers.Swords;
import om.fog.handlers.Tools;
import om.fog.handlers.players.FoGPlayer;
import om.fog.invs.BankInv;
import om.fog.invs.EnchBookInv;
import om.fog.invs.FactionSelectInv;
import om.fog.invs.GalaxyMapInv;
import om.fog.invs.OreCraftInv;
import om.fog.invs.OreSellInv;
import om.fog.invs.QuestInv;
import om.fog.invs.ShopInv;
import om.fog.utils.enums.Faction;
import om.fog.utils.enums.Ore;
import om.fog.utils.enums.Rarity;
import om.fog.utils.enums.Repeat;
import om.fog.utils.enums.ShieldLore;
import om.fog.utils.enums.ShieldLore.ArmorType;
import om.fog.utils.enums.ShieldLore.ShieldLoreLevel;
import om.fog.utils.enums.Suit;
import om.fog.utils.enums.SwordLore;
import om.fog.utils.enums.SwordLore.SwordLoreLevel;
import om.fog.utils.enums.ToolLore;
import om.fog.utils.enums.ToolLore.ToolLoreLevel;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;

public class FoGClickManager extends ClickManager {

	private FoGPlayer omp;
	
	public FoGClickManager(InventoryClickEvent e) {
		super(e);
		
		omp = FoGPlayer.getFoGPlayer(p);
	}

	@Override
	protected void checkCancelItems(ItemStack bitem) {
		if(bitem.getItemMeta().getDisplayName().contains("Trail") && !bitem.getItemMeta().getDisplayName().equals("§7Hat Block Trail") && !bitem.getItemMeta().getDisplayName().startsWith("§7§l")){
			new TrailInv().open(p);	
		}
		else if(bitem.getItemMeta().getDisplayName().endsWith("ChatColor")){
			new ChatColorInv().open(p);	
		}
		else if(bitem.getItemMeta().getDisplayName().endsWith("Pet")){
			new PetInv().open(p);	
		}
		else if(bitem.getItemMeta().getDisplayName().endsWith("Trail") && bitem.getItemMeta().getDisplayName().startsWith("§7§l")){
			new TrailSettingsInv().open(p);	
		}
		else{}
	}

	@Override
	protected void checkConfirmItems(ItemStack bitem, ItemStack pitem) {
		FoGPlayer omp = FoGPlayer.getFoGPlayer(p);
		p.sendMessage("§7Item Bought: " + bitem.getItemMeta().getDisplayName() + "§7.");
		p.sendMessage("§7Price: " + pitem.getItemMeta().getDisplayName().substring(9) + "§7.");
		p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 1);
		
		if(bitem.getItemMeta().getDisplayName().contains("Bold ChatColor")){
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
			for(Trail trail : Trail.values()){
				if(bitem.getItemMeta().getDisplayName().equals(trail.getName())){
					omp.removeVIPPoints(trail.getPrice());
					omp.addTrail(trail);
					new TrailInv().open(p);
					return;
				}
			}
			for(ChatColor chatcolor : ChatColor.values()){
				if(bitem.getItemMeta().getDisplayName().equals(chatcolor.getName())){
					omp.removeVIPPoints(chatcolor.getPrice());
					omp.addChatColor(chatcolor);
					new ChatColorInv().open(p);
					return;
				}
			}
			for(TrailType trailtype : TrailType.values()){
				if(bitem.getItemMeta().getDisplayName().equals(trailtype.getName())){
					omp.removeVIPPoints(trailtype.getPrice());
					omp.addTrailType(trailtype);
					new TrailSettingsInv().open(p);
					return;
				}
			}
		}
	}
	
	public boolean handleSoulBound(){
		if(p.getOpenInventory().getTopInventory().getType() != InventoryType.CRAFTING){
			if(!itemIsNull() && item.getItemMeta().getLore() != null && item.getItemMeta().getLore().contains("§fSoulbound")){
				e.setCancelled(true);
				return true;
			}
		}
		return false;
	}
	
	public boolean handleFactionSelector(){
		if(e.getInventory().getName().equals(new FactionSelectInv().getInventory().getName())){
			e.setCancelled(true);
			
			if(!itemIsNull() && item.getType() == Material.STAINED_CLAY){
				switch(item.getDurability()){
					case 4:
						omp.joinFaction(Faction.ALPHA);
						break;
					case 3:
						omp.joinFaction(Faction.BETA);
						break;
					case 14:
						omp.joinFaction(Faction.OMEGA);
						break;
				}
			}
			
			return true;
		}
		return false;
	}
	
	public boolean handleCraftingInventory(){
		if(e.getClickedInventory() instanceof CraftingInventory){
			e.setCancelled(true);
			
			if(!itemIsNull()){
				if(item.getType() == Material.EYE_OF_ENDER){
					new GalaxyMapInv().open(p);
				}
				else if(item.getType() == Material.WORKBENCH){
					new OreCraftInv().open(p);
				}
				else if(item.getType() == Material.MAP){
					new QuestInv().open(p);
				}
				else{}
			}
			
			return true;
		}
		return false;
	}
	
	public boolean handleGalaxyMap(){
		if(e.getInventory().getName().equals(new GalaxyMapInv().getInventory().getName())){
			e.setCancelled(true);
			
			//TODO Teleportation
			
			return true;
		}
		return false;
	}
	
	public boolean handleOreCrafting(){
		if(e.getInventory().getName().equals(new OreCraftInv().getInventory().getName())){
			e.setCancelled(true);
			
			if(!itemIsNull() && item.getType() == Material.INK_SACK){
				for(Ore ore : Ore.values()){
					if(ore.isCraftAble() && ore.getDurability() == item.getDurability()){
						if(ore.canCraft(omp)){
							ore.craft(omp);
						}
						else{
							p.sendMessage("§7You do not have enough ores to craft " + ore.getName() + "§7.");
						}
					}
				}
			}
			
			return true;
		}
		return false;
	}
	
	public boolean handleQuests(){
		if(e.getInventory().getName().equals(new QuestInv().getInventory().getName())){
			e.setCancelled(true);
			
			if(!itemIsNull() && e.getClickedInventory().getName().equals(new QuestInv().getInventory().getName())){
				if(item.getType() == Material.MAP){
					Quest quest = Quest.getByName(item.getItemMeta().getDisplayName().substring(4));
					
					if(!quest.isProgressQuest()){
						if(quest.canCompleteQuest(omp)){
							quest.completeQuest(omp);
							new QuestInv().open(p);
						}
						else{
							p.sendMessage("§7You cannot complete this quest yet.");
						}
					}
				}
				else if(item.getType() == Material.EMPTY_MAP){
					Quest quest = Quest.getByName(item.getItemMeta().getDisplayName().substring(4));
					omp.startQuest(quest);
					new QuestInv().open(p);
					
					p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 1);
					p.sendMessage("§7You have successfully activated the §f" + quest.getName() + "§7 quest.");
					
					if(omp.isInTutorial() && omp.getTutorial().getStage() == 9 && omp.getCurrentQuestsAsNewList().contains(Quest.getByName("First Training")) && omp.getCurrentQuestsAsNewList().contains(Quest.getByName("Copper Mining"))){
						omp.getTutorial().toNextStage();
					}
				}
				else if(item.getType() == Material.PAPER){
					Quest quest = Quest.getByName(item.getItemMeta().getDisplayName().substring(4));
					
					if(quest.getRepeat() != Repeat.NEVER){
				        p.sendMessage("§7You can complete this challenge again at §f" + omp.getQuestCooldownDate(quest) + "§7.");
					}
					else{
						p.sendMessage("§7This quest can only be done once.");
					}
				}
				else{}
			}
			
			return true;
		}
		return false;
	}
	
	public boolean handleFoGShop(){
		if(e.getInventory().getName().equals(new ShopInv().getInventory().getName())){
			e.setCancelled(true);
			
			if(!itemIsNull()){
				if(item.getType() == Material.INK_SACK && item.getItemMeta().getDisplayName().equals("§7Sell Ores")){
					new OreSellInv().open(p);
				}
				else if(item.getType() == Material.BOOK && item.getItemMeta().getDisplayName().equals("§7Buy Enchantment Books")){
					new EnchBookInv().open(p);
				}
				else{}
			}
			
			return true;
		}
		else if(e.getInventory().getName().equals(new OreSellInv().getInventory().getName())){
			e.setCancelled(true);
			
			if(!itemIsNull() && item.getType() == Material.INK_SACK){
				for(Ore ore : Ore.values()){
					if(ore.getDurability() == item.getDurability()){
						int amount = omp.removeAll(Material.INK_SACK, ore.getDurability());
						
						if(amount != 0){
							int sellPrice = amount * ore.getSellPrice();
							
							p.playSound(p.getLocation(), Sound.LEVEL_UP, 1, 1);
							p.sendMessage("§7Sold " + ore.getColor() + amount + " " + ore.getName() + " Ore§7 for §7§l" + sellPrice + " Silver§7.");
							omp.addSilver(sellPrice);
							
							if(omp.isInTutorial() && omp.getTutorial().getStage() == 12){
								omp.getTutorial().toNextStage();
							}
						}
						else{
							p.sendMessage("§7You don't have any " + ore.getName() + " Ore§7 left.");
						}
						break;
					}
				}
			}
			
			return true;
		}
		else if(e.getInventory().getName().equals(new EnchBookInv().getInventory().getName())){
			e.setCancelled(true);
			
			if(!itemIsNull() && item.getType() == Material.BOOK){
				for(Rarity rarity : Rarity.values()){
					if(item.getItemMeta().getDisplayName().equals("§7Buy " + rarity.getName() + " Enchantment §7Book")){
						if(omp.hasSilver(rarity.getPrice())){
							omp.removeSilver(rarity.getPrice());

							p.playSound(p.getLocation(), Sound.LEVEL_UP, 1, 1);
							p.getInventory().addItem(rarity.getRandomItem());
							if(rarity == Rarity.UNCOMMON){
								p.sendMessage("§7You have received an " + rarity.getName() + " Enchantment§7 Book.");
							}
							else{
								p.sendMessage("§7You have received a " + rarity.getName() + " Enchantment§7 Book.");
							}
						}
						else{
							omp.requiredSilver(rarity.getPrice());
						}
						break;
					}
				}
			}
			
			return true;
		}
		else{
			return false;
		}
	}
	
	@SuppressWarnings("deprecation")
	public boolean handleBank(){
		BankInv bankInv = omp.getBank().getBankInv();
		
		if(e.getInventory().getName().equals(bankInv.getInventory().getName())){
			if(e.getClickedInventory() == p.getOpenInventory().getTopInventory()){
				if(e.getSlot() <= 35){
					if(!itemIsNull() && item.getType() == Material.BARRIER){
						e.setCancelled(true);
					}
				}
				else{
					e.setCancelled(true);
					
					if(!itemIsNull()){
						if(item.getType() == Material.EMPTY_MAP && item.getItemMeta().getDisplayName().equals("§e▲§l Scroll Up")){
							if(bankInv.getPage() != 1){
								omp.updateCurrentBankNow();
								bankInv.setPage(bankInv.getPage() -1);
								bankInv.open(p);
							}
						}
						else if(item.getType() == Material.EMPTY_MAP && item.getItemMeta().getDisplayName().equals("§e▼§l Scroll Down")){
							if(omp.getBank().getSize() > 36 + 9 * (bankInv.getPage() -1)){
								omp.updateCurrentBankNow();
								bankInv.setPage(bankInv.getPage() +1);
								bankInv.open(p);
							}
						}
						else if(item.getType() == Material.STAINED_GLASS_PANE && item.getItemMeta().getDisplayName().equals("§4§lDestroy Item")){
							if(a == InventoryAction.SWAP_WITH_CURSOR){
								e.setCursor(null);
								p.playSound(p.getLocation(), Sound.EXPLODE, 5, 1);
							}
						}
						else{}
					}
				}
			}
			
			omp.updateCurrentBank();
			
			return true;
		}
		return false;
	}
	
	public boolean handleSuits(){
		if(omp.getFaction() != null){
			for(Suit suit : Suit.values()){
				if(e.getInventory().getName().equals("§0§l" + suit.getName(omp.getFaction()))){
					e.setCancelled(true);
					
					if(!itemIsNull()){
						if(item.getType() == suit.getSword() && item.getItemMeta().getDisplayName().equals("§7Swords")){
							omp.getSwords(suit).getSwordsInv().open(p);
						}
						else if(item.getType() == Material.IRON_FENCE && item.getItemMeta().getDisplayName().equals("§7Shields")){
							omp.getShields(suit).getShieldsInv().open(p);
						}
						else if(item.getType() == suit.getTool() && item.getItemMeta().getDisplayName().equals("§6Tools")){
							omp.getTools(suit).getToolsInv().open(p);
						}
						else if(item.getType() == Material.BOW && item.getItemMeta().getDisplayName().equals("§6Bows")){
							
						}
						else{}
					}
					
					return true;
				}
			}
		}
		return false;
	}
	
	@SuppressWarnings("deprecation")
	public boolean handleSwords(){
		if(omp.getFaction() != null){
			for(Suit suit : omp.getRepairTokens().keySet()){
				Swords swords = omp.getSwords(suit);
				
				if(e.getInventory().getName().equals(swords.getSwordsInv().getInventory().getName())){
					if(!itemIsNull() && e.getClickedInventory() == p.getOpenInventory().getTopInventory()){
						e.setCancelled(true);
						
						if(item.getType() == suit.getSword()){
							if(a == InventoryAction.SWAP_WITH_CURSOR){
								ItemStack cursor = e.getCursor();
								
								if(cursor != null && cursor.getType() == Material.ENCHANTED_BOOK && cursor.getItemMeta() != null && cursor.getItemMeta().getDisplayName() != null){
									SwordLoreLevel sl = SwordLore.getEnchantment(cursor);
								
									if(sl != null){
										if(sl.getLore() == SwordLore.DAMAGE && sl.getLevel() == 1){
											if(omp.isInTutorial() && omp.getTutorial().getStage() == 4){
												omp.getTutorial().toNextStage();
											}
										}
										swords.addSwordItem(omp, sl);
										e.setCursor(null);
										p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 1);
										swords.getSwordsInv().open(p);
									}
								}
							}
						}
						else if(item.getType() == Material.ENCHANTED_BOOK && item.getItemMeta().getLore() != null && item.getItemMeta().getLore().contains("§7Click to §cRemove Enchantment") && SwordLore.getEnchantment(item) != null){
							swords.removeSwordItem(omp, e.getSlot());
							p.playSound(p.getLocation(), Sound.FIRE_IGNITE, 5, 1);
							swords.getSwordsInv().open(p);
						}
						else{}
					}
					
					return true;
				}
			}
		}
		return false;
	}
	
	@SuppressWarnings("deprecation")
	public boolean handleShields(){
		if(omp.getFaction() != null){
			for(Suit suit : omp.getRepairTokens().keySet()){
				Shields shields = omp.getShields(suit);
				
				if(e.getInventory().getName().equals(shields.getShieldsInv().getInventory().getName())){
					if(!itemIsNull() && e.getClickedInventory() == p.getOpenInventory().getTopInventory()){
						e.setCancelled(true);
						
						int slot = e.getSlot();
						ArmorType uType = null;
						ArmorType rType = null;
						if(slot >= 36){
							for(ArmorType type : ArmorType.getCorrectOrder()){
								if(type.getSuitArmor(suit) == item.getType()){
									uType = type;
									break;
								}
							}
						}
						else{
							for(ArmorType type : ArmorType.getCorrectOrder()){
								if(slot > type.getRIndex() * 9){
									rType = type;
									break;
								}
							}
						}
						
						if(uType != null){
							if(item.getType() == uType.getSuitArmor(suit)){
								if(a == InventoryAction.SWAP_WITH_CURSOR){
									ItemStack cursor = e.getCursor();
									
									if(cursor != null && cursor.getType() == Material.ENCHANTED_BOOK && cursor.getItemMeta() != null && cursor.getItemMeta().getDisplayName() != null){
										ShieldLoreLevel sl = ShieldLore.getEnchantment(cursor);
									
										if(sl != null){
											if(sl.getLore() == ShieldLore.SHIELD_REGEN && sl.getLevel() == 1){
												if(omp.isInTutorial() && omp.getTutorial().getStage() == 5){
													omp.getTutorial().toNextStage();
												}
											}
										
											shields.addShieldItem(omp, uType, sl);
											e.setCursor(null);
											p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 1);
											shields.getShieldsInv().open(p);
										}
									}
								}
							}
						}
						else if(rType != null){
							if(item.getType() == Material.ENCHANTED_BOOK && item.getItemMeta().getLore() != null && item.getItemMeta().getLore().contains("§7Click to §cRemove Enchantment") && ShieldLore.getEnchantment(item) != null){
								shields.removeShieldItem(omp, rType, e.getSlot() - (1 + (rType.getRIndex() * 9)));
								p.playSound(p.getLocation(), Sound.FIRE_IGNITE, 5, 1);
								shields.getShieldsInv().open(p);
							}
						}
						else{}
					}
					
					return true;
				}
			}
		}
		return false;
	}
	
	@SuppressWarnings("deprecation")
	public boolean handleTools(){
		if(omp.getFaction() != null){
			for(Suit suit : omp.getRepairTokens().keySet()){
				Tools tools = omp.getTools(suit);
				
				if(e.getInventory().getName().equals(tools.getToolsInv().getInventory().getName())){
					if(!itemIsNull() && e.getClickedInventory() == p.getOpenInventory().getTopInventory()){
						e.setCancelled(true);
						
						if(item.getType() == suit.getTool()){
							if(a == InventoryAction.SWAP_WITH_CURSOR){
								ItemStack cursor = e.getCursor();
								
								if(cursor != null && cursor.getType() == Material.ENCHANTED_BOOK && cursor.getItemMeta() != null && cursor.getItemMeta().getDisplayName() != null){
									ToolLoreLevel sl = ToolLore.getEnchantment(cursor);
								
									if(sl != null){
										tools.addToolItem(omp, sl);
										e.setCursor(null);
										p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 1);
										tools.getToolsInv().open(p);
									}
								}
							}
						}
						else if(item.getType() == Material.ENCHANTED_BOOK && item.getItemMeta().getLore() != null && item.getItemMeta().getLore().contains("§7Click to §cRemove Enchantment") && ToolLore.getEnchantment(item) != null){
							tools.removeToolItem(omp, e.getSlot());
							p.playSound(p.getLocation(), Sound.FIRE_IGNITE, 5, 1);
							tools.getToolsInv().open(p);
						}
						else{}
					}
					
					return true;
				}
			}
		}
		return false;
	}
}
