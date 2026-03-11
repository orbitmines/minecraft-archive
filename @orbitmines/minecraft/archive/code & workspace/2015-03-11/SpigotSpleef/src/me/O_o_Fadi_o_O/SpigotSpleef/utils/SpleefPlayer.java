package me.O_o_Fadi_o_O.SpigotSpleef.utils;

import java.util.ArrayList;
import java.util.List;

import me.O_o_Fadi_o_O.SpigotSpleef.managers.PlayerDataManager;
import me.O_o_Fadi_o_O.SpigotSpleef.managers.ScoreboardManager;
import me.O_o_Fadi_o_O.SpigotSpleef.managers.StorageManager;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;

public class SpleefPlayer {

	private Player player;
	private List<Kit> unlockedkits;
	private Scoreboard scoreboard;
	private int lastscoreboardid;
	private List<String> scoreboardrows;
	private Arena arena;
	private Kit kit;
	private int tokens;
	private int kills;
	private int wins;
	private int loses;
	private boolean spectator;
	private int animateddots;
	
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
		this.spectator = spectator;
		this.animateddots = 0;
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
	public String nextAnimatedDots(){
		List<String> messages = Message.getMessage(MessageName.ANIMATED_DOTS).getMessages();
		
		if(this.animateddots >= messages.size()){this.animateddots = 0;}
		this.animateddots = getAnimatedDots() +1;
		return messages.get((getAnimatedDots() -1));
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
			
			addLose();
			if(getKilledBy() != null){
				 getKilledBy().addKill();
			}
			
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
		getPlayer().updateInventory();
	}
	
	public void joinSpleef(Arena arena){
		setArena(arena);
		if(arena.getMinutes() == 0 && arena.getSeconds() <= 10){
			if(getKit() == null){
				setKit(getUnlockedKits().get(0));
			}
		}
		else{
			setKit(null);
		}
		getPlayer().teleport(arena.getLobby());
		arena.addPlayer(this);
		arena.sendJoinMessage(this);
		clearInventory();
	}
	
	public void quitSpleef(){
		clearInventory();
		if(isPlayer()){
			getArena().removePlayer(this);
			getArena().sendQuitMessage(this);
			getArena().checkGameFinish();
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
}
