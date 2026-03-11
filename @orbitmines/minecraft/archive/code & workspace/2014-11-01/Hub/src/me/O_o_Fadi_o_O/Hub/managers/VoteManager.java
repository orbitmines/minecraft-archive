package me.O_o_Fadi_o_O.Hub.managers;

import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class VoteManager {

	public static void registerVote(Player p){
		giveVoteReward(p);
		sendVoteBroadCast(p);
	}
	
	public static void giveVoteReward(Player p){
		
		try {
			DatabaseManager.addOMT(p, 1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
				player.sendMessage("§6§lOrbitMines§b§lVote §8| §7Your reward in the §3§lHub§7 Server:");
				player.sendMessage("§6§lOrbitMines§b§lVote §8| §7");
				player.sendMessage("§6§lOrbitMines§b§lVote §8| §7  - §e§l1 OrbitMines Token");
				player.sendMessage("§6§lOrbitMines§b§lVote §8| §7");
				player.sendMessage("§6§lOrbitMines§b§lVote §8| §7Your Total Votes this Month: §b§l" + StorageManager.votes.get(p.getName()));
				player.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 1);
			}
		}
	}
}
