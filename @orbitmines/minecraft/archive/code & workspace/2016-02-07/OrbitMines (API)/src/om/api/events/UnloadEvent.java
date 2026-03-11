package om.api.events;

import om.api.API;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkUnloadEvent;

public class UnloadEvent implements Listener {

	@EventHandler
	public void onUnload(ChunkUnloadEvent e){
		if(API.getInstance().getNPCChunks().contains(e.getChunk())){
			e.setCancelled(true);
		}
	}
}
