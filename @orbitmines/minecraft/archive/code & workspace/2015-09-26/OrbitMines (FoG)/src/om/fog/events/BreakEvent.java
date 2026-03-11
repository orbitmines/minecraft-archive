package om.fog.events;

import om.fog.handlers.players.FoGPlayer;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BreakEvent implements Listener {
	
	@EventHandler
	public void onBreak(BlockBreakEvent e){
		Player p = e.getPlayer();
		FoGPlayer omp = FoGPlayer.getFoGPlayer(p);
		
		if(omp.isLoaded()){
			if(!omp.isOpMode()){
				e.setCancelled(true);
			}
		}
		else{
			omp.notLoaded();
			e.setCancelled(true);
		}
	}
}
