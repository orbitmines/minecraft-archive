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
		OMPlayer omp = OMPlayer.addOMPlayer(p, false);
		
		e.setJoinMessage(null);

		omp.load();
		
		new BukkitRunnable(){
			public void run(){
				if(ServerData.isServer(Server.HUB)){
					p.teleport(ServerData.getHub().getSpawn());
				}
				else if(ServerData.isServer(Server.KITPVP)){
					p.teleport(ServerData.getKitPvP().getSpawn());
				}
				else{}
			}
		}.runTaskLater(Start.getInstance(), 5);
	}
}
