package om.fog.events;

import om.fog.handlers.players.FoGPlayer;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;

public class DropEvent implements Listener {
	
	@EventHandler
	public void onDrop(PlayerDropItemEvent e){
		final Player p = e.getPlayer();
		FoGPlayer omp = FoGPlayer.getFoGPlayer(p);
		ItemStack item = e.getItemDrop().getItemStack();
		
		if(omp.isLoaded()){
			if(omp.getFaction() != null){
				if(item.getItemMeta() != null && item.getItemMeta().getLore() != null && item.getItemMeta().getLore().contains("§fSoulbound")){
					e.setCancelled(true);
					omp.updateInventory();
				}
			}
			else{
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
