package me.O_o_Fadi_o_O.OrbitMines.managers;

import me.O_o_Fadi_o_O.OrbitMines.utils.OMPlayer;
import me.O_o_Fadi_o_O.OrbitMines.utils.ServerData;
import me.O_o_Fadi_o_O.OrbitMines.utils.Utils.GameState;
import me.O_o_Fadi_o_O.OrbitMines.utils.Utils.MiniGameType;
import me.O_o_Fadi_o_O.OrbitMines.utils.Utils.Server;
import me.O_o_Fadi_o_O.OrbitMines.utils.Utils.StaffRank;
import me.O_o_Fadi_o_O.OrbitMines.utils.Utils.VIPRank;
import me.O_o_Fadi_o_O.OrbitMines.utils.creative.CreativePlayer;
import me.O_o_Fadi_o_O.OrbitMines.utils.kitpvp.KitPvPPlayer;
import me.O_o_Fadi_o_O.OrbitMines.utils.minigames.Arena;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
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
				score1.setScore(14);
	
				Team omt = b.registerNewTeam("OMT-Hub");
				omt.setSuffix(" Tokens");
				OfflinePlayer omt1 = Bukkit.getServer().getOfflinePlayer("§e§lOrbitMines");
				omt.addPlayer(omt1);
				
				Score score2 = o.getScore(omt1.getName());
				score2.setScore(13);
	
				if(omp.isLoaded()){
					Score score3 = o.getScore(" " + omp.getOrbitMinesTokens() + "  ");
					score3.setScore(12);
				}
				else{
					Score score3 = o.getScore(" " + "Loading..." + "  ");
					score3.setScore(12);
				}
			
				Score score4 = o.getScore(" ");
				score4.setScore(11);
	
				Score score5 = o.getScore("§b§lVIP Points");
				score5.setScore(10);

				if(omp.isLoaded()){
					Score score6 = o.getScore(" " + omp.getVIPPoints() + "");
					score6.setScore(9);
				}
				else{
					Score score6 = o.getScore(" " + "Loading..." + "");
					score6.setScore(9);
				}
	
				Score score7 = o.getScore("  ");
				score7.setScore(8);
				
				Team coin = b.registerNewTeam("Coins-Hub");
				coin.setSuffix(" Coins");
				OfflinePlayer coins1 = Bukkit.getServer().getOfflinePlayer("§f§lMiniGame");
				coin.addPlayer(coins1);
				
				Score score8 = o.getScore(coins1.getName());
				score8.setScore(7);
				
				if(omp.isLoaded()){
					Score score9 = o.getScore(" " + omp.getMiniGameCoins() + " ");
					score9.setScore(6);
				}
				else{
					Score score9 = o.getScore(" " + "Loading..." + " ");
					score9.setScore(6);
				}
		
				Score score10 = o.getScore("   ");
				score10.setScore(5);
			
				Score score11 = o.getScore("§c§lRank");
				score11.setScore(4);
				
				Score score12 = o.getScore(" " + omp.getRankString());
				score12.setScore(3);
				
				Score score13 = o.getScore("    ");
				score13.setScore(2);

				
				Team pcounter = b.registerNewTeam("PCounter-Hub");
				pcounter.setSuffix(" §f#" + ServerData.getHub().getPlayerCounter());
				OfflinePlayer pcounter1 = Bukkit.getServer().getOfflinePlayer("§d§lAll Players:");
				pcounter.addPlayer(pcounter1);
				
				Score score14 = o.getScore(pcounter1.getName());
				score14.setScore(1);
				
				Score score15 = o.getScore("     ");
				score15.setScore(0);
				
				o.setDisplayName(title);
			}
			
			updateRankTeams(b);
		}
		else if(ServerData.isServer(Server.KITPVP)){
			Objective o = b.registerNewObjective("KitPvP", "KitPvP2");
			o.setDisplayName("§6§lOrbitMines");
			o.setDisplaySlot(DisplaySlot.SIDEBAR);
		
			KitPvPPlayer kp = omp.getKitPvPPlayer();
			
			Score score1 = o.getScore("");
			score1.setScore(14);
			
			Team beststreak = b.registerNewTeam("KitPvP-Streak");
			OfflinePlayer streakp = Bukkit.getServer().getOfflinePlayer("§f§lCurrent ");
			if(kp != null){
				beststreak.setSuffix("Streak: §6§l" + kp.getCurrentStreak());
			}
			else{
				beststreak.setSuffix("Streak: §6§l" + 0);
			}
			beststreak.addPlayer(streakp);
			
			Score score2 = o.getScore(streakp.getName());
			score2.setScore(13);
			
			Score score3 = o.getScore(" ");
			score3.setScore(12);
			
			Score score4 = o.getScore("§7§lKit");
			score4.setScore(11);
			
			Team kit = b.registerNewTeam("KitPvP-Kit");
			OfflinePlayer kitp = null;
			if(kp != null && kp.getKitSelected() != null){
				kitp = Bukkit.getServer().getOfflinePlayer(" §b§l" + kp.getKitSelected().getName());
				kit.setSuffix(" §7(§aLvL " + kp.getKitLevelSelected() + "§7)");
				kit.addPlayer(kitp);
			}
			
			if(kitp != null){
				Score score5 = o.getScore(kitp.getName());
				score5.setScore(10);
			}
			else{
				kitp = Bukkit.getServer().getOfflinePlayer(" Selecting ");
				kit.setSuffix("Kit...");
				kit.addPlayer(kitp);
				
				Score score5 = o.getScore(kitp.getName());
				score5.setScore(10);
			}
			
			Score score6 = o.getScore("  ");
			score6.setScore(9);
			
			Score score7 = o.getScore("§6§lCoins");
			score7.setScore(8);
			
			if(kp != null){
				Score score8 = o.getScore(" " + kp.getMoney());
				score8.setScore(7);
			}
			else{
				Score score8 = o.getScore(" Loading...");
				score8.setScore(7);
			}
			
			Score score9 = o.getScore("   ");
			score9.setScore(6);
			
			Score score10 = o.getScore("§c§lKills");
			score10.setScore(5);
			
			if(kp != null){
				Score score11 = o.getScore(" " + kp.getKills() + " ");
				score11.setScore(4);
			}
			else{
				Score score11 = o.getScore(" Loading... ");
				score11.setScore(4);
			}
			
			Score score12 = o.getScore("    ");
			score12.setScore(3);
			
			Score score13 = o.getScore("§4§lDeaths");
			score13.setScore(2);
			
			if(kp != null){
				Score score14 = o.getScore(" " + kp.getDeaths() + "  ");
				score14.setScore(1);
			}
			else{
				Score score14 = o.getScore(" Loading...  ");
				score14.setScore(1);
			}
			
			Score score15 = o.getScore("     ");
			score15.setScore(0);
			
			o.setDisplayName(title);
			updateRankTeams(b);
		}
		else if(ServerData.isServer(Server.CREATIVE)){
			Objective o = b.registerNewObjective("Creative", "Creative2");
			o.setDisplayName("§6§lOrbitMines");
			o.setDisplaySlot(DisplaySlot.SIDEBAR);
		
			Score score1 = o.getScore("");
			score1.setScore(9);

			Team omt = b.registerNewTeam("OMT-Crea");
			omt.setSuffix(" Tokens");
			OfflinePlayer omt1 = Bukkit.getServer().getOfflinePlayer("§e§lOrbitMines");
			omt.addPlayer(omt1);
			
			Score score2 = o.getScore(omt1.getName());
			score2.setScore(8);

			if(omp.isLoaded()){
				Score score3 = o.getScore(" " + omp.getOrbitMinesTokens() + "  ");
				score3.setScore(7);
			}
			else{
				Score score3 = o.getScore(" " + "Loading..." + "  ");
				score3.setScore(7);
			}
		
			Score score4 = o.getScore(" ");
			score4.setScore(6);

			Score score5 = o.getScore("§b§lVIP Points");
			score5.setScore(5);

			if(omp.isLoaded()){
				Score score6 = o.getScore(" " + omp.getVIPPoints() + "");
				score6.setScore(4);
			}
			else{
				Score score6 = o.getScore(" " + "Loading..." + "");
				score6.setScore(4);
			}

			Score score7 = o.getScore("  ");
			score7.setScore(3);
			
			Score score8 = o.getScore("§d§lPlot Number");
			score8.setScore(2);
			
			CreativePlayer cp = omp.getCreativePlayer();
			
			if(cp.hasPlot()){
				Score score9 = o.getScore(" " + cp.getPlot().getPlotID() + " ");
				score9.setScore(1);
			}
			else{
				Score score9 = o.getScore(" /plot h");
				score9.setScore(1);
			}
	
			Score score10 = o.getScore("   ");
			score10.setScore(0);
			
			o.setDisplayName(title);
			
			updateRankTeams(b);
		}
		else if(ServerData.isServer(Server.SURVIVAL)){
			Objective o = b.registerNewObjective("Survival", "Survival2");
			o.setDisplayName("§6§lOrbitMines");
			o.setDisplaySlot(DisplaySlot.SIDEBAR);
		
			Score score1 = o.getScore("");
			score1.setScore(12);

			Team omt = b.registerNewTeam("OMTSurv");
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
				Score score6 = o.getScore(" " + omp.getVIPPoints()+ "");
				score6.setScore(7);
			}
			else{
				Score score6 = o.getScore(" " + "Loading..." + "");
				score6.setScore(7);
			}

			Score score7 = o.getScore("  ");
			score7.setScore(6);
			
			Score score11 = o.getScore("§2§lMoney");
			score11.setScore(2);
			
			if(omp.isLoaded()){
				Score score12 = o.getScore(" " + omp.getSurvivalPlayer().getMoney() + "$");
				score12.setScore(1);
			}
			else{
				Score score12 = o.getScore(" " + "Loading..." + " ");
				score12.setScore(1);
			}
			
			Score score13 = o.getScore("    ");
			score13.setScore(0);
			
			o.setDisplayName(title);
			
			updateRankTeams(b);
		}
		else if(ServerData.isServer(Server.SKYBLOCK)){
			Objective o = b.registerNewObjective("SkyBlock", "SkyBlock2");
			o.setDisplayName("§6§lOrbitMines");
			o.setDisplaySlot(DisplaySlot.SIDEBAR);
			
			Score score1 = o.getScore("");
			score1.setScore(9);

			Team omt = b.registerNewTeam("OMTSky");
			omt.setSuffix(" Tokens");
			OfflinePlayer omt1 = Bukkit.getServer().getOfflinePlayer("§e§lOrbitMines");
			omt.addPlayer(omt1);
			
			Score score2 = o.getScore(omt1.getName());
			score2.setScore(8);

			if(omp.isLoaded()){
				Score score3 = o.getScore(" " + omp.getOrbitMinesTokens() + " ");
				score3.setScore(7);
			}
			else{
				Score score3 = o.getScore(" " + "Loading..." + "  ");
				score3.setScore(7);
			}
		
			Score score4 = o.getScore(" ");
			score4.setScore(6);

			Score score5 = o.getScore("§b§lVIP Points");
			score5.setScore(5);

			if(omp.isLoaded()){
				Score score6 = o.getScore(" " + omp.getVIPPoints() + "");
				score6.setScore(4);
			}
			else{
				Score score6 = o.getScore(" " + "Loading..." + "");
				score6.setScore(4);
			}

			Score score7 = o.getScore("  ");
			score7.setScore(3);
			
			Score score8 = o.getScore("§d§lIsland Number");
			score8.setScore(2);

			if(omp.getSkyBlockPlayer().hasIsland()){
				Score score9 = o.getScore(" " + omp.getSkyBlockPlayer().getIsland().getIslandID() + "  ");
				score9.setScore(1);
			}
			else{
				Score score9 = o.getScore(" " + "/is h" + "");
				score9.setScore(1);
			}

			Score score10 = o.getScore("   ");
			score10.setScore(0);
			
			o.setDisplayName(title);
			
			updateRankTeams(b);
		}
		else if(ServerData.isServer(Server.MINIGAMES)){
			Arena arena = omp.getArena();
			
			if(arena != null){
				Objective o = b.registerNewObjective("MiniGames", "MiniGames2");
				o.setDisplayName("§6§lOrbitMines");
				o.setDisplaySlot(DisplaySlot.SIDEBAR);
				
				if(arena.getType() == MiniGameType.SURVIVAL_GAMES){
					if(arena.getState() == GameState.WAITING || arena.getState() == GameState.STARTING){
						if(arena.getPlayers().size() > 3){
							Score score1 = o.getScore("");
							score1.setScore(9);
					
							Score score2 = o.getScore("§6§lTime Left");
							score2.setScore(8);
					
							Score score3 = o.getScore(" " + arena.getMinutes() + "m " + arena.getSeconds() + "s");
							score3.setScore(7);
						}
						else{
							Score score1 = o.getScore("");
							score1.setScore(8);
							
							Score score2 = o.getScore("§6§l§oWaiting...");
							score2.setScore(7);
						}
						
						Score score4 = o.getScore(" ");
						score4.setScore(6);
				
						Score score5 = o.getScore("§a§lPlayers");
						score5.setScore(5);
						
						Score score6 = o.getScore(" " + arena.getPlayers().size());
						score6.setScore(4);
				
						Score score7 = o.getScore("  ");
						score7.setScore(3);
						
						Score score11 = o.getScore("§f§lCoins");
						score11.setScore(2);
						
						Score score12 = o.getScore(" " + omp.getMiniGameCoins() + " ");
						score12.setScore(1);
				
						Score score13 = o.getScore("    ");
						score13.setScore(0);
						
						updateRankTeams(b);
					}
					else if(arena.getState() == GameState.IN_GAME){
						if(!arena.getSG().isInDeathMatch()){
							Team d = b.registerNewTeam("MG-Deathmatch");
							d.setSuffix(" in");
							OfflinePlayer d1 = Bukkit.getServer().getOfflinePlayer("§d§lDeathmatch");
							d.addPlayer(d1);
							
							Score score2 = o.getScore(d1.getName());
							score2.setScore(8);
						}
						else{
							Score score2 = o.getScore("§6§lTime Left");
							score2.setScore(8);
						}
						
						updateAliveTeams(b, omp, arena);
					}
					else if(arena.getState() == GameState.ENDING){
						if(arena.getSG().getFirstPlace() != null){
							Score score1 = o.getScore("");
							score1.setScore(3);

							Score score2 = o.getScore("§a§lWinner");
							score2.setScore(2);
							
							Score score3 = o.getScore(arena.getSG().getFirstPlace().getPlayer().getName());
							score3.setScore(1);

							Score score4 = o.getScore("");
							score4.setScore(0);
						}
						else{
							Score score1 = o.getScore("");
							score1.setScore(0);
						}
						
						updateAliveTeams(b, omp, arena);
					}
					else{
						Score score1 = o.getScore("");
						score1.setScore(0);
						
						updateAliveTeams(b, omp, arena);
					}
				}
				else{
					Score score1 = o.getScore("");
					score1.setScore(0);
				}
				
				o.setDisplayName(title);
			}
		}
		else{}
		
		omp.getPlayer().setScoreboard(b);
	}
	
	public static void setNextTitle(){
		i++;
		
		if(ServerData.isServer(Server.HUB)){	
			if(i == 1){title = "§6§lOrbitMines§4§lNetwork";}
			else if(i == 7){title = "§e§lO§6§lrbitMines§4§lNetwork";}
			else if(i == 8){title = "§e§lOr§6§lbitMines§4§lNetwork";}
			else if(i == 9){title = "§e§lOrb§6§litMines§4§lNetwork";}
			else if(i == 10){title = "§e§lOrbi§6§ltMines§4§lNetwork";}
			else if(i == 11){title = "§e§lOrbit§6§lMines§4§lNetwork";}
			else if(i == 12){title = "§e§lOrbitM§6§lines§4§lNetwork";}
			else if(i == 13){title = "§e§lOrbitMi§6§lnes§4§lNetwork";}
			else if(i == 14){title = "§e§lOrbitMin§6§les§4§lNetwork";}
			else if(i == 15){title = "§e§lOrbitMine§6§ls§4§lNetwork";}
			else if(i == 16){title = "§e§lOrbitMines§4§lNetwork";}
			else if(i == 17){title = "§e§lOrbitMines§c§lN§4§letwork";}
			else if(i == 18){title = "§e§lOrbitMines§c§lNe§4§ltwork";}
			else if(i == 19){title = "§e§lOrbitMines§c§lNet§4§lwork";}
			else if(i == 20){title = "§e§lOrbitMines§c§lNetw§4§lork";}
			else if(i == 21){title = "§e§lOrbitMines§c§lNetwo§4§lrk";}
			else if(i == 22){title = "§e§lOrbitMines§c§lNetwor§4§lk";}
			else if(i == 23){title = "§e§lOrbitMines§c§lNetwork";}
			else if(i == 29){title = "§6§lO§e§lrbitMines§c§lNetwork";}
			else if(i == 30){title = "§6§lOr§e§lbitMines§c§lNetwork";}
			else if(i == 31){title = "§6§lOrb§e§litMines§c§lNetwork";}
			else if(i == 32){title = "§6§lOrbi§e§ltMines§c§lNetwork";}
			else if(i == 33){title = "§6§lOrbit§e§lMines§c§lNetwork";}
			else if(i == 34){title = "§6§lOrbitM§e§lines§c§lNetwork";}
			else if(i == 35){title = "§6§lOrbitMi§e§lnes§c§lNetwork";}
			else if(i == 36){title = "§6§lOrbitMin§e§les§c§lNetwork";}
			else if(i == 37){title = "§6§lOrbitMine§e§ls§c§lNetwork";}
			else if(i == 38){title = "§6§lOrbitMines§c§lNetwork";}
			else if(i == 39){title = "§6§lOrbitMines§4§lN§c§letwork";}
			else if(i == 40){title = "§6§lOrbitMines§4§lNe§c§ltwork";}
			else if(i == 41){title = "§6§lOrbitMines§4§lNet§c§lwork";}
			else if(i == 42){title = "§6§lOrbitMines§4§lNetw§c§lork";}
			else if(i == 43){title = "§6§lOrbitMines§4§lNetwo§c§lrk";}
			else if(i == 44){title = "§6§lOrbitMines§4§lNetwor§c§lk";}
			else if(i == 45){title = "§6§lOrbitMines§4§lNetwork"; i = 0;}
			else{}
		}
		else if(ServerData.isServer(Server.KITPVP)){
			if(i == 1){title = "§6§lOrbitMines§c§lKitPvP";}
			else if(i == 7){title = "§e§lO§6§lrbitMines§c§lKitPvP";}
			else if(i == 8){title = "§e§lOr§6§lbitMines§c§lKitPvP";}
			else if(i == 9){title = "§e§lOrb§6§litMines§c§lKitPvP";}
			else if(i == 10){title = "§e§lOrbi§6§ltMines§c§lKitPvP";}
			else if(i == 11){title = "§e§lOrbit§6§lMines§c§lKitPvP";}
			else if(i == 12){title = "§e§lOrbitM§6§lines§c§lKitPvP";}
			else if(i == 13){title = "§e§lOrbitMi§6§lnes§c§lKitPvP";}
			else if(i == 14){title = "§e§lOrbitMin§6§les§c§lKitPvP";}
			else if(i == 15){title = "§e§lOrbitMine§6§ls§c§lKitPvP";}
			else if(i == 16){title = "§e§lOrbitMines§c§lKitPvP";}
			else if(i == 17){title = "§e§lOrbitMines§4§lK§c§litPvP";}
			else if(i == 18){title = "§e§lOrbitMines§4§lKi§c§ltPvP";}
			else if(i == 19){title = "§e§lOrbitMines§4§lKit§c§lPvP";}
			else if(i == 20){title = "§e§lOrbitMines§4§lKitP§c§lvP";}
			else if(i == 21){title = "§e§lOrbitMines§4§lKitPv§c§lP";}
			else if(i == 22){title = "§e§lOrbitMines§4§lKitPvP";}
			else if(i == 28){title = "§6§lO§e§lrbitMines§4§lKitPvP";}
			else if(i == 29){title = "§6§lOr§e§lbitMines§4§lKitPvP";}
			else if(i == 30){title = "§6§lOrb§e§litMines§4§lKitPvP";}
			else if(i == 31){title = "§6§lOrbi§e§ltMines§4§lKitPvP";}
			else if(i == 32){title = "§6§lOrbit§e§lMines§4§lKitPvP";}
			else if(i == 33){title = "§6§lOrbitM§e§lines§4§lKitPvP";}
			else if(i == 34){title = "§6§lOrbitMi§e§lnes§4§lKitPvP";}
			else if(i == 35){title = "§6§lOrbitMin§e§les§4§lKitPvP";}
			else if(i == 36){title = "§6§lOrbitMine§e§ls§4§lKitPvP";}
			else if(i == 37){title = "§6§lOrbitMines§4§lKitPvP";}
			else if(i == 38){title = "§6§lOrbitMines§c§lK§4§litPvP";}
			else if(i == 39){title = "§6§lOrbitMines§c§lKi§4§ltPvP";}
			else if(i == 40){title = "§6§lOrbitMines§c§lKit§4§lPvP";}
			else if(i == 41){title = "§6§lOrbitMines§c§lKitP§4§lvP";}
			else if(i == 42){title = "§6§lOrbitMines§c§lKitPv§4§lP";}
			else if(i == 43){title = "§6§lOrbitMines§c§lKitPv§4§lP";}
			else if(i == 44){title = "§6§lOrbitMines§c§lKitPvP"; i = 0;}
			else{}
		}
		else if(ServerData.isServer(Server.CREATIVE)){
			if(i == 1){title = "§6§lOrbitMines§d§lCreative";}
			else if(i == 7){title = "§e§lO§6§lrbitMines§d§lCreative";}
			else if(i == 8){title = "§e§lOr§6§lbitMines§d§lCreative";}
			else if(i == 9){title = "§e§lOrb§6§litMines§d§lCreative";}
			else if(i == 10){title = "§e§lOrbi§6§ltMines§d§lCreative";}
			else if(i == 11){title = "§e§lOrbit§6§lMines§d§lCreative";}
			else if(i == 12){title = "§e§lOrbitM§6§lines§d§lCreative";}
			else if(i == 13){title = "§e§lOrbitMi§6§lnes§d§lCreative";}
			else if(i == 14){title = "§e§lOrbitMin§6§les§d§lCreative";}
			else if(i == 15){title = "§e§lOrbitMine§6§ls§d§lCreative";}
			else if(i == 16){title = "§e§lOrbitMines§d§lCreative";}
			else if(i == 17){title = "§e§lOrbitMines§5§lC§d§lreative";}
			else if(i == 18){title = "§e§lOrbitMines§5§lCr§d§leative";}
			else if(i == 19){title = "§e§lOrbitMines§5§lCre§d§lative";}
			else if(i == 20){title = "§e§lOrbitMines§5§lCrea§d§ltive";}
			else if(i == 21){title = "§e§lOrbitMines§5§lCreat§d§live";}
			else if(i == 22){title = "§e§lOrbitMines§5§lCreati§d§lve";}
			else if(i == 23){title = "§e§lOrbitMines§5§lCreativ§d§le";}
			else if(i == 24){title = "§e§lOrbitMines§5§lCreative";}
			else if(i == 30){title = "§6§lO§e§lrbitMines§5§lCreative";}
			else if(i == 31){title = "§6§lOr§e§lbitMines§5§lCreative";}
			else if(i == 32){title = "§6§lOrb§e§litMines§5§lCreative";}
			else if(i == 33){title = "§6§lOrbi§e§ltMines§5§lCreative";}
			else if(i == 34){title = "§6§lOrbit§e§lMines§5§lCreative";}
			else if(i == 35){title = "§6§lOrbitM§e§lines§5§lCreative";}
			else if(i == 36){title = "§6§lOrbitMi§e§lnes§5§lCreative";}
			else if(i == 37){title = "§6§lOrbitMin§e§les§5§lCreative";}
			else if(i == 38){title = "§6§lOrbitMine§e§ls§5§lCreative";}
			else if(i == 39){title = "§6§lOrbitMines§5§lCreative";}
			else if(i == 40){title = "§6§lOrbitMines§d§lC§5§lreative";}
			else if(i == 41){title = "§6§lOrbitMines§d§lCr§5§leative";}
			else if(i == 42){title = "§6§lOrbitMines§d§lCre§5§lative";}
			else if(i == 43){title = "§6§lOrbitMines§d§lCrea§5§ltive";}
			else if(i == 44){title = "§6§lOrbitMines§d§lCreat§5§live";}
			else if(i == 45){title = "§6§lOrbitMines§d§lCreati§5§lve";}
			else if(i == 46){title = "§6§lOrbitMines§d§lCreativ§5§le";}
			else if(i == 47){title = "§6§lOrbitMines§d§lCreative"; i = 0;}
			else{}
		}
		else if(ServerData.isServer(Server.SURVIVAL)){
			if(i == 1){title = "§6§lOrbitMines§a§lSurvival";}
			else if(i == 7){title = "§e§lO§6§lrbitMines§a§lSurvival";}
			else if(i == 8){title = "§e§lOr§6§lbitMines§a§lSurvival";}
			else if(i == 9){title = "§e§lOrb§6§litMines§a§lSurvival";}
			else if(i == 10){title = "§e§lOrbi§6§ltMines§a§lSurvival";}
			else if(i == 11){title = "§e§lOrbit§6§lMines§a§lSurvival";}
			else if(i == 12){title = "§e§lOrbitM§6§lines§a§lSurvival";}
			else if(i == 13){title = "§e§lOrbitMi§6§lnes§a§lSurvival";}
			else if(i == 14){title = "§e§lOrbitMin§6§les§a§lSurvival";}
			else if(i == 15){title = "§e§lOrbitMine§6§ls§a§lSurvival";}
			else if(i == 16){title = "§e§lOrbitMines§a§lSurvival";}
			else if(i == 17){title = "§e§lOrbitMines§2§lS§a§lurvival";}
			else if(i == 18){title = "§e§lOrbitMines§2§lSu§a§lrvival";}
			else if(i == 19){title = "§e§lOrbitMines§2§lSur§a§lvival";}
			else if(i == 20){title = "§e§lOrbitMines§2§lSurv§a§lival";}
			else if(i == 21){title = "§e§lOrbitMines§2§lSurvi§a§lval";}
			else if(i == 22){title = "§e§lOrbitMines§2§lSurviv§a§lal";}
			else if(i == 23){title = "§e§lOrbitMines§2§lSurviva§a§ll";}
			else if(i == 24){title = "§e§lOrbitMines§2§lSurvival";}
			else if(i == 30){title = "§6§lO§e§lrbitMines§2§lSurvival";}
			else if(i == 31){title = "§6§lOr§e§lbitMines§2§lSurvival";}
			else if(i == 32){title = "§6§lOrb§e§litMines§2§lSurvival";}
			else if(i == 33){title = "§6§lOrbi§e§ltMines§2§lSurvival";}
			else if(i == 34){title = "§6§lOrbit§e§lMines§2§lSurvival";}
			else if(i == 35){title = "§6§lOrbitM§e§lines§2§lSurvival";}
			else if(i == 36){title = "§6§lOrbitMi§e§lnes§2§lSurvival";}
			else if(i == 37){title = "§6§lOrbitMin§e§les§2§lSurvival";}
			else if(i == 38){title = "§6§lOrbitMine§e§ls§2§lSurvival";}
			else if(i == 39){title = "§6§lOrbitMines§2§lSurvival";}
			else if(i == 40){title = "§6§lOrbitMines§a§lS§2§lurvival";}
			else if(i == 41){title = "§6§lOrbitMines§a§lSu§2§lrvival";}
			else if(i == 42){title = "§6§lOrbitMines§a§lSur§2§lvival";}
			else if(i == 43){title = "§6§lOrbitMines§a§lSurv§2§lival";}
			else if(i == 44){title = "§6§lOrbitMines§a§lSurvi§2§lval";}
			else if(i == 45){title = "§6§lOrbitMines§a§lSurviv§2§lal";}
			else if(i == 46){title = "§6§lOrbitMines§a§lSurviva§2§ll";}
			else if(i == 47){title = "§6§lOrbitMines§a§lSurvival"; i = 0;}
			else{}
		}
		else if(ServerData.isServer(Server.SKYBLOCK)){
			if(i == 1){title = "§6§lOrbitMines§5§lSkyBlock";}
			else if(i == 7){title = "§e§lO§6§lrbitMines§5§lSkyBlock";}
			else if(i == 8){title = "§e§lOr§6§lbitMines§5§lSkyBlock";}
			else if(i == 9){title = "§e§lOrb§6§litMines§5§lSkyBlock";}
			else if(i == 10){title = "§e§lOrbi§6§ltMines§5§lSkyBlock";}
			else if(i == 11){title = "§e§lOrbit§6§lMines§5§lSkyBlock";}
			else if(i == 12){title = "§e§lOrbitM§6§lines§5§lSkyBlock";}
			else if(i == 13){title = "§e§lOrbitMi§6§lnes§5§lSkyBlock";}
			else if(i == 14){title = "§e§lOrbitMin§6§les§5§lSkyBlock";}
			else if(i == 15){title = "§e§lOrbitMine§6§ls§5§lSkyBlock";}
			else if(i == 16){title = "§e§lOrbitMines§5§lSkyBlock";}
			else if(i == 17){title = "§e§lOrbitMines§d§lS§5§lkyBlock";}
			else if(i == 18){title = "§e§lOrbitMines§d§lSk§5§lyBlock";}
			else if(i == 19){title = "§e§lOrbitMines§d§lSky§5§lBlock";}
			else if(i == 20){title = "§e§lOrbitMines§d§lSkyB§5§llock";}
			else if(i == 21){title = "§e§lOrbitMines§d§lSkyBl§5§lock";}
			else if(i == 22){title = "§e§lOrbitMines§d§lSkyBlo§5§lck";}
			else if(i == 23){title = "§e§lOrbitMines§d§lSkyBloc§5§lk";}
			else if(i == 24){title = "§e§lOrbitMines§d§lSkyBlock";}
			else if(i == 30){title = "§6§lO§e§lrbitMines§d§lSkyBlock";}
			else if(i == 31){title = "§6§lOr§e§lbitMines§d§lSkyBlock";}
			else if(i == 32){title = "§6§lOrb§e§litMines§d§lSkyBlock";}
			else if(i == 33){title = "§6§lOrbi§e§ltMines§d§lSkyBlock";}
			else if(i == 34){title = "§6§lOrbit§e§lMines§d§lSkyBlock";}
			else if(i == 35){title = "§6§lOrbitM§e§lines§d§lSkyBlock";}
			else if(i == 36){title = "§6§lOrbitMi§e§lnes§d§lSkyBlock";}
			else if(i == 37){title = "§6§lOrbitMin§e§les§d§lSkyBlock";}
			else if(i == 38){title = "§6§lOrbitMine§e§ls§d§lSkyBlock";}
			else if(i == 39){title = "§6§lOrbitMines§d§lSkyBlock";}
			else if(i == 40){title = "§6§lOrbitMines§5§lS§d§lkyBlock";}
			else if(i == 41){title = "§6§lOrbitMines§5§lSk§d§lyBlock";}
			else if(i == 42){title = "§6§lOrbitMines§5§lSky§d§lBlock";}
			else if(i == 43){title = "§6§lOrbitMines§5§lSkyB§d§llock";}
			else if(i == 44){title = "§6§lOrbitMines§5§lSkyBl§d§lock";}
			else if(i == 45){title = "§6§lOrbitMines§5§lSkyBlo§d§lck";}
			else if(i == 46){title = "§6§lOrbitMines§5§lSkyBloc§d§lk";}
			else if(i == 47){title = "§6§lOrbitMines§5§lSkyBlock"; i = 0;}
			else{}
		}
		else if(ServerData.isServer(Server.MINIGAMES)){
			if(i == 1){title = "§6§lOrbitMines§f§lMiniGames";}
			else if(i == 7){title = "§e§lO§6§lrbitMines§f§lMiniGames";}
			else if(i == 8){title = "§e§lOr§6§lbitMines§f§lMiniGames";}
			else if(i == 9){title = "§e§lOrb§6§litMines§f§lMiniGames";}
			else if(i == 10){title = "§e§lOrbi§6§ltMines§f§lMiniGames";}
			else if(i == 11){title = "§e§lOrbit§6§lMines§f§lMiniGames";}
			else if(i == 12){title = "§e§lOrbitM§6§lines§f§lMiniGames";}
			else if(i == 13){title = "§e§lOrbitMi§6§lnes§f§lMiniGames";}
			else if(i == 14){title = "§e§lOrbitMin§6§les§f§lMiniGames";}
			else if(i == 15){title = "§e§lOrbitMine§6§ls§f§lMiniGames";}
			else if(i == 16){title = "§e§lOrbitMines§f§lMiniGames";}
			else if(i == 17){title = "§e§lOrbitMines§7§lM§f§liniGames";}
			else if(i == 18){title = "§e§lOrbitMines§7§lMi§f§lniGames";}
			else if(i == 19){title = "§e§lOrbitMines§7§lMin§f§liGames";}
			else if(i == 20){title = "§e§lOrbitMines§7§lMini§f§lGames";}
			else if(i == 21){title = "§e§lOrbitMines§7§lMiniG§f§lames";}
			else if(i == 22){title = "§e§lOrbitMines§7§lMiniGa§f§lmes";}
			else if(i == 23){title = "§e§lOrbitMines§7§lMiniGam§f§les";}
			else if(i == 24){title = "§e§lOrbitMines§7§lMiniGame§f§ls";}
			else if(i == 25){title = "§e§lOrbitMines§7§lMiniGames";}
			else if(i == 31){title = "§6§lO§e§lrbitMines§7§lMiniGames";}
			else if(i == 32){title = "§6§lOr§e§lbitMines§7§lMiniGames";}
			else if(i == 33){title = "§6§lOrb§e§litMines§7§lMiniGames";}
			else if(i == 34){title = "§6§lOrbi§e§ltMines§7§lMiniGames";}
			else if(i == 35){title = "§6§lOrbit§e§lMines§7§lMiniGames";}
			else if(i == 36){title = "§6§lOrbitM§e§lines§7§lMiniGames";}
			else if(i == 37){title = "§6§lOrbitMi§e§lnes§7§lMiniGames";}
			else if(i == 38){title = "§6§lOrbitMin§e§les§7§lMiniGames";}
			else if(i == 39){title = "§6§lOrbitMine§e§ls§7§lMiniGames";}
			else if(i == 40){title = "§6§lOrbitMines§7§lMiniGames";}
			else if(i == 41){title = "§6§lOrbitMines§f§lM§7§liniGames";}
			else if(i == 42){title = "§6§lOrbitMines§f§lMi§7§lniGames";}
			else if(i == 43){title = "§6§lOrbitMines§f§lMin§7§liGames";}
			else if(i == 44){title = "§6§lOrbitMines§f§lMini§7§lGames";}
			else if(i == 45){title = "§6§lOrbitMines§f§lMiniG§7§lames";}
			else if(i == 46){title = "§6§lOrbitMines§f§lMiniGa§7§lmes";}
			else if(i == 47){title = "§6§lOrbitMines§f§lMiniGam§7§les";}
			else if(i == 48){title = "§6§lOrbitMines§f§lMiniGame§7§ls";}
			else if(i == 49){title = "§6§lOrbitMines§f§lMiniGames"; i = 0;}
			else{}
		}
		else{}
	}
	
	@SuppressWarnings("deprecation")
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
	
	@SuppressWarnings("deprecation")
	private static void updateAliveTeams(Scoreboard b, OMPlayer omp, Arena arena){
		Team Players = b.registerNewTeam("PlayersMG");
		Team Spectators = b.registerNewTeam("SpectateMG");
		
		Players.setPrefix("§a");
		Spectators.setPrefix("§c");
		Players.setAllowFriendlyFire(true);
		Spectators.setCanSeeFriendlyInvisibles(true);
		
		for(OMPlayer omplayer : arena.getPlayers()){
			Players.addPlayer(omplayer.getPlayer());
			
			if(arena.isSpectator(omp)){
				omp.getPlayer().showPlayer(omplayer.getPlayer());
			}
		}
		for(OMPlayer omplayer : arena.getSpectators()){
			Spectators.addPlayer(omplayer.getPlayer());
			((CraftPlayer) omplayer.getPlayer()).getHandle().setInvisible(true);
			
			if(arena.isSpectator(omp)){
				omp.getPlayer().showPlayer(omplayer.getPlayer());
			}
			else{
				omp.getPlayer().hidePlayer(omplayer.getPlayer());
			}
		}
	}
}
