package om.api.handlers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import net.minecraft.server.v1_8_R3.PacketPlayOutEntityDestroy;
import om.api.API;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftArmorStand;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class Hologram {

	private static API api;
	private int hologramid;
	private Location location;
	private List<ArmorStand> armorstands;
	private List<String> lines;
	private HashMap<String, ArmorStand> displaynames;
	
	public Hologram(Location location){
		api = API.getInstance();
		this.location = location;
		this.hologramid = api.getHolograms().size() +1;
		this.armorstands = new ArrayList<ArmorStand>();
		this.lines = new ArrayList<String>();
		this.displaynames = new HashMap<String, ArmorStand>();

		api.getHolograms().add(this);
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
		lines.add(line);
		setDisplayname(line, null);
	}
	
	public void setLine(int index, String line){
		if(lines.size() >= index){
			String fromLine = lines.get(index -1);
			lines.set(index -1, line);
			displaynames.remove(fromLine);
			displaynames.put(line, null);
		}
	}
	
	public void create(){
		if(getArmorstands().size() > 0){
			delete();
			this.armorstands.clear();
		}
		
		Chunk c = location.getChunk();
		c.load();
		api.getNPCChunks().add(c);
		for(Entity en : location.getWorld().getNearbyEntities(location, 0.1, 0.1, 0.1)){
			if(!(en instanceof Player)){
				en.remove();
			}
		}
		
		int index = 0;
		Location l = getLocation();
		if(l != null){
			for(String displayname : lines){
				ArmorStand armorstand = (ArmorStand) l.getWorld().spawnEntity(new Location(l.getWorld(), l.getX(), l.getY() -(index * 0.25), l.getZ()), EntityType.ARMOR_STAND);
				armorstand.setCustomName(displayname);
				armorstand.setCustomNameVisible(true);
				armorstand.setGravity(false);
				
				((CraftArmorStand) armorstand).getHandle().setInvisible(true);
				
				setDisplayname(displayname, armorstand);
				addArmorStand(armorstand);
				
				index++;
			}
		}
	}
	
	public void create(Player... players){
		if(getArmorstands().size() > 0){
			delete();
			this.armorstands.clear();
		}
		
		Chunk c = location.getChunk();
		c.load();
		api.getNPCChunks().add(c);
		for(Entity en : location.getWorld().getNearbyEntities(location, 0.1, 0.1, 0.1)){
			if(!(en instanceof Player)){
				en.remove();
			}
		}
		
		int index = 0;
		Location l = getLocation();
		if(l != null){
			for(String displayname : lines){
				
				ArmorStand armorstand = (ArmorStand) l.getWorld().spawnEntity(new Location(l.getWorld(), l.getX(), l.getY() -(index * 0.25), l.getZ()), EntityType.ARMOR_STAND);
				armorstand.setCustomName(displayname);
				armorstand.setCustomNameVisible(true);
				armorstand.setGravity(false);
				
				((CraftArmorStand) armorstand).getHandle().setInvisible(true);
				
		        for(Player player : Bukkit.getOnlinePlayers()){
		        	if(!Arrays.asList(players).contains(player)){
		        		((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutEntityDestroy(((CraftArmorStand) armorstand).getHandle().getId()));
		        	}
		        }
				
				setDisplayname(displayname, armorstand);
				addArmorStand(armorstand);
				
				index++;
			}
		}
	}
	
	public void createHideFor(Player... players){
		if(getArmorstands().size() > 0){
			delete();
			this.armorstands.clear();
		}
		
		Chunk c = location.getChunk();
		c.load();
		api.getNPCChunks().add(c);
		for(Entity en : location.getWorld().getNearbyEntities(location, 0.1, 0.1, 0.1)){
			if(!(en instanceof Player)){
				en.remove();
			}
		}
		
		int index = 0;
		Location l = getLocation();
		if(l != null){
			for(String displayname : lines){
				
				ArmorStand armorstand = (ArmorStand) l.getWorld().spawnEntity(new Location(l.getWorld(), l.getX(), l.getY() -(index * 0.25), l.getZ()), EntityType.ARMOR_STAND);
				armorstand.setCustomName(displayname);
				armorstand.setCustomNameVisible(true);
				armorstand.setGravity(false);
				
				((CraftArmorStand) armorstand).getHandle().setInvisible(true);
				
		        for(Player player : Bukkit.getOnlinePlayers()){
		        	if(Arrays.asList(players).contains(player)){
		        		((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutEntityDestroy(((CraftArmorStand) armorstand).getHandle().getId()));
		        	}
		        }
				
				setDisplayname(displayname, armorstand);
				addArmorStand(armorstand);
				
				index++;
			}
		}
	}
	
	public void delete(){
		for(ArmorStand as : getArmorstands()){
			as.remove();
		}
	}
	
	public static Hologram getHologram(int hologramid){
		for(Hologram hologram : api.getHolograms()){
			if(hologram.getHologramID() == hologramid){
				return hologram;
			}
		}
		return null;
	}
	public static Hologram getHologram(ArmorStand as){
		for(Hologram hologram : api.getHolograms()){
			if(hologram.getArmorstands().contains(as)){
				return hologram;
			}
		}
		return null;
	}
}
