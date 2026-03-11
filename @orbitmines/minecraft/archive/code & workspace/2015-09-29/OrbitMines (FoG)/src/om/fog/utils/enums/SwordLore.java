package om.fog.utils.enums;

import java.util.ArrayList;
import java.util.List;

import om.fog.utils.FoGUtils;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public enum SwordLore {
	
	DAMAGE("Damage", 5),
	FIRE_DAMAGE("Blaze", 3),
	STRENGTH("Wrath", 2),
	BLINDNESS("Impact", 3),
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
						return Rarity.UNCOMMON;
					case 2:
						return Rarity.RARE;
					case 3:
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
	
	public static SwordLore[] getCorrectOrder() {
		return correctOrder;
	}
}
