package fadidev.orbitmines.minigames.handlers.players.minigames;

import fadidev.orbitmines.api.handlers.Database;
import fadidev.orbitmines.api.utils.ColorUtils;
import fadidev.orbitmines.minigames.handlers.players.ArenaPlayer;
import fadidev.orbitmines.minigames.handlers.players.MiniGamesPlayer;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class SurvivalGamesPlayer extends ArenaPlayer {

	private int kills;
	private int wins;
	private int loses;
	private int bestStreak;
    private Color leatherColor;

	private boolean doubleLoot;
	private boolean enablePotions;
	
	public SurvivalGamesPlayer(MiniGamesPlayer omp, int kills, int wins, int loses, int bestStreak, Color leatherColor){
        super(omp);

		this.kills = kills;
		this.wins = wins;
		this.loses = loses;
		this.bestStreak = bestStreak;
        this.leatherColor = leatherColor;

        this.doubleLoot = false;
        this.enablePotions = false;
	}

	public int getKills(){
		return kills;
	}
	
	public void setKills(int kills){
		this.kills = kills;
		
		Database.get().update("SurvivalGames-Kills", "kills", "" + getKills(), "uuid", getPlayer().getUUID().toString());
	}
	
	public void addKill(){
		this.kills = getKills() +1;
		
		Database.get().update("SurvivalGames-Kills", "kills", "" + getKills(), "uuid", getPlayer().getUUID().toString());
	}

	public int getWins(){
		return wins;
	}
	
	public void setWins(int wins){
		this.wins = wins;

		Database.get().update("SurvivalGames-Wins", "wins", "" + getWins(), "uuid", getPlayer().getUUID().toString());
	}
	
	public void addWin(){
		this.wins = getWins() +1;
		
		Database.get().update("SurvivalGames-Wins", "wins", "" + getWins(), "uuid", getPlayer().getUUID().toString());
	}

	public int getLoses(){
		return loses;
	}
	
	public void setLoses(int loses){
		this.loses = loses;
		
		Database.get().update("SurvivalGames-Loses", "loses", "" + getLoses(), "uuid", getPlayer().getUUID().toString());
	}
	
	public void addLose(){
		this.loses = getLoses() +1;
		
		Database.get().update("SurvivalGames-Loses", "loses", "" + getLoses(), "uuid", getPlayer().getUUID().toString());
	}

	public int getBestStreak(){
		return bestStreak;
	}
	
	public void setBestStreak(int bestStreak){
		this.bestStreak = bestStreak;
		
		Database.get().update("SurvivalGames-BestStreak", "beststreak", "" + getBestStreak(), "uuid", getPlayer().getUUID().toString());
	}

    public Color getLeatherColor() {
        return leatherColor;
    }
    
    public void setLeatherColor(Color leatherColor) {
        this.leatherColor = leatherColor;

        if(Database.get().containsPath("SurvivalGames-Color", "uuid", "uuid", getPlayer().getUUID().toString())){
            Database.get().update("SurvivalGames-Color", "color", "" + ColorUtils.parse(getLeatherColor()), "uuid", getPlayer().getUUID().toString());
        }
        else{
            Database.get().insert("SurvivalGames-Color", "uuid`, `color", getPlayer().getUUID().toString() + "', '" + ColorUtils.parse(getLeatherColor()));
        }
    }

    public void updateLeatherColor(){
        if(getLeatherColor() != null){
            for(ItemStack item : getPlayer().getPlayer().getInventory().getContents()){
                if(item != null && (item.getType() == Material.LEATHER_BOOTS || item.getType() == Material.LEATHER_LEGGINGS || item.getType() == Material.LEATHER_CHESTPLATE || item.getType() == Material.LEATHER_HELMET)){
                    LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
                    meta.setColor(getLeatherColor());
                    item.setItemMeta(meta);
                }
            }
        }
    }

    public boolean isDoubleLoot() {
        return doubleLoot;
    }

    public void setDoubleLoot(boolean doubleLoot) {
        this.doubleLoot = doubleLoot;
    }

    public boolean isEnablePotions() {
        return enablePotions;
    }

    public void setEnablePotions(boolean enablePotions) {
        this.enablePotions = enablePotions;
    }
}
