package me.O_o_Fadi_o_O.SpigotSpleef.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import me.O_o_Fadi_o_O.SpigotSpleef.Start;
import me.O_o_Fadi_o_O.SpigotSpleef.managers.PlayerDataManager;
import me.O_o_Fadi_o_O.SpigotSpleef.managers.StorageManager;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;

public class Arena {

	Start start = Start.getInstance();
	
	private int arenaid;
	private Location lobbyspawn;
	private int minplayers;
	private int maxplayers;
	private Map map;
	private List<SpleefPlayer> players;
	private List<SpleefPlayer> diedplayers;
	private List<SpleefPlayer> spectators;
	private SpleefStatus status;
	private int minutes;
	private int seconds;
	
	private SpleefPlayer first;
	private SpleefPlayer second;
	private SpleefPlayer third;

	private List<ArenaSign> arenasigns = new ArrayList<ArenaSign>();
	
	private List<SpleefPlayer> playerblocks = new ArrayList<SpleefPlayer>();
	private List<Location> blocklocations = new ArrayList<Location>();
	private List<OldBlock> oldblocks = new ArrayList<OldBlock>();
	
	public Arena(int arenaid, Location lobbyspawn, int minplayers, int maxplayers, Map map, List<SpleefPlayer> players, List<SpleefPlayer> spectators, SpleefStatus status, int minutes, int seconds){
		this.arenaid = arenaid;
		this.lobbyspawn = lobbyspawn;
		this.minplayers = minplayers;
		this.maxplayers = maxplayers;
		this.map = map;
		this.players = players;
		this.diedplayers = new ArrayList<SpleefPlayer>();
		this.spectators = spectators;
		this.status = status;
		this.minutes = minutes;
		this.seconds = seconds;
		
		if(lobbyspawn != null){
			selectRandomMap();
		}
		
		new BukkitRunnable(){
			public void run(){
				
				if(isStatus(SpleefStatus.WAITING)){
					if(enoughPlayers()){
						timeCheck();
						
						if(StorageManager.startingmessageat.contains(getMinutes() + ":" + getSeconds())){
							sendStartingMessage();
						}
						
						if(getMinutes() == 0 && getSeconds() == 11){
							toStarting();
						}
					}
					else{
						setMinutes(StorageManager.waittimeminutes);
						setSeconds(StorageManager.waittimeseconds);
					}
				}
				else if(isStatus(SpleefStatus.STARTING)){
					timeCheck();

					if(StorageManager.startingmessageat.contains(getMinutes() + ":" + getSeconds())){
						sendStartingMessage();
					}
					
					if(getMinutes() == 0 && getSeconds() == 0){
						toWarmup();
					}
				}
				else if(isStatus(SpleefStatus.WARMUP)){
					timeCheck();
					
					if(getMinutes() == 0 && getSeconds() == 0){
						toInGame();
					}
				}
				else if(isStatus(SpleefStatus.INGAME)){
					timeCheck();
					
					if(StorageManager.endingmessageat.contains(getMinutes() + ":" + getSeconds())){
						sendEndingMessage();
					}

					if(getMinutes() == 0 && getSeconds() == 0){
						toEnding();
					}
				}
				else if(isStatus(SpleefStatus.ENDING)){
					timeCheck();

					if(getMinutes() == 0 && getSeconds() == 0){
						toRestarting();
					}
				}
				else if(isStatus(SpleefStatus.RESTARTING)){
					if(getBlockLocations().size() != 0){
						resetMapPart();
					}
					else{
						toWaiting();
					}
				}
				else{}
				
				updateSigns();
				setScoreboard();
			}
		}.runTaskTimer(this.start, 0, 20);
	}
	
	public int getArenaID(){
		return this.arenaid;
	}
	
	public Location getLobby(){
		return this.lobbyspawn;
	}
	public void setLobby(Location lobbyspawn){
		this.lobbyspawn = lobbyspawn;
	}
	
	public int getMinPlayers(){
		return this.minplayers;
	}
	public void setMinPlayers(int minplayers){
		this.minplayers = minplayers;
	}
	
	public int getMaxPlayers(){
		return this.maxplayers;
	}
	public void setMaxPlayers(int maxplayers){
		this.maxplayers = maxplayers;
	}
	
