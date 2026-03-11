package me.O_o_Fadi_o_O.OrbitMines.utils.survival;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class Inventories {

	public static class RegionInv {
		
		private Inventory inventory;
		
		public RegionInv(){
			this.inventory = Bukkit.createInventory(null, 45, "§0§lRegion Teleporter");
			this.inventory.setContents(getContects());
		}
		
		public Inventory getInventory(){
			return inventory;
		}
		public void setInventory(Inventory inventory){
			this.inventory = inventory;
		}
		
		public void open(Player player){
			player.openInventory(getInventory());
		}
		
		public ItemStack[] getContects(){
			ItemStack[] contents = inventory.getContents();
			
			for(Region r : Region.getRegions()){
				contents[r.getSlot()] = r.getItemStack();
			}
			
			return contents;
		}
	}
}
