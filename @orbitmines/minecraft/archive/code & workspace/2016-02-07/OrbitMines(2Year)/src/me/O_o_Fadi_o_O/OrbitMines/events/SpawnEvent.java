package me.O_o_Fadi_o_O.OrbitMines.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

public class SpawnEvent implements Listener {

	@EventHandler
	public void onSpawn(CreatureSpawnEvent e){
		if(e.getSpawnReason() == SpawnReason.BUILD_IRONGOLEM || e.getSpawnReason() == SpawnReason.BUILD_SNOWMAN || e.getSpawnReason() == SpawnReason.BUILD_WITHER){
			e.setCancelled(true);
		}
	}
}
