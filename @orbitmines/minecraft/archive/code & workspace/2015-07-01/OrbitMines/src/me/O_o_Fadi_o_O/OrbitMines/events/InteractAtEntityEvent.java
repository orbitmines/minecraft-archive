package me.O_o_Fadi_o_O.OrbitMines.events;

import me.O_o_Fadi_o_O.OrbitMines.utils.Hologram;
import me.O_o_Fadi_o_O.OrbitMines.utils.Inventories.ServerSelectorInv;
import me.O_o_Fadi_o_O.OrbitMines.utils.Kit;
import me.O_o_Fadi_o_O.OrbitMines.utils.NPCArmorStand;
import me.O_o_Fadi_o_O.OrbitMines.utils.OMPlayer;
import me.O_o_Fadi_o_O.OrbitMines.utils.ServerData;
import me.O_o_Fadi_o_O.OrbitMines.utils.ServerData.CreativeServer;
import me.O_o_Fadi_o_O.OrbitMines.utils.Utils.NPCType;
import me.O_o_Fadi_o_O.OrbitMines.utils.creative.CreativePlayer;
import me.O_o_Fadi_o_O.OrbitMines.utils.creative.CreativeUtils.PlotType;
import me.O_o_Fadi_o_O.OrbitMines.utils.creative.Inventories.PlotInfoInv;
import me.O_o_Fadi_o_O.OrbitMines.utils.creative.Plot;
import me.O_o_Fadi_o_O.OrbitMines.utils.kitpvp.Inventories.MasteryInv;

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
			
			if(npc != null){
				e.setCancelled(true);
				
				if(npc.getNPCType() == NPCType.SERVER_SELECTOR){
					ServerSelectorInv.get().open(p);
				}
				else if(npc.getNPCType() == NPCType.MASTERIES){
					new MasteryInv().open(p);
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
				else{}
			}
			
			Hologram h = Hologram.getHologram((ArmorStand) e.getRightClicked());
			if(h != null){
				e.setCancelled(true);
			}
		}
	}
}
