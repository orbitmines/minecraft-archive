package om.fog.handlers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import om.fog.FoG;
import om.fog.utils.enums.Mob;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;

public class SpawnArea {

	private FoG fog;
	private FoGMap map;
	private int x1;
	private int z1;
	private int x2;
	private int z2;
	private Map<Mob, Integer> levels;
	private Map<Mob, Integer> maxSpawn;
	private Map<Mob, Integer> delay;
	private Map<Mob, Integer> lastSpawn;
	private Map<Mob, List<Entity>> entities;
	
	public SpawnArea(FoGMap map, int x1, int z1, int x2, int z2){
		this.fog = FoG.getInstance();
		this.map = map;
		this.x1 = x1;
		this.z1 = z1;
		this.x2 = x2;
		this.z2 = z2;
		this.levels = new HashMap<Mob, Integer>();
		this.maxSpawn = new HashMap<Mob, Integer>();
		this.delay = new HashMap<Mob, Integer>();
		this.lastSpawn = new HashMap<Mob, Integer>();
		this.entities = new HashMap<Mob, List<Entity>>();
	}
	
	public FoGMap getMap() {
		return map;
	}
	
	public int getLevel(Mob mob) {
		return levels.get(mob);
	}
	
	public int getMaxSpawn(Mob mob) {
		return maxSpawn.get(mob);
	}
	
	public int getDelay(Mob mob) {
		return delay.get(mob);
	}
	
	public int getLastSpawn(Mob mob) {
		return lastSpawn.get(mob);
	}
	
	public List<Entity> getEntities(Mob mob) {
		return entities.get(mob);
	}
	
	public void addMob(Mob mob, int level, int maxSpawn, int delay){
		this.levels.put(mob, level);
		this.maxSpawn.put(mob, maxSpawn);
		this.delay.put(mob, delay);
		this.lastSpawn.put(mob, 0);
		this.entities.put(mob, new ArrayList<Entity>());
	}
	
	public void checkSpawns(){
		if(getMap().getPlayers() != 0){
			for(Mob mob : this.levels.keySet()){
				lastSpawn.put(mob, getLastSpawn(mob) +1);
				
				List<Entity> newList = new ArrayList<Entity>();
				for(Entity en : getEntities(mob)){
					if(!en.isDead() && !en.isEmpty()){
						newList.add(en);
					}
				}
				this.entities.put(mob, newList);
				
				if(getLastSpawn(mob) >= getDelay(mob) && getEntities(mob).size() < getMaxSpawn(mob)){
					Entity en = mob.spawn(getRandomLocation(), getLevel(mob));
					getEntities(mob).add(en);
					lastSpawn.put(mob, 0);
				}
			}
		}
	}
	
	public Location getRandomLocation(){
		Random r = new Random();
		
		int lowX = x1;
		int highX = x2;
		if(x2 < x1){
			lowX = x2; 
			highX = x1;
		}
		
		int lowZ = z1;
		int highZ = z2;
		if(z2 < z1){
			lowZ = z2; 
			highZ = z1;
		}
		
		int rX = r.nextInt(highX - lowX) + lowX;
		int rZ = r.nextInt(highZ - lowZ) + lowZ;
		
		return new Location(fog.getGalaxyWorld(), rX, getHighestYPos(rX, rZ), rZ);
	}
	
	public int getHighestYPos(int x, int z){
		for(int i = 250; i > 0; i--){
			Block b = fog.getGalaxyWorld().getBlockAt(x, i, z);
			
			if(b != null && !b.isEmpty()){
				return i +1;
			}
		}
		return 100;
	}
}
