package om.fog.handlers.players;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import om.api.handlers.ActionBar;
import om.api.handlers.Database;
import om.api.handlers.Title;
import om.api.handlers.players.OMPlayer;
import om.api.utils.ConfigUtils;
import om.api.utils.enums.Server;
import om.fog.FoG;
import om.fog.handlers.Bank;
import om.fog.handlers.Quest;
import om.fog.handlers.Shields;
import om.fog.handlers.Swords;
import om.fog.handlers.Tools;
import om.fog.handlers.Tutorial;
import om.fog.handlers.map.FoGMap;
import om.fog.handlers.map.OreBlock;
import om.fog.handlers.map.SaveZone;
import om.fog.handlers.quests.OreMineQuest;
import om.fog.utils.enums.Faction;
import om.fog.utils.enums.Ore;
import om.fog.utils.enums.Repeat;
import om.fog.utils.enums.ShieldLore;
import om.fog.utils.enums.ShieldLore.ArmorType;
import om.fog.utils.enums.ShieldLore.ShieldLoreLevel;
import om.fog.utils.enums.Suit;
import om.fog.utils.enums.SwordLore;
import om.fog.utils.enums.SwordLore.SwordLoreLevel;
import om.fog.utils.enums.ToolLore;
import om.fog.utils.enums.ToolLore.ToolLoreLevel;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class FoGPlayer extends OMPlayer {
	
	private static FoG fog;
	private Faction faction;
	private Map<Suit, Integer> repairTokens;
	private int kills;
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
	private Map<Suit, Tools> tools;
	private boolean inCombat;
	private int inCombatTimer;
	private OreBlock blockMining;
	private double miningTimer;
	private boolean inTutorial;
	private Tutorial tutorial;
	private boolean expBar;
	private int expBarAdded;
	private BukkitTask expBarTask;
	private List<FoGMap> explored;
	private Map<Quest, Long> questCooldowns;
	private Map<Quest, Integer> questProgress;
	
	public FoGPlayer(Player player, boolean loaded) {
		super(player, loaded);
		
		fog = FoG.getInstance();
		updateMap();
		
		if(getFaction() == null){
			clearInventory();
			updateInventory();
			((CraftPlayer) getPlayer()).setMaxHealth(20.0D);
			getPlayer().setHealth(20.0D);
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
		addSilver(750);
		
		Player p = getPlayer();
		new Title("§b§lVote", "§7+750 Silver").send(p);
		
		p.sendMessage("");
		p.sendMessage("§b§lVote §8| §7Thank you, §b§l" + p.getName() + " §7for your §b§lVote§7!");
		p.sendMessage("§b§lVote §8| §7Your reward in the " + Server.FOG.getName() + "§7 Server:");
		p.sendMessage("§b§lVote §8| §7");

		for(String s : Server.FOG.getVoteRewardMessages()){
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
		String uuid = getUUID().toString();
		
		updateCurrentShield(uuid);
		updateSuit(uuid);
		updateTutorial(uuid);
		
		if(getFaction() != null){
			updateBank(uuid);
			updateExploredMaps(uuid);
			updateQuests(uuid);
			updateSwords(uuid);
			updateShields(uuid);
			updateTools(uuid);
		}
	}
	
	public Faction getFaction() {
		return faction;
	}
	
	public void setFaction(Faction faction) {
		this.faction = faction;

		if(Database.get().containsPath("FoG-Faction", "uuid", "uuid", getUUID().toString())){
			Database.get().update("FoG-Faction", "faction", "" + this.faction.toString(), "uuid", getUUID().toString());
		}
		else{
			Database.get().insert("FoG-Faction", "uuid`, `faction", getUUID().toString() + "', '" + this.faction.toString());
		}
	}
	
	public void joinFaction(Faction faction) {
		Bukkit.broadcastMessage(getName() + "§7 joined " + faction.getName() + "§7!");
		
		getPlayer().teleport(faction.getBaseLocation());
		
		updateMap();
		setFaction(faction);
		faction.setPlayerAmount(faction.getPlayerAmount() +1);
		faction.updatePopulationHologram();
		
		for(Suit suit : Suit.values()){
			getSwords().put(suit, new Swords(this, suit, getFaction(), new SwordLoreLevel[9]));
			getShields().put(suit, new Shields(this, suit, getFaction(), new ShieldLoreLevel[4][8]));
			getTools().put(suit, new Tools(this, suit, getFaction(), new ToolLoreLevel[9]));
		}
		((CraftPlayer) getPlayer()).setMaxHealth(10.0D);
		updateCraftingInventory();
		
		if(isInTutorial() && getTutorial().getStage() == 1){
			getTutorial().toNextStage();
		}
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
		
		if(isLevelUp()){
			setExp(getExp() - (int) getExpRequired());
			addLevel();
		}
		updateLevel();
		updateCraftingInventory();
		setExpBar(exp);
		updateEXPBar();
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
		this.suit = suit;
		
		if(suit != null){
			Swords swords = getSwords(suit);
			swords.checkItems(getPlayer(), swords.getSword(this, suit));
			
			for(ArmorType type : ArmorType.values()){
				Shields shields = getShields(suit);
				shields.checkItems(this, type, shields.getShield(this, type, suit));
			}
			
			this.shield = suit.getBaseHealth();
			updateShield();
			
			new BukkitRunnable(){
				public void run(){
					((CraftPlayer) getPlayer()).setMaxHealth(getSuit().getBaseHealth());
				}
			}.runTaskLater(FoG.getInstance(), 1);
		}
		else{
			this.currentShield = Suit.TRAINING_SUIT.getBaseHealth();
			this.shield = Suit.TRAINING_SUIT.getBaseHealth();
			((CraftPlayer) getPlayer()).setMaxHealth(Suit.TRAINING_SUIT.getBaseHealth());
			getPlayer().setHealth(Suit.TRAINING_SUIT.getBaseHealth());
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
	
	public Map<Suit, Tools> getTools() {
		return tools;
	}
	public Tools getTools(Suit suit) {
		return tools.get(suit);
	}
	
	public boolean isInCombat() {
		return inCombat;
	}
	public void setInCombat(boolean inCombat) {
		if(this.inCombat != inCombat){
			this.inCombat = inCombat;
			
			if(isInCombat()){
				getPlayer().sendMessage("§cYou have entered combat.");
			}
			else{
				getPlayer().sendMessage("§aYou have left combat.");
			}
		}
	}
	public void setInCombat(){
		setInCombat(true);
		setInCombatTimer(15);
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
			if(getSuit() != null && getCurrentShield() != getShield()){
				int levels = 0;
				
				for(ItemStack item : getPlayer().getInventory().getArmorContents()){
					int level = ShieldLore.SHIELD_REGEN.getLevel(item);
					
					if(level != -1){
						levels += level;
					}
				}
				
				double multiplier = 0.005 * levels;
				if(getMap() != null){
					for(SaveZone sz : getMap().getSaveZones()){
						if(sz.inSaveZone(getPlayer().getLocation())){
							multiplier *= 5;
							break;
						}
					}
				}
				
				if(levels != 0){
					regenShield(getShield() * multiplier);
				}
			}
		}
	}
	
	public boolean isMining() {
		return blockMining != null;
	}
	public OreBlock getBlockMining() {
		return blockMining;
	}
	public void setBlockMining(OreBlock blockMining) {
		this.blockMining = blockMining;
	}
	
	public double getMiningTimer() {
		return miningTimer;
	}
	public void setMiningTimer(double miningTimer) {
		this.miningTimer = miningTimer;
	}
	public void tickMiningTimer(){
		if(isMining()){
			OreBlock b = getBlockMining();
			
			if(b.isMining(this)){
				ItemStack item = getPlayer().getItemInHand();
				if(item != null && item.getType() == getSuit().getTool()){
					this.miningTimer--;
					
					if(getMiningTimer() <= 0){
						getBlockMining().dropItem(getPlayer());
						getBlockMining().spawnFirework();
						getBlockMining().despawn();
						setBlockMining(null);
						
						addExp(b.getOre().getExp());
						
						for(Quest quest : getCurrentQuestsAsNewList()){
							if(quest instanceof OreMineQuest){
								OreMineQuest oQuest = (OreMineQuest) quest;
								
								if(oQuest.getOre() == b.getOre()){
									oQuest.addProgress(this);
								}
							}
						}
					}
				}
				else{
					setBlockMining(null);
				}
			}
			else{
				setBlockMining(null);
			}
		}
	}
	
	public boolean isInTutorial() {
		return inTutorial;
	}
	public void setInTutorial(boolean inTutorial) {
		this.inTutorial = inTutorial;
		
		updateTutorial(getUUID().toString());
	}
	
	public Tutorial getTutorial() {
		return tutorial;
	}
	
	public boolean hasExpBar() {
		return expBar;
	}
	public void setExpBar(int expAdded) {
		if(this.expBarTask != null){
			this.expBarTask.cancel();
		}
		this.expBar = true;
		this.expBarAdded = expAdded;
		
		BukkitTask task = new BukkitRunnable() {
			@Override
			public void run() {
				removeExpBar();
			}
		}.runTaskLater(fog, 80);
		this.expBarTask = task;
	}
	public void removeExpBar(){
		this.expBar = false;
		this.expBarAdded = 0;
		this.expBarTask = null;
	}
	
	public List<FoGMap> getExploredMaps() {
		return explored;
	}
	
	public List<Quest> getCurrentQuestsAsNewList() {
		List<Quest> newList = new ArrayList<Quest>();
		newList.addAll(this.questProgress.keySet());
		return newList;
	}
	
	public Map<Quest, Long> getQuestCooldowns() {
		return questCooldowns;
	}
	public long getQuestCooldown(Quest quest){
		if(this.questCooldowns.containsKey(quest)){
			return this.questCooldowns.get(quest);
		}
		return -1;
	}
	public void resetQuestCooldown(Quest quest){
		this.questCooldowns.put(quest, System.currentTimeMillis());
		
		updateQuests(getUUID().toString());
	}
	public void removeQuestCooldown(Quest quest){
		this.questCooldowns.remove(quest);
		
		updateQuests(getUUID().toString());
	}
	public boolean onQuestCooldown(Quest quest){
		if(this.questCooldowns.containsKey(quest)){
			if(quest.getRepeat() != Repeat.NEVER && System.currentTimeMillis() - this.questCooldowns.get(quest) >= quest.getRepeat().getCooldown()){
				return false;
			}
			return true;
		}
		return false;
	}
	public void checkQuestCooldowns(){
	    List<Quest> quests = new ArrayList<Quest>();
		for(Quest quest : this.questCooldowns.keySet()){
			quests.add(quest);
		}
	    
		for(Quest quest : quests){
			if(quest.getRepeat() != Repeat.NEVER && System.currentTimeMillis() - this.questCooldowns.get(quest) >= quest.getRepeat().getCooldown()){
				removeQuestCooldown(quest);
			}
		}
	}
	public String getQuestCooldownDate(Quest quest){
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(getQuestCooldown(quest));
		switch(quest.getRepeat()){
			case DAILY:
				c.add(Calendar.DATE, 1);
				break;
			case MONTHLY:
				c.add(Calendar.MONTH, 1);
				break;
			case WEEKLY:
				c.add(Calendar.DATE, 7);
				break;
			default:
				break;
		}
		
		return new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date(c.getTimeInMillis()));
	}
	public String getQuestLastUseDate(Quest quest){
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(getQuestCooldown(quest));
		return new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date(c.getTimeInMillis()));
	}
	public long getQuestTimeInMillis(String date){
		try{
			Calendar c = Calendar.getInstance();
			c.setTime(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(date));
			
			return c.getTimeInMillis();
		}catch(ParseException e){
			return -1;
		}
	}
	
	public Map<Quest, Integer> getQuestProgress() {
		return questProgress;
	}
	public int getQuestProgress(Quest quest){
		if(this.questProgress.containsKey(quest)){
			return this.questProgress.get(quest);
		}
		return -1;
	}
	public void addQuestProgress(Quest quest){
		this.questProgress.put(quest, this.questProgress.get(quest) +1);
		
		if(quest.canCompleteQuest(this)){
			quest.completeQuest(this);
		}
		
		updateQuests(getUUID().toString());
	}
	public void startQuest(Quest quest){
		this.questProgress.put(quest, 0);
		
		updateQuests(getUUID().toString());
	}
	
	public void updateMining(){
		FoGMap map = getMap();
		Suit suit = getSuit();
		if(map != null && suit != null){
			ItemStack item = getPlayer().getItemInHand();
			if(item != null && item.getType() == suit.getTool()){
				OreBlock b = map.getOreBlock(this);
				
				if(b != null){
					setBlockMining(b);
					setMiningTimer(b.getOre().getPickupDelay(getSuit(), getPlayer().getItemInHand()));
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
	
	public void updateActionBar(){
		if(isMining()){
			updateMiningBar();
		}
		else{
			if(getFaction() != null){
				if(hasExpBar()){
					updateEXPBar();
				}
				else{
					updateShieldBar();
				}
			}
		}
	}
	
	public void updateMiningBar(){
		if(hasExpBar()){
			removeExpBar();
		}
		
		Ore ore = getBlockMining().getOre();
		
		double red = 50 - (getMiningTimer() / ore.getPickupDelay(getSuit(), getPlayer().getItemInHand())) * 50 + 2;
		String bar = "§a||||||||||||||||||||||||||||||||||||||||||||||||||";
		
		if(red < 52){
			bar = bar.substring(0, (int) red) + "§7" + bar.substring((int) red);
		}
		
		ActionBar actionbar = new ActionBar("§7Mining " + ore.getColor() + ore.getName() + " Ore§7... [" + bar + "§7]");
		actionbar.send(getPlayer());
	}
	
	public void updateEXPBar(){
		double red = (double) getExp() / getExpRequired() * 50 + 2;
		String bar = "§e||||||||||||||||||||||||||||||||||||||||||||||||||";
		
		if(red < 52){
			bar = bar.substring(0, (int) red) + "§7" + bar.substring((int) red);
		}
		
		ActionBar actionbar = new ActionBar("§eNext Level§7: " + bar + " §e§l+" + expBarAdded + " XP");
		actionbar.send(getPlayer());
	}
	
	public void updateShieldBar(){
		if(hasExpBar()){
			removeExpBar();
		}
		
		double red = (getCurrentShield() / getShield() * 100) + 2;
		String bar = "§b||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||";
		bar = bar.substring(0, (int) red) + "§c" + bar.substring((int) red);
		
		ActionBar actionbar = new ActionBar(bar);
		actionbar.send(getPlayer());
	}
	
	public void updateMap(){
		Location l = getPlayer().getLocation();
		if(l.getWorld().getName().equals(fog.getGalaxyWorld().getName())){
			for(FoGMap map : fog.getMaps()){
				if(map.inMap(l)){
					if(!this.explored.contains(map)){
						this.explored.add(map);
					}
					this.map = map;
					map.setPlayers(map.getPlayers() +1);
					
					for(SaveZone sz : map.getSaveZones()){
						if(sz.inSaveZone(l)){
							sz.getPlayers().add(this);
							break;
						}
					}
					return;
				}
			}
		}
		this.map = null;
	}
	
	public void updateCraftingInventory(){
		Inventory inv = getPlayer().getOpenInventory().getTopInventory();
		{
			String c = getFaction().getColor();
			ItemStack item = new ItemStack(Material.EXP_BOTTLE);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName("§eLevel: " + c + getLevels());
			List<String> lore = new ArrayList<String>();
			lore.add("§eXP: " + c + getExp() + "§e/" + c + (int) getExpRequired());
			meta.setLore(lore);
			item.setItemMeta(meta);
			
			inv.setItem(1, item);
		}
		{
			ItemStack item = new ItemStack(Material.EYE_OF_ENDER);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName("§3Galaxy Teleportation");
			item.setItemMeta(meta);
			
			inv.setItem(2, item);
		}
		{
			ItemStack item = new ItemStack(Material.WORKBENCH);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName("§7Ore Crafting");
			item.setItemMeta(meta);
			
			inv.setItem(3, item);
		}
		{
			ItemStack item = new ItemStack(Material.MAP);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName("§fQuests");
			item.setItemMeta(meta);
			
			inv.setItem(4, item);
		}
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
		this.expBar = false;
		
		try{
			String uuid = getUUID().toString();
			// TODO LOAD DATA
			loadFaction(uuid);
			loadTutorial(uuid);
			loadKills(uuid);
			loadDeaths(uuid);
			loadLevels(uuid);
			loadExp(uuid);
			loadSilver(uuid);
			loadRepairTokens(uuid);
			loadExploredMaps(uuid);
			loadBank(uuid);
			loadQuests(uuid);
			
			loadBank(uuid);
			loadSwords(uuid);
			loadShields(uuid);
			loadTools(uuid);
			loadSuit(uuid);
			
			updateLevel();
		}catch(NullPointerException ex){
			ex.printStackTrace();
		}
	}
	
	private void loadTutorial(String uuid){
		if(Database.get().containsPath("FoG-Tutorial", "uuid", "uuid", uuid)){
			this.inTutorial = true;
			this.tutorial = new Tutorial(this, Database.get().getInt("FoG-Tutorial", "tutorial", "uuid", uuid));
		}
		else{
			if(getFaction() != null){
				this.inTutorial = false;
			}
			else{
				this.inTutorial = true;
				this.tutorial = new Tutorial(this, 1);
			}
		}
	}
	
	private void loadRepairTokens(String uuid){
		this.repairTokens = new HashMap<Suit, Integer>();
		for(Suit suit : Suit.values()){
			if(suit == Suit.TRAINING_SUIT){
				this.repairTokens.put(suit, -1);
			}
			else{
				
			}
		}
	}
	
	private void loadSuit(String uuid){
		if(Database.get().containsPath("FoG-Suit", "uuid", "uuid", uuid)){
			Suit suit = Suit.valueOf(Database.get().getString("FoG-Suit", "suit", "uuid", uuid));
			this.shield = suit.getBaseHealth();
			setSuit(suit);
			this.currentShield = Database.get().getInt("FoG-CurrentShield", "currentshield", "uuid", uuid);
		}
		else{
			setSuit(null);
		}
	}
	
	private void loadFaction(String uuid){
		this.swords = new HashMap<Suit, Swords>();
		this.shields = new HashMap<Suit, Shields>();
		this.tools = new HashMap<Suit, Tools>();
		
		if(Database.get().containsPath("FoG-Faction", "uuid", "uuid", uuid)){
			this.faction = Faction.valueOf(Database.get().getString("FoG-Faction", "faction", "uuid", uuid));
		
			for(Suit suit : Suit.values()){
				getSwords().put(suit, new Swords(this, suit, getFaction(), new SwordLoreLevel[9]));
				getShields().put(suit, new Shields(this, suit, getFaction(), new ShieldLoreLevel[4][8]));
				getTools().put(suit, new Tools(this, suit, getFaction(), new ToolLoreLevel[9]));
			}
		}
	}
	
	private void loadKills(String uuid){
		if(Database.get().containsPath("FoG-Kills", "uuid", "uuid", uuid)){
			this.kills = Database.get().getInt("FoG-Kills", "kills", "uuid", uuid);
		}
		else{
			Database.get().insert("FoG-Kills", "uuid`, `kills", uuid + "', '" + 0);
			this.kills = 0;
		}
	}
	
	private void loadDeaths(String uuid){
		if(Database.get().containsPath("FoG-Deaths", "uuid", "uuid", uuid)){
			this.deaths = Database.get().getInt("FoG-Deaths", "deaths", "uuid", uuid);
		}
		else{
			Database.get().insert("FoG-Deaths", "uuid`, `deaths", uuid + "', '" + 0);
			this.deaths = 0;
		}
	}
	
	private void loadLevels(String uuid){
		if(Database.get().containsPath("FoG-Levels", "uuid", "uuid", uuid)){
			this.levels = Database.get().getInt("FoG-Levels", "levels", "uuid", uuid);
		}
		else{
			Database.get().insert("FoG-Levels", "uuid`, `levels", uuid + "', '" + 0);
			this.levels = 0;
		}
	}
	
	private void loadExp(String uuid){
		if(Database.get().containsPath("FoG-Exp", "uuid", "uuid", uuid)){
			this.exp = Database.get().getInt("FoG-Exp", "exp", "uuid", uuid);
		}
		else{
			Database.get().insert("FoG-Exp", "uuid`, `exp", uuid + "', '" + 0);
			this.exp = 0;
		}
	}
	
	private void loadSilver(String uuid){
		if(Database.get().containsPath("FoG-Silver", "uuid", "uuid", uuid)){
			this.silver = Database.get().getInt("FoG-Silver", "silver", "uuid", uuid);
		}
		else{
			Database.get().insert("FoG-Silver", "uuid`, `silver", uuid + "', '" + 0);
			this.silver = 0;
		}
	}
	
	private void loadExploredMaps(String uuid){
		this.explored = new ArrayList<FoGMap>();
		
		if(Database.get().containsPath("FoG-ExploredMaps", "uuid", "uuid", uuid)){
			String maps = Database.get().getString("FoG-ExploredMaps", "exploredmaps", "uuid", uuid);
			
			for(String s : maps.split("\\|")){
				FoGMap map = FoGMap.getMap(s);
				
				if(map != null){
					this.explored.add(map);
				}
			}
		}
	}
	
	private void loadBank(String uuid){
		this.bank = new Bank(this, 15, new ItemStack[90]);
		
		if(Database.get().containsPath("FoG-Bank", "uuid", "uuid", uuid)){
			String bank = Database.get().getString("FoG-Bank", "bank", "uuid", uuid);
			
			int index = 0;
			for(String s : bank.split("\\#")){
				if(!s.equals("null")){
					this.bank.getItems()[index] = ConfigUtils.parseItemStack(s);
				}
				index++;
			}
		}
	}
	
	private void loadSwords(String uuid){
		if(Database.get().containsPath("FoG-Swords", "uuid", "uuid", uuid)){
			String swords = Database.get().getString("FoG-Swords", "swords", "uuid", uuid);
			
			for(String s : swords.split("\\|")){
				String sParts[] = s.split(";");
				Suit suit = Suit.valueOf(sParts[0]);
				SwordLoreLevel[] sl = new SwordLoreLevel[9];
				
				int index = 0;
				for(String s2 : sParts){
					if(index != 0){
						if(!s2.equals("null")){
							String[] ench = s2.split("\\:");
							sl[index -1] = new SwordLoreLevel(SwordLore.valueOf(ench[0]), Integer.parseInt(ench[1]));
						}
					}
					index++;
				}
				
				this.swords.put(suit, new Swords(this, suit, getFaction(), sl));
			}
		}
	}
	
	private void loadShields(String uuid){
		if(Database.get().containsPath("FoG-Shields", "uuid", "uuid", uuid)){
			String swords = Database.get().getString("FoG-Shields", "shields", "uuid", uuid);
			//SUIT;1#BLOCK:1#DAMAGE:1;2#REPAIR:1#null#null#null#null#null|SUIT...
			for(String s : swords.split("\\|")){
				String sParts[] = s.split(";");
				Suit suit = Suit.valueOf(sParts[0]);
				ShieldLoreLevel[][] sl = new ShieldLoreLevel[4][8];

				int i = 0;
				for(String s2 : sParts){
					if(i != 0){
						String[] sA = s2.split("\\#");
						int iA = Integer.parseInt(sA[0]);
	
						int index = 0;
						for(String s3 : sA){
							if(index != 0){
								if(!s3.equals("null")){
									String[] ench = s3.split("\\:");
									sl[iA][index -1] = new ShieldLoreLevel(ShieldLore.valueOf(ench[0]), Integer.parseInt(ench[1]));
								}
							}
							index++;
						}
					}
					i++;
				}

				this.shields.put(suit, new Shields(this, suit, getFaction(), sl));
			}
		}
	}
	
	private void loadTools(String uuid){
		if(Database.get().containsPath("FoG-Tools", "uuid", "uuid", uuid)){
			String tools = Database.get().getString("FoG-Tools", "tools", "uuid", uuid);
			
			for(String s : tools.split("\\|")){
				String sParts[] = s.split(";");
				Suit suit = Suit.valueOf(sParts[0]);
				ToolLoreLevel[] sl = new ToolLoreLevel[9];
				
				int index = 0;
				for(String s2 : sParts){
					if(index != 0){
						if(!s2.equals("null")){
							String[] ench = s2.split("\\:");
							sl[index -1] = new ToolLoreLevel(ToolLore.valueOf(ench[0]), Integer.parseInt(ench[1]));
						}
					}
					index++;
				}
				
				this.tools.put(suit, new Tools(this, suit, getFaction(), sl));
			}
		}
	}
	
	private void loadQuests(String uuid){
		this.questCooldowns = new HashMap<Quest, Long>();
		this.questProgress = new HashMap<Quest, Integer>();
		
		if(Database.get().containsPath("FoG-Quests", "uuid", "uuid", uuid)){
			String quests = Database.get().getString("FoG-Quests", "quests", "uuid", uuid);
			
			for(String s : quests.split("\\|")){
				if(!s.equals("")){
					String[] sParts = s.split("\\;");
					this.questCooldowns.put(Quest.getById(Integer.parseInt(sParts[0])), getQuestTimeInMillis(sParts[1]));
				}
			}
		}
		if(Database.get().containsPath("FoG-CurrentQuests", "uuid", "uuid", uuid)){
			String quests = Database.get().getString("FoG-CurrentQuests", "currentquests", "uuid", uuid);
			
			for(String s : quests.split("\\|")){
				if(!s.equals("")){
					String[] sParts = s.split("\\;");
					Quest quest = Quest.getById(Integer.parseInt(sParts[0]));
					this.questProgress.put(quest, Integer.parseInt(sParts[1]));
				}
			}
		}
	}
	
	public void updateCurrentShield(String uuid){
		if(Database.get().containsPath("FoG-CurrentShield", "uuid", "uuid", uuid)){
			Database.get().update("FoG-CurrentShield", "currentshield", "" + (int) getCurrentShield(), "uuid", uuid);
		}
		else{
			Database.get().insert("FoG-CurrentShield", "uuid`, `currentshield", uuid + "', '" + (int) getCurrentShield());
		}
	}
	
	public void updateSuit(String uuid){
		if(Database.get().containsPath("FoG-Suit", "uuid", "uuid", uuid)){
			if(getSuit() != null){
				Database.get().update("FoG-Suit", "suit", getSuit().toString(), "uuid", uuid);
			}
			else{
				Database.get().delete("FoG-Suit", "uuid", uuid);
			}
		}
		else{
			if(getSuit() != null){
				Database.get().insert("FoG-Suit", "uuid`, `suit", uuid + "', '" + getSuit().toString());
			}
		}
	}
	
	public void updateTutorial(String uuid){
		if(Database.get().containsPath("FoG-Tutorial", "uuid", "uuid", uuid)){
			if(isInTutorial()){
				Database.get().update("FoG-Tutorial", "tutorial", "" + getTutorial().getStage(), "uuid", uuid);
			}
			else{
				Database.get().delete("FoG-Tutorial", "uuid", uuid);
			}
		}
		else{
			if(isInTutorial()){
				Database.get().insert("FoG-Tutorial", "uuid`, `tutorial", uuid + "', '" + getTutorial().getStage());
			}
		}
	}
	
	public void updateExploredMaps(String uuid){
		String maps = "";
		for(FoGMap map : getExploredMaps()){
			if(!maps.equals("")){
				maps += "|";
			}
			maps += map.getName();
		}
		
		if(Database.get().containsPath("FoG-ExploredMaps", "uuid", "uuid", uuid)){
			Database.get().update("FoG-ExploredMaps", "exploredmaps", "" + maps, "uuid", uuid);
		}
		else{
			Database.get().insert("FoG-ExploredMaps", "uuid`, `exploredmaps", uuid + "', '" + maps);
		}
	}
	
	public void updateQuests(String uuid){
		{
			String quests = "";
			for(Quest quest : getQuestCooldowns().keySet()){
				if(!quests.equals("")){
					quests += "|";
				}
				quests += quest.getId() + ";" + getQuestLastUseDate(quest);
			}
			
			if(Database.get().containsPath("FoG-Quests", "uuid", "uuid", uuid)){
				Database.get().update("FoG-Quests", "quests", "" + quests, "uuid", uuid);
			}
			else{
				Database.get().insert("FoG-Quests", "uuid`, `quests", uuid + "', '" + quests);
			}
		}
		{
			String quests = "";
			for(Quest quest : getQuestProgress().keySet()){
				if(!quests.equals("")){
					quests += "|";
				}
				quests += quest.getId() + ";" + getQuestProgress(quest);
			}
			
			if(Database.get().containsPath("FoG-CurrentQuests", "uuid", "uuid", uuid)){
				Database.get().update("FoG-CurrentQuests", "currentquests", "" + quests, "uuid", uuid);
			}
			else{
				Database.get().insert("FoG-CurrentQuests", "uuid`, `currentquests", uuid + "', '" + quests);
			}
		}
	}
	
	public void updateBank(String uuid){
		String bank = "";
		for(ItemStack item : getBank().getItems()){
			if(item != null){
				if(!bank.equals("")){
					bank += "#";
				}
				bank += ConfigUtils.parseString(item);
			}
			else{
				if(!bank.equals("")){
					bank += "#";
				}
				bank += "null";
			}
		}
		
		if(Database.get().containsPath("FoG-Bank", "uuid", "uuid", uuid)){
			Database.get().update("FoG-Bank", "bank", "" + bank, "uuid", uuid);
		}
		else{
			Database.get().insert("FoG-Bank", "uuid`, `bank", uuid + "', '" + bank);
		}
	}
	
	public void updateSwords(String uuid){
		String swords = "";
		for(Suit suit : Suit.values()){
			SwordLoreLevel[] slList = getSwords(suit).getSwordItems();
			String suitSword = suit.toString();
			
			for(SwordLoreLevel sl : slList){
				if(sl != null){
					suitSword += ";" + sl.getLore().toString() + ":" + sl.getLevel();
				}
				else{
					suitSword += ";" + null;
				}
			}
			
			if(!swords.equals("")){
				swords += "|";
			}
			swords += suitSword;
		}
		
		if(Database.get().containsPath("FoG-Swords", "uuid", "uuid", uuid)){
			Database.get().update("FoG-Swords", "swords", "" + swords, "uuid", uuid);
		}
		else{
			Database.get().insert("FoG-Swords", "uuid`, `swords", uuid + "', '" + swords);
		}
	}
	
	public void updateShields(String uuid){
		String shields = "";
		for(Suit suit : Suit.values()){
			ShieldLoreLevel[][] slList = getShields(suit).getShieldItems();
			String suitShield = suit.toString();
			
			for(int iA = 0; iA < 4; iA++){
				suitShield += ";" + iA;
				
				for(ShieldLoreLevel sl : slList[iA]){
					if(sl != null){
						suitShield += "#" + sl.getLore().toString() + ":" + sl.getLevel();
					}
					else{
						suitShield += "#" + null;
					}
				}
			}
			
			if(!shields.equals("")){
				shields += "|";
			}
			shields += suitShield;
		}
		
		if(Database.get().containsPath("FoG-Shields", "uuid", "uuid", uuid)){
			Database.get().update("FoG-Shields", "shields", "" + shields, "uuid", uuid);
		}
		else{
			Database.get().insert("FoG-Shields", "uuid`, `shields", uuid + "', '" + shields);
		}
	}
	
	public void updateTools(String uuid){
		String tools = "";
		for(Suit suit : Suit.values()){
			ToolLoreLevel[] slList = getTools(suit).getToolItems();
			String suitTool = suit.toString();
			
			for(ToolLoreLevel sl : slList){
				if(sl != null){
					suitTool += ";" + sl.getLore().toString() + ":" + sl.getLevel();
				}
				else{
					suitTool += ";" + null;
				}
			}
			
			if(!tools.equals("")){
				tools += "|";
			}
			tools += suitTool;
		}
		
		if(Database.get().containsPath("FoG-Tools", "uuid", "uuid", uuid)){
			Database.get().update("FoG-Tools", "tools", "" + tools, "uuid", uuid);
		}
		else{
			Database.get().insert("FoG-Tools", "uuid`, `tools", uuid + "', '" + tools);
		}
	}
}
