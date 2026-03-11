package me.O_o_Fadi_o_O.SpigotSpleef.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.O_o_Fadi_o_O.SpigotSpleef.managers.PlayerDataManager;
import me.O_o_Fadi_o_O.SpigotSpleef.managers.ScoreboardManager;
import me.O_o_Fadi_o_O.SpigotSpleef.managers.StorageManager;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.scoreboard.Scoreboard;

public class SpleefPlayer {

	private Player player;
	private List<Kit> unlockedkits;
	private Map<KitItemStack, Long> cooldowns = new HashMap<KitItemStack, Long>();
	private Scoreboard scoreboard;
	private int lastscoreboardid;
	private List<String> scoreboardrows;
	private Arena arena;
	private Kit kit;
	private int tokens;
	private int kills;
	private int wins;
	private int loses;
	private int animateddots;
	private int animtedrightclick;
	private List<TNTPrimed> instanttnt;
	private boolean spectator;
	
	public SpleefPlayer(Player player, List<Kit> unlockedkits, Arena arena, Kit kit, int tokens, int kills, int wins, int loses, boolean spectator){
		this.player = player;
		this.unlockedkits = unlockedkits;
		this.scoreboard = null;
		this.scoreboardrows = new ArrayList<String>();
		this.arena = arena;
		this.kit = kit;
		this.tokens = tokens;
		this.kills = kills;
		this.wins = wins;
		this.loses = loses;
		this.animateddots = 0;
		this.animtedrightclick = 0;
		this.spectator = spectator;
	}
	
	public Player getPlayer(){
		return this.player;
	}
	
	public List<Kit> getUnlockedKits(){
		return this.unlockedkits;
	}
	public void addUnlockedKit(Kit kit){
		this.unlockedkits.add(kit);
	}
	
	public Map<KitItemStack, Long> getCooldowns(){
		return cooldowns;
	}
	public Long getCooldown(KitItemStack kititemstack){
		if(getCooldowns().containsKey(kititemstack)){
			return getCooldowns().get(kititemstack);
		}
		return null;
	}
	public void setCooldown(KitItemStack kititemstack, Long l){
		this.cooldowns.put(kititemstack, l);
	}
	public void setCooldowns(Map<KitItemStack, Long> cooldowns){
		this.cooldowns = cooldowns;
	}

	public Scoreboard getScoreboard(){
		return this.scoreboard;
	}
	public void setScoreboard(Scoreboard scoreboard){
		this.scoreboard = scoreboard;
	}
	public void setScoreboard(){
		if(this.scoreboard == null){
			Scoreboard scoreboard = ScoreboardManager.getNewScoreboard(this);
			this.scoreboard = scoreboard;
			this.player.setScoreboard(scoreboard);
		}
		else{
			Scoreboard scoreboard = ScoreboardManager.updateScoreboard(this);
			this.scoreboard = scoreboard;
		}
	}
	
	public int getScoreboardID(){
		return this.lastscoreboardid;
	}
	public void setScoreboardID(int lastscoreboardid){
		this.lastscoreboardid = lastscoreboardid;
	}

	public List<String> getScoreboardRows(){
		return this.scoreboardrows;
	}
	public String getRow(int index){
		try{
			String op = this.scoreboardrows.get(index);
			return op;
		}catch(IndexOutOfBoundsException ex){
			return null;
		}
	}
	public void setRows(List<String> scoreboardrows){
		this.scoreboardrows = scoreboardrows;
	}
	public void setRow(int index, String op){
		this.scoreboardrows.set(index, op);
	}
	public void removeRow(String op){
		this.scoreboardrows.remove(op);
	}
	public void addRow(String op){
		this.scoreboardrows.add(op);
	}
	
	public Arena getArena(){
		return this.arena;
	}
	public void setArena(Arena arena){
		this.arena = arena;
	}	
	
	public Kit getKit(){
		return this.kit;
	}
	public void setKit(Kit kit){
		this.kit = kit;
	}
	
	public int getTokens(){
		return this.tokens;
	}
	public void addTokens(int tokens){
		this.tokens = this.tokens +tokens;
		PlayerDataManager.setTokens(getPlayer().getUniqueId(), getTokens());
	}

