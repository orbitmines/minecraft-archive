package om.api.utils.enums.ranks;

import org.bukkit.Material;

public enum VIPRank {

	User,
	Iron_VIP,
	Gold_VIP,
	Diamond_VIP,
	Emerald_VIP;
	
	public Material getMaterial(){
		switch(this){
			case Diamond_VIP:
				return Material.DIAMOND;
			case Emerald_VIP:
				return Material.EMERALD;
			case Gold_VIP:
				return Material.GOLD_INGOT;
			case Iron_VIP:
				return Material.IRON_INGOT;
			default:
				return null;
		}
	}
	
	public int getMonthlyBonus(){
			switch(this){
			case Diamond_VIP:
				return 1250;
			case Emerald_VIP:
				return 2500;
			case Gold_VIP:
				return 500;
			case Iron_VIP:
				return 250;
			default:
				return 0;
		}
	}
	
	public String getChatPrefix(){
		switch(this){
			case Diamond_VIP:
				return "§9§lDiamond §9";
			case Emerald_VIP:
				return "§a§lEmerald §a";
			case Gold_VIP:
				return "§6§lGold §6";
			case Iron_VIP:
				return "§7§lIron §7";
			default:
				return "§8";
		}
	}
	
	public String getColor(){
		switch(this){
			case Diamond_VIP:
				return "§9";
			case Emerald_VIP:
				return "§a";
			case Gold_VIP:
				return "§6";
			case Iron_VIP:
				return "§7";
			default:
				return "§8";
		}
	}
	
	public String getScoreboardPrefix(){
		switch(this){
			case Diamond_VIP:
				return "§9§lDiamond §f";
			case Emerald_VIP:
				return "§a§lEmerald §f";
			case Gold_VIP:
				return "§6§lGold §f";
			case Iron_VIP:
				return "§7§lIron §f";
			default:
				return "§f";
		}
	}
	
	public String getRankString(){
		switch(this){
			case Diamond_VIP:
				return "§9§lDiamond";
			case Emerald_VIP:
				return "§a§lEmerald";
			case Gold_VIP:
				return "§6§lGold";
			case Iron_VIP:
				return "§7§lIron";
			default:
				return "§fNo Rank";
		}
	}
}

