package me.O_o_Fadi_o_O.OMHub.utils.orbitmines;

import java.util.HashMap;
import java.util.List;

import me.O_o_Fadi_o_O.OMHub.utils.orbitmines.ServerData.ServerStorage;

import org.bukkit.entity.Player;

public class KitPvPPlayer {

	private Player player;
	private int kills;
	private int deaths;
	private int levels;
	private int money;
	private int beststreak;
	private int currentstreak;
	private HashMap<KitPvPKit, Integer> unlockedkits;
	
	public KitPvPPlayer(Player player, int kills, int deaths, int levels, int money, int beststreak, HashMap<KitPvPKit, Integer> unlockedkits){
		this.player = player;
		this.kills = kills;
		this.deaths = deaths;
		this.levels = levels;
		this.money = money;
		this.beststreak = beststreak;
		this.currentstreak = 0;
		this.unlockedkits = unlockedkits;
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

	public int getDeaths(){
		return deaths;
	}
	public void setDeaths(int deaths){
		this.deaths = deaths;
	}
	public void addDeath(){
		this.deaths = getDeaths() +1;
	}

	public int getLevels(){
		return levels;
	}
	public void setLevels(int levels){
		this.levels = levels;
	}
	public void addLevel(){
		this.levels = getLevels() +1;
	}

	public int getMoney(){
		return money;
	}
	public void setMoney(int money){
		this.money = money;
	}
	public void addMoney(int money){
		this.money = getMoney() +money;
	}
	public void removeMoney(int money){
		this.money = getMoney() -money;
	}
	public boolean hasMoney(int money){
		if(getMoney() >= money){
			return true;
		}
		return false;
	}

	public int getBestStreak(){
		return beststreak;
	}
	public void setBestStreak(int beststreak){
		this.beststreak = beststreak;
	}

	public int getCurrentStreak(){
		return currentstreak;
	}
	public void setCurrentStreak(int currentstreak){
		this.currentstreak = currentstreak;
	}

	public HashMap<KitPvPKit, Integer> getUnlockedKits(){
		return unlockedkits;
	}
	public void setUnlockedKits(HashMap<KitPvPKit, Integer> unlockedkits){
		this.unlockedkits = unlockedkits;
	}
	public void setUnlockedKitLevel(KitPvPKit kit, int level){
		this.unlockedkits.put(kit, level);
	}
	
	public static List<KitPvPPlayer> getKitPvPPlayers(){
		return ServerStorage.kitpvpplayers;
	}
	
	public static KitPvPPlayer getKitPvPPlayer(Player player){
		for(KitPvPPlayer kitpvpplayer : ServerStorage.kitpvpplayers){
			if(kitpvpplayer.getPlayer() == player){
				return kitpvpplayer;
			}
		}
		return null;
	}
	
	public static KitPvPPlayer addKitPvPPlayer(Player player, int kills, int deaths, int levels, int money, int beststreak, HashMap<KitPvPKit, Integer> unlockedkits){
		KitPvPPlayer kitpvpplayer = new KitPvPPlayer(player, kills, deaths, levels, money, beststreak, unlockedkits);
		ServerStorage.kitpvpplayers.add(kitpvpplayer);
		return kitpvpplayer;
	}
}
