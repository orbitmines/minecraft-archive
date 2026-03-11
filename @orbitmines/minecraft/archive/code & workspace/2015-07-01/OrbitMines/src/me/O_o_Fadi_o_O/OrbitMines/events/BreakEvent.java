package me.O_o_Fadi_o_O.OrbitMines.events;

import me.O_o_Fadi_o_O.OrbitMines.utils.OMPlayer;
import me.O_o_Fadi_o_O.OrbitMines.utils.ServerData;
import me.O_o_Fadi_o_O.OrbitMines.utils.Utils.Server;
import me.O_o_Fadi_o_O.OrbitMines.utils.creative.CreativePlayer;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BreakEvent implements Listener {
	
	@EventHandler
	public void onBreak(BlockBreakEvent e){
		Player p = e.getPlayer();
		OMPlayer omp = OMPlayer.getOMPlayer(p);
		
		if(omp.isLoaded()){
			if(!omp.isOpMode()){
				if(ServerData.isServer(Server.KITPVP) || p.getWorld().getName().equals(ServerData.getLobbyWorld().getName())){
					e.setCancelled(true);
				}
				else if(ServerData.isServer(Server.CREATIVE)){
					if(p.getWorld().getName().equals(ServerData.getCreative().getPlotWorld().getName())){
						CreativePlayer cp = omp.getCreativePlayer();
						e.setCancelled(!cp.isOnPlot(e.getBlock().getLocation()));
					}
				}
				else{}
			}
		}
		else{
			omp.notLoaded();
			e.setCancelled(true);
		}
	}
}
