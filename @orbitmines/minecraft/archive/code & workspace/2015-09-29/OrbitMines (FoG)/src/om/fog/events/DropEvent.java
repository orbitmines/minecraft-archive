package om.fog.events;

import om.fog.handlers.players.FoGPlayer;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class DropEvent implements Listener {
	
	@EventHandler
	public void onDrop(PlayerDropItemEvent e){
		final Player p = e.getPlayer();
		FoGPlayer omp = FoGPlayer.getFoGPlayer(p);
		
		if(omp.isLoaded()){
			if(!omp.isOpMode()){
				e.setCancelled(true);
				omp.updateInventory();
			}
		}
		else{
			omp.notLoaded();
			e.setCancelled(true);
			omp.updateInventory();
		}
	}
}
