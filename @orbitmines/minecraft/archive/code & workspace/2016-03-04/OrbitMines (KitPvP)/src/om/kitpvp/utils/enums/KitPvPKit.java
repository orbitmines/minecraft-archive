package om.kitpvp.utils.enums;

import java.util.ArrayList;
import java.util.List;

import om.api.handlers.Kit;
import om.api.utils.enums.Currency;
import om.api.utils.enums.ranks.VIPRank;
import om.kitpvp.KitPvP;

import org.bukkit.Material;

public enum KitPvPKit {

	KNIGHT("Knight", Material.STONE_SWORD, 3, null, Currency.KITPVP_MONEY, 0, 10000, 50000),
	ARCHER("Archer", Material.BOW, 3, null, Currency.KITPVP_MONEY, 0, 25000, 50000),
	SOLDIER("Soldier", Material.LEATHER_LEGGINGS, 3, null, Currency.KITPVP_MONEY, 7500, 35000, 42500),
	WIZARD("Wizard", Material.POTION, 3, null, Currency.KITPVP_MONEY, 10000, 30000, 45000),
	TANK("Tank", Material.DIAMOND_CHESTPLATE, 3, null, Currency.KITPVP_MONEY, 15000, 50000, 80000),
	DRUNK("Drunk", Material.CHAINMAIL_LEGGINGS, 3, null, Currency.KITPVP_MONEY, 17500, 52500, 87500),
	PYRO("Pyro", Material.GOLD_HELMET, 3, null, Currency.KITPVP_MONEY, 25000, 65000, 95000),
	BUNNY("Bunny", Material.LEATHER_BOOTS, 3, null, Currency.KITPVP_MONEY, 37500, 50000, 72500),
	NECROMANCER("Necromancer", Material.GOLD_HOE, 3, null, Currency.KITPVP_MONEY, 50000, 75000, 100000),
	KING("King", Material.DIAMOND_HELMET, 3, null, Currency.KITPVP_MONEY, 10000, 25000, 40000),
	TREE("Tree", Material.LEAVES, 3, null, Currency.KITPVP_MONEY, 20000, 35000, 50000),
	BLAZE("Blaze", Material.BLAZE_POWDER, 3, null, Currency.KITPVP_MONEY, 30000, 60000, 40000),
	TNT("TNT", Material.TNT, 3, null, Currency.KITPVP_MONEY, 40000, 65000, 42500),
	FISHERMAN("Fisherman", Material.FISHING_ROD, 3, null, Currency.KITPVP_MONEY, 32500, 40000, 45000),
	SNOWGOLEM("SnowGolem", Material.PUMPKIN, 3, null, Currency.KITPVP_MONEY, 40000, 55000, 60000),
	LIBRARIAN("Librarian", Material.BOOKSHELF, 3, null, Currency.KITPVP_MONEY, 60000, 70000, 80000),
	SPIDER("Spider", Material.SPIDER_EYE, 3, null, Currency.KITPVP_MONEY, 67500, 75000, 85000),
	VILLAGER("Villager", Material.EMERALD, 3, null, Currency.KITPVP_MONEY, 72500, 85000, 100000),
	ASSASSIN("Assassin", Material.DIAMOND_SWORD, 1, null, Currency.ORBITMINES_TOKENS, 150),
	LORD("Lord", Material.GOLD_SWORD, 1, null, Currency.ORBITMINES_TOKENS, 150),
	VAMPIRE("Vampire", Material.REDSTONE, 1, null, Currency.ORBITMINES_TOKENS, 150),
	DARKMAGE("DarkMage", Material.BLAZE_ROD, 1, null, Currency.ORBITMINES_TOKENS, 150),
	BEAST("Beast", Material.IRON_AXE, 1, null, Currency.ORBITMINES_TOKENS, 150),
	FISH("Fish", Material.RAW_FISH, 1, null, Currency.ORBITMINES_TOKENS, 150),
	HEAVY("Heavy", Material.ARROW, 1, null, Currency.ORBITMINES_TOKENS, 150),
	GRIMREAPER("GrimReaper", Material.SKULL_ITEM, 1, null, Currency.ORBITMINES_TOKENS, 150),
	MINER("Miner", Material.GOLD_PICKAXE, 1, null, Currency.ORBITMINES_TOKENS, 150),
	FARMER("Farmer", Material.HAY_BLOCK, 1, VIPRank.Gold_VIP, null, -1),
	UNDEATH_KING("Undeath King", Material.ROTTEN_FLESH, 1, VIPRank.Emerald_VIP, null, -1),
	ENGINEER("Engineer", Material.ENDER_PEARL, 1, VIPRank.Diamond_VIP, null, -1);
	
	private KitPvP kitpvp;
	private String name;
	private Material material;
	private int maxLevel;
	private VIPRank viprank;
	private Currency currency;
	private int[] prices;
	
	KitPvPKit(String name, Material material, int maxLevel, VIPRank viprank, Currency currency, int... prices){
		this.kitpvp = KitPvP.getInstance();
		this.name = name;
		this.material = material;
		this.maxLevel = maxLevel;
		this.viprank = viprank;
		this.currency = currency;
		this.prices = prices;
	}
	
	public Kit getKit(int level){
		return Kit.getKit(getName() + " " + level);
	}
	
	public String getName(){
		return name;
	}
	
	public Material getMaterial(){
		return material;
	}
	
	public short getDurability(){
		if(this == GRIMREAPER) return 1;
		return 0;
	}
	
	public int getMaxLevel(){
		return maxLevel;
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
		if(kitpvp.isFreeKitEnabled() && level == 0){
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
			if(level != getMaxLevel()){
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
		return viprank;
	}
	
	public Currency getCurrency(){
		return currency;
	}
	
	public int getPrice(int level){
		if(prices.length >= level) return prices[level -1];
		return 0;
	}
}

