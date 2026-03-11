package fadidev.orbitmines.kitpvp.events;

import fadidev.orbitmines.api.utils.WorldUtils;
import fadidev.orbitmines.kitpvp.OrbitMinesKitPvP;
import fadidev.orbitmines.kitpvp.utils.enums.ProjectileType;
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

import java.util.List;

public class ProjHitEvent implements Listener {

	private OrbitMinesKitPvP kitPvP;

	public ProjHitEvent(){
		kitPvP = OrbitMinesKitPvP.getKitPvP();
	}

	@EventHandler
    public void onHit(ProjectileHitEvent e){
		Projectile proj = e.getEntity();
		
		if(kitPvP.isProjectile(proj)){
			if(kitPvP.getProjectileType(proj) == ProjectileType.PAINTBALLS_I){
				final List<Location> locs = WorldUtils.getPaintballLocations(proj.getLocation());
				int color = 14;
				if(proj.getCustomName().endsWith("Red §8|| §bWeapon§f"))
					color = 14;
				else if(proj.getCustomName().endsWith("Blue §8|| §bWeapon§f"))
					color = 3;
				else if(proj.getCustomName().endsWith("Black §8|| §bWeapon§f"))
					color = 15;
				else if(proj.getCustomName().endsWith("Gray §8|| §bWeapon§f"))
					color = 8;
				else if(proj.getCustomName().endsWith("Green §8|| §bWeapon§f"))
					color = 5;
			
				for(Location l : locs){
					for(Player p : Bukkit.getOnlinePlayers()){
						Block b = l.getWorld().getBlockAt(l);
						if(canTransform(b)){
							p.sendBlockChange(l, Material.WOOL, (byte) color);
							kitPvP.getPaintballBlocks().put(b, color);
							kitPvP.getPaintballBlockPlayers().put(b, (Player) proj.getShooter());
						}
					}
				}
				
				new BukkitRunnable(){
					public void run(){
						for(Location l : locs){
							Block b = l.getWorld().getBlockAt(l);
							for(Player p : Bukkit.getOnlinePlayers()){
								p.sendBlockChange(l, b.getType(), b.getData());
								kitPvP.getPaintballBlocks().remove(b);
								kitPvP.getPaintballBlockPlayers().remove(b);
							}
						}
					}
				}.runTaskLater(kitPvP, 40);

				kitPvP.removeProjectile(proj);
				proj.remove();
			}
		}
		
		if(proj instanceof Arrow)
			proj.remove();
    }

	private boolean canTransform(Block b){
		return !b.isEmpty() && b.getTypeId() != 31 && b.getTypeId() != 37 && b.getTypeId() != 38 && b.getTypeId() != 175 && b.getType() != Material.WOOD_STEP && b.getType() != Material.WOOD_STAIRS && b.getType() != Material.COBBLESTONE_STAIRS && b.getType() != Material.TRAP_DOOR && b.getType() != Material.IRON_TRAPDOOR && b.getType() != Material.SKULL && b.getType() != Material.WATER_LILY && b.getType() != Material.SIGN_POST && b.getType() != Material.WALL_SIGN && b.getType() != Material.TORCH && b.getType() != Material.FENCE && b.getType() != Material.WATER && b.getType() != Material.STATIONARY_WATER;
	}
}
