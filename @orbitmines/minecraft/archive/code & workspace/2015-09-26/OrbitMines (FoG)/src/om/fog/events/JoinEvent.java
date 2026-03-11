package om.fog.events;

import om.fog.FoG;
import om.fog.handlers.players.FoGPlayer;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class JoinEvent implements Listener {
	
	private FoG fog;
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e){
		this.fog = FoG.getInstance();
		final Player p = e.getPlayer();
		
		new FoGPlayer(p, false);
		e.setJoinMessage(null);
		
		new BukkitRunnable(){
			public void run(){
				p.teleport(fog.getSpawn());
			}
		}.runTaskLater(fog, 5);
	}
}
