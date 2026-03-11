package me.O_o_Fadi_o_O.SpigotSpleef.events;

import me.O_o_Fadi_o_O.SpigotSpleef.managers.ArenaManager;
import me.O_o_Fadi_o_O.SpigotSpleef.managers.MapManager;
import me.O_o_Fadi_o_O.SpigotSpleef.managers.StorageManager;
import me.O_o_Fadi_o_O.SpigotSpleef.utils.Arena;
import me.O_o_Fadi_o_O.SpigotSpleef.utils.ArenaSign;
import me.O_o_Fadi_o_O.SpigotSpleef.utils.SpleefPlayer;
import me.O_o_Fadi_o_O.SpigotSpleef.utils.SpleefStatus;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class InteractEvent implements Listener{
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e){
		Player p = e.getPlayer();
		Action a = e.getAction();
		Block b = e.getClickedBlock();
		
		if(b != null && b.getType() == Material.SIGN_POST || b != null && b.getType() == Material.WALL_SIGN){
			if(StorageManager.spleefplayer.containsKey(p)){
				SpleefPlayer sp = StorageManager.spleefplayer.get(p);
				
				if(!sp.isInArena()){
					if(ArenaSign.isSignArenaSign(b.getLocation())){
						e.setCancelled(true);
						Arena arena = ArenaSign.getArenaSign(b.getLocation()).getArena();
						sp.joinSpleef(arena);
					}
				}
			}
		}
		
		if(StorageManager.mapsetup.containsKey(p)){
			if(a == Action.LEFT_CLICK_AIR || a == Action.LEFT_CLICK_BLOCK){
				MapManager.openSetupInventory(p, StorageManager.mapsetup.get(p));
			}
		}
		if(StorageManager.arenasetup.containsKey(p)){
			if(a == Action.LEFT_CLICK_AIR || a == Action.LEFT_CLICK_BLOCK){
				
				if(b != null && b.getType() == Material.SIGN_POST || b != null && b.getType() == Material.WALL_SIGN){
					e.setCancelled(true);
					ArenaManager.setArenaSign(p, b);
				}
				else{
					ArenaManager.openSetupInventory(p, StorageManager.arenasetup.get(p));
				}
			}
		}
		
		if(StorageManager.spleefplayer.containsKey(p)){
			SpleefPlayer sp = StorageManager.spleefplayer.get(p);
			
			if(sp.isInArena()){
				if(sp.getArena().isStatus(SpleefStatus.INGAME)){
					if(a == Action.LEFT_CLICK_BLOCK){
						sp.getArena().breakBlock(sp, e.getClickedBlock());
					}
					
					if(a == Action.RIGHT_CLICK_AIR || a == Action.RIGHT_CLICK_BLOCK){
						//TODO ABILITIES
					}
				}
				else{
					e.setCancelled(true);
				}
			}
		}
	}
}
