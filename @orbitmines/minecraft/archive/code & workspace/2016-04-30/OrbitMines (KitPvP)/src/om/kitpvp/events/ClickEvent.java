package om.kitpvp.events;

import om.api.utils.enums.cp.CosmeticPerk;
import om.kitpvp.handlers.players.KitPvPPlayer;
import om.kitpvp.managers.KitPvPClickManager;

import org.bukkit.entity.Player;
import org.bukkit.event.Event.Result;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;

public class ClickEvent implements Listener {
	
	@EventHandler
	public void onClick(InventoryClickEvent e){
		if(e.getWhoClicked() instanceof Player){
			final Player p = (Player) e.getWhoClicked();
			KitPvPPlayer omp = KitPvPPlayer.getKitPvPPlayer(p);
			KitPvPClickManager manager = new KitPvPClickManager(e);
			
			if(omp.isLoaded()){
				if(e.getSlotType() == SlotType.ARMOR || omp.isSpectator()){
					e.setResult(Result.DENY);
					omp.updateInventory();
				}
			
				if(manager.handleServerSelector()){}
				else if(manager.handleOMTShop()){}
				else if(manager.handleCosmeticPerks(CosmeticPerk.PETS, CosmeticPerk.CHATCOLORS, CosmeticPerk.TRAILS)){}
				else if(manager.handleTrails()){}
				else if(manager.handleChatColors()){}
				else if(manager.handlePets()){}
				else if(manager.handleTrailSettings()){}
				else if(manager.handleConfirm()){}
				else if(manager.handleInventoryViewer()){}
				
				else if(manager.handleKitSelector()){}
				else if(manager.handleKits()){}
				else if(manager.handleBoosters()){}
				else if(manager.handleSpectator()){}
				else if(manager.handleTeleporter()){}
				else if(manager.handleMasteries()){}
				else if(omp.getKitSelected() == null && manager.handleEnchantmentTable()){}
				else{}
			}
		}
	}
}
