package fadidev.orbitmines.hub.handlers.players.minigames;

import fadidev.orbitmines.api.handlers.Database;
import fadidev.orbitmines.hub.handlers.players.HubPlayer;

public class ChickenFightPlayer {

	private HubPlayer omp;
	private int kills;
	private int wins;
	private int loses;
	private int bestStreak;
	
	public ChickenFightPlayer(HubPlayer omp, int kills, int wins, int loses, int bestStreak){
		this.omp = omp;
		this.kills = kills;
		this.wins = wins;
		this.loses = loses;
		this.bestStreak = bestStreak;
	}

    public HubPlayer getPlayer() {
        return omp;
    }

    public int getKills(){
		return kills;
	}

	public void setKills(int kills){
		this.kills = kills;
		
		Database.get().update("ChickenFight-Kills", "kills", "" + getKills(), "uuid", getPlayer().getUUID().toString());
	}

	public void addKill(){
		this.kills = getKills() +1;
		
		Database.get().update("ChickenFight-Kills", "kills", "" + getKills(), "uuid", getPlayer().getUUID().toString());
	}

	public int getWins(){
		return wins;
	}

	public void setWins(int wins){
		this.wins = wins;
		
		Database.get().update("ChickenFight-Wins", "wins", "" + getWins(), "uuid", getPlayer().getUUID().toString());
	}
	public void addWin(){
		this.wins = getWins() +1;
		
		Database.get().update("ChickenFight-Wins", "wins", "" + getWins(), "uuid", getPlayer().getUUID().toString());
	}

	public int getLoses(){
		return loses;
	}

	public void setLoses(int loses){
		this.loses = loses;
		
		Database.get().update("ChickenFight-Loses", "loses", "" + getLoses(), "uuid", getPlayer().getUUID().toString());
	}

	public void addLose(){
		this.loses = getLoses() +1;
		
		Database.get().update("ChickenFight-Loses", "loses", "" + getLoses(), "uuid", getPlayer().getUUID().toString());
	}

	public int getBestStreak(){
		return bestStreak;
	}

	public void setBestStreak(int bestStreak){
		this.bestStreak = bestStreak;
		
		Database.get().update("ChickenFight-BestStreak", "beststreak", "" + getBestStreak(), "uuid", getPlayer().getUUID().toString());
	}
}
