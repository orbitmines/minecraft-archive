package me.O_o_Fadi_o_O.MiniGames.events;

import me.O_o_Fadi_o_O.MiniGames.DisguisePlayer;
import me.O_o_Fadi_o_O.MiniGames.Start;
import me.O_o_Fadi_o_O.MiniGames.Kits.Others.SpectatorKit;
import me.O_o_Fadi_o_O.MiniGames.managers.ChickenFight;
import me.O_o_Fadi_o_O.MiniGames.managers.DatabaseManager;
import me.O_o_Fadi_o_O.MiniGames.managers.StorageManager;
import me.O_o_Fadi_o_O.MiniGames.managers.SurvivalGames;
import me.O_o_Fadi_o_O.MiniGames.utils.ChickenFightState;
import me.O_o_Fadi_o_O.MiniGames.utils.Game;
import me.O_o_Fadi_o_O.MiniGames.utils.SurvivalGamesState;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathEvent implements Listener{
	
	Start start = Start.getInstance();
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onDeath(PlayerDeathEvent e){
	
		final Player died = e.getEntity();
		e.setDeathMessage(null);
		
		if(StorageManager.playersgame.containsKey(died)){
			if(StorageManager.playersgame.get(died) == Game.CHICKENFIGHT){
				died.setHealth(20D);
				e.getDrops().clear();
				died.setLevel(0);
				died.setExp(0);
				
				int arena = StorageManager.playersarena.get(died);
				if(StorageManager.chickenfightstate.get(arena) == ChickenFightState.INGAME){
					if(died.getKiller() instanceof Player){
						Player killer = died.getKiller();
						
						StorageManager.chickenfightplayersdied.put(arena, StorageManager.chickenfightplayersdied.get(arena) + 1);
						StorageManager.deadplayersinchickenfight.add(died);
						
						for(Player player : Bukkit.getOnlinePlayers()){
							if(StorageManager.playersgame.containsKey(player)){
								if(StorageManager.playersgame.get(player) == Game.CHICKENFIGHT){
									if(StorageManager.playersarena.get(player).equals(arena)){
										
										player.sendMessage("§7Death §6| §c" + died.getName() + "§7 killed by §c" + killer.getName());
										
									}
								}
							}
						}
						
						killer.sendMessage("§7Stats §6| §2§l+ 1 Kill");

						DatabaseManager.addChickenFightKills(killer, 1);
						killer.playSound(killer.getLocation(), Sound.LAVA_POP, 5, 1);
						
						StorageManager.chickenfightroundkills.put(killer, StorageManager.chickenfightroundkills.get(killer) +1);
						killer.sendMessage("§7Stats §6| §f§lCurrent Streak: §2§l" + StorageManager.chickenfightroundkills.get(killer) + " §f§lBest Streak: §2§l" + StorageManager.chickenfightbeststreak.get(killer));
						if(StorageManager.chickenfightbeststreak.get(killer) < StorageManager.chickenfightroundkills.get(killer)){
							killer.sendMessage("§7Stats §6| §f§lNew Best Streak: §2§l" + StorageManager.chickenfightroundkills.get(killer));

							DatabaseManager.setBestChickenFightStreak(killer, StorageManager.chickenfightroundkills.get(killer));
						}
						
						StorageManager.playersinchickenfight.remove(died);
						StorageManager.chickenfightplayers.put(arena, StorageManager.chickenfightplayers.get(arena) - 1);
						
						StorageManager.spectatorsinchickenfight.add(died);
						StorageManager.chickenfightspectators.put(arena, StorageManager.chickenfightspectators.get(arena) + 1);
						
						died.setAllowFlight(true);
						died.setFlying(true);
					    ((CraftPlayer) died).getHandle().setInvisible(true);
					    died.teleport(StorageManager.chickenfightspectatorlocation.get(StorageManager.chickenfightcurrentmap.get(arena)));
						
						start.getServer().getScheduler().scheduleSyncDelayedTask(this.start, new Runnable(){
							public void run(){
								SpectatorKit.giveInventory(died);
								DisguisePlayer.undisguisePlayer(died);
							} 
						}, 1L);
					    
						if(StorageManager.chickenfightplayers.get(arena) == 1){
							ChickenFight.endGame(arena);
						}
						
					}	
					else{
						for(Player player : Bukkit.getOnlinePlayers()){
							if(StorageManager.playersgame.containsKey(player)){
								if(StorageManager.playersgame.get(player) == Game.CHICKENFIGHT){
									if(StorageManager.playersarena.get(player).equals(arena)){
										
										player.sendMessage("§7Death §6| §c" + died.getName());
										
									}
								}
							}
						}
						
						StorageManager.chickenfightplayersdied.put(arena, StorageManager.chickenfightplayersdied.get(arena) + 1);
						StorageManager.deadplayersinchickenfight.add(died);
						
						StorageManager.playersinchickenfight.remove(died);
						StorageManager.chickenfightplayers.put(arena, StorageManager.chickenfightplayers.get(arena) - 1);
						
						StorageManager.spectatorsinchickenfight.add(died);
						StorageManager.chickenfightspectators.put(arena, StorageManager.chickenfightspectators.get(arena) + 1);
						
						died.setAllowFlight(true);
						died.setFlying(true);
					    ((CraftPlayer) died).getHandle().setInvisible(true);
					    died.teleport(StorageManager.chickenfightspectatorlocation.get(StorageManager.chickenfightcurrentmap.get(arena)));
					    
					    
						start.getServer().getScheduler().scheduleSyncDelayedTask(this.start, new Runnable(){
							public void run(){
								SpectatorKit.giveInventory(died);
								DisguisePlayer.undisguisePlayer(died);
							} 
						}, 1L);
						
						if(StorageManager.chickenfightplayers.get(arena) == 1){
							ChickenFight.endGame(arena);
						}
						
					}
				}
			}
		}
	}
}
