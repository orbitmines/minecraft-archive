package om.fog.events;

import om.fog.handlers.players.FoGPlayer;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMove implements Listener {
	
	@EventHandler
    public void onMove(final PlayerMoveEvent e) {
    	Player p = e.getPlayer();
    	FoGPlayer omp = FoGPlayer.getFoGPlayer(p);
		
		if(omp.isLoaded()){
			omp.checkLastLocation();
			
			if(omp.isAFK()){
				omp.noLongerAFK();
			}
		}
    }
}
