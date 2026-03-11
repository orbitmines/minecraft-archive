package me.O_o_Fadi_o_O.OrbitMines.events;

import me.O_o_Fadi_o_O.OrbitMines.Start;
import me.O_o_Fadi_o_O.OrbitMines.utils.ServerData;
import me.O_o_Fadi_o_O.OrbitMines.utils.Utils.Server;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class BlockChangeEvent implements Listener{
	
	@EventHandler
	public void onChange(final EntityChangeBlockEvent e){
		Entity en = e.getEntity();
		
		if(en instanceof FallingBlock){
			FallingBlock b = (FallingBlock) en;
			
			if(b.getMaterial() == Material.WEB || b.getMaterial() == Material.FIRE){
				int delay = 100;
				if(ServerData.isServer(Server.HUB) && b.getMaterial() == Material.FIRE){
					delay = 40;
				}
				
				new BukkitRunnable(){
					public void run(){
						e.getBlock().setType(Material.AIR);
					}
				}.runTaskLater(Start.getInstance(), delay);
			}
			else{
				e.getEntity().remove();
				e.setCancelled(true);
			}
		}
	}
}
