package fadidev.centrumpvpbungee.utils;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class PlayerUtils {

	public static ProxiedPlayer getPlayer(String name){
		for(ProxiedPlayer p : ProxyServer.getInstance().getPlayers()){
			if(p.getName().equalsIgnoreCase(name)){
				return p;
			}
		}
		return null;
	}
}
