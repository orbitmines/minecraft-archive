package me.O_o_Fadi_o_O.SkyBlock.events;

import java.util.ArrayList;
import java.util.List;

import me.O_o_Fadi_o_O.SkyBlock.managers.ConfigManager;
import me.O_o_Fadi_o_O.SkyBlock.managers.IslandManager;
import me.O_o_Fadi_o_O.SkyBlock.managers.PlayerManager;
import me.O_o_Fadi_o_O.SkyBlock.managers.StorageManager;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinEvent implements Listener{

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onJoin(PlayerJoinEvent e){
		Player p = e.getPlayer();
		
		e.setJoinMessage("§2§l§o>> " + p.getName() + " §2§o(§a§oSkyBlock§2§o)");

		loadPlayerIslandInfo(p);
		
		if(!PlayerManager.hasIsland(p)){
			p.chat("/spawn");
		}
	}
	
	public void loadPlayerIslandInfo(Player p){
		if(!ConfigManager.playerdata.contains("players." + p.getName())){
			StorageManager.PlayerHasIsland.put(p, false);
		}
		else{
			StorageManager.PlayerHasIsland.put(p, true);
			int IslandNumber = IslandManager.getPlayersIslandNumber(p);

			StorageManager.PlayersIslandNumber.put(p, IslandNumber);
			StorageManager.PlayersIslandRank.put(p, IslandManager.getPlayersIslandRank(p));
			StorageManager.PlayersIslandHomeLocation.put(p, IslandManager.getPlayersIslandHomeLocation(p));
			
			List<String> challenges = new ArrayList<String>();
			for(String s : StorageManager.Challenges){
				if(!ConfigManager.playerdata.contains("players." + p.getName() + ".Challenges." + s)){
					IslandManager.setChallengeCompletedAmount(p, s, 0);
				}
				challenges.add(s + "|" + IslandManager.getChallengeCompletedAmount(p, s));
			}
			StorageManager.PlayersChallengesCompleteAmount.put(p, challenges);
		}
	}
}
