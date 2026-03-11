package me.O_o_Fadi_o_O.OrbitMines.events;

import me.O_o_Fadi_o_O.OrbitMines.Start;
import me.O_o_Fadi_o_O.OrbitMines.utils.NPC;
import me.O_o_Fadi_o_O.OrbitMines.utils.OMPlayer;
import me.O_o_Fadi_o_O.OrbitMines.utils.ServerData;
import me.O_o_Fadi_o_O.OrbitMines.utils.ServerData.ServerStorage;
import me.O_o_Fadi_o_O.OrbitMines.utils.Utils;
import me.O_o_Fadi_o_O.OrbitMines.utils.Utils.Server;
import me.O_o_Fadi_o_O.OrbitMines.utils.kitpvp.KitPvPPlayer;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class EntityDamage implements Listener{
	
	@EventHandler
	public void onEntityDamage(EntityDamageEvent e){
		Entity en = e.getEntity();
		
		if(ServerData.isServer(Server.HUB)){
			e.setCancelled(true);
		}
		else if(ServerData.isServer(Server.KITPVP)){
			if(en instanceof Player){
				Player p = (Player) en;
				OMPlayer omp = OMPlayer.getOMPlayer(p);
				KitPvPPlayer kp = omp.getKitPvPPlayer();
				
				if(kp.getKitSelected() == null){
					e.setCancelled(true);
				}
				else{
					if(!e.isCancelled()){
						for(int i = 0; i < 2; i++){
							final Item item = p.getWorld().dropItem(p.getLocation(), Utils.setDurability(Utils.setDisplayname(new ItemStack(Material.INK_SACK), "Blood " + p.getName() + i), 1));
							item.setPickupDelay(Integer.MAX_VALUE);
							item.setVelocity(Utils.getRandomVelocity());
							
							new BukkitRunnable(){
								public void run(){
									item.remove();
								}
							}.runTaskLater(Start.getInstance(), 50);
						}
					}
				}
			}
		}
		else{}
		
		if(ServerStorage.pets.contains(en) || NPC.getNPC(en) != null){
			e.setCancelled(true);
		}
	}
}
