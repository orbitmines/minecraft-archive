package fadidev.orbitmines.kitpvp.utils.enums;

import fadidev.orbitmines.api.handlers.Currency;
import fadidev.orbitmines.api.handlers.Kit;
import fadidev.orbitmines.api.handlers.Messages;
import fadidev.orbitmines.api.utils.enums.ranks.VIPRank;
import fadidev.orbitmines.kitpvp.OrbitMinesKitPvP;
import fadidev.orbitmines.kitpvp.handlers.KitPvPMessages;
import fadidev.orbitmines.kitpvp.handlers.KitPvPPlayer;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public enum KitPvPKit {

	KNIGHT("Knight", Material.STONE_SWORD, 3, null, Material.GOLD_INGOT, 0, 10000, 50000),
	ARCHER("Archer", Material.BOW, 3, null, Material.GOLD_INGOT, 0, 25000, 50000),
	SOLDIER("Soldier", Material.LEATHER_LEGGINGS, 3, null, Material.GOLD_INGOT, 7500, 35000, 42500),
	WIZARD("Wizard", Material.POTION, 3, null, Material.GOLD_INGOT, 10000, 30000, 45000),
	TANK("Tank", Material.DIAMOND_CHESTPLATE, 3, null, Material.GOLD_INGOT, 15000, 50000, 80000),
	DRUNK("Drunk", Material.CHAINMAIL_LEGGINGS, 3, null, Material.GOLD_INGOT, 17500, 52500, 87500),
	PYRO("Pyro", Material.GOLD_HELMET, 3, null, Material.GOLD_INGOT, 25000, 65000, 95000),
	BUNNY("Bunny", Material.LEATHER_BOOTS, 3, null, Material.GOLD_INGOT, 37500, 50000, 72500),
	NECROMANCER("Necromancer", Material.GOLD_HOE, 3, null, Material.GOLD_INGOT, 50000, 75000, 100000),
	KING("King", Material.DIAMOND_HELMET, 3, null, Material.GOLD_INGOT, 10000, 25000, 40000),
	TREE("Tree", Material.LEAVES, 3, null, Material.GOLD_INGOT, 20000, 35000, 50000),
	BLAZE("Blaze", Material.BLAZE_POWDER, 3, null, Material.GOLD_INGOT, 30000, 60000, 40000),
	TNT("TNT", Material.TNT, 3, null, Material.GOLD_INGOT, 40000, 65000, 42500),
	FISHERMAN("Fisherman", Material.FISHING_ROD, 3, null, Material.GOLD_INGOT, 32500, 40000, 45000),
	SNOWGOLEM("SnowGolem", Material.PUMPKIN, 3, null, Material.GOLD_INGOT, 40000, 55000, 60000),
	LIBRARIAN("Librarian", Material.BOOKSHELF, 3, null, Material.GOLD_INGOT, 60000, 70000, 80000),
	SPIDER("Spider", Material.SPIDER_EYE, 3, null, Material.GOLD_INGOT, 67500, 75000, 85000),
	VILLAGER("Villager", Material.EMERALD, 3, null, Material.GOLD_INGOT, 72500, 85000, 100000),
	ASSASSIN("Assassin", Material.DIAMOND_SWORD, 1, null, Material.GOLD_NUGGET, 150),
	LORD("Lord", Material.GOLD_SWORD, 1, null, Material.GOLD_NUGGET, 150),
	VAMPIRE("Vampire", Material.REDSTONE, 1, null, Material.GOLD_NUGGET, 150),
	DARKMAGE("DarkMage", Material.BLAZE_ROD, 1, null, Material.GOLD_NUGGET, 150),
	BEAST("Beast", Material.IRON_AXE, 1, null, Material.GOLD_NUGGET, 150),
	FISH("Fish", Material.RAW_FISH, 1, null, Material.GOLD_NUGGET, 150),
	HEAVY("Heavy", Material.ARROW, 1, null, Material.GOLD_NUGGET, 150),
	GRIMREAPER("GrimReaper", Material.SKULL_ITEM, 1, null, Material.GOLD_NUGGET, 150),
	MINER("Miner", Material.GOLD_PICKAXE, 1, null, Material.GOLD_NUGGET, 150),
	FARMER("Farmer", Material.HAY_BLOCK, 1, VIPRank.GOLD_VIP, null, -1),
	UNDEATH_KING("Undeath King", Material.ROTTEN_FLESH, 1, VIPRank.EMERALD_VIP, null, -1),
	ENGINEER("Engineer", Material.ENDER_PEARL, 1, VIPRank.DIAMOND_VIP, null, -1);
	
	private OrbitMinesKitPvP kitPvP;
	private String name;
	private Material material;
	private int maxLevel;
	private VIPRank viprank;
	private Material currency;
	private int[] prices;
	
	KitPvPKit(String name, Material material, int maxLevel, VIPRank viprank, Material currency, int... prices){
		this.kitPvP = OrbitMinesKitPvP.getKitPvP();
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
	
	public List<String> getKitLore(KitPvPPlayer omp, int level){
		List<String> kitLore = new ArrayList<>();
		kitLore.add("");
		if(kitPvP.isFreeKitEnabled() && level == 0){
			kitLore.add("§d§lFREE Kit " + KitPvPMessages.WORD_SATURDAY.get(omp) + "!");
		}
		else{
			if(level != 0){
				kitLore.add("§a§l" + Messages.WORD_UNLOCKED.get(omp) + " §7§o(§a§oLvL " + level + "§7§o)");
			}
			else{
				kitLore.add("§4§l"  + Messages.WORD_LOCKED.get(omp));
			}
		}
		kitLore.add("");
		if(level == 0){
			kitLore.add("§c§o§l§m" + KitPvPMessages.WORD_RIGHT_CLICK.get(omp) + " §c§m(" + KitPvPMessages.WORD_SELECT_KIT.get(omp) + ")");
			kitLore.add("§6§l§o" + KitPvPMessages.WORD_LEFT_CLICK.get(omp) + " §7(Details & " + KitPvPMessages.WORD_BUY_KIT.get(omp) + ")");
		}
		else{
			kitLore.add("§e§l§o" + KitPvPMessages.WORD_RIGHT_CLICK.get(omp) + " §7(" + KitPvPMessages.WORD_SELECT_KIT.get(omp) + ")");
			if(level != getMaxLevel()){
				kitLore.add("§6§l§o" + KitPvPMessages.WORD_LEFT_CLICK.get(omp) + " §7(Details & Upgrade Kit)");
			}
			else{
				kitLore.add("§6§l§o" + KitPvPMessages.WORD_LEFT_CLICK.get(omp) + " §7(Details)");
			}
		}
		kitLore.add("");
		
		return kitLore;
	}
	
	public VIPRank getVIPRank(){
		return viprank;
	}
	
	public Currency getCurrency(){
        if(currency == null)
            return null;
		return Currency.getCurrency(currency);
	}
	
	public int getPrice(int level){
		if(prices.length >= level) return prices[level -1];
		return 0;
	}
}

