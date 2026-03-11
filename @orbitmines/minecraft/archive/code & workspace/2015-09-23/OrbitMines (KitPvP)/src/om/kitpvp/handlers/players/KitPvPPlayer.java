package om.kitpvp.handlers.players;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import om.api.API;
import om.api.handlers.Database;
import om.api.handlers.Hologram;
import om.api.handlers.Kit;
import om.api.handlers.Title;
import om.api.handlers.players.OMPlayer;
import om.api.utils.enums.Cooldown;
import om.api.utils.enums.Server;
import om.kitpvp.KitPvP;
import om.kitpvp.enums.KitPvPKit;
import om.kitpvp.handlers.KitPvPMap;
import om.kitpvp.handlers.Masteries;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class KitPvPPlayer extends OMPlayer {
	
	private static KitPvP kitpvp;
	private int kills;
	private int deaths;
	private int levels;
	private int exp;
	private int money;
	private int beststreak;
	private int currentstreak;
	private HashMap<KitPvPKit, Integer> unlockedkits;
	private KitPvPKit kitselected;
	private Masteries masteries;
	private int kitlevelselected;
	private boolean isspectator;
	private int arrowseconds;
	private List<Entity> summonedundeath;
	
	public KitPvPPlayer(Player player, boolean loaded) {
		super(player, loaded);
		
		kitpvp = KitPvP.getInstance();
		kitpvp.getPlayers().put(player, this);
		kitpvp.getKitPvPPlayers().add(this);
	}
	
	@Override
	public String getPrefix(){
		if(isSpectator()) return "§eSpec §8| ";
		return "";
	}
	
	@Override
	public void vote() {
		updateVotes();
		addMoney(500);
		
		Player p = getPlayer();
		new Title("§b§lVote", "§6+500 Coins").send(p);
		
		p.sendMessage("");
		p.sendMessage("§b§lVote §8| §7Thank you, §b§l" + p.getName() + " §7for your §b§lVote§7!");
		p.sendMessage("§b§lVote §8| §7Your reward in the " + Server.KITPVP.getName() + "§7 Server:");
		p.sendMessage("§b§lVote §8| §7");

		for(String s : Server.KITPVP.getVoteRewardMessages()){
			p.sendMessage("§b§lVote §8| §7  - " + s);
		}
		
		p.sendMessage("§b§lVote §8| §7");
		p.sendMessage("§b§lVote §8| §7Your Total Votes this Month: §b§l" + getVotes());
		p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 1);
		
		for(Player player : Bukkit.getOnlinePlayers()){
			if(player != p) player.sendMessage("§b§l" + p.getName() + "§7 has voted with §b§l/vote§7.");
		}
	}
	
	@Override
	public void unloadPlayerData() {
		getPlayer().teleport(kitpvp.getSpawn());
		clearInventory();
		
		if(getKitSelected() != null){
			addDeath();
		}
		
		kitpvp.getKitPvPPlayers().remove(this);
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
		
		Database.get().update("KitPvP-Levels", "levels", "" + this.levels, "uuid", getUUID().toString());
	}
	public void addLevel(){
		this.levels += 1;

		getPlayer().playSound(getPlayer().getLocation(), Sound.LEVEL_UP, 5, 1);
		getPlayer().sendMessage("§a§lLevel Up! §7(§c§l+ 1 Mastery Point§7)");
		getMasteries().addPoints(1);
		getMasteries().update();
		Database.get().update("KitPvP-Levels", "levels", "" + this.levels, "uuid", getUUID().toString());
	}

	public int getExp(){
		return exp;
	}
	public void setExp(int exp){
		this.exp = exp;
		
		Database.get().update("KitPvP-Exp", "exp", "" + this.exp, "uuid", getUUID().toString());
	}
	public void addExp(int exp){
		this.exp += exp * getExpBooster();
		
		Database.get().update("KitPvP-Exp", "exp", "" + this.exp, "uuid", getUUID().toString());
	}
	
	public double getExpRequired(){
		int level = this.levels +1;
		return ((level * 10) / 3 + level * level) * 3;
	}
	public boolean isLevelUp(){
		return getExp() >= getExpRequired();
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
		
		// Reset Cooldowns \\
		this.arrowseconds = -1;
		if(kitselected == null){
			OMPlayer omp = OMPlayer.getOMPlayer(getPlayer());
			
			for(Cooldown cooldown : Cooldown.getKitPvPCooldowns()){
				omp.removeCooldown(cooldown);
			}
		}
	}

	public Masteries getMasteries(){
		return masteries;
	}
	public void setMasteries(Masteries masteries){
		this.masteries = masteries;
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

	public int getArrowSeconds(){
		return arrowseconds;
	}
	public void setArrowSeconds(int arrowseconds){
		this.arrowseconds = arrowseconds;
	}
	public void tickArrowTimer(){
		this.arrowseconds--;
	}

	public List<Entity> getSummonedUndeath(){
		return summonedundeath;
	}
	public void setSummonedUndeath(List<Entity> summonedundeath){
		this.summonedundeath = summonedundeath;
	}
	
	public void giveKit(KitPvPKit kitpvpkit, int level){
		clearInventory();
		clearPotionEffects();
		
		Kit kit = kitpvpkit.getKit(level);
		kit.setItems(getPlayer());
		
		setKitSelected(kitpvpkit);
		setKitLevelSelected(level);

		getPlayer().sendMessage("§7Selected Kit: '§b§l" + kitpvpkit.getName() + "§7' §7§o(§a§oLvL " + level + "§7§o)");
		Title t = new Title("", "§7Selected Kit '§b§l" + kitpvpkit.getName() + "§7' (§aLevel " + level + "§7)");
		t.send(getPlayer());
	}
	
	public void updateLevel(){
		getPlayer().setLevel(this.levels);
		getPlayer().setExp((float) getExp() / (float) getExpRequired());
	}
	
	public void teleportToMap(){
		KitPvPMap map = kitpvp.getCurrentMap();
		
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
		}.runTaskLater(API.getInstance(), 100);
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
	
	public double getVIPBooster(){
		switch(getVIPRank()){
			case Diamond_VIP:
				return 1.20;
			case Emerald_VIP:
				return 1.50;
			default:
				return 1.00;
		}
	}
	
	public double getExpBooster(){
		switch(getVIPRank()){
			case Diamond_VIP:
				return 2.00;
			case Emerald_VIP:
				return 2.50;
			case Gold_VIP:
				return 1.50;
			default:
				return 1.00;
		}
	}
	
	public static KitPvPPlayer getKitPvPPlayer(Player player){
		for(KitPvPPlayer kitpvpplayer : kitpvp.getKitPvPPlayers()){
			if(kitpvpplayer.getPlayer() == player){
				return kitpvpplayer;
			}
		}
		return null;
	}
	
	@Override
	public void loadPlayerData(){
		this.currentstreak = 0;
		this.isspectator = false;
		this.summonedundeath = new ArrayList<Entity>();
		
		String uuid = getUUID().toString();
		try{
			kitpvp.giveLobbyKit(this);
			
			loadKits(uuid);
			loadMasteries(uuid);
			loadStats(uuid);
			
			loadPets(uuid);
			
			updateLevel();
		}catch(NullPointerException ex){
			ex.printStackTrace();
		}
	}
	
	private void loadKits(String uuid){
		if(!Database.get().containsPath("Kits-Knight", "uuid", "uuid", uuid)){
			Database.get().insert("Kits-Knight", "uuid`, `knight", uuid + "', '" + 1);
		}
		if(!Database.get().containsPath("Kits-Archer", "uuid", "uuid", uuid)){
			Database.get().insert("Kits-Archer", "uuid`, `archer", uuid + "', '" + 1);
		}
		
		this.unlockedkits = new HashMap<KitPvPKit, Integer>();
		for(KitPvPKit kit : KitPvPKit.values()){
			if(kit != KitPvPKit.FARMER && kit != KitPvPKit.UNDEATH_KING && kit != KitPvPKit.ENGINEER){
				if(Database.get().containsPath("Kits-" + kit.getName(), "uuid", "uuid", uuid)){
					this.unlockedkits.put(kit, Database.get().getInt("Kits-" + kit.getName(), kit.getName().toLowerCase(), "uuid", uuid));
				}
			}
		}
	}
	
	private void loadMasteries(String uuid){
		String masteries = levels + "|0|0|0|0";
		if(Database.get().containsPath("KitPvP-Masteries", "uuid", "uuid", uuid)){
			masteries = Database.get().getString("KitPvP-Masteries", "masteries", "uuid", uuid);
		}
		else{
			Database.get().insert("KitPvP-Masteries", "uuid`, `masteries", uuid + "', '" + masteries);
		}
		
		this.masteries = Masteries.fromString(getPlayer(), masteries);
	}
	
	private void loadStats(String uuid){
		if(Database.get().containsPath("KitPvP-Kills", "uuid", "uuid", uuid)){
			kills = Database.get().getInt("KitPvP-Kills", "kills", "uuid", uuid);
		}
		else{
			Database.get().insert("KitPvP-Kills", "uuid`, `kills", uuid + "', '" + 0);
		}

		if(Database.get().containsPath("KitPvP-Deaths", "uuid", "uuid", uuid)){
			deaths = Database.get().getInt("KitPvP-Deaths", "deaths", "uuid", uuid);
		}
		else{
			Database.get().insert("KitPvP-Deaths", "uuid`, `deaths", uuid + "', '" + 0);
		}

		if(Database.get().containsPath("KitPvP-Levels", "uuid", "uuid", uuid)){
			levels = Database.get().getInt("KitPvP-Levels", "levels", "uuid", uuid);
		}
		else{
			Database.get().insert("KitPvP-Levels", "uuid`, `levels", uuid + "', '" + 0);
		}

		if(Database.get().containsPath("KitPvP-Exp", "uuid", "uuid", uuid)){
			exp = Database.get().getInt("KitPvP-Exp", "exp", "uuid", uuid);
		}
		else{
			Database.get().insert("KitPvP-Exp", "uuid`, `exp", uuid + "', '" + 0);
		}

		if(Database.get().containsPath("KitPvP-Money", "uuid", "uuid", uuid)){
			money = Database.get().getInt("KitPvP-Money", "money", "uuid", uuid);
		}
		else{
			Database.get().insert("KitPvP-Money", "uuid`, `money", uuid + "', '" + 0);
		}

		if(Database.get().containsPath("KitPvP-BestStreak", "uuid", "uuid", uuid)){
			beststreak = Database.get().getInt("KitPvP-BestStreak", "beststreak", "uuid", uuid);
		}
		else{
			Database.get().insert("KitPvP-BestStreak", "uuid`, `beststreak", uuid + "', '" + 0);
		}
	}
}
