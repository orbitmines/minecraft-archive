package om.fog.events;

import om.fog.FoG;
import om.fog.handlers.players.FoGPlayer;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.scheduler.BukkitRunnable;

public class CloseEvent implements Listener {

	@EventHandler
	public void onClose(final InventoryCloseEvent e){
		final Player p = (Player) e.getPlayer();
		final FoGPlayer omp = FoGPlayer.getFoGPlayer(p);
		
		if(e.getInventory().getName() != null && e.getInventory().getName().equals(omp.getBank().getBankInv().getInventory().getName())){
			omp.updateCurrentBankNow();
		}
		
		if(omp.getFaction() != null){
			if(e.getInventory() instanceof CraftingInventory){
				e.getInventory().clear();
			}
			
			new BukkitRunnable(){
				public void run(){
					if(p.getOpenInventory().getTopInventory() instanceof CraftingInventory){
						omp.updateCraftingInventory();
						p.updateInventory();
					}
				}
			}.runTaskLater(FoG.getInstance(), 1);
		}
	}
}
