package me.O_o_Fadi_o_O.SpigotSpleef.utils;

import java.util.List;

import me.O_o_Fadi_o_O.SpigotSpleef.managers.StorageManager;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class ArenaSign {

	private Location location;
	private Arena arena;
	
	public ArenaSign(Location location, Arena arena){
		this.location = location;
		this.arena = arena;
	}
	
	public Location getLocation(){
		return this.location;
	}
	public void setLocation(Location location){
		this.location = location;
	}
	
	public Arena getArena(){
		return this.arena;
	}
	public void setArena(Arena arena){
		this.arena = arena;
	}
	
	public void update(){
		Block b = location.getWorld().getBlockAt(getLocation());
		if(b.getType() != Material.SIGN_POST && b.getType() != Material.WALL_SIGN){
			b.setType(Material.WALL_SIGN);
		}
		
		if(getArena().isStatus(SpleefStatus.WAITING) || getArena().isStatus(SpleefStatus.STARTING)){
			if(getArena().enoughPlayers()){
				if(getArena().isFull()){
					sendUpdate(listToArray(StorageManager.signsfull));
				}
				else{
					sendUpdate(listToArray(StorageManager.signsenoughplayers));
				}
			}
			else{
				sendUpdate(listToArray(StorageManager.signswaiting));
			}
		}
		else if(getArena().isStatus(SpleefStatus.WARMUP) || getArena().isStatus(SpleefStatus.INGAME)){
			sendUpdate(listToArray(StorageManager.signsingame));
		}
		else{
			sendUpdate(listToArray(StorageManager.signsrestarting));
		}
	}
	
	public String[] listToArray(List<String> list){
		String[] newlist = new String[4];
		newlist[0] = getReplacedLine(list.get(0).replace("&", "§"));
		newlist[1] = getReplacedLine(list.get(1).replace("&", "§"));
		newlist[2] = getReplacedLine(list.get(2).replace("&", "§"));
		newlist[3] = getReplacedLine(list.get(3).replace("&", "§"));
		return newlist;
	}
	
	public void sendUpdate(String[] lines){
		for(Player p : Bukkit.getOnlinePlayers()){
			if(p.getWorld().getName().equals(getLocation().getWorld().getName())){
				if(p.getLocation().distance(getLocation()) <= 16){
					p.sendSignChange(getLocation(), lines);
				}
			}
		}
	}
	
	public boolean isArena(Arena arena){
		if(this.arena.getArenaID() == arena.getArenaID()){
			return true;
		}
		return false;
	}
	
	public String getReplacedLine(String line){
		return line.replace("%minutes%", "" + getArena().getMinutes()).replace("%seconds%", "" + getArena().getSeconds()).replace("%min-players%", "" + getArena().getMinPlayers()).replace("%players%", "" + getArena().getPlayers().size()).replace("%max-players%", "" + getArena().getMaxPlayers()).replace("%arena-id%", "" + getArena().getArenaID()).replace("%spectators%", "" + getArena().getSpectators().size()).replace("%map-name%", getArena().getMap().getName()).replace("%animated-dots%", StorageManager.arenaselector.getAnimatedDotsString());
	}
	
	public static ArenaSign getArenaSign(Location location){
		for(ArenaSign arenasign : StorageManager.signs){
			if(arenasign.getLocation().getBlockX() == location.getBlockX() && arenasign.getLocation().getBlockY() == location.getBlockY() && arenasign.getLocation().getBlockZ() == location.getBlockZ()){
				return arenasign;
			}
		}
		return null;
	}
	public static boolean isSignArenaSign(Location location){
		for(ArenaSign arenasign : StorageManager.signs){
			if(arenasign.getLocation().getBlockX() == location.getBlockX() && arenasign.getLocation().getBlockY() == location.getBlockY() && arenasign.getLocation().getBlockZ() == location.getBlockZ()){
				return true;
			}
		}
		return false;
	}
}
