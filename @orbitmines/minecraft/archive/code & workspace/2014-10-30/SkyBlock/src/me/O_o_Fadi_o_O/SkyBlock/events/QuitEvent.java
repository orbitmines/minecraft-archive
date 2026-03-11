package me.O_o_Fadi_o_O.SkyBlock.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitEvent implements Listener{

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onQuit(PlayerQuitEvent e){
		Player p = e.getPlayer();
		
		e.setQuitMessage("§4§l§o<< " + p.getName() + " §4§o(§c§oSkyBlock§4§o)");
	}
}