	public void removeTokens(int tokens){
		this.tokens = this.tokens -tokens;
		PlayerDataManager.setTokens(getPlayer().getUniqueId(), getTokens());
	}
	
	public int getKills(){
		return this.kills;
	}
	public void addKill(){
		this.kills = this.kills +1;
		PlayerDataManager.setKills(getPlayer().getUniqueId(), getKills());
	}
	
	public int getWins(){
		return this.wins;
	}
	public void addWin(){
		this.wins = this.wins +1;
		PlayerDataManager.setWins(getPlayer().getUniqueId(), getWins());
	}
	
	public int getLoses(){
		return this.loses;
	}
	public void addLose(){
		this.loses = this.loses +1;
		PlayerDataManager.setLoses(getPlayer().getUniqueId(), getLoses());
	}

	public int getAnimatedDots(){
		return this.animateddots;
	}
	public String getAnimatedDotsString(){
		return Message.getMessage(MessageName.ANIMATED_DOTS).getMessages().get((getAnimatedDots() -1));
	}
	public String nextAnimatedDots(){
		List<String> messages = Message.getMessage(MessageName.ANIMATED_DOTS).getMessages();
		
		if(this.animateddots >= messages.size()){this.animateddots = 0;}
		this.animateddots = getAnimatedDots() +1;
		return messages.get((getAnimatedDots() -1));
	}
	
	public int getAnimatedRightClick(){
		return this.animtedrightclick;
	}
	public String nextAnimatedRightClick(){
		List<String> messages = Message.getMessage(MessageName.ANIMATED_RIGHT_CLICK).getMessages();
		
		if(this.animtedrightclick >= messages.size()){this.animtedrightclick = 0;}
		this.animtedrightclick = getAnimatedRightClick() +1;
		return messages.get((getAnimatedRightClick() -1));
	}
	
	public List<TNTPrimed> getInstantTNT(){
		return instanttnt;
	}
	public void addInstantTNT(TNTPrimed instanttnt){
		this.instanttnt.add(instanttnt);
	}
	public void removeInstantTNT(TNTPrimed instanttnt){
		this.instanttnt.remove(instanttnt);
	}
	public void setInstantTNT(List<TNTPrimed> instanttnt){
		this.instanttnt = instanttnt;
	}

	public void die(){
		if(isPlayer()){
			getArena().resetScoreboard();
			clearInventory();
			getArena().sendDeathMessage(this, getKilledBy());
			getPlayer().teleport(getArena().getMap().getSpectatorLocation());
			getArena().removePlayer(this);
			getArena().addDiedPlayer(this);
			getArena().addSpectator(this);
			this.spectator = true;
			
			addLose();
			if(getKilledBy() != null){
				 getKilledBy().addKill();
			}
			
			getArena().updateSpectators();
			getArena().checkGameFinish();
		}
	}
	
	public SpleefPlayer getKilledBy(){
		List<Location> blocklocations = getArena().getBlockLocations();
		int index = 0;
		int closestindex = -1;
		double closest = -1;
		
		if(blocklocations.size() > 0){
			for(Location l : blocklocations){
				double distance = l.distance(getPlayer().getLocation());
				if(!(distance >= 16)){
					if(closest == -1 || closest > distance){
						closestindex = index;
						closest = distance;
					}
				}
				index++;
			}
			
			if(closestindex != -1){
				return getArena().getPlayerBlocks().get(closestindex);
			}
			return null;
		}
		return null;
	}
	
	public boolean isInArena(){
		if(getArena() == null){
			return false;
		}
		return true;
	}
	
	public boolean isSpectator(){
		return this.spectator;
	}
	public boolean isPlayer(){
		return !this.spectator;
	}
	
	public void teleportToArena(){
		getPlayer().teleport(getArena().getLobby());
	}
	public void teleportToLobby(){
		getPlayer().teleport(StorageManager.minigameslobby);
	}
	
	public void clearInventory(){
		getPlayer().getInventory().setHelmet(null);
		getPlayer().getInventory().setChestplate(null);
		getPlayer().getInventory().setLeggings(null);
		getPlayer().getInventory().setBoots(null);
		getPlayer().getInventory().clear();
		getPlayer().setHealth(20D);
		getPlayer().setFoodLevel(20);
		getPlayer().updateInventory();
	}
	
