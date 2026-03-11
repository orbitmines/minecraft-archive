package fadidev.orbitmines.kitpvp.events;

import fadidev.orbitmines.kitpvp.handlers.KitPvPPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class PlaceEvent implements Listener {
	
	@EventHandler
	public void onPlace(final BlockPlaceEvent e){
		Player p = e.getPlayer();
		KitPvPPlayer omp = KitPvPPlayer.getKitPvPPlayer(p);
		
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
