package om.fog.utils.enums;

import om.fog.FoG;

import org.bukkit.Location;

public enum Faction {

	ALPHA("§eAlpha", "§e", null),
	BETA("§9Beta", "§9", new Location(FoG.getInstance().getGalaxyWorld(), 245.5, 18, 3830.5, -180, 0)),
	OMEGA("§cOmega", "§c", null);
	
	private String name;
	private String color;
	private Location baseLocation;
	private int playerAmount;
	
	Faction(String name, String color, Location baseLocation){
		this.name = name;
		this.color = color;
		this.baseLocation = baseLocation;
		this.playerAmount = 0;
	}
	
	public String getName() {
		return name;
	}
	
	public String getColor() {
		return color;
	}
	
	public Location getBaseLocation() {
		return baseLocation;
	}
	
	public int getPlayerAmount() {
		return playerAmount;
	}
	public void setPlayerAmount(int playerAmount) {
		this.playerAmount = playerAmount;
	}
}
