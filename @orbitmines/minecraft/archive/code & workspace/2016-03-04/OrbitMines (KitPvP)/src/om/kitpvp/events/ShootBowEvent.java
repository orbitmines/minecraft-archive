package om.kitpvp.events;

import java.util.List;

import om.kitpvp.KitPvP;
import om.kitpvp.handlers.players.KitPvPPlayer;
import om.kitpvp.utils.enums.ProjectileType;

import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class ShootBowEvent implements Listener {
	
	private KitPvP kitpvp;
	
	@EventHandler
	public void onShot(EntityShootBowEvent e){
		this.kitpvp = KitPvP.getInstance();
		Entity en = e.getEntity();
		
		if(en instanceof Player){
			Player p = (Player) en;
			KitPvPPlayer omp = KitPvPPlayer.getKitPvPPlayer(p);
			ItemStack item = p.getItemInHand();
			
			if(item != null && item.getType() == Material.BOW && item.getItemMeta() != null && item.getItemMeta().getLore() != null){
				List<String> itemlore = item.getItemMeta().getLore();
				
				for(ProjectileType type : ProjectileType.getProjectileTypes(omp.getKitSelected())){
					if(itemlore.contains(type.getName())){
						if(type != ProjectileType.ARROW_SPLIT_I){
							kitpvp.addProjectile((Projectile) e.getProjectile(), type);
						}
						else{
							if(e.getProjectile() instanceof Arrow){
								Vector velocity = e.getProjectile().getVelocity();
								double speed = velocity.length();
								Vector direction = new Vector(velocity.getX() / speed, velocity.getY() / speed, velocity.getZ() / speed);
								double spray = 3.5D;
								 
								for(int i = 0; i < 4; i++){
									Arrow arrow = p.launchProjectile(Arrow.class);
									arrow.setVelocity(new Vector(direction.getX() + (Math.random() - 0.5) / spray, direction.getY() + (Math.random() - 0.5) / spray, direction.getZ() + (Math.random() - 0.5) / spray).normalize().multiply(speed));
								}
							}
						}
					}
				}
			}
		}
	}
}
