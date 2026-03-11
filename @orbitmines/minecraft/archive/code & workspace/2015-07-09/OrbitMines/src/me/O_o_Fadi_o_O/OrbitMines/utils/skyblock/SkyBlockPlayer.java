package me.O_o_Fadi_o_O.OrbitMines.utils.skyblock;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import me.O_o_Fadi_o_O.OrbitMines.managers.ConfigManager;
import me.O_o_Fadi_o_O.OrbitMines.utils.ServerData.ServerStorage;
import me.O_o_Fadi_o_O.OrbitMines.utils.Utils.Cooldown;
import me.O_o_Fadi_o_O.OrbitMines.utils.OMPlayer;
import me.O_o_Fadi_o_O.OrbitMines.utils.Utils;
import me.O_o_Fadi_o_O.OrbitMines.utils.skyblock.SkyBlockUtils.IslandRank;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class SkyBlockPlayer {

	private Player player;
	private Island island;
	private IslandRank islandrank;
	private Location homelocation;
	private HashMap<Challenge, Integer> challenges;
	
	public SkyBlockPlayer(Player player){
		this.player = player;
		this.challenges = new HashMap<Challenge, Integer>();
		
		FileConfiguration playerdata = ConfigManager.playerdata;
		if(playerdata.contains("players." + getUUID().toString() + ".IslandInfo")){
			this.island = Island.getIsland(playerdata.getInt("players." + getUUID().toString() + ".IslandInfo.IslandNumber"));
			this.islandrank = IslandRank.valueOf(playerdata.getString("players." + getUUID().toString() + ".IslandInfo.IslandRank"));
			this.homelocation = Utils.getLocationFromString(playerdata.getString("players." + getUUID().toString() + ".HomeLocation"));
			
			String[] challengeparts = playerdata.getString("players." + getUUID().toString() + ".Challenges").split("\\|");
			for(String challenge : challengeparts){
				String[] cparts = challenge.split("\\:");
				
				this.challenges.put(Challenge.getChallenge(Integer.parseInt(cparts[0])), Integer.parseInt(cparts[1]));
			}
		}
	}

	public Player getPlayer() {
		return player;
	}
	public void setPlayer(Player player) {
		this.player = player;
	}

	public Island getIsland() {
		return island;
	}
	public void setIsland(Island island, IslandRank islandrank) {
		this.island = island;
		this.islandrank = islandrank;
		this.challenges = new HashMap<Challenge, Integer>();
		
		FileConfiguration playerdata = ConfigManager.playerdata;
		playerdata.set("players." + getUUID().toString() + ".IslandInfo.IslandNumber", island.getIslandID());
		playerdata.set("players." + getUUID().toString() + ".IslandInfo.IslandRank", islandrank.toString());
		if(isOwner()){
			Location l = island.getLocation();
			playerdata.set("players." + getUUID().toString() + ".IslandInfo.HomeLocation", Utils.getStringFromLocation(new Location(l.getWorld(), l.getBlockX() +0.5, l.getBlockY(), l.getBlockZ() +2.5, 180, 0)));
		}
		else{
			playerdata.set("players." + getUUID().toString() + ".IslandInfo.HomeLocation", Utils.getStringFromLocation(island.getOwnersHomeLocation()));
		}
		playerdata.set("players." + getUUID().toString() + ".Challenges", null);
		ConfigManager.savePlayerData();
	}
	public boolean hasIsland(){
		return this.island != null;
	}

	public IslandRank getIslandRank() {
		return islandrank;
	}
	public void setIslandRank(IslandRank islandrank) {
		this.islandrank = islandrank;
		
		ConfigManager.playerdata.set("players." + getUUID().toString() + ".IslandInfo.IslandRank", islandrank.toString());
		ConfigManager.savePlayerData();
	}
	public boolean isOwner(){
		return this.islandrank != null && this.islandrank == IslandRank.OWNER;
	}
	public boolean isMember(){
		return this.islandrank != null && this.islandrank == IslandRank.MEMBER;
	}

	public Location getHomeLocation() {
		return homelocation;
	}
	public void setHomeLocation(Location homelocation) {
		this.homelocation = homelocation;
		
		ConfigManager.playerdata.set("players." + getUUID().toString() + ".IslandInfo.HomeLocation", Utils.getStringFromLocation(this.homelocation));
		ConfigManager.savePlayerData();
	}

	public HashMap<Challenge, Integer> getChallenges() {
		return challenges;
	}
	public void setChallenges(HashMap<Challenge, Integer> challenges) {
		this.challenges = challenges;
		
		saveChallenges();
	}
	public void setChallengeCompleted(Challenge challenge){
		if(this.challenges.containsKey(challenge)){
			this.challenges.put(challenge, this.challenges.get(challenge) +1);
		}
		else{
			this.challenges.put(challenge, 1);
		}
		
		saveChallenges();
	}
	public int getChallengeCompleted(Challenge challenge){
		if(this.challenges.containsKey(challenge)){
			return this.challenges.get(challenge);
		}
		return 0;
	}
	private void saveChallenges(){
		String cstring = null;
		
		for(Challenge c : this.challenges.keySet()){
			if(cstring == null){
				cstring = c.getChallengeID() + ":" + this.challenges.get(c);
			}
			else{
				cstring += ";" + c.getChallengeID() + ":" + this.challenges.get(c);
			}
		}
		
		ConfigManager.playerdata.set("players." + getUUID().toString() + ".Challenges", cstring);
		ConfigManager.savePlayerData();
	}
	
	public boolean inVoid(){
		Location l = getPlayer().getLocation();
		
		for(int i = 1; i <= l.getBlockY(); i++){
			Block b = l.getWorld().getBlockAt(new Location(l.getWorld(), l.getBlockX(), i, l.getBlockZ()));
			
			if(!b.isEmpty()){
				return false;
			}
		}
		return true;
	}
	
	public boolean onOwnIsland(Location l2, boolean message){
		Location l = getIsland().getLocation();
		int x = l.getBlockX();
		int z = l.getBlockZ();
		
		int bDistance = 0;
		int xB = l2.getBlockX() -x;
		int zB = l2.getBlockZ() -z;
		
		if(xB < 0){
			xB = -xB;
		}
		if(zB < 0){
			zB = -zB;
		}
		
		if(xB <= zB){
			bDistance = zB;
		}
		else{
			bDistance = xB;
		}
		
		bDistance = 50 - bDistance;
		
		if(bDistance < 0){
			OMPlayer omp = OMPlayer.getOMPlayer(getPlayer());
			if(message && !omp.onCooldown(Cooldown.MESSAGE)){
				getPlayer().sendMessage("§7You're not on your own §dIsland§7!");
				
				omp.resetCooldown(Cooldown.MESSAGE);
			}
			
			return false;
		}
		return true;
	}
	
	private UUID getUUID(){
		return getPlayer().getUniqueId();
	}
	
	public static List<SkyBlockPlayer> getSkyBlockPlayers(){
		return ServerStorage.skyblockplayers;
	}
	
	public static SkyBlockPlayer getSkyBlockPlayer(Player player){
		for(SkyBlockPlayer sbp : ServerStorage.skyblockplayers){
			if(sbp.getPlayer() == player){
				return sbp;
			}
		}
		return null;
	}
	
	public static SkyBlockPlayer addSkyBlockPlayer(Player player){
		SkyBlockPlayer sbp = new SkyBlockPlayer(player);
		ServerStorage.skyblockplayers.add(sbp);
		return sbp;
	}
}
