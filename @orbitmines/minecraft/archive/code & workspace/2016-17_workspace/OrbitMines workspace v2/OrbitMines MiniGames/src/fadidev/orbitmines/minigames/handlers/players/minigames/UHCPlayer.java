package fadidev.orbitmines.minigames.handlers.players.minigames;

import fadidev.orbitmines.api.handlers.Database;
import fadidev.orbitmines.minigames.handlers.players.ArenaPlayer;
import fadidev.orbitmines.minigames.handlers.players.MiniGamesPlayer;

public class UHCPlayer extends ArenaPlayer {

	private int kills;
	private int wins;
	private int loses;
	private int bestStreak;

	private boolean doubleIron;
	private boolean blueGold;
	private boolean redGold;
	
	public UHCPlayer(MiniGamesPlayer omp, int kills, int wins, int loses, int bestStreak){
        super(omp);

		this.kills = kills;
		this.wins = wins;
		this.loses = loses;
		this.bestStreak = bestStreak;

        this.doubleIron = false;
        this.blueGold = false;
        this.redGold = false;
	}

	public int getKills(){
		return kills;
	}
	
	public void setKills(int kills){
		this.kills = kills;
		
		Database.get().update("UHC-Kills", "kills", "" + getKills(), "uuid", getPlayer().getUUID().toString());
	}
	
	public void addKill(){
		this.kills = getKills() +1;
		
		Database.get().update("UHC-Kills", "kills", "" + getKills(), "uuid", getPlayer().getUUID().toString());
	}

	public int getWins(){
		return wins;
	}
	
	public void setWins(int wins){
		this.wins = wins;
		
		Database.get().update("UHC-Wins", "wins", "" + getWins(), "uuid", getPlayer().getUUID().toString());
	}
	
	public void addWin(){
		this.wins = getWins() +1;
		
		Database.get().update("UHC-Wins", "wins", "" + getWins(), "uuid", getPlayer().getUUID().toString());
	}

	public int getLoses(){
		return loses;
	}
	
	public void setLoses(int loses){
		this.loses = loses;
		
		Database.get().update("UHC-Loses", "loses", "" + getLoses(), "uuid", getPlayer().getUUID().toString());
	}
	
	public void addLose(){
		this.loses = getLoses() +1;
		
		Database.get().update("UHC-Loses", "loses", "" + getLoses(), "uuid", getPlayer().getUUID().toString());
	}

	public int getBestStreak(){
		return bestStreak;
	}
	
	public void setBestStreak(int bestStreak){
		this.bestStreak = bestStreak;
		
		Database.get().update("UHC-BestStreak", "beststreak", "" + getBestStreak(), "uuid", getPlayer().getUUID().toString());
	}

    public boolean isDoubleIron() {
        return doubleIron;
    }

    public void setDoubleIron(boolean doubleIron) {
        this.doubleIron = doubleIron;
    }

    public boolean isBlueGold() {
        return blueGold;
    }

    public void setBlueGold(boolean blueGold) {
        this.blueGold = blueGold;
    }

    public boolean isRedGold() {
        return redGold;
    }

    public void setRedGold(boolean redGold) {
        this.redGold = redGold;
    }
}
