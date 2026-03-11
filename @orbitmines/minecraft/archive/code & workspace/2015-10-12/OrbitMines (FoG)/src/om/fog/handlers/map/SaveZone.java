package om.fog.handlers.map;

import java.util.ArrayList;
import java.util.List;

import om.fog.handlers.players.FoGPlayer;

import org.bukkit.Location;

public class SaveZone {

	private FoGMap map;
	private int x1;
	private int z1;
	private int x2;
	private int z2;
	private List<FoGPlayer> players;
	
	public SaveZone(FoGMap map, int x1, int z1, int x2, int z2){
		this.map = map;
		this.map.getSaveZones().add(this);
		this.x1 = x1;
		this.z1 = z1;
		this.x2 = x2;
		this.z2 = z2;
		this.players = new ArrayList<FoGPlayer>();
	}
	
	public FoGMap getMap() {
		return map;
	}
	
	public List<FoGPlayer> getPlayers() {
		return players;
	}
	
	public boolean inSaveZone(Location l){
		int x = l.getBlockX();
		int z = l.getBlockZ();
		
		return x > x1 && x < x2 && z > z1 && z < z2;
	}
}
