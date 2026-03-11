package fadidev.orbitmines.hub.handlers;

import fadidev.orbitmines.api.utils.enums.Language;
import fadidev.orbitmines.hub.OrbitMinesHub;
import fadidev.orbitmines.hub.handlers.players.HubPlayer;
import fadidev.orbitmines.hub.utils.enums.MiniGameType;
import fadidev.orbitmines.hub.utils.enums.State;
import org.bukkit.Location;

import java.util.HashMap;
import java.util.Map;

public class MiniGameArena {

    private static OrbitMinesHub hub;
	private MiniGameType type;
	private int arenaId;
	private Location signLocation;
	private State state;
	private int secondsRestarting;
	private int players;
	private int minutes;
	private int seconds;
	private int lastResponse;

	public MiniGameArena(MiniGameType type, int arenaId, Location signLocation){
	    hub = OrbitMinesHub.getHub();
		this.type = type;
		this.arenaId = arenaId;
		this.signLocation = signLocation;
		this.state = State.CLOSED;
		this.players = 0;
		this.minutes = 0;
		this.seconds = 0;
		this.secondsRestarting = 0;
	}

	public MiniGameArena(MiniGameType type, int arenaId, State state, Location signLocation){
        hub = OrbitMinesHub.getHub();
		this.type = type;
		this.arenaId = arenaId;
		this.signLocation = signLocation;
		this.state = state;
		this.players = 0;
		this.minutes = 0;
		this.seconds = 0;
		this.secondsRestarting = 0;
	}

	public MiniGameType getType() {
		return type;
	}
	public void setType(MiniGameType type) {
		this.type = type;
	}

	public int getArenaID() {
		return arenaId;
	}
	public void setArenaID(int arenaId) {
		this.arenaId = arenaId;
	}

	public Location getSignLocation() {
		return signLocation;
	}
	public void setSignLocation(Location signLocation) {
		this.signLocation = signLocation;
	}

	public State getState() {
		return state;
	}
	public void setState(State state) {
		this.state = state;
	}

	public int getSecondsRestarting() {
		return secondsRestarting;
	}
	public void setSecondsRestarting(int secondsRestarting) {
		this.secondsRestarting = secondsRestarting;
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
		return lastResponse;
	}
	public void setLastResponse(int lastResponse) {
		this.lastResponse = lastResponse;
	}
	
	public void updateSign(){
		String color = getState().getColor();
		if((getState() == State.WAITING || getState() == State.STARTING) && getPlayers() == getType().getMaxPlayers()){
			color = "§4";
		}

		Map<Language, String[]> langLines = new HashMap<>();

        for(Language language : Language.values()){
            String[] lines = new String[4];
            if(getState() != State.CLOSED && getState() != State.RESTARTING && getState() != State.ENDING){
                lines[0] = color + "§l" + getType().getSignName() + " " + getArenaID();
                lines[1] = color + "§l" + getState().getStatus(this, language);
                lines[2] = getPlayers() + color + "/§0" + getType().getMaxPlayers();
                if(getState() == State.WAITING && getPlayers() < 3){
                    switch(language){
                        case DUTCH:
                            lines[3] = color + "Wachten...";
                            break;
                        case ENGLISH:
                            lines[3] = color + "Waiting...";
                            break;
                    }
                }
                else{
                    lines[3] = color + getMinutes() + "m " + getSeconds() + "s";
                }
            }
            else{
                lines[0] = color + "§m            ";
                lines[1] = color + "§l" + getType().getSignName() + " " + getArenaID();
                lines[2] = color + color + "§l" + getState().getStatus(this, language);
                lines[3] = color + "§m            ";
            }
            langLines.put(language, lines);
        }

        for(HubPlayer omp : hub.getHubPlayers()){
            omp.getPlayer().sendSignChange(getSignLocation(), langLines.get(omp.getLanguage()));
        }
	}
	
	public static MiniGameArena getMiniGameArena(MiniGameType type, int arenaId){
		for(MiniGameArena arena : hub.getMiniGameArenas()){
			if(arena.getType() == type && arena.getArenaID() == arenaId)
				return arena;
		}
		return null;
	}
	public static MiniGameArena getMiniGameArena(Location signLocation){
		for(MiniGameArena arena : hub.getMiniGameArenas()){
			Location l = arena.getSignLocation();
			if(l.getBlockX() == signLocation.getBlockX() && l.getBlockY() == signLocation.getBlockY() && l.getBlockZ() == signLocation.getBlockZ())
				return arena;
		}
		return null;
	}
}
