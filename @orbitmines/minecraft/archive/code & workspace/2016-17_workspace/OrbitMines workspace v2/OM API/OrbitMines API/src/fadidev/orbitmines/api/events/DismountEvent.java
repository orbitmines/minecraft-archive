package fadidev.orbitmines.api.events;

import fadidev.orbitmines.api.OrbitMinesAPI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.spigotmc.event.entity.EntityDismountEvent;

public class DismountEvent implements Listener {

    private OrbitMinesAPI api;

    public DismountEvent(){
        this.api = OrbitMinesAPI.getApi();
    }

	@EventHandler
    public void onExit(final EntityDismountEvent e) {
		if(e.getDismounted() instanceof Player && e.getEntity() instanceof Player){
			api.getNms().entity().mountUpdate(e.getDismounted());
			api.getNms().entity().mountUpdate(e.getEntity());
		}
    }
}
