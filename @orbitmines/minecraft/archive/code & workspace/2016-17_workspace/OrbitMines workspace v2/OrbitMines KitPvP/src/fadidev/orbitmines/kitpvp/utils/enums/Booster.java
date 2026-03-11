package fadidev.orbitmines.kitpvp.utils.enums;

import fadidev.orbitmines.api.handlers.Messages;
import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.utils.enums.ranks.VIPRank;
import org.bukkit.Material;

public enum Booster {
	
	IRON_BOOSTER("§7§lIron VIP Booster", 1.25, VIPRank.IRON_VIP, Material.IRON_INGOT),
	GOLD_BOOSTER("§6§lGold VIP Booster", 1.50, VIPRank.GOLD_VIP, Material.GOLD_INGOT),
	DIAMOND_BOOSTER("§b§lDiamond VIP Booster", 2.00, VIPRank.DIAMOND_VIP, Material.DIAMOND),
	EMERALD_BOOSTER("§a§lEmerald VIP Booster", 2.50, VIPRank.EMERALD_VIP, Material.EMERALD);
	
	private String name;
	private double multiplier;
	private VIPRank viprank;
	private Material material;
	
	Booster(String name, double multiplier, VIPRank viprank, Material material){
		this.name = name;
		this.multiplier = multiplier;
		this.viprank = viprank;
		this.material = material;
	}
	
	public String getName(){
		return name;
	}
	
	public int getPrice(){
		return 250;
	}
	
	public double getMultiplier(){
		return multiplier;
	}
	
	public boolean hasPerms(OMPlayer omp){
		return omp.hasPerms(getVIPRank());
	}
	
	public VIPRank getVIPRank(){
		return viprank;
	}
	
	public Material getMaterial(){
		return material;
	}
	
	public String getPriceName(OMPlayer omp){
		if(hasPerms(omp)) return "§c" + Messages.WORD_PRICE.get(omp) + ": §b" + getPrice() + " VIP Points";
		return "§c" + Messages.WORD_REQUIRED.get(omp) + ": " + getVIPRank().getRankString();
	}
}
