package fadidev.orbitmines.kitpvp.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class PickupEvent implements Listener {
	
	@EventHandler
	public void onPickUp(PlayerPickupItemEvent e){
		e.setCancelled(true);
	}
}
