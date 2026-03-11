package fadidev.orbitmines.prison.events;

import fadidev.orbitmines.prison.OrbitMinesPrison;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;

/**
 * Created by Fadi on 29-9-2016.
 */
public class FromToEvent implements Listener {

    private OrbitMinesPrison prison;

    public FromToEvent(){
        prison = OrbitMinesPrison.getPrison();
    }

    @EventHandler
    public void onChange(final BlockFromToEvent e) {
        Block b = e.getBlock();

        if(!b.getWorld().getName().equalsIgnoreCase(prison.getCellWorld().getName()))
            return;

        int x = Math.abs(e.getToBlock().getX()) -7;
        if(b.getType() == Material.WATER || b.getType() == Material.LAVA || b.getType() == Material.STATIONARY_WATER || b.getType() == Material.STATIONARY_LAVA)
            e.setCancelled(x == 0 || x % 43 == 0 || x % 43 == 29 || e.getToBlock().getType() == Material.TORCH);
    }
}
