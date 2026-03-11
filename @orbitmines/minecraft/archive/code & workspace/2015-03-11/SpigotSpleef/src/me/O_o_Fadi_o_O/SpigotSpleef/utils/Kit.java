package me.O_o_Fadi_o_O.SpigotSpleef.utils;

import java.util.List;

import me.O_o_Fadi_o_O.SpigotSpleef.managers.StorageManager;

import org.bukkit.entity.Player;

public class Kit {

	private int kitid;
	private String kitname;
	private int price;
	private List<KitItemStack> items;
	
	public Kit(int kitid, String kitname, int price, List<KitItemStack> items){
		this.kitid = kitid;
		this.kitname = kitname;
		this.price = price;
		this.items = items;
	}
	
	public int getKitID(){
		return this.kitid;
	}
	
	public String getName(){
		return this.kitname;
	}
	
	public int getPrice(){
		return this.price;
	}
	
	public List<KitItemStack> getItems(){
		return items;
	}
	
	public void give(Player p){
		StorageManager.spleefplayer.get(p).clearInventory();
		int index = 0;
		for(KitItemStack item : this.items){
			if(item == null){
				p.getInventory().setItem(index, null);
			}
			else{
				p.getInventory().setItem(index, item.getItemStack());
			}
			index++;
		}
		p.updateInventory();
	}
}
