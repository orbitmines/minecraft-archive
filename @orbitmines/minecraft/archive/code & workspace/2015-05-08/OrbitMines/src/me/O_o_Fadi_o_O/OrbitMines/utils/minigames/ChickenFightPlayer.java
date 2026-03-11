package me.O_o_Fadi_o_O.OrbitMines.utils.minigames;

import java.util.List;

import me.O_o_Fadi_o_O.OrbitMines.utils.ServerData.ServerStorage;

import org.bukkit.entity.Player;

public class ChickenFightPlayer {

	private Player player;
	private int kills;
	private int wins;
	private int loses;
	private int beststreak;
	private List<ChickenFightKit> unlockedkits;
	
	private int roundkills;
	
	public ChickenFightPlayer(Player player, int kills, int wins, int loses, int beststreak, List<ChickenFightKit> unlockedkits){
		this.player = player;
		this.kills = kills;
		this.wins = wins;
		this.loses = loses;
		this.beststreak = beststreak;
		this.unlockedkits = unlockedkits;
		
		this.roundkills = 0;
	}

	public Player getPlayer(){
		return player;
	}
	public void setPlayer(Player player){
		this.player = player;
	}

	public int getKills(){
		return kills;
	}
	public void setKills(int kills){
		this.kills = kills;
	}
	public void addKill(){
		this.kills = getKills() +1;
	}

	public int getWins(){
		return wins;
	}
	public void setWins(int wins){
		this.wins = wins;
	}
	public void addWin(){
		this.wins = getWins() +1;
	}

	public int getLoses(){
		return loses;
	}
	public void setLoses(int loses){
		this.loses = loses;
	}
	public void addLose(){
		this.loses = getLoses() +1;
	}

	public int getBeststreak(){
		return beststreak;
	}
	public void setBeststreak(int beststreak){
		this.beststreak = beststreak;
	}

	public int getRoundKills(){
		return roundkills;
	}
	public void setRoundKills(int roundkills){
		this.roundkills = roundkills;
	}
	public void addRoundKill(){
		this.roundkills = getRoundKills() +1;
	}

	public List<ChickenFightKit> getUnlockedKits(){
		return unlockedkits;
	}
	public void setUnlockedKits(List<ChickenFightKit> unlockedkits){
		this.unlockedkits = unlockedkits;
	}
	
	public static List<ChickenFightPlayer> getCFPlayers(){
		return ServerStorage.cfplayers;
	}
	
	public static ChickenFightPlayer getCFPlayer(Player player){
		for(ChickenFightPlayer cfplayer : ServerStorage.cfplayers){
			if(cfplayer.getPlayer() == player){
				return cfplayer;
			}
		}
		return null;
	}
	
	public static ChickenFightPlayer addCFPlayer(Player player, int kills, int wins, int loses, int beststreak, List<ChickenFightKit> unlockedkits){
		ChickenFightPlayer cfplayer = new ChickenFightPlayer(player, kills, wins, loses, beststreak, unlockedkits);
		ServerStorage.cfplayers.add(cfplayer);
		return cfplayer;
	}
}
