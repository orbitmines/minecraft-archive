package me.O_o_Fadi_o_O.UHC.scoreboard;

import me.O_o_Fadi_o_O.UHC.Start;
import me.O_o_Fadi_o_O.UHC.managers.Manager;
import me.O_o_Fadi_o_O.UHC.utils.GameState;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

public class Board {

	public static int i = 0;
	@SuppressWarnings("deprecation")
	public static void setScoreboard(Player p){
		
		if(Start.state != GameState.ENDING){
			ScoreboardManager sb = Bukkit.getScoreboardManager();
			Scoreboard b = sb.getNewScoreboard();	
			Objective o = b.registerNewObjective("Test1", "Test2");
		
			o.setDisplaySlot(DisplaySlot.SIDEBAR);
			o.setDisplayName("§6§lOrbitMines");
			
			if(Start.state == GameState.LOBBY){
				Score score1 = o.getScore("");
				score1.setScore(5);	
				
				if(i == 1){
					Score score2 = o.getScore("§6§lWaiting.");
					score2.setScore(4);	
				}
				else if(i == 2){
					Score score2 = o.getScore("§6§lWaiting..");
					score2.setScore(4);	
				}
				else if(i == 3){
					Score score2 = o.getScore("§6§lWaiting...");
					score2.setScore(4);	
				}
				
				Score score3 = o.getScore(" ");
				score3.setScore(3);	
				
				Score score4 = o.getScore("§a§lPlayers");
				score4.setScore(2);	
				
				Score score5 = o.getScore(" " + Manager.Players.size());
				score5.setScore(1);	
				
				Score score6 = o.getScore("  ");
				score6.setScore(0);		
			}
			if(Start.state == GameState.WARMUP){
				Score score1 = o.getScore("");
				score1.setScore(6);	
			
				Score score2 = o.getScore("§6§lTime Left");
				score2.setScore(5);	
				
				Score score3 = o.getScore(" " + Manager.Minutes + "m " + Manager.Seconds + "s");
				score3.setScore(4);	
				
				Score score4 = o.getScore(" ");
				score4.setScore(3);	
				
				Score score5 = o.getScore("§a§lPlayers");
				score5.setScore(2);	
				
				Score score6 = o.getScore(" " + Manager.Players.size());
				score6.setScore(1);	
				
				Score score7 = o.getScore("  ");
				score7.setScore(0);	
			}
			if(Start.state == GameState.NOPVP){
				
				Team tfp1 = b.registerNewTeam("PvPDisabled");
				tfp1.setSuffix("§c§lDISABLED");
				OfflinePlayer fp1 = Bukkit.getServer().getOfflinePlayer("§c§lPvP ");
				tfp1.addPlayer(fp1);
				
				Team tfp2 = b.registerNewTeam("DistanceBorder");
				tfp2.setSuffix("Distance");
				OfflinePlayer fp2 = Bukkit.getServer().getOfflinePlayer("§e§lBorder ");
				tfp2.addPlayer(fp2);
				
				Team tfp3 = b.registerNewTeam("DistanceSpawn");
				tfp3.setSuffix("Distance");
				OfflinePlayer fp3 = Bukkit.getServer().getOfflinePlayer("§9§lSpawn ");
				tfp3.addPlayer(fp3);
				
				Team tfp4 = b.registerNewTeam("SpawnLocation");
				tfp4.setSuffix(" Location");
				OfflinePlayer fp4 = Bukkit.getServer().getOfflinePlayer("§b§lSpawn");
				tfp4.addPlayer(fp4);
				
				Score score1 = o.getScore("");
				score1.setScore(14);	
				
				Score score2 = o.getScore(fp1.getName());
				score2.setScore(13);	
				
				Score score3 = o.getScore(" ");
				score3.setScore(12);	
				
				Score score4 = o.getScore("§a§lAlive");
				score4.setScore(11);	
				
				Score score5 = o.getScore(" " + Manager.Players.size());
				score5.setScore(10);	
				
				Score score6 = o.getScore("  ");
				score6.setScore(9);
				
				Score score7 = o.getScore(fp2.getName());
				score7.setScore(8);	
				
				int bDistance = 0;
				int xB = p.getLocation().getBlockX();
				int zB = p.getLocation().getBlockZ();
				
				if(xB < 0){
					xB = -xB;
				}
				if(zB < 0){
					zB = -zB;
				}
				
				if(xB <= zB){
					bDistance = zB;
				}
				else{
					bDistance = xB;
				}
				
				bDistance = Manager.BorderSize - bDistance;
				
				if(bDistance < 0){
					if(Manager.Players.contains(p)){
						p.sendMessage("§4§lYOU ARE OUT OF THE §e§lBORDER§4§l!!!");
						p.damage(1);
					}
				}
				
				if(bDistance != 1){
					Score score8 = o.getScore(" " + bDistance + " Blocks");
					score8.setScore(7);
				}
				else{
					Score score8 = o.getScore(" " + bDistance + " Block");
					score8.setScore(7);
				}
				
				Score score9 = o.getScore("   ");
				score9.setScore(6);
				
				Score score10 = o.getScore(fp3.getName());
				score10.setScore(5);	
				
				int sDistance = 0;
				int xS = p.getLocation().getBlockX();
				int zS = p.getLocation().getBlockZ();
				
				if(xS < 0){
					xS = -xS;
				}
				if(zS < 0){
					zS = -zS;
				}
				
				if(xS <= zS){
					sDistance = zS;
				}
				else{
					sDistance = xS;
				}
				
				if(sDistance != 1){
					Score score11 = o.getScore(" " + sDistance + " Blocks ");
					score11.setScore(4);
				}
				else{
					Score score11 = o.getScore(" " + sDistance + " Block ");
					score11.setScore(4);
				}
				
				Score score12 = o.getScore("    ");
				score12.setScore(3);
				
				Score score13 = o.getScore(fp4.getName());
				score13.setScore(2);	
				
				Score score14 = o.getScore(" x: 0, z: 0");
				score14.setScore(1);	
				
				Score score15 = o.getScore("     ");
				score15.setScore(0);
			}
			if(Start.state == GameState.PVP){
				
				Team tfp1 = b.registerNewTeam("PvPEnabled");
				tfp1.setSuffix("§a§lENABLED");
				OfflinePlayer fp1 = Bukkit.getServer().getOfflinePlayer("§c§lPvP ");
				tfp1.addPlayer(fp1);
				
				Team tfp2 = b.registerNewTeam("DistanceBorder");
				tfp2.setSuffix("Distance");
				OfflinePlayer fp2 = Bukkit.getServer().getOfflinePlayer("§e§lBorder ");
				tfp2.addPlayer(fp2);
				
				Team tfp3 = b.registerNewTeam("DistanceSpawn");
				tfp3.setSuffix("Distance");
				OfflinePlayer fp3 = Bukkit.getServer().getOfflinePlayer("§9§lSpawn ");
				tfp3.addPlayer(fp3);
				
				Team tfp4 = b.registerNewTeam("SpawnLocation");
				tfp4.setSuffix(" Location");
				OfflinePlayer fp4 = Bukkit.getServer().getOfflinePlayer("§b§lSpawn");
				tfp4.addPlayer(fp4);
				
				Score score1 = o.getScore("");
				score1.setScore(14);	
				
				Score score2 = o.getScore(fp1.getName());
				score2.setScore(13);	
				
				Score score3 = o.getScore(" ");
				score3.setScore(12);	
				
				Score score4 = o.getScore("§a§lAlive");
				score4.setScore(11);	
				
				Score score5 = o.getScore(" " + Manager.Players.size());
				score5.setScore(10);	
				
				Score score6 = o.getScore("  ");
				score6.setScore(9);
				
				Score score7 = o.getScore(fp2.getName());
				score7.setScore(8);	
				
				int bDistance = 0;
				int xB = p.getLocation().getBlockX();
				int zB = p.getLocation().getBlockZ();
				
				if(xB < 0){
					xB = -xB;
				}
				if(zB < 0){
					zB = -zB;
				}
				
				if(xB <= zB){
					bDistance = zB;
				}
				else{
					bDistance = xB;
				}
				
				bDistance = Manager.BorderSize - bDistance;
				
				if(bDistance < 0){
					if(Manager.Players.contains(p)){
						p.sendMessage("§4§lYOU ARE OUT OF THE §e§lBORDER§4§l!!!");
						p.damage(1);
					}
				}
				
				if(bDistance != 1){
					Score score8 = o.getScore(" " + bDistance + " Blocks");
					score8.setScore(7);	
				}
				else{
					Score score8 = o.getScore(" " + bDistance + " Block");
					score8.setScore(7);	
				}
				
				Score score9 = o.getScore("   ");
				score9.setScore(6);
				
				Score score10 = o.getScore(fp3.getName());
				score10.setScore(5);	
				
				int sDistance = 0;
				int xS = p.getLocation().getBlockX();
				int zS = p.getLocation().getBlockZ();
				
				if(xS < 0){
					xS = -xS;
				}
				if(zS < 0){
					zS = -zS;
				}
				
				if(xS <= zS){
					sDistance = zS;
				}
				else{
					sDistance = xS;
				}
				
				if(sDistance != 1){
					Score score11 = o.getScore(" " + sDistance + " Blocks ");
					score11.setScore(4);
				}
				else{
					Score score11 = o.getScore(" " + sDistance + " Block ");
					score11.setScore(4);
				}
				
				Score score12 = o.getScore("    ");
				score12.setScore(3);
				
				Score score13 = o.getScore(fp4.getName());
				score13.setScore(2);	
				
				Score score14 = o.getScore(" x: 0, z: 0");
				score14.setScore(1);	
				
				Score score15 = o.getScore("     ");
				score15.setScore(0);
			}
			
			Team Players = b.registerNewTeam("Players");
			
			Team Spectators = b.registerNewTeam("Spectators");
			Spectators.setPrefix("§c");
			
			Players.setCanSeeFriendlyInvisibles(false);
			Players.setAllowFriendlyFire(true);
			Spectators.setCanSeeFriendlyInvisibles(true);
			Spectators.setAllowFriendlyFire(false);
			
			for(Player player : Bukkit.getOnlinePlayers()){
				if(Manager.Players.contains(player)){
					Players.addPlayer(player);
				}
				else{
					Spectators.addPlayer(player);
					if(Manager.Players.contains(p)){
						p.hidePlayer(player);
					}
				}
			}
			
			o.setDisplayName("§6§lOrbitMines §3§lEvent");
			p.setScoreboard(b);
		}
	}
}
