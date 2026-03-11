package fadidev.orbitmines.kitpvp.events;

import fadidev.orbitmines.kitpvp.OrbitMinesKitPvP;
import fadidev.orbitmines.kitpvp.handlers.KitPvPPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class ExpChangeEvent implements Listener {

    private OrbitMinesKitPvP kitPvP;

    public ExpChangeEvent(){
        kitPvP = OrbitMinesKitPvP.getKitPvP();
    }

	@EventHandler
	public void onExpChange(PlayerExpChangeEvent e){
		Player p = e.getPlayer();
		final KitPvPPlayer omp = KitPvPPlayer.getKitPvPPlayer(p);
		
		if(omp.isLoaded())
			new BukkitRunnable(){
				public void run(){
					omp.updateLevel();
				}
			}.runTaskLater(kitPvP, 1);
	}
}
