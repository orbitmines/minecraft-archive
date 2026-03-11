package fadidev.orbitmines.hub.events;

import fadidev.orbitmines.hub.handlers.players.HubPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

/**
 * Created by Fadi on 10-9-2016.
 */
public class PlaceEvent implements Listener {

    @EventHandler
    public void onPlace(final BlockPlaceEvent e){
        Player p = e.getPlayer();
        HubPlayer omp = HubPlayer.getHubPlayer(p);

        if(omp.isLoaded()){
            if(omp.isOpMode())
                return;

            if(!omp.inBuilderWorld()){
                e.setCancelled(true);
                omp.updateInventory();
            }
        }
        else{
            e.setCancelled(true);
            omp.notLoaded();
        }
    }
}
