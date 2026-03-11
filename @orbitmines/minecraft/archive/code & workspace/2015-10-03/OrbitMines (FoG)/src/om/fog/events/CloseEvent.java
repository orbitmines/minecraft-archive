package om.fog.events;

import om.fog.handlers.players.FoGPlayer;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class CloseEvent implements Listener {

	@EventHandler
	public void onClose(InventoryCloseEvent e){
		Player p = (Player) e.getPlayer();
		FoGPlayer omp = FoGPlayer.getFoGPlayer(p);
		
		if(e.getInventory().getName() != null && e.getInventory().getName().equals(omp.getBank().getBankInv().getInventory().getName())){
			omp.updateCurrentBankNow();
		}
	}
}