	public Map getMap(){
		return this.map;
	}
	public void setMap(Map map){
		this.map = map;
		this.map.setInUse(true);
	}
	
	public List<SpleefPlayer> getPlayers(){
		return players;
	}
	public void setPlayers(List<SpleefPlayer> players){
		this.players = players;
	}	
	public void addPlayer(SpleefPlayer p){
		this.players.add(p);
	}	
	public void removePlayer(SpleefPlayer p){
		this.players.remove(p);
	}
	
	public List<SpleefPlayer> getDiedPlayers(){
		return diedplayers;
	}
	public void setDiedPlayers(List<SpleefPlayer> diedplayers){
		this.diedplayers = diedplayers;
	}	
	public void addDiedPlayer(SpleefPlayer p){
		this.diedplayers.add(p);
	}	
	public void removeDiedPlayer(SpleefPlayer p){
		this.diedplayers.remove(p);
	}
	
	public List<SpleefPlayer> getSpectators(){
		return spectators;
	}
	public void setSpectators(List<SpleefPlayer> spectators){
		this.spectators = spectators;
	}	
	public void addSpectator(SpleefPlayer p){
		this.spectators.add(p);
	}	
	public void removeSpectator(SpleefPlayer p){
		this.spectators.remove(p);
	}
	
	public SpleefStatus getStatus(){
		return status;
	}
	public boolean isStatus(SpleefStatus status){
		return this.status == status;
	}
	public void setStatus(SpleefStatus status){
		this.status = status;
	}
	
	public int getMinutes(){
		return this.minutes;
	}
	public void subtractMinute(){
		this.minutes = this.minutes -1;
	}
	public void setMinutes(int minutes){
		this.minutes = minutes;
	}
	
	public int getSeconds(){
		return this.seconds;
	}
	public void subtractSecond(){
		this.seconds = this.seconds -1;
	}
	public void setSeconds(int seconds){
		this.seconds = seconds;
	}
	
	public List<ArenaSign> getArenaSigns(){
		return this.arenasigns;
	}
	public void addArenaSign(ArenaSign arenasign){
		this.arenasigns.add(arenasign);
	}
	public void removeArenaSign(ArenaSign arenasign){
		this.arenasigns.remove(arenasign);
	}
	public void setArenaSigns(List<ArenaSign> arenasigns){
		this.arenasigns = arenasigns;
	}

	public List<SpleefPlayer> getPlayerBlocks(){
		return this.playerblocks;
	}
	public void addPlayerBlocks(SpleefPlayer sp){
		this.playerblocks.add(sp);
	}
	public void setPlayerBlocks(List<SpleefPlayer> playerblocks){
		this.playerblocks = playerblocks;
	}

	public List<Location> getBlockLocations(){
		return this.blocklocations;
	}
	public void addBlockLocation(Location location){
		this.blocklocations.add(location);
	}
	public void removeBlockLocation(Location location){
		this.blocklocations.remove(location);
	}
	public void setBlockLocations(List<Location> blocklocations){
		this.blocklocations = blocklocations;
	}
	
	public List<OldBlock> getOldBlocks(){
		return this.oldblocks;
	}
	public void addOldBlock(Location location, Material material, byte durability){
		this.oldblocks.add(new OldBlock(this, location, material, durability));
	}
	public void removeOldBlock(OldBlock oldblock){
		this.oldblocks.remove(oldblock);
	}
	public void setOldBlocks(List<OldBlock> oldblocks){
		this.oldblocks = oldblocks;
	}

	public void setScoreboard(){
		for(SpleefPlayer sp : this.getPlayers()){
			sp.setScoreboard();
		}
	}
	
	public List<SpleefPlayer> getAllPlayers(){
		List<SpleefPlayer> players = new ArrayList<SpleefPlayer>();
		for(SpleefPlayer sps : getPlayers()){
			players.add(sps);
		}
		for(SpleefPlayer sps : getSpectators()){
			players.add(sps);
		}
		return players;
	}
	
	public void timeCheck(){
		if(getSeconds() != -1){
			setSeconds(getSeconds() -1);
		}
		if(getSeconds() == -1){
			setMinutes(getMinutes() -1);
			setSeconds(59);
		}
	}
	
	public boolean enoughPlayers(){
		if(getPlayers().size() >= getMinPlayers()){
			return true;
		}
		return false;
	}
	public boolean isFull(){
		if(getPlayers().size() >= getMaxPlayers()){
			return true;
		}
		return false;
	}
	
