package fadidev.orbitmines.creative.events;

import fadidev.orbitmines.creative.handlers.CreativePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

/**
 * Created by Fadi on 15-9-2016.
 */
public class DropEvent implements Listener {

    @EventHandler
    public void onDrop(PlayerDropItemEvent e){
        Player p = e.getPlayer();
        CreativePlayer omp = CreativePlayer.getCreativePlayer(p);

        if(omp.isLoaded()){
            if(!omp.isOpMode()){
                e.setCancelled(true);
                omp.updateInventory();
            }
        }
        else{
            omp.notLoaded();
            e.setCancelled(true);
            omp.updateInventory();
        }
    }
}
