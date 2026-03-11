package om.kitpvp.events;

import java.util.List;

import om.api.utils.Utils;
import om.kitpvp.KitPvP;
import om.kitpvp.utils.enums.ProjectileType;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class ProjectileHit implements Listener {
	
	private KitPvP kitpvp;
	
	@EventHandler
	@SuppressWarnings("deprecation")
    public void onHit(ProjectileHitEvent e){
		this.kitpvp = KitPvP.getInstance();
		Projectile proj = e.getEntity();
		
		if(kitpvp.isProjectile(proj)){
			if(kitpvp.getProjectileType(proj) == ProjectileType.PAINTBALLS_I){
				final List<Location> locs = Utils.getPaintballLocations(proj.getLocation());
				int color = 14;
				if(proj.getCustomName().endsWith("Red §8|| §bWeapon§f")){
					color = 14;
				}
				else if(proj.getCustomName().endsWith("Blue §8|| §bWeapon§f")){
					color = 3;
				}
				else if(proj.getCustomName().endsWith("Black §8|| §bWeapon§f")){
					color = 15;
				}
				else if(proj.getCustomName().endsWith("Gray §8|| §bWeapon§f")){
					color = 8;
				}
				else if(proj.getCustomName().endsWith("Green §8|| §bWeapon§f")){
					color = 5;
				}
				else{}
			
				for(Location l : locs){
					for(Player p : Bukkit.getOnlinePlayers()){
						Block b = l.getWorld().getBlockAt(l);
						if(canTransform(b)){
							p.sendBlockChange(l, Material.WOOL, (byte) color);
							kitpvp.getPaintballBlocks().put(b, color);
							kitpvp.getPaintballBlockPlayers().put(b, (Player) proj.getShooter());
						}
					}
				}
				
				new BukkitRunnable(){
					public void run(){
						for(Location l : locs){
							Block b = l.getWorld().getBlockAt(l);
							for(Player p : Bukkit.getOnlinePlayers()){
								p.sendBlockChange(l, b.getType(), b.getData());
								kitpvp.getPaintballBlocks().remove(b);
								kitpvp.getPaintballBlockPlayers().remove(b);
							}
						}
					}
				}.runTaskLater(kitpvp, 40);
				
				kitpvp.removeProjectile(proj);
				proj.remove();
			}
		}
		
		if(proj instanceof Arrow){
			proj.remove();
		}
    }
	
	@SuppressWarnings("deprecation")
	private boolean canTransform(Block b){
		if(!b.isEmpty() && b.getTypeId() != 31 && b.getTypeId() != 37 && b.getTypeId() != 38 && b.getTypeId() != 175 && b.getType() != Material.WOOD_STEP && b.getType() != Material.WOOD_STAIRS && b.getType() != Material.COBBLESTONE_STAIRS && b.getType() != Material.TRAP_DOOR && b.getType() != Material.IRON_TRAPDOOR && b.getType() != Material.SKULL && b.getType() != Material.WATER_LILY && b.getType() != Material.SIGN_POST && b.getType() != Material.WALL_SIGN && b.getType() != Material.TORCH && b.getType() != Material.FENCE && b.getType() != Material.WATER && b.getType() != Material.STATIONARY_WATER){
			return true;
		}
		else{
			return false;
		}
	}
}
