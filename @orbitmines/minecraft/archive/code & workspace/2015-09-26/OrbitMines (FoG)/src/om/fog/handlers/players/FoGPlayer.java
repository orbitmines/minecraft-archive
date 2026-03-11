package om.fog.handlers.players;

import java.util.Map;

import om.api.handlers.Database;
import om.api.handlers.players.OMPlayer;
import om.fog.FoG;
import om.fog.utils.enums.Faction;
import om.fog.utils.enums.Suit;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class FoGPlayer extends OMPlayer {
	
	private static FoG fog = FoG.getInstance();
	private Faction faction;
	private Map<Suit, Integer> repairTokens;
	private int kills;
	private int mobKills;
	private int deaths;
	private int levels;
	private int exp;
	private int silver;
	
	public FoGPlayer(Player player, boolean loaded) {
		super(player, loaded);
		
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
		//addSilver(500);
		
		Player p = getPlayer();
		//new Title("§b§lVote", "§6+500 Coins").send(p);
		
		p.sendMessage("");
		p.sendMessage("§b§lVote §8| §7Thank you, §b§l" + p.getName() + " §7for your §b§lVote§7!");
		//p.sendMessage("§b§lVote §8| §7Your reward in the " + Server.KITPVP.getName() + "§7 Server:");
		p.sendMessage("§b§lVote §8| §7");

		//for(String s : Server.KITPVP.getVoteRewardMessages()){
		//	p.sendMessage("§b§lVote §8| §7  - " + s);
		//}
		
		p.sendMessage("§b§lVote §8| §7");
		p.sendMessage("§b§lVote §8| §7Your Total Votes this Month: §b§l" + getVotes());
		p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 1);
		
		for(Player player : Bukkit.getOnlinePlayers()){
			if(player != p) player.sendMessage("§b§l" + p.getName() + "§7 has voted with §b§l/vote§7.");
		}
	}
	
	@Override
	public void unloadPlayerData() {
		
	}
	
	public Faction getFaction() {
		return faction;
	}
	
	public void setFaction(Faction faction) {
		this.faction = faction;
		
		Database.get().update("FoG-Faction", "faction", "" + this.faction.toString(), "uuid", getUUID().toString());
	}
	
	public void joinFaction(Faction faction) {
		Bukkit.broadcastMessage(getName() + "§7 joined " + faction.getName() + "§7!");
		
		//p.teleporter(faction.getBaseLocation());
		//Give Items
		
		setFaction(faction);
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

	public int getMobKills(){
		return mobKills;
	}
	public void setMobKills(int mobKills){
		this.mobKills = mobKills;
		
		Database.get().update("FoG-MobKills", "mobkills", "" + this.mobKills, "uuid", getUUID().toString());
	}
	public void addMobKill(){
		this.mobKills = getKills() +1;
		
		Database.get().update("FoG-MobKills", "mobkills", "" + this.mobKills, "uuid", getUUID().toString());
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
		
		try{
			
			
			updateLevel();
		}catch(NullPointerException ex){
			ex.printStackTrace();
		}
	}
}
