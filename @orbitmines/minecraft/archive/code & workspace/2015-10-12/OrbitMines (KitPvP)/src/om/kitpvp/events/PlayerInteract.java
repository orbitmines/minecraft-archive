package om.kitpvp.events;

import om.kitpvp.handlers.players.KitPvPPlayer;
import om.kitpvp.managers.KitPvPInteractManager;

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
		KitPvPPlayer omp = KitPvPPlayer.getKitPvPPlayer(p);
		ItemStack item = e.getItem();
		
		if(omp.isLoaded()){
			KitPvPInteractManager manager = new KitPvPInteractManager(e);
			
			if(omp.isAFK()){
				omp.noLongerAFK();
			}
			
			manager.handlePhysicalAction();
			manager.handleSpectator();
			manager.handleClickable();
			
			if(item != null){
				if(!omp.isOpMode() && omp.getKitSelected() == null && manager.handleWrittenBook()){
					e.setCancelled(true);
				}
				
				if(item.getItemMeta() != null && item.getItemMeta().getDisplayName() != null){
					if(omp.getKitSelected() != null){
						manager.handleSnowball();
						manager.handleKitPvPEnchantments();
					}
					else if(omp.isSpectator()){
						if(manager.handleTeleporter()){}
						else if(manager.handleBackToLobby()){}
						else{}
					}
					else{
						if(manager.handleAchievements()){}
						else if(manager.handleServerSelector()){}
						else if(manager.handleBoosters()){}
						else if(manager.handleCosmeticPerks()){}
						else if(manager.handleKitSelector()){}
						else{}
					}
				}
			}
		}
	}
}
