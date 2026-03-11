package fadidev.orbitmines.minigames.handlers.players.minigames;

import fadidev.orbitmines.api.handlers.Database;
import fadidev.orbitmines.minigames.handlers.players.ArenaPlayer;
import fadidev.orbitmines.minigames.handlers.players.MiniGamesPlayer;
import fadidev.orbitmines.minigames.utils.enums.TicketType;
import org.bukkit.block.Block;

import java.util.ArrayList;
import java.util.List;

public class SkywarsPlayer extends ArenaPlayer {

	private int kills;
	private int wins;
	private int loses;
	private int bestStreak;
    private TicketType cage;

    private List<Block> cageBlocks;
    private MiniGamesPlayer lastProjectile;
	
	public SkywarsPlayer(MiniGamesPlayer omp, int kills, int wins, int loses, int bestStreak, TicketType cage){
		super(omp);

		this.kills = kills;
		this.wins = wins;
		this.loses = loses;
		this.bestStreak = bestStreak;
        this.cage = cage;

        this.cageBlocks = new ArrayList<>();
	}

	public int getKills(){
		return kills;
	}
	
	public void setKills(int kills){
		this.kills = kills;
		
		Database.get().update("Skywars-Kills", "kills", "" + getKills(), "uuid", getPlayer().getUUID().toString());
	}
	
	public void addKill(){
		this.kills = getKills() +1;
		
		Database.get().update("Skywars-Kills", "kills", "" + getKills(), "uuid", getPlayer().getUUID().toString());
	}

	public int getWins(){
		return wins;
	}
	
	public void setWins(int wins){
		this.wins = wins;
		
		Database.get().update("Skywars-Wins", "wins", "" + getWins(), "uuid", getPlayer().getUUID().toString());
	}
	public void addWin(){
		this.wins = getWins() +1;
		
		Database.get().update("Skywars-Wins", "wins", "" + getWins(), "uuid", getPlayer().getUUID().toString());
	}

	public int getLoses(){
		return loses;
	}
	
	public void setLoses(int loses){
		this.loses = loses;
		
		Database.get().update("Skywars-Loses", "loses", "" + getLoses(), "uuid", getPlayer().getUUID().toString());
	}
	
	public void addLose(){
		this.loses = getLoses() +1;
		
		Database.get().update("Skywars-Loses", "loses", "" + getLoses(), "uuid", getPlayer().getUUID().toString());
	}

	public int getBestStreak(){
		return bestStreak;
	}
	
	public void setBestStreak(int bestStreak){
		this.bestStreak = bestStreak;
		
		Database.get().update("Skywars-BestStreak", "beststreak", "" + getBestStreak(), "uuid", getPlayer().getUUID().toString());
	}

    public TicketType getCage() {
        return cage;
    }
    
    public void setCage(TicketType cage) {
        this.cage = cage;

        if(Database.get().containsPath("Skywars-Cage", "uuid", "uuid", getPlayer().getUUID().toString())){
            Database.get().update("Skywars-Cage", "cage", "" + getCage().toString(), "uuid", getPlayer().getUUID().toString());
        }
        else{
            Database.get().insert("Skywars-Cage", "uuid`, `cage", getPlayer().getUUID().toString() + "', '" + getCage().toString());
        }
    }

    public List<Block> getCageBlocks() {
        return cageBlocks;
    }

	public void setCageBlocks(List<Block> cageBlocks) {
		this.cageBlocks = cageBlocks;
	}

	public MiniGamesPlayer getLastProjectile() {
        return lastProjectile;
    }

    public void setLastProjectile(MiniGamesPlayer lastProjectile) {
        this.lastProjectile = lastProjectile;
    }
}
