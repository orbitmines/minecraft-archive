package me.O_o_Fadi_o_O.UHC.events;

import me.O_o_Fadi_o_O.UHC.Start;
import me.O_o_Fadi_o_O.UHC.utils.GameState;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class FoodEvent implements Listener{
	
	@EventHandler
	public void onFood(FoodLevelChangeEvent e){
		
		if(Start.state != GameState.NOPVP && Start.state != GameState.PVP){
			e.setFoodLevel(20);
		}
	}
}
