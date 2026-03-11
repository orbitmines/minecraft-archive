package fadidev.centrumpvpbungee.events;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import fadidev.centrumpvpbungee.CentrumPvPBungee;
import fadidev.centrumpvpbungee.handlers.BungeePlayer;

public class PlayerConnectEvent implements Listener{
	
	private CentrumPvPBungee msg;
	
	@EventHandler
	public void onJoin(ServerConnectEvent e) {
		this.msg = CentrumPvPBungee.getInstance();
		ProxiedPlayer p = e.getPlayer();

		if(!msg.getBungeePlayers().containsKey(p)){
			BungeePlayer bp = new BungeePlayer(p);
			msg.getBungeePlayers().put(p, bp);
		}
	}
}
