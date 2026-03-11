package om.fog.events;

import om.fog.handlers.players.FoGPlayer;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class PlaceEvent implements Listener {
	
	@EventHandler
	public void onPlace(final BlockPlaceEvent e){
		Player p = e.getPlayer();
		FoGPlayer omp = FoGPlayer.getFoGPlayer(p);
		
		if(omp.isLoaded()){
			if(!omp.isOpMode()){
				e.setCancelled(true);
				omp.updateInventory();
			}
		}
		else{
			e.setCancelled(true);
			omp.notLoaded();
		}
	}
}
