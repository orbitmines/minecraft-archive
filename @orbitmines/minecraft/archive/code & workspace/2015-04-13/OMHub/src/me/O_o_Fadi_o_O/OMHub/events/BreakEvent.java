package me.O_o_Fadi_o_O.OMHub.events;

import me.O_o_Fadi_o_O.OMHub.Hub;
import me.O_o_Fadi_o_O.OMHub.managers.PlayerManager;
import me.O_o_Fadi_o_O.OMHub.managers.StorageManager;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BreakEvent implements Listener {
	
	Hub hub = Hub.getInstance();
	
	@EventHandler
	public void onBreak(BlockBreakEvent e){
		final Player p = e.getPlayer();
		if(p.getWorld().getName().equals(StorageManager.hubworld.getName())){
			if(StorageManager.loaded.get(p) == true){
				if(StorageManager.opmodeenabled.get(p) == false){
					e.setCancelled(true);
				}
			}
			else{
				PlayerManager.warnPlayerNotLoaded(p);
				e.setCancelled(true);
			}
		}
	}
}
