package me.O_o_Fadi_o_O.UHC.events;

import me.O_o_Fadi_o_O.UHC.Start;
import me.O_o_Fadi_o_O.UHC.managers.Manager;
import me.O_o_Fadi_o_O.UHC.utils.GameState;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreak implements Listener{
	
	@EventHandler
	public void onBreak(BlockBreakEvent e){
		
		Player p = e.getPlayer();
		if(!p.isOp()){
			if(Start.state == GameState.NOPVP || Start.state == GameState.PVP){}
			else{
				e.setCancelled(true);
			}
		}
		if(Manager.Spectators.contains(p)){
			e.setCancelled(true);
		}
	}
}
