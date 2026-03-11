package me.O_o_Fadi_o_O.OrbitMines.events;

import me.O_o_Fadi_o_O.OrbitMines.utils.OMPlayer;
import me.O_o_Fadi_o_O.OrbitMines.utils.ServerData;
import me.O_o_Fadi_o_O.OrbitMines.utils.Utils.Server;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class PlaceEvent implements Listener {
	
	@EventHandler
	public void onPlace(final BlockPlaceEvent e){
		Player p = e.getPlayer();
		OMPlayer omp = OMPlayer.getOMPlayer(p);
		
		if(ServerData.isServer(Server.KITPVP) || ServerData.isServer(Server.HUB) && p.getWorld().getName().equals(ServerData.getLobbyWorld().getName())){
			if(omp.isLoaded()){
				if(!omp.isOpMode()){
					e.setCancelled(true);
					omp.updateInventory();
				}
			}
			else{
				e.setCancelled(true);
				omp.notLoaded();
			}
		}
	}
}
