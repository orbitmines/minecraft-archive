package me.O_o_Fadi_o_O.UHC.events;

import me.O_o_Fadi_o_O.UHC.Start;
import me.O_o_Fadi_o_O.UHC.utils.GameState;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class DamageEvent implements Listener{
	
	@EventHandler
	public void onDamage(EntityDamageEvent e){
		
		if(Start.state != GameState.NOPVP && Start.state != GameState.PVP){
			e.setCancelled(true);
		}
	}
}
