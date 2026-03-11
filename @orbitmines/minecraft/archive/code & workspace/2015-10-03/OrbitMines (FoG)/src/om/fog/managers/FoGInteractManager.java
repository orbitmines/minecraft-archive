package om.fog.managers;

import om.api.managers.InteractManager;
import om.fog.FoG;
import om.fog.handlers.players.FoGPlayer;

import org.bukkit.event.player.PlayerInteractEvent;

public class FoGInteractManager extends InteractManager {

	private FoG fog;
	private FoGPlayer omp;
	
	public FoGInteractManager(PlayerInteractEvent e) {
		super(e);
		
		this.fog = FoG.getInstance();
		this.omp = FoGPlayer.getFoGPlayer(p);
	}
	
	//public boolean handleTeleporter(){
	//	if(item.getType() == Material.NAME_TAG && item.getItemMeta().getDisplayName().equals("§e§nTeleporter")){
	//		e.setCancelled(true);
	//		omp.updateInventory();
	//	}
	//}
}
