package om.fog.utils.enums;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public enum Ore {

	COPPER("§cCopper", 1, 10),
	COBALT("§9Cobalt", 4, 15),
	STRONTIUM("§eStrontium", 11, 25),
	AMETHYST("§5Amethyst", 5, 200),
	URANIUM("§2Uranium", 2, 350),
	FRANCIUM("§6Fancium", 14, 7000),
	IRIDIUM("§7Iridium", 7, 1000);
	
	private String name;
	private int durability;
	private int sellPrice;
	
	Ore(String name, int durability, int sellPrice){
		this.name = name;
		this.durability = durability;
		this.sellPrice = sellPrice;
	}
	
	public String getName() {
		return name;
	}
	
	public short getDurability() {
		return (short) durability;
	}
	
	public int getSellPrice() {
		return sellPrice;
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
