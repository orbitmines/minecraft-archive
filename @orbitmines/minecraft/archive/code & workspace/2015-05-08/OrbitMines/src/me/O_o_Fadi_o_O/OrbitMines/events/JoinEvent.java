package me.O_o_Fadi_o_O.OrbitMines.events;

import me.O_o_Fadi_o_O.OrbitMines.Start;
import me.O_o_Fadi_o_O.OrbitMines.utils.OMPlayer;
import me.O_o_Fadi_o_O.OrbitMines.utils.ServerData;
import me.O_o_Fadi_o_O.OrbitMines.utils.Utils.Server;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class JoinEvent implements Listener {
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e){
		final Player p = e.getPlayer();
		
		e.setJoinMessage(null);
		
		OMPlayer omp = OMPlayer.addOMPlayer(p, false);
		omp.load();
		
		if(ServerData.isServer(Server.HUB)){
			new BukkitRunnable(){
				public void run(){
					p.teleport(ServerData.getHub().getSpawn());
				}
			}.runTaskLater(Start.getInstance(), 5);
		}
		else{}
	}
}
