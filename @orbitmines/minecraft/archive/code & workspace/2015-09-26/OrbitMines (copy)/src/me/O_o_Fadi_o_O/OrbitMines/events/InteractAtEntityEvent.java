package me.O_o_Fadi_o_O.OrbitMines.events;

import me.O_o_Fadi_o_O.OrbitMines.utils.OMPlayer;
import me.O_o_Fadi_o_O.OrbitMines.utils.ServerData;
import me.O_o_Fadi_o_O.OrbitMines.utils.ServerData.CreativeServer;
import me.O_o_Fadi_o_O.OrbitMines.utils.ServerData.SkyBlockServer;
import me.O_o_Fadi_o_O.OrbitMines.utils.Utils.Cooldown;
import me.O_o_Fadi_o_O.OrbitMines.utils.Utils.NPCType;
import me.O_o_Fadi_o_O.OrbitMines.utils.Utils.Server;
import me.O_o_Fadi_o_O.OrbitMines.utils.creative.CreativePlayer;
import me.O_o_Fadi_o_O.OrbitMines.utils.creative.CreativeUtils.PlotType;
import me.O_o_Fadi_o_O.OrbitMines.utils.creative.Inventories.PlotInfoInv;
import me.O_o_Fadi_o_O.OrbitMines.utils.creative.Plot;
import me.O_o_Fadi_o_O.OrbitMines.utils.hub.Inventories.ServerSelectorInv;
import me.O_o_Fadi_o_O.OrbitMines.utils.kitpvp.Inventories.MasteryInv;
import me.O_o_Fadi_o_O.OrbitMines.utils.skyblock.SkyBlockPlayer;
import om.api.handlers.Hologram;
import om.api.handlers.Kit;
import om.api.handlers.NPCArmorStand;

import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.ItemStack;

public class InteractAtEntityEvent implements Listener{
	
	@EventHandler
	public void onInteract(PlayerInteractAtEntityEvent e){
		if(e.getRightClicked() instanceof ArmorStand){
			Player p = e.getPlayer();
			OMPlayer omp = OMPlayer.getOMPlayer(p);
			NPCArmorStand npc = NPCArmorStand.getNPCArmorStand((ArmorStand) e.getRightClicked());
			ItemStack item = p.getItemInHand();

			if(item != null && item.getType() == Material.MONSTER_EGG){
				e.setCancelled(true);
				omp.updateInventory();
			}
			
			if(ServerData.isServer(Server.SKYBLOCK)){
				SkyBlockServer skyblock = ServerData.getSkyBlock();
				SkyBlockPlayer sbp = omp.getSkyBlockPlayer();
				
				if(p.getWorld().getName().equals(skyblock.getSkyblockWorld().getName()) || p.getWorld().getName().equals(skyblock.getSkyblockNetherWorld().getName())){
					if(sbp.hasIsland()){
						if(!sbp.onIsland(e.getRightClicked().getLocation(), true)){
							e.setCancelled(true);
						}
					}
					else{
						e.setCancelled(true);
					}
				}
			}
			else if(ServerData.isServer(Server.CREATIVE)){
				CreativePlayer cp = omp.getCreativePlayer();
				
				if(cp.getPlot() == null || cp.isInPvPPlot() || !cp.isOnPlot(e.getRightClicked().getLocation()) || ((e.getRightClicked() instanceof ArmorStand) && NPCArmorStand.getNPCArmorStand((ArmorStand) e.getRightClicked()) != null)){
					e.setCancelled(true);
				}
			}
			else{}
			
			if(npc != null){
				e.setCancelled(true);
				
				if(npc.getNPCType() == NPCType.SERVER_SELECTOR){
					ServerSelectorInv.get().open(p);
				}
				else if(npc.getNPCType() == NPCType.PLOT_KIT_SELECTOR){
					CreativeServer creative = ServerData.getCreative();
					CreativePlayer cp = omp.getCreativePlayer();
					Plot plot = cp.getPlot();
					
					if(p.getWorld().getName().equals(creative.getPlotWorld().getName())){
						if(cp.isInPvPPlot()){
							plot = cp.getPvPPlot();
						}
						
						if(plot != null){
							for(Kit kit : plot.getPvPNPCs().keySet()){
								NPCArmorStand npc2 = plot.getPvPNPCs().get(kit);
								
								if(e.getRightClicked() == npc2.getArmorStand()){
									if(plot.getPlotType() == PlotType.PVP){
										if(cp.getSelectedKit() == null && cp.isInPvPPlot()){
											cp.selectKit(kit);
										}
									}
									else{
										if(plot.getPlotID() == cp.getPlot().getPlotID()){
											cp.openKitInventory(kit);
										}
									}
								}
							}
						}
					}
				}
				else if(npc.getNPCType() == NPCType.PLOT_INFO){
					new PlotInfoInv().open(p);
				}
				else if(npc.getNPCType() == NPCType.PVP_AREA){
					omp.resetCooldown(Cooldown.PVP_CONFIRM);
				}
				else if(npc.getNPCType() == NPCType.REGION_TELEPORTER){
					ServerData.getSurvival().getRegionTeleporter().open(p);
				}
				else{}
			}
			
			Hologram h = Hologram.getHologram((ArmorStand) e.getRightClicked());
			if(h != null){
				e.setCancelled(true);
			}
		}
	}
}
