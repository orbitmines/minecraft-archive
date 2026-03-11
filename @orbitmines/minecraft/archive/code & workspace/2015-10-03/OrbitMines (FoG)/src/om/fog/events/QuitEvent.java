package om.fog.events;

import om.fog.handlers.players.FoGPlayer;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitEvent implements Listener {

	@EventHandler
	public void onQuit(PlayerQuitEvent e){
		Player p = e.getPlayer();
		FoGPlayer omp = FoGPlayer.getFoGPlayer(p);
		
		if(omp.isInCombat()){
			p.damage(1000D);
		}
	}
}
