package om.fog.utils.enums;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import om.fog.utils.FoGUtils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public enum ToolLore {
	
	EFFICIENCY("Efficiency", 4),
	LUCK("Luck", 8);
	
	private static ToolLore[] correctOrder = { EFFICIENCY, LUCK };
	private String name;
	private int maxLevel;
	
	ToolLore(String name, int maxLevel){
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
			case EFFICIENCY:
				switch(level){
					case 1:
						return Rarity.COMMON;
					case 2:
						return Rarity.COMMON;
					case 3:
						return Rarity.UNCOMMON;
					case 4:
						return Rarity.RARE;
				}
			case LUCK:
				switch(level){
					case 1:
						return Rarity.COMMON;
					case 2:
						return Rarity.UNCOMMON;
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
					case 8:
						return Rarity.LEGENDARY;
				}
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
		lore.add(getRarity(level).getName() + " Enchantment §7(Tools)");
		meta.setLore(lore);
		item.setItemMeta(meta);
		
		return item;
	}
	
	public static ItemStack random(){
		ToolLoreLevel sl = getLore(Rarity.random());
		return sl.getLore().getEnchantment(sl.getLevel());
	}
	
	public static ItemStack random(Rarity rarity){
		ToolLoreLevel sl = getLore(rarity);
		return sl.getLore().getEnchantment(sl.getLevel());
	}
	
	private static ToolLoreLevel getLore(Rarity rarity){
		List<ToolLoreLevel> slList = new ArrayList<ToolLoreLevel>();
		for(ToolLore sl : ToolLore.values()){
			for(int i = 1; i <= sl.getMaxLevel(); i++){
				if(rarity == sl.getRarity(i)){
					slList.add(new ToolLoreLevel(sl, i));
				}
			}
		}
		return slList.get(new Random().nextInt(slList.size()));
	}
	
	public static ToolLoreLevel getEnchantment(ItemStack item){
		if(item != null && item.getItemMeta().getDisplayName() != null){
			String name = item.getItemMeta().getDisplayName();
			String[] nameParts = name.split(" ");
			ToolLore sl = getByName(nameParts[0].substring(4));
			
			if(sl != null){
				return new ToolLoreLevel(sl, FoGUtils.parseInteger(nameParts[1]));
			}
			else{
				sl = getByName(nameParts[0].substring(4) + " " + nameParts[1]);
				
				if(sl != null){
					return new ToolLoreLevel(sl, FoGUtils.parseInteger(nameParts[2]));
				}
			}
		}
		return null;
	}
	
	public static ToolLore getByName(String name){
		for(ToolLore sl : correctOrder){
			if(sl.getName().equalsIgnoreCase(name)){
				return sl;
			}
		}
		return null;
	}
	
	public static ToolLore[] getCorrectOrder() {
		return correctOrder;
	}
	
	public static class ToolLoreLevel {
		
		private ToolLore sl;
		private int level;
		
		public ToolLoreLevel(ToolLore sl, int level) {
			this.sl = sl;
			this.level = level;
		}
		
		public ToolLore getLore() {
			return sl;
		}
		
		public int getLevel() {
			return level;
		}
	}
}
