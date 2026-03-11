package om.fog.utils.enums;

import java.util.HashMap;
import java.util.Map;

import om.api.handlers.Database;
import om.api.handlers.Hologram;
import om.api.handlers.NPCArmorStand;
import om.fog.FoG;

import org.bukkit.Color;
import org.bukkit.Location;

public enum Faction {

	ALPHA("§eAlpha", "§e", new Location(FoG.getInstance().getGalaxyWorld(), -3310.5, 10, 1881.5, 0, 0), Color.YELLOW, 4),
	BETA("§9Beta", "§9", new Location(FoG.getInstance().getGalaxyWorld(), 245.5, 18, 3830.5, -180, 0), Color.BLUE, 3),
	OMEGA("§cOmega", "§c", new Location(FoG.getInstance().getGalaxyWorld(), 3381.5, 12, 1863.5, 90, 0), Color.RED, 14);
	
	private String name;
	private String color;
	private Location baseLocation;
	private Color bColor;
	private int durability;
	private int playerAmount;
	private Hologram populationHologram;
	private Map<Suit, NPCArmorStand> suitNPCs;
	
	Faction(String name, String color, Location baseLocation, Color bColor, int durability){
		this.name = name;
		this.color = color;
		this.baseLocation = baseLocation;
		this.bColor = bColor;
		this.durability = durability;
		this.suitNPCs = new HashMap<Suit, NPCArmorStand>();
		
		if(Database.get().containsPath("FoG-FactionPlayers", "faction", "faction", toString())){
			this.playerAmount = Database.get().getInt("FoG-FactionPlayers", "players", "faction", toString());
		}
		else{
			Database.get().insert("FoG-FactionPlayers", "faction`, `players", toString() + "', '" + 0);
			this.playerAmount = 0;
		}
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
	
	public short getDurability() {
		return (short) durability;
	}
	
	public int getPlayerAmount() {
		return playerAmount;
	}
	public void setPlayerAmount(int playerAmount) {
		this.playerAmount = playerAmount;
		
		Database.get().update("FoG-FactionPlayers", "players", "" + playerAmount, "faction", toString());
	}
	
	public Hologram getPopulationHologram() {
		return populationHologram;
	}
	public void setPopulationHologram(Hologram populationHologram) {
		this.populationHologram = populationHologram;
	}
	
	public Map<Suit, NPCArmorStand> getSuitNPCs() {
		return suitNPCs;
	}
	
	public void updatePopulationHologram() {
		Hologram h = getPopulationHologram();
		h.setLine(2, "§7Total population: " + getColor() + getPlayerAmount() + "§7.");
		h.create();
	}
}