	public void checkGameFinish(){
		if(getPlayers().size() == 1){
			toEnding();
		}
	}
	
	public void toWaiting(){
		selectRandomMap();
		setStatus(SpleefStatus.WAITING);
		setMinutes(StorageManager.waittimeminutes);
		setSeconds(StorageManager.waittimeseconds);
		setPlayers(new ArrayList<SpleefPlayer>());
		setDiedPlayers(new ArrayList<SpleefPlayer>());
		setSpectators(new ArrayList<SpleefPlayer>());
		setPlayerBlocks(new ArrayList<SpleefPlayer>());
		setBlockLocations(new ArrayList<Location>());
		setOldBlocks(new ArrayList<OldBlock>());
	}
	
	public void toStarting(){
		setStatus(SpleefStatus.STARTING);
		for(SpleefPlayer sp : getPlayers()){
			if(sp.getKit() == null){
				sp.setKit(sp.getUnlockedKits().get(0));
			}
		}
	}

	public void toWarmup(){
		setStatus(SpleefStatus.WARMUP);
		setMinutes(0);
		setSeconds(StorageManager.warmuptimeseconds);
		getMap().teleport(this.players);
		sendStartedMessage();
		resetScoreboard();
	}

	public void toInGame(){
		setStatus(SpleefStatus.INGAME);
		setMinutes(StorageManager.maxgametimeminutes);
		setSeconds(StorageManager.maxgametimeseconds);
		for(SpleefPlayer sp : getPlayers()){
			sp.getKit().give(sp.getPlayer());
		}
		sendStartedGameMessage();
	}

	public void toEnding(){
		setStatus(SpleefStatus.ENDING);
		setMinutes(0);
		setSeconds(StorageManager.endingtimeseconds);
		
		SpleefPlayer sp = getPlayers().get(0);
		first = sp;
		sp.addWin();
		PlayerDataManager.setWins(sp.getPlayer().getUniqueId(), sp.getWins());
		
		if(getPlayers().size() > 1){
			second = getPlayers().get(1);
		}
		else if(getDiedPlayers().size() > 0){
			second = getDiedPlayers().get(getDiedPlayers().size() -1);
		}
		else{}
		
		if(getPlayers().size() > 2){
			third = getPlayers().get(2);
		}
		else if(getDiedPlayers().size() > 1){
			third = getDiedPlayers().get(getDiedPlayers().size() -2);
		}
		else{}
		
		sendEndedMessage();
	}

	public void toRestarting(){
		setStatus(SpleefStatus.RESTARTING);
		for(SpleefPlayer sp : getAllPlayers()){
			sp.quitSpleef();
		}
	}
	
	public void resetScoreboard(){
		for(SpleefPlayer sp : getAllPlayers()){
			sp.setScoreboard(null);
			sp.getPlayer().getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
		}
	}
	
	public void resetMapPart(){
		for(int i = 0; i <= 15; i++){
			if(getBlockLocations().get(i) != null){
				Location location = getBlockLocations().get(i);
				restoreBlock(location);
				removeBlockLocation(location);
			}
			else{
				i = 15;
			}
		}
	}
	@SuppressWarnings("deprecation")
	public void restoreBlock(Location location){
		for(OldBlock oldblock : getOldBlocks()){
			if(oldblock.getLocation() == location){
				Block b = location.getBlock();
				b.setType(oldblock.getMaterial());
				b.setData(oldblock.getDurability());
				removeOldBlock(oldblock);
			}
		}
	}
	
	public void selectRandomMap(){
		List<Map> maps = StorageManager.maps;
		List<Map> notinuse = new ArrayList<Map>();
		for(Map map : maps){
			if(!map.isInUse()){
				notinuse.add(map);
			}
		}
		setMap(notinuse.get(new Random().nextInt(notinuse.size())));
	}
	
