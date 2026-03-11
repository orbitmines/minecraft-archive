package me.O_o_Fadi_o_O.UHC.managers;

import java.util.ArrayList;

import me.O_o_Fadi_o_O.UHC.Start;
import me.O_o_Fadi_o_O.UHC.utils.GameState;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Manager {
	
	public static ArrayList<Player> Players = new ArrayList<Player>();
	public static ArrayList<Player> Spectators = new ArrayList<Player>();
	
	public static int Seconds = 0;
	public static int Minutes = 45;
	
	public static Player Winner = null;
	
	public static int BorderSize = 1000;
	
	public static void clearInventory(Player p){
		p.getInventory().setHelmet(null);
		p.getInventory().setChestplate(null);
		p.getInventory().setLeggings(null);
		p.getInventory().setBoots(null);
		
		p.getInventory().clear();
	}
	
	public static void startGame(){
		Start.state = GameState.NOPVP;
		Seconds = 1;
		Minutes = 45;
		Bukkit.broadcastMessage("");
		Bukkit.broadcastMessage(" §7- Gather Recources");
		Bukkit.broadcastMessage(" §7- §cPvP §aEnabled§7 after §345 minutes§7.");
		Bukkit.broadcastMessage(" §7- The §eBorder§7 shrinks §61 block§7 per second when §cPvP§7 is §aEnabled");
		Bukkit.broadcastMessage(" §7- Be the last player alive to §awin§7!");
		Bukkit.broadcastMessage("");
		for(Player p : Bukkit.getOnlinePlayers()){
			clearInventory(p);
			p.playSound(p.getLocation(), Sound.WITHER_DEATH, 5, 1);
			
			ItemStack item = new ItemStack(Material.COOKED_BEEF, 10);
			p.getInventory().setItem(0, item);
		}
	}
	
	public static void endGame(Player p){
		Winner = p;
		Start.state = GameState.ENDING;
		
		Bukkit.broadcastMessage("");
		Bukkit.broadcastMessage(" §7- Winner: §a§l" + p.getName());
		Bukkit.broadcastMessage("");
		for(Player player : Bukkit.getOnlinePlayers()){
			clearInventory(player);
			player.playSound(player.getLocation(), Sound.WITHER_DEATH, 5, 1);
		}
	}
	
	public static void setSpectator(final Player p){
		
		Spectators.add(p);
	}
	
	public static void setPvP(){
		
		Start.state = GameState.PVP;
		Bukkit.broadcastMessage("");
		Bukkit.broadcastMessage("");
		Bukkit.broadcastMessage("");
		Bukkit.broadcastMessage("§c§l PvP §a§lENABLED");
		Bukkit.broadcastMessage("");
		Bukkit.broadcastMessage("§e§lThe Border will shrink §6§l1 Block§e§l every second!");
		Bukkit.broadcastMessage("§e§lThe Border size will be §6§l50 Blocks §e§lafter 15 minutes!");
		Bukkit.broadcastMessage("");
		for(Player p : Bukkit.getOnlinePlayers()){
			p.playSound(p.getLocation(), Sound.WITHER_DEATH, 5, 1);
		}
		
	}
	
	public static String getBroadcastMessage(){
		
		String s = null;		
		if(Start.state == GameState.ENDING){
			//TODO: BACK TO HUB
		}
		else if(Start.state == GameState.LOBBY){
			s = "§3§lEvent §8| §7Starting in §3" + Seconds + "§7...";
		}
		else if(Start.state == GameState.NOPVP){
			s = "§3§lEvent §8| §7PvP §aEnabled§7 in §3" + Minutes + "m " + Seconds + "s§7!";
		}
		else if(Start.state == GameState.PVP){
			//TODO: BORDER
		}
		else if(Start.state == GameState.WARMUP){
			s = "§3§lEvent §8| §7Starting in §3" + Seconds + "§7...";
		}
		else{
			
		}
		
		return s;
	}
}
