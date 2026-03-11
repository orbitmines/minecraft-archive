package fadidev.orbitmines.api.events;

import fadidev.orbitmines.api.OrbitMinesAPI;
import fadidev.orbitmines.api.utils.Utils;
import fadidev.orbitmines.api.utils.WorldUtils;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class ProjHitEvent implements Listener {

	private OrbitMinesAPI api;

    public ProjHitEvent(){
        this.api = OrbitMinesAPI.getApi();
    }

	@EventHandler
    public void onHit(ProjectileHitEvent e){
		Projectile proj = e.getEntity();

		if(proj instanceof FishHook){
			if(!(proj.getShooter() instanceof Player))
			    return;

            Player p = (Player) proj.getShooter();
            ItemStack item = p.getItemInHand();

            if(item != null && item.getItemMeta() != null && item.getItemMeta().getDisplayName() != null && item.getType() == Material.FISHING_ROD && item.getItemMeta().getDisplayName().equals("§7§nGrappling Hook")){
                Location l1 = proj.getLocation();
                Location l2 = p.getLocation();

                p.setVelocity(l1.toVector().subtract(l2.toVector()).multiply(1.1).add(new Vector(0, 0.5, 0)));

                proj.remove();
            }
		}
		else if(proj instanceof Fireball){
			Fireball b = (Fireball) proj;
			if(api.getGadgets().getFireballs().contains(b))
                api.getGadgets().getFireballs().remove(b);
		}
		else if(proj instanceof Egg){
			Egg egg = (Egg) proj;
			if(api.getGadgets().getEggBombs().contains(egg)){
                api.getGadgets().getEggBombs().remove(egg);
				egg.getWorld().playEffect(egg.getLocation(), Effect.EXPLOSION_LARGE, 1);
				egg.getWorld().playSound(egg.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 5, 1);
				egg.remove();
			}
		}
		else if(proj instanceof Snowball){
			Snowball ball = (Snowball) proj;

			if(api.getGadgets().getPaintBalls().contains(ball)){
				final World w = ball.getWorld();

				final List<Location> locations = WorldUtils.getPaintballLocations(ball.getLocation());

                for(Location l : new ArrayList<>(locations)){
                    if(!canTransform(w.getBlockAt(l)))
                        locations.remove(l);
                }

				int i = Utils.random(0, 15);

				for(Player p : Bukkit.getOnlinePlayers()){
					for(Location l : locations){
                        p.sendBlockChange(l, 159, (byte) i);
					}
				}

				new BukkitRunnable(){
					public void run(){
						for(Player p : Bukkit.getOnlinePlayers()){
							for(Location l : locations){
								p.sendBlockChange(l, w.getBlockAt(l).getType(), w.getBlockAt(l).getData());
							}
						}

					}
				}.runTaskLater(api, 200);
			}
		}
    }

	private boolean canTransform(Block b){
		return !b.isEmpty() && b.getTypeId() != 31 && b.getTypeId() != 37 && b.getTypeId() != 38 && b.getTypeId() != 175 && b.getType() != Material.WOOD_STEP && b.getType() != Material.WOOD_STAIRS && b.getType() != Material.COBBLESTONE_STAIRS && b.getType() != Material.TRAP_DOOR && b.getType() != Material.IRON_TRAPDOOR && b.getType() != Material.SKULL && b.getType() != Material.WATER_LILY && b.getType() != Material.SIGN_POST && b.getType() != Material.WALL_SIGN && b.getType() != Material.TORCH && b.getType() != Material.FENCE && b.getType() != Material.WATER && b.getType() != Material.STATIONARY_WATER;
	}
}
