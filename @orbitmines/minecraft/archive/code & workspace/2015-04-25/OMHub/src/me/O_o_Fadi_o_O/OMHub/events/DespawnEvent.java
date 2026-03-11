package me.O_o_Fadi_o_O.OMHub.events;

import me.O_o_Fadi_o_O.OMHub.Start;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemDespawnEvent;

public class DespawnEvent implements Listener{
	
	Start hub = Start.getInstance();
	
	@EventHandler
	public void onDespawn(ItemDespawnEvent e){
		e.setCancelled(true);
	}
}
