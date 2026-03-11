package om.fog.utils.enums;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import om.fog.utils.FoGUtils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public enum ShieldLore {
	
	SHIELD("Shield", 10),
	SHIELD_REGEN("Repair", 3),
	BLOCK("Block", 2);
	
	private static ShieldLore[] correctOrder = { BLOCK, SHIELD, SHIELD_REGEN };
	private String name;
	private int maxLevel;
	
	ShieldLore(String name, int maxLevel){
		this.name = name;
		this.maxLevel = maxLevel;
	}
	
	public String getName() {
		return name;
	}
	
	public int getMaxLevel() {
		return maxLevel;
	}
	
	public Rarity getRarity(int level){
		switch(this){
			case BLOCK:
				switch(level){
					case 1:
						return Rarity.RARE;
					case 2:
						return Rarity.LEGENDARY;
				}
				break;
			case SHIELD:
				switch(level){
					case 1:
						return Rarity.COMMON;
					case 2:
						return Rarity.COMMON;
					case 3:
						return Rarity.UNCOMMON;
					case 4:
						return Rarity.UNCOMMON;
					case 5:
						return Rarity.UNCOMMON;
					case 6:
						return Rarity.RARE;
					case 7:
						return Rarity.RARE;
					case 8:
						return Rarity.LEGENDARY;
					case 9:
						return Rarity.LEGENDARY;
					case 10:
						return Rarity.LEGENDARY;
				}
				break;
			case SHIELD_REGEN:
				switch(level){
					case 1:
						return Rarity.UNCOMMON;
					case 2:
						return Rarity.RARE;
					case 3:
						return Rarity.LEGENDARY;
				}
				break;
			default:
				break;
		}
		return null;
	}
	
	public int getLevel(ItemStack item){
		if(item != null && item.getItemMeta() != null && item.getItemMeta().getLore() != null){
			List<String> lore = item.getItemMeta().getLore();
			if(lore == null) return -1;
			
			for(String line : lore){
				if(line.length() > 2 && line.substring(2).startsWith(getName())){
					return FoGUtils.parseInteger(line.substring(3 + getName().length()));
				}
			}
		}
		return -1;
	}
	
	public ItemStack add(ItemStack item, int level){
		List<String> lore = item.getItemMeta().getLore();
		List<String> newLore = new ArrayList<String>();
		newLore.add(getRarity(level).getColor() + getName() + " " + FoGUtils.parseString(level));
		
		if(lore != null) newLore.addAll(lore);
		
		ItemMeta meta = item.getItemMeta();
		meta.setLore(newLore);
		item.setItemMeta(meta);
		return item;
	}
	
	public ItemStack addAfter(ItemStack item, int level){
		List<String> lore = item.getItemMeta().getLore();
		List<String> newLore = new ArrayList<String>();
		
		if(lore != null) newLore.addAll(lore);
		newLore.add(getRarity(level).getColor() + getName() + " " + FoGUtils.parseString(level));
		
		ItemMeta meta = item.getItemMeta();
		meta.setLore(newLore);
		item.setItemMeta(meta);
		return item;
	}
	
	public ItemStack getEnchantment(int level){
		ItemStack item = new ItemStack(Material.ENCHANTED_BOOK);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(getRarity(level).getColor() + "§l" + getName() + " " + FoGUtils.parseString(level));
		List<String> lore = new ArrayList<String>();
		lore.add(getRarity(level).getName() + " Enchantment §7(Shields)");
		meta.setLore(lore);
		item.setItemMeta(meta);
		
		return item;
	}
	
	public static ItemStack random(){
		ShieldLoreLevel sl = getLore(Rarity.random());
		return sl.getLore().getEnchantment(sl.getLevel());
	}
	
	public static ItemStack random(Rarity rarity){
		ShieldLoreLevel sl = getLore(rarity);
		return sl.getLore().getEnchantment(sl.getLevel());
	}
	
	private static ShieldLoreLevel getLore(Rarity rarity){
		List<ShieldLoreLevel> slList = new ArrayList<ShieldLoreLevel>();
		for(ShieldLore sl : ShieldLore.values()){
			for(int i = 1; i <= sl.getMaxLevel(); i++){
				if(rarity == sl.getRarity(i)){
					slList.add(new ShieldLoreLevel(sl, i));
				}
			}
		}
		return slList.get(new Random().nextInt(slList.size()));
	}
	
	public static ShieldLoreLevel getEnchantment(ItemStack item){
		if(item != null && item.getItemMeta().getDisplayName() != null){
			String name = item.getItemMeta().getDisplayName();
			String[] nameParts = name.split(" ");
			ShieldLore sl = getByName(nameParts[0].substring(4));
			
			if(sl != null){
				if(!name.substring(4).startsWith("Shield Buster")){
					return new ShieldLoreLevel(sl, FoGUtils.parseInteger(nameParts[1]));
				}
			}
			else{
				sl = getByName(nameParts[0].substring(4) + " " + nameParts[1]);
				
				if(sl != null){
					return new ShieldLoreLevel(sl, FoGUtils.parseInteger(nameParts[2]));
				}
			}
		}
		return null;
	}
	
	public static ShieldLore getByName(String name){
		for(ShieldLore sl : correctOrder){
			if(sl.getName().equalsIgnoreCase(name)){
				return sl;
			}
		}
		return null;
	}
	
	public static ShieldLore[] getCorrectOrder() {
		return correctOrder;
	}
	
	public static class ShieldLoreLevel {
		
		private ShieldLore sl;
		private int level;
		
		public ShieldLoreLevel(ShieldLore sl, int level) {
			this.sl = sl;
			this.level = level;
		}
		
		public ShieldLore getLore() {
			return sl;
		}
		
		public int getLevel() {
			return level;
		}
	}
	
	public enum ArmorType {
		
		HELMET("Helmet", 3, 0),
		CHESTPLATE("Chestplate", 2, 1),
		LEGGINGS("Leggings", 1, 2),
		BOOTS("Boots", 0, 3);
		
		private static ArmorType[] correctOrder = { BOOTS, LEGGINGS, CHESTPLATE, HELMET };
		private String name;
		private int index;
		private int rIndex;
		
		ArmorType(String name, int index, int rIndex){
			this.name = name;
			this.index = index;
			this.rIndex = rIndex;
		}
		
		public String getName() {
			return name;
		}
		
		public int getIndex() {
			return index;
		}
		
		public int getRIndex() {
			return rIndex;
		}
		
		public Material getSuitArmor(Suit suit){
			switch(this){
				case BOOTS:
					return suit.getBoots();
				case CHESTPLATE:
					return suit.getChestplate();
				case HELMET:
					return suit.getHelmet();
				case LEGGINGS:
					return suit.getLeggings();
			}
			return null;
		}
		
		public static ArmorType getByIndex(int index){
			for(ArmorType type : ArmorType.values()){
				if(type.getIndex() == index){
					return type;
				}
			}
			return null;
		}
		
		public static ArmorType[] getCorrectOrder() {
			return correctOrder;
		}
	}
}
