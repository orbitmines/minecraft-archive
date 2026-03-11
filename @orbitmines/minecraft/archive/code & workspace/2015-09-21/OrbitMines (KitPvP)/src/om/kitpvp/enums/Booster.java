package om.kitpvp.enums;

import me.O_o_Fadi_o_O.OrbitMines.utils.OMPlayer;
import me.O_o_Fadi_o_O.OrbitMines.utils.kitpvp.VIPRank;

import org.bukkit.Material;

public enum Booster {
	
	IRON_BOOSTER,
	GOLD_BOOSTER,
	DIAMOND_BOOSTER,
	EMERALD_BOOSTER;
	
	public String getName(){
		switch(this){
			case DIAMOND_BOOSTER:
				return "§b§lDiamond VIP Booster";
			case EMERALD_BOOSTER:
				return "§a§lEmerald VIP Booster";
			case GOLD_BOOSTER:
				return "§6§lGold VIP Booster";
			case IRON_BOOSTER:
				return "§7§lIron VIP Booster";
			default:
				return null;
		}
	}
	
	public int getPrice(){
		return 250;
	}
	
	public double getMultiplier(){
		switch(this){
			case DIAMOND_BOOSTER:
				return 2.00;
			case EMERALD_BOOSTER:
				return 2.50;
			case GOLD_BOOSTER:
				return 1.50;
			case IRON_BOOSTER:
				return 1.25;
			default:
				return 1.00;
		}
	}
	
	public boolean hasPerms(OMPlayer omp){
		switch(this){
			case DIAMOND_BOOSTER:
				return omp.hasPerms(VIPRank.Diamond_VIP);
			case EMERALD_BOOSTER:
				return omp.hasPerms(VIPRank.Emerald_VIP);
			case GOLD_BOOSTER:
				return omp.hasPerms(VIPRank.Gold_VIP);
			case IRON_BOOSTER:
				return omp.hasPerms(VIPRank.Iron_VIP);
			default:
				return false;
		}
	}
	
	public VIPRank getVIPRank(){
		switch(this){
			case DIAMOND_BOOSTER:
				return VIPRank.Diamond_VIP;
			case EMERALD_BOOSTER:
				return VIPRank.Emerald_VIP;
			case GOLD_BOOSTER:
				return VIPRank.Gold_VIP;
			case IRON_BOOSTER:
				return VIPRank.Iron_VIP;
			default:
				return null;
		}
	}
	
	public Material getMaterial(){
		switch(this){
			case DIAMOND_BOOSTER:
				return Material.DIAMOND;
			case EMERALD_BOOSTER:
				return Material.EMERALD;
			case GOLD_BOOSTER:
				return Material.GOLD_INGOT;
			case IRON_BOOSTER:
				return Material.IRON_INGOT;
			default:
				return null;
		}
	}
	
	public String getPriceName(OMPlayer omp){
		if(hasPerms(omp)){
			return "§cPrice: §b" + getPrice() + " VIP Points";
		}
		return "§cRequired: " + getVIPRank().getRankString();
	}
}
