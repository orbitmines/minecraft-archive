package me.O_o_Fadi_o_O.OrbitMines.managers;

import me.O_o_Fadi_o_O.OrbitMines.utils.OMPlayer;
import me.O_o_Fadi_o_O.OrbitMines.utils.ServerData;
import me.O_o_Fadi_o_O.OrbitMines.utils.Utils.Server;
import me.O_o_Fadi_o_O.OrbitMines.utils.Utils.StaffRank;
import me.O_o_Fadi_o_O.OrbitMines.utils.Utils.VIPRank;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class ScoreboardManager {

	public static int i;
	public static String title;
	
	@SuppressWarnings("deprecation")
	public static void setScoreboard(OMPlayer omp){
		org.bukkit.scoreboard.ScoreboardManager sm = Bukkit.getScoreboardManager();
		Scoreboard b = sm.getNewScoreboard();

		if(ServerData.isServer(Server.HUB)){
			if(omp.hasScoreboardEnabled()){
				Objective o = b.registerNewObjective("Hub", "Hub2");
				o.setDisplayName("§6§lOrbitMines");
				o.setDisplaySlot(DisplaySlot.SIDEBAR);
			
				Score score1 = o.getScore("");
				score1.setScore(12);
	
				Team omt = b.registerNewTeam("OMT");
				omt.setSuffix(" Tokens");
				OfflinePlayer omt1 = Bukkit.getServer().getOfflinePlayer("§e§lOrbitMines");
				omt.addPlayer(omt1);
				
				Score score2 = o.getScore(omt1.getName());
				score2.setScore(11);
	
				if(omp.isLoaded()){
					Score score3 = o.getScore(" " + omp.getOrbitMinesTokens() + "  ");
					score3.setScore(10);
				}
				else{
					Score score3 = o.getScore(" " + "Loading..." + "  ");
					score3.setScore(10);
				}
			
				Score score4 = o.getScore(" ");
				score4.setScore(9);
	
				Score score5 = o.getScore("§b§lVIP Points");
				score5.setScore(8);

				if(omp.isLoaded()){
					Score score6 = o.getScore(" " + omp.getVIPPoints() + "");
					score6.setScore(7);
				}
				else{
					Score score6 = o.getScore(" " + "Loading..." + "");
					score6.setScore(7);
				}
	
				Score score7 = o.getScore("  ");
				score7.setScore(6);
				
				Team coin = b.registerNewTeam("Coins");
				coin.setSuffix(" Coins");
				OfflinePlayer coins1 = Bukkit.getServer().getOfflinePlayer("§f§lMiniGame");
				coin.addPlayer(coins1);
				
				Score score8 = o.getScore(coins1.getName());
				score8.setScore(5);
				
				if(omp.isLoaded()){
					Score score9 = o.getScore(" " + omp.getMiniGameCoins() + " ");
					score9.setScore(4);
				}
				else{
					Score score9 = o.getScore(" " + "Loading..." + " ");
					score9.setScore(4);
				}
		
				Score score10 = o.getScore("   ");
				score10.setScore(3);
			
				Score score11 = o.getScore("§c§lRank");
				score11.setScore(2);
				
				Score score12 = o.getScore(" " + omp.getRankString());
				score12.setScore(1);
				
				Score score13 = o.getScore("    ");
				score13.setScore(0);
				
				o.setDisplayName(title);
			}
			
			updateRankTeams(b);
		}
		
		omp.getPlayer().setScoreboard(b);
	}
	
	public static void setNextTitle(){
		i++;
		
		if(ServerData.isServer(Server.HUB)){	
			if(i == 1){title = "§6§lOrbitMines§4§lNetwork";}
			if(i == 7){title = "§e§lO§6§lrbitMines§4§lNetwork";}
			if(i == 8){title = "§e§lOr§6§lbitMines§4§lNetwork";}
			if(i == 9){title = "§e§lOrb§6§litMines§4§lNetwork";}
			if(i == 10){title = "§e§lOrbi§6§ltMines§4§lNetwork";}
			if(i == 11){title = "§e§lOrbit§6§lMines§4§lNetwork";}
			if(i == 12){title = "§e§lOrbitM§6§lines§4§lNetwork";}
			if(i == 13){title = "§e§lOrbitMi§6§lnes§4§lNetwork";}
			if(i == 14){title = "§e§lOrbitMin§6§les§4§lNetwork";}
			if(i == 15){title = "§e§lOrbitMine§6§ls§4§lNetwork";}
			if(i == 16){title = "§e§lOrbitMines§4§lNetwork";}
			if(i == 17){title = "§e§lOrbitMines§c§lN§4§letwork";}
			if(i == 18){title = "§e§lOrbitMines§c§lNe§4§ltwork";}
			if(i == 19){title = "§e§lOrbitMines§c§lNet§4§lwork";}
			if(i == 20){title = "§e§lOrbitMines§c§lNetw§4§lork";}
			if(i == 21){title = "§e§lOrbitMines§c§lNetwo§4§lrk";}
			if(i == 22){title = "§e§lOrbitMines§c§lNetwor§4§lk";}
			if(i == 23){title = "§e§lOrbitMines§c§lNetwork";}
			if(i == 29){title = "§6§lO§e§lrbitMines§c§lNetwork";}
			if(i == 30){title = "§6§lOr§e§lbitMines§c§lNetwork";}
			if(i == 31){title = "§6§lOrb§e§litMines§c§lNetwork";}
			if(i == 32){title = "§6§lOrbi§e§ltMines§c§lNetwork";}
			if(i == 33){title = "§6§lOrbit§e§lMines§c§lNetwork";}
			if(i == 34){title = "§6§lOrbitM§e§lines§c§lNetwork";}
			if(i == 35){title = "§6§lOrbitMi§e§lnes§c§lNetwork";}
			if(i == 36){title = "§6§lOrbitMin§e§les§c§lNetwork";}
			if(i == 37){title = "§6§lOrbitMine§e§ls§c§lNetwork";}
			if(i == 38){title = "§6§lOrbitMines§c§lNetwork";}
			if(i == 39){title = "§6§lOrbitMines§4§lN§c§letwork";}
			if(i == 40){title = "§6§lOrbitMines§4§lNe§c§ltwork";}
			if(i == 41){title = "§6§lOrbitMines§4§lNet§c§lwork";}
			if(i == 42){title = "§6§lOrbitMines§4§lNetw§c§lork";}
			if(i == 43){title = "§6§lOrbitMines§4§lNetwo§c§lrk";}
			if(i == 44){title = "§6§lOrbitMines§4§lNetwor§c§lk";}
			if(i == 45){title = "§6§lOrbitMines§4§lNetwork"; i = 0;}
		}
	}
	
	private static void updateRankTeams(Scoreboard b){
		Team IronVIP = b.registerNewTeam("IronVIPHub");
		IronVIP.setPrefix("§7§lIron §f");
		Team GoldVIP = b.registerNewTeam("GoldVIPHub");
		GoldVIP.setPrefix("§6§lGold §f");
		Team DiamondVIP = b.registerNewTeam("DiamondVIPHub");
		DiamondVIP.setPrefix("§9§lDiamond §f");
		Team EmeraldVIP = b.registerNewTeam("EmeraldVIPHub");
		EmeraldVIP.setPrefix("§a§lEmerald §f");
		Team Builder = b.registerNewTeam("BuilderHub");
		Builder.setPrefix("§d§lBuilder §f");
		Team Moderator = b.registerNewTeam("ModeratorHub");
		Moderator.setPrefix("§b§lMod §f");
		Team Owner = b.registerNewTeam("OwnerHub");
		Owner.setPrefix("§4§lOwner §f");
		
		for(Player player : Bukkit.getOnlinePlayers()){
			OMPlayer omplayer = OMPlayer.getOMPlayer(player);
			
			StaffRank staff = omplayer.getStaffRank();
			VIPRank vip = omplayer.getVIPRank();
			
			if(staff == StaffRank.Owner){
				Owner.addPlayer(player);
			}
			else if(staff == StaffRank.Moderator){
				Moderator.addPlayer(player);
			}
			else if(staff == StaffRank.Builder){
				Builder.addPlayer(player);
			}
			else if(vip == VIPRank.Emerald_VIP){
				EmeraldVIP.addPlayer(player);
			}
			else if(vip == VIPRank.Diamond_VIP){
				DiamondVIP.addPlayer(player);
			}
			else if(vip == VIPRank.Gold_VIP){
				GoldVIP.addPlayer(player);
			}
			else if(vip == VIPRank.Iron_VIP){
				IronVIP.addPlayer(player);
			}
			else{}
		}
	}
}
