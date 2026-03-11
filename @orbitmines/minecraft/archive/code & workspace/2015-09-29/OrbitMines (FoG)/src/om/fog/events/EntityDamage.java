package om.fog.events;

import om.api.handlers.NPC;
import om.api.utils.enums.cp.Pet;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class EntityDamage implements Listener {
	
	@EventHandler
	public void onEntityDamage(EntityDamageEvent e){
		Entity en = e.getEntity();
		
		if(en instanceof Player){
			//Player p = (Player) en;
			//FoGPlayer omp = FoGPlayer.getFoGPlayer(p);
			
			//if(omp.getKitSelected() == null) e.setCancelled(true);
		}
		else{
			if(Pet.pets.contains(en) || NPC.getNPC(en) != null){
				e.setCancelled(true);
			}
		}
	}
}
