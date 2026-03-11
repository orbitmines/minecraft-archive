package fadidev.orbitmines.creative.events;

import fadidev.orbitmines.creative.OrbitMinesCreative;
import fadidev.orbitmines.creative.handlers.CreativePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class JoinEvent implements Listener {

	private OrbitMinesCreative creative;

	public JoinEvent(){
		creative = OrbitMinesCreative.getCreative();
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e){
		final Player p = e.getPlayer();
		final CreativePlayer omp = new CreativePlayer(p, false);
		
		e.setJoinMessage(null);

		omp.load();

        new BukkitRunnable(){
            public void run(){
				if(!omp.hasPlot())
					p.teleport(creative.getSpawn());
            }
        }.runTaskLater(creative, 5);
	}
}
