package addtohub;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class MGArena {

	private static API api;
	private MiniGame type;
	private int arenaid;
	private Location signlocation;
	private State state;
	private int secondsrestarting;
	private int players;
	private int minutes;
	private int seconds;
	private int lastresponse;
	
	public MGArena(MiniGame type, int arenaid, Location signlocation){
		api = API.getInstance();
		this.type = type;
		this.arenaid = arenaid;
		this.signlocation = signlocation;
		this.state = State.CLOSED;
		this.players = 0;
		this.minutes = 0;
		this.seconds = 0;
		this.secondsrestarting = 0;
	}
	
	public MGArena(MiniGame type, int arenaid, State state, Location signlocation){
		api = API.getInstance();
		this.type = type;
		this.arenaid = arenaid;
		this.signlocation = signlocation;
		this.state = state;
		this.players = 0;
		this.minutes = 0;
		this.seconds = 0;
		this.secondsrestarting = 0;
	}

	public MiniGame getType() {
		return type;
	}
	public void setType(MiniGame type) {
		this.type = type;
	}

	public int getArenaID() {
		return arenaid;
	}
	public void setArenaID(int arenaid) {
		this.arenaid = arenaid;
	}

	public Location getSignLocation() {
		return signlocation;
	}
	public void setSignLocation(Location signlocation) {
		this.signlocation = signlocation;
	}

	public State getState() {
		return state;
	}
	public void setState(State state) {
		this.state = state;
	}

	public int getSecondsRestarting() {
		return secondsrestarting;
	}
	public void setSecondsRestarting(int secondsrestarting) {
		this.secondsrestarting = secondsrestarting;
	}

	public int getPlayers() {
		return players;
	}
	public void setPlayers(int players) {
		this.players = players;
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

	public int getLastResponse() {
		return lastresponse;
	}
	public void setLastResponse(int lastresponse) {
		this.lastresponse = lastresponse;
	}
	
	public void updateSign(){
		String color = getState().getColor();
		if((getState() == State.WAITING || getState() == State.STARTING) && getPlayers() == getType().getMaxPlayers()){
			color = "§4";
		}
		
		String[] lines = new String[4];
		if(getState() != State.CLOSED && getState() != State.RESTARTING && getState() != State.ENDING){
			lines[0] = color + "§l" + getType().getSignName() + " " + getArenaID();
			lines[1] = color + "§l" + getState().getStatus(this);
			lines[2] = getPlayers() + color + "/§0" + getType().getMaxPlayers();
			if(getState() == State.WAITING && getPlayers() < 3){
				lines[3] = color + "Waiting...";
			}
			else{
				lines[3] = color + getMinutes() + "m " + getSeconds() + "s";
			}
		}
		else{
			lines[0] = color + "§m            ";
			lines[1] = color + "§l" + getType().getSignName() + " " + getArenaID();
			lines[2] = color + color + "§l" + getState().getStatus(this);
			lines[3] = color + "§m            ";
		}
		
		for(Player p : Bukkit.getOnlinePlayers()){
			if(p.getWorld().getName().equals(getSignLocation().getWorld().getName()) && p.getLocation().distance(getSignLocation()) <= 16){
				p.sendSignChange(getSignLocation(), lines);
			}
		}
	}
	
	public static MGArena getMGArena(MiniGame type, int arenaid){
		for(MGArena arena : api.getMGArenas()){
			if(arena.getType() == type && arena.getArenaID() == arenaid){
				return arena;
			}
		}
		return null;
	}
	public static MGArena getMGArena(Location signlocation){
		for(MGArena arena : api.getMGArenas()){
			Location l = arena.getSignLocation();
			if(l.getBlockX() == signlocation.getBlockX() && l.getBlockY() == signlocation.getBlockY() && l.getBlockZ() == signlocation.getBlockZ()){
				return arena;
			}
		}
		return null;
	}
}
