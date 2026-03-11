package me.O_o_Fadi_o_O.OrbitMines.events;

import me.O_o_Fadi_o_O.OrbitMines.utils.OMPlayer;
import me.O_o_Fadi_o_O.OrbitMines.utils.ServerData;
import me.O_o_Fadi_o_O.OrbitMines.utils.Utils.Server;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BreakEvent implements Listener {
	
	@EventHandler
	public void onBreak(BlockBreakEvent e){
		final Player p = e.getPlayer();
		
		if(ServerData.isServer(Server.KITPVP) || p.getWorld().getName().equals(ServerData.getLobbyWorld().getName())){
			OMPlayer omp = OMPlayer.getOMPlayer(p);
			
			if(omp.isLoaded() && !omp.isOpMode()){
				e.setCancelled(true);
			}
			else{
				omp.notLoaded();
				e.setCancelled(true);
			}
		}
	}
}
