package me.O_o_Fadi_o_O.OrbitMines.utils.minigames;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import me.O_o_Fadi_o_O.OrbitMines.Start;
import me.O_o_Fadi_o_O.OrbitMines.utils.Kit;
import me.O_o_Fadi_o_O.OrbitMines.utils.OMPlayer;
import me.O_o_Fadi_o_O.OrbitMines.utils.Utils;
import me.O_o_Fadi_o_O.OrbitMines.utils.Utils.GameState;
import me.O_o_Fadi_o_O.OrbitMines.utils.minigames.MiniGamesUtils.TicketType;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class GhostAttackData {

	private Arena arena;
	private int ghostrevealedin;
	private boolean ghostrevealed;
	private OMPlayer ghost;
	private OMPlayer ghostkiller;
	
	public GhostAttackData(Arena arena){
		this.arena = arena;
		this.ghostrevealedin = 5;
		this.ghostrevealed = false;
		this.ghost = null;
		this.ghostkiller = null;
	}

	public Arena getArena() {
		return arena;
	}
	public void setArena(Arena arena) {
		this.arena = arena;
	}
	
	public int getRevealedIn() {
		return ghostrevealedin;
	}
	public void setRevealedIn(int ghostrevealedin) {
		this.ghostrevealedin = ghostrevealedin;
	}
	public void tickRevealedIn(){
		ghostrevealedin--;
		
		if(ghostrevealedin == 0){
			ghostrevealedin = 5;
			
			if(new Random().nextBoolean()){
				revealeGhost();
			}
		}
	}
	
	public boolean isRevealed(){
		return ghostrevealed;
	}

	public void revealeGhost(){
		ghostrevealed = true;
		for(OMPlayer omp : arena.getAllPlayers()){
			Scoreboard b = omp.getPlayer().getScoreboard();
			
			if(b != null){
				Team t = b.getTeam("PlayersMG");
				t.setCanSeeFriendlyInvisibles(true);
			}
		}
		
		new BukkitRunnable(){
			public void run(){
				ghostrevealed = false;
				for(OMPlayer omp : arena.getAllPlayers()){
					Scoreboard b = omp.getPlayer().getScoreboard();
					
					if(b != null){
						Team t = b.getTeam("PlayersMG");
						t.setCanSeeFriendlyInvisibles(false);
					}
				}
			}
		}.runTaskLater(Start.getInstance(), 10);
	}

	public OMPlayer getGhost() {
		return ghost;
	}
	public void setGhost(OMPlayer ghost) {
		this.ghost = ghost;
	}
	public boolean isGhost(OMPlayer omp){
		return this.ghost == omp;
	}

	public OMPlayer getGhostKiller() {
		return ghostkiller;
	}
	public void setGhostKiller(OMPlayer ghostkiller) {
		this.ghostkiller = ghostkiller;
	}
	
	public void restart(){
		arena.setState(GameState.RESTARTING);
		
		Map map = arena.getMap();
		map.setInUse(false);
		map.restoreWorld();
		arena.setRandomMap();
		
		new BukkitRunnable(){
			public void run(){
				waiting();
			}
		}.runTaskLater(Start.getInstance(), 100);
	}
	
	public void ending(){
		arena.setMinutes(0);
		arena.setSeconds(15);
			
		arena.playSound(Sound.WITHER_DEATH, 5, 1);
		arena.sendMessage("§6§m--------------------------------------------------");
		arena.sendMessage(" §f§lGhost Attack §7- §6Resultaten");
		arena.sendMessage("");
		if(getGhostKiller() != null || getGhost() == null){
			if(getGhost() != null){
				arena.sendMessage(" §7§lDe Ghost was gedood door " + getGhostKiller().getName() + "§7§l!");
				arena.sendMessage("");
			}
			
			if(arena.getPlayers().size() > 1){
				String winners = "";
				for(OMPlayer omp : arena.getPlayers()){
					if(!isGhost(omp)){
						if(winners.equals("")){
							winners = omp.getName();
						}
						else{
							winners += "§7, " + omp.getName();
						}
					}
				}
				arena.sendMessage(" §6Winnaar: " + getGhost().getName());
			}
			else{
				arena.sendMessage(" §6Winnaar: " + arena.getPlayers().get(0).getName());
			}
		}
		else{
			arena.sendMessage(" §7§lDe Ghost heeft alle spelers uitgeschakeld!");
			arena.sendMessage("");
			arena.sendMessage(" §6Winnaar: " + getGhost().getName());
		}
		arena.sendMessage("");
		arena.sendMessage("§6§m--------------------------------------------------");
		
		for(OMPlayer omp : arena.getDeadPlayers()){
			omp.getPlayer().sendMessage("§c§l+1 Lose");
			omp.getGAPlayer().addLose();
		}
		
		if(getGhostKiller() != null || getGhost() == null){
			for(OMPlayer omp : arena.getPlayers()){
				omp.getPlayer().sendMessage("§2§l+1 Win");
				omp.getPlayer().sendMessage("§f§l+3 Tickets");
				omp.getGAPlayer().addWin();
				omp.addMGTickets(3);
			}
		}
		else{
			getGhost().getPlayer().sendMessage("§2§l+1 Win");
			getGhost().getPlayer().sendMessage("§f§l+4 Tickets");
			getGhost().getGAPlayer().addGhostWin();
			getGhost().addMGTickets(4);
		}

		arena.clearScoreboards();
		arena.setState(GameState.ENDING);
	}
	
	public void start(){
		arena.setMinutes(15);
		arena.setSeconds(0);
		
		OMPlayer ompG = arena.getPlayers().get(new Random().nextInt(arena.getPlayers().size()));
		setGhost(ompG);
		
		for(OMPlayer omp : arena.getPlayers()){
			omp.clearInventory();
			omp.getGAPlayer().setRoundKills(0);

			if(ompG == omp){
				Kit kit = Kit.getKit("GhostKit");
				kit.setItems(omp.getPlayer());
			}
			else{
				Kit kit = omp.getGAPlayer().getKit();
				if(kit != null){
					TicketType type = TicketType.valueOf(kit.getKitName());
					omp.removeTicket(type);
					
					kit.setItems(omp.getPlayer());
				}
			}
			
			omp.updateInventory();
		}
		
		arena.sendMessage(ompG.getColorName() + "§7 is de §7§lGhost§7!");

		arena.getMap().getWorld().setTime(20000);
		arena.clearScoreboards();
		arena.setState(GameState.IN_GAME);
	}
	
	public void warmup(){
		arena.setMinutes(0);
		arena.setSeconds(15);
		
		teleportToArena();
		for(OMPlayer omp : arena.getPlayers()){
			omp.clearInventory();
			omp.updateInventory();
			
			for(OMPlayer omplayer : arena.getPlayers()){
				omp.getPlayer().showPlayer(omplayer.getPlayer());
			}
		}
		
		arena.getMap().getWorld().setTime(20000);
		Utils.removeEntities(arena.getMap().getWorld());

		arena.playSound(Sound.WITHER_DEATH, 5, 1);
		arena.sendMessage("§6§m--------------------------------------------------");
		arena.sendMessage(" §f§lGhost Attack §7- §6Info");
		arena.sendMessage("");
		arena.sendMessage(" §a§oGhost: Schakel alle spelers uit");
		arena.sendMessage(" §a§oSpelers: Dood de Ghost. Je kan het af en toe zien.");
		arena.sendMessage("");
		arena.sendMessage(" §7Map: §6" + arena.getMap().getMapName());
		arena.sendMessage(" §7Gebouwd Door: §6" +  arena.getMap().getBuilder());
		arena.sendMessage("§6§m--------------------------------------------------");
		
		arena.clearScoreboards();
		arena.setState(GameState.WARMUP);
	}
	
	public void waiting(){
		arena.setMinutes(1);
		arena.setSeconds(0);
		arena.setPlayers(new ArrayList<OMPlayer>());
		arena.setDeadPlayers(new ArrayList<OMPlayer>());
		arena.setSpectators(new ArrayList<OMPlayer>());
		
		this.ghostrevealed = false;
		this.ghost = null;
		this.ghostkiller = null;
		
		arena.setState(GameState.WAITING);
	}
	
	public void rewardPlayers(){
		for(OMPlayer omp : arena.getAllPlayers()){
			Player p = omp.getPlayer();

			if(arena.isPlayer(omp) || arena.isSpectator(omp) && arena.getDeadPlayers().contains(omp)){
				int amount = 0;
				
				p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 1);
				p.sendMessage("§6§m--------------------------------------------------");
				p.sendMessage(" §f§lGhost Attack §7- §6Beloningen");
				p.sendMessage("");
				if(isGhost(omp) && getGhostKiller() == null){
					p.sendMessage(" §f§l+20 §7(Gewonnen als de Ghost)");
					amount += 20;
				}
				else if(getGhostKiller() == omp){
					p.sendMessage(" §f§l+15 §7(De Ghost gedood)");
					amount += 15;
				}
				else{
					p.sendMessage(" §f§l+5 §7(Geduld)");
					amount += 5;
				}
				
				if(!isGhost(omp)){
					int totalseconds = omp.getGAPlayer().getSecondsSurvived();
					int seconds = totalseconds % 60;
					int minutes = 0;
					int survivereward = (int) totalseconds / 25;
					
					if(totalseconds != seconds){
						minutes = (totalseconds - seconds) / 60;
					}
					
					if(survivereward != 0){
						p.sendMessage(" §f§l+" + survivereward + " §7(" + minutes + "m " + seconds + "s Overleefd)");
						amount += survivereward;
					}
				}
				else{
					int kills = omp.getGAPlayer().getRoundKills();
					if(kills != 0){
						if(kills == 1){
							p.sendMessage(" §f§l+" + 3 + " §7(1 Kill)");
						}
						else{
							p.sendMessage(" §f§l+" + 3 * kills + " §7(" + kills + " Kills)");
						}
						amount += 3 * kills;
					}
				}
				
				//TODO Boosters
				
				p.sendMessage(" §7Nieuw Balans: §f§l" + (omp.getMiniGameCoins() + amount) + " Coins");
				p.sendMessage("§6§m--------------------------------------------------");
				
				omp.addMiniGameCoins(amount);
			}
		}
	}
	
	public void teleportToArena(){
		Map map = getArena().getMap();
		
		for(OMPlayer omp : arena.getPlayers()){
			List<Location> locations = new ArrayList<Location>();
			for(Location l : map.getSpawns()){
				if(!map.getPlayerSpawns().containsValue(l)){
					locations.add(l);
				}
			}
			Location l = locations.get(new Random().nextInt(locations.size()));
			arena.getMap().getPlayerSpawns().put(omp, l);
			
			omp.getPlayer().teleport(l);
		}
	}
}
