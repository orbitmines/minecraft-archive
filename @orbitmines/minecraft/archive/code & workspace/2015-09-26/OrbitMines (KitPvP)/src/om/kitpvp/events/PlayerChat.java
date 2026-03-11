package om.kitpvp.events;

import om.kitpvp.handlers.players.KitPvPPlayer;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerChat implements Listener {
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerChatEvent(AsyncPlayerChatEvent e){
		Player p = e.getPlayer();
		KitPvPPlayer omp = KitPvPPlayer.getKitPvPPlayer(p);
		
		if(omp.isLoaded()){
			if(omp.isAFK()){
				omp.noLongerAFK();
			}
			
			e.setFormat(omp.getChatFormat());
		}
		else{
			omp.notLoaded();
		}
	}
}
