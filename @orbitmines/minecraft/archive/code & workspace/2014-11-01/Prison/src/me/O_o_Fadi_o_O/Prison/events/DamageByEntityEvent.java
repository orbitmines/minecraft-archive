package me.O_o_Fadi_o_O.Prison.events;

import me.O_o_Fadi_o_O.Prison.managers.StorageManager;

import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class DamageByEntityEvent implements Listener{
	
	@EventHandler
	public void onDamage(EntityDamageByEntityEvent e){
		Entity en = e.getEntity();
		
		if(StorageManager.owners.containsKey(en.getUniqueId())){
			e.setCancelled(true);
		}
	}
}
