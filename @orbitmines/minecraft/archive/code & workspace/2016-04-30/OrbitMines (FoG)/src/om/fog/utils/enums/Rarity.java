package om.fog.utils.enums;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public enum Rarity {
	
	COMMON("§a", "§aCommon", 0.55D, 2500),
	UNCOMMON("§b", "§bUncommon", 0.90D, 10000),
	RARE("§6", "§6Rare", 0.99D, 75000),
	LEGENDARY("§c", "§cLegendary", 1.0D, 200000);
	
	private String color;
	private String name;
	private double chance;
	private int price;
	
	Rarity(String color, String name, double chance, int price){
		this.color = color;
		this.name = name;
		this.chance = chance;
		this.price = price;
	}
	
	public String getColor() {
		return color;
	}
	
	public String getName() {
		return name;
	}
	
	public String getRawName() {
		return name.substring(2);
	}
	
	public double getChance() {
		return chance;
	}
	
	public int getPrice() {
		return price;
	}
	
	public ItemStack getRandomItem(){
		ItemStack item = new ItemStack(Material.BOOK);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(getName() + " Enchantment §7(Right Click)");
		item.setItemMeta(meta);
		return item;
	}
	
	public ItemStack randomEnchantment(){
		int r = new Random().nextInt(3);
		switch(r){
			case 0:
				return SwordLore.random(this);
			case 1:
				return ShieldLore.random(this);
			case 2:
				return ToolLore.random(this);
		}
		return null;
	}
	
	public static Rarity random(){
		double r = Math.random();
		if(r < Rarity.COMMON.getChance()){
			return Rarity.COMMON;
		}
		else if(r < Rarity.UNCOMMON.getChance()){
			return Rarity.UNCOMMON;
		}
		else if(r < Rarity.RARE.getChance()){
			return Rarity.RARE;
		}
		else{
			return Rarity.LEGENDARY;
		}
	}
	
	public static Rarity getByName(String name){
		for(Rarity rarity : Rarity.values()){
			if(rarity.getName().equals(name)){
				return rarity;
			}
		}
		return null;
	}
}
