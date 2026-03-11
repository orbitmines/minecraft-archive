package om.fog.utils.enums;

import om.fog.handlers.players.FoGPlayer;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public enum Ore {

	COPPER("§cCopper", "§c", 1, 10, 1, 2, Color.RED, 4, 14, false, 5),
	COBALT("§9Cobalt", "§9", 4, 15, 2, 4, Color.BLUE, 8, 11, false, 10),
	STRONTIUM("§eStrontium", "§e", 11, 25, 3, 6, Color.YELLOW, 14, 4, false, 15),
	AMETHYST("§5Amethyst", "§5", 5, 200, 4, 21, Color.PURPLE, 30, 10, true, -1, new CraftOre(COPPER, 10), new CraftOre(COBALT, 5)),
	URANIUM("§2Uranium", "§2", 2, 350, 5, 23, Color.GREEN, 40, 13, true, -1, new CraftOre(COBALT, 5), new CraftOre(STRONTIUM, 10)),
	IRIDIUM("§7Iridium", "§7", 7, 1000, 7, 38, Color.GRAY, 50, 8, false, 50),
	FRANCIUM("§6Fancium", "§6", 14, 7000, 6, 40, Color.ORANGE, 60, 1, true, -1, new CraftOre(AMETHYST, 10), new CraftOre(URANIUM, 10), new CraftOre(IRIDIUM, 1));
	
	private String name;
	private String color;
	private int durability;
	private int sellPrice;
	private int slot;
	private int craftSlot;
	private ItemStack item;
	private Color bColor;
	private int pickupDelay;
	private int blockData;
	private boolean craftAble;
	private int exp;
	private CraftOre[] craftOres;
	
	Ore(String name, String color, int durability, int sellPrice, int slot, int craftSlot, Color bColor, int pickupDelay, int blockData, boolean craftAble, int exp, CraftOre... craftOres){
		this.name = name;
		this.color = color;
		this.durability = durability;
		this.sellPrice = sellPrice;
		this.slot = slot;
		this.craftSlot = craftSlot;
		this.bColor = bColor;
		this.pickupDelay = pickupDelay;
		this.blockData = blockData;
		this.craftAble = craftAble;
		this.exp = exp;
		this.craftOres = craftOres;
		
		ItemStack item = new ItemStack(Material.INK_SACK);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		item.setItemMeta(meta);
		item.setDurability((short) durability);
		this.item = item;
	}
	
	public String getName() {
		return name;
	}
	
	public String getColor() {
		return color;
	}
	
	public short getDurability() {
		return (short) durability;
	}
	
	public int getSellPrice() {
		return sellPrice;
	}
	
	public int getSlot() {
		return slot;
	}
	
	public int getCraftSlot() {
		return craftSlot;
	}
	
	public boolean isCraftAble() {
		return craftAble;
	}
	
	public int getExp() {
		return exp;
	}
	
	public CraftOre[] getCraftOres() {
		return craftOres;
	}
	
	public Color getBColor() {
		return bColor;
	}
	
	public double getPickupDelay(Suit suit, ItemStack inHand) {
		int level = ToolLore.EFFICIENCY.getLevel(inHand);
		if(level != -1){
			return pickupDelay / (suit.getExtraToolSpeed() + 0.2 * level);
		}
		return pickupDelay / suit.getExtraToolSpeed();
	}
	
	public int getBlockData() {
		return blockData;
	}
	
	public ItemStack getItem(int amount) {
		ItemStack item = new ItemStack(this.item);
		item.setAmount(amount);
		return item;
	}
	
	public boolean canCraft(FoGPlayer omp){
		for(CraftOre ore : getCraftOres()){
			if(ore.getAmount() > omp.getAmount(Material.INK_SACK, ore.getOre().getDurability())){
				return false;
			}
		}
		return true;
	}
	
	public void craft(FoGPlayer omp){
		omp.getPlayer().playSound(omp.getPlayer().getLocation(), Sound.ANVIL_USE, 5, 1);
		for(CraftOre ore : getCraftOres()){
			omp.removeItems(Material.INK_SACK, ore.getOre().getDurability(), ore.getAmount());
		}
		omp.getPlayer().getInventory().addItem(getItem(1));
		
		if(this == AMETHYST && omp.isInTutorial() && omp.getTutorial().getStage() == 11){
			omp.getTutorial().toNextStage();
		}
	}
	
	public static Ore getByBlockData(byte blockData){
		for(Ore ore : Ore.values()){
			if(ore.getBlockData() == blockData){
				return ore;
			}
		}
		return null;
	}
	
	public static class CraftOre {
		
		private Ore ore;
		private int amount;
		
		public CraftOre(Ore ore, int amount){
			this.ore = ore;
			this.amount = amount;
		}
		
		public Ore getOre() {
			return ore;
		}
		
		public int getAmount() {
			return amount;
		}
	}
}
