package om.kitpvp.events;

import om.kitpvp.KitPvP;
import om.kitpvp.handlers.players.KitPvPPlayer;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class ExpChangeEvent implements Listener {
	
	@EventHandler
	public void onExpChange(PlayerExpChangeEvent e){
		Player p = e.getPlayer();
		final KitPvPPlayer omp = KitPvPPlayer.getKitPvPPlayer(p);
		
		if(omp.isLoaded()){
			new BukkitRunnable(){
				public void run(){
					omp.updateLevel();
				}
			}.runTaskLater(KitPvP.getInstance(), 1);
		}
	}
}
