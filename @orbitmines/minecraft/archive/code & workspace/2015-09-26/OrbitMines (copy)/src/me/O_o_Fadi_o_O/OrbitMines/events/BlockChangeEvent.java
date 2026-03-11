package me.O_o_Fadi_o_O.OrbitMines.events;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import me.O_o_Fadi_o_O.OrbitMines.Start;
import me.O_o_Fadi_o_O.OrbitMines.utils.OMPlayer;
import me.O_o_Fadi_o_O.OrbitMines.utils.ServerData;
import me.O_o_Fadi_o_O.OrbitMines.utils.Utils;
import me.O_o_Fadi_o_O.OrbitMines.utils.Utils.Server;
import om.kitpvp.handlers.KitPvPMap;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class BlockChangeEvent implements Listener{
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onChange(final EntityChangeBlockEvent e){
		Entity en = e.getEntity();
		
		if(en instanceof FallingBlock){
			FallingBlock b = (FallingBlock) en;
			
			if(b.getMaterial() == Material.WEB || b.getMaterial() == Material.FIRE){
				int delay = 100;
				if(ServerData.isServer(Server.HUB, Server.MINIGAMES) && b.getMaterial() == Material.FIRE){
					delay = 40;
				}
				
				new BukkitRunnable(){
					public void run(){
						e.getBlock().setType(Material.AIR);
					}
				}.runTaskLater(Start.getInstance(), delay);
			}
			else{
				en.remove();
				e.setCancelled(true);
			}
		}
	}
}
