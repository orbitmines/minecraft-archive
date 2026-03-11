package me.O_o_Fadi_o_O.MiniGames.runnables;

import me.O_o_Fadi_o_O.MiniGames.Start;
import me.O_o_Fadi_o_O.MiniGames.managers.StorageManager;
import me.O_o_Fadi_o_O.MiniGames.utils.ChickenFightState;
import me.O_o_Fadi_o_O.MiniGames.utils.Game;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class EXPBarRunnable {

	Start plugin;
	 
	public EXPBarRunnable(Start instance) {
		plugin = instance;
	}
	
	public void startEXPBarRunnable(){
		
		new BukkitRunnable(){
			
			@Override
			public void run(){
				
				for(Player p : Bukkit.getOnlinePlayers()){
					if(StorageManager.PlayersInChickenFight.contains(p)){
						
						if(StorageManager.PlayersGame.get(p).equals(Game.CHICKENFIGHT)){
							if(!StorageManager.DeadPlayersInChickenFight.contains(p)){
								if(StorageManager.ChickenFightState.get(StorageManager.PlayersArena.get(p)) == ChickenFightState.INGAME){
									float currentexp = p.getExp();
									
									if(StorageManager.ChickenFightKit.get(p).equals("Chicken Mama")){
										if(currentexp <= 1){
											
											p.setExp(currentexp + 1F / 120F);
											
										}
									}
									if(StorageManager.ChickenFightKit.get(p).equals("Baby Chicken")){
										if(currentexp <= 1){
											
											p.setExp(currentexp + 1F / 140F);
											
										}
									}
									if(StorageManager.ChickenFightKit.get(p).equals("Hot Wing")){
										if(currentexp <= 1){
											
											p.setExp(currentexp + 1F / 200F);
											
										}
									}
									if(StorageManager.ChickenFightKit.get(p).equals("Chicken Warrior")){
										if(currentexp <= 1){
											
											p.setExp(currentexp + 1F / 160F);
											
										}
									}
								}
							}
						}
					}
				}
			}
		}.runTaskTimer(this.plugin, 0, 1);
	}
	// 3, 60 Ticks, 1 / 60
	// 6, 120 Ticks, 1 / 120
	// 7, 140 Ticks, 1 / 140
	// 10, 200 Ticks, 1 / 200
	// 8, 160 Ticks, 1 / 160
}
