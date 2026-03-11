package fadidev.orbitmines.api.events;

import fadidev.orbitmines.api.OrbitMinesAPI;
import fadidev.orbitmines.api.handlers.npc.NPC;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class EntityDamage implements Listener {

	private OrbitMinesAPI api;

	public EntityDamage(){
		this.api = OrbitMinesAPI.getApi();
	}

	@EventHandler
	public void onEntityDamage(EntityDamageEvent e){
		Entity en = e.getEntity();
		
		if(api.getPets().contains(en) || NPC.getNpc(en) != null)
			e.setCancelled(true);
	}
}
