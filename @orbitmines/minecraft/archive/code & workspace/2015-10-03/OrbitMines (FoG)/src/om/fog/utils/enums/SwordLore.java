package om.fog.utils.enums;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import om.fog.utils.FoGUtils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public enum SwordLore {
	
	DAMAGE("Damage", 7),
	FIRE_DAMAGE("Blaze", 3),
	STRENGTH("Wrath", 2),
	BLINDNESS("Impact", 5),
	SHIELD_BUSTER("Shield Buster", 3);
	
	private static SwordLore[] correctOrder = { DAMAGE, STRENGTH, SHIELD_BUSTER, FIRE_DAMAGE, BLINDNESS };
	private String name;
	private int maxLevel;
	
	SwordLore(String name, int maxLevel){
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
			case BLINDNESS:
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
						return Rarity.RARE;
				}
			case DAMAGE:
				switch(level){
					case 1:
						return Rarity.COMMON;
					case 2:
						return Rarity.COMMON;
					case 3:
						return Rarity.UNCOMMON;
					case 4:
						return Rarity.RARE;
					case 5:
						return Rarity.RARE;
					case 6:
						return Rarity.LEGENDARY;
					case 7:
						return Rarity.LEGENDARY;
				}
			case FIRE_DAMAGE:
				switch(level){
					case 1:
						return Rarity.UNCOMMON;
					case 2:
						return Rarity.UNCOMMON;
					case 3:
						return Rarity.RARE;
				}
			case SHIELD_BUSTER:
				switch(level){
					case 1:
						return Rarity.UNCOMMON;
					case 2:
						return Rarity.RARE;
					case 3:
						return Rarity.LEGENDARY;
				}
			case STRENGTH:
				switch(level){
					case 1:
						return Rarity.RARE;
					case 2:
						return Rarity.LEGENDARY;
				}
			default:
				break;
		}
		return null;
	}
	
	public int getLevel(ItemStack item){
		List<String> lore = item.getItemMeta().getLore();
		if(lore == null) return -1;
		
		for(String line : lore){
			if(line.length() > 2 && line.substring(2).startsWith(getName())){
				return FoGUtils.parseInteger(line.substring(3 + getName().length()));
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
		lore.add(getRarity(level).getName() + " Enchantment §7(Swords)");
		meta.setLore(lore);
		item.setItemMeta(meta);
		
		return item;
	}
	
	public static ItemStack random(){
		SwordLoreLevel sl = getLore(Rarity.random());
		return sl.getLore().getEnchantment(sl.getLevel());
	}
	
	private static SwordLoreLevel getLore(Rarity rarity){
		List<SwordLoreLevel> slList = new ArrayList<SwordLoreLevel>();
		for(SwordLore sl : SwordLore.values()){
			for(int i = 1; i <= sl.getMaxLevel(); i++){
				if(rarity == sl.getRarity(i)){
					slList.add(new SwordLoreLevel(sl, i));
				}
			}
		}
		return slList.get(new Random().nextInt(slList.size()));
	}
	
	public static SwordLoreLevel getEnchantment(ItemStack item){
		if(item != null && item.getItemMeta().getDisplayName() != null){
			String name = item.getItemMeta().getDisplayName();
			String[] nameParts = name.split(" ");
			SwordLore sl = getByName(nameParts[0].substring(4));
			
			if(sl != null){
				return new SwordLoreLevel(sl, FoGUtils.parseInteger(nameParts[1]));
			}
			else{
				sl = getByName(nameParts[0].substring(4) + " " + nameParts[1]);
				
				if(sl != null){
					return new SwordLoreLevel(sl, FoGUtils.parseInteger(nameParts[2]));
				}
			}
		}
		return null;
	}
	
	public static SwordLore getByName(String name){
		for(SwordLore sl : correctOrder){
			if(sl.getName().equals(name)){
				return sl;
			}
		}
		return null;
	}
	
	public static SwordLore[] getCorrectOrder() {
		return correctOrder;
	}
	
	public static class SwordLoreLevel {
		
		private SwordLore sl;
		private int level;
		
		public SwordLoreLevel(SwordLore sl, int level) {
			this.sl = sl;
			this.level = level;
		}
		
		public SwordLore getLore() {
			return sl;
		}
		
		public int getLevel() {
			return level;
		}
	}
}
