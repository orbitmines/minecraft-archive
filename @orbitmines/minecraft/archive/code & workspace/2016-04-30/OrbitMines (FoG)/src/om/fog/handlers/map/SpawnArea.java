package om.fog.handlers.map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import om.api.utils.WorldUtils;
import om.fog.FoG;
import om.fog.utils.FoGUtils;
import om.fog.utils.enums.Mob;
import om.fog.utils.enums.Ore;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
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

	private Map<Ore, Integer> maxSpawnOre;
	private Map<Ore, Integer> delayOre;
	private Map<Ore, Integer> lastSpawnOre;
	private Map<Ore, List<OreBlock>> ores;
	
	public SpawnArea(FoGMap map, int x1, int z1, int x2, int z2){
		this.fog = FoG.getInstance();
		this.map = map;
		this.map.getSpawnAreas().add(this);
		this.x1 = x1;
		this.z1 = z1;
		this.x2 = x2;
		this.z2 = z2;
		this.levels = new HashMap<Mob, Integer>();
		this.maxSpawn = new HashMap<Mob, Integer>();
		this.delay = new HashMap<Mob, Integer>();
		this.lastSpawn = new HashMap<Mob, Integer>();
		this.entities = new HashMap<Mob, List<Entity>>();
		
		this.maxSpawnOre = new HashMap<Ore, Integer>();
		this.delayOre = new HashMap<Ore, Integer>();
		this.lastSpawnOre = new HashMap<Ore, Integer>();
		this.ores = new HashMap<Ore, List<OreBlock>>();
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
	
	public Map<Mob, List<Entity>> getEntities() {
		return entities;
	}
	
	public List<Entity> getEntities(Mob mob) {
		return entities.get(mob);
	}
	
	public int getMaxSpawnOre(Ore ore) {
		return maxSpawnOre.get(ore);
	}
	
	public int getDelayOre(Ore ore) {
		return delayOre.get(ore);
	}
	
	public int getLastSpawnOre(Ore ore) {
		return lastSpawnOre.get(ore);
	}
	
	public List<OreBlock> getOreBlocks(Ore ore) {
		return ores.get(ore);
	}
	
	public void addMob(Mob mob, int level, int maxSpawn, int delay){
		this.levels.put(mob, level);
		this.maxSpawn.put(mob, maxSpawn);
		this.delay.put(mob, delay);
		this.lastSpawn.put(mob, 0);
		this.entities.put(mob, new ArrayList<Entity>());
	}
	
	public void addOre(Ore ore, int maxSpawn, int delay){
		this.maxSpawnOre.put(ore, maxSpawn);
		this.delayOre.put(ore, delay);
		this.lastSpawnOre.put(ore, 0);
		this.ores.put(ore, new ArrayList<OreBlock>());
	}
	
	public void checkSpawns(){
		if(getMap().getPlayers() != 0){
			for(Mob mob : this.maxSpawn.keySet()){
				lastSpawn.put(mob, getLastSpawn(mob) +1);
				
				List<Entity> newList = new ArrayList<Entity>();
				for(Entity en : this.entities.get(mob)){
					if(!en.isDead()){
						newList.add(en);
					}
				}
				
				if(getLastSpawn(mob) >= getDelay(mob) && newList.size() < getMaxSpawn(mob)){
					Location l = FoGUtils.getRandomLocation(fog.getGalaxyWorld(), x1, z1, x2, z2);
					Block b = l.getBlock().getRelative(BlockFace.DOWN);
					if(!b.isLiquid() && l.getBlock().getType() != Material.FIRE){
						Entity en = mob.spawn(l, getLevel(mob));
						newList.add(en);
						entities.put(mob, newList);
						lastSpawn.put(mob, 0);
					}
				}
			}
			
			for(Ore ore : this.maxSpawnOre.keySet()){
				lastSpawnOre.put(ore, getLastSpawnOre(ore) +1);
				
				if(getLastSpawnOre(ore) >= getDelayOre(ore)){
					if(getOreBlocks(ore).size() < getMaxSpawnOre(ore)){
						Location l = FoGUtils.getRandomLocation(fog.getGalaxyWorld(), x1, z1, x2, z2);
						Block bl = fog.getGalaxyWorld().getBlockAt(l).getRelative(BlockFace.DOWN);
						
						if(!bl.isLiquid() && bl.getType() != Material.FIRE){
							boolean cancontinue = true;
							for(OreBlock b : getOreBlocks(ore)){
								if(WorldUtils.equalsLoc(b.getBlock().getLocation(), bl.getLocation())){
									cancontinue = false;
									break;
								}
							}
							
							if(cancontinue){
								OreBlock b = new OreBlock(this, ore, bl);
								getOreBlocks(ore).add(b);
								lastSpawnOre.put(ore, 0);
							}
						}
					}
					else{
						if(new Random().nextInt(20) == 0){
							OreBlock b = getOreBlocks(ore).get(new Random().nextInt(getOreBlocks(ore).size()));
							b.spawnFirework();
						}
					}
				}
			}
		}
	}
}
