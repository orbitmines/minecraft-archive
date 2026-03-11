package fadidev.orbitmines.kitpvp.events;

import fadidev.orbitmines.kitpvp.handlers.KitPvPPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class DamageEvent implements Listener {
	
	@EventHandler
	public void onEntityDamage(EntityDamageEvent e){
		Entity en = e.getEntity();
		
		if(!(en instanceof Player))
			return;

		Player p = (Player) en;
		KitPvPPlayer omp = KitPvPPlayer.getKitPvPPlayer(p);

		if(omp.getKitSelected() == null) e.setCancelled(true);
	}
}
