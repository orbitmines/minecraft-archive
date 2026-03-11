package om.fog.events;

import om.fog.handlers.players.FoGPlayer;
import om.fog.managers.FoGInteractManager;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerInteract implements Listener{
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onInteract(PlayerInteractEvent e){
		Player p = e.getPlayer();
		FoGPlayer omp = FoGPlayer.getFoGPlayer(p);
		ItemStack item = e.getItem();
		
		if(omp.isLoaded()){
			FoGInteractManager manager = new FoGInteractManager(e);
			
			if(omp.isAFK()){
				omp.noLongerAFK();
			}
			
			manager.handleClickable();
			
			if(item != null){
				if(!omp.isOpMode()){
					e.setCancelled(true);
				}
				
				if(item.getItemMeta() != null && item.getItemMeta().getDisplayName() != null){
					
				}
			}
		}
	}
}
