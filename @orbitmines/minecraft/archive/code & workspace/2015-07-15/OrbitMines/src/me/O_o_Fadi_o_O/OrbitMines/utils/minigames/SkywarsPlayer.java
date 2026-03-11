package me.O_o_Fadi_o_O.OrbitMines.utils.minigames;

import java.util.List;
import java.util.UUID;

import me.O_o_Fadi_o_O.OrbitMines.utils.Database;
import me.O_o_Fadi_o_O.OrbitMines.utils.ServerData.ServerStorage;

import org.bukkit.entity.Player;

public class SkywarsPlayer {

	private Player player;
	private int kills;
	private int wins;
	private int loses;
	private int beststreak;
	private int roundkills;
	
	public SkywarsPlayer(Player player, int kills, int wins, int loses, int beststreak){
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
		
		Database.get().update("Skywars-Kills", "kills", "" + getKills(), "uuid", getUUID().toString());
	}
	public void addKill(){
		this.kills = getKills() +1;
		
		Database.get().update("Skywars-Kills", "kills", "" + getKills(), "uuid", getUUID().toString());
	}

	public int getWins(){
		return wins;
	}
	public void setWins(int wins){
		this.wins = wins;
		
		Database.get().update("Skywars-Wins", "wins", "" + getWins(), "uuid", getUUID().toString());
	}
	public void addWin(){
		this.wins = getWins() +1;
		
		Database.get().update("Skywars-Wins", "wins", "" + getWins(), "uuid", getUUID().toString());
	}

	public int getLoses(){
		return loses;
	}
	public void setLoses(int loses){
		this.loses = loses;
		
		Database.get().update("Skywars-Loses", "loses", "" + getLoses(), "uuid", getUUID().toString());
	}
	public void addLose(){
		this.loses = getLoses() +1;
		
		Database.get().update("Skywars-Loses", "loses", "" + getLoses(), "uuid", getUUID().toString());
	}

	public int getBeststreak(){
		return beststreak;
	}
	public void setBeststreak(int beststreak){
		this.beststreak = beststreak;
		
		Database.get().update("Skywars-BestStreak", "beststreak", "" + getBeststreak(), "uuid", getUUID().toString());
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
	
	private UUID getUUID(){
		return getPlayer().getUniqueId();
	}
	
	public static List<SkywarsPlayer> getSWPlayers(){
		return ServerStorage.swplayers;
	}
	
	public static SkywarsPlayer getSWPlayer(Player player){
		for(SkywarsPlayer swplayer : ServerStorage.swplayers){
			if(swplayer.getPlayer() == player){
				return swplayer;
			}
		}
		return null;
	}
	
	public static SkywarsPlayer addSWPlayer(Player player, int kills, int wins, int loses, int beststreak){
		SkywarsPlayer swplayer = new SkywarsPlayer(player, kills, wins, loses, beststreak);
		ServerStorage.swplayers.add(swplayer);
		return swplayer;
	}
}
