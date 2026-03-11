package fadidev.orbitmines.minigames.handlers.players.minigames;

import fadidev.orbitmines.api.handlers.Database;
import fadidev.orbitmines.minigames.handlers.players.ArenaPlayer;
import fadidev.orbitmines.minigames.handlers.players.MiniGamesPlayer;

public class GhostAttackPlayer extends ArenaPlayer {

	private int kills;
	private int ghostKills;
	private int wins;
	private int ghostWins;
	private int loses;
	private int bestStreak;
	
	public GhostAttackPlayer(MiniGamesPlayer omp, int kills, int ghostKills, int wins, int ghostWins, int loses, int bestStreak){
		super(omp);

		this.kills = kills;
		this.ghostKills = ghostKills;
		this.wins = wins;
		this.ghostWins = ghostWins;
		this.loses = loses;
		this.bestStreak = bestStreak;
	}

	public int getKills(){
		return kills;
	}
	
	public void setKills(int kills){
		this.kills = kills;
		
		Database.get().update("GhostAttack-Kills", "kills", "" + getKills(), "uuid", getPlayer().getUUID().toString());
	}
	
	public void addKill(){
		this.kills = getKills() +1;
		
		Database.get().update("GhostAttack-Kills", "kills", "" + getKills(), "uuid", getPlayer().getUUID().toString());
	}

	public int getGhostKills(){
		return ghostKills;
	}
	
	public void setGhostKills(int ghostKills){
		this.ghostKills = ghostKills;
		
		Database.get().update("GhostAttack-GhostKills", "ghostKills", "" + getGhostKills(), "uuid", getPlayer().getUUID().toString());
	}
	
	public void addGhostKill(){
		this.ghostKills = getGhostKills() +1;
		
		Database.get().update("GhostAttack-GhostKills", "ghostKills", "" + getGhostKills(), "uuid", getPlayer().getUUID().toString());
	}

	public int getWins(){
		return wins;
	}
	
	public void setWins(int wins){
		this.wins = wins;
		
		Database.get().update("GhostAttack-Wins", "wins", "" + getWins(), "uuid", getPlayer().getUUID().toString());
	}
	
	public void addWin(){
		this.wins = getWins() +1;
		
		Database.get().update("GhostAttack-Wins", "wins", "" + getWins(), "uuid", getPlayer().getUUID().toString());
	}

	public int getGhostWins(){
		return ghostWins;
	}
	
	public void setGhostWins(int ghostWins){
		this.ghostWins = ghostWins;
		
		Database.get().update("GhostAttack-GhostWins", "ghostWins", "" + getGhostWins(), "uuid", getPlayer().getUUID().toString());
	}
	
	public void addGhostWin(){
		this.ghostWins = getGhostWins() +1;
		
		Database.get().update("GhostAttack-GhostWins", "ghostWins", "" + getGhostWins(), "uuid", getPlayer().getUUID().toString());
	}

	public int getLoses(){
		return loses;
	}
	
	public void setLoses(int loses){
		this.loses = loses;
		
		Database.get().update("GhostAttack-Loses", "loses", "" + getLoses(), "uuid", getPlayer().getUUID().toString());
	}
	
	public void addLose(){
		this.loses = getLoses() +1;
		
		Database.get().update("GhostAttack-Loses", "loses", "" + getLoses(), "uuid", getPlayer().getUUID().toString());
	}

	public int getBestStreak(){
		return bestStreak;
	}
	
	public void setBestStreak(int bestStreak){
		this.bestStreak = bestStreak;
		
		Database.get().update("GhostAttack-BestStreak", "beststreak", "" + getBestStreak(), "uuid", getPlayer().getUUID().toString());
	}
}
