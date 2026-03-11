package om.kitpvp.enums;

import java.util.ArrayList;
import java.util.List;

import me.O_o_Fadi_o_O.OrbitMines.utils.ServerData;
import me.O_o_Fadi_o_O.OrbitMines.utils.kitpvp.Currency;
import me.O_o_Fadi_o_O.OrbitMines.utils.kitpvp.Kit;
import me.O_o_Fadi_o_O.OrbitMines.utils.kitpvp.VIPRank;

import org.bukkit.Material;

public enum KitPvPKit {

	KNIGHT,
	ARCHER,
	SOLDIER,
	WIZARD,
	TANK,
	DRUNK,
	PYRO,
	BUNNY,
	NECROMANCER,
	KING,
	TREE,
	BLAZE,
	TNT,
	FISHERMAN,
	SNOWGOLEM,
	LIBRARIAN,
	SPIDER,
	VILLAGER,
	ASSASSIN,
	LORD,
	VAMPIRE,
	DARKMAGE,
	BEAST,
	FISH,
	HEAVY,
	GRIMREAPER,
	MINER,
	
	FARMER,
	UNDEATH_KING,
	ENGINEER;
	
	public Kit getKit(int level){
		return Kit.getKit(getName() + " " + level);
	}
	
	public String getName(){
		switch(this){
			case KNIGHT:
				return "Knight";
			case ARCHER:
				return "Archer";
			case SOLDIER:
				return "Soldier";
			case WIZARD:
				return "Wizard";
			case TANK:
				return "Tank";
			case DRUNK:
				return "Drunk";
			case PYRO:
				return "Pyro";
			case BUNNY:
				return "Bunny";
			case NECROMANCER:
				return "Necromancer";
			case KING:
				return "King";
			case TREE:
				return "Tree";
			case BLAZE:
				return "Blaze";
			case TNT:
				return "TNT";
			case FISHERMAN:
				return "Fisherman";
			case SNOWGOLEM:
				return "SnowGolem";
			case LIBRARIAN:
				return "Librarian";
			case SPIDER:
				return "Spider";
			case VILLAGER:
				return "Villager";
			case ASSASSIN:
				return "Assassin";
			case VAMPIRE:
				return "Vampire";
			case DARKMAGE:
				return "DarkMage";
			case BEAST:
				return "Beast";
			case FISH:
				return "Fish";
			case HEAVY:
				return "Heavy";
			case GRIMREAPER:
				return "GrimReaper";
			case MINER:
				return "Miner";
			case LORD:
				return "Lord";
			case FARMER:
				return "Farmer";
			case UNDEATH_KING:
				return "Undeath King";
			case ENGINEER:
				return "Engineer";
			default:
				return null;
		}
	}
	
	public Material getMaterial(){
		switch(this){
			case ARCHER:
				return Material.BOW;
			case ASSASSIN:
				return Material.DIAMOND_SWORD;
			case BEAST:
				return Material.IRON_AXE;
			case BLAZE:
				return Material.BLAZE_POWDER;
			case BUNNY:
				return Material.LEATHER_BOOTS;
			case DARKMAGE:
				return Material.BLAZE_ROD;
			case DRUNK:
				return Material.CHAINMAIL_LEGGINGS;
			case FISH:
				return Material.RAW_FISH;
			case FISHERMAN:
				return Material.FISHING_ROD;
			case GRIMREAPER:
				return Material.SKULL_ITEM;
			case HEAVY:
				return Material.ARROW;
			case KING:
				return Material.DIAMOND_HELMET;
			case KNIGHT:
				return Material.STONE_SWORD;
			case LIBRARIAN:
				return Material.BOOKSHELF;
			case LORD:
				return Material.GOLD_SWORD;
			case MINER:
				return Material.GOLD_PICKAXE;
			case NECROMANCER:
				return Material.GOLD_HOE;
			case PYRO:
				return Material.GOLD_HELMET;
			case SNOWGOLEM:
				return Material.PUMPKIN;
			case SOLDIER:
				return Material.LEATHER_LEGGINGS;
			case SPIDER:
				return Material.SPIDER_EYE;
			case TANK:
				return Material.DIAMOND_CHESTPLATE;
			case TNT:
				return Material.TNT;
			case TREE:
				return Material.LEAVES;
			case VAMPIRE:
				return Material.REDSTONE;
			case VILLAGER:
				return Material.EMERALD;
			case WIZARD:
				return Material.POTION;
			case FARMER:
				return Material.HAY_BLOCK;
			case UNDEATH_KING:
				return Material.ROTTEN_FLESH;
			case ENGINEER:
				return Material.ENDER_PEARL;
			default:
				return null;
		}
	}
	
