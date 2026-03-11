package fadidev.orbitmines.hub.events;

import fadidev.orbitmines.hub.OrbitMinesHub;
import fadidev.orbitmines.hub.handlers.players.HubPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class JoinEvent implements Listener {

	private OrbitMinesHub hub;

	public JoinEvent(){
		hub = OrbitMinesHub.getHub();
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e){
		final Player p = e.getPlayer();
		final HubPlayer omp = new HubPlayer(p, false);
		
		e.setJoinMessage(null);

        p.getInventory().clear();
		omp.load();

        new BukkitRunnable(){
            public void run(){
                if(!p.getWorld().getName().equals(hub.getLobby().getName()) || p.getLocation().distance(hub.getMiniGameLocation()) > 16)
                    p.teleport(hub.getSpawn());
            }
        }.runTaskLater(hub, 5);
	}
}
