package me.O_o_Fadi_o_O.OrbitMines.events;

import me.O_o_Fadi_o_O.OrbitMines.utils.OMPlayer;
import me.O_o_Fadi_o_O.OrbitMines.utils.ServerData;
import me.O_o_Fadi_o_O.OrbitMines.utils.ServerData.SkyBlockServer;
import me.O_o_Fadi_o_O.OrbitMines.utils.Utils.Server;
import me.O_o_Fadi_o_O.OrbitMines.utils.creative.CreativePlayer;
import me.O_o_Fadi_o_O.OrbitMines.utils.skyblock.SkyBlockPlayer;
import me.O_o_Fadi_o_O.OrbitMines.utils.survival.Region;
import me.O_o_Fadi_o_O.OrbitMines.utils.survival.ShopSign;
import me.O_o_Fadi_o_O.OrbitMines.utils.survival.SurvivalPlayer;

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
				else if(ServerData.isServer(Server.SURVIVAL)){
					SurvivalPlayer sp = omp.getSurvivalPlayer();
					
					if(p.getWorld().getName().equals(ServerData.getSurvival().getSurvivalWorld().getName())){
						if(Region.isInRegion(omp, e.getBlock().getLocation())){
							e.setCancelled(true);
						}
						
						if(!e.isCancelled()){
							ShopSign sign = ShopSign.getShopSign(e.getBlock().getLocation());
							
							if(sign != null && sp.getShopSigns().contains(sign)){
								sign.delete();
								p.sendMessage("§7Your Chest Shop has been removed.");
							}
						}
					}
				}
				else if(ServerData.isServer(Server.SKYBLOCK)){
					SkyBlockServer skyblock = ServerData.getSkyBlock();
					SkyBlockPlayer sbp = omp.getSkyBlockPlayer();
					
					if(p.getWorld().getName().equals(skyblock.getSkyblockWorld().getName()) || p.getWorld().getName().equals(skyblock.getSkyblockNetherWorld().getName())){
						if(sbp.hasIsland()){
							if(!sbp.onIsland(e.getBlock().getLocation(), true)){
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
			omp.notLoaded();
			e.setCancelled(true);
		}
	}
}
