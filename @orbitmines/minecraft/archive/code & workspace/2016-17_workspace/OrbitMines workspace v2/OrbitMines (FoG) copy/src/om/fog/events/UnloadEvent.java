package om.fog.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkUnloadEvent;

public class UnloadEvent implements Listener {

	@EventHandler
	public void onUnload(ChunkUnloadEvent e){
		e.setCancelled(true);
	}
}
