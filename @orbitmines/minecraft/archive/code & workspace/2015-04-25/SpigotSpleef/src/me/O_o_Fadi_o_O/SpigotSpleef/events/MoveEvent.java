package me.O_o_Fadi_o_O.SpigotSpleef.events;

import me.O_o_Fadi_o_O.SpigotSpleef.managers.StorageManager;
import me.O_o_Fadi_o_O.SpigotSpleef.utils.SpleefPlayer;
import me.O_o_Fadi_o_O.SpigotSpleef.utils.SpleefStatus;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class MoveEvent implements Listener {
	
	@EventHandler
	public void onMove(PlayerMoveEvent e){
		Player p = e.getPlayer();
		
		if(StorageManager.spleefplayer.containsKey(p)){
			SpleefPlayer sp = StorageManager.spleefplayer.get(p);
			
			if(sp.isInArena() && sp.isPlayer()){
				if(sp.getArena().isStatus(SpleefStatus.INGAME)){
					if(p.getLocation().getY() <= StorageManager.diewhenyis){
						sp.die();
					}
				}
			}
		}
	}
}
