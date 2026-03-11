package fadidev.orbitmines.minigames.handlers.players.minigames;

import fadidev.orbitmines.api.handlers.Database;
import fadidev.orbitmines.minigames.handlers.players.ArenaPlayer;
import fadidev.orbitmines.minigames.handlers.players.MiniGamesPlayer;
import fadidev.orbitmines.minigames.utils.enums.TicketType;
import org.bukkit.util.Vector;

public class ChickenFightPlayer extends ArenaPlayer {

	private int kills;
	private int wins;
	private int loses;
	private int bestStreak;
	
	public ChickenFightPlayer(MiniGamesPlayer omp, int kills, int wins, int loses, int bestStreak){
		super(omp);
		this.kills = kills;
		this.wins = wins;
		this.loses = loses;
		this.bestStreak = bestStreak;
	}

    public int getKills(){
		return kills;
	}

	public void setKills(int kills){
		this.kills = kills;
		
		Database.get().update("ChickenFight-Kills", "kills", "" + getKills(), "uuid", getPlayer().getUUID().toString());
	}

	public void addKill(){
		this.kills = getKills() +1;
		
		Database.get().update("ChickenFight-Kills", "kills", "" + getKills(), "uuid", getPlayer().getUUID().toString());
	}

	public int getWins(){
		return wins;
	}

	public void setWins(int wins){
		this.wins = wins;
		
		Database.get().update("ChickenFight-Wins", "wins", "" + getWins(), "uuid", getPlayer().getUUID().toString());
	}
	public void addWin(){
		this.wins = getWins() +1;
		
		Database.get().update("ChickenFight-Wins", "wins", "" + getWins(), "uuid", getPlayer().getUUID().toString());
	}

	public int getLoses(){
		return loses;
	}

	public void setLoses(int loses){
		this.loses = loses;
		
		Database.get().update("ChickenFight-Loses", "loses", "" + getLoses(), "uuid", getPlayer().getUUID().toString());
	}

	public void addLose(){
		this.loses = getLoses() +1;
		
		Database.get().update("ChickenFight-Loses", "loses", "" + getLoses(), "uuid", getPlayer().getUUID().toString());
	}

	public int getBestStreak(){
		return bestStreak;
	}

	public void setBestStreak(int bestStreak){
		this.bestStreak = bestStreak;
		
		Database.get().update("ChickenFight-BestStreak", "beststreak", "" + getBestStreak(), "uuid", getPlayer().getUUID().toString());
	}

	public Vector getVelocity(Vector v){
		return v.multiply(getKnockbackMotifier());
	}

	public double getKnockbackMotifier(){
        String name = getKitSelected().getName();
		if(getKitSelected() == null)
		    return 1;

        if(name.equals(TicketType.CHICKEN_MAMA_KIT.toString())){
            return 1.2;
        }
        else if(name.equals(TicketType.BABY_CHICKEN_KIT.toString())){
            return 1.5;
        }
        else if(name.equals(TicketType.HOT_WING_KIT.toString())){
            return 1.3;
        }
        else if(name.equals(TicketType.CHICKEN_WARLORD_KIT.toString())){
            return 0.9;
        }
        else if(name.equals(TicketType.CHICKEN_KIT.toString())){
            return 0.95;
        }
        else{
            return 1;
        }
	}
}
