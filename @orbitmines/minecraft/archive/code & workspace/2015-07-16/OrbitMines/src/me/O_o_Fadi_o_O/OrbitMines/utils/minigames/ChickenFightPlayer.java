package me.O_o_Fadi_o_O.OrbitMines.utils.minigames;

import java.util.List;
import java.util.UUID;

import me.O_o_Fadi_o_O.OrbitMines.utils.Database;
import me.O_o_Fadi_o_O.OrbitMines.utils.Kit;
import me.O_o_Fadi_o_O.OrbitMines.utils.ServerData.ServerStorage;

import org.bukkit.entity.Player;

public class ChickenFightPlayer {

	private Player player;
	private int kills;
	private int wins;
	private int loses;
	private int beststreak;
	private int roundkills;
	private Kit kitselected;
	
	public ChickenFightPlayer(Player player, int kills, int wins, int loses, int beststreak){
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
		
		Database.get().update("MiniGameKills", "kills", "" + getKills(), "uuid", getUUID().toString());
	}
	public void addKill(){
		this.kills = getKills() +1;
		
		Database.get().update("MiniGameKills", "kills", "" + getKills(), "uuid", getUUID().toString());
	}

	public int getWins(){
		return wins;
	}
	public void setWins(int wins){
		this.wins = wins;
		
		Database.get().update("MiniGameWins", "wins", "" + getWins(), "uuid", getUUID().toString());
	}
	public void addWin(){
		this.wins = getWins() +1;
		
		Database.get().update("MiniGameWins", "wins", "" + getWins(), "uuid", getUUID().toString());
	}

	public int getLoses(){
		return loses;
	}
	public void setLoses(int loses){
		this.loses = loses;
		
		Database.get().update("MiniGameLoses", "loses", "" + getLoses(), "uuid", getUUID().toString());
	}
	public void addLose(){
		this.loses = getLoses() +1;
		
		Database.get().update("MiniGameLoses", "loses", "" + getLoses(), "uuid", getUUID().toString());
	}

	public int getBeststreak(){
		return beststreak;
	}
	public void setBeststreak(int beststreak){
		this.beststreak = beststreak;
		
		Database.get().update("ChickenFight-BestStreak", "beststreak", "" + getBeststreak(), "uuid", getUUID().toString());
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

	public Kit getKit() {
		return kitselected;
	}
	public void setKit(Kit kitselected) {
		this.kitselected = kitselected;
	}
	
	private UUID getUUID(){
		return getPlayer().getUniqueId();
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
	
	public static ChickenFightPlayer addCFPlayer(Player player, int kills, int wins, int loses, int beststreak){
		ChickenFightPlayer cfplayer = new ChickenFightPlayer(player, kills, wins, loses, beststreak);
		ServerStorage.cfplayers.add(cfplayer);
		return cfplayer;
	}
}
