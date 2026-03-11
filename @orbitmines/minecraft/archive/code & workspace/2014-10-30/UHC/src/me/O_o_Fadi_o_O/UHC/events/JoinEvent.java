package me.O_o_Fadi_o_O.UHC.events;

import me.O_o_Fadi_o_O.UHC.Start;
import me.O_o_Fadi_o_O.UHC.managers.Manager;
import me.O_o_Fadi_o_O.UHC.utils.GameState;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinEvent implements Listener{
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onJoin(PlayerJoinEvent e){
		
		Player p = e.getPlayer();
		e.setJoinMessage("§2§l§o>> " + p.getName() + " §2§o(§a§oEvent§2§o)");
		
		Manager.clearInventory(p);
		
		if(Start.state == GameState.LOBBY){
			Manager.Players.add(p);
		}
		else{
			Manager.setSpectator(p);
		}
	}

}
