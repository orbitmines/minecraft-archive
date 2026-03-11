package fadidev.orbitmines.skyblock.handlers;

import org.bukkit.Material;

public class ItemData {

	private String displayName;
	private int amount;
	private Material material;
	private short durability;
	
	public ItemData(String displayName, int amount, Material material, int durability){
		this.displayName = displayName;
		this.amount = amount;
		this.material = material;
		this.durability = (short) durability;
	}

	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}

	public Material getMaterial() {
		return material;
	}
	public void setMaterial(Material material) {
		this.material = material;
	}

	public short getDurability() {
		return durability;
	}
	public void setDurability(short durability) {
		this.durability = durability;
	}
}
