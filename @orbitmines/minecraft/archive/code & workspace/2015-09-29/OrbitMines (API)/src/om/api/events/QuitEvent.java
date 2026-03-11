package om.api.events;

import om.api.handlers.players.OMPlayer;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitEvent implements Listener {
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onQuit(PlayerQuitEvent e){
		Player p = e.getPlayer();
		OMPlayer omp = OMPlayer.getOMPlayer(p);
		
		e.setQuitMessage(null);
		
		omp.logOut();
	}
}
