package om.fog.events;

import om.api.utils.enums.cp.CosmeticPerk;
import om.fog.handlers.players.FoGPlayer;
import om.fog.managers.FoGClickManager;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class ClickEvent implements Listener {
	
	@EventHandler
	public void onClick(InventoryClickEvent e){
		if(e.getWhoClicked() instanceof Player){
			final Player p = (Player) e.getWhoClicked();
			FoGPlayer omp = FoGPlayer.getFoGPlayer(p);
			FoGClickManager manager = new FoGClickManager(e);
			
			if(omp.isLoaded()){
				if(manager.handleServerSelector()){}
				//else if(manager.handleOMTShop()){}
				else if(manager.handleCosmeticPerks(CosmeticPerk.CHATCOLORS, CosmeticPerk.TRAILS)){}
				else if(manager.handleTrails()){}
				else if(manager.handleChatColors()){}
				else if(manager.handleTrailSettings()){}
				else if(manager.handleConfirm()){}
				else if(manager.handleInventoryViewer()){}

				else if(manager.handleFactionSelector()){}
				else if(manager.handleFoGShop()){}
				else if(manager.handleBank()){}
				else if(manager.handleSuits()){}
				else{}
			}
		}
	}
}
