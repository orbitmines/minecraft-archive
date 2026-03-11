package om.fog.handlers;

import java.util.List;

import om.fog.FoG;
import om.fog.utils.enums.Faction;

import org.bukkit.Location;

public class FoGMap {

	private FoG fog;
	private Faction faction;
	private String name;
	private int x1;
	private int z1;
	private int x2;
	private int z2;
	private List<Portal> portals;
	private int players;
	
	public FoGMap(Faction faction, String name, int x1, int z1, List<Portal> portals){
		this.fog = FoG.getInstance();
		this.faction = faction;
		this.name = name;
		this.x1 = x1;
		this.z1 = z1;
		this.x2 = x1 + 499;
		this.z2 = z1 + 499;
		this.portals = portals;
		this.players = 0;
		
		fog.getMaps().add(this);
	}
	
	public Faction getFaction() {
		return faction;
	}
	
	public String getName() {
		return name;
	}
	
	public List<Portal> getPortals() {
		return portals;
	}
	
	public int getPlayers() {
		return players;
	}
	public void setPlayers(int players) {
		this.players = players;
	}
	
	public boolean inMap(Location l){
		int x = l.getBlockX();
		int z = l.getBlockZ();
		
		return x > x1 && x < x2 && z > z1 && z < z2;
	}
}
