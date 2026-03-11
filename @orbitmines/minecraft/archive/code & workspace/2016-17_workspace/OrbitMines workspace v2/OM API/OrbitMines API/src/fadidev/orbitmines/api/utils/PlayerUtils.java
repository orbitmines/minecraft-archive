package fadidev.orbitmines.api.utils;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class PlayerUtils {
	
	public static Player getPlayer(String playerName){
		for(Player player : Bukkit.getOnlinePlayers()){
			if(player.getName().equalsIgnoreCase(playerName))
				return player;
		}
		return null;
	}
	
	public static Player getPlayer(UUID uuid){
		for(Player player : Bukkit.getOnlinePlayers()){
			if(player.getUniqueId().toString().equals(uuid.toString()))
				return player;
		}
		return null;
	}
	
	public static GameMode getGameMode(String a[]){
		if(a[1].equalsIgnoreCase("a") || a[1].equalsIgnoreCase("2") || a[1].equalsIgnoreCase("adventure"))
			return GameMode.ADVENTURE;
        else if(a[1].equalsIgnoreCase("c") || a[1].equalsIgnoreCase("1") || a[1].equalsIgnoreCase("creative"))
            return GameMode.CREATIVE;
        else if(a[1].equalsIgnoreCase("spec") || a[1].equalsIgnoreCase("3") || a[1].equalsIgnoreCase("spectate"))
			return GameMode.SPECTATOR;
        else if(a[1].equalsIgnoreCase("s") || a[1].equalsIgnoreCase("0") || a[1].equalsIgnoreCase("survival"))
            return GameMode.SURVIVAL;
		else
		    return null;
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
	
	public static int getSlotsRequired(int itemAmount, Material material){
		double slotsRequired = itemAmount / material.getMaxStackSize();
		int required = (int) slotsRequired;
		
		if(slotsRequired - (int) slotsRequired > 0.0)
			required = (int) slotsRequired + 1;

		if(required == 0)
			required = 1;
		
		return required;
	}
}
