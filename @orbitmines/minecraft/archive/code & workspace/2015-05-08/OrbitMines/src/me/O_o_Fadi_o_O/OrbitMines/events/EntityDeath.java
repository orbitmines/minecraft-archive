package me.O_o_Fadi_o_O.OrbitMines.events;

import me.O_o_Fadi_o_O.OrbitMines.utils.ServerData;
import me.O_o_Fadi_o_O.OrbitMines.utils.Utils.Server;

import org.bukkit.entity.Creeper;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class EntityDeath implements Listener{
	
	@EventHandler
	public void onDeath(EntityDeathEvent e){
		if(ServerData.isServer(Server.HUB, Server.MINIGAMES) && e.getEntity() instanceof Creeper){
			e.getDrops().clear();
		}
	}
}
