package me.O_o_Fadi_o_O.OrbitMines.utils.survival;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import me.O_o_Fadi_o_O.OrbitMines.managers.ConfigManager;
import me.O_o_Fadi_o_O.OrbitMines.utils.OMPlayer;
import me.O_o_Fadi_o_O.OrbitMines.utils.ServerData;
import me.O_o_Fadi_o_O.OrbitMines.utils.ServerData.ServerStorage;
import me.O_o_Fadi_o_O.OrbitMines.utils.Utils;
import me.O_o_Fadi_o_O.OrbitMines.utils.Utils.VIPRank;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class SurvivalPlayer {

	private Player player;
	private int money;
	private List<Home> homes;
	private boolean hometeleporting;
	
	public SurvivalPlayer(Player player){
		this.player = player;
		this.homes = new ArrayList<Home>();
		this.hometeleporting = false;
		
		FileConfiguration playerdata = ConfigManager.playerdata;
		if(playerdata.contains("players." + getUUID().toString() + ".Homes")){
			for(String homename : playerdata.getConfigurationSection("players." + getUUID().toString() + ".Homes").getKeys(false)){
				this.homes.add(new Home(this, homename, Utils.getLocationFromString(playerdata.getString("players." + getUUID().toString() + ".Homes." + homename))));
			}
		}
	}

	public Player getPlayer() {
		return player;
	}
	public void setPlayer(Player player) {
		this.player = player;
	}

	public int getMoney() {
		return money;
	}
	public void setMoney(int money) {
		this.money = money;
	}
	public void addMoney(int money) {
		this.money += money;
	}
	public void removeMoney(int money) {
		this.money -= money;
	}

	public List<Home> getHomes() {
		return homes;
	}
	public void setHomes(List<Home> homes) {
		this.homes = homes;
	}
	public Home getHome(String homename){
		for(Home home : this.homes){
			if(home.getName().equals(homename)){
				return home;
			}
		}
		return null;
	}
	public void removeHome(Home home){
		this.homes.remove(this);
		
		ConfigManager.playerdata.set("players." + getUUID().toString() + ".Homes." + home.getName(), null);
		ConfigManager.savePlayerData();
	}
	public void setHome(String homename){
		Home home = getHome(homename);
		
		ConfigManager.playerdata.set("players." + getUUID().toString() + ".Homes." + homename, Utils.getStringFromLocation(this.player.getLocation()));
		ConfigManager.savePlayerData();
		
		if(home == null){
			this.homes.add(new Home(this, homename, this.player.getLocation()));
			this.player.sendMessage("§7New home §6set§7! (§6" + home + "§7)");
		}
		else{
			home.setLocation(this.player.getLocation());
			this.player.sendMessage("§7Home §6set§7! (§6" + home + "§7)");
		}
	}

	public boolean isHomeTeleporting() {
		return hometeleporting;
	}
	public void setHomeTeleporting(boolean hometeleporting) {
		this.hometeleporting = hometeleporting;
	}
	
	public void addClaimBlocks(int claimblocks) {
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "adjustbonusclaimblocks " + this.player.getName() + " " + claimblocks);
	}
	
	public int getHomesAllowed(){
		OMPlayer omp = OMPlayer.getOMPlayer(this.player);
		
		if(omp.hasPerms(VIPRank.Emerald_VIP)){
			return 100;
		}
		else if(omp.hasPerms(VIPRank.Diamond_VIP)){
			return 50;
		}
		else if(omp.hasPerms(VIPRank.Gold_VIP)){
			return 25;
		}
		else if(omp.hasPerms(VIPRank.Iron_VIP)){
			return 10;
		}
		else{
			return 3;
		}
	}
	
	public void teleportToPvPArea(){
		List<Location> pvpspawns = ServerData.getSurvival().getPvPSpawns();
		getPlayer().teleport(pvpspawns.get(new Random().nextInt(pvpspawns.size())));
	}
	
	private UUID getUUID(){
		return this.player.getUniqueId();
	}
	
	public static List<SurvivalPlayer> getSurvivalPlayers(){
		return ServerStorage.survivalplayers;
	}
	
	public static SurvivalPlayer getSurvivalPlayer(Player player){
		for(SurvivalPlayer sp : ServerStorage.survivalplayers){
			if(sp.getPlayer() == player){
				return sp;
			}
		}
		return null;
	}
	
	public static SurvivalPlayer addSurvivalPlayer(Player player){
		SurvivalPlayer sp = new SurvivalPlayer(player);
		ServerStorage.survivalplayers.add(sp);
		return sp;
	}
}
