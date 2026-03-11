package me.O_o_Fadi_o_O.Survival.managers;

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
			ItemStack item = new ItemStack(Material.DIAMOND, 1);
			p.getInventory().addItem(item);
		}
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "adjustbonusclaimblocks " + p.getName() +" 100");
		
	}
	
	public static void sendVoteBroadCast(Player p){
		
		for(Player player : Bukkit.getOnlinePlayers()){
			if(player != p){
				player.sendMessage("§6§lOrbitMines§b§lVote §8| §b§l" + p.getName() + "§7 has voted with §b§l/vote");
			}
			else{
				player.sendMessage("");
				player.sendMessage("§6§lOrbitMines§b§lVote §8| §7Thank you, §b§l" + p.getName() + " §7for your §b§lVote§7!");
				player.sendMessage("§6§lOrbitMines§b§lVote §8| §7Your reward in the §a§lSurvival§7 Server:");
				player.sendMessage("§6§lOrbitMines§b§lVote §8| §7");
				player.sendMessage("§6§lOrbitMines§b§lVote §8| §7  - §b§l1 Diamond");
				player.sendMessage("§6§lOrbitMines§b§lVote §8| §7  - §7§l100 Claimblocks");
				player.sendMessage("§6§lOrbitMines§b§lVote §8| §7");
				player.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 1);
			}
		}
	}
}
