package om.fog.events;

import om.api.handlers.NPC;
import om.api.utils.enums.cp.Pet;
import om.fog.handlers.players.FoGPlayer;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

public class EntityDamage implements Listener {
	
	@EventHandler
	public void onEntityDamage(EntityDamageEvent e){
		Entity en = e.getEntity();
		
		if(en instanceof Player){
			if(e.getCause() != DamageCause.ENTITY_ATTACK && e.getCause() != DamageCause.PROJECTILE){
				Player p = (Player) en;
				FoGPlayer omp = FoGPlayer.getFoGPlayer(p);
				double damage = e.getDamage();
				double damageToShield = 0;
				
				if(omp.getCurrentShield() != 0){
					if(omp.getCurrentShield() - damage >= 0){
						damageToShield += damage;
						damage = 0;
					}
					else{
						damageToShield = omp.getCurrentShield();
						damage -= damageToShield;
					}
				}
				
				omp.damageShield(damageToShield);
				omp.updateShieldBar();
				e.setDamage(damage);
			}
		}
		else{
			if(Pet.pets.contains(en) || NPC.getNPC(en) != null){
				e.setCancelled(true);
			}
		}
	}
}
