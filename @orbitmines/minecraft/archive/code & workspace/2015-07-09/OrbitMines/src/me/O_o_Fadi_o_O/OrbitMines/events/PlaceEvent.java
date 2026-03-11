package me.O_o_Fadi_o_O.OrbitMines.events;

import me.O_o_Fadi_o_O.OrbitMines.utils.OMPlayer;
import me.O_o_Fadi_o_O.OrbitMines.utils.ServerData;
import me.O_o_Fadi_o_O.OrbitMines.utils.ServerData.CreativeServer;
import me.O_o_Fadi_o_O.OrbitMines.utils.ServerData.SkyBlockServer;
import me.O_o_Fadi_o_O.OrbitMines.utils.Utils.Server;
import me.O_o_Fadi_o_O.OrbitMines.utils.creative.CreativePlayer;
import me.O_o_Fadi_o_O.OrbitMines.utils.skyblock.SkyBlockPlayer;
import me.O_o_Fadi_o_O.OrbitMines.utils.survival.Region;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class PlaceEvent implements Listener {
	
	@EventHandler
	public void onPlace(final BlockPlaceEvent e){
		Player p = e.getPlayer();
		OMPlayer omp = OMPlayer.getOMPlayer(p);
		
		if(omp.isLoaded()){
			if(!omp.isOpMode()){
				if(ServerData.isServer(Server.KITPVP) || ServerData.isServer(Server.HUB, Server.SURVIVAL) && p.getWorld().getName().equals(ServerData.getLobbyWorld().getName())){
					e.setCancelled(true);
					omp.updateInventory();
				}
				else if(ServerData.isServer(Server.CREATIVE)){
					CreativeServer creative = ServerData.getCreative();
					CreativePlayer cp = omp.getCreativePlayer();
					
					if(p.getWorld().getName().equals(creative.getPlotWorld().getName())){
						e.setCancelled(!cp.isOnPlot(e.getBlockPlaced().getLocation()));
					}
					else if(p.getWorld().getName().equals(creative.getCreativeWorld().getName())){
						e.setCancelled(true);
					}
					else{}
				}
				else if(ServerData.isServer(Server.SURVIVAL)){
					if(p.getWorld().getName().equals(ServerData.getSurvival().getSurvivalWorld().getName())){
						if(Region.isInRegion(omp, e.getBlockPlaced().getLocation())){
							e.setCancelled(true);
							omp.updateInventory();
						}
					}
				}
				else if(ServerData.isServer(Server.SKYBLOCK)){
					SkyBlockServer skyblock = ServerData.getSkyBlock();
					SkyBlockPlayer sbp = omp.getSkyBlockPlayer();
					
					if(p.getWorld().getName().equals(skyblock.getSkyblockWorld().getName())){
						if(sbp.hasIsland()){
							if(!sbp.onOwnIsland(e.getBlockPlaced().getLocation(), true)){
								e.setCancelled(true);
							}
						}
						else{
							e.setCancelled(true);
						}
					}
					else if(p.getWorld().getName().equals(skyblock.getLobbyWorld().getName())){
						e.setCancelled(true);
					}
					else{}
				}
				else{}
			}
		}
		else{
			e.setCancelled(true);
			omp.notLoaded();
		}
	}
}
