package me.O_o_Fadi_o_O.OrbitMines.utils.minigames;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import me.O_o_Fadi_o_O.OrbitMines.utils.OMPlayer;
import me.O_o_Fadi_o_O.OrbitMines.utils.Utils;
import me.O_o_Fadi_o_O.OrbitMines.utils.Utils.GameState;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.WorldBorder;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;

public class SurvivalGamesData {

	private Arena arena;
	private List<Chest> lootedchests;
	private HashMap<OMPlayer, Integer> kills;
	private boolean indeathmatch;
	private OMPlayer firstplace;
	private OMPlayer secondplace;
	private OMPlayer thirdplace;
	
	public SurvivalGamesData(Arena arena){
		this.arena = arena;
		this.lootedchests = new ArrayList<Chest>();
		this.setKills(new HashMap<OMPlayer, Integer>());
		this.indeathmatch = false;
		this.firstplace = null;
		this.secondplace = null;
		this.thirdplace = null;
	}

	public Arena getArena() {
		return arena;
	}
	public void setArena(Arena arena) {
		this.arena = arena;
	}

	public List<Chest> getLootedChests() {
		return lootedchests;
	}
	public void setLootedChests(List<Chest> lootedchests) {
		this.lootedchests = lootedchests;
	}

	public HashMap<OMPlayer, Integer> getKills() {
		return kills;
	}
	public void setKills(HashMap<OMPlayer, Integer> kills) {
		this.kills = kills;
	}
	public int getKills(OMPlayer omp){
		return this.kills.get(omp);
	}

	public boolean isInDeathMatch() {
		return indeathmatch;
	}
	public void setInDeathMatch(boolean indeathmatch) {
		this.indeathmatch = indeathmatch;
	}

	public OMPlayer getFirstPlace() {
		return firstplace;
	}
	public void setFirstPlace(OMPlayer firstplace) {
		this.firstplace = firstplace;
	}

	public OMPlayer getSecondPlace() {
		return secondplace;
	}
	public void setSecondPlace(OMPlayer secondplace) {
		this.secondplace = secondplace;
	}

	public OMPlayer getThirdPlace() {
		return thirdplace;
	}
	public void setThirdPlace(OMPlayer thirdplace) {
		this.thirdplace = thirdplace;
	}
	
	public void startDeathMatch(){
		arena.sendMessage("§a§l§oAll Chests have been restocked!");
		arena.playSound(Sound.LEVEL_UP, 5, 1);
		
		getLootedChests().clear();
		setInDeathMatch(true);
		
		arena.setMinutes(3);
		arena.setSeconds(0);
		
		for(OMPlayer omp : arena.getPlayers()){
			if(omp.getPlayer().getVehicle() != null){
				omp.getPlayer().leaveVehicle();
			}
			
			omp.getPlayer().teleport(arena.getMap().getPlayerSpawns().get(omp));
		}
		for(OMPlayer omp : arena.getSpectators()){
			omp.getPlayer().teleport(arena.getMap().getSpectatorLocation());
		}
		
		WorldBorder border = arena.getMap().getSpectatorLocation().getWorld().getWorldBorder();
		border.setCenter(arena.getMap().getSpectatorLocation());
		border.setSize(40);
	}
	
	public void end(){
		if(arena.getPlayers().size() == 1){
			setFirstPlace(arena.getPlayers().get(0));
			
			arena.playSound(Sound.WITHER_DEATH, 5, 1);
			arena.sendMessage("§c§l§m###############################");
			arena.sendMessage("");
			arena.sendMessage("§f§lSurvival Games §7- Results");
			arena.sendMessage("");
			arena.sendMessage(" §61st: " + getFirstPlace().getName());
			arena.sendMessage(" §72nd: " + getSecondPlace().getName());
			arena.sendMessage(" §c3rd: " + getThirdPlace().getName());
			arena.sendMessage("");
			arena.sendMessage("§c§l§m###############################");	
			
			for(OMPlayer omp : arena.getDeadPlayers()){
				omp.getPlayer().sendMessage("§c§l+ 1 Lose");
				omp.getSGPlayer().addLose();
			}
			
			getFirstPlace().getPlayer().sendMessage("§2§l+ 1 Win");
			getFirstPlace().getSGPlayer().addWin();
			
			arena.setState(GameState.ENDING);
		}
	}
	
	public void warmup(){
		teleportToArena();
		for(OMPlayer omp : arena.getPlayers()){
			omp.clearInventory();
			omp.updateInventory();
			
			for(OMPlayer omplayer : arena.getPlayers()){
				omp.getPlayer().showPlayer(omplayer.getPlayer());
			}
		}
		
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "day night " + arena.getMap().getWorldName());
		Utils.removeEntities(arena.getMap().getWorld());

		arena.playSound(Sound.WITHER_DEATH, 5, 1);
		arena.sendMessage("§c§l§m###############################");
		arena.sendMessage("");
		arena.sendMessage("§f§lSurvival Games §7- Info");
		arena.sendMessage("");
		arena.sendMessage("§a§o Gather loot from chests and kill all opponents!");
		arena.sendMessage("");
		arena.sendMessage(" §cMap: §f" + arena.getMap().getMapName());	
		arena.sendMessage(" §cBuilders: §f" +  arena.getMap().getBuilder());
		arena.sendMessage("§c§l§m###############################");
		
		arena.setState(GameState.WARMUP);
	}
	
	public void rewardPlayers(){
		for(OMPlayer omp : arena.getAllPlayers()){
			Player p = omp.getPlayer();

			if(arena.isPlayer(omp) || arena.isSpectator(omp) && arena.getDeadPlayers().contains(omp)){
				int amount = 0;
				
				p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 1);
				p.sendMessage("§c§l§m###############################");
				p.sendMessage("");
				p.sendMessage("§f§lSurvival Games §7- Rewards");
				p.sendMessage("");
				if(getFirstPlace() == omp){
					p.sendMessage(" §f§l+25 §7(1st Place)");
					amount += 25;
				}
				else if(getSecondPlace() == omp){
					p.sendMessage(" §f§l+15 §7(2nd Place)");
					amount += 15;
				}
				else if(getThirdPlace() == omp){
					p.sendMessage(" §f§l+10 §7(3rd Place)");
					amount += 10;
				}
				else{
					p.sendMessage(" §f§l+5 §7(Patient)");
					amount += 5;
				}
				
				int kills = getKills(omp);
				if(kills != 0){
					if(kills == 1){
						p.sendMessage(" §f§l+" + 4 + " §7(1 Kill)");
					}
					else{
						p.sendMessage(" §f§l+" + 4 * kills + " §7(" + kills + " Kills)");
					}
					amount += 4 * kills;
				}
				
				//TODO Boosters
				
				p.sendMessage(" §7New Balance: §f§l" + omp.getMiniGameCoins() + amount + " Coins");
				p.sendMessage("");
				p.sendMessage("§c§l§m###############################");
				
				omp.addMiniGameCoins(amount);
			}
		}
	}
	
	public void teleportToArena(){
		List<Location> locations = new ArrayList<Location>();
		locations.addAll(arena.getMap().getSpawns());
		
		for(OMPlayer omp : arena.getPlayers()){
			Location l = locations.get(new Random().nextInt(locations.size()));
			arena.getMap().getPlayerSpawns().put(omp, l);
			locations.remove(l);
			
			omp.getPlayer().teleport(l);
		}
	}
}
