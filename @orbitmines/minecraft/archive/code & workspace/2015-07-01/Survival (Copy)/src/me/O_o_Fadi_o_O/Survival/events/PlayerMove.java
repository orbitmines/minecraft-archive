package me.O_o_Fadi_o_O.Survival.events;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import me.O_o_Fadi_o_O.Survival.Start;
import me.O_o_Fadi_o_O.Survival.managers.PlayerManager;
import me.O_o_Fadi_o_O.Survival.managers.RegionManager;
import me.O_o_Fadi_o_O.Survival.managers.StorageManager;
import me.O_o_Fadi_o_O.Survival.managers.TitleManager;
import me.O_o_Fadi_o_O.Survival.utils.Trail;
import me.O_o_Fadi_o_O.Survival.utils.TrailType;
import net.minecraft.server.v1_8_R1.EnumParticle;
import net.minecraft.server.v1_8_R1.PacketPlayOutWorldParticles;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMove implements Listener{

	Start start = Start.getInstance();
	
	private Map<String, Long> lastconnected = new HashMap<String, Long>();
	private final int connectcooldown = 3;
	
    @SuppressWarnings("deprecation")
	@EventHandler
    public void onMove(final PlayerMoveEvent e) {

		if(StorageManager.spawnteleporting.containsKey(p)){
			StorageManager.spawnteleporting.remove(p);
			TitleManager.setTitle(p, "", "§c§lCancelled§6 Spawn §7Teleportation.", 0, 40, 20);
		}
		if(StorageManager.playersinpvp.contains(p) || StorageManager.pvpteleporting.containsKey(p) || StorageManager.pvpcountdown.containsKey(p)){
			if(p.getAllowFlight() == true){
				p.setFlying(false);
				p.setAllowFlight(false);
			}
		}
		if(StorageManager.hometeleporting.containsKey(p)){
			StorageManager.hometeleporting.remove(p);
			TitleManager.setTitle(p, "", "§c§lCancelled§6 " + StorageManager.hometeleportingto.get(p) + " §7Teleportation.", 0, 40, 20);
			StorageManager.hometeleportinglocation.remove(p);
			StorageManager.hometeleportingto.remove(p);
		}
		
		
		if(p.getWorld().getName().equals(StorageManager.lobbyworld.getName())){
			Block b = p.getWorld().getBlockAt(p.getLocation());
			
			long lastconnect = 0;
			if(lastconnected.containsKey(p.getName())){
				lastconnect = lastconnected.get(p.getName());
			}
			int cdmillis = connectcooldown * 1000;
					
			if(System.currentTimeMillis()-lastconnect>=cdmillis){
				if(StorageManager.worldportals.get(1).contains(b)){
					RegionManager.teleportToRegion(p, new Random().nextInt(StorageManager.regionbiome.size()) +1);

					lastconnected.put(p.getName(), System.currentTimeMillis());
				}
			}
		}
    }
}
