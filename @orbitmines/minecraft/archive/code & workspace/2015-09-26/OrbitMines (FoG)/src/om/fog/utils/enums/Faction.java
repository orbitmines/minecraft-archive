package om.fog.utils.enums;

public enum Faction {

	ALPHA("§eAlpha", "§e"),
	BETA("§9Beta", "§9"),
	OMEGA("§cOmega", "§c");
	
	private String name;
	private String color;
	private int playerAmount;
	
	Faction(String name, String color){
		this.name = name;
		this.color = color;
		this.playerAmount = 0;
	}
	
	public String getName() {
		return name;
	}
	
	public String getColor() {
		return color;
	}
	
	public int getPlayerAmount() {
		return playerAmount;
	}
	public void setPlayerAmount(int playerAmount) {
		this.playerAmount = playerAmount;
	}
}
