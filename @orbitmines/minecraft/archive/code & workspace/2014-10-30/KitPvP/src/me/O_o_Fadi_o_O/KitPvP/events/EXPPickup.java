package me.O_o_Fadi_o_O.KitPvP.events;

import me.O_o_Fadi_o_O.KitPvP.Start;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerExpChangeEvent;

public class EXPPickup implements Listener{
	
	Start plugin;
	 
	public EXPPickup(Start instance) {
	plugin = instance;
	}
	
	@EventHandler
	public void onPlayerExpChange(PlayerExpChangeEvent e) {
		final Player p = e.getPlayer();
		
		final int level = plugin.getLevel(p);
		
			plugin.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable(){
			public void run(){
				
				p.setLevel(level);
				p.setExp(1);
				
			}
		}, 1L);
	}

}
