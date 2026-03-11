package me.O_o_Fadi_o_O.KitPvP;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class KitSelector {
	
	Start plugin;
	 
	public KitSelector(Start instance) {
	plugin = instance;
	}
	
	public Inventory getKitSelector(final Player p){
		
		final Inventory i = Bukkit.createInventory(null, 54, "                §c§lKitPvP");
		
		{		
			i.setItem(9, KnightItem(p)); //1
			i.setItem(10, ArcherItem(p)); //2
			i.setItem(11, SoldierItem(p)); //3
			i.setItem(12, WizardItem(p)); //4
			i.setItem(13, TankItem(p)); //5
			i.setItem(14, DrunkItem(p)); //6
			i.setItem(15, PyroItem(p)); //7
			i.setItem(16, BunnyItem(p)); //8
			i.setItem(17, NecromancerItem(p)); //9
			i.setItem(18, KingItem(p)); //10
			i.setItem(19, TreeItem(p)); //11
			i.setItem(20, BlazeItem(p)); //12
			i.setItem(21, TNTItem(p)); //13
			i.setItem(22, FishermanItem(p)); //14
			i.setItem(23, SnowGolemItem(p)); //15
			i.setItem(24, LibrarianItem(p)); //16
			i.setItem(25, SpiderItem(p)); //17
			i.setItem(26, VillagerItem(p)); //18
			i.setItem(27, AssassinItem(p)); //19
			i.setItem(28, LordItem(p)); //20
			i.setItem(29, VampireItem(p)); //21
			i.setItem(30, DarkMageItem(p)); //22
			i.setItem(31, BeastItem(p)); //23
			i.setItem(32, FishItem(p)); //24
			i.setItem(33, HeavyItem(p)); //25
			i.setItem(34, GrimReaperItem(p)); //26
			i.setItem(35, MinerItem(p)); //27
			
			i.setItem(47, AchievementsItem(p)); 
			i.setItem(51, CosmeticPerksItem(p)); 
		}

		
		return i;
	}
	public ItemStack AchievementsItem(Player p){
		ItemStack item = new ItemStack(Material.WATCH);
		ItemMeta itemmeta = item.getItemMeta();
		itemmeta.setDisplayName("§d§lAchievements");
		List<String> l = new ArrayList<String>();		
		l.add("");
		l.add("§7§oShow your Achievements");
		l.add("");	
		itemmeta.setLore(l);
		item.setItemMeta(itemmeta);
			
	return item;
	}
	
	public ItemStack CosmeticPerksItem(Player p){
		ItemStack item = new ItemStack(Material.ENDER_CHEST);
		ItemMeta itemmeta = item.getItemMeta();
		itemmeta.setDisplayName("§9§nCosmetic Perks");
		item.setItemMeta(itemmeta);
			
	return item;
	}
	
	public ItemStack KnightItem(Player p){
		ItemStack item = new ItemStack(Material.STONE_SWORD);
		ItemMeta itemmeta = item.getItemMeta();
		// DISPLAY ON KIT LEVEL
		if(Start.knight.get(p.getName()) == 0){
			itemmeta.setDisplayName("§b§lKnight");
		}
		else if(Start.knight.get(p.getName()) == 1){
			itemmeta.setDisplayName("§b§lKnight §a§lLvL 1");
		}
		else if(Start.knight.get(p.getName()) == 2){
			itemmeta.setDisplayName("§b§lKnight §a§lLvL 2");
		}
		else if(Start.knight.get(p.getName()) == 3){
			itemmeta.setDisplayName("§b§lKnight §a§lLvL 3");
		}
		
		List<String> l = new ArrayList<String>();
				
		// LORE ON KIT LEVEL
		if(Start.knight.get(p.getName()) == 0){
			if(Start.FreeKitEnabled == false){
				l.add("");
				l.add("§4§lLocked");
				l.add("");
				l.add("§c§o§mRight Click: Select Kit");
				l.add("§7§oLeft Click: §6§oDetails & Buy Kit");
			}
			else{
				l.add("");
				l.add("§a§lUnlocked §7§o(§a§oLvL 1§7§o)");
				l.add("");
				l.add("§7§oRight Click: §e§oSelect Kit");
				l.add("§7§oLeft Click: §6§oDetails & Upgrade Kit");
				l.add("");
				l.add("§a§lFREE Kit Saturday!");
			}
		}
		if(Start.knight.get(p.getName()) == 1){
			l.add("");
			l.add("§a§lUnlocked §7§o(§a§oLvL 1§7§o)");
			l.add("");
			l.add("§7§oRight Click: §e§oSelect Kit");
			l.add("§7§oLeft Click: §6§oDetails & Upgrade Kit");
		}
		if(Start.knight.get(p.getName()) == 2){
			l.add("");
			l.add("§a§lUnlocked §7§o(§a§oLvL 2§7§o)");
			l.add("");
			l.add("§7§oRight Click: §e§oSelect Kit");
			l.add("§7§oLeft Click: §6§oDetails & Upgrade Kit");
		}
		if(Start.knight.get(p.getName()) == 3){
			l.add("");
			l.add("§a§lUnlocked §7§o(§a§oLvL 3§7§o)");
			l.add("");
			l.add("§7§oRight Click: §e§oSelect Kit");
			l.add("§7§oLeft Click: §6§oDetails");
		}
				
		itemmeta.setLore(l);
		item.setItemMeta(itemmeta);
			
	return item;
	}
	
	public ItemStack ArcherItem(Player p){
		ItemStack item = new ItemStack(Material.BOW);
		ItemMeta itemmeta = item.getItemMeta();
		// DISPLAY ON KIT LEVEL
		if(Start.archer.get(p.getName()) == 0){
			itemmeta.setDisplayName("§b§lArcher");
		}
		else if(Start.archer.get(p.getName()) == 1){
			itemmeta.setDisplayName("§b§lArcher §a§lLvL 1");
		}
		else if(Start.archer.get(p.getName()) == 2){
			itemmeta.setDisplayName("§b§lArcher §a§lLvL 2");
		}
		else if(Start.archer.get(p.getName()) == 3){
			itemmeta.setDisplayName("§b§lArcher §a§lLvL 3");
		}
		
		List<String> l = new ArrayList<String>();
				
		// LORE ON KIT LEVEL
		if(Start.archer.get(p.getName()) == 0){
			if(Start.FreeKitEnabled == false){
				l.add("");
				l.add("§4§lLocked");
				l.add("");
				l.add("§c§o§mRight Click: Select Kit");
				l.add("§7§oLeft Click: §6§oDetails & Buy Kit");
			}
			else{
				l.add("");
				l.add("§a§lUnlocked §7§o(§a§oLvL 1§7§o)");
				l.add("");
				l.add("§7§oRight Click: §e§oSelect Kit");
				l.add("§7§oLeft Click: §6§oDetails & Upgrade Kit");
				l.add("");
				l.add("§a§lFREE Kit Saturday!");
			}
		}
		if(Start.archer.get(p.getName()) == 1){
			l.add("");
			l.add("§a§lUnlocked §7§o(§a§oLvL 1§7§o)");
			l.add("");
			l.add("§7§oRight Click: §e§oSelect Kit");
			l.add("§7§oLeft Click: §6§oDetails & Upgrade Kit");
		}
		if(Start.archer.get(p.getName()) == 2){
			l.add("");
			l.add("§a§lUnlocked §7§o(§a§oLvL 2§7§o)");
			l.add("");
			l.add("§7§oRight Click: §e§oSelect Kit");
			l.add("§7§oLeft Click: §6§oDetails & Upgrade Kit");
		}
		if(Start.archer.get(p.getName()) == 3){
			l.add("");
			l.add("§a§lUnlocked §7§o(§a§oLvL 3§7§o)");
			l.add("");
			l.add("§7§oRight Click: §e§oSelect Kit");
			l.add("§7§oLeft Click: §6§oDetails");
		}
				
		itemmeta.setLore(l);
		item.setItemMeta(itemmeta);
			
	return item;
	}
	public ItemStack SoldierItem(Player p){
		ItemStack item = new ItemStack(Material.LEATHER_LEGGINGS);
		ItemMeta itemmeta = item.getItemMeta();
		// DISPLAY ON KIT LEVEL
		if(Start.soldier.get(p.getName()) == 0){
			itemmeta.setDisplayName("§b§lSoldier");
		}
		else if(Start.soldier.get(p.getName()) == 1){
			itemmeta.setDisplayName("§b§lSoldier §a§lLvL 1");
		}
		else if(Start.soldier.get(p.getName()) == 2){
			itemmeta.setDisplayName("§b§lSoldier §a§lLvL 2");
		}
		else if(Start.soldier.get(p.getName()) == 3){
			itemmeta.setDisplayName("§b§lSoldier §a§lLvL 3");
		}
		
		List<String> l = new ArrayList<String>();
				
		// LORE ON KIT LEVEL
		if(Start.soldier.get(p.getName()) == 0){
			if(Start.FreeKitEnabled == false){
				l.add("");
				l.add("§4§lLocked");
				l.add("");
				l.add("§c§o§mRight Click: Select Kit");
				l.add("§7§oLeft Click: §6§oDetails & Buy Kit");
			}
			else{
				l.add("");
				l.add("§a§lUnlocked §7§o(§a§oLvL 1§7§o)");
				l.add("");
				l.add("§7§oRight Click: §e§oSelect Kit");
				l.add("§7§oLeft Click: §6§oDetails & Upgrade Kit");
				l.add("");
				l.add("§a§lFREE Kit Saturday!");
			}
		}
		if(Start.soldier.get(p.getName()) == 1){
			l.add("");
			l.add("§a§lUnlocked §7§o(§a§oLvL 1§7§o)");
			l.add("");
			l.add("§7§oRight Click: §e§oSelect Kit");
			l.add("§7§oLeft Click: §6§oDetails & Upgrade Kit");
		}
		if(Start.soldier.get(p.getName()) == 2){
			l.add("");
			l.add("§a§lUnlocked §7§o(§a§oLvL 2§7§o)");
			l.add("");
			l.add("§7§oRight Click: §e§oSelect Kit");
			l.add("§7§oLeft Click: §6§oDetails & Upgrade Kit");
		}
		if(Start.soldier.get(p.getName()) == 3){
			l.add("");
			l.add("§a§lUnlocked §7§o(§a§oLvL 3§7§o)");
			l.add("");
			l.add("§7§oRight Click: §e§oSelect Kit");
			l.add("§7§oLeft Click: §6§oDetails");
		}
				
		itemmeta.setLore(l);
		item.setItemMeta(itemmeta);
			
	return item;
	}
	
	public ItemStack WizardItem(Player p){
		ItemStack item = new ItemStack(Material.POTION);
		ItemMeta itemmeta = item.getItemMeta();
		// DISPLAY ON KIT LEVEL
		if(Start.wizard.get(p.getName()) == 0){
			itemmeta.setDisplayName("§b§lWizard");
		}
		else if(Start.wizard.get(p.getName()) == 1){
			itemmeta.setDisplayName("§b§lWizard §a§lLvL 1");
		}
		else if(Start.wizard.get(p.getName()) == 2){
			itemmeta.setDisplayName("§b§lWizard §a§lLvL 2");
		}
		else if(Start.wizard.get(p.getName()) == 3){
			itemmeta.setDisplayName("§b§lWizard §a§lLvL 3");
		}
		
		List<String> l = new ArrayList<String>();
				
		// LORE ON KIT LEVEL
		if(Start.wizard.get(p.getName()) == 0){
			if(Start.FreeKitEnabled == false){
				l.add("");
				l.add("§4§lLocked");
				l.add("");
				l.add("§c§o§mRight Click: Select Kit");
				l.add("§7§oLeft Click: §6§oDetails & Buy Kit");
			}
			else{
				l.add("");
				l.add("§a§lUnlocked §7§o(§a§oLvL 1§7§o)");
				l.add("");
				l.add("§7§oRight Click: §e§oSelect Kit");
				l.add("§7§oLeft Click: §6§oDetails & Upgrade Kit");
				l.add("");
				l.add("§a§lFREE Kit Saturday!");
			}
		}
		if(Start.wizard.get(p.getName()) == 1){
			l.add("");
			l.add("§a§lUnlocked §7§o(§a§oLvL 1§7§o)");
			l.add("");
			l.add("§7§oRight Click: §e§oSelect Kit");
			l.add("§7§oLeft Click: §6§oDetails & Upgrade Kit");
		}
		if(Start.wizard.get(p.getName()) == 2){
			l.add("");
			l.add("§a§lUnlocked §7§o(§a§oLvL 2§7§o)");
			l.add("");
			l.add("§7§oRight Click: §e§oSelect Kit");
			l.add("§7§oLeft Click: §6§oDetails & Upgrade Kit");
		}
		if(Start.wizard.get(p.getName()) == 3){
			l.add("");
			l.add("§a§lUnlocked §7§o(§a§oLvL 3§7§o)");
			l.add("");
			l.add("§7§oRight Click: §e§oSelect Kit");
			l.add("§7§oLeft Click: §6§oDetails");
		}
				
		itemmeta.setLore(l);
		item.setItemMeta(itemmeta);
			
	return item;
	}
	
	public ItemStack TankItem(Player p){
		ItemStack item = new ItemStack(Material.DIAMOND_CHESTPLATE);
		ItemMeta itemmeta = item.getItemMeta();
		// DISPLAY ON KIT LEVEL
		if(Start.tank.get(p.getName()) == 0){
			itemmeta.setDisplayName("§b§lTank");
		}
		else if(Start.tank.get(p.getName()) == 1){
			itemmeta.setDisplayName("§b§lTank §a§lLvL 1");
		}
		else if(Start.tank.get(p.getName()) == 2){
			itemmeta.setDisplayName("§b§lTank §a§lLvL 2");
		}
		else if(Start.tank.get(p.getName()) == 3){
			itemmeta.setDisplayName("§b§lTank §a§lLvL 3");
		}
		
		List<String> l = new ArrayList<String>();
				
		// LORE ON KIT LEVEL
		if(Start.tank.get(p.getName()) == 0){
			if(Start.FreeKitEnabled == false){
				l.add("");
				l.add("§4§lLocked");
				l.add("");
				l.add("§c§o§mRight Click: Select Kit");
				l.add("§7§oLeft Click: §6§oDetails & Buy Kit");
			}
			else{
				l.add("");
				l.add("§a§lUnlocked §7§o(§a§oLvL 1§7§o)");
				l.add("");
				l.add("§7§oRight Click: §e§oSelect Kit");
				l.add("§7§oLeft Click: §6§oDetails & Upgrade Kit");
				l.add("");
				l.add("§a§lFREE Kit Saturday!");
			}
		}
		if(Start.tank.get(p.getName()) == 1){
			l.add("");
			l.add("§a§lUnlocked §7§o(§a§oLvL 1§7§o)");
			l.add("");
			l.add("§7§oRight Click: §e§oSelect Kit");
			l.add("§7§oLeft Click: §6§oDetails & Upgrade Kit");
		}
		if(Start.tank.get(p.getName()) == 2){
			l.add("");
			l.add("§a§lUnlocked §7§o(§a§oLvL 2§7§o)");
			l.add("");
			l.add("§7§oRight Click: §e§oSelect Kit");
			l.add("§7§oLeft Click: §6§oDetails & Upgrade Kit");
		}
		if(Start.tank.get(p.getName()) == 3){
			l.add("");
			l.add("§a§lUnlocked §7§o(§a§oLvL 3§7§o)");
			l.add("");
			l.add("§7§oRight Click: §e§oSelect Kit");
			l.add("§7§oLeft Click: §6§oDetails");
		}
				
		itemmeta.setLore(l);
		item.setItemMeta(itemmeta);
			
	return item;
	}
	
	public ItemStack DrunkItem(Player p){
		ItemStack item = new ItemStack(Material.CHAINMAIL_LEGGINGS);
		ItemMeta itemmeta = item.getItemMeta();
		// DISPLAY ON KIT LEVEL
		if(Start.drunk.get(p.getName()) == 0){
			itemmeta.setDisplayName("§b§lDrunk");
		}
		else if(Start.drunk.get(p.getName()) == 1){
			itemmeta.setDisplayName("§b§lDrunk §a§lLvL 1");
		}
		else if(Start.drunk.get(p.getName()) == 2){
			itemmeta.setDisplayName("§b§lDrunk §a§lLvL 2");
		}
		else if(Start.drunk.get(p.getName()) == 3){
			itemmeta.setDisplayName("§b§lDrunk §a§lLvL 3");
		}
		
		List<String> l = new ArrayList<String>();
				
		// LORE ON KIT LEVEL
		if(Start.drunk.get(p.getName()) == 0){
			if(Start.FreeKitEnabled == false){
				l.add("");
				l.add("§4§lLocked");
				l.add("");
				l.add("§c§o§mRight Click: Select Kit");
				l.add("§7§oLeft Click: §6§oDetails & Buy Kit");
			}
			else{
				l.add("");
				l.add("§a§lUnlocked §7§o(§a§oLvL 1§7§o)");
				l.add("");
				l.add("§7§oRight Click: §e§oSelect Kit");
				l.add("§7§oLeft Click: §6§oDetails & Upgrade Kit");
				l.add("");
				l.add("§a§lFREE Kit Saturday!");
			}
		}
		if(Start.drunk.get(p.getName()) == 1){
			l.add("");
			l.add("§a§lUnlocked §7§o(§a§oLvL 1§7§o)");
			l.add("");
			l.add("§7§oRight Click: §e§oSelect Kit");
			l.add("§7§oLeft Click: §6§oDetails & Upgrade Kit");
		}
		if(Start.drunk.get(p.getName()) == 2){
			l.add("");
			l.add("§a§lUnlocked §7§o(§a§oLvL 2§7§o)");
			l.add("");
			l.add("§7§oRight Click: §e§oSelect Kit");
			l.add("§7§oLeft Click: §6§oDetails & Upgrade Kit");
		}
		if(Start.drunk.get(p.getName()) == 3){
			l.add("");
			l.add("§a§lUnlocked §7§o(§a§oLvL 3§7§o)");
			l.add("");
			l.add("§7§oRight Click: §e§oSelect Kit");
			l.add("§7§oLeft Click: §6§oDetails");
		}
				
		itemmeta.setLore(l);
		item.setItemMeta(itemmeta);
			
	return item;
	}
	
	public ItemStack PyroItem(Player p){
		ItemStack item = new ItemStack(Material.GOLD_HELMET);
		ItemMeta itemmeta = item.getItemMeta();
		// DISPLAY ON KIT LEVEL
		if(Start.pyro.get(p.getName()) == 0){
			itemmeta.setDisplayName("§b§lPyro");
		}
		else if(Start.pyro.get(p.getName()) == 1){
			itemmeta.setDisplayName("§b§lPyro §a§lLvL 1");
		}
		else if(Start.pyro.get(p.getName()) == 2){
			itemmeta.setDisplayName("§b§lPyro §a§lLvL 2");
		}
		else if(Start.pyro.get(p.getName()) == 3){
			itemmeta.setDisplayName("§b§lPyro §a§lLvL 3");
		}
		
		List<String> l = new ArrayList<String>();
				
		// LORE ON KIT LEVEL
		if(Start.pyro.get(p.getName()) == 0){
			if(Start.FreeKitEnabled == false){
				l.add("");
				l.add("§4§lLocked");
				l.add("");
				l.add("§c§o§mRight Click: Select Kit");
				l.add("§7§oLeft Click: §6§oDetails & Buy Kit");
			}
			else{
				l.add("");
				l.add("§a§lUnlocked §7§o(§a§oLvL 1§7§o)");
				l.add("");
				l.add("§7§oRight Click: §e§oSelect Kit");
				l.add("§7§oLeft Click: §6§oDetails & Upgrade Kit");
				l.add("");
				l.add("§a§lFREE Kit Saturday!");
			}
		}
		if(Start.pyro.get(p.getName()) == 1){
			l.add("");
			l.add("§a§lUnlocked §7§o(§a§oLvL 1§7§o)");
			l.add("");
			l.add("§7§oRight Click: §e§oSelect Kit");
			l.add("§7§oLeft Click: §6§oDetails & Upgrade Kit");
		}
		if(Start.pyro.get(p.getName()) == 2){
			l.add("");
			l.add("§a§lUnlocked §7§o(§a§oLvL 2§7§o)");
			l.add("");
			l.add("§7§oRight Click: §e§oSelect Kit");
			l.add("§7§oLeft Click: §6§oDetails & Upgrade Kit");
		}
		if(Start.pyro.get(p.getName()) == 3){
			l.add("");
			l.add("§a§lUnlocked §7§o(§a§oLvL 3§7§o)");
			l.add("");
			l.add("§7§oRight Click: §e§oSelect Kit");
			l.add("§7§oLeft Click: §6§oDetails");
		}
				
		itemmeta.setLore(l);
		item.setItemMeta(itemmeta);
			
	return item;
	}
	
	public ItemStack BunnyItem(Player p){
		ItemStack item = new ItemStack(Material.LEATHER_BOOTS);
		ItemMeta itemmeta = item.getItemMeta();
		// DISPLAY ON KIT LEVEL
		if(Start.bunny.get(p.getName()) == 0){
			itemmeta.setDisplayName("§b§lBunny");
		}
		else if(Start.bunny.get(p.getName()) == 1){
			itemmeta.setDisplayName("§b§lBunny §a§lLvL 1");
		}
		else if(Start.bunny.get(p.getName()) == 2){
			itemmeta.setDisplayName("§b§lBunny §a§lLvL 2");
		}
		else if(Start.bunny.get(p.getName()) == 3){
			itemmeta.setDisplayName("§b§lBunny §a§lLvL 3");
		}
		
		List<String> l = new ArrayList<String>();
				
		// LORE ON KIT LEVEL
		if(Start.bunny.get(p.getName()) == 0){
			if(Start.FreeKitEnabled == false){
				l.add("");
				l.add("§4§lLocked");
				l.add("");
				l.add("§c§o§mRight Click: Select Kit");
				l.add("§7§oLeft Click: §6§oDetails & Buy Kit");
			}
			else{
				l.add("");
				l.add("§a§lUnlocked §7§o(§a§oLvL 1§7§o)");
				l.add("");
				l.add("§7§oRight Click: §e§oSelect Kit");
				l.add("§7§oLeft Click: §6§oDetails & Upgrade Kit");
				l.add("");
				l.add("§a§lFREE Kit Saturday!");
			}
		}
		if(Start.bunny.get(p.getName()) == 1){
			l.add("");
			l.add("§a§lUnlocked §7§o(§a§oLvL 1§7§o)");
			l.add("");
			l.add("§7§oRight Click: §e§oSelect Kit");
			l.add("§7§oLeft Click: §6§oDetails & Upgrade Kit");
		}
		if(Start.bunny.get(p.getName()) == 2){
			l.add("");
			l.add("§a§lUnlocked §7§o(§a§oLvL 2§7§o)");
			l.add("");
			l.add("§7§oRight Click: §e§oSelect Kit");
			l.add("§7§oLeft Click: §6§oDetails & Upgrade Kit");
		}
		if(Start.bunny.get(p.getName()) == 3){
			l.add("");
			l.add("§a§lUnlocked §7§o(§a§oLvL 3§7§o)");
			l.add("");
			l.add("§7§oRight Click: §e§oSelect Kit");
			l.add("§7§oLeft Click: §6§oDetails");
		}
				
		itemmeta.setLore(l);
		item.setItemMeta(itemmeta);
			
	return item;
	}
	
	public ItemStack NecromancerItem(Player p){
		ItemStack item = new ItemStack(Material.GOLD_HOE);
		ItemMeta itemmeta = item.getItemMeta();
		// DISPLAY ON KIT LEVEL
		if(Start.necromancer.get(p.getName()) == 0){
			itemmeta.setDisplayName("§b§lNecromancer");
		}
		else if(Start.necromancer.get(p.getName()) == 1){
			itemmeta.setDisplayName("§b§lNecromancer §a§lLvL 1");
		}
		else if(Start.necromancer.get(p.getName()) == 2){
			itemmeta.setDisplayName("§b§lNecromancer §a§lLvL 2");
		}
		else if(Start.necromancer.get(p.getName()) == 3){
			itemmeta.setDisplayName("§b§lNecromancer §a§lLvL 3");
		}
		
		List<String> l = new ArrayList<String>();
				
		// LORE ON KIT LEVEL
		if(Start.necromancer.get(p.getName()) == 0){
			if(Start.FreeKitEnabled == false){
				l.add("");
				l.add("§4§lLocked");
				l.add("");
				l.add("§c§o§mRight Click: Select Kit");
				l.add("§7§oLeft Click: §6§oDetails & Buy Kit");
			}
			else{
				l.add("");
				l.add("§a§lUnlocked §7§o(§a§oLvL 1§7§o)");
				l.add("");
				l.add("§7§oRight Click: §e§oSelect Kit");
				l.add("§7§oLeft Click: §6§oDetails & Upgrade Kit");
				l.add("");
				l.add("§a§lFREE Kit Saturday!");
			}
		}
		if(Start.necromancer.get(p.getName()) == 1){
			l.add("");
			l.add("§a§lUnlocked §7§o(§a§oLvL 1§7§o)");
			l.add("");
			l.add("§7§oRight Click: §e§oSelect Kit");
			l.add("§7§oLeft Click: §6§oDetails & Upgrade Kit");
		}
		if(Start.necromancer.get(p.getName()) == 2){
			l.add("");
			l.add("§a§lUnlocked §7§o(§a§oLvL 2§7§o)");
			l.add("");
			l.add("§7§oRight Click: §e§oSelect Kit");
			l.add("§7§oLeft Click: §6§oDetails & Upgrade Kit");
		}
		if(Start.necromancer.get(p.getName()) == 3){
			l.add("");
			l.add("§a§lUnlocked §7§o(§a§oLvL 3§7§o)");
			l.add("");
			l.add("§7§oRight Click: §e§oSelect Kit");
			l.add("§7§oLeft Click: §6§oDetails");
		}
				
		itemmeta.setLore(l);
		item.setItemMeta(itemmeta);
			
	return item;
	}
	
	public ItemStack KingItem(Player p){
		ItemStack item = new ItemStack(Material.DIAMOND_HELMET);
		ItemMeta itemmeta = item.getItemMeta();
		// DISPLAY ON KIT LEVEL
		if(Start.king.get(p.getName()) == 0){
			itemmeta.setDisplayName("§b§lKing");
		}
		else if(Start.king.get(p.getName()) == 1){
			itemmeta.setDisplayName("§b§lKing §a§lLvL 1");
		}
		else if(Start.king.get(p.getName()) == 2){
			itemmeta.setDisplayName("§b§lKing §a§lLvL 2");
		}
		else if(Start.king.get(p.getName()) == 3){
			itemmeta.setDisplayName("§b§lKing §a§lLvL 3");
		}
		
		List<String> l = new ArrayList<String>();
				
		// LORE ON KIT LEVEL
		if(Start.king.get(p.getName()) == 0){
			if(Start.FreeKitEnabled == false){
				l.add("");
				l.add("§4§lLocked");
				l.add("");
				l.add("§c§o§mRight Click: Select Kit");
				l.add("§7§oLeft Click: §6§oDetails & Buy Kit");
			}
			else{
				l.add("");
				l.add("§a§lUnlocked §7§o(§a§oLvL 1§7§o)");
				l.add("");
				l.add("§7§oRight Click: §e§oSelect Kit");
				l.add("§7§oLeft Click: §6§oDetails & Upgrade Kit");
				l.add("");
				l.add("§a§lFREE Kit Saturday!");
			}
		}
		if(Start.king.get(p.getName()) == 1){
			l.add("");
			l.add("§a§lUnlocked §7§o(§a§oLvL 1§7§o)");
			l.add("");
			l.add("§7§oRight Click: §e§oSelect Kit");
			l.add("§7§oLeft Click: §6§oDetails & Upgrade Kit");
		}
		if(Start.king.get(p.getName()) == 2){
			l.add("");
			l.add("§a§lUnlocked §7§o(§a§oLvL 2§7§o)");
			l.add("");
			l.add("§7§oRight Click: §e§oSelect Kit");
			l.add("§7§oLeft Click: §6§oDetails & Upgrade Kit");
		}
		if(Start.king.get(p.getName()) == 3){
			l.add("");
			l.add("§a§lUnlocked §7§o(§a§oLvL 3§7§o)");
			l.add("");
			l.add("§7§oRight Click: §e§oSelect Kit");
			l.add("§7§oLeft Click: §6§oDetails");
		}
				
		itemmeta.setLore(l);
		item.setItemMeta(itemmeta);
			
	return item;
	}
	
	public ItemStack TreeItem(Player p){
		ItemStack item = new ItemStack(Material.LEAVES);
		ItemMeta itemmeta = item.getItemMeta();
		// DISPLAY ON KIT LEVEL
		if(Start.tree.get(p.getName()) == 0){
			itemmeta.setDisplayName("§b§lTree");
		}
		else if(Start.tree.get(p.getName()) == 1){
			itemmeta.setDisplayName("§b§lTree §a§lLvL 1");
		}
		else if(Start.tree.get(p.getName()) == 2){
			itemmeta.setDisplayName("§b§lTree §a§lLvL 2");
		}
		else if(Start.tree.get(p.getName()) == 3){
			itemmeta.setDisplayName("§b§lTree §a§lLvL 3");
		}
		
		List<String> l = new ArrayList<String>();
				
		// LORE ON KIT LEVEL
		if(Start.tree.get(p.getName()) == 0){
			if(Start.FreeKitEnabled == false){
				l.add("");
				l.add("§4§lLocked");
				l.add("");
				l.add("§c§o§mRight Click: Select Kit");
				l.add("§7§oLeft Click: §6§oDetails & Buy Kit");
			}
			else{
				l.add("");
				l.add("§a§lUnlocked §7§o(§a§oLvL 1§7§o)");
				l.add("");
				l.add("§7§oRight Click: §e§oSelect Kit");
				l.add("§7§oLeft Click: §6§oDetails & Upgrade Kit");
				l.add("");
				l.add("§a§lFREE Kit Saturday!");
			}
		}
		if(Start.tree.get(p.getName()) == 1){
			l.add("");
			l.add("§a§lUnlocked §7§o(§a§oLvL 1§7§o)");
			l.add("");
			l.add("§7§oRight Click: §e§oSelect Kit");
			l.add("§7§oLeft Click: §6§oDetails & Upgrade Kit");
		}
		if(Start.tree.get(p.getName()) == 2){
			l.add("");
			l.add("§a§lUnlocked §7§o(§a§oLvL 2§7§o)");
			l.add("");
			l.add("§7§oRight Click: §e§oSelect Kit");
			l.add("§7§oLeft Click: §6§oDetails & Upgrade Kit");
		}
		if(Start.tree.get(p.getName()) == 3){
			l.add("");
			l.add("§a§lUnlocked §7§o(§a§oLvL 3§7§o)");
			l.add("");
			l.add("§7§oRight Click: §e§oSelect Kit");
			l.add("§7§oLeft Click: §6§oDetails");
		}
				
		itemmeta.setLore(l);
		item.setItemMeta(itemmeta);
			
	return item;
	}
	
	public ItemStack BlazeItem(Player p){
		ItemStack item = new ItemStack(Material.BLAZE_POWDER);
		ItemMeta itemmeta = item.getItemMeta();
		// DISPLAY ON KIT LEVEL
		if(Start.blaze.get(p.getName()) == 0){
			itemmeta.setDisplayName("§b§lBlaze");
		}
		else if(Start.blaze.get(p.getName()) == 1){
			itemmeta.setDisplayName("§b§lBlaze §a§lLvL 1");
		}
		else if(Start.blaze.get(p.getName()) == 2){
			itemmeta.setDisplayName("§b§lBlaze §a§lLvL 2");
		}
		else if(Start.blaze.get(p.getName()) == 3){
			itemmeta.setDisplayName("§b§lBlaze §a§lLvL 3");
		}
		
		List<String> l = new ArrayList<String>();
				
		// LORE ON KIT LEVEL
		if(Start.blaze.get(p.getName()) == 0){
			if(Start.FreeKitEnabled == false){
				l.add("");
				l.add("§4§lLocked");
				l.add("");
				l.add("§c§o§mRight Click: Select Kit");
				l.add("§7§oLeft Click: §6§oDetails & Buy Kit");
			}
			else{
				l.add("");
				l.add("§a§lUnlocked §7§o(§a§oLvL 1§7§o)");
				l.add("");
				l.add("§7§oRight Click: §e§oSelect Kit");
				l.add("§7§oLeft Click: §6§oDetails & Upgrade Kit");
				l.add("");
				l.add("§a§lFREE Kit Saturday!");
			}
		}
		if(Start.blaze.get(p.getName()) == 1){
			l.add("");
			l.add("§a§lUnlocked §7§o(§a§oLvL 1§7§o)");
			l.add("");
			l.add("§7§oRight Click: §e§oSelect Kit");
			l.add("§7§oLeft Click: §6§oDetails & Upgrade Kit");
		}
		if(Start.blaze.get(p.getName()) == 2){
			l.add("");
			l.add("§a§lUnlocked §7§o(§a§oLvL 2§7§o)");
			l.add("");
			l.add("§7§oRight Click: §e§oSelect Kit");
			l.add("§7§oLeft Click: §6§oDetails & Upgrade Kit");
		}
		if(Start.blaze.get(p.getName()) == 3){
			l.add("");
			l.add("§a§lUnlocked §7§o(§a§oLvL 3§7§o)");
			l.add("");
			l.add("§7§oRight Click: §e§oSelect Kit");
			l.add("§7§oLeft Click: §6§oDetails");
		}
				
		itemmeta.setLore(l);
		item.setItemMeta(itemmeta);
			
	return item;
	}
	
	public ItemStack TNTItem(Player p){
		ItemStack item = new ItemStack(Material.TNT);
		ItemMeta itemmeta = item.getItemMeta();
		// DISPLAY ON KIT LEVEL
		if(Start.tnt.get(p.getName()) == 0){
			itemmeta.setDisplayName("§b§lTNT");
		}
		else if(Start.tnt.get(p.getName()) == 1){
			itemmeta.setDisplayName("§b§lTNT §a§lLvL 1");
		}
		else if(Start.tnt.get(p.getName()) == 2){
			itemmeta.setDisplayName("§b§lTNT §a§lLvL 2");
		}
		else if(Start.tnt.get(p.getName()) == 3){
			itemmeta.setDisplayName("§b§lTNT §a§lLvL 3");
		}
		
		List<String> l = new ArrayList<String>();
				
		// LORE ON KIT LEVEL
		if(Start.tnt.get(p.getName()) == 0){
			if(Start.FreeKitEnabled == false){
				l.add("");
				l.add("§4§lLocked");
				l.add("");
				l.add("§c§o§mRight Click: Select Kit");
				l.add("§7§oLeft Click: §6§oDetails & Buy Kit");
			}
			else{
				l.add("");
				l.add("§a§lUnlocked §7§o(§a§oLvL 1§7§o)");
				l.add("");
				l.add("§7§oRight Click: §e§oSelect Kit");
				l.add("§7§oLeft Click: §6§oDetails & Upgrade Kit");
				l.add("");
				l.add("§a§lFREE Kit Saturday!");
			}
		}
		if(Start.tnt.get(p.getName()) == 1){
			l.add("");
			l.add("§a§lUnlocked §7§o(§a§oLvL 1§7§o)");
			l.add("");
			l.add("§7§oRight Click: §e§oSelect Kit");
			l.add("§7§oLeft Click: §6§oDetails & Upgrade Kit");
		}
		if(Start.tnt.get(p.getName()) == 2){
			l.add("");
			l.add("§a§lUnlocked §7§o(§a§oLvL 2§7§o)");
			l.add("");
			l.add("§7§oRight Click: §e§oSelect Kit");
			l.add("§7§oLeft Click: §6§oDetails & Upgrade Kit");
		}
		if(Start.tnt.get(p.getName()) == 3){
			l.add("");
			l.add("§a§lUnlocked §7§o(§a§oLvL 3§7§o)");
			l.add("");
			l.add("§7§oRight Click: §e§oSelect Kit");
			l.add("§7§oLeft Click: §6§oDetails");
		}
				
		itemmeta.setLore(l);
		item.setItemMeta(itemmeta);
			
	return item;
	}
	
	public ItemStack FishermanItem(Player p){
		ItemStack item = new ItemStack(Material.FISHING_ROD);
		ItemMeta itemmeta = item.getItemMeta();
		// DISPLAY ON KIT LEVEL
		if(Start.fisherman.get(p.getName()) == 0){
			itemmeta.setDisplayName("§b§lFisherman");
		}
		else if(Start.fisherman.get(p.getName()) == 1){
			itemmeta.setDisplayName("§b§lFisherman §a§lLvL 1");
		}
		else if(Start.fisherman.get(p.getName()) == 2){
			itemmeta.setDisplayName("§b§lFisherman §a§lLvL 2");
		}
		else if(Start.fisherman.get(p.getName()) == 3){
			itemmeta.setDisplayName("§b§lFisherman §a§lLvL 3");
		}
		
		List<String> l = new ArrayList<String>();
				
		// LORE ON KIT LEVEL
		if(Start.fisherman.get(p.getName()) == 0){
			if(Start.FreeKitEnabled == false){
				l.add("");
				l.add("§4§lLocked");
				l.add("");
				l.add("§c§o§mRight Click: Select Kit");
				l.add("§7§oLeft Click: §6§oDetails & Buy Kit");
			}
			else{
				l.add("");
				l.add("§a§lUnlocked §7§o(§a§oLvL 1§7§o)");
				l.add("");
				l.add("§7§oRight Click: §e§oSelect Kit");
				l.add("§7§oLeft Click: §6§oDetails & Upgrade Kit");
				l.add("");
				l.add("§a§lFREE Kit Saturday!");
			}
		}
		if(Start.fisherman.get(p.getName()) == 1){
			l.add("");
			l.add("§a§lUnlocked §7§o(§a§oLvL 1§7§o)");
			l.add("");
			l.add("§7§oRight Click: §e§oSelect Kit");
			l.add("§7§oLeft Click: §6§oDetails & Upgrade Kit");
		}
		if(Start.fisherman.get(p.getName()) == 2){
			l.add("");
			l.add("§a§lUnlocked §7§o(§a§oLvL 2§7§o)");
			l.add("");
			l.add("§7§oRight Click: §e§oSelect Kit");
			l.add("§7§oLeft Click: §6§oDetails & Upgrade Kit");
		}
		if(Start.fisherman.get(p.getName()) == 3){
			l.add("");
			l.add("§a§lUnlocked §7§o(§a§oLvL 3§7§o)");
			l.add("");
			l.add("§7§oRight Click: §e§oSelect Kit");
			l.add("§7§oLeft Click: §6§oDetails");
		}
				
		itemmeta.setLore(l);
		item.setItemMeta(itemmeta);
			
	return item;
	}
	
	public ItemStack SnowGolemItem(Player p){
		ItemStack item = new ItemStack(Material.PUMPKIN);
		ItemMeta itemmeta = item.getItemMeta();
		// DISPLAY ON KIT LEVEL
		if(Start.snowgolem.get(p.getName()) == 0){
			itemmeta.setDisplayName("§b§lSnowGolem");
		}
		else if(Start.snowgolem.get(p.getName()) == 1){
			itemmeta.setDisplayName("§b§lSnowGolem §a§lLvL 1");
		}
		else if(Start.snowgolem.get(p.getName()) == 2){
			itemmeta.setDisplayName("§b§lSnowGolem §a§lLvL 2");
		}
		else if(Start.snowgolem.get(p.getName()) == 3){
			itemmeta.setDisplayName("§b§lSnowGolem §a§lLvL 3");
		}
		
		List<String> l = new ArrayList<String>();
				
		// LORE ON KIT LEVEL
		if(Start.snowgolem.get(p.getName()) == 0){
			if(Start.FreeKitEnabled == false){
				l.add("");
				l.add("§4§lLocked");
				l.add("");
				l.add("§c§o§mRight Click: Select Kit");
				l.add("§7§oLeft Click: §6§oDetails & Buy Kit");
			}
			else{
				l.add("");
				l.add("§a§lUnlocked §7§o(§a§oLvL 1§7§o)");
				l.add("");
				l.add("§7§oRight Click: §e§oSelect Kit");
				l.add("§7§oLeft Click: §6§oDetails & Upgrade Kit");	
				l.add("");
				l.add("§a§lFREE Kit Saturday!");
			}
		}
		if(Start.snowgolem.get(p.getName()) == 1){
			l.add("");
			l.add("§a§lUnlocked §7§o(§a§oLvL 1§7§o)");
			l.add("");
			l.add("§7§oRight Click: §e§oSelect Kit");
			l.add("§7§oLeft Click: §6§oDetails & Upgrade Kit");
		}
		if(Start.snowgolem.get(p.getName()) == 2){
			l.add("");
			l.add("§a§lUnlocked §7§o(§a§oLvL 2§7§o)");
			l.add("");
			l.add("§7§oRight Click: §e§oSelect Kit");
			l.add("§7§oLeft Click: §6§oDetails & Upgrade Kit");
		}
		if(Start.snowgolem.get(p.getName()) == 3){
			l.add("");
			l.add("§a§lUnlocked §7§o(§a§oLvL 3§7§o)");
			l.add("");
			l.add("§7§oRight Click: §e§oSelect Kit");
			l.add("§7§oLeft Click: §6§oDetails");
		}
				
		itemmeta.setLore(l);
		item.setItemMeta(itemmeta);
			
	return item;
	}
	
	public ItemStack LibrarianItem(Player p){
		ItemStack item = new ItemStack(Material.BOOKSHELF);
		ItemMeta itemmeta = item.getItemMeta();
		// DISPLAY ON KIT LEVEL
		if(Start.librarian.get(p.getName()) == 0){
			itemmeta.setDisplayName("§b§lLibrarian");
		}
		else if(Start.librarian.get(p.getName()) == 1){
			itemmeta.setDisplayName("§b§lLibrarian §a§lLvL 1");
		}
		else if(Start.librarian.get(p.getName()) == 2){
			itemmeta.setDisplayName("§b§lLibrarian §a§lLvL 2");
		}
		else if(Start.librarian.get(p.getName()) == 3){
			itemmeta.setDisplayName("§b§lLibrarian §a§lLvL 3");
		}
		
		List<String> l = new ArrayList<String>();
				
		// LORE ON KIT LEVEL
		if(Start.librarian.get(p.getName()) == 0){
			if(Start.FreeKitEnabled == false){
				l.add("");
				l.add("§4§lLocked");
				l.add("");
				l.add("§c§o§mRight Click: Select Kit");
				l.add("§7§oLeft Click: §6§oDetails & Buy Kit");
			}
			else{
				l.add("");
				l.add("§a§lUnlocked §7§o(§a§oLvL 1§7§o)");
				l.add("");
				l.add("§7§oRight Click: §e§oSelect Kit");
				l.add("§7§oLeft Click: §6§oDetails & Upgrade Kit");
				l.add("");
				l.add("§a§lFREE Kit Saturday!");
			}
		}
		if(Start.librarian.get(p.getName()) == 1){
			l.add("");
			l.add("§a§lUnlocked §7§o(§a§oLvL 1§7§o)");
			l.add("");
			l.add("§7§oRight Click: §e§oSelect Kit");
			l.add("§7§oLeft Click: §6§oDetails & Upgrade Kit");
		}
		if(Start.librarian.get(p.getName()) == 2){
			l.add("");
			l.add("§a§lUnlocked §7§o(§a§oLvL 2§7§o)");
			l.add("");
			l.add("§7§oRight Click: §e§oSelect Kit");
			l.add("§7§oLeft Click: §6§oDetails & Upgrade Kit");
		}
		if(Start.librarian.get(p.getName()) == 3){
			l.add("");
			l.add("§a§lUnlocked §7§o(§a§oLvL 3§7§o)");
			l.add("");
			l.add("§7§oRight Click: §e§oSelect Kit");
			l.add("§7§oLeft Click: §6§oDetails");
		}
				
		itemmeta.setLore(l);
		item.setItemMeta(itemmeta);
			
	return item;
	}
	
	public ItemStack SpiderItem(Player p){
		ItemStack item = new ItemStack(Material.SPIDER_EYE);
		ItemMeta itemmeta = item.getItemMeta();
		// DISPLAY ON KIT LEVEL
		if(Start.spider.get(p.getName()) == 0){
			itemmeta.setDisplayName("§b§lSpider");
		}
		else if(Start.spider.get(p.getName()) == 1){
			itemmeta.setDisplayName("§b§lSpider §a§lLvL 1");
		}
		else if(Start.spider.get(p.getName()) == 2){
			itemmeta.setDisplayName("§b§lSpider §a§lLvL 2");
		}
		else if(Start.spider.get(p.getName()) == 3){
			itemmeta.setDisplayName("§b§lSpider §a§lLvL 3");
		}
		
		List<String> l = new ArrayList<String>();
				
		// LORE ON KIT LEVEL
		if(Start.spider.get(p.getName()) == 0){
			if(Start.FreeKitEnabled == false){
				l.add("");
				l.add("§4§lLocked");
				l.add("");
				l.add("§c§o§mRight Click: Select Kit");
				l.add("§7§oLeft Click: §6§oDetails & Buy Kit");
			}
			else{
				l.add("");
				l.add("§a§lUnlocked §7§o(§a§oLvL 1§7§o)");
				l.add("");
				l.add("§7§oRight Click: §e§oSelect Kit");
				l.add("§7§oLeft Click: §6§oDetails & Upgrade Kit");
				l.add("");
				l.add("§a§lFREE Kit Saturday!");
			}
		}
		if(Start.spider.get(p.getName()) == 1){
			l.add("");
			l.add("§a§lUnlocked §7§o(§a§oLvL 1§7§o)");
			l.add("");
			l.add("§7§oRight Click: §e§oSelect Kit");
			l.add("§7§oLeft Click: §6§oDetails & Upgrade Kit");
		}
		if(Start.spider.get(p.getName()) == 2){
			l.add("");
			l.add("§a§lUnlocked §7§o(§a§oLvL 2§7§o)");
			l.add("");
			l.add("§7§oRight Click: §e§oSelect Kit");
			l.add("§7§oLeft Click: §6§oDetails & Upgrade Kit");
		}
		if(Start.spider.get(p.getName()) == 3){
			l.add("");
			l.add("§a§lUnlocked §7§o(§a§oLvL 3§7§o)");
			l.add("");
			l.add("§7§oRight Click: §e§oSelect Kit");
			l.add("§7§oLeft Click: §6§oDetails");
		}
				
		itemmeta.setLore(l);
		item.setItemMeta(itemmeta);
			
	return item;
	}
	
	public ItemStack VillagerItem(Player p){
		ItemStack item = new ItemStack(Material.EMERALD);
		ItemMeta itemmeta = item.getItemMeta();
		// DISPLAY ON KIT LEVEL
		if(Start.villager.get(p.getName()) == 0){
			itemmeta.setDisplayName("§b§lVillager");
		}
		else if(Start.villager.get(p.getName()) == 1){
			itemmeta.setDisplayName("§b§lVillager §a§lLvL 1");
		}
		else if(Start.villager.get(p.getName()) == 2){
			itemmeta.setDisplayName("§b§lVillager §a§lLvL 2");
		}
		else if(Start.villager.get(p.getName()) == 3){
			itemmeta.setDisplayName("§b§lVillager §a§lLvL 3");
		}
		
		List<String> l = new ArrayList<String>();
				
		// LORE ON KIT LEVEL
		if(Start.villager.get(p.getName()) == 0){
			if(Start.FreeKitEnabled == false){
				l.add("");
				l.add("§4§lLocked");
				l.add("");
				l.add("§c§o§mRight Click: Select Kit");
				l.add("§7§oLeft Click: §6§oDetails & Buy Kit");
			}
			else{
				l.add("");
				l.add("§a§lUnlocked §7§o(§a§oLvL 1§7§o)");
				l.add("");
				l.add("§7§oRight Click: §e§oSelect Kit");
				l.add("§7§oLeft Click: §6§oDetails & Upgrade Kit");
				l.add("");
				l.add("§a§lFREE Kit Saturday!");
			}
		}
		if(Start.villager.get(p.getName()) == 1){
			l.add("");
			l.add("§a§lUnlocked §7§o(§a§oLvL 1§7§o)");
			l.add("");
			l.add("§7§oRight Click: §e§oSelect Kit");
			l.add("§7§oLeft Click: §6§oDetails & Upgrade Kit");
		}
		if(Start.villager.get(p.getName()) == 2){
			l.add("");
			l.add("§a§lUnlocked §7§o(§a§oLvL 2§7§o)");
			l.add("");
			l.add("§7§oRight Click: §e§oSelect Kit");
			l.add("§7§oLeft Click: §6§oDetails & Upgrade Kit");
		}
		if(Start.villager.get(p.getName()) == 3){
			l.add("");
			l.add("§a§lUnlocked §7§o(§a§oLvL 3§7§o)");
			l.add("");
			l.add("§7§oRight Click: §e§oSelect Kit");
			l.add("§7§oLeft Click: §6§oDetails");
		}
				
		itemmeta.setLore(l);
		item.setItemMeta(itemmeta);
			
	return item;
	}
	
	public ItemStack AssassinItem(Player p){
		ItemStack item = new ItemStack(Material.DIAMOND_SWORD);
		ItemMeta itemmeta = item.getItemMeta();
		// DISPLAY ON KIT LEVEL
		if(Start.assassin.get(p.getName()) == 0){
			itemmeta.setDisplayName("§b§lAssassin");
		}
		else if(Start.assassin.get(p.getName()) == 1){
			itemmeta.setDisplayName("§b§lAssassin §a§lLvL 1");
		}
		else if(Start.assassin.get(p.getName()) == 2){
			itemmeta.setDisplayName("§b§lAssassin §a§lLvL 2");
		}
		else if(Start.assassin.get(p.getName()) == 3){
			itemmeta.setDisplayName("§b§lAssassin §a§lLvL 3");
		}
		
		List<String> l = new ArrayList<String>();
				
		// LORE ON KIT LEVEL
		if(Start.assassin.get(p.getName()) == 0){
			if(Start.FreeKitEnabled == false){
				l.add("");
				l.add("§4§lLocked");
				l.add("");
				l.add("§c§o§mRight Click: Select Kit");
				l.add("§7§oLeft Click: §6§oDetails & Buy Kit");
			}
			else{
				l.add("");
				l.add("§a§lUnlocked §7§o(§a§oLvL 1§7§o)");
				l.add("");
				l.add("§7§oRight Click: §e§oSelect Kit");
				l.add("§7§oLeft Click: §6§oDetails & Upgrade Kit");
				l.add("");
				l.add("§a§lFREE Kit Saturday!");
			}
		}
		if(Start.assassin.get(p.getName()) == 1){
			l.add("");
			l.add("§a§lUnlocked §7§o(§a§oLvL 1§7§o)");
			l.add("");
			l.add("§7§oRight Click: §e§oSelect Kit");
			l.add("§7§oLeft Click: §6§oDetails & Upgrade Kit");
		}
		if(Start.assassin.get(p.getName()) == 2){
			l.add("");
			l.add("§a§lUnlocked §7§o(§a§oLvL 2§7§o)");
			l.add("");
			l.add("§7§oRight Click: §e§oSelect Kit");
			l.add("§7§oLeft Click: §6§oDetails & Upgrade Kit");
		}
		if(Start.assassin.get(p.getName()) == 3){
			l.add("");
			l.add("§a§lUnlocked §7§o(§a§oLvL 3§7§o)");
			l.add("");
			l.add("§7§oRight Click: §e§oSelect Kit");
			l.add("§7§oLeft Click: §6§oDetails");
		}
				
		itemmeta.setLore(l);
		item.setItemMeta(itemmeta);
			
	return item;
	}
	
	public ItemStack LordItem(Player p){
		ItemStack item = new ItemStack(Material.GOLD_SWORD);
		ItemMeta itemmeta = item.getItemMeta();
		// DISPLAY ON KIT LEVEL
		if(Start.lord.get(p.getName()) == 0){
			itemmeta.setDisplayName("§b§lLord");
		}
		else if(Start.lord.get(p.getName()) == 1){
			itemmeta.setDisplayName("§b§lLord §a§lLvL 1");
		}
		else if(Start.lord.get(p.getName()) == 2){
			itemmeta.setDisplayName("§b§lLord §a§lLvL 2");
		}
		else if(Start.lord.get(p.getName()) == 3){
			itemmeta.setDisplayName("§b§lLord §a§lLvL 3");
		}
		
		List<String> l = new ArrayList<String>();
				
		// LORE ON KIT LEVEL
		if(Start.lord.get(p.getName()) == 0){
			if(Start.FreeKitEnabled == false){
				l.add("");
				l.add("§4§lLocked");
				l.add("");
				l.add("§c§o§mRight Click: Select Kit");
				l.add("§7§oLeft Click: §6§oDetails & Buy Kit");
			}
			else{
				l.add("");
				l.add("§a§lUnlocked §7§o(§a§oLvL 1§7§o)");
				l.add("");
				l.add("§7§oRight Click: §e§oSelect Kit");
				l.add("§7§oLeft Click: §6§oDetails & Upgrade Kit");
				l.add("");
				l.add("§a§lFREE Kit Saturday!");
			}
		}
		if(Start.lord.get(p.getName()) == 1){
			l.add("");
			l.add("§a§lUnlocked §7§o(§a§oLvL 1§7§o)");
			l.add("");
			l.add("§7§oRight Click: §e§oSelect Kit");
			l.add("§7§oLeft Click: §6§oDetails & Upgrade Kit");
		}
		if(Start.lord.get(p.getName()) == 2){
			l.add("");
			l.add("§a§lUnlocked §7§o(§a§oLvL 2§7§o)");
			l.add("");
			l.add("§7§oRight Click: §e§oSelect Kit");
			l.add("§7§oLeft Click: §6§oDetails & Upgrade Kit");
		}
		if(Start.lord.get(p.getName()) == 3){
			l.add("");
			l.add("§a§lUnlocked §7§o(§a§oLvL 3§7§o)");
			l.add("");
			l.add("§7§oRight Click: §e§oSelect Kit");
			l.add("§7§oLeft Click: §6§oDetails");
		}
				
		itemmeta.setLore(l);
		item.setItemMeta(itemmeta);
			
	return item;
	}
	
	public ItemStack VampireItem(Player p){
		ItemStack item = new ItemStack(Material.REDSTONE);
		ItemMeta itemmeta = item.getItemMeta();
		// DISPLAY ON KIT LEVEL
		if(Start.vampire.get(p.getName()) == 0){
			itemmeta.setDisplayName("§b§lVampire");
		}
		else if(Start.vampire.get(p.getName()) == 1){
			itemmeta.setDisplayName("§b§lVampire §a§lLvL 1");
		}
		else if(Start.vampire.get(p.getName()) == 2){
			itemmeta.setDisplayName("§b§lVampire §a§lLvL 2");
		}
		else if(Start.vampire.get(p.getName()) == 3){
			itemmeta.setDisplayName("§b§lVampire §a§lLvL 3");
		}
		
		List<String> l = new ArrayList<String>();
				
		// LORE ON KIT LEVEL
		if(Start.vampire.get(p.getName()) == 0){
			if(Start.FreeKitEnabled == false){
				l.add("");
				l.add("§4§lLocked");
				l.add("");
				l.add("§c§o§mRight Click: Select Kit");
				l.add("§7§oLeft Click: §6§oDetails & Buy Kit");
			}
			else{
				l.add("");
				l.add("§a§lUnlocked §7§o(§a§oLvL 1§7§o)");
				l.add("");
				l.add("§7§oRight Click: §e§oSelect Kit");
				l.add("§7§oLeft Click: §6§oDetails & Upgrade Kit");
				l.add("");
				l.add("§a§lFREE Kit Saturday!");
			}
		}
		if(Start.vampire.get(p.getName()) == 1){
			l.add("");
			l.add("§a§lUnlocked §7§o(§a§oLvL 1§7§o)");
			l.add("");
			l.add("§7§oRight Click: §e§oSelect Kit");
			l.add("§7§oLeft Click: §6§oDetails & Upgrade Kit");
		}
		if(Start.vampire.get(p.getName()) == 2){
			l.add("");
			l.add("§a§lUnlocked §7§o(§a§oLvL 2§7§o)");
			l.add("");
			l.add("§7§oRight Click: §e§oSelect Kit");
			l.add("§7§oLeft Click: §6§oDetails & Upgrade Kit");
		}
		if(Start.vampire.get(p.getName()) == 3){
			l.add("");
			l.add("§a§lUnlocked §7§o(§a§oLvL 3§7§o)");
			l.add("");
			l.add("§7§oRight Click: §e§oSelect Kit");
			l.add("§7§oLeft Click: §6§oDetails");
		}
				
		itemmeta.setLore(l);
		item.setItemMeta(itemmeta);
			
	return item;
	}
	
	public ItemStack DarkMageItem(Player p){
		ItemStack item = new ItemStack(Material.BLAZE_ROD);
		ItemMeta itemmeta = item.getItemMeta();
		// DISPLAY ON KIT LEVEL
		if(Start.darkmage.get(p.getName()) == 0){
			itemmeta.setDisplayName("§b§lDarkMage");
		}
		else if(Start.darkmage.get(p.getName()) == 1){
			itemmeta.setDisplayName("§b§lDarkMage §a§lLvL 1");
		}
		else if(Start.darkmage.get(p.getName()) == 2){
			itemmeta.setDisplayName("§b§lDarkMage §a§lLvL 2");
		}
		else if(Start.darkmage.get(p.getName()) == 3){
			itemmeta.setDisplayName("§b§lDarkMage §a§lLvL 3");
		}
		
		List<String> l = new ArrayList<String>();
				
		// LORE ON KIT LEVEL
		if(Start.darkmage.get(p.getName()) == 0){
			if(Start.FreeKitEnabled == false){
				l.add("");
				l.add("§4§lLocked");
				l.add("");
				l.add("§c§o§mRight Click: Select Kit");
				l.add("§7§oLeft Click: §6§oDetails & Buy Kit");
			}
			else{
				l.add("");
				l.add("§a§lUnlocked §7§o(§a§oLvL 1§7§o)");
				l.add("");
				l.add("§7§oRight Click: §e§oSelect Kit");
				l.add("§7§oLeft Click: §6§oDetails & Upgrade Kit");
				l.add("");
				l.add("§a§lFREE Kit Saturday!");
			}
		}
		if(Start.darkmage.get(p.getName()) == 1){
			l.add("");
			l.add("§a§lUnlocked §7§o(§a§oLvL 1§7§o)");
			l.add("");
			l.add("§7§oRight Click: §e§oSelect Kit");
			l.add("§7§oLeft Click: §6§oDetails & Upgrade Kit");
		}
		if(Start.darkmage.get(p.getName()) == 2){
			l.add("");
			l.add("§a§lUnlocked §7§o(§a§oLvL 2§7§o)");
			l.add("");
			l.add("§7§oRight Click: §e§oSelect Kit");
			l.add("§7§oLeft Click: §6§oDetails & Upgrade Kit");
		}
		if(Start.darkmage.get(p.getName()) == 3){
			l.add("");
			l.add("§a§lUnlocked §7§o(§a§oLvL 3§7§o)");
			l.add("");
			l.add("§7§oRight Click: §e§oSelect Kit");
			l.add("§7§oLeft Click: §6§oDetails");
		}
				
		itemmeta.setLore(l);
		item.setItemMeta(itemmeta);
			
	return item;
	}
	
	public ItemStack BeastItem(Player p){
		ItemStack item = new ItemStack(Material.IRON_AXE);
		ItemMeta itemmeta = item.getItemMeta();
		// DISPLAY ON KIT LEVEL
		if(Start.beast.get(p.getName()) == 0){
			itemmeta.setDisplayName("§b§lBeast");
		}
		else if(Start.beast.get(p.getName()) == 1){
			itemmeta.setDisplayName("§b§lBeast §a§lLvL 1");
		}
		else if(Start.beast.get(p.getName()) == 2){
			itemmeta.setDisplayName("§b§lBeast §a§lLvL 2");
		}
		else if(Start.beast.get(p.getName()) == 3){
			itemmeta.setDisplayName("§b§lBeast §a§lLvL 3");
		}
		
		List<String> l = new ArrayList<String>();
				
		// LORE ON KIT LEVEL
		if(Start.beast.get(p.getName()) == 0){
			if(Start.FreeKitEnabled == false){
				l.add("");
				l.add("§4§lLocked");
				l.add("");
				l.add("§c§o§mRight Click: Select Kit");
				l.add("§7§oLeft Click: §6§oDetails & Buy Kit");
			}
			else{
				l.add("");
				l.add("§a§lUnlocked §7§o(§a§oLvL 1§7§o)");
				l.add("");
				l.add("§7§oRight Click: §e§oSelect Kit");
				l.add("§7§oLeft Click: §6§oDetails & Upgrade Kit");	
				l.add("");
				l.add("§a§lFREE Kit Saturday!");
			}
		}
		if(Start.beast.get(p.getName()) == 1){
			l.add("");
			l.add("§a§lUnlocked §7§o(§a§oLvL 1§7§o)");
			l.add("");
			l.add("§7§oRight Click: §e§oSelect Kit");
			l.add("§7§oLeft Click: §6§oDetails & Upgrade Kit");
		}
		if(Start.beast.get(p.getName()) == 2){
			l.add("");
			l.add("§a§lUnlocked §7§o(§a§oLvL 2§7§o)");
			l.add("");
			l.add("§7§oRight Click: §e§oSelect Kit");
			l.add("§7§oLeft Click: §6§oDetails & Upgrade Kit");
		}
		if(Start.beast.get(p.getName()) == 3){
			l.add("");
			l.add("§a§lUnlocked §7§o(§a§oLvL 3§7§o)");
			l.add("");
			l.add("§7§oRight Click: §e§oSelect Kit");
			l.add("§7§oLeft Click: §6§oDetails");
		}
				
		itemmeta.setLore(l);
		item.setItemMeta(itemmeta);
			
	return item;
	}
	
	public ItemStack FishItem(Player p){
		ItemStack item = new ItemStack(Material.RAW_FISH);
		ItemMeta itemmeta = item.getItemMeta();
		// DISPLAY ON KIT LEVEL
		if(Start.fish.get(p.getName()) == 0){
			itemmeta.setDisplayName("§b§lFish");
		}
		else if(Start.fish.get(p.getName()) == 1){
			itemmeta.setDisplayName("§b§lFish §a§lLvL 1");
		}
		else if(Start.fish.get(p.getName()) == 2){
			itemmeta.setDisplayName("§b§lFish §a§lLvL 2");
		}
		else if(Start.fish.get(p.getName()) == 3){
			itemmeta.setDisplayName("§b§lFish §a§lLvL 3");
		}
		
		List<String> l = new ArrayList<String>();
				
		// LORE ON KIT LEVEL
		if(Start.fish.get(p.getName()) == 0){
			if(Start.FreeKitEnabled == false){
				l.add("");
				l.add("§4§lLocked");
				l.add("");
				l.add("§c§o§mRight Click: Select Kit");
				l.add("§7§oLeft Click: §6§oDetails & Buy Kit");
			}
			else{
				l.add("");
				l.add("§a§lUnlocked §7§o(§a§oLvL 1§7§o)");
				l.add("");
				l.add("§7§oRight Click: §e§oSelect Kit");
				l.add("§7§oLeft Click: §6§oDetails & Upgrade Kit");
				l.add("");
				l.add("§a§lFREE Kit Saturday!");
			}
		}
		if(Start.fish.get(p.getName()) == 1){
			l.add("");
			l.add("§a§lUnlocked §7§o(§a§oLvL 1§7§o)");
			l.add("");
			l.add("§7§oRight Click: §e§oSelect Kit");
			l.add("§7§oLeft Click: §6§oDetails & Upgrade Kit");
		}
		if(Start.fish.get(p.getName()) == 2){
			l.add("");
			l.add("§a§lUnlocked §7§o(§a§oLvL 2§7§o)");
			l.add("");
			l.add("§7§oRight Click: §e§oSelect Kit");
			l.add("§7§oLeft Click: §6§oDetails & Upgrade Kit");
		}
		if(Start.fish.get(p.getName()) == 3){
			l.add("");
			l.add("§a§lUnlocked §7§o(§a§oLvL 3§7§o)");
			l.add("");
			l.add("§7§oRight Click: §e§oSelect Kit");
			l.add("§7§oLeft Click: §6§oDetails");
		}
				
		itemmeta.setLore(l);
		item.setItemMeta(itemmeta);
			
	return item;
	}
	
	public ItemStack HeavyItem(Player p){
		ItemStack item = new ItemStack(Material.ARROW);
		ItemMeta itemmeta = item.getItemMeta();
		// DISPLAY ON KIT LEVEL
		if(Start.heavy.get(p.getName()) == 0){
			itemmeta.setDisplayName("§b§lHeavy");
		}
		else if(Start.heavy.get(p.getName()) == 1){
			itemmeta.setDisplayName("§b§lHeavy §a§lLvL 1");
		}
		else if(Start.heavy.get(p.getName()) == 2){
			itemmeta.setDisplayName("§b§lHeavy §a§lLvL 2");
		}
		else if(Start.heavy.get(p.getName()) == 3){
			itemmeta.setDisplayName("§b§lHeavy §a§lLvL 3");
		}
		
		List<String> l = new ArrayList<String>();
				
		// LORE ON KIT LEVEL
		if(Start.heavy.get(p.getName()) == 0){
			if(Start.FreeKitEnabled == false){
				l.add("");
				l.add("§4§lLocked");
				l.add("");
				l.add("§c§o§mRight Click: Select Kit");
				l.add("§7§oLeft Click: §6§oDetails & Buy Kit");
			}
			else{
				l.add("");
				l.add("§a§lUnlocked §7§o(§a§oLvL 1§7§o)");
				l.add("");
				l.add("§7§oRight Click: §e§oSelect Kit");
				l.add("§7§oLeft Click: §6§oDetails & Upgrade Kit");
				l.add("");
				l.add("§a§lFREE Kit Saturday!");
			}
		}
		if(Start.heavy.get(p.getName()) == 1){
			l.add("");
			l.add("§a§lUnlocked §7§o(§a§oLvL 1§7§o)");
			l.add("");
			l.add("§7§oRight Click: §e§oSelect Kit");
			l.add("§7§oLeft Click: §6§oDetails & Upgrade Kit");
		}
		if(Start.heavy.get(p.getName()) == 2){
			l.add("");
			l.add("§a§lUnlocked §7§o(§a§oLvL 2§7§o)");
			l.add("");
			l.add("§7§oRight Click: §e§oSelect Kit");
			l.add("§7§oLeft Click: §6§oDetails & Upgrade Kit");
		}
		if(Start.heavy.get(p.getName()) == 3){
			l.add("");
			l.add("§a§lUnlocked §7§o(§a§oLvL 3§7§o)");
			l.add("");
			l.add("§7§oRight Click: §e§oSelect Kit");
			l.add("§7§oLeft Click: §6§oDetails");
		}
				
		itemmeta.setLore(l);
		item.setItemMeta(itemmeta);
			
	return item;
	}
	
	public ItemStack GrimReaperItem(Player p){
		ItemStack item = new ItemStack(Material.SKULL_ITEM);
		ItemMeta itemmeta = item.getItemMeta();
		item.setDurability((short) 1);
		// DISPLAY ON KIT LEVEL
		if(Start.grimreaper.get(p.getName()) == 0){
			itemmeta.setDisplayName("§b§lGrimReaper");
		}
		else if(Start.grimreaper.get(p.getName()) == 1){
			itemmeta.setDisplayName("§b§lGrimReaper §a§lLvL 1");
		}
		else if(Start.grimreaper.get(p.getName()) == 2){
			itemmeta.setDisplayName("§b§lGrimReaper §a§lLvL 2");
		}
		else if(Start.grimreaper.get(p.getName()) == 3){
			itemmeta.setDisplayName("§b§lGrimReaper §a§lLvL 3");
		}
		
		List<String> l = new ArrayList<String>();
				
		// LORE ON KIT LEVEL
		if(Start.grimreaper.get(p.getName()) == 0){
			if(Start.FreeKitEnabled == false){
				l.add("");
				l.add("§4§lLocked");
				l.add("");
				l.add("§c§o§mRight Click: Select Kit");
				l.add("§7§oLeft Click: §6§oDetails & Buy Kit");
			}
			else{
				l.add("");
				l.add("§a§lUnlocked §7§o(§a§oLvL 1§7§o)");
				l.add("");
				l.add("§7§oRight Click: §e§oSelect Kit");
				l.add("§7§oLeft Click: §6§oDetails & Upgrade Kit");
				l.add("");
				l.add("§a§lFREE Kit Saturday!");
			}
		}
		if(Start.grimreaper.get(p.getName()) == 1){
			l.add("");
			l.add("§a§lUnlocked §7§o(§a§oLvL 1§7§o)");
			l.add("");
			l.add("§7§oRight Click: §e§oSelect Kit");
			l.add("§7§oLeft Click: §6§oDetails & Upgrade Kit");
		}
		if(Start.grimreaper.get(p.getName()) == 2){
			l.add("");
			l.add("§a§lUnlocked §7§o(§a§oLvL 2§7§o)");
			l.add("");
			l.add("§7§oRight Click: §e§oSelect Kit");
			l.add("§7§oLeft Click: §6§oDetails & Upgrade Kit");
		}
		if(Start.grimreaper.get(p.getName()) == 3){
			l.add("");
			l.add("§a§lUnlocked §7§o(§a§oLvL 3§7§o)");
			l.add("");
			l.add("§7§oRight Click: §e§oSelect Kit");
			l.add("§7§oLeft Click: §6§oDetails");
		}
				
		itemmeta.setLore(l);
		item.setItemMeta(itemmeta);
			
	return item;
	}
	
	public ItemStack MinerItem(Player p){
		ItemStack item = new ItemStack(Material.GOLD_PICKAXE);
		ItemMeta itemmeta = item.getItemMeta();
		// DISPLAY ON KIT LEVEL
		if(Start.miner.get(p.getName()) == 0){
			itemmeta.setDisplayName("§b§lMiner");
		}
		else if(Start.miner.get(p.getName()) == 1){
			itemmeta.setDisplayName("§b§lMiner §a§lLvL 1");
		}
		else if(Start.miner.get(p.getName()) == 2){
			itemmeta.setDisplayName("§b§lMiner §a§lLvL 2");
		}
		else if(Start.miner.get(p.getName()) == 3){
			itemmeta.setDisplayName("§b§lMiner §a§lLvL 3");
		}
		
		List<String> l = new ArrayList<String>();
				
		// LORE ON KIT LEVEL
		if(Start.miner.get(p.getName()) == 0){
			if(Start.FreeKitEnabled == false){
				l.add("");
				l.add("§4§lLocked");
				l.add("");
				l.add("§c§o§mRight Click: Select Kit");
				l.add("§7§oLeft Click: §6§oDetails & Buy Kit");
			}
			else{
				l.add("");
				l.add("§a§lUnlocked §7§o(§a§oLvL 1§7§o)");
				l.add("");
				l.add("§7§oRight Click: §e§oSelect Kit");
				l.add("§7§oLeft Click: §6§oDetails & Upgrade Kit");
				l.add("");
				l.add("§a§lFREE Kit Saturday!");
			}
		}
		if(Start.miner.get(p.getName()) == 1){
			l.add("");
			l.add("§a§lUnlocked §7§o(§a§oLvL 1§7§o)");
			l.add("");
			l.add("§7§oRight Click: §e§oSelect Kit");
			l.add("§7§oLeft Click: §6§oDetails & Upgrade Kit");
		}
		if(Start.miner.get(p.getName()) == 2){
			l.add("");
			l.add("§a§lUnlocked §7§o(§a§oLvL 2§7§o)");
			l.add("");
			l.add("§7§oRight Click: §e§oSelect Kit");
			l.add("§7§oLeft Click: §6§oDetails & Upgrade Kit");
		}
		if(Start.miner.get(p.getName()) == 3){
			l.add("");
			l.add("§a§lUnlocked §7§o(§a§oLvL 3§7§o)");
			l.add("");
			l.add("§7§oRight Click: §e§oSelect Kit");
			l.add("§7§oLeft Click: §6§oDetails");
		}
				
		itemmeta.setLore(l);
		item.setItemMeta(itemmeta);
			
	return item;
	}
}
