package om.api.utils;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class PlayerUtils {
	
	public static Player getPlayer(String playername){
		for(Player player : Bukkit.getOnlinePlayers()){
			if(player.getName().equalsIgnoreCase(playername)){
				return player;
			}
		}
		return null;
	}
	
	public static Player getPlayer(UUID uuid){
		for(Player player : Bukkit.getOnlinePlayers()){
			if(player.getUniqueId().toString().equals(uuid.toString())){
				return player;
			}
		}
		return null;
	}
	
	public static boolean inGameMode(GameMode gamemode, String a[]){
		switch(gamemode){
			case ADVENTURE:
				return a[1].equalsIgnoreCase("a") || a[1].equalsIgnoreCase("2") || a[1].equalsIgnoreCase("adventure");
			case CREATIVE:
				return a[1].equalsIgnoreCase("c") || a[1].equalsIgnoreCase("1") || a[1].equalsIgnoreCase("creative");
			case SPECTATOR:
				return a[1].equalsIgnoreCase("spec") || a[1].equalsIgnoreCase("3") || a[1].equalsIgnoreCase("spectate");
			case SURVIVAL:
				return a[1].equalsIgnoreCase("s") || a[1].equalsIgnoreCase("0") || a[1].equalsIgnoreCase("survival");
			default:
				return false;
		}
	}
	
	public static int getEmptySlots(Inventory inventory){
		int amount = 0;
		for(ItemStack item : inventory.getContents()){
			if(item == null){
				amount++;
			}
		}
		return amount;
	}
	
	public static int getSlotsRequired(int itemamount, Material material){
		double slotsrequired = itemamount / material.getMaxStackSize();
		int required = (int) slotsrequired;
		
		if(slotsrequired - (int) slotsrequired > 0.0){
			required = (int) slotsrequired + 1;
		}
		if(required == 0){
			required = 1;
		}
		
		return required;
	}
}