	public void joinSpleef(Arena arena){
		getPlayer().setGameMode(GameMode.SURVIVAL);
		getPlayer().setAllowFlight(false);
		getPlayer().setFlying(false);
		
		setCooldowns(new HashMap<KitItemStack, Long>());
		setInstantTNT(new ArrayList<TNTPrimed>());
		setArena(arena);
		
		if(arena.isStatus(SpleefStatus.WAITING) || arena.isStatus(SpleefStatus.STARTING)){
			getPlayer().teleport(arena.getLobby());
			arena.addPlayer(this);
			
			if(arena.getMinutes() == 0 && arena.getSeconds() <= 10){
				if(getKit() == null){
					setKit(getUnlockedKits().get(0));
				}
			}
			else{
				setKit(null);
			}
			this.spectator = false;
			
			for(SpleefPlayer sp : getArena().getAllPlayers()){
				sp.getPlayer().showPlayer(getPlayer());
				getPlayer().showPlayer(sp.getPlayer());
			}
		}
		else{
			getPlayer().teleport(arena.getMap().getSpectatorLocation());
			arena.addSpectator(this);
			this.spectator = true;
			
			for(SpleefPlayer sp : getArena().getPlayers()){
				sp.getPlayer().hidePlayer(getPlayer());
				getPlayer().showPlayer(sp.getPlayer());
			}
			for(SpleefPlayer sp : getArena().getSpectators()){
				sp.getPlayer().showPlayer(getPlayer());
				getPlayer().showPlayer(sp.getPlayer());
			}
		}
		
		arena.sendJoinMessage(this);
		clearInventory();
		getArena().resetScoreboard();
	}
	
	public void quitSpleef(){
		setCooldowns(new HashMap<KitItemStack, Long>());
		clearInventory();
		getArena().resetScoreboard();
		this.spectator = false;
		getPlayer().setGameMode(GameMode.SURVIVAL);
		for(SpleefPlayer sp : getArena().getAllPlayers()){
			sp.getPlayer().showPlayer(getPlayer());
			getPlayer().showPlayer(sp.getPlayer());
		}
		if(isPlayer()){
			getArena().removePlayer(this);
			getArena().sendQuitMessage(this);
			if(getArena().isStatus(SpleefStatus.INGAME)){
				getArena().checkGameFinish();
			}
			if(getArena().isStatus(SpleefStatus.STARTING)){
				if(getArena().getPlayers().size() == 1){
					getArena().setStatus(SpleefStatus.WAITING);
					getArena().setMinutes(StorageManager.waittimeminutes);
					getArena().setSeconds(StorageManager.waittimeseconds);
				}
			}
			setArena(null);
			setKit(null);
			teleportToLobby();
		}
		else{
			getArena().removeSpectator(this);
			getArena().removeDiedPlayer(this);
			getArena().sendQuitMessage(this);
			setArena(null);
			setKit(null);
			teleportToLobby();
		}
	}
	
	public void updateKitInventory(boolean clear){
		if(clear == true){
			clearInventory();
		}
		getKit().give(this);
	}
	
	public SpleefPlayer getNearestPlayer(){
		List<SpleefPlayer> list = getArena().getPlayers();
		
		if(list.size() != 0){
			double distance = 100000;
			SpleefPlayer tracker = null;
			
			for(SpleefPlayer sps : list){
				if(sps != this){
					if(sps.getPlayer().getWorld().getName().equals(getPlayer().getWorld().getName())){
						double dis = sps.getPlayer().getLocation().distance(getPlayer().getLocation());
						if(dis <= distance){
							distance = dis;
							tracker = sps;
						}
					}
				}
			}
			
			return tracker;
		}
		return null;
	}
	
	public int getNearestPlayerDistance(){
		List<SpleefPlayer> list = getArena().getPlayers();
		
		if(list.size() != 0){
			double distance = 100000;
			
			for(SpleefPlayer sps : list){
				if(sps != this){
					if(sps.getPlayer().getWorld().getName().equals(getPlayer().getWorld().getName())){
						double dis = sps.getPlayer().getLocation().distance(getPlayer().getLocation());
						if(dis <= distance){
							distance = dis;
						}
					}
				}
			}
			
			return (int) distance;
		}
		return 0;
	}
}
