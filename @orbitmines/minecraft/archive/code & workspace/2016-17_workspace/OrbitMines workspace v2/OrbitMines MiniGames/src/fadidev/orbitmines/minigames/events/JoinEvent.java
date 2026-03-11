package fadidev.orbitmines.minigames.events;

import fadidev.orbitmines.minigames.handlers.players.MiniGamesPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinEvent implements Listener {

	@EventHandler
	public void onJoin(PlayerJoinEvent e){
		Player p = e.getPlayer();
		MiniGamesPlayer omp = new MiniGamesPlayer(p, false);
		
		e.setJoinMessage(null);

		omp.load();
	}
}
