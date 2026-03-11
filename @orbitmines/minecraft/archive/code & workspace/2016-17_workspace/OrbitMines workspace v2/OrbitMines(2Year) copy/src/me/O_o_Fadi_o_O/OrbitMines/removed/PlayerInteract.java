package me.O_o_Fadi_o_O.OrbitMines.removed;

import me.O_o_Fadi_o_O.OrbitMines.Start;
import me.O_o_Fadi_o_O.OrbitMines.utils.Utils.GameState;
import me.O_o_Fadi_o_O.OrbitMines.utils.Utils.MiniGameType;
import me.O_o_Fadi_o_O.OrbitMines.utils.kitpvp.KitPvPPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerInteract implements Listener{
	
	Start hub = Start.getInstance();
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onInteract(PlayerInteractEvent e){
		Player p = e.getPlayer();
		OMPlayer omp = OMPlayer.getOMPlayer(p);
		ItemStack item = e.getItem();
		
		if(omp.isLoaded()){
			InteractManager manager = new InteractManager(e);

			//Hub:
			/*Block b = e.getClickedBlock();
			if(b != null && b.getType() == Material.TRAPPED_CHEST){
				if(!omp.hasDisguise(Disguise.ARMOR_STAND)){
					omp.addDisguise(Disguise.ARMOR_STAND);

					p.sendMessage("§7You can no longer open this §6§lSurprise Crate§7!");
				}
				else{
					p.sendMessage("§7You already opened this §6§lSurprise Crate§7!");
				}
			}*/

		}
		else{
			omp.notLoaded();
		}
	}
}
