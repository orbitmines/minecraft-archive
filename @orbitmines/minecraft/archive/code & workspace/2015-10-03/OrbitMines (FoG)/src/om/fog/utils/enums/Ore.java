package om.fog.utils.enums;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public enum Ore {

	COPPER("§cCopper", "§c", 1, 10, 1),
	COBALT("§9Cobalt", "§9", 4, 15, 2),
	STRONTIUM("§eStrontium", "§e", 11, 25, 3),
	AMETHYST("§5Amethyst", "§5", 5, 200, 4),
	URANIUM("§2Uranium", "§2", 2, 350, 5),
	FRANCIUM("§6Fancium", "§6", 14, 7000, 6),
	IRIDIUM("§7Iridium", "§7", 7, 1000, 7);
	
	private String name;
	private String color;
	private int durability;
	private int sellPrice;
	private int slot;
	private ItemStack item;
	
	Ore(String name, String color, int durability, int sellPrice, int slot){
		this.name = name;
		this.color = color;
		this.durability = durability;
		this.sellPrice = sellPrice;
		this.slot = slot;
		
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
	
	public ItemStack getItem(int amount) {
		ItemStack item = new ItemStack(this.item);
		item.setAmount(amount);
		return item;
	}
	
	public static boolean canCreateAmethyst(Player p){
		int copper = 0;
		int cobalt = 0;
		
		for(ItemStack item : p.getInventory().getContents()){
			if(item.getType() == Material.INK_SACK){
				if(item.getDurability() == COPPER.getDurability()){
					copper += item.getAmount();
				}
				else if(item.getDurability() == COBALT.getDurability()){
					cobalt += item.getAmount();
				}
				else{}
				
				if(copper >= 10 && cobalt >= 5) return true;
			}
		}
		
		return false;
	}
	
	public static boolean canCreateUranium(Player p){
		int cobalt = 0;
		int strontium = 0;
		
		for(ItemStack item : p.getInventory().getContents()){
			if(item.getType() == Material.INK_SACK){
				if(item.getDurability() == COBALT.getDurability()){
					cobalt += item.getAmount();
				}
				else if(item.getDurability() == STRONTIUM.getDurability()){
					strontium += item.getAmount();
				}
				else{}
				
				if(cobalt >= 5 && strontium >= 10) return true;
			}
		}
		
		return false;
	}
	
	public static boolean canCreateFrancium(Player p){
		int amethyst = 0;
		int uranium = 0;
		int iridium = 0;
		
		for(ItemStack item : p.getInventory().getContents()){
			if(item.getType() == Material.INK_SACK){
				if(item.getDurability() == AMETHYST.getDurability()){
					amethyst += item.getAmount();
				}
				else if(item.getDurability() == URANIUM.getDurability()){
					uranium += item.getAmount();
				}
				else if(item.getDurability() == IRIDIUM.getDurability()){
					iridium += item.getAmount();
				}
				else{}
				
				if(amethyst >= 10 && uranium >= 10 && iridium >= 1) return true;
			}
		}
		
		return false;
	}
}
