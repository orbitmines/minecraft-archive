package om.fog.utils.enums;

import om.api.utils.ItemUtils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum Suit {

	TRAINING_SUIT(-1, "Training Suit", "I.P.U.", "Mymari", 10.0, 1.00, 1.00, 1.00, Material.WOOD_SWORD, Material.GOLD_PICKAXE, Material.LEATHER_HELMET, Material.LEATHER_CHESTPLATE, Material.LEATHER_LEGGINGS, Material.LEATHER_BOOTS),
	DEFENDER_SUIT(100000, "Defender Suit", "E.R.P.U.", "Prophysa", 24.0, 1.15, 1.20, 2.00, Material.GOLD_SWORD, Material.DIAMOND_PICKAXE, Material.LEATHER_HELMET, Material.GOLD_CHESTPLATE, Material.GOLD_LEGGINGS, Material.LEATHER_BOOTS),
	SHIELD_SUIT(325000, "Shield Suit", "I.U.P.U.", "Euryco", 50.0, 1.30, 0.70, 0.90, Material.STONE_SWORD, Material.WOOD_PICKAXE, Material.LEATHER_HELMET, Material.CHAINMAIL_CHESTPLATE, Material.CHAINMAIL_LEGGINGS, Material.CHAINMAIL_BOOTS),
	SPEEDSTER_SUIT(600000, "Speedster Suit", "Q.M.A.P.U.", "Paratar", 32.0, 1.50, 1.50, 1.40, Material.IRON_SWORD, Material.STONE_PICKAXE, Material.LEATHER_HELMET, Material.LEATHER_CHESTPLATE, Material.LEATHER_LEGGINGS, Material.DIAMOND_BOOTS),
	EXO_SUIT(1500000, "Exo Suit", "U.D.P.U.", "Disrapter", 40.0, 1.75, 1.35, 1.50, Material.DIAMOND_SWORD, Material.IRON_PICKAXE, Material.LEATHER_HELMET, Material.IRON_CHESTPLATE, Material.GOLD_LEGGINGS, Material.LEATHER_BOOTS);
	
	private int price;
	private String alphaName;
	private String betaName;
	private String omegaName;
	private double baseHealth;
	private double extraDamage;
	private double extraSpeed;//TODO
	private double extraToolSpeed;
	private Material sword;
	private Material tool;
	private Material helmet;
	private Material chestplate;
	private Material leggings;
	private Material boots;
	
	Suit(int price, String betaName, String alphaName, String omegaName, double baseHealth, double extraDamage, double extraSpeed, double extraToolSpeed, Material sword, Material tool, Material helmet, Material chestplate, Material leggings, Material boots){
		this.price = price;
		this.alphaName = alphaName;
		this.betaName = betaName;
		this.omegaName = omegaName;
		this.baseHealth = baseHealth;
		this.extraDamage = extraDamage;
		this.extraSpeed = extraSpeed;
		this.extraToolSpeed = extraToolSpeed;
		this.sword = sword;
		this.tool = tool;
		this.helmet = helmet;
		this.chestplate = chestplate;
		this.leggings = leggings;
		this.boots = boots;
	}
	
	public int getPrice() {
		return price;
	}
	
	public String getName(Faction faction) {
		switch(faction){
			case ALPHA:
				return alphaName;
			case BETA:
				return betaName;
			case OMEGA:
				return omegaName;
			default:
				return null;
		}
	}
	
	public double getBaseHealth() {
		return baseHealth;
	}
	
	public double getExtraDamage() {
		return extraDamage;
	}
	
	public double getExtraSpeed() {
		return extraSpeed;
	}
	
	public double getExtraToolSpeed() {
		return extraToolSpeed;
	}
	
	public Material getSword() {
		return sword;
	}
	
	public Material getTool() {
		return tool;
	}
	
	public Material getHelmet() {
		return helmet;
	}
	
	public Material getChestplate() {
		return chestplate;
	}
	
	public Material getLeggings() {
		return leggings;
	}
	
	public Material getBoots() {
		return boots;
	}
	
	public ItemStack[] getArmorContents(Faction faction){
		ItemStack[] armorContents = new ItemStack[4];
		armorContents[0] = ItemUtils.addColor(new ItemStack(boots), faction.getBColor());
		armorContents[1] = ItemUtils.addColor(new ItemStack(leggings), faction.getBColor());
		armorContents[2] = ItemUtils.addColor(new ItemStack(chestplate), faction.getBColor());
		armorContents[3] = ItemUtils.addColor(new ItemStack(helmet), faction.getBColor());
		
		return armorContents;
	}
}
