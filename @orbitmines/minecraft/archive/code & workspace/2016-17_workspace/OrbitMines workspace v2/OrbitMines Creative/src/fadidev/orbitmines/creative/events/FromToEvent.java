package fadidev.orbitmines.creative.events;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;

/**
 * Created by Fadi on 29-9-2016.
 */
public class FromToEvent implements Listener {

    @EventHandler
    public void onChange(final BlockFromToEvent e) {
        Block b = e.getBlock();
        e.setCancelled(b.getType() == Material.WATER || b.getType() == Material.LAVA || b.getType() == Material.STATIONARY_WATER || b.getType() == Material.STATIONARY_LAVA);
    }
}
