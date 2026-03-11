package fadidev.orbitmines.kitpvp.events;

import fadidev.orbitmines.kitpvp.OrbitMinesKitPvP;
import fadidev.orbitmines.kitpvp.handlers.KitPvPPlayer;
import fadidev.orbitmines.kitpvp.utils.enums.ProjectileType;
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

import java.util.List;

public class ShootBowEvent implements Listener {

	private OrbitMinesKitPvP kitPvP;

	public ShootBowEvent(){
		kitPvP = OrbitMinesKitPvP.getKitPvP();
	}
	
	@EventHandler
	public void onShot(EntityShootBowEvent e){
		Entity en = e.getEntity();
		
		if(!(en instanceof Player))
			return;

        Player p = (Player) en;
        KitPvPPlayer omp = KitPvPPlayer.getKitPvPPlayer(p);
        ItemStack item = p.getItemInHand();

        if(item != null && item.getType() == Material.BOW && item.getItemMeta() != null && item.getItemMeta().getLore() != null){
            List<String> itemLore = item.getItemMeta().getLore();

            for(ProjectileType type : ProjectileType.getProjectileTypes(omp.getKitSelected())){
                if(itemLore.contains(type.getName())){
                    if(type != ProjectileType.ARROW_SPLIT_I){
                        kitPvP.addProjectile((Projectile) e.getProjectile(), type);
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
