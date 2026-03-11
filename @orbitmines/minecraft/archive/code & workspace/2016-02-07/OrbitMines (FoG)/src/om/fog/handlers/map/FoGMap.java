package om.fog.handlers.map;

import java.util.ArrayList;
import java.util.List;

import om.fog.FoG;
import om.fog.handlers.players.FoGPlayer;
import om.fog.utils.enums.Faction;
import om.fog.utils.enums.Ore;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

public class FoGMap {

	private FoG fog;
	private Faction faction;
	private String name;
	private int x1;
	private int z1;
	private int x2;
	private int z2;
	private List<SaveZone> saveZones;
	private List<Portal> portals;
	private List<SpawnArea> spawnAreas;
	private int players;
	
	public FoGMap(Faction faction, String name, int x1, int z1, List<Portal> portals){
		this.fog = FoG.getInstance();
		this.faction = faction;
		this.name = name;
		this.x1 = x1;
		this.z1 = z1;
		this.x2 = x1 + 499;
		this.z2 = z1 + 499;
		this.saveZones = new ArrayList<SaveZone>();
		this.portals = portals;
		this.spawnAreas = new ArrayList<SpawnArea>();
		this.players = 0;
		
		fog.getMaps().add(this);
	}
	
	public Faction getFaction() {
		return faction;
	}
	
	public String getName() {
		return name;
	}
	
	public List<SaveZone> getSaveZones() {
		return saveZones;
	}
	
	public List<Portal> getPortals() {
		return portals;
	}
	
	public List<SpawnArea> getSpawnAreas() {
		return spawnAreas;
	}
	
	public void setSpawnAreas(List<SpawnArea> spawnAreas) {
		this.spawnAreas = spawnAreas;
	}
	
	@SuppressWarnings("deprecation")
	public OreBlock getOreBlock(FoGPlayer omp){
		Player p = omp.getPlayer();
		Block b1 = p.getLocation().getBlock().getRelative(BlockFace.DOWN);
		
		if(b1.getType() == Material.WOOL){
			Ore ore = Ore.getByBlockData(b1.getData());
			
			if(ore != null){
				OreBlock ob = null;
				
				loop:
				for(SpawnArea area : getSpawnAreas()){
					if(area.getOreBlocks(ore) != null){
						for(OreBlock b : area.getOreBlocks(ore)){
							if(b.isMining(omp)){
								ob = b;
								break loop;
							}
						}
					}
				}
				
				return ob;
			}
		}
		return null;
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
	
	public static FoGMap getMap(String name){
		for(FoGMap map : FoG.getInstance().getMaps()){
			if(map.getName().equals(name)){
				return map;
			}
		}
		return null;
	}
}
