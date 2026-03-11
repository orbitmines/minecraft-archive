package om.fog.handlers.players;

import java.util.HashMap;
import java.util.Map;

import om.api.handlers.ActionBar;
import om.api.handlers.Database;
import om.api.handlers.players.OMPlayer;
import om.fog.FoG;
import om.fog.handlers.Bank;
import om.fog.handlers.FoGMap;
import om.fog.handlers.Shields;
import om.fog.handlers.Swords;
import om.fog.utils.enums.Faction;
import om.fog.utils.enums.ShieldLore;
import om.fog.utils.enums.ShieldLore.ArmorType;
import om.fog.utils.enums.ShieldLore.ShieldLoreLevel;
import om.fog.utils.enums.Suit;
import om.fog.utils.enums.SwordLore;
import om.fog.utils.enums.SwordLore.SwordLoreLevel;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class FoGPlayer extends OMPlayer {
	
	private static FoG fog;
	private Faction faction;
	private Map<Suit, Integer> repairTokens;
	private int kills;
	private int mobKills;
	private int deaths;
	private int levels;
	private int exp;
	private int silver;
	private double currentShield;
	private double shield;
	private FoGMap map;
	private Suit suit;
	private Bank bank;
	private Map<Suit, Swords> swords;
	private Map<Suit, Shields> shields;
	private boolean inCombat;
	private int inCombatTimer;
	
	public FoGPlayer(Player player, boolean loaded) {
		super(player, loaded);
		
		fog = FoG.getInstance();
		updateMap();
		
		if(getFaction() == null){
			clearInventory();
			updateInventory();
			((CraftPlayer) getPlayer()).setMaxHealth(20.0D);
		}
		
		fog.getPlayers().put(player, this);
		fog.getFoGPlayers().add(this);
	}
	
	@Override
	public String getPrefix(){
		if(getFaction() != null) return faction.getName() + " §8| ";
		return "";
	}
	
	@Override
	public void vote() {
		updateVotes();
		//addSilver(500);
		
		Player p = getPlayer();
		//new Title("§b§lVote", "§6+500 Coins").send(p);
		
		p.sendMessage("");
		p.sendMessage("§b§lVote §8| §7Thank you, §b§l" + p.getName() + " §7for your §b§lVote§7!");
		//p.sendMessage("§b§lVote §8| §7Your reward in the " + Server.KITPVP.getName() + "§7 Server:");
		p.sendMessage("§b§lVote §8| §7");

		//for(String s : Server.KITPVP.getVoteRewardMessages()){
		//	p.sendMessage("§b§lVote §8| §7  - " + s);
		//}
		
		p.sendMessage("§b§lVote §8| §7");
		p.sendMessage("§b§lVote §8| §7Your Total Votes this Month: §b§l" + getVotes());
		p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 1);
		
		for(Player player : Bukkit.getOnlinePlayers()){
			if(player != p) player.sendMessage("§b§l" + p.getName() + "§7 has voted with §b§l/vote§7.");
		}
	}
	
	@Override
	public void unloadPlayerData() {
		
	}
	
	public Faction getFaction() {
		return faction;
	}
	
	public void setFaction(Faction faction) {
		this.faction = faction;
		
		//Database.get().update("FoG-Faction", "faction", "" + this.faction.toString(), "uuid", getUUID().toString());
	}
	
	public void joinFaction(Faction faction) {
		Bukkit.broadcastMessage(getName() + "§7 joined " + faction.getName() + "§7!");
		
		getPlayer().teleport(faction.getBaseLocation());
		//Give Items
		
		updateMap();
		setFaction(faction);
		for(Suit suit : getSwords().keySet()){
			getSwords(suit).setSwordsInv(suit, getFaction());
		}
		for(Suit suit : getShields().keySet()){
			getShields(suit).setShieldsInv(suit, getFaction());
		}
		setSuit(Suit.TRAINING_SUIT);
	}
	
	public Map<Suit, Integer> getRepairTokens() {
		return repairTokens;
	}
	
	public int getRepairTokens(Suit suit) {
		return repairTokens.get(suit);
	}

	public int getKills(){
		return kills;
	}
	public void setKills(int kills){
		this.kills = kills;
		
		Database.get().update("FoG-Kills", "kills", "" + this.kills, "uuid", getUUID().toString());
	}
	public void addKill(){
		this.kills = getKills() +1;
		
		Database.get().update("FoG-Kills", "kills", "" + this.kills, "uuid", getUUID().toString());
	}

	public int getMobKills(){
		return mobKills;
	}
	public void setMobKills(int mobKills){
		this.mobKills = mobKills;
		
		Database.get().update("FoG-MobKills", "mobkills", "" + this.mobKills, "uuid", getUUID().toString());
	}
	public void addMobKill(){
		this.mobKills = getKills() +1;
		
		Database.get().update("FoG-MobKills", "mobkills", "" + this.mobKills, "uuid", getUUID().toString());
	}

	public int getDeaths(){
		return deaths;
	}
	public void setDeaths(int deaths){
		this.deaths = deaths;
		
		Database.get().update("FoG-Deaths", "deaths", "" + this.deaths, "uuid", getUUID().toString());
	}
	public void addDeath(){
		this.deaths += 1;
		
		Database.get().update("FoG-Deaths", "deaths", "" + this.deaths, "uuid", getUUID().toString());
	}

	public int getLevels(){
		return levels;
	}
	public void setLevels(int levels){
		this.levels = levels;
		
		Database.get().update("FoG-Levels", "levels", "" + this.levels, "uuid", getUUID().toString());
	}
	public void addLevel(){
		this.levels += 1;

		getPlayer().playSound(getPlayer().getLocation(), Sound.LEVEL_UP, 5, 1);
		getPlayer().sendMessage("§a§lLevel Up!");
		Database.get().update("FoG-Levels", "levels", "" + this.levels, "uuid", getUUID().toString());
	}

	public int getExp(){
		return exp;
	}
	public void setExp(int exp){
		this.exp = exp;
		
		Database.get().update("FoG-Exp", "exp", "" + this.exp, "uuid", getUUID().toString());
	}
	public void addExp(int exp){
		this.exp += exp;
		
		Database.get().update("FoG-Exp", "exp", "" + this.exp, "uuid", getUUID().toString());
	}
	
	public double getExpRequired(){
		int level = this.levels +1;
		return ((level * 25) + level * level * level) * 12;
	}
	public boolean isLevelUp(){
		return getExp() >= getExpRequired();
	}

	public int getSilver(){
		return silver;
	}
	public void setSilver(int silver){
		this.silver = silver;
		
		Database.get().update("FoG-Silver", "silver", "" + this.silver, "uuid", getUUID().toString());
	}
	public void addSilver(int silver){
		this.silver += silver;
		
		Database.get().update("FoG-Silver", "silver", "" + this.silver, "uuid", getUUID().toString());
	}
	public void removeSilver(int silver){
		this.silver -= silver;
		
		Database.get().update("FoG-Silver", "silver", "" + this.silver, "uuid", getUUID().toString());
	}
	public boolean hasSilver(int silver){
		if(getSilver() >= silver){
			return true;
		}
		return false;
	}
	
	public void updateLevel(){
		getPlayer().setLevel(this.levels);
		getPlayer().setExp((float) getExp() / (float) getExpRequired());
	}
	
	public void requiredSilver(int price){
		Player p = getPlayer();
		
		p.playSound(p.getLocation(), Sound.ENDERMAN_SCREAM, 5, 1);
		int needed = price - getSilver();
		if(needed == 1){
			p.sendMessage("§7You need §7§l" + needed + "§7 more §7§lSilver§7!");
		}
		else{
			p.sendMessage("§7You need §7§l" + needed + "§7 more §7§lSilver§7!");
		}
	}
	
	public double getCurrentShield() {
		return currentShield;
	}
	public void setCurrentShield(double currentShield) {
		this.currentShield = currentShield;
	}
	public void damageShield(double currentShield) {
		this.currentShield -= currentShield;
	}
	public void regenShield(double currentShield) {
		this.currentShield += currentShield;
		
		if(this.currentShield > this.shield) this.currentShield = this.shield;
	}
	
	public double getShield() {
		return shield;
	}
	public void setShield(double shield) {
		this.shield = shield;
	}
	
	public FoGMap getMap() {
		return map;
	}
	
	public Suit getSuit() {
		return suit;
	}
	public void setSuit(Suit suit) {
		if(suit == null || suit != this.suit){
			this.suit = suit;
			
			if(suit != null){
				((CraftPlayer) getPlayer()).setMaxHealth(suit.getBaseHealth());
				getPlayer().getInventory().setArmorContents(suit.getArmorContents(getFaction()));
				
				Swords swords = getSwords(suit);
				swords.checkItems(getPlayer(), swords.getSword(this, suit));
				
				for(ArmorType type : ArmorType.values()){
					Shields shields = getShields(suit);
					shields.checkItems(this, type, shields.getShield(this,type, suit));
				}
				
				this.currentShield = suit.getBaseHealth();//TODO Load Shield Left
				this.shield = suit.getBaseHealth();
				updateShield();
			}
			else{
				this.currentShield = Suit.TRAINING_SUIT.getBaseHealth();
				this.shield = Suit.TRAINING_SUIT.getBaseHealth();
				((CraftPlayer) getPlayer()).setMaxHealth(Suit.TRAINING_SUIT.getBaseHealth());
				getPlayer().setHealth(Suit.TRAINING_SUIT.getBaseHealth());
			}
		}
	}
	
	public Bank getBank() {
		return bank;
	}
	
	public Map<Suit, Swords> getSwords() {
		return swords;
	}
	public Swords getSwords(Suit suit) {
		return swords.get(suit);
	}
	
	public Map<Suit, Shields> getShields() {
		return shields;
	}
	public Shields getShields(Suit suit) {
		return shields.get(suit);
	}
	
	public boolean isInCombat() {
		return inCombat;
	}
	public void setInCombat(boolean inCombat) {
		if(this.inCombat != inCombat){
			this.inCombat = inCombat;
			
			if(isInCombat()){
				getPlayer().sendMessage("§cYou've entered combat.");
			}
			else{
				getPlayer().sendMessage("§aYou're no longer in combat.");
			}
		}
	}
	public void setInCombat(){
		setInCombat(true);
		setInCombatTimer(20);
	}
	
	public int getInCombatTimer() {
		return inCombatTimer;
	}
	public void setInCombatTimer(int inCombatTimer) {
		this.inCombatTimer = inCombatTimer;
	}
	public void tickCombatTimer(){
		if(isInCombat()){
			this.inCombatTimer--;
			
			if(getInCombatTimer() == 0){
				setInCombat(false);
			}
		}
		else{
			if(getCurrentShield() != getShield()){
				int levels = 0;
				
				for(ItemStack item : getPlayer().getInventory().getArmorContents()){
					int level = ShieldLore.SHIELD_REGEN.getLevel(item);
					
					if(level != -1){
						levels += level;
					}
				}
				
				if(levels != 0){
					regenShield(getShield() * (0.01 * levels));
				}
			}
		}
	}
	
	public void updateCurrentBank(){
		new BukkitRunnable(){
			public void run(){
				updateCurrentBankNow();
			}
		}.runTaskLater(fog, 1);
	}
	
	public void updateCurrentBankNow(){
		ItemStack[] items = new ItemStack[36];
		
		int index = 0;
		for(ItemStack item : getBank().getBankInv().getInventory().getContents()){
			if(item != null && item.getType() != Material.BARRIER) items[index] = item; 
			
			index++;
			if(index == 36) break; 
		}
		
		getBank().setCurrentItems(items);
	}
	
	public void updateShield(){
		if(getSuit() != null){
			double extraShield = 0;
			for(ItemStack item : getPlayer().getInventory().getArmorContents()){
				int level = ShieldLore.SHIELD.getLevel(item);
				
				if(level != -1){
					extraShield += level * 4;
				}
			}
		
			if(getShield() != getSuit().getBaseHealth() + extraShield){
				setShield(getSuit().getBaseHealth() + extraShield);
			}
		}
	}
	
	public void updateShieldBar(){
		if(getFaction() != null){
			double red = (getCurrentShield() / getShield() * 100) + 2;
			String bar = "§b||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||";
			bar = bar.substring(0, (int) red) + "§c" + bar.substring((int) red);
			
			ActionBar actionbar = new ActionBar(bar);
			actionbar.send(getPlayer());
		}
	}
	
	public void updateMap(){
		Location l = getPlayer().getLocation();
		if(l.getWorld().getName().equals(fog.getGalaxyWorld().getName())){
			for(FoGMap map : fog.getMaps()){
				if(map.inMap(l)){
					this.map = map;
					map.setPlayers(map.getPlayers() +1);
					return;
				}
			}
		}
		this.map = null;
	}
	
	public static FoGPlayer getFoGPlayer(Player player){
		for(FoGPlayer fogplayer : fog.getFoGPlayers()){
			if(fogplayer.getPlayer() == player){
				return fogplayer;
			}
		}
		return null;
	}
	
	@Override
	public void loadPlayerData(){
		
		try{
			// TODO LOAD DATA
			loadRepairTokens();
			
			loadBank();
			loadSwords();
			loadShields();
			
			updateLevel();
		}catch(NullPointerException ex){
			ex.printStackTrace();
		}
	}
	
	private void loadRepairTokens(){
		this.repairTokens = new HashMap<Suit, Integer>();
		for(Suit suit : Suit.values()){
			if(suit == Suit.TRAINING_SUIT){
				this.repairTokens.put(suit, -1);
			}
			else{
				
			}
		}
	}
	
	private void loadBank(){
		this.bank = new Bank(15, new ItemStack[90]);
	}
	
	private void loadSwords(){
		this.swords = new HashMap<Suit, Swords>();
		for(Suit suit : getRepairTokens().keySet()){
			SwordLoreLevel[] sl = new SwordLoreLevel[9];
			sl[0] = new SwordLoreLevel(SwordLore.DAMAGE, 1);//TODO LOAD
			this.swords.put(suit, new Swords(suit, getFaction(), sl));
		}
	}
	
	private void loadShields(){
		this.shields = new HashMap<Suit, Shields>();
		for(Suit suit : getRepairTokens().keySet()){
			ShieldLoreLevel[][] sl = new ShieldLoreLevel[4][8];
			sl[2][0] = new ShieldLoreLevel(ShieldLore.SHIELD_REGEN, 1);//TODO LOAD
			this.shields.put(suit, new Shields(suit, getFaction(), sl));
		}
	}
}
