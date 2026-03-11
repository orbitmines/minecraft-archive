package om.api.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;

public class WorldUtils {

	public boolean equalsLoc(Location l1, Location l2){
		return l1.getWorld().getName().equals(l2.getWorld().getName()) && l1.getBlockX() == l2.getBlockX() && l1.getBlockY() == l2.getBlockY() && l1.getBlockZ() == l2.getBlockZ();
	}
	
	public void removeAllEntities(){
		for(World world : Bukkit.getWorlds()){
			for(Entity en : world.getEntities()){
				if(!(en instanceof Player)){
					en.remove();
				}
			}
		}
	}
	
	public void removeAllEntitiesIF(){
		for(World world : Bukkit.getWorlds()){
			for(Entity en : world.getEntities()){
				if(!(en instanceof Player) && !(en instanceof ItemFrame)){
					en.remove();
				}
			}
		}
	}
	
	public void removeEntities(World world){
		for(Entity en : world.getEntities()){
			if(!(en instanceof Player)){
				en.remove();
			}
		}
	}
	
    public List<Block> getBlocksBetween(Location l1, Location l2){
        List<Block> blocks = new ArrayList<Block>();
 
        int topBlockX = (l1.getBlockX() < l2.getBlockX() ? l2.getBlockX() : l1.getBlockX());
        int bottomBlockX = (l1.getBlockX() > l2.getBlockX() ? l2.getBlockX() : l1.getBlockX());
 
        int topBlockY = (l1.getBlockY() < l2.getBlockY() ? l2.getBlockY() : l1.getBlockY());
        int bottomBlockY = (l1.getBlockY() > l2.getBlockY() ? l2.getBlockY() : l1.getBlockY());
 
        int topBlockZ = (l1.getBlockZ() < l2.getBlockZ() ? l2.getBlockZ() : l1.getBlockZ());
        int bottomBlockZ = (l1.getBlockZ() > l2.getBlockZ() ? l2.getBlockZ() : l1.getBlockZ());
 
        for(int x = bottomBlockX; x <= topBlockX; x++) {
            for(int z = bottomBlockZ; z <= topBlockZ; z++){
                for(int y = bottomBlockY; y <= topBlockY; y++){
                    Block block = l1.getWorld().getBlockAt(x, y, z);
                   
                    blocks.add(block);
                }
            }
        }
       
        return blocks;
    }
}
