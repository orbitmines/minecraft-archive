package om.kitpvp.managers;

import om.api.invs.cp.ChatColorInv;
import om.api.invs.cp.PetInv;
import om.api.invs.cp.TrailInv;
import om.api.invs.cp.TrailSettingsInv;
import om.api.invs.others.ConfirmInv;
import om.api.managers.ClickManager;
import om.api.utils.enums.Currency;
import om.api.utils.enums.cp.ChatColor;
import om.api.utils.enums.cp.Pet;
import om.api.utils.enums.cp.Trail;
import om.api.utils.enums.cp.TrailType;
import om.kitpvp.KitPvP;
import om.kitpvp.handlers.ActiveBooster;
import om.kitpvp.handlers.Masteries;
import om.kitpvp.handlers.players.KitPvPPlayer;
import om.kitpvp.invs.BoosterInv;
import om.kitpvp.invs.KitInv;
import om.kitpvp.invs.KitPvPOMTShopInv;
import om.kitpvp.invs.KitSelectorInv;
import om.kitpvp.invs.MasteryInv;
import om.kitpvp.utils.enums.Booster;
import om.kitpvp.utils.enums.KitPvPKit;
import om.kitpvp.utils.enums.Mastery;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class KitPvPClickManager extends ClickManager {

	private KitPvP kitpvp;
	
	public KitPvPClickManager(InventoryClickEvent e) {
		super(e);
		
		kitpvp = KitPvP.getInstance();
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
		else if(bitem.getItemMeta().getDisplayName().endsWith("Booster")){
			new BoosterInv().open(p);	
		}
		else if(bitem.getItemMeta().getDisplayName().endsWith("Coins")){
			new KitPvPOMTShopInv().open(p);	
		}
		else if(bitem.getItemMeta().getDisplayName().equals("§c§l-2% Melee Damage")){
			new MasteryInv().open(p);
		}
		else if(bitem.getItemMeta().getDisplayName().equals("§a§l+2% Melee Damage Taken")){
			new MasteryInv().open(p);
		}
		else if(bitem.getItemMeta().getDisplayName().equals("§c§l-4% Range Damage")){
			new MasteryInv().open(p);
		}
		else if(bitem.getItemMeta().getDisplayName().equals("§a§l+3% Range Damage Taken")){
			new MasteryInv().open(p);
		}
		else{}
	}

	@Override
	protected void checkConfirmItems(ItemStack bitem, ItemStack pitem) {
		KitPvPPlayer omp = KitPvPPlayer.getKitPvPPlayer(p);
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
		else if(bitem.getItemMeta().getDisplayName().equals("§6§l+200 Coins")){
			omp.removeOrbitMinesTokens(1);
			omp.addMoney(200);
			new KitPvPOMTShopInv().open(p);
		}
		else if(bitem.getItemMeta().getDisplayName().equals("§6§l+1000 Coins")){
			omp.removeOrbitMinesTokens(4);
			omp.addMoney(1000);
			new KitPvPOMTShopInv().open(p);
		}
		else if(bitem.getItemMeta().getDisplayName().equals("§6§l+2500 Coins")){
			omp.removeOrbitMinesTokens(9);
			omp.addMoney(2500);
			new KitPvPOMTShopInv().open(p);
		}
		else if(bitem.getItemMeta().getDisplayName().equals("§6§l+5000 Coins")){
			omp.removeOrbitMinesTokens(16);
			omp.addMoney(5000);
			new KitPvPOMTShopInv().open(p);
		}
		else if(bitem.getItemMeta().getDisplayName().equals("§c§l-2% Melee Damage")){
			omp.removeVIPPoints(20);
			Masteries m = omp.getMasteries();
			m.setMelee(m.getMelee() -1);
			m.addPoints(1);
			new MasteryInv().open(p);
		}
		else if(bitem.getItemMeta().getDisplayName().equals("§a§l+2% Melee Damage Taken")){
			omp.removeVIPPoints(20);
			Masteries m = omp.getMasteries();
			m.setMeleeProtection(m.getMeleeProtection() -1);
			m.addPoints(1);
			new MasteryInv().open(p);
		}
		else if(bitem.getItemMeta().getDisplayName().equals("§c§l-4% Range Damage")){
			omp.removeVIPPoints(20);
			Masteries m = omp.getMasteries();
			m.setRange(m.getRange() -1);
			m.addPoints(1);
			new MasteryInv().open(p);
		}
		else if(bitem.getItemMeta().getDisplayName().equals("§a§l+3% Range Damage Taken")){
			omp.removeVIPPoints(20);
			Masteries m = omp.getMasteries();
			m.setRangeProtection(m.getRangeProtection() -1);
			m.addPoints(1);
			new MasteryInv().open(p);
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
			for(Pet pet : Pet.values()){
				if(bitem.getItemMeta().getDisplayName().equals(pet.getName())){
					omp.removeVIPPoints(pet.getPrice());
					omp.addPet(pet);
					new PetInv().open(p);
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
			for(Booster booster : Booster.values()){
				if(bitem.getItemMeta().getDisplayName().equals(booster.getName())){
					//TODO IF NOT ACTIVE
					omp.removeVIPPoints(booster.getPrice());
					
					p.closeInventory();
				
					kitpvp.setBooster(new ActiveBooster(booster, p.getName(), 30, 0));
					Bukkit.broadcastMessage("" + omp.getName() + " §7activated a §aBooster§7! (§ax" + booster.getMultiplier() + "§7)");
					return;
				}
			}
		}
	}
	
	public boolean handleOMTShop(){
		if(e.getInventory().getName().equals(new KitPvPOMTShopInv().getInventory().getName())){
			e.setCancelled(true);
			
			if(!itemIsNull() && item.getType() == Material.GOLD_NUGGET){
				switch(item.getItemMeta().getDisplayName()){
					case "§6§l+200 Coins":
						if(omp.hasOrbitMinesTokens(1)){
							new ConfirmInv(item, Currency.ORBITMINES_TOKENS, 1).open(p);
						}
						else{
							omp.requiredOMT(1);
						}
						break;
					case "§6§l+1000 Coins":
						if(omp.hasOrbitMinesTokens(4)){
							new ConfirmInv(item, Currency.ORBITMINES_TOKENS, 4).open(p);
						}
						else{
							omp.requiredOMT(4);
						}
						break;
					case "§6§l+2500 Coins":
						if(omp.hasOrbitMinesTokens(9)){
							new ConfirmInv(item, Currency.ORBITMINES_TOKENS, 9).open(p);
						}
						else{
							omp.requiredOMT(9);
						}
						break;
					case "§6§l+5000 Coins":
						if(omp.hasOrbitMinesTokens(16)){
							new ConfirmInv(item, Currency.ORBITMINES_TOKENS, 16).open(p);
						}
						else{
							omp.requiredOMT(16);
						}
						break;
				}
			}
			
			return true;
		}
		return false;
	}

	public boolean handleKitSelector(){
		if(e.getInventory().getName().equals(new KitSelectorInv().getInventory().getName())){
			e.setCancelled(true);
			
			if(!itemIsNull()){
				KitPvPPlayer omp = KitPvPPlayer.getKitPvPPlayer(p);
				
				for(KitPvPKit kitpvpkit : KitPvPKit.values()){
					if(item.getType() == kitpvpkit.getMaterial() && item.getItemMeta().getLore().contains("§4§lLocked")){
						if(a == InventoryAction.PICKUP_HALF){
							p.sendMessage("§7This kit is §4§lLocked§7 for you! Buy it with §6Left Click§7!");
						}
						if(a == InventoryAction.PICKUP_ALL){						
							new KitInv(kitpvpkit, 1).open(p);
						}
						break;
					}
					
					for(int level = 1; level <= kitpvpkit.getMaxLevel(); level++){
						if(item.getType() == kitpvpkit.getMaterial() && item.getItemMeta().getLore().contains("§a§lUnlocked §7§o(§a§oLvL " + level + "§7§o)") || level == 1 && item.getType() == kitpvpkit.getMaterial() && item.getItemMeta().getLore().contains("§d§lFREE Kit Saturday!")){
							if(a == InventoryAction.PICKUP_HALF){
								omp.giveKit(kitpvpkit, level);
								omp.teleportToMap();
								p.closeInventory();
							}
							if(a == InventoryAction.PICKUP_ALL){
								if(level == kitpvpkit.getMaxLevel()){					
									new KitInv(kitpvpkit, level).open(p);
								}
								else{					
									new KitInv(kitpvpkit, level +1).open(p);
								}
							}
							break;
						}
					}
				}
			}
			
			return true;
		}
		return false;
	}
	
	public boolean handleKits(){
		KitPvPPlayer omp = KitPvPPlayer.getKitPvPPlayer(p);
		
		for(KitPvPKit kitpvpkit : KitPvPKit.values()){
			for(int level = 1; level <= kitpvpkit.getMaxLevel(); level++){
				if(e.getInventory().getName().equals(new KitInv(kitpvpkit, level).getInventory().getName())){
					e.setCancelled(true);
					
					if(!itemIsNull()){
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
							if(omp.hasMoney(kitpvpkit.getPrice(level))){
								omp.removeMoney(kitpvpkit.getPrice(level));
								omp.setUnlockedKitLevel(kitpvpkit, level);
								
								p.sendMessage("§7You have bought the '§b§l" + kitpvpkit.getName() + "§7' kit! §7§o(§a§oLvL " + level + "§7§o)");
								new KitInv(kitpvpkit, level).open(p);
							}
							else{
								p.sendMessage("§7You don't have enough §6Coins§7!");
							}
						}
						else if(item.getType() == Material.EMERALD_BLOCK && item.getItemMeta().getLore() != null && item.getItemMeta().getLore().contains("§7Price: §e" + kitpvpkit.getPrice(level) + " OMT")){
							if(omp.hasOrbitMinesTokens(kitpvpkit.getPrice(level))){
								omp.removeOrbitMinesTokens(kitpvpkit.getPrice(level));
								omp.setUnlockedKitLevel(kitpvpkit, level);
								
								p.sendMessage("§7You have bought the '§b§l" + kitpvpkit.getName() + "§7' kit! §7§o(§a§oLvL " + level + "§7§o)");
								new KitInv(kitpvpkit, level).open(p);
							}
							else{
								p.sendMessage("§7You don't have enough §eOrbitMines Tokens§7!");
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
	
	public boolean handleBoosters(){
		if(e.getInventory().getName().equals(new BoosterInv().getInventory().getName())){
			e.setCancelled(true);
			
			if(!itemIsNull()){
				for(Booster booster : Booster.values()){
					if(item.getType() == booster.getMaterial() && item.getItemMeta().getDisplayName().equals(booster.getName())){
						if(!booster.hasPerms(omp)){
							p.closeInventory();
							omp.requiredVIPRank(booster.getVIPRank());
						}
						else{
							if(omp.hasVIPPoints(booster.getPrice())){
								if(kitpvp.getBooster() == null){
									new ConfirmInv(item, Currency.VIP_POINTS, booster.getPrice()).open(p);;
								}
								else{
									p.sendMessage("§a" + kitpvp.getBooster().getPlayer() + "'s Booster§7 is currently active.");
									p.playSound(p.getLocation(), Sound.ENDERMAN_SCREAM, 5, 1);
								}
							}
							else{
								omp.requiredVIPPoints(booster.getPrice());
							}
						}
						break;
					}
				}
			}
			
			return true;
		}
		return false;
	}
	
	public boolean handleMasteries(){
		if(e.getInventory().getName().equals(new MasteryInv().getInventory().getName())){
			e.setCancelled(true);
			
			if(!itemIsNull()){
				KitPvPPlayer omp = KitPvPPlayer.getKitPvPPlayer(p);
				Masteries m = omp.getMasteries();
				
				if(item.getType() == Material.EMERALD){
					switch(item.getItemMeta().getDisplayName()){
						case "§a§l+2% Melee Damage":
							if(m.getPoints() > 0){
								m.setPoints(m.getPoints() -1);
								m.setMelee(m.getMelee() +1);
								m.update();
								new MasteryInv().open(p);
								p.sendMessage("§7Item Bought: " + item.getItemMeta().getDisplayName() + "§7.");
								p.sendMessage("§7Price: §c§l1 Mastery Point§7.");
							}
							break;
						case "§c§l-2% Melee Damage Taken":
							if(m.getPoints() > 0){
								m.setPoints(m.getPoints() -1);
								m.setMeleeProtection(m.getMeleeProtection() +1);
								m.update();
								new MasteryInv().open(p);
								p.sendMessage("§7Item Bought: " + item.getItemMeta().getDisplayName() + "§7.");
								p.sendMessage("§7Price: §c§l1 Mastery Point§7.");
							}
							break;
						case "§a§l+4% Range Damage":
							if(m.getPoints() > 0){
								m.setPoints(m.getPoints() -1);
								m.setRange(m.getRange() +1);
								m.update();
								new MasteryInv().open(p);
								p.sendMessage("§7Item Bought: " + item.getItemMeta().getDisplayName() + "§7.");
								p.sendMessage("§7Price: §c§l1 Mastery Point§7.");
							}
							break;
						case "§c§l-3% Range Damage Taken":
							if(m.getPoints() > 0){
								m.setPoints(m.getPoints() -1);
								m.setRangeProtection(m.getRangeProtection() +1);
								m.update();
								new MasteryInv().open(p);
								p.sendMessage("§7Item Bought: " + item.getItemMeta().getDisplayName() + "§7.");
								p.sendMessage("§7Price: §c§l1 Mastery Point§7.");
							}
							break;
					}
				}
				if(item.getType() == Material.BLAZE_POWDER){
					switch(item.getItemMeta().getDisplayName()){
						case "§c§l-2% Melee Damage":
							if(m.getMasteryLevel(Mastery.MELEE) > 0){
								new ConfirmInv(item, Currency.VIP_POINTS, 20).open(p);
							}
							break;
						case "§a§l+2% Melee Damage Taken":
							if(m.getMasteryLevel(Mastery.MELEE_PROTECTION) > 0){
								new ConfirmInv(item, Currency.VIP_POINTS, 20).open(p);
							}
							break;
						case "§c§l-4% Range Damage":
							if(m.getMasteryLevel(Mastery.RANGE) > 0){
								new ConfirmInv(item, Currency.VIP_POINTS, 20).open(p);
							}
							break;
						case "§a§l+3% Range Damage Taken":
							if(m.getMasteryLevel(Mastery.RANGE_PROTECTION) > 0){
								new ConfirmInv(item, Currency.VIP_POINTS, 20).open(p);
							}
							break;
					}
				}
			}
			
			return true;
		}
		return false;
	}
	
	public boolean handleSpectator(){
		KitPvPPlayer omp = KitPvPPlayer.getKitPvPPlayer(p);
		if(omp.isSpectator() && !itemIsNull()){
			if(item.getType() == Material.NAME_TAG && item.getItemMeta().getDisplayName().equals("§e§nTeleporter")){
				e.setCancelled(true);
				
				kitpvp.getTeleporterInv().open(p);
				
				return true;
			}
			else if(item.getType() == Material.ENDER_PEARL && item.getItemMeta().getDisplayName().equals("§3§nBack to the Lobby")){
				e.setCancelled(true);
				
				omp.setPlayer();
				p.teleport(kitpvp.getSpawn());
				omp.clearInventory();
				kitpvp.giveLobbyKit(omp);
				p.playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 5, 1);
				p.setFlying(false);
				p.setAllowFlight(false);
				
				omp.show();
				return true;
			}
			else{}
		}
		return false;
	}
}
