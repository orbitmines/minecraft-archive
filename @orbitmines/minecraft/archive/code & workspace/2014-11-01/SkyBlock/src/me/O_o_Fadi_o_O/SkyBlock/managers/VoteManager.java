package me.O_o_Fadi_o_O.SkyBlock.managers;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class VoteManager {

	public static void registerVote(Player p){
		giveVoteReward(p);
		sendVoteBroadCast(p);
	}
	
	public static void giveVoteReward(Player p){
		
		{
			ItemStack item = new ItemStack(Material.COBBLESTONE, 32);
			p.getInventory().addItem(item);
		}
		{
			ItemStack item = new ItemStack(Material.IRON_INGOT, 1);
			p.getInventory().addItem(item);
		}
		{
			ItemStack item = new ItemStack(Material.COAL, 4);
			p.getInventory().addItem(item);
		}
		
	}
	
	public static void sendVoteBroadCast(Player p){
		
		for(Player player : Bukkit.getOnlinePlayers()){
			if(player != p){
				player.sendMessage("§6§lOrbitMines§b§lVote §8| §b§l" + p.getName() + "§7 has voted with §b§l/vote");
			}
			else{
				player.sendMessage("");
				player.sendMessage("§6§lOrbitMines§b§lVote §8| §7Thank you, §b§l" + p.getName() + " §7for your §b§lVote§7!");
				player.sendMessage("§6§lOrbitMines§b§lVote §8| §7Your reward in the §5§lSkyBlock§7 Server:");
				player.sendMessage("§6§lOrbitMines§b§lVote §8| §7");
				player.sendMessage("§6§lOrbitMines§b§lVote §8| §7  - §8§l32 Cobblestone");
				player.sendMessage("§6§lOrbitMines§b§lVote §8| §7  - §7§l1 Iron Ingot");
				player.sendMessage("§6§lOrbitMines§b§lVote §8| §7  - §0§l4 Coal");
				player.sendMessage("§6§lOrbitMines§b§lVote §8| §7");
				player.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 1);
			}
		}
	}
}
