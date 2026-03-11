package me.O_o_Fadi_o_O.OrbitMinesBungee.events;

import me.O_o_Fadi_o_O.OrbitMinesBungee.managers.DefaultPingManager;
import me.O_o_Fadi_o_O.OrbitMinesBungee.utils.ServerData;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.ServerPing.Players;
import net.md_5.bungee.api.ServerPing.Protocol;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;

public class PingEvent implements Listener {

	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPing(final ProxyPingEvent e){
		ServerPing r = e.getResponse();
		Protocol protocolVersion = null;
		if(!ServerData.getBungee().inMaintenanceMode()){
			protocolVersion = r.getVersion();
		}
		else{
			protocolVersion = new Protocol("§d§lMaintenance Mode", 1);
		}
		String motd = DefaultPingManager.getRandomStringMessage();
		Players players = r.getPlayers();
		players.setMax(players.getOnline() +1);
		String s = "Test";
		ServerPing BF = new ServerPing(protocolVersion, players, motd, s);
		e.setResponse(BF);
	}
}