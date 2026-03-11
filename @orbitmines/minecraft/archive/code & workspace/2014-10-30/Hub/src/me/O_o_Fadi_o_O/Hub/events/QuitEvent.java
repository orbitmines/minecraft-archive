package me.O_o_Fadi_o_O.Hub.events;

import java.sql.SQLException;

import me.O_o_Fadi_o_O.Hub.Hub;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitEvent implements Listener {

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onQuit(PlayerQuitEvent e){
		
		Player p = e.getPlayer();
		
		e.setQuitMessage("§4§l§o<< " + p.getName() + " §4§o(§c§oHub§4§o)");
		
		if(Hub.FireworkPasses.get(p.getName()) != null){
			try {
				Hub.setFireworksPasses(p, Hub.FireworkPasses.get(p.getName()));
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		
		if(Hub.ownerpets.containsKey(p)){
			
			for(Entity en : Bukkit.getWorld("Hub").getEntities()){
				if(en.getUniqueId() == Hub.ownerpets.get(p)){
					if(en instanceof LivingEntity){
						en.remove();
						Hub.owners.remove(Hub.ownerpets.get(p));
						Hub.ownerpets.remove(p);
					}
				}
			}
		}
	}
}
