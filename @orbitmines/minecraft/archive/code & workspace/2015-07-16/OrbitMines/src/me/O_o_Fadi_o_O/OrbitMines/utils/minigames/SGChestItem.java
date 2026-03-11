package me.O_o_Fadi_o_O.OrbitMines.utils.minigames;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SGChestItem {

	private Material material;
	private int amount;
	private int maxamount;
	private String displayname;
	private int durability;
	private double percentage;
	
	public SGChestItem(Material material, int amount, int maxamount, String displayname, int durability, double percentage){
		this.material = material;
		this.amount = amount;
		this.maxamount = maxamount;
		this.displayname = displayname;
		this.durability = durability;
		this.percentage = percentage;
	}

	public Material getMaterial() {
		return material;
	}

	public int getMaxAmount() {
		return maxamount;
	}
	public String getDisplayName() {
		return displayname;
	}

	public short getDurability() {
		return (short) durability;
	}

	public double getPercentage() {
		return percentage;
	}
	
	public int getAmount(){
		if(amount == maxamount){
			return amount;
		}
		return new Random().nextInt(getMaxAmount()) +1;
	}
	
	public ItemStack getItemStack(){
		ItemStack item = new ItemStack(getMaterial(), getAmount());
		item.setDurability(getDurability());
		if(getDisplayName() != null){
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(getDisplayName());
			item.setItemMeta(meta);
		}
		return item;
	}
}
