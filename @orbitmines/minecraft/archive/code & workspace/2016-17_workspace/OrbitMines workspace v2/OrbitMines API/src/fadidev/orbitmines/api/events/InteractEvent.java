package fadidev.orbitmines.api.events;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.bukkit.selections.CuboidSelection;
import fadidev.orbitmines.api.handlers.OMPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class InteractEvent implements Listener {
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onInteract(PlayerInteractEvent e){
		Player p = e.getPlayer();
		OMPlayer omp = OMPlayer.getOMPlayer(p);
		
		if(omp.isLoaded()){
			if(omp.isAfk())
				omp.noLongerAfk();

            if(Bukkit.getServer().getPluginManager().getPlugin("WorldEdit") == null)
                return;

            Action a = e.getAction();
            ItemStack item = e.getItem();
            Block b = e.getClickedBlock();

			if(omp.isOpMode() && (a == Action.RIGHT_CLICK_BLOCK || a == Action.LEFT_CLICK_BLOCK) && item != null && item.getType() == Material.BARRIER && item.getEnchantments() != null && item.getEnchantments().size() > 0){
				e.setCancelled(true);
				WorldEditPlugin worldEdit = (WorldEditPlugin) Bukkit.getServer().getPluginManager().getPlugin("WorldEdit");
				worldEdit.setSelection(p, new CuboidSelection(p.getWorld(), b.getLocation(), b.getLocation()));
				p.chat("//expand 100 up");
				p.chat("//replace 0 166");
			}
		}
		else{
			omp.notLoaded();
		}
	}
}
