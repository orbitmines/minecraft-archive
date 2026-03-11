package me.O_o_Fadi_o_O.UHC.events;

import me.O_o_Fadi_o_O.UHC.Start;
import me.O_o_Fadi_o_O.UHC.managers.Manager;
import me.O_o_Fadi_o_O.UHC.utils.GameState;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitEvent implements Listener{
	
	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onQuit(PlayerQuitEvent e){
		
		Player p = e.getPlayer();
		e.setQuitMessage("§4§l§o<< " + p.getName() + " §4§o(§c§oEvent§4§o)");
		
		if(Manager.Players.contains(p)){
			Manager.Players.remove(p);
		}
		
		if(Start.state == GameState.PVP){
			if(Manager.Players.contains(p)){
				p.damage(100);
			}
		}
		
	}

}
