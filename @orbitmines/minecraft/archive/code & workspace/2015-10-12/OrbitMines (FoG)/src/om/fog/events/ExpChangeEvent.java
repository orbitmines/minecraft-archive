package om.fog.events;

import om.fog.FoG;
import om.fog.handlers.players.FoGPlayer;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class ExpChangeEvent implements Listener {
	
	@EventHandler
	public void onExpChange(PlayerExpChangeEvent e){
		Player p = e.getPlayer();
		final FoGPlayer omp = FoGPlayer.getFoGPlayer(p);
		
		if(omp.isLoaded()){
			new BukkitRunnable(){
				public void run(){
					omp.updateLevel();
				}
			}.runTaskLater(FoG.getInstance(), 1);
		}
	}
}
