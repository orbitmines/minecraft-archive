package me.O_o_Fadi_o_O.OrbitMines.events;

import me.O_o_Fadi_o_O.OrbitMines.utils.NPC;
import me.O_o_Fadi_o_O.OrbitMines.utils.OMPlayer;
import me.O_o_Fadi_o_O.OrbitMines.utils.ServerData;
import me.O_o_Fadi_o_O.OrbitMines.utils.ServerData.ServerStorage;
import me.O_o_Fadi_o_O.OrbitMines.utils.Utils.Server;

import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.util.Vector;

public class ExplodeEvent implements Listener{
	
	@EventHandler
    public void EntityExplodeEvent(ExplosionPrimeEvent e){
		if(ServerData.isServer(Server.HUB, Server.MINIGAMES)){
			e.setFire(false);
			
	        if(e.getEntity() instanceof Creeper){
	        	if(ServerStorage.creeperlaunched.contains(e.getEntity())){
		        	for(Entity en : e.getEntity().getNearbyEntities(3, 3, 3)){
		        		if(NPC.getNPC(en) != null){
		        			e.setRadius(0);
		        		}
		        		
		        		if(en instanceof Player){
		        			Player p = (Player) en;
		        			OMPlayer omp = OMPlayer.getOMPlayer(p);
		        			if(!omp.isInLapisParkour()){
		        				Vector v = p.getLocation().getDirection();
		        				p.setVelocity(v.multiply(-1).add(new Vector(0, 1.3, 0)));
		        			}	
		        		}
	        		}
	        	}
	        }
		}
	}
}
