package me.O_o_Fadi_o_O.OrbitMines.events;

import me.O_o_Fadi_o_O.OrbitMines.utils.OMPlayer;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

public class SignEvent implements Listener {

	@EventHandler
	public void onSign(SignChangeEvent e){
		Player p = e.getPlayer();
		OMPlayer omp = OMPlayer.getOMPlayer(p);
		
		if(p.hasPermission("OrbitMines.signs") || omp.isLoaded() && omp.isOpMode()){
			e.setLine(0, ChatColor.translateAlternateColorCodes('&', e.getLine(0)));
			e.setLine(1, ChatColor.translateAlternateColorCodes('&', e.getLine(1)));
			e.setLine(2, ChatColor.translateAlternateColorCodes('&', e.getLine(2)));
			e.setLine(3, ChatColor.translateAlternateColorCodes('&', e.getLine(3)));
		}
	}
}
