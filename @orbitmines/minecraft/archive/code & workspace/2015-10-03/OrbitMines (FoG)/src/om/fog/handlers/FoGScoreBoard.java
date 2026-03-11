package om.fog.handlers;

import om.api.managers.ScoreBoardManager;
import om.api.utils.enums.ranks.StaffRank;
import om.api.utils.enums.ranks.VIPRank;
import om.fog.FoG;
import om.fog.handlers.players.FoGPlayer;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

public class FoGScoreBoard extends ScoreBoardManager {

	private FoG fog;
	
	public FoGScoreBoard() {
		fog = FoG.getInstance();
	}
	
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

	@SuppressWarnings("deprecation")
	@Override
	public void requestUpdate(Player p) {
		ScoreboardManager sm = Bukkit.getScoreboardManager();
		Scoreboard b = sm.getNewScoreboard();
		Objective o = b.registerNewObjective("FoG", "FoG2");
		o.setDisplayName("§6§lOrbitMines");
		o.setDisplaySlot(DisplaySlot.SIDEBAR);
	
		FoGPlayer omp = FoGPlayer.getFoGPlayer(p);
		
		if(omp.getFaction() != null){
			Score score1 = o.getScore("");
			score1.setScore(14);
			
			Team t1 = b.registerNewTeam("KitPvP-Streak");
			OfflinePlayer op1 = Bukkit.getServer().getOfflinePlayer("§fFaction: ");
			t1.setSuffix(omp.getFaction().getName());
			t1.addPlayer(op1);
			
			Score score2 = o.getScore(op1.getName());
			score2.setScore(13);
			
			Score score3 = o.getScore(" ");
			score3.setScore(12);
			
			Score score4 = o.getScore("§a§lSuit");
			score4.setScore(11);
			
			if(omp.getSuit() != null){
				Score score5 = o.getScore(" " + omp.getSuit().getName(omp.getFaction()));
				score5.setScore(10);
			}
			else{
				Score score5 = o.getScore(" None");
				score5.setScore(10);
			}
			
			Score score6 = o.getScore("  ");
			score6.setScore(9);
			
			Score score7 = o.getScore("§7§lSilver");
			score7.setScore(8);
			
			if(omp.isLoaded()){
				Score score8 = o.getScore(" " + omp.getSilver());
				score8.setScore(7);
			}
			else{
				Score score8 = o.getScore(" Loading...");
				score8.setScore(7);
			}
			
			Score score9 = o.getScore("   ");
			score9.setScore(6);
			
			Score score10 = o.getScore("§e§lGalaxy");
			score10.setScore(5);
	
			if(omp.getMap() != null){
				Score score11 = o.getScore(" " + omp.getMap().getName());
				score11.setScore(4);
			}
			else{
				Score score11 = o.getScore(" Unknown");
				score11.setScore(4);
			}
			
			Score score12 = o.getScore("    ");
			score12.setScore(3);
			
			/*Score score13 = o.getScore("§4§lDeaths");
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
		}
		else{
			//TODO Tutorial 
			Score score1 = o.getScore("");
			score1.setScore(3);
			
			Team t1 = b.registerNewTeam("FoG-Tutorial");
			OfflinePlayer op1 = Bukkit.getServer().getOfflinePlayer("§e§lTutorial ");
			t1.setSuffix("§7(1/...)");
			t1.addPlayer(op1);
			
			Score score2 = o.getScore(op1.getName());
			score2.setScore(2);
			
			Team t2 = b.registerNewTeam("FoG-Tutorial1");
			OfflinePlayer op2 = Bukkit.getServer().getOfflinePlayer("§7Choose a ");
			t2.setSuffix("Faction");
			t2.addPlayer(op2);
			
			Score score3 = o.getScore(op2.getName());
			score3.setScore(1);
			
			Score score4 = o.getScore(" ");
			score4.setScore(0);
		}
		
		o.setDisplayName(getTitle());
		updateFactionTeams(b);
		updateHealth(b);
		
		omp.getPlayer().setScoreboard(b);
	}
	
	@SuppressWarnings("deprecation")
	private void updateFactionTeams(Scoreboard b){
		int index = 0;
		for(FoGPlayer omp : fog.getFoGPlayers()){
			Player p = omp.getPlayer();
			StaffRank staff = omp.getStaffRank();
			VIPRank vip = omp.getVIPRank();
			
			String faction = "";
			if(omp.getFaction() != null) faction = omp.getFaction().getColor();
			
			Team t = b.registerNewTeam("" + index);
			if(staff != StaffRank.User){
				t.setPrefix(staff.getScoreboardPrefix() + faction);
			}
			else{
				t.setPrefix(vip.getScoreboardPrefix() + faction);
			}
			t.addPlayer(p);
			index++;
		}
	}
	
	@SuppressWarnings("deprecation")
	private void updateHealth(Scoreboard b){
		Objective o = b.registerNewObjective("FoGPlayer", "dummy");
		o.setDisplaySlot(DisplaySlot.BELOW_NAME);
		o.setDisplayName("§c❤");
		
		for(FoGPlayer omp : fog.getFoGPlayers()){
			Player p = omp.getPlayer();
			
			Score score = o.getScore(p);
			score.setScore((int) (p.getHealth() + omp.getCurrentShield()));
		}
	}
}