	public void updateSigns(){
		for(ArenaSign sign : StorageManager.signs){
			if(sign.isArena(this)){
				sign.update();
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	public void breakBlock(SpleefPlayer sp, Block b){
		if(!containsBlockLocation(b.getLocation())){
			addBlockLocation(b.getLocation());
			addPlayerBlocks(sp);
			addOldBlock(b.getLocation(), b.getType(), b.getData());
			
			for(SpleefPlayer sps : getPlayers()){
				sps.getPlayer().playEffect(b.getLocation().add(0.5, 0, 0.5), Effect.STEP_SOUND, b.getTypeId());
			}
			
			String block = b.getTypeId() + ":" + b.getData();
			if(StorageManager.blocktoblock.containsKey(block)){
				String to = StorageManager.blocktoblock.get(block);
				String[] toparts = to.split("\\:");
				Material material = Material.getMaterial(Integer.parseInt(toparts[0]));
				byte durability = (byte) Integer.parseInt(toparts[1]);
				
				b.setType(material);
				b.setData(durability);
			}
			else{
				b.setType(Material.AIR);
			}
		}
	}
	public boolean containsBlockLocation(Location location){
		return getBlockLocations().contains(location);
	}

	public void sendStartedGameMessage(){
		Message message = Message.getMessage(MessageName.GAME_STARTED);
		message.updateForArena(this, null, null, null);
		message.send(getPlayers());
		message.send(getSpectators());
	}
	public void sendDeathMessage(SpleefPlayer died, SpleefPlayer killer){
		List<SpleefPlayer> tosps = getAllPlayers();
		tosps.remove(died);
		if(killer != null){
			tosps.remove(killer);
		}

		if(killer == null){
			Message message = Message.getMessage(MessageName.DEATHMESSAGE_UNKNOWN_KILLER);
			message.updateForArena(this, killer, died, null);
			message.send(tosps);
		}
		else{
			Message message = Message.getMessage(MessageName.DEATHMESSAGE);
			message.updateForArena(this, killer, died, null);
			message.send(tosps);
		}
		
		if(killer != null){
			Message message1 = Message.getMessage(MessageName.DEATHMESSAGE_TO_PLAYER);
			message1.updateForArena(this, killer, died, null);
			message1.send(Arrays.asList(died));

			Message message2 = Message.getMessage(MessageName.DEATHMESSAGE_TO_KILLER);
			message2.updateForArena(this, killer, died, null);
			message2.send(Arrays.asList(killer));
		}
		else{
			Message message = Message.getMessage(MessageName.DEATHMESSAGE_TO_PLAYER_UNKNOWN_KILLER);
			message.updateForArena(this, killer, died, null);
			message.send(Arrays.asList(died));
		}
	}
	public void sendEndedMessage(){
		Message message = Message.getMessage(MessageName.ENDED);
		message.updateForArena(this, first, second, third);
		message.send(getPlayers());
		message.send(getSpectators());
	}
	public void sendEndingMessage(){
		Message message = Message.getMessage(MessageName.ENDING);
		message.updateForArena(this, null, null, null);
		message.send(getPlayers());
		message.send(getSpectators());
	}
	public void sendStartedMessage(){
		Message message = Message.getMessage(MessageName.STARTED);
		message.updateForArena(this, null, null, null);
		message.send(getPlayers());
		message.send(getSpectators());
	}
	public void sendStartingMessage(){
		Message message = Message.getMessage(MessageName.STARTING);
		message.updateForArena(this, null, null, null);
		message.send(getPlayers());
		message.send(getSpectators());
	}
	public void sendJoinMessage(SpleefPlayer sp){
		MessageName messagename;
		if(sp.isPlayer()){
			messagename = MessageName.PLAYER_JOIN;
		}
		else{
			messagename = MessageName.SPECTATOR_JOIN;
		}
		
		Message message = Message.getMessage(messagename);
		message.updateForArena(this, sp, null, null);
		message.send(getPlayers());
		message.send(getSpectators());
	}
	public void sendQuitMessage(SpleefPlayer sp){
		MessageName messagename;
		if(sp.isPlayer()){
			messagename = MessageName.PLAYER_QUIT;
		}
		else{
			messagename = MessageName.SPECTATOR_QUIT;
		}
		
		Message message = Message.getMessage(messagename);
		message.updateForArena(this, sp, null, null);
		message.send(getPlayers());
		message.send(getSpectators());
	}
	
	public static Arena getArenaFromID(int arenaid){
		for(Arena arena : StorageManager.arenas){
			if(arena.getArenaID() == arenaid){
				return arena;
			}
		}
		return null;
	}
	
	public static int getNextArenaID(){
		return StorageManager.lastarenaid +1;
	}
}
