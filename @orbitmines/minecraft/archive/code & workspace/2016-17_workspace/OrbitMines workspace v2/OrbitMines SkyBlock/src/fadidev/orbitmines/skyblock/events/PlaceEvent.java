package fadidev.orbitmines.skyblock.events;

import fadidev.orbitmines.skyblock.OrbitMinesSkyBlock;
import fadidev.orbitmines.skyblock.handlers.SkyBlockPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

/**
 * Created by Fadi on 10-9-2016.
 */
public class PlaceEvent implements Listener {

    private OrbitMinesSkyBlock skyBlock;

    public PlaceEvent(){
        skyBlock = OrbitMinesSkyBlock.getSkyBlock();
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e){
        Player p = e.getPlayer();
        SkyBlockPlayer omp = SkyBlockPlayer.getSkyBlockPlayer(p);

        if(omp.isLoaded()) {
            if (omp.isOpMode())
                return;

            if(p.getWorld().getName().equals(skyBlock.getSkyBlockWorld().getName()) || p.getWorld().getName().equals(skyBlock.getSkyBlockNetherWorld().getName())){
                if(omp.hasIsland()){
                    if(!omp.onIsland(e.getBlockPlaced().getLocation(), true))
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
        else{
            e.setCancelled(true);
            omp.notLoaded();
        }
    }
}
