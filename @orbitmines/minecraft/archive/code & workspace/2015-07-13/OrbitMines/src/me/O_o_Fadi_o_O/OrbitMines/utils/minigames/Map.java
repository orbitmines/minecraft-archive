package me.O_o_Fadi_o_O.OrbitMines.utils.minigames;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import me.O_o_Fadi_o_O.OrbitMines.utils.OMPlayer;
import me.O_o_Fadi_o_O.OrbitMines.utils.Utils.MiniGameType;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import com.google.common.io.Files;

public class Map {

	private String mapname;
	private String builder;
	private String worldname;
	private World world;
	private Location spectatorlocation;
	private List<Location> spawns;
	private boolean inuse;
	private HashMap<OMPlayer, Location> playerspawns;
	
	public Map(String mapname, String builder, MiniGameType type, String worldname){
		this.mapname = mapname;
		this.builder = builder;
		this.worldname = worldname;
		this.inuse = false;
		this.playerspawns = new HashMap<OMPlayer, Location>();
		
		restoreWorld();
	}

	public String getMapName() {
		return mapname;
	}
	public void setMapName(String mapname) {
		this.mapname = mapname;
	}

	public String getBuilder() {
		return builder;
	}
	public void setBuilder(String builder) {
		this.builder = builder;
	}

	public World getWorld() {
		return world;
	}
	public void setWorld(World world) {
		this.world = world;
	}

	public String getWorldName() {
		return worldname;
	}
	public void setWorldName(String worldname) {
		this.worldname = worldname;
	}

	public Location getSpectatorLocation() {
		return spectatorlocation;
	}
	public void setSpectatorLocation(Location spectatorlocation) {
		this.spectatorlocation = spectatorlocation;
	}

	public List<Location> getSpawns() {
		return spawns;
	}
	public void setSpawns(List<Location> spawns) {
		this.spawns = spawns;
	}

	public boolean isInUse() {
		return inuse;
	}
	public void setInUse(boolean inuse) {
		this.inuse = inuse;
	}

	public HashMap<OMPlayer, Location> getPlayerSpawns() {
		return playerspawns;
	}
	public void setPlayerSpawns(HashMap<OMPlayer, Location> playerspawns) {
		this.playerspawns = playerspawns;
	}
	
	public void restoreWorld(){
		File file = new File(getWorldName());
		
		if(file != null){
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mv remove " + getWorldName());
			for(String filename : file.list()){
				File f = new File(file.getPath() + "/" + filename);
				f.delete();
			}
		}
		
		try{
			File file2 = new File("plugins/MiniGame Worlds", getWorldName());
			new File(getWorldName() + "/data").mkdirs();
			new File(getWorldName() + "/region").mkdirs();
			for(String filename : file2.list()){
				try{
					Files.copy(new File(file2.getPath() + "/" + filename), new File(getWorldName() + "/" + filename));
				}catch(FileNotFoundException ex){
					File file3 = new File(file2.getPath() + "/" + filename);
					for(String filename2 : file3.list()){
						Files.copy(new File(file3.getPath() + "/" + filename2), new File(getWorldName() + "/" + filename + "/" + filename2));
					}
				}
			}
		}catch(IOException e){
			e.printStackTrace();
		}
		
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mv import " + getWorldName() + " normal -g OrbitMinesEmptyWorldGenerator");
		setWorld(Bukkit.getWorld(getWorldName()));
	}
}