	public short getDurability(){
		switch(this){
			case GRIMREAPER:
				return 1;
			default:
				return 0;
		}
	}
	
	public int getHighestLevel(){
		switch(this){
			case ASSASSIN:
				return 1;
			case BEAST:
				return 1;
			case DARKMAGE:
				return 1;
			case FISH:
				return 1;
			case GRIMREAPER:
				return 1;
			case HEAVY:
				return 1;
			case LORD:
				return 1;
			case MINER:
				return 1;
			case VAMPIRE:
				return 1;
			case FARMER:
				return 1;
			case UNDEATH_KING:
				return 1;
			case ENGINEER:
				return 1;
			default:
				return 3;
		}
	}
	
	public int getNextArrow(){
		switch(this){
			case ARCHER:
				return 10;
			case HEAVY:
				return 21;
			case NECROMANCER:
				return 27;
			case PYRO:
				return 22;
			case SOLDIER:
				return 18;
			case TNT:
				return 30;
			default:
				return 0;
		}
	}
	
	public int getMaxArrows(int level){
		switch(this){
			case ARCHER:
				return 32;
			case HEAVY:
				return 8;
			case NECROMANCER:
				if(level == 1){
					return 5;
				}
				return 10;
			case PYRO:
				return 12;
			case SOLDIER:
				if(level == 3){
					return 20;
				}
				return 16;
			case TNT:
				if(level == 3){
					return 4;
				}
				return 2;
			default:
				return 0;
		}
	}
	
	public String getSelectedKitName(int level){
		return "§b§l" + getName() + " §7(§aLvL " + level + "§7)";
	}
	
	public String getKitName(int level){
		if(level != 0){
			return "§b§l" + getName() + " §a§lLvL " + level;
		}
		return "§b§l" + getName();
	}
	
	public List<String> getKitLore(int level){
		List<String> kitlore = new ArrayList<String>();
		kitlore.add("");
		if(ServerData.getKitPvP().isFreeKitEnabled() && level == 0){
			kitlore.add("§d§lFREE Kit Saturday!");
		}
		else{
			if(level != 0){
				kitlore.add("§a§lUnlocked §7§o(§a§oLvL " + level + "§7§o)");
			}
			else{
				kitlore.add("§4§lLocked");
			}
		}
		kitlore.add("");
		if(level == 0){
			kitlore.add("§c§o§l§mRight Click §c§m(Select Kit)");
			kitlore.add("§6§l§oLeft Click §7(Details & Buy Kit)");
		}
		else{
			kitlore.add("§e§l§oRight Click §7(Select Kit)");				
			if(level != getHighestLevel()){
				kitlore.add("§6§l§oLeft Click §7(Details & Upgrade Kit)");
			}
			else{
				kitlore.add("§6§l§oLeft Click §7(Details)");
			}
		}
		kitlore.add("");
		
		return kitlore;
	}
	
	public VIPRank getVIPRank(){
		switch(this){
			case FARMER:
				return VIPRank.Gold_VIP;
			case UNDEATH_KING:
				return VIPRank.Emerald_VIP;
			case ENGINEER:
				return VIPRank.Diamond_VIP;
			default:
				return null;
		}
	}
	
	public Currency getCurrency(){
		if(this == ASSASSIN || this == LORD || this == VAMPIRE || this == DARKMAGE || this == BEAST || this == FISH || this == HEAVY || this == GRIMREAPER || this == MINER){
			return Currency.ORBITMINES_TOKENS;
		}
		return Currency.KITPVP_MONEY;
	}
	
