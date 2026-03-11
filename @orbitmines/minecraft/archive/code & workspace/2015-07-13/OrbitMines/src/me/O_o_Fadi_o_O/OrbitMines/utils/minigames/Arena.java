package me.O_o_Fadi_o_O.OrbitMines.utils.minigames;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import me.O_o_Fadi_o_O.OrbitMines.Start;
import me.O_o_Fadi_o_O.OrbitMines.utils.Letters;
import me.O_o_Fadi_o_O.OrbitMines.utils.OMPlayer;
import me.O_o_Fadi_o_O.OrbitMines.utils.ServerData;
import me.O_o_Fadi_o_O.OrbitMines.utils.Utils;
import me.O_o_Fadi_o_O.OrbitMines.utils.Utils.Direction;
import me.O_o_Fadi_o_O.OrbitMines.utils.Utils.GameState;
import me.O_o_Fadi_o_O.OrbitMines.utils.Utils.MiniGameType;
import me.O_o_Fadi_o_O.OrbitMines.utils.Utils.Server;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class Arena {

	private MiniGameType type;
	private int arenaid;
	private GameState state;
	private Location lobby;
	private Map map;
	private List<OMPlayer> players;
	private List<OMPlayer> deadplayers;
	private List<OMPlayer> spectators;
	private int minutes;
	private int seconds;
	private SurvivalGamesData sgdata;
	
	public Arena(MiniGameType type, int arenaid, Location lobby){
		this.type = type;
		this.arenaid = arenaid;
		this.setState(GameState.WAITING);
		this.lobby = lobby;
		this.players = new ArrayList<OMPlayer>();
		this.spectators = new ArrayList<OMPlayer>();
	
		switch(type){
			case CHICKEN_FIGHT:
				break;
			case GHOST_ATTACK:
				break;
			case SKYWARS:
				break;
			case SPLATCRAFT:
				break;
			case SPLEEF:
				break;
			case SURVIVAL_GAMES:
				this.sgdata = new SurvivalGamesData(this);
				break;
			case ULTRA_HARD_CORE:
				break;
		}
		
		setRandomMap();
		ServerData.getMiniGames().getArenas().add(this);
	}

	public MiniGameType getType() {
		return type;
	}

	public int getArenaID() {
		return arenaid;
	}

	public Location getLobby() {
		return lobby;
	}

	public Map getMap() {
		return map;
	}
	public void setMap(Map map) {
		this.map = map;
	}
	public void setRandomMap(){
		List<Map> maps = ServerData.getMiniGames().getMaps().get(type);
		List<Map> availablemaps = new ArrayList<Map>();
		for(Map map : maps){
			if(!map.isInUse()){
				availablemaps.add(map);
			}
		}
		this.map = availablemaps.get(new Random().nextInt(availablemaps.size()));
		this.map.setInUse(true);
		
		generateWords();
	}

	public GameState getState() {
		return state;
	}
	public void setState(GameState state) {
		this.state = state;
	}

	public List<OMPlayer> getPlayers() {
		return players;
	}
	public void setPlayers(List<OMPlayer> players) {
		this.players = players;
	}

	public List<OMPlayer> getDeadPlayers() {
		return deadplayers;
	}
	public void setDeadPlayers(List<OMPlayer> deadplayers) {
		this.deadplayers = deadplayers;
	}

	public List<OMPlayer> getSpectators() {
		return spectators;
	}
	public void setSpectators(List<OMPlayer> spectators) {
		this.spectators = spectators;
	}
	
	public List<OMPlayer> getAllPlayers(){
		List<OMPlayer> players = new ArrayList<OMPlayer>();
		players.addAll(getPlayers());
		players.addAll(getSpectators());
		
		return players;
	}

	public int getMinutes() {
		return minutes;
	}
	public void setMinutes(int minutes) {
		this.minutes = minutes;
	}

	public int getSeconds() {
		return seconds;
	}
	public void setSeconds(int seconds) {
		this.seconds = seconds;
	}
	
	public SurvivalGamesData getSG() {
		return sgdata;
	}

	public void setSG(SurvivalGamesData sgdata) {
		this.sgdata = sgdata;
	}
	
	public void tickTimer(){
		if(getSeconds() != -1){
			setSeconds(getSeconds() -1);
		}
		if(getSeconds() == -1){
			setMinutes(getMinutes() -1);
			setSeconds(59);
		}
	}
	
	public void sendMessage(String message){
		for(OMPlayer omp : getAllPlayers()){
			omp.getPlayer().sendMessage(message);
		}
	}
	
	public void sendMessage(String[] messages){
		for(OMPlayer omp : getAllPlayers()){
			for(String message : messages){
				omp.getPlayer().sendMessage(message);
			}
		}
	}
	
	public void playSound(Sound sound, float arg2, float arg3){
		for(OMPlayer omp : getAllPlayers()){
			omp.getPlayer().playSound(omp.getPlayer().getLocation(), sound, arg2, arg3);
		}
	}
	
	public boolean isSpectator(OMPlayer omp){
		return this.spectators.contains(omp);
	}
	public boolean isPlayer(OMPlayer omp){
		return this.players.contains(omp);
	}
	
	public void join(OMPlayer omp){
		if(getState() != GameState.ENDING && getState() != GameState.RESTARTING){
			if(getState() != GameState.WARMUP && getState() != GameState.IN_GAME){
				if(getPlayers().size() != getType().getMaxPlayers()){
					getPlayers().add(omp);
					omp.getPlayer().teleport(getLobby());
				}
				else{
					omp.toServer(Server.HUB);
				}
			}
			else{
				getSpectators().add(omp);
				omp.getPlayer().teleport(getMap().getSpectatorLocation());
			}
		}
		else{
			omp.toServer(Server.HUB);
		}
	}
	
	public void leave(OMPlayer omp){
		if(isPlayer(omp)){
			getPlayers().remove(omp);
		}
		else{
			getSpectators().remove(omp);
		}
		
		omp.toServer(Server.HUB);
	}
	
	public void generateWords(){
		Location l1 = new Location(getLobby().getWorld(), getLobby().getX() -14, getLobby().getY() +9, getLobby().getZ() -30);
		Location l2 = new Location(getLobby().getWorld(), getLobby().getX() -18, getLobby().getY() +3, getLobby().getZ() -30);
		
		//Clear Previous Words\\
		for(Block b : Utils.getBlocksBetween(l1, new Location(getLobby().getWorld(), getLobby().getX() +50, getLobby().getY() +13, getLobby().getZ() -30))){
			if(b.getType() != Material.AIR){
				b.setType(Material.AIR);
			}
		}
		for(Block b : Utils.getBlocksBetween(l2, new Location(getLobby().getWorld(), getLobby().getX() +50, getLobby().getY() +7, getLobby().getZ() -30))){
			if(b.getType() != Material.AIR){
				b.setType(Material.AIR);
			}
		}
		
		new Letters(getType().getName(), Direction.NORTH, l1).generate(Material.STAINED_CLAY, 0);
		new Letters(getMap().getMapName(), Direction.NORTH, l2).generate(Material.STAINED_CLAY, 0);
	}
	
	public void sendData(){
		if(Bukkit.getOnlinePlayers().size() > 0){
			ByteArrayOutputStream b = new ByteArrayOutputStream();
        	DataOutputStream out = new DataOutputStream(b);
 
        	try{
        		out.writeUTF("Forward");
        		out.writeUTF("ALL");
        		out.writeUTF(getType().getShortName());
            	
            	out.writeUTF(getArenaID() + "|" + getState().toString() + "|" + getPlayers().size() + "|" + getMinutes() + "|" + getSeconds());
        	}catch(IOException ex){
        		ex.printStackTrace();
        	}
        	
        	Player player = null;
        	for(Player p : Bukkit.getOnlinePlayers()){
        		if(player == null){
        			player = p;
        		}
        	}
    		player.sendPluginMessage(Start.getInstance(), "BungeeCord", b.toByteArray());
    	}
	}
	
	public static Arena getArena(MiniGameType type, int arenaid){
		for(Arena arena : ServerData.getMiniGames().getArenas()){
			if(arena.getType() == type && arena.getArenaID() == arenaid){
				return arena;
			}
		}
		return null;
	}
}
