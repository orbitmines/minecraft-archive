package me.O_o_Fadi_o_O.MiniGames.events;

import me.O_o_Fadi_o_O.MiniGames.managers.StorageManager;
import me.O_o_Fadi_o_O.MiniGames.utils.Game;
import me.O_o_Fadi_o_O.MiniGames.utils.SurvivalGamesState;

import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleFlightEvent;

public class ToggleFlightEvent implements Listener{
	
	@EventHandler
	public void onFlightToggle(PlayerToggleFlightEvent e){
		
		Player p = e.getPlayer();
		
		if(p.getGameMode() != GameMode.CREATIVE && !StorageManager.SpectatorsInChickenFight.contains(p) && !StorageManager.SpectatorsInSurvivalGames.contains(p)){
			if(StorageManager.PlayersGame.containsKey(p)){
				if(StorageManager.PlayersGame.get(p) == Game.CHICKENFIGHT){
						
		            p.setFlying(false);
					p.setVelocity(p.getLocation().getDirection().multiply(1.15).setY(0.5));
		            p.setAllowFlight(false);
		            e.setCancelled(true);
					p.playSound(p.getLocation(), Sound.FIREWORK_BLAST, 5, 1);
				}
				if(StorageManager.PlayersGame.get(p) == Game.SURVIVALGAMES){
					if(StorageManager.PlayersInSurvivalGames.contains(p)){
						if(StorageManager.SurvivalGamesState.get(StorageManager.PlayersArena.get(p)) == SurvivalGamesState.LOBBY || StorageManager.SurvivalGamesState.get(StorageManager.PlayersArena.get(p)) == SurvivalGamesState.LASTSECONDS){
							p.setAllowFlight(false);
							p.setFlying(false);
							p.setVelocity(p.getLocation().getDirection().multiply(1.15).setY(0.5));
							p.playSound(p.getLocation(), Sound.FIREWORK_BLAST, 5, 1);
							e.setCancelled(true);
						}
						else{
							p.setAllowFlight(false);
							p.setFlying(false);
						}
					}
				}
			}
			else{
				p.setAllowFlight(false);
				p.setFlying(false);
				p.setVelocity(p.getLocation().getDirection().multiply(1.15).setY(0.5));
				p.playSound(p.getLocation(), Sound.FIREWORK_BLAST, 5, 1);
				e.setCancelled(true);
			}
		}
	}
}
