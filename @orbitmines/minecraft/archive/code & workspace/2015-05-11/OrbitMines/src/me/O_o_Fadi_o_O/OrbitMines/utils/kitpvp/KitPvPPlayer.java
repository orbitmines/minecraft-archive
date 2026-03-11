package me.O_o_Fadi_o_O.OrbitMines.utils.kitpvp;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import me.O_o_Fadi_o_O.OrbitMines.Start;
import me.O_o_Fadi_o_O.OrbitMines.utils.Database;
import me.O_o_Fadi_o_O.OrbitMines.utils.Hologram;
import me.O_o_Fadi_o_O.OrbitMines.utils.Kit;
import me.O_o_Fadi_o_O.OrbitMines.utils.OMPlayer;
import me.O_o_Fadi_o_O.OrbitMines.utils.ServerData;
import me.O_o_Fadi_o_O.OrbitMines.utils.ServerData.ServerStorage;
import me.O_o_Fadi_o_O.OrbitMines.utils.Title;
import me.O_o_Fadi_o_O.OrbitMines.utils.kitpvp.KitPvPUtils.KitPvPKit;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class KitPvPPlayer {

	private Player player;
	private int kills;
	private int deaths;
	private int levels;
	private int money;
	private int beststreak;
	private int currentstreak;
	private HashMap<KitPvPKit, Integer> unlockedkits;
	private KitPvPKit kitselected;
	private int kitlevelselected;
	private boolean isspectator;
	
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
		
		Database.get().update("KitPvP-Kills", "kills", "" + this.kills, "uuid", getUUID().toString());
	}
	public void addKill(){
		this.kills = getKills() +1;
		
		Database.get().update("KitPvP-Kills", "kills", "" + this.kills, "uuid", getUUID().toString());
	}

	public int getDeaths(){
		return deaths;
	}
	public void setDeaths(int deaths){
		this.deaths = deaths;
		
		Database.get().update("KitPvP-Deaths", "deaths", "" + this.deaths, "uuid", getUUID().toString());
	}
	public void addDeath(){
		this.deaths += 1;
		
		Database.get().update("KitPvP-Deaths", "deaths", "" + this.deaths, "uuid", getUUID().toString());
	}

	public int getLevels(){
		return levels;
	}
	public void setLevels(int levels){
		this.levels = levels;
		
		Database.get().update("KitPvP-Levels", "level", "" + this.levels, "uuid", getUUID().toString());
	}
	public void addLevel(){
		this.levels += 1;
		
		Database.get().update("KitPvP-Levels", "level", "" + this.levels, "uuid", getUUID().toString());
	}

	public int getMoney(){
		return money;
	}
	public void setMoney(int money){
		this.money = money;
		
		Database.get().update("KitPvP-Money", "money", "" + this.money, "uuid", getUUID().toString());
	}
	public void addMoney(int money){
		this.money += money;
		
		Database.get().update("KitPvP-Money", "money", "" + this.money, "uuid", getUUID().toString());
	}
	public void removeMoney(int money){
		this.money -= money;
		
		Database.get().update("KitPvP-Money", "money", "" + this.money, "uuid", getUUID().toString());
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
		
		Database.get().update("KitPvP-BestStreak", "beststreak", "" + this.beststreak, "uuid", getUUID().toString());
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
		
		if(!Database.get().containsPath("Kits-" + kit.getName(), "uuid", "uuid", getUUID().toString())){
			Database.get().insert("Kits-" + kit.getName(), "uuid`, `" + kit.getName().toLowerCase(), getUUID().toString() + "', '" + 1);
		}
		else{
			Database.get().update("Kits-" + kit.getName(), kit.getName().toLowerCase(), "" + level, "uuid", getUUID().toString());
		}
	}
	public int getUnlockedLevel(KitPvPKit kit){
		if(this.unlockedkits.containsKey(kit)){
			return this.unlockedkits.get(kit);
		}
		return 0;
	}

	public KitPvPKit getKitSelected(){
		return kitselected;
	}
	public void setKitSelected(KitPvPKit kitselected){
		this.kitselected = kitselected;
	}

	public int getKitLevelSelected(){
		return kitlevelselected;
	}
	public void setKitLevelSelected(int kitlevelselected){
		this.kitlevelselected = kitlevelselected;
	}

	public boolean isSpectator(){
		return isspectator;
	}
	public void setSpectator(){
		this.isspectator = true;
	}
	public boolean isPlayer(){
		return !isspectator;
	}
	public void setPlayer(){
		this.isspectator = false;
	}
	
	public void giveKit(KitPvPKit kitpvpkit, int level){
		OMPlayer omp = OMPlayer.getOMPlayer(getPlayer());
		
		omp.clearInventory();
		omp.clearPotionEffects();
		
		Kit kit = kitpvpkit.getKit(level);
		kit.setItems(getPlayer());
		
		setKitSelected(kitpvpkit);
		setKitLevelSelected(level);
		
		Title t = new Title("", "§7Selected Kit '§b§l" + kitpvpkit.getName() + "§7' (§aLevel " + level + "§7)");
		t.send(getPlayer());
	}
	
	public void updateLevel(){
		getPlayer().setLevel(this.levels);
		getPlayer().setExp(1);
	}
	
	public UUID getUUID(){
		return this.player.getUniqueId();
	}
	
	public void teleportToMap(){
		Map map = ServerData.getKitPvP().getCurrentMap();
		
		if(isPlayer()){
			getPlayer().teleport(map.getRandomSpawn());
		}
		else{
			getPlayer().teleport(map.getSpectatorSpawn());
		}
		map.sendJoinMessage(getPlayer());
    	getPlayer().playSound(getPlayer().getLocation(), Sound.ENDERMAN_TELEPORT, 5, 1);
	}
	
	public void createKillHologram(Player killer, int coinsadded){
		final Hologram h = new Hologram(getPlayer().getLocation());
		h.addLine("§7You killed §6" + getPlayer().getName() + "§7!");
		h.addLine("§6§l+" + coinsadded + " Coins");
		h.create(killer);
		
		new BukkitRunnable(){
			public void run(){
				h.delete();
			}
		}.runTaskLater(Start.getInstance(), 100);
	}
	
	public void requiredMoney(int price){
		Player p = getPlayer();
		
		p.playSound(p.getLocation(), Sound.ENDERMAN_SCREAM, 5, 1);
		int needed = price - getMoney();
		if(needed == 1){
			p.sendMessage("§c§lKitPvP §8| §7You need §6§l" + needed + "§7 more §6§lCoins§7!");
		}
		else{
			p.sendMessage("§c§lKitPvP §8| §7You need §6§l" + needed + "§7 more §6§lCoins§7!");
		}
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
