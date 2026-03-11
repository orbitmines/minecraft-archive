package me.O_o_Fadi_o_O.OMHub.managers;

import me.O_o_Fadi_o_O.OMHub.utils.orbitmines.OMPlayer;
import me.O_o_Fadi_o_O.OMHub.utils.orbitmines.Utils.Server;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class ClickManager {

	private InventoryClickEvent e;
	private Player p;
	private OMPlayer omp;
	private ItemStack item;
	
	public ClickManager(InventoryClickEvent e){
		this.e = e;
		this.p = (Player) e.getWhoClicked();
		this.omp = OMPlayer.getOMPlayer(p);
		this.item = e.getCurrentItem();
	}
	
	public void handleServerSelector(){
		if(e.getInventory().getName().equals("§0§lServer Selector")){
			e.setCancelled(true);
			
			if(item.getType() == Material.IRON_SWORD){
				omp.toServer(Server.KITPVP);
			}
			if(item.getType() == Material.DIAMOND_PICKAXE){
				omp.toServer(Server.PRISON);
			}
			if(item.getType() == Material.WOOD_AXE){
				omp.toServer(Server.CREATIVE);
			}
			if(item.getType() == Material.WATCH){
				omp.toServer(Server.HUB);
			}
			if(item.getType() == Material.STONE_HOE){
				omp.toServer(Server.SURVIVAL);
			}
			if(item.getType() == Material.FISHING_ROD){
				omp.toServer(Server.SKYBLOCK);
			}
			if(item.getType() == Material.BOW){
				omp.toServer(Server.MINIGAMES);
			}
		}
	}
	
}
