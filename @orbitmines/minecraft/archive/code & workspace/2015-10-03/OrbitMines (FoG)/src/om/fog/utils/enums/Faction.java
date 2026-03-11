package om.fog.utils.enums;

import om.fog.FoG;

import org.bukkit.Color;
import org.bukkit.Location;

public enum Faction {

	ALPHA("§eAlpha", "§e", null, Color.YELLOW),
	BETA("§9Beta", "§9", new Location(FoG.getInstance().getGalaxyWorld(), 245.5, 18, 3830.5, -180, 0), Color.BLUE),
	OMEGA("§cOmega", "§c", null, Color.RED);
	
	private String name;
	private String color;
	private Location baseLocation;
	private Color bColor;
	private int playerAmount;
	
	Faction(String name, String color, Location baseLocation, Color bColor){
		this.name = name;
		this.color = color;
		this.baseLocation = baseLocation;
		this.bColor = bColor;
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
	
	public Color getBColor() {
		return bColor;
	}
	
	public int getPlayerAmount() {
		return playerAmount;
	}
	public void setPlayerAmount(int playerAmount) {
		this.playerAmount = playerAmount;
	}
}
