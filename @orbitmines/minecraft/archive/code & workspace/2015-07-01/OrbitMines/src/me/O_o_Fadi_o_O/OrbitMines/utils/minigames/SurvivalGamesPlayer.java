package me.O_o_Fadi_o_O.OrbitMines.utils.minigames;

import java.util.List;

import me.O_o_Fadi_o_O.OrbitMines.utils.ServerData.ServerStorage;

import org.bukkit.entity.Player;

public class SurvivalGamesPlayer {

	private Player player;
	private int kills;
	private int wins;
	private int loses;
	private int beststreak;
	
	private int roundkills;
	
	public SurvivalGamesPlayer(Player player, int kills, int wins, int loses, int beststreak){
		this.player = player;
		this.kills = kills;
		this.wins = wins;
		this.loses = loses;
		this.beststreak = beststreak;
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
	
	public static List<SurvivalGamesPlayer> getSGPlayers(){
		return ServerStorage.sgplayers;
	}
	
	public static SurvivalGamesPlayer getSGPlayer(Player player){
		for(SurvivalGamesPlayer sgplayer : ServerStorage.sgplayers){
			if(sgplayer.getPlayer() == player){
				return sgplayer;
			}
		}
		return null;
	}
	
	public static SurvivalGamesPlayer addSGPlayer(Player player, int kills, int wins, int loses, int beststreak){
		SurvivalGamesPlayer sgplayer = new SurvivalGamesPlayer(player, kills, wins, loses, beststreak);
		ServerStorage.sgplayers.add(sgplayer);
		return sgplayer;
	}
}
