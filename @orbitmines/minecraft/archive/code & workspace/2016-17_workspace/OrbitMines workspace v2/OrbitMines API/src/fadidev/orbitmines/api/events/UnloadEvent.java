package fadidev.orbitmines.api.events;

import fadidev.orbitmines.api.OrbitMinesAPI;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkUnloadEvent;

public class UnloadEvent implements Listener {

	private OrbitMinesAPI api;

	public UnloadEvent(){
		api = OrbitMinesAPI.getApi();
	}

	@EventHandler
	public void onUnload(ChunkUnloadEvent e){
		if(api.getChunks().contains(e.getChunk()))
			e.setCancelled(true);
	}
}
