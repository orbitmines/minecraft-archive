package om.fog.events;

import om.api.utils.enums.cp.Pet;
import om.fog.handlers.players.FoGPlayer;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class DamageEvent implements Listener {
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onDamage(EntityDamageByEntityEvent e){
		if(e.getDamager() instanceof Player){
			Player pD = (Player) e.getDamager();
			FoGPlayer ompD = FoGPlayer.getFoGPlayer(pD);
			
			if(e.getEntity() instanceof Player){
				Player pE = (Player) e.getEntity();
				FoGPlayer ompE = FoGPlayer.getFoGPlayer(pE);
				
				if(ompD.getFaction() == ompE.getFaction()){
					e.setCancelled(true);
				}
			}
		}
		else if(Pet.pets.contains(e.getDamager())){
			e.setCancelled(true);
		}
		else{}
	}
}
