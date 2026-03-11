package om.kitpvp.events;

import om.kitpvp.KitPvP;
import om.kitpvp.handlers.players.KitPvPPlayer;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class JoinEvent implements Listener {
	
	private KitPvP kitpvp;
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e){
		this.kitpvp = KitPvP.getInstance();
		final Player p = e.getPlayer();
		
		new KitPvPPlayer(p, false);
		e.setJoinMessage(null);
		
		new BukkitRunnable(){
			public void run(){
				p.teleport(kitpvp.getSpawn());
			}
		}.runTaskLater(kitpvp, 5);
	}
}
