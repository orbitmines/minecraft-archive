package me.O_o_Fadi_o_O.UHC.events;

import me.O_o_Fadi_o_O.UHC.Start;
import me.O_o_Fadi_o_O.UHC.managers.Manager;
import me.O_o_Fadi_o_O.UHC.utils.GameState;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class DamageByEntityEvent implements Listener{
	
	@EventHandler
	public void onDamage(EntityDamageByEntityEvent e){
		
		if(e.getEntity() instanceof Player && e.getDamager() instanceof Player){
			if(Start.state != GameState.PVP){
				e.setCancelled(true);
			}
		}
		else{
			if(Start.state != GameState.PVP && Start.state != GameState.NOPVP){
				e.setCancelled(true);
			}
		}
		if(e.getDamager() instanceof Player){
			Player p = (Player) e.getDamager();
			if(Manager.Spectators.contains(p)){
				e.setCancelled(true);
			}
		}
	}
}
