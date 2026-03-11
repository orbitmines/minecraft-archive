package me.O_o_Fadi_o_O.OMHub.utils.orbitmines;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import me.O_o_Fadi_o_O.OMHub.utils.orbitmines.ServerData.ServerStorage;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;

public class Hologram {

	private int hologramid;
	private Location location;
	private List<ArmorStand> armorstands;
	private HashMap<String, ArmorStand> displaynames;
	
	public Hologram(){
		this.hologramid = ServerStorage.holograms.size() +1;
		this.armorstands = new ArrayList<ArmorStand>();
		this.displaynames = new HashMap<String, ArmorStand>();
	}

	public int getHologramID(){
		return hologramid;
	}
	public void setHologramID(int hologramid){
		this.hologramid = hologramid;
	}

	public Location getLocation(){
		return location;
	}
	public void setLocation(Location location){
		this.location = location;
	}

	public List<ArmorStand> getArmorstands(){
		return armorstands;
	}
	public void setArmorstands(List<ArmorStand> armorstands){
		this.armorstands = armorstands;
	}
	public void addArmorStand(ArmorStand armorstand){
		this.armorstands.add(armorstand);
	}

	public HashMap<String, ArmorStand> getDisplaynames(){
		return displaynames;
	}
	public void setDisplaynames(HashMap<String, ArmorStand> displaynames){
		this.displaynames = displaynames;
	}
	public void setDisplayname(String displayname, ArmorStand armorstand){
		this.displaynames.put(displayname, armorstand);
	}
	
	public void addLine(String line){
		setDisplayname(line, null);
	}
	
	public void create(){
		if(getArmorstands().size() > 0){
			for(ArmorStand as : getArmorstands()){
				as.remove();
			}
			this.armorstands.clear();
		}
		
		int index = 0;
		Location l = getLocation();
		if(l != null){
			for(String displayname : getDisplaynames().keySet()){
				
				ArmorStand armorstand = (ArmorStand) l.getWorld().spawnEntity(new Location(l.getWorld(), l.getX(), l.getY() -(index * 0.25), l.getZ()), EntityType.ARMOR_STAND);
				armorstand.setCustomName(displayname);
				armorstand.setCustomNameVisible(true);
				armorstand.setGravity(false);
				armorstand.setVisible(false);
				
				setDisplayname(displayname, armorstand);
				addArmorStand(armorstand);
				
				index++;
			}
		}
	}
	
	public static List<Hologram> getHolograms(){
		return ServerStorage.holograms;
	}
	
	public static Hologram getHologram(int hologramid){
		for(Hologram hologram : ServerStorage.holograms){
			if(hologram.getHologramID() == hologramid){
				return hologram;
			}
		}
		return null;
	}
	
	public static Hologram addHologram(){
		Hologram hologram = new Hologram();
		ServerStorage.holograms.add(hologram);
		return hologram;
	}
}
