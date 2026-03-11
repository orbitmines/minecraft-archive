package om.fog.utils.enums;

public enum Rarity {
	
	COMMON("§a", "§aCommon", 0.55D),
	UNCOMMON("§b", "§bUncommon", 0.90D),
	RARE("§6", "§6Rare", 0.99D),
	LEGENDARY("§c", "§cLegendary", 1.0D);
	
	private String color;
	private String name;
	private double chance;
	
	Rarity(String color, String name, double chance){
		this.color = color;
		this.name = name;
		this.chance = chance;
	}
	
	public String getColor() {
		return color;
	}
	
	public String getName() {
		return name;
	}
	
	public double getChance() {
		return chance;
	}
	
	public static Rarity random(){
		double r = Math.random();
		if(r < Rarity.COMMON.getChance()){
			return Rarity.COMMON;
		}
		else if(r < Rarity.UNCOMMON.getChance()){
			return Rarity.UNCOMMON;
		}
		else if(r < Rarity.RARE.getChance()){
			return Rarity.RARE;
		}
		else{
			return Rarity.LEGENDARY;
		}
	}
}
