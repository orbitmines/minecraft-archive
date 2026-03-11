package om.fog.handlers;

import om.api.managers.ScoreBoardManager;
import om.fog.handlers.players.FoGPlayer;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

public class FoGScoreBoard extends ScoreBoardManager {

	@Override
	public void nextTitle() {
		setTitleIndex(getTitleIndex() +1);
		
		switch(getTitleIndex()){
			case 1: setTitle("§6§lOrbitMines§e§lFoG"); break;
			case 7: setTitle("§e§lO§6§lrbitMines§e§lFoG"); break;
			case 8: setTitle("§e§lOr§6§lbitMines§e§lFoG"); break;
			case 9: setTitle("§e§lOrb§6§litMines§e§lFoG"); break;
			case 10: setTitle("§e§lOrbi§6§ltMines§e§lFoG"); break;
			case 11: setTitle("§e§lOrbit§6§lMines§e§lFoG"); break;
			case 12: setTitle("§e§lOrbitM§6§lines§e§lFoG"); break;
			case 13: setTitle("§e§lOrbitMi§6§lnes§e§lFoG"); break;
			case 14: setTitle("§e§lOrbitMin§6§les§e§lFoG"); break;
			case 15: setTitle("§e§lOrbitMine§6§ls§e§lFoG"); break;
			case 16: setTitle("§e§lOrbitMines§e§lFoG"); break;
			case 17: setTitle("§e§lOrbitMines§6§lF§e§loG"); break;
			case 18: setTitle("§e§lOrbitMines§6§lFo§e§lG"); break;
			case 19: setTitle("§e§lOrbitMines§6§lFoG"); break;
			case 25: setTitle("§6§lO§e§lrbitMines§6§lFoG"); break;
			case 26: setTitle("§6§lOr§e§lbitMines§6§lFoG"); break;
			case 27: setTitle("§6§lOrb§e§litMines§6§lFoG"); break;
			case 28: setTitle("§6§lOrbi§e§ltMines§6§lFoG"); break;
			case 29: setTitle("§6§lOrbit§e§lMines§6§lFoG"); break;
			case 30: setTitle("§6§lOrbitM§e§lines§6§lFoG"); break;
			case 31: setTitle("§6§lOrbitMi§e§lnes§6§lFoG"); break;
			case 32: setTitle("§6§lOrbitMin§e§les§6§lFoG"); break;
			case 33: setTitle("§6§lOrbitMine§e§ls§6§lFoG"); break;
			case 34: setTitle("§6§lOrbitMines§6§lFoG"); break;
			case 35: setTitle("§6§lOrbitMines§e§lF§6§loG"); break;
			case 36: setTitle("§6§lOrbitMines§e§lFo§6§lG"); break;
			case 37: setTitle("§6§lOrbitMines§e§lFoG"); setTitleIndex(0); break;
		}
	}

	@Override
	public void requestUpdate(Player p) {
		ScoreboardManager sm = Bukkit.getScoreboardManager();
		Scoreboard b = sm.getNewScoreboard();
		Objective o = b.registerNewObjective("FoG", "FoG2");
		o.setDisplayName("§6§lOrbitMines");
		o.setDisplaySlot(DisplaySlot.SIDEBAR);
	
		FoGPlayer omp = FoGPlayer.getFoGPlayer(p);
		
		Score score1 = o.getScore("");
		score1.setScore(14);
		
		/*Team beststreak = b.registerNewTeam("KitPvP-Streak");
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
		score15.setScore(0);*/
		
		o.setDisplayName(getTitle());
		updateRankTeams(b);
		
		omp.getPlayer().setScoreboard(b);
	}
}
