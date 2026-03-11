package me.O_o_Fadi_o_O.OrbitMines.events;

import java.util.Arrays;
import java.util.List;

import me.O_o_Fadi_o_O.OrbitMines.Start;
import me.O_o_Fadi_o_O.OrbitMines.utils.ServerData;
import me.O_o_Fadi_o_O.OrbitMines.utils.Utils;
import me.O_o_Fadi_o_O.OrbitMines.utils.ServerData.ServerStorage;
import me.O_o_Fadi_o_O.OrbitMines.utils.Utils.Server;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.FishHook;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class ProjectileHit implements Listener {
	
	@EventHandler
	@SuppressWarnings("deprecation")
    public void onHit(ProjectileHitEvent e){
		Projectile proj = e.getEntity();
		
		if(ServerData.isServer(Server.HUB, Server.MINIGAMES)){
			if(proj instanceof FishHook){
				if(proj.getShooter() instanceof Player){
					Player p = (Player) proj.getShooter();
					ItemStack item = p.getItemInHand();
					
					if(item != null && item.getItemMeta() != null && item.getItemMeta().getDisplayName() != null && item.getType() == Material.FISHING_ROD && item.getItemMeta().getDisplayName().equals("§7§nGrappling Hook")){
						Location l1 = proj.getLocation();
						Location l2 = p.getLocation();
						
						p.setVelocity(l1.toVector().subtract(l2.toVector()).multiply(1.1).add(new Vector(0, 0.5, 0)));
						
						proj.remove();
					}
				}
			}
			if(proj instanceof Fireball){
				Fireball b = (Fireball) proj;
				if(ServerStorage.fireballs.contains(b)){
					ServerStorage.fireballs.remove(b);
				}
			}
			if(proj instanceof Egg){
				Egg egg = (Egg) proj;
				if(ServerStorage.eggbombs.contains(egg)){
					ServerStorage.eggbombs.remove(egg);
					egg.getWorld().playEffect(egg.getLocation(), Effect.EXPLOSION_LARGE, 1);
					egg.getWorld().playSound(egg.getLocation(), Sound.EXPLODE, 5, 1);
					egg.remove();
				}
			}
			if(proj instanceof Snowball){
				Snowball ball = (Snowball) proj;
				
				if(ServerStorage.paintballs.contains(ball)){
					final World w = ball.getWorld();
					
					final Location l1 = ball.getLocation().subtract(0, 1, 0);
					final Location l2 = ball.getLocation().subtract(0, 1, 0).add(0, 1, 0);
					final Location l3 = ball.getLocation().subtract(0, 1, 0).add(1, 0, 0);
					final Location l4 = ball.getLocation().subtract(0, 1, 0).add(0, 0, 1);
					final Location l5 = ball.getLocation().subtract(0, 1, 0).subtract(1, 0, 0);
					final Location l6 = ball.getLocation().subtract(0, 1, 0).subtract(0, 1, 0);
					final Location l7 = ball.getLocation().subtract(0, 1, 0).subtract(0, 0, 1);
					
					final Location l8 = ball.getLocation().subtract(0, 1, 0).add(0, 2, 0);
					final Location l9 = ball.getLocation().subtract(0, 1, 0).add(0, 1, 0).add(1, 0, 0);
					final Location l10 = ball.getLocation().subtract(0, 1, 0).add(0, 1, 0).subtract(1, 0, 0);
					final Location l11 = ball.getLocation().subtract(0, 1, 0).add(0, 1, 0).add(0, 0, 1);
					final Location l12 = ball.getLocation().subtract(0, 1, 0).add(0, 1, 0).subtract(0, 0, 1);
					final Location l13 = ball.getLocation().subtract(0, 1, 0).add(2, 0, 0);
					final Location l14 = ball.getLocation().subtract(0, 1, 0).add(0, 0, 2);
					final Location l15 = ball.getLocation().subtract(0, 1, 0).subtract(2, 0, 0);
					final Location l16 = ball.getLocation().subtract(0, 1, 0).subtract(0, 0, 2);
					final Location l17 = ball.getLocation().subtract(0, 1, 0).add(1, 0, 0).add(0, 0, 1);
					final Location l18 = ball.getLocation().subtract(0, 1, 0).add(1, 0, 0).subtract(0, 0, 1);
					final Location l19 = ball.getLocation().subtract(0, 1, 0).add(0, 0, 1).subtract(1, 0, 0);
					final Location l20 = ball.getLocation().subtract(0, 1, 0).subtract(0, 0, 1).subtract(1, 0, 0);
					
					final Location l21 = ball.getLocation().subtract(0, 1, 0).subtract(0, 2, 0);
					final Location l22 = ball.getLocation().subtract(0, 1, 0).subtract(0, 1, 0).add(1, 0, 0);
					final Location l23 = ball.getLocation().subtract(0, 1, 0).subtract(0, 1, 0).subtract(1, 0, 0);
					final Location l24 = ball.getLocation().subtract(0, 1, 0).subtract(0, 1, 0).add(0, 0, 1);
					final Location l25 = ball.getLocation().subtract(0, 1, 0).subtract(0, 1, 0).subtract(0, 0, 1);
					
					final List<Location> locs = Arrays.asList(l1, l2, l3, l4, l5, l6, l7, l8, l9, l10, l11, l12, l13, l14, l15, l16, l17, l18, l19, l20, l21, l22, l23, l24, l25);
					
					int i = Utils.getRandom(0, 15);
					
					for(Player p : Bukkit.getOnlinePlayers()){
						for(Location l : locs){
							if(canTransform(w.getBlockAt(l))){
								p.sendBlockChange(l, 159, (byte) i);
							}
						}
					}
					
					new BukkitRunnable(){
						public void run(){
							for(Player p : Bukkit.getOnlinePlayers()){
								for(Location l : locs){
									p.sendBlockChange(l, w.getBlockAt(l).getType(), w.getBlockAt(l).getData());
								}
							}
							
						}
					}.runTaskLater(Start.getInstance(), 200);
				}
			}
		}
    }
	
	@SuppressWarnings("deprecation")
	private boolean canTransform(Block b){
		if(!b.isEmpty() && b.getTypeId() != 31 && b.getTypeId() != 175 && b.getType() != Material.WOOD_STEP && b.getType() != Material.WOOD_STAIRS && b.getType() != Material.COBBLESTONE_STAIRS && b.getType() != Material.TRAP_DOOR && b.getType() != Material.IRON_TRAPDOOR && b.getType() != Material.SKULL && b.getType() != Material.WATER_LILY && b.getType() != Material.SIGN_POST && b.getType() != Material.WALL_SIGN && b.getType() != Material.TORCH && b.getType() != Material.FENCE){
			return true;
		}
		else{
			return false;
		}
	}
}
