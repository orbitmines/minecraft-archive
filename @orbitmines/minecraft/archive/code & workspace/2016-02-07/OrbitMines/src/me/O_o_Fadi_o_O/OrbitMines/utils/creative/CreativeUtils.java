package me.O_o_Fadi_o_O.OrbitMines.utils.creative;

import org.bukkit.Material;

public class CreativeUtils {

	public static enum PlotType {
		
		NORMAL,
		PVP;
		
		public String getName(){
			switch(this){
				case NORMAL:
					return "§d§lBouw Modus";
				case PVP:
					return "§c§lPvP Modus";
				default:
					return null;
			}
		}
		
		public Material getMaterial(){
			switch(this){
				case NORMAL:
					return Material.WOOD_AXE;
				case PVP:
					return Material.STONE_SWORD;
				default:
					return null;
			}
		}
	}
}
