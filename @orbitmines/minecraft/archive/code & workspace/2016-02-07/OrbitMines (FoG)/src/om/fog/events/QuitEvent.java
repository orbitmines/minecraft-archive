package om.fog.events;

import om.fog.handlers.players.FoGPlayer;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.CraftingInventory;

public class QuitEvent implements Listener {

	@EventHandler
	public void onQuit(PlayerQuitEvent e){
		Player p = e.getPlayer();
		FoGPlayer omp = FoGPlayer.getFoGPlayer(p);
		
		if(omp.getFaction() != null && p.getOpenInventory().getTopInventory() instanceof CraftingInventory){
			p.getOpenInventory().getTopInventory().clear();
		}
		
		if(omp.isInCombat()){
			p.damage(1000D);
			p.teleport(omp.getFaction().getBaseLocation());
		}
	}
}
