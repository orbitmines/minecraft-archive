package fadidev.orbitmines.kitpvp.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

public class TeleportEvent implements Listener {
	
	@EventHandler
	public void onTeleport(PlayerTeleportEvent e){
		if(e.getCause() == TeleportCause.ENDER_PEARL)
			e.setCancelled(true);
	}
}
