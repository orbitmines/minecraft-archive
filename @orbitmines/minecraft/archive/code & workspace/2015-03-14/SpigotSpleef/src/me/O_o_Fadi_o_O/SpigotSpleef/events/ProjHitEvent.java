package me.O_o_Fadi_o_O.SpigotSpleef.events;

import java.util.ArrayList;
import java.util.List;

import me.O_o_Fadi_o_O.SpigotSpleef.managers.StorageManager;
import me.O_o_Fadi_o_O.SpigotSpleef.utils.Arena;
import me.O_o_Fadi_o_O.SpigotSpleef.utils.SpleefPlayer;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.projectiles.ProjectileSource;

public class ProjHitEvent implements Listener {
	
	@EventHandler
	public void onHit(ProjectileHitEvent e){
		Projectile proj = e.getEntity();
		
		if(StorageManager.projectiles.containsKey(proj)){
			Arena arena = StorageManager.projectiles.get(proj);
			ProjectileSource shooter = proj.getShooter();
			
			if(shooter instanceof Player){
				Player p = (Player) shooter;
				SpleefPlayer sp = StorageManager.spleefplayer.get(p);
				Location l = proj.getLocation();
				
				int expand = 1;
				
				if(arena.getProjectilesSmall().contains(proj)){
					expand = 2;
					arena.removeProjectileSmall(proj);
				}
				else if(arena.getProjectilesMedium().contains(proj)){
					expand = 3;
					arena.removeProjectileMedium(proj);
				}
				else if(arena.getProjectilesBig().contains(proj)){					
					expand = 4;
					arena.removeProjectileBig(proj);
				}
				else{}
				
				Location l1 = new Location(l.getWorld(), l.getBlockX() +expand, l.getBlockY() -1, l.getBlockZ() +expand);
				Location l2 = new Location(l.getWorld(), l.getBlockX() -expand, l.getBlockY() -1, l.getBlockZ() -expand);
				Location l3 = new Location(l.getWorld(), l.getBlockX() -expand, l.getBlockY() -1, l.getBlockZ() +expand);
				Location l4 = new Location(l.getWorld(), l.getBlockX() +expand, l.getBlockY() -1, l.getBlockZ() -expand);
				
				for(Block b : getBlocksBetween(l1, l2)){
					Location bl = b.getLocation();
					if(bl.getBlockX() != l1.getBlockX() && bl.getBlockZ() != l1.getBlockZ() || bl.getBlockX() != l2.getBlockX() && bl.getBlockZ() != l2.getBlockZ() || bl.getBlockX() != l3.getBlockX() && bl.getBlockZ() != l3.getBlockZ() || bl.getBlockX() != l4.getBlockX() && bl.getBlockZ() != l4.getBlockZ()){
						arena.breakBlock(sp, b);
					}
				}
				proj.remove();
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
