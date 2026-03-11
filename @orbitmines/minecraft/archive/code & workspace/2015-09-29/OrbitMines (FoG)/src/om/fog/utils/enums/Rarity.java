package om.fog.utils.enums;

public enum Rarity {
	
	COMMON("§a"),
	UNCOMMON("§b"),
	RARE("§6"),
	LEGENDARY("§c");
	
	private String color;
	
	Rarity(String color){
		this.color = color;
	}
	
	public String getColor() {
		return color;
	}
}
