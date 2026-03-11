package me.O_o_Fadi_o_O.OrbitMines.utils.minigames;

import java.util.ArrayList;
import java.util.List;

import me.O_o_Fadi_o_O.OrbitMines.managers.ConfigManager;
import me.O_o_Fadi_o_O.OrbitMines.utils.Utils;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

public class CageBuilder {

	private String name;
	private Location spawn;
	private Location l1;
	private Location l2;
	
	public CageBuilder(String name, Location spawn){
		this.name = name;
		this.spawn = spawn;
	}

	public String getName() {
		return name;
	}

	public Location getSpawn() {
		return spawn;
	}
	public void setSpawn(Location spawn) {
		this.spawn = spawn;
	}

	public Location getL1() {
		return l1;
	}
	public void setL1(Location l1) {
		this.l1 = l1;
	}

	public Location getL2() {
		return l2;
	}
	public void setL2(Location l2) {
		this.l2 = l2;
	}
	
	@SuppressWarnings("deprecation")
	public void generate(){
		List<String> list = new ArrayList<String>();
		for(Block b : Utils.getBlocksBetween(getL1(), getL2())){
			if(!b.isEmpty()){
				Location l1 = getSpawn();
				Location l2 = b.getLocation();
				
				list.add("setBlock(blocks, b.getRelative(" + (l2.getBlockX() - l1.getBlockX()) + ", " + (l2.getBlockY() - l1.getBlockY()) + ", " + (l2.getBlockZ() - l1.getBlockZ()) + "), Material." + b.getType().toString() + ", " + b.getData() + ");");
			}
		}
		
		ConfigManager.config.set(getName(), list);
		ConfigManager.saveConfig();
	}
	
	public static ItemStack getWand(){
		return Utils.setDisplayname(new ItemStack(Material.GOLD_AXE), "§eCage Builder");
	}
}
