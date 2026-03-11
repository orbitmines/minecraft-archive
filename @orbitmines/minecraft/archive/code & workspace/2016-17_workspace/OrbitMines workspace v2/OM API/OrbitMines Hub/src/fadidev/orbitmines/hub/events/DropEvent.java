package fadidev.orbitmines.hub.events;

import fadidev.orbitmines.api.handlers.OMPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

/**
 * Created by Fadi on 10-9-2016.
 */
public class DropEvent implements Listener {

    @EventHandler
    public void onDrop(PlayerDropItemEvent e){
        final Player p = e.getPlayer();
        OMPlayer omp = OMPlayer.getOMPlayer(p);

        if(omp.isLoaded() && !omp.isOpMode()){
            e.setCancelled(true);
            omp.updateInventory();
        }
    }
}
