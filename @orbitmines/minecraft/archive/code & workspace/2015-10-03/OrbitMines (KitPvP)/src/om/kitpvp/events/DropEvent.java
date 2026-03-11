package om.kitpvp.events;

import om.kitpvp.handlers.players.KitPvPPlayer;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class DropEvent implements Listener {
	
	@EventHandler
	public void onDrop(PlayerDropItemEvent e){
		final Player p = e.getPlayer();
		KitPvPPlayer omp = KitPvPPlayer.getKitPvPPlayer(p);
		
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
