package me.O_o_Fadi_o_O.SkyBlock.managers;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class PlayerManager {

	public static boolean hasIsland(Player p){
		if(StorageManager.PlayerHasIsland.containsKey(p)){
			if(StorageManager.PlayerHasIsland.get(p) == true){
				return true;
			}
			else{
				return false;
			}
		}
		else{
			return false;
		}
	}
	
	public static boolean isInTheVoid(Player p){
		Location l = p.getLocation();
		int y = l.getBlockY();
		World w = l.getWorld();
		
		for(int i = 1; i <= y; i++){
			Block b = w.getBlockAt(new Location(w, l.getBlockX(), i, l.getBlockZ()));
			if(!b.isEmpty()){
				return false;
			}
		}
		return true;
	}
	
	public static boolean isOnOwnIsland(Player p){
		Location l = StorageManager.IslandLocation.get(StorageManager.PlayersIslandNumber.get(p));
		int x = l.getBlockX();
		int z = l.getBlockZ();
		
		int bDistance = 0;
		int xB = p.getLocation().getBlockX() -x;
		int zB = p.getLocation().getBlockZ() -z;
		
		if(xB < 0){
			xB = -xB;
		}
		if(zB < 0){
			zB = -zB;
		}
		
		if(xB <= zB){
			bDistance = zB;
		}
		else{
			bDistance = xB;
		}
		
		bDistance = 50 - bDistance;
		
		if(bDistance < 0){
			return false;
		}
		else{
			return true;
		}
	}
}
