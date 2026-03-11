package om.kitpvp.enums;

import org.bukkit.Material;

public enum Mastery {
	
	MELEE,
	MELEE_PROTECTION,
	RANGE,
	RANGE_PROTECTION;
	
	public String getName(){
		switch(this){
			case MELEE:
				return "§7§lMelee Damage";
			case MELEE_PROTECTION:
				return "§7§lMelee Protection";
			case RANGE:
				return "§7§lRange Damage";
			case RANGE_PROTECTION:
				return "§7§lRange Protection";
			default:
				return null;
		}
	}
	
	public String getEffectName(){
		switch(this){
			case MELEE:
				return "Melee Damage";
			case MELEE_PROTECTION:
				return "Melee Damage Taken";
			case RANGE:
				return "Range Damage";
			case RANGE_PROTECTION:
				return "Range Damage Taken";
			default:
				return null;
		}
	}
	
	public Material getMaterial(){
		switch(this){
			case MELEE:
				return Material.DIAMOND_SWORD;
			case MELEE_PROTECTION:
				return Material.IRON_CHESTPLATE;
			case RANGE:
				return Material.BOW;
			case RANGE_PROTECTION:
				return Material.ARROW;
			default:
				return null;
		}
	}
	
	public double getMultiplier(){
		switch(this){
			case MELEE:
				return 0.02;
			case MELEE_PROTECTION:
				return 0.02;
			case RANGE:
				return 0.04;
			case RANGE_PROTECTION:
				return 0.03;
			default:
				return 0.0;
		}
	}
	
	public String getColor(){
		switch(this){
			case MELEE:
				return "§a§l+";
			case MELEE_PROTECTION:
				return "§c§l-";
			case RANGE:
				return "§a§l+";
			case RANGE_PROTECTION:
				return "§c§l-";
			default:
				return null;
		}
	}
	public String getOpositeColor(){
		switch(this){
			case MELEE:
				return "§c§l-";
			case MELEE_PROTECTION:
				return "§a§l+";
			case RANGE:
				return "§c§l-";
			case RANGE_PROTECTION:
				return "§a§l+";
			default:
				return null;
		}
	}
}
