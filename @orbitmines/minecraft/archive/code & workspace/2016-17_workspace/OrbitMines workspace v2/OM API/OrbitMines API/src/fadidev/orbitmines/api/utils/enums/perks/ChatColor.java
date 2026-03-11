package fadidev.orbitmines.api.utils.enums.perks;

import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.handlers.Messages;
import fadidev.orbitmines.api.utils.enums.ranks.VIPRank;
import org.bukkit.Material;

public enum ChatColor {

	DARK_RED("§4", "§4Dark Red ChatColor", "DarkRed", 475, null, 0),
	LIGHT_GREEN("§a", "§aLight Green ChatColor", "LightGreen", 575, null, 10),
	DARK_GRAY("§8", "§8Dark Gray ChatColor", "DarkGray", 250, null, 8),
	RED("§c", "§cRed ChatColor", "Red", 650, null, 1),
	YELLOW("§e", "§eYellow ChatColor", "Yellow", -1, VIPRank.GOLD_VIP, 15),
	WHITE("§f", "§fWhite ChatColor", "White", 500, null, 11),
	LIGHT_BLUE("§b", "§bLight Blue ChatColor", "LightBlue", 700, null, 12),
	PINK("§d", "§dPink ChatColor", "Pink", 525, null, 9),
	BLUE("§9", "§9Blue ChatColor", "Blue", 475, null, 12),
	DARK_BLUE("§1", "§1Dark Blue ChatColor", "DarkBlue", 350, null, 4),
	GRAY("§7", "§7Gray ChatColor", "Gray", -1, null, 7),
	ORANGE("§6", "§6Orange ChatColor", "Orange", -1, VIPRank.EMERALD_VIP, 14),
	PURPLE("§5", "§5Purple ChatColor", "Purple", -1, VIPRank.IRON_VIP, 5),
	CYAN("§3", "§3Cyan ChatColor", "Cyan", -1, VIPRank.DIAMOND_VIP, 6),
	GREEN("§2", "§2Green ChatColor", "Green", 475, null, 2),
	BLACK("§0", "§0Black ChatColor", "Black", 200, null, 0);
	
	private String color;
	private String name;
	private String databaseName;
	private int price;
	private VIPRank viprank;
	private int durability;
	
	ChatColor(String color, String name, String databaseName, int price, VIPRank viprank, int durability){
		this.color = color;
		this.name = name;
		this.databaseName = databaseName;
		this.price = price;
		this.viprank = viprank;
		this.durability = durability;
	}
	
	public String getColor(){
		return color;
	}
		
	public String getName(){
		return name;
	}

	public String getDatabaseName(){
		return databaseName;
	}
	
	public int getPrice(){
		return price;
	}
	
	public String getPriceName(OMPlayer omp){
		if(getVIPRank() != null)
			return "§c" + Messages.WORD_REQUIRED.get(omp) + ": " + getVIPRank().getRankString() + " VIP";
		return "§c" + Messages.WORD_PRICE.get(omp) + ": §b" + getPrice() + " VIP Points";
	}
	
	public boolean hasChatColor(OMPlayer omp){
		if(this == GRAY){
			return true;
		}
		else if(getVIPRank() != null){
			return omp.hasPerms(getVIPRank());
		}
		else{
			return omp.hasChatColor(this);
		}
	}
	
	public VIPRank getVIPRank(){
		return viprank;
	}
	
	public Material getMaterial(){
		switch(this){
			case DARK_RED:
				return Material.REDSTONE;
			default:
				return Material.INK_SACK;
		}
	}
	
	public short getDurability(){
		return (short) durability;
	}
}

