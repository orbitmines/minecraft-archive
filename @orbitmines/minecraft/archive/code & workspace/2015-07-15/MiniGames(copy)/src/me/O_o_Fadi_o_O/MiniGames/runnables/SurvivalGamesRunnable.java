package me.O_o_Fadi_o_O.MiniGames.runnables;

import java.util.HashMap;

import me.O_o_Fadi_o_O.MiniGames.Start;
import me.O_o_Fadi_o_O.MiniGames.Inventories.SurvivalGamesInv;
import me.O_o_Fadi_o_O.MiniGames.Inventories.TeleporterInv;
import me.O_o_Fadi_o_O.MiniGames.managers.StorageManager;
import me.O_o_Fadi_o_O.MiniGames.managers.SurvivalGames;
import me.O_o_Fadi_o_O.MiniGames.utils.Game;
import me.O_o_Fadi_o_O.MiniGames.utils.SurvivalGamesState;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

public class SurvivalGamesRunnable {

	Start start = Start.getInstance();
	
	public HashMap<Integer, Integer> i = new HashMap<Integer, Integer>();
	
	public void startSurvivalGamesRunnable(){
		
		i.put(1, 0);
		i.put(2, 0);
		
		new BukkitRunnable(){
			
			@SuppressWarnings("deprecation")
			public void run(){
				
				for(int arena = 1; arena <= 2; arena++){
					try{
						
						if(StorageManager.survivalgamesstate.get(arena).equals(SurvivalGamesState.DEATHMATCH)){
							
							
							for(Player p : Bukkit.getOnlinePlayers()){
								if(StorageManager.playersinsurvivalgames.contains(p)){
									if(StorageManager.playersarena.get(p) == arena){
										
										Location l = StorageManager.survivalgamesspectatorlocation.get(StorageManager.survivalgamescurrentmap.get(arena));
										int x = l.getBlockX();
										int z = l.getBlockZ();
										
										int bDistance = 0;
										int xB = p.getLocation().getBlockX() -x;
										int zB = p.getLocation().getBlockZ() -z;
										
										if(xB < 0){
											xB = -xB;
										}
										if(zB < 0){
											zB = -zB;
										}
										
										if(xB <= zB){
											bDistance = zB;
										}
										else{
											bDistance = xB;
										}
										
										bDistance = 40 - bDistance;
										
										if(bDistance < 0){
											p.sendMessage("§6SurvivalGames §8| §4§l§oStay in the Arena!");
											p.playSound(p.getLocation(), Sound.ENDERMAN_SCREAM, 5, 1);
											p.damage(3);
											
										}
									}
								}
							}
							
							
							
						}
					}catch(Exception ex){
					
					}
				}
			}
		}.runTaskTimer(this.start, 0, 20);
	}
}
