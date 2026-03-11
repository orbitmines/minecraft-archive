package me.O_o_Fadi_o_O.OrbitMines.utils;

import java.util.List;

import me.O_o_Fadi_o_O.OrbitMines.utils.ServerData.ServerStorage;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Kit {

	private String kitname;
	private ItemStack[] armorcontents;
	private ItemStack[] contents;
	
	public Kit(String kitname){
		this.kitname = kitname;
		this.armorcontents = new ItemStack[4];
		this.contents = new ItemStack[36];
		
		ServerStorage.kits.add(this);
	}

	public String getKitName() {
		return kitname;
	}

	public void setKitName(String kitname) {
		this.kitname = kitname;
	}

	public ItemStack getHelmet() {
		return this.armorcontents[3];
	}

	public void setHelmet(ItemStack helmet) {
		this.armorcontents[3] = helmet;
	}

	public ItemStack getChestplate() {
		return this.armorcontents[2];
	}

	public void setChestplate(ItemStack chestplate) {
		this.armorcontents[2] = chestplate;
	}

	public ItemStack getLeggings() {
		return this.armorcontents[1];
	}

	public void setLeggings(ItemStack leggings) {
		this.armorcontents[1] = leggings;
	}

	public ItemStack getBoots() {
		return this.armorcontents[0];
	}

	public void setBoots(ItemStack boots) {
		this.armorcontents[0] = boots;
	}
	
	public ItemStack[] getArmorContents() {
		return armorcontents;
	}

	public void setArmorContents(ItemStack[] armorcontents) {
		this.armorcontents = armorcontents;
	}

	public ItemStack[] getContents() {
		return contents;
	}

	public void setContents(ItemStack[] contents) {
		this.contents = contents;
	}
	
	public void setItem(int index, ItemStack content) {
		this.contents[index] = content;
	}
	
	public void setItems(Player p){
		p.getInventory().setArmorContents(getArmorContents());
		p.getInventory().setContents(getContents());
	}
	
	public void addItems(Player p){
		for(ItemStack item : getContents()){
			if(item != null){
				p.getInventory().addItem(item);
			}
		}
		int index = 0;
		for(ItemStack item : getArmorContents()){
			ItemStack item2 = p.getInventory().getArmorContents()[index];
			
			if(item2 != null){
				p.getInventory().addItem(item2);
			}
			
			if(index == 0){
				p.getInventory().setBoots(item);
			}
			else if(index == 1){
				p.getInventory().setLeggings(item);
			}
			else if(index == 2){
				p.getInventory().setChestplate(item);
			}
			else{
				p.getInventory().setHelmet(item);
			}
			
			index++;
		}
	}
	
	public static List<Kit> getKits(){
		return ServerStorage.kits;
	}
	
	public static Kit getKit(String kitname){
		for(Kit kit : ServerStorage.kits){
			if(kit.getKitName().equals(kitname)){
				return kit;
			}
		}
		return null;
	}
}
