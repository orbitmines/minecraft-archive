package om.kitpvp.handlers;

import om.api.managers.ScoreBoardManager;
import om.kitpvp.handlers.players.KitPvPPlayer;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

public class KitPvPScoreBoard extends ScoreBoardManager {

	@Override
	public void nextTitle() {
		setTitleIndex(getTitleIndex() +1);
		
		switch(getTitleIndex()){
			case 1: setTitle("§6§lOrbitMines§c§lKitPvP"); break;
			case 7: setTitle("§e§lO§6§lrbitMines§c§lKitPvP"); break;
			case 8: setTitle("§e§lOr§6§lbitMines§c§lKitPvP"); break;
			case 9: setTitle("§e§lOrb§6§litMines§c§lKitPvP"); break;
			case 10: setTitle("§e§lOrbi§6§ltMines§c§lKitPvP"); break;
			case 11: setTitle("§e§lOrbit§6§lMines§c§lKitPvP"); break;
			case 12: setTitle("§e§lOrbitM§6§lines§c§lKitPvP"); break;
			case 13: setTitle("§e§lOrbitMi§6§lnes§c§lKitPvP"); break;
			case 14: setTitle("§e§lOrbitMin§6§les§c§lKitPvP"); break;
			case 15: setTitle("§e§lOrbitMine§6§ls§c§lKitPvP"); break;
			case 16: setTitle("§e§lOrbitMines§c§lKitPvP"); break;
			case 17: setTitle("§e§lOrbitMines§4§lK§c§litPvP"); break;
			case 18: setTitle("§e§lOrbitMines§4§lKi§c§ltPvP"); break;
			case 19: setTitle("§e§lOrbitMines§4§lKit§c§lPvP"); break;
			case 20: setTitle("§e§lOrbitMines§4§lKitP§c§lvP"); break;
			case 21: setTitle("§e§lOrbitMines§4§lKitPv§c§lP"); break;
			case 22: setTitle("§e§lOrbitMines§4§lKitPvP"); break;
			case 28: setTitle("§6§lO§e§lrbitMines§4§lKitPvP"); break;
			case 29: setTitle("§6§lOr§e§lbitMines§4§lKitPvP"); break;
			case 30: setTitle("§6§lOrb§e§litMines§4§lKitPvP"); break;
			case 31: setTitle("§6§lOrbi§e§ltMines§4§lKitPvP"); break;
			case 32: setTitle("§6§lOrbit§e§lMines§4§lKitPvP"); break;
			case 33: setTitle("§6§lOrbitM§e§lines§4§lKitPvP"); break;
			case 34: setTitle("§6§lOrbitMi§e§lnes§4§lKitPvP"); break;
			case 35: setTitle("§6§lOrbitMin§e§les§4§lKitPvP"); break;
			case 36: setTitle("§6§lOrbitMine§e§ls§4§lKitPvP"); break;
			case 37: setTitle("§6§lOrbitMines§4§lKitPvP"); break;
			case 38: setTitle("§6§lOrbitMines§c§lK§4§litPvP"); break;
			case 39: setTitle("§6§lOrbitMines§c§lKi§4§ltPvP"); break;
			case 40: setTitle("§6§lOrbitMines§c§lKit§4§lPvP"); break;
			case 41: setTitle("§6§lOrbitMines§c§lKitP§4§lvP"); break;
			case 42: setTitle("§6§lOrbitMines§c§lKitPv§4§lP"); break;
			case 43: setTitle("§6§lOrbitMines§c§lKitPv§4§lP"); break;
			case 44: setTitle("§6§lOrbitMines§c§lKitPvP"); setTitleIndex(0); break;
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public void requestUpdate(Player p) {
		ScoreboardManager sm = Bukkit.getScoreboardManager();
		Scoreboard b = sm.getNewScoreboard();
		Objective o = b.registerNewObjective("KitPvP", "KitPvP2");
		o.setDisplayName("§6§lOrbitMines");
		o.setDisplaySlot(DisplaySlot.SIDEBAR);
	
		KitPvPPlayer omp = KitPvPPlayer.getKitPvPPlayer(p);
		
		Score score1 = o.getScore("");
		score1.setScore(14);
		
		Team beststreak = b.registerNewTeam("KitPvP-Streak");
		OfflinePlayer streakp = Bukkit.getServer().getOfflinePlayer("§f§lCurrent ");
		beststreak.setSuffix("Streak: §6§l" + omp.getCurrentStreak());
		beststreak.addPlayer(streakp);
		
		Score score2 = o.getScore(streakp.getName());
		score2.setScore(13);
		
		Score score3 = o.getScore(" ");
		score3.setScore(12);
		
		Score score4 = o.getScore("§7§lKit");
		score4.setScore(11);
		
		Team kit = b.registerNewTeam("KitPvP-Kit");
		OfflinePlayer kitp = null;
		if(omp.isLoaded() && omp.getKitSelected() != null){
			kitp = Bukkit.getServer().getOfflinePlayer(" §b§l" + omp.getKitSelected().getName());
			kit.setSuffix(" §7(§aLvL " + omp.getKitLevelSelected() + "§7)");
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
		
		if(omp.isLoaded()){
			Score score8 = o.getScore(" " + omp.getMoney());
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

		if(omp.isLoaded()){
			Score score11 = o.getScore(" " + omp.getKills() + " ");
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

		if(omp.isLoaded()){
			Score score14 = o.getScore(" " + omp.getDeaths() + "  ");
			score14.setScore(1);
		}
		else{
			Score score14 = o.getScore(" Loading...  ");
			score14.setScore(1);
		}
		
		Score score15 = o.getScore("     ");
		score15.setScore(0);
		
		o.setDisplayName(getTitle());
		updateRankTeams(b);
		
		omp.getPlayer().setScoreboard(b);
	}
}
