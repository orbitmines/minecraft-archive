package fadidev.orbitmines.skyblock.events;

import fadidev.orbitmines.skyblock.OrbitMinesSkyBlock;
import fadidev.orbitmines.skyblock.handlers.SkyBlockPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

/**
 * Created by Fadi on 20-9-2016.
 */
public class BreakEvent implements Listener {

    private OrbitMinesSkyBlock skyBlock;

    public BreakEvent(){
        skyBlock = OrbitMinesSkyBlock.getSkyBlock();
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e){
        Player p = e.getPlayer();
        SkyBlockPlayer omp = SkyBlockPlayer.getSkyBlockPlayer(p);

        if(p.getWorld().getName().equals(skyBlock.getSkyBlockWorld().getName()) || p.getWorld().getName().equals(skyBlock.getSkyBlockNetherWorld().getName())){
            if(omp.hasIsland()){
                if(!omp.onIsland(e.getBlock().getLocation(), true))
                    e.setCancelled(true);
            }
            else{
                e.setCancelled(true);
            }
        }
        else if(p.getWorld().getName().equals(skyBlock.getLobby().getName())){
            e.setCancelled(true);
        }
    }
}