	public int getPrice(int level){
		switch(this){
			case ARCHER:
				if(level == 1){
					return 0;
				}
				else if(level == 2){
					return 25000;
				}
				else if(level == 3){
					return 50000;
				}
				else{
					return 0;
				}
			case ASSASSIN:
				if(level == 1){
					return 150;
				}
				else{
					return 0;
				}
			case BEAST:
				if(level == 1){
					return 150;
				}
				else{
					return 0;
				}
			case BLAZE:
				if(level == 1){
					return 30000;
				}
				else if(level == 2){
					return 60000;
				}
				else if(level == 3){
					return 40000;
				}
				else{
					return 0;
				}
			case BUNNY:
				if(level == 1){
					return 37500;
				}
				else if(level == 2){
					return 50000;
				}
				else if(level == 3){
					return 72500;
				}
				else{
					return 0;
				}
			case DARKMAGE:
				if(level == 1){
					return 150;
				}
				else{
					return 0;
				}
			case DRUNK:
				if(level == 1){
					return 17500;
				}
				else if(level == 2){
					return 52500;
				}
				else if(level == 3){
					return 87500;
				}
				else{
					return 0;
				}
			case FISH:
				if(level == 1){
					return 150;
				}
				else{
					return 0;
				}
			case FISHERMAN:
				if(level == 1){
					return 32500;
				}
				else if(level == 2){
					return 40000;
				}
				else if(level == 3){
					return 45000;
				}
				else{
					return 0;
				}
			case GRIMREAPER:
				if(level == 1){
					return 150;
				}
				else{
					return 0;
				}
			case HEAVY:
				if(level == 1){
					return 150;
				}
				else{
					return 0;
				}
			case KING:
				if(level == 1){
					return 10000;
				}
				else if(level == 2){
					return 25000;
				}
				else if(level == 3){
					return 40000;
				}
				else{
					return 0;
				}
			case KNIGHT:
				if(level == 1){
					return 0;
				}
				else if(level == 2){
					return 10000;
				}
				else if(level == 3){
					return 50000;
				}
				else{
					return 0;
				}
			case LIBRARIAN:
				if(level == 1){
					return 60000;
				}
				else if(level == 2){
					return 70000;
				}
				else if(level == 3){
					return 80000;
				}
				else{
					return 0;
				}
			case LORD:
				if(level == 1){
					return 150;
				}
				else{
					return 0;
				}
			case MINER:
				if(level == 1){
					return 150;
				}
				else{
					return 0;
				}
			case NECROMANCER:
				if(level == 1){
					return 50000;
				}
				else if(level == 2){
					return 75000;
				}
				else if(level == 3){
					return 100000;
				}
				else{
					return 0;
				}
			case PYRO:
				if(level == 1){
					return 25000;
				}
				else if(level == 2){
					return 65000;
				}
				else if(level == 3){
					return 95000;
				}
				else{
					return 0;
				}
			case SNOWGOLEM:
				if(level == 1){
					return 40000;
				}
				else if(level == 2){
					return 55000;
				}
				else if(level == 3){
					return 60000;
				}
				else{
					return 0;
				}
			case SOLDIER:
				if(level == 1){
					return 7500;
				}
				else if(level == 2){
					return 35000;
				}
				else if(level == 3){
					return 42500;
				}
				else{
					return 0;
				}
			case SPIDER:
				if(level == 1){
					return 67500;
				}
				else if(level == 2){
					return 75000;
				}
				else if(level == 3){
					return 85000;
				}
				else{
					return 0;
				}
			case TANK:
				if(level == 1){
					return 15000;
				}
				else if(level == 2){
					return 50000;
				}
				else if(level == 3){
					return 80000;
				}
				else{
					return 0;
				}
			case TNT:
				if(level == 1){
					return 40000;
				}
				else if(level == 2){
					return 65000;
				}
				else if(level == 3){
					return 42500;
				}
				else{
					return 0;
				}
			case TREE:
				if(level == 1){
					return 20000;
				}
				else if(level == 2){
					return 35000;
				}
				else if(level == 3){
					return 50000;
				}
				else{
					return 0;
				}
			case VAMPIRE:
				if(level == 1){
					return 150;
				}
				else{
					return 0;
				}
			case VILLAGER:
				if(level == 1){
					return 72500;
				}
				else if(level == 2){
					return 85000;
				}
				else if(level == 3){
					return 100000;
				}
				else{
					return 0;
				}
			case WIZARD:
				if(level == 1){
					return 10000;
				}
				else if(level == 2){
					return 30000;
				}
				else if(level == 3){
					return 45000;
				}
				else{
					return 0;
				}
			case FARMER:
				return 0;
			case UNDEATH_KING:
				return 0;
			case ENGINEER:
				return 0;
			default:
				return 0;
		}
	}
}

