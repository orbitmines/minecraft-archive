package fadidev.orbitmines.api.events;

import fadidev.orbitmines.api.OrbitMinesAPI;
import fadidev.orbitmines.api.handlers.OMPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerChatEvent implements Listener {

	private OrbitMinesAPI api;

    public PlayerChatEvent(){
        api = OrbitMinesAPI.getApi();
    }

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerChatEvent(AsyncPlayerChatEvent e){
		Player p = e.getPlayer();
		OMPlayer omp = OMPlayer.getOMPlayer(p);
		
		if(omp.isLoaded()){
			if(omp.isAfk())
				omp.noLongerAfk();

            if(api.getServerPlugin() == null)
                return;

			api.getServerPlugin().updateFormat(e);
		}
		else{
			omp.notLoaded();
		}
	}
}
